package com.moxi.veilletechnoback.Ideas;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeasRepository extends JpaRepository<Ideas, Long> {
Ideas findByUserAndId(User currentUser, Long id);

List<Ideas> findAllByUser(User currentUser);
}
