package util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeZoneConverter {
	private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm a";
	private static final int DATE_TIME_SEPERATOR_INDEX = DATE_FORMAT.indexOf(" ");
	private static final int First_Hour_Index = DATE_FORMAT.indexOf("h");
	private static final int Second_Hour_Index = DATE_FORMAT.indexOf("h", First_Hour_Index + 1);

	private static StringBuilder sb;

	public static String toUTC(String dateTime) {
		String result = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		System.out.println(dateTime);
		LocalDateTime eventTime = LocalDateTime.parse(dateTime, formatter);

		ZoneId userZoneId = ZoneId.systemDefault();
		ZoneOffset zoneOffSet = userZoneId.getRules().getOffset(eventTime);
		System.out.println("the utc offset is: " + zoneOffSet);

		sb = changeToMilitaryTime(dateTime);

		sb.replace(DATE_TIME_SEPERATOR_INDEX, DATE_TIME_SEPERATOR_INDEX, "T");

		sb.append(":00" + zoneOffSet.toString());

		result = sb.toString();
		result = result.replaceAll("\\s", "");
		System.out.println("result: " + result);
		return result;

	}

	private static StringBuilder changeToMilitaryTime(String dateTime) {
		StringBuilder result = null;
		if (dateTime.contains("AM")) {
			sb = new StringBuilder(dateTime);
			sb.delete(dateTime.indexOf("A"), dateTime.indexOf("M") + 1);

			return sb;
		} else if (dateTime.contains("PM")) {
			sb = new StringBuilder(dateTime);
			String num = sb.subSequence(First_Hour_Index, Second_Hour_Index + 1).toString();

			int hourInt = Integer.parseInt(num);
			hourInt += 12;

			sb.delete(dateTime.indexOf("P"), dateTime.indexOf("M") + 1);

			return sb.replace(First_Hour_Index, Second_Hour_Index + 1, Integer.toString(hourInt));
		}
		return result;

	}
	// https://www.baeldung.com/java-time-zones
}
