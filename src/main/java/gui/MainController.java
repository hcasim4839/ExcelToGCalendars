package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.CalTimeStandardization;
import util.ExcelReader;
import util.GoogleCalDateFormatter;
import util.GoogleCalendar;
import util.GoogleEvent;
import util.GoogleEventList;
import util.MainFormSavedData;
import util.MoreOptionsFormSavedData;

public class MainController implements Initializable {

	@FXML
	private TextField rowsBeginning_text;
	@FXML
	private TextField rowsEnding_text;
	@FXML
	private TextField titleCol_txt;
	@FXML
	private TextField startDateCol_txt;
	@FXML
	private TextField endDateCol_txt;
	@FXML
	private TextField descriptionCol_txt;
	@FXML
	private TextField secondDescriptionCol_txt;
	@FXML
	private TextField guestsCol_txt;
	@FXML
	private TextField locationCol_txt;
	@FXML
	private Button options_btn;
	@FXML
	private Button submit_btn;
	@FXML
	private Button file_btn;
	@FXML
	private Label fileStatus_txt;
	@FXML
	private ComboBox<String> cb_EndDate;
	@FXML
	private ComboBox<String> cb_StartDate;
	@FXML
	private TextArea warning_Area;
	@FXML
	private TextField startTime_txt;
	@FXML
	private TextField endTime_txt;
	@FXML
	private ComboBox<String> cb_startTime;
	@FXML
	private ComboBox<String> cb_endTime;
	@FXML
	private ComboBox<String> cb_sheets;
	private int titleColIndex;
	private int startDateColIndex;
	private int endDateColIndex;
	private int descColIndex;
	private int locationColIndex;
	private String fileLocation = "";
	private ExcelReader reader = new ExcelReader();

	private int startTimeColIndex;
	private String cellValue;
	private int endTimeColIndex;

	@FXML
	void submitInformation(ActionEvent event) {
		requestExcelInfo();
		insertToGoogleCalendar();
		clearSaveData();
	}

	private void clearSaveData() {
		GoogleEventList.clear();
		MainFormSavedData.clear();
		MoreOptionsFormSavedData.clear();
		fileStatus_txt.setText("");
		clearFormValues();
		setErrorMsg("Entries were submitted");
	}

	private void clearFormValues() {
		List<TextField> txtFieldList = new ArrayList<TextField>(
				Arrays.asList(rowsBeginning_text, rowsEnding_text, titleCol_txt, startDateCol_txt, endDateCol_txt,
						startTime_txt, endTime_txt, descriptionCol_txt, secondDescriptionCol_txt, locationCol_txt));
		List<ComboBox<String>> cmbList = new ArrayList<ComboBox<String>>(
				Arrays.asList(cb_StartDate, cb_EndDate, cb_startTime, cb_endTime));

		txtFieldList.stream().forEach(txtField -> txtField.setText(""));
		cmbList.stream().forEach(cmb -> cmb.valueProperty().set(null));
		cb_sheets.getItems().clear();
	}

