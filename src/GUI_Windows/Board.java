package GUI_Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;	
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Board.fruits.Apple;
import Board.fruits.Pineapple;
import Board.fruits.Strawberry;
import Board.ghostsCharacters.BLINKEY;
import Board.ghostsCharacters.FLINKEY;
import Board.ghostsCharacters.GINKEY;
import Board.ghostsCharacters.INKEY;
import Board.ghostsCharacters.STINKEY;
import Board.pacmanCharacters.GrumpyPacman;
import Board.pacmanCharacters.NicePacman;
import Board.pacmanCharacters.ProtectedPacman;
import Logic.GameManager;
import Logic.GameTimer;
import Logic.Pacman;
import Logic.Ghost;
	
public class Board extends JPanel {

	private int[][] mazeMatrix;
	private GameManager gameManager; 
	private Pacman pacman;
	private GINKEY ginkey;
	private BLINKEY blinkey;
	private INKEY inkey;
	private FLINKEY flinkey;
	private STINKEY stinkey;
	private int stage;
	private Integer boardNum;
	private LinkedList<Ghost> ghostsList;
	private GameTimer leadTimer;
	
	public Board(int[][] boardMat, int currStage, Game g, int boardNumber, int lives, GameTimer leadTimer) {

		this.boardNum = boardNumber;
		this.leadTimer = leadTimer;
		stage = currStage;
		
		// create a ghosts list
		this.ghostsList = new LinkedList<>();
		this.ginkey = new GINKEY(boardMat, this, leadTimer);
		ghostsList.add(ginkey);
		this.flinkey = new FLINKEY(boardMat, this, leadTimer);
		ghostsList.add(flinkey);
		this.stinkey = new STINKEY(boardMat, this, leadTimer);
		ghostsList.add(stinkey);
		
		// add inkey and blinkey by the level
		if (stage > 1) {
			this.inkey = new INKEY(boardMat, this, leadTimer);
			ghostsList.add(inkey);
		}
		if (stage > 2) {
			this.blinkey = new BLINKEY(boardMat, this, leadTimer);
			ghostsList.add(blinkey);
		}

		this.mazeMatrix = boardMat;
		//scan the basic board from the csv file
		scanCSV(); 
		FruitsToBoard();
		this.setBounds(108, 22, 640, 640);
		initPacman(lives);
		this.gameManager = new GameManager(this, mazeMatrix, this.pacman, g, lives, leadTimer);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void keyPressed(KeyEvent e) {
		gameManager.keyPressed(e);
		this.repaint();
	}

	public LinkedList<Ghost> getGhosts() {
		return this.ghostsList;
	}

	// build pacman according to the level
	private void initPacman(int lives) {
		switch (this.stage) {
		case 1:
			this.pacman = new NicePacman(12, 15, lives, this, mazeMatrix, leadTimer);
			break;
		case 2:
			this.pacman = new ProtectedPacman(12, 15, lives, this, mazeMatrix, leadTimer);
			break;
		case 3:
			this.pacman = new GrumpyPacman(12, 15, lives, this, mazeMatrix, leadTimer);
			break;
		}
	}

	// loads the arangment from the csv file
	private void scanCSV() {

		File file = new File("maze"+boardNum.toString()+".csv");
		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {

				// init curr board according to the csv file
				for (int i = 0; i < 32; i++) {
					String[] str = scanner.nextLine().split(",");
					stringArrayToInt(str, i);
				}
			}

			scanner.close();
		} 

		// file doesn't exist
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "CSV file not found", "Error", JOptionPane.WARNING_MESSAGE);	
		}
	}


	private void stringArrayToInt(String[] strs, int row) {
		for (int j=0; j < strs.length; j++) {
			mazeMatrix[row][j] = Integer.parseInt(strs[j]);
		}
	}

	public void reduce10points() {
		gameManager.reduce10points();
	}

	public Pacman getPacman() {
		return pacman;
	}

	public int getStage() {
		return stage;
	}

	// create the fruits of the stage
	public void FruitsToBoard() {
		new Apple(stage,mazeMatrix, leadTimer);
		new Pineapple(stage, mazeMatrix, leadTimer);
		new Strawberry(stage, mazeMatrix, leadTimer);

	}

	public GameManager getGameManager()	{
		return this.gameManager;
	}

	// paint the board according to the maze matrix
	public void paint(Graphics g) {

		final int BRICK = 1;
		final int PATH = 2;
		final int FOOD = 3;
		final int STRAWBERRY = 4;
		final int FENCE = 5;
		final int PACMAN = 6;
		final int POWER = 7;
		final int PINEAPPLE = 8;
		final int APPLE = 9;
		final int GHOST_GINKEY = 10;
		final int GHOST_INKEY = 11;
		final int GHOST_BLINKEY = 12;
		final int GHOST_FLINKEY = 13;
		final int GHOST_STINKEY = 14;
		final int WATERSPLASH = 15;
		final int FIREBALL = 16;

		super.paint(g);
		for (int i=0; i< mazeMatrix.length; i++) {
			for (int j=0; j<mazeMatrix.length; j++) {

				switch (mazeMatrix[i][j]){

				case BRICK: 
					g.setColor(Color.BLUE);
					g.fillRect(j * 20, i * 20, 20, 20);
					g.setColor(Color.BLUE);
					break;

				case PATH:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);
					g.setColor(Color.BLACK);
					break;

				case FENCE:
					g.setColor(Color.WHITE);
					g.fillRect(j * 20, i * 20, 20, 20);
					g.setColor(Color.WHITE);
					break;

				case FOOD:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon food = new ImageIcon(Board.class.getResource("/pics/pills.png"));

					g.drawImage(food.getImage(), j*20 + 6, i * 20 + 6, 8, 8, this);
					break;

				case PACMAN:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);
					g.drawImage(this.pacman.getImg(), j*20+3, i * 20+3, 15, 15, this);
					break;

				case POWER:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon power = new ImageIcon(Board.class.getResource("/pics/power.png"));  

					g.drawImage(power.getImage(), j*20, i * 20, 15, 15, this);
					break;

				case STRAWBERRY: 
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon strawberry = new ImageIcon(Board.class.getResource("/pics/straberry.png"));  

					g.drawImage(strawberry.getImage(), j*20 + 2, i * 20 + 2, 15, 15, this);
					break;

				case PINEAPPLE:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon pineapple = new ImageIcon(Board.class.getResource("/pics/pineapple.png"));  

					g.drawImage(pineapple.getImage(), j*20 + 2, i * 20 + 2, 15, 15, this);
					break;

				case APPLE:
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon apple = new ImageIcon(Board.class.getResource("/pics/apple.png"));  

					g.drawImage(apple.getImage(), j*20 + 2, i * 20 + 2, 15, 15, this);
					break;


				case GHOST_BLINKEY:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon ghost_blinkey = new ImageIcon(Board.class.getResource("/pics/red_ghost.png"));  

					g.drawImage(ghost_blinkey.getImage(), j*20, i * 20, 15, 15, this);
					break;


				case GHOST_GINKEY:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon ghost_ginkey = new ImageIcon(Board.class.getResource("/pics/green_g.png"));  

					g.drawImage(ghost_ginkey.getImage(), j*20, i * 20, 15, 15, this);
					break;


				case GHOST_INKEY:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon ghost_inkey = new ImageIcon(Board.class.getResource("/pics/yellow_g.png"));  

					g.drawImage(ghost_inkey.getImage(), j*20, i * 20, 15, 15, this);
					break;

				case GHOST_FLINKEY:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon ghost_flinkey = new ImageIcon(Board.class.getResource("/pics/purpleGhost.png"));  

					g.drawImage(ghost_flinkey.getImage(), j*20+2, i * 20, 15, 15, this);
					break;

				case GHOST_STINKEY:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon ghost_stinkey = new ImageIcon(Board.class.getResource("/pics/whiteGhost.png"));  

					g.drawImage(ghost_stinkey.getImage(), j*20+2, i * 20, 15, 15, this);
					break;

				case WATERSPLASH:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon watersplash = new ImageIcon(Board.class.getResource("/pics/watersplash.jpg"));  

					g.drawImage(watersplash.getImage(), j*20+2, i * 20, 15, 15, this);
					break;

				case FIREBALL:			
					g.setColor(Color.BLACK);
					g.fillRect(j * 20, i * 20, 20, 20);

					ImageIcon fireball = new ImageIcon(Board.class.getResource("/pics/fireball.png"));  

					g.drawImage(fireball.getImage(), j*20+2, i * 20, 15, 15, this);
					break;

				}

			}
		}
	}

}

