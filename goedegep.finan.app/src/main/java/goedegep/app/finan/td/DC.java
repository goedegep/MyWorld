package goedegep.app.finan.td;

import java.awt.Component;

public abstract class DC<T extends Component> {
  private CD        componentDescriptor;
  private T         component;
  
  public DC(CD componentDescriptor) {
    this.componentDescriptor = componentDescriptor;
  }
  
  public CD getComponentDescriptor() {
    return componentDescriptor;
  }

  public void setComponentDescriptor(CD componentDescriptor) {
    this.componentDescriptor = componentDescriptor;
  }

  public T getComponent() {
    return component;
  }
  
  public String getName() {
    return componentDescriptor.getName();
  }
  
  public void setComponent(T component) {
    this.component = component;
  }

  public abstract boolean isValid();
  
  public abstract boolean isFilledIn();
  
  public abstract void setStatusHighlight();
  
  public abstract Object getValue();
  
  /**
   * Method which is called when the component, on which this component depends,
   * loses focus (which means it may have changed).
   */
  public void update(TransactionEntryStatus transactionEntryStatus) {
  }
}
