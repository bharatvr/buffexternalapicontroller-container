package com.cisco.buff.model;

import java.util.ArrayList;
import java.util.List;


public class MonicaResponse {
	
	 private  String message;
	 private  List<MonicaSuggestion> suggestionList = new ArrayList<>();
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the suggestionList
	 */
	public List<MonicaSuggestion> getSuggestionList() {
		return suggestionList;
	}
	/**
	 * @param suggestionList the suggestionList to set
	 */
	public void setSuggestionList(List<MonicaSuggestion> suggestionList) {
		this.suggestionList = suggestionList;
	}
	 
	 
	

}
