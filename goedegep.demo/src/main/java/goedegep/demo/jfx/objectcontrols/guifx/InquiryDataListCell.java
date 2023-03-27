package goedegep.demo.jfx.objectcontrols.guifx;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class InquiryDataListCell extends ListCell<InquiryData>{
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(InquiryDataListCell.class.getName());
  private final static FixedPointValueFormat FPV = new FixedPointValueFormat();
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private ComponentFactoryFx componentFactory;
  
  public InquiryDataListCell(CustomizationFx customization) {
    componentFactory = customization.getComponentFactoryFx();
  }
  
  @Override
  public void updateItem(InquiryData inquiryData, boolean empty) {
    super.updateItem(inquiryData, empty);
    if (empty  ||  inquiryData == null) {
      setGraphic(null);
    } else {
      setGraphic(createInquiryDataPanel(inquiryData));
    }
  }

  private Node createInquiryDataPanel(InquiryData inquiryData) {
    VBox vBox = componentFactory.createVBox(12.0, 12.0);
    Label label;
    
    label = componentFactory.createStrongLabel("InquiryData");
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("name: " + inquiryData.getName());
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("happy: " + inquiryData.isHappy());
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("birthplace: " + (inquiryData.getBirthPlace() != null ? inquiryData.getBirthPlace().getName() : null));
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("age: " + inquiryData.getAge());
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("traveler type: " + (inquiryData.getTravelerType() != null ? inquiryData.getTravelerType().toString() : null));
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("last travel ratin: " + (inquiryData.getLastTravelRating() != null ? FPV.format(inquiryData.getLastTravelRating()) : null));
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("last travel date: " + (inquiryData.getLastTravelDate() != null ? DTF.format(inquiryData.getLastTravelDate()) : null));
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("travel report filename: " + inquiryData.getTravelReportFilename());
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("next travel date: " + (inquiryData.getNextTravelDate() != null ? FDF.format(inquiryData.getNextTravelDate()) : null));
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("pictures folder: " + inquiryData.getPicturesFolder());
    vBox.getChildren().add(label);
    label = componentFactory.createLabel("image file: " + (inquiryData.getImageFile() != null ? inquiryData.getImageFile().getAbsolutePath() : null));
    vBox.getChildren().add(label);
    TextArea textArea = componentFactory.createTextArea(inquiryData.getNotes());
    vBox.getChildren().add(textArea);
    WebView webView = new WebView();
    webView.setMaxHeight(150.0);
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(inquiryData.getDetails());
    vBox.getChildren().add(webView);    
    
    return vBox;
  }

}
