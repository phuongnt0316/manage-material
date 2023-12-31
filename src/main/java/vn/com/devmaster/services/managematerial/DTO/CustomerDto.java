package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;
import vn.com.devmaster.services.managematerial.domain.ProductImage;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto{
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String email;
    private String phone;
    private Instant createdDate;
    private Byte isactive;
}