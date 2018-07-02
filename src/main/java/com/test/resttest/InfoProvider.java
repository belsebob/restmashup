package com.test.resttest;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.restmashup.model.Album;
import com.restmashup.model.Artist;
import com.restmashup.model.MusicBrainzData;
import com.restmashup.model.MusicBrainzData.ReleaseGroup;
import com.restmashup.rest.ArtistService;
import com.restmashup.rest.CoverService;
import com.restmashup.rest.WikipediaService;

/**
 * Root resource (exposed at "/" path)
 * Handles data fetching services, mashes up result into artist object. Returns Artist object as JSON to client
 */
@Path("/")
public class InfoProvider {

	ArtistService artistService = new ArtistService();
	WikipediaService wikipediaService = new WikipediaService();
	CoverService coverService = new CoverService();

	/**
	 * Method handling HTTP GET requests with MBID as parameter. Artist object is sent to
	 * the client as "application/json" media type.
	 * 
	 * @param mbid
	 * @return Artist
	 */
	@GET
	@Path("{mbid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Artist getArtistAndAlbumsByMbid(@PathParam("mbid") String mbid) {


		Album album = new Album();
		ArrayList<Album> albumlist = new ArrayList<>();

		/**
		 * Fetches Music Brainz Artist from artistService
		 */
		MusicBrainzData.MusicBrainzArtist mbArtist = new MusicBrainzData.MusicBrainzArtist();
		mbArtist = artistService.getArtist(mbid);
		if (mbArtist == null) {
			// Throw error
		}

		Artist artist = new Artist();
		artist.setMbid(mbid);

	
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

		// merge album art and mbReleaseGroups
		for (ReleaseGroup rg : mbArtist.getReleaseGroups()) {
			String id = rg.getId();

			album.setId(id);
			album.setTitle(rg.getTitle());
			if (coverService.getCoverArt(id) != null) {
				album.setCover(coverService.getCoverArt(id));
			}

			albumlist.add(album);
		}
		artist.setAlbums(albumlist);

		return artist;
	}
}