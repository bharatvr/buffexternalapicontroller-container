/**
 * 
 */
package com.cisco.buff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bharatsi
 *
 */
@RestController
public class HealthCheckController {
	private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

	
	@GetMapping(value = "/healthCheck", params = {})
	public Object healthCheck() {
		logger.info("Health check service called");
		return "Service is up and running";
	}
}
