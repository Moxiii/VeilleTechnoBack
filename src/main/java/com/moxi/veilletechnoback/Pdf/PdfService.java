package com.moxi.veilletechnoback.Pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.moxi.veilletechnoback.Pdf.section.Header;
import com.moxi.veilletechnoback.Pdf.section.ProjectSection;
import com.moxi.veilletechnoback.Pdf.section.TechnologyThreeSection;
import com.moxi.veilletechnoback.Pdf.section.TechnologyUsageSection;

import com.itextpdf.text.pdf.PdfWriter;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



@Service
@RequiredArgsConstructor
public class PdfService {
private final Header header;
private final ProjectSection projectSection;
private final TechnologyUsageSection technologyUsageSection;
private final TechnologyThreeSection technologyThreeSection;

public ByteArrayInputStream generateUserReport(User user , PdfReportOptions options) throws IOException, DocumentException {
	Document document = new Document();
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	try{

	PdfWriter.getInstance(document , outputStream);
	document.open();
	header.render(document , user);
	projectSection.render(document,options, user);
	technologyUsageSection.render(document, user);	
	technologyThreeSection.render(document, user);
	} finally {
		document.close();
	}
	
	return new ByteArrayInputStream(outputStream.toByteArray());
}
}
