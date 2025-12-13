package com.moxi.veilletechnoback.Pdf.section.Utils;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddSectionTitle {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;
public void create(Document document, String titleText) throws DocumentException {
	Paragraph sectionTitle = new Paragraph(titleText, pdfFonts.title());
	sectionTitle.setAlignment(Paragraph.ALIGN_MIDDLE);
    pdfSpacer.title();
	document.add(sectionTitle);
}
}
