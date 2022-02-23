package goedegep.finan.stocks;

import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CompanyFundsContentHandler extends AbstractValidatingHandler<CompanyFundsContentHandler.State> {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static String  currentCompanyFundsURI = null;
  private static String  schema = null;
  
  private CompanyPool      companyPool = null;
  
  // Company info
  private Company          company      = null;
  private LinkedList<Fund> companyFunds = null;
  
  public CompanyFundsContentHandler(CompanyPool companyPool) {
    this.companyPool = companyPool;
    state = State.START;
//    setPrintStateChanges(true);
  }

  public void read(String companyFundsURI) throws ParseException {
    try {
      getParser(null).parse(companyFundsURI, this);
      currentCompanyFundsURI = companyFundsURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(companyFundsURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public void write() {
    StringBuilder output = new StringBuilder();
    Indent        indent = new Indent(2);
    String        newline = System.getProperty("line.separator");

    if (currentCompanyFundsURI == null) {
      throw new RuntimeException("Geen URI voor de fondsen per bedrijf file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de fondsen per bedrijf file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentCompanyFundsURI);
      file.renameTo(new File(currentCompanyFundsURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentCompanyFundsURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.companyFundsTag, schema));
      indent.increment();

      // Build the String with all companyFunds
      for (Company company: companyPool.getCompanies()) {
       output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.companyFundListTag)).append(newline);
       indent.increment();
       output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), CompanyBasics.companyNameTag, company.getName())).append(newline);

       for (Fund fund: company.getFunds()) {
         output.append(toXmlString(indent, getXmlNameSpaceName(), fund)).append(newline);
       }
       indent.decrement();
       output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(),CompanyBasics.companyFundListTag)).append(newline);
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.companyFundsTag));

      // write to output
      out.write(output.toString());

      out.close();
//      System.out.println("CompanyFunds written to file " + currentCompanyFundsURI);
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de fondsen per bedrijf file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String toXmlString(Indent indent, String nameSpace, Fund fund) {
    return SgmlUtil.createElement(indent, nameSpace, CompanyBasics.fundTag, fund.getName());
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;
    
//  System.out.println("CompanyFundsContentHandler: startElement " + tag);

    switch (state) {
    case START:
      if (tag.compareTo(CompanyBasics.companyFundsTag) == 0) {
        pushState(State.COMPANY_FUNDS);
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
      }
      break;

    case COMPANY_FUNDS:
      if (tag.compareTo(CompanyBasics.companyFundListTag) == 0) {
        pushState(State.COMPANY_FUND_LIST);
        company = null;
        companyFunds = new LinkedList<Fund>();
      }
      break;
      
    case COMPANY_FUND_LIST:
      if (tag.compareTo(CompanyBasics.companyNameTag) != 0  &&
          tag.compareTo(CompanyBasics.fundTag) != 0) {
        throw new RuntimeException("Illegal tag: " + tag + ", in state: " + state);
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

//  System.out.println("CompanyFundsContentHandler: endElement " + tag);
    switch (state) {
    case COMPANY_FUNDS:
      if (tag.compareTo(CompanyBasics.companyFundsTag) == 0) {
        popState();
      }
      break;

    case COMPANY_FUND_LIST:
      if (tag.compareTo(CompanyBasics.companyFundListTag) == 0) {
        company.setFunds(companyFunds);
        popState();
      } else if (tag.compareTo(CompanyBasics.companyNameTag) == 0) {
        company = companyPool.getCompany(data);
        if (company == null) {
          throw new SAXParseException("Onbekend bedrijf " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.fundTag) == 0) {
        if ((data == null)  || (data.length() == 0)) {
          throw new SAXParseException("Geen fondsnaam " + data, locator);          
        }
        Fund fund = new Fund(data, company);
        companyFunds.add(fund);
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  protected enum State {
    START,
    COMPANY_FUNDS,
    COMPANY_FUND_LIST;
  }
}