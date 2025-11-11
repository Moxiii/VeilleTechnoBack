package com.moxi.veilletechnoback.Pdf;

import com.moxi.veilletechnoback.Technology.Technology;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.User.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfService {
private Font getTitleFont(){
	return new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
}
private Font getBodyFont(){
	return new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
}

private void addHeader(Document document, User user) throws DocumentException {
	Paragraph title = new Paragraph("Rapport de veille technologique", getTitleFont());
	title.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(title);

	Paragraph userInfo = new Paragraph("Utilisateur: " + user.getUsername() + "\nDate: " + LocalDate.now(), getBodyFont());
	userInfo.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(userInfo);

	document.add(Chunk.NEWLINE);
}
private void addProjectTable(Document document, List<Project> projects) throws DocumentException {
	PdfPTable table = new PdfPTable(projects.size());
	table.setWidthPercentage(100);
	table.setSpacingBefore(10f);
	table.setSpacingAfter(10f);

	table.addCell("Projet");
	table.addCell("Statut");
	table.addCell("Technologies");

	for (Project p : projects) {
		table.addCell(p.getName());
		table.addCell(p.getStatus().toString());
		table.addCell(p.getTechnology().stream()
				.map(Technology::getName)
				.collect(Collectors.joining(", ")));
	}
	document.add(table);
}
public ByteArrayInputStream generateUserReport(User user , PdfReportOptions options) throws IOException, DocumentException {
	Document document = new Document();
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	try{

	PdfWriter.getInstance(document , outputStream);
	document.open();
	addHeader(document, user);
	List<Project> filteredrojects = user.getProjects().stream()
			.filter(p->options.isIncludeAllProjects() || 
			(options.getProjectIdsToInclude() != null && options.getProjectIdsToInclude().contains(p.getId())))
			.collect(Collectors.toList());
	if(!filteredrojects.isEmpty()){
		addProjectTable(document, filteredrojects);}
		
	} finally {
		document.close();
	}
	
	return new ByteArrayInputStream(outputStream.toByteArray());
}
}
