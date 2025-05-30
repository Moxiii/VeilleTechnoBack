package com.moxi.veilletechnoback.Technology;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
	public Technology findByUserAndId(User user , long id);

}
