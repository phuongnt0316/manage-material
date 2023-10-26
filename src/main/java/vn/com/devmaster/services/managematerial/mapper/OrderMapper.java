package vn.com.devmaster.services.managematerial.mapper;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.OrderDto;

import java.util.List;
@Component
public class OrderMapper implements EntityMapper<Order, OrderDto> {
    @Override
    public Order toEntity(OrderDto orderDto) {

        return null;
    }

    @Override
    public List<Order> toEntity(List<OrderDto> d) {
        return null;
    }

    @Override
    public OrderDto toDto(Order order) {
        return null;
    }

    @Override
    public List<OrderDto> toDto(List<Order> e) {
        return null;
    }
}
