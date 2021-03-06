package com.marnils.restmashup;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marnils.restmashup.model.Album;
import com.marnils.restmashup.model.Artist;
import com.marnils.restmashup.model.MusicBrainzData;
import com.marnils.restmashup.model.MusicBrainzData.ReleaseGroup;
import com.marnils.restmashup.service.ArtistService;
import com.marnils.restmashup.service.CoverService;
import com.marnils.restmashup.service.WikipediaService;

/**
 * Root resource (exposed at "/" path) Handles data fetching services, mashes up
 * result into artist object. Returns Artist object as JSON to client
 */
@Path("/")
public class InfoProvider {

	ArtistService artistService = new ArtistService();
	WikipediaService wikipediaService = new WikipediaService();
	CoverService coverService = new CoverService();

	/**
	 * Method handling HTTP GET requests with MBID as parameter. Artist object
	 * is sent to the client as "application/json" media type.
	 * 
	 * @param mbid
	 * @return Artist
	 */
	@GET
	@Path("{mbid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArtistAndAlbumsByMbid(@PathParam("mbid") String mbid) {

		if (!validateParam(mbid)) {
			return Response.status(404, "Invalid mbid").build();
		}

		ArrayList<Album> albumlist = new ArrayList<>();

		/**
		 * Fetches Music Brainz Artist from artistService
		 */
		MusicBrainzData.MusicBrainzArtist mbArtist = artistService.getArtist(mbid);
		if (mbArtist == null) {
			return Response.serverError().build();
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

		/**
		 * / Add coverart to corresponding albums
		 */
		for (ReleaseGroup rg : mbArtist.getReleaseGroups()) {
			Album album = new Album();
			String id = rg.getId();

			album.setId(id);
			album.setTitle(rg.getTitle());
			album.setCover(coverService.getCoverArt(id));

			albumlist.add(album);
		}

		artist.setAlbums(albumlist);

		/**
		 * Build response with cache set to one day
		 */
		CacheControl cc = new CacheControl();
		cc.setMaxAge(86400);
		return Response.ok(artist, MediaType.APPLICATION_JSON).cacheControl(cc).build();
	}

	private boolean validateParam(String mbid) {
		try {
			UUID.fromString(mbid);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;

	}
}