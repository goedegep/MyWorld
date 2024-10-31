package goedegep.jfx;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PropertyDescriptorsEditorFx extends JfxStage {
  private static final Logger         LOGGER = Logger.getLogger(PropertyDescriptorsEditorFx.class.getName());
  private static final String WINDOW_TITLE = "Edit Property Desciptors";
  
  private EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource;
  private PropertyDescriptorGroup propertyDescriptorGroup;
  private ComponentFactoryFx componentFactory;
  private EObjectTreeView treeView = null;
  private boolean isDirty = false;
      
  public PropertyDescriptorsEditorFx(CustomizationFx customization, EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource) {
    super(customization, WINDOW_TITLE);
    
    this.propertyDescriptorsResource = propertyDescriptorsResource;
    propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(
          org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        isDirty = true;
        updateTitle();

      }

    };
    propertyDescriptorGroup.eAdapters().add(eContentAdapter);
    componentFactory = getComponentFactory();
    
    setX(10);
    setY(20);
    
    /*
     * Main pane is a BorderPane.
     * Center is TreeView for the PropertyDescriptors.
     * Bottom is a pane with cancel and save buttons.
     */
    BorderPane mainPane = new BorderPane();
    
    treeView = new PropertyDescriptorsTreeViewCreator().createPropertyDescriptorsTreeView(customization);
    treeView.setEditMode(true);
    treeView.setEObject(propertyDescriptorGroup);
    treeView.setMinWidth(600);
    
    mainPane.setCenter(treeView);
    
    mainPane.setBottom(createButtonsPane());

    setScene(new Scene(mainPane, 1600, 600));

    show();
    
    updateTitle();
    
    LOGGER.fine("<=");
  }

  /**
   * Create the pane with the 'Cancel' and 'Save changes' buttons.
   * 
   * @return the created buttons pane.
   */
  private Node createButtonsPane() {
    HBox buttonPane = new HBox(20d);
    buttonPane.setPadding(new Insets(10d, 15d, 10d, 15d));
    
    Button button = componentFactory.createButton("Cancel", "Close window without saving changes");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        closeWindow();
      }
      
    });
    buttonPane.getChildren().add(button);
    
    button = componentFactory.createButton("Save changes", "Changes are effective only after restarting the application");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          propertyDescriptorsResource.save();
        } catch (IOException e) {
          e.printStackTrace();
        }
        isDirty = false;
        updateTitle();
      }
      
    });
    buttonPane.getChildren().add(button);

    return buttonPane;
  }
  
  private void closeWindow() {
    this.close();
  }

  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (isDirty) {
      buf.append("*");
    }
    buf.append(propertyDescriptorsResource.getFileName());
    
    setTitle(buf.toString());
  }
}
