package goedegep.finan.direktbank.direktsprek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * Dit is de basis class voor alle Direktspaarrekening transacties.
 * Aangezien deze rekening niet meer bestond bij de introductie van de euro,
 * zijn alle bedragen in guldens.
 *
 */

public abstract class DirektSpRekTransaction extends PgTransaction {
  // Transaction types.
  public static final short TT_AFSCHRIJVING  = 1;
  public static final short TT_BIJSCHRIJVING = 2;
  public static final short TT_RENTE         = 3;
  public static final short TT_OPHEFFING     = 4;
  
  private FixedPointValue        rentePercentage = null;    // huidig rentepercentage

  public DirektSpRekTransaction(PgAccount account) {
    super(account);
  }

  public void setRentePercentage(FixedPointValue rentePercentage) {
    this.rentePercentage = rentePercentage;
  }

  public FixedPointValue getRentePercentage() {
    return rentePercentage;
  }

  public abstract String toString();

  public void handleFirst() {
    DirektSpRek account = (DirektSpRek) getAccount();

    account.handleFirst(this);

  }

  public void handleLast() {
    DirektSpRek account = (DirektSpRek) getAccount();

    setNieuwTegoed(account.getBalance());
    setHandled(true);

    account.handleLast(this);
  }
}
