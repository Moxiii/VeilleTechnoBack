package com.moxi.veilletechnoback.Pdf.section;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.chart.DougnutChartBuilder;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.section.Utils.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.Concepts.Concepts;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TechnologyThreeSection {
private final PdfSpacer pdfSpacer;
private final AddSectionTitle addSectionTitle;
private final PdfFonts pdfFonts;
private static final int INDENT_SIZE = 3;
private final DougnutChartBuilder dougnutChartBuilder;


private void addIndentedParagraph(Document document , int level , String name) throws DocumentException {
	document.add(new Paragraph(getIndent(level) + "- " + name));
}
private String getIndent(int level){
	return " ".repeat(level * INDENT_SIZE);
}	
private boolean markTechSeen(Set<Technology> seenTech , Technology tech){
	return seenTech.add(tech);
}
private boolean markConceptSeen(Set<String> seenConcepts , Concepts concepts){
	return seenConcepts.add(concepts.getName());
}
private void addTechRecursive(Document document , Technology tech , int level , Set<Technology> seenTech , Project project) throws DocumentException {
	if(!markTechSeen(seenTech, tech)) return;
	
	addIndentedParagraph(document, level, tech.getName());

	Set<String> seenConcepts = new HashSet<>();
	if (tech.getConcepts() != null && !tech.getConcepts().isEmpty()) {
		for (Concepts concept : tech.getConcepts()) {
			if (concept.getProjects().contains(project) && markConceptSeen(seenConcepts, concept)) {
				addIndentedParagraph(document, level + 1, concept.getName());
			}
		}
}
	if (tech.getSubTechnologies() != null) {
		for (Technology subTech : tech.getSubTechnologies()) {
			addTechRecursive(document, subTech, level + 1, seenTech, project);
		}
	}
}

private void addTechnologyWithConcepts(Document document , Project project) throws DocumentException {
	Set<Technology> seenTech = new HashSet<>();
    document.add(new Paragraph("Projet : " + project.getName(), pdfFonts.bold()));

    if (project.getTechnology() == null || project.getTechnology().isEmpty()) {
        document.add(new Paragraph("   Aucune technologie associée."));
        return;
    }

    for (Technology tech : project.getTechnology()) {
        addTechRecursive(document, tech, 1, seenTech, project);
    }
}
private void addTechThree(Document document , List<Project> projects) throws DocumentException {

	for (Project project : projects){
		addTechnologyWithConcepts(document, project);
		document.add(pdfSpacer.small());
	}

}
public void render(Document document , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Notions aborder par technologie");
	document.add(pdfSpacer.small());
	List<Project>userProjects = user.getProjects();
	addTechThree(document, userProjects);
}
}
