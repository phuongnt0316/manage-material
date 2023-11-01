package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.Order;
import vn.com.devmaster.services.managematerial.projection.IODPMTS;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
     @Query(value = "select * from orders s where s.IDCUSTOMER=:id",nativeQuery = true)
    List<Order> getOrderByCustomer(Integer id);
    @Query(value = Sql.COUNT_ORDER_CUSTOMER,nativeQuery = true)
    int getCountByCustomer(Integer idcustomer);
    @Query(value = Sql.ORDER_INFOR,nativeQuery = true)
    IODPMTS getorderInfor(Integer idod);
}