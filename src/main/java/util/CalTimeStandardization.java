package util;

public class CalTimeStandardization {

	public static String convert(String timeFormat, String time) {
		String result = null;
		System.out.println("================================================================");
		System.out.println("/nThe time format in caltimestandardization is: " + timeFormat);
		System.out.println("================================================================");
		time.toUpperCase();

		// String def = "08:00 AM";

		String correctPattern = "[0-1]\\d:[0-5]\\d AM";
		String correctPattern2 = "[0-1]\\d:[0-5]\\d PM";
		if (time.matches(correctPattern) || time.matches(correctPattern2)) {
			return time;
		}

//the issue comes when the time is in what u wanted
		if (timeFormat.equals("HH AM/PM")) {
			String[] components = time.split(" ");

			if (components[0].length() == 1) {
				String hour = "0" + components[0];
				result = hour + ":00 " + components[1];
				return result;
			}
			result = components[0] + ":00" + components[1];
			return result;
		} else if (timeFormat.equals("HH:MM AM/PM")) {
			/*
			 * String[] components = time.split(":"); if (components[0].length() == 1) {
			 * String hour = "0" + components[0]; result = hour + ":" + components[1]; }
			 * System.out.println("caltimestandardization the final result is: " + result);
			 */
			return result;
		}
		System.out.println("The timeformat is: " + timeFormat + "The time is: " + time);

		return result;
	}
}
