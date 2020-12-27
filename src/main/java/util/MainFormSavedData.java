package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFormSavedData {
	private static List<String> txtFieldDataValueList;
	private static List<String> cmbDataValueList;
	private static String filePath;

	public static void insertData(List<String> txtFieldList, List<String> cmbList) {
		txtFieldDataValueList = new ArrayList<String>();
		cmbDataValueList = new ArrayList<String>();

		saveValue(txtFieldDataValueList, txtFieldList);
		saveValue(cmbDataValueList, cmbList);
	}

	private static void saveValue(List<String> valueListHolder, List<String> valueList) {
		List<String> dataholder = valueListHolder;
		valueList.stream().forEach(entry -> dataholder.add(entry));
	}

	public static List<List<String>> exportData() {
		Boolean txtFieldListHasValues = txtFieldDataValueList == null || txtFieldDataValueList.isEmpty() == true ? false
				: true;
		Boolean cmbListHasValues = cmbDataValueList == null || cmbDataValueList.isEmpty() == true ? false : true;

		if (txtFieldListHasValues || cmbListHasValues) {
			List<List<String>> resultList = Arrays.asList(txtFieldDataValueList, cmbDataValueList);
			return resultList;
		}
		return null;
	}

	public static String exportSheetName() {
		return cmbDataValueList.get(cmbDataValueList.size() - 1);
	}

	public static boolean hasValues() {
		Boolean txtFieldListHasValues = txtFieldDataValueList == null || txtFieldDataValueList.isEmpty() == true ? false
				: true;
		Boolean cmbListHasValues = cmbDataValueList == null || cmbDataValueList.isEmpty() == true ? false : true;
		if (txtFieldListHasValues && cmbListHasValues)
			return true;
		else
			return false;
	}

	public static void clear() {
		txtFieldDataValueList = null;
		cmbDataValueList = null;
		filePath = null;

	}

	public static void insertFilePath(String fileLocation) {
		filePath = fileLocation;
	}

	public static String exportFilePath() {
		return filePath;
	}
}
