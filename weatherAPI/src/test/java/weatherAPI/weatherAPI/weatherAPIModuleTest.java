package weatherAPI.weatherAPI;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class weatherAPIModuleTest {

	private URL testUrl;
	private int testResponseCode;
	private JSONArray testList = new JSONArray();
	JSONObject testData = new JSONObject();

	@BeforeEach
	void setUp() throws Exception {
		// new url for test
		testUrl = new URL(
				"https://api.openweathermap.org/data/2.5/weather?q=cork,ie&appid=0219cd5cd854de517fe7720f70c8da25&units=metric");
		// get response code
		 testResponseCode = weatherAPIModule.establishConnection(testUrl);
		testList = (JSONArray) (weatherAPIModule.getData(testUrl, testResponseCode).get("list"));
		testData = weatherAPIModule.getData(testUrl, testResponseCode);
		System.out.println(testData);
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
		weatherAPIModule.getTemp(0,testList);
	}

	@Test
	void testGetFeelsLike() {
		weatherAPIModule.getFeelsLike(0,testList);
	}

	@Test
	void testGetMinTemp() {
		weatherAPIModule.getMinTemp(0,testList);
	}

	@Test
	void testGetMaxTemp() {
		weatherAPIModule.getMaxTemp(0,testList);
	}

	@Test
	void testGetPressure() {
		weatherAPIModule.getPressure(0,testList);
	}

	@Test
	void testGetHumidity() {
		weatherAPIModule.getHumidity(0,testList);
	}

	@Test
	void testGetWeather() {
		weatherAPIModule.getWeather(0,testList);
	}

	@Test
	void testGetWeatherDescription() {
		weatherAPIModule.getWeatherDescription(0,testList);
	}

	@Test
	void testGetClouds() {
		weatherAPIModule.getClouds(0,testList);
	}

	@Test
	void testGetWindSpeed() {
		weatherAPIModule.getWindSpeed(0,testList);
	}

	@Test
	void testGetWindDirection() {
		weatherAPIModule.getWindDirection(0,testList);
	}

	@Test
	void testGetVisibility() {
		weatherAPIModule.getVisibility(0,testList);
	}

	@Test
	void testGetSunRise() {
		weatherAPIModule.getSunRise(testData);
	}

	@Test
	void testGetSunSet() {
		weatherAPIModule.getSunSet(testData);
	}

	@Test
	void testGetRecorded() {
		weatherAPIModule.getRecorded(0,testList);
	}

}
