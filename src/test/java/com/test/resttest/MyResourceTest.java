package com.test.resttest;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.test.resttest.InfoProvider;

public class MyResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(InfoProvider.class);
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetArtistAlbumInfo() {
        final String responseMsg = target().path("/5b11f4ce-a62d-471e-81fc-a69a8278c7").request().get(String.class);
       
        assert(responseMsg.contains("Nirvana"));        
        assert(responseMsg.contains("In Bloom"));
        assertEquals(true, responseMsg.contains("Nirvana"));    

    }
}
