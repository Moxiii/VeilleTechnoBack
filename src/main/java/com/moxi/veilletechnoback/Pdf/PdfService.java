package com.moxi.veilletechnoback.Pdf;
import com.moxi.veilletechnoback.Technology.Technology;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
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
private Font getHeaderFont(){
	return new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
}

private Font getTitleFont(){
	return new Font(Font.FontFamily.HELVETICA, 15, Font.NORMAL);
}
private Font getBodyFont(){
	return new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
}
private PdfPCell createCell(String text , boolean isHeader  ){
	PdfPCell cell = new PdfPCell(new Paragraph(text));
	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	cell.setPadding(8f);
	cell.setMinimumHeight(25f);
     if (isHeader) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
    } else {
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 11)));
    }
    cell.setBorderWidth(0.5f);
	return cell;
}
private void addHeader(Document document, User user) throws DocumentException {
	Paragraph title = new Paragraph("Rapport de veille technologique", getHeaderFont());
	title.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(title);

	Paragraph date = new Paragraph("\nDate: " + LocalDate.now(), getBodyFont());
	date.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(date);

	document.add(Chunk.NEWLINE);
}
private void addSectionTitle(Document document, String titleText) throws DocumentException {
	Paragraph sectionTitle = new Paragraph(titleText, getTitleFont());
	sectionTitle.setAlignment(Paragraph.ALIGN_MIDDLE);
	sectionTitle.setSpacingBefore(10f);
	sectionTitle.setSpacingAfter(10f);
	document.add(sectionTitle);
}
private void addDoughnutChart(Document document, byte[] chartImage) throws DocumentException, IOException {
	com.itextpdf.text.Image chart = com.itextpdf.text.Image.getInstance(chartImage);
	chart.scaleToFit(400f, 400f);
	chart.setAlignment(Element.ALIGN_CENTER);
	chart.setSpacingBefore(15f);
	chart.setSpacingAfter(20f);
	document.add(chart);
}
private void addSmallSpacer(Document document) throws DocumentException {
    Paragraph spacer = new Paragraph();
    spacer.setSpacingAfter(8f);
    document.add(spacer);
}

private void addLargeSpacer(Document document) throws DocumentException {
    Paragraph spacer = new Paragraph();
    spacer.setSpacingAfter(20f);
    document.add(spacer);
}
private void addProjectTable(Document document, List<Project> projects) throws DocumentException {
	PdfPTable table = new PdfPTable(3);
	table.setWidthPercentage(100);
	table.setSpacingBefore(10f);
	table.setSpacingAfter(10f);
	table.setWidths(new float[]{2f, 1f, 3f});

	table.addCell(createCell("Projet", true));
	table.addCell(createCell("Status", true));
	table.addCell(createCell("Technologies", true));

	for (Project p : projects) {
		table.addCell(createCell(p.getName(), false));
		table.addCell(createCell(p.getStatus().toString(), false));
		table.addCell(createCell(p.getTechnology().stream()
				.map(Technology::getName)
				.collect(Collectors.joining(", ")), false));
	}
	document.add(table);
}
private void addProjectSection(Document document , PdfReportOptions options , User user) throws DocumentException, IOException {
	addSectionTitle(document, "Projets");
	List<Project> filteredrojects = user.getProjects().stream()
			.filter(p->options.isIncludeAllProjects() || 
			(options.getProjectIdsToInclude() != null && options.getProjectIdsToInclude().contains(p.getId())))
			.collect(Collectors.toList());
	if(!filteredrojects.isEmpty()){
		addProjectTable(document, filteredrojects);
	}
	addSmallSpacer(document);
}
private void addTechnologyUsageSection(Document document , PdfReportOptions options , User user) throws DocumentException, IOException {
	addSectionTitle(document, "Utilisation des technologies");
	byte[] dummyChartImage = new byte[0];
	addDoughnutChart(document, dummyChartImage);
	addLargeSpacer(document);
}
public ByteArrayInputStream generateUserReport(User user , PdfReportOptions options) throws IOException, DocumentException {
	Document document = new Document();
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	try{

	PdfWriter.getInstance(document , outputStream);
	document.open();
	addHeader(document, user);
	addProjectSection(document, options, user);
		
	} finally {
		document.close();
	}
	
	return new ByteArrayInputStream(outputStream.toByteArray());
}
}
