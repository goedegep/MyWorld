package goedegep.ov2;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.ov2.csv.Ov2ToCsvFile;
import goedegep.ov2.csv.Ov2ToCsvType;
import goedegep.ov2.csv.Ov2ToCsvVisitor;
import goedegep.util.logging.MyLoggingFormatter;

public class Main {
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  
  public static void main(String[] args) {
    logSetup(Level.INFO);
    
    URL url = Main.class.getResource("radarsFr.ov2");
    if (url == null) {
      LOGGER.severe("still null");
      System.exit(1);
    }
    
    String fileName = url.getFile();
    LOGGER.severe("fileName: " + fileName);
    try {
      List<Ov2Item> ov2Items = Ov2FileEncoderDecoder.decodeFile(fileName);
      Ov2FileEncoderDecoder.encodeToFile(ov2Items, "/C:/EclipseWorkspace/goedegep.ov2/target/classes/goedegep/ov2/test.ov2");
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
      e.printStackTrace();
    }

    Ov2ToCsvVisitor csvVisitor = new Ov2ToCsvVisitor();

    try
    {
      final Ov2ToCsvFile csvFile = new Ov2ToCsvFile("output_radar2.csv", Ov2ToCsvType.TWO );

      Consumer<String> write = ( String csvString ) ->  {  
        try
        {
          csvFile.writeLine( csvString );
        } 
        catch (Exception e) {
          e.printStackTrace();
        }  
      };

      Ov2DecodeFile.ov2Items("/C:/EclipseWorkspace/goedegep.ov2/target/classes/goedegep/ov2/test.ov2").filter(oV2Item -> oV2Item.getType() == 2)
      .map( oV2Item -> oV2Item.accept( csvVisitor ) )
      .forEach( string ->  write.accept( string )  );

      csvFile.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    
//    Ov2ToCsvVisitor csvVisitor = new Ov2ToCsvVisitor();
//
//    try
//    {
//      final Ov2ToCsvFile csvFile = new Ov2ToCsvFile("output_radar.csv", Ov2ToCsvType.TWO );	
//
//      Consumer<String> write = ( String csvString ) ->  {  
//        try
//        {
//          csvFile.writeLine( csvString );
//        } 
//        catch (Exception e) {
//          e.printStackTrace();
//        }  
//      };
//
//      Ov2DecodeFile.ov2Items(url).filter(oV2Item -> oV2Item.getType() == 2)
//      .map( oV2Item -> oV2Item.accept( csvVisitor ) )
//      .forEach( string ->  write.accept( string )  );
//
//      csvFile.close();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    
    LOGGER.severe("<=");
  }


  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install a {@link MyLoggingFormatter}.</li>
   * <li>Install logging to a file, if a logFileBaseName is specified.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   * @param logFileBaseName base name of the file to which logging information will be written. The actual filename is this base name with ".txt" appended to it.
   *                        If null, no file logging takes place.
   */
  protected static void logSetup(Level level) {

    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(level);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(level);    
  }
}
