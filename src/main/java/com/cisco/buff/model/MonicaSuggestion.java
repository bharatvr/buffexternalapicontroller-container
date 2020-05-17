package com.cisco.buff.model;

public class MonicaSuggestion implements Comparable<MonicaSuggestion> {
	
	String text;
	String profile;
	Double confidenceScore;
	String vcaID;
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the confidenceScore
	 */
	public Double getConfidenceScore() {
		return confidenceScore;
	}
	/**
	 * @param confidenceScore the confidenceScore to set
	 */
	public void setConfidenceScore(Double confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	/**
	 * @return the vcaID
	 */
	public String getVcaID() {
		return vcaID;
	}
	/**
	 * @param vcaID the vcaID to set
	 */
	public void setVcaID(String vcaID) {
		this.vcaID = vcaID;
	}
	/**
	 * @return the answer
	 */
	
	
	@Override
    public int compareTo(MonicaSuggestion monicaSuggestion) {
        return this.confidenceScore.compareTo(monicaSuggestion.confidenceScore);
    }
}
