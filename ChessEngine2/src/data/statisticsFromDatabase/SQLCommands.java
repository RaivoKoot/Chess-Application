package data.statisticsFromDatabase;

public class SQLCommands {

	/*
	 * 
	 * INSERT and UPDATE commands
	 * 
	 */
	public static String getInsertPlayerSQLCommand(String playerName, int difficulty) {
		String sqlInsertNewPlayerName = "INSERT INTO t_player_game_counts VALUES('" + playerName + "', " + difficulty
				+ " , DEFAULT, DEFAULT, DEFAULT)";

		return sqlInsertNewPlayerName;
	}

	public static String getIncrementGamesStartedSQLCommand(String playerName, int difficulty) {
		String sqlIncrementGamesStarted = "UPDATE t_player_game_counts\r\n"
				+ "SET games_started = games_started + 1\r\n" + "WHERE pk_playername = '" + playerName
				+ "' AND pk_difficulty = " + difficulty;

		return sqlIncrementGamesStarted;
	}

	public static String getIncrementGamesTenPlusTurnsSQLCommand(String playerName, int difficulty) {
		String sqlIncrementGamesStarted = "UPDATE t_player_game_counts\r\n"
				+ "SET games_ten_plus_turns = games_ten_plus_turns + 1\r\n" + "WHERE pk_playername = '" + playerName
				+ "' AND pk_difficulty = " + difficulty;

		return sqlIncrementGamesStarted;
	}

	public static String getIncrementWinsSQLCommand(String playerName, int difficulty) {
		String sqlIncrementGamesStarted = "UPDATE t_player_game_counts\r\n" + "SET wins = wins + 1\r\n"
				+ "WHERE pk_playername = '" + playerName + "' AND pk_difficulty = " + difficulty;

		return sqlIncrementGamesStarted;
	}

	/*
	 * 
	 * SELECT commands
	 * 
	 */

	public static String getCheckIfNameExistsSQLCommand(String playerName) {
		String sqlCheckifNameExists = "SELECT COUNT(pk_playerName) FROm t_player_game_counts WHERE pk_playerName = '"
				+ playerName + "'";

		return sqlCheckifNameExists;
	}

	public static String getAllPlayerStatsForSpecificDifficultySQLCommand(int difficulty) {
		String sqlStatsForDifficulty = "SELECT * FROM t_player_game_counts WHERE games_started > 0 AND pk_difficulty = "
				+ difficulty + " ORDER BY wins DESC, games_started DESC";

		return sqlStatsForDifficulty;
	}

	public static String getSumOfGamesAndSumOfWinsByDifficultySQLCommand(int difficulty) {
		String sqlSumOfGamesAndSumOfWins = "SELECT SUM(games_started), SUM(wins) FROM t_player_game_counts WHERE pk_difficulty ="
				+ difficulty;

		return sqlSumOfGamesAndSumOfWins;
	}

	public static String getCompleteSumOfGamesAndSumOfWinsSQLCommand() {
		String sqlAllWinsAllGames = "SELECT SUM(games_started), SUM(wins) FROM t_player_game_counts";

		return sqlAllWinsAllGames;
	}

	public static String getAiLossesSQLCommand() {
		String lostOnDifficulties = "SELECT DISTINCT pk_difficulty FROM t_player_game_counts WHERE wins > 0";
		return lostOnDifficulties;
	}

}
