/**
 * 
 */
package com.cisco.buff.utils;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ConstantK.class)
public class ConstantKTest {

	@Test
	public void textGetRefrenceId() {
		String expected = "No Data";
		assertTrue(expected == ConstantK.NO_DATA);
	}

	@Test
	public void testValidatePrivateConstructor() {
		try {

			Constructor<ConstantK> cls = ConstantK.class.getDeclaredConstructor();

			cls.setAccessible(true);

			cls.newInstance();

		} catch (Exception ex) {
			assertTrue("java.lang.reflect.InvocationTargetException" == ex.toString());
		}
	}
}
