package com.moxi.veilletechnoback.Pdf.chart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.Pdf.Utils.Hierarchy.Resolver;
import com.moxi.veilletechnoback.Pdf.Utils.font.PdfFonts;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;


import lombok.RequiredArgsConstructor;

@Scope("prototype")
@Component
@RequiredArgsConstructor
public class DougnutChartBuilder {
	private final PdfFonts pdfFonts;
	private final Resolver hierarchyResolver;

public synchronized void addDoughnutChart(Document document, Project project) throws DocumentException, IOException {
	byte[] chartBytes = createDoughnutChart(project);
	if (chartBytes == null || chartBytes.length == 0) {
		document.add(new Paragraph("Impossible de générer le graphique des technologies.", pdfFonts.italic()));
		return;
	}
	
	Image chart = Image.getInstance(chartBytes);
	chart.scaleToFit(400f, 400f);
	chart.setAlignment(Element.ALIGN_CENTER);
	document.add(chart);
}
private synchronized byte[] createDoughnutChart(Project project ) throws IOException, DocumentException {
	DefaultPieDataset dataset = new DefaultPieDataset();
 if (project.getTechnology() == null || project.getTechnology().isEmpty()) {
        dataset.setValue("Aucune technologie", 1);
    } else {
        Map<String, Integer> rootCounts = new HashMap<>();

        for (Technology tech : project.getTechnology()) {
            Technology root = hierarchyResolver.getRootTechnology(tech);
			
            rootCounts.put(root.getName(), rootCounts.getOrDefault(root.getName(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : rootCounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
    }
	JFreeChart chart = ChartFactory.createRingChart(
			"",
			dataset,
			true,
			false,
			false
	);
	//chart.setTitle("graph-" + project.getId() + "-" + System.nanoTime());
	RingPlot plot = (RingPlot) chart.getPlot();
	plot.setLabelGenerator(null);
	plot.setSeparatorsVisible(false);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ChartUtils.writeChartAsPNG(baos, chart, 500, 400);
	return baos.toByteArray();
}
}
