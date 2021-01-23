package microservice.user_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;

/***
 * Entity of a User.
 * This is what gets persisted to the Database.
 * MongoDB collection = User
 */
@Data
@NoArgsConstructor
public class User {
    private String id;

    @Indexed(unique=true)
    @NotBlank(message = "Property username is mandatory")
    private String username;

    @NotBlank(message = "Property password is mandatory")
    private String password;
}
