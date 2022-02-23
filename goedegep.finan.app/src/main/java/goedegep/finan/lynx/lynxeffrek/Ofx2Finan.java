package goedegep.finan.lynx.lynxeffrek;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry;
import goedegep.finan.stocks.Share;
import goedegep.util.datetime.DateUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.Indent;
import net.sf.ofx4j.domain.data.ApplicationSecurity;
import net.sf.ofx4j.domain.data.MessageSetType;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessage;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.AccountType;
import net.sf.ofx4j.domain.data.banking.BankAccountDetails;
import net.sf.ofx4j.domain.data.common.BalanceInfo;
import net.sf.ofx4j.domain.data.common.BalanceRecord;
import net.sf.ofx4j.domain.data.common.BalanceRecord.Type;
import net.sf.ofx4j.domain.data.common.CorrectionAction;
import net.sf.ofx4j.domain.data.common.Currency;
import net.sf.ofx4j.domain.data.common.Payee;
import net.sf.ofx4j.domain.data.common.Status;
import net.sf.ofx4j.domain.data.common.Status.Severity;
import net.sf.ofx4j.domain.data.common.StatusCode;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.common.TransactionList;
import net.sf.ofx4j.domain.data.common.TransactionType;
import net.sf.ofx4j.domain.data.creditcard.CreditCardAccountDetails;
import net.sf.ofx4j.domain.data.investment.accounts.InvestmentAccountDetails;
import net.sf.ofx4j.domain.data.investment.accounts.SubAccountType;
import net.sf.ofx4j.domain.data.investment.positions.BasePosition;
import net.sf.ofx4j.domain.data.investment.positions.Inv401KSource;
import net.sf.ofx4j.domain.data.investment.positions.InvestmentPosition;
import net.sf.ofx4j.domain.data.investment.positions.InvestmentPositionList;
import net.sf.ofx4j.domain.data.investment.positions.PositionType;
import net.sf.ofx4j.domain.data.investment.statements.BalanceList;
import net.sf.ofx4j.domain.data.investment.statements.InvestmentBalance;
import net.sf.ofx4j.domain.data.investment.statements.InvestmentStatementResponse;
import net.sf.ofx4j.domain.data.investment.statements.InvestmentStatementResponseMessageSet;
import net.sf.ofx4j.domain.data.investment.statements.InvestmentStatementResponseTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.BaseInvestmentTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.BuyOptionTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.BuyStockTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.BuyType;
import net.sf.ofx4j.domain.data.investment.transactions.IncomeTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.InvestmentBankTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.InvestmentTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.InvestmentTransactionList;
import net.sf.ofx4j.domain.data.investment.transactions.SellOptionTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.SellStockTransaction;
import net.sf.ofx4j.domain.data.investment.transactions.SellType;
import net.sf.ofx4j.domain.data.investment.transactions.TransferInvestmentTransaction;
import net.sf.ofx4j.domain.data.seclist.BaseSecurityInfo;
import net.sf.ofx4j.domain.data.seclist.SecurityId;
import net.sf.ofx4j.domain.data.seclist.SecurityInfo;
import net.sf.ofx4j.domain.data.seclist.SecurityList;
import net.sf.ofx4j.domain.data.seclist.SecurityListResponse;
import net.sf.ofx4j.domain.data.seclist.SecurityListResponseMessageSet;
import net.sf.ofx4j.domain.data.seclist.SecurityListResponseTransaction;
import net.sf.ofx4j.domain.data.signon.FinancialInstitution;
import net.sf.ofx4j.domain.data.signon.PasswordChangeResponse;
import net.sf.ofx4j.domain.data.signon.PasswordChangeResponseTransaction;
import net.sf.ofx4j.domain.data.signon.SignonResponse;
import net.sf.ofx4j.domain.data.signon.SignonResponseMessageSet;
import net.sf.ofx4j.io.AggregateUnmarshaller;
import net.sf.ofx4j.io.OFXParseException;

/**
 * This class reads .ofx files and can:
 * - print them in a more readable format then the .ofx xml format.
 * - convert them to Finan transactions.
 */
public class Ofx2Finan {
  private final static Logger           LOGGER = Logger.getLogger(Ofx2Finan.class.getName()); 
  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final PgCurrencyFormat CF = new PgCurrencyFormat();
  private static final int              INDENT_SIZE = 2;
  private static final String           NEWLINE = System.getProperty("line.separator");
  
  // settings to control what to run and what not
  private final static Level logLevel = Level.INFO;
  
  // Mapping of a lynx UniqueId to a Finan Share.
  private static Map<String, Share> shareIdToShareMap = new HashMap<String, Share>();
  
  /**
   * Constructor.
   * 
   * @param lynxToFinanShareIdListEntries Information to find a Finan Share for a Lynx UniqueId.
   *                                      This value may be null, but then no conversion to Finan transactions if possible.
   */
  public Ofx2Finan(List<LynxToFinanShareIdListEntry> lynxToFinanShareIdListEntries) {
    if (lynxToFinanShareIdListEntries != null) {
      for (LynxToFinanShareIdListEntry lynxToFinanShareIdListEntry: lynxToFinanShareIdListEntries) {
        if (lynxToFinanShareIdListEntry.getFinanName() != null  &&
            lynxToFinanShareIdListEntry.getUniqueId() != null) {
          addEntryToShareIdToShareMap(lynxToFinanShareIdListEntry.getFinanName(), lynxToFinanShareIdListEntry.getUniqueId());
        }
      }
    }
  }
  
  /**
   * Add an entry to the shareIdToShareMap.
   * @param shareName An existing Finan Share name. If it doesn't exist, an exception will be thrown. 
   * @param uniqueId A Lynx UniqueId of a share.
   */
  private static void addEntryToShareIdToShareMap(String shareName, String uniqueId) {
    Share share = Share.getShare(shareName);
    if (share == null) {
      throw new RuntimeException("No share found for name: " + shareName);
    }
    shareIdToShareMap.put(uniqueId, share);
  }

  /**
   * Print a complete ResponseEnvelope; the Top Level element of a .ofx file.<br/>
   * Empty elements are printed as '-'.
   * 
   * @param envelope The envelope to be printed.
   * @param skipUnsetFields If true, only fields with real contents are printed, else all fields are printed.
   * @param indent Line indentation.
   */
  private void printResponseEnvelope(ResponseEnvelope envelope, Indent indent, boolean skipUnsetFields, Set<String> skipElements) {
    printStringElement("UID", envelope.getUID(), indent, skipUnsetFields, null, skipElements);

    // ApplicationSecurity
    if (!skipElements.contains("ApplicationSecurity")) {
      ApplicationSecurity applicationSecurity = envelope.getSecurity();
      if ((applicationSecurity != null)  ||  (!skipUnsetFields)) {
        printElementLabel("ApplicationSecurity", indent);
        if (applicationSecurity != null) {
          System.out.println(applicationSecurity.name());
        } else {
          System.out.print("-");
        }
      }
    }
    
    // SignonResponse
    printSignonResponse(envelope.getSignonResponse(), indent, skipUnsetFields, null, skipElements);  // INFO: Niet nodig, is ook onderdeel van envelope.getMessageSets()

    // ResponseMessageSets
    printResponseMessageSets(envelope.getMessageSets(), indent, skipUnsetFields, null, skipElements);
  }

