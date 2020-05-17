package com.cisco.buff.executorservice;

import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.mockito.junit.MockitoJUnitRunner;

import com.cisco.buff.MockData;
import com.cisco.buff.config.AppConfigProperties;
import com.cisco.buff.model.LicesningProfile;
import com.cisco.buff.model.MonicaSuggestion;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ServiceExecutor.class)
public class ServiceExecutorTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceExecutorTest.class);

	@Mock
	AppConfigProperties appConfig;

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	private ServiceExecutor serviceExecutor = new ServiceExecutor();

	@Test
	public void testExeuteQuery1() throws JsonSyntaxException, JsonIOException, FileNotFoundException,
			URISyntaxException, MalformedURLException {

		when(appConfig.getServiceURL()).thenReturn("http://hostmonica.com");
		String query = "What is a Smart Account?";

		String res = MockData.getMonicaResData();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);

		String queryString = String.format("%s/predict/?profile=%s&q=%s", "http://hostmonica.com", "lic01", query);

		URL url = new URL(queryString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());


		when(restTemplate.exchange(ArgumentMatchers.eq(uri), ArgumentMatchers.eq(HttpMethod.GET),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

		String queryStringSmart = String.format("%s/predict/?profile=%s&q=%s", "http://hostmonica.com", "lic02", query);

		URL urlSmart = new URL(queryStringSmart);
		URI uriSmart = new URI(urlSmart.getProtocol(), urlSmart.getUserInfo(), urlSmart.getHost(), urlSmart.getPort(), urlSmart.getPath(),
				urlSmart.getQuery(), urlSmart.getRef());
		
		String res2 = MockData.getMonicaResDataSmart();
		ResponseEntity<String> responseEntity2 = new ResponseEntity<>(res2, HttpStatus.OK);
		
		when(restTemplate.exchange(ArgumentMatchers.eq(uriSmart), ArgumentMatchers.eq(HttpMethod.GET),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity2);
		
		
		List<LicesningProfile> profileList = new ArrayList<>();
		profileList.add(new LicesningProfile("lic01", "Traditional Licensing", 0.7, true));
		profileList.add(new LicesningProfile("lic02", "Smart Licensing", .5, false));
		try {
			List<MonicaSuggestion> result = serviceExecutor.exeuteQuery(profileList, query, 2);
			
			for (MonicaSuggestion suggestion:result ){
				System.out.println(suggestion.getVcaID());
			}
		}

		catch (ResourceAccessException ex) {
			logger.info("ResourceAccessException in junit Testing : {}", ex.toString());

		} catch (Exception ex) {
			logger.info("Exception in junit Testing : {}", ex.toString(), ex);

		}

	}

}
