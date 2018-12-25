package logic.AI;

import java.util.ArrayList;
import java.util.Collections;

public class BubbleSortPieceLocations {

	public static void sort(int[] gameState, ArrayList<Integer> currentPieceLocations) {
		// bubbleSort
		for (int i = 0; i < currentPieceLocations.size(); i++) {
			for (int x = 1; x < currentPieceLocations.size(); x++) {

				if (moreImportantThan(currentPieceLocations.get(x), currentPieceLocations.get(x - 1), gameState)) {
					Collections.swap(currentPieceLocations, x, x - 1);
				}
			}
		}
	}

	public static boolean moreImportantThan(int location, int otherLocation, int[] gameState) {

		int typeOne = Math.abs(gameState[location]);
		int typeTwo = Math.abs(gameState[otherLocation]);

		if (((typeOne > typeTwo) && typeOne != 20000))
			return true;

		return false;
	}

}
