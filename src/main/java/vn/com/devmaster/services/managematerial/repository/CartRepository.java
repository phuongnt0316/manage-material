package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.projection.IOrderDetailDTO;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = Sql.CART_BY_ID_CUSTOMER,nativeQuery = true)
    List<IViewProduct> getCartByIdCustomer(Integer idcustomer);

    @Query(value = Sql.CART_BY_ID,nativeQuery = true)
    List<IOrderDetailDTO> getCartByID(Integer idcustomer);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query(value = Sql.DELETE_CART,nativeQuery = true)
    Integer deleteProductCarts(Integer delete_idpr, Integer idcustomer);
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query(value = Sql.BUY_CART,nativeQuery = true)
    Integer BuyCarts(Integer delete_idpr, Integer idcustomer);
    @Query(value = Sql.CART_BY_CUSTOMER,nativeQuery = true)
    List<Cart> getCartByCustomer(Integer id);
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query(value = Sql.UPDATE_QUANTITY_CART,nativeQuery = true)
    Integer updateCart(Integer id, Integer idpr);
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query(value = Sql.UPDATE_CART_QUANTITY,nativeQuery = true)
    int updateQuantityCart(Integer id, Integer idproduct, Integer quantity);
}
