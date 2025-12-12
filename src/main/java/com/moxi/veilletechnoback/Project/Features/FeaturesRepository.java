package com.moxi.veilletechnoback.Project.Features;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository

public interface FeaturesRepository extends JpaRepository<Features, Long> {
List<Features> findAllByProjectId (Long projectId);

Optional<Features> findById(Long id);
}
