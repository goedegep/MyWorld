package goedegep.appgen.swing;

import goedegep.appgen.MessageDialogType;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.emf.common.util.EList;

/**
 * This class shows an EList in a JScrollPane.
 * The items in the list can be Objects, therefore this class implements the ObjectSelector interface.
 * The list to be shown can be set by the methods of the ObjectSelectionListener interface.
 * 
 * See Class BestandReferentieEListPane for an example of how to use this class.
 */
@SuppressWarnings("serial")
public class EListPane<T extends Object> extends JScrollPane implements ObjectSelector<T>, ObjectSelectionListener<EList<T>> {
  private static final Logger         LOGGER = Logger.getLogger(EListPane.class.getName());
  
  private AppFrame                         owner;                  // The AppFrame in which this pane is used.
  private String                           noListItem;             // The text displayed if the list is empty.
  private DefaultListModel<Object>         listModel;              // As noListItem is of type String, the JList and its model can not be of type T.
  private JList<Object>                    jList;                  // The Swing list used to display the list.
  private EList<T>                         eList;                  // The list of data elements to show and edit
  private Desktop                          desktop = null;         // Used for 'opening' an element in the list
  private boolean                          openDocumentSupported = false;  // Indicates whether the Desktop supports opening a document.
  private List<ObjectSelectionListener<T>> objectSelectionListeners = new ArrayList<ObjectSelectionListener<T>>();
  
