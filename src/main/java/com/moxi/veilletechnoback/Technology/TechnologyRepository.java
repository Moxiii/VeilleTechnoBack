package com.moxi.veilletechnoback.Technology;

import com.moxi.veilletechnoback.User.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
	public Technology findByUserAndId(User user , long id);
	List<Technology> findByParentIsNullAndUser(User user);
}
