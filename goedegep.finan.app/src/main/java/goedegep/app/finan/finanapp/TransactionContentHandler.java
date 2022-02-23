package goedegep.app.finan.finanapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import goedegep.app.finan.gen.ContentHandlerExtension;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

class TransactionContentHandler extends AbstractValidatingHandler<TransactionContentHandler.State>  {
  /*
   * State transitions:
   *   start in START
   *   when _transactionTag is seen, change to TRANSACTION
   *     when _bankTag is seen, change to BANK
   *       this ContentHandler reads the bank and the rest of the transaction
   *       is handled by the ContentHandler of the related bank.
   */
  private final static String   TRANSACTIONS_TAG = "Transactions";
  private final static String   TRANSACTION_TAG = "Transaction";
  private final static String   BANK_TAG = "Bank";

  private static final String   NEWLINE = System.getProperty("line.separator");

  private String         currentTransactionsFileURI = null;
  private SumAccount     sumAccount = null; // The SumAccount to which transactions are to be added.

  private ContentHandler bankHandler = null;  // Handler for the bank for which a transaction is being handled.

  // Map bankname, as found in a transaction, to the handler for that bank.
  static HashMap<String, DefaultHandler> bankHandlerMap =
    new HashMap<String, DefaultHandler>();

  TransactionContentHandler() {
    state = State.START;
  }

  public void setAccounts(SumAccount sumAccount) {
    this.sumAccount = sumAccount;
  }
  
  @SuppressWarnings("rawtypes")
  public void addBankHandler(String bankName, Class handlerClass) {
    try {
      @SuppressWarnings("deprecation")  // As this code not maintained
      DefaultHandler handler = (DefaultHandler) handlerClass.newInstance();
      bankHandlerMap.put(bankName, handler);
    } catch (InstantiationException e) {
      // Should never happen, so just print stack trace if it does.
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // Should never happen, so just print stack trace if it does.
      e.printStackTrace();
    }
  }
  
  public void read(SAXParser parser, String transactionsFileURI) throws Exception {
    try {
      getParser(parser).parse(transactionsFileURI, this);
      currentTransactionsFileURI = transactionsFileURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      StringBuilder buf = new StringBuilder();
      buf.append("Transactie file ");
      buf.append(transactionsFileURI);
      buf.append(" kan niet gelezen worden.");
      buf.append(System.getProperty("line.separator"));
      buf.append("Fout: ").append(e.getMessage());
      buf.append(System.getProperty("line.separator"));
      buf.append("Locatie: regel ").append(locator.getLineNumber());
      buf.append(", kolom ").append(locator.getColumnNumber());
      buf.append(System.getProperty("line.separator"));
      throw new Exception(buf.toString());
    }
  }

  public void write() throws Exception {
    // TODO rewrite using a buffered writer.
    StringBuilder     output = new StringBuilder();
    Indent            indent = new Indent(2);
    PgTransaction     currentTransaction;
    String            nameSpace = null;
    
    if (currentTransactionsFileURI == null) {
      throw new RuntimeException("Geen URI voor de transactie file.");
    }

    try
    {
      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentTransactionsFileURI);
      output.append(XmlUtil.createStartLine());
      
      // Transactions open tag
      output.append(XmlUtil.createRootElementOpen(nameSpace, TRANSACTIONS_TAG, null));
      indent.increment();

      // Build the String with all transactions
      for (FinanTransaction finanTransaction: sumAccount.getTransactions()) {
        currentTransaction = finanTransaction.getTransaction();
        Bank bank = finanTransaction.getBank();
        output.append(SgmlUtil.createElementOpen(indent, nameSpace, TRANSACTION_TAG)).append(NEWLINE);
        indent.increment();
        output.append(SgmlUtil.createElement(indent, nameSpace, BANK_TAG, bank.getName()));
        
//        if (bank.getName().equals("Direktbank")) {
//          System.out.println("WRITING VIA CONTENT HANDLER");
          bankHandler = handlerForBankName(bank.getName());
          output.append(" ").append(((ContentHandlerExtension) bankHandler).transactionToXmlString(currentTransaction, nameSpace)).append(NEWLINE);
//        } else {
//          output.append(" ").append(bank.toXmlString(currentTransaction, nameSpace)).append(NEWLINE);
//        }
        indent.decrement();
        output.append(SgmlUtil.createElementClose(indent, nameSpace, TRANSACTION_TAG)).append(NEWLINE);
      }
      indent.decrement();
      output.append(XmlUtil.createRootElementClose(nameSpace, TRANSACTIONS_TAG));

      // write to output
      out.write(output.toString());

      out.close();
    }
    catch (IOException e) {
      throw new Exception("Opslaan mislukt naar " + currentTransactionsFileURI +
          ". Systeem melding: " + e.getMessage() + ", " + e.getCause());
    }
  }


  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("Finan TransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(TRANSACTIONS_TAG) == 0) {
        pushState(State.TRANSACTIONS);
      } else {
        throw new SAXParseException("Ongeldig root element " + localpart, locator);
      }
      break;

    case TRANSACTIONS:
      if (localpart.compareTo(TRANSACTION_TAG) == 0) {
        pushState(State.TRANSACTION);
      } else {
        throw new SAXParseException("Ongeldig element " + localpart + "(i.p.v. element " + TRANSACTION_TAG + ")", locator);
      }
      break;

    case TRANSACTION:
      // in this state all information is passed on to the handler of the bank.
      // here we only have to check the start of the 'bank' tag, which should be the first element,
      // as this determines the handler.
      if (localpart.compareTo(BANK_TAG) == 0) {
        pushState(State.TRANSACTION_BANK);
      } else {
        bankHandler.startElement(uri, localpart, rawname, attributes);
      }
      break;

    case TRANSACTION_BANK:
      throw new SAXParseException("Ongeldig element " + localpart +
          "(er kan geen ander element binnen het element " + BANK_TAG +
          " voorkomen)", locator);
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("Finan TransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;

    case TRANSACTIONS:
      if (localpart.compareTo(TRANSACTIONS_TAG) == 0) {
        popState();
      }
      break;

    case TRANSACTION:
      // in this state all information is passed on to the handler of the bank.
      // here we only have to check the end of the 'transaction' tag.
      if (localpart.compareTo(TRANSACTION_TAG) == 0) {
        bankHandler = null;
        popState();
      } else {
        bankHandler.endElement(uri, localpart, rawname);
      }
      break;

    case TRANSACTION_BANK:
      if (localpart.compareTo(BANK_TAG) == 0) {
        popState();
        bankHandler = handlerForBankName(data);
        bankHandler.setDocumentLocator(locator);
        Bank account = sumAccount.getBankForBankName(data).getBank();
        ContentHandlerExtension che = (ContentHandlerExtension) bankHandler;
        che.setAccount(sumAccount, account);
      }
      break;
    }
  }

  public void characters(char[] ch, int offset, int length) throws SAXException {
    //System.out.println("Finan TransactionContentHandler characters = " + new String(ch, offset, length));
    if (bankHandler == null) {
      super.characters(ch, offset, length);
    } else {
      bankHandler.characters(ch, offset, length);
    }
  }

  private ContentHandler handlerForBankName(String bankName) throws SAXException {
    ContentHandler handler = (ContentHandler) bankHandlerMap.get(bankName);
    if (handler == null) {
      throw new SAXParseException("Onbekende bank " + bankName, locator);
    }
    return handler;
  }

  enum State {
    START,
    TRANSACTIONS,
    TRANSACTION,
    TRANSACTION_BANK;
  }
}
