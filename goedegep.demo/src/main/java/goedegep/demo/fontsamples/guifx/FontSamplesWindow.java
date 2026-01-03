package goedegep.demo.fontsamples.guifx;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * This class shows samples of fonts.
 */
public class FontSamplesWindow extends JfxStage {
  private final static String WINDOW_TITLE = "Font samples";
  
  private ComboBox<String> fontWeightComboBox;
  private TextFlow textFlow;

  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   */
  public FontSamplesWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    createGUI();
    
    show();
    
    fontWeightComboBox.getSelectionModel().select("NORMAL");
  }

  /**
   * Create the GUI
   * <p>
   * At the top there is a ComboBox to select the font weight.
   * Below that there is a ScrollPane showing the text in each font and in the selected weight.
   */
  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox();
    
    HBox hBox = componentFactory.createHBox(20.0, 20.0);
    hBox.setAlignment(Pos.CENTER_LEFT);
    Label label = componentFactory.createLabel("Font weight:");
    String fontWeightNames[] = {
        "BLACK",
        "BOLD",
        "EXTRA_BOLD",
        "EXTRA_LIGHT",
        "LIGHT",
        "MEDIUM",
        "NORMAL",
        "SEMI_BOLD",
        "THIN"};
    fontWeightComboBox = componentFactory.createComboBox(FXCollections.observableArrayList(fontWeightNames));
    fontWeightComboBox.setOnAction((_) -> redrawSamples());
    hBox.getChildren().addAll(label, fontWeightComboBox);
    mainPanel.getChildren().add(hBox);
    
    textFlow = new TextFlow();
    textFlow.setLineSpacing(20.0f);    
    ScrollPane scrollPane = new ScrollPane(textFlow);
    mainPanel.getChildren().add(scrollPane);

    Scene scene = new Scene(mainPanel, 1600, 1200);

    setScene(scene);
  }

  private void redrawSamples() {
    textFlow.getChildren().clear();
    

    for (String fontName: Font.getFontNames()) {
      Text textRegular = new Text(fontName + ": This is how a REGULAR text in this font looks like -");
      textRegular.setFill(Color.GREEN);
      Font fontRegular = Font.font(fontName, FontWeight.valueOf(fontWeightComboBox.getValue()), FontPosture.REGULAR, 25);
      textRegular.setFont(fontRegular);
      
      Text textItalic = new Text(" This is how an ITALIC text in this font looks like\n");
      textItalic.setFill(Color.GREEN);
      Font fontItalic = Font.font(fontName, FontWeight.valueOf(fontWeightComboBox.getValue()), FontPosture.ITALIC, 25);
      textItalic.setFont(fontItalic);

      textFlow.getChildren().addAll(textRegular, textItalic);
    }
  }
}
