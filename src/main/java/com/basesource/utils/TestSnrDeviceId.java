package com.basesource.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSnrDeviceId {
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Parameters provided by user
        String serialNumber = "CPAUTO100751";
        String biosUUID = "66BA178E-7D82-4BB6-9SSS-9335956F8521";
        String productNumber = "1134466#BQQQ";
        
        System.out.println("=== Input Parameters ===");
        System.out.println("Serial Number: " + serialNumber);
        System.out.println("BIOS UUID: " + biosUUID);
        System.out.println("Product Number: " + productNumber);
        System.out.println("\n=== Output ===");
        
        // Execute the method
        snrdeviceid(serialNumber, biosUUID, productNumber);
    }
    
    public static void snrdeviceid(String serialNumber, String biosUUID, String productNumber) throws NoSuchAlgorithmException {
        String deviceId = generateDeviceId(serialNumber, biosUUID, productNumber);
        System.out.println("Device ID (Base64URL): " + deviceId);
    }
    
    /**
     * Generates device ID from serial number, BIOS UUID, and product number
     * @param serialNumber Serial number of the device
     * @param biosUUID BIOS UUID of the device
     * @param productNumber Product number of the device
     * @return Device ID in Base64URL format
     * @throws NoSuchAlgorithmException if SHA-256 algorithm is not available
     */
    public static String generateDeviceId(String serialNumber, String biosUUID, String productNumber) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String deviceIDBase64 = null;
        
        if (biosUUID.contains("-")) {
            deviceIDBase64 = java.util.Base64.getEncoder().encodeToString(
                digest.digest((serialNumber + "/" + biosUUID + "/" + productNumber)
                    .replace("\0", "")
                    .getBytes(StandardCharsets.UTF_8))
            );
        } else {
            deviceIDBase64 = java.util.Base64.getEncoder().encodeToString(
                digest.digest((serialNumber + "/" + 
                    biosUUID.replaceAll("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5")
                           .replaceAll("(.{2})(.{2})(.{2})(.{2}).(.{2})(.{2}).(.{2})(.{2})(.{18})", "$4$3$2$1-$6$5-$8$7$9") 
                    + "/" + productNumber)
                    .replace("\0", "")
                    .getBytes(StandardCharsets.UTF_8))
            );
        }

        return convertBase64ToBase64Url(deviceIDBase64);
    }
    
    /**
     * Convert standard Base64 to Base64URL format
     * - Replace + with -
     * - Replace / with _
     * - Remove padding =
     */
    public static String convertBase64ToBase64Url(String base64) {
        return base64.replace("+", "-")
                    .replace("/", "_")
                    .replace("=", "");
    }
}
