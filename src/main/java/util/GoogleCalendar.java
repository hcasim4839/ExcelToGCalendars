package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Colors;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendar {

	private static List<GoogleEvent> listOfGoogleEvents;

	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String ZONE_ID = ZoneId.systemDefault().toString();
	private static Calendar service;

	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
	// problem the resource is not found when created to exe as the file structure
	// seems to have changed it works when the credential file path is
	// /resources/credentials.json to make it work on an ide use path
	// /credentials.json
	// https://stackoverflow.com/questions/6192661/how-to-reference-a-resource-file-correctly-for-jar-and-debugging
	private static final String CREDENTIALS_FILE_PATH = "/resources/credentials.json";

	public static void setListOfEvents(List<GoogleEvent> googleEventlist) {
		listOfGoogleEvents = googleEventlist;
	}

	public static List<String> getCalendarColors() {
		try {
			Colors colors = service.colors().get().execute();
			List<String> list = new ArrayList<String>();

			for (Entry<String, Object> color : colors.entrySet()) {
				System.out.println("ColorId : " + color.getKey());
				list.add(color.getKey());
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public static void startService() throws GeneralSecurityException, IOException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
	}

	public static void upload() throws IOException, GeneralSecurityException {

		String startDateTime;
		String utcStartDateTime;
		String endDateTime;
		String utcEndDateTime;
		DateTime dateTime;

		GoogleEventList.insertAdvOptionToEvents();
		GoogleEventList.getList().stream().forEach(System.out::println);

		for (int i = 0; i < listOfGoogleEvents.size(); i++) {
			System.out.println("The size of google events is: " + listOfGoogleEvents.size());
			listOfGoogleEvents.stream().forEach(e -> System.out.println(e));

			// Creating a new Event
			GoogleEvent currentEvent = listOfGoogleEvents.get(i);
			// The hour is in UTC format
			// Utc is 5 hours ahead of est time

			startDateTime = currentEvent.getStartDate() + " " + currentEvent.getStartTime();
			System.out.println("the start date and time is " + startDateTime);
			utcStartDateTime = TimeZoneConverter.toUTC(startDateTime);
			dateTime = new DateTime(utcStartDateTime);

			EventDateTime startTime = new EventDateTime().setDateTime(dateTime).setTimeZone(ZONE_ID);

			endDateTime = currentEvent.getEndDate() + " " + currentEvent.getEndTime();
			utcEndDateTime = TimeZoneConverter.toUTC(endDateTime);
			dateTime = new DateTime(utcEndDateTime);

			String visibilityValue = currentEvent.getVisibility() == null ? "default" : currentEvent.getVisibility();

			String transparencyValue = currentEvent.getTransparency() == "Busy" ? "opaque" : "transparent";
			EventDateTime endTime = new EventDateTime().setDateTime(dateTime).setTimeZone(ZONE_ID);

			Event newEvent = new Event().setSummary(currentEvent.getTitle())
					.setDescription(currentEvent.getDescription()).setEnd(endTime).setStart(startTime)
					.setLocation(currentEvent.getLocation()).setDescription(currentEvent.getDescription())
					.setVisibility(visibilityValue).setTransparency(transparencyValue);
			System.out.println("the event's name is " + currentEvent.getTitle());
			String calendarId = "primary";
			newEvent = service.events().insert(calendarId, newEvent).execute();
		}
	}

	public static void deleteGmailAcct() {
		// look up class.getResource vs paths.get!
		Path path = Paths.get("./tokens/StoredCredential");
		File filePath = path.toFile();
		Boolean fileStatus = filePath.delete();

		System.out.println("The file is deleted: " + fileStatus);

	}

}
