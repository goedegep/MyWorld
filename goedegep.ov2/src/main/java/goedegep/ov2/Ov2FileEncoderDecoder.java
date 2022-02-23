package goedegep.ov2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Ov2FileEncoderDecoder {
  private static final Logger LOGGER = Logger.getLogger(Ov2FileEncoderDecoder.class.getName());
  
  public static List<Ov2Item> decodeFile(String fileName) throws FileNotFoundException {
    List<Ov2Item> ov2Items = new ArrayList<>();
    
      Ov2DecodeFileIterator iterator = new Ov2DecodeFileIterator(fileName);
      while (iterator.hasNext()) {
        Ov2Item ov2Item = iterator.next();
        ov2Items.add(ov2Item);
      }
      
      return ov2Items;
    
  }
  
  public static void encodeToFile(List<Ov2Item> ov2Items, String fileName) throws IOException {
    File file = new File(fileName);

    if (file.exists()) {
      throw new FileAlreadyExistsException(fileName);
    }

    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
    
    for (Ov2Item ov2Item: ov2Items) {
      outputStream.write(ov2Item.getType());
      
      byte[] data = null;
      
      switch (ov2Item.getType()) {
      case 0:
        LOGGER.severe("Type 0 is not supported yet");
        break;
        
      case 1:
        LOGGER.severe("Type 1 is not supported yet");
        break;
        
      case 2:
        data = encodeType2Data((Ov2ItemType2) ov2Item);
        break;
        
      case 3:
        LOGGER.severe("Type 3 is not supported yet");
        break;
      }
      
      if (data != null) {
        writeLittleEndianInt(outputStream, 1 + 4 + data.length);  // type + length + data
        outputStream.write(data);
      }
      
    }
 
    outputStream.close();
  }
  
  private static byte[] encodeType2Data(Ov2ItemType2 ov2ItemType2) {
    String description = ov2ItemType2.getDescription();
    if (description == null) {
      description = "(no description)";
    }
    int dataSize = 4 + 4 + description.length() + 1;  // lat + long + description + '0'
    byte[] data = new byte[dataSize];
    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
    
    int dataIndex = 0;
    writeCoordinate(byteBuffer, dataIndex, ov2ItemType2.getLongitude());
    dataIndex += 4;
    writeCoordinate(byteBuffer, dataIndex, ov2ItemType2.getLatitude());
    
    dataIndex += 4;
    for (int index = 0; index < description.length(); index++) {
      byte b = (byte) description.charAt(index);
      data[dataIndex++] = b;
    }
    data[dataIndex] = 0;
    
    return data;
  }

  /**
   * array byte to int (little endian)
   * 
   * @param array
   * @return
   * @throws IOException 
   */

  private static void writeLittleEndianInt(BufferedOutputStream outputStream, int value) throws IOException {
    outputStream.write(value % 256);
    value >>= 8;
    outputStream.write(value % 256);
    value >>= 8;
    outputStream.write(value % 256);
    value >>= 8;
    outputStream.write(value);
  }
  
  private static void writeCoordinate(ByteBuffer byteBuffer, int index, double coordinate) {
    coordinate *= 100000;
    if (coordinate < 0) {
      coordinate -= 0.5;
    } else {
      coordinate += 0.5;
    }
    int coordinateInt = (int) coordinate;
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putInt(index, coordinateInt);
  }
}
