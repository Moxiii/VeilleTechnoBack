package com.moxi.veilletechnoback.Technologies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologiesRepository extends JpaRepository<Technologies, String> {
}
