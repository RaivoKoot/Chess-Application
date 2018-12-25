package logic.dataTypes;

public class ChessMove {
	private int startingLocation;
	private int destinationLocation;

	private int heuristicValue;
	
	private boolean isGameOver;

	public ChessMove(int startingLocation, int destinationLocation) {
		this.setStartingLocation(startingLocation);
		this.setDestinationLocation(destinationLocation);
		setIsGameOver(false);
	}

	public int getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(int destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public int getStartingLocation() {
		return startingLocation;
	}

	public void setStartingLocation(int startingLocation) {
		this.startingLocation = startingLocation;
	}

	public int getHeuristicValue() {
		return heuristicValue;
	}

	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setIsGameOver(boolean gameNotOver) {
		this.isGameOver = gameNotOver;
	}
}
