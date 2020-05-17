/**
 * 
 */
package com.cisco.buff.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author bharatsi
 *
 */

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ServiceError.class)
public class ServiceErrorTest {

	@Test
	public void testMessagesCode() {


		ServiceError serviveError = new ServiceError(null, null, null);
		String code = "1001";
		String type = "error";
		String errorMsg = "error message";
		serviveError.setCode(code);
		serviveError.setErrorMsg(errorMsg);
		serviveError.setType(type);

		assertEquals(code, serviveError.getCode());
		assertEquals(type, serviveError.getType());
		assertEquals(errorMsg, serviveError.getErrorMsg());

	}
	

}
