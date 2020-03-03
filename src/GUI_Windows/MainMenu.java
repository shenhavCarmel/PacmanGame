package GUI_Windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI_Windows.MainMenu;
import Logic.WinnersTable;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class MainMenu extends JFrame {

	private JPanel lblBackground;
	private int[][] boardMat;
	private Clip clip;
	private boolean isMuted;
	private URL soundName = MainMenu.class.getResource("/sound/pacman_beginning.wav");
	private Integer boardSelection = 1;
	private JTable table_1;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
 	}

	public MainMenu() {
		
		// start audio
		isMuted = false;
		try {		
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(clip.LOOP_CONTINUOUSLY);
		clip.start();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		// set controllers and background
		this.boardMat = new int[32][32];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 478);
		lblBackground = new JPanel();
		lblBackground.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(lblBackground);
		lblBackground.setLayout(null);
		

		// create mute button
		JButton btnMute = new JButton("");
		
	
		btnMute.setBounds(480, 11, 30, 30);
		ImageIcon muteImg = new ImageIcon(MainMenu.class.getResource("/pics/unMute.png"));
		// transform
		Image resizedMute = muteImg.getImage();
		
		// scale it by smooth way
		resizedMute = resizedMute.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		// transform it back
		muteImg = new ImageIcon(resizedMute);
		btnMute.setIcon(muteImg);
		lblBackground.add(btnMute);
		
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isMuted) { //unMute
					// start audio
					try {		
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.loop(clip.LOOP_CONTINUOUSLY);
					clip.start();
					}
					catch (Exception e1) {
						// TODO: handle exception
					}
					isMuted = false;
					
					// change icon
					
					ImageIcon muteImg = new ImageIcon(MainMenu.class.getResource("/pics/unMute.png"));
					Image resizedMute = muteImg.getImage();
					resizedMute = resizedMute.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
					muteImg = new ImageIcon(resizedMute);
					btnMute.setIcon(muteImg);
					lblBackground.add(btnMute);
					
				}
				else { //mute
					// stop audio
					clip.close();
					isMuted = true;
					
					// change icon
					ImageIcon muteImg = new ImageIcon(MainMenu.class.getResource("/pics/mute.png"));
					Image resizedMute = muteImg.getImage();
					resizedMute = resizedMute.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
					muteImg = new ImageIcon(resizedMute);
					btnMute.setIcon(muteImg);
					lblBackground.add(btnMute);
				}
			}
		});
		
		// choose board to play and start the game:

		// board1 button
		JButton btnBoard1 = new JButton("");
		
		btnBoard1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clip.stop();
				setVisible(false);
			    boardSelection = 1;
			    
			    // start game
				Game gameWindow = new Game(boardSelection, boardMat);
				gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				
				gameWindow.setVisible(true);
				dispose();
			}
		});

		btnBoard1.setBounds(84, 187, 89, 117);
		ImageIcon board1Img = new ImageIcon(MainMenu.class.getResource("/pics/Board1.png"));

		// transform it 
		Image resizedBoard1 = board1Img.getImage(); 

		// scale it the smooth way  
		resizedBoard1 = resizedBoard1.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); 

		// transform it back
		board1Img = new ImageIcon(resizedBoard1);  
		btnBoard1.setIcon(board1Img);
		lblBackground.add(btnBoard1);
		
		// board 2 button
		
		JButton btnBoard2 = new JButton("");
		btnBoard2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clip.stop();
				setVisible(false);
				boardSelection = 2;
				Game gameWindow = new Game(boardSelection, boardMat);
				
				gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				
				gameWindow.setVisible(true);
				dispose();
			}
		});
		btnBoard2.setBounds(221, 187, 89, 117);
		ImageIcon board2Img = new ImageIcon(MainMenu.class.getResource("/pics/Board2.png"));

		// transform it 
		Image resizedBoard2 = board2Img.getImage(); 

		// scale it the smooth way  
		resizedBoard2 = resizedBoard2.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); 

		// transform it back
		board2Img = new ImageIcon(resizedBoard2); 

		btnBoard2.setIcon(board2Img);
		lblBackground.add(btnBoard2);
		
		// board 3 button
		
		JButton btnBoard3 = new JButton("");
		btnBoard3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.stop();
				setVisible(false);
				boardSelection = 3;
				Game gameWindow = new Game(boardSelection, boardMat);
				gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				
				gameWindow.setVisible(true);
				dispose();
			}
		});
		btnBoard3.setBounds(356, 187, 89, 117);
		ImageIcon board3Img = new ImageIcon(MainMenu.class.getResource("/pics/Board3.jpg"));

		// transform it 
		Image resizedBoard3 = board3Img.getImage(); 

		// scale it the smooth way  
		resizedBoard3 = resizedBoard3.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); 

		// transform it back
		board3Img = new ImageIcon(resizedBoard3); 

		btnBoard3.setIcon(board3Img);
		lblBackground.add(btnBoard3);
				
		// set background
		ImageIcon background = new ImageIcon(MainMenu.class.getResource("/pics/Background.jpg"));
		Image resizedBackground = background.getImage();
		
		JLabel lblB = new JLabel("");
		lblB.setBounds(0, 0, 540, 439);
		resizedBackground = resizedBackground.getScaledInstance(lblB.getWidth(), lblB.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		background = new ImageIcon(resizedBackground);
		
		
		
		// show winners table
		
		WinnersTable winTable = new WinnersTable();
		String[][] players = winTable.getScoreTable(); 
	
		table_1 = new JTable(5,2);
		table_1.setToolTipText("");
		table_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Player", "Pineapple", "Apple", "Strawberry", "Score"},
				{players[0][0],players[0][1], players[0][2], players[0][3], players[0][4] },
				{players[1][0],players[1][1], players[1][2], players[1][3], players[1][4] },
				{players[2][0],players[2][1], players[2][2], players[2][3], players[2][4] },
				{players[3][0],players[3][1], players[3][2], players[3][3], players[3][4] },
				{players[4][0],players[4][1], players[4][2], players[4][3], players[4][4] },
			},
			new String[] {
				"Player", "Pineapple", "Apple", "Strawberry","Score"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.setForeground(Color.BLACK);
		table_1.setBackground(Color.LIGHT_GRAY);
		table_1.setBounds(84, 333, 361, 95);
		lblBackground.add(table_1);
		lblB.setIcon(background);
		lblBackground.add(lblB);
		
	}


	

	
}
