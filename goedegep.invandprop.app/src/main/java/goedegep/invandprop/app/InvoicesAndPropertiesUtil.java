package goedegep.invandprop.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class InvoicesAndPropertiesUtil {
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  private static InvoicesAndPropertiesRegistry invoicesAndPropertiesRegistry = InvoicesAndPropertiesRegistry.getInstance();
  
  /**
   * Constructor - private as this is a utility class
   */
  private InvoicesAndPropertiesUtil() {
    
  }

  /**
   * Prepend the base directory (defined by {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder}) to a file name, if this isn't an absolute path.
   * 
   * @param filename an absolute or relative file name.
   * @return {@code filename} if {@code filename} is absolute, {@code filename} prepended with the {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder} otherwise.
   */
  public static String prependBaseDirToRelativeFilename(String filename) {
    File file = new File(filename);
    if (!file.isAbsolute()) {
      file = new File(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), filename);
      return file.getAbsolutePath();
    } else {
      return filename;
    }
  }
  
  /**
   * Remove the base directory (defined by {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder}) from a file name, if this is an absolute path starting with the base directory.
   * 
   * @param filename an absolute or relative file name.
   * @return {@code filename} if {@code filename} is absolute, {@code filename} prepended with the {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder} otherwise.
   */
  public static String removeBaseDirFromAbsoluteFilename(String filename) {
    return null;  // TODO
  }

  public static void dumpData(InvoicesAndProperties invoicesAndProperties, BufferedWriter out) throws IOException {
    out.write("INVOICES AND PROPERTIES DATA DUMP");
    out.newLine();
    
    for (InvoiceAndProperty invoiceAndProperty: invoicesAndProperties.getInvoicseandpropertys()) {
      dumpInvoiceAndProperty(invoiceAndProperty, out);
    }
  }

  private static void dumpInvoiceAndProperty(InvoiceAndProperty invoiceAndProperty, BufferedWriter out) throws IOException {
    String string;
    FlexDate date;

    date = invoiceAndProperty.getDate();
    if (date != null) {
      out.write(FDF.format(date));
    }
    out.write('\t');
    string = invoiceAndProperty.getCompany();
    if (string != null) {
      out.write(string);
    }
    out.write('\t');
   
    writeInvoiceAndPropertyItem(invoiceAndProperty, out);
    out.newLine();
    for (InvoiceAndPropertyItem invoiceAndPropertyItem: invoiceAndProperty.getInvoiceandpropertyitems()) {
      out.write('\t');
      writeInvoiceAndPropertyItem(invoiceAndPropertyItem, out);
    }
  }

  private static void writeInvoiceAndPropertyItem(InvoiceAndPropertyItem invoiceAndPropertyItem, BufferedWriter out) throws IOException {
    String string;
    PgCurrency money;

    out.write(String.valueOf(invoiceAndPropertyItem.getNumberOfItems()));
    out.write('\t');
    string = invoiceAndPropertyItem.getDescription();
    if (string != null) {
      out.write(string);
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isSetBrand()) {
      out.write(invoiceAndPropertyItem.getBrand());
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isSetType()) {
      out.write(invoiceAndPropertyItem.getType());
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isSetSerialNumber()) {
      out.write(invoiceAndPropertyItem.getSerialNumber());
    }
    out.write('\t');
    money = invoiceAndPropertyItem.getAmount();
    if (money != null) {
      out.write(CF.format(money));
    }
    out.write('\t');
    string = invoiceAndPropertyItem.getRemarks();
    if (string != null) {
      out.write(string);
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isSetFromDate()) {
      out.write(FDF.format(invoiceAndPropertyItem.getFromDate()));
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isSetUntilDate()) {
      out.write(FDF.format(invoiceAndPropertyItem.getUntilDate()));
    }
    out.write('\t');
    if (invoiceAndPropertyItem.isArchive()) {
      out.write("archive");
    }
    out.newLine();
    
    for (FileReference fileReference: invoiceAndPropertyItem.getDocuments()) {
      out.write('\t');
      out.write("Document:");
      if (fileReference != null) {
        if (fileReference.getTitle() != null) {
          out.write(fileReference.getTitle());
        } else {
          out.write("<no title>");
        }
        out.write(":");
        out.write(fileReference.getFile());
      } else {
        out.write("<NULL Reference>");
      }
      out.newLine();
    }
    
    for (FileReference fileReference: invoiceAndPropertyItem.getPictures()) {
      out.write('\t');
      out.write("Picture:");
      if (fileReference.getTitle() != null) {
        out.write(fileReference.getTitle());
      }
      out.write(":");
      if (fileReference.getFile() != null) {
        out.write(fileReference.getFile());
      }
      out.newLine();
    }
  }

}
