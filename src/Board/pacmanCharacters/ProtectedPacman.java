package Board.pacmanCharacters;

import Board.Visitor;
import Board.ghostsCharacters.FIREBALL;
import Board.ghostsCharacters.FLINKEY;
import Board.ghostsCharacters.GINKEY;
import Board.ghostsCharacters.INKEY;
import Board.ghostsCharacters.STINKEY;
import Board.ghostsCharacters.WATERSPLASH;
import GUI_Windows.Board;
import Logic.GameTimer;
import Logic.Pacman;

public class ProtectedPacman extends Pacman{


	private int timeOut;


	public ProtectedPacman(int row, int col, int lives, Board board, int[][] maze, GameTimer leadTimer) {
		super(row, col, 2, lives, board, maze, leadTimer);
		timeOut = 0;
	}

	// protected pacman meets a ghost
	@Override
	public void impact(Visitor visitor) {
		visitor.visit(this);
	}

	// protected pacman freezes
	public void freez() {
		if (!isFreezed()) {
			maze[row][col] = 6;
			timeOut = 30;
			setFreezed(true);
			board.reduce10points();
			board.repaint();
		}
	}

	// handle the freeze of pacman
	@Override
	public void action() {
		if (!isFreezed()) {

		}
		else { // freezed
			timeOut--;
			maze[row][col] = 6;
			if (timeOut == 0) {
				setFreezed(false);
			}
		}
		board.repaint();
	}

}





