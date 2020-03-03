package Board;

import Board.pacmanCharacters.GrumpyPacman;
import Board.pacmanCharacters.NicePacman;
import Board.pacmanCharacters.ProtectedPacman;

public interface Visitor {
	
	// ghosts hit a pacman
	
	public void visit(NicePacman visited) ;
	public void visit(ProtectedPacman visited) ;
	public void visit(GrumpyPacman visited) ; 
	

}
