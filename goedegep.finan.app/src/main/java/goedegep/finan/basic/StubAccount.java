package goedegep.finan.basic;

import java.io.IOException;

import goedegep.util.money.PgCurrency;
import goedegep.util.text.TextWriter;

/**
 * <p>Title: Finan</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Peter Goedegebure
 * @version 1.0
 */

public class StubAccount extends PgAccount {

  public StubAccount(String name) {
    super(true, PgCurrency.EURO, 0l, false);
    setName(name);
  }
  
  public void clear() {
    setBalance(new PgCurrency(PgCurrency.EURO, 0l));
  }

  public String toString() {
    return getName();
  }

  public String toXmlString(PgTransaction transaction, String nameSpace) {
    return "";
  }

  public String toString(PgTransaction transaction) {
    return "";
  }
  
  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("STUB ACCOUNT");
    textWriter.newLine();
  }
}