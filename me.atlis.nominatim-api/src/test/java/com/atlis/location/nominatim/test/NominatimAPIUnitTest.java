package com.atlis.location.nominatim.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.atlis.location.nominatim.NominatimAPI;
import com.atlis.location.nominatim.OSMAddress;
import com.atlis.location.nominatim.OSMLocationInfo;

import goedegep.util.logging.MyLoggingFormatter;
import javafx.util.Pair;

/**
 *
 * @author michaelassraf
 */
public class NominatimAPIUnitTest {

    String endpointUrl = "https://nominatim.openstreetmap.org/";

//    @Test
//    public void testAddressToMapPoint() {
//
//        List<String> cities = Arrays.asList("New York", "Berlin", "Amsterdam", "Cape Town", "Rome");
//
//        for (String city : cities) {
//            OSMRequestAddress address = new OSMRequestAddress();
//            address.setCity(city);
//            address.setPrecision(5);
//            WGS84Coordinates mapPoint = NominatimAPI.with(endpointUrl).getMapPointFromAddress(address);
//            assertNotNull("Map point object is bad.", mapPoint);
//        }
//    }

    @Test
    public void testMapPointToAddress() throws IOException {
        List<Pair<Double, Double>> points
                = Arrays.asList(new Pair<>(40.7470, -73.9860),
                        new Pair<>(25.778135, -80.179100),
                        new Pair<>(31.853431, 35.233367),
                        new Pair<>(55.751244, 37.618423),
                        new Pair<>(33.510414, 36.278336),
                        new Pair<>(35.715298, 51.404343));
        for (Pair<Double, Double> point : points) {
            OSMLocationInfo locationInfo = NominatimAPI.with(endpointUrl).getAddressFromMapPoint(point.getKey(), point.getValue());
            OSMAddress address = locationInfo.getAddress();
            assertNotNull("Address object is bad.", address);
        }
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
    @Before
    public void logSetup() {
      
      // Create Logger
      Logger logger = Logger.getLogger("");
      logger.setLevel(Level.FINE);
      
      Handler consoleHandler = null;
      for (Handler handler: logger.getHandlers()) {
        if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
          consoleHandler = handler;
          break;
        }
      }
      consoleHandler.setFormatter(new MyLoggingFormatter());
      consoleHandler.setLevel(Level.FINE);      
    }
}
