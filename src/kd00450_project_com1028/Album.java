package kd00450_project_com1028;

import java.util.ArrayList;
import java.util.List;

public class Album {

	private String name = null;
	private int rating = 0;
	private List<Artist> artist = null;
	private List<Song> songs = null;
	private String format = null;

	public Album(String name, List<Artist> artists, List<Song> songs, int rating, String format)
			throws IllegalArgumentException {
		if (name.equals(null) || artists.isEmpty() || format.equals(null)) {
			throw new IllegalArgumentException("Album must have a name, artist, song and format");
		} else if (rating > 5 || rating < 0) {
			throw new IllegalArgumentException("Rating must be between 0-5");
		} else {
			this.name = name;
			this.artist = artists;
			this.songs = songs;
			this.format = format;
			this.rating = rating;
		}
	}

	public String getName() {
		return this.name;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) throws IllegalArgumentException {
		if (rating > 5 || rating < 0) {
			throw new IllegalArgumentException("Rating is from 0-5");
		} else {
			this.rating = rating;
		}
	}

	public List<Artist> getArtist() {
		return this.artist;
	}

	public String viewArtists() {
		StringBuffer buffer = new StringBuffer();
		for (Artist a : artist) {
			buffer.append(a.getName() + ", ");
		}
		return buffer.toString();
	}

	public List<Song> getSongs() {
		return this.songs;
	}

	public String getFormat() {
		return this.format;
	}

}
