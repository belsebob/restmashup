package com.restmashup.rest;

import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WikipediaService {

	Client client = ClientBuilder.newClient();
	ObjectMapper objectMapper = new ObjectMapper();

	public String getDescription(String bandName) {
		String json;

		WebTarget wikipediaWebtarget = client
				.target("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles="
						+ bandName);

		json = wikipediaWebtarget.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);

		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String extract = jsonNode.findValuesAsText("extract").get(0);
		if (extract != null) {
			return extract;
		}

		// try {
		// description = objectMapper.readValue(json, WikipediaData.class);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		return "";
		// description.getExtract();

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class WikipediaData {

		private String extract;

		// @SuppressWarnings("unchecked")
		// @JsonProperty("query")
		// private void unpackNested(Map<String, Object> query) {
		//// this.pages = (String) query.get("pages");
		// Map<String, Object> pages = (Map<String, Object>) query.get("pages");
		// String pageIDnr = (String) pages.get("21231");
		// Map<String, String> pageID = (Map<String, String>)
		// pages.get("21231");
		// this.extract = pageID.get("extract");
		// }

		private String getExtract() {
			return extract;
		}
	}
}

