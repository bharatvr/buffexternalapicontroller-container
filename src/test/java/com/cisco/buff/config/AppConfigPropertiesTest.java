/**
 * 
 */
package com.cisco.buff.config;

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
@PrepareForTest(AppConfigProperties.class)
public class AppConfigPropertiesTest {


	@Test
	public void testObject() {
		 AppConfigProperties appConfigProperties =new AppConfigProperties();
		 appConfigProperties.setEnvType("envType");
		 appConfigProperties.setMonicaServiceURL("monicaServiceURL");
		 appConfigProperties.setSkillBuilderServiceURL("skillBuilderServiceURL");

		assertEquals("envType", appConfigProperties.getEnvType());
		assertEquals("monicaServiceURL", appConfigProperties.getServiceURL());
		assertEquals("skillBuilderServiceURL", appConfigProperties.getSkillBuilderServiceURL());
	}

}
