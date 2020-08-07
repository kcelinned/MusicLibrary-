package kd00450_project_gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import kd00450_project_com1028.Album;
import kd00450_project_com1028.Artist;
import kd00450_project_com1028.Song;
import kd00450_project_dao.MusicLibraryDAO;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class OpenLibrary {

	private JFrame frmOpenLibrary;
	private JTable table;
	private JTextField byFormatField;
	private JTextField byArtistField;
	private JButton searchArtistBtn;
	private JComboBox<String> comboBoxSongs;
	
	MusicLibraryDAO musicLibrary = new MusicLibraryDAO();
	private JLabel lblViewSongsBy;
	private JLabel label;
	private JComboBox<String> comboBoxAlbums;
	private JTextField inAlbumField;

	/**
	 * Launch the application.
	 */
	public static void launchLibrary() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenLibrary window = new OpenLibrary();
					window.frmOpenLibrary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpenLibrary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOpenLibrary = new JFrame();
		frmOpenLibrary.setTitle("Open Library");
		frmOpenLibrary.setBounds(100, 100, 1069, 687);
		frmOpenLibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOpenLibrary.getContentPane().setLayout(null);
		
		
		JScrollPane pane = new JScrollPane(table);
		frmOpenLibrary.getContentPane().add(pane);
		
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		table.setBounds(500, 55, 519, 560);
		frmOpenLibrary.getContentPane().add(table);
		
		
		JLabel lblSortBy = new JLabel("Sort By");
		lblSortBy.setBounds(32, 101, 69, 20);
		frmOpenLibrary.getContentPane().add(lblSortBy);
		
		comboBoxSongs = new JComboBox<String>();
		comboBoxSongs.addItem("Name");
		comboBoxSongs.addItem("Rating");
		comboBoxSongs.setSelectedItem(null);
		comboBoxSongs.setBounds(106, 98, 202, 26);
		frmOpenLibrary.getContentPane().add(comboBoxSongs);
		
		comboBoxAlbums = new JComboBox<String>();
		comboBoxAlbums.addItem("Name");
		comboBoxAlbums.addItem("Rating");
		comboBoxAlbums.setSelectedItem(null);
		comboBoxAlbums.setBounds(106, 410, 202, 26);
		frmOpenLibrary.getContentPane().add(comboBoxAlbums);
		
		JButton btnNewButton = new JButton("View All Albums");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Album Name");
				model.addColumn("Artists");
				model.addColumn("Rating");
				model.addColumn("Format");
				
				String[] columnHeadings ={"ALBUM NAME", "ARTISTS", "RATING", "FORMAT"} ;
				model.insertRow(0, columnHeadings);
				
				JTableHeader header = table.getTableHeader();
			    TableColumnModel colModel = header.getColumnModel();
			    TableColumn column = colModel.getColumn(0);
			    column.setHeaderValue("Album Name");
			    
				List<Album> albumList = null;
				
				String sort = (String) comboBoxAlbums.getSelectedItem();
				try {
					albumList = musicLibrary.getAlbums(sort);
				}catch(Exception e) {
					e.printStackTrace();
				}
				for(Album tmpAlbum : albumList) {
					Object rowData[] = new Object[4];
					rowData[0] = tmpAlbum.getName();
					rowData[1] = tmpAlbum.viewArtists();
					rowData[2] = tmpAlbum.getRating();
					rowData[3] = tmpAlbum.getFormat(); 
					model.addRow(rowData);
				}	
				
			}
		});
		btnNewButton.setBounds(323, 409, 154, 29);
		frmOpenLibrary.getContentPane().add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("View All Songs");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Song Name");
				model.addColumn("Artists");
				model.addColumn("Album");
				model.addColumn("Rating");
				model.addColumn("Format");
				List<Song> songList = null;
				
				String[] columnHeadings ={"SONG NAME", "ARTISTS","ALBUM", "RATING", "FORMAT"};
				model.insertRow(0, columnHeadings);
				
				
				String sort = (String) comboBoxSongs.getSelectedItem();
				
				try {
					songList = musicLibrary.getSongs(sort);
				}catch(Exception e) {
					e.printStackTrace();
				}
				for(Song tmpSong : songList) {
					Object rowData[] = new Object[5];
					rowData[0] = tmpSong.getName();
					rowData[1] = tmpSong.viewArtists();
					rowData[2] = tmpSong.getAlbum();
					rowData[3] = tmpSong.getRating();
					rowData[4] = tmpSong.getFormat(); 
					model.addRow(rowData);
				}	
			}
		});
		btnNewButton_1.setBounds(118, 292, 154, 29);
		frmOpenLibrary.getContentPane().add(btnNewButton_1);
		
		
		JButton viewArtistBtn = new JButton("View All Artists");
		viewArtistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Artist Name");
				
				String[] columnHeadings = {"ARTIST NAME"};
				model.insertRow(0, columnHeadings);
				
				List<Artist> artistList = null;
				try {
					artistList = musicLibrary.getArtists();
				}catch(Exception e) {
						e.printStackTrace();
				}
				for(Artist tmpArtist : artistList) {
					Object rowData[] = new Object[1];
					rowData[0] = tmpArtist.getName();
					model.addRow(rowData);
				}
			}
		});
		viewArtistBtn.setBounds(118, 496, 154, 29);
		frmOpenLibrary.getContentPane().add(viewArtistBtn);
		
		byFormatField = new JTextField();
		byFormatField.setBounds(47, 188, 146, 26);
		frmOpenLibrary.getContentPane().add(byFormatField);
		byFormatField.setColumns(10);
		
		JButton btnFormat = new JButton("Format");
		btnFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Song Name");
				model.addColumn("Artists");
				model.addColumn("Album");
				model.addColumn("Rating");
				model.addColumn("Format");
				
				String[] columnHeadings ={"SONG NAME", "ARTISTS","ALBUM", "RATING", "FORMAT"};
				
				model.insertRow(0, columnHeadings);
				
				List<Song> songList = null;
				
				String sort = (String) comboBoxSongs.getSelectedItem();
				try {
					songList = musicLibrary.viewByFormat(byFormatField.getText(), sort);
				}catch(Exception e) {
					e.printStackTrace();
				}
				for(Song tmpSong : songList) {
					Object rowData[] = new Object[5];
					rowData[0] = tmpSong.getName();
					rowData[1] = tmpSong.viewArtists();
					rowData[2] = tmpSong.getAlbum();
					rowData[3] = tmpSong.getRating();
					rowData[4] = tmpSong.getFormat(); 
					model.addRow(rowData);
				}	
			}
		});
		btnFormat.setBounds(208, 187, 115, 29);
		frmOpenLibrary.getContentPane().add(btnFormat);
		
		byArtistField = new JTextField();
		byArtistField.setBounds(47, 233, 146, 26);
		frmOpenLibrary.getContentPane().add(byArtistField);
		byArtistField.setColumns(10);
		
		searchArtistBtn = new JButton("Artist");
		searchArtistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Song Name");
				model.addColumn("Artists");
				model.addColumn("Album");
				model.addColumn("Rating");
				model.addColumn("Format");
				
				String[] columnHeadings ={"SONG NAME", "ARTISTS","ALBUM", "RATING", "FORMAT"};
				
				model.insertRow(0, columnHeadings);
				
				List<Song> songList = null;
				
				String sort = (String) comboBoxSongs.getSelectedItem();
				try {
					songList = musicLibrary.viewByArtist(byArtistField.getText(),sort);
				}catch(Exception e) {
					e.printStackTrace();
				}
				for(Song tmpSong : songList) {
					Object rowData[] = new Object[5];
					rowData[0] = tmpSong.getName();
					rowData[1] = tmpSong.viewArtists();
					rowData[2] = tmpSong.getAlbum();
					rowData[3] = tmpSong.getRating();
					rowData[4] = tmpSong.getFormat(); 
					model.addRow(rowData);
				}	
				
			}
		});
		searchArtistBtn.setBounds(208, 232, 115, 29);
		frmOpenLibrary.getContentPane().add(searchArtistBtn);
		
		lblViewSongsBy = new JLabel("View Songs By :");
		lblViewSongsBy.setBounds(119, 155, 120, 20);
		frmOpenLibrary.getContentPane().add(lblViewSongsBy);
		
		label = new JLabel("Sort By");
		label.setBounds(32, 413, 69, 20);
		frmOpenLibrary.getContentPane().add(label);
		
		inAlbumField = new JTextField();
		inAlbumField.setColumns(10);
		inAlbumField.setBounds(181, 351, 146, 26);
		frmOpenLibrary.getContentPane().add(inAlbumField);
		
		JButton btnAlbum = new JButton("Album");
		btnAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				model.setColumnCount(0);
				model.addColumn("Song Name");
				model.addColumn("Artists");
				model.addColumn("Album");
				model.addColumn("Rating");
				model.addColumn("Format");
				
				String[] columnHeadings ={"SONG NAME", "ARTISTS","ALBUM", "RATING", "FORMAT"};
				
				model.insertRow(0, columnHeadings);
				
				List<Song> songList = null;
				
				String sort = (String) comboBoxSongs.getSelectedItem();
				try {
					songList = musicLibrary.viewAlbumSongs(inAlbumField.getText(),sort);
				}catch(Exception e) {
					e.printStackTrace();
				}
				for(Song tmpSong : songList) {
					Object rowData[] = new Object[5];
					rowData[0] = tmpSong.getName();
					rowData[1] = tmpSong.viewArtists();
					rowData[2] = tmpSong.getAlbum();
					rowData[3] = tmpSong.getRating();
					rowData[4] = tmpSong.getFormat(); 
					model.addRow(rowData);
				}	
				
			}
		});
		btnAlbum.setBounds(342, 350, 115, 29);
		frmOpenLibrary.getContentPane().add(btnAlbum);
		
		JLabel lblViewSongsIn = new JLabel("View Songs In :");
		lblViewSongsIn.setBounds(47, 354, 120, 20);
		frmOpenLibrary.getContentPane().add(lblViewSongsIn);
		
		
	}
}
