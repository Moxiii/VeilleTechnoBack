package com.moxi.veilletechnoback.Pdf.chart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DougnutChartBuilder {

public byte[] createDoughnutChart(User user ) throws IOException, DocumentException {
	DefaultPieDataset dataset = new DefaultPieDataset();
	for(Project project : user.getProjects()){
		if(project.getTechnology() != null) {
			for(Technology tech : project.getTechnology()) {
				double currentValue = dataset.getKeys().contains(tech.getName())
                        ? dataset.getValue(tech.getName()).doubleValue()
                        : 0;
				dataset.setValue(tech.getName(), currentValue + 1);
			}
		};
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
