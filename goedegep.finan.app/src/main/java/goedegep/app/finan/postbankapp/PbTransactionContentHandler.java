package goedegep.app.finan.postbankapp;

import goedegep.app.finan.gen.AccountContentHandler;
import goedegep.app.finan.gen.ContentHandlerExtension;
import goedegep.app.finan.gen.XmlTags;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.postbank.pbbasic.PbBasic;
import goedegep.finan.postbank.pbfonds.PbFondsId;
import goedegep.finan.postbank.pbrek.PbRek;
import goedegep.finan.postbank.pbsprek.PbSpRekId;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sgml.SgmlUtil;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class PbTransactionContentHandler
    extends AbstractValidatingHandler<PbTransactionContentHandler.State>
    implements ContentHandlerExtension {
  /*
   * In all situations, this content handler calls the content handler
   * for the current Postbank account.
   */

  private SumAccount   sumAccount = null; // The SumAccount to which transactions are to be added.
  private PbRek        bank = null;       // The bank to which the transactions apply.
  private String       transactionTag = null; // This is saved to recognize the closing tag.

  private ContentHandler accountHandler = null;  // Handler for the account for which a transaction is being handled.

  // Map account name to the handler class for that account.
  @SuppressWarnings({ "rawtypes" })
  static HashMap<String, Class> accountHandlerClassMap = new HashMap<String, Class>();
  
  // Map account name, as found in a transaction, to the handler for that account.
  static HashMap<String, DefaultHandler> accountHandlerMap =
    new HashMap<String, DefaultHandler>();
  
  static {
    for (PbSpRekId pbSpRekId: PbSpRekId.values()) {
      accountHandlerClassMap.put(pbSpRekId.getName(), PbSpRekTransactionContentHandler.class);      
    }
    for (PbFondsId pbFondsId: PbFondsId.values()) {
      accountHandlerClassMap.put(pbFondsId.getPbFundName(), PbFondsTransactionContentHandler.class);      
    }
    accountHandlerClassMap.put(PbBasic.effectenRekeningString, PbEffRekTransactionContentHandler.class);
  }
  
  public PbTransactionContentHandler() {
  }

  public void setAccount(SumAccount sumAccount, Bank bank) {
    this.sumAccount = sumAccount;
    this.bank = (PbRek) bank;
    
    state = State.START;
  }

  @SuppressWarnings({ "rawtypes" })
  public String transactionToXmlString(PgTransaction transaction, String nameSpace) throws SAXException {
    String  rekeningString = transaction.getAccount().getName();

    accountHandler = handlerForAccountName(rekeningString);
    
    return SgmlUtil.createElement(nameSpace, "Rekening", rekeningString) + " " +
    ((AccountContentHandler) accountHandler).transactionToXmlString(transaction, nameSpace);
  }
  
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("PbTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.ACCOUNT_TAG) == 0) {
        pushState(State.ACCOUNT);
      } else {
        throw new SAXParseException("Ongeldig element " + localpart +
            " voor " + bank.getName() + "transactie.", locator);
      }
      break;

    case GET_TRANSACTION_TAG:
      transactionTag = localpart;
      pushState(State.TRANSACTION);
      // FALL THROUGH !
    
    case TRANSACTION:
      accountHandler.startElement(uri, localpart, rawname, attributes);
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("PbTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;

    case ACCOUNT:
      if (localpart.compareTo(XmlTags.ACCOUNT_TAG) == 0) {
        accountHandler = handlerForAccountName(data);
        popState();
        pushState(State.GET_TRANSACTION_TAG);
      }
      break;
      
    case TRANSACTION:
      // in this state all information is passed on to the handler of the bank.
      // here we only have to check the end of the 'transaction' tag.
      accountHandler.endElement(uri, localpart, rawname);
      if (localpart.compareTo(transactionTag) == 0) {
        accountHandler = null;
        popState();  // back to GET_TRANSACTION_TAG
        popState();  // back to START
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  public void characters(char[] ch, int offset, int length) throws SAXException {
    //System.out.println("Finan TransactionContentHandler characters = " + new String(ch, offset, length));
    if (state != State.TRANSACTION) {
      super.characters(ch, offset, length);
    } else {
      accountHandler.characters(ch, offset, length);
    }
  }
  
//  private void reportTransactionContentError(String rekening, String transactionType, PgTransaction transaction) throws SAXParseException {
//    this.printStatus("Ongeldige transactie inhoud gevonden voor Postbank" +
//        rekening + "." + System.getProperty("line.separator") +
//        "Soort transactie: " + transactionType + System.getProperty("line.separator") +
//        "Transactie is : " + transaction.toString());
//  }

//  private void printStatus(String message) throws SAXParseException {
//    System.out.println("Fout in regel " + locator.getLineNumber() + ", kolom " + locator.getColumnNumber());
//    System.out.println("_state = " + state);
//    System.out.println("_data = " + data);
//    if (bank != null) {
//      System.out.println("_pbRek is set");
//    }
//    
//    throw new SAXParseException(message, null, null, locator.getLineNumber(), locator.getColumnNumber());
//  }

  @SuppressWarnings({ "rawtypes", "deprecation" })  // As this code not maintained
  private ContentHandler handlerForAccountName(String accountName) throws SAXException {
    ContentHandler handler = (ContentHandler) accountHandlerMap.get(accountName);
    if (handler == null) {
      Class handlerClass = accountHandlerClassMap.get(accountName);
      if (handlerClass != null) {
        try {
          handler = (ContentHandler) handlerClass.newInstance();
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
        handler.setDocumentLocator(locator);
        accountHandlerMap.put(accountName, (DefaultHandler) handler);
      } else {
        throw new SAXParseException("Onbekende rekening " + accountName, locator);
      }
    }
    
    // Always set the accounts, so the handler can be reused for different accounts.
    PgAccount account = bank.getAccount(accountName, true);
    ((AccountContentHandler) handler).setAccount(sumAccount, bank, account);
  
    return handler;
  }

  enum State {
    START,      // Initial state
    ACCOUNT,    // Get the account, to determine the accountHandler.
    GET_TRANSACTION_TAG,  // After the account follows the transaction. we remember
                          // this tag, to check that it is finished.
    TRANSACTION;
  }
}
