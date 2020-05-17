package com.cisco.buff.model;

public class LicesningProfile {

	private String key;
	private String value;
	private boolean primary;
	private double kbConfidence;

	public LicesningProfile(String key, String value, double kbConfidence, boolean primary) {
		this.key = key;
		this.value = value;
		this.kbConfidence = kbConfidence;
		this.primary = primary;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the kbConfidence
	 */
	public double getKbConfidence() {
		return kbConfidence;
	}

	/**
	 * @return the primary
	 */
	public boolean isPrimary() {
		return primary;
	}

}
