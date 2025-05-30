package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourcesRepository extends JpaRepository<Ressources, String> {
	public Ressources findByUserAndId(User user, long id);
}
