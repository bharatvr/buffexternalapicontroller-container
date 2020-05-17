/**
 * 
 */
package com.cisco.buff.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cisco.buff.model.MonicaResponse;
import com.cisco.buff.model.MonicaSuggestion;
import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceError;
import com.cisco.buff.response.ServiceResponse;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.cisco.buff.service.LoggerService;
import com.cisco.buff.service.MonicaServiceCall;
import com.cisco.buff.utils.BuffServiceException;
import com.cisco.buff.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author bharatsi JUnit for rest controller for report data API
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SCMIntergationController.class)
public class SCMIntergationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MonicaServiceCall monicaServiceCall;

	@MockBean
	LoggerService loggerService;

	@MockBean
	CommonUtils commonUtils;

	ServiceResponseWrapper<Object> wrapperRes1 = new ServiceResponseWrapper<>();
	ServiceResponseWrapper<Object> wrapperResError = new ServiceResponseWrapper<>();

	String clienttxnId = "04f6fb3a-f1e4-4afc-a957-06f112f22060";
	String clienttxnId2 = "04f6fb3a-f1e4-4afc-a957-06f112f22062";

	@Before
	public void setUp() throws ParseException, Exception {

		Mockito.when(commonUtils.getRefrenceId(eq(clienttxnId))).thenReturn(clienttxnId);
		
		Mockito.when(commonUtils.getRefrenceId(eq(clienttxnId2))).thenReturn(clienttxnId2);


		MonicaResponse monicaResponse = new MonicaResponse();
		monicaResponse.setSuggestionList(new ArrayList<MonicaSuggestion>());
		wrapperRes1.setResponseBody(monicaResponse);
		wrapperRes1.setStatus(ServiceResponse.SUCCESS_STATUS);
		wrapperRes1.setClientTrxId(clienttxnId);

		Mockito.when(monicaServiceCall.getMonicaQAResponse(any(RequesPayload.class), eq(clienttxnId)))
				.thenReturn(wrapperRes1);

		Mockito.when(monicaServiceCall.getMonicaQAResponse(any(RequesPayload.class), eq(clienttxnId2)))
				.thenThrow(new BuffServiceException(BuffServiceException.EXCEPTION));

		wrapperResError.setStatus(ServiceResponse.FAILURE_STATUS);
		wrapperResError.setClientTrxId(clienttxnId2);
		wrapperResError.setErrors(new ServiceError("1", "type", "Technical Error"));

		Mockito.when(commonUtils.getErrorResponseMessage(anyString(), anyString())).thenReturn(wrapperResError);

	}

	@Test
	public void suggestionsKAV1Test() throws Exception {

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("12345");
		requesPayload.setClientTrxId(clienttxnId);
		requesPayload.setNumSuggestion(2);
		requesPayload.setInputText("Registering PAK for a AnyConnect");
		requesPayload.setProfile("Traditional Licensing");

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/suggestions").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requesPayload))).andExpect(status().isOk());

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/suggestions")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(requesPayload))).andReturn();

		Gson gson = new GsonBuilder().serializeNulls().create();
		assertEquals(gson.toJson(wrapperRes1), result.getResponse().getContentAsString());

	}

	/**
	 * 
	 * Validation throws Exception from getMonicaQAResponse
	 */
	@Test
	public void suggestionsKAV1ExceptionTest() throws Exception {

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("12345");
		requesPayload.setClientTrxId(clienttxnId2);
		requesPayload.setNumSuggestion(2);
		requesPayload.setInputText("Registering PAK for a AnyConnect");
		requesPayload.setProfile("Traditional Licensing");

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/suggestions").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requesPayload))).andExpect(status().isOk());

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/suggestions")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(requesPayload))).andReturn();
		

		Gson gson = new GsonBuilder().serializeNulls().create();
		assertEquals(gson.toJson(wrapperResError), result.getResponse().getContentAsString());

	}
	
	/**
	 * 
	 * Validation throws Exception from getMonicaQAResponse
	 */
	@Test
	public void tesTsuggestionsAPIException() throws Exception {

		RequesPayload requesPayload = new RequesPayload();
		requesPayload.setBotId("12345");
		requesPayload.setClientTrxId(clienttxnId2);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/suggestions")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(requesPayload))).andReturn();
		

		Gson gson = new GsonBuilder().serializeNulls().create();
		assertEquals(gson.toJson(wrapperResError), result.getResponse().getContentAsString());

	}
	

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}