
package com.cisco.buff.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Added this class to test locally if we can't make external server call
 * 
 * @author bharatsi
 *
 */
@RestController
public class ExternalCallController {

	private static final Logger logger = LoggerFactory.getLogger(ExternalCallController.class);

	@PostMapping(value = "/api/v1/log/csm-lic/suggestion")
	public int kafkaAPICall(Object requestPayload) {
		logger.info("This request must go to external server to put log on kafka server");
		return HttpServletResponse.SC_OK;
	}
	
	
	@GetMapping(value = "/predict")
	public Object searchEngineCall(@RequestParam(name = "profile") String profile, @RequestParam(name = "q") String queryString) {
		logger.info("Profile and queryString: {} : {}",profile , queryString );
		
		return gerSearchResultFile(profile).toString();
	}
	
	protected static Object gerSearchResultFile(String searchFile) {

		Gson gson = new Gson();

		try {

			return  gson.fromJson(new FileReader("/data/" + searchFile+".json"), JsonArray.class);

		} catch (Exception e) {
			try {
				File file = new ClassPathResource("/data/" + searchFile+".json").getFile();
			return gson.fromJson(new FileReader(file), JsonArray.class);
			} catch (IOException ioExp) {
				logger.error("Exception while reading file : {} and  Error >>: {} ", searchFile, ioExp.toString());
			}

		}
		return "[]";
	

	}

}
