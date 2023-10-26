package vn.com.devmaster.services.managematerial.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderDto {
    private Integer idcustomer;
    private Double totalMoney;
    private String notes;
    private String nameReciver;
    private String address;
    private  String phone;
    private List<OrdersDetailDto> detailDtoList;
    private OrdersPaymentDto ordersPaymentDto;
    private OrdersTransportDto ordersTransportDto;
}