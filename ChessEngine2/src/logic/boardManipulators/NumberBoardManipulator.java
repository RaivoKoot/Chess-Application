package logic.boardManipulators;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import logic.dataTypes.ChessMove;
import logic.dataTypes.ChessPiece;

public class NumberBoardManipulator {

	// 0 empty
	// BLACK: 1 pawn, 2 rook, 3 bishop, 4 knight, 5 queen, 6 king
	// WHITE: -1 pawn, -2 rook, -3 bishop, -4 knight, -5 queen, -6 king

	// changes type of specific location on number board
	public void makeMove(int originField, int targetField, int[] numberBoard) {
		numberBoard[targetField] = numberBoard[originField];
		numberBoard[originField] = 0;

		checkForPromotion(new ChessMove(originField, targetField), numberBoard);
	}

	public void makeMove(ChessMove move, int[] numberBoard) {
		numberBoard[move.getDestinationLocation()] = numberBoard[move.getStartingLocation()];
		numberBoard[move.getStartingLocation()] = 0;

		checkForPromotion(move, numberBoard);
	}

	private void checkForPromotion(ChessMove move, int[] numberBoard) {
		int type = numberBoard[move.getDestinationLocation()];
		int newLocation = move.getDestinationLocation();

		if ((type == -1 && newLocation / 10 == 1) || (type == 1 && newLocation / 10 == 8)) {
			if (type == -1)
				numberBoard[newLocation] = -5;
			else
				numberBoard[newLocation] = 5;
		}
	}

	public void updatePieceLocations(ArrayList<Integer> pieceLocations, ArrayList<Integer> opponentPieceLocations,
			int oldLocation, int newLocation) {
		try {
			pieceLocations.remove(Integer.valueOf(oldLocation));
			pieceLocations.add(newLocation);

			if (opponentPieceLocations.contains(newLocation))
				opponentPieceLocations.remove(Integer.valueOf(newLocation));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// creates a copy of the GridPane Board as a numberBoard
	public int[] gridpaneToNumberboard(GridPane board) {
		int[] arrayBoard = new int[100];

		int index = -1;
		int col = -1;
		int row = -1;
		for (Node temp : board.getChildren()) {
			ChessPiece child = (ChessPiece) temp;
			col = GridPane.getColumnIndex(child);
			row = GridPane.getRowIndex(child);

			int[] coordinate = { col, row };
			index = coordinateToNumber(coordinate);

			arrayBoard[index] = child.getType();
			index = -1;
			col = -1;
			row = -1;
		}

		return arrayBoard;
	}

	public int coordinateToNumber(int[] coordinate) {
		int number = 0;

		number += coordinate[0];
		number += 10 * coordinate[1];

		return number;
	}

}
