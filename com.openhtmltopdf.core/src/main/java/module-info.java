module com.openhtmltopdf {
  exports com.openhtmltopdf.bidi;
  exports com.openhtmltopdf.context;
  exports com.openhtmltopdf.css.constants;
  exports com.openhtmltopdf.css.parser;
  exports com.openhtmltopdf.css.sheet;
  exports com.openhtmltopdf.css.style;
  exports com.openhtmltopdf.css.style.derived;
  exports com.openhtmltopdf.css.value;
  exports com.openhtmltopdf.extend;
  exports com.openhtmltopdf.extend.impl;
  exports com.openhtmltopdf.layout;
  exports com.openhtmltopdf.newtable;
  exports com.openhtmltopdf.outputdevice.helper;
  exports com.openhtmltopdf.render;
  exports com.openhtmltopdf.render.displaylist;
  exports com.openhtmltopdf.resource;
  exports com.openhtmltopdf.simple.extend;
  exports com.openhtmltopdf.swing;
  exports com.openhtmltopdf.util;
  
  requires java.xml;
  requires java.logging;
  requires java.desktop;
}