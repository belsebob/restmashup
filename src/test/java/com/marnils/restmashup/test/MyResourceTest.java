package com.marnils.restmashup.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.marnils.restmashup.InfoProvider;

public class MyResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(InfoProvider.class);
    }

    /**
     * Sends correct mbid for Nirvana.
     */
    @Test
    public void testGetNirvanaAlbumInfo() {
        
        Response output = target("5b11f4ce-a62d-471e-81fc-a69a8278c7").request().get();
//        assertEquals(200, output.getStatus()); 
        String s = output.getEntity().toString();
        
        final String responseMsg = target().path("5b11f4ce-a62d-471e-81fc-a69a8278c7").request().get(String.class);
       
        assert(responseMsg.contains("Nirvana"));        
        assert(responseMsg.contains("In Bloom"));
        assertEquals(true, responseMsg.contains("Nirvana"));

    }
    
    /**
     * Sends incorrect mbid
     */

    @Test
    public void testInvalidMbid() {
       
        Response output = target("incorrectmbidexample").request().get();
        assertEquals("Invalid mbid", 404, output.getStatus());  
    }

}
