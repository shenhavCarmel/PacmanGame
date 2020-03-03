package Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.*;

import GUI_Windows.Board;
import GUI_Windows.Game;

public class GameManager implements KeyListener, TimerListener{

	final static int BOARD_SIZE = 640;
	final static int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

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


	private int lives;
	private int cageDelay;
	private int timeHelper;

	private Pacman pacman;
	private int[][] mazeMat;
	private Game g; 
	private Board board;
	private GameTimer leadTimer;

	public GameManager(Board board, int[][] mazeMatrix, Pacman pacman, Game g, int lives, GameTimer leadTimer) {

		this.g = g;
		this.board = board;
		this.mazeMat = mazeMatrix;
		this.pacman = pacman;
		this.lives = lives;
		this.leadTimer = leadTimer;
		this.leadTimer.addListener(this);
		cageDelay = 260;
		timeHelper = 10;
	}


	public void setLives(int numOfLives) {
		this.lives =  numOfLives;
		this.g.setNumOfLives(this.lives);
	}

	public int getLives() {
		return lives;
	}

	
	// manage the key pressed options to move the pacman through the board
	public void keyPressed(KeyEvent e) {
		if (!pacman.isFreezed()) { // lock arrow keys when pacman is freezed
			int r = pacman.getRow();
			int c = pacman.getCol();

			//	left case
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
				if (isLegalMove(r, c-1)) {
					pacman.moveLeft();
					editScore(r, c-1);
					mazeMat[r][c] = PATH;
					mazeMat[r][c-1] = PACMAN;
				}
			}
			
			// right case
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				if (isLegalMove(r, c+1)) {
					pacman.moveRight();
					editScore(r, c+1);
					mazeMat[r][c] = PATH;
					mazeMat[r][c+1] = PACMAN;
				}
			}
			
			// up case
			else if (e.getKeyCode() == KeyEvent.VK_UP){
				if (isLegalMove(r-1, c)) {
					pacman.moveUp();
					editScore(r-1, c);
					mazeMat[r][c] = PATH;
					mazeMat[r-1][c] = PACMAN;
				}
			}
			
			// down case
			else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				if (isLegalMove(r+1, c)) {
					pacman.moveDown();
					editScore(r+1, c);
					mazeMat[r][c] = PATH;
					mazeMat[r+1][c] = PACMAN;
				}
			}
			
			// check if pacman met any of the ghosts
			for (Ghost currGhost : this.board.getGhosts()) {
				// pacman did meet a ghost
				if (board.getPacman().getCol() == currGhost.getCol() & board.getPacman().getRow() == currGhost.getRow()) {
					currGhost.crashWith(board.getPacman());

					// handle pacman lives
					setLives(this.board.getPacman().getNumOfLives());
				}
			}

			// check if the level is done
			if (isFinishedStage()) {
				// go to next stage
				nextStage();
			}
		}

	}

	// check if pacman ate food and edit the score accordingly
	public void editScore(int row, int col) {

		int n = mazeMat[row][col];

		switch(n) {

		case FOOD:
			g.eatPill();
			break;

		case STRAWBERRY:
			g.eatStrawberry();
			break;

		case POWER:
			g.eatPower();
			break;

		case PINEAPPLE:
			g.eatPineapple();
			break;

		case APPLE:
			g.eatApple();
			break;

		}
		g.score();
	}

	// check if pacman ate all the pills in the board
	public boolean isFinishedStage() {
		return (countFood() == 0);
	}

	// go to next stage
	public void nextStage() {
		if (g.getStage() < 3) {

			leadTimer.clearListeners();
			leadTimer.stopTimer();
			pacman.setRow(12);
			pacman.setCol(15);
			mazeMat[12][15] = 6;
			g.stageUp();
			board = new Board(mazeMat,board.getStage()+1,g,g.getBoardNumber(), lives, leadTimer);
			pacman = board.getPacman();
			g.setBoard(board);
			leadTimer.startTimer();
		}
		else {
			g.gameWon();
		}

	}


	public void reduce10points() {
		g.setScore(-10);
	}

	// check if the move is legal
	private boolean isLegalMove(int toRow, int toCol) {

		if ((toRow>=0) & (toRow < 32) & (toCol >= 0) & (toCol < 32)) {
			return(mazeMat[toRow][toCol] != BRICK) & (mazeMat[toRow][toCol] != FENCE);
		}
		return false;

	}

	// count and return the pills on the board
	public int countFood() {
		int count = 0;
		for (int i=0; i<mazeMat.length; i++) {
			for (int j=0; j<mazeMat.length; j++) {
				if (mazeMat[i][j] == FOOD)
					count++;
			}
		}
		return count;
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void action() {

		// close gate after all ghosts left
		if (cageDelay == 0)
		{
			mazeMat[13][15] = 1;
			mazeMat[13][16] = 1;
		}
		else
		{
			cageDelay--;
		}


		// set the time at the info display
		if (timeHelper == 0) {
			Integer seconds = g.getSeconds();
			seconds++;
			g.setSeconds(seconds);
			Integer hours = seconds / 3600;
			Integer mins = (seconds % 3600)/60;
			String hour = hours.toString(); 
			String min = mins.toString();
			String sec = seconds.toString();
			hour = Check(hour);
			min = Check(min);
			sec = Check(sec);
			g.setTime(hour+":"+min+":"+sec);
			timeHelper = 10;
		}
		else {
			timeHelper--;
		}

	}


	private String Check(String t) {
		if (t.length() == 1) {
			t = "0" + t;
		}
		return t;
	}


}