package com.shikha.learn.MarsRover.response;

import java.util.ArrayList;

import java.util.List;

public class MarsRoverApiResponse {
	List<MarsPhotos> photos = new ArrayList<>();
	

	public List<MarsPhotos> getPhotos() {
		return photos;
	}


	public void setPhotos(List<MarsPhotos> photos) {
		this.photos = photos;
	}


	@Override
	public String toString() {
		return "MaraRoverApiResponse [photos=" + photos + "]";
	}

}
