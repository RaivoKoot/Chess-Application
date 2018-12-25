package data.statisticsFromDatabase;

import java.sql.ResultSet;
import javafx.collections.ObservableList;

public interface CanPullStatsFromDB {

	ResultSet getStatList(int difficulty);

	int[] getSumOfGamesAndSumOfWins(int difficulty);

	int[] getSumOfGamesAndSumOfWinsAllDifficulties();

	int[] getHasAiLostOnDifficulty145();

}
