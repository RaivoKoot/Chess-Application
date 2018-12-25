package logic.statisticsFormatting;

public class TableViewChild {

	private String playerName;
	private int wins;
	private int gamesStarted;
	private int gamesPlayedTenPlusTurns;

	public TableViewChild() {
	};

	public TableViewChild(String playerName, int wins, int gamesStarted, int gamesPlayedTenPlusTurns) {
		setPlayerName(playerName);
		setWins(wins);
		setGamesStarted(gamesStarted);
		setGamesPlayedTenPlusTurns(gamesPlayedTenPlusTurns);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getGamesStarted() {
		return gamesStarted;
	}

	public void setGamesStarted(int gamesStarted) {
		this.gamesStarted = gamesStarted;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getGamesPlayedTenPlusTurns() {
		return gamesPlayedTenPlusTurns;
	}

	public void setGamesPlayedTenPlusTurns(int gamesPlayedTenPlusTurns) {
		this.gamesPlayedTenPlusTurns = gamesPlayedTenPlusTurns;
	}

}
