package goedegep.travels.app.logic;

import java.util.List;
import java.util.logging.Logger;

import de.micromata.opengis.kml.v_2_2_0.AbstractObject;
import de.micromata.opengis.kml.v_2_2_0.AbstractView;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.NetworkLinkControl;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Region;
import de.micromata.opengis.kml.v_2_2_0.Schema;
import de.micromata.opengis.kml.v_2_2_0.SchemaData;
import de.micromata.opengis.kml.v_2_2_0.SimpleData;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import de.micromata.opengis.kml.v_2_2_0.atom.Author;
import de.micromata.opengis.kml.v_2_2_0.atom.Link;
import de.micromata.opengis.kml.v_2_2_0.xal.AddressDetails;
import goedegep.util.text.Indent;

/**
 * This class provides utility methods for KML data.
 */
public class KmlUtil {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(KmlUtil.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Private constructor as this is a utility class.
   */
  private KmlUtil() {
    
  }
  
  public static String toString(Kml kml) {
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(4);
    boolean noEmptyValues = true;
    
    buf.append(NEWLINE).append("kml:").append(NEWLINE);
    indent.increment();
    
    Feature feature = kml.getFeature();
    toString(buf, indent, feature, noEmptyValues);
    
    String hint = kml.getHint();
    buf.append(indent).append("hint: ").append(hint).append(NEWLINE);
    
    List<AbstractObject> kmlObjectExtension = kml.getKmlObjectExtension();
    buf.append(indent).append("kmlObjectExtension: ").append(NEWLINE);
    indent.increment();
    for (AbstractObject abstractObject: kmlObjectExtension) {
      toString(buf, indent, abstractObject, noEmptyValues);
    }
    indent.decrement();
    
    List<Object> kmlSimpleExtension = kml.getKmlSimpleExtension();
    buf.append(indent).append("kmlSimpleExtension: ").append(NEWLINE);
    indent.increment();
    for (Object object: kmlSimpleExtension) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    
    NetworkLinkControl networdLinkControl = kml.getNetworkLinkControl();
    toString(buf, indent, networdLinkControl, noEmptyValues);
    
    indent.decrement();
    
    return buf.toString();
  }
  
  public static void toString(StringBuilder buf, Indent indent, Feature feature, boolean noEmptyValues) {
    if (feature == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Feature: null").append(NEWLINE);
      }
      return;
    }
    
    switch (feature.getClass().getName()) {
    case "de.micromata.opengis.kml.v_2_2_0.Document":
      toString(buf, indent, (Document) feature, noEmptyValues);
      break;
      
    case "de.micromata.opengis.kml.v_2_2_0.Placemark":
      toString(buf, indent, (Placemark) feature, noEmptyValues);
      break;
      
    default:
      buf.append(indent).append("Feature not yet implemented").append(NEWLINE);
      buf.append(indent).append("feature class: ").append(feature.getClass().getName()).append(NEWLINE);
      break;
    }
  }
  
