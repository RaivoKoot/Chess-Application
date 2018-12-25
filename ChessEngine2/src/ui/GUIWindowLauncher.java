package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GUIWindowLauncher extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("loginWindow/LoginPane.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchChessboardWindow() {
		try {

			Parent gameBoard = FXMLLoader.load(GUIWindowLauncher.class.getResource("chessboardWindow/GameboardPane.fxml"));
			Stage gameBoardStage = new Stage();

			gameBoardStage.setScene(new Scene(gameBoard));
			gameBoardStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchLeaderboardWindow() {
		try {

			Parent leaderBoardPane = FXMLLoader.load(GUIWindowLauncher.class.getResource("leaderboardWindow/LeaderboardPane.fxml"));
			Stage leaderBoardStage = new Stage();

			leaderBoardStage.setTitle("Leaderboards");
			leaderBoardStage.setScene(new Scene(leaderBoardPane));
			leaderBoardStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
