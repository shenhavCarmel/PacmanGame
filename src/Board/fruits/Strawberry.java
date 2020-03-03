package Board.fruits;

import Logic.Fruit;
import Logic.GameTimer;

public class Strawberry extends Fruit {

	private int stage;
	private int[][]Maze;
	private boolean isOnBoard;
	private int[] save;
	private int index;

	public Strawberry(int currStage, int[][] maze, GameTimer leadTimer) {

		super(300,0,1,2,leadTimer);
		this.leadTimer.addListener(this);
		stage = currStage;
		Maze = maze;
		isOnBoard = false;
		save = new int[33];
		for (int i=0; i<33; i++)
		{
			save[i]=-1;
		}

	}

	// add all strawberrys to the board
	public void addToBoard() {

		int number = 0;

		switch(stage) {

		case 1:
			number = appearS1;
			break;

		case 2:
			number = appearS2;
			break;

		case 3:
			number = appearS3;
			break;
		}

		for (int i=0; i < number; i++) {
			add();
		}
		index=0;
	}

	// add a strawberry to the board in a random way
	private void add() {

		int r = randomPlace();
		int c = randomPlace();
		while (!(Maze[r][c] == 3) & !(Maze[r][c] == 2)) {
			r = randomPlace();
			c = randomPlace();
		}
		save[index] = r;
		save[index+1] = c;
		save[index+2] = Maze[r][c];
		Maze[r][c] = 4;
		index = index+3;
	}

	public int randomPlace() {

		return (int )(Math.random() * 31 + 1);

	}	

	// manage the strawberrys at a timer tick
	@Override
	public void action() {
		if (!isOnBoard) {
			if(delay != 0)
			{
				delay --;
			}
			else {
				addToBoard();
				delay = 80;
				isOnBoard = true;
			}
		}
		else{
			if (delay > 50) {
				if (delay % 2 == 0) {
					Hide();
				}
				else {
					Show();
				}
			}
			else if (delay == 50) {
				Show();
			}
			else if (delay == 0) {
				Hide();
				delay = 60;
				isOnBoard = false;
			}
			delay--;
		}
	}

	// hide all strawberrys
	public void Hide() {
		for (int i=0; i < save.length; i = i+3)
		{
			if(save[i] != -1) {
				Maze[save[i]][save[i+1]] = save[i+2];
			}
		}	
	}
	
	// show all strawberrys
	public void Show() {
		for (int i=0; i < save.length; i = i+3)
		{
			if(save[i] != -1) {
				Maze[save[i]][save[i+1]] = 4;
			}
		}
	}
}
