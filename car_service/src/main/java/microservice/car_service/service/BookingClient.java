package microservice.car_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.car_service.shared.DateSpan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookingClient {

    @Value("${booking.service.base-url}")
    private String BASE_URL;
    @Value("${booking.service.api-key}")
    private String API_KEY;

    public Map<String, List<DateSpan>> getBookedDatesForCars() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("API-KEY", API_KEY)
                .uri(URI.create(BASE_URL + "/bookings/current"))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return parseJSON(response.body());

        } catch (JsonProcessingException e) {
            log.error("Cloud not parse response body.");
        } catch (InterruptedException | IOException e) {
            log.error("Http Request failed to /bookings/current.");
        }
        return null;
    }

    private Map<String, List<DateSpan>> parseJSON(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(body, new TypeReference<Map<String, List<DateSpan>>>() {});
    }
}
