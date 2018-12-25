package logic.DragAndDropEventHandling;

import java.util.ArrayList;

import data.localGameData.GameStateData;
import data.statisticsFromDatabase.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import logic.AI.Minimax;
import logic.boardManipulators.NumberBoardManipulator;
import logic.boardManipulators.VisualBoardManipulator;
import logic.dataTypes.ChessMove;
import logic.dataTypes.ChessPiece;
import logic.moveGenerator.MoveCalculator;

public class DragAndDropEventHandlers {

	private static ArrayList<Double> moveTimes = new ArrayList<Double>();

	private static GameStateData gameStateData;
	private static Minimax aiAlgorithm;

	private static MoveCalculator mc = new MoveCalculator();
	private static NumberBoardManipulator nbm = new NumberBoardManipulator();
	private static VisualBoardManipulator vbm = new VisualBoardManipulator();
	private static DatabaseConnection databaseConnection = new DatabaseConnection();

	private static int turnNumber = 0;

	public static void setupGameData(GridPane visualBoard, GridPane moveHighlighter, Minimax aiAlgorithm) {
		gameStateData = new GameStateData(visualBoard, moveHighlighter);

		vbm.populateGridPane(visualBoard, gameStateData.getNumberBoard());

		setupDragAndDrop(visualBoard);

		DragAndDropEventHandlers.aiAlgorithm = aiAlgorithm;

		turnNumber = 0;
	}

	/*
	 * Drag and Drop implementation
	 */

	static ChessPiece sourcePiece;
	static ArrayList<Integer> availableMoves;
	static ArrayList<Region> highlightedFields;

	public static void setupDragAndDrop(GridPane visualBoard) {
		ObservableList<Node> children = visualBoard.getChildren();

		for (Node child : children) {
			makeNodeDraggable(child);
		}

	}

	public static void makeNodeDraggable(Node node) {
		GridPane moveHighlightingPane = gameStateData.getMoveHighlighter();
		setupDragDetection(node, moveHighlightingPane);
		setupDragOver(node);
		setupDragDropped(node);
		setupDragDone(node, moveHighlightingPane);
	}

	public static void setupDragDetection(Node node, GridPane moveHighlightingPane) {
		node.setOnDragDetected(event -> {

			ChessPiece originPiece = (ChessPiece) event.getSource();

			if (originPiece.getType() > -1)
				event.consume();
			else if (aiAlgorithm.isPlayersTurn()) {

				sourcePiece = originPiece;

				int originLocation = originPiece.getArraylocation();

				availableMoves = mc.generatePossibleMoves(originLocation, gameStateData.getNumberBoard());

				vbm.highlightFields(availableMoves, moveHighlightingPane);

				Dragboard db = originPiece.startDragAndDrop(TransferMode.MOVE);

				ClipboardContent content = new ClipboardContent();

				content.putImage(originPiece.getImage());

				db.setContent(content);
			}

		});
	}

	public static void setupDragDropped(Node node) {
		node.setOnDragDropped(event -> {
			ChessPiece destinationNode = (ChessPiece) event.getSource();

			int dropLocation = destinationNode.getArraylocation();

			if (!availableMoves.contains(dropLocation))
				event.consume();

			else {

				executeAMove(sourcePiece, destinationNode, gameStateData.getNumberBoard(),
						gameStateData.getVisualBoard());

				if (hasPlayerWon())
					recordPlayerWin();

				// signal that players turn is finished
				aiAlgorithm.setPlayersTurn(false);

				// make AI move
				makeAIMove(true, gameStateData.getNumberBoard(), gameStateData.getVisualBoard());

				/*
				System.out.println("Calls to Minimax Function:" + aiAlgorithm.getCallsToMinimax());
				if (aiAlgorithm.getCallsToMinimax() > aiAlgorithm.getMaximumCallsToMinimax())
					aiAlgorithm.setMaximumCallsToMinimax(aiAlgorithm.getCallsToMinimax());
				aiAlgorithm.setCallsToMinimax(0);
				System.out
						.println("\nmaximum calls to minimax: " + aiAlgorithm.getMaximumCallsToMinimax() / 1000 + "k");
				*/
			}
		});
	}

