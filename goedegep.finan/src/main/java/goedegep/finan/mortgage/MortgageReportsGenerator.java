package goedegep.finan.mortgage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.ListItem;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.TabSettings;
//import com.itextpdf.text.TabStop;
//import com.itextpdf.text.pdf.PdfWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.rolodex.model.Address;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class MortgageReportsGenerator {
  private final static Logger LOGGER = Logger.getLogger(MortgageReportsGenerator.class.getName());
  
  private static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public static void generateYearlyPdfReport(MortgageCalculator mortgageCalculator, int year, File file) {
    LOGGER.info("=> file=" + file.getAbsolutePath() + ", year=" + year);
    
    Mortgage mortgage = mortgageCalculator.getMortgage();
    MortgageYearlyOverview mortgageYearlyOverview = mortgageCalculator.getYearlyOverview(year);
    if (mortgageYearlyOverview == null) {
      return ;
    }

    PDDocument document = new PDDocument();
    PDPage page = new PDPage(PDRectangle.A4);
    PDDocumentInformation pdd = document.getDocumentInformation();
    pdd.setAuthor("Peter Goedegebure");
    pdd.setCreationDate(new GregorianCalendar());
    pdd.setSubject("Jaaroverzicht");
    pdd.setTitle("Jaaroverzicht " + year);

    document.addPage(page);
    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
      FontName fontName = Standard14Fonts.FontName.TIMES_ROMAN;
      contentStream.beginText();
      PDFont font = new PDType1Font(fontName);
      contentStream.setFont(font, 12);
      contentStream.newLineAtOffset(10, 400);
      contentStream.setLeading(14.5f);
      
      // Lender information top right
      // Name
      contentStream.showText(mortgage.getLender());
      
      // Address
      Address address = mortgage.getLenderAddress();
      if (address != null  &&  !address.eIsProxy()) {
        LOGGER.severe("Address: " + address.getStreetName());
        contentStream.showText(address.getStreetName());
        contentStream.showText(" ");
        contentStream.showText(String.valueOf(address.getHouseNumber()));
        if (address.getHouseNumberExtension() != null) {
          contentStream.showText(address.getHouseNumberExtension());
        }
        contentStream.newLine();
        contentStream.showText(address.getPostalCode());
        contentStream.showText("  ");
        contentStream.showText(address.getCity().getCityName());
        contentStream.newLine();
      }
      
      // Borrower information
      contentStream.showText(mortgage.getBorrowerTitleAndName());
      contentStream.newLine();
      address = mortgage.getBorrowerAddress();
      if (address != null  &&  !address.eIsProxy()) {
        contentStream.showText(address.getStreetName());
        contentStream.showText(" ");
        contentStream.showText(String.valueOf(address.getHouseNumber()));
        if (address.getHouseNumberExtension() != null) {
          contentStream.showText(address.getHouseNumberExtension());
        }
        contentStream.newLine();
        contentStream.showText(address.getPostalCode());
        contentStream.showText("  ");
        contentStream.showText(address.getCity().getCityName());
        contentStream.newLine();
      }
      
    // Overview date
      contentStream.showText("Datum");
      contentStream.newLine();
      contentStream.showText("januari " + (year + 1));
      contentStream.newLine();

      // Subject line
      contentStream.showText("Onderwerp");
      contentStream.newLine();
      contentStream.showText("Hypotheek jaaroverzicht " + year);
      contentStream.newLine();

      // Tekst
      contentStream.showText("Hierbij ontvangt je het jaaroverzicht van de hypotheek die je bij ons hebt.");
      contentStream.newLine();
      contentStream.showText("De gegevens in dit jaaroverzicht kunt je gebruiken als je over " + year + " belastingaangifte doet.");
      contentStream.newLine();
      contentStream.showText("We hebben deze gegevens, voor zover we dat wettelijk verplicht zijn, ook aan de Belastingdienst doorgegeven.");
      contentStream.newLine();
      contentStream.newLine();

      // Hypotheek informatie
      PgCurrencyFormat cf = new PgCurrencyFormat(0, false, false, false, true);

      contentStream.showText("Hypotheek ");
      String borrowerTitleAndName = mortgage.getBorrowerTitleAndName();
      if (borrowerTitleAndName != null) {
        contentStream.showText(borrowerTitleAndName.substring(0, 1).toLowerCase() + borrowerTitleAndName.substring(1));
      }
      contentStream.newLine();
      
//      List<TabStop> tabStops = new ArrayList<>();
//      TabStop tabStop = new TabStop(280f, TabStop.Alignment.RIGHT);
//      tabStops.add(tabStop);
//      TabSettings tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);

      contentStream.showText("Schuld op 01-01-" + year);
//      paragraph.add(Chunk.TABBING);
      PgCurrency debtAtJanuari1 = mortgageYearlyOverview.getDebtAtBeginningOfYear();
      if (debtAtJanuari1 == null) {
        debtAtJanuari1 = new PgCurrency(0);
      }
      contentStream.showText(cf.format(debtAtJanuari1) );
      contentStream.newLine();

      contentStream.showText("Schuld op 31-12-" + year);
//      paragraph.add(Chunk.TABBING);
      PgCurrency debtAtDecember31 = mortgageYearlyOverview.getDebtAtEndOfYear();
      if (debtAtDecember31 != null) {
        contentStream.showText(cf.format(debtAtDecember31));
      } else {
        contentStream.showText("??,??");
      }
      contentStream.newLine();

      contentStream.showText("Rente betaald in " + year);
//      paragraph.add(Chunk.TABBING);
      PgCurrency interestPaidInYear = mortgageYearlyOverview.getInterestPaid();
      if (interestPaidInYear != null) {
        contentStream.showText(cf.format(interestPaidInYear));
      } else {
        contentStream.showText("??,??");
      }
      contentStream.newLine();

      contentStream.showText("Aflossing betaald in " + year);
//      paragraph.add(Chunk.TABBING);
      PgCurrency repaymentInYear = mortgageYearlyOverview.getRepayment();
      if (repaymentInYear != null) {
        contentStream.showText(cf.format(repaymentInYear));
      } else {
        contentStream.showText("??,??");
      }
      contentStream.newLine();

//      tabStops = new ArrayList<>();
//      tabStop = new TabStop(280f, TabStop.Alignment.LEFT);
//      tabStops.add(tabStop);
//      tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);

      contentStream.showText(mortgage.getLenderSigner1());
      String lenderSigner2 = mortgage.getLenderSigner2();
      if (lenderSigner2 != null) {
//        paragraph.add(Chunk.TABBING);
        contentStream.showText(lenderSigner2);
      }
      
      contentStream.endText();
      contentStream.close();

      document.save(file.getAbsolutePath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
////    Document document = new Document(PageSize.A4);

//    try {
//      PdfWriter.getInstance(document, new FileOutputStream(file));
//      document.setMargins(80, 40, 108, 180);
//
//      document.open();
//
//      // Lender information top right
//      Paragraph paragraph = new Paragraph();
//      paragraph.setIndentationLeft(280);
//      paragraph.add(mortgage.getLender());
//      paragraph.add("\n");
//      Address address = mortgage.getLenderAddress();
//      if (address != null) {
//        paragraph.add(address.getStreetName());
//        paragraph.add(" ");
//        paragraph.add(String.valueOf(address.getHouseNumber()));
//        if (address.getHouseNumberExtension() != null) {
//          paragraph.add(address.getHouseNumberExtension());
//        }
//        paragraph.add("\n");
//        paragraph.add(address.getPostalCode());
//        paragraph.add("  ");
//        paragraph.add(address.getCity().getCityName());
//        paragraph.add("\n");
//      }
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Borrower information
//      paragraph = new Paragraph();
//      paragraph.add(mortgage.getBorrowerTitleAndName());
//      paragraph.add("\n");
//      address = mortgage.getBorrowerAddress();
//      if (address != null) {
//        paragraph.add(address.getStreetName());
//        paragraph.add(" ");
//        paragraph.add(String.valueOf(address.getHouseNumber()));
//        if (address.getHouseNumberExtension() != null) {
//          paragraph.add(address.getHouseNumberExtension());
//        }
//        paragraph.add("\n");
//        paragraph.add(address.getPostalCode());
//        paragraph.add("  ");
//        paragraph.add(address.getCity().getCityName());
//        paragraph.add("\n");
//      }
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Overview date
//      paragraph = new Paragraph();
//      paragraph.add("Datum\njanuari " + (year + 1) + "\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Subject line
//      paragraph = new Paragraph();
//      paragraph.add("Onderwerp\nHypotheek jaaroverzicht " + year + "\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Tekst
//      paragraph = new Paragraph();
//      paragraph.add("Hierbij ontvangt je het jaaroverzicht van de hypotheek die je bij ons hebt.\n");
//      paragraph.add("De gegevens in dit jaaroverzicht kunt je gebruiken als je over " + year + " belastingaangifte doet.\n");
//      paragraph.add("We hebben deze gegevens, voor zover we dat wettelijk verplicht zijn, ook aan de Belastingdienst doorgegeven.\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Hypotheek informatie
//      PgCurrencyFormat cf = new PgCurrencyFormat(0, false, false, false, true);
//      paragraph = new Paragraph();
//
//      paragraph.add("Hypotheek ");
//      String borrowerTitleAndName = mortgage.getBorrowerTitleAndName();
//      if (borrowerTitleAndName != null) {
//        paragraph.add(borrowerTitleAndName.substring(0, 1).toLowerCase() + borrowerTitleAndName.substring(1));
//      }
//      paragraph.add("\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      paragraph = new Paragraph();
//      List<TabStop> tabStops = new ArrayList<>();
//      TabStop tabStop = new TabStop(280f, TabStop.Alignment.RIGHT);
//      tabStops.add(tabStop);
//      TabSettings tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);
//
//      paragraph.add(new Chunk("Schuld op 01-01-" + year));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency debtAtJanuari1 = mortgageYearlyOverview.getDebtAtBeginningOfYear();
//      if (debtAtJanuari1 == null) {
//        debtAtJanuari1 = new PgCurrency(0);
//      }
//      paragraph.add(new Chunk(cf.format(debtAtJanuari1) + "\n"));
//
//      paragraph.add(new Chunk("Schuld op 31-12-" + year));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency debtAtDecember31 = mortgageYearlyOverview.getDebtAtEndOfYear();
//      if (debtAtDecember31 != null) {
//        paragraph.add(new Chunk(cf.format(debtAtDecember31) + "\n"));
//      } else {
//        paragraph.add(new Chunk("??,??" + "\n"));
//      }
//
//      paragraph.add(new Chunk("Rente betaald in " + year));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency interestPaidInYear = mortgageYearlyOverview.getInterestPaid();
//      if (interestPaidInYear != null) {
//        paragraph.add(new Chunk(cf.format(interestPaidInYear) + "\n"));
//      } else {
//        paragraph.add(new Chunk("??,??" + "\n"));
//      }
//
//      paragraph.add(new Chunk("Aflossing betaald in " + year));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency repaymentInYear = mortgageYearlyOverview.getRepayment();
//      if (repaymentInYear != null) {
//        paragraph.add(new Chunk(cf.format(repaymentInYear) + "\n"));
//      } else {
//        paragraph.add(new Chunk("??,??" + "\n"));
//      }
//
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//      document.add(new Paragraph("\n"));
//
//      paragraph = new Paragraph();
//      tabStops = new ArrayList<>();
//      tabStop = new TabStop(280f, TabStop.Alignment.LEFT);
//      tabStops.add(tabStop);
//      tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);
//
//      paragraph.add(new Chunk(mortgage.getLenderSigner1()));
//      String lenderSigner2 = mortgage.getLenderSigner2();
//      if (lenderSigner2 != null) {
//        paragraph.add(Chunk.TABBING);
//        paragraph.add(new Chunk(lenderSigner2));
//      }
//
//      document.add(paragraph);      
//
//      document.close();  
//
//    } catch (FileNotFoundException | DocumentException e) {
//      e.printStackTrace();
//    }
  }

  public static void generateRedemptionInvoice(MortgageCalculator mortgageCalculator, File file) {
    LOGGER.severe("=> file=" + file.getAbsolutePath());
    
    Mortgage mortgage = mortgageCalculator.getMortgage();

    // The FinalPayment shall be the last event.
    List<MortgageEvent> events = mortgage.getMortgageEvents();
    
    if (events.isEmpty()) {
      throw new RuntimeException("There is no FinalPayment event");
    }
    
    MortgageEvent event = events.get(events.size() - 1);
    if (!(event instanceof FinalPayment)) {
      throw new RuntimeException("The last event is not a FinalPayment: " + event.getClass().getName());
    }
    
    FinalPayment finalPayment = (FinalPayment) event;

//    Document document = new Document(PageSize.A4);
//    
//    try {
//      PdfWriter.getInstance(document, new FileOutputStream(file));
//      document.setMargins(60, 60, 90, 90);
//
//      document.open();
//
//      // Lender information top right
//      Paragraph paragraph = new Paragraph();
//      paragraph.setIndentationLeft(280);
//      paragraph.add(mortgage.getLender());
//      paragraph.add("\n");
//      Address address = mortgage.getLenderAddress();
//      if (address != null) {
//        paragraph.add(address.getStreetName());
//        paragraph.add(" ");
//        paragraph.add(String.valueOf(address.getHouseNumber()));
//        if (address.getHouseNumberExtension() != null) {
//          paragraph.add(address.getHouseNumberExtension());
//        }
//        paragraph.add("\n");
//        paragraph.add(address.getPostalCode());
//        paragraph.add("  ");
//        paragraph.add(address.getCity().getCityName());
//        paragraph.add("\n");
//      }
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//      
//
//      // Borrower information
//      paragraph = new Paragraph();
//      paragraph.add(mortgage.getBorrowerTitleAndName());
//      paragraph.add("\n");
//      address = mortgage.getBorrowerAddress();
//      if (address != null) {
//        paragraph.add(address.getStreetName());
//        paragraph.add(" ");
//        paragraph.add(String.valueOf(address.getHouseNumber()));
//        if (address.getHouseNumberExtension() != null) {
//          paragraph.add(address.getHouseNumberExtension());
//        }
//        paragraph.add("\n");
//        paragraph.add(address.getPostalCode());
//        paragraph.add("  ");
//        paragraph.add(address.getCity().getCityName());
//        paragraph.add("\n");
//      }
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//      
//
//      // Report date
//      paragraph = new Paragraph();
//      paragraph.add("Date: ");
//      paragraph.add(DF.format(new Date())  + "\n");
//      // Subject
//      paragraph.add("Onderwerp: Hypotheek aflosnota\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Text
//      paragraph = new Paragraph();
//      paragraph.add("Hierbij ontvangt je de aflosnota voor de hypotheek die je bij ons hebt.\n");
//      document.add(paragraph);
//
//      document.add(new Paragraph("\n"));
//
//      // Mortgage information
//      PgCurrencyFormat cf = new PgCurrencyFormat(0, false, false, false, false);
//      paragraph = new Paragraph();
//
//      paragraph.add("Hypotheek: ");
//      String borrowerTitleAndName = mortgage.getBorrowerTitleAndName();
//      if (borrowerTitleAndName != null) {
//        paragraph.add(borrowerTitleAndName.substring(0, 1).toLowerCase() + borrowerTitleAndName.substring(1));
//      }
//      paragraph.add("\n");
//      List<TabStop> tabStops = new ArrayList<>();
//      TabStop tabStop = new TabStop(280f, TabStop.Alignment.RIGHT);
//      tabStops.add(tabStop);
//      TabSettings tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);
//      
//      // Debt on jan. 1st of the year of redemption
//      // Interest paid this year
//      // Repayment this year
//      // Debt at redemption day
//      // To be paid, is debt at redemption day
//      Date redemptionDate = finalPayment.getDate();
//      int year = DateUtil.getDateYear(redemptionDate);
//      MortgageYearlyOverview mortgageYearlyOverview = mortgageCalculator.getYearlyOverview(year);
//
//      paragraph.add(new Chunk("Schuld op 01-01-" + year));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency debtOnJanFirst = mortgageYearlyOverview.getDebtAtBeginningOfYear();
//      if (debtOnJanFirst == null) {
//        debtOnJanFirst = new PgCurrency(0);
//      }
//      paragraph.add(new Chunk(cf.format(debtOnJanFirst) + "\n"));
//
//      paragraph.add(new Chunk("Rente betaald tot " + DF.format(redemptionDate)));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency interestPaidThisYear = mortgageYearlyOverview.getInterestPaid();
//      if (interestPaidThisYear == null) {
//        interestPaidThisYear = new PgCurrency(0);
//      }
//      paragraph.add(new Chunk(cf.format(interestPaidThisYear) + "\n"));
//
//      paragraph.add(new Chunk("Aflossing betaald tot " + DF.format(redemptionDate)));
//      paragraph.add(Chunk.TABBING);
//      PgCurrency repaymentThisYear = mortgageYearlyOverview.getRepayment();
//      if (repaymentThisYear == null) {
//        repaymentThisYear = new PgCurrency(0);
//      }
//      paragraph.add(new Chunk(cf.format(repaymentThisYear) + "\n"));
//      
//      paragraph.add(new Chunk("Schuld op " + DF.format(redemptionDate)));
//      paragraph.add(Chunk.TABBING);
//
//      PgCurrency debtOnRedemptionDate = mortgageCalculator.getDebtAtDate(redemptionDate, true);
//      if (debtOnRedemptionDate == null) {
//        debtOnRedemptionDate = new PgCurrency(0);
//      }
//      paragraph.add(new Chunk(cf.format(debtOnRedemptionDate) + "\n\n"));
//
//      paragraph.add(new Chunk("Per " + DF.format(redemptionDate) + " moet je terugbetalen"));
//      paragraph.add(Chunk.TABBING);
//      paragraph.add(new Chunk(cf.format(debtOnRedemptionDate) + "\n"));
//      
//      paragraph.add(new Chunk("Let op:\n"));
//
//
//      document.add(paragraph);
//      
//      com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
//      ListItem item = new ListItem("Bij het berekenen van het terugbetaalbedrag zijn wij er vanuit gegaan dat je op " +
//          DF.format(redemptionDate) + " je lening volledig terugbetaalt.");
//      list.add(item);
//      item = new ListItem("Betaal je de lening later terug? " + 
//                          "Dan moet je ook rente betalen over de periode die ligt tussen de dag dat je zou terugbetalen en " +
//                          "de dag dat je de lening hebt terugbetaald.");
//      list.add(item);
//      PgCurrency interestPerDay = debtOnRedemptionDate.multiply(finalPayment.getNewInterestRate()).divide(36500);
//      item = new ListItem("De rente die je per dag extra moet betalen als je na " +
//                          DF.format(redemptionDate) +
//                          " terugbetaalt is " +
//                          cf.format(interestPerDay) +
//                          ".");
//      list.add(item);
//      FixedPointValue dailyInterestRate = finalPayment.getNewInterestRate().changeFactor(10000).divide(365);
//      item = new ListItem("Bij de berekening van de rente die je extra per dag moet betalen, hanteren wij een percentage van " +
//                          FPVF.format(dailyInterestRate) +
//                          " % (jaarrente " +
//                          FPVF.format(finalPayment.getNewInterestRate()) +
//                          " % gedeeld door 365 dagen).");
//      list.add(item);
//      item = new ListItem("Bij het berekenen van het terugbetaalbedrag zijn wij er vanuit gegaan dat op " +
//          DF.format(redemptionDate) + " alle tot die datum te betalen termijnen voldaan zijn.");
//      list.add(item);
//      item = new ListItem("Alle te betalen bedragen dienen te worden overgemaakt naar rekening nummer " +
//                          mortgage.getLenderBankAccountNumber() + ", t.n.v. " +
//                          mortgage.getLenderBankAccountNumberNameAndAddress() + ".");
//      list.add(item);
//      
//      document.add(list);
//
//      document.add(new Paragraph("\n"));
//
//      paragraph = new Paragraph();
//      tabStops = new ArrayList<>();
//      tabStop = new TabStop(280f, TabStop.Alignment.LEFT);
//      tabStops.add(tabStop);
//      tabSettings = new TabSettings(tabStops);
//      paragraph.setTabSettings(tabSettings);
//
//      paragraph.add(new Chunk(mortgage.getLenderSigner1()));
//      String lenderSigner2 = mortgage.getLenderSigner2();
//      if (lenderSigner2 != null) {
//        paragraph.add(Chunk.TABBING);
//        paragraph.add(new Chunk(lenderSigner2));
//      }
//
//      document.add(paragraph);      
//
//      document.close();  
//
//    } catch (FileNotFoundException | DocumentException e) {
//      e.printStackTrace();
//    }
    
    LOGGER.severe("<=");
    
  }

}
