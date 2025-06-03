package com.moxi.veilletechnoback.Category.SubCat;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
List<SubCategory> findByUser(User user);

List<Category> findByCategory(Category category);
}
