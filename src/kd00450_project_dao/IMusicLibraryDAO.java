package kd00450_project_dao;

import java.util.List;

import kd00450_project_com1028.Album;
import kd00450_project_com1028.Artist;
import kd00450_project_com1028.Song;

public interface IMusicLibraryDAO {
	
	public void addAlbum(Album album);
	
	public void addSong(Song song);
	
	public void removeAlbum(Album album);
	
	public void removeSong(Song song);
	
	public List<Album> getAlbums(String sort);
	
	public List<Song> getSongs(String sort);
	
	public List<Artist> getArtists();
	
	public List<Song> viewByArtist(String artist, String sort);
	
	public List<Song> viewByFormat(String format, String sort);
	
	public List<Song> viewAlbumSongs(String album, String sort);
	
	

}
