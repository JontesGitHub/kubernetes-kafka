package microservice.car_service.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ExampleC {

    @PostMapping("date")
    public DateSpan date(@RequestBody DateSpan dateSpan) {
        return dateSpan;
    }
    @GetMapping("date")
    public DateSpan date() {
        return new DateSpan(LocalDate.now());
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class DateSpan {
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate localDate;

}
