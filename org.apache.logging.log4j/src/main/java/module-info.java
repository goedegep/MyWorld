module org.apache.logging.log4j {
  exports org.apache.logging.log4j;
  exports org.apache.logging.log4j.util;
  
  requires java.base;
  requires java.desktop;
  requires java.logging;
  
  uses org.apache.logging.log4j.spi.Provider;
  uses org.apache.logging.log4j.util.PropertySource;
}