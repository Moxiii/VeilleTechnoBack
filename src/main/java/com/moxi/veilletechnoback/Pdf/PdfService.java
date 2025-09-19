package com.moxi.veilletechnoback.Pdf;

import com.moxi.veilletechnoback.Technology.Technology;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import java.util.stream.Collectors;

@Service
public class PdfService {
public ByteArrayInputStream generateUserReport(User user) throws IOException, DocumentException {
	Document document = new Document();
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	PdfWriter.getInstance(document , outputStream);
	document.open();
	document.add(new Paragraph("Rapport de veille technologique"));
	document.add(new Paragraph("Utilisateur: " + user.getUsername()));
	document.add(new Paragraph("Date: " + LocalDate.now()));
	PdfPTable table = new PdfPTable(3);
	table.addCell("Projet");
	table.addCell("Statut");
	table.addCell("Technologies");

	for (Project p : user.getProjects()) {
		table.addCell(p.getName());
		table.addCell(p.getStatus().toString());
		table.addCell(p.getTechnology().stream()
				.map(Technology::getName)
				.collect(Collectors.joining(", ")));
	}
	document.add(table);
	document.close();
	return new ByteArrayInputStream(outputStream.toByteArray());
}
}
