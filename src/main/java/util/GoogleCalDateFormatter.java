package util;

public class GoogleCalDateFormatter {
	private static final String DATE_SEPERATOR = "-";

	public static String convert(String date, String dateFormat) {

		if (dateFormat.equals("DD/MM/YYYY")) {
			String[] dateComponents = date.split("/");
			return new String(
					dateComponents[2] + DATE_SEPERATOR + dateComponents[1] + DATE_SEPERATOR + dateComponents[0]);
		} else if (dateFormat.equals("MM/DD/YYYY")) {
			String[] dateComponents = date.split("/");
			return new String(
					dateComponents[2] + DATE_SEPERATOR + dateComponents[0] + DATE_SEPERATOR + dateComponents[1]);
		} else if (dateFormat.equals("YYYY/MM/DD")) {
			String[] dateComponents = date.split("/");
			return new String(
					dateComponents[0] + DATE_SEPERATOR + dateComponents[1] + DATE_SEPERATOR + dateComponents[2]);
		} else if (dateFormat.equals("DD-MM-YYYY")) {
			String[] dateComponents = date.split("-");
			return new String(
					dateComponents[2] + DATE_SEPERATOR + dateComponents[0] + DATE_SEPERATOR + dateComponents[1]);
		} else if (dateFormat.equals("MM-DD-YYYY")) {
			String[] dateComponents = date.split("-");
			return new String(
					dateComponents[2] + DATE_SEPERATOR + dateComponents[0] + DATE_SEPERATOR + dateComponents[1]);
		} else if (dateFormat.equals("YYYY-MM-DD")) {
			return date;
		}

		return null;

	}

}
