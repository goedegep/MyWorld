module org.eclipse.equinox.registry {
  exports org.eclipse.core.internal.adapter;
  exports org.eclipse.core.internal.registry;
  exports org.eclipse.core.internal.registry.osgi;
  exports org.eclipse.core.internal.registry.spi;
  exports org.eclipse.core.runtime;
  exports org.eclipse.core.runtime.dynamichelpers;
  exports org.eclipse.core.runtime.spi;
  requires java.xml;
  
}