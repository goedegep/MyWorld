package goedegep.travels.gui;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LevelSelectionPanel extends HBox {
  private IntegerProperty levelProperty = new SimpleIntegerProperty();  // value range 1 .. 3
  private Button upButton;
  private Button downButton;
  private TextField levelTextField;
  
  public LevelSelectionPanel(CustomizationFx customization) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    componentFactory.customizePane(this);
    this.setSpacing(12.0);
    this.setPadding(new Insets(0.0, 15.0, 0.0, 0.0));
    
    Label label = componentFactory.createLabel("Show level:");
    downButton = componentFactory.createButton("-", "Click to decrease the show level, to show less at once");
    downButton.setOnAction((e) -> decreaseLevel());
    levelTextField = componentFactory.createTextField(20, "Current show level");
    upButton = componentFactory.createButton("+", "Click to increase the show level, to show more at once");
    upButton.setOnAction((e) -> increaseLevel());
    
    getChildren().addAll(label, downButton, levelTextField, upButton);
    
    ChangeListener<Number> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        int level = newValue.intValue();
        downButton.setDisable(level == 1);
        upButton.setDisable(level == 3);
        levelTextField.setText(String.valueOf(level));
      }
    };
    levelProperty.addListener(cl);
    
    levelProperty.set(3);
  }
  
  public IntegerProperty levelProperty() {
    return levelProperty;
  }
  
  private void decreaseLevel() {
    if (levelProperty.intValue() > 1) {
      levelProperty.set(levelProperty.intValue() - 1);
//      enableOrDisableButtons();
    }
  }
  
  private void increaseLevel() {
    if (levelProperty.intValue() < 3) {
      levelProperty.set(levelProperty.intValue() + 1);
//      enableOrDisableButtons();
    }
  }
  
//  private void enableOrDisableButtons() {
//    downButton.setDisable(levelProperty.get() == 1);
//    upButton.setDisable(levelProperty.get() == 3);
//  }
  
}
