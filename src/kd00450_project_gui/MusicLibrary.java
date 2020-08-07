package kd00450_project_gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MusicLibrary {

	private JFrame frmOpeningWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicLibrary window = new MusicLibrary();
					window.frmOpeningWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MusicLibrary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOpeningWindow = new JFrame();
		frmOpeningWindow.setTitle("Opening Window");
		frmOpeningWindow.setBounds(100, 100, 450, 300);
		frmOpeningWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOpeningWindow.getContentPane().setLayout(null);
		
		JButton openButton = new JButton("Open Library");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenLibrary open = new OpenLibrary();
				open.launchLibrary();
			}
		});
		openButton.setBounds(15, 109, 162, 29);
		frmOpeningWindow.getContentPane().add(openButton);
		
		JButton editButton = new JButton("Edit Library");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit edit = new Edit();
				edit.launchEdit();
			}
		});
		editButton.setBounds(243, 109, 170, 29);
		frmOpeningWindow.getContentPane().add(editButton);
	}
}
