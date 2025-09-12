package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourcesRepository extends JpaRepository<Ressources, String> {
	Ressources findByUserAndId(User user, long id);
	List<Ressources> findByUserAndIdIn(User user, List<Long> ids);
}
