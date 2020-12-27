package gui;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.GoogleCalendar;

public class IntroController {

	@FXML
	void NextStage(ActionEvent event) throws IOException, GeneralSecurityException {
		GoogleCalendar.startService();
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(home_page_scene);
		appStage.show();
	}

	@FXML
	void clearGmailAcct(ActionEvent event) {
		GoogleCalendar.deleteGmailAcct();
	}

}