  public static void toString(StringBuilder buf, Indent indent, Document document, boolean noEmptyValues) {
    if (document == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Document: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Document:").append(NEWLINE);
    
    indent.increment();
    
    AbstractView abstractView = document.getAbstractView();
    toString(buf, indent, abstractView, noEmptyValues);
    
    if ((document.getAddress() != null)  || !noEmptyValues) {
      buf.append(indent).append("Address: ").append(document.getAddress()).append(NEWLINE);
    }
    
    toString(buf, indent, document.getAtomAuthor(), noEmptyValues);
    
    Link link = document.getAtomLink();
    toString(buf, indent, link, noEmptyValues);
    
    List<Feature> features = document.getFeature();
    buf.append(indent).append("Feature:").append(NEWLINE);
    indent.increment();
    for (Feature feature: features) {
      toString(buf, indent, feature, noEmptyValues);
    }
    indent.decrement();
    
    buf.append(indent).append("ContainerObjectExtension:").append(NEWLINE);
    indent.increment();
    for (AbstractObject abstractObject: document.getContainerObjectExtension()) {
      toString(buf, indent, abstractObject, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("ContainerSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: document.getContainerSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("Description: ").append(document.getDescription()).append(NEWLINE);
    buf.append(indent).append("DocumentObjectExtension:").append(NEWLINE);
    indent.increment();
    for (AbstractObject abstractObject: document.getDocumentObjectExtension()) {
      toString(buf, indent, abstractObject, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("DocumentSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: document.getDocumentSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    toString(buf, indent, document.getExtendedData(), noEmptyValues);
    buf.append(indent).append("ObjectExtension:").append(NEWLINE);
    indent.increment();
    for (AbstractObject abstractObject: document.getFeatureObjectExtension()) {
      toString(buf, indent, abstractObject, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("FeatureSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: document.getFeatureSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("Id: ").append(document.getId()).append(NEWLINE);
    toString(buf, indent, document.getMetadata(), noEmptyValues);
    buf.append(indent).append("Name: ").append(document.getName()).append(NEWLINE);
    buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: document.getObjectSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("PhoneNumber: ").append(document.getPhoneNumber()).append(NEWLINE);
    toString(buf, indent, document.getRegion(), noEmptyValues);
    buf.append(indent).append("Schema:").append(NEWLINE);
    indent.increment();
    for (Schema schema: document.getSchema()) {
      toString(buf, indent, schema, noEmptyValues);
    }
    indent.decrement();
    toString(buf, indent, document.getSnippet(), noEmptyValues);
    buf.append(indent).append("Snippetd: ").append(document.getSnippetd()).append(NEWLINE);
    buf.append(indent).append("StyleSelector:").append(NEWLINE);
    indent.increment();
    for (StyleSelector styleSelector: document.getStyleSelector()) {
      toString(buf, indent, styleSelector, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("StyleUrl: ").append(document.getStyleUrl()).append(NEWLINE);
    buf.append(indent).append("TargetId: ").append(document.getTargetId()).append(NEWLINE);
    toString(buf, indent, document.getTimePrimitive(), noEmptyValues);
    toString(buf, indent, document.getXalAddressDetails(), noEmptyValues);
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, Link link, boolean noEmptyValues) {
    if (link == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Link: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Link not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Author author, boolean noEmptyValues) {
    if (author == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Author: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Author not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, AbstractView abstractView, boolean noEmptyValues) {
    if (abstractView == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("AbstractView: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("AbstractView:").append(NEWLINE);
    indent.increment();
    
    buf.append(indent).append("AbstractViewObjectExtension:").append(NEWLINE);
    indent.increment();
    for (AbstractObject abstractObject: abstractView.getAbstractViewObjectExtension()) {
      toString(buf, indent, abstractObject, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("AbstractViewSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: abstractView.getAbstractViewSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("Id: ").append(abstractView.getId()).append(NEWLINE);
    buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: abstractView.getObjectSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("TargetId: ").append(abstractView.getTargetId()).append(NEWLINE);
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, AbstractObject abstractObject, boolean noEmptyValues) {
    if (abstractObject == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("AbstractObject: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("AbstractObject not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Object object, boolean noEmptyValues) {
    if (object == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Object: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Object not yet implemented").append(NEWLINE);
  }
  
  public static String geometryToString(Geometry geometry) {
    StringBuilder buf = new StringBuilder();
    toString(buf, new Indent(4), geometry, false);
    return buf.toString();
  }
  
  public static void toString(StringBuilder buf, Indent indent, Geometry geometry, boolean noEmptyValues) {
    if (geometry == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Geometry: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Geometry:").append(NEWLINE);
    indent.increment();
    
    if (geometry instanceof Point point) {
      toString(buf, indent, point, noEmptyValues);
    } else if (geometry instanceof LineString lineString) {
      toString(buf, indent, lineString, noEmptyValues);
    } else {
      throw new RuntimeException("Geometry sub type not supported: " + geometry.getClass().getName());
    }
    
    // GeometryObjectExtension
    if (!geometry.getGeometryObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometryObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: geometry.getGeometryObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // GeometrySimpleExtension
    if (!geometry.getGeometrySimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometrySimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: geometry.getGeometrySimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Id
    if ((geometry.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(geometry.getId()).append(NEWLINE);
    }
    
    // ObjectSimpleExtension
    if (!geometry.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: geometry.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((geometry.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(geometry.getTargetId()).append(NEWLINE);
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, Point point, boolean noEmptyValues) {
    if (point == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Point: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Point:").append(NEWLINE);
    indent.increment();
    
    // AltitudeMode
    toString(buf, indent, point.getAltitudeMode(), noEmptyValues);
    
    // Coordinates
    if (!point.getCoordinates().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("Coordinates:").append(NEWLINE);
      indent.increment();
      for (Coordinate coordinates: point.getCoordinates()) {
        toString(buf, indent, coordinates, noEmptyValues);
      }
      indent.decrement();
    }
    
    // GeometryObjectExtension
    if (!point.getGeometryObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometryObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: point.getGeometryObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }

    // GeometrySimpleExtension
    if (!point.getGeometrySimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometrySimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: point.getGeometrySimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Id
    if ((point.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(point.getId()).append(NEWLINE);
    }

    // ObjectSimpleExtension
    if (!point.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: point.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // PointObjectExtension
    if (!point.getPointObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("PointObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: point.getPointObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // PointSimpleExtension
    if (!point.getPointSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("PointSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: point.getPointSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((point.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(point.getTargetId()).append(NEWLINE);
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, LineString lineString, boolean noEmptyValues) {
    if (lineString == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("LineString: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("LineString:").append(NEWLINE);
    indent.increment();
    
    // AltitudeMode
    toString(buf, indent, lineString.getAltitudeMode(), noEmptyValues);
    
    // Coordinates
    if (!lineString.getCoordinates().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("Coordinates:").append(NEWLINE);
      indent.increment();
      for (Coordinate coordinates: lineString.getCoordinates()) {
        toString(buf, indent, coordinates, noEmptyValues);
      }
      indent.decrement();
    }
    
    // GeometryObjectExtension
    if (!lineString.getGeometryObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometryObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: lineString.getGeometryObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }

    // GeometrySimpleExtension
    if (!lineString.getGeometrySimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("GeometrySimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: lineString.getGeometrySimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Id
    if ((lineString.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(lineString.getId()).append(NEWLINE);
    }
    
    // LineStringObjectExtension
    if (!lineString.getLineStringObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("LineStringObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: lineString.getLineStringObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // LineStringSimpleExtension
    if (!lineString.getLineStringSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("LineStringSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: lineString.getLineStringSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }

    // ObjectSimpleExtension
    if (!lineString.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: lineString.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((lineString.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append(lineString.getTargetId()).append(NEWLINE);
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, AltitudeMode altitudeMode, boolean noEmptyValues) {
    if (altitudeMode == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("AltitudeMode: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("AltitudeMode: ").append(altitudeMode.name()).append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Coordinate coordinate, boolean noEmptyValues) {
    if (coordinate == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Coordinate: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Coordinate: ").append(coordinate.getLatitude()).append(", ").append(coordinate.getLongitude()).append(", ").append(coordinate.getAltitude()).append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, @SuppressWarnings("deprecation") de.micromata.opengis.kml.v_2_2_0.Metadata metadata, boolean noEmptyValues) {
    if (metadata == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Metadata: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Metadata not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Region region, boolean noEmptyValues) {
    if (region == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Region: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Region not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, @SuppressWarnings("deprecation") de.micromata.opengis.kml.v_2_2_0.Snippet snippet, boolean noEmptyValues) {
    if (snippet == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Snippet: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Snippet not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, StyleSelector styleSelector, boolean noEmptyValues) {
    if (styleSelector == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("StyleSelector: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("StyleSelector:").append(NEWLINE);
    indent.increment();
    
    // Id
    if ((styleSelector.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Id: ").append(styleSelector.getId()).append(NEWLINE);
    }
    
    // ObjectSimpleExtension
    if (!styleSelector.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment(); 
      for (Object object: styleSelector.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }

    // StyleSelectorObjectExtension
    if (!styleSelector.getStyleSelectorObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("StyleSelectorObjectExtension:").append(NEWLINE);
      indent.increment(); 
      for (AbstractObject abstractObject: styleSelector.getStyleSelectorObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // StyleSelectorSimpleExtension
    if (!styleSelector.getStyleSelectorSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("StyleSelectorSimpleExtension:").append(NEWLINE);
      indent.increment(); 
      for (Object object: styleSelector.getStyleSelectorSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((styleSelector.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("TargetId: ").append(styleSelector.getTargetId()).append(NEWLINE);
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, TimePrimitive timePrimitive, boolean noEmptyValues) {
    if (timePrimitive == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("TimePrimitive: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("TimePrimitive:").append(NEWLINE);
    indent.increment();
    
    if (timePrimitive instanceof TimeSpan timeSpan) {
      // Begin
      if ((timeSpan.getBegin() != null)  ||  !noEmptyValues) {
        buf.append(indent).append("Begin: ").append(timeSpan.getBegin()).append(NEWLINE);
      }
      
      // End
      if ((timeSpan.getBegin() != null)  ||  !noEmptyValues) {
        buf.append(indent).append("End: ").append(timeSpan.getEnd()).append(NEWLINE);
      }
      
      
      // TimeSpanObjectExtension
      if (!timeSpan.getTimeSpanObjectExtension().isEmpty()  ||  !noEmptyValues) {
        buf.append(indent).append("TimeSpanObjectExtension:").append(NEWLINE);
        indent.increment(); 
        for (AbstractObject abstractObject: timeSpan.getTimeSpanObjectExtension()) {
          toString(buf, indent, abstractObject, noEmptyValues);
        }
        indent.decrement();
      }
      
      // TimeSpanSimpleExtension
      if (!timeSpan.getTimeSpanSimpleExtension().isEmpty()  ||  !noEmptyValues) {
        buf.append(indent).append("TimeSpanSimpleExtension:").append(NEWLINE);
        indent.increment(); 
        for (Object object: timeSpan.getTimeSpanSimpleExtension()) {
          toString(buf, indent, object, noEmptyValues);
        }
        indent.decrement();
      }
    } else {
      throw new RuntimeException("TimePrimitive sub class not supported: " + timePrimitive.getClass().getName());
    }
    
    // Id
    if ((timePrimitive.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Id: ").append(timePrimitive.getId()).append(NEWLINE);
    }
    
    // ObjectSimpleExtension
    if (!timePrimitive.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment(); 
      for (Object object: timePrimitive.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((timePrimitive.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("TargetId: ").append(timePrimitive.getTargetId()).append(NEWLINE);
    }
    
    // TimePrimitiveObjectExtension
    if (!timePrimitive.getTimePrimitiveObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment(); 
      for (AbstractObject abstractObject: timePrimitive.getTimePrimitiveObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TimePrimitiveSimpleExtension
    if (!timePrimitive.getTimePrimitiveSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment(); 
      for (Object object: timePrimitive.getTimePrimitiveSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, AddressDetails addressDetails, boolean noEmptyValues) {
    if (addressDetails == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("AddressDetails: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("AddressDetails not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Schema schema, boolean noEmptyValues) {
    if (schema == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Schema: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Schema not yet implemented").append(NEWLINE);
  }
  
  public static void toString(StringBuilder buf, Indent indent, Placemark placemark, boolean noEmptyValues) {
    if (placemark == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Placemark: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Placemark:").append(NEWLINE);
    indent.increment();
    
    // AbstractView
    toString(buf, indent, placemark.getAbstractView(), noEmptyValues);
    
    // Address
    if ((placemark.getAddress() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Address: ").append(placemark.getAddress()).append(NEWLINE);
    }
    
    // AtomAuthor
    if ((placemark.getAtomAuthor() != null)  ||  !noEmptyValues) {
      toString(buf, indent, placemark.getAtomAuthor(), noEmptyValues);
    }
    
    // AtomLink
    toString(buf, indent, placemark.getAtomLink(), noEmptyValues);
    
    // Description
    if ((placemark.getDescription() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Description: ").append(placemark.getDescription()).append(NEWLINE);
    }
    
    // ExtendedData
    toString(buf, indent, placemark.getExtendedData(), noEmptyValues);
    
    // ObjectExtension
    if (!placemark.getFeatureObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: placemark.getFeatureObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // SimpleExtension
    if (!placemark.getFeatureSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("SimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: placemark.getFeatureSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Geometry
    toString(buf, indent, placemark.getGeometry(), noEmptyValues);
    
    // Id
    if ((placemark.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Id: ").append(placemark.getId()).append(NEWLINE);
    }
    
    // Metadata
    toString(buf, indent, placemark.getMetadata(), noEmptyValues);
    
    // Name
    if ((placemark.getName() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Name: ").append(placemark.getName()).append(NEWLINE);
    }
    
    // ObjectSimpleExtension
    if (!placemark.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: placemark.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // PhoneNumber
    if ((placemark.getPhoneNumber() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("PhoneNumber: ").append(placemark.getPhoneNumber()).append(NEWLINE);
    }
    
    // PlacemarkObjectExtension
    if (!placemark.getPlacemarkObjectExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("PlacemarkObjectExtension:").append(NEWLINE);
      indent.increment();
      for (AbstractObject abstractObject: placemark.getPlacemarkObjectExtension()) {
        toString(buf, indent, abstractObject, noEmptyValues);
      }
      indent.decrement();
    }
    
    // PlacemarkSimpleExtension
    if (!placemark.getPlacemarkSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("PlacemarkSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: placemark.getPlacemarkSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Region
    toString(buf, indent, placemark.getRegion(), noEmptyValues);
    
    // Snippet
    toString(buf, indent, placemark.getSnippet(), noEmptyValues);
    
    // Snippetd
    if ((placemark.getSnippetd() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Snippetd: ").append(placemark.getSnippetd()).append(NEWLINE);
    }
    
    // StyleSelector
    if (!placemark.getStyleSelector().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("StyleSelector:").append(NEWLINE);
      indent.increment();
      for (StyleSelector styleSelector: placemark.getStyleSelector()) {
        toString(buf, indent, styleSelector, noEmptyValues);
      }
      indent.decrement();
    }
    
    // StyleUrl
    if ((placemark.getStyleUrl() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("StyleUrl: ").append(placemark.getStyleUrl()).append(NEWLINE);
    }
    
    // TargetId
    if ((placemark.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("TargetId: ").append(placemark.getTargetId()).append(NEWLINE);
    }
    
    // TimePrimitive
    toString(buf, indent, placemark.getTimePrimitive(), noEmptyValues);
    
    // XalAddressDetails
    toString(buf, indent, placemark.getXalAddressDetails(), noEmptyValues);
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, ExtendedData extendedData, boolean noEmptyValues) {
    if (extendedData == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("ExtendedData: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("ExtendedData:").append(NEWLINE);
    indent.increment();
    
    // Any
    if (!extendedData.getAny().isEmpty()  || !noEmptyValues) {
      buf.append(indent).append("Any:").append(NEWLINE);
      indent.increment();
      for (Object object: extendedData.getAny()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // Data
    if (!extendedData.getData().isEmpty()  || !noEmptyValues) {
      buf.append(indent).append("Data:").append(NEWLINE);
      indent.increment();
      for (Data data: extendedData.getData()) {
        toString(buf, indent, data, noEmptyValues);
      }
      indent.decrement();
    }
    
    // SchemaData
    if (!extendedData.getSchemaData().isEmpty()  || !noEmptyValues) {
      buf.append(indent).append("SchemaData:").append(NEWLINE);
      indent.increment();
      for (SchemaData schemaData: extendedData.getSchemaData()) {
        toString(buf, indent, schemaData, noEmptyValues);
      }
      indent.decrement();
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, Data data, boolean noEmptyValues) {
    if (data == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("Data: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Data:").append(NEWLINE);
    indent.increment();
    
    // DataExtension
    if (!data.getDataExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("DataExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: data.getDataExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // DisplayName
    if ((data.getDisplayName() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("DisplayName: ").append(data.getDisplayName()).append(NEWLINE);
    }
  
    // Id
    if ((data.getId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Id: ").append(data.getId()).append(NEWLINE);
    }
    
    // Name
    if ((data.getName() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Name: ").append(data.getName()).append(NEWLINE);
    }
    
    // ObjectSimpleExtension
    if (!data.getObjectSimpleExtension().isEmpty()  ||  !noEmptyValues) {
      buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
      indent.increment();
      for (Object object: data.getObjectSimpleExtension()) {
        toString(buf, indent, object, noEmptyValues);
      }
      indent.decrement();
    }
    
    // TargetId
    if ((data.getTargetId() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("TargetId: ").append(data.getTargetId()).append(NEWLINE);
    }

    // Value
    if ((data.getValue() != null)  ||  !noEmptyValues) {
      buf.append(indent).append("Value: ").append(data.getValue()).append(NEWLINE);
    }
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, SchemaData schemaData, boolean noEmptyValues) {
    if (schemaData == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("SchemaData: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("Id: ").append(schemaData.getId()).append(NEWLINE);
    buf.append(indent).append("ObjectSimpleExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: schemaData.getObjectSimpleExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("SchemaDataExtension:").append(NEWLINE);
    indent.increment();
    for (Object object: schemaData.getSchemaDataExtension()) {
      toString(buf, indent, object, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("SchemaUrl: ").append(schemaData.getSchemaUrl()).append(NEWLINE);
    buf.append(indent).append("SchemaDataExtension:").append(NEWLINE);
    indent.increment();
    for (SimpleData simpleData: schemaData.getSimpleData()) {
      toString(buf, indent, simpleData, noEmptyValues);
    }
    indent.decrement();
    buf.append(indent).append("TargetId: ").append(schemaData.getTargetId()).append(NEWLINE);
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, SimpleData simpleData, boolean noEmptyValues) {
    if (simpleData == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("SimpleData: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append("SimpleData:").append(NEWLINE);
    indent.increment();
    
    simpleData.getName();
    simpleData.getValue();

    buf.append(indent).append("Name: ").append(simpleData.getName()).append(NEWLINE);
    buf.append(indent).append("Value:").append(simpleData.getValue()).append(NEWLINE);
    
    indent.decrement();
  }
  
  public static void toString(StringBuilder buf, Indent indent, NetworkLinkControl networdLinkControl, boolean noEmptyValues) {
    if (networdLinkControl == null) {
      if (!noEmptyValues) {
        buf.append(indent).append("NetworkLinkControl: null").append(NEWLINE);
      }
      return;
    }
    
    buf.append(indent).append("NetworkLinkControl not yet implemented").append(NEWLINE);
    buf.append("NetworkLinkControl:").append(NEWLINE);
  }
}
