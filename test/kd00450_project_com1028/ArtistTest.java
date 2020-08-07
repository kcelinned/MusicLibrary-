package kd00450_project_com1028;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArtistTest {

	@Test
	public void testArtist() {
		Artist a1 = new Artist("Beyonce");
		
		assertEquals("Beyonce", a1.getName());
	}


}