	@FXML
	void NextStage(ActionEvent event) throws IOException {
		saveFormData();
		Parent homePageParent = FXMLLoader.load(getClass().getResource("MoreOptionForm.fxml"));
		Scene HomePageScene = new Scene(homePageParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		appStage.setScene(HomePageScene);
		appStage.show();
	}

	private void saveFormData() {
		List<String> txtFieldList = new ArrayList<String>(Arrays.asList(rowsBeginning_text.getText(),
				rowsEnding_text.getText(), titleCol_txt.getText(), startDateCol_txt.getText(), endDateCol_txt.getText(),
				startTime_txt.getText(), endTime_txt.getText(), descriptionCol_txt.getText(),
				secondDescriptionCol_txt.getText(), locationCol_txt.getText()));
		List<String> cmbList = new ArrayList<String>(Arrays.asList(cb_StartDate.getValue(), cb_EndDate.getValue(),
				cb_startTime.getValue(), cb_endTime.getValue(), cb_sheets.getValue()));

		txtFieldList.forEach(System.out::println);

		MainFormSavedData.insertData(txtFieldList, cmbList);
		MainFormSavedData.insertFilePath(fileLocation);
	}

	private void requestExcelInfo() {
		saveFormData();
		if (MainFormSavedData.exportFilePath() != null) {
			initializeExcelReader();

			int startingRow = Integer.parseInt(rowsBeginning_text.getText());
			int endingRow = Integer.parseInt(rowsEnding_text.getText());

			int numOfEvents = (endingRow - startingRow) + 1;

			reader.setRowNum(startingRow - 1);
			if (numOfEvents > 0) {
				for (int i = 0; i < numOfEvents; i++) {
					convertToEvent(startingRow);
				}
			} else if (endingRow - startingRow == 0)
				convertToEvent(startingRow);
			else
				setErrorMsg("Integers only!");
		} else
			setErrorMsg("Enter File Location AND the sheet name");
	}

	private void convertToEvent(int startingRow) {
		String descriptionPrev = "";
		GoogleEvent currentEvent = new GoogleEvent();

		if (!titleCol_txt.getText().trim().equals("")) {
			titleColIndex = Integer.parseInt(titleCol_txt.getText().trim()) - 1;
			reader.setCellNum(titleColIndex);
			cellValue = reader.run();

			currentEvent.setTitle(cellValue);
		}
		if (!startDateCol_txt.getText().trim().equals("")) {
			startDateColIndex = Integer.parseInt(startDateCol_txt.getText().trim()) - 1;
			reader.setCellNum(startDateColIndex);
			cellValue = reader.run();

			String dateFormat = cb_StartDate.getValue();
			String startDate = GoogleCalDateFormatter.convert(cellValue, dateFormat);

			currentEvent.setStartDate(startDate);

			if (!startTime_txt.getText().trim().equals("")) {
				startTimeColIndex = Integer.parseInt(startTime_txt.getText().trim()) - 1;
				reader.setCellNum(startTimeColIndex);
				cellValue = reader.run();

				String timeFormat = cb_startTime.getValue();
				String startTime;

				if (cellValue.trim() == null || cellValue.trim() == "")
					startTime = "08:00 AM";
				else {
					startTime = CalTimeStandardization.convert(timeFormat, cellValue.trim());
					System.out.println("The cell value is located in else statement\nvalue is: " + cellValue);
				}
				System.out.println("The StartTime is: " + startTime);
				currentEvent.setStartTime(startTime);
			} else
				currentEvent.setStartTime("08:00 AM");
		}
		if (!endDateCol_txt.getText().trim().equals("")) {
			endDateColIndex = Integer.parseInt(endDateCol_txt.getText().trim()) - 1;
			reader.setCellNum(endDateColIndex);
			cellValue = reader.run();

			String dateFormat = cb_EndDate.getValue();
			String endDate = GoogleCalDateFormatter.convert(cellValue, dateFormat);

			currentEvent.setEndDate(endDate);

			if (!endTime_txt.getText().trim().equals("")) {
				endTimeColIndex = Integer.parseInt(endTime_txt.getText().trim()) - 1;
				reader.setCellNum(endTimeColIndex);
				cellValue = reader.run();

				String timeFormat = cb_endTime.getValue();
				String endTime;

				if (cellValue == null || cellValue.trim() == "") {
					endTime = "11:59 PM";
					System.out.println("The cell value is located in if statement");
				} else {
					endTime = CalTimeStandardization.convert(timeFormat, cellValue);
					System.out.println("The cell value is located in else statement\nvalue is: " + cellValue);
				}
				System.out.println("The endtime is: " + endTime);
				currentEvent.setEndTime(endTime);
			} else
				currentEvent.setEndTime("11:59 PM");
		}
		if (!descriptionCol_txt.getText().trim().equals("")) {
			descColIndex = Integer.parseInt(descriptionCol_txt.getText().trim()) - 1;
			reader.setCellNum(descColIndex);
			cellValue = reader.run();
			descriptionPrev = cellValue;

			currentEvent.setDescription(cellValue);
		}
		if (!secondDescriptionCol_txt.getText().equals("")) {
			descColIndex = Integer.parseInt(secondDescriptionCol_txt.getText().trim()) - 1;
			reader.setCellNum(descColIndex);
			cellValue = reader.run();

			descriptionPrev += "\n" + cellValue;
			currentEvent.setDescription(descriptionPrev);
		}
		if (!locationCol_txt.getText().equals("")) {
			locationColIndex = Integer.parseInt(locationCol_txt.getText().trim()) - 1;
			reader.setCellNum(locationColIndex);
			cellValue = reader.run();

			currentEvent.setLocation(cellValue);
		}
		reader.setRowNum(++startingRow);
		GoogleEventList.addEvent(currentEvent);
	}

	private void initializeExcelReader() {
		String sheetText = MainFormSavedData.exportSheetName();
		reader.setFileLocation(MainFormSavedData.exportFilePath());
		reader.setSheetName(sheetText);
	}

	private void insertToGoogleCalendar() {
		if (GoogleEventList.getNumOfEvents() != 0) {
			int listSize = GoogleEventList.getList().size();
			if (listSize > 0) {
				GoogleCalendar.setListOfEvents(GoogleEventList.getList());
			} else
				setErrorMsg("There are Zero Events");
		} else
			setErrorMsg("There are Zero Events");
		try {

			GoogleCalendar.upload();
		} catch (IOException e) {
			e.printStackTrace();
			setErrorMsg("Error in the\n:" + e.getMessage());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			setErrorMsg("Error in the\n:" + e.getMessage());
		}
	}

	@FXML
	private void openFileExplorer(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose the Excel File");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		if (file.getPath() == null) {
			return;
		}
		fileLocation = file.getPath();
		if (!fileLocation.equals("") && fileLocation.contains(".xlsx")) {
			clearErrorMsg();
			reader.setFileLocation(fileLocation);
			fileStatus_txt.setText("File Obtained!");
			fillSheetCmb();

		} else if (!fileLocation.contains("xlsx")) {
			setErrorMsg("File chosen is not an \"xlsx\" file, please select another file.");
		}
	}

