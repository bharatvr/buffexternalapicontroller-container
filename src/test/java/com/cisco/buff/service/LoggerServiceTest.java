/**
 * 
 */
package com.cisco.buff.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.model.MonicaResponse;
import com.cisco.buff.model.MonicaSuggestion;
import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponse;
import com.cisco.buff.response.ServiceResponseWrapper;

/**
 * @author bharatsi
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ LoggerService.class })
public class LoggerServiceTest {

	LoggerService loggerService;

	@Mock
	RestTemplate restTemplate;

	@Before
	public void setup() {

		loggerService = new LoggerService();
		Whitebox.setInternalState(loggerService, RestTemplate.class, restTemplate);

	}

	@Test
	public void textLogResponse() {
		Date startDate = new Date();

		ResponseEntity<String> responseEntity = new ResponseEntity<>("Response message", HttpStatus.OK);
		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.eq(HttpMethod.POST),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

		String refrenceId = "eferferg";
		ServiceResponseWrapper<Object> wrapper = new ServiceResponseWrapper<>();
		MonicaResponse monicaResponse = new MonicaResponse();
		List<MonicaSuggestion> suggestionList = new ArrayList<>();
		monicaResponse.setSuggestionList(suggestionList);

		wrapper.setResponseBody(monicaResponse);
		wrapper.setStatus(ServiceResponse.SUCCESS_STATUS);
		wrapper.setClientTrxId(refrenceId);

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setProfile("profile");
		requesPayload.setInputText("inputText");
		
		Date resDate = new Date();
		long diffInMillies = Math.abs(resDate.getTime() - startDate.getTime());

		loggerService.logResponse(refrenceId, wrapper,requesPayload, startDate, resDate, diffInMillies);

	}

	@Test
	public void textLogResponseException() {

		Date startDate = new Date();
		String refrenceId = "eferferg";
		
		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setProfile("profile");
		requesPayload.setInputText("inputText");
		
		Date resDate = new Date();
		long diffInMillies = Math.abs(resDate.getTime() - startDate.getTime());


		loggerService.logResponse(refrenceId, null,requesPayload, startDate, resDate, diffInMillies);

	}

}
