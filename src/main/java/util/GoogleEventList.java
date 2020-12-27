package util;

import java.util.ArrayList;
import java.util.List;

public class GoogleEventList {

	private static List<GoogleEvent> listOfGoogleEvents = new ArrayList<GoogleEvent>();
	private static int numOfEvents = 0;
	private static String emailNotification;
	private static String notification;
	private static String visibility;
	private static String transparency;
	private static String description;

	public static void addEvent(GoogleEvent event) {
		listOfGoogleEvents.add(event);
		numOfEvents++;
	}

	public static List<GoogleEvent> getList() {
		return listOfGoogleEvents;
	}

	public static int getNumOfEvents() {
		return numOfEvents;
	}

	public static void createEvents(int numOfEvents) {
		for (int i = 0; i < numOfEvents; i++) {
			GoogleEvent event = new GoogleEvent();
			addEvent(event);
		}
	}

	public static void setEmailNotification(String emailNotif) {
		emailNotification = emailNotif;
	}

	public static void setNotification(String notif) {
		notification = notif;
	}

	public static void setVisibility(String visibil) {
		visibility = visibil;
	}

	public static void setTransparency(String transp) {
		transparency = transp;
	}

	public static void setDescription(String descrip) {
		description = descrip;
	}

	public static void insertAdvOptionToEvents() {
		if (isNotNull(description))
			listOfGoogleEvents.stream().forEach(entry -> entry.setDescription(description));
		if (isNotNull(emailNotification))
			listOfGoogleEvents.stream().forEach(entry -> entry.setEmailNotif(emailNotification));
		if (isNotNull(notification))
			listOfGoogleEvents.stream().forEach(entry -> entry.setNotif(notification));
		if (isNotNull(transparency))
			listOfGoogleEvents.stream().forEach(entry -> entry.setTransparency(transparency));
		if (isNotNull(visibility))
			listOfGoogleEvents.stream().forEach(entry -> entry.setVisibility(visibility));
	}

	private static boolean isNotNull(String entry) {
		if (entry != null)
			return true;
		else
			return false;
	}

	public static void clear() {
		listOfGoogleEvents = new ArrayList<GoogleEvent>();
		numOfEvents = 0;
	}
}
