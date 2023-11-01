package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private String notes;
     private String image;
    private Double price;
    private Integer quantity;
    private Instant createdDate;
    private Instant updatedDate;
    private String createdBy;
    private String updatedBy;
    private CategoryDto idcategory;
    private Byte isactive;
    private List<ProductImageDto> productImageDtos;
}