package vn.com.devmaster.services.managematerial.DTO;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersTransportDto  {
    private Integer idtransport;
    private Integer total;
    private String notes;
}