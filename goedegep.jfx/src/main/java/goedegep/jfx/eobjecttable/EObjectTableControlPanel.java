package goedegep.jfx.eobjecttable;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * This class provides a panel with controls that operate on an EObjectTable.
 * <p>
 * The panel has the following layout:
 * <pre>
 * "Filter on": &lt;text-to-filter-on&gt;
 * </pre>
 */
public class EObjectTableControlPanel extends HBox {
  /**
   * Text to filter on.
   */
  private StringProperty filterTextProperty = new SimpleStringProperty();
//  private TextField filterTextField;
  
  /**
   * Constructor
   * 
   * @param componentFactory Factory to create customized GUI components.
   */
  public EObjectTableControlPanel(CustomizationFx customization) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    componentFactory.customizePane(this);
    setPadding(new Insets(12.0));
    setSpacing(12.0);
    
    Label label = componentFactory.createLabel("Filter on:");
    getChildren().add(label);
    
    TextField filterTextField = componentFactory.createTextField(300, "Enter any text to filter the entries in the table on");
    filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      filterTextProperty.set(newValue);
    });
    getChildren().add(filterTextField);
  }
  
  public StringProperty filterTextProperty() {
    return filterTextProperty;
  }
  
//  public TextField getFilterTextField() {
//    return filterTextField;
//  }
}
