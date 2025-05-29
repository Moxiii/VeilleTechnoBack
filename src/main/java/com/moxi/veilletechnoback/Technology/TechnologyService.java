package com.moxi.veilletechnoback.Technology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TechnologyService {
@Autowired
private TechnologyRepository ressourcesRepository;

public List<Technology> findAll() {
	return ressourcesRepository.findAll();
}

public Technology findById(long id) {
	return ressourcesRepository.findById(id);
}

public void save(Technology newTechnology) {
	ressourcesRepository.save(newTechnology);
}

public void delete(Technology technology) {
	ressourcesRepository.delete(technology);
}

public List<Technology> findAllById(List<Long> id) {
	return ressourcesRepository.findAllById(id);
}
}
