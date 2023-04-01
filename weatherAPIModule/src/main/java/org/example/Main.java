package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        https://api.openweathermap.org/data/2.5/weather?q=cork,ie&appid=0219cd5cd854de517fe7720f70c8da25&units=metric

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a city: ");
        String city = scanner.nextLine();
        System.out.println("Enter a country: ");
        String country = scanner.nextLine();
        System.out.println("Enter which day you want to see the weather for: ");
        System.out.println("0. Today");
        System.out.println("1. Tomorrow");
        System.out.println("2. The day after tomorrow");
        System.out.println("3. The day after that");
        System.out.println("4. The day after that and so on...");
        int days = scanner.nextInt();


        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&appid=0219cd5cd854de517fe7720f70c8da25&units=metric&cnt=" + days);
            int responseCode = establishConnection(url);

            JSONArray list = (JSONArray) (getData(url, responseCode).get("list"));
            JSONObject data = getData(url, responseCode);
            System.out.println("Select what you want to see: ");
            while (true) {
                System.out.print("1. Temperature \t");
                System.out.print("2. Humidity and Pressure \t");
                System.out.print("3. Weather \t");
                System.out.println("4. Wind \t");
                System.out.print("5. Cloud and Visibility \t");
                System.out.print("6. Sea Level \t");
                System.out.println("7. Sunrise and Sunset \t");
                System.out.print("8. Recorded Time \t");
                System.out.println("9. Exit");
                int input = scanner.nextInt();
                giveWeather(list, data
                        , input);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static int establishConnection(URL url) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return connection.getResponseCode();
    }

    public static JSONObject getData(URL url, int responseCode) throws Exception {
        System.out.println(responseCode);
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
            System.out.println(data.get(0));

            return (JSONObject) data.get(0);
        }
    }

    public static void giveWeather(JSONArray list, JSONObject data, int input) {
        try {
            switch (input) {
                case 1 -> {
                    getTemp(0, list);
                    getFeelsLike(0, list);
                    getMinTemp(0, list);
                    getMaxTemp(0, list);
                    System.out.println();
                }
                case 2 -> {
                    getHumidity(0, list);
                    getPressure(0, list);
                    System.out.println();
                }
                case 3 -> {
                    getWeather(0, list);
                    getWeatherDescription(0, list);
                    System.out.println();
                }
                case 4 -> {
                    getWindSpeed(0, list);
                    getWindDirection(0, list);
                    System.out.println();
                }
                case 5 -> {
                    getClouds(0, list);
                    getVisibility(0, list);
                    System.out.println();
                }


                case 6 -> {
                    getSeaLevel(0, list);
                    getGroundLevel(0, list);
                    System.out.println();
                }
                case 7 -> {
                    getSunRise(data);
                    getSunSet(data);
                    System.out.println();
                }
                case 8 -> {
                    getRecorded(0, list);
                    System.out.println();
                }
                case 9 -> System.exit(0);

            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    public static void getTemp(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Now: " + main.get("temp") + " °C");
    }

    public static void getFeelsLike(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Feels like: " + main.get("feels_like") + " °C");
    }

    public static void getMinTemp(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Min: " + main.get("temp_min") + " °C");
    }

    public static void getMaxTemp(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Max: " + main.get("temp_max") + " °C");
    }

    public static void getPressure(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Pressure: " + main.get("pressure") + " hPa");
    }

    public static void getSeaLevel(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Sea Level: " + main.get("sea_level") + " m");
    }

    public static void getGroundLevel(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Ground Level: " + main.get("grnd_level") + " m");
    }

    public static void getHumidity(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject main = (JSONObject) index.get("main");
        System.out.println("Humidity: " + main.get("humidity") + " %");
    }

    public static void getWeather(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONArray weather = (JSONArray) index.get("weather");
        JSONObject weather0 = (JSONObject) weather.get(0);
        System.out.println("Conditions: " + weather0.get("main"));
    }

    public static void getWeatherDescription(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONArray weather = (JSONArray) index.get("weather");
        JSONObject weather0 = (JSONObject) weather.get(0);
        System.out.println("Conditions intensity: " + weather0.get("description"));
    }

    public static void getClouds(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject clouds = (JSONObject) index.get("clouds");
        System.out.println("Cloud: " + clouds.get("all") + " %");
    }

    public static void getWindSpeed(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject wind = (JSONObject) index.get("wind");
        System.out.println("Wind speed: " + wind.get("speed") + " m/s");
    }

    public static void getWindDirection(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        JSONObject wind = (JSONObject) index.get("wind");
        System.out.println("Wind direction: " + wind.get("deg") + "°");
    }

    public static void getVisibility(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        System.out.println("Visibility: " + index.get("visibility") + " m");
    }

    public static void getSunRise(JSONObject data) {
        JSONObject city = (JSONObject) data.get("city");
        Date date = new Date((long) city.get("sunrise")*1000);
        System.out.println("Sunrise: " + date);
    }

    public static void getSunSet(JSONObject data) {
        JSONObject city = (JSONObject) data.get("city");
        Date date = new Date((long) city.get("sunset")*1000);
        System.out.println("Sunset: " + date);
    }

    public static void getRecorded(int day, JSONArray list) {
        JSONObject index = (JSONObject) list.get(day);
        System.out.println("Recorded: " + index.get("dt_txt"));
    }
}