/**
 * 
 */
package com.cisco.buff.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.cisco.buff.response.ServiceError;
import com.google.gson.Gson;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(BuffAPIErrorCode.class)
public class BuffAPIErrorCodeTest {
	
	
	
	@Before
    public void setup() {
		
		BuffAPIErrorCode.loadAPIErrorCode();
	}
	
	/**
	 * Validation for existing error key
	 */
	@Test
	public void getEfforCodeMessageTest() {
		
		Gson gson = new Gson();

		
		ServiceError actualServiceError=  new ServiceError(BuffAPIErrorCode.ERROR_CODE_MALFORMED_URI_EXCEPTION, BuffAPIErrorCode.EXCEPTION_ERROR_TYPE, BuffAPIErrorCode.TECHNICAL_ERROR_MESSAGE);
				
		String actual = gson.toJson(actualServiceError);
		
		String  exceptionMsg = BuffServiceException.MALFORMED_URI_EXCEPTION;
		ServiceError expectedServiceError = BuffAPIErrorCode.getEfforCodeMessage(exceptionMsg);
		

		String expected = gson.toJson(expectedServiceError);
		
		assertEquals(expected, actual);

		
	}
	
	/**
	 * Validation for non existing error key
	 */
	@Test
	public void getEfforCodeMessageTest1() {
		
		Gson gson = new Gson();

		
		ServiceError actualServiceError=  new ServiceError(BuffAPIErrorCode.ERROR_EXCEPTION, BuffAPIErrorCode.EXCEPTION_ERROR_TYPE, BuffAPIErrorCode.TECHNICAL_ERROR_MESSAGE);
				
		String actual = gson.toJson(actualServiceError);
		
		String  exceptionMsg = "New error code";
		ServiceError expectedServiceError = BuffAPIErrorCode.getEfforCodeMessage(exceptionMsg);
		

		String expected = gson.toJson(expectedServiceError);
		
		assertEquals(expected, actual);

		
	}
}
