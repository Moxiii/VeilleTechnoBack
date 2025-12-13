package com.moxi.veilletechnoback.Category;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
List<Category> findByUser(User user);

void deleteByIdAndUser(long id, User user);

boolean existsByTypeAndDefaultCategory(CategoryEnum type, boolean b);

List<Category> findByDefaultCategory(boolean b);
}
