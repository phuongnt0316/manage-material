package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDto {
    private String name;
    private String url;
}