package goedegep.jfx.eobjecttreeview;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.browser.BrowserWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class EObjectTreeCellHelperForAttributeSimple extends EObjectTreeCellHelperAbstract<EObjectTreeItemAttributeDescriptor>  {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeSimple.class.getName());
  
  private HBox graphic = null;             // will contain labelLabel plus either valueLabel (not editing) or valueTextField (editing).
  private Label labelLabel = null;         // this label is always there and always part of the graphic
  private Label valueLabel = null;         // this label is always there (as it is the normal situation), but only part of the graphic when not in editing mode.
  private TextInputControl textInputControl = null;
  private TextField valueTextField = null; // this text field is created and made part of the graphic when editing starts, and set back to null when editing ends.
  private TextArea valueTextArea = null;   // 
  private ChoiceBox<Object> valueChoiceBox = null;
  private DirectoryChooser folderChooser = null;
  private FileChooser fileChooser = null;
  private Control editControl = null;
  
  public EObjectTreeCellHelperForAttributeSimple(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }

  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)"));
    
    super.updateItem(eObjectTreeItemContent);
    
    ContextMenu contextMenu = createContextMenu();
    if (contextMenu != null) {
      eObjectTreeCell.setContextMenu(contextMenu);
    }
        
    if (graphic == null) {
      createGraphic();
    }
    
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
  private ContextMenu createContextMenu() {
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
        throw new IllegalArgumentException("Only operation 'OPEN' is possible for simple attributes");
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
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeCell.getItem();
    Object object = eObjectTreeItemContent.getObject();
        
    /*
     *  replace the valueLabel with a newly created control, which is specific for the attributes data type:
     *  - default is a TextField
     *  - for a multiLineText it is a TextArea
     *  - for an enum it is a ChoiceBox
     *  The value for the control is obtained from the item of this cell.
     */
    graphic.getChildren().remove(valueLabel);
    
    EObjectTreeItem treeItem;
    EObjectTreeItem parentItem;
    EObjectTreeItemContent parentValue;
    EObject eObject;
    
    switch(itemDescriptor.getPresentationType()) {
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
            eObjectTreeItemContent.setObject(textInputControl.getText());
            eObjectTreeCell.commitEdit(eObjectTreeItemContent);
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
            eObjectTreeItemContent.setObject(textInputControl.getText());
            eObjectTreeCell.commitEdit(eObjectTreeItemContent);
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
      EObjectTreeView eObjectTreeView = eObjectTreeItemContent.geteObjectTreeView();
      EObjectTreeDescriptor eObjectTreeDescriptor = eObjectTreeView.getEObjectTreeDescriptor();
      final EEnumEditorDescriptor<?> eEnumEditorDescriptorForEEnum = eObjectTreeDescriptor.getEEnumEditorDescriptorForEEnum(eEnum);
      treeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue.getObject();
       if (eEnumEditorDescriptorForEEnum != null) {
        valueChoiceBox.getItems().addAll(eEnumEditorDescriptorForEEnum.getDisplayNames());
      } else {
        for (Object value: object.getClass().getEnumConstants()) {
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
          eObjectTreeItemContent.setObject(value);
          eObjectTreeCell.commitEdit(eObjectTreeItemContent);
        }
        
      });
      
      editControl = valueChoiceBox;
      graphic.getChildren().add(valueChoiceBox);
      break;
    
    case FILE:
      fileChooser = new FileChooser();
      if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
        String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
        if (initialDirectoryName != null) {
          fileChooser.setInitialDirectory(new File(initialDirectoryName));
        }
      }
      File file = fileChooser.showOpenDialog(null);
      if (file != null) {
        eObjectTreeItemContent.setObject(file.getAbsolutePath());
      }
      treeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue.getObject();
      eObject.eSet(treeItem.getValue().getEStructuralFeature(), file.getAbsolutePath());
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
        eObjectTreeItemContent.setObject(folder.getAbsolutePath());
      }
      treeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
      parentItem = (EObjectTreeItem) treeItem.getParent();
      parentValue = parentItem.getValue();
      eObject = (EObject) parentValue.getObject();
      eObject.eSet(treeItem.getValue().getEStructuralFeature(), folder.getAbsolutePath());
      eObjectTreeCell.cancelEdit();
      break;
    }
    
  }
    
  @Override
  public void commitEdit(TreeItem<EObjectTreeItemContent> treeItem, EObjectTreeItemContent newValue) {
    LOGGER. info("=> newValue=" + newValue.toString());
    
    graphic.getChildren().remove(editControl);
    textInputControl = null;
    valueTextField = null;
    valueTextArea = null;
    valueChoiceBox = null;
    
    String newValueString = null;
    if (newValue.getObject() instanceof String) {
      newValueString = (String) newValue.getObject();
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
        newValue.setObject(newValueObject);
      } catch (ParseException e) {
        newValueObject = null;
        e.printStackTrace();
      }
    } else {
      LOGGER.info("type name: " + newValue.getEStructuralFeature().getEType().getInstanceTypeName());
      switch (newValue.getEStructuralFeature().getEType().getInstanceTypeName()) {
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
        
      case "boolean":
        if ((newValueString != null)  &&  !newValueString.isEmpty()) {
          newValueObject = Boolean.parseBoolean(newValueString);
        }
        break;
        
        
      default:
        newValueObject = newValue.getObject();
      }
    }
    valueLabel.setText(getValueText(newValue));
    graphic.getChildren().add(valueLabel);
    
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    EObjectTreeItemContent parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue.getObject();
    LOGGER.fine("Type=" + newValue.getEStructuralFeature().getEType().getInstanceTypeName());
    eObject.eSet(newValue.getEStructuralFeature(), newValueObject);
    
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
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeCell.getItem();
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
  
  private String getLabelText(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> structual feature=" + eObjectTreeItemContent.getEStructuralFeature().getName());
    EStructuralFeature eStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();

    String labelText = null;
    
    if (itemDescriptor.getLabelText() != null) {
      labelText = itemDescriptor.getLabelText() + ":";
    }

    if (labelText == null) {
      labelText = eStructuralFeature.getName() + ":";
    }
    
    LOGGER.info("<= labelText=" + labelText);
    return labelText;
  }
  
  private String getValueText(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=>");
    
    String itemText = null;
    Object object = eObjectTreeItemContent.getObject();
    
    if (object != null) {
      if (itemDescriptor.getFormat() != null) {
        itemText = itemDescriptor.getFormat().format(object);
      }
      itemText = object.toString();
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
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
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
