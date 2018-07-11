package com.marnils.restmashup.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.marnils.restmashup.InfoProvider;

/**
 * Test Class
 * 
 * @author Martin
 *
 */

public class InfoProviderTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(InfoProvider.class);
    }

    /**
     * Sends correct mbid for artist Nirvana, checks result.
     */
    @Test
    public void testGetNirvanaAlbumInfo() {

        final String responseMsg = target().path("5b11f4ce-a62d-471e-81fc-a69a8278c7").request().get(String.class);

        assert (responseMsg.contains("Nirvana"));
        assert (responseMsg.contains("Wipeout"));
        assert (responseMsg.contains("Kurt Cobain"));

    }

}
