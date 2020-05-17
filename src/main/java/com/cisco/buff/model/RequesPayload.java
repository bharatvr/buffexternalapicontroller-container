package com.cisco.buff.model;

import java.io.Serializable;

public class RequesPayload implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String botId;
	private String profile;
	private String inputText;
	private String clientTrxId;
	private boolean enableCrossProfile=false;
	private int numSuggestion=10;
	private double kbThresholdProfile=0.5; // .6 for monica service
	private double kbThresholdCrossProfile=0.5; // .7 for monica service
	
	
	
	/**
	 * @return the botId
	 */
	public String getBotId() {
		return botId;
	}
	/**
	 * @param botId the botId to set
	 */
	public void setBotId(String botId) {
		this.botId = botId;
	}
	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}
	/**
	 * @return the inputText
	 */
	public String getInputText() {
		return inputText;
	}
	/**
	 * @param inputText the inputText to set
	 */
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	/**
	 * @return the numSuggestion
	 */
	public int getNumSuggestion() {
		return numSuggestion;
	}
	/**
	 * @param numSuggestion the numSuggestion to set
	 */
	public void setNumSuggestion(int numSuggestion) {
		this.numSuggestion = numSuggestion;
	}
	/**
	 * @return the clientTrxId
	 */
	public String getClientTrxId() {
		return clientTrxId;
	}
	/**
	 * @param clientTrxId the clientTrxId to set
	 */
	public void setClientTrxId(String clientTrxId) {
		this.clientTrxId = clientTrxId;
	}
	/**
	 * @return the enableCrossProfile
	 */
	public boolean isEnableCrossProfile() {
		return enableCrossProfile;
	}
	/**
	 * @param enableCrossProfile the enableCrossProfile to set
	 */
	public void setEnableCrossProfile(boolean enableCrossProfile) {
		this.enableCrossProfile = enableCrossProfile;
	}
	/**
	 * @return the kbThresholdProfile
	 */
	public double getKbThresholdProfile() {
		return kbThresholdProfile;
	}
	/**
	 * @param kbThresholdProfile the kbThresholdProfile to set
	 */
	public void setKbThresholdProfile(double kbThresholdProfile) {
		this.kbThresholdProfile = kbThresholdProfile;
	}
	/**
	 * @return the kbThresholdCrossProfile
	 */
	public double getKbThresholdCrossProfile() {
		return kbThresholdCrossProfile;
	}
	/**
	 * @param kbThresholdCrossProfile the kbThresholdCrossProfile to set
	 */
	public void setKbThresholdCrossProfile(double kbThresholdCrossProfile) {
		this.kbThresholdCrossProfile = kbThresholdCrossProfile;
	}
 
}
