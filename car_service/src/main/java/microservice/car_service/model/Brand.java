package microservice.car_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/***
 * Enum of all possible Brands the Car Rental has
 */
public enum Brand {
    @JsonProperty("volvo")
    VOLVO,
    @JsonProperty("bmw")
    BMW,
    @JsonProperty("saab")
    SAAB,
    @JsonProperty("fiat")
    FIAT,
    @JsonProperty("ferrari")
    FERRARI
}