	private void fillSheetCmb() {
		cb_sheets.getItems().addAll(reader.getSheetNames());
	}

	public void initialize(URL location, ResourceBundle resources) {
		initializeDateComboBoxes();
		initializeTimeComboBoxes();
		if (MainFormSavedData.hasValues()) {
			initializeFormValues();
		}
	}

	private void initializeFormValues() {
		List<List<String>> listOfValues = MainFormSavedData.exportData();

		List<String> txtFieldValues = listOfValues.get(0);
		List<String> cmbValues = listOfValues.get(1);

		List<TextField> txtFieldList = new ArrayList<TextField>(
				Arrays.asList(rowsBeginning_text, rowsEnding_text, titleCol_txt, startDateCol_txt, endDateCol_txt,
						startTime_txt, endTime_txt, descriptionCol_txt, secondDescriptionCol_txt, locationCol_txt));

		for (int i = 0; i < txtFieldList.size(); i++) {
			txtFieldList.get(i).setText(txtFieldValues.get(i));
		}

		List<ComboBox<String>> cmbList = new ArrayList<ComboBox<String>>(
				Arrays.asList(cb_StartDate, cb_EndDate, cb_startTime, cb_endTime, cb_sheets));

		for (int i = 0; i < cmbList.size(); i++) {
			cmbList.get(i).setValue(cmbValues.get(i));
		}
	}

	public void setErrorMsg(String message) {
		warning_Area.applyCss();
		warning_Area.setText(message);
	}

	public void clearErrorMsg() {
		warning_Area.setText("");
	}

	private void initializeTimeComboBoxes() {
		List<String> cbOptions = Arrays.asList("HH AM/PM", "HH:MM AM/PM");

		cb_startTime.getItems().addAll(cbOptions);
		cb_endTime.getItems().addAll(cbOptions);
	}

	private void initializeDateComboBoxes() {
		List<String> cbOptions = Arrays.asList("DD/MM/YYYY", "MM/DD/YYYY", "YYYY/MM/DD", "DD-MM-YYYY", "MM-DD-YYYY",
				"YYYY-MM-DD");

		cb_StartDate.getItems().addAll(cbOptions);
		cb_EndDate.getItems().addAll(cbOptions);
	}
}
