module goedegep.demo {
  exports goedegep.demo;
  exports goedegep.demo.guifx;
  
  requires transitive goedegep.appgenfx;
  requires goedegep.resources;
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.base;
  requires javafx.web;
  requires goedegep.util;
  requires goedegep.emfsample.model;
  requires goedegep.model.configuration;
}