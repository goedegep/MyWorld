package goedegep.invandprop.app;

import goedegep.invandprop.model.Expenditure;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceItem;
import javafx.util.StringConverter;

public class ExpenditureStringConverter extends StringConverter<Expenditure>  {

  @Override
  public String toString(Expenditure expenditure) {
    if (expenditure == null) {
      return null;
    }
    
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;
    
    if (expenditure instanceof Invoice) {
      Invoice invoice = (Invoice) expenditure;
      
      if (invoice.getCompany() != null) {
        buf.append(invoice.getCompany());
        spaceNeeded = true;
      }
      
      if (invoice.getDescription() != null) {
        if (spaceNeeded) {
          buf.append(" ");
        }
        buf.append(invoice.getDescription());
      }
    } else if (expenditure instanceof InvoiceItem) {
      InvoiceItem invoiceItem = (InvoiceItem) expenditure;
      Invoice invoice = (Invoice) invoiceItem.eContainer();
      
      if (invoice.getCompany() != null) {
        buf.append(invoice.getCompany());
        spaceNeeded = true;
      }
      
      if (invoiceItem.getDescription() != null) {
        if (spaceNeeded) {
          buf.append(" ");
        }
        buf.append(invoiceItem.getDescription());
      }
    }
    
    return buf.toString();
  }

  @Override
  public Expenditure fromString(String string) {
    throw new UnsupportedOperationException("This method isn't implemented yet");
  }

}
