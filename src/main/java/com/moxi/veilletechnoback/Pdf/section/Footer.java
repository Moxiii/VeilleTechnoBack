package com.moxi.veilletechnoback.Pdf.section;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.Utils.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.Utils.spacer.PdfSpacer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Footer {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;

public void render(Document document) throws DocumentException {

    Paragraph footer = new Paragraph("Ce rapport est généré automatiquement par l’outil de suivi de projets.\n" +
                "Il reflète l’état actuel des projets et de la veille associée." , pdfFonts.italic());
    footer.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(pdfSpacer.large());
    document.add(footer);
    document.add(pdfSpacer.large());

}
}