package goedegep.ov2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import goedegep.ov2.decode.Ov2DecodeChain;
import goedegep.ov2.decode.Ov2DecodeException;
import goedegep.ov2.decode.Ov2DecodeItem;

public class Ov2DecodeFileIterator implements Iterator<Ov2Item> {

  private long currentIndex = 0;
  private long fileLength;
  private byte[] lengthByteArray = new byte[4];
  private BufferedInputStream inputSteam = null;

  private Ov2DecodeItem decodeChain;

  public Ov2DecodeFileIterator(String path) throws FileNotFoundException {
    File file = new File(path);
    if (file.exists()) {
      fileLength = file.length();
      inputSteam = new BufferedInputStream(new FileInputStream(file));
      decodeChain = Ov2DecodeChain.getInstance().getDecodeChain();
    } else {
      throw new FileNotFoundException(path + " not exists");
    }

  }

  @Override
  public boolean hasNext() {

    return currentIndex < fileLength;
  }

  @Override
  public Ov2Item next() {

    int entryLength = -1;
    inputSteam.mark(5);
    int type;
    try {
      type = inputSteam.read();

      // hack for ov2 type 1 SKIPPER RECORD
      if (type == 1) {
        entryLength = 21;
      } else {
        inputSteam.read(lengthByteArray);
        entryLength = readInt(lengthByteArray);
      }

      inputSteam.reset();
      byte[] entryData = new byte[entryLength];
      inputSteam.read(entryData);
      currentIndex += entryLength;
      return decodeChain.decode(entryData);
    } catch (IOException e) {
      throw new Ov2DecodeException("Error to decode Ov2 Item");
    } finally {
      if (!hasNext()) {
        close();
      }
    }
  }

  /**
   * array byte to int (little endian)
   * 
   * @param array
   * @return
   */

  private int readInt(byte[] array) {
    int response = array[0];

    response += array[1] << 8;
    response += array[2] << 16;
    response += array[3] << 24;

    return response;
  }

  /**
   * close inputStrem
   */
  private void close() {
    if (inputSteam != null) {
      try {
        inputSteam.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
