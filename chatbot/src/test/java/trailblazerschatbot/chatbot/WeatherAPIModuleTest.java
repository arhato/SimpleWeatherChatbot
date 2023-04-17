package trailblazerschatbot.chatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeatherAPIModuleTest {

	private URL testUrl;
	private int testResponseCode;
	private JSONArray testList = new JSONArray();
	JSONObject testData = new JSONObject();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// new url for test
		testUrl = new URL(
				"https://api.openweathermap.org/data/2.5/forecast?q=cork,ie&appid=0219cd5cd854de517fe7720f70c8da25&units=metric&cnt=10");
		// get response code
		testResponseCode = WeatherAPIModule.establishConnection(testUrl);
		testList = (JSONArray) (WeatherAPIModule.getData(testUrl, testResponseCode).get("list"));
		testData = WeatherAPIModule.getData(testUrl, testResponseCode);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testEstablishConnection() throws Exception {
		// check if responsecode in 200/OK
		Assertions.assertEquals(200, testResponseCode);
	}

	@Test
	void testGetData() throws Exception {
		// assert if the data has one of the key
		Assertions.assertTrue(testData.containsKey("list"));
	}

	@Test
	void testGiveWeather() {
		final int input = 1;
		int days = 0;

		// Test case 1: valid input
		assertDoesNotThrow(() -> WeatherAPIModule.giveWeather(testList, testData, input, days));

		// Test case 2: invalid input
		final int secondInput = 10;
		assertThrows(Exception.class, () -> WeatherAPIModule.giveWeather(testList, testData, secondInput, days));
	}

	@Test
	void testGetters() {
		int day = 0;

		// Test case 1: valid input
		assertDoesNotThrow(() -> {
			WeatherAPIModule.getTemp(day, testList);
			WeatherAPIModule.getFeelsLike(day, testList);
			WeatherAPIModule.getMinTemp(day, testList);
			WeatherAPIModule.getMaxTemp(day, testList);
			WeatherAPIModule.getPressure(day, testList);
			WeatherAPIModule.getHumidity(day, testList);
			WeatherAPIModule.getWeather(day, testList);
			WeatherAPIModule.getWeatherDescription(day, testList);
			WeatherAPIModule.getClouds(day, testList);
			WeatherAPIModule.getWindSpeed(day,testList);
			WeatherAPIModule.getWindDirection(day, testList);
			WeatherAPIModule.getVisibility(day, testList);
			WeatherAPIModule.getSunRise(testData);
			WeatherAPIModule.getSunSet(testData);
			WeatherAPIModule.getRecorded(day, testList);
		});

		// Test case 2: invalid input
		assertThrows(IndexOutOfBoundsException.class, () -> WeatherAPIModule.getTemp(-1, testList));
	}

	@Test
	public void testDayOfTheWeek() {
		assertEquals(0, WeatherAPIModule.dayOfTheWeek("today"));
		assertEquals(1, WeatherAPIModule.dayOfTheWeek("tomorrow"));
		assertEquals(2, WeatherAPIModule.dayOfTheWeek("dayaftertomorrow"));
	}
}
