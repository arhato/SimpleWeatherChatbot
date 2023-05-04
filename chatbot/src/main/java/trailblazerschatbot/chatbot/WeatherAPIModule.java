package trailblazerschatbot.chatbot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.Date;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class WeatherAPIModule {

	public ArrayList<Object> weatherAPI(String location, String day) {
		// https://api.openweathermap.org/data/2.5/weather?q=cork,ie&appid=0219cd5cd854de517fe7720f70c8da25&units=metric
		// remove any whitespce from the location
		location = location.replaceAll("\\s+ ", "");

		// invoke method dayOfTheWeek to get a usable integer
		int dayNum = dayOfTheWeek(day);

		ArrayList<Object> returnArray = new ArrayList<>();

		// This try catch block is used to catch any exceptions that may occur
		// All the necessary methods are called in this block
		// User input is also taken in this block in a loop until they decide to exit

		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + location
					+ "&appid=0219cd5cd854de517fe7720f70c8da25&units=metric&cnt=" + 10);
			int responseCode = establishConnection(url);

			JSONArray list = (JSONArray) (getData(url, responseCode).get("list"));
			JSONObject data = getData(url, responseCode);

			returnArray.addAll(giveWeather(list, data, dayNum));

		} catch (Exception e) {
			e.printStackTrace();

		}
		return returnArray;
	}

	// This method is used to establish a connection with the API
	// The URL is passed as a parameter
	// The method then establishes a connection with the API and returns the
	// response code

	public static int establishConnection(URL url) throws Exception {

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		return connection.getResponseCode();
	}

	// This method is used to get the JSON data from the API
	// The URL is passed as a parameter
	// The method then gets the JSON data from the API and returns it as a
	// JSONObject

	public static JSONObject getData(URL url, int responseCode) throws Exception {
		if (responseCode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responseCode);
		} else {

			StringBuilder info = new StringBuilder();
			Scanner sc = new Scanner(url.openStream());

			while (sc.hasNext()) {
				info.append(sc.nextLine());
			}
			sc.close();

			JSONParser parse = new JSONParser();
			Object obj = parse.parse(String.valueOf(info));
			JSONArray data = new JSONArray();
			data.add(obj);

			return (JSONObject) data.get(0);
		}
	}

	// This method is used to call the required methods based on the user input
	// The user input is passed as a parameter.
	// The method then calls the required method based on the user input
	// The method also handles any exceptions that may occur

	public static ArrayList<Object> giveWeather(JSONArray list, JSONObject data, int days) {
		ArrayList<Object> arr = new ArrayList<>();
		arr.add(getTemp(days, list));

		arr.add(getWeather(days, list));
		arr.add(getWeatherDescription(days, list));

		return arr;
	}

	// Getters; the JSON data is parsed and the required data is extracted and
	// printed
	// The data is extracted from the JSON file using the index of the JSON objects
	// and arrays
	// The data is then printed
	public static Object getTemp(int day, JSONArray list) {
		JSONObject index = (JSONObject) list.get(day);
		JSONObject main = (JSONObject) index.get("main");
		return (main.get("temp"));
	}

	public static Object getWeather(int day, JSONArray list) {
		JSONObject index = (JSONObject) list.get(day);
		JSONArray weather = (JSONArray) index.get("weather");
		JSONObject weather0 = (JSONObject) weather.get(0);
		return (weather0.get("main"));
	}

	public static Object getWeatherDescription(int day, JSONArray list) {
		JSONObject index = (JSONObject) list.get(day);
		JSONArray weather = (JSONArray) index.get("weather");
		JSONObject weather0 = (JSONObject) weather.get(0);
		return (weather0.get("description"));
	}

	public static int dayOfTheWeek(String d) {
		if (d != null && d.length() > 0 && Character.isDigit(d.charAt(0))) {
			// if the input string starts with a digit, assume it's a number and return it
			// as is
			return Integer.parseInt(d);
		} else {
			// get today's day in int
			DayOfWeek today = LocalDate.now().getDayOfWeek();
			// Normalise today to 0
			int todayValue = today.getValue() % 7;

			DayOfWeek inputDay;
			// use switch with a lowercase and not whitespaces
			switch (d.toLowerCase().replaceAll("\\s+", "")) {
			case "today":
				inputDay = today;
				break;
			case "tomorrow":
				inputDay = today.plus(1);
				break;
			case "dayaftertomorrow":
				inputDay = today.plus(2);
				break;
			case "sunday":
				inputDay = DayOfWeek.SUNDAY;
				break;
			case "monday":
				inputDay = DayOfWeek.MONDAY;
				break;
			case "tuesday":
				inputDay = DayOfWeek.TUESDAY;
				break;
			case "wednesday":
				inputDay = DayOfWeek.WEDNESDAY;
				break;
			case "thursday":
				inputDay = DayOfWeek.THURSDAY;
				break;
			case "friday":
				inputDay = DayOfWeek.FRIDAY;
				break;
			case "saturday":
				inputDay = DayOfWeek.SATURDAY;
				break;
			default:
				throw new IllegalArgumentException("Invalid input: " + d);
			}

			// normalize the int from the input day
			int inputDayValue = inputDay.getValue() % 7;
			// get difference
			int daysDiff = (inputDayValue + 7 - todayValue) % 7;
			// return result
			return daysDiff;
		}
	}

	public static String getClothing(Object object) {
		String clothingRecommendation = "";
		String weatherCondition =  object.toString();
		switch (weatherCondition) {
		case "snow":
		case "shower snow":
		case "light shower snow":
		case "heavy shower snow":
		case "rain and snow":
		case "light rain and snow":
		case "heavy snow":
		case "light snow":
			clothingRecommendation = "Wear warm and waterproof clothing, boots, gloves, and a hat";
			break;
		case "drizzle":
		case "shower drizzle":
		case "heavy shower rain and drizzle":
		case "shower rain and drizzle":
		case "heavy intensity drizzle rain":
		case "drizzle rain":
		case "light intensity drizzle rain":
		case "heavy intensity drizzle":
		case "light intensity drizzle":
		case "thunderstorm with heavy drizzle":
		case "thunderstorm with drizzle":
		case "thunderstorm with light drizzle":
			clothingRecommendation = "Wear a light waterproof jacket and waterproof shoes";
			break;
		case "thunderstorm":
		case "ragged thunderstorm":
		case "heavy thunderstorm":
		case "light thunderstorm":
		case "thunderstorm with heavy rain":
		case "thunderstorm with rain":
		case "thunderstorm with light rain":
			clothingRecommendation = "Avoid wearing metal jewelry and seek shelter indoors. If outside, wear waterproof clothing and shoes";
			break;
		case "clear sky":
			clothingRecommendation = "Wear light clothing, a hat, and sunglasses";
			break;
		case "few clouds":
		case "scattered clouds":
		case "broken clouds":
		case "overcast clouds":
			clothingRecommendation = "Wear light clothing and bring a light jacket in case it gets cooler";
			break;
		case "shower rain":
		case "light intensity shower rain":
		case "heavy intensity shower rain":
			clothingRecommendation = "Wear a waterproof jacket and shoes";
			break;
		case "freezing rain":
			clothingRecommendation = "Wear warm and waterproof clothing, boots, gloves, and a hat";
			break;
		case "extreme rain":
		case "very heavy rain":
		case "heavy intensity rain":
		case "moderate rain":
		case "light rain":
			clothingRecommendation = "Wear a waterproof jacket and shoes";
			break;
		case "mist":
		case "fog":
		case "haze":
		case "smoke":
			clothingRecommendation = "Wear light clothing and bring a light jacket in case it gets cooler";
			break;
		case "sand":
		case "dust":
		case "sand/dust whirls":
		case "volcanic ash":
		case "tornado":
		case "squalls":
			clothingRecommendation = "Wear protective clothing such as goggles, a mask, and a hat";
			break;
		default:
			clothingRecommendation = "Unable to provide a clothing recommendation for this weather condition";
			break;
		}

		return clothingRecommendation;
	}
}
