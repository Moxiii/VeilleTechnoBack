package com.moxi.veilletechnoback.Pdf.Utils.Section;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class WrapperTable {
    private final PdfPTable table;

    public WrapperTable(PdfPTable table) {
        this.table = new PdfPTable(1);
        this.table.setWidthPercentage(100);
        this.table.setSplitLate(false);
        this.table.setSplitRows(false);
    }
    public void add(Element element) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(element);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPadding(0);
        table.addCell(cell);
    }
}
