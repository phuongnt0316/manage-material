package vn.com.devmaster.services.managematerial.mapper;

import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.ProductImageDto;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.domain.ProductImage;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductImagesMapper implements EntityMapper<ProductImage, ProductImageDto> {
    @Override
    public ProductImage toEntity(ProductImageDto productImageDto) {
        return null;
    }

    @Override
    public List<ProductImage> toEntity(List<ProductImageDto> d) {
        return null;
    }

    @Override
    public ProductImageDto toDto(ProductImage entity) {
        return ProductImageDto
                .builder()
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }

    @Override
    public List<ProductImageDto> toDto(List<ProductImage> entities) {
        List<ProductImageDto> dtos=new ArrayList<>();
        entities.forEach(image ->{
            ProductImageDto dto=toDto(image);
            dtos.add(dto);
        });
        return dtos;
    }
}
