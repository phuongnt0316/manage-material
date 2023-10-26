package vn.com.devmaster.services.managematerial.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDetailDto {
    private Integer idproduct;
    private Double price;
    private Integer qty;
}