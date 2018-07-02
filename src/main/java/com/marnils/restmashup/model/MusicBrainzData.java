package com.marnils.restmashup.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds data for artist from MusicBrainz
 * @author Martin
 *
 */
public class MusicBrainzData {

	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MusicBrainzArtist {

		private String id;
		private String name;

		@JsonProperty("release-groups")
		private List<ReleaseGroup> releaseGroups = new ArrayList<ReleaseGroup>();

		private List<Relation> relations = new ArrayList<Relation>();

		public MusicBrainzArtist() {
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<ReleaseGroup> getReleaseGroups() {
			return releaseGroups;
		}

		public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
			this.releaseGroups = releaseGroups;
		}

		public List<Relation> getRelations() {
			return relations;
		}

		public void setRelations(List<Relation> relations) {
			this.relations = relations;
		}
	}

	// Java object populated by MusicBrainz, set to ignore unknown properties
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Relation {
		private String type;
		private RelationURL url;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public RelationURL getUrl() {
			return url;
		}

		public void setUrl(RelationURL url) {
			this.url = url;
		}

	}

	// Java object populated by MusicBrainz, set to ignore unknown properties
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RelationURL {
		private URI resource;

		public URI getResource() {
			return resource;
		}

		public void setResource(URI resource) {
			this.resource = resource;
		}
	}

	// Java object populated by MusicBrainz, set to ignore unknown properties
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ReleaseGroup {
		private String id;
		private String title;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

}
