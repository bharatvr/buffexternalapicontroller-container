/**
 * 
 */
package com.cisco.buff;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * @author bharatsi
 *
 */
public class MockData {

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 */
	public static String getMonicaResData() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();

		JsonArray jsonArray = (JsonArray) gson
				.fromJson(new FileReader("src/test/resources/mockdata/monica_res_result.json"), JsonArray.class);

		return jsonArray.toString();
	}

	/**
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JsonIOException 
	 * @throws JsonSyntaxException 
	 */
	public static String getFAQResData() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();

		JsonObject jsonObject = (JsonObject) gson
				.fromJson(new FileReader("src/test/resources/mockdata/faq_res_result.json"), JsonObject.class);

		return jsonObject.toString();
	}

	/**
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JsonIOException 
	 * @throws JsonSyntaxException 
	 */
	public static String getMonicaResDataSmart() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();

		JsonArray jsonArray = (JsonArray) gson
				.fromJson(new FileReader("src/test/resources/mockdata/monica_res_resultSmart.json"), JsonArray.class);

		return jsonArray.toString();
	}
	

}
