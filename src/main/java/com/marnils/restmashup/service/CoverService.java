package com.marnils.restmashup.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service fetches links to album covers from Cover Art Archive
 * 
 * @author Martin
 *
 */

public class CoverService {

	private static final String COVERT_ART_ARCHIVE_URL = "http://coverartarchive.org/release-group/";

	Client client = ClientBuilder.newClient();
	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Returns cover art image URL corresponding to mbid fetched from
	 * MusicBrainz in ArtistService
	 * 
	 * @param id
	 * @return imageUrl
	 * @throws IOException,
	 *             MalformedURLException
	 */
	public URL getCoverArt(String id) {
		String json;
		URL imageUrl = null;

		WebTarget coverArtArchiveTarget = client.target(COVERT_ART_ARCHIVE_URL + id);

		json = coverArtArchiveTarget.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);

		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (IOException e) {
			// TODO log this
			return null;
		}

		try {
			imageUrl = new URL(jsonNode.findValuesAsText("image").get(0));
		} catch (MalformedURLException e) {
			// TODO log this
			return null;
		}
		if (imageUrl != null) {
			return imageUrl;
		}
		return null;
	}

}
