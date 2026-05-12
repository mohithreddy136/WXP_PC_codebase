package com.basesource.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.bouncycastle.util.Strings;

import com.daasui.pages.PreferencesPage;
import com.google.common.io.BaseEncoding;

public class CSRGenerator {
	// Static block to register BouncyCastle provider once (thread-safe)
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	private CSRGenerator instance;
	
	public CSRGenerator getInstance() throws IOException {
		if (instance == null) {
			synchronized (PreferencesPage.class) {
				if (instance == null) {
					instance = new CSRGenerator();

				}
			}
		}
		return instance;
	}

//    public static void main(String[] args) throws Exception {
//        try {
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            String deviceId = "2605a46e-ad08-4c5c-8d5f-3c42450cf05e";
//            if (args.length < 1 || args[0].isEmpty()) {
//                System.out.println("Generating the device ID as it is not received as argument");
//                deviceId = getDeviceId();
//            } else {
//                deviceId = args[0];
//            }
//            String csr = enrollDevice(deviceId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    public final String getDeviceId() throws Exception {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048);
        Base64.Encoder base64 = Base64.getEncoder();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        KeyPair mKeyPair = kpGen.genKeyPair();
        byte[] publicKey = mKeyPair.getPublic().getEncoded();
        PrivateKey privateKey = mKeyPair.getPrivate();
        digest.reset();
        byte[] deviceIdBytes = digest.digest(publicKey);
        byte[] data = publicKey;
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(privateKey);
        s.update(data);
        String deviceIdFinal = base64.encodeToString(deviceIdBytes);
        deviceIdFinal = deviceIdFinal.replace("+", "");
        deviceIdFinal = deviceIdFinal.replace("/", "");
        deviceIdFinal = deviceIdFinal.replace("-", "");
        deviceIdFinal = deviceIdFinal.replace("=", "");
        deviceIdFinal = deviceIdFinal.replace("?", "");
        System.out.println("DeviceId: " + deviceIdFinal + " Length of deviceId is " + deviceIdFinal.length());
        String randomDeviceId = RandomStringUtils.randomAlphanumeric((43 - deviceIdFinal.length()));
        System.out.println("DeviceId: " + randomDeviceId + " Length of deviceId is " + randomDeviceId.length());
        String deviceId = deviceIdFinal + randomDeviceId;
        System.out.println("DeviceId: " + deviceId + " Length of deviceId is " + deviceId.length());
        return deviceId;
    }

    public final String enrollDevice(String deviceId) throws Exception {
        // Use SecureRandom for proper entropy in parallel processing
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048, secureRandom);
        KeyPair pair = kpGen.generateKeyPair();
        PKCS10CertificationRequest request = new PKCS10CertificationRequest("SHA256withRSA", new X500Principal("CN=" + deviceId), pair.getPublic(), new DERSet(), pair.getPrivate());

        PemObject pemObject = new PemObject("CERTIFICATE REQUEST", request.getEncoded());
        StringWriter str = new StringWriter();
        PemWriter pemWriter = new PemWriter(str);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        str.close();
        Base64.Encoder base64 = Base64.getEncoder();
        String csr = base64.encodeToString(str.toString().getBytes());
        System.out.println("Creating CSR for device id " + deviceId + " of length " + deviceId.length());
        System.out.println("==============CSR:======== \n" + csr);
        authenticate(deviceId, pair);
        return csr;
    }

    public static void authenticate(String deviceId, KeyPair pair) throws Exception {
        System.out.println("Generating authenticate data!");
        HashMap<String, String> deviceKeys = getDeviceKeys(pair);
        Date authDate = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss:sss'Z'");
        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String authTimeStamp = sf.format(authDate);
        String authV2Data = "{\"device_id\":\"" + deviceId + "\",\"timestamp\":\"" + authTimeStamp + "\"}";
        System.out.println("Authenticate Data " + authV2Data);
        String deviceIdSignature = signData(deviceKeys.get("privateKey"), authV2Data);
        System.out.println("deviceIdSignature " + deviceIdSignature);
    }

    /**
     * This method will return device public and private keys.
     * 
     * @param mKeyPair
     * @return HashMap
     */
    public static HashMap<String, String> getDeviceKeys(KeyPair mKeyPair) {
        HashMap<String, String> deviceKeys = new HashMap<String, String>();
        Base64.Encoder base64 = Base64.getEncoder();
        String publickey = base64.encodeToString(mKeyPair.getPublic().getEncoded());
        String privatekey = base64.encodeToString(mKeyPair.getPrivate().getEncoded());
        deviceKeys.put("publicKey", publickey);
        deviceKeys.put("privateKey", privatekey);
        return deviceKeys;
    }

    /**
     * This method will generate a random device id signature
     * 
     * @param mKeyPair
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String getDeviceIdSignature(KeyPair mKeyPair) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return signPublicKey(mKeyPair.getPrivate(), mKeyPair.getPublic());
    }

    /**
     * This method will sign public key with private key.
     * 
     * @param prvKey
     * @param data
     * @return String
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    private static String signPublicKey(PrivateKey prvKey, PublicKey pubKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String signedData;
        byte[] data = pubKey.getEncoded();
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(prvKey);
        sign.update(data);
        byte[] signature = sign.sign();
        signedData = new String(BaseEncoding.base64().encode(signature));
        return signedData;
    }

    /**
     * This method will sign String data with private key.
     * 
     * @param prvKey
     * @param data
     * @return String
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws InvalidKeySpecException
     */
    private static String signData(String prvKey, String data) throws Exception {
        PrivateKey privateKey = getPrivateKey(prvKey);
        String signedData;
        byte[] dataByte = data.getBytes("UTF8");
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(dataByte);
        byte[] signature = sign.sign();
        signedData = new String(BaseEncoding.base64().encode(signature));
        return signedData;
    }

    /**
     * This method will decode the private key and return the string
     * 
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] privateBytes = base64Decoder.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        return priv;
    }

}
