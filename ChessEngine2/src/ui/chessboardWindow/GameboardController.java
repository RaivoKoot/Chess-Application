package ui.chessboardWindow;

import java.net.URL;
import java.util.ResourceBundle;

import data.statisticsFromDatabase.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.Logic;
import logic.DragAndDropEventHandling.DragAndDropEventHandlers;
import start.Main;
import ui.GUIWindowLauncher;

public class GameboardController implements Initializable {

	Logic logic;

	@FXML
	GridPane visualBoard;
	@FXML
	GridPane moveHighlighter;
	@FXML
	BorderPane rootPane;

	@FXML
	ChoiceBox<Integer> choiceBoxDifficulty;

	@FXML
	Button leaderboardButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		choiceBoxDifficulty.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

		setPaneBackgroundImage(rootPane);

	}

	public void leaderboardButtonPressed() {
		GUIWindowLauncher.launchLeaderboardWindow();
	}

	public void setPaneBackgroundImage(Pane pane) {
		BackgroundImage background = new BackgroundImage(
				new Image(Main.class.getResource("/backgroundImage2.jpg").toString(), 1920, 1080, false, true), BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		pane.setBackground(new Background(background));
	}

	public void btnStartPressed() {

		if (choiceBoxDifficulty.getValue() == null)
			return;

		DatabaseConnection.setDifficulty(choiceBoxDifficulty.getValue());
		
		visualBoard.getChildren().clear();
		logic = new Logic(visualBoard, moveHighlighter, choiceBoxDifficulty.getValue());

	}
}
