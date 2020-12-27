package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private String fileLocation;
	private String sheetName;
	private int rowNumber;
	private int cellNumber;

	public String run() {
		if (fileLocation.isEmpty() == false || fileLocation != null) {
			try {
				FileInputStream file = new FileInputStream(fileLocation);

				Workbook workbook = new XSSFWorkbook(file);
				Sheet sheet = workbook.getSheet(sheetName);

				Row row = sheet.getRow(rowNumber);
				Cell cell = row.getCell(cellNumber);

				DataFormatter formatter = new DataFormatter();

				String cellValue = formatter.formatCellValue(cell);
				workbook.close();
				return cellValue;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				String warningMessage = "Error with file inputted\nDetails: " + e.getMessage();
				return warningMessage;
			}
		}
		return null;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public List<String> getSheetNames() {
		FileInputStream file;
		List<String> listOfSheets = new ArrayList<String>();
		try {
			file = new FileInputStream(fileLocation);
			Workbook wb = new XSSFWorkbook(file);
			int numOfSheets = wb.getNumberOfSheets();

			for (int i = 0; i < numOfSheets; i++) {
				listOfSheets.add(wb.getSheetName(i));
			}
			wb.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfSheets;

	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setRowNum(int rowNum) {
		this.rowNumber = rowNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNumber = cellNum;
	}
}