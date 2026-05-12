package com.basesource.action;

import java.util.HashMap;

/**
 * Class for individual mailinator email to be used in Mailinator.java
 * 
 * @author kumargup
 *
 */
public class MailinatorMail {
	private long secondsAgo;
	private String id;
	private String to;
	private long time;
	private String subject;
	private String fromFull;
	private HashMap<String, String> headers;
	private String body;
	private String from;

	/**
	 * Getter method for secondsAgo
	 * 
	 * @return
	 */
	public long getSecondsAgo() {
		return secondsAgo;
	}

	/**
	 * Setter method for secondsAgo
	 * 
	 * @param secondsAgo
	 */
	public void setSecondsAgo(long secondsAgo) {
		this.secondsAgo = secondsAgo;
	}

	/**
	 * Getter method of id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter method for id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter method for to
	 * 
	 * @return
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Setter method for to
	 * 
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Getter method for time
	 * 
	 * @return
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Setter method for time
	 * 
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Getter method for subject
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter method for subject
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter method for fromFull
	 * 
	 * @return
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Setter method for from
	 * 
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Getter method for fromFull
	 * 
	 * @return
	 */
	public String getFromFull() {
		return fromFull;
	}

	/**
	 * Setter method for fromFull
	 * 
	 * @param fromFull
	 */
	public void setFromFull(String fromFull) {
		this.fromFull = fromFull;
	}

	/**
	 * Getter method for headers
	 * 
	 * @return
	 */
	public HashMap<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Setter method for headers
	 * 
	 * @param headers
	 */
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * Getter method for body
	 * 
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Setter method for body
	 * 
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}
}