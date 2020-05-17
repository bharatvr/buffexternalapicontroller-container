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
public class BuffLoadAPIErrorCode {



	@PostConstruct
	public void init() {

		BuffAPIErrorCode.loadAPIErrorCode();

	}
}
