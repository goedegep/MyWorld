package goedegep.vacations.app.guifx;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import goedegep.vacations.checklist.model.CurrentVacation;
import goedegep.vacations.checklist.model.ItemStatus;
import goedegep.vacations.checklist.model.PackStatus;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistCategory;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VacationChecklistWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(VacationChecklistWindow.class.getName());
  private static final String WINDOW_TITLE = "Vacation checklist";

  private EMFResource<VacationChecklist> vacationChecklistResource;
  private ComponentFactoryFx componentFactory;
  private VBox labelsPanel;
  private VBox itemsPanel;
  private TextArea toDoList;
  private Label statusLabel = new Label("");
  
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param vacationChecklistResource the {@code EMFResource} with the vacation checklist.
   */
  public VacationChecklistWindow(CustomizationFx customization, EMFResource<VacationChecklist> vacationChecklistResource) {
    super(customization, WINDOW_TITLE);
    
    this.vacationChecklistResource = vacationChecklistResource;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    updateLabelsPanel();
    updateItemsPanel();
    updateToDoList();
    updateTitle();
    
    // Update the title to reflect the 'dirty status' of the checklist.
    vacationChecklistResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
      
    });
    
    // Update the title to show the filename of the checklist (although this will never happen).
    vacationChecklistResource.fileNameProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        updateTitle();
      }
      
    });
    
    // Listen to any changes in the checklist, handleChecklistChangeNotification() acts on specific types of changes.
    vacationChecklistResource.addNotificationListener(this::handleChecklistChangeNotification);
    
    show();
  }

  /**
   * Create the GUI.
   * <p>
   * For the labels and categories/items only the panels are created here. They are filled by the update methods.
   */
  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    // Top labels panel
    labelsPanel = componentFactory.createVBox(12.0, 12.0);
    labelsPanel.setBorder(componentFactory.getRectangularBorder());
    mainPanel.getChildren().add(labelsPanel);
    
    // Checklist panel
    itemsPanel = componentFactory.createVBox(12.0, 12.0);
    itemsPanel.setBorder(componentFactory.getRectangularBorder());
    mainPanel.getChildren().add(itemsPanel);
    
    // ToDo list
    toDoList = componentFactory.createTextArea();
    toDoList.setStyle("-fx-font-size: 2em;");
    toDoList.setWrapText(true);
    mainPanel.getChildren().add(toDoList);
    
    // Save and cancel buttons
    mainPanel.getChildren().addAll(createButtonsPanel());
    
    // Status label
    mainPanel.getChildren().addAll(statusLabel);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }
  
  /**
   * Handle changes anywhere in the checklist.
   * <p>
   * There are specific actions for changes in specific parts of the checklist:
   * <ul>
   * <li>
   * If there are changes in the VacationChecklistLabelsList, labels that no longer exist have to be removed from the CurrentVacation,
   * and the labels panel has to be updated. The latter may lead to changes in the selected labels.
   * </li>
   * <li>
   * If there are changes in the selected labels, items that are no longer relevant have to be removed from the CurrentVacation,
   * and the items panel has to be updated.
   * </li>
   * <li>
   * If there are changes in the VacationChecklistCategoriesList, items that no longer exist have to be removed from the CurrentVacation,
   * and the items panel has to be updated.
   * </li>
   * </ul>
   * @param notification A {@code Notification} with information about the change in the checklist.
   */
  private void handleChecklistChangeNotification(Notification notification) {
    LOGGER.info("=> " + notification.toString());
    
    // Ignore REMOVING_ADAPTER events as we never do this ourselves, it is done as part of deleting an item.
    if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
      return;
    }

    VacationChecklistPackage vacationChecklistPackage = VacationChecklistPackage.eINSTANCE;
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    VacationChecklistLabelsList vacationChecklistLabelsList = vacationChecklist.getVacationChecklistLabelsList();
    VacationChecklistCategoriesList vacationChecklistCategoriesList = vacationChecklist.getVacationChecklistCategoriesList();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    
    // Handle changes in the labels list.
    if (EmfUtil.isNotificationFromClassFeatureOrAnyChild(notification, vacationChecklistPackage.getVacationChecklistLabelsList(), 
        vacationChecklistPackage.getVacationChecklistLabelsList_VacationChecklistLabels(), vacationChecklistLabelsList)) {
      removeNonExistingLabelsFromCurrentVacation();
      updateLabelsPanel();
    }
    
    // Handle changes in the selected labels
    if (EmfUtil.isNotificationFromClassFeatureOrAnyChild(notification, vacationChecklistPackage.getCurrentVacation(),
        vacationChecklistPackage.getCurrentVacation_SelectedLabels(), currentVacation)) {
      removeNonRelevantItemsFromCurrentVacation();
      updateItemsPanel();
      updateToDoList();
    }
    
    // Handle changes in the categories/items
    if (EmfUtil.isNotificationFromEObjectOrAnyChild(notification, vacationChecklistCategoriesList)) {
      LOGGER.severe("Updating");
      removeNonRelevantItemsFromCurrentVacation();
      updateItemsPanel();
      updateToDoList();
    }
  }

  private void removeNonRelevantItemsFromCurrentVacation() {
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    List<ItemStatus> itemStatuses = currentVacation.getItemStatuses();
    
    for (int i = itemStatuses.size() -1; i >= 0; i--) {
      ItemStatus itemStatus = itemStatuses.get(i);
      VacationChecklistItem vacationChecklistItem = itemStatus.getVacationChecklistItem();
      
      if (!itemInVacationChecklistCategoriesList(vacationChecklistItem)  ||  !isItemNeeded(vacationChecklistItem)) {
        itemStatuses.remove(itemStatus);
//        LOGGER.severe("Item removed from CurrentVacation: " + itemStatus.getVacationChecklistItem().getName());
      }
    }
  }

  private boolean itemInVacationChecklistCategoriesList(VacationChecklistItem vacationChecklistItem) {
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();

    for (VacationChecklistCategory vacationChecklistCategory: vacationChecklist.getVacationChecklistCategoriesList().getVacationChecklistCategories()) {
      for (VacationChecklistItem checkVacationChecklistItem: vacationChecklistCategory.getVacationChecklistItems()) {
        if (checkVacationChecklistItem.equals(vacationChecklistItem)) {
          return true;
        }
      }
    }
    
    return false;
  }

  /**
   * Any label in the selectedLabels of the current vacation that is no longer existing, is removed.
   */
  private void removeNonExistingLabelsFromCurrentVacation() {
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    List<VacationChecklistLabel> selectedLabels = currentVacation.getSelectedLabels();
    VacationChecklistLabelsList vacationChecklistLabelsList = vacationChecklist.getVacationChecklistLabelsList();
    List<VacationChecklistLabel> labels = vacationChecklistLabelsList.getVacationChecklistLabels();
    
    for (int index = selectedLabels.size() - 1; index >= 0; index--) {
      if (!labels.contains(selectedLabels.get(index))) {
        selectedLabels.remove(index);
      }
    }
  }
  
  /**
   * Create the labels panel
   * <p>
   * This panel list all labels.
   * 
   * @return the labels panel.
   */
  private void updateLabelsPanel() {
    labelsPanel.getChildren().clear();
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    if (currentVacation == null) {
      currentVacation = VacationChecklistFactory.eINSTANCE.createCurrentVacation();
      vacationChecklist.setCurrentVacation(currentVacation);
    }
    List<VacationChecklistLabel> selectedLabels = currentVacation.getSelectedLabels();
    
    // Title
    Label titleLabel = componentFactory.createStrongLabel("Soort vakantie");
    labelsPanel.getChildren().add(titleLabel);
    
    // Labels
    TilePane labelsTilePane = new TilePane();
    labelsTilePane.setHgap(12);
    labelsTilePane.setVgap(6);
    for (VacationChecklistLabel vacationChecklistLabel: vacationChecklist.getVacationChecklistLabelsList().getVacationChecklistLabels()) {
      CheckBox checkBox = componentFactory.createCheckBox(vacationChecklistLabel.getName(), false);
      checkBox.selectedProperty().addListener(
          (ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
             if (newValue) {
               // add label to list
               LOGGER.info("Adding label: " + vacationChecklistLabel.getName() + " to current vacation");
               selectedLabels.add(vacationChecklistLabel);
             } else {
               // remove label from list
               LOGGER.info("Removing label: " + vacationChecklistLabel.getName() + " from current vacation");
               selectedLabels.remove(vacationChecklistLabel);
             }
          });
      if (selectedLabels.contains(vacationChecklistLabel)) {
        checkBox.setSelected(true);
      }
      labelsTilePane.getChildren().add(checkBox);
    }
    labelsPanel.getChildren().add(labelsTilePane);
  }
  
  /**
   * Create the panel with all items per category.
   * 
   * @return the items panel.
   */
  private void updateItemsPanel() {
    itemsPanel.getChildren().clear();
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    
    // Title
    Label titleLabel = componentFactory.createStrongLabel("Wat moet er dan mee op vakantie?");
    itemsPanel.getChildren().add(titleLabel);
    
    // Categories
    for (VacationChecklistCategory vacationChecklistCategory: vacationChecklist.getVacationChecklistCategoriesList().getVacationChecklistCategories()) {
      Node node = createCategoryPanel(vacationChecklistCategory);
      itemsPanel.getChildren().add(node);
    }
  }

  /**
   * Create a panel for a category.
   * 
   * @param vacationChecklistCategory the category for which the panel is to be created.
   * @return the panel for the {@code vacationChecklistCategory}
   */
  private Node createCategoryPanel(VacationChecklistCategory vacationChecklistCategory) {
    VBox vBox = componentFactory.createVBox(12.0, 12.0);
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    List<ItemStatus> itemStatuses = currentVacation.getItemStatuses();
    
    // Title
    Label titleLabel = componentFactory.createStrongLabel(vacationChecklistCategory.getName());
    vBox.getChildren().add(titleLabel);
    
    // Items
    TilePane tilePane = new TilePane();
    tilePane.setHgap(12);
    tilePane.setVgap(6);
    tilePane.setTileAlignment(Pos.TOP_LEFT);
    for (VacationChecklistItem vacationChecklistItem: vacationChecklistCategory.getVacationChecklistItems()) {
      // Skip item if: it has at least one category set and none of the set categories is selected.
      if (!isItemNeeded(vacationChecklistItem)) {
        continue;
      }
      
      // Make sure there is an ItemStatus for this item
      ItemStatus itemStatus = null;
      for (ItemStatus checkItemStatus: itemStatuses) {
        if (checkItemStatus.getVacationChecklistItem().equals(vacationChecklistItem)) {
          itemStatus = checkItemStatus;
          break;
        }
      }
      if (itemStatus == null) {
        itemStatus = VacationChecklistFactory.eINSTANCE.createItemStatus();
        itemStatus.setPackStatus(PackStatus.TODO);
        itemStatus.setVacationChecklistItem(vacationChecklistItem);
        itemStatuses.add(itemStatus);
      }
      
      final ItemStatus finalItemStatus = itemStatus;
      
      HBox itemPanel = componentFactory.createHBox(12.0);
      CheckBox checkBox = componentFactory.createCheckBox(null, false);
      Text text = componentFactory.createText(vacationChecklistItem.getName());
      updateItemControls(finalItemStatus, checkBox, text);
            
      text.setOnMouseClicked(e -> {
        if (finalItemStatus.getPackStatus() == PackStatus.NOT_NEEDED) {
          finalItemStatus.setPackStatus(PackStatus.TODO);
        } else {
          finalItemStatus.setPackStatus(PackStatus.NOT_NEEDED);
        }
        updateItemControls(finalItemStatus, checkBox, text);
        updateToDoList();
      });
      
      checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
        if (newValue) {
          finalItemStatus.setPackStatus(PackStatus.PACKED);
        } else {
          // If the new value is 'not selected' and the current status is 'not needed', no action, as we've deselected it ourselves.
          if (finalItemStatus.getPackStatus() != PackStatus.NOT_NEEDED) {
            finalItemStatus.setPackStatus(PackStatus.TODO);
          }
        }
        updateItemControls(finalItemStatus, checkBox, text);
        updateToDoList();
      });
      
      itemPanel.getChildren().addAll(checkBox, text);
      tilePane.getChildren().add(itemPanel);
    }
    vBox.getChildren().add(tilePane);

    return vBox;
  }
  
  private void updateItemControls(ItemStatus itemStatus, CheckBox checkBox, Text text) {
    LOGGER.info("=> itemStatus=" + itemStatus);
    
    switch (itemStatus.getPackStatus()) {
    case TODO:
      checkBox.setSelected(false);
      checkBox.setDisable(false);
      text.setStyle("");
      break;
      
    case PACKED:
      checkBox.setSelected(true);
      checkBox.setDisable(false);
      text.setStyle("");
      break;
      
    case NOT_NEEDED:
      checkBox.setSelected(false);
      checkBox.setDisable(true);
      text.setStyle("-fx-strikethrough: true;");
      break;
    }
  }
  
  private void updateToDoList() {
    int itemsToPack = getNumberOfItemsToPack();
    
    StringBuilder buf = new StringBuilder();
    if (itemsToPack == 0) {
      buf.append("All done, ready to go!");
    } else if (itemsToPack <= 10) {
      buf.append("You still have to pack: ");
      
      VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
      CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
      List<ItemStatus> itemStatuses = currentVacation.getItemStatuses();
      boolean first = true;
      
      for (ItemStatus itemStatus: itemStatuses) {
        if (itemStatus.getPackStatus() == PackStatus.TODO) {
          if (first) {
            first = false;
          } else {
            buf.append(", ");
          }
          
          buf.append(itemStatus.getVacationChecklistItem().getName());
        }
      }
    } else {
      buf.append("You still have a lot to pack!");
    }
    
    toDoList.setText(buf.toString());
  }
  
  private int getNumberOfItemsToPack() {
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    List<ItemStatus> itemStatuses = currentVacation.getItemStatuses();
    int itemsToPack = 0;
    
    for (ItemStatus itemStatus: itemStatuses) {
      if (itemStatus.getPackStatus() == PackStatus.TODO) {
        itemsToPack++;
      }
    }
    
    return itemsToPack;
  }
 
  private boolean isItemNeeded(VacationChecklistItem vacationChecklistItem) {
    List<VacationChecklistLabel> itemLabels = vacationChecklistItem.getVacationChecklistLabels();
    if (itemLabels.isEmpty()) {
      return true;
    }
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    List<VacationChecklistLabel> selectedLabels = currentVacation.getSelectedLabels();
    
    for (VacationChecklistLabel label: itemLabels) {
      if (selectedLabels.contains(label)) {
        return true;
      }
    }
    
    return false;
  }

  private Node createButtonsPanel() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button clearButton = componentFactory.createButton("Clear", "Clear the list for the current vacation");
    clearButton.setOnAction(e -> clearCurrentVacation());
    buttonsBox.getChildren().add(clearButton);
    
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without saving any changes");
    cancelButton.setOnAction(e -> close());
    buttonsBox.getChildren().add(cancelButton);
    
    Button saveButton = componentFactory.createButton("Save", "Save changes");
    saveButton.setOnAction(e -> saveVacationChecklist());
    buttonsBox.getChildren().add(saveButton);
        
    return  buttonsBox;
  }
  
  /**
   * Clear the current vacation
   */
  private void clearCurrentVacation() {
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    CurrentVacation currentVacation = vacationChecklist.getCurrentVacation();
    currentVacation.getSelectedLabels().clear();
    currentVacation.getItemStatuses().clear();
    
    updateLabelsPanel();
    updateItemsPanel();
  }
  
  /**
   * Save the vacation checklist information to the related file.
   */
  private void saveVacationChecklist() {
    if (vacationChecklistResource != null) {
      try {
        vacationChecklistResource.save();
        statusLabel.setText("Vacation checklist saved to '" + vacationChecklistResource.getFileName() + "'");
      } catch (IOException e) {        
        componentFactory.createErrorDialog(
            "Saving the vacation checklist has failed.",
            "System error message: "  + e.getMessage()
            ).showAndWait();
      }
    }
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the language specific window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the vacations file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the vacations file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (vacationChecklistResource.isDirty()) {
      buf.append("*");
    }
    String fileName = vacationChecklistResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
