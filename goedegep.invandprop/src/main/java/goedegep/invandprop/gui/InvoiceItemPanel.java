package goedegep.invandprop.gui;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.util.money.PgCurrency;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel to edit an invoice item.
 */
class InvoiceItemPanel extends TitledPane {
  private static final Logger LOGGER = Logger.getLogger(InvoiceItemPanel.class.getName());
  private static String DEFAULT_TITLE = "New invoice item";
  
  /**
   * The list of {@code InvoiceItemPanel}s to which a newly created panel has to add itself.
   * <p>
   * This list is used for reordering the panels via drag and drop.
   */
  private List<InvoiceItemPanel> invoiceItemPanels;
  
  private ComponentFactoryFx componentFactory;
  
  private ObjectControlGroup objectControlGroup;
  
  /**
   * The {@DataFormat} in which a panel is placed on the clipboard in a drag and drop action.
   */
  private static final DataFormat INVOICE_ITEM_PANEL = new DataFormat("InvoiceItemPanel");
  
  /**
   * Reference to {@code this}. Used in lambdas where 'this' is not available.
   */
  private InvoiceItemPanel thisInvoiceItemPanel;
  
  // The ObjectControls
  private ObjectControl<Integer> numberOfItemsObjectControl;
  private ObjectControlString descriptionObjectControl;
  private ObjectControlCurrency amountObjectControl;
  private ObjectControlString remarksObjectControl;

  /**
   * Constructor
   * 
   * @param customization - the GUI customization
   * @param invoiceItemPanels - the list of {@code InvoiceItemPanel}s of which this panel is part.
   * @param expanded - if true the panel will initially be in the expanded state.
   */
  public InvoiceItemPanel(CustomizationFx customization, List<InvoiceItemPanel> invoiceItemPanels, boolean expanded) {
    this.invoiceItemPanels = invoiceItemPanels;
    thisInvoiceItemPanel = this;
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();

    createGUI();
    
    installChangeListeners();
    
    installDragAndDropHandling();
    
    this.setExpanded(expanded);
    invoiceItemPanels.add(this);
    
    updateTitle();
  }

  public ObjectControl<Integer> getNumberOfItemsObjectInput() {
    return numberOfItemsObjectControl;
  }

  public ObjectControlString getDescriptionObjectInput() {
    return descriptionObjectControl;
  }

  public ObjectControlCurrency getAmountObjectInput() {
    return amountObjectControl;
  }

  public ObjectControlString getRemarksObjectInput() {
    return remarksObjectControl;
  }

  public ObjectControlGroup getObjectControlGroup() {
    return objectControlGroup;
  }
  
