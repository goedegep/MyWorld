package goedegep.jfx.eobjecteditor;

import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.util.string.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EObjectEditor<E extends EObject> extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(EObjectEditor.class.getName());
  
  public static final String OK_INDICATOR = "✓";
  public static final String NOK_INDICATOR = "!";
  
  /**
   * The EClass of the items in the table.
   */
  private EClass eClass;
  
  /**
   * The {@link EFactory} to create new objects of type T.
   */
  private EFactory eFactory;
  
  private ComponentFactoryFx componentFactory;
  private EObjectEditorDescriptor eObjectEditorDescriptor;
  private EList<E> objects;
  private ObjectControlGroup objectInputContainer;
  
  public EObjectEditor(CustomizationFx customization, EClass eClass, EObjectEditorDescriptor eObjectEditorDescriptor, EList<E> objects) {
    super(createWindowTitle(eObjectEditorDescriptor.getWindowTitle()), customization);
    
    this.eClass = eClass;
    this.eObjectEditorDescriptor = eObjectEditorDescriptor;
    this.objects = objects;

    EPackage ePackage = eClass.getEPackage();
    eFactory = ePackage.getEFactoryInstance();
    
    componentFactory = customization.getComponentFactoryFx();
    
    createObjectInputContainer();
    
    createGUI();
    
    show();
  }
  
  private void createObjectInputContainer() {
    objectInputContainer = new ObjectControlGroup();
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: eObjectEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      objectInputContainer.addObjectControls((ObjectControl<?>) eObjectAttributeEditDescriptor.getObjectControl());
    }
    
  }

  private static String createWindowTitle(String objectName) {
    return "New " + StringUtil.CapitalizeWords(objectName);
  }

  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    int rowIndex = 0;
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: eObjectEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      addAttributeEditControlsToGrid(gridPane, rowIndex++, eObjectAttributeEditDescriptor);
    }    
    
    rootPane.getChildren().add(gridPane);
    
    rootPane.getChildren().add(createButtonsBox());    
    
    setScene(new Scene(rootPane));
    
  }
  
  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without creating " + getIndefiniteArticle(eObjectEditorDescriptor.getWindowTitle()) + " " + eObjectEditorDescriptor.getWindowTitle());
    cancelButton.setOnAction(e -> this.close());
    buttonsBox.getChildren().add(cancelButton);
    
    Button createButton = componentFactory.createButton("Create " + StringUtil.CapitalizeWords(eObjectEditorDescriptor.getWindowTitle()), "Create a new " + eObjectEditorDescriptor.getWindowTitle() + " based on the entered values");
//    objectInputContainer.isValid().addListener(new ChangeListener<>() {
//
//      @Override
//      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//        createButton.setDisable(!newValue);        
//      }
//
//        
//    });
    createButton.setDisable(!objectInputContainer.isValid());
    createButton.setOnAction(e -> createObject());
    buttonsBox.getChildren().add(createButton);
    
    return  buttonsBox;
  }
  
  /**
   * Get "a" or "an" to put in front of a word.
   * 
   * @param string the text following "a" or "an".
   * @return "an" if <code>string</code> starts with a vowel, "a" otherwise.
   */
  private String getIndefiniteArticle(String string) {
    char firstChar = string.charAt(0);
    char[] charsForAn = {'a', 'e', 'i', 'o', 'u'};
    
    for (char charForAn: charsForAn) {
      if (firstChar == charForAn)  {
        return "an";
      }
    }
    
    return "a";
  }

  /**
   * Add the controls (Label and ObjectInput) for one attribute to the gridPane.
   * 
   * @param gridPane The GridPane to add the controls to.
   * @param rowIndex Index for the row in the GridPane to which the controls are to be added.
   * @param eObjectAttributeEditDescriptor EObjectAttributeEditDescriptor for the attribute.
   */
  private void addAttributeEditControlsToGrid(GridPane gridPane, int rowIndex, EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor) {
    // Label
    StringBuilder buf = new StringBuilder();
    buf.append(eObjectAttributeEditDescriptor.getLabelText());
    if (!eObjectAttributeEditDescriptor.getObjectControl().isOptional()) {
      buf.append(" *");
    }
    buf.append(":");
    Label label = componentFactory.createLabel(buf.toString());
    gridPane.add(label, 0, rowIndex);
    
    // ObjectInput control
    Node node = eObjectAttributeEditDescriptor.getObjectControl().getControl();
    gridPane.add(node, 1, rowIndex); 
    
    // Ok/Not OK label
    Label statusLabel = componentFactory.createLabel(null);
    ObjectControl<?> objectInput = (ObjectControl<?>) node;
    objectInput.addListener((o) -> updateStatusLabel(statusLabel, objectInput.isValid()));   
    updateStatusLabel(statusLabel, objectInput.isValid());
    gridPane.add(statusLabel, 2, rowIndex);
  }
  
  public static void updateStatusLabel(Label label, boolean status) {
    if (status) {
      label.setText(OK_INDICATOR);
    } else {
      label.setText(NOK_INDICATOR);
    }
  }
  
  /**
   * Create a new object, based on the values of the controls.
   */
  private void createObject() {
    LOGGER.severe("=>");
    
    @SuppressWarnings("unchecked")
    E eObject = (E) eFactory.create(eClass);
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: eObjectEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      ObjectControl<?> objectInput = eObjectAttributeEditDescriptor.getObjectControl();
      if (objectInput.isFilledIn()) {
        Object value;
        value = objectInput.getValue();
        eObject.eSet(eObjectAttributeEditDescriptor.getStructuralFeature(), value);
      }
    }
    
    objects.add(eObject);
    
    LOGGER.severe("<= " + eObject.toString());
  }
}

