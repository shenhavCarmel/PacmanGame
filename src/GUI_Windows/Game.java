package GUI_Windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logic.GameManager;
import Logic.GameTimer;
import Logic.Pacman;
import Logic.TimerListener;

import java.awt.Color;
import java.awt.Desktop.Action;

import javax.swing.JTextPane;

public class Game extends JFrame{

	public JPanel contentPane;
	private Board board;
	private int[][] boardMat;
	private int boardNumber;
	private int lives;
	private boolean isMuted;
	private URL soundName = Game.class.getResource("/sound/pacman_chomp.wav");
	private Clip clip;
	private Integer stage;
	private int numPineapple;
	private int numApple;
	private int numStrawberry;
	private int numPill;
	private int numPower;
	private Integer score;
	private JLabel lblScoreNum;
	private JLabel lblStageNum;
	private boolean start;
	private boolean paused;
	private boolean doubleSpeed;
	private boolean GameOver;
	//private JButton btnMute;
	public JLabel lblGetReady;
	private JLabel lblLife1;
	private JLabel lblLife2;
	private JLabel lblLife3;
	private JLabel lblTime;
	private GameTimer leadTimer;
	private JLabel lblFruitsNum;
	private JLabel lblGameOver;
	private int seconds;
	
	
	public Game(int BoardNumber, int[][] boardMat){

		score = 0;
		numPineapple = 0;
		numApple = 0;
		numStrawberry = 0;
		numPill = 0;
		numPower = 0;
		seconds = 0;

		start = false;
		paused = false;
		doubleSpeed = false;
	
		this.boardNumber = BoardNumber;
		this.boardMat = boardMat;
		this.lives = 3;
		stage = 1;
		
		
		
		
		//set window controllers 	
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// create game timer
		leadTimer = GameTimer.getInstace();
		
		// create first stage board
		board = new Board(this.boardMat, stage, this, boardNumber, lives, leadTimer);
		contentPane.add(board);
		board.setVisible(false);
		board.setLayout(null);

		// GAME OVER label
		lblGameOver = new JLabel("GAME OVER");
		lblGameOver.setToolTipText("");
		lblGameOver.setForeground(Color.WHITE);
		lblGameOver.setFont(new Font("Verdana Pro Black", Font.BOLD, 37));
		lblGameOver.setBackground(new Color(0, 0, 0));
		lblGameOver.setBounds(307, 176, 345, 113);
		lblGameOver.setVisible(false);
		contentPane.add(lblGameOver);
		lblGameOver.setRequestFocusEnabled(true);
		
		// GET READY label
		JLabel lblGetReady = new JLabel("GET READY");
		lblGetReady.setToolTipText("");
		lblGetReady.setForeground(Color.WHITE);
		lblGetReady.setFont(new Font("Verdana Pro Black", Font.BOLD, 37));
		lblGetReady.setBackground(new Color(0, 0, 0));
		lblGetReady.setBounds(307, 176, 274, 113);
		contentPane.add(lblGetReady);
		lblGetReady.setRequestFocusEnabled(true);
		isMuted = false;
		
		this.addKeyListener(new KeyListener() {


			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
			
			// manage the key pressed option
			@Override
			public void keyPressed(KeyEvent e) {

				// speed up case
				if (e.getKeyCode() == KeyEvent.VK_S) {
					if(!doubleSpeed) {
						leadTimer.doubleSpeed();
						doubleSpeed = true;
					}
					else {
						leadTimer.regularSpeed();
						doubleSpeed = false;
					}
				}
				
				// pause case
				if (e.getKeyCode() == KeyEvent.VK_P) {
					if(!paused) {
						leadTimer.stopTimer();
						board.getPacman().setFreezed(true);
						paused = true;
					}
					else {
						leadTimer.startTimer();
						board.getPacman().setFreezed(false);
						paused = false;
					}
				}
				
				// mute case
				if (e.getKeyCode() == KeyEvent.VK_M) {
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

					}
					else { //mute
						// stop audio
						clip.close();
						isMuted = true;
					}
					
				}
				
				// exit to main menu case
				if (e.getKeyCode() == KeyEvent.VK_E) {
					 
					setVisible(false);
					MainMenu main = new MainMenu();
					main.setVisible(true);
					dispose();
				}
				
				// continue case
				if (e.getKeyCode() == KeyEvent.VK_SPACE){
					if (start == false) {
						lblGetReady.setVisible(false);
						start = true;
						leadTimer.startTimer();
						board.setVisible(true);
						
						// start audio
						try {		
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);
							clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.loop(clip.LOOP_CONTINUOUSLY);
							clip.start();
							isMuted = false;
						}
						catch (Exception e1) {
							// TODO: handle exception
						}	
					}
					else if(GameOver == true)
					{
						lblGameOver.setVisible(false);
						setVisible(false);
						new FinishGame(score, numPineapple, numApple, numStrawberry, lblTime.getText(), false);
						dispose();
					}
				}
				board.keyPressed(e); 
			}
		});

		
	

		JLabel lblMute = new JLabel("'M'-Mute");
		lblMute.setForeground(Color.WHITE);
		lblMute.setBounds(16, 52, 59, 14);
		contentPane.add(lblMute);
		
		JLabel lblSpeed = new JLabel("'S'-Speed");
		lblSpeed.setForeground(Color.WHITE);
		lblSpeed.setBounds(16, 77, 65, 30);
		contentPane.add(lblSpeed);
		
		JLabel lblPause = new JLabel("'P'-Pause");
		lblPause.setForeground(Color.WHITE);
		lblPause.setBounds(16, 116, 59, 20);
		contentPane.add(lblPause);

		JLabel lblScore = new JLabel("Score:");
		lblScore.setFont(new Font("Pristina", Font.BOLD, 29));
		lblScore.setForeground(Color.WHITE);
		lblScore.setBounds(10, 433, 80, 40);
		contentPane.add(lblScore);

		lblScoreNum = new JLabel(score.toString());
		lblScoreNum.setFont(new Font("Pristina", Font.BOLD, 29));
		lblScoreNum.setForeground(Color.WHITE);
		lblScoreNum.setBounds(35, 458, 80, 40);
		contentPane.add(lblScoreNum);

		JLabel lblStage = new JLabel("Stage:");
		lblStage.setFont(new Font("Pristina", Font.BOLD, 29));
		lblStage.setForeground(Color.WHITE);
		lblStage.setBounds(10, 354, 80, 40);
		contentPane.add(lblStage);

		lblStageNum = new JLabel(stage.toString());
		lblStageNum.setFont(new Font("Pristina", Font.BOLD, 29));
		lblStageNum.setForeground(Color.WHITE);
		lblStageNum.setBounds(35, 389, 80, 40);
		contentPane.add(lblStageNum);
		
		JLabel lblExit = new JLabel("'E'-Exit");
		lblExit.setForeground(Color.WHITE);
		lblExit.setBounds(16, 11, 53, 30);
		contentPane.add(lblExit);

		JLabel lblFruits = new JLabel("Fruits:");
		lblFruits.setForeground(Color.WHITE);
		lblFruits.setFont(new Font("Pristina", Font.BOLD, 29));
		lblFruits.setBounds(10, 489, 80, 40);
		contentPane.add(lblFruits);
		
		lblFruitsNum = new JLabel("0");
		lblFruitsNum.setForeground(Color.WHITE);
		lblFruitsNum.setFont(new Font("Pristina", Font.BOLD, 29));
		lblFruitsNum.setBounds(35, 522, 80, 40);
		contentPane.add(lblFruitsNum);
		
		
	    lblTime = new JLabel("00:00:00");
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Pristina", Font.BOLD, 29));
		lblTime.setBounds(2, 596, 115, 40);
		contentPane.add(lblTime);
		
		
		setPacmanLivesImg();
		leadTimer.stopTimer();
		

	}

	public void setBoard(Board b)
	{
		this.board = b;
		
		getContentPane().add(board);
		contentPane.add(board);
		board.setLayout(null);
		repaint();
	}

	private void setPacmanLivesImg() {

		JLabel lblLifeT = new JLabel("Life:");
		lblLifeT.setFont(new Font("Pristina", Font.BOLD, 29));
		lblLifeT.setForeground(Color.WHITE);
		lblLifeT.setBounds(16, 249, 80, 40);
		contentPane.add(lblLifeT);

		ImageIcon lifeImg = new ImageIcon(Game.class.getResource("/pics/pacmanRight.png"));
		Image resizedLifeImg = lifeImg.getImage();

		lblLife1 = new JLabel("");
		lblLife1.setBounds(10, 300, 20, 20);

		resizedLifeImg = resizedLifeImg.getScaledInstance(lblLife1.getWidth(), lblLife1.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		lifeImg = new ImageIcon(resizedLifeImg);

		lblLife1.setIcon(lifeImg);
		contentPane.add(lblLife1);	

		lblLife2 = new JLabel("");
		lblLife2.setBounds(35, 300, 20, 20);

		lblLife2.setIcon(lifeImg);
		contentPane.add(lblLife2);

		lblLife3 = new JLabel("");
		lblLife3.setBounds(60, 300, 20, 20);

		lblLife3.setIcon(lifeImg);
		contentPane.add(lblLife3);
	
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setSeconds(int secs) {
		seconds = secs;
	}

	public void eatPineapple() {
		numPineapple++;
	}

	public void eatApple() {
		numApple++;
	}

	public void eatStrawberry() {
		numStrawberry++;
	}

	public void eatPill() {
		numPill++;
	}

	public void eatPower() {
		numPower++;
	}

	public void setScore(int delta) {
		score = score+delta;
		lblScoreNum.setText(score.toString());
	}
	
	public void score() {
		score =  numPill * 10 + numPower*50 + numPineapple*100 + numApple*200 + numStrawberry*300; 
		lblScoreNum.setText(score.toString());
		Integer sumFruits = numPineapple+numApple+numStrawberry;
		lblFruitsNum.setText(sumFruits.toString());
	}


	public void setNumOfLives(int lives) {
		if (lives != this.lives) { 
			this.lives = lives;
			generateLives();
		}
	}

	// pacman lost one of his lives
	private void generateLives() {

		switch (this.lives) {
		case 2:
			contentPane.remove(lblLife3);
			break;
		case 1:
			contentPane.remove(lblLife2);
			break;
		case 0:
			contentPane.remove(lblLife1);
			gameOver();
			break;
		}
		repaint();
	}

	public GameTimer getLeadTimer() {
		return leadTimer;
	}
	public int getStage() {
		return stage;
	}

	public int getBoardNumber() {
		return boardNumber;
	}

	public void stageUp() {
		stage++;
		lblStageNum.setText(stage.toString());
		repaint();
	}
	
	public void gameWon() {
		// win!
		clip.close();
		String time = lblTime.getText();
		leadTimer.stopTimer();
		leadTimer.clearListeners();
		setVisible(false);
		FinishGame finish = new FinishGame(this.score,this.numPineapple, this.numApple, this.numStrawberry, time, true);
		finish.setVisible(true);
		dispose();	
	
	}
	
	public void gameOver() {
		// lose
		clip.close();
		leadTimer.stopTimer();
		leadTimer.clearListeners();
		board.setVisible(false);
		lblGameOver.setVisible(true);
		GameOver = true;
	}
	
	
	public void setTime(String time) {
		this.lblTime.setText(time);
		contentPane.add(lblTime);
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}

}
