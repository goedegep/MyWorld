package goedegep.jfx.eobjecttreeview;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.stage.FileChooser;

public class EObjectTreeCellHelperForAttributeFile extends EObjectTreeCellHelperForAttributeAbstract {
//  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeMultiLineText.class.getName());
  
  private FileChooser fileChooser = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeFile(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    fileChooser = new FileChooser();
    
    if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
      LOGGER.severe("InitialDirectoryNameFunction specified");
      String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
      LOGGER.severe("initialDirectoryName = " + initialDirectoryName);
      if (initialDirectoryName != null) {
        File initialDirectory = new File(initialDirectoryName);
        if (!initialDirectory.exists()) {
          // try the directory above
          LOGGER.severe("Directory doesn't exist, trying parent.");
          initialDirectory = initialDirectory.getParentFile();
        }
        LOGGER.severe("initialDirectory = " + initialDirectory.toString());
        if (initialDirectory.exists()) {
          LOGGER.severe("Setting initial directory to: " + initialDirectory.toString());
          fileChooser.setInitialDirectory(initialDirectory);
        }
      }
    }
    
    if (itemDescriptor.getInitialFileNameFunction() != null) {
      LOGGER.severe("InitialFileNameFunction specified");
      String initialFileName = itemDescriptor.getInitialFileNameFunction().apply(eObjectTreeCell);
      LOGGER.severe("initialFileName = " + initialFileName);
      if (initialFileName != null) {
        fileChooser.setInitialFileName(initialFileName);
      }
    }
    
    File file = null;
    if (itemDescriptor.isOpenDialog()) {
      file = fileChooser.showOpenDialog(null);
    } else {
      file = fileChooser.showSaveDialog(null);
    }
    if (file != null) {
      eObjectTreeCell.commitEdit(file);
    } else {
      eObjectTreeCell.cancelEdit();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  protected void storeNewValue(Object newValue) {
    File file = (File) newValue;
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), file != null ? file.getAbsolutePath() : null);    
  }

  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }

}
