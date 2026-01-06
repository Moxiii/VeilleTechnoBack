package com.moxi.veilletechnoback.Pdf.section;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moxi.veilletechnoback.Category.Category;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.Utils.Hierarchy.Resolver;
import com.moxi.veilletechnoback.Pdf.Utils.Section.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.Utils.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.Utils.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Pdf.chart.DougnutChartBuilder;
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
private final ObjectProvider<DougnutChartBuilder> dougnutChartBuilderProvider;
private final Resolver hierarchyResolver;

private void addIndentedParagraph(Document document , int level , String name) throws DocumentException {
	document.add(new Paragraph(getIndent(level) + "- " + name));
}
private String getIndent(int level){
	return " ".repeat(level * INDENT_SIZE);
}
private Map<Category , List<Concepts>> groupConceptsByCategory(Project project){
	Map<Category , List<Concepts>> groupedConcepts = new HashMap<>();
	for(Concepts concept : project.getConcepts()) {
		groupedConcepts.computeIfAbsent(concept.getCategory(), k -> new ArrayList<>()).add(concept);
	}
	return groupedConcepts;
}
private void addConcepts(Document document , Project project) throws DocumentException {
Map<Category , List<Concepts>> groupedConcepts = groupConceptsByCategory(project);
if (groupedConcepts.isEmpty()) {
	document.add(new Paragraph("Aucun concept associé à ce projet.", pdfFonts.italic()));
	return;
}
for(Category category : groupedConcepts.keySet()) {
	List<Concepts> list = groupedConcepts.get(category);
	if (list != null && !list.isEmpty()) {
		Paragraph title = new Paragraph(category.getName(), pdfFonts.bold());
		document.add(title);
		for(Concepts concept : list) {	
			addIndentedParagraph(document, 1, concept.getName());
		}
	}
	document.add(pdfSpacer.small());
}
}
private void addTechnologyWithConcepts(Document document , Project project) 
	throws DocumentException , IOException {

    document.add(new Paragraph("Projet : " + project.getName(), pdfFonts.bold()));
	document.add(pdfSpacer.small());
	DougnutChartBuilder dougnutChartBuilder = dougnutChartBuilderProvider.getObject();

	dougnutChartBuilder.addDoughnutChart(document, project);
   
	document.add(pdfSpacer.small());
	addLibrairies(document,project);
	document.add(pdfSpacer.small());
	addConcepts(document,project);
 
}
private void addTechThree(Document document , List<Project> projects) throws DocumentException ,IOException {

	for (Project project : projects){
		addTechnologyWithConcepts(document, project);
		document.add(pdfSpacer.small());
	}

}
private void addLibrairies(Document document , Project project) throws DocumentException {
	document.add(new Paragraph("Librairies utilisées :", pdfFonts.bold()));
	Map<String, List<String>> libsByParent = new HashMap<>();
	for(Technology tech : project.getTechnology()) {
		if(tech.getParent() != null){
			Technology root = hierarchyResolver.getRootTechnology(tech);
			libsByParent.computeIfAbsent(root.getName(), k -> new ArrayList<>()).add(tech.getName());
		}
	}
if (libsByParent.isEmpty()) {
		document.add(new Paragraph("Aucune librairie associée à ce projet.", pdfFonts.italic()));
		return;
	}
  for (Map.Entry<String, List<String>> entry : libsByParent.entrySet()) {
        String parent = entry.getKey();
        List<String> libs = entry.getValue();

        document.add(new Paragraph(
            parent + " : " + String.join(", ", libs),
            pdfFonts.body()
        ));
    }	
	document.add(pdfSpacer.small());
}
public synchronized void render(Document document , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Notions aborder par projet");
	List<Project>userProjects = user.getProjects();
	addTechThree(document, userProjects);

}
}
