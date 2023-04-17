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
			System.out.println("Select what you want to see: ");

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
			System.out.println(info);

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

	public static  ArrayList<Object> giveWeather(JSONArray list, JSONObject data, int days) {
		ArrayList<Object> arr=new ArrayList<>();
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
		return  (main.get("temp"));
	}

//	public void getFeelsLike(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject main = (JSONObject) index.get("main");
//		System.out.println("Feels like: " + main.get("feels_like") + " °C");
//	}
//
//	public static void getMinTemp(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject main = (JSONObject) index.get("main");
//		System.out.println("Min: " + main.get("temp_min") + " °C");
//	}
//
//	public static void getMaxTemp(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject main = (JSONObject) index.get("main");
//		System.out.println("Max: " + main.get("temp_max") + " °C");
//	}
//
//	public static void getPressure(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject main = (JSONObject) index.get("main");
//		System.out.println("Pressure: " + main.get("pressure") + " hPa");
//	}
//
//	public static void getHumidity(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject main = (JSONObject) index.get("main");
//		System.out.println("Humidity: " + main.get("humidity") + " %");
//	}

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
		return(weather0.get("description"));
	}

//	public static void getClouds(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject clouds = (JSONObject) index.get("clouds");
//		System.out.println("Cloud: " + clouds.get("all") + " %");
//	}
//
//	public static void getWindSpeed(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject wind = (JSONObject) index.get("wind");
//		System.out.println("Wind speed: " + wind.get("speed") + " m/s");
//	}
//
//	public static void getWindDirection(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		JSONObject wind = (JSONObject) index.get("wind");
//		System.out.println("Wind direction: " + wind.get("deg") + "°");
//	}
//
//	public static void getVisibility(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		System.out.println("Visibility: " + index.get("visibility") + " m");
//	}
//
//	public static void getSunRise(JSONObject data) {
//		JSONObject city = (JSONObject) data.get("city");
//		Date date = new Date((long) city.get("sunrise") * 1000);
//		System.out.println("Sunrise: " + date);
//	}
//
//	public static void getSunSet(JSONObject data) {
//		JSONObject city = (JSONObject) data.get("city");
//		Date date = new Date((long) city.get("sunset") * 1000);
//		System.out.println("Sunset: " + date);
//	}
//
//	public static void getRecorded(int day, JSONArray list) {
//		JSONObject index = (JSONObject) list.get(day);
//		System.out.println("Recorded: " + index.get("dt_txt"));
//	}

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
			switch (d.toLowerCase().replaceAll("\\s+ ", "")) {
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
}
