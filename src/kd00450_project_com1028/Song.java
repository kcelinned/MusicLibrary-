package kd00450_project_com1028;

import java.util.ArrayList;
import java.util.List;

public class Song {

	private String name = null;
	private int rating = 0;
	private List<Artist> artists = null;
	private String format = null;
	private String album = null;

	public Song(String name, List<Artist> artists, String album, String format, int rating)
			throws IllegalArgumentException {
		if (name.equals(null) || artists.isEmpty() || format.equals(null)) {
			throw new IllegalArgumentException("A song has to be made with an artist, name or format");
		} else if (rating > 5 || rating < 0) {
			throw new IllegalArgumentException("Rating can only be from 0-5");
		} else {
			this.name = name;
			this.rating = rating;
			this.format = format;
			this.artists = artists;
			this.album = album;
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
			throw new IllegalArgumentException("Rating can only be from 0-5");
		} else {
			this.rating = rating;
		}
	}

	public String getAlbum() {
		return this.album;
	}

	public List<Artist> getArtists() {
		return this.artists;
	}

	public String viewArtists() {
		StringBuffer buffer = new StringBuffer();
		for (Artist a : artists) {
			buffer.append(a.getName() + ", ");
		}
		return buffer.toString();
	}

	public String getFormat() {
		return this.format;
	}

}
