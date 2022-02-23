package goedegep.app.finan.gen;

import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;

import org.xml.sax.SAXException;

public interface ContentHandlerExtension extends org.xml.sax.ContentHandler {
  public abstract void setAccount(SumAccount sumAccount, Bank account);
  
  public abstract String transactionToXmlString(PgTransaction transaction, String nameSpace) throws SAXException;
}