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

public class FIREBALL extends Ghost  implements ActionListener{


	private boolean arrivedToBorder;
	private int moveHelper;
	private String direction;
	private boolean arrivedToPacman;
	

	public FIREBALL(int[][] maze, Board board, GameTimer leadTimer, int startRow, int startCol, String direction) {

		super(startRow,startCol, maze, board, leadTimer);

		this.leadTimer.addListener(this);
		moveHelper = 1;
		arrivedToBorder = false;
		arrivedToPacman = false;
		this.direction = direction;
	}

	@Override
	public void action() {

		if (!arrivedToPacman & !arrivedToBorder) { // fire moves until hits pacman or a border
			if (moveHelper == 0) {
				switch (direction) {

				case "Right":
					if (C < 31) {
						this.replaceLastElement(R, C);
						currElement = maze[R][C+1];
						C++;
					}
					else {
						arrivedToBorder = true;
					}
					break;

				case "Left":
					if (C > 0) {
						this.replaceLastElement(R, C);
						currElement = maze[R][C-1];
						C--;
					}
					else {
						arrivedToBorder = true;
					}
					break;

				case "Up":
					if (R > 0) {
						this.replaceLastElement(R, C);
						currElement = maze[R-1][C];
						R--;
					}
					else {
						arrivedToBorder = true;
					}
					break;

				case "Down":
					if (R < 31) {
						this.replaceLastElement(R, C);
						currElement = maze[R+1][C];
						R++;
					}
					else {
						arrivedToBorder = true;
					}
					break;
				}
				moveHelper = 1;
				maze[R][C] = 16;

				if (!arrivedToBorder) {
					//hit pacman
					if (board.getPacman().getCol() == C & board.getPacman().getRow() == R) { 
						arrivedToPacman = true;
						crashWith(board.getPacman());

						// handle pacman lives
						this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
					}
				}
				else {
					maze[R][C] = currElement;
					leadTimer.RemoveListener(this);
				}
			}
			else {
				moveHelper--;
			}
			board.repaint();
		}

	}



	public boolean getIsArrivedToBorder() {
		return arrivedToBorder;
	}

	// fireball hit grumpy pacman
	@Override
	public void visit(GrumpyPacman visited) {
		// pacman dies
		visited.die();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
