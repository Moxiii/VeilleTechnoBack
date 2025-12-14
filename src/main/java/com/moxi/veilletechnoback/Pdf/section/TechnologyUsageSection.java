package com.moxi.veilletechnoback.Pdf.section;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import com.moxi.veilletechnoback.Pdf.chart.DougnutChartBuilder;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.model.TechUsageStats;
import com.moxi.veilletechnoback.Pdf.section.Utils.AddSectionTitle;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TechnologyUsageSection {
private final PdfFonts pdfFonts;
private final PdfSpacer pdfSpacer;
private final AddSectionTitle addSectionTitle;
private final TechUsageStats techUsageStats;
private final DougnutChartBuilder dougnutChartBuilder;
private void addDoughnutChart(Document document, byte[] chartBytes) throws DocumentException, IOException {
	com.itextpdf.text.Image chart = com.itextpdf.text.Image.getInstance(chartBytes);
	chart.scaleToFit(400f, 400f);
	chart.setAlignment(Element.ALIGN_CENTER);
    document.add(pdfSpacer.large());
	document.add(chart);
}
public void render(Document document , User user) throws DocumentException, IOException {
	addSectionTitle.create(document, "Utilisation des technologies");
	document.add(pdfSpacer.small());
	byte[] chartBytes = dougnutChartBuilder.createDoughnutChart(user );
	 if (chartBytes != null && chartBytes.length > 0) {
        addDoughnutChart(document, chartBytes);
    } else {
        Paragraph placeholder = new Paragraph("Graphique indisponible");
        placeholder.setAlignment(Element.ALIGN_CENTER);
        document.add(placeholder);
    }

	Paragraph usageText = new Paragraph("Pourcentage d'utilisation des technologies dans les projets :", pdfFonts.title());
	document.add(pdfSpacer.medium());
	usageText.setAlignment(Element.ALIGN_LEFT);
	document.add(usageText);
	document.add(pdfSpacer.small());
	document.add(techUsageStats.render(user));

}
}
