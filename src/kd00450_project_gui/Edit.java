package kd00450_project_gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import kd00450_project_com1028.Album;
import kd00450_project_com1028.Artist;
import kd00450_project_com1028.Song;
import kd00450_project_dao.MusicLibraryDAO;
import javax.swing.JComboBox;

public class Edit {

	private JFrame frmEditLibrary;
	private JTextField nameField;
	private JTextField albumName;
	private JTextField artistField;
	private JTextField formatField;
	private JTextField ratingField;
	private JTextField albumArtist;
	private JTextField albumFormat;
	private JTextField albumRating;
	private JTextField sName;
	private JTextField sArtists;
	private JTextField sFormat;
	private JTextField sRating;
	
	private MusicLibraryDAO musicLibrary = new MusicLibraryDAO(); 
	private List<Song> songsList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void launchEdit() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit window = new Edit();
					window.frmEditLibrary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Edit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEditLibrary = new JFrame();
		frmEditLibrary.setTitle("Edit Library");
		frmEditLibrary.setBounds(100, 100, 499, 660);
		frmEditLibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditLibrary.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(15, 16, 457, 572);
		frmEditLibrary.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Add Album", null, panel, null);
		panel.setLayout(null);
		
		albumName = new JTextField();
		albumName.setBounds(86, 16, 146, 26);
		panel.add(albumName);
		albumName.setColumns(10);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(15, 19, 69, 20);
		panel.add(lblName_1);
		
		JLabel lblArtist_1 = new JLabel("Artist");
		lblArtist_1.setBounds(15, 65, 69, 20);
		panel.add(lblArtist_1);
		
		albumArtist = new JTextField();
		albumArtist.setBounds(86, 62, 284, 26);
		panel.add(albumArtist);
		albumArtist.setColumns(10);
		
		JLabel lblFormat_1 = new JLabel("Format");
		lblFormat_1.setBounds(15, 115, 69, 20);
		panel.add(lblFormat_1);
		

		albumFormat = new JTextField();
		albumFormat.setBounds(86, 112, 146, 26);
		panel.add(albumFormat);
		albumFormat.setColumns(10);
		
		JLabel lblRating_1 = new JLabel("Rating");
		lblRating_1.setBounds(15, 163, 69, 20);
		panel.add(lblRating_1);
		
		albumRating = new JTextField();
		albumRating.setBounds(86, 160, 46, 26);
		panel.add(albumRating);
		albumRating.setColumns(10);
		
		JLabel lblSongs = new JLabel("Songs");
		lblSongs.setBounds(163, 215, 69, 20);
		panel.add(lblSongs);
		
		sName = new JTextField();
		sName.setBounds(86, 264, 146, 26);
		panel.add(sName);
		sName.setColumns(10);
			
		JLabel lblName_2 = new JLabel("Name");
		lblName_2.setBounds(15, 267, 69, 20);
		panel.add(lblName_2);
		
		sArtists = new JTextField();
		sArtists.setBounds(86, 316, 284, 26);
		panel.add(sArtists);
		sArtists.setColumns(10);
		
		JLabel lblArtist_2 = new JLabel("Artist");
		lblArtist_2.setBounds(15, 319, 69, 20);
		panel.add(lblArtist_2);
		
		JLabel lblFormat_2 = new JLabel("Format");
		lblFormat_2.setBounds(15, 366, 69, 20);
		panel.add(lblFormat_2);
		
		sFormat = new JTextField();
		sFormat.setBounds(86, 363, 146, 26);
		panel.add(sFormat);
		sFormat.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Rating");
		lblNewLabel.setBounds(15, 415, 69, 20);
		panel.add(lblNewLabel);
		
		sRating = new JTextField();
		sRating.setBounds(86, 412, 61, 26);
		panel.add(sRating);
		sRating.setColumns(10);
		
		JButton btnAddSong = new JButton("Add Song");
		btnAddSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = sName.getText();
				List<String> artistNames = Arrays.asList(sArtists.getText().split(",",-1));
				List<Artist> artistList = new ArrayList<>();
				for(String artist: artistNames) {
					Artist newArtist = new Artist(artist);
					artistList.add(newArtist);
				}
				String format = sFormat.getText();
				int rating = Integer.parseInt(sRating.getText());
				String albmName = albumName.getText();
				
				Song tmpSong = new Song(name, artistList, albmName,format, rating);
				songsList.add(tmpSong);
				
