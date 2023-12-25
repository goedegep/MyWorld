package goedegep.jfx.eobjecttreeview;

import java.text.ParseException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class is a tree cell helper for a simple attribute.
 */
public class EObjectTreeCellHelperForAttributeSimple extends EObjectTreeCellHelperForAttributeAbstract {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeSimple.class.getName());
  
  private TextField valueTextField = null; // this text field is created and made part of the graphic when editing starts, and set back to null when editing ends.
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeSimple(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String buildText(Object value) {
    LOGGER.info("=>");
    
    String itemText = null;
    
    if (value != null) {
      if (itemDescriptor.getFormat() != null) {
        itemText = itemDescriptor.getFormat().format(value);
      } else {
        itemText = value.toString();
      }
    }
    
    LOGGER.info("<= itemText=" + itemText);
    return itemText;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();
        
    //  replace the valueLabel with a TextField
    graphic.getChildren().remove(valueLabel);
    
    valueTextField = new TextField();
    valueTextField.setText(buildText(eObjectTreeItemContent));
    valueTextField.setOnKeyReleased((keyEvent) -> {
        if ((keyEvent.getCode() == KeyCode.ENTER)  &&  
            ((valueTextField != null)  ||  keyEvent.isControlDown())) {
          eObjectTreeCell.commitEdit(valueTextField.getText());
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
          eObjectTreeCell.cancelEdit();
        }
    });
    graphic.getChildren().add(valueTextField);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicBackToNormal() {
    graphic.getChildren().remove(valueTextField);
    valueTextField = null;

    graphic.getChildren().remove(valueLabel);
    graphic.getChildren().add(valueLabel);
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void storeNewValue(Object newValue) {
        
    String newValueString = null;
    if (newValue instanceof String) {
      newValueString = (String) newValue;
      if (newValueString != null) {
        newValueString = newValueString.trim();
      }
    }
    
    Object newValueObject = null;
    if (itemDescriptor.getFormat() != null) {
      try {
        if ((newValueString != null)  &&  !newValueString.isEmpty()) {
          newValueObject = itemDescriptor.getFormat().parseObject(newValueString);
        }
        treeItem.setValue(newValueObject);
      } catch (ParseException e) {
        newValueObject = null;
        e.printStackTrace();
      }
    } else {
      LOGGER.info("type name: " + treeItem.getEAttribute().getEType().getInstanceTypeName());
      switch (treeItem.getEAttribute().getEType().getInstanceTypeName()) {
      case "java.lang.Double":
        if ((newValueString != null)  &&  !newValueString.isEmpty()) {
          newValueObject = Double.parseDouble(newValueString);
        }
        break;
        
      case "java.lang.Integer":
        if ((newValueString != null)  &&  !newValueString.isEmpty()) {
          newValueObject = Integer.parseInt(newValueString);
        }
        break;
        
        
      default:
        newValueObject = newValue;
      }
    }
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), newValueObject);
  }
      
  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }
}
