package goedegep.demo.resources.guifx;

import java.net.URL;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.resources.ImageFileInfo;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.util.html.HtmlUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ImageResourceDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(ImageResourceDemo.class.getName());
  
  private final static String WINDOW_TITLE = "Demo";
  private final static String RADIO_BUTTON_FIXED_SIZES = "Fixed sizes";
  private final static String RADIO_BUTTON_AVAILABLE_FILES = "Available files";
    
  private ComboBox<ImageResourceWrapper> imageSelectionComboBox;
  private HBox differentSizesHBox;
  private ToggleGroup toggleGroup;

  public ImageResourceDemo(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    createGUI();
    
    show();
    
    if (!imageSelectionComboBox.getItems().isEmpty()) {
      imageSelectionComboBox.getSelectionModel().select(0);
    }
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    VBox vBox = new VBox();
    
    vBox.getChildren().add(createImagesOverviewPanel());
    vBox.getChildren().add(createDifferentSizeImagePanel());
    vBox.getChildren().add(createWebPanel());
    
    Scene scene = new Scene(vBox, 1500, 1200);
    setScene(scene);
  }

  /**
   * Show an overview of the images.
   * <p>
   * See the text of the <code>explanationTextArea</code> for details.
   * 
   * @return the images overview panel
   */
  private Node createImagesOverviewPanel() {
    VBox mainVBox = new VBox();
    
    TextArea explanationTextArea = new TextArea("""
            This panel shows all available images. The images are shown with an ImageSize.SIZE_2 (obtained via getImage(ImageSize.SIZE_2)).
            Below each image the following is shown:
              * all filenames for the image, with their image sizes (obtained via getImageFilesInfo(), imageFileInfo.filename(), imageFileInfo.width(), imageFileInfo.height().
              * the description of the image (obtained via getDescription())
              * the credits for the image (obtained via getCredits())
            """
        );
    explanationTextArea.setEditable(false);
    explanationTextArea.setMaxHeight(110);
    mainVBox.getChildren().add(explanationTextArea);
    
    FlowPane flowPane = new FlowPane();
    final ScrollPane scrollPane = new ScrollPane();

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
    scrollPane.setMinHeight(300);
    scrollPane.setContent(flowPane);
    scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
        @Override
        public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
            flowPane.setPrefWidth(bounds.getWidth());
            flowPane.setPrefHeight(bounds.getHeight());
        }
    });    
    
    for (ImageResource imageResource: ImageResource.values()) {
      flowPane.getChildren().add(createImagePanel(imageResource));
    }
    mainVBox.getChildren().add(scrollPane);
    
    return mainVBox;
  }

  /**
   * Create a panel for the details of an ImageResource.
   * <p>
   * The panel contains:
   * <ul>
   * <li>the image shown with an ImageSize.SIZE_2</li>
   * <li>all filenames for the image, with their image sizes</li>
   * <li>the description of the image</li>
   * <li>the credits for the image</li>
   * </ul>
   * 
   * @param imageResource
   * @return
   */
  private Node createImagePanel(ImageResource imageResource) {
    VBox vBox = new VBox();
    
    vBox.setPadding(new Insets(12.0));
    
    Image image = imageResource.getImage(ImageSize.SIZE_2);
    ImageView imageView = new ImageView(image);
    vBox.getChildren().add(imageView);
    
    for (ImageFileInfo imageFileInfo: imageResource.getImageFilesInfo()) {
      Label label = new Label(imageFileInfo.filename() + ": " + imageFileInfo.width() + " x " + imageFileInfo.height());
      vBox.getChildren().add(label);
    }
    Label description = new Label(imageResource.getDescription() != null ? imageResource.getDescription() : "<no description>");
    Label credits = new Label(imageResource.getCredits() != null ? imageResource.getCredits() : "<no credits>");
    credits.setMaxWidth(180);
    credits.setWrapText(true);
    vBox.getChildren().addAll(description, credits);
    
    return vBox;
  }
  
  /**
   * Create a panel which shows an image for different sizes.
   * 
   * @return
   */
  private Node createDifferentSizeImagePanel() {
    VBox mainVBox = new VBox();
    
    TextArea explanationTextArea = new TextArea("""
            In this panel you can select an image, which is then shown for different size.
            First the largest available image is shown, followed by all sizes defined by the ImageSize enum.
            """
        );
    explanationTextArea.setEditable(false);
    explanationTextArea.setMaxHeight(50);
    mainVBox.getChildren().add(explanationTextArea);
    
    
    VBox controlsVBox = new VBox();
    controlsVBox.setSpacing(12.0);
    ObservableList<ImageResourceWrapper> imageResourceWrappers = FXCollections.observableArrayList();
    
    for (ImageResource imageResource: ImageResource.values()) {
      imageResourceWrappers.add(new ImageResourceWrapper(imageResource));
    }
    
    imageSelectionComboBox = new ComboBox<>(imageResourceWrappers);
    imageSelectionComboBox.setOnAction((e) -> updateDifferentSizesBox());
    controlsVBox.getChildren().add(imageSelectionComboBox);
    
    toggleGroup = new ToggleGroup();
    RadioButton fixedSizesButton = new RadioButton(RADIO_BUTTON_FIXED_SIZES);
    fixedSizesButton.setToggleGroup(toggleGroup);
//    fixedSizesButton.setOnAction((e) -> updateDifferentSizesBox());
    RadioButton availableFilesButton = new RadioButton(RADIO_BUTTON_AVAILABLE_FILES);
    availableFilesButton.setToggleGroup(toggleGroup);
//    availableFilesButton.setOnAction((e) -> updateDifferentSizesBox());
    toggleGroup.selectedToggleProperty().addListener((c) -> updateDifferentSizesBox());
    controlsVBox.getChildren().addAll(fixedSizesButton, availableFilesButton);
    
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(12.0));
    
    hBox.getChildren().add(controlsVBox);
    
    mainVBox.getChildren().add(hBox);
    
    
    differentSizesHBox = new HBox();
    differentSizesHBox.setPadding(new Insets(12.0));
    hBox.getChildren().add(differentSizesHBox);
    
    
    return mainVBox;
  }
  
  private Node createBoxedImageView(Image image, String ... text) {
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(12.0));
    
    ImageView imageView = new ImageView(image);
    vBox.getChildren().add(imageView);
    for (String string: text) {
      Label label = new Label(string);
      vBox.getChildren().add(label);
    }
    
    return vBox;
  }
  
  /**
   * Update the box where images for different sizes are shown.
   */
  private void updateDifferentSizesBox() {
    differentSizesHBox.getChildren().clear();
    
    ImageResourceWrapper imageResourceWrapper = imageSelectionComboBox.getSelectionModel().getSelectedItem();
    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
    if (imageResourceWrapper != null  &&  selectedRadioButton != null) {
      
      ImageResource imageResource = imageResourceWrapper.getImageResource();

      if (selectedRadioButton.getText().equals(RADIO_BUTTON_FIXED_SIZES)) {
        Image image = imageResource.getImage();
        differentSizesHBox.getChildren().add(createBoxedImageView(image));

        for (ImageSize imageSize: ImageSize.values()) {
          image = imageResource.getImage(imageSize);
          String imageFilename = imageResource.getImageFilename(imageSize);
          differentSizesHBox.getChildren().add(createBoxedImageView(image, imageSize.getWidth() + "x" + imageSize.getHeight(), imageFilename));
        }
      } else {
        for (ImageFileInfo imageFileInfo: imageResource.getImageFilesInfo()) {
          Image image = imageResource.getImage(imageFileInfo.filename());
          differentSizesHBox.getChildren().add(createBoxedImageView(image, imageFileInfo.filename()));
        }
      }
    }
    
  }
  
  private Node createWebPanel() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("<html>");
    buf.append("<header>");
    buf.append("</header>");
    
    buf.append("<body>");
    buf.append("<h1>");
    buf.append("Example of using an ImageResource in generated HTML");
    buf.append("</h1>");
    
    buf.append("Walking icon (at 16x16): ");
    buf.append("<img src=\"");
    URL url = ImageResource.WALKING.getImageUrl(ImageSize.SIZE_0);
    buf.append(HtmlUtil.encodeHTML(url.toString()));
    buf.append("\" height=\"16\" width=\"16\"/> ");

    buf.append("<br/>");
    
    buf.append("Cycling image (largest available): ");
    buf.append("<img src=\"");
    url = ImageResource.CYCLING.getImageUrl();
    buf.append(HtmlUtil.encodeHTML(url.toString()));
    buf.append("\"");
    
    buf.append("</body>");
    
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(buf.toString());
    
    return webView;
  }
}

class ImageResourceWrapper {
  private ImageResource imageResource;
  
  ImageResourceWrapper(ImageResource imageResource) {
    this.imageResource = imageResource;
  }
  
  public String toString() {
    return imageResource.name();
  }

  public ImageResource getImageResource() {
    return imageResource;
  }
    
}
