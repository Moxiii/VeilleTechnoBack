package com.moxi.veilletechnoback.Project.Features;


import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long> {
List<Features> findAllByProjectIdAndUser(User user, Long projectId);

Features findByIdAndUser(User currentUser, Long id);
}
