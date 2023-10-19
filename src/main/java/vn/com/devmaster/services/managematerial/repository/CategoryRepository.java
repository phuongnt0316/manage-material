package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.domain.Product;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query(value = "select * from Category s where s.isactive=1",nativeQuery = true)
    List<Category> getAllCategory1();
}
