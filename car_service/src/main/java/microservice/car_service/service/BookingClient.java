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

/***
 * A Client object for the Booking Microservice endpoint: /booking/current
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BookingClient {

    @Value("${booking.service.base-url}")
    private String BASE_URL;
    @Value("${booking.service.api-key}")
    private String API_KEY;

    /***
     * Gets all the current (no old) bookings from Booking-Service Database
     * @return the carId with all their booked dates in a list of DateSpan
     */
    public Map<String, List<DateSpan>> getBookedDatesForCars() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("API-KEY", API_KEY)
                .uri(URI.create("http://" + BASE_URL + "/bookings/current"))
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

    /***
     * Parsing a JSON string into a object of: Map<String, List<DateSpan>>
     * @param body from a response that need JSON parsing
     * @return A object of the given body
     * @throws JsonProcessingException
     */
    private Map<String, List<DateSpan>> parseJSON(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(body, new TypeReference<Map<String, List<DateSpan>>>() {});
    }
}
