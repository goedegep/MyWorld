package goedegep.finan.stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.xml.XmlUtil;

public class CompaniesContentHandler extends AbstractValidatingHandler<CompaniesContentHandler.State> {
  private static final Logger LOGGER = Logger.getLogger(CompaniesContentHandler.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static String                     currentCompaniesURI = null;
  private static String                     schema = null;

  CompanyPool companyPool;
  
  public CompaniesContentHandler(CompanyPool companyPool) {
    this.companyPool = companyPool;
    state = State.START;
  }

  public void read(String companiesURI) throws ParseException {
    try {
      getParser(null).parse(companiesURI, this);
      currentCompaniesURI =  companiesURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(companiesURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public void write() {
    StringBuffer output = new StringBuffer();
    String       newline = System.getProperty("line.separator");

    if (currentCompaniesURI == null) {
      throw new RuntimeException("Geen URI voor de bedrijven file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de bedrijven file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentCompaniesURI);
      file.renameTo(new File(currentCompaniesURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentCompaniesURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.companiesTag, schema));

      // Build the String with all companies
      for (Company company: companyPool.getCompanies()) {
       output.append("  ");
       output.append(toXmlString(getXmlNameSpaceName(), company));
       output.append(newline);
      }

      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.companiesTag));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de bedrijven file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String toXmlString(String nameSpace, Company company) {
    return SgmlUtil.createElement(nameSpace, CompanyBasics.CompanyTag, company.getName());
  }
  
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    LOGGER.info("=> " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;

    switch (state) {
    case START:
      if (tag.compareTo(CompanyBasics.companiesTag) == 0) {
        pushState(State.COMPANIES);
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
      }
      break;
      
    case COMPANIES:
      if (tag.compareTo(CompanyBasics.CompanyTag) != 0) {
        throw new RuntimeException("Illegal tag: " + tag + ", in state: " + state);
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
    LOGGER.info("=> localpart = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

    switch (state) {
    case COMPANIES:
      if (tag.compareTo(CompanyBasics.companiesTag) == 0) {
        popState();
      } else if (tag.compareTo(CompanyBasics.CompanyTag) == 0) {
        companyPool.addCompany(new Company(data));
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }

  protected enum State {
    START,
    COMPANIES;
  }
}