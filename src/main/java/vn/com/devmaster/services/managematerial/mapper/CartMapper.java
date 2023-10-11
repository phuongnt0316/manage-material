package vn.com.devmaster.services.managematerial.mapper;

import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.CartDto;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Cart;

import java.util.List;
@Component
public class CartMapper implements EntityMapper<Cart, CartDto>{
    @Override
    public Cart toEntity(CartDto cartDto) {
        return null;
    }

    @Override
    public List<Cart> toEntity(List<CartDto> d) {
        return null;
    }

    @Override
    public CartDto toDto(Cart entity) {
        CartDto cartDto= CartDto.builder()
                .id(entity.getId())
                .idCustomer(entity.getIdCustomer())
                .idProduct(entity.getIdProduct())
                .quantity(entity.getQuantity())
                .build();
        return cartDto;
    }

    @Override
    public List<CartDto> toDto(List<Cart> e) {
        return null;
    }
}
