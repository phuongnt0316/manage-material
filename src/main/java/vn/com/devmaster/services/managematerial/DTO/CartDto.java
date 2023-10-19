package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;
import vn.com.devmaster.services.managematerial.domain.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto{
    Integer id;
    Integer idCustomer;
    Product product;
    Integer quantity;
}