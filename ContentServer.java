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

        } catch (IOException e) {

        }
    }

    private static String readWeatherDataFromFile(String filepath) throws IOException {

    }
    public static boolean uploadWeatherData(String jsonData) {
        int attempts = 0;
    }
}
