package com.marnils.restmashup.service;

import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marnils.restmashup.model.MusicBrainzData.MusicBrainzArtist;

public class ArtistService {

	Client client = ClientBuilder.newClient();
	ObjectMapper objectMapper = new ObjectMapper();

	public MusicBrainzArtist getArtist(String mbid) {
		String json;
		MusicBrainzArtist mbArtist = new MusicBrainzArtist();

		WebTarget musicBrainzWebtarget = client
				.target("https://musicbrainz.org/ws/2/artist/" + mbid + "da?&fmt=json&inc=url-rels+release-groups");

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
