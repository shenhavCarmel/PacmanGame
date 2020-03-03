package GUI_Windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logic.WinnersTable;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class FinishGame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	// Constructor
	public FinishGame(int score, int pineappleCount, int appleCount, int strawberryCount, String time, boolean win) {
		
		
		this.setVisible(true);

		setTitle("Finished Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// show the player his score
		JLabel lblYourScore = new JLabel("Your Score : " + score);
		lblYourScore.setFont(new Font("Verdana Pro Cond Light", Font.BOLD, 24));
		lblYourScore.setForeground(new Color(255, 255, 255));
		lblYourScore.setBounds(151, 106, 242, 56);
		contentPane.add(lblYourScore);

		// show the player his score
		JLabel lblYourTime = new JLabel("Your time : " + time);
		lblYourTime.setFont(new Font("Verdana Pro Cond Light", Font.BOLD, 24));
		lblYourTime.setForeground(new Color(255, 255, 255));
		lblYourTime.setBounds(151, 149, 258, 56);
		contentPane.add(lblYourTime);


		// you lose label
		JLabel lblFailed = new JLabel("Ouch! You Lose");
		lblFailed.setForeground(Color.WHITE);
		lblFailed.setFont(new Font("Dialog", Font.BOLD, 47));
		lblFailed.setForeground(new Color(255, 255, 255));
		lblFailed.setBounds(22, 41, 387, 87);
		contentPane.add(lblFailed);

		ImageIcon facePalm = new ImageIcon(FinishGame.class.getResource("/pics/facePalm.png"));
		Image resizedFacePam = facePalm.getImage();

		JLabel labelSadEmoji = new JLabel("");
		labelSadEmoji.setBounds(45, 121, 85, 85);
		resizedFacePam = resizedFacePam.getScaledInstance(labelSadEmoji.getWidth(), labelSadEmoji.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		facePalm = new ImageIcon(resizedFacePam);
		labelSadEmoji.setIcon(facePalm);
		contentPane.add(labelSadEmoji);

		// you won label
		JLabel lblCongratulations = new JLabel("Congratulations!");
		lblCongratulations.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 66));
		lblCongratulations.setForeground(new Color(255, 255, 255));
		lblCongratulations.setBounds(22, 41, 387, 87);
		contentPane.add(lblCongratulations);

		ImageIcon dab = new ImageIcon(FinishGame.class.getResource("/pics/Unicorn.png"));
		Image resizedDab = dab.getImage();

		JLabel labelEmoji = new JLabel("");
		labelEmoji.setBounds(45, 121, 85, 85);
		resizedDab = resizedDab.getScaledInstance(labelEmoji.getWidth(), labelEmoji.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		dab = new ImageIcon(resizedDab);
		labelEmoji.setIcon(dab);
		contentPane.add(labelEmoji);

	

		// set background
		ImageIcon background = new ImageIcon(FinishGame.class.getResource("/pics/backgroundWin.jpg"));
		Image resizedBackground = background.getImage();

		JButton btnExit = new JButton("Save Score");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {   
				String nickname = textField.getText();
				if (nickname == null | nickname == "") {
					// TODO: a pop up
				}
				else {
					new WinnersTable(score, pineappleCount, appleCount, strawberryCount, nickname);
					setVisible(false);
					MainMenu main = new MainMenu();
					main.setVisible(true);
					dispose();
				}

			}
		});
		btnExit.setFont(new Font("Verdana Pro Cond SemiBold", Font.PLAIN, 14));
		btnExit.setBackground(new Color(248, 248, 255));
		btnExit.setBounds(310, 212, 114, 31);
		contentPane.add(btnExit);


		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 434, 261);
		resizedBackground = resizedBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		background = new ImageIcon(resizedBackground);

		// button - go back to main menu
		JButton btnMainMenu = new JButton("Go Back");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				MainMenu main = new MainMenu();
				main.setVisible(true);
				dispose();
			}
		});
		btnMainMenu.setFont(new Font("Verdana Pro Cond SemiBold", Font.PLAIN, 14));
		btnMainMenu.setBackground(new Color(248, 248, 255));
		btnMainMenu.setBounds(10, 212, 92, 31);
		contentPane.add(btnMainMenu);

		// place to add name
		textField = new JTextField("");
		textField.setBounds(202, 216, 86, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNickname = new JLabel("Nickname :");
		lblNickname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNickname.setForeground(Color.WHITE);
		lblNickname.setBounds(112, 216, 85, 26);
		contentPane.add(lblNickname);
		lblBackground.setIcon(background);
		contentPane.add(lblBackground);
		
		// if game won - show labels of victory
		if(win) {
			lblFailed.setVisible(false);
			labelSadEmoji.setVisible(false);	
			lblCongratulations.setVisible(true);
			labelEmoji.setVisible(true);
		}
		else {  // if game lost - show labels of loosing
			lblCongratulations.setVisible(false);
			labelEmoji.setVisible(false);
			lblFailed.setVisible(true);
			labelSadEmoji.setVisible(true);
		}

	}

}
