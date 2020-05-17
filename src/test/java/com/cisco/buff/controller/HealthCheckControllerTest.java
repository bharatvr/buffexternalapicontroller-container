/**
 * 
 */
package com.cisco.buff.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author bharatsi JUnit for rest controller for health check
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHealthCheck() throws Exception {

		mockMvc.perform(get("/healthCheck").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/healthCheck").accept(MediaType.APPLICATION_JSON)).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertTrue(content.contains("Service is up and running"));

	}

}
