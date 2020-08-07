package kd00450_project_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import kd00450_project_com1028.Album;
import kd00450_project_com1028.Artist;
import kd00450_project_com1028.Song;

public class MusicLibraryDAO implements IMusicLibraryDAO {

	private Connection connect;
	private Statement statement;

	public MusicLibraryDAO() {
		super();

		this.connect = null;
		this.statement = null;
		this.openConnection();
	}

	private void openConnection() {
		try {
			if (this.connect == null || this.connect.isClosed()) {
				this.connect = DriverManager
						.getConnection("jdbc:hsqldb:file:db_Data/myDBfilestore;ifexists=true;shutdown=true", "SA", "");
			}
			if (this.statement == null || this.statement.isClosed()) {
				this.statement = this.connect.createStatement();
			}
		} catch (SQLException e) {
			System.out.println("Failed to Create a Connection to the database");
			throw new RuntimeException(e);
		}
	}

	public void closeConnection() {
		try {
			if (this.statement != null) {
				this.statement.close();
			}
			if (this.connect != null) {
				this.connect.close();
			}
			System.out.println("Closed the connection to the database");
		} catch (Exception e) {
			System.out.println("Failed to close the connection to the database");
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * Add album to database
	 * 
	 * @param album
	 *
	 **/

	@Override
	public void addAlbum(Album album) {
		try {
			// insert album into album table
			PreparedStatement preparedStatement = this.connect
					.prepareStatement("INSERT INTO album (name, rating, format) VALUES(?, ?, ?)");

			preparedStatement.setString(1, album.getName());
			preparedStatement.setInt(2, album.getRating());
			preparedStatement.setString(3, album.getFormat());

			preparedStatement.executeUpdate();

			// get the albumId of the album just inserted into the database
			int albumID = 0;
			String query = "SELECT TOP 1 albumId FROM album ORDER BY albumId DESC";
			ResultSet results = this.statement.executeQuery(query);
			while (results.next()) {
				albumID = results.getInt("albumid");
			}

			// insert the album's artists into the table
			for (Artist a : album.getArtist()) {
				PreparedStatement statement2 = this.connect
						.prepareStatement("INSERT INTO AlbumArtist(albumID, ArtistID) VALUES (?,?)");
				
				//checks if artist already exists in the artist table
				if (artistExists(a)) {
					addArtist(a);
				}

				
				int artistID = 0;
				PreparedStatement artistQuery = this.connect
						.prepareStatement("SELECT artistID FROM artist WHERE name =?");
				artistQuery.setString(1, a.getName());
				ResultSet artistRslt = artistQuery.executeQuery();
				while (artistRslt.next()) {
					artistID = artistRslt.getInt("artistID");
				}
				statement2.setInt(1, albumID);
				statement2.setInt(2, artistID);

				statement2.executeUpdate();
			}

			// insert album's songs into table
			for (Song s : album.getSongs()) {
				PreparedStatement preparedStatement2 = this.connect
						.prepareStatement("INSERT INTO song(name,rating,format,albumID) VALUES (?,?,?,?)");

				preparedStatement2.setString(1, s.getName());
				preparedStatement2.setInt(2, s.getRating());
				preparedStatement2.setString(3, s.getFormat());
				preparedStatement2.setInt(4, albumID);

				preparedStatement2.executeUpdate();

				int songID = 0;
				String query2 = "SELECT TOP 1 songId FROM Song ORDER BY songId DESC";
				ResultSet songResults = this.statement.executeQuery(query2);
				while (songResults.next()) {
					songID = songResults.getInt("songid");
				}
			
				// insert song's artists into database
				for (Artist artist : s.getArtists()) {
					PreparedStatement statement2 = this.connect
							.prepareStatement("INSERT INTO artistSong(songID, ArtistID) VALUES (?,?)");
					if (artistExists(artist)) {
						addArtist(artist);
					}

					int artistID = 0;
					PreparedStatement artistQuery = this.connect
							.prepareStatement("SELECT artistID FROM artist WHERE name =?");
					artistQuery.setString(1, artist.getName());
					ResultSet artistRslt = artistQuery.executeQuery();
					while (artistRslt.next()) {
						artistID = artistRslt.getInt("artistID");
					}
					statement2.setInt(1, songID);
					statement2.setInt(2, artistID);

					statement2.executeUpdate();
				}

			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add song to database
	 * 
	 * @param song
	 * 
	 **/

	@Override
	public void addSong(Song song) {
		try {
			PreparedStatement preparedStatement = this.connect
					.prepareStatement("INSERT INTO song (name, rating, format) VALUES(?,?,?)");

			preparedStatement.setString(1, song.getName());
			preparedStatement.setInt(2, song.getRating());
			preparedStatement.setString(3, song.getFormat());

			preparedStatement.executeUpdate();

			String query2 = "SELECT TOP 1 songId FROM Song ORDER BY songId DESC";
			ResultSet songResults = this.statement.executeQuery(query2);
			songResults.next();
			int songID = songResults.getInt("songid");

			for (Artist artist : song.getArtists()) {
				PreparedStatement statement2 = this.connect
						.prepareStatement("INSERT INTO artistSong(songID, ArtistID) VALUES (?,?)");
				if (artistExists(artist)) {
					addArtist(artist);
				}

				int artistID = 0;
				PreparedStatement artistQuery = this.connect
						.prepareStatement("SELECT artistID FROM artist WHERE name = ?");
				artistQuery.setString(1, artist.getName());
				ResultSet artistRslt = artistQuery.executeQuery();

				while (artistRslt.next()) {
					artistID = artistRslt.getInt("artistID");
				}

				statement2.setInt(1, songID);
				statement2.setInt(2, artistID);

				statement2.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}

	}

	/** Checks if artist exists in the database **/

	private boolean artistExists(Artist artist) {
		boolean exists = true;
		try {
			PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * from artist WHERE name = ?");
			preparedStatement.setString(1, artist.getName());
			ResultSet results = preparedStatement.executeQuery();

			if (results.next()) {
				exists = false;
			} else {
				exists = true;
			}

			return exists;

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}
	}

	/** Adds artist to database **/
	private void addArtist(Artist artist) {
		try {
			PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO artist(name) VALUES (?)");

			preparedStatement.setString(1, artist.getName());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}
	}

	/** Removes album from database **/
	@Override
	public void removeAlbum(Album album) {
		try {

			List<Integer> artistIds = new ArrayList<>();
			PreparedStatement artistQuery = this.connect.prepareStatement(
					"SELECT artistID from AlbumArtist WHERE albumId = (SELECT albumID from Album WHERE name = ?)");
			artistQuery.setString(1, album.getName());
			ResultSet results = artistQuery.executeQuery();
			while (results.next()) {
				artistIds.add(results.getInt("artistID"));
			}

			PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM Album WHERE name = ? ");
			preparedStatement.setString(1, album.getName());
			preparedStatement.executeUpdate();

			for (int id : artistIds) {
				if (shouldRemoveArtist(id)) {
					removeArtist(id);
				}
			}

		} catch (SQLException e) {
			System.out.print("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}
	}

	/** Checks if Artist should be Removed from Database **/

	private boolean shouldRemoveArtist(int id) {
		boolean albumExists = true;
		boolean songExists = true;
		boolean shouldRemove = false;
		try {
			PreparedStatement albumQuery = this.connect.prepareStatement("SELECT * FROM AlbumArtist WHERE albumId = ?");
			albumQuery.setInt(1, id);
			ResultSet resultsAlbum = albumQuery.executeQuery();
			if (resultsAlbum.next()) {
				albumExists = true;
			} else {
				albumExists = false;
			}

			PreparedStatement songQuery = this.connect.prepareStatement("SELECT * FROM ArtistSong WHERE artistId = ?");
			songQuery.setInt(1, id);
			ResultSet resultsSong = songQuery.executeQuery();
			if (resultsSong.next()) {
				songExists = true;
			} else {
				songExists = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (albumExists || songExists) {
			shouldRemove = false;
		} else {
			shouldRemove = true;
		}

		return shouldRemove;
	}

	private void removeArtist(int id) {
		try {
			PreparedStatement preparedStatement = this.connect
					.prepareStatement("DELETE FROM Artist WHERE artistId = ? ");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** Removes song from database **/

	@Override
	public void removeSong(Song song) {
		try {
			List<Integer> artistIds = new ArrayList<>();
			PreparedStatement artistQuery = this.connect.prepareStatement(
					"SELECT artistID from ArtistSong WHERE songId = (SELECT songID from Song WHERE name = ?)");
			artistQuery.setString(1, song.getName());
			ResultSet results = artistQuery.executeQuery();
			while (results.next()) {
				artistIds.add(results.getInt("artistID"));
			}

			PreparedStatement query = this.connect.prepareStatement("SELECT albumID FROM song WHERE name =?");
			query.setString(1, song.getName());
			ResultSet albumResults = query.executeQuery();

			while (albumResults.next()) {
				if (!(albumResults.getInt("albumId") > 0)) {
					PreparedStatement preparedStatement = this.connect
							.prepareStatement("DELETE FROM Song WHERE name = ? ");
					preparedStatement.setString(1, song.getName());
					preparedStatement.executeUpdate();

					for (int id : artistIds) {
						if (shouldRemoveArtist(id)) {
							removeArtist(id);
						}
					}

				} else {
					System.out.println("Cannot delete a Song in an Album");
				}

			}

		} catch (SQLException e) {
			System.out.print("SQLException happened while trying to edit library");
			throw new RuntimeException(e);
		}
	}

	/** Get all albums from Database **/
	@Override
	public List<Album> getAlbums(String sort) {
		List<Album> albumList = new ArrayList<>();

		try {
			String query = "SELECT * FROM Album ORDER BY " + sort;
			ResultSet albumResults = this.statement.executeQuery(query);
			// PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT *
			// FROM Album ORDER BY (?)");
			// preparedStatement.setString(1, sort);
			// ResultSet albumResults = preparedStatement.executeQuery();

			while (albumResults.next()) {
				int albumId = albumResults.getInt("albumID");
				String albumName = albumResults.getString("name");
				String albumFormat = albumResults.getString("format");
				int albumRating = albumResults.getInt("rating");

				PreparedStatement songStatement = this.connect.prepareStatement("SELECT * FROM Song WHERE albumID = ?");
				songStatement.setInt(1, albumId);
				ResultSet songResults = songStatement.executeQuery();
				List<Song> songsList = new ArrayList<>();

				// Go through songs of that album
				while (songResults.next()) {

					int songId = songResults.getInt("songId");
					String songName = songResults.getString("name");
					int songRating = songResults.getInt("rating");
					String songFormat = songResults.getString("format");

					List<Artist> artists = new ArrayList<>();
					PreparedStatement artistStatement = this.connect
							.prepareStatement("SELECT artistID FROM ArtistSong WHERE songID = ?");
					artistStatement.setInt(1, songId);
					ResultSet artistResults = artistStatement.executeQuery();

					while (artistResults.next()) {
						int artistID = artistResults.getInt("artistID");
						PreparedStatement artistStatement_2 = this.connect
								.prepareStatement("SELECT name FROM Artist WHERE artistID = ?");
						artistStatement_2.setInt(1, artistID);
						ResultSet artistResults_2 = artistStatement_2.executeQuery();

						while (artistResults_2.next()) {
							String name = artistResults_2.getString("name");
							Artist tmpArtist = new Artist(name);
							artists.add(tmpArtist);
						}

					}

					Song tmpSong = new Song(songName, artists, albumName, songFormat, songRating);
					songsList.add(tmpSong);
				}

				PreparedStatement artistQuery = this.connect
						.prepareStatement("SELECT artistID FROM albumArtist WHERE albumID = ?");
				artistQuery.setInt(1, albumId);
				ResultSet artistRslt = artistQuery.executeQuery();
				List<Artist> artistList = new ArrayList<>();

				while (artistRslt.next()) {
					int artistID = artistRslt.getInt("artistID");

					PreparedStatement artistQuery_2 = this.connect
							.prepareStatement("SELECT name FROM Artist WHERE artistID = ?");
					artistQuery_2.setInt(1, artistID);
					ResultSet artistRslt_2 = artistQuery_2.executeQuery();

					while (artistRslt_2.next()) {
						String artistName = artistRslt_2.getString("name");
						Artist artist = new Artist(artistName);
						artistList.add(artist);
					}
				}

				Album tmpAlbum = new Album(albumName, artistList, songsList, albumRating, albumFormat);
				albumList.add(tmpAlbum);

			}
		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}

		return albumList;
	}

	/** Get all songs from Database **/

	@Override
	public List<Song> getSongs(String sort) {
		List<Song> songList = new ArrayList<>();

		try {
			String query = "SELECT * FROM Song ORDER BY " + sort;
			ResultSet songResults = this.statement.executeQuery(query);
			

			while (songResults.next()) {
				int songId = songResults.getInt("songId");
				String songName = songResults.getString("name");
				String songFormat = songResults.getString("format");
				int songRating = songResults.getInt("rating");
				int albumId = songResults.getInt("albumID");
				String albumName = null;

				if (albumId != 0) {
					PreparedStatement albumStatement = this.connect
							.prepareStatement("SELECT name FROM Album WHERE albumID =?");
					albumStatement.setInt(1, albumId);
					ResultSet albumRslt = albumStatement.executeQuery();

					while (albumRslt.next()) {
						albumName = albumRslt.getString("name");
					}
				}

				PreparedStatement artistStatement = this.connect
						.prepareStatement("SELECT artistID FROM ArtistSong WHERE songID = ?");
				artistStatement.setInt(1, songId);
				ResultSet artistResults = artistStatement.executeQuery();
				List<Artist> artists = new ArrayList<>();

				while (artistResults.next()) {
					PreparedStatement artistStatement_2 = this.connect
							.prepareStatement("SELECT * FROM Artist WHERE artistID = ?");
					artistStatement_2.setInt(1, artistResults.getInt("artistID"));
					ResultSet artistRslts = artistStatement_2.executeQuery();
					while (artistRslts.next()) {
						String name = artistRslts.getString("name");
						Artist tmpArtist = new Artist(name);
						artists.add(tmpArtist);
					}

				}

				Song tmpSong = new Song(songName, artists, albumName, songFormat, songRating);
				songList.add(tmpSong);

			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}

		return songList;

	}

	/** View all artists **/
	@Override
	public List<Artist> getArtists() {
		List<Artist> artistlist = new ArrayList<>();

		try {
			String artistQuery = "SELECT * FROM Artist ORDER BY name";
			PreparedStatement preparedStatement = this.connect.prepareStatement(artistQuery);
			ResultSet artistResults = preparedStatement.executeQuery();

			while (artistResults.next()) {
				String name = artistResults.getString("name");
				Artist tmpArtist = new Artist(name);
				artistlist.add(tmpArtist);
			}

			return artistlist;

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}
	}

	/** View all songs by Artist **/

	@Override
	public List<Song> viewByArtist(String artist, String sort) {
		List<Song> songs = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = this.connect.prepareStatement(
					"SELECT songID FROM ArtistSong WHERE artistID = (SELECT artistID FROM artist WHERE name = ?)");
			preparedStatement.setString(1, artist);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int songId = results.getInt("songID");

				PreparedStatement songQuery = this.connect
						.prepareStatement("(SELECT * FROM song WHERE songId = ?) ORDER BY " + sort);
				songQuery.setInt(1, songId);
				ResultSet songResults = songQuery.executeQuery();

				while (songResults.next()) {
					String songName = songResults.getString("name");
					String songFormat = songResults.getString("format");
					int songRating = songResults.getInt("rating");
					int albumId = songResults.getInt("albumID");
					String albumName = null;

					if (albumId > 0) {
						PreparedStatement albumStatement = this.connect
								.prepareStatement("SELECT name FROM Album WHERE albumID =?");
						albumStatement.setInt(1, albumId);
						ResultSet albumRslt = albumStatement.executeQuery();

						while (albumRslt.next()) {
							albumName = albumRslt.getString("name");
						}
					}

					PreparedStatement artistStatement = this.connect
							.prepareStatement("SELECT * FROM ArtistSong WHERE songID = ?");
					artistStatement.setInt(1, songId);
					ResultSet artistResults = artistStatement.executeQuery();
					List<Artist> artists = new ArrayList<>();

					while (artistResults.next()) {
						int artistID = artistResults.getInt("artistID");

						PreparedStatement artistStatement_2 = this.connect
								.prepareStatement("SELECT name from Artist WHERE artistID=?");
						artistStatement_2.setInt(1, artistID);
						ResultSet artistRslts = artistStatement_2.executeQuery();

						while (artistRslts.next()) {
							String name = artistRslts.getString("name");
							Artist tmpArtist = new Artist(name);
							artists.add(tmpArtist);
						}
					}
					Song tmpSong = new Song(songName, artists, albumName, songFormat, songRating);
					songs.add(tmpSong);
				}
			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}
		return songs;
	}

	/**
	 *  View all songs of a certain format 
	 * @format
	 * @sort
	 * 
	 * **/

	@Override
	public List<Song> viewByFormat(String format, String sort) {
		List<Song> songs = new ArrayList<>();
		try {

			PreparedStatement preparedStatement = this.connect
					.prepareStatement("(SELECT * FROM Song WHERE Format = ?) ORDER BY " + sort);
			preparedStatement.setString(1, format);
			ResultSet songResults = preparedStatement.executeQuery();

			while (songResults.next()) {
				int songId = songResults.getInt("songId");
				String songName = songResults.getString("name");
				String songFormat = songResults.getString("format");
				int songRating = songResults.getInt("rating");
				int albumId = songResults.getInt("albumID");
				String albumName = null;

				if (albumId != 0) {
					PreparedStatement albumStatement = this.connect
							.prepareStatement("SELECT name FROM Album WHERE albumID =?");
					albumStatement.setInt(1, albumId);
					ResultSet albumRslt = albumStatement.executeQuery();

					while (albumRslt.next()) {
						albumName = albumRslt.getString("name");
					}
				}

				PreparedStatement artistStatement = this.connect
						.prepareStatement("SELECT * FROM ArtistSong WHERE songID = ?");
				artistStatement.setInt(1, songId);
				ResultSet artistResults = artistStatement.executeQuery();
				List<Artist> artists = new ArrayList<>();

				while (artistResults.next()) {
					int artistID = artistResults.getInt("artistID");

					PreparedStatement artistStatement_2 = this.connect
							.prepareStatement("SELECT name from Artist WHERE artistID=?");
					artistStatement_2.setInt(1, artistID);
					ResultSet artistRslts = artistStatement_2.executeQuery();

					while (artistRslts.next()) {
						String name = artistRslts.getString("name");
						Artist tmpArtist = new Artist(name);
						artists.add(tmpArtist);
					}
				}
				Song tmpSong = new Song(songName, artists, albumName, songFormat, songRating);
				songs.add(tmpSong);

			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}
		return songs;
	}

	/**
	 *  View Songs of an Album
	 * @ album
	 * @ sort
	 * 
	 *  **/
	@Override
	public List<Song> viewAlbumSongs(String album, String sort) {
		List<Song> songs = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = this.connect.prepareStatement(
					"SELECT * FROM Song WHERE albumID = (SELECT albumID FROM album WHERE name = ?) ORDER BY " + sort);
			preparedStatement.setString(1, album);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int songId = results.getInt("songID");
				String songName = results.getString("name");
				String songFormat = results.getString("format");
				int songRating = results.getInt("rating");
				int albumId = results.getInt("albumID");
				String albumName = null;

				PreparedStatement albumStatement = this.connect
						.prepareStatement("SELECT name FROM Album WHERE albumID =?");
				albumStatement.setInt(1, albumId);
				ResultSet albumRslt = albumStatement.executeQuery();

				while (albumRslt.next()) {
					albumName = albumRslt.getString("name");
				}

				PreparedStatement artistStatement = this.connect
						.prepareStatement("SELECT * FROM ArtistSong WHERE songID = ?");
				artistStatement.setInt(1, songId);
				ResultSet artistResults = artistStatement.executeQuery();
				List<Artist> artists = new ArrayList<>();

				while (artistResults.next()) {
					int artistID = artistResults.getInt("artistID");

					PreparedStatement artistStatement_2 = this.connect
							.prepareStatement("SELECT name from Artist WHERE artistID=?");
					artistStatement_2.setInt(1, artistID);
					ResultSet artistRslts = artistStatement_2.executeQuery();

					while (artistRslts.next()) {
						String name = artistRslts.getString("name");
						Artist tmpArtist = new Artist(name);
						artists.add(tmpArtist);
					}
				}
				Song tmpSong = new Song(songName, artists, albumName, songFormat, songRating);
				songs.add(tmpSong);
			}

		} catch (SQLException e) {
			System.out.println("SQLException happened while trying to query library");
			throw new RuntimeException(e);
		}
		return songs;
	}

}
