package com.moxi.veilletechnoback.Category.SubCat;

import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
List<SubCategory> findByUser(User user);

List<SubCategory> findByCategory_Type(CategoryEnum category);
}
