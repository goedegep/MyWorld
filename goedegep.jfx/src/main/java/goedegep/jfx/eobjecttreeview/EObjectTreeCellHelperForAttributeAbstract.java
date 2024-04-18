package goedegep.jfx.eobjecttreeview;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;

import goedegep.appgen.Operation;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.browser.BrowserWindow;
import goedegep.util.url.UrlUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

/**
 * This class is the common part for all tree cell helpers for a simple attribute.
 * <p>
 * The graphic for these helpers is an {@code HBox} with a label {@code Label} followed by a control for the value.
 * The control for the value is also a {@code Label}, except for {@code EObjectTreeCellHelperForAttributeBoolean} where it is a {@code CheckBox}.
 */
public abstract class EObjectTreeCellHelperForAttributeAbstract extends EObjectTreeCellHelperTemplate<EObjectTreeItemForAttributeSimple, EObjectTreeItemAttributeDescriptor, HBox> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeAbstract.class.getName());

  /**
   * The {@code Label} showing the attribute name.
   */
  protected Label labelLabel = null;
  
  /**
   * The {@code Label} showing the value
   */
  protected Label valueLabel = null;

  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeAbstract(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  /**
   * {@inheritDoc}
   * The graphic is an {@code HBox} with a label {@code Label} and a value {@code Label}.<br/>
   * In edit mode the value {@code Label} is replaced by a {@code ChoiceBox}.
   */
  @Override
  protected void createGraphic() {
    LOGGER.info("=>");
    
    createGraphicBasePart();
    
    valueLabel = new Label();
    valueLabel.setMaxWidth(400);
    graphic.getChildren().add(valueLabel);
    
    LOGGER.info("<=");
  }

  /**
   * Create the base part of the graphic, which is an @code HBox} with a label {@code Label}.
   */
  protected void createGraphicBasePart() {
    graphic = new HBox(5);
    labelLabel = new Label();
    labelLabel.setMinWidth(150d);
    graphic.getChildren().add(labelLabel);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void setEObjectTreeItemDescriptor() {
    itemDescriptor = treeItem.getEObjectTreeItemAttributeDescriptor();
  }
  
  /**
   * Create a context menu for this cell.
   * 
   * @return a context menu derived from the node operation descriptors, or null if no node operation descriptors are specified.
   */
  protected ContextMenu createContextMenu(Object object) {
    LOGGER.info("=>");
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      LOGGER.info("<= null");
      return null;
    }
    
    ContextMenu contextMenu = new ContextMenu();
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      LOGGER.info("Handling operation: " + nodeOperationDescriptor.getOperation().name());
      
      if (nodeOperationDescriptor instanceof NodeOperationDescriptorCustom extendedNodeOperationDescriptor) {
        LOGGER.severe("Extended operation: " + extendedNodeOperationDescriptor.getMenuText());
        // TODO handle extended operations.
      } else {
        if (!nodeOperationDescriptor.getOperation().equals(Operation.OPEN)) {
          throw new IllegalArgumentException("Only operation 'OPEN' is possible for simple attributes, operation is: " + nodeOperationDescriptor.getOperation() + ", menu text is: " + nodeOperationDescriptor.getMenuText());
        }

        MenuItem menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent t) {
            openObject();
          }
        });
        contextMenu.getItems().add(menuItem);
      }
    }
    
    LOGGER.info("<= contextMenu");
    return contextMenu;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateContent(Object object) {
    updateLabelLabel();
    
    if (eObjectTreeCell.isEditing()) {
      throw new RuntimeException("Update while editing not supported.");
    } else {
      valueLabel.setText(buildText(object));
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Update the label {@code Label}.
   * 
   * @param object the Object to derive the text from.
   */
  protected void updateLabelLabel() {
    labelLabel.setText(getLabelText());
  }
  
  /**
   * Get the text for the label {@Label}.
   * <p>
   * If the descriptor specifies a label text, this is used.
   * Else the name of the {@code EAttribute} in the tree item is used.
   * 
   * @return the text for the label {@Label}.
   */
  protected String getLabelText() {
    EAttribute eAttribute = treeItem.getEAttribute();

    String labelText = null;
    
    if (itemDescriptor.getLabelText() != null) {
      labelText = itemDescriptor.getLabelText() + ":";
    }

    if (labelText == null) {
      labelText = eAttribute.getName() + ":";
    }
    
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String buildText(Object value) {
    LOGGER.info("=>");
    
    String itemText = null;
    
    if (value != null) {
      itemText = value.toString();
    }
    
    LOGGER.info("<= itemText=" + itemText);
    return itemText;
  }
  
  /**
   * Open the object given by the value of the treeItem.
   */
  protected void openObject() {
    LOGGER.info("=>");
    
    Object object = treeItem.getValue();
    if (object == null) {
      return;
    }
    
    if (object instanceof String documentReference) {
      if (UrlUtil.isURL(documentReference)) {
        LOGGER.severe("Going to open browser for URL: " + documentReference);
        new BrowserWindow("Browser", DefaultCustomizationFx.getInstance(), documentReference);
      } else {
        File file = new File(documentReference);
        try {
          Desktop.getDesktop().open(file);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
    LOGGER.info("<=");
  }
}
