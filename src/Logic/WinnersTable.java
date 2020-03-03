package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class WinnersTable {

	private String[][] players;
	private String[] newPlayer;

	public WinnersTable() {

		players = new String[5][5];
		// read file
		readFile();

	}

	public WinnersTable(int score, int pineappleCount, int appleCount, int strawberryCount, String nickname){

		// read players tabel from the file
		readFile();

		// create new player
		newPlayer = new String[5];
		newPlayer[0] = nickname;
		newPlayer[1] = Integer.toString(pineappleCount);
		newPlayer[2] = Integer.toString(appleCount);
		newPlayer[3] = Integer.toString(strawberryCount);
		newPlayer[4] = Integer.toString(score);

		// edit file

		boolean changed = addNewPlayer();
		if (changed) {
			editFile();
		}

	}

	// read the txt file and create a matrix
	private void readFile(){

		players = new String[5][5];
		for (int i=0; i<5; i++)
		{
			for(int j=0; j<5; j++)
			{
				players[i][j] = "0";
			}
		}

		String line = null;
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(new File("ScoreTable.txt")))) {
			while ((line = br.readLine()) != null) {
				String[] player = line.split(",");
				if (player.length > 0) {
					players[count][0] = player[0];
					players[count][1] = player[1];
					players[count][2] = player[2];
					players[count][3] = player[3];
					players[count][4] = player[4];
					count++;
				}
			}
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// try to add a new player to the top 5 table - to the matrix
	private boolean addNewPlayer() {

		boolean found = false;
		int change = -1;
		for(int i=0; i<5 & !found; i++) {

			if (players[i] == null) {
				change = i;
				found = true;
			}
			else if (Integer.parseInt(newPlayer[4]) > Integer.parseInt(players[i][4])) {
				change = i;
				found = true;
			}
		}
		if (found)
		{
			for(int i=3; i > change; i--){				
				players[i+1]= players[i];	
			}
			players[change] = newPlayer;
		}
		return found;

	}

	// save the matrix to the txt file as lines
	public void editFile() {

		PrintWriter write = null;
		try {
			write = new PrintWriter("ScoreTable.txt", "UTF-8");
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] != null)
				write.println(players[i][0] + "," + players[i][1]  + "," + players[i][2]  + "," + players[i][3]  + "," + players[i][4]);
		}
		write.close();
	}

	public String[][] getScoreTable(){
		return players;
	}


}


