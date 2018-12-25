package logic.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.localGameData.PieceSquareValueConstants;
import logic.boardManipulators.NumberBoardManipulator;
import logic.dataTypes.ChessMove;
import logic.moveGenerator.MoveCalculator;

public class Minimax {

	private MoveCalculator mc;
	private NumberBoardManipulator nbm;

	private int callsToMinimax = 0;

	private int maximumCallsToMinimax = 0;

	private boolean playersTurn;

	private int chosenDepth;

	// b pawn, b rook, b bishop, b knight, b queen, b king, w pawn, w rook, w
	// bishop, w knight, w queen, w king
	private final static int[] HEURISTIC_PIECE_VALUES = { 100, 500, 330, 320, 900, 20000, -100, -500, -330, -320, -900,
			-20000 };

	public Minimax(int chosenDepth) {
		setPlayersTurn(true);
		mc = new MoveCalculator();
		nbm = new NumberBoardManipulator();
		this.chosenDepth = chosenDepth;

	}

	public void playerTurnMade() {
		setPlayersTurn(false);
	}

	public boolean isPlayersTurn() {
		return playersTurn;
	}

	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	/*
	 * Evaluation function
	 * counts up the black and white pieces on the field and adds up their
	 * heuristic values
	 * post: returns the heuristic difference between black and white pieces
	 */
	public int evaluateBoardByPieces(int[] numberBoard, ArrayList<Integer> aiPieceLocations,
			ArrayList<Integer> userPieceLocations) {
		int score = 0;
		try {
			List[] allPieces = { aiPieceLocations, userPieceLocations };

			int type = 100;
			for (int i = 0; i < 2; i++)
				for (int k = 0; k < allPieces[i].size(); k++) {

					type = numberBoard[(int) allPieces[i].get(k)];

					if (type < 0) {
						type *= -1;
						type += 5;
					} else
						type--;

					score += HEURISTIC_PIECE_VALUES[type];
					/*
					 * add value from piece location
					 */
					int locationValue = PieceSquareValueConstants.getSquareValues()[type][(int) allPieces[i].get(k)];
					score += locationValue;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return score;
	}

	/*
	 * returns true if a player has lost his king
	 * kings have a value of 20k, all other pieces together 3k
	 * So if the boardValue is significantly high it means one kings is dead
	 */
	public boolean isGameOver(int heuristicValue) {
		if (Math.abs(heuristicValue) > 12000)
			return true;
		return false;
	}

	/*
	 * updates the lists that hold the locations of each players pieces
	 */
	private void updatePieceLocations(boolean aiTurn, ArrayList<Integer> aiPieceLocations,
			ArrayList<Integer> userPieceLocations, ChessMove move) {
		if (aiTurn)
			nbm.updatePieceLocations(aiPieceLocations, userPieceLocations, move.getStartingLocation(),
					move.getDestinationLocation());
		else
			nbm.updatePieceLocations(userPieceLocations, aiPieceLocations, move.getStartingLocation(),
					move.getDestinationLocation());
	}

	public int getCallsToMinimax() {
		return callsToMinimax;
	}

	public void setCallsToMinimax(int callsToMinimax) {
		this.callsToMinimax = callsToMinimax;
	}

	public int getMaximumCallsToMinimax() {
		return maximumCallsToMinimax;
	}

	public void setMaximumCallsToMinimax(int maximumCallsToMinimax) {
		this.maximumCallsToMinimax = maximumCallsToMinimax;
	}

	/*
	 * Main Algorithm for AI player
	 */
	public ChessMove miniMax(boolean aiTurn, int[] gameState, ArrayList<Integer> aiPieceLocations,
			ArrayList<Integer> userPieceLocations, int depth, ChessMove aMove, int alpha, int beta) {

		//callsToMinimax++;

		/* if heuristic value is over 10000 it means one player has lost his king and the game is over */
		int heuristicValue = evaluateBoardByPieces(gameState, aiPieceLocations, userPieceLocations);
		boolean gameIsOver = isGameOver(heuristicValue);

		// return the heuristic value of node and the move
		if (depth == chosenDepth || gameIsOver) {
			aMove.setHeuristicValue(heuristicValue);
			if (gameIsOver)
				aMove.setIsGameOver(true);
			return aMove;
		} else {

			ChessMove bestMove = new ChessMove(-1, -1);
			ArrayList<Integer> currentPieceLocations;

			/*
			 * determines whether black or white pieces will be used for this iteration of minimax
			 * post: currentPieceLocations is set to either ai's or user's pieces, bestmove is initialized
			 */
			if (aiTurn) {
				bestMove.setHeuristicValue(-9999999);
				currentPieceLocations = aiPieceLocations;
			} else {
				bestMove.setHeuristicValue(9999999);
				currentPieceLocations = userPieceLocations;
			}

			BubbleSortPieceLocations.sort(gameState, currentPieceLocations);

			/*
			 * generates all possible moves 
			 * post: allPossibleMoves is filled with moves that include their heuristic value
			 */
			try {
				root_loop: for (int i = 0; i < currentPieceLocations.size(); i++) {

					int originLocation = currentPieceLocations.get(i);

					ArrayList<Integer> possibleMovesForPiece = mc.generatePossibleMoves(originLocation, gameState);

					/*
					 * call miniMax on each move to ultimately get its heuristic value
					 */
					for (int k = 0; k < possibleMovesForPiece.size(); k++) {

						ChessMove newMove = new ChessMove(originLocation, possibleMovesForPiece.get(k));

						// creates a copy of the numberboad and updates it for
						// the new move
						int[] numberBoardCopy = gameState.clone();
						nbm.makeMove(newMove, numberBoardCopy);

						// creates copies of the piece location lists and
						// updates them for the new move
						ArrayList<Integer> aiPieceLocationsCopy = (ArrayList<Integer>) aiPieceLocations.clone();
						ArrayList<Integer> userPieceLocationsCopy = (ArrayList<Integer>) userPieceLocations.clone();
						updatePieceLocations(aiTurn, aiPieceLocationsCopy, userPieceLocationsCopy, newMove);

						// MINIMAX ON CHILD NODE
						newMove = miniMax(!aiTurn, numberBoardCopy, aiPieceLocationsCopy, userPieceLocationsCopy,
								depth + 1, newMove, alpha, beta);

						if (aiTurn) {
							if (newMove.getHeuristicValue() > bestMove.getHeuristicValue()) {

								bestMove = newMove;

								if (newMove.getHeuristicValue() > alpha)
									alpha = newMove.getHeuristicValue();

								if (alpha >= beta)
									break root_loop;

							}
						} else if (newMove.getHeuristicValue() < bestMove.getHeuristicValue()) {
							bestMove = newMove;

							if (newMove.getHeuristicValue() < beta)
								beta = newMove.getHeuristicValue();

							if (beta <= alpha)
								break root_loop;

						}

					}
				}

				if (depth != 0) {
					aMove.setHeuristicValue(bestMove.getHeuristicValue());
					bestMove = aMove;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bestMove;
		}
	}

}
