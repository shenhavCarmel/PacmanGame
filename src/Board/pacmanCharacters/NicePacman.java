package Board.pacmanCharacters;

import Board.Visited;
import Board.Visitor;
import Board.ghostsCharacters.FLINKEY;
import Board.ghostsCharacters.GINKEY;
import Board.ghostsCharacters.STINKEY;
import GUI_Windows.Board;
import Logic.GameTimer;
import Logic.Pacman;

public class NicePacman extends Pacman {


	public NicePacman(int row, int col, int lives, Board board, int[][] maze, GameTimer leadTimer) {
		super(row, col, 1, lives, board, maze, leadTimer);
	}
	
	// nice pacman meet a ghost
	@Override
	public void impact(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

}
