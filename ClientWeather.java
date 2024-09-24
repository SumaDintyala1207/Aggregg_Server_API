import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClientWeather {

    public static void main(String[] args) throws IOException {
        String location = "NewYork";
        String url = "http://localhost:8080/weather?location=" + location;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
                scanner.close();
            } else {
                System.out.println("Error: " + responseCode);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
