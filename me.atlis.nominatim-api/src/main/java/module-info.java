module com.atlis.location.nominatim {
  exports com.atlis.location.nominatim;
  
  opens com.atlis.location.nominatim;
  
  requires transitive com.google.gson;
  requires goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires org.apache.commons.lang3;
  requires transitive de.micromata.opengis.kml.v220;
  requires junit;
}