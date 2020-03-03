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

public class FLINKEY extends Ghost  implements ActionListener{

	private int delay;
	private boolean arrivedToCorner;
	private int walkHelper;

	public FLINKEY(int[][] maze, Board board, GameTimer leadTimer) {

		super(16, 16, maze, board, leadTimer);
		this.leadTimer.addListener(this);
		delay = 180;
		arrivedToCorner = false;
		walkHelper = 12;
	}

	@Override
	public void action() {

		if (!arrivedToCorner) { //flinkey goes to top right corner
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
					walkHelper = 12;
				}
				else {
					walkHelper--;
				}
			}
		}
		else{ //flinkey wonder around
			if (delay !=0) {
				delay--;
			}
			else
			{
				if (walkHelper == 0)
				{
					int rand = (int)(Math.random()*4);
					if(rand == 0) {
						Move("Right","Up");
					}
					else if (rand == 1){
						Move("Up", "Left");
					}
					else if (rand == 2) {
						Move("Left","Down");
					}
					else if (rand == 3) {
						Move("Down","Right");
					}
					walkHelper = 12;
				}
				else {
					walkHelper--;
				}

			}
		}
		maze[R][C] = 13;
		
		// flinkey hit pacman
		if (board.getPacman().getCol() == C & board.getPacman().getRow() == R) {
			crashWith(board.getPacman());

			// handle pacman lives
			this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
		}

		board.repaint();

	}



	private void arrivedToCorner() {
		if ((R == 0 & C == 31) | (R == 1 & C == 30)) {
			arrivedToCorner = true;
			delay = 20;
		}
	}



	// flinkey meet pacman cases:
	
	public void visit(NicePacman visited) {
		// pacman dies
		visited.die();
	}

	public void visit(ProtectedPacman visited) {
		// pacman dies
		visited.die();
	}

	public void visit(GrumpyPacman visited) {
		// pacman dies
		visited.die();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
