package vn.com.devmaster.services.managematerial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewCart {
    Integer idProduct;
    String name;
    Integer quantityCart;
    String image;
    Double price;
    Integer quantityProduct;
    Double sale;
}
