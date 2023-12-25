package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;

import javafx.scene.control.ChoiceBox;

public class EObjectTreeCellHelperForAttributeEnumeration extends EObjectTreeCellHelperForAttributeAbstract {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeMultiLineText.class.getName());
  
  private ChoiceBox<Object> valueChoiceBox = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeEnumeration(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();

    /*
     *  replace the valueLabel with a ChoiceBox.
     *  The value for the control is obtained from the item of this cell.
     */
    graphic.getChildren().remove(valueLabel);    
    valueChoiceBox = new ChoiceBox<>();
    
    // If there is a descriptor for this enum with the display names specified, fill the items with the provided names.
    // Else fill it with the enums themselves, relying on toString for the texts to display.
    EClassifier eClassifier = itemDescriptor.getEAttribute().getEType();
    final EEnum eEnum = (EEnum) eClassifier;
    EObjectTreeView eObjectTreeView = treeItem.getEObjectTreeView();
    EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
    final EEnumEditorDescriptor<?> eEnumEditorDescriptorForEEnum = eObjectTreeDescriptor.getEEnumEditorDescriptorForEEnum(eEnum);
    if (eEnumEditorDescriptorForEEnum != null) {
      valueChoiceBox.getItems().addAll(eEnumEditorDescriptorForEEnum.getDisplayNames());
    } else {
      for (Object value: eObjectTreeItemContent.getClass().getEnumConstants()) {
        valueChoiceBox.getItems().add(value.toString());
      }
    }
    
    LOGGER.severe("Going to select: " + valueLabel.getText());
    valueChoiceBox.getSelectionModel().select(valueLabel.getText());
    
    valueChoiceBox.onActionProperty().set((actionEvent) -> {
        Object value = valueChoiceBox.getValue();
        if (eEnumEditorDescriptorForEEnum != null) {
          value = eEnumEditorDescriptorForEEnum.getEEnumLiteralForDisplayName((String) value);
          LOGGER.severe("value: " + value.toString() + ", " + value.getClass().getName());
        }
        eObjectTreeCell.commitEdit(value);
    });
    
    graphic.getChildren().add(valueChoiceBox);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicBackToNormal() {
    graphic.getChildren().remove(valueChoiceBox);
    valueChoiceBox = null;
        
    graphic.getChildren().remove(valueLabel);
    graphic.getChildren().add(valueLabel);
    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void storeNewValue(Object newValue) {
    LOGGER.info("type name: " + treeItem.getEAttribute().getEType().getInstanceTypeName());
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), newValue);
  }

  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }

}
