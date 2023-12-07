package vn.com.devmaster.services.managematerial.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.Blog;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query(value = Sql.GET_BLOG, nativeQuery = true)
    List<Blog> getBlog();
    @Query(value = Sql.GET_BLOG_RELATED, nativeQuery = true)
    List<Blog> getBlogRelated();
}