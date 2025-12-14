package com.moxi.veilletechnoback.DTO.Technology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;

import lombok.Getter;
@Getter
public class TechUsageNode {
private Technology technology;
private long usageCount;
private Map<Technology, TechUsageNode> children = new HashMap<>();
private Map<Project,List<String>> conceptsByProject = new HashMap<>();
public TechUsageNode(Technology technology){
    this.technology = technology;
}
public void increment(){
    this.usageCount++;
}
public void addChild(TechUsageNode child) {
        children.put(child.getTechnology(), child);
    }

public void addConcepts(Project project, List<String> concepts) {
        conceptsByProject.put(project, concepts);
    }
}
