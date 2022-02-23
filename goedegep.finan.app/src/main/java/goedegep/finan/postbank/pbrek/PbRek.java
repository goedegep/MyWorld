//Title:      Postbank rekening
//Version:
//Copyright:  Copyright (c) 2001
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:Deze klasse omvat een volledige Postbank rekening: giro, effecten, beleggingsfondsen, etc.

package goedegep.finan.postbank.pbrek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.postbank.pbfonds.PbFonds;
import goedegep.finan.postbank.pbfonds.PbFondsId;
import goedegep.finan.postbank.pbsprek.PbSpRek;
import goedegep.finan.postbank.pbsprek.PbSpRekController;
import goedegep.finan.postbank.pbsprek.PbSpRekId;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.text.TextWriter;

public class PbRek extends Bank {
  private static String     BANK_NAME = "Postbank";

  private StockDepot              parentDepot;
  private Map<PbSpRekId, PbSpRek> spaarRekeningen = new TreeMap<PbSpRekId, PbSpRek>();
  private Map<PbFondsId, PbFonds> fondsen = new TreeMap<PbFondsId, PbFonds>();
  private PbEffRek                effectenRekening;

  // Creeer een Postbank rekening
  public PbRek(StockDepot parentDepot) {
    super();

    setName(BANK_NAME);
    this.parentDepot = parentDepot;
  }
  
  public String toString() {
    return getName();
  }

  public String toString(PgTransaction transaction) {
    return transaction.getAccount().getName() + "\t" +
        transaction.toString();
  }

  /**
   * Verkrijg een lijst met alle open Postbank rekeningen. Momenteel:
   * - alle spaarrekeningen
   * - alle beleggingsfondsen
   * - effectenrekening
   */
  public List<PgAccount> getAccounts() {
    List<PgAccount> accountList = new ArrayList<PgAccount>();
    PgAccount     account;

    accountList.addAll(spaarRekeningen.values());
    accountList.addAll(fondsen.values());

    account = (PgAccount) getEffectenRekening(true);
    if (account != null) {
      accountList.add((PgAccount) account);
    }

    return accountList;
  }
  
  //
  // Spaarrekeningen
  //

  public PbSpRek openSpaarRekening(PbSpRekId rekID) {
    if (spaarRekeningen.containsKey(rekID)) {
      throw new IllegalArgumentException("Rekening bestaat al");      
    }

    PbSpRek pbSpRek = PbSpRekController.createSpaarRekening(rekID);

    spaarRekeningen.put(rekID, pbSpRek);
    return pbSpRek;
  }

  public List<PbSpRek> getOpenSpaarRekeningen() {
    return new ArrayList<PbSpRek>((Collection<PbSpRek>) spaarRekeningen.values());
  }
  
  public PbSpRek getSpaarRekening(PbSpRekId pbSpRekId, boolean createIfNotExisting) {
    PbSpRek spaarrekening = getSpaarRekening(pbSpRekId);

    if (spaarrekening == null  &&  createIfNotExisting) {
      spaarrekening = openSpaarRekening(pbSpRekId);
    }

    return spaarrekening;
  }

  public PbSpRek getSpaarRekening(PbSpRekId pbSpRekId) {
    return spaarRekeningen.get(pbSpRekId);
  }


  //
  // Effectenrekening
  //

  public PbEffRek OpenEffectenRekening() {
    effectenRekening = new PbEffRek(parentDepot);
    
    return effectenRekening;
  }

  public PbEffRek getEffectenRekening(boolean createIfNotExisting) {
    PbEffRek er = effectenRekening;
    if (er == null  &&  createIfNotExisting) {
      er = OpenEffectenRekening();
    }

    return er;
  }

  public String getStatusEffectenRekeningAsString() {
    return this.effectenRekening.getStatusAsString();
  }

  //
  // Beleggingsfondsen
  //

  public PbFonds OpenFonds(PbFondsId pbFondId) {
    if (fondsen.containsKey(pbFondId)) {
      throw new IllegalArgumentException("Fonds bestaat al");      
    }

    PbFonds pbFonds = new PbFonds(pbFondId);

    fondsen.put(pbFondId, pbFonds);
    return pbFonds;
  }

  public List<PbFonds> getFondsen() {
    return new ArrayList<PbFonds>((Collection<PbFonds>) fondsen.values());
  }

  public PbFonds getFonds(PbFondsId pbFondsId) {
    return fondsen.get(pbFondsId);
  }

  public PbFonds getFonds(PbFondsId pbFondsId, boolean createIfNotExisting) {
    PbFonds fonds = getFonds(pbFondsId);

    if (fonds == null  &&  createIfNotExisting) {
      fonds = OpenFonds(pbFondsId);
    }

    return fonds;
  }

  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("POSTBANK DATA DUMP");
    textWriter.newLine();
    for (PgAccount account: getAccounts()) {
      account.dumpData(textWriter);
    }
  }

  @Override
  public PgAccount openAccount(String accountName) {
    PbSpRekId pbSpRekId;
    PbFondsId pbFondsId;
    
    if (accountName.compareTo(goedegep.finan.postbank.pbbasic.PbBasic.effectenRekeningString) == 0) {
      return getEffectenRekening(true);
    } else if ((pbSpRekId = PbSpRekId.getIdForName(accountName)) != null) {
      return getSpaarRekening(pbSpRekId, true);          
    } else if ((pbFondsId = PbFondsId.getIdForPbFundName(accountName)) != null) {
      return getFonds(pbFondsId, true);          
    } else {
      throw new IllegalArgumentException("Onbekende rekening naam");
    }
  }  
}
