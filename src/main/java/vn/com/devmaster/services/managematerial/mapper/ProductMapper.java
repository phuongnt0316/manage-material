package vn.com.devmaster.services.managematerial.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper implements EntityMapper<Product,ProductDto>{
    @Autowired
    ProductImagesMapper productImagesMapper;

    @Override
    public Product toEntity(ProductDto dto) {
        Product product=Product
                .builder()
                //.id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .notes(dto.getNotes())
                .image(dto.getImage())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .isactive(dto.getIsactive())
                .build();
        return product;
    }

    @Override
    public List<Product> toEntity(List<ProductDto> dtos) {
        List<Product> products=new ArrayList<>();
        dtos.forEach(dto->{
            products.add(toEntity(dto));
        });
        return products;
    }

    @Override
    public ProductDto toDto(Product entity) {
        ProductDto pr=ProductDto
                .builder()
                //.id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .notes(entity.getNotes())
                .image(entity.getImage())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isactive(entity.getIsactive())
                //.productImageDtos(productImagesMapper.toDto(entity.getProductImages()))
                .build();
        return pr;
    }

    @Override
    public List<ProductDto> toDto(List<Product> entities) {
        List<ProductDto> dtos=new ArrayList<>();
        entities.forEach(product ->{
            ProductDto dto=toDto(product);
            dtos.add(dto);
        });
        return dtos;
    }
}
