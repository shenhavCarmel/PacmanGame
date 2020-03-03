package Logic;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import Board.Visited;
import Board.Visitor;
import GUI_Windows.Board;

public abstract class Pacman implements Visited, ActionListener, TimerListener {

	private int numLives;
	protected int row;
	protected int col;
	private int state;
	private boolean isAlive;
	private ImageIcon img;
	private final int BOUND = 32;
	protected Board board;
	protected int[][] maze;
	private boolean freezed;
	protected GameTimer leadTimer;
	
	public Pacman(int row, int col, int state, int numLives, Board board, int[][] maze, GameTimer leadTimer) {
		this.leadTimer = leadTimer;
		this.leadTimer.addListener(this);
		this.isAlive = numLives > 0;
		this.numLives = numLives;
		this.row = row;
		this.col = col;
		this.state = state;
		this.img = getImgOfPacman("Left");
		this.board = board;
		this.maze = maze;
		this.freezed = false;


	}

	// get the image of pacman according to the given side
	private ImageIcon getImgOfPacman(String side) {
		switch (state){
		case 1:
			return new ImageIcon(Pacman.class.getResource("/pics/pacman"+side+".png"));
		case 2:
			return new ImageIcon(Pacman.class.getResource("/pics/bluePacman"+side+".png"));
		case 3: 
			return new ImageIcon(Pacman.class.getResource("/pics/redPacman"+side+".jpg"));
		}
		return null;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int r) {
		this.row = r;
	}

	public void setCol(int c) {
		this.col = c;
	}

	public int getNumOfLives() {
		return this.numLives;
	}

	public Image getImg() {
		return this.img.getImage();
	}

	public int getCol() {
		return col;
	}

	public boolean isFreezed() {
		return freezed;
	}
	
	public void setFreezed(boolean isFreezed) {
		this.freezed = isFreezed;
	}
	
	public void moveUp() {
		this.row = row-1;
		this.img = getImgOfPacman("Up");
	}

	public void moveDown() {
		this.row = row+1;
		this.img = getImgOfPacman("Down");
	}

	public void moveLeft() {

		this.col = col-1;
		this.img = getImgOfPacman("Left");
	}

	public void moveRight() {
		this.col = col+1;
		this.img = getImgOfPacman("Right");
	}

	// pacman dies and goes to the start place
	public void die() {
		row = 12;
		col = 15;
		this.numLives --;
		if (numLives == 0) { // pacman dies without a revive
			finishLives();
		}
		maze[12][15] = 6;
		board.repaint();
	}

	
	public void finishLives() {
		this.isAlive = false;
		leadTimer.RemoveListener(this);
		this.maze[row][col]=2;
		board.repaint();	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
