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

public class GINKEY extends Ghost  implements ActionListener{

	private int delay;
	private int walkHelper;
	private boolean arrivedToCorner;
	private boolean timedOut;
	private int saveR;
	private int saveC;
	private int timeOut;
	private boolean alive;

	public GINKEY(int[][] maze, Board board, GameTimer leadTimer) {

		super(15, 15, maze, board, leadTimer);
		this.leadTimer.addListener(this);
		delay = 70;
		arrivedToCorner = false;
		walkHelper = 4;
		timeOut = 0;
		timedOut = false;
		alive = true;
		
	}

	
	public void action() {
		if (!timedOut)
		{
			if (!arrivedToCorner) { //ginkey goes to top lefts corner
				if (delay !=0) {
					delay--;
				}
				else {
					if(walkHelper == 0) {
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
						walkHelper = 4;
					}
					else
					{
						walkHelper --;
					}
				}
			}
			else { //ginkey hunts pacman
				if (delay !=0) {
					delay--;
				}
				else if (walkHelper == 0){
					int rPac = board.getPacman().getRow();
					int cPac = board.getPacman().getCol();
					Chase(rPac,cPac);
					walkHelper = 4;
				}
				else {
					walkHelper--;
				}
			}
			maze[R][C] = 10;
			
			// ginkey meet a pacman
			if (board.getPacman().getCol() == C & board.getPacman().getRow() == R) {
				crashWith(board.getPacman());

				// handle pacman lives
				this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
			}
			
			board.repaint();
		}
		else { // ginkey is in time out
			timeOut--;
			if (timeOut == 0) {
				Show();
			}
		}


	}



	private void arrivedToCorner() {
		if ((R==0 & C==0) | (R==1 & C==1)) {
			arrivedToCorner = true;
			delay = 20;
		}
	}

	// ginkey meet pacman cases:
	
	public void visit(NicePacman visited) {
		// pacman dies
		visited.die();
	}
	

	public void visit(ProtectedPacman visited) {
		// ginkey disappear for 5 sec
		if (!timedOut) {
			Hide();
		}
	}
	
	public void visit(GrumpyPacman visited) {
		// ginkey dies
		if (alive) {
			leadTimer.RemoveListener(this);
			this.maze[R][C]=6;
			board.repaint();
			alive = false;
		}
	}

	// ginkey timed out by protected
	public void Hide() {
		maze[R][C] = 2;
		saveR = R;
		saveC = C;
		timeOut = 50;
		timedOut = true;
	}

	// time out finished
	public void Show() {
		maze[saveR][saveC] = 10;
		timedOut = false;
	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}




	

	

}
