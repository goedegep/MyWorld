module com.gluonhq.charm.down {
  exports com.gluonhq.charm.down;
//  exports com.gluonhq.charm.down.plugins.storage;
  exports com.gluonhq.impl.charm.down.plugins;
//  exports com.gluonhq.charm.down.plugins.position;
  exports com.gluonhq.charm.down.plugins;
  exports com.gluonhq.charm.down.plugins.desktop;

  requires java.logging;
  requires javafx.base;
  requires javafx.graphics;
}