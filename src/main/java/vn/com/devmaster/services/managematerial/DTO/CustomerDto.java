package vn.com.devmaster.services.managematerial.DTO;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link vn.com.devmaster.services.managematerial.domain.Customer}
 */
@Value
public class CustomerDto implements Serializable {
    Integer id;
    String name;
    String username;
    String password;
    String address;
    String email;
    String phone;
    Instant createdDate;
    Byte isactive;
}