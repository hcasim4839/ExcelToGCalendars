package utilTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import util.MoreOptionsFormSavedData;

public class MoreOptionsFormSavedDataTest {

	@Test
	@DisplayName("Test For MoreOptionsSD method clear w/Values")
	void test0() {
		List<String> txtFieldData = new ArrayList<String>();
		List<String> cmbData = new ArrayList<String>();
		List<String> txtAreaData = new ArrayList<String>();

		txtFieldData.add("firstEntry");
		cmbData.add("secondEntry");
		txtAreaData.add("thirdEntry");

		MoreOptionsFormSavedData.insertData(txtFieldData, cmbData, txtAreaData);
		MoreOptionsFormSavedData.clear();
		List<List<String>> list = MoreOptionsFormSavedData.exportData();

		boolean actual = list == null ? true : list.isEmpty();
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Test For MoreOptionsSD method clear w/Values")
	void test1() {
		List<String> txtFieldData = new ArrayList<String>();
		List<String> cmbData = new ArrayList<String>();
		List<String> txtAreaData = new ArrayList<String>();

		txtFieldData.add("firstEntry");
		cmbData.add("secondEntry");
		txtAreaData.add("thirdEntry");

		MoreOptionsFormSavedData.insertData(txtFieldData, cmbData, txtAreaData);

		List<List<String>> list = MoreOptionsFormSavedData.exportData();

		boolean actual = list == null ? true : list.isEmpty();
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Test For MoreOptionsSD method hasValues w/ no value")
	void test2() {
		List<String> txtFieldData = new ArrayList<String>();
		List<String> cmbData = new ArrayList<String>();
		List<String> txtAreaData = new ArrayList<String>();

		MoreOptionsFormSavedData.insertData(txtFieldData, cmbData, txtAreaData);

		boolean actual = MoreOptionsFormSavedData.hasValues();
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Test For MoreOptionsSD method hasValues w/ value")
	void test3() {
		List<String> txtFieldData = new ArrayList<String>();
		List<String> cmbData = new ArrayList<String>();
		List<String> txtAreaData = new ArrayList<String>();

		txtFieldData.add("firstEntry");
		cmbData.add("secondEntry");
		txtAreaData.add("thirdEntry");

		MoreOptionsFormSavedData.insertData(txtFieldData, cmbData, txtAreaData);

		boolean actual = MoreOptionsFormSavedData.hasValues();
		boolean expected = true;
		assertEquals(expected, actual);
	}

}
