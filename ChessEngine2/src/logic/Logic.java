package logic;

import javafx.scene.layout.GridPane;
import logic.AI.Minimax;
import logic.DragAndDropEventHandling.DragAndDropEventHandlers;

public class Logic {

	

	public Logic(GridPane visualBoard, GridPane moveHighlighter, int chosenAlgorithmDepth) {

		Minimax aiAlgorithm = new Minimax(chosenAlgorithmDepth);
		DragAndDropEventHandlers.setupGameData(visualBoard, moveHighlighter, aiAlgorithm);

	}
	
	
}
