/**
 * 
 */
package com.cisco.buff.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author bharatsi
 *
 */
@Component
public class LoggerService {
	private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	@Value("${spring.logger.url}")
	String loggerUrl;

	RestTemplate restTemplate = new RestTemplate();

	@Async
	@LogExecutionTime
	public void logResponse(String refrenceId, ServiceResponseWrapper<Object> response, RequesPayload requesPayload,
			Date reqDate, Date resDate, long elapsedTimeMs) {
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		JSONObject request = new JSONObject();

		try {
			Gson gson = new GsonBuilder().serializeNulls().create();

			request.put("clientTrxId", refrenceId);
			request.put("request", new JSONObject(gson.toJson(requesPayload)));
			request.put("response", new JSONObject(gson.toJson(response)));
			request.put("actionTimeGMT", format.format(new Date()));
			request.put("elapsedTimeMs", elapsedTimeMs);
			request.put("requestTime", format.format(reqDate));
			request.put("responseTime", format.format(resDate));

			logger.debug("ROW Response : {}", request.toString());
			HttpEntity<?> req = new HttpEntity<>(request.toString(), headers);
			restTemplate.exchange(loggerUrl + "/api/v1/log/csm-lic/suggestion", HttpMethod.POST, req, String.class);

		} catch (Exception e) {
			logger.error("Error in logging response {}", e.getMessage(), e);
		}

	}

}