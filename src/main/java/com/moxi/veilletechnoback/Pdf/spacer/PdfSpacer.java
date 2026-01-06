package com.moxi.veilletechnoback.Pdf.spacer;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Paragraph;

@Component
public class PdfSpacer {
    private Paragraph spacer(float space){
        Paragraph p  = new Paragraph("\u00A0");
        p.setSpacingAfter(space);
        return p;
    };

  public Paragraph title() {
        return spacer(14f);
    }

    public Paragraph large() {
        return spacer(10f);
    }

    public Paragraph medium() {
        return spacer(6f);
    }

    public Paragraph small() {
        return spacer(3f);
    }
    public Paragraph tiny() {
        return spacer(1f);
    }
}
