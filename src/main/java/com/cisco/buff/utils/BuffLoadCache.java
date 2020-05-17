/**
 * 
 */
package com.cisco.buff.utils;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author bharatsi
 *
 */
@Component
public class BuffLoadCache {

	@PostConstruct
	public void init() {

		LicensingProfileCache.loadLicensingProfile();

	}
}
