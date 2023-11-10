package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.ProductImage;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{
        @Query(value = Sql.IMAGES_PRODUCT,nativeQuery = true)
        List<ProductImage> getImageProduct(Integer idproduct);

}
