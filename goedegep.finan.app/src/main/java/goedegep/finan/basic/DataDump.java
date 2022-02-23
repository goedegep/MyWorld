package goedegep.finan.basic;

import java.io.IOException;

import goedegep.util.text.TextWriter;

public interface DataDump {
  /**
   * This method shall dump all relevant data, in a simple format, to the BufferedWriter (typically a file).
   * This is used to compare the functional behavior of different versions of the software.
   * 
   * @param out the Writer where all information shall be written to.
   * @throws IOException
   */
  public void dumpData(TextWriter textWriter) throws IOException;
}
