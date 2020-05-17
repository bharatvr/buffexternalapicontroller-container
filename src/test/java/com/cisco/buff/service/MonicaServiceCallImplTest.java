/**
 * 
 */
package com.cisco.buff.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.MockData;
import com.cisco.buff.config.AppConfigProperties;
import com.cisco.buff.executorservice.ServiceExecutor;
import com.cisco.buff.model.MonicaSuggestion;
import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.cisco.buff.utils.BuffServiceException;
import com.cisco.buff.utils.LicensingProfileCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * @author bharatsi
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MonicaServiceCall.class })
public class MonicaServiceCallImplTest {

	@Mock
	AppConfigProperties appConfig;

	@Mock
	ServiceExecutor serviceExecutor;

	@Mock
	RestTemplate restTemplate;

	@Before
	public void setup() throws Exception {

		when(appConfig.getServiceURL()).thenReturn("http://hostmonica.com");
		when(appConfig.getSkillBuilderServiceURL()).thenReturn("http://hostskillbuilder.com");

		LicensingProfileCache.loadLicensingProfile();

	}

	@InjectMocks
	private MonicaServiceCall monicaServiceCall = new MonicaServiceCallImpl();

	@Test
	public void testGetMonicaQAResponse() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenReturn(resPonseList);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			ServiceResponseWrapper<Object> resResult = monicaServiceCall.getMonicaQAResponse(requesPayload,
					uuid.toString());

			Gson gson = new Gson();

			JsonObject jsonObject = gson.fromJson(gson.toJson(resResult), JsonObject.class)
					.getAsJsonObject("responseBody");

			assertEquals("Testing Question", jsonObject.get("message").getAsString());

