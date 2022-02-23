package goedegep.app.finan.gen;

import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.util.sax.AbstractValidatingHandler;

import org.xml.sax.SAXException;

public abstract class AccountContentHandler<T> extends AbstractValidatingHandler<T> {
  public abstract void setAccount(SumAccount sumAccount, Bank bank, PgAccount account);
  
  public abstract String transactionToXmlString(PgTransaction transaction, String nameSpace) throws SAXException;
}
