package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import vn.com.devmaster.services.managematerial.domain.Order;
import vn.com.devmaster.services.managematerial.projection.*;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {
    @Query(value = "select * from orders s where s.IDCUSTOMER=:id", nativeQuery = true)
    List<Order> getOrderByCustomer(Integer id);

    @Query(value = Sql.COUNT_ORDER_CUSTOMER, nativeQuery = true)
    int getCountByCustomer(Integer idcustomer);

    @Query(value = Sql.ORDER_INFOR, nativeQuery = true)
    IOrderInFor getorderInfor(Integer idod);

    @Query(value = Sql.ALL_ORDERS, nativeQuery = true)
    List<Order> getAllOrders();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_ORDER_STATUS, nativeQuery = true)
    int updateOrderStatus(Integer idod, Integer status);

    @Query(value = Sql.REVENUE_BY_MONTH, nativeQuery = true)
    List<IRevenueMonth> getRevenueByMonth();

    @Query(value = Sql.REVENUE_BY_DAY, nativeQuery = true)
    List<IRevenueDay> getRevenueByDay(int month);

    @Query(value = Sql.MONTH_YEAR, nativeQuery = true)
    List<IMonth> getMonthYear(int year);

    @Query(value = Sql.TOTAL_REVENUE, nativeQuery = true)
    int getTotalRevenue(Integer month);

    @Query(value = Sql.GET_VIEW_ORDER, nativeQuery = true)
    List<IOrderInFor> getViewOrder();
    @Query(value = Sql.STATISTICAL_PRODUCT, nativeQuery = true)
    List<IProduct> getStatisticalProduct();
    @Query(value = Sql.STATISTICAL_PRODUCT1, nativeQuery = true)
    List<IProduct> getStatisticalProduct1();
}