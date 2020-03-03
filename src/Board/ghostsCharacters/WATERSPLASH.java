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

public class WATERSPLASH extends Ghost  implements ActionListener{

	private boolean arrivedToBrick;
	private int moveHelper;
	private String direction;
	private boolean arrivedToPacman;

	public WATERSPLASH(int[][] maze, Board board, GameTimer leadTimer, int startRow, int startCol, String direction) {

		super(startRow,startCol, maze, board, leadTimer);

		this.leadTimer.addListener(this);
		moveHelper = 2;
		arrivedToBrick = false;
		arrivedToPacman = false;
		this.direction = direction;
	}

	@Override
	public void action() {

		if (!arrivedToPacman & !arrivedToBrick) { // water moves until hits pacman or a brick
			if (moveHelper == 0) {
				switch (direction) {

				case "Right":
					if (canRight()) {
						this.replaceLastElement(R, C);
						currElement = maze[R][C+1];
						C++;
					}
					else {
						arrivedToBrick = true;
					}
					break;

				case "Left":
					if (canLeft()) {
						this.replaceLastElement(R, C);
						currElement = maze[R][C-1];
						C--;
					}
					else {
						arrivedToBrick = true;
					}
					break;

				case "Up":
					if (canUp()) {
						this.replaceLastElement(R, C);
						currElement = maze[R-1][C];
						R--;
					}
					else {
						arrivedToBrick = true;
					}
					break;

				case "Down":
					if (canDown()) {
						this.replaceLastElement(R, C);
						currElement = maze[R+1][C];
						R++;
					}
					else {
						arrivedToBrick = true;
					}
					break;
				}
				moveHelper = 2;
				maze[R][C] = 15;

				if (!arrivedToBrick) {
					// water splash hit pacman
					if (board.getPacman().getCol() == C & board.getPacman().getRow() == R) { 
						arrivedToPacman = true;
						crashWith(board.getPacman());

						// handle pacman lives
						this.board.getGameManager().setLives(this.board.getPacman().getNumOfLives());
					}
				}
				else { // watersplash has ended
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



	public boolean getIsArrivedToBrick() {
		return arrivedToBrick;
	}


	// water splash meet pacman cases:
	
	public void visit(ProtectedPacman visited) {
		// pacman freezes for 3 secs and loses 10 points
		visited.freez();
	}

	public void visit(GrumpyPacman visited) {
		// nothing happens to pacman
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NicePacman visited) {
		// TODO Auto-generated method stub
		
	}




}
