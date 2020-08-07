package kd00450_project_com1028;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AlbumTest {

	@Test
	public void testAlbum() {
		List<Artist> artistsAlbum = new ArrayList<>();
		List<Artist> artists1 = new ArrayList<>();
		List<Artist> artists2 = new ArrayList<>();

		Artist a1 = new Artist("Kehlani");
		Artist a2 = new Artist("6lack");
		Artist a3 = new Artist("Musiq Soulchild");
		
		artistsAlbum.add(a1);
		artists1.add(a1);
		artists1.add(a2);
		artists2.add(a1);
		artists2.add(a3);
		
		List<Song> songList = new ArrayList<>();
		
		Song s1 = new Song("Footsteps", artists2, "While We Wait", "MP3", 5);
		Song s2 = new Song("RPG", artists1, "While We Wait", "MP3", 4);
		songList.add(s1);
		songList.add(s2);
		
		Album album1 = new Album("While We Wait", artistsAlbum, songList, 5, "MP3");
		
		assertEquals("While We Wait", album1.getName());
		assertEquals(5, album1.getRating());
		assertEquals("MP3", album1.getFormat());
		assertEquals("Kehlani, ", album1.viewArtists());
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRating() {
		List<Artist> artistsAlbum = new ArrayList<>();
		List<Artist> artists1 = new ArrayList<>();
		List<Artist> artists2 = new ArrayList<>();

		Artist a1 = new Artist("Kehlani");
		Artist a2 = new Artist("6lack");
		Artist a3 = new Artist("Musiq Soulchild");
		
		artistsAlbum.add(a1);
		artists1.add(a1);
		artists1.add(a2);
		artists2.add(a1);
		artists2.add(a3);
		
		List<Song> songList = new ArrayList<>();
		
		Song s1 = new Song("Footsteps", artists2, "While We Wait", "MP3", 5);
		Song s2 = new Song("RPG", artists1, "While We Wait", "MP3", 4);
		songList.add(s1);
		songList.add(s2);
		
		Album album1 = new Album("While We Wait", artistsAlbum, songList, 7, "MP3");
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullName() {
		List<Artist> artistsAlbum = new ArrayList<>();
		List<Artist> artists1 = new ArrayList<>();
		List<Artist> artists2 = new ArrayList<>();

		Artist a1 = new Artist("Kehlani");
		Artist a2 = new Artist("6lack");
		Artist a3 = new Artist("Musiq Soulchild");
		
		artistsAlbum.add(a1);
		artists1.add(a1);
		artists1.add(a2);
		artists2.add(a1);
		artists2.add(a3);
		
		List<Song> songList = new ArrayList<>();
		
		Song s1 = new Song("Footsteps", artists2, "While We Wait", "MP3", 5);
		Song s2 = new Song("RPG", artists1, "While We Wait", "MP3", 4);
		songList.add(s1);
		songList.add(s2);
		
		Album album1 = new Album(null, artistsAlbum, songList, 7, "MP3");
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullFormat() {
		List<Artist> artistsAlbum = new ArrayList<>();
		List<Artist> artists1 = new ArrayList<>();
		List<Artist> artists2 = new ArrayList<>();

		Artist a1 = new Artist("Kehlani");
		Artist a2 = new Artist("6lack");
		Artist a3 = new Artist("Musiq Soulchild");
		
		artistsAlbum.add(a1);
		artists1.add(a1);
		artists1.add(a2);
		artists2.add(a1);
		artists2.add(a3);
		
		List<Song> songList = new ArrayList<>();
		
		Song s1 = new Song("Footsteps", artists2, "While We Wait", "MP3", 5);
		Song s2 = new Song("RPG", artists1, "While We Wait", "MP3", 4);
		songList.add(s1);
		songList.add(s2);
		
		Album album1 = new Album("While We Wait", artistsAlbum, songList, 7, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNoArtists() {
		List<Artist> artistsAlbum = new ArrayList<>();
		List<Artist> artists1 = new ArrayList<>();
		List<Artist> artists2 = new ArrayList<>();

		Artist a1 = new Artist("Kehlani");
		Artist a2 = new Artist("6lack");
		Artist a3 = new Artist("Musiq Soulchild");
		
		artists1.add(a1);
		artists1.add(a2);
		artists2.add(a1);
		artists2.add(a3);
		
		List<Song> songList = new ArrayList<>();
		
		Song s1 = new Song("Footsteps", artists2, "While We Wait", "MP3", 5);
		Song s2 = new Song("RPG", artists1, "While We Wait", "MP3", 4);
		songList.add(s1);
		songList.add(s2);
		
		Album album1 = new Album(null, artistsAlbum, songList, 7, "MP3");
	}
	
	

}
