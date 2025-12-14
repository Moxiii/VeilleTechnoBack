package com.moxi.veilletechnoback.Pdf.chart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;

import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DougnutChartBuilder {
private Technology findRooTechnology(Technology tech) {
	Technology current = tech;
	while(current.getParent() != null) {
		current = current.getParent();
	}
	return current;
}
public byte[] createDoughnutChart(User user ) throws IOException, DocumentException {
	DefaultPieDataset dataset = new DefaultPieDataset();

	for(Project project : user.getProjects()){
		Set<Technology> rootUsedInProject = new HashSet<>();
		if(project.getTechnology() != null) {

			for(Technology tech : project.getTechnology()) {
			Technology root = findRooTechnology(tech);
			rootUsedInProject.add(root);
			}
		};
		for(Technology root : rootUsedInProject) {
			String techName = root.getName();
			double currentvalue = dataset.getKeys().contains(root.getName())? dataset.getValue(techName).doubleValue() : 0.0;
			dataset.setValue(techName, currentvalue + 1);
		}
	}
	 if (dataset.getItemCount() == 0) {
        return new byte[0];
    }
	JFreeChart chart = ChartFactory.createRingChart(
			"",
			dataset,
			true,
			false,
			false
	);
	RingPlot plot = (RingPlot) chart.getPlot();
	plot.setLabelGenerator(null);
	plot.setSeparatorsVisible(false);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ChartUtils.writeChartAsPNG(baos, chart, 500, 400);
	return baos.toByteArray();
}
}
