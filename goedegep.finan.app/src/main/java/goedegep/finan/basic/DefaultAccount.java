package goedegep.finan.basic;

import java.io.IOException;

import goedegep.util.text.TextWriter;

public class DefaultAccount extends PgAccount {
  public DefaultAccount() {
    super(true, false);
  }
  
  public DefaultAccount(String name) {
    super(true, false, name);
  }

  @Override
  public void dumpData(TextWriter out) throws IOException {
  }

  public String toString(PgTransaction transaction) {
    return null;
  }

  public String toXmlString(PgTransaction transaction, String nameSpace) {
    return null;
  }
}
