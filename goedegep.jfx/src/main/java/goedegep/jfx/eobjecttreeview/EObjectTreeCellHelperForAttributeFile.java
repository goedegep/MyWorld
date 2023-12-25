package goedegep.jfx.eobjecttreeview;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import javafx.stage.FileChooser;

public class EObjectTreeCellHelperForAttributeFile extends EObjectTreeCellHelperForAttributeAbstract {
  @SuppressWarnings("unused")
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
    fileChooser.setInitialDirectory(null);
    if (itemDescriptor.getInitialDirectoryNameFunction() != null) {
      String initialDirectoryName = itemDescriptor.getInitialDirectoryNameFunction().apply(eObjectTreeCell);
      if (initialDirectoryName != null) {
        fileChooser.setInitialDirectory(new File(initialDirectoryName));
      }
    }
    File file = fileChooser.showOpenDialog(null);
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
