package goedegep.invandprop.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an edit panel (extension of {@link EditPanelTemplate} to
 * show/edit a list of invoice and property items (a list of {@link InvoiceAndPropertyItem}).
 * <p>
 * The panel (the Control) is a {@link TitledPane}.<br/>
 * The items can be reordered via drag and drop.
 */
public class InvoiceAndPropertyItemsPanel extends EditPanelTemplate<List<InvoiceAndPropertyItem>> {
  private static final Logger LOGGER = Logger.getLogger(InvoiceAndPropertyItemsPanel.class.getName());
  
  /**
   * Clipboard content type used for drag and drop.
   */
  private static final DataFormat INVOICE_AND_PROPERTY_ITEM_PANEL = new DataFormat("InvoiceAndPropertyItemPanel");
  
  /**
   * The {@code InvoicesAndPropertiesService}.
   */
  private InvoicesAndPropertiesService invoicesAndPropertiesService;
  
  /**
   * Top level panel.
   */
  protected TitledPane titledPane;
  
  /**
   * Pane for the invoice and property items.
   */
  protected VBox invoiceAndPropertyItemsVBox;
  
  /**
   * The list of {@code InvoiceAndPropertyItemPanel}s.
   */
  ObservableList<InvoiceAndPropertyItemPanel> invoiceAndPropertyItemPanels;
  
  
  /**
   * Create a new {@code InvoiceAndPropertyItemsPanel}
   * 
   * @param customization The GUI customization.
   * @param invoicesAndPropertiesService the {@code InvoicesAndPropertiesService}
   * @return a newly created {@code InvoiceAndPropertyItemsPanel}
   */
  public static InvoiceAndPropertyItemsPanel newInstance(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    InvoiceAndPropertyItemsPanel invoiceAndPropertyItemsPanel = new InvoiceAndPropertyItemsPanel(customization, invoicesAndPropertiesService);
    
    invoiceAndPropertyItemsPanel.performInitialization();
    
    return invoiceAndPropertyItemsPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param invoicesAndPropertiesService the {@code InvoicesAndPropertiesService}
   */
  private InvoiceAndPropertyItemsPanel(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, true);
    
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return titledPane;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    invoiceAndPropertyItemPanels = FXCollections.observableArrayList();
    invoiceAndPropertyItemPanels.addListener(new ListChangeListener<InvoiceAndPropertyItemPanel>() {

      @Override
      public void onChanged(Change<? extends InvoiceAndPropertyItemPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {  // NOPMD
            // No action needed here
          } else if (c.wasUpdated()) {
            LOGGER.severe("Update not handled!!");
          } else {
            for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel: c.getRemoved()) {
              unregisterEditorComponents(invoiceAndPropertyItemPanel);
              handleChanges();
            }
            for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel: c.getAddedSubList()) {
              registerEditorComponents(invoiceAndPropertyItemPanel);
              handleChanges();
            }
          }
        }
        
        updateInvoiceAndPropertyItemPanel();
        
      }
      
    });
    
    this.addValueAndOrStatusChangeListener((e,f) -> handleChanges());
  }
  
  protected void handleChanges() {
    updatePanelTitle();
  }
  
  private void updatePanelTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Invoice and property items");
    buf.append(" ")
    .append(getStatusSymbol());
    
    titledPane.setText(buf.toString());
  }
  
  /**
   * Redraw the invoice and property item panel ({@link invoiceAndPropertyItemsVBox}).
   */
  private void updateInvoiceAndPropertyItemPanel() {
    invoiceAndPropertyItemsVBox.getChildren().clear();
    for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel:  invoiceAndPropertyItemPanels) {
      HBox hBox = componentFactory.createHBox(12.0);
      Button deleteButton = componentFactory.createButton("Delete", "Remove this item");
      deleteButton.setOnAction((e) -> deleteInvoiceAndPropertyItem(e));
      hBox.getChildren().addAll(invoiceAndPropertyItemPanel.getControl(), deleteButton);
      invoiceAndPropertyItemsVBox.getChildren().add(hBox);
    }
  }
  
  /**
   * Handle the action of one of the delete buttons.
   * 
   * @param actionEvent the {@code ActionEvent}.
   */
  private void deleteInvoiceAndPropertyItem(ActionEvent actionEvent) {
    /*
     * The source of the actionEvent is a delete button.
     * Find the HBox with this button, then the first node in this HBox is the control of one of the invoiceAndPropertyItemPanels.
     */
    Object eventSource = actionEvent.getSource();
    
    for (Node node: invoiceAndPropertyItemsVBox.getChildren()) {
      if (node instanceof HBox hBox) {
        Node lastNode = hBox.getChildren().getLast();
        if (lastNode instanceof Button deleteButton) {
          if (deleteButton == eventSource) {
            Node firstNode = hBox.getChildren().getFirst();
            InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanelToDelete = null;
            for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel: invoiceAndPropertyItemPanels) {
              if (invoiceAndPropertyItemPanel.getControl() == firstNode) {
                invoiceAndPropertyItemPanelToDelete = invoiceAndPropertyItemPanel;
                break;
              }
            }
            invoiceAndPropertyItemPanels.remove(invoiceAndPropertyItemPanelToDelete);
            break;
          }
        }
      }
    }    
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    
    VBox contentVBox  = componentFactory.createVBox(12.0, 12.0);
    
    invoiceAndPropertyItemsVBox = componentFactory.createVBox(12.0, 12.0);
    contentVBox.getChildren().add(invoiceAndPropertyItemsVBox);
    
    Button newInvoiceAndPropertyItemButton = componentFactory.createButton("+ Add an invoice and property item", "Click to add an invoice and property item");
    newInvoiceAndPropertyItemButton.setOnAction(e -> createNewInvoiceAndPropertyItemPanel(true));
    contentVBox.getChildren().add(newInvoiceAndPropertyItemButton);
    
    titledPane = new TitledPane(getLabelBaseText(), contentVBox);
    titledPane.setMaxHeight(800);
    titledPane.setMinHeight(100);
    titledPane.setPrefHeight(800);
  }
  
  /**
   * Create a new file reference (attachment) panel.
   * 
   * @param expand if true, the panel will be expanded upon creation.
   * @return the created file reference (attachment) panel.
   */
  private InvoiceAndPropertyItemPanel createNewInvoiceAndPropertyItemPanel(boolean expand) {
    InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel = InvoiceAndPropertyItemPanel.newInstance(customization);
    
    installDragAndDropHandling(invoiceAndPropertyItemPanel);
    invoiceAndPropertyItemPanels.add(invoiceAndPropertyItemPanel);
    
    return invoiceAndPropertyItemPanel;
  }
  
  /**
   * Install drag and drop handling.
   * <p>
   * The file references can be reordered via drag and drop.
   */
  private void installDragAndDropHandling(InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel) {
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    invoiceAndPropertyItemPanel.getControl().setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = invoiceAndPropertyItemPanels.indexOf(invoiceAndPropertyItemPanel);
        clipboardContent.put(INVOICE_AND_PROPERTY_ITEM_PANEL, myIndex);

        Dragboard dragBoard = titledPane.startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for FILE_REFERENCE_PANEL.
     */
    invoiceAndPropertyItemPanel.getControl().setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != invoiceAndPropertyItemPanel  &&  dragboard.hasContent(INVOICE_AND_PROPERTY_ITEM_PANEL)) {
          event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    invoiceAndPropertyItemPanel.getControl().setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(INVOICE_AND_PROPERTY_ITEM_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(INVOICE_AND_PROPERTY_ITEM_PANEL);
          if (sourceIndex != null) {
            InvoiceAndPropertyItemPanel sourcePanel = invoiceAndPropertyItemPanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            invoiceAndPropertyItemPanels.remove(sourceIndexInt);
            
            Integer myIndex = invoiceAndPropertyItemPanels.indexOf(invoiceAndPropertyItemPanel);
            invoiceAndPropertyItemPanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<InvoiceAndPropertyItem> createObject() {
    return new ArrayList<InvoiceAndPropertyItem>();
  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    
    for (InvoiceAndPropertyItem invoiceAndPropertyItem: value) {
      InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel = createNewInvoiceAndPropertyItemPanel(false);
      invoiceAndPropertyItemPanel.setObject(invoiceAndPropertyItem);
    }
    
  }

  @Override
  protected void fillObjectFromControls(List<InvoiceAndPropertyItem> invoiceAndPropertyItems, boolean getCurrentValue) throws EditorException {
    if (getCurrentValue) {
      invoiceAndPropertyItems.clear();

      for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel: invoiceAndPropertyItemPanels) {
        InvoiceAndPropertyItem invoiceAndPropertyItem = invoiceAndPropertyItemPanel.getCurrentValue();
        invoiceAndPropertyItems.add(invoiceAndPropertyItem);
      }
    } else {
      // First update all panels
      for (InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel: invoiceAndPropertyItemPanels) {
        invoiceAndPropertyItemPanel.accept();
      }

      // Check for any changes. If there are changes, recreate the complete list.
      boolean changes = false;

      if (invoiceAndPropertyItems.size() != invoiceAndPropertyItemPanels.size()) {
        LOGGER.info("changes because of different list sizes");
        changes = true;
      }

      if (!changes) {
        //      int index = 0;
        //      for (FileReference attachment: fileReferences) {
        //        FileReferenceEditPanel fileReferencePanel = fileReferencePanels.get(index++);
        //        FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
        //        String fileReferencePanelReferenceTypeTag = fileReferencePanelReferenceType != null ? fileReferencePanelReferenceType.tag() : null;
        //        String fileReferencePanelFile = fileReferencePanel.getPathNameRelativeToPrefix();
        //        if (!PgUtilities.equals(attachment.getTags(), fileReferencePanelReferenceTypeTag)  ||
        //            !attachment.getFile().equals(fileReferencePanelFile)  ||
        //            !PgUtilities.equals(attachment.getTitle(), fileReferencePanel.getTitleObjectControl().getValue())) {
        //          changes = true;
        //          break;
        //        }
        //      }
      }

      if (changes) {
        //      object.clear();

        //      for (FileReferenceEditPanel fileReferencePanel: fileReferencePanels) {
        ////        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        ////        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        //        object.add(fileReferencePanel.getValue());
        //      }
      }

    }

  }
}
