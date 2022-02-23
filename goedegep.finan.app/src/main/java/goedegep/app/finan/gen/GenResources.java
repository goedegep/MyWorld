package goedegep.app.finan.gen;

import javax.swing.ImageIcon;

public class GenResources {
  // TODO integrate this class in AppResources
  static ImageIcon  closeFileIcon = null;
  static ImageIcon  errorIcon48x48 = null;
  static ImageIcon  handleTransactionsIcon = null;
  static ImageIcon  handleTransactionsDisabledIcon = null;
  static ImageIcon  helpIcon = null;
  static ImageIcon  openFileIcon = null;
  
  
  static public ImageIcon getCloseFileIcon() {
    if (closeFileIcon == null) {
      closeFileIcon = new ImageIcon(GenResources.class.getResource("closeFile.gif"));
    }

    return closeFileIcon;
  }
  
  static public ImageIcon getErrorIconLarge() {
    if (errorIcon48x48 == null) {
      errorIcon48x48 = new ImageIcon(GenResources.class.getResource("error48x48.gif"));
    }
    
    return errorIcon48x48;
  }

  static public ImageIcon getHandleTransactionsIcon() {
    if (handleTransactionsIcon == null) {
      handleTransactionsIcon = new ImageIcon(GenResources.class.getResource("handleTransactions.gif"));
    }

    return handleTransactionsIcon;
  }

  static public ImageIcon getHandleTransactionsDisabledIcon() {
    if (handleTransactionsDisabledIcon == null) {
      handleTransactionsDisabledIcon = new ImageIcon(GenResources.class.getResource("handleTransactions_disabled.gif"));
    }

    return handleTransactionsIcon;
  }

  static public ImageIcon getHelpIcon() {
    if (helpIcon == null) {
      helpIcon = new ImageIcon(GenResources.class.getResource("help.gif"));
    }

    return helpIcon;
  }

  static public ImageIcon getOpenFileIcon() {
    if (openFileIcon == null) {
      openFileIcon = new ImageIcon(GenResources.class.getResource("openFile.gif"));
    }

    return openFileIcon;
  }
}
