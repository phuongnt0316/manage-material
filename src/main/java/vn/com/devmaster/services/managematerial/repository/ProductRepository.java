package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "select * from product s where s.id =:idpr",nativeQuery = true)
    List<Product> getProductByID(@Param("idpr")String idpr);

    @Query(value = "select * from product s where s.ISACTIVE=1",nativeQuery = true)
    List<Product> getProduct();
}
