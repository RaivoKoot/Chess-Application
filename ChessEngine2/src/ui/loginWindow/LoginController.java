package ui.loginWindow;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import data.statisticsFromDatabase.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ui.GUIWindowLauncher;

public class LoginController implements Initializable {

	@FXML
	JFXTextField usernameField;

	DatabaseConnection databaseConnection = new DatabaseConnection();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void loginButtonClicked() {
		checkIfLoginValid(usernameField);
	}

	public void checkIfLoginValid(JFXTextField usernameField) {
		String username = usernameField.getText();

		if (username.isEmpty())
			return;

		DatabaseConnection.setPlayerName(username);

		databaseConnection.enterNewPlayer(username);

		GUIWindowLauncher.launchChessboardWindow();
		Stage thisStage = (Stage) usernameField.getScene().getWindow();

		thisStage.close();
	}

}