	public static void makeAIMove(boolean makeBlackTurn, int[] numberBoard, GridPane visualBoard) {

		// long startTime = System.currentTimeMillis();

		ChessMove aiMove = aiAlgorithm.miniMax(makeBlackTurn, gameStateData.getNumberBoard(),
				gameStateData.getAiPieceLocations(), gameStateData.getUserPieceLocations(), 0, null, -9999999, 9999999);

		// long endTime = System.currentTimeMillis();
		// System.out.println("Heuristic: " + aiMove.getHeuristicValue());
		// System.out.println("It took " + (endTime - startTime) + "
		// milliseconds");
		// moveTimes.add((double) (endTime - startTime) / 1000);

		/*
		double sum = 0;
		
		for (int i = 0; i < moveTimes.size(); i++) {
			sum += moveTimes.get(i);
		}
		
		System.out.println("Average move time: " + sum / moveTimes.size());
		*/

		ChessPiece movingNode = vbm.getANode(aiMove.getStartingLocation(), visualBoard);
		ChessPiece destinationNode = vbm.getANode(aiMove.getDestinationLocation(), visualBoard);

		executeAMove(movingNode, destinationNode, numberBoard, visualBoard);

		if (!aiMove.isGameOver())
			aiAlgorithm.setPlayersTurn(true);

	}

	public static void executeAMove(ChessPiece movingNode, ChessPiece destinationNode, int[] numberBoard,
			GridPane visualBoard) {

		// save the dragged pieces location before it is moved
		int originLocation = movingNode.getArraylocation();

		// pass on move information for execution and return new nodes that need
		// to be set up for drag and drop
		Node[] fieldMovedFromAndNewQueen = vbm.makeMove(movingNode, destinationNode, visualBoard);

		/*
		 * make the field that has been moved from, which is now empty drag and drop compatible
		 * also do this for a new
		 */
		for (Node newNode : fieldMovedFromAndNewQueen) {
			makeNodeDraggable(newNode);
		}

		// get the destination of the drag and drop
		int dropLocation = destinationNode.getArraylocation();

		// synchronize the numberBoard with the new move
		nbm.makeMove(originLocation, dropLocation, gameStateData.getNumberBoard());

		if (movingNode.getType() > 0) {
			nbm.updatePieceLocations(gameStateData.getAiPieceLocations(), gameStateData.getUserPieceLocations(),
					originLocation, dropLocation);
		} else {
			nbm.updatePieceLocations(gameStateData.getUserPieceLocations(), gameStateData.getAiPieceLocations(),
					originLocation, dropLocation);
		}

		turnNumber++;

		if (turnNumber == 1)
			recordGameStarted();
		if (turnNumber == 10)
			gameHasReachedTenTurns();

	}

	private static boolean hasPlayerWon() {
		int heuristicBoardValue = aiAlgorithm.evaluateBoardByPieces(gameStateData.getNumberBoard(),
				gameStateData.getAiPieceLocations(), gameStateData.getUserPieceLocations());

		if (heuristicBoardValue < -12000)
			return true;
		else
			return false;
	}

	private static void recordGameStarted() {
		databaseConnection.incrementGamesPlayedAmount(DatabaseConnection.getDifficulty(),
				DatabaseConnection.getPlayerName());
	}

	private static void recordPlayerWin() {
		databaseConnection.incrementPlayerWins(DatabaseConnection.getDifficulty(), DatabaseConnection.getPlayerName());
	}

	private static void gameHasReachedTenTurns() {
		databaseConnection.incrementGamesWithMoreThanTenTurnsAmount(DatabaseConnection.getDifficulty(),
				DatabaseConnection.getPlayerName());
	}

	public static void setupDragOver(Node node) {
		node.setOnDragOver(event -> {
			event.acceptTransferModes(TransferMode.ANY);
			event.consume();
		});
	}

	public static void setupDragDone(Node node, GridPane moveHighlighter) {
		node.setOnDragDone(event -> {

			vbm.removeHighlights(availableMoves, moveHighlighter);
		});
	}

}
