package com.moxi.veilletechnoback.Pdf.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Paragraph;
import com.moxi.veilletechnoback.DTO.Technology.TechUsageNode;
import com.moxi.veilletechnoback.Pdf.font.PdfFonts;
import com.moxi.veilletechnoback.Pdf.spacer.PdfSpacer;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TechUsageStats {
private final PdfFonts pdfFonts;
private final TechUsageCalculator techUsageCalculator;
private final PdfSpacer pdfSpacer;
 private double percent(long value, long total) {
        return total == 0 ? 0 : (value * 100.0) / total;
}

    private String format(double value) {
        return String.format("%.0f%%", value);
}

public Paragraph render(User user){
	TechUsageNode root = techUsageCalculator.calculate(user);
	Paragraph content = new Paragraph();
	content.setFont(pdfFonts.body());
	long total = root.getChildren()
                .values()
                .stream()
                .mapToLong(TechUsageNode::getUsageCount)
                .sum();

for (TechUsageNode parent : root.getChildren().values()) {

            double parentPercent = percent(parent.getUsageCount(), total);

            content.add(new Paragraph(
                    parent.getTechnology().getName()
                            + " : "
                            + format(parentPercent),
                    pdfFonts.body()
            ));

            for (TechUsageNode child : parent.getChildren().values()) {
                double childPercent = percent(
                        child.getUsageCount(),
                        parent.getUsageCount()
                );

                content.add(new Paragraph(
                        "   • "
                                + child.getTechnology().getName()
                                + " : "
                                + format(childPercent),
                        pdfFonts.body()
                ));
            }

            content.add(pdfSpacer.small());
        }

        return content;
}
}