  public EListPane(final AppFrame owner,
      String borderTitle, String toolTip, String noListItem,
      Dimension preferredSize, ListCellRenderer<Object> cellRenderer,
      DataFlavor[] supportedDataFlavors) {
    this.owner = owner;
    this.noListItem = noListItem;
    ComponentFactory componentFactory = owner.getTheComponentFactory();
        
    if (preferredSize == null) {
      preferredSize = new Dimension(300, 300);  // Set default size.
    }
    setPreferredSize(preferredSize);
    setBorder(BorderFactory.createTitledBorder(borderTitle));
    
    jList = componentFactory.createList(toolTip);
    
    if (cellRenderer != null) {
      jList.setCellRenderer(cellRenderer);
    }
    listModel = new DefaultListModel<>();
    jList.setModel(listModel);
    refillListModel(null);

    setViewportView(jList);

    // Drag and Drop support
    if (supportedDataFlavors != null) {
      jList.setTransferHandler(createTransferHandler(supportedDataFlavors));
      jList.setDragEnabled(true);
      jList.setDropMode(DropMode.ON_OR_INSERT);
    }
    
    installPopupMenu(jList);
    jList.addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {
        handleSelectionChanged();
      }
      
    });
    
    initDesktop();
  }
  
  protected TransferHandler createTransferHandler(DataFlavor[] supportedDataFlavors) {
    return new MyTransferHandler(supportedDataFlavors);
  }
  
  /**
   * Checks whether the opening of documents is supported.
   */
  private void initDesktop() {
    // Before more Desktop API is used, first check 
    // whether the API is supported by this particular 
    // virtual machine (VM) on this particular host.
    if (Desktop.isDesktopSupported()) {
        desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN)) {
          openDocumentSupported = true;
      }
    }
  }
  
  public AppFrame getOwner() {
    return owner;
  }

  public boolean isOpenDocumentSupported() {
    return openDocumentSupported;
  }
  
  public Desktop getDesktop() {
    return desktop;
  }
  
  public JList<Object> getjList() {
    return jList;
  }

  public EList<T> geteList() {
    return eList;
  }

  private void installPopupMenu(final JList<?> list) {
    // Add a mouse listener for a context menu.
    MouseListener tableMouseListener = new MouseAdapter() {
      @SuppressWarnings("deprecation")  // All my Swing code is legacy code that will not be maintained
      public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          Point mouseLocation = e.getPoint();
          int index = jList.locationToIndex(mouseLocation);
          LOGGER.info("Selected index = " + index);
          jList.setSelectedIndex(index);
          handleSelectionChanged();
        }
        
        if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
          int x = e.getX();
          int y = e.getY();
          Component clickedComponent = list.getComponentAt(new Point(x, y));
          list.setSelectedValue(clickedComponent, true);
          JPopupMenu popup = createListPopupMenu(x, y);
          if (popup != null) {
            popup.show(e.getComponent(), x, y);
          }
        }
      }
    };
    list.addMouseListener(tableMouseListener);
  }
  
  protected JPopupMenu createListPopupMenu(final int x, final int y) {
    return null;
  }
  
  protected void moveSelectedItem(int offset) {
    int selectedIndex = jList.getSelectedIndex();
    @SuppressWarnings("unchecked")
    T item = (T) jList.getSelectedValue();
    eList.move(selectedIndex + offset, selectedIndex);
    refillListModel(item);
  }

  protected void createEmptyItemBeforeOrAfterSelectedItem(boolean before) {
    T item = createEmptyItem();
    if (item != null) {
      int selectedIndex = jList.getSelectedIndex();
      if (before) {
        if (selectedIndex != -1) {
          eList.add(selectedIndex, item);
        } else {
          eList.add(item);
        }
      } else {
        if (selectedIndex == eList.size()) {
          eList.add(item);
        } else {
          eList.add(selectedIndex + 1, item);
        }
      }
      refillListModel(item);
    }
  }
  
  protected T createEmptyItem() {
    return null;
  }
  
  protected void importItems(int index, List<File> files) {
    List<T> items = createItemsFromFileList(files);
    if (items != null) {
      T lastNewItem = null;
      int insertIndex = -1;
      if (index < eList.size()) {
        insertIndex = index;
        LOGGER.info("insertIndex: " + insertIndex);
      }
      for (T item: items) {
        if (insertIndex == -1) {
          LOGGER.info("appending item");
          eList.add(item);
        } else {
          LOGGER.info("inserting before insertIndex: " + insertIndex);
          eList.add(insertIndex++, item);
        }
        lastNewItem = item;
      }

      refillListModel(lastNewItem);
    }
  }
  
  protected T createItemViaEditor() {
    return null;
  }
  
  protected List<T> createItemsFromFileList(List<File> files) {
    return null;
  }
  
  protected void openSelectedItem() {
    T selectedObject = getSelectedObject();
    File file = null;
    URI uri = null;
    
    if (selectedObject instanceof File) {
      file = (File) selectedObject;
    } else if (selectedObject instanceof URI) {
      uri = (URI) selectedObject;
    } else if (selectedObject instanceof String) {
      file = new File((String) selectedObject);
    }
    
    if (uri != null) {
      try {
        desktop.browse(uri);
      } catch (IOException e) {
        owner.showMessageDialog(MessageDialogType.ERROR, e.getMessage());
      }
    }
    
    if (file != null) {
      try {
        desktop.open(file);
      } catch (IllegalArgumentException e) {
        owner.showMessageDialog(MessageDialogType.ERROR, e.getMessage());
      } catch (IOException e) {
        owner.showMessageDialog(MessageDialogType.ERROR, e.getMessage());
      }
    }    
  }
  
  private void refillListModel(T selectedItem) {
    listModel.removeAllElements();
    
    if (eList != null) {
      jList.setEnabled(true);
      for (T item: eList) {
        listModel.addElement(item);
      }

      if (selectedItem != null) {
        jList.setSelectedValue(selectedItem, true);
      }
    } else {
      jList.setEnabled(false);
      listModel.addElement(noListItem);
    }
  }
  
  protected void deleteSelectedItem() {
    @SuppressWarnings("unchecked")
    T bestandReferentie = (T) jList.getSelectedValue();
    if (bestandReferentie != null) {
      int selectedIndex = jList.getSelectedIndex();
      if (selectedIndex > 0) {
        selectedIndex--;
      }
      eList.remove(bestandReferentie);
      refillListModel(null);
      jList.setSelectedIndex(selectedIndex);
    }
  }

  /**
   * @see goedegep.util.objectselector.ObjectSelectionListener#objectSelected(java.lang.Object)
   */
  public void objectSelected(Object source, EList<T> eList) {
    this.eList = eList;
    
    refillListModel(null);
    
    jList.setSelectedIndex(0);
  }


  public void addObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }
  
  public void removeObjectSelectionListener(ObjectSelectionListener<T> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  private void handleSelectionChanged() {
    T selectedObject = getSelectedObject();
    
    for (ObjectSelectionListener<T> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(this, selectedObject);
    }
  }
  
  @SuppressWarnings("unchecked")
  public T getSelectedObject() {
    if (eList != null) {
      return (T) jList.getSelectedValue();
    } else {
      return null;
    }
  }

  
  protected class MyTransferHandler extends TransferHandler {
    private DataFlavor[] supportedDataFlavors = null;
    
    public MyTransferHandler(DataFlavor[] supportedDataFlavors) {
      this.supportedDataFlavors = supportedDataFlavors;
    }
    
    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
      LOGGER.info("=> info: " + info);
      
      if (!transferSupportContainsSupportedFlavor(info)) {
        LOGGER.info("<= no, no supported flavor"); 
        return false;
      }

      JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
      if (dl.getIndex() == -1) {
        LOGGER.info("<= no, index = -1"); 
        return false;
      }
      
      LOGGER.info("<= yes we can!, drop location index = " + dl.getIndex()); 
      return true;
    }
    
    /**
     * Check whether a TransferSupport contains one of the supported DataFlavors.
     */
    private boolean transferSupportContainsSupportedFlavor(TransferHandler.TransferSupport info) {
      for (DataFlavor dataFlavor: supportedDataFlavors) {
        if (info.isDataFlavorSupported(dataFlavor)) {
          return true;
        }
      }
      
      return false;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
      LOGGER.info("=> info: " + info); 
      if (!info.isDrop()) {
        LOGGER.info("<= !isDrop()"); 
        return false;
      }

      if (!transferSupportContainsSupportedFlavor(info)) {
        LOGGER.info("<= no, no supported flavor"); 
        return false;
      }

      JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
      int index = dl.getIndex();
      LOGGER.info("index: " + index);
      boolean insert = dl.isInsert();
      LOGGER.info("insert: " + insert);

      if (dl.isInsert()) {
        if (index == 0) {
          LOGGER.info("at beginning of list");
        } else if (index >= jList.getModel().getSize()) {
          LOGGER.info("at end of list (index=" + index + ")");
        } else {
          LOGGER.info("between index=" + (index-1) + " and index=" + index);
        }
      } else {
        LOGGER.info("on top of index=" + index);
      }

      // Get the File that is being dropped.
      Transferable t = info.getTransferable();
      
      for (DataFlavor dataFlavor: supportedDataFlavors) {
        if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
          try {
            LOGGER.info("try to get data ..."); 
            @SuppressWarnings("unchecked")
            List<File> data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
            LOGGER.info("data.size() = " + data.size());
            File f = null;
            if (data.size() > 0) {
              f = data.get(0);
              LOGGER.info("f: " + f.getPath());
            }
            
            importItems(index, data);
          }  catch (Exception e) {
            LOGGER.severe("Exception: " + e.getMessage()); 
            return false;
          }
          
          return true;
        } else {
          throw new IllegalArgumentException("Unsupported DataFlavor: " + dataFlavor.getMimeType());
        }
      }

      
      return false;
    }

    public int getSourceActions(JComponent c) {
      return COPY_OR_MOVE;
    }

    protected Transferable createTransferable(JComponent c) {
      LOGGER.info("=>");
      @SuppressWarnings("unchecked")
      JList<Object> list = (JList<Object>)c;
      List<Object> values = list.getSelectedValuesList();
      
      return createTransferable(values);
    }
    
    protected Transferable createTransferable(List<Object> values) {
      LOGGER.info("=>");
      return null;
    }
    
  }
}
