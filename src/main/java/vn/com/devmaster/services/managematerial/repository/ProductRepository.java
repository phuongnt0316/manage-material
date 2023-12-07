package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
@Component
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = Sql.PRODUCT_BY_ID, nativeQuery = true)
    Product getProductByID(@Param("idpr") Integer idpr);

    @Query(value = "select * from product ORDER BY PRICE ASC", nativeQuery = true)
    List<Product> sortProductByPriceASC();

    @Query(value = "select * from product s where s.ISACTIVE=1", nativeQuery = true)
    List<Product> getProduct();

    @Query(value = "select * from product s where s.IDCATEGORY=:idcate", nativeQuery = true)
    List<Product> getProductByCategory(String idcate);

    @Query(value = "select * from product s where s.NAME like concat('%',:keyword,'%')", nativeQuery = true)
    List<Product> searchProduct(String keyword);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_QUANTITY_PRODUCT, nativeQuery = true)
    Integer updateQuantityProduct(Integer idproduct, Integer qty);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_PRODUCT, nativeQuery = true)
    void updateProduct(Integer id, String name, Integer idcategory, Double price, String description, String notes, Byte isactive, String urlImage, Instant updated_date, Double sale, Integer customerId);
    @Query(value = "select * from product s where s.ISACTIVE=1", nativeQuery = true)
    Page<Product> getProduct(Pageable pageable);
    @Query(value = "select * from product s where s.NAME like concat('%',:keyword,'%') and s.ISACTIVE=1", nativeQuery = true)
    List<Product> searchProductbyUser(String keyword);
    @Query(value = Sql.SEARCH_PRODUCT, nativeQuery = true)
    List<Product> searchAllProduct(List<Integer> idCates, double priceProduct1, double priceProduct2, String keyword);
    @Query(value = Sql.PRODUCT_SALE, nativeQuery = true)
    List<Product> saleProduct();
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_PRODUCT_ISACTIVE,nativeQuery = true)
    void updateProductIsActive(Integer id);
}