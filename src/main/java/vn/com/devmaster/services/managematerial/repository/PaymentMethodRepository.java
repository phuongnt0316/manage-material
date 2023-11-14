package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.PaymentMethod;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    @Query(value = "select * from payment_method s where s.ISACTIVE=1",nativeQuery = true)
    List<PaymentMethod> getPayment();
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_PAYMENT, nativeQuery = true)
    int updatePayMentMethod(Integer idPayment, String namePayment, String notes, Byte paymentActive, String urlImage, Instant updatedDate);
}