package com.moxi.veilletechnoback.Pdf.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TechUsageStats {
private final PdfFonts pdfFonts;
public Paragraph calculateTechnologyUsagePercent(User user){
	Map<String , Long> techCount = new HashMap<>();
	long totalCount = 0;
	for(Project project : user.getProjects()){
		if(project.getTechnology() != null) {
			for(Technology tech : project.getTechnology()) {
				techCount.put(tech.getName(), techCount.getOrDefault(tech.getName(), 0L) + 1);
				totalCount++;
			}
		};
	}
	Paragraph paragraph = new Paragraph("" , pdfFonts.body());
	for(Map.Entry<String, Long> entry : techCount.entrySet()){
		double percent = (entry.getValue() * 100.0) / totalCount;
		paragraph.add(new Paragraph(entry.getKey() + ": " + String.format("%.0f", percent) + "%\n"));
	}
	return paragraph;
}
}
