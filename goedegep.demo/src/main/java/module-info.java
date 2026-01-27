module goedegep.demo {
  exports goedegep.demo.exe;
  
  requires transitive goedegep.jfx;
  requires goedegep.resources;
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.base;
  requires javafx.web;
  requires goedegep.util;
  requires goedegep.emfsample.model;
  requires goedegep.model.configuration;
  requires goedegep.myworld.common;
}