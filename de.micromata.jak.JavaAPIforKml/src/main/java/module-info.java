module de.micromata.opengis.kml.v220 {
  exports de.micromata.opengis.kml.v_2_2_0.xal;
  exports de.micromata.opengis.kml.v_2_2_0.atom;
  exports de.micromata.opengis.kml.v_2_2_0.gx;
  exports de.micromata.opengis.kml.v_2_2_0;
  exports de.micromata.opengis.kml.v_2_2_0.annotations;
  
  opens de.micromata.opengis.kml.v_2_2_0;
  opens de.micromata.opengis.kml.v_2_2_0.gx;
  opens de.micromata.opengis.kml.v_2_2_0.atom;
  opens de.micromata.opengis.kml.v_2_2_0.xal;

  requires com.sun.istack.runtime;
  requires jakarta.xml.bind;
  requires java.logging;
  requires java.xml;
  requires org.glassfish.jaxb.runtime;
//  requires junit;
//  requires log4j;
}