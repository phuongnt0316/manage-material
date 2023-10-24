package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.com.devmaster.services.managematerial.DTO.CartDto;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.projection.IViewCart;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = Sql.CART_BY_ID_CUSTOMER,nativeQuery = true)
    List<IViewCart> getCartByIdCustomer(String idcustomer);


    @Modifying(clearAutomatically=true)
    @Transactional
    @Query(value = Sql.DELETE_CART,nativeQuery = true)
    Integer deleteProductCarts(String delete_idpr, String idcustomer);
}
