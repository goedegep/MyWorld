module goedegep.gpx {
  exports goedegep.gpx;
  exports goedegep.gpx.parser;
  exports goedegep.gpx.parser.model;
  
  requires transitive java.xml;
  requires transitive goedegep.geo;
  requires java.logging;
  requires transitive goedegep.util;
  requires transitive goedegep.gpx.model;
}