package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.domain.OrdersPayment;

public interface OrdersPaymentRepository extends JpaRepository<OrdersPayment, Integer> {
}