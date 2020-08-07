package kd00450_project_com1028;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SongTest {

	@Test
	public void testConstruction() {
		Artist a1 = new Artist("UMI");
		Artist a2 = new Artist("Yeek");
		List<Artist> artists = new ArrayList<>();
		artists.add(a1);
		artists.add(a2);
		Song s1 = new Song("Lullaby", artists, null, "MP3",5);
		
		assertEquals("Lullaby", s1.getName());
		assertEquals("MP3", s1.getFormat());
		assertEquals(5, s1.getRating());
		
	}
	
	

}
