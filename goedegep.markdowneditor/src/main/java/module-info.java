module goedegep.markdown.app {
  exports goedegep.markdowneditor.exe;
  
  requires transitive goedegep.jfx;
  requires goedegep.myworld.common;
  requires goedegep.resources;
  requires org.commonmark;
  requires goedegep.model.configuration;
  requires javafx.controls;
}