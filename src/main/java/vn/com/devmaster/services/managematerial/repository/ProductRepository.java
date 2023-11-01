package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
@Component
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = Sql.PRODUCT_BY_ID,nativeQuery = true)
    Product getProductByID(@Param("idpr") Integer idpr);

    @Query(value = "select * from product s where s.ISACTIVE=1",nativeQuery = true)
    List<Product> getProduct();
    @Query(value = "select * from product s where s.IDCATEGORY=:idcate",nativeQuery = true)
    List<Product> getProductByCategory(String idcate);
    @Query(value = "select * from product s where s.NAME like concat('%',:keyword,'%')",nativeQuery = true)
    List<Product> searchProduct(String keyword);
}
