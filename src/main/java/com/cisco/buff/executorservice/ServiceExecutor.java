package com.cisco.buff.executorservice;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.cisco.buff.config.AppConfigProperties;
import com.cisco.buff.model.LicesningProfile;
import com.cisco.buff.model.MonicaSuggestion;
import com.cisco.buff.utils.ConstantK;

/**
 * @author bharatsi
 *
 */
@Controller
public class ServiceExecutor {

	private static final Logger logger = LoggerFactory.getLogger(ServiceExecutor.class);

	private final static ExecutorService executorService = Executors.newCachedThreadPool();

	private static final int INDEX_ZERO = 0;
	private static final Double KB_THRESHOLD_ANSWER = 0.7;
	private static final String VCA_ID="VCA_ID";

	@Autowired
	AppConfigProperties appConfig;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param profileList
	 * @param query
	 * @return
	 */
	@SuppressWarnings("squid:S3776")
	public List<MonicaSuggestion> exeuteQuery(List<LicesningProfile> profileList, String query, int maxSuggestion)
			throws ResourceAccessException, URISyntaxException, MalformedURLException, Exception {

		List<MonicaSuggestion> suggestionResult = new ArrayList<>();
		List<MonicaSuggestion> suggestionResultFinal = new ArrayList<>();
		Set<String> vcaIdSet = new TreeSet<>();
		MonicaSuggestion botAnswer = null;
		boolean botResponse = false;

		HashMap<LicesningProfile, Future<String>> futureDataMap = new HashMap<>();

		for (LicesningProfile profile : profileList) {
			Future<String> futurObject = executorService
					.submit(new RestCallExecutor(profile.getKey(), query, appConfig.getServiceURL(), restTemplate));

			futureDataMap.put(profile, futurObject);
		}
        /**
         * Extracting data for each profile
         */
		for (LicesningProfile profile : profileList) {

			Future<String> result = futureDataMap.get(profile);

			if (result != null && !result.get().equals(ConstantK.NO_DATA)) {
				List<MonicaSuggestion> suggestionList = getSuggestionList(result.get(), profile, vcaIdSet);

				if (profile.isPrimary() && !suggestionList.isEmpty()) {
					botAnswer = suggestionList.get(INDEX_ZERO);
					if (botAnswer.getConfidenceScore() > KB_THRESHOLD_ANSWER) {
						suggestionList.remove(INDEX_ZERO);
						botResponse = true;
					}
					suggestionResult.addAll(suggestionList);

				}

				else {
					suggestionResult.addAll(suggestionList);
				}

			}

		}

		Collections.sort(suggestionResult, Collections.reverseOrder());
		
		if (botResponse) {

			suggestionResultFinal.add(botAnswer);
		}
		suggestionResultFinal.addAll(suggestionResult);
		
		if (suggestionResultFinal.size() > maxSuggestion) {
		
			suggestionResultFinal = suggestionResultFinal.subList(INDEX_ZERO, maxSuggestion);

		}

		return suggestionResultFinal;

	}

	/**
	 * @param response
	 * @param profile
	 * @param vcaIdSet
	 * 
	 * @return
	 */
	private List<MonicaSuggestion> getSuggestionList(String response, LicesningProfile profile, Set<String> vcaIdSet) {
		
		List<MonicaSuggestion> suggestionList = new ArrayList<>();
		JSONArray jsonArr = null;
		try {
						jsonArr = new JSONArray(response);
			JSONObject jsonObject = (JSONObject) jsonArr.get(0);

			JSONArray jsonResList = jsonObject.getJSONArray("predictions");

			for (int i = 0; i < jsonResList.length(); i++) {
				JSONObject jsonRes = (JSONObject) jsonResList.get(i);

				if (jsonRes.getDouble("prob") >= profile.getKbConfidence()) {
					JSONObject answerObject = new JSONObject(jsonRes.getString("answer"));

					/**
					 * To remove duplicate CVA Id from response suggestion list
					 */
					if (!vcaIdSet.contains(answerObject.getString(VCA_ID))) {
						vcaIdSet.add(answerObject.getString(VCA_ID));
						MonicaSuggestion suggestioin = new MonicaSuggestion();
						suggestioin.setConfidenceScore(jsonRes.getDouble("prob"));
						suggestioin.setText(jsonRes.getString("sent"));
						suggestioin.setProfile(profile.getValue());
						suggestioin.setVcaID(answerObject.getString(VCA_ID));
						suggestionList.add(suggestioin);
					}

				}
			}

		}

		catch (JSONException jsonParse) {
			logger.error("Exception while getting response from body : ", jsonParse);
			return suggestionList;

		}
		return suggestionList;
	}
}