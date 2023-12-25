package goedegep.jfx.eobjecttreeview;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.stage.DirectoryChooser;

public class EObjectTreeCellHelperForAttributeFolder extends EObjectTreeCellHelperForAttributeAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCellHelperForAttributeMultiLineText.class.getName());
  
  private DirectoryChooser folderChooser = null;
  
  /**
   * Constructor
   * 
   * @param eObjectTreeCell the {@link EObjectTreeCell} for which this is a helper.
   */
  public EObjectTreeCellHelperForAttributeFolder(EObjectTreeCell eObjectTreeCell) {
    super(eObjectTreeCell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeGraphicForEditing() {
    folderChooser = new DirectoryChooser();
    if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
      String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
      if (initialDirectoryName != null) {
        folderChooser.setInitialDirectory(new File(initialDirectoryName));
      }
    }
    File folder = folderChooser.showDialog(null);
    if (folder != null) {
      eObjectTreeCell.commitEdit(folder);
    } else {
      eObjectTreeCell.cancelEdit();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  protected void storeNewValue(Object newValue) {
    File folder = (File) newValue;
    EObjectTreeItem parentItem = (EObjectTreeItem) treeItem.getParent();
    Object parentValue = parentItem.getValue();
    EObject eObject = (EObject) parentValue;
    eObject.eSet(treeItem.getEAttribute(), folder != null ? folder.getAbsolutePath() : null);    
  }

  @Override
  public String getText() {
    return labelLabel.getText() + " " + valueLabel.getText();
  }

}