  /**
   * Create the GUI controls (all ObjectControls) and add them to the {@code objectControlGroup}.
   */
  private void createControls() {
    // Create the ObjectControls    
    numberOfItemsObjectControl = componentFactory.createObjectControlInteger(1, 150.0, true, "the number of items");
    numberOfItemsObjectControl.setId("InvoiceItemNumberOfItems");
    descriptionObjectControl = componentFactory.createObjectControlString(null, 200, false, "typically the product");
    descriptionObjectControl.setId("InvoiceItemDescription");
    amountObjectControl = componentFactory.createObjectControlCurrency(null, 150, false, "the amount of money paid");
    amountObjectControl.setId("InvoiceItemAmount");
    remarksObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "any comments on this invoice");
    remarksObjectControl.setId("InvoiceItemRemarks");
    
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(
        numberOfItemsObjectControl,
        descriptionObjectControl,
        amountObjectControl,
        remarksObjectControl);
  }

  
  /**
   * Create the GUI
   * <p>
   * The GUI is a VBox, with a GridPane containing the ObjectControls and a buttons box.
   */
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
        
    int rowIndex = 1;
    
    addAttributeEditControlsToGrid(gridPane, rowIndex++, numberOfItemsObjectControl, "Number of items");
    addAttributeEditControlsToGrid(gridPane, rowIndex++, descriptionObjectControl, "Description");
    addAttributeEditControlsToGrid(gridPane, rowIndex++, amountObjectControl, "Amount");
    addAttributeEditControlsToGrid(gridPane, rowIndex++, remarksObjectControl, "Remarks");
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    setContent(rootPane);
  }

  /**
   * Add the controls (Label and ObjectInput) for one attribute to the gridPane.
   * 
   * @param gridPane The GridPane to add the controls to.
   * @param rowIndex Index for the row in the GridPane to which the controls are to be added.
   * @param eObjectAttributeEditDescriptor EObjectAttributeEditDescriptor for the attribute.
   */
  private void addAttributeEditControlsToGrid(GridPane gridPane, int rowIndex, ObjectControl<?> objectControl, String labelText) {
    // Label
    StringBuilder buf = new StringBuilder();
    buf.append(labelText);
    if (!objectControl.isOptional()) {
      buf.append(" *");
    }
    buf.append(":");
    Label label = componentFactory.createLabel(buf.toString());
    gridPane.add(label, 0, rowIndex);
    
    // ObjectInput control
    Node node = objectControl.getControl();
    gridPane.add(node, 1, rowIndex); 
    
    // Status indicator
    Node statusIndicator = objectControl.getStatusIndicator();
    gridPane.add(statusIndicator, 2, rowIndex);
  }

  /**
   * Create a box with the 'Delete' button.
   * @return the newly created buttons box.
   */
  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button deleteButton = componentFactory.createButton("Delete this item", "Delete this invoice item");
    deleteButton.setOnAction(e -> deleteInvoiceItem());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  /**
   * Install change listeners.
   * <p>
   * The title of this panel is built up from a number of control values. So for these controls an invalidation listener is installed and
   * upon a change {@code updateTitle()} is called.<br/>
   * Listeners are installed on: numberOfItemsObjectControl, descriptionObjectControl, amountObjectControl and objectControlGroup.isValid.
   */
  private void installChangeListeners() {
    InvalidationListener invalidationListener = (observable) -> updateTitle();
    
    numberOfItemsObjectControl.addListener(invalidationListener);
    descriptionObjectControl.addListener(invalidationListener);
    amountObjectControl.addListener(invalidationListener);
    objectControlGroup.addListener(invalidationListener);
  }
  
  /**
   * Install drag and drop handling.
   * <p>
   * The invoice items on our {@code invoiceItemPanels} can be reordered via drag and drop.
   */
  private void installDragAndDropHandling() {
    /*
     * A panel is identified by its index in the invoiceItemPanels.
     */
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */    
    setOnDragDetected((mouseEvent) -> {
      ClipboardContent clipboardContent = new ClipboardContent();
      Integer myIndex = invoiceItemPanels.indexOf(thisInvoiceItemPanel);
      clipboardContent.put(INVOICE_ITEM_PANEL, myIndex);

      Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
      dragBoard.setContent(clipboardContent);

      mouseEvent.consume();
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for data type INVOICE_ITEM_PANEL.
     */
    setOnDragOver((dragEvent) -> {
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragEvent.getGestureSource() != thisInvoiceItemPanel) {
          if (dragboard.hasContent(INVOICE_ITEM_PANEL)) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
          }
        }

        dragEvent.consume();
      });
    
    /*
     * Handle the dropping on the target
     */
    setOnDragDropped((dragEvent) -> {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasContent(INVOICE_ITEM_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(INVOICE_ITEM_PANEL);
          if (sourceIndex != null) {
            InvoiceItemPanel sourcePanel = invoiceItemPanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            invoiceItemPanels.remove(sourceIndexInt);
            
            Integer myIndex = invoiceItemPanels.indexOf(thisInvoiceItemPanel);
            invoiceItemPanels.add(myIndex, sourcePanel);
          }

          dragEvent.setDropCompleted(success);
        }

        dragEvent.consume();
      });
  }
  
  /**
   * Delete this panel (invoice item).
   */
  private void deleteInvoiceItem() {
    invoiceItemPanels.remove(this);
  }
  
  /**
   * Update the title of this TitledPane.
   */
  private void updateTitle() {
    StringBuilder buf = new  StringBuilder();
    
    // Add number of items if available
    Integer numberOfItems = null;
    if (numberOfItemsObjectControl.isValid() && numberOfItemsObjectControl.isFilledIn()) {
      numberOfItems = numberOfItemsObjectControl.getValue();
    }
    if (numberOfItems != null  &&  numberOfItems != 0) {
      buf.append(numberOfItems).append("x ");
    }
    
    // Add description if available
    String description = null;
    description = descriptionObjectControl.getValue();
     if (description == null  ||  description.isEmpty()) {
      description = DEFAULT_TITLE;
    }
    buf.append(description).append(" ");
    
    // Add amount if available
    PgCurrency amount = null;
    if (amountObjectControl.isValid() && amountObjectControl.isFilledIn()) {
      amount = amountObjectControl.getValue();
    }
    if (amount != null) {
      buf.append(amountObjectControl.getValueAsFormattedText()).append(" ");
    }
    
    // Add (in)valid indication
    if (objectControlGroup.isValid()) {
      buf.append(EObjectEditor.OK_INDICATOR);
    } else {
      buf.append(EObjectEditor.NOK_INDICATOR);
    }
    
    setText(buf.toString());
  }
}

