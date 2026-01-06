package com.moxi.veilletechnoback.Pdf.Utils.font;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Font;



@Component

public class PdfFonts {
    private final Font header;
    private final Font title;
    private final Font body;
    private final Font small;
    private final Font italic;
    private final Font tableHeader;
    private final Font tableBody;
    private final Font bold;

    public PdfFonts(){
        this.header = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        this.title = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        this.body = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        this.bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        this.small = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        this.italic = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
        this.tableHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        this.tableBody = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
}
   public Font header() {
        return header;
    }

    public Font title() {
        return title;
    }

    public Font body() {
        return body;
    }
    public Font small() {
        return small;
    }
    public Font italic() {
        return italic;
    }
    public Font tableHeader() {
        return tableHeader;
    }
    public Font tableBody() {
        return tableBody;
    }
    public Font bold() {
        return bold;
    }
}
