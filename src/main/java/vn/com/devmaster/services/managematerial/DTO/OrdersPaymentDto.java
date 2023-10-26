package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersPaymentDto {
    private Integer idpayment;
    private Integer total;
    private String notes;
    private String status;
}