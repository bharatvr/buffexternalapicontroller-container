/**
 * 
 */
package com.cisco.buff.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.cisco.buff.response.ServiceResponseWrapper;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CommonUtils.class)
public class CommonUtilsTest {

	CommonUtils commonUtils = new CommonUtils();
	
	@Before
    public void setup() {
		
		BuffAPIErrorCode.loadAPIErrorCode();
	}

	@Test
	public void textGetRefrenceId() {

		String clientTrxId = "clientTrxId";
		assertEquals("clientTrxId", commonUtils.getRefrenceId(clientTrxId));

		assertNotEquals("clientTrxId", commonUtils.getRefrenceId(null));
	}

	@Test
	public void textCurrentDated() {

		assertNotNull(commonUtils.getCurrentDate());

	}

	@Test
	public void textGetErrorResponseMessage() {

		ServiceResponseWrapper<Object> wrapper = commonUtils.getErrorResponseMessage("refrenceId", "new error message");

		assertEquals("101", wrapper.getResponseStatus().getErrors().getCode());

		wrapper = commonUtils.getErrorResponseMessage("refrenceId", "RISyntaxException");

		assertEquals("102", wrapper.getResponseStatus().getErrors().getCode());

	}
}
