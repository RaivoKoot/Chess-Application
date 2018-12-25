package logic.statisticsFormatting;

import java.sql.ResultSet;

import data.statisticsFromDatabase.CanPullStatsFromDB;
import data.statisticsFromDatabase.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StatisticsFormatter implements CanGetAndFormatGameStatistics {

	private CanPullStatsFromDB databaseConnection = new DatabaseConnection();

	private ObservableList<TableViewChild> gameStatistics;

	public void fillList(ResultSet statisticTuples) {
		try {
			while (statisticTuples.next()) {

				TableViewChild row = new TableViewChild();
				row.setPlayerName(statisticTuples.getString(1));

				row.setGamesStarted(statisticTuples.getInt(3));

				row.setGamesPlayedTenPlusTurns(statisticTuples.getInt(4));

				row.setWins(statisticTuples.getInt(5));

				gameStatistics.add(row);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public ObservableList<TableViewChild> getPlayerStatistics(int difficulty) {
		gameStatistics = FXCollections.observableArrayList();

		fillList(databaseConnection.getStatList(difficulty));

		return gameStatistics;
	}

	@Override
	public int[] getSumOfGamesAndWins(int difficulty) {

		int[] sumOfGamesAndWins = databaseConnection.getSumOfGamesAndSumOfWins(difficulty);

		return sumOfGamesAndWins;
	}

	@Override
	public int[] getAllSumOfGamesAndWins() {

		int[] sumOfGamesAndWins = databaseConnection.getSumOfGamesAndSumOfWinsAllDifficulties();

		return sumOfGamesAndWins;
	}

	@Override
	public int[] getHasAiLostOnDifficulty145() {
		int[] aiLosses = databaseConnection.getHasAiLostOnDifficulty145();
		return aiLosses;
	}

}
