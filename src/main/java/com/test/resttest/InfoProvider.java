package com.test.resttest;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.restmashup.model.Album;
import com.restmashup.model.Artist;
import com.restmashup.model.MusicBrainzData;
import com.restmashup.model.MusicBrainzData.ReleaseGroup;
import com.restmashup.rest.ArtistService;
import com.restmashup.rest.CoverService;
import com.restmashup.rest.WikipediaService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class InfoProvider {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */

	ArtistService artistService = new ArtistService();
	WikipediaService wikipediaService = new WikipediaService();
	CoverService coverService = new CoverService();

	// @Produces(MediaType.JSON)
	@GET
	@Path("{mbid}")
	public Artist getArtistAndAlbumsByMbid(@PathParam("mbid") String mbid) {

		Response artistAlbums = null;

		Album album = new Album();
		ArrayList<Album> albumlist = null;

		// get artist info
		MusicBrainzData.MusicBrainzArtist mbArtist = new MusicBrainzData.MusicBrainzArtist();
		mbArtist = artistService.getArtist(mbid);
		if (mbArtist == null) {
			// Throw error
		}

		Artist artist = new Artist();
		artist.setMbid(mbid);

		// Java8ify or refactor other way
		String wikiBandName = null;
		for (MusicBrainzData.Relation relation : mbArtist.getRelations()) {
			if ("wikipedia".equals(relation.getType())) {
				URI uri = relation.getUrl().getResource();
				String path = uri.getPath();
				wikiBandName = path.substring(path.lastIndexOf('/') + 1);
				break;
			}
		}

		if (wikiBandName != null) {
			String description = wikipediaService.getDescription(wikiBandName);
			artist.setDescription(description);
		}

		// get cover art

		// merge album art and mbReleaseGroups
		for (ReleaseGroup rg : mbArtist.getReleaseGroups()) {
			String id = rg.getId();
			
			album.setId(id);
			album.setTitle(rg.getTitle());
			album.setCover(coverService.getCoverArt(id));

			
			albumlist.add(album);
		}
		artist.setAlbums(albumlist);

		// ObjectWriter ow = new
		// ObjectMapper().writer().withDefaultPrettyPrinter();
		// try {
		// String artistJson = ow.writeValueAsString(artist);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// final mashed response
		return artist;
	}

	// return Response.status(200).entity(infoconsumer.GetData(MBID)).build();

}