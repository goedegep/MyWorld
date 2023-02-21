package goedegep.gpx.app;

import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DetailsView extends JfxStage  {
  private DocumentRoot documentRoot;
  private ComponentFactoryFx componentFactory;
  
  public DetailsView(CustomizationFx customization, DocumentRoot documentRoot) {
    super("GPX File Details", customization);
    
    componentFactory = customization.getComponentFactoryFx();
    this.documentRoot = documentRoot;
    
    createGUI(documentRoot);
    
    show();
  }
    
    private void createGUI(DocumentRoot documentRoot) {
      GpxType gpxType = documentRoot.getGpx();
      GridPane mainLayout = componentFactory.createGridPane(12.0, 12.0);
      mainLayout.setPadding(new Insets(12.0));
      int row = 0;
      
      Label label;
      TextField textField;
      
      // Name
      label = componentFactory.createLabel("Name: ");
      mainLayout.add(label, 0, row);
      textField = componentFactory.createTextField(null, 400, null);
      MetadataType metadataType = gpxType.getMetadata();
      if (metadataType != null) {
        textField.setText(metadataType.getName());
      }
      mainLayout.add(textField, 1, row);
      
      row++;
      
      // Length
      label = componentFactory.createLabel("Length (km): ");
      mainLayout.add(label, 0, row);
      textField = componentFactory.createTextField(String.format("%1$.1f", gpxType.getLength()/1000d), 400, null);
      mainLayout.add(textField, 1, row);
      
      row++;
      
      // Total ascend
      label = componentFactory.createLabel("Total ascend (m): ");
      mainLayout.add(label, 0, row);
      textField = componentFactory.createTextField(((Double)gpxType.getCumulativeAscent()).toString(), 400, null);
      mainLayout.add(textField, 1, row);
      
      
      setScene(new Scene(mainLayout));
    }
}
