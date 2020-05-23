package com.cisco.buff.executorservice;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.utils.ConstantK;

public class RestCallExecutor implements Callable<String> {

	private static final Logger logger = LoggerFactory.getLogger(RestCallExecutor.class);

	private String profileKey;
	private String query;
	private String serviceURL;
	private RestTemplate restTemplate;

	public RestCallExecutor(String profileKey, String query, String serviceURL,RestTemplate restTemplate) {
		this.profileKey = profileKey;
		this.query = query;
		this.serviceURL = serviceURL;
		this.restTemplate = restTemplate;

	}

	@Override
	public String call() throws ResourceAccessException, URISyntaxException, MalformedURLException, Exception {

		String response = ConstantK.NO_DATA;
		
		String queryString = String.format("%s/predict/?profile=%s&q=%s", serviceURL, profileKey, query);

		URL url = new URL(queryString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		logger.info("QueryString  URL: {}", uri);

		HttpEntity<String> entity = new HttpEntity<>("", new HttpHeaders());
		ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		logger.info("Resposne for profile key : {}  : {} ", profileKey, resp.getStatusCode());
		
		
		System.out.println(resp.getBody());

		if (resp.getStatusCode() == HttpStatus.OK) {
			response = resp.getBody();

		}
		return response;

	}

}
