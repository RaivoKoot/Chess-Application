package ui.leaderboardWindow;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import logic.statisticsFormatting.StatisticsFormatter;
import logic.statisticsFormatting.TableViewChild;
import start.Main;

public class LeaderboardController implements Initializable {

	@FXML
	TableView<TableViewChild> tblStatistics;

	@FXML
	TableColumn<TableViewChild, Integer> columnWins;
	@FXML
	TableColumn<TableViewChild, Integer> columnGames;
	@FXML
	TableColumn<TableViewChild, Integer> columnLongGames;
	@FXML
	TableColumn<TableViewChild, String> columnPlayerName;

	@FXML
	ChoiceBox<Integer> choiceboxDifficulties;

	@FXML
	Label labelGamesPlayed;
	@FXML
	Label labelGamesLost;

	@FXML
	BorderPane rootPane;

	@FXML
	HBox paneForCharts;

	@FXML
	Label neverLost5;
	@FXML
	Label neverLost4;

	private StatisticsFormatter statPuller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statPuller = new StatisticsFormatter();

		choiceboxDifficulties.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
		choiceboxDifficulties.setValue(5);

		setupTableColumns();

		refreshTableViewChildren(5);
		refreshSumOfGamesAndWins(5);
		refreshNeverLostLabels();

		addDifficultyChoiceListener();

		spawnPieCharts();

		setPaneBackgroundImage(rootPane);

	}

	private void refreshNeverLostLabels() {
		int[] aiLosses = statPuller.getHasAiLostOnDifficulty145();
		for (int i = 0; i < aiLosses.length; i++) {

			if (aiLosses[i] > 0 && aiLosses[i] < 6)
				if (aiLosses[i] == 5)
					neverLost5.setVisible(false);
				else if (aiLosses[i] == 4)
					neverLost4.setVisible(false);
		}
	}

	private void addDifficultyChoiceListener() {
		choiceboxDifficulties.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

				refreshTableViewChildren(newValue);
				refreshSumOfGamesAndWins(newValue);
			}

		});
	}

	public void refreshSumOfGamesAndWins(int difficulty) {
		int[] sumOfGamesAndWins = statPuller.getSumOfGamesAndWins(difficulty);

		labelGamesPlayed.setText(String.valueOf(sumOfGamesAndWins[0]));

		labelGamesLost.setText(String.valueOf(sumOfGamesAndWins[1]));
	}

	public void refreshTableViewChildren(int difficulty) {
		tblStatistics.setItems(statPuller.getPlayerStatistics(difficulty));
	}

	private void setupTableColumns() {
		columnWins.setCellValueFactory(new PropertyValueFactory<>("wins"));
		columnGames.setCellValueFactory(new PropertyValueFactory<>("gamesStarted"));
		columnLongGames.setCellValueFactory(new PropertyValueFactory<>("gamesPlayedTenPlusTurns"));
		columnPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
	}

	private void spawnPieCharts() {
		for (int difficulty = 6; difficulty >= 1; difficulty--) {

			int[] gamesAndWins;

			if (difficulty == 6)
				gamesAndWins = statPuller.getAllSumOfGamesAndWins();
			else
				gamesAndWins = statPuller.getSumOfGamesAndWins(difficulty);

			DecimalFormat df = new DecimalFormat("##.#");

			double overallGames = gamesAndWins[0];
			double playerWinPercentage = gamesAndWins[1] / overallGames * 100;
			double aiWinPercentage = (overallGames - gamesAndWins[1]) / overallGames * 100;

			playerWinPercentage = Double.parseDouble(df.format(playerWinPercentage).replaceAll(",", "."));
			aiWinPercentage = Double.parseDouble(df.format(aiWinPercentage).replaceAll(",", "."));

			PieChart.Data aiWinsSlice = new PieChart.Data("AI Wins", aiWinPercentage);
			PieChart.Data playerWinsSlice = new PieChart.Data("Player Wins", playerWinPercentage);

			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(aiWinsSlice,
					playerWinsSlice);

			aiWinsSlice.setName("AI Wins " + aiWinPercentage + "%");
			playerWinsSlice.setName("Player Wins " + playerWinPercentage + "%");

			PieChart chart = new PieChart(pieChartData);
			chart.setMinSize(450, 475);
			chart.setLabelsVisible(false);

			if (difficulty == 6)
				chart.setTitle("All Difficulties");
			else
				chart.setTitle("Difficulty " + difficulty);
			paneForCharts.getChildren().add(chart);
		}
	}

	public void setPaneBackgroundImage(Pane pane) {
		BackgroundImage background = new BackgroundImage(
				new Image(Main.class.getResource("/backgroundImage2.jpg").toString(), 1920, 1080, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		pane.setBackground(new Background(background));
	}

}
