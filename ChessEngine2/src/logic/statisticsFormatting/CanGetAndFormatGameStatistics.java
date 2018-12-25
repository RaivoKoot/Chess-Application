package logic.statisticsFormatting;

import javafx.collections.ObservableList;

public interface CanGetAndFormatGameStatistics {

	ObservableList<TableViewChild> getPlayerStatistics(int difficulty);

	int[] getSumOfGamesAndWins(int difficulty);

	int[] getAllSumOfGamesAndWins();

	int[] getHasAiLostOnDifficulty145();

}
