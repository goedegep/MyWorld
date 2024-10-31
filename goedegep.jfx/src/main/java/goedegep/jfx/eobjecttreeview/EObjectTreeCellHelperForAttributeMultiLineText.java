package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class EObjectTreeCellHelperForAttributeMultiLineText extends EObjectTreeCellHelperForAttributeAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeMultiLineText.class.getName());
  
  private TextArea valueTextArea = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeMultiLineText(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();

    // replace the valueLabel with a TextArea, which is initialized with the value of the tree item.
    graphic.getChildren().remove(valueLabel);

    valueTextArea = new TextArea();
    valueTextArea.setMaxWidth(400);
    valueTextArea.setWrapText(true);
    valueTextArea.setText(buildText(eObjectTreeItemContent));
    valueTextArea.setOnKeyReleased((keyEvent) -> {
        if ((keyEvent.getCode() == KeyCode.ENTER)  &&  
            keyEvent.isControlDown()) {
          eObjectTreeCell.commitEdit(valueTextArea.getText());
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
          eObjectTreeCell.cancelEdit();
        }
    });
    graphic.getChildren().add(valueTextArea);
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicBackToNormal() {
    graphic.getChildren().remove(valueTextArea);
    valueTextArea = null;
        
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

    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), newValueString);
  }

  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }
}
