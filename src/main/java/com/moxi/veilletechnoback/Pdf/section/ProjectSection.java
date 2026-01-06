package com.moxi.veilletechnoback.Pdf.section;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.moxi.veilletechnoback.Pdf.PdfReportOptions;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.section.Utils.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectSection {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;
private final AddSectionTitle addSectionTitle;

private PdfPCell createCell(String text , boolean isHeader  ){
    Font font = isHeader ? pdfFonts.tableHeader() : pdfFonts.tableBody();

    PdfPCell cell = new PdfPCell(new Paragraph(text , font));

	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

    cell.setHorizontalAlignment(isHeader ? Element.ALIGN_CENTER : Element.ALIGN_LEFT);
     if (isHeader) {
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    } else {
        cell.setBackgroundColor(BaseColor.WHITE);
    }
    cell.setPadding(8f);
	cell.setMinimumHeight(25f);
    cell.setBorderWidth(0.5f);
	return cell;
}
private void addProjectTable(Document document, List<Project> projects) throws DocumentException {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	PdfPTable table = new PdfPTable(4);
	table.setWidthPercentage(100);
    document.add(pdfSpacer.large());
	table.setWidths(new float[]{2f, 2f, 3f , 1f});

	table.addCell(createCell("Projet", true));
	table.addCell(createCell("Status", true));
	table.addCell(createCell("Technologies", true));
	table.addCell(createCell("Modifié le", true));

	

	for (Project p : projects) {
		table.addCell(createCell(p.getName(), false));
		table.addCell(createCell(p.getStatus().toString(), false));
		table.addCell(createCell(p.getTechnology().stream()
				.map(Technology::getName)
				.collect(Collectors.joining(", ")), false));
		String updatedAt = p.getUpdatedAt() != null ? 
		p.getUpdatedAt().format(formatter) :
		 "Non modifié";
		table.addCell(createCell(updatedAt, false));
	}
	document.add(table);
}
public void render(Document document , PdfReportOptions options , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Projets");
	List<Project> filteredrojects = user.getProjects().stream()
			.filter(p->options.isIncludeAllProjects() || 
			(options.getProjectIdsToInclude() != null && options.getProjectIdsToInclude().contains(p.getId())))
			.collect(Collectors.toList());
	if(!filteredrojects.isEmpty()){
		addProjectTable(document, filteredrojects);
	}

}
}
