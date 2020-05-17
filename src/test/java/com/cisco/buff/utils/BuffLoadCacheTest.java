/**
 * 
 */
package com.cisco.buff.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(BuffLoadCache.class)
public class BuffLoadCacheTest {

	@Test
	public void initTest() {
		
		BuffLoadCache buffLoadCache = new BuffLoadCache();
		
		buffLoadCache.init();

	}

}