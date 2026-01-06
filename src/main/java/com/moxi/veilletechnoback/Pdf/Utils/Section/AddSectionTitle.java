package com.moxi.veilletechnoback.Pdf.Utils.Section;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.Utils.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.Utils.spacer.PdfSpacer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddSectionTitle {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;
public void create(Document document, String titleText) throws DocumentException {
	Paragraph sectionTitle = new Paragraph(titleText, pdfFonts.title());
	sectionTitle.setAlignment(Paragraph.ALIGN_MIDDLE);
    document.add(pdfSpacer.title());
	document.add(sectionTitle);
	document.add(pdfSpacer.medium());
}
}
