package microservice.payment_service.event.model;

import lombok.Getter;

import java.util.UUID;

/***
 * Abstract class for all Events containing UUID
 */
@Getter
public abstract class Event {
    private final UUID id = UUID.randomUUID();
}
