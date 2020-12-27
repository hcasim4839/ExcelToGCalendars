package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.GoogleEvent;
import util.GoogleEventList;
import util.MoreOptionsFormSavedData;

public class MoreOptionFormController implements Initializable {

	@FXML
	private TextArea description_txtArea;

	@FXML
	private TextField emailNotification_txt;

	@FXML
	private TextField notification_txt;

	@FXML
	private ComboBox<String> cmb_visibility;

	@FXML
	private ComboBox<String> cmb_activityStatus;

	private List<String> txtFieldList;
	private List<String> cmbList;
	private List<String> txtAreaList;

	@FXML
	void NextStage(ActionEvent event) throws IOException {
		saveFormData();
		Parent homePageParent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
		Scene HomePageScene = new Scene(homePageParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(HomePageScene);
		appStage.show();
	}

	private void saveFormData() {
		txtFieldList = new ArrayList<String>(
				Arrays.asList(emailNotification_txt.getText(), notification_txt.getText()));

		cmbList = new ArrayList<String>(Arrays.asList(cmb_visibility.getValue(), cmb_activityStatus.getValue()));

		txtAreaList = new ArrayList<String>(Arrays.asList(description_txtArea.getText()));

		MoreOptionsFormSavedData.insertData(txtFieldList, cmbList, txtAreaList);

	}

	@FXML
	void saveChoices(ActionEvent event) {
		saveFormData();

		int amountOfEventsToChange = GoogleEventList.getNumOfEvents();

		for (int currentEvent = 0; currentEvent < amountOfEventsToChange; currentEvent++) {

			/*
			 * GoogleEventList.getList().get(currentEvent).setEmailNotif(txtFieldList.get(0)
			 * ); GoogleEventList.getList().get(currentEvent).setNotif(txtFieldList.get(1));
			 * 
			 * GoogleEventList.getList().get(currentEvent).setVisibility(cmbList.get(0));
			 * GoogleEventList.getList().get(currentEvent).setTransparency(cmbList.get(1));
			 * 
			 * GoogleEventList.getList().get(currentEvent).setDescription(txtAreaList.get(0)
			 * );
			 */
			GoogleEventList.setEmailNotification(txtFieldList.get(0));
			GoogleEventList.setNotification(txtFieldList.get(1));
			GoogleEventList.setVisibility(cmbList.get(0));
			GoogleEventList.setTransparency(cmbList.get(1));
			GoogleEventList.setDescription(txtAreaList.get(0));

		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		initializeCmbActivityStatus();
		initializeCmbVisibility();
		initializeDefaultValues();
		if (MoreOptionsFormSavedData.hasValues()) {
			initializeFormValues();
		}
	}

	private void initializeFormValues() {
		List<List<String>> listOfValues = MoreOptionsFormSavedData.exportData();

		List<String> txtFieldValues = listOfValues.get(0);
		List<String> cmbValues = listOfValues.get(1);
		List<String> txtAreaValues = listOfValues.get(2);

		setValues(txtFieldValues, cmbValues, txtAreaValues);

	}

	private void setValues(List<String> txtFieldValues, List<String> cmbValues, List<String> txtAreaValues) {
		List<TextField> txtFieldList = new ArrayList<TextField>(Arrays.asList(emailNotification_txt, notification_txt));

		for (int i = 0; i < txtFieldList.size(); i++) {
			txtFieldList.get(i).setText(txtFieldValues.get(i));
		}

		List<ComboBox<String>> cmbList = new ArrayList<ComboBox<String>>(
				Arrays.asList(cmb_visibility, cmb_activityStatus));

		for (int i = 0; i < cmbList.size(); i++) {
			cmbList.get(i).setValue(cmbValues.get(i));
		}

		List<TextArea> txtAreaList = new ArrayList<TextArea>(Arrays.asList(description_txtArea));

		for (int i = 0; i < txtAreaList.size(); i++) {
			txtAreaList.get(i).setText(txtAreaValues.get(i));
		}

	}

	private void initializeDefaultValues() {
		final boolean listIsEmpty = GoogleEventList.getList().isEmpty();
		List<ComboBox<String>> list = Arrays.asList(cmb_visibility, cmb_activityStatus);

		list.stream().forEach(cmbEntry -> {
			if (listIsEmpty)
				cmbEntry.setValue("default");
		});
		if (listIsEmpty == false) {
			GoogleEvent entry = GoogleEventList.getList().get(0);

			cmb_visibility.setValue(entry.getVisibility());
			cmb_activityStatus.setValue(entry.getTransparency());

		}
	}

	private void initializeCmbVisibility() {
		cmb_activityStatus.getItems().addAll("Free", "Busy");
	}

	private void initializeCmbActivityStatus() {
		cmb_visibility.getItems().addAll("default", "public", "private", "confidential");
	}
}
