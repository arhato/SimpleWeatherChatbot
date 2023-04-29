package trailblazerschatbot.chatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

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
	public void testWeatherAPI() {
		ArrayList<Object> result = WeatherAPIModule.weatherAPI("cork", "tuesday");
		
		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.get(0) instanceof Double);
		assertTrue(result.get(1) instanceof String);
		assertTrue(result.get(2) instanceof String);
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
	    public void testGiveWeather() {
	        JSONArray list = new JSONArray();
	        JSONObject index = new JSONObject();
	        JSONObject main = new JSONObject();
	        main.put("temp", 20);
	        index.put("main", main);
	        JSONArray weather = new JSONArray();
	        JSONObject weather0 = new JSONObject();
	        weather0.put("main", "Sunny");
	        weather0.put("description", "Clear skies");
	        weather.add(weather0);
	        index.put("weather", weather);
	        list.add(index);
	        JSONObject data = new JSONObject();
	        ArrayList<Object> result = new WeatherAPIModule().giveWeather(list, data, 0);
	        assertEquals(20, result.get(0));
	        assertEquals("Sunny", result.get(1));
	        assertEquals("Clear skies", result.get(2));
	    }

	 @Test
	    public void testGetTemp() {
	        JSONArray list = new JSONArray();
	        JSONObject obj1 = new JSONObject();
	        JSONObject main1 = new JSONObject();
	        main1.put("temp", 20.0);
	        obj1.put("main", main1);
	        list.add(obj1);
	        assertEquals(20.0, WeatherAPIModule.getTemp(0, list));
	    }

	    @Test
	    public void testGetWeather() {
	        JSONArray list = new JSONArray();
	        JSONObject obj1 = new JSONObject();
	        JSONArray weather1 = new JSONArray();
	        JSONObject weather1obj = new JSONObject();
	        weather1obj.put("main", "Clear");
	        weather1.add(weather1obj);
	        obj1.put("weather", weather1);
	        list.add(obj1);
	        assertEquals("Clear", WeatherAPIModule.getWeather(0, list));
	    }

	    @Test
	    public void testGetWeatherDescription() {
	        JSONArray list = new JSONArray();
	        JSONObject obj1 = new JSONObject();
	        JSONArray weather1 = new JSONArray();
	        JSONObject weather1obj = new JSONObject();
	        weather1obj.put("description", "clear sky");
	        weather1.add(weather1obj);
	        obj1.put("weather", weather1);
	        list.add(obj1);
	        assertEquals("clear sky", WeatherAPIModule.getWeatherDescription(0, list));
	    }

	@Test
	public void testDayOfTheWeek() {
		assertEquals(0, WeatherAPIModule.dayOfTheWeek("today"));
		assertEquals(1, WeatherAPIModule.dayOfTheWeek("tomorrow"));
		assertEquals(2, WeatherAPIModule.dayOfTheWeek("dayaftertomorrow"));
	}
}
