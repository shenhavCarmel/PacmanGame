package Logic;

import Board.Visited;
import Board.Visitor;
import GUI_Windows.Board;

public abstract class Ghost implements Visitor, TimerListener{

	protected int R;
	protected int C;
	protected int[][] maze;
	protected int currElement;
	protected Board board;
	protected GameTimer leadTimer;
	protected String lastMoveDirection;
	
	public Ghost(int row, int col, int[][] maze, Board board, GameTimer leadTimer) {
		R = row;
		C = col;
		this.maze = maze;
		currElement = 2;
		this.board = board;
		this.leadTimer = leadTimer;
		lastMoveDirection = "Down";
		
	}

	
	public int getCol() {
		return this.C;
	}

	public int getRow() {
		return this.R;
	}
	
	// ghost moves one step towards pacman - Chase mode
	protected void Chase(int rPac, int cPac) {
		
		if (R < rPac) {
			if (C < cPac) {
				if(Math.random()*10 < 5) {
					Move("Right","Down");
				}
				else {
					Move("Down", "Right");
				}

			}
			else if(C > cPac){
				if(Math.random()*10 < 5) {
					Move("Left","Down");
				}
				else {
					Move("Down","Left");
				}
			}
			else { // ghost and pacman at same col
				if(Math.random()*10 < 5) {
					Move("Down","Left");
				}
				else {
					Move("Down","Right");
				}
			}
		}
		else if (R > rPac){ 
			if (C < cPac) {
				if(Math.random()*10 < 5) {
					Move("Right","Up");
				}
				else {
					Move("Up", "Right");
				}
			}
			else if(C > cPac){
				if(Math.random()*10 < 5) {
					Move("Left","Up");
				}
				else {
					Move("Up","Left");
				}
			}
			else { //ghost and pacman at same col
				if(Math.random()*10 < 5) {
					Move("Up","Left");
				}
				else {
					Move("Up","Right");
				}
			}
		}
		else { // ghost and pacman at same row
			if (C < cPac) {
				if(Math.random()*10 < 5) {
					Move("Right","Up");
				}
				else {
					Move("Right","Down");
				}
			}
			else {
				if(Math.random()*10 < 5) {
					Move("Left","Up");
				}
				else {
					Move("Left","Downã");
				}
			}
		}
	}

	// move ghost if possible at the given direction
	protected void Move(String firstDir, String secondDir) {

		switch (firstDir) {

		case "Right":

			if (canRight()) {
				replaceLastElement(R,C);
				currElement = maze[R][C+1];
				C++;
				lastMoveDirection = "Right";	
				}
			else {
				switch (secondDir) {
				case "Up":
					if (canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					else if(canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					else if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					break;

				case "Down":
					if (canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					else if(canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					else if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					break;

				} 
			}
			break; // end Right case

		case "Left":
			if (canLeft()) {
				replaceLastElement(R,C);
				currElement = maze[R][C-1];
				C--;
				lastMoveDirection = "Left";	
			}
			else {
				switch (secondDir) {
				case "Up":
					if (canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					else if(canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					else if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					break;

				case "Down":
					if (canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					else if(canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					else if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					break;

				} 
			}
			break; // end Left case

		case "Up":

			if (canUp()) {
				replaceLastElement(R,C);
				currElement = maze[R-1][C];
				R--;
				lastMoveDirection = "Up";	
			}
			else {
				switch (secondDir) {
				case "Right":
					if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					else if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					else if (canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					break;

				case "Left":
					if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					else if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					else if (canDown()) {
						replaceLastElement(R,C);
						currElement = maze[R+1][C];
						R++;
						lastMoveDirection = "Down";	
					}
					break;

				} 
			}
			break; // end Up case

		case "Down":

			if (canDown()) {
				replaceLastElement(R,C);
				currElement = maze[R+1][C];
				R++;
				lastMoveDirection = "Down";	
			}
			else {
				switch (secondDir) {
				case "Right":
					if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					else if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					else if (canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					break;

				case "Left":
					if (canLeft()) {
						replaceLastElement(R,C);
						currElement = maze[R][C-1];
						C--;
						lastMoveDirection = "Left";	
					}
					else if (canRight()) {
						replaceLastElement(R,C);
						currElement = maze[R][C+1];
						C++;
						lastMoveDirection = "Right";	
					}
					else if (canUp()) {
						replaceLastElement(R,C);
						currElement = maze[R-1][C];
						R--;
						lastMoveDirection = "Up";	
					}
					break;

				} 
			}
			break; 
		}// end Down case
	}
	protected boolean canUp() {

		if (R > 0) {
			return (maze[R-1][C] != 1);
		}
		return false;
	}

	protected boolean canLeft() {

		if (C > 0) {
			return (maze[R][C-1] != 1);
		}
		return false;
	}

	protected boolean canDown() {

		if (R < 31) {
			return (maze[R+1][C] != 1);
		}
		return false;
	}

	protected boolean canRight() {

		if (C < 31) {
			return (maze[R][C+1] != 1);
		}
		return false;
	}

	
	public void replaceLastElement(int R, int C) {
		if (currElement != 6 | (R == board.getPacman().getRow() & C == board.getPacman().getCol())) {
			if (currElement > 9 & currElement < 17){
				maze[R][C] = 2;
			}
			else {
				maze[R][C] = currElement;
			}
		}
		else {
			maze[R][C] = 2;
		}
	}
		
	// initiate a meeting with a pacman
	public void crashWith(Pacman pacman) {
		pacman.impact(this);
	}

}

