package trailblazerschatbot.chatbot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class UserData {
	public int count = -1;
    public String[][] location;
    private ArrayList<String> days = new ArrayList<>(List.of("monday","tuesday","wednesday","thursday","friday","saturday","sunday"));

    public UserData(int size) {
        this.location = new String[size][2];
    }

    public void addCity(String input) {
        location[count][0] = input;
    }
    public void addDay(String day, int duration){
        day = day.toLowerCase();
        int index = days.indexOf(day);
        for (int i = 0; i < duration; i++) {
            location[count+i][0] = location[count+i][0];
            location[count+i][1] = days.get((index+i)%7);
        }

    }
    public void increment(){
        count++;
    }
}
