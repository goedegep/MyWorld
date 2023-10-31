package goedegep.invandprop.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.invandprop.model.Invoices;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Properties;
import goedegep.invandprop.model.Property;
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class InvoicesAndPropertiesUtil {
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  
  /**
   * Constructor - private as this is a utility class
   */
  private InvoicesAndPropertiesUtil() {
    
  }
  

  /**
   * Prepend the base directory (define by {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder}) to a file name, if this isn't an absolute path.
   * 
   * @param filename an absolute or relative file name.
   * @return {@code filename} if it {@code filename} is absolute, {@code filename} prepended with the {@code InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder} otherwise.
   */
  public static String prependBaseDirToRelativeFilename(String filename) {
    File file = new File(filename);
    if (!file.isAbsolute()) {
      file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, filename);
      return file.getAbsolutePath();
    } else {
      return filename;
    }
  }

  public static void dumpData(InvoicesAndProperties invoicesAndProperties, BufferedWriter out) throws IOException {
    out.write("INVOICES AND PROPERTIES DATA DUMP");
    out.newLine();
    dumpInvoices(invoicesAndProperties.getInvoices(), out);
    dumpProperties(invoicesAndProperties.getProperties(), out);
  }

  private static void dumpInvoices(Invoices invoices, BufferedWriter out) throws IOException {
    out.write("INVOICES");
    out.newLine();
    String string;
    FlexDate date;
    PgCurrency money;

    for (Invoice invoice: invoices.getInvoices()) {
      date = invoice.getDate();
      if (date != null) {
        out.write(FDF.format(date));
      }
      out.write('\t');
      string = invoice.getCompany();
      if (string != null) {
        out.write(string);
      }
      out.write('\t');
      string = invoice.getDescription();
      if (string != null) {
        out.write(string);
      }
      out.write('\t');
      money = invoice.getAmount();
      if (money != null) {
        out.write(CF.format(money));
      }
      out.write('\t');
      string = invoice.getRemarks();
      if (string != null) {
        out.write(string);
      }
      out.write('\t');
      if (invoice.getPurchase() != null) {
        out.write("reference to property");
      }
      out.newLine();
      for (InvoiceItem invoiceItem: invoice.getInvoiceItems()) {
        out.write('\t');
        out.write(String.valueOf(invoiceItem.getNumberOfItems()));
        out.write('\t');
        string = invoiceItem.getDescription();
        if (string != null) {
          out.write(string);
        }
        out.write('\t');
        money = invoiceItem.getAmount();
        if (money != null) {
          out.write(CF.format(money));
        }
        out.write('\t');
        string = invoiceItem.getRemarks();
        if (string != null) {
          out.write(string);
        }
        out.write('\t');
        if (invoiceItem.getPurchase() != null) {
          out.write("reference to property");
        }
        out.newLine();
      }
    }
  }

  private static void dumpProperties(Properties properties, BufferedWriter out) throws IOException {
    out.write("PROPERTIES");
    out.newLine();

    for (Property property: properties.getProperties()) {
      if (property.isSetDescription()) {
        out.write(property.getDescription());
      }
      out.write('\t');
      if (property.isSetBrand()) {
        out.write(property.getBrand());
      }
      out.write('\t');
      if (property.isSetType()) {
        out.write(property.getType());
      }
      out.write('\t');
      if (property.isSetSerialNumber()) {
        out.write(property.getSerialNumber());
      }
      out.write('\t');
      if (property.isSetRemarks()) {
        out.write(property.getRemarks());
      }
      out.write('\t');
      if (property.isSetFromDate()) {
        out.write(FDF.format(property.getFromDate()));
      }
      out.write('\t');
      if (property.isSetUntilDate()) {
        out.write(FDF.format(property.getUntilDate()));
      }
      out.write('\t');
      if (property.isArchive()) {
        out.write("archive");
      }
      out.write('\t');
      if ( property.getExpenditure() != null) {
        out.write("reference to invoice");
      }
      out.newLine();
      
      for (FileReference fileReference: property.getDocuments()) {
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
      
      for (FileReference fileReference: property.getPictures()) {
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
}
