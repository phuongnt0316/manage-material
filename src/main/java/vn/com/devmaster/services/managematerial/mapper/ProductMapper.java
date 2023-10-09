package vn.com.devmaster.services.managematerial.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.DTO.ProductImageDto;
import vn.com.devmaster.services.managematerial.domain.Product;

import java.util.List;

@Component
public class ProductMapper implements EntityMapper<Product,ProductDto>{
    @Autowired
    ProductImagesMapper productImagesMapper;

    @Override
    public Product toEntity(ProductDto productDto) {
        return null;
    }

    @Override
    public List<Product> toEntity(List<ProductDto> d) {
        return null;
    }

    @Override
    public ProductDto toDto(Product entity) {
        ProductDto pr=ProductDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .notes(entity.getNotes())
                .image(entity.getImage())
                .price(entity.getPrice())
                .quatity(entity.getQuatity())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isactive(entity.getIsactive())
                .productImageDtos(productImagesMapper.toDto(entity.getProductImages()))
                .build();
        return pr;
    }

    @Override
    public List<ProductDto> toDto(List<Product> e) {
        return null;
    }
}
