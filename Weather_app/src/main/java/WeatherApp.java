import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] cities = {"London", "Paris", "New York"};

        System.out.println("Choose a city:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println((i + 1) + ". " + cities[i]);
        }

        int choice = scanner.nextInt();
        if (choice < 1 || choice > cities.length) {
            System.out.println("Invalid choice.");
            return;
        }

        String city = cities[choice - 1];
        getWeather(city);
    }

    public static void getWeather(String city) {
        try {
            String apiKey = "b1af7ab6dfce841cb8dc2a6ad2f07c94";
            String urlString =
                    "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                            "&appid=" + apiKey + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(content.toString());

            String description = obj.getJSONArray("weather")
                    .getJSONObject(0).getString("description");
            double temp = obj.getJSONObject("main").getDouble("temp");
            int humidity = obj.getJSONObject("main").getInt("humidity");

            System.out.println("Weather in " + city + ":");
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Description: " + description);
            System.out.println("Humidity: " + humidity + "%");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