  /**
   * Print the the SignonResponse of an envelope.<br/>
   * Empty elements are printed as '-'.
   * 
   * <pre>
   * ResponseEnvelope:             See method {@link #printResponseEnvelope(ResponseEnvelope, Indent, boolean)}
   *   UID:                        A String, which always seems to be 'NONE'. Not relevant for me.
   *   Security:                   An enumeration type, which always seems to be 'NONE'. Not relevant for me.
   *   SignonResponse:             See method {@link #printSignonResponse(SignonResponse, Indent, boolean)}
   *                               Seems the response of a sign on. Not relevant for me.
   *     AccessKey:                A String, which isn't filled in.
   *     AccountLastUpdated:       A Date, which isn't filled in.
   *     FinancialInstitution:     See method {@link #printFinancialInstitution(FinancialInstitution, Indent, boolean)}
   *       Id:                     A String, which for Lynx seems to be '4705'.
   *       Organization:           A String, which for Lynx seems to be 'IBLLC-US'.
   *     Language:                 A String, which is 'ENG'.
   *     ProfileLastUpdated:       A Date, which isn't filled in.
   *     ResponseMessageName:      A String, which is set to 'signon'.
   *     SessionId:                A String, which isn't filled in.
   *     Status:                   See method {@link #printStatus(Status, Indent, boolean)}
   *     StatusHolderName:
   *     TimeStamp:
   *     UserKey:
   *     UserKeyExpiration:
   *   MessageSets:                printResponseMessageSets
   * </pre>
   * 
   * @param envelope The envelope to be printed.
   * @param skipUnsetFields If true, only fields with real contents are printed, else all fields are printed.
   * @param indent Line indentation.
   */
  private void printSignonResponse(SignonResponse signonResponse, Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SignonResponse");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SignonResponse", indent);
    
    indent.increment();
    
    printStringElement("AccessKey", signonResponse.getAccessKey(), indent, skipUnsetFields, elementId, skipElements);

    printDateItem("AccountLastUpdated", DateUtil.dateToLocalDate(signonResponse.getAccountLastUpdated()), indent, skipUnsetFields, elementId, skipElements);
    
