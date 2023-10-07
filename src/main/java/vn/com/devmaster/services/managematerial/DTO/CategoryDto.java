package vn.com.devmaster.services.managematerial.DTO;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link vn.com.devmaster.services.managematerial.domain.Category}
 */
@Value
public class CategoryDto implements Serializable {
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