package microservice.car_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

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

//    VOLVO("volvo"),
//    BMW("bmw"),
//    SAAB("saab"),
//    FIAT("fiat"),
//    FERRARI("ferrari");
//
//    private final String name;
//
//    Brand(String name) {
//        this.name = name;
//    }
//
//    private static final Map<String, Brand> map = new HashMap<>();
//
//    static {
//        for (Brand brand : Brand.values()) {
//            map.put(brand.name, brand);
//        }
//    }
//
//    public static Brand get(String name) {
//        return map.get(name);
//    }
}
