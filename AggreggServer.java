import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
public class AggreggServer {

    private final ConcurrentHashMap<String, List<WeatherData>> weatherDataMap = new ConcurrentHashMap<>();
    private final int maxUpdates = 20;

    public static void main(String[] args) {
        SpringApplication.run(AggreggServer.class, args);
    }

    @RestController
    public class WeatherController {

        @GetMapping("/weather")
        public List<WeatherData> getWeatherData(@RequestParam String location) {
            return weatherDataMap.getOrDefault(location, Collections.emptyList());
        }

        @PutMapping("/weather")
        public String updateWeatherData(@RequestBody WeatherUpdateRequest update) {
            weatherCataMap.compute(update.getLocation(), (location, data) -> {
                if (data == null) {
                    data = new ArrayList<>();
                }
                data.add(update.getWeatherData());
                if (data.size() > maxUpdates) {
                    data.remove(0);
                }
                return data;
            });
            return "Data updated for" + update.getLocation();
        }
    }
}
