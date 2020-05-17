/**
 * 
 */
package com.cisco.buff.utils;

/**
 * @author bharatsi
 *
 */
public class BuffServiceException  extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String URI_SYNTAX_EXCEPTION = "RISyntaxException";
	public static final String JSON_EXCEPTION = "JSONException";
	public static final String MALFORMED_URI_EXCEPTION = "MalformedURLException";
	public static final String EXCEPTION = "Exception";
	public static final String RESOURCE_ACCESS_EXCEPTION = "ResourceAccessException";
	
	public BuffServiceException(String errorMessage) {
		super(errorMessage);
	}

}
