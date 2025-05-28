package com.moxi.veilletechnoback.Ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourcesRepository extends JpaRepository<Ressources, String> {
}
