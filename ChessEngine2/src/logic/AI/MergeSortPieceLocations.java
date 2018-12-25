package logic.AI;

import java.util.ArrayList;

/*
 * Using MergeSort, just as with QuickSort, to choose a move via minimax takes longer as with BubbleSort
 * Thus, This class is not used in the system. It is kept though for future reference
 */

public class MergeSortPieceLocations {

	public static ArrayList<Integer> merge(ArrayList<Integer> listA, ArrayList<Integer> listB, int[] gameState) {
		int a = 0, b = 0; // position des zu vergleichenden Elementes
		// Neue liste die so lange wie beide Arrays zusammen ist
		ArrayList<Integer> mergedList = new ArrayList<Integer>();

		// Sortiert so oft, wie die Laenge des neuen Arrays
		for (int i = 0; i < (listB.size() + listA.size()); i++) {
			// elementeVerschoben++;
			// wenn aus array eins bereits alle elemente verschoben wurden
			if (a == listA.size()) {
				mergedList.add(listB.get(b));
				b++;
				// wenn aus array zwei bereits alle elemente verschoben wurde
			} else if (b == listB.size()) {
				mergedList.add(listA.get(a));
				a++;
				// wenn element von array 1 kleiner als element von array 2
			} else if (moreImportantThan(listA.get(a), listB.get(b), gameState)) {
				mergedList.add(listA.get(a));
				a++;
				// wenn element von array 2 kleiner als element von array 1
			} else {
				mergedList.add(listB.get(b));
				b++;
			}
		}
		return mergedList;
	}

	private static boolean moreImportantThan(int location, int otherLocation, int[] gameState) {
		boolean locationMoreImportantThanOtherLocation = false;

		int typeOne = Math.abs(gameState[location]);
		int typeTwo = Math.abs(gameState[otherLocation]);

		if (((typeOne > typeTwo) && typeOne != 20000))
			locationMoreImportantThanOtherLocation = true;

		return locationMoreImportantThanOtherLocation;
	}

	public static ArrayList<Integer> mergeSort(ArrayList<Integer> list, int[] gameState) {
		int length = list.size();
		// wenn array nur ein element hat wird es returned
		if (length < 2)
			return list;

		// zwei neue genau halb so grosse arrays erstellt
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();

		// array 1 die erste haelfte an elementen zuordnen
		for (int i = 0; i < length / 2; i++)
			list1.add(list.get(i));
		// array 2 die zweite haelfte an elementen zuordnen
		for (int i = length / 2; i < length; i++)
			list2.add(list.get(i));

		// beide arrays rekursiv weitergeben und in zwei teilen
		ArrayList<Integer> listA = mergeSort(list1, gameState);
		ArrayList<Integer> listB = mergeSort(list2, gameState);

		// arrays wieder in eines mergen und returnen
		ArrayList<Integer> sortedList = merge(listA, listB, gameState);
		return sortedList;
	}

	public static String listToString(ArrayList<Integer> list) {
		String printArray = "";
		for (int i = 0; i < list.size(); i++) {
			printArray += list.get(i);
			if (i != list.size() - 1)
				printArray += "-";
		}
		return printArray;
	}

}
