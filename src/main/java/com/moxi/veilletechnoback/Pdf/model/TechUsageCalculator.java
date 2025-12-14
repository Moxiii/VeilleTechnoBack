package com.moxi.veilletechnoback.Pdf.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.moxi.veilletechnoback.DTO.Technology.TechUsageNode;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

@Component
public class TechUsageCalculator {
private Technology findRoot(Technology tech) {
    Technology current = tech;
    while(current.getParent() != null) {
        current = current.getParent();
    }
    return current;
}
public TechUsageNode calculate(User user){
    TechUsageNode root = new TechUsageNode(null);

    for (Project project : user.getProjects()) {
        for (Technology tech : project.getTechnology()) {
            Technology parentTech = findRoot(tech);

            TechUsageNode parentNode = root.getChildren().get(parentTech);
            if (parentNode == null) {
                parentNode = new TechUsageNode(parentTech);
                root.addChild(parentNode);
            }
            parentNode.increment();

            if (tech.getParent() != null) {
                TechUsageNode childNode = parentNode.getChildren().get(tech);
                if (childNode == null) {
                    childNode = new TechUsageNode(tech);
                    parentNode.addChild(childNode);
                }
                childNode.increment();

                if (tech.getConcepts() != null) {
                    childNode.getConceptsByProject().put(
                        project,
                        tech.getConcepts().stream().map(c -> c.getName()).toList()
                    );
                }
            }
        }
    }
    return root;
}
}
