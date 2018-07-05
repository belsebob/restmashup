package com.marnils.restmashup.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.uri.UriTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marnils.restmashup.model.MusicBrainzData.MusicBrainzArtist;

public class ArtistService {
	private final String BASE_URI = "https://musicbrainz.org/";
	private static final String API_PATH = "ws/2/artist/{mbid}?fmt=json&inc=url-rels+release-groups";

	Client client = ClientBuilder.newClient();
	ObjectMapper objectMapper = new ObjectMapper();

	public MusicBrainzArtist getArtist(String mbid) {
		String json;

		String template = BASE_URI.concat(API_PATH);
		UriTemplate uriTemplate = new UriTemplate(template);

		// UriBuilder builder =
		// UriBuilder.fromPath("https://musicbrainz.org").path(API_PATH).queryParam("mbid",
		// mbid);
		UriBuilder builder = UriBuilder.fromUri(template);
//		Map<String, String> parameters = new HashMap<>();
//		parameters.put("mbid", mbid);
		URI uri = builder.build("mbid", mbid);

		MusicBrainzArtist mbArtist = new MusicBrainzArtist();

		WebTarget musicBrainzWebtarget = client.target(uri);

		// TODO check if can be improved by skipping string conversion
		json = musicBrainzWebtarget.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
		try {
			mbArtist = objectMapper.readValue(json, MusicBrainzArtist.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mbArtist;
	}
}
