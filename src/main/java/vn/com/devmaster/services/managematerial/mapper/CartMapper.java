package vn.com.devmaster.services.managematerial.mapper;

import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.CartDto;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.domain.Product;

import java.util.List;
@Component
public class CartMapper implements EntityMapper<Cart, CartDto>{
    @Override
    public Cart toEntity(CartDto dto) {
        return null;
//        Cart cart=Cart
//                .builder()
//                .id(dto.getId())
//                .idCustomer(dto.getIdCustomer())
//                .product(dto.getClass(dt))
//                .quantity(dto.getQuantity())
//                .build();
//        return cart;
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
                //.idProduct(entity.getProduct().getId())
                .quantity(entity.getQuantity())
                .build();
        return cartDto;
    }

    @Override
    public List<CartDto> toDto(List<Cart> e) {
        return null;
    }
}
