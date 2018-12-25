package data.statisticsFromDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class DatabaseConnection implements CanInsertDataIntoDB, CanPullStatsFromDB {

	private String driver = "\"com.mysql.jdbc.Driver\"";
	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String password = "";

	private Connection connection;
	private Statement statement;

	private static int difficulty;
	private static String playerName;

	private void createConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,user,password);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setDifficulty(int difficulty) {
		DatabaseConnection.difficulty = difficulty;
	}

	public static void setPlayerName(String playerName) {
		DatabaseConnection.playerName = playerName;
	}

	public static String getPlayerName() {
		return playerName;
	}

	public static int getDifficulty() {
		return difficulty;
	}

	@Override
	public void enterNewPlayer(String playerName) {

		if (checkIfNameExists(playerName))
			return;

		try {
			createConnection();

			for (int difficulty = 1; difficulty <= 5; difficulty++) {
				String sqlCommand = SQLCommands.getInsertPlayerSQLCommand(playerName, difficulty);

				statement.executeUpdate(sqlCommand);
			}

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private boolean checkIfNameExists(String playerName) {
		String sqlCommand = SQLCommands.getCheckIfNameExistsSQLCommand(playerName);
		boolean result = true;

		try {
			createConnection();

			ResultSet resultSet = statement.executeQuery(sqlCommand);

			resultSet.next();
			if (resultSet.getInt(1) == 0)
				result = false;

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;

	}

	@Override
	public void incrementGamesPlayedAmount(int difficulty, String playerName) {
		try {
			createConnection();

			String sqlCommand = SQLCommands.getIncrementGamesStartedSQLCommand(playerName, difficulty);

			statement.executeUpdate(sqlCommand);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void incrementPlayerWins(int difficulty, String playerName) {
		try {
			createConnection();

			String sqlCommand = SQLCommands.getIncrementWinsSQLCommand(playerName, difficulty);

			statement.executeUpdate(sqlCommand);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void incrementGamesWithMoreThanTenTurnsAmount(int difficulty, String playerName) {
		try {
			createConnection();

			String sqlCommand = SQLCommands.getIncrementGamesTenPlusTurnsSQLCommand(playerName, difficulty);

			statement.executeUpdate(sqlCommand);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public ResultSet getStatList(int difficulty) {

		String sqlCommand = SQLCommands.getAllPlayerStatsForSpecificDifficultySQLCommand(difficulty);

		ResultSet resultSet = null;

		try {
			createConnection();

			resultSet = statement.executeQuery(sqlCommand);

			// connection.close();
			// statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resultSet;

	}

	@Override
	public int[] getSumOfGamesAndSumOfWins(int difficulty) {
		String sqlCommand = SQLCommands.getSumOfGamesAndSumOfWinsByDifficultySQLCommand(difficulty);

		int[] gamesAndWins = new int[2];

		try {
			createConnection();

			ResultSet resultSet = statement.executeQuery(sqlCommand);

			resultSet.next();

			gamesAndWins[0] = resultSet.getInt(1);
			gamesAndWins[1] = resultSet.getInt(2);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return gamesAndWins;
	}

	@Override
	public int[] getSumOfGamesAndSumOfWinsAllDifficulties() {
		String sqlCommand = SQLCommands.getCompleteSumOfGamesAndSumOfWinsSQLCommand();

		int[] gamesAndWins = new int[2];

		try {
			createConnection();

			ResultSet resultSet = statement.executeQuery(sqlCommand);

			resultSet.next();

			gamesAndWins[0] = resultSet.getInt(1);
			gamesAndWins[1] = resultSet.getInt(2);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return gamesAndWins;
	}

	@Override
	public int[] getHasAiLostOnDifficulty145() {
		String sqlCommand = SQLCommands.getAiLossesSQLCommand();

		int[] aiLost = { -1, -1, -1, -1, -1 };

		try {
			createConnection();

			ResultSet resultSet = statement.executeQuery(sqlCommand);

			for (int i = 0; resultSet.next(); i++)
				aiLost[i] = resultSet.getInt(i + 1);

			connection.close();
			statement.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return aiLost;
	}
}
