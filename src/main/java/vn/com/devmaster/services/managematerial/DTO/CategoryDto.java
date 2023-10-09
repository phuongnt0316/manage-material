package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    Integer id;
    Integer idparent;
    String name;
    String notes;
    String icon;
    Instant createdDate;
    Instant updatedDate;
    String createdBy;
    String updatedBy;
    Byte isactive;
}