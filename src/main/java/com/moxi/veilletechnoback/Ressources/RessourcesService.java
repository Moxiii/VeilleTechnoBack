package com.moxi.veilletechnoback.Ressources;


import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RessourcesService {
@Autowired
private RessourcesRepository ressourcesRepository;

public List<Ressources> findAll() {
	return ressourcesRepository.findAll();
}

public Ressources findByUserAndId(User user , Long id) {
	return ressourcesRepository.findByUserAndId(user , id);
}
public List<Ressources> findByUserAndIds(User user , List<Long> ids) {
	return ressourcesRepository.findByUserAndIdIn(user , ids);
}

public void save(Ressources newRessources) {
	ressourcesRepository.save(newRessources);
}
public void update(Ressources newRessources) {
	ressourcesRepository.save(newRessources);
}

public void delete(Ressources ressources) {
	ressourcesRepository.delete(ressources);
}
}
