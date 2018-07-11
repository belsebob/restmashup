package com.marnils.restmashup.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Artist object
 *  
 * @author Martin
 *
 */

public class Artist {

	private String mbid;
	private String description;
	private List<Album> albums = new ArrayList<Album>();

	public Artist() {
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public List<Album> getAlbums() {
		return this.albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}