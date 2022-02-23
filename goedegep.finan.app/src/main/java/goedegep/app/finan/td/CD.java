package goedegep.app.finan.td;

import java.awt.Component;

import goedegep.appgen.swing.Customization;

public abstract class CD {
  String   name;
  boolean  isActive;        // Active means editable, requires an action listener.
  String   dependsOnName;   // Name of another component on which this one depends.
  boolean  optional;        // An optional component doesn't have to be filled in.
  
  public CD(String name, boolean isActive) {
    this(name, isActive, null, false);
  }

  public CD(String name, boolean isActive, boolean optional) {
    this(name, isActive, null, optional);
  }

  public CD(String name, boolean isActive, String dependsOnName) {
    this(name, isActive, dependsOnName, false);
  }

  public CD(String name, boolean isActive, String dependsOnName, boolean optional) {
    this.name = name;
    this.isActive = isActive;
    this.dependsOnName = dependsOnName;
    this.optional = optional;
  }

  public String getName() {
    return name;
  }

  public boolean isActive() {
    return isActive;
  }

  public boolean isOptional() {
    return optional;
  }

  public String getDependsOnName() {
    return dependsOnName;
  }

  public abstract DC<? extends Component> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization);
}
