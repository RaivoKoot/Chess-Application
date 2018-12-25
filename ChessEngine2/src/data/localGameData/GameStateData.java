package data.localGameData;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class GameStateData {

	private int[] gameState;
	private GridPane visualBoard;
	private GridPane moveHighlighter;

	private ArrayList<Integer> aiPieceLocations = new ArrayList<Integer>(
			Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25, 26, 27, 28));
			
			private ArrayList<Integer> userPieceLocations = new ArrayList<Integer>(
			Arrays.asList(71, 72, 73, 74, 75, 76, 77, 78, 81, 82, 83, 84, 85, 86, 87, 88));


	public GameStateData(GridPane visualBoard, GridPane moveHighlighter) {
		setNumberBoard(new int[] { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 2, 4, 3, 5, 6, 3, 4, 2, 100,
				100, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 100,
				100, 0, 0, 0, 0, 0, 0, 0, 0, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 100, 100, -1, -1, -1, -1, -1, -1, -1, -1,
				100, 100, -2, -4, -3, -5, -6, -3, -4, -2, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 });
		this.setVisualBoard(visualBoard);

		this.setMoveHighlighter(moveHighlighter);
		for (int i = 0; i < 10; i++)
			for (int k = 0; k < 10; k++) {
				Region rg = new Region();
				rg.setStyle("-fx-background-color: lightgreen");
				rg.setOpacity(0.0);
				moveHighlighter.add(rg, k, i);
			}
	}

	public GridPane getVisualBoard() {
		return visualBoard;
	}

	public void setVisualBoard(GridPane visualBoard) {
		this.visualBoard = visualBoard;
	}

	public int[] getNumberBoard() {
		return gameState;
	}

	public void setNumberBoard(int[] numberBoard) {
		this.gameState = numberBoard;
	}

	// returns a string formatted to look like a board
	public String getString(int[] numberBoard) {
		String board_representation = "";

		int currentSymbol = 999;

		board_representation += "[ ";

		for (int k = 0; k < 100; k++) {
			currentSymbol = 999;
			currentSymbol = numberBoard[k];

			if (currentSymbol > -1 && currentSymbol != 100)
				board_representation += 0;
			board_representation += currentSymbol + ", ";

			if ((k + 1) % 10 == 0) {
				board_representation += "]\n";
				if (k != 99)
					board_representation += "[ ";
			}
		}

		return board_representation;

	}

	public GridPane getMoveHighlighter() {
		return moveHighlighter;
	}

	public void setMoveHighlighter(GridPane moveHighlighter) {
		this.moveHighlighter = moveHighlighter;
	}

	public ArrayList<Integer> getUserPieceLocations() {
		return userPieceLocations;
	}

	public ArrayList<Integer> getAiPieceLocations() {
		return aiPieceLocations;
	}

	/*
			0	  1		2	  3		4	  5		6	  7
	0	[ (0,0),(0,1),(0,2),(0,3),(0,4),(0,5),(0,6),(0,7) ]
	
	1	[ (1,0),(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7) ]
	
	2	[ (2,0),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7) ]
	
	3	[ (3,0),(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7) ]
	
	4	[ (4,0),(4,1),(4,2),(4,3),(4,4),(4,5),(4,6),(4,7) ]
	
	5	[ (5,0),(5,1),(5,2),(5,3),(5,4),(5,5),(5,6),(5,7) ]
	
	6	[ (6,0),(6,1),(6,2),(6,3),(6,4),(6,5),(6,6),(6,7) ]
	
	7	[ (7,0),(7,1),(7,2),(7,3),(7,4),(7,5),(7,6),(7,7) ]
	
	*/

}
