package goedegep.docgen.rtf;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;


public class RtfTableCreator {
  private static final Logger         LOGGER = Logger.getLogger(RtfTableCreator.class.getName());

  public static final String FONT_NAME = "Arial";
  public static final Font HEADER_FONT = new RtfFont(FONT_NAME, 10, RtfFont.STYLE_BOLD);
  public static final Font CELL_FONT = new RtfFont(FONT_NAME, 10);

  public static void CreateTable(String[][] tableData, boolean firstRowIsHeader, Float tableWidth, float[] columnWidths) {
    LOGGER.severe("=>");

    if (tableData.length == 0) {
      throw new IllegalArgumentException("tableData shall have at least one row");
    }

    String[] firstRowData = tableData[0];
    int nrOfColums = firstRowData.length;
    LOGGER.severe("nrOfColums=" + nrOfColums);

    String documentName = "RtfTableExample.rtf";
    LOGGER.severe("documentName=" + documentName);

    try {
      Document document=new Document();
      RtfWriter2.getInstance(document, new FileOutputStream(documentName));
      Table table = null;
      table = new Table(nrOfColums);
      if (tableWidth == null) {
        tableWidth = 83f;
      }
      table.setWidth(tableWidth);
      if (columnWidths == null) {
        columnWidths = new float[]{1f, 3f, 3f};
      }
      table.setWidths(columnWidths);

      Paragraph paragraph;

      int rowIndex = 0;
      for (String[] tableRow: tableData) {
        boolean firstRow = (rowIndex == 0);
        boolean secondRow = (rowIndex == 1);
        boolean lastRow = (rowIndex == tableData.length - 1);

        int columnIndex = 0;
        for (String cellData: tableRow) {
          boolean firstColumn = (columnIndex == 0);
          boolean lastColumn = (columnIndex == tableRow.length - 1);
          paragraph = new Paragraph(cellData);
          if (firstRowIsHeader  &&  firstRow) {
            paragraph.setFont(HEADER_FONT);
          } else {
            paragraph.setFont(CELL_FONT);
          }
          paragraph.setIndentationLeft(6f);
          RtfCell roadTypeCell = new RtfCell(paragraph);
          RtfBorderGroup roadTypeCellBorders=new RtfBorderGroup();
          if (firstRow  ||
              (secondRow  &&  firstRowIsHeader)) {
            roadTypeCellBorders.addBorder(Rectangle.TOP, RtfBorder.BORDER_THICK_THIN_LARGE, 1, Color.BLACK);
          } else {
            roadTypeCellBorders.addBorder(Rectangle.TOP, RtfBorder.BORDER_DOUBLE, 1, Color.BLACK);
          }
          if (lastRow) {
            roadTypeCellBorders.addBorder(Rectangle.BOTTOM, RtfBorder.BORDER_THICK_THIN_LARGE, 1, Color.BLACK);
          } else {
            roadTypeCellBorders.addBorder(Rectangle.BOTTOM, RtfBorder.BORDER_DOUBLE, 1, Color.BLACK);
          }
          if (firstColumn) {
            roadTypeCellBorders.addBorder(Rectangle.LEFT, RtfBorder.BORDER_THICK_THIN_LARGE, 1, Color.BLACK);
          } else {
            roadTypeCellBorders.addBorder(Rectangle.LEFT, RtfBorder.BORDER_DOUBLE, 1, Color.BLACK);
          }
          if (lastColumn) {
            roadTypeCellBorders.addBorder(Rectangle.RIGHT, RtfBorder.BORDER_THICK_THIN_LARGE, 1, Color.BLACK);
          } else {
            roadTypeCellBorders.addBorder(Rectangle.RIGHT, RtfBorder.BORDER_DOUBLE, 1, Color.BLACK);
          }           
          roadTypeCell.setBorders(roadTypeCellBorders);
          table.addCell(roadTypeCell);

          columnIndex++;
        }

        rowIndex++;
      }

      document.open();
      document.add(table);
      document.close();
    }
    catch (  FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    catch (  DocumentException de) {
      de.printStackTrace();
    }
  }

  public static void main(String args[]) {
    String[][] tableData = {
        {"Rank", "Color", "Points"},
        {"first", "green", "200"},
        {"second", "red", "650"},
        {"third", "black", "0"},
        {"last", "yellow", "9999"},
    };

    CreateTable(tableData, true, null, null);
  }
}
