package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MoreOptionsFormSavedData {
	private static List<String> txtFieldDataValueList;
	private static List<String> cmbDataValueList;
	private static List<String> txtAreaDataValueList;

	public static void insertData(List<String> txtFieldData, List<String> cmbData, List<String> txtAreaData) {
		saveTxtFieldValue(txtFieldData);
		saveCmbValue(cmbData);
		saveTxtAreaValue(txtAreaData);

	}

	private static void saveTxtAreaValue(List<String> txtAreaData) {
		txtAreaDataValueList = new ArrayList<String>();
		Stream<String> stream = txtAreaData.stream();

		stream.forEach(entry -> txtAreaDataValueList.add(entry));
	}

	private static void saveCmbValue(List<String> cmbData) {
		cmbDataValueList = new ArrayList<String>();
		Stream<String> stream = cmbData.stream();

		stream.forEach(entry -> cmbDataValueList.add(entry));
	}

	private static void saveTxtFieldValue(List<String> txtFieldData) {
		txtFieldDataValueList = new ArrayList<String>();
		Stream<String> stream = txtFieldData.stream();

		stream.forEach(entry -> txtFieldDataValueList.add(entry));
	}

	public static List<List<String>> exportData() {
		Boolean txtFieldListHasValues = txtFieldDataValueList == null || txtFieldDataValueList.isEmpty() == true ? false
				: true;
		Boolean cmbListHasValues = cmbDataValueList == null || cmbDataValueList.isEmpty() == true ? false : true;
		Boolean txtAreaListHasValues = txtAreaDataValueList == null || txtAreaDataValueList.isEmpty() == true ? false
				: true;

		if (txtFieldListHasValues || cmbListHasValues || txtAreaListHasValues) {
			List<List<String>> resultList = Arrays.asList(txtFieldDataValueList, cmbDataValueList,
					txtAreaDataValueList);
			return resultList;
		}
		return null;
	}

	public static boolean hasValues() {
		Boolean txtFieldListHasValues = txtFieldDataValueList == null || txtFieldDataValueList.isEmpty() == true ? false
				: true;
		Boolean cmbListHasValues = cmbDataValueList == null || cmbDataValueList.isEmpty() == true ? false : true;
		Boolean txtAreaListHasValues = txtAreaDataValueList == null || txtAreaDataValueList.isEmpty() == true ? false
				: true;

		if (txtFieldListHasValues || cmbListHasValues || txtAreaListHasValues) {
			return true;
		} else
			return false;
	}

	public static void clear() {
		txtFieldDataValueList = null;
		cmbDataValueList = null;
		txtAreaDataValueList = null;
	}

}
