package logic.AI;

import java.util.ArrayList;
import java.util.Collections;

/*
 * NOTE: Taken from StackOverFlow and edited
 * 
 * Using QuickSort, just as with MergeSort, to choose a move via minimax takes longer as with BubbleSort
 * Thus, This class is not used in the system. It is kept though for future reference
 */

public class QuickSortPieceLocations {

	public static void sort(ArrayList<Integer> list, int from, int to, int[] gameState) {
		if (from < to) {
			int pivot = from;
			int left = from + 1;
			int right = to;
			int pivotValue = list.get(pivot);
			while (left <= right) {
				// left <= to -> limit protection
				while (left <= to && !moreImportantThan(pivotValue, list.get(left), gameState)) {
					left++;
				}
				// right > from -> limit protection
				while (right > from && moreImportantThan(pivotValue, list.get(right), gameState)) {
					right--;
				}
				if (left < right) {
					Collections.swap(list, left, right);
				}
			}
			Collections.swap(list, pivot, left - 1);
			sort(list, from, right - 1, gameState); // <-- pivot was wrong!
			sort(list, right + 1, to, gameState); // <-- pivot was wrong!
		}
	}

	private static boolean moreImportantThan(int location, int otherLocation, int[] gameState) {
		boolean locationMoreImportantThanOtherLocation = false;

		int typeOne = Math.abs(gameState[location]);
		int typeTwo = Math.abs(gameState[otherLocation]);

		if (((typeOne > typeTwo) && typeOne != 20000))
			locationMoreImportantThanOtherLocation = true;

		return locationMoreImportantThanOtherLocation;
	}
}