				sName.setText(null);
				sArtists.setText(null);
				sFormat.setText(null);
				sRating.setText("");
			}
		});
		btnAddSong.setBounds(229, 411, 115, 29);
		panel.add(btnAddSong);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = albumName.getText();
				List<String> artistNames = Arrays.asList(albumArtist.getText().split(",",-1));
				List<Artist> artistList = new ArrayList<>();
				for(String artist: artistNames) {
					Artist newArtist = new Artist(artist);
					artistList.add(newArtist);
				}
				int rating = Integer.parseInt(albumRating.getText());
				String format = albumFormat.getText();
				
				Album album = new Album(name, artistList, songsList, rating, format);
				
				try {
					musicLibrary.addAlbum(album);
				}catch(Exception e1) {
					e1.printStackTrace();
				
			}
				songsList.clear();
				albumName.setText("");
				albumArtist.setText("");
				albumRating.setText("");
				albumFormat.setText("");
			}
				
		});
		btnSubmit.setBounds(322, 493, 115, 29);
		panel.add(btnSubmit);
		
		
		// Song Panel
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Add Song", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(73, 53, 69, 20);
		panel_1.add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(145, 50, 146, 26);
		panel_1.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblArtist = new JLabel("Artist");
		lblArtist.setBounds(73, 100, 69, 20);
		panel_1.add(lblArtist);
		
		artistField = new JTextField();
		artistField.setBounds(145, 97, 230, 26);
		panel_1.add(artistField);
		artistField.setColumns(10);
		
		JLabel lblFormat = new JLabel("Format");
		lblFormat.setBounds(73, 145, 69, 20);
		panel_1.add(lblFormat);
		
		formatField = new JTextField();
		formatField.setBounds(145, 142, 146, 26);
		panel_1.add(formatField);
		formatField.setColumns(10);
		
		JLabel lblRating = new JLabel("Rating");
		lblRating.setBounds(73, 193, 69, 20);
		panel_1.add(lblRating);
		
		ratingField = new JTextField();
		ratingField.setBounds(145, 190, 53, 26);
		panel_1.add(ratingField);
		ratingField.setColumns(10);
		
		//Button submits data into database
		JButton songBtn = new JButton("Submit");
		songBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String format = formatField.getText();
				int rating = Integer.parseInt(ratingField.getText());
				List<String> artistNames = Arrays.asList(artistField.getText().split(",",-1));
				List<Artist> artistList = new ArrayList<>();
				for(String artist: artistNames) {
					Artist newArtist = new Artist(artist);
					artistList.add(newArtist);
				}
				
				Song newSong = new Song(name, artistList, null, format, rating);
				
				try {
					musicLibrary.addSong(newSong);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				nameField.setText("");
				formatField.setText("");
				ratingField.setText("");
				artistField.setText("");
			}
		});
		songBtn.setBounds(322, 279, 115, 29);
		panel_1.add(songBtn);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Remove Song/Album", null, panel_2, null);
		panel_2.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		String sort = "name";
		List<Album> albums = new ArrayList<>();
		albums = musicLibrary.getAlbums(sort);
		for(Album a : albums) {
			comboBox.addItem(a.getName());
		}
		comboBox.setBounds(15, 76, 306, 26);
		panel_2.add(comboBox);
		
		JButton deleteAlbum = new JButton("Delete");
		deleteAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String albumName = (String) comboBox.getSelectedItem();
				
				try {
					List<Album> albums = new ArrayList<>();
					albums = musicLibrary.getAlbums(sort);
					for(Album a: albums) {
						if(albumName.equals(a.getName())) {
							musicLibrary.removeAlbum(a);
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}	
				
			}
		});
		deleteAlbum.setBounds(346, 76, 91, 26);
		panel_2.add(deleteAlbum);
		
		
		JComboBox<String> songCombo = new JComboBox<String>();
		List<Song> songs = new ArrayList<>();
		songs = musicLibrary.getSongs(sort);
		for(Song s: songs) {
			songCombo.addItem(s.getName());
		}
		songCombo.setBounds(15, 316, 306, 26);
		panel_2.add(songCombo);
		
		JButton deleteSong = new JButton("Delete");
		deleteSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
					String songName = (String) songCombo.getSelectedItem();
					
					try {
						List<Song> songs = new ArrayList<>();
						songs = musicLibrary.getSongs(sort);
						for(Song s: songs) {
							if(songName.equals(s.getName())) {
								musicLibrary.removeSong(s);
							}
						}
					}catch(Exception e) {
						e.printStackTrace();
					}	
			}
		});
		deleteSong.setBounds(346, 316, 91, 26);
		panel_2.add(deleteSong);
		
		
		JLabel lblAlbums = new JLabel("Albums:");
		lblAlbums.setBounds(25, 40, 69, 20);
		panel_2.add(lblAlbums);
		
		JLabel lblSongs_1 = new JLabel("Songs:");
		lblSongs_1.setBounds(25, 275, 69, 20);
		panel_2.add(lblSongs_1);
	}
}
