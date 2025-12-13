package com.moxi.veilletechnoback.Pdf.section;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Header {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;

public void render(Document document, User user) throws DocumentException {
	Paragraph title = new Paragraph("Rapport de veille technologique", pdfFonts.header());
	title.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(title);
	Paragraph userName = new Paragraph("Utilisateur: " + user.getUsername(), pdfFonts.body());
	userName.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(userName);
	Paragraph date = new Paragraph("\nDate: " + LocalDate.now(), pdfFonts.body());
	date.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(date);
	pdfSpacer.title();
}
}
