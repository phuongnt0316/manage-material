package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.projection.IRevenueCategory;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query(value = "select * from Category s where s.isactive=1",nativeQuery = true)
    List<Category> getAllCategory1();
    @Query(value = Sql.REVENUE_BY_CATEGORY,nativeQuery = true)
    List<IRevenueCategory> getRevenueByCategory();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_CATEGORY,nativeQuery = true)
    void updateCategory(Integer id, String name, String notes, String urlImage, Integer isActive, Instant date, Integer customerId);
}
