package com.test.resttest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.restmashup.rest.InfoConsumer;

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

	InfoConsumer infoconsumer = new InfoConsumer();

	@GET
	@Path("{MBID}")
	public Response getUserById(@PathParam("MBID") String MBID) {

//		return Response.status(200).entity("getUserById is called, id : " + id).build();
		return infoconsumer.GetData(MBID);
//		return Response.status(200).entity(infoconsumer.GetData(MBID)).build();

	}

//	@GET
//	public Response people() {
//		return infoconsumer.GetData();
//	}

	
//	 @GET
//	 public JsonArray people() {
//	 return Json.createArrayBuilder().add(person("peter",
//	 13)).add(person("pan", 67)).build();
//	 }
//	
//	 public JsonObject person(String name, int age) {
//	 return Json.createObjectBuilder().add(name, age).build();
//	
//	 }
}