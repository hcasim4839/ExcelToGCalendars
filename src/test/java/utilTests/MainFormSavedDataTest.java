package utilTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import util.MainFormSavedData;

public class MainFormSavedDataTest {
	@Test
	@DisplayName("Test For hasValue Method w/No Values")
	void test1() {
		boolean acutalz = MainFormSavedData.hasValues();
		boolean actual = acutalz;
		boolean expected = false;

		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Test For Import & export")
	void test2() {
		List<String> txtFieldList = Arrays.asList("info", "blurp");
		List<String> cmbList = Arrays.asList("blue", "blurple");
		List<String> filePathList = Arrays.asList("/users/HoracioCasimiro");

		MainFormSavedData.insertData(txtFieldList, cmbList);

		System.out.println("The export is :" + MainFormSavedData.exportData());
		List<List<String>> expected = new ArrayList<List<String>>();
		expected.add(txtFieldList);
		expected.add(cmbList);
		expected.add(filePathList);

		List<List<String>> actual = MainFormSavedData.exportData();
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Test For hasValue w/Values")
	void test3() {
		List<String> txtFieldList = Arrays.asList("info", "blurp");
		List<String> cmbList = Arrays.asList("blue", "blurple");
		List<String> filePathList = Arrays.asList("/users/HoracioCasimiro");

		MainFormSavedData.insertData(txtFieldList, cmbList);

		boolean acutalz = MainFormSavedData.hasValues();
		boolean actual = acutalz;
		boolean expected = true;

		assertEquals(expected, actual);
	}

}
