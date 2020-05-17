/**
 * 
 */
package com.cisco.buff.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cisco.buff.response.ServiceError;

/**
 * @author bharatsi
 *
 */
public class BuffAPIErrorCode {

	private BuffAPIErrorCode() {

	}

	// Error/Exception code list
	public static final String ERROR_EXCEPTION = "101";
	public static final String ERROR_URI_SYNTAX_EXCEPTION = "102";
	public static final String ERROR_JSON_EXCEPTION = "103";
	public static final String ERROR_CODE_MALFORMED_URI_EXCEPTION = "104";
	public static final String ERROR_CODE_RESOURCE_ACESSS_EXCEPTION = "105";

	// Error/Exception message description details
	public static final String TECHNICAL_ERROR_MESSAGE = "Technical error, Please try again or contact buff api team with error code and ref txn Id.";

	// Type of Error
	public static final String EXCEPTION_ERROR_TYPE = "Exception";

	// Cache map to load all error code and message
	private static Map<String, ServiceError> errorCodeMessageMap = new ConcurrentHashMap<>();

	/**
	 * Method to load all error message when service start
	 */
	public static void loadAPIErrorCode() {

		errorCodeMessageMap.put(BuffServiceException.EXCEPTION,
				new ServiceError(ERROR_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE));

		errorCodeMessageMap.put(BuffServiceException.URI_SYNTAX_EXCEPTION,
				new ServiceError(ERROR_URI_SYNTAX_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE));

		errorCodeMessageMap.put(BuffServiceException.JSON_EXCEPTION,
				new ServiceError(ERROR_JSON_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE));

		errorCodeMessageMap.put(BuffServiceException.MALFORMED_URI_EXCEPTION,
				new ServiceError(ERROR_CODE_MALFORMED_URI_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE));
		
		errorCodeMessageMap.put(BuffServiceException.RESOURCE_ACCESS_EXCEPTION,
				new ServiceError(ERROR_CODE_RESOURCE_ACESSS_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE));

	}

	/**
	 * Method to read error code message from loaded cache map
	 */
	public static ServiceError getEfforCodeMessage(String exceptionMsg) {

		ServiceError error_code_message = ((error_code_message = errorCodeMessageMap.get(exceptionMsg)) != null)
				? error_code_message : new ServiceError(ERROR_EXCEPTION, EXCEPTION_ERROR_TYPE, TECHNICAL_ERROR_MESSAGE);

		return error_code_message;
	}

}
