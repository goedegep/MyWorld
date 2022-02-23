package goedegep.finan.basic;

import java.io.IOException;
import java.util.List;

import goedegep.util.text.TextWriter;

public class StubBank extends Bank {
  
  public StubBank(String name) {
    super();
    setName(name);
  }
  
  public void clear() {
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
  }

  public List<PgAccount> getAccounts() {
    return null;
  }

  @Override
  public PgAccount openAccount(String accountName) {
    return null;
  }
}
