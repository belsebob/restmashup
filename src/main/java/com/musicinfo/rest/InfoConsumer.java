package com.musicinfo.rest;

import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InfoConsumer {

	Client client = ClientBuilder.newClient();

	WebTarget wikipediaWebtarget = client.target("https://en.wikipedia.org/w/api.php");

	WebTarget coverArtwebtarget = client.target("http://coverartarchive.org/");

	public Response GetData(String MBID) {

		WebTarget musicBrainzWebtarget = client
				.target("https://musicbrainz.org/ws/2/artist/" + MBID + "da?&fmt=json&inc=url-rels+release-groups");
		return musicBrainzWebtarget.request(MediaType.APPLICATION_JSON).get();
		// return musicBrainzWebtarget.request().;
	}

	// public void Print() {
	// System.out.println(
	// coverArtwebtarget.request(MediaType.APPLICATION_JSON).get() + "/n" +
	// musicBrainzWebtarget.request(MediaType.APPLICATION_JSON).get()
	// + "/n" + wikipediaWebtarget.request(MediaType.APPLICATION_JSON).get());
	// }
}
