package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.untils.Sql;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.OrdersDetail;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
    @Query(value = Sql.VIEW_ORDER_DETAIL,nativeQuery = true)
    List<IViewProduct> getOrderDetailByID(Integer id);
    @Query(value = Sql.ORDER_DETAIL_BY_IDOD,nativeQuery = true)
    List<OrdersDetail> getOrderDetailByIDOrder(Integer idod);
}