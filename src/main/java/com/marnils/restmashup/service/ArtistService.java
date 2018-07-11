package com.marnils.restmashup.service;

import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marnils.restmashup.model.MusicBrainzData.MusicBrainzArtist;

/**
 * Service fetches artist data from MusicBrainz
 * 
 * @author Martin
 *
 */

//add asynch

public class ArtistService {

    Client client = ClientBuilder.newClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public MusicBrainzArtist getArtist(String mbid) {
        String json;
        MusicBrainzArtist mbArtist = null;

        WebTarget musicBrainzWebtarget =
                client.target("https://musicbrainz.org/ws/2/artist/" + mbid + "da?&fmt=json&inc=url-rels+release-groups");

        json = musicBrainzWebtarget.request(MediaType.APPLICATION_JSON).header("User-Agent",
                "MusicRestMashup/<0.0.1> ( marnils@gmail.com )").get().readEntity(String.class);
        try {
        	  mbArtist  = objectMapper.readValue(json, MusicBrainzArtist.class);
        } catch (IOException e) {
            // TODO log
			return null;
        }

        return mbArtist;
    }
}
