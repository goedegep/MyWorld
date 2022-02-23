package goedegep.rolodex.app.guifx;

import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReferredByPanel {
  private static final Logger LOGGER = Logger.getLogger(ReferredByPanel.class.getName());

  private CustomizationFx customization;
  private String title;
  private VBox panel;
  private ObservableList<String> items;
  private ListView<String> listView;
  
  public ReferredByPanel(CustomizationFx customization, String title) {
    this.customization = customization;
    this.title = title;
    
    createGui();
  }
  
  private void createGui() {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    panel = componentFactory.createVBox();
    panel.setMinHeight(200);
    
    HBox hBox = componentFactory.createHBox(new Insets(12));
    Label label = componentFactory.createStrongLabel(title);
    hBox.getChildren().add(label);
    panel.getChildren().add(hBox);
    
    listView = new ListView<String>();
    listView.setMaxHeight(100);
    items = FXCollections.observableArrayList();
    listView.setItems(items);
    panel.getChildren().add(listView);
    
  }
  
  public Node getPanel() {
    return panel;
  }
  
  public void setObject(EObject eObject) {
    items.clear();
    
    if (eObject == null) {
      return;
    }
    
    ResourceSet resourceSet = null;
    Resource resource = eObject.eResource();
    if (resource != null) {
      resourceSet = resource.getResourceSet();
    }
    
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObject, resourceSet);

    for (EStructuralFeature.Setting setting: settings) {
      EObject referringObject = setting.getEObject();
      LOGGER.info("Reference: " + setting.getEStructuralFeature().getName() + ": " + referringObject.toString());
      items.add(setting.getEStructuralFeature().getName() + ": " + referringObject.toString());
    }
      
  }
}
