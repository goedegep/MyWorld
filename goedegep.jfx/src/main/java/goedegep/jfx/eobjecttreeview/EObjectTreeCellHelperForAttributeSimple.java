package goedegep.jfx.eobjecttreeview;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.browser.BrowserWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * This class is a tree cell helper for a simple attribute.
 */
public class EObjectTreeCellHelperForAttributeSimple extends EObjectTreeCellHelperAbstract<EObjectTreeItemForAttributeSimple>  {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeSimple.class.getName());
  
  protected EObjectTreeItemAttributeDescriptor itemDescriptor;
  
  protected HBox graphic = null;             // will contain labelLabel plus either valueLabel (not editing) or valueTextField (editing).
  protected Label labelLabel = null;         // this label is always there and always part of the graphic
  private Label valueLabel = null;         // this label is always there, except for BOOLEAN (as it is the normal situation), but only part of the graphic when not in editing mode.
  private TextInputControl textInputControl = null;
  private TextField valueTextField = null; // this text field is created and made part of the graphic when editing starts, and set back to null when editing ends.
  private TextArea valueTextArea = null;   // 
  private ChoiceBox<Object> valueChoiceBox = null;
  private DirectoryChooser folderChooser = null;
  private FileChooser fileChooser = null;
  private Control editControl = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeSimple(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(Object eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    itemDescriptor = treeItem.getEObjectTreeItemAttributeDescriptor();
    
    ContextMenu contextMenu = createContextMenu();
    if (contextMenu != null) {
      eObjectTreeCell.setContextMenu(contextMenu);
    }
        
    createGraphic();
    
    eObjectTreeCell.setText(null);
    eObjectTreeCell.setGraphic(graphic);
    labelLabel.setText(getLabelText(eObjectTreeItemContent));
    
    if (eObjectTreeCell.isEditing()) {
      if (textInputControl != null) {
        textInputControl.setText(getValueText(eObjectTreeItemContent));
      }
    } else {
      valueLabel.setText(getValueText(eObjectTreeItemContent));
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Create a context menu for this cell.
   * 
   * @return a context menu derived from the node operation descriptors, or null if no node operation descriptors are specified.
   */
  protected ContextMenu createContextMenu() {
    LOGGER.info("=>");
    
    List<NodeOperationDescriptor> nodeOperationDescriptors = itemDescriptor.getNodeOperationDescriptors();
    if (nodeOperationDescriptors == null) {
      LOGGER.info("<= null");
      return null;
    }
    
    ContextMenu contextMenu = new ContextMenu();
    for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
      LOGGER.info("Handling operation: " + nodeOperationDescriptor.getOperation().name());
      
      if (!nodeOperationDescriptor.getOperation().equals(TableRowOperation.OPEN)) {
//        throw new IllegalArgumentException("Only operation 'OPEN' is possible for simple attributes");
      }
      
      MenuItem menuItem = new MenuItem(nodeOperationDescriptor.getMenuText());
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
          openObject();
        }
      });
      contextMenu.getItems().add(menuItem);
    }
    
    LOGGER.info("<= contextMenu");
    return contextMenu;
  }
  
  @Override
  public void startEdit(EObjectTreeCell eObjectTreeCell) {
    LOGGER.info("=>");
    
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();
        
    /*
     *  replace the valueLabel with a newly created control, which is specific for the attributes data type:
     *  - default is a TextField
     *  - for a multiLineText it is a TextArea
     *  - for an enum it is a ChoiceBox
     *  The value for the control is obtained from the item of this cell.
     */
    graphic.getChildren().remove(valueLabel);
    
    EObjectTreeItem parentItem;
    Object parentValue;
    EObject eObject;
    
    switch(itemDescriptor.getPresentationType()) {
    case BOOLEAN:
      // no action
      break;
      
    case SINGLE_LINE_TEXT:
    case FORMAT:
      valueTextField = new TextField();
      textInputControl = valueTextField;
      textInputControl.setText(getValueText(eObjectTreeItemContent));
      textInputControl.setOnKeyReleased(new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
          if ((keyEvent.getCode() == KeyCode.ENTER)  &&  
              ((valueTextField != null)  ||  keyEvent.isControlDown())) {
//            treeItem.setValue(textInputControl.getText());
//            eObjectTreeCell.commitEdit(eObjectTreeItemContent);
            eObjectTreeCell.commitEdit(textInputControl.getText());
          } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            eObjectTreeCell.cancelEdit();
          }
        }
      });
      graphic.getChildren().add(textInputControl);
      editControl = textInputControl;
      break;
      
    case MULTI_LINE_TEXT:
      valueTextArea = new TextArea();
      valueTextArea.setMaxWidth(400);
      valueTextArea.setWrapText(true);
      textInputControl = valueTextArea;
      textInputControl.setText(getValueText(eObjectTreeItemContent));
      textInputControl.setOnKeyReleased(new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
          if ((keyEvent.getCode() == KeyCode.ENTER)  &&  
              ((valueTextField != null)  ||  keyEvent.isControlDown())) {
//            treeItem.setValue(textInputControl.getText());
            eObjectTreeCell.commitEdit(textInputControl.getText());
          } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            eObjectTreeCell.cancelEdit();
          }
        }
      });
      graphic.getChildren().add(textInputControl);
      editControl = textInputControl;
      break;
      
    case ENUMERATION:
      // Use a ChoiceBox for enums
      valueChoiceBox = new ChoiceBox<>();
      
      // If there is a descriptor for this enum with the display names specified, fill the items with the provided names.
      // Else fill it with the enums themselves, relying on toString for the texts to display.
      EClassifier eClassifier = itemDescriptor.getEAttribute().getEType();
      LOGGER.info("eClassifier: " + eClassifier.getName());
      final EEnum eEnum = (EEnum) eClassifier;
      EObjectTreeView eObjectTreeView = ((EObjectTreeItem) eObjectTreeCell.getTreeItem()).getEObjectTreeView();
      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      final EEnumEditorDescriptor<?> eEnumEditorDescriptorForEEnum = eObjectTreeDescriptor.getEEnumEditorDescriptorForEEnum(eEnum);
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue;
       if (eEnumEditorDescriptorForEEnum != null) {
        valueChoiceBox.getItems().addAll(eEnumEditorDescriptorForEEnum.getDisplayNames());
      } else {
        for (Object value: eObjectTreeItemContent.getClass().getEnumConstants()) {
          valueChoiceBox.getItems().add(value);
        }
      }
      
      valueChoiceBox.onActionProperty().set(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          Object value = valueChoiceBox.getValue();
          if (eEnumEditorDescriptorForEEnum != null) {
            value = eEnumEditorDescriptorForEEnum.getEEnumLiteralForDisplayName((String) value);
            LOGGER.severe("value: " + value.toString() + ", " + value.getClass().getName());
          }
          treeItem.setValue(value);
          eObjectTreeCell.commitEdit(eObjectTreeItemContent);
        }
        
      });
      
      editControl = valueChoiceBox;
      graphic.getChildren().add(valueChoiceBox);
      break;
    
    case FILE:
      fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(null);
      if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
        String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
        if (initialDirectoryName != null) {
          fileChooser.setInitialDirectory(new File(initialDirectoryName));
        }
      }
      File file = fileChooser.showOpenDialog(null);
      if (file != null) {
        treeItem.setValue(file.getAbsolutePath());
      }
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue;
      eObject.eSet(treeItem.getEAttribute(), file.getAbsolutePath());
      eObjectTreeCell.cancelEdit();
      break;
      
    case FOLDER:
      folderChooser = new DirectoryChooser();
      if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
        String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
        if (initialDirectoryName != null) {
          folderChooser.setInitialDirectory(new File(initialDirectoryName));
        }
      }
      File folder = folderChooser.showDialog(null);
      if (folder != null) {
        treeItem.setValue(folder.getAbsolutePath());
      }
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue;
      eObject.eSet(treeItem.getEAttribute(), folder.getAbsolutePath());
      eObjectTreeCell.cancelEdit();
      break;
    }
    
  }
    
  @Override
  public void commitEdit(TreeItem<Object> eObjectTreeItem, Object newValue) {
    LOGGER. info("=> newValue=" + (newValue != null ? newValue.toString() : "<null>"));
    if (!(eObjectTreeItem == treeItem)) {
      throw new RuntimeException("treeItem has changed");
    }
    
    ((EObjectTreeItem) eObjectTreeItem).getEObjectTreeView().ignoreNotification = true;
    
    graphic.getChildren().remove(editControl);
    textInputControl = null;
    valueTextField = null;
    valueTextArea = null;
    valueChoiceBox = null;
        
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
    
    valueLabel.setText(getValueText(newValue));
    graphic.getChildren().remove(valueLabel);
    graphic.getChildren().add(valueLabel);
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), newValueObject);

    ((EObjectTreeItem) eObjectTreeItem).getEObjectTreeView().ignoreNotification = false;
    
    LOGGER.info("<=");
  }

  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    graphic.getChildren().remove(editControl);
    textInputControl = null;
    valueTextField = null;
    valueTextArea = null;
    valueChoiceBox = null;
    
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();
    valueLabel.setText(getValueText(eObjectTreeItemContent));
    graphic.getChildren().remove(valueLabel);
    graphic.getChildren().add(valueLabel);

    LOGGER.info("<=");
  }

  private void createGraphic() {
    LOGGER.info("=>");
    
    graphic = new HBox(5);
    labelLabel = new Label();
    labelLabel.setMinWidth(150d);
    graphic.getChildren().add(labelLabel);
    
    valueLabel = new Label();
    valueLabel.setMaxWidth(400);
    graphic.getChildren().add(valueLabel);
    
    LOGGER.info("<=");
  }
  
  protected String getLabelText(Object eObjectTreeItemContent) {
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
  
  private String getValueText(Object value) {
    LOGGER.info("=>");
    
    String itemText = null;
    
    if (value != null) {
      if (itemDescriptor.getFormat() != null) {
        itemText = itemDescriptor.getFormat().format(value);
      } else {
        itemText = value.toString();
      }
    } else {
      itemText = "(null)";
    }
    
    LOGGER.info("<= itemText=" + itemText);
    return itemText;
  }
      
  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }
  
  private void openObject() {
    LOGGER.info("=>");
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    Object object = eObjectTreeItem.getValue();
    if (object instanceof String) {
      String documentReference = (String) object;
      if (isURL(documentReference)) {
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
  
  private boolean isURL(String text) {
    if (text.startsWith("http:")  ||  text.startsWith("https:")) {
      return true;
    } else {
      return false;
    }
  }
}
