package Board.pacmanCharacters;

import Board.Visitor;
import Board.ghostsCharacters.BLINKEY;
import Board.ghostsCharacters.FIREBALL;
import Board.ghostsCharacters.FLINKEY;
import Board.ghostsCharacters.GINKEY;
import Board.ghostsCharacters.INKEY;
import Board.ghostsCharacters.STINKEY;
import Board.ghostsCharacters.WATERSPLASH;
import GUI_Windows.Board;
import Logic.GameTimer;
import Logic.Pacman;

public class GrumpyPacman extends Pacman {


	public GrumpyPacman(int row, int col, int lives, Board board, int[][] maze, GameTimer leadTimer) {
		super(row, col, 3, lives, board, maze, leadTimer);
	}

	// grumpy hit a ghost
	@Override
	public void impact(Visitor visitor) {
		visitor.visit(this);
	}


	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
