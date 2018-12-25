package logic.moveGenerator;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.layout.GridPane;

/* This class has the purpose to generate 
 * the current legal moves
 * of chess pieces on the board. 
 *  
 * It needs the numberBoard as a paramter
 */
public class MoveCalculator {

	// move directions of chess pieces from their current position
	private static final int[] ROOK_MOVES = { 1, 1, -1, 10, -10 };
	private static final int[] BISHOP_MOVES = { 1, 9, -9, 11, -11 };
	private static final int[] KNIGHT_MOVES = { 0, 8, -8, 12, -12, 19, -19, 21, -21 };
	private static final int[] QUEEN_MOVES = { 1, 1, -1, 9, -9, 10, -10, 11, -11 };
	private static final int[] KING_MOVES = { 0, 1, -1, 9, -9, 10, -10, 11, -11 };
	// special case
	private static final int[] BLACK_PAWN_MOVES = { 10, 20, 9, 11 };
	private static final int[] WHITE_PAWN_MOVES = { -10, -20, -9, -11 };

	private static final int[][] allMoves = { ROOK_MOVES, BISHOP_MOVES, KNIGHT_MOVES, QUEEN_MOVES, KING_MOVES };

	public ArrayList<Integer> generatePawnMoves(int pieceLocation, int[] numberBoard) {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		int type = numberBoard[pieceLocation];
		int newLocation = -1; // default

		int[] movesArray = BLACK_PAWN_MOVES;
		if (type == -1)
			movesArray = WHITE_PAWN_MOVES;

		for (int i = 0; i < movesArray.length; i++) {
			// if i is 0 or the pawn is on its starting field
			if (i != 1 || ((((pieceLocation / 10) == 2) || ((pieceLocation / 10) == 7))) && possibleMoves.size() != 0) {
				newLocation = pieceLocation + movesArray[i];

				if (isLegalMove(numberBoard[newLocation], type)) {
					if ((i < 2 && numberBoard[newLocation] == 0) || (i > 1 && numberBoard[newLocation] != 0))
						possibleMoves.add(newLocation);
				}
			}

		}

		return possibleMoves;

	}

	public ArrayList<Integer> generatePossibleMoves(int pieceLocation, int[] numberBoard) {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		int type = numberBoard[pieceLocation];

		if (type == 0 || type == 100) {
			System.out.println("ERROR. You have passed a bad location to the generateMoves function");
			System.out.println("Piece Location: " + pieceLocation);
		}
		if (type == 1 || type == -1)
			possibleMoves = generatePawnMoves(pieceLocation, numberBoard);
		else {

			int movesIndex = Math.abs(type) - 2;

			int[] moveDirections = null;
			try {
				moveDirections = allMoves[movesIndex];
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("You are trying to find moves for a vacant square");
			}

			//
			boolean extend = false;

			// algorithm
			for (int i = 1; i < moveDirections.length; i++) {
				int newLocation = pieceLocation - moveDirections[i];
				if (moveDirections[0] == 1)
					extend = true;

				for (int k = 0; extend || k == 0; k++) {
					if (k != 0) {
						if (newLocation / 10 != pieceLocation / 10) {
							newLocation -= moveDirections[i];
						} else {
							if (newLocation > pieceLocation)
								newLocation++;
							else {
								newLocation--;
							}
						}
					}

					if (newLocation >= 0 && newLocation < 100 && isLegalMove(numberBoard[newLocation], type)) {
						possibleMoves.add(newLocation);
						// if enemy in the way, extension over
						if (numberBoard[newLocation] != 0)
							extend = false;
					} else {
						extend = false;
					}

				}
			}
		}

		Collections.sort(possibleMoves);
		return possibleMoves;
	}

	// checks if target location is either vacant or taken by opponent piece
	private boolean isLegalMove(int targetType, int type) {
		boolean moveLegality = true;

		if (targetType == 100 || (targetType > 0 && type > 0) || (targetType < 0 && type < 0))
			moveLegality = false;

		return moveLegality;

	}

}
