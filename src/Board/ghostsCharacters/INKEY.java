package Board.ghostsCharacters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Board.Visited;
import Board.pacmanCharacters.GrumpyPacman;
import Board.pacmanCharacters.NicePacman;
import Board.pacmanCharacters.ProtectedPacman;
import GUI_Windows.Board;
import Logic.GameTimer;
import Logic.Ghost;

public class INKEY extends Ghost  implements ActionListener{

	private int delay;
	private int walkHelper;
	private boolean freezed;
	private int timeOut;
	private boolean arrivedToCorner;
	private int reload;
	private boolean isAttacking;
	private WATERSPLASH watersplash;

	public INKEY(int[][] maze, Board board, GameTimer leadTimer) {

		super(17, 15, maze, board, leadTimer);
		this.leadTimer.addListener(this);
		delay = 150; 
		arrivedToCorner = false;
		walkHelper = 8;
		freezed = false;
		timeOut = 0;
		reload = 50;
		isAttacking = false;
	}

	@Override
	public void action() {
		
		int toShow = 11;
		
		if (!freezed) {
			if (!arrivedToCorner) { //INKEY goes to top right corner
				if (delay !=0) {
					delay--;
				}
				else {
					if (walkHelper == 0) {
						if (R > 0 | C < 31) {
							if (canUp()) {
								this.replaceLastElement(R, C);
								currElement = maze[R-1][C];
								R--;
							}
							else if (canRight()) {
								this.replaceLastElement(R, C);
								currElement = maze[R][C+1];
								C++;
							}
						}
						arrivedToCorner();
						walkHelper = 8;
					}
					else {
						walkHelper--;
					}
				}
			}
			else { //ginkey hunts pacman

				// move toward pacman
				if (delay !=0) {
					delay--;
				}
				else if (walkHelper == 0){
					int rPac = board.getPacman().getRow();
					int cPac = board.getPacman().getCol();
					Chase(rPac,cPac);
					walkHelper = 8;
				}
				else {
					walkHelper--;
				}

				
				if (reload == 0){ // inkey finished to reload a watersplash
					waterAttack();
				}
				else if(isAttacking){  // water splash still out
					if (watersplash.getIsArrivedToBrick()){ // check if water attack has ended
						isAttacking = false;
					}
				}
				else { // inkey reloading
					if (reload % 5 == 0) {
						toShow = 2;
					}
					reload--;
				}

			}
			maze[R][C] = toShow;

			// inkey meets a pacman
			if (board.getPacman().getCol() == this.getCol() & board.getPacman().getRow() == this.getRow()) {
				crashWith(board.getPacman());

				// handle pacman lives
				this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
			}
		}
		else { // inkey is freezed
			timeOut--;
			maze[R][C] = 11;
			if (timeOut == 0) {
				freezed = false;
			}
		}
		board.repaint();
	}


	private void arrivedToCorner() {
		if ((R==0 & C==31) | (R==1 & C==30)) {
			arrivedToCorner = true;
			delay = 20;
		}
	}

	 // inkey create water attack
	private void waterAttack() {
			reload = 50;
			isAttacking = true;
			delay = 10;

			switch (lastMoveDirection){
			case "Right":
				watersplash = new WATERSPLASH(maze, board, leadTimer, R, C++, lastMoveDirection);
				break;
			case "Left":
				watersplash = new WATERSPLASH(maze, board, leadTimer, R, C++, lastMoveDirection);
				break;
			case "Up":
				watersplash = new WATERSPLASH(maze, board, leadTimer, R--, C , lastMoveDirection);
				break;
			case "Down":
				watersplash = new WATERSPLASH(maze, board, leadTimer, R++, C, lastMoveDirection);
				break;
			}
	}



	@Override
	public void visit(ProtectedPacman visited) {
		// pacman freezes for 3 secs and loses 10 points
		visited.freez();
	}

	@Override
	public void visit(GrumpyPacman visited) {
		// inkey freezes for 5 secs
		freezInkey();
	}

	// inkey freezes
	public void freezInkey() {
		maze[R][C] = 11;
		timeOut = 50;
		freezed = true;
		board.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NicePacman visited) {
		// TODO Auto-generated method stub

	}
}
