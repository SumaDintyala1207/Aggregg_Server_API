import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.concurrent.*;

public class ContentServer {

    private static final String FILE_PATH = "weather_data.json";
    private static final String SERVER_URL = "http://localhost:8080/weather";
    private static final int MAX_RETRIES = 3;

    public static void main(String[] args) {
        try{
            String jsonData = readWeatherDataFromFile(FILE_PATH);
            boolean success = uploadWeatherData(jsonData);

            if (!success) {
                System.err.println("Failed to upload weather data");
            }
        } catch (IOException e) {
            System.err.println("Failed to read or upload weather data" + e.getMessage());
        }
    }

    private static String readWeatherDataFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static boolean uploadWeatherData(String jsonData) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(SERVER_URL).openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                try (OutputStream os = connection.getOutputStream()){
                    os.write(jsonData.getBytes());
                    os.flush();
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Successfully uploaded weather data");
                    return true;
                } else{
                    System.out.println("Sever response: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Failed to upload weather data" + e.getMessage());
            }
            attempts++;
            try {
                TimeUnit.SECONDS.sleep(2 << attempts);
            } catch (InterruptedException ignored) {}
        }
        return false;
        
    }
}
