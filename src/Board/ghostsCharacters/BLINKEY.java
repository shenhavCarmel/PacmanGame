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

public class BLINKEY extends Ghost  implements ActionListener{

	private int delay;
	private boolean arrivedToCorner;
	private int walkHelper;
	private int reload;
	private boolean isAttacking;
	private FIREBALL fireball;
	
	public BLINKEY(int[][] maze, Board board,GameTimer leadTimer) {

		super(17, 16, maze, board, leadTimer);
		this.leadTimer.addListener(this);
		delay = 200;
		arrivedToCorner = false;
		walkHelper = 8;
		reload = 50;
		isAttacking = false;
	}

	
	// manage blinkey state at a timer tick
	@Override
	public void action() {

		int toShow = 12;
		
		if (!arrivedToCorner) { //BLINKEY goes to top left corner
			if (delay !=0) {
				delay--;
			}
			else {
				if (walkHelper == 0) {
					if (R > 0 | C > 0) {
						if (canUp()) {
							this.replaceLastElement(R, C);
							currElement = maze[R-1][C];
							R--;
						}
						else if (canLeft()) {
							this.replaceLastElement(R, C);
							currElement = maze[R][C-1];
							C--;
						}
					}
					arrivedToCorner();
					walkHelper = 12;
				}
				else {
					walkHelper--;
				}
			}
		}
		else { //blinkey hunts pacman
			if (delay !=0) {
				delay--;
			}
			else if (walkHelper == 0){
				int rPac = board.getPacman().getRow();
				int cPac = board.getPacman().getCol();
				Chase(rPac,cPac);
				walkHelper = 12;
			}
			else {
				walkHelper--;
			}
			
			// fire attack
			if (reload == 0){
				fireAttack();
			}
			else if(isAttacking){
				if (fireball.getIsArrivedToBorder()){ // fire attack has ended
					isAttacking = false;
				}
			}
			else {
				if (reload % 5 == 0) {
					toShow = 2;
				}
				reload--;
			}

		}
		maze[R][C] = toShow;

		// blinkey met pacman
		if (board.getPacman().getCol() == C & board.getPacman().getRow() == R) {
			crashWith(board.getPacman());

			// handle pacman lives
			this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
		}

		board.repaint();

	}


	
	private void arrivedToCorner() {
		if ((R==0 & C==0) | (R==1 & C==1)) {
			arrivedToCorner = true;
			delay = 20;
		}
	}

	// blinkey creates fire attack
	private void fireAttack() {
	 
			reload = 50;
			isAttacking = true;
			delay = 10;
			
			switch (lastMoveDirection){
			case "Right":
				fireball = new FIREBALL(maze, board, leadTimer, R, C++, lastMoveDirection);
				break;
			case "Left":
				fireball = new FIREBALL(maze, board, leadTimer, R, C--, lastMoveDirection);
				break;
			case "Up":
				fireball = new FIREBALL(maze, board, leadTimer, R--, C , lastMoveDirection);
				break;
			case "Down":
				fireball = new FIREBALL(maze, board, leadTimer, R++, C, lastMoveDirection);
				break;
			}
	}

	
	
	// blinkey meets grumpy pacman
	public void visit(GrumpyPacman visited) {
		// pacman dies
		visited.die();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NicePacman visited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ProtectedPacman visited) {
		// TODO Auto-generated method stub
		
	}
}
