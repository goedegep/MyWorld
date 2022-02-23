package goedegep.vacations.app.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.poi.model.POICategoryId;
import goedegep.util.html.HtmlUtil;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsPackage;

public class OsmAndUtil {
  private static final Logger LOGGER = Logger.getLogger(OsmAndUtil.class.getName());
  static final String NEW_LINE = System.getProperty("line.separator");
  
  private static Map<POICategoryId, String> colorMap = new HashMap<>();
  
  static {
    colorMap.put(POICategoryId.HOTEL, "#ff0000");
    colorMap.put(POICategoryId.SCENIC_VIEWPOINT, "#0000ff");
    colorMap.put(POICategoryId.MONUMENT, "#00ff00");
  }

  public static void createFavouritesFiles(EObject startEObject, File file) {
    LOGGER.severe("=> startEObject=" + startEObject.toString() + ", file=" + file.getAbsolutePath());
    
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(2);
    
    // write header part.
    buf.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>").append(NEW_LINE);
    buf.append("<gpx version=\"1.1\" xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">").append(NEW_LINE);
    buf.append("  <metadata>").append(NEW_LINE);
    buf.append("    <name>favourites</name>").append(NEW_LINE);
    buf.append("  </metadata>").append(NEW_LINE);
    
    // write the locations
    TreeIterator<EObject> vacationIterator = startEObject.eAllContents();
    indent.increment();
    
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      switch(eObject.eClass().getClassifierID()) {
        
      // Only locations are written as waypoints
      case VacationsPackage.LOCATION:
        Location location = (Location) eObject;
        if (location.isSetLatitude()  &&  location.isSetLongitude()) {
          String[] attributeDefinitions = new String[2];
          attributeDefinitions[0] = "lat=\"" + location.getLatitude() + "\"";
          attributeDefinitions[1] = "lon=\"" + location.getLongitude() + "\"";
          buf.append(SgmlUtil.createElementOpen(indent, null, "wpt", attributeDefinitions)).append(NEW_LINE);
          indent.increment();
          
          if (location.isSetName()) {
            buf.append(SgmlUtil.createElement(indent, null, "name", HtmlUtil.encodeHTML(location.getName()))).append(NEW_LINE);
          } else if (location.getLocationType() == POICategoryId.CITY  &&  location.isSetCity()) {
            buf.append(SgmlUtil.createElement(indent, null, "name", HtmlUtil.encodeHTML(location.getCity()))).append(NEW_LINE);
          }
          if (location.isSetDescription()) {
            buf.append(SgmlUtil.createElement(indent, null, "desc", HtmlUtil.encodeHTML(location.getDescription()))).append(NEW_LINE);
          }
          if (location.isSetLocationType()) {
            buf.append(SgmlUtil.createElement(indent, null, "category", HtmlUtil.encodeHTML(location.getLocationType().getName()))).append(NEW_LINE);
          }
          String colorString = colorMap.get(location.getLocationType());
          if (colorString != null) {
            buf.append(SgmlUtil.createElementOpen(indent, null, "extensions")).append(NEW_LINE);
            indent.increment();
            
            buf.append(SgmlUtil.createElement(indent, null, "color", colorString)).append(NEW_LINE);

            indent.decrement();
            buf.append(SgmlUtil.createElementClose(indent, null, "extensions")).append(NEW_LINE);
          }
          
          indent.decrement();
          buf.append(SgmlUtil.createElementClose(indent, null, "wpt")).append(NEW_LINE);
          
        }
        break;
        
      }
      
    }
    indent.decrement();
    
    // write the footer
    buf.append("</gpx>").append(NEW_LINE);
    
    try {
      Files.write(Paths.get(file.getAbsolutePath()), buf.toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }    
    LOGGER.severe("<= " + buf.toString());
  }

}
