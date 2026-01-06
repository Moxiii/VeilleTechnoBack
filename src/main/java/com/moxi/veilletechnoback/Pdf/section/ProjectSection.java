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
import com.moxi.veilletechnoback.Pdf.Utils.Section.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.Utils.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.Utils.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Project.Project;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectSection {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;
private final AddSectionTitle addSectionTitle;
private void addProjectDescritpion(Document document , List<Project> projects) throws DocumentException {
	document.add(pdfSpacer.tiny());
	document.add(new Paragraph("Description des projets:", pdfFonts.bold()));
	for(Project p : projects) {
		document.add(pdfSpacer.small());
		document.add(new Paragraph("Projet : " + p.getName(), pdfFonts.bold()));
	if (p.getPdfDescription() == null || p.getPdfDescription().isEmpty()) {
		document.add(new Paragraph("Aucune description disponible.", pdfFonts.italic()));
	} else {
		document.add(new Paragraph(p.getPdfDescription(), pdfFonts.body()));
	}
	document.add(pdfSpacer.small());
}
}
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
	PdfPTable table = new PdfPTable(3);
	table.setWidthPercentage(100);
    document.add(pdfSpacer.large());
	table.setWidths(new float[]{2f, 2f, 2f });

	table.addCell(createCell("Projet", true));
	table.addCell(createCell("Status", true));
	table.addCell(createCell("Modifié le", true));

	for (Project p : projects) {
		table.addCell(createCell(p.getName(), false));
		table.addCell(createCell(p.getStatus().toString(), false));
		String updatedAt = p.getUpdatedAt() != null ? 
		p.getUpdatedAt().format(formatter) :
		 "Non modifié";
		table.addCell(createCell(updatedAt, false));
	}
	document.add(table);
}
public void render(Document document , PdfReportOptions options , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Projets");
	List<Project> filteredprojects = user.getProjects().stream()
			.filter(p->options.isIncludeAllProjects() || 
			(options.getProjectIdsToInclude() != null && options.getProjectIdsToInclude().contains(p.getId())))
			.collect(Collectors.toList());
	if(!filteredprojects.isEmpty()){
		addProjectDescritpion(document, filteredprojects);
		addProjectTable(document, filteredprojects);
	}

}
}
