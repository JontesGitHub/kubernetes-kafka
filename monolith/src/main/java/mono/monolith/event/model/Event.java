package mono.monolith.event.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Event {
    private final UUID id = UUID.randomUUID();
}
