package data.statisticsFromDatabase;

public interface CanInsertDataIntoDB {

	void enterNewPlayer(String playerName);

	void incrementGamesPlayedAmount(int difficulty, String playerName);

	void incrementPlayerWins(int difficulty, String playerName);

	void incrementGamesWithMoreThanTenTurnsAmount(int difficulty, String playerName);
	
}
