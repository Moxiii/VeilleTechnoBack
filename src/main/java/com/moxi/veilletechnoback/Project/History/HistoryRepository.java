package com.moxi.veilletechnoback.Project.History;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moxi.veilletechnoback.Project.Project;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByProjectOrderByTimestampDesc(Project project);
}