			assertEquals(1, jsonObject.getAsJsonArray(("suggestionList")).size());
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@Test
	public void testGetMonicaQAResponseCrossProfile() {
		try {

			System.out.println("Hello");
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			MonicaSuggestion smartSugg = new MonicaSuggestion();
			smartSugg.setProfile("Smart Licensing");
			smartSugg.setText("Testing Question otehr");
			smartSugg.setVcaID("VCA124");
			resPonseList.add(smartSugg);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenReturn(resPonseList);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setEnableCrossProfile(true);
			requesPayload.setKbThresholdProfile(0.7);
			requesPayload.setKbThresholdCrossProfile(0.9);
			requesPayload.setInputText("Testing Question");

			ServiceResponseWrapper<Object> resResult = monicaServiceCall.getMonicaQAResponse(requesPayload,
					uuid.toString());

			Gson gson = new Gson();

			JsonObject jsonObject = gson.fromJson(gson.toJson(resResult), JsonObject.class)
					.getAsJsonObject("responseBody");

			assertEquals("Testing Question", jsonObject.get("message").getAsString());

			assertEquals(2, jsonObject.getAsJsonArray(("suggestionList")).size());
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@Test
	public void testFAQResponse() throws BuffServiceException, JsonSyntaxException, JsonIOException,
			FileNotFoundException, JsonProcessingException {

		String res = MockData.getFAQResData();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);
		when(restTemplate.exchange(ArgumentMatchers.any(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

		UUID uuid = UUID.randomUUID();
		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("licBotId");
		requesPayload.setClientTrxId(uuid.toString());
		requesPayload.setNumSuggestion(3);
		requesPayload.setProfile("Traditional Licensing");
		requesPayload.setInputText("");

		ServiceResponseWrapper<Object> resResult = monicaServiceCall.getMonicaQAResponse(requesPayload,
				uuid.toString());

		Gson gson = new Gson();

		JsonObject jsonObject = gson.fromJson(gson.toJson(resResult), JsonObject.class).getAsJsonObject("responseBody");

		assertEquals("", jsonObject.get("message").getAsString());

		assertEquals(3, jsonObject.getAsJsonArray(("suggestionList")).size());

	}

	@Test
	public void testGetMonicajsonException()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException, JsonProcessingException {

		String inputText = "Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question "
				+ "ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg "
				+ "esting Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg "
				+ "vfegerg Testing Question ferge fgret rfgrtg vfegerg Testing Question ferge fgret rfgrtg vfegergTesting Question ferge fgret rfgrtg vfegerg "
				+ "Testing Question ferge fgret rfgrtg vfegergTesting Question ferge fgret rfgrtg vfegerg";
		UUID uuid = UUID.randomUUID();

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("licBotId");
		requesPayload.setClientTrxId(uuid.toString());
		requesPayload.setNumSuggestion(3);
		requesPayload.setInputText(inputText);
		requesPayload.setProfile("Traditional Licensing");

		try {
			ServiceResponseWrapper<Object> response = monicaServiceCall.getMonicaQAResponse(requesPayload,
					uuid.toString());

			Gson gson = new Gson();

			JsonObject jsonObject = gson.fromJson(gson.toJson(response), JsonObject.class)
					.getAsJsonObject("responseBody");

			assertEquals(0, jsonObject.getAsJsonArray(("suggestionList")).size());
		} catch (BuffServiceException buffExp) {
			assertEquals(BuffServiceException.JSON_EXCEPTION, buffExp.getMessage());
		}

	}

	@Test
	public void testGetMonicaMaxWord() throws JsonSyntaxException, JsonIOException, FileNotFoundException,
			JsonProcessingException, BuffServiceException {

		String res = MockData.getMonicaResData();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);
		when(restTemplate.exchange(ArgumentMatchers.any(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>> any())).thenReturn(responseEntity);

		String inputText = "TestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtgTestingQuestionfergefgretrfgrtgvfegergTestingQuestionfergefgretfgrtgvfegergTestingQuestionfergefgretrfgrtg";

		UUID uuid = UUID.randomUUID();

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("licBotId");
		requesPayload.setClientTrxId(uuid.toString());
		requesPayload.setNumSuggestion(3);
		requesPayload.setProfile("Traditional Licensing");
		requesPayload.setInputText(inputText);

		ServiceResponseWrapper<Object> resResult = monicaServiceCall.getMonicaQAResponse(requesPayload,
				uuid.toString());

		Gson gson = new Gson();

		JsonObject jsonObject = gson.fromJson(gson.toJson(resResult), JsonObject.class).getAsJsonObject("responseBody");

		assertEquals(inputText, jsonObject.get("message").getAsString());

		assertEquals(0, jsonObject.getAsJsonArray(("suggestionList")).size());

	}

	@Test
	public void testGetMonicaQAResponseURISyntaxExceptionn() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(URISyntaxException.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			ServiceResponseWrapper<Object> resResult = monicaServiceCall.getMonicaQAResponse(requesPayload,
					uuid.toString());

			Gson gson = new Gson();

			JsonObject jsonObject = gson.fromJson(gson.toJson(resResult), JsonObject.class)
					.getAsJsonObject("responseBody");

			assertEquals("Testing Question", jsonObject.get("message").getAsString());

			assertEquals(1, jsonObject.getAsJsonArray(("suggestionList")).size());
		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: RISyntaxException", ex.toString());

		}

	}

	@Test
	public void testGetMonicaQAResponseMalformedURLException() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(MalformedURLException.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			monicaServiceCall.getMonicaQAResponse(requesPayload, uuid.toString());
		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: MalformedURLException", ex.toString());

		}

	}

	@Test
	public void testGetMonicaQAResponseJSONException() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(JSONException.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			monicaServiceCall.getMonicaQAResponse(requesPayload, uuid.toString());

		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: JSONException", ex.toString());

		}

	}

	@Test
	public void testGetMonicaQAResponseException() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(Exception.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			monicaServiceCall.getMonicaQAResponse(requesPayload, uuid.toString());
		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: Exception", ex.toString());

		}

	}
	
	@Test
	public void testGetMonicaQAResponseResourceAccessException() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(ResourceAccessException.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			monicaServiceCall.getMonicaQAResponse(requesPayload, uuid.toString());
		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: ResourceAccessException", ex.toString());

		}

	}
	
	@Test
	public void testGetMonicaQAResponseExecutionException() {
		try {
			List<MonicaSuggestion> resPonseList = new ArrayList<>();

			MonicaSuggestion suggestion = new MonicaSuggestion();
			suggestion.setProfile("Traditional Licensing");
			suggestion.setText("Testing Question");
			suggestion.setVcaID("VCA123");

			resPonseList.add(suggestion);

			when(serviceExecutor.exeuteQuery(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
					.thenThrow(ExecutionException.class);

			UUID uuid = UUID.randomUUID();
			RequesPayload requesPayload = new RequesPayload();
			requesPayload.setBotId("licBotId");
			requesPayload.setClientTrxId(uuid.toString());
			requesPayload.setNumSuggestion(3);
			requesPayload.setProfile("Traditional Licensing");
			requesPayload.setInputText("Testing Question");

			monicaServiceCall.getMonicaQAResponse(requesPayload, uuid.toString());
		} catch (Exception ex) {
			assertEquals("com.cisco.buff.utils.BuffServiceException: ResourceAccessException", ex.toString());

		}

	}

}
