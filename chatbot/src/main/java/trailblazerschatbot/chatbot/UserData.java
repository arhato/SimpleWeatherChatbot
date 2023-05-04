package trailblazerschatbot.chatbot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class UserData {
	public int count = -1;
    public String[][] location;
    private WeatherAPIModule apiModule;
    private ArrayList<ArrayList<Object>> weatherData = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>(List.of("monday","tuesday","wednesday","thursday","friday","saturday","sunday"));

    public UserData(int size) {
        this.location = new String[size][2];
        apiModule = new WeatherAPIModule();
    }

    public void addCity(String input) {
        location[count][0] = input;
    }
    public void addDay(String day, int duration){
        day = day.toLowerCase();
        int index = days.indexOf(day);
        for (int i = 0; i < duration; i++) {
            location[count+i][0] = location[count][0];
            location[count+i][1] = days.get((index+i)%7);
        }
        count = count + duration - 1;
    }
    public void getWeather() {
    	
        for (int i = 0; i < location.length; i++) {
            weatherData.add(apiModule.weatherAPI(location[i][0],location[i][1]));
        }
        for (int i=0;i<weatherData.size();i++) {
        System.out.println(WeatherAPIModule.getClothing(weatherData.get(i).get(2))+ " for " + location[i][0] +" on "+ location[i][1]+".");
        }
    }
    public void increment(){
        count++;
    }
}
