package com.moxi.veilletechnoback.Pdf.section;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.section.Utils.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.Concepts.Concepts;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TechnologyThreeSection {
private final PdfSpacer pdfSpacer;
private final AddSectionTitle addSectionTitle;
private void addTechThree(Document document , List<Technology> techs) throws DocumentException {
		for(Technology tech : techs){
		document.add(new Paragraph(tech.getName()));
		if(tech.getConcepts() != null && !tech.getConcepts().isEmpty()){
			for(Concepts concept : tech.getConcepts()){
				document.add(new Paragraph(" - " + concept.getName()));
			}
		} else {
			document.add(new Paragraph("Aucune notion associée."));
		}
		if(!tech.getSubTechnologies().isEmpty()){
			addTechThree(document, tech.getSubTechnologies());
		}
	}
}
public void render(Document document , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Notions aborder par technologie");
	document.add(pdfSpacer.small());
	addTechThree(document, user.getProjects().stream()
			.flatMap(p -> p.getTechnology().stream())
			.distinct()
			.collect(Collectors.toList()));
}
}