    // FinancialInstitution
    printFinancialInstitution(signonResponse.getFinancialInstitution(), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("Language", signonResponse.getLanguage(), indent, skipUnsetFields, elementId, skipElements);
    
    printDateItem("ProfileLastUpdated", DateUtil.dateToLocalDate(signonResponse.getProfileLastUpdated()), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("ResponseMessageName", signonResponse.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("SessionId", signonResponse.getSessionId(), indent, skipUnsetFields, elementId, skipElements);
    
    // Status
    printStatus(signonResponse.getStatus(), indent, skipUnsetFields, elementId, skipElements);

    printStringElement("StatusHolderName", signonResponse.getStatusHolderName(), indent, skipUnsetFields, elementId, skipElements);
    
    // Bij maandoverzicht datum van eerste dag na de maand van het overzicht.
    printDateItem("TimeStamp", DateUtil.dateToLocalDate(signonResponse.getTimestamp()), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("UserKey", signonResponse.getUserKey(), indent, skipUnsetFields, elementId, skipElements);
    
    printDateItem("UserKeyExpiration", DateUtil.dateToLocalDate(signonResponse.getUserKeyExpiration()), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printFinancialInstitution(FinancialInstitution financialInstitution,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "FinancialInstitution");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("FinancialInstitution", indent);

    indent.increment();

    printStringElement("Id", financialInstitution.getId(), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("Organization", financialInstitution.getOrganization(), indent, skipUnsetFields, elementId, skipElements);

    indent.decrement();
  }

  private void printStatus(Status status,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "Status");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (status != null  ||  skipUnsetFields == false) {
      System.out.print(indent.toString());
      System.out.println("Status:");
      printClassLabel("Status", indent);
      if (status != null) {
        indent.increment();
        
        printStatusCode(status.getCode(), indent, skipUnsetFields, elementId, skipElements);
        
        printStringElement("Message", status.getMessage(), indent, skipUnsetFields, elementId, skipElements);
        
        printSeverity(status.getSeverity(), indent, skipUnsetFields, elementId, skipElements);
        
        indent.decrement();
      } else {
        System.out.println("-");
      }
    }
  }
  
  private void printResponseMessageSets(SortedSet<ResponseMessageSet> responseMessageSets,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "ResponseMessageSets");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("ResponseMessageSets", indent);
    
    indent.increment();
    
    for (ResponseMessageSet responseMessageSet: responseMessageSets) {
      if (responseMessageSet instanceof SignonResponseMessageSet) {
        printSignonResponseMessageSet((SignonResponseMessageSet) responseMessageSet, indent, skipUnsetFields, elementId, skipElements);
      } else if (responseMessageSet instanceof InvestmentStatementResponseMessageSet) {
        printInvestmentStatementResponseMessageSet((InvestmentStatementResponseMessageSet) responseMessageSet, indent, skipUnsetFields, elementId, skipElements);
      } else if (responseMessageSet instanceof SecurityListResponseMessageSet) {
        printSecurityListResponseMessageSet((SecurityListResponseMessageSet) responseMessageSet, indent, skipUnsetFields, elementId, skipElements);// HIER
      } else {
        throw new RuntimeException("Unsuppported responseMessageSet type: " + responseMessageSet.getType());
      }
    }
    
    indent.decrement();
  }

  private void printSeverity(Severity severity,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (severity != null  ||  !skipUnsetFields) {
      printElementLabel("Severity", indent);
      if (severity != null) {
        System.out.println(severity.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printStatusCode(StatusCode code,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "StatusCode");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (code != null  ||  skipUnsetFields == false) {
      printClassLabel("StatusCode", indent);
      if (code != null) {
        indent.increment();
        
        printElementLabel("Code", indent);
        System.out.println(code.getCode());
        
        printSeverity(code.getDefaultSeverity(), indent, skipUnsetFields, elementId, skipElements);
        
        printStringElement("Message", code.getMessage(), indent, skipUnsetFields, parent, skipElements);
        
        indent.decrement();
      }
    }
  }

  private void printSecurityListResponseMessageSet(
      SecurityListResponseMessageSet securityListResponseMessageSet,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityListResponseMessageSet");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SecurityListResponseMessageSet", indent);
    
    printResponseMessages(securityListResponseMessageSet.getResponseMessages(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityList(securityListResponseMessageSet.getSecurityList(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityListResponseTransaction(securityListResponseMessageSet.getSecurityListResponse(), indent, skipUnsetFields, elementId, skipElements);
    printMessageSetType(securityListResponseMessageSet.getType(), indent, skipUnsetFields, elementId, skipElements);

    // Version
    printStringElement("Version", securityListResponseMessageSet.getVersion(), indent, skipUnsetFields, parent, skipElements);
  }

  private void printSecurityListResponseTransaction(SecurityListResponseTransaction securityListResponseTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityListResponseTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (securityListResponseTransaction != null  ||  !skipUnsetFields) {
      printClassLabel("SecurityListResponseTransaction", indent);

      indent.increment();

      if (securityListResponseTransaction != null) {
        printStringElement("ClientCookie", securityListResponseTransaction.getClientCookie(), indent, skipUnsetFields, elementId, skipElements);
        printSecurityListResponse(securityListResponseTransaction.getMessage(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("ResponseMessageName", securityListResponseTransaction.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
        printStatus(securityListResponseTransaction.getStatus(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("StatusHolderName", securityListResponseTransaction.getStatusHolderName(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("UID", securityListResponseTransaction.getUID(), indent, skipUnsetFields, elementId, skipElements);
        System.out.print(indent.toString());
        System.out.println("[next is WrappedMessage]");
        printSecurityListResponse(securityListResponseTransaction.getWrappedMessage(), indent, skipUnsetFields, elementId, skipElements);
      }
      
      indent.decrement();
    }
  }

  private void printSecurityListResponse(SecurityListResponse securityListResponse,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityListResponse");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SecurityListResponse", indent);
    
    indent.increment();
    
    printStringElement("ResponseMessageName", securityListResponse.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printSecurityList(SecurityList securityList,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityList");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (securityList != null  ||  !skipUnsetFields) {
      printClassLabel("SecurityList", indent);

      indent.increment();

      if (securityList != null) {
        printSecurityInfos(securityList.getSecurityInfos(), indent, skipUnsetFields, elementId, skipElements);
      }

      indent.decrement();
    }
  }

  private void printSecurityInfos(List<BaseSecurityInfo> baseSecurityInfos,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityInfos");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SecurityInfos", indent);
    
    indent.increment();
    
    for (BaseSecurityInfo baseSecurityInfo: baseSecurityInfos) {
      printBaseSecurityInfo(baseSecurityInfo, indent, skipUnsetFields, elementId, skipElements);
    }
    
    indent.decrement();
    
  }

  private void printBaseSecurityInfo(BaseSecurityInfo baseSecurityInfo,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BaseSecurityInfo");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("BaseSecurityInfo", indent);
    
    indent.increment();
    
    printStringElement("CurrencyCode", baseSecurityInfo.getCurrencyCode(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("FiId", baseSecurityInfo.getFiId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Memo", baseSecurityInfo.getMemo(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Rating", baseSecurityInfo.getRating(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityId(baseSecurityInfo.getSecurityId(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityInfo(baseSecurityInfo.getSecurityInfo(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("SecurityName", baseSecurityInfo.getSecurityName(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("TickerSymbol", baseSecurityInfo.getTickerSymbol(), indent, skipUnsetFields, elementId, skipElements);
    printMoneyItem("UnitPrice", baseSecurityInfo.getUnitPrice(), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("UnitPriceAsOfDate", DateUtil.dateToLocalDate(baseSecurityInfo.getUnitPriceAsOfDate()), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printSecurityInfo(SecurityInfo securityInfo,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityInfo");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SecurityInfo", indent);
    
    indent.increment();
    
    printStringElement("CurrencyCode", securityInfo.getCurrencyCode(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("FiId", securityInfo.getFiId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Memo", securityInfo.getMemo(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Rating", securityInfo.getRating(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityId(securityInfo.getSecurityId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("SecurityName", securityInfo.getSecurityName(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("TickerSymbol", securityInfo.getTickerSymbol(), indent, skipUnsetFields, elementId, skipElements);
    printMoneyItem("UnitPrice", securityInfo.getUnitPrice(), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("UnitPriceAsOfDate", DateUtil.dateToLocalDate(securityInfo.getUnitPriceAsOfDate()), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printSecurityId(SecurityId securityId,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SecurityId");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SecurityId", indent);
    
    indent.increment();
    
    printStringElement("UniqueId", securityId.getUniqueId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("UniqueIdType", securityId.getUniqueIdType(), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printInvestmentStatementResponseMessageSet(
      InvestmentStatementResponseMessageSet investmentStatementResponseMessageSet,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentStatementResponseMessageSet");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("InvestmentStatementResponseMessageSet", indent);
    
    indent.increment();
    
    printMessageSetType(investmentStatementResponseMessageSet.getType(), indent, skipUnsetFields, elementId, skipElements);
    printResponseMessages(investmentStatementResponseMessageSet.getResponseMessages(), indent, skipUnsetFields, elementId, skipElements);  // niet boeiend.
    printInvestmentStatementResponseTransaction(investmentStatementResponseMessageSet.getStatementResponse(), indent, skipUnsetFields, elementId, skipElements);
    printInvestmentStatementResponseTransactions(investmentStatementResponseMessageSet.getStatementResponses(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Version", investmentStatementResponseMessageSet.getVersion(), indent, skipUnsetFields, elementId, skipElements);

    indent.decrement();
  }

  private void printInvestmentStatementResponseTransactions(List<InvestmentStatementResponseTransaction> investmentStatementResponseTransactions,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentStatementResponseTransactions");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("InvestmentStatementResponseTransactions", indent);
    
    indent.increment();

    for (InvestmentStatementResponseTransaction investmentStatementResponseTransaction: investmentStatementResponseTransactions) {
      printInvestmentStatementResponseTransaction(investmentStatementResponseTransaction, indent, skipUnsetFields, elementId, skipElements);
    }
    
    indent.decrement();
  }

  private void printInvestmentStatementResponseTransaction(
      InvestmentStatementResponseTransaction investmentStatementResponseTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentStatementResponseTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("InvestmentStatementResponseTransaction", indent);
    
    indent.increment();
    
    printStringElement("ClientCookie", investmentStatementResponseTransaction.getClientCookie(), indent, skipUnsetFields, elementId, skipElements);
    printInvestmentStatementResponse(investmentStatementResponseTransaction.getMessage(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("ResponseMessageName", investmentStatementResponseTransaction.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
    printStatus(investmentStatementResponseTransaction.getStatus(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("StatusHolderName", investmentStatementResponseTransaction.getStatusHolderName(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("UID", investmentStatementResponseTransaction.getUID(), indent, skipUnsetFields, elementId, skipElements);
    System.out.print(indent.toString());
    System.out.println("[next is WrappedMessage]");
    printInvestmentStatementResponse(investmentStatementResponseTransaction.getWrappedMessage(), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }

  private void printInvestmentStatementResponse(InvestmentStatementResponse investmentStatementResponse,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentStatementResponse");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("InvestmentStatementResponse", indent);
    
    indent.increment();
    
    printInvestmentAccountDetails(investmentStatementResponse.getAccount(), indent, skipUnsetFields, elementId, skipElements);
    printInvestmentBalance(investmentStatementResponse.getAccountBalance(), indent, skipUnsetFields, elementId, skipElements);
    printBalanceInfo("AvailableBalance", investmentStatementResponse.getAvailableBalance(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("CurrencyCode", investmentStatementResponse.getCurrencyCode(), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("DateOfStatement", DateUtil.dateToLocalDate(investmentStatementResponse.getDateOfStatement()), indent, skipUnsetFields, elementId, skipElements);
    printInvestmentTransactionList(investmentStatementResponse.getInvestmentTransactionList(), indent, skipUnsetFields, elementId, skipElements);
    printBalanceInfo("LedgerBalance", investmentStatementResponse.getLedgerBalance(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("MarketingInfo", investmentStatementResponse.getMarketingInfo(), indent, skipUnsetFields, elementId, skipElements);
    printInvestmentPositionList(investmentStatementResponse.getPositionList(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("ResponseMessageName", investmentStatementResponse.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
    printSecurityList(investmentStatementResponse.getSecurityList(), indent, skipUnsetFields, elementId, skipElements);
    printTransactionList(investmentStatementResponse.getTransactionList(), indent, skipUnsetFields, elementId, skipElements);
        
    indent.decrement();
  }

  private void printTransactionList(TransactionList transactionList,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "TransactionList");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (transactionList != null  ||  !skipUnsetFields) {
      printClassLabel("TransactionList", indent);

      if (transactionList != null) {
        indent.increment();

        printDateItem("End", DateUtil.dateToLocalDate(transactionList.getEnd()), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("Start", DateUtil.dateToLocalDate(transactionList.getStart()), indent, skipUnsetFields, elementId, skipElements);
        printTransactions(transactionList.getTransactions(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printTransactions(List<Transaction> transactions,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "Transactions");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("Transactions", indent);
    
    indent.increment();
    
    for (Transaction transaction: transactions) {
      printTransaction(transaction, indent, skipUnsetFields, elementId, skipElements);
    }
        
    indent.decrement();    
  }

  private void printTransaction(Transaction transaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "Transaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("Transaction", indent);
    
    indent.increment();
    
    printMoneyItem("Amount",  transaction.getAmount(), indent, skipUnsetFields, elementId, skipElements);
    printBankAccountDetails("BankAccountTo", transaction.getBankAccountTo(), indent, skipUnsetFields, elementId, skipElements);
    printBigDecimalItem("BigDecimalAmount", transaction.getBigDecimalAmount(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("CheckNumber", transaction.getCheckNumber(), indent, skipUnsetFields, elementId, skipElements);
    printCorrectionAction(transaction.getCorrectionAction(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("CorrectionId", transaction.getCorrectionId(), indent, skipUnsetFields, elementId, skipElements);
    printCreditCardAccountDetails("CreditCardAccountTo", transaction.getCreditCardAccountTo(), indent, skipUnsetFields, elementId, skipElements);
    printCurrency("Currency", transaction.getCurrency(), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("DateAvailable", DateUtil.dateToLocalDate(transaction.getDateAvailable()), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("DateInitiated", DateUtil.dateToLocalDate(transaction.getDateInitiated()), indent, skipUnsetFields, elementId, skipElements);
    printDateItem("DatePosted", DateUtil.dateToLocalDate(transaction.getDatePosted()), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Id", transaction.getId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Memo", transaction.getMemo(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("Name", transaction.getName(), indent, skipUnsetFields, elementId, skipElements);
    printCurrency("OriginalCurrency", transaction.getOriginalCurrency(), indent, skipUnsetFields, elementId, skipElements);
    printPayee(transaction.getPayee(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("PayeeId", transaction.getPayeeId(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("ReferenceNumber", transaction.getReferenceNumber(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("StandardIndustrialCode", transaction.getStandardIndustrialCode(), indent, skipUnsetFields, elementId, skipElements);
    printStringElement("TempId", transaction.getTempId(), indent, skipUnsetFields, elementId, skipElements);
    printTransactionType(transaction.getTransactionType(), indent, skipUnsetFields, elementId, skipElements);
        
    indent.decrement();
  }

  private void printTransactionType(TransactionType transactionType,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (transactionType != null  ||  !skipUnsetFields) {
      printElementLabel("TransactionType", indent);
      
      if (transactionType != null) {
        System.out.println(transactionType.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printPayee(Payee payee,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "Payee");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (payee != null  ||  !skipUnsetFields) {
      printClassLabel("Payee", indent);

      if (payee != null) {
        indent.increment();
        
        printStringElement("Address1", payee.getAddress1(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Address2", payee.getAddress2(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Address3", payee.getAddress3(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("City", payee.getCity(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Country", payee.getCountry(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Name", payee.getName(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Phone", payee.getPhone(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("State", payee.getState(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Zip", payee.getZip(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printCurrency(String label, Currency currency,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, label);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (currency != null  ||  !skipUnsetFields) {
      printClassLabel(label, indent);

      if (currency != null) {
        indent.increment();
        
        printStringElement("Code", currency.getCode(), indent, skipUnsetFields, elementId, skipElements);
        printFloatItem("ExchangeRate", currency.getExchangeRate(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printCreditCardAccountDetails(String label, CreditCardAccountDetails creditCardAccountDetails,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, label);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (creditCardAccountDetails != null  ||  !skipUnsetFields) {
      printClassLabel(label, indent);

      if (creditCardAccountDetails != null) {
        indent.increment();
        
        printStringElement("AccountKey", creditCardAccountDetails.getAccountKey(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("AccountNumber", creditCardAccountDetails.getAccountNumber(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printCorrectionAction(CorrectionAction correctionAction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (correctionAction != null  ||  !skipUnsetFields) {
      printElementLabel("CorrectionAction", indent);
      
      if (correctionAction != null) {
        System.out.println(correctionAction.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printBankAccountDetails(String label, BankAccountDetails bankAccountDetails,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, label);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (bankAccountDetails != null  ||  !skipUnsetFields) {
      printClassLabel(label, indent);

      if (bankAccountDetails != null) {
        indent.increment();
        
        printStringElement("AccountKey", bankAccountDetails.getAccountKey(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("AccountNumber", bankAccountDetails.getAccountNumber(), indent, skipUnsetFields, elementId, skipElements);
        printAccountType(bankAccountDetails.getAccountType(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("BankId", bankAccountDetails.getBankId(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("BranchId", bankAccountDetails.getBranchId(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("RoutingNumber", bankAccountDetails.getRoutingNumber(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printAccountType(AccountType accountType,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    throw new RuntimeException("Method not yet implemented");
  }

  private void printInvestmentPositionList(InvestmentPositionList investmentPositionList,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentPositionList");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentPositionList != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentPositionList", indent);

      if (investmentPositionList != null) {
        indent.increment();
        
        for (BasePosition basePosition: investmentPositionList.getPositions()) {
          printBasePosition(basePosition, indent, skipUnsetFields, elementId, skipElements);
        }
        
        indent.decrement();
      }
    }    
  }

  private void printBasePosition(BasePosition basePosition,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BasePosition");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (basePosition != null  ||  !skipUnsetFields) {
      printClassLabel("BasePosition", indent);

      if (basePosition != null) {
        indent.increment();
        
        printStringElement("401kSource", basePosition.get401kSource(), indent, skipUnsetFields, elementId, skipElements);
        printInv401kSource(basePosition.get401kSourceEnum(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("CurrencyCode", basePosition.getCurrencyCode(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("HeldInAccount", basePosition.getHeldInAccount(), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition
        printInvestmentPosition(basePosition.getInvestmentPosition(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("MarketValue", basePosition.getMarketValue(), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition
        printDateItem("MarketValueDate", DateUtil.dateToLocalDate(basePosition.getMarketValueDate()), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition
        printStringElement("Memo", basePosition.getMemo(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("PositionType", basePosition.getPositionType(), indent, skipUnsetFields, elementId, skipElements);  // same as next line
        printPositionType(basePosition.getPositionTypeEnum(), indent, skipUnsetFields, elementId, skipElements);
        printSecurityId(basePosition.getSecurityId(), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition
        printMoneyItem("UnitPrice", basePosition.getUnitPrice(), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition
        printDoubleItem("Units", basePosition.getUnits(), indent, skipUnsetFields, elementId, skipElements);  // is part of InvestmentPosition

        indent.decrement();
      }
    }    
  }

  private void printPositionType(PositionType positionType,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (positionType != null  ||  !skipUnsetFields) {
      printElementLabel("PositionType", indent);
      
      if (positionType != null) {
        System.out.println(positionType.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printInvestmentPosition(InvestmentPosition investmentPosition,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentPosition");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentPosition != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentPosition", indent);

      if (investmentPosition != null) {
        indent.increment();
        
        printStringElement("401kSource", investmentPosition.get401kSource(), indent, skipUnsetFields, elementId, skipElements);
        printInv401kSource(investmentPosition.get401kSourceEnum(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("CurrencyCode", investmentPosition.getCurrencyCode(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("HeldInAccount", investmentPosition.getHeldInAccount(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("MarketValue", investmentPosition.getMarketValue(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("MarketValueDate", DateUtil.dateToLocalDate(investmentPosition.getMarketValueDate()), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Memo", investmentPosition.getMemo(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("PositionType", investmentPosition.getPositionType(), indent, skipUnsetFields, elementId, skipElements); // same as next line
        printPositionType(investmentPosition.getPositionTypeEnum(), indent, skipUnsetFields, elementId, skipElements);
        printSecurityId(investmentPosition.getSecurityId(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("UnitPrice", investmentPosition.getUnitPrice(), indent, skipUnsetFields, elementId, skipElements);
        printDoubleItem("Units", investmentPosition.getUnits(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }    
  }

  private void printInv401kSource(Inv401KSource inv401KSource,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (inv401KSource != null  ||  !skipUnsetFields) {
      printElementLabel("Inv401KSource", indent);
      
      if (inv401KSource != null) {
        System.out.println(inv401KSource.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printInvestmentTransactionList(InvestmentTransactionList investmentTransactionList,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentTransactionList");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentTransactionList != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentTransactionList", indent);

      if (investmentTransactionList != null) {
        indent.increment();
        
        printInvestmentBankTransactions(investmentTransactionList.getBankTransactions(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("End",  DateUtil.dateToLocalDate(investmentTransactionList.getEnd()), indent, skipUnsetFields, elementId, skipElements);
        printBaseInvestmentTransactions(investmentTransactionList.getInvestmentTransactions(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("Start",  DateUtil.dateToLocalDate(investmentTransactionList.getStart()), indent, skipUnsetFields, elementId, skipElements);
        investmentTransactionList.getStart();

        indent.decrement();
      }
    }    
  }

  private void printBaseInvestmentTransactions(List<BaseInvestmentTransaction> baseInvestmentTransactions,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BaseInvestmentTransactions");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (baseInvestmentTransactions != null  ||  !skipUnsetFields) {
      printClassLabel("BaseInvestmentTransactions", indent);

      if (baseInvestmentTransactions != null) {
        indent.increment();

        for (BaseInvestmentTransaction baseInvestmentTransaction: baseInvestmentTransactions) {
          printBaseInvestmentTransaction(baseInvestmentTransaction, indent, skipUnsetFields, elementId, skipElements);
        }

        indent.decrement();
      }
    }
  }

  private void printInvestmentBankTransactions(List<InvestmentBankTransaction> investmentBankTransactions,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentTransactions");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentBankTransactions != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentTransactions", indent);

      if (investmentBankTransactions != null) {
        indent.increment();

        for (InvestmentBankTransaction investmentBankTransaction: investmentBankTransactions) {
          printInvestmentBankTransaction(investmentBankTransaction, indent, skipUnsetFields, elementId, skipElements);
        }

        indent.decrement();
      }
    }
  }

  private void printBaseInvestmentTransaction(BaseInvestmentTransaction baseInvestmentTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BaseInvestmentTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (baseInvestmentTransaction != null  ||  !skipUnsetFields) {
      printClassLabel("BaseInvestmentTransaction", indent);

      if (baseInvestmentTransaction != null) {
        indent.increment();
        
        printInvestmentTransaction(baseInvestmentTransaction.getInvestmentTransaction(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Memo",  baseInvestmentTransaction.getMemo(), indent, skipUnsetFields, elementId, skipElements);        
        printStringElement("ReversalTransactionId",  baseInvestmentTransaction.getReversalTransactionId(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("ServerId",  baseInvestmentTransaction.getServerId(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("SettlementDate",  DateUtil.dateToLocalDate(baseInvestmentTransaction.getSettlementDate()), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("TradeDate",  DateUtil.dateToLocalDate(baseInvestmentTransaction.getTradeDate()), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("TransactionId",  baseInvestmentTransaction.getTransactionId(), indent, skipUnsetFields, elementId, skipElements);
        printTransactionType(baseInvestmentTransaction.getTransactionType(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printTransactionType(net.sf.ofx4j.domain.data.investment.transactions.TransactionType transactionType,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (transactionType != null  ||  !skipUnsetFields) {
      printElementLabel("TransactionType", indent);
      
      if (transactionType != null) {
        System.out.println(transactionType.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printInvestmentTransaction(InvestmentTransaction investmentTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentTransaction != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentTransaction", indent);

      if (investmentTransaction != null) {
        indent.increment();
        
        printStringElement("Memo",  investmentTransaction.getMemo(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("ReversalTransactionId",  investmentTransaction.getReversalTransactionId(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("ServerId",  investmentTransaction.getServerId(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("SettlementDate",  DateUtil.dateToLocalDate(investmentTransaction.getSettlementDate()), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("TradeDate",  DateUtil.dateToLocalDate(investmentTransaction.getTradeDate()), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("TransactionId",  investmentTransaction.getTransactionId(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printInvestmentBankTransaction(InvestmentBankTransaction investmentBankTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentBankTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentBankTransaction != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentBankTransaction", indent);

      if (investmentBankTransaction != null) {
        indent.increment();
        
        printStringElement("SubAccountFund",  investmentBankTransaction.getSubAccountFund(), indent, skipUnsetFields, elementId, skipElements);
        printSubAccountType(investmentBankTransaction.getSubAccountFundEnum(), indent, skipUnsetFields, elementId, skipElements);
        printTransaction(investmentBankTransaction.getTransaction(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printSubAccountType(SubAccountType subAccountType,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (subAccountType != null  ||  !skipUnsetFields) {
      printElementLabel("SubAccountType", indent);
      
      if (subAccountType != null) {
        System.out.println(subAccountType.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printInvestmentBalance(InvestmentBalance investmentBalance,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentBalance");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentBalance != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentBalance", indent);

      if (investmentBalance != null) {
        indent.increment();
        
        printMoneyItem("AvailableCash",  investmentBalance.getAvailableCash(), indent, skipUnsetFields, elementId, skipElements);
        printBalanceList(investmentBalance.getBalanceList(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("BuyingPower",  investmentBalance.getBuyingPower(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("MarginBalance",  investmentBalance.getMarginBalance(), indent, skipUnsetFields, elementId, skipElements);
        printMoneyItem("ShortBalance",  investmentBalance.getShortBalance(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printBalanceList(BalanceList balanceList,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BalanceList");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (balanceList != null  ||  !skipUnsetFields) {
      printClassLabel("BalanceList", indent);

      if (balanceList != null) {
        indent.increment();
        
        for (BalanceRecord balanceRecord: balanceList.getBalanceRecords()) {
          printBalanceRecord(balanceRecord, indent, skipUnsetFields, elementId, skipElements);
        }

        indent.decrement();
      }
    }
  }

  private void printBalanceRecord(BalanceRecord balanceRecord,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "BalanceRecord");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (balanceRecord != null  ||  !skipUnsetFields) {
      printClassLabel("BalanceRecord", indent);

      if (balanceRecord != null) {
        indent.increment();
        
        printCurrency(balanceRecord.getCurrency(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Description", balanceRecord.getDescription(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Name", balanceRecord.getName(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("Timestamp", DateUtil.dateToLocalDate(balanceRecord.getTimestamp()), indent, skipUnsetFields, elementId, skipElements);
        printType(balanceRecord.getType(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("Value", balanceRecord.getValue(), indent, skipUnsetFields, elementId, skipElements);

        System.out.println();

        indent.decrement();
      }
    }
  }

  private void printType(Type type,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (type != null  ||  !skipUnsetFields) {
      printElementLabel("Type", indent);
      
      if (type != null) {
        System.out.println(type.name());
      } else {
        System.out.println("-");
      }
    }
  }

  private void printCurrency(Currency currency,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "Currency");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (currency != null  ||  !skipUnsetFields) {
      printClassLabel("Currency", indent);

      if (currency != null) {
        indent.increment();
        
        printStringElement("Code", currency.getCode(), indent, skipUnsetFields, elementId, skipElements);
        printFloatItem("ExchangeRate", currency.getExchangeRate(), indent, skipUnsetFields, elementId, skipElements);
          
        indent.decrement();
      }
    }
  }

  private void printBalanceInfo(String label, BalanceInfo balanceInfo,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, label);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (balanceInfo != null  ||  !skipUnsetFields) {
      printClassLabel(label, indent);

      if (balanceInfo != null) {
        indent.increment();

        printMoneyItem("Amount", balanceInfo.getAmount(), indent, skipUnsetFields, elementId, skipElements);
        printDateItem("AsOfDate", DateUtil.dateToLocalDate(balanceInfo.getAsOfDate()), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printInvestmentAccountDetails(InvestmentAccountDetails investmentAccountDetails,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "InvestmentAccountDetails");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (investmentAccountDetails != null  ||  !skipUnsetFields) {
      printClassLabel("InvestmentAccountDetails", indent);

      if (investmentAccountDetails != null) {
        indent.increment();

        printStringElement("AccountKey", investmentAccountDetails.getAccountKey(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("AccountNumber", investmentAccountDetails.getAccountNumber(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("BrokerId", investmentAccountDetails.getBrokerId(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printSignonResponseMessageSet(SignonResponseMessageSet signonResponseMessageSet,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "SignonResponseMessageSet");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    printClassLabel("SignonResponseMessageSet", indent);
    
    indent.increment();
    
    printPasswordChangeResponseTransaction(signonResponseMessageSet.getPasswordChangeResponse(), indent, skipUnsetFields, elementId, skipElements);
    printResponseMessages(signonResponseMessageSet.getResponseMessages(), indent, skipUnsetFields, elementId, skipElements);
    printSignonResponse(signonResponseMessageSet.getSignonResponse(), indent, skipUnsetFields, elementId, skipElements);
    printMessageSetType(signonResponseMessageSet.getType(), indent, skipUnsetFields, elementId, skipElements);
    
    printStringElement("Version", signonResponseMessageSet.getVersion(), indent, skipUnsetFields, elementId, skipElements);
    
    indent.decrement();
  }
  
  private void printMessageSetType(MessageSetType type,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    printStringElement("MessageSetType", type.name(), indent, skipUnsetFields, parent, skipElements);
  }

  private void printResponseMessages(List<ResponseMessage> responseMessages,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "ResponseMessages");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (responseMessages != null  ||  !skipUnsetFields) {
      printClassLabel("ResponseMessages", indent);

      if (responseMessages != null) {
        indent.increment();

        for (ResponseMessage responseMessage: responseMessages) {
          printResponseMessage(responseMessage, indent, skipUnsetFields, elementId, skipElements);
        }

        indent.decrement();
      }
    }
  }

  private void printResponseMessage(ResponseMessage responseMessage,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "ResponseMessage");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (responseMessage != null  ||  !skipUnsetFields) {
      printClassLabel("ResponseMessage", indent);

      if (responseMessage != null) {
        indent.increment();

        printStringElement("ResponseMessageName", responseMessage.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();
      }
    }
  }

  private void printPasswordChangeResponseTransaction(PasswordChangeResponseTransaction passwordChangeResponseTransaction,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, "PasswordChangeResponseTransaction");
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (passwordChangeResponseTransaction != null  ||  !skipUnsetFields) {
      printClassLabel("PasswordChangeResponseTransaction", indent);

      if (passwordChangeResponseTransaction != null) {
        indent.increment();

        printStringElement("ClientCookie", passwordChangeResponseTransaction.getClientCookie(), indent, skipUnsetFields, elementId, skipElements);
        printPasswordChangeResponse(passwordChangeResponseTransaction.getMessage(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("ResponseMessageName", passwordChangeResponseTransaction.getResponseMessageName(), indent, skipUnsetFields, elementId, skipElements);
        printStatus(passwordChangeResponseTransaction.getStatus(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("StatusHolderName", passwordChangeResponseTransaction.getStatusHolderName(), indent, skipUnsetFields, elementId, skipElements);
        printStringElement("UID", passwordChangeResponseTransaction.getUID(), indent, skipUnsetFields, elementId, skipElements);
        printElementLabel("[WrappedMessage]", indent);
        printPasswordChangeResponse(passwordChangeResponseTransaction.getWrappedMessage(), indent, skipUnsetFields, elementId, skipElements);

        indent.decrement();    
      }
    }
  }

  private void printPasswordChangeResponse(PasswordChangeResponse message,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    throw new RuntimeException("Method not yet implemented");
  }

  private void printClassLabel(String label, Indent indent) {
      System.out.print(indent.toString());
      System.out.println(label + ":");
  }

  private void printElementLabel(String label, Indent indent) {
      System.out.print(indent.toString());
      System.out.print(label + ": ");
  }
  
  private void printStringElement(String elementName, String value,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, elementName);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if ((value != null)  ||  (skipUnsetFields == false)) {
      printElementLabel(elementName, indent);
      if (value != null) {
        System.out.println(value);
      } else {
        System.out.println("-");
      }
    }
  }
  
  private void printFloatItem(String label, Float value,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (value != null  ||  skipUnsetFields == false) {
      printElementLabel(label, indent);
      if (value != null) {
        System.out.println(value);
      } else {
        System.out.println("-");
      }
    }
  }
  
  private void printDoubleItem(String label, Double value,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (value != null  ||  skipUnsetFields == false) {
      printElementLabel(label, indent);
      if (value != null) {
        System.out.println(value);
      } else {
        System.out.println("-");
      }
    }
  }
  
  private void printMoneyItem(String label, Double value,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (value != null  ||  skipUnsetFields == false) {
      printElementLabel(label, indent);
      if (value != null) {
        System.out.print(value);
        
        PgCurrency money = new PgCurrency(PgCurrency.EURO, value);
        System.out.print(" [");
        System.out.print(CF.format(money));
        System.out.println("]");
      } else {
        System.out.println("-");
      }
    }
  }

  private void printBigDecimalItem(String label, BigDecimal bigDecimalAmount,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    if (bigDecimalAmount != null  ||  skipUnsetFields == false) {
      printElementLabel(label, indent);
      if (bigDecimalAmount != null) {
        System.out.print(bigDecimalAmount);
        
        PgCurrency money = new PgCurrency(PgCurrency.EURO, bigDecimalAmount.longValue());
        System.out.print(" [");
        System.out.print(CF.format(money));
        System.out.println("]");
      } else {
        System.out.println("-");
      }
    }
  }
  
  private void printDateItem(String elementName, LocalDate date,
      Indent indent, boolean skipUnsetFields, String parent, Set<String> skipElements) {
    String elementId = createElementPath(parent, elementName);
    if (skipElements.contains(elementId)) {
      return;
    }
    
    if (date != null  ||  skipUnsetFields == false) {
      printElementLabel(elementName, indent);
      if (date != null) {
        System.out.println(date.format(DF));
      } else {
        System.out.println("-");
      }
    }
  }
  
  public static String createElementPath(String parent, String elementName) {
    if (parent == null) {
      return elementName;
    } else {
      return parent + "." + elementName;
    }
  }
  
  public static void getBaseSecurityInfos(ResponseEnvelope envelope, Map<String, LynxSecurityInfo> lynxSecurityInfos) {
    LOGGER.info("=>");
    LOGGER.info("handling: ResponseMessageSets");
    SortedSet<ResponseMessageSet> responseMessageSets = envelope.getMessageSets();
    for (ResponseMessageSet responseMessageSet: responseMessageSets) {
      LOGGER.info("  handling: ResponseMessageSet");
      if (responseMessageSet instanceof SecurityListResponseMessageSet) {
        LOGGER.info("    handling: InvestmentStatementResponseMessageSet");
        SecurityListResponseMessageSet securityListResponseMessageSet = (SecurityListResponseMessageSet) responseMessageSet;
        SecurityList securityList = securityListResponseMessageSet.getSecurityList();

        if (securityList != null) {
          LOGGER.info("        securityList not null");
          getBaseSecurityInfos(securityList.getSecurityInfos(), lynxSecurityInfos);
        }
      }
    }
    LOGGER.info("<=");
  }
  
  public static void getBaseSecurityInfos(List<BaseSecurityInfo> baseSecurityInfos, Map<String, LynxSecurityInfo> lynxSecurityInfos) {
    LOGGER.info("=>");
    for (BaseSecurityInfo baseSecurityInfo: baseSecurityInfos) {
      LynxSecurityInfo lynxSecurityInfo = getBaseSecurityInfo(baseSecurityInfo);
      LynxSecurityInfo existingLynxSecurityInfo = lynxSecurityInfos.get(lynxSecurityInfo.uniqueId);
      if (existingLynxSecurityInfo == null) {
        lynxSecurityInfos.put(lynxSecurityInfo.uniqueId, lynxSecurityInfo);
      } else {
        // The securityName may change over time, so it is not checked.
        if (!(existingLynxSecurityInfo.fiId.equals(lynxSecurityInfo.fiId)  &&
              existingLynxSecurityInfo.uniqueIdType.equals(lynxSecurityInfo.uniqueIdType) &&
              existingLynxSecurityInfo.tickerSymbol.equals(lynxSecurityInfo.tickerSymbol))) {
          LOGGER.severe("Found Security Info with different values for same UniqueId:" + NEWLINE +
                                     "existing:" + existingLynxSecurityInfo.toString() + NEWLINE +
                                     "new: " + lynxSecurityInfo.toString() + NEWLINE);
        }
      }
    }
    LOGGER.info("<=");
  }
  
  public static LynxSecurityInfo getBaseSecurityInfo(BaseSecurityInfo baseSecurityInfo) {
    LOGGER.info("=>");
    LynxSecurityInfo lynxSecurityInfo = new LynxSecurityInfo();
    
    lynxSecurityInfo.fiId = baseSecurityInfo.getFiId();
    if (lynxSecurityInfo.fiId == null) {
      throw new RuntimeException("Found SecurityInfo with FiId null.");
    }
    SecurityId securityId = baseSecurityInfo.getSecurityId();
    if (securityId != null) {
      lynxSecurityInfo.uniqueId = securityId.getUniqueId();
      if (lynxSecurityInfo.uniqueId == null) {
        throw new RuntimeException("Found SecurityInfo with UniqueId null.");
      }
      lynxSecurityInfo.uniqueIdType = securityId.getUniqueIdType();
      if (lynxSecurityInfo.uniqueIdType == null) {
        throw new RuntimeException("Found SecurityInfo with UniqueIdType null.");
      }
    }
    lynxSecurityInfo.securityName = baseSecurityInfo.getSecurityName();
    if (lynxSecurityInfo.securityName == null) {
      throw new RuntimeException("Found SecurityInfo with SecurityName null.");
    }
    lynxSecurityInfo.tickerSymbol = baseSecurityInfo.getTickerSymbol();
    if (lynxSecurityInfo.tickerSymbol == null) {
      throw new RuntimeException("Found SecurityInfo with TickerSymbol null.");
    }
    
    
    SecurityInfo securityInfo = baseSecurityInfo.getSecurityInfo();
    if (securityInfo != null) {
      if (!securityInfo.getFiId().equals(lynxSecurityInfo.fiId)) {
        throw new RuntimeException("Found SecurityInfo where FiId differs from SecurityInfo.FiId.");
      }
      
      SecurityId securityInfoSecurityId = securityInfo.getSecurityId();
      if (securityInfoSecurityId != null) {
        if (!securityInfoSecurityId.getUniqueId().equals(lynxSecurityInfo.uniqueId)) {
          throw new RuntimeException("Found SecurityInfo where UniqueId differs from SecurityInfo.UniqueId.");
        }
        if (!securityInfoSecurityId.getUniqueIdType().equals(lynxSecurityInfo.uniqueIdType)) {
          throw new RuntimeException("Found SecurityInfo where UniqueIdType differs from SecurityInfo.UniqueIdType.");
        }
      }
      
      if (!securityInfo.getSecurityName().equals(lynxSecurityInfo.securityName)) {
        throw new RuntimeException("Found SecurityInfo where SecurityName differs from SecurityInfo.SecurityName.");
      }
      
      if (!securityInfo.getTickerSymbol().equals(lynxSecurityInfo.tickerSymbol)) {
        throw new RuntimeException("Found SecurityInfo where TickerSymbol differs from SecurityInfo.TickerSymbol.");
      }
    }
    
    LOGGER.info("<=");
    return lynxSecurityInfo;
  }

  /**
   * This main function reads a .ofx file and prints it.
   * It is not possible to provide a translation to Finan transactions, as this depends on share conversion information.
   * @param args Input arguments, currently none.
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {

    String filename = "U874486_201205_201205.ofx";
    Ofx2Finan ofx2Finan = new Ofx2Finan(null);
    ofx2Finan.logSetup();

    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
    FileInputStream file = null;

    try {
        file = new FileInputStream(filename);
        Set<String> skipElements = new HashSet<>();
        skipElements.add("UID");
        skipElements.add("ApplicationSecurity");
//        skipElements.add("SignonResponse");
        skipElements.add("SignonResponse.AccessKey");
        skipElements.add("SignonResponse.AccountLastUpdated");
        
        ResponseEnvelope envelope = unmarshaller.unmarshal(file);
        
        ofx2Finan.printResponseEnvelope(envelope, new Indent(INDENT_SIZE), false, skipElements);
        Map<String, LynxSecurityInfo> lynxSecurityInfos = new HashMap<>();
        getBaseSecurityInfos(envelope, lynxSecurityInfos);
        System.out.println(LynxSecurityInfo.lynxSecurityInfoListToString(lynxSecurityInfos));
    } catch (OFXParseException e) {
      LOGGER.severe("Error: " + e.getMessage());
    }    
  }


  private void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(logLevel);
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(Level.INFO);
  }
  
  private List<PgTransaction> createTransactions(PgAccount account, List<PgTransaction> transactions, ResponseEnvelope envelope) {
    LOGGER.info("=>");
    PgTransaction transaction;

    SortedSet<ResponseMessageSet> responseMessageSets = envelope.getMessageSets();
    for (ResponseMessageSet responseMessageSet: responseMessageSets) {
      if (responseMessageSet instanceof InvestmentStatementResponseMessageSet) {
        InvestmentStatementResponseMessageSet investmentStatementResponseMessageSet = (InvestmentStatementResponseMessageSet) responseMessageSet;
        List<InvestmentStatementResponseTransaction> investmentStatementResponseTransactions = investmentStatementResponseMessageSet.getStatementResponses();

        for (InvestmentStatementResponseTransaction investmentStatementResponseTransaction: investmentStatementResponseTransactions) {
          InvestmentStatementResponse investmentStatementResponse = investmentStatementResponseTransaction.getMessage();
          InvestmentTransactionList investmentTransactionList = investmentStatementResponse.getInvestmentTransactionList();

          if (investmentTransactionList != null) {
            List<BaseInvestmentTransaction> baseInvestmentTransactions = investmentTransactionList.getInvestmentTransactions();

            if (baseInvestmentTransactions != null) {
              for (BaseInvestmentTransaction baseInvestmentTransaction: baseInvestmentTransactions) {
                // TODO First group transactions in list for 'deeluitvoeringen', waarschijnlijk zelfde 1e deel transactionId.
                transaction = null;
                net.sf.ofx4j.domain.data.investment.transactions.TransactionType transactionType = baseInvestmentTransaction.getTransactionType();
                if (baseInvestmentTransaction instanceof BuyOptionTransaction) {
                  LOGGER.info("BuyOptionTransaction");
                  transaction = createBuyOptionTransaction(account, (BuyOptionTransaction) baseInvestmentTransaction);
                } else if (baseInvestmentTransaction instanceof BuyStockTransaction) {
                  LOGGER.info("BuyStockTransaction");
                  transaction = createBuyStockTransaction(account, (BuyStockTransaction) baseInvestmentTransaction);
                } else if (baseInvestmentTransaction instanceof IncomeTransaction) {
                  LOGGER.info("IncomeTransaction");
                  // TODO Handle this: dividend, rights, ..
                } else if (baseInvestmentTransaction instanceof SellOptionTransaction) {
                  LOGGER.info("SellOptionTransaction");
                  transaction = createSellOptionTransaction(account, (SellOptionTransaction) baseInvestmentTransaction);
                } else if (baseInvestmentTransaction instanceof SellStockTransaction) {
                  LOGGER.info("SellStockTransaction");
                  transaction = createSellStockTransaction(account, (SellStockTransaction) baseInvestmentTransaction);
                } else if (baseInvestmentTransaction instanceof TransferInvestmentTransaction) {
                  LOGGER.info("TransferTransaction");
                  // TODO Handle this:  ..
                } else {
                  LOGGER.severe("Onbekende transactie: " + transactionType.name());
//                  throw new RuntimeException("Onbekende transactie: " + transactionType.name());
                }
                if (transactionType == net.sf.ofx4j.domain.data.investment.transactions.TransactionType.BUY_STOCK  ||
                    transactionType == net.sf.ofx4j.domain.data.investment.transactions.TransactionType.SELL_STOCK) {
                  //	                System.out.println("Aandelen transactie");
                  //	                InvestmentTransaction investmentTransaction = baseInvestmentTransaction.getInvestmentTransaction();
                  //	                investmentTransaction.
                  //	                aandelenTransactie.setAantalEffecten(aantalEffecten);
                  //	                System.out.println(aandelenTransactie.toString());
                }

                if (transaction != null) {
                  LocalDate date = transaction.getBookingDate();
                  if (date != null) {
                    LOGGER.info(DF.format(date));
                  } else {
                    LOGGER.info("No booking date");
                  }
                  LOGGER.info(transaction.getDescription());
                  date = transaction.getExecutionDate();
                  if (date != null) {
                    LOGGER.info(DF.format(date));
                  } else {
                    LOGGER.info("No execution date");
                  }
                  LOGGER.info("Comment: " + transaction.getComment());
                  transactions.add(transaction);
                } else {
                  LOGGER.info("GEEN Transactie ingevuld.");
                }
              }
            }
          }
        }
      }
    }
    return transactions;
  }

  private PgTransaction createSellStockTransaction(PgAccount account, SellStockTransaction baseInvestmentTransaction) {
    LOGGER.info("=>");
    
    LynxEffRekAandelenTransactie aandelenTransactie = new LynxEffRekAandelenTransactie(account);
    aandelenTransactie.setBookingDate(DateUtil.dateToLocalDate(baseInvestmentTransaction.getSettlementDate()));
    aandelenTransactie.setExecutionDate(DateUtil.dateToLocalDate(baseInvestmentTransaction.getTradeDate()));
    aandelenTransactie.setAankoop(false);  // This is a SellStockTransaction
    SellType sellType = baseInvestmentTransaction.getSellTypeEnum();
    switch (sellType) {
    case SELL:
      break;
    case SELL_SHORT:
      throw new RuntimeException("Unknown SellType: " + sellType);
    }
    int aantalEffecten = baseInvestmentTransaction.getUnits().intValue();
    aandelenTransactie.setAantalEffecten(-aantalEffecten);  // In Ofx is het aantal bij verkoop negatief.
    String uniqueId = baseInvestmentTransaction.getSecurityId().getUniqueId();
    Share effect = shareIdToShareMap.get(uniqueId);
    if (effect == null) {
      throw new RuntimeException("No effect found for uniqueId: " + uniqueId);
    }
    aandelenTransactie.setEffect(effect);
    Double unitPrice = baseInvestmentTransaction.getUnitPrice();
    Double unitPriceCents = 100 * unitPrice;
    long longUnitPriceCents = unitPriceCents.longValue();
    PgCurrency koers = new PgCurrency(longUnitPriceCents);
    aandelenTransactie.setKoers(koers);
    Double commission = baseInvestmentTransaction.getCommission();
    Double commissionCents = 100 * commission;
    long longCommissionCents = commissionCents.longValue();
    aandelenTransactie.setOrderKosten(new PgCurrency(longCommissionCents));
    aandelenTransactie.setUitvoeringsType(EffRekAandelenTransactie.UT_VOLLEDIG);
    LOGGER.info("<=");
    return aandelenTransactie;
  }

  private PgTransaction createSellOptionTransaction(PgAccount account, SellOptionTransaction baseInvestmentTransaction) {
    LOGGER.info("=>");
    
    throw new RuntimeException("Method not yet implemented");
    
//    LOGGER.info("<=");
//    return null;
  }

  private PgTransaction createBuyStockTransaction(PgAccount account, BuyStockTransaction baseInvestmentTransaction) {
    LOGGER.info("=>");
    
    LynxEffRekAandelenTransactie aandelenTransactie = new LynxEffRekAandelenTransactie(account);
    aandelenTransactie.setBookingDate(DateUtil.dateToLocalDate(baseInvestmentTransaction.getSettlementDate()));
    aandelenTransactie.setExecutionDate(DateUtil.dateToLocalDate(baseInvestmentTransaction.getTradeDate()));
    aandelenTransactie.setAankoop(true);  // This is a BuyStockTransaction
    BuyType buyType = baseInvestmentTransaction.getBuyTypeEnum();
    switch (buyType) {
    case BUY:
      break;
    case BUY_TO_COVER:
      throw new RuntimeException("Unknown BuyType: " + buyType);
    }
    int aantalEffecten = baseInvestmentTransaction.getUnits().intValue();
    aandelenTransactie.setAantalEffecten(aantalEffecten);
    String uniqueId = baseInvestmentTransaction.getSecurityId().getUniqueId();
    Share effect = shareIdToShareMap.get(uniqueId);
    if (effect == null) {
      throw new RuntimeException("No effect found for uniqueId: " + uniqueId);
    }
    aandelenTransactie.setEffect(effect);
    Double unitPrice = baseInvestmentTransaction.getUnitPrice();
    Double unitPriceDeciCents = 1000 * unitPrice;
    long longUnitPriceDeciCents = unitPriceDeciCents.longValue();
    long deciCent = longUnitPriceDeciCents % 10;
    PgCurrency koers;
    if (deciCent == 0) {
      koers = new PgCurrency(longUnitPriceDeciCents / 10);
    } else {
      koers = new PgCurrency(PgCurrency.EURO, longUnitPriceDeciCents, 1000);
    }
    aandelenTransactie.setKoers(koers);
    Double commission = baseInvestmentTransaction.getCommission();
    Double commissionCents = 100 * commission;
    long longCommissionCents = commissionCents.longValue();
    aandelenTransactie.setOrderKosten(new PgCurrency(longCommissionCents));
    aandelenTransactie.setUitvoeringsType(EffRekAandelenTransactie.UT_VOLLEDIG);
    
    LOGGER.info("baseInvestmentTransaction.getTransactionId(): " + baseInvestmentTransaction.getTransactionId());
    LOGGER.info("<=");
    return aandelenTransactie;
  }

  private PgTransaction createBuyOptionTransaction(PgAccount account, BuyOptionTransaction baseInvestmentTransaction) {
    LOGGER.info("=>");
    
    throw new RuntimeException("Method not yet implemented");
//    LOGGER.info("<=");
//    return null;
  }

  public List<PgTransaction> getFinanTransactionsFromActivityStatement(LynxMonthlyActivityStatement lynxMonthlyActivityStatement, List<PgTransaction> pgTransactions) {
    if (pgTransactions == null) {
      pgTransactions = new ArrayList<>();
    }
    
    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
    FileInputStream file = null;
    Path maandOverzichtPath = lynxMonthlyActivityStatement.getPath();
    
    try {
            LOGGER.info("Handling maandoverzicht: " + maandOverzichtPath.getFileName().toString());
            try {
                file = new FileInputStream(maandOverzichtPath.toFile().getAbsolutePath());
                ResponseEnvelope envelope = unmarshaller.unmarshal(file);
                
                createTransactions(null, pgTransactions, envelope);
//                Ofx2Finan.getBaseSecurityInfos(envelope, lynxSecurityInfos);
            } catch (OFXParseException e) {
              LOGGER.severe("Error: " + e.getMessage());
            }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return pgTransactions;
  }
  
  public List<PgTransaction> getFinanTransactionsFromActivityStatements() {
    List<PgTransaction> pgTransactions = new ArrayList<>();
    
    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
    FileInputStream file = null;
    
    Path maandOverzichtenPath = Paths.get(FinanRegistry.dataDirectory, "lynx", "maandoverzichten");
    List<Path> yearFolders = new ArrayList<>();
    try (DirectoryStream<Path> jarenStream = Files.newDirectoryStream(maandOverzichtenPath)) {
      for (Path yearFolderPath: jarenStream) {
        yearFolders.add(yearFolderPath);
      }
      Collections.sort(yearFolders);
      for (Path yearFolder: yearFolders) {
        LOGGER.info("Handling folder: " + yearFolder.getFileName().toString());
        DirectoryStream<Path> maandOverzichtenStream = Files.newDirectoryStream(yearFolder);
        List<Path> maandOverzichtPaths = new ArrayList<>();
        for (Path maandOverzichtPath: maandOverzichtenStream) {
          maandOverzichtPaths.add(maandOverzichtPath);
        }
        Collections.sort(maandOverzichtPaths);
        for (Path maandOverzichtPath: maandOverzichtPaths) {
          String fileExtension = FileUtils.getFileExtension(maandOverzichtPath);
          if (fileExtension.equals(".ofx")) {
            LOGGER.info("Handling maandoverzicht: " + maandOverzichtPath.getFileName().toString());
            try {
                file = new FileInputStream(maandOverzichtPath.toFile().getAbsolutePath());
                ResponseEnvelope envelope = unmarshaller.unmarshal(file);
                
                createTransactions(null, pgTransactions, envelope);
//                Ofx2Finan.getBaseSecurityInfos(envelope, lynxSecurityInfos);
            } catch (OFXParseException e) {
              LOGGER.severe("Error: " + e.getMessage());
            }
          } else {
            LOGGER.severe("Skipping file: " + maandOverzichtPath.getFileName().toString());
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return pgTransactions;
  }
}
