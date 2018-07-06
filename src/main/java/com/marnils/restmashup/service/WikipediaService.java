package com.marnils.restmashup.service;

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

        WebTarget wikipediaWebtarget = client.target(
                "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=" + bandName);

        json = wikipediaWebtarget.request(MediaType.APPLICATION_JSON).header("User-Agent",
                "MusicRestMashup/<0.0.1> ( marnils@gmail.com )").get().readEntity(String.class);

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (IOException e) {
            // TODO log
        }

        String extract = jsonNode.findValuesAsText("extract").get(0);
        if (extract != null) {
            return extract;
        }
        // TODO consider letting client know explicitly no info found
        return null;
    }
}
