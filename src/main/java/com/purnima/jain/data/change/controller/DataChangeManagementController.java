package com.purnima.jain.data.change.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/data-change-management")
public class DataChangeManagementController {
	
	@PostMapping("/generate-sql-statements")
	public void generateSqlStatements(@RequestBody String jsonString) {		
		log.info("jsonString :: {}", jsonString);		
	}

	@GetMapping("/generate-input-json-string")
	public String getJsonString(@RequestParam(name = "databaseProduct") String databaseProduct) {

		JSONObject jsonObject = null;

		// Initialize JSONObject
		if ("ORACLE".equals(databaseProduct)) {
			jsonObject = generateOracleJsonString();
		}

		// Serialize JSONObject to String
		String jsonString = serializeJsonObject(jsonObject);
		log.info("jsonString :: {}", jsonString);

		// Parse jsonString too get back to JSONObject
		parseAndPrintJsonString(jsonString);

		return jsonString;
	}

	private JSONObject generateOracleJsonString() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("tableName", "CUSTOMER");
		jsonObject.put("databaseProduct", "ORACLE");

		// ----
		JSONArray changedRowsJsonArray = new JSONArray();

		JSONObject changedRow1 = new JSONObject();
		changedRow1.put("operation", "INSERT");

		JSONObject preJsonObject = new JSONObject();
		preJsonObject.put("CUSTOMER_ID", "1001");
		preJsonObject.put("FIRST_NAME", "Jane");
		changedRow1.put("pre", preJsonObject);

		JSONObject postJsonObject = new JSONObject();
		postJsonObject.put("CUSTOMER_ID", "1001");
		postJsonObject.put("FIRST_NAME", "Jane_1");
		changedRow1.put("post", postJsonObject);

		changedRowsJsonArray.put(changedRow1);

		// ----

		JSONObject changedRow2 = new JSONObject();
		changedRow2.put("operation", "UPDATE");

		JSONObject preJsonObject2 = new JSONObject();
		preJsonObject2.put("CUSTOMER_ID", "1005");
		preJsonObject2.put("FIRST_NAME", "John");
		changedRow2.put("pre", preJsonObject2);

		JSONObject postJsonObject2 = new JSONObject();
		postJsonObject2.put("CUSTOMER_ID", "1001");
		postJsonObject2.put("FIRST_NAME", "Jane_1");
		changedRow2.put("post", postJsonObject2);

		changedRowsJsonArray.put(changedRow2);

		// ---

		jsonObject.put("changedRows", changedRowsJsonArray);

		return jsonObject;
	}

	private String serializeJsonObject(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	private void parseAndPrintJsonString(String jsonString) {

		JSONObject jsonObject = new JSONObject(jsonString);

		// Accessing Data
		String tableName = jsonObject.getString("tableName");
		log.info("tableName :: {}", tableName);

		String databaseProduct = jsonObject.getString("databaseProduct");
		log.info("databaseProduct :: {}", databaseProduct);

		JSONArray changedRowsJsonArray = jsonObject.getJSONArray("changedRows");
		JSONObject changedRowJsonObject = (JSONObject) changedRowsJsonArray.getJSONObject(0);
		String operation = changedRowJsonObject.getString("operation");
		log.info("operation :: {}", operation);

		JSONObject preJsonObject = changedRowJsonObject.getJSONObject("pre");
		Map<String, String> map = new HashMap<>();

		for (String key : preJsonObject.keySet()) {
			map.put(key, preJsonObject.getString(key));
		}

		map.forEach((key, value) -> log.info("Key: " + key + ", Value: " + value));
	}

}
