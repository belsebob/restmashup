package com.test.resttest;
import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import com.restmashup.model.Artist;
import com.restmashup.model.MusicBrainzData;
import com.restmashup.rest.ArtistService;
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

	@GET
	@Path("{mbid}")
	public Response getArtistAndAlbumsByMbid(@PathParam("mbid") String mbid) {

		Response artistAlbums = null;
		
		
		
		MusicBrainzData.MusicBrainzArtist mbArtist = new MusicBrainzData.MusicBrainzArtist();
		mbArtist = artistService.getArtist(mbid);
		if (mbArtist == null){
			//Throw error
		}

		Artist artist = new Artist();
		artist.setMbid(mbid);
		
     // Java8ify or refactor other way
		String bandName = null;
		for (MusicBrainzData.Relation relation : mbArtist.getRelations()) {
			if ("wikipedia".equals(relation.getType())) {
				URI uri = relation.getUrl().getResource();
				String path = uri.getPath();
				bandName = path.substring(path.lastIndexOf('/') + 1);
				break;
			}}
		
		if (bandName != null) {
			String description = wikipediaService.getDescription(bandName);
			artist.setDescription(description);
		}
		
		
				
		// final mashed response
			return artistAlbums;
	}

	// return Response.status(200).entity(infoconsumer.GetData(MBID)).build();
	// return Response.status(200).entity("getUserById is called, id : " +
	// id).build();


}