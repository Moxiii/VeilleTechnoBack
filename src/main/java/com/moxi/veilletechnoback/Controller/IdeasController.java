package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Ideas.IdeasReq;
import com.moxi.veilletechnoback.DTO.Ideas.IdeasRes;
import com.moxi.veilletechnoback.Ideas.Ideas;
import com.moxi.veilletechnoback.Ideas.IdeasService;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ideas")
public class IdeasController {
@Autowired
private SecurityUtils securityUtils;
@Autowired
private IdeasService ideasService;

private IdeasRes ideasToRes(Ideas ideas){
	IdeasRes ideasRes = new IdeasRes();
	ideasRes.setId(ideas.getId());
	ideasRes.setDescription(ideas.getDescription());
	ideasRes.setTags(ideas.getTags());
	ideasRes.setTitle(ideas.getTitle());
	if(!ideas.getRessources().isEmpty()){
		List<Long> ressourcesIds = ideas.getRessources().stream().map(Ressources::getId).toList();
		ideasRes.setRessourcesIds(ressourcesIds);
	}

	return ideasRes;
}
@GetMapping
public ResponseEntity<List<IdeasRes>> getAllIdeas(){
	User currentUser = securityUtils.getCurrentUser();
	List<IdeasRes> ideas = ideasService.findAllByUser(currentUser).stream().map(this::ideasToRes).toList();
	return new ResponseEntity<>(ideas , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<IdeasRes> getIdeasById(@PathVariable Long id) {
	User currentUser = securityUtils.getCurrentUser();
	Ideas ideas = ideasService.findByUserAndId(currentUser,id);
	return new ResponseEntity<>(ideasToRes(ideas) , HttpStatus.OK);
}
@PutMapping("/{id}")
public ResponseEntity<IdeasRes> updateIdeas(@PathVariable Long id, @RequestBody IdeasReq ideas) {
	User currentUser = securityUtils.getCurrentUser();
	Ideas updated = ideasService.update(currentUser, id , ideas);
	return new ResponseEntity<>(ideasToRes(updated) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<IdeasRes> createIdeas(@RequestBody IdeasReq req){
	User currentUser = securityUtils.getCurrentUser();
	Ideas createdIdeas = ideasService.create(currentUser, req);
	return new ResponseEntity<>((ideasToRes(createdIdeas)), HttpStatus.CREATED);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteIdeas(@PathVariable Long id){
	ideasService.delete(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}
