package chatBot.weatherAPI;


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
				"https://api.openweathermap.org/data/2.5/weather?q=cork,ie&appid=0219cd5cd854de517fe7720f70c8da25&units=metric");
		// get response code
		 testResponseCode = WeatherAPIModule.establishConnection(testUrl);
		testList = (JSONArray) (WeatherAPIModule.getData(testUrl, testResponseCode).get("list"));
		testData = WeatherAPIModule.getData(testUrl, testResponseCode);
		System.out.println(testData);
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
		Assertions.assertTrue(testData.containsKey("main"));
	}

	@Test
	void testGetTemp() {
		WeatherAPIModule.getTemp(0,testList);
	}

	@Test
	void testGetFeelsLike() {
		WeatherAPIModule.getFeelsLike(0,testList);
	}

	@Test
	void testGetMinTemp() {
		WeatherAPIModule.getMinTemp(0,testList);
	}

	@Test
	void testGetMaxTemp() {
		WeatherAPIModule.getMaxTemp(0,testList);
	}

	@Test
	void testGetPressure() {
		WeatherAPIModule.getPressure(0,testList);
	}

	@Test
	void testGetHumidity() {
		WeatherAPIModule.getHumidity(0,testList);
	}

	@Test
	void testGetWeather() {
		WeatherAPIModule.getWeather(0,testList);
	}

	@Test
	void testGetWeatherDescription() {
		WeatherAPIModule.getWeatherDescription(0,testList);
	}

	@Test
	void testGetClouds() {
		WeatherAPIModule.getClouds(0,testList);
	}

	@Test
	void testGetWindSpeed() {
		WeatherAPIModule.getWindSpeed(0,testList);
	}

	@Test
	void testGetWindDirection() {
		WeatherAPIModule.getWindDirection(0,testList);
	}

	@Test
	void testGetVisibility() {
		WeatherAPIModule.getVisibility(0,testList);
	}

	@Test
	void testGetSunRise() {
		WeatherAPIModule.getSunRise(testData);
	}

	@Test
	void testGetSunSet() {
		WeatherAPIModule.getSunSet(testData);
	}

	@Test
	void testGetRecorded() {
		WeatherAPIModule.getRecorded(0,testList);
	}


}
