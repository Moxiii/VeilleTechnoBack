package com.moxi.veilletechnoback.Pdf.Utils.Hierarchy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.moxi.veilletechnoback.Technology.Technology;

@Component
public class Resolver {

public Technology getRootTechnology(Technology tech) {
    Technology current = tech;
    while (current.getParent() != null) {
        current = current.getParent();
    }
    return current;
}
 public List<Technology> getRootTechnologies(List<Technology> techs) {

    List<Technology> roots = new ArrayList<>();
    for (Technology t : techs){
        Technology root = getRootTechnology(t);
        if (!roots.contains(root)) {
            roots.add(root);
        }
    }
    return roots;
  
}
public List<Technology> getChildTechnologies(Technology parent, List<Technology> allTechs) {
    List<Technology> children = new ArrayList<>();
    for (Technology t : allTechs) {
        if (t.getParent() != null && t.getParent().getId().equals(parent.getId())) {
            children.add(t);
        }
    }
    return children;

}
}
