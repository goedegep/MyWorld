package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import goedegep.util.emf.EmfUtil;
import javafx.scene.control.CheckBox;

/**
 * This class is an {@link EObjectTreeCellHelper} for simple attribute nodes of type boolean (EBoolean).
 * <p>
 * Note: For editing the class doesn't switch to edit mode, clicking on the check box directly changes the value.
 *
 */
public class EObjectTreeCellHelperForAttributeBoolean extends EObjectTreeCellHelperForAttributeAbstract {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeBoolean.class.getName());
  
  private CheckBox checkBox = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeBoolean(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  /**
   * {@inheritDoc}
   * For this type the value is represented by a {@code CheckBox}.
   */
  @Override
  protected void createGraphic() {
    createGraphicBasePart();
        
    checkBox = new CheckBox();
    checkBox.setOnAction((e) -> storeNewValue(checkBox.isSelected()));
    graphic.getChildren().add(checkBox);
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void storeNewValue(Object newValue) {
    EObjectTreeItem parentItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem().getParent();
    EObject parentValue = (EObject) parentItem.getValue();
    EmfUtil.setFeatureValue(parentValue, treeItem.getEAttribute(), newValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateContent(Object object) {
    updateLabelLabel();
    
    if (eObjectTreeCell.isEditing()) {
      throw new RuntimeException("Call to updateItem while editing");
    } else {
      checkBox.setSelected(object != null  &&  (boolean) object);
    }
    
    LOGGER.info("<=");
  }


  @Override
  public String getText() {
    return labelLabel.getText();
  }

}
