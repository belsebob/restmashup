package com.marnils.restmashup.model;

import java.net.URL;

public class Album {
	private String id;
	private URL cover;
	private String title;

	public Album() {
	}

	public URL getCover() {
		return cover;
	}

	public void setCover(URL url) {
		this.cover = url;
	}

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
