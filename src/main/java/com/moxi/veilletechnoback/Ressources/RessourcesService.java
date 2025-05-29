package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.Technology.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RessourcesService {
@Autowired
private RessourcesRepository ressourcesRepository;

public List<Ressources> findAll() {
	return ressourcesRepository.findAll();
}

public Ressources findById(long id) {
	return ressourcesRepository.findById(id);
}

public void save(Ressources newRessources) {
	ressourcesRepository.save(newRessources);
}

public void delete(Ressources ressources) {
	ressourcesRepository.delete(ressources);
}
}
