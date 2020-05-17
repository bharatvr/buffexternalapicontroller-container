/**
 * 
 */
package com.cisco.buff.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.cisco.buff.response.ServiceError;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(BuffLoadAPIErrorCode.class)
public class BuffLoadAPIErrorCodeTest {

	@Test
	public void initCallMethod() {

		BuffLoadAPIErrorCode buffLoadAPIErrorCode = new BuffLoadAPIErrorCode();
		buffLoadAPIErrorCode.init();
		ServiceError serviceError = BuffAPIErrorCode.getEfforCodeMessage("Exception");

		assertNotNull(serviceError);

	}

}
