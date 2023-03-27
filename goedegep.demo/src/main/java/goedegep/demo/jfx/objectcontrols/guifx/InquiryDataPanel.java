package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;

public class InquiryDataPanel extends ListView<InquiryData> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(InquiryDataPanel.class.getName());
  
  private ComponentFactoryFx componentFactory;
  
  public InquiryDataPanel(CustomizationFx customization) {
    componentFactory = customization.getComponentFactoryFx();
    
    setWidth(600.0);
    setHeight(1000.0);
    setPadding(new Insets(12.0));
    setBorder(componentFactory.getRectangularBorder());
    this.setCellFactory((object) -> new InquiryDataListCell(customization));
  }

  
}
