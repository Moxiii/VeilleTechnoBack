package com.moxi.veilletechnoback.Ideas;

import com.moxi.veilletechnoback.DTO.Ideas.IdeasReq;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.Ressources.RessourcesService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeasService {
@Autowired
private IdeasRepository ideasRepository;
@Autowired
private RessourcesService ressourcesService;
public Ideas save(Ideas ideas){
	return ideasRepository.save(ideas);
}
public Ideas create(User user, IdeasReq req){
	Ideas ideas = new Ideas();
	ideas.setUser(user);
	ideas.setTitle(req.getTitle());
	ideas.setDescription(req.getDescription());
	if (req.getTags() != null) ideas.setTags(req.getTags());
	if (req.getLinks() != null) ideas.setLinks(req.getLinks());
	if(req.getRessourcesIds() != null && !req.getRessourcesIds().isEmpty()){
		List<Ressources> ressources = ressourcesService.findByUserAndIds(user, req.getRessourcesIds());
		ideas.setRessources(ressources);
	}
	return ideasRepository.save(ideas);

}
public List<Ideas> findAll(){
	return ideasRepository.findAll();
}
public Ideas update(User user ,Long id , Ideas updatedIdeas){
	Ideas toUpdate = findByUserAndId(user , id);
	toUpdate.setTitle(updatedIdeas.getTitle());
	toUpdate.setDescription(updatedIdeas.getDescription());
	toUpdate.setTags(updatedIdeas.getTags());
	toUpdate.setLinks(updatedIdeas.getLinks());
	return ideasRepository.save(toUpdate);
}
public void delete(Long id){
	ideasRepository.deleteById(id);
}

public Ideas findByUserAndId(User currentUser, Long id) {
	return ideasRepository.findByUserAndId(currentUser , id);
}

public List<Ideas> findAllByUser(User currentUser) {
	return ideasRepository.findAllByUser(currentUser);
}
}
