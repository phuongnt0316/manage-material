package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto implements Serializable {
    Integer id;
    Integer idCustomer;
    Integer idProduct;
    Integer quantity;
}