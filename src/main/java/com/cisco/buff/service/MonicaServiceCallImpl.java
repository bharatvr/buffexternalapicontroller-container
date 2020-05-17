package com.cisco.buff.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.config.AppConfigProperties;
import com.cisco.buff.executorservice.ServiceExecutor;
import com.cisco.buff.model.LicesningProfile;
import com.cisco.buff.model.MonicaResponse;
import com.cisco.buff.model.MonicaSuggestion;
import com.cisco.buff.model.RequesPayload;
import com.cisco.buff.response.ServiceResponse;
import com.cisco.buff.response.ServiceResponseWrapper;
import com.cisco.buff.utils.BuffServiceException;
import com.cisco.buff.utils.LicensingProfileCache;

@Service
public class MonicaServiceCallImpl implements MonicaServiceCall {

	private static final Logger logger = LoggerFactory.getLogger(MonicaServiceCallImpl.class);

	private static final int INDEX_ZERO = 0;

	private static final int INDEX_25 = 25;

	private static final int MAX_LENGTH = 500;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppConfigProperties appConfig;

	@Autowired
	private ServiceExecutor serviceExecutor;

	/*
	 * 
	 * @see
	 * com.cisco.buff.service.MonicaServiceCall#getMonicaQAResponse(com.cisco.
	 * buff.model.RequesPayload)
	 */

	@LogExecutionTime
	@Override
	public ServiceResponseWrapper<Object> getMonicaQAResponse(RequesPayload requesPayload, String refrenceId)
			throws BuffServiceException {

		Map<String, String> licProfile = SerializationUtils
				.clone((HashMap<String, String>) LicensingProfileCache.getLicensingProfile());

		MonicaResponse monicaResponse = new MonicaResponse();
		ServiceResponseWrapper<Object> wrapper = new ServiceResponseWrapper<>();

		try {

			int maxSuggestion = requesPayload.getNumSuggestion();
			String inputText = requesPayload.getInputText();
			monicaResponse.setMessage(requesPayload.getInputText());

			if (StringUtils.isBlank(requesPayload.getInputText())) {

				return getFAQResponse(monicaResponse, requesPayload.getProfile(), maxSuggestion, refrenceId);

			}
			String[] inputWords = requesPayload.getInputText().split(" ");

			if (inputWords.length > INDEX_25) {

				String[] inputWordsMax = Arrays.copyOfRange(inputWords, INDEX_ZERO, INDEX_25);

				inputText = String.join(" ", inputWordsMax);

			}

			if (inputText.length() > MAX_LENGTH) {
				inputText = inputText.substring(INDEX_ZERO, MAX_LENGTH);
			}
			List<LicesningProfile> profileList = new ArrayList<>();
			profileList.add(new LicesningProfile(licProfile.get(requesPayload.getProfile()), requesPayload.getProfile(),
					requesPayload.getKbThresholdProfile(), true));

			if (requesPayload.isEnableCrossProfile()) {

				/**
				 * To fetch answer from cross profile
				 */
				Set<String> setKey = licProfile.keySet();
				setKey.remove(requesPayload.getProfile());
				for (String crossProfile : setKey) {
					profileList.add(new LicesningProfile(licProfile.get(crossProfile), crossProfile,
							requesPayload.getKbThresholdCrossProfile(), false));

				}

			}

			List<MonicaSuggestion> suggestionList = serviceExecutor.exeuteQuery(profileList, inputText, maxSuggestion);
			monicaResponse.setSuggestionList(suggestionList);
			wrapper.setResponseBody(monicaResponse);
			wrapper.setStatus(ServiceResponse.SUCCESS_STATUS);
			wrapper.setClientTrxId(refrenceId);

		}

		catch (ExecutionException exp) {
			logger.error("ExecutionException while getting response from monica call {} :", exp.getClass(), exp);
			throw new BuffServiceException(BuffServiceException.RESOURCE_ACCESS_EXCEPTION);

		} catch (ResourceAccessException exp) {
			logger.error("ResourceAccessException while getting response from monica call : {}", exp.toString(), exp);
			throw new BuffServiceException(BuffServiceException.RESOURCE_ACCESS_EXCEPTION);
		}

		catch (URISyntaxException exp) {
			logger.error("URISyntaxException while getting response from monica call : {}", exp.toString(), exp);
			throw new BuffServiceException(BuffServiceException.URI_SYNTAX_EXCEPTION);
		} catch (MalformedURLException expMalFormed) {
			logger.error("MalformedURLException while getting response from monica call : {}", expMalFormed.toString());
			throw new BuffServiceException(BuffServiceException.MALFORMED_URI_EXCEPTION);
		}

		catch (JSONException jsonParse) {
			logger.error("JSONException while getting response from monica call : {}", jsonParse.toString(), jsonParse);
			throw new BuffServiceException(BuffServiceException.JSON_EXCEPTION);

		}

		catch (Exception exp) {
			logger.error("Exception while getting response from monica call {} :", exp.getClass(), exp);
			throw new BuffServiceException(BuffServiceException.EXCEPTION);

		}

		return wrapper;
	}

	/**
	 * @param monicaResponse
	 * @param profile
	 * @param numSuggestion
	 * @return
	 */
	private ServiceResponseWrapper<Object> getFAQResponse(MonicaResponse monicaResponse, String profile,
			int maxSuggestion, String refrenceId) throws URISyntaxException, MalformedURLException, JSONException {

		List<MonicaSuggestion> suggestionList = new ArrayList<>();
		ServiceResponseWrapper<Object> wrapper = new ServiceResponseWrapper<>();

		String queryString = String.format("%s/config/api/v1/custom-chat/faqs?profile=%s&size=%s",
				appConfig.getSkillBuilderServiceURL(), profile, maxSuggestion);

		URL url = new URL(queryString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		logger.debug("QueryString  for FAQ URL : {} ", uri);

		HttpEntity<String> entity = new HttpEntity<>("", new HttpHeaders());
		ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		if (resp.getStatusCode() == HttpStatus.OK) {

			JSONObject jsonObject = new JSONObject(resp.getBody());

			JSONArray jsonArray = jsonObject.getJSONArray("faqs");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject faq = jsonArray.getJSONObject(i);

				MonicaSuggestion suggestioin = new MonicaSuggestion();

				suggestioin.setConfidenceScore(0.0);
				suggestioin.setText(faq.getString("question"));
				suggestioin.setProfile(profile);
				suggestioin.setVcaID(faq.getString("vcaId"));
				suggestionList.add(suggestioin);

			}
		}
		monicaResponse.setSuggestionList(suggestionList);

		wrapper.setResponseBody(monicaResponse);
		wrapper.setStatus(ServiceResponse.SUCCESS_STATUS);
		wrapper.setClientTrxId(refrenceId);
		return wrapper;
	}

}
