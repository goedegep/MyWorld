package goedegep.jfx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Logger;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import goedegep.configuration.model.Look;
import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.editor.controls.EditorControlBoolean;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.jfx.editor.controls.EditorControlFolderSelecter;
import goedegep.jfx.editor.controls.EditorControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFixedPointValue;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlInteger;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objectcontrols.ObjectControlWGS84Coordinates;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import goedegep.resources.ImageSize;
import goedegep.util.datetime.FlexDate;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComponentFactoryFx {
  private static final Logger LOGGER = Logger.getLogger(ComponentFactoryFx.class.getName());
  
  private static BorderStroke BLACK_RECTANGULAR_BORDER_STROKE = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
  public static Border BLACK_RECTANGULAR_BORDER = new Border(BLACK_RECTANGULAR_BORDER_STROKE);
  
  private CustomizationFx customization;
  private AppResourcesFx appResources;
  
  private Color boxBackgroundColor = null;
  private Color panelBackgroundColor = null;
  private Color textFieldBackgroundColor = null;
  private Color borderColor = null;
  private Color buttonBackgroundColor = null;
  
  /**
   * Background (Color) for Panes.
   */
  private Background panelBackground = null;
  private Background textFieldBackground = null;
  private Background labelBackground = null;
  
  private String buttonHexColorValue1 = null;
  private String buttonHexColorValue2 = null;
  private String buttonHexColorValue3 = null;
  private String buttonHexColorValue4 = null;
//  private String boxHexColorValue1 = null;
//  private String boxHexColorValue2 = null;
//  private String boxHexColorValue3 = null;
//  private String boxHexColorValue4 = null;
  private String panelBackgroundHexColorValue = null;
  private String textFieldBackgroundHexColorValue = null;

  private BorderStroke borderStroke = null;
  private Border rectangularBorder = null;

  /**
   * Constructor.
   * 
   * @param look the color definitions for this factory (may be null).
   */
  public ComponentFactoryFx(CustomizationFx customization) {
    this.customization = customization;
    Look look = customization.getLook();
    AppResourcesFx appResources = customization.getResources();
    
    if (look != null) {
      panelBackgroundColor = look.getPanelBackgroundColor();
      borderColor = createColor(look.getButtonBackgroundColor(), 0.3);
      buttonBackgroundColor = look.getButtonBackgroundColor();
      
      boxBackgroundColor = look.getBoxBackgroundColor();
      Color color1 = new Color(0.8 * boxBackgroundColor.getRed(), 0.8 * boxBackgroundColor.getGreen(), 0.8 * boxBackgroundColor.getBlue(), 1.0);
      Color color2 = new Color(0.95 * boxBackgroundColor.getRed(), 0.95 * boxBackgroundColor.getGreen(), 0.95 * boxBackgroundColor.getBlue(), 1.0);
      Color color3 = new Color(1.0 * boxBackgroundColor.getRed(), 1.0 * boxBackgroundColor.getGreen(), 1.0 * boxBackgroundColor.getBlue(), 1.0);
      Color color4 = new Color(0.9 * boxBackgroundColor.getRed(), 0.9 * boxBackgroundColor.getGreen(), 0.9 * boxBackgroundColor.getBlue(), 1.0);
//      boxHexColorValue1 = JfxUtil.colorToCssString(color1);
//      boxHexColorValue2 = JfxUtil.colorToCssString(color2);
//      boxHexColorValue3 = JfxUtil.colorToCssString(color3);
//      boxHexColorValue4 = JfxUtil.colorToCssString(color4);
      
      panelBackground = new Background(new BackgroundFill(look.getPanelBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY));
      
      // For labels often no background is used
      if (look.getLabelBackgroundColor() != null) {
        labelBackground = new Background(new BackgroundFill(look.getLabelBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY));
      }

      panelBackgroundHexColorValue = JfxUtil.colorToCssString(look.getPanelBackgroundColor());
      textFieldBackgroundHexColorValue = JfxUtil.colorToCssString(look.getTextFieldBackgroundColor());
      textFieldBackground = new Background(new BackgroundFill(look.getTextFieldBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY));

      textFieldBackgroundColor = look.getTextFieldBackgroundColor();
      color1 = new Color(0.8 * buttonBackgroundColor.getRed(), 0.8 * buttonBackgroundColor.getGreen(), 0.8 * buttonBackgroundColor.getBlue(), 1.0);
      color2 = new Color(0.95 * buttonBackgroundColor.getRed(), 0.95 * buttonBackgroundColor.getGreen(), 0.95 * buttonBackgroundColor.getBlue(), 1.0);
      color3 = new Color(1.0 * buttonBackgroundColor.getRed(), 1.0 * buttonBackgroundColor.getGreen(), 1.0 * buttonBackgroundColor.getBlue(), 1.0);
      color4 = new Color(0.9 * buttonBackgroundColor.getRed(), 0.9 * buttonBackgroundColor.getGreen(), 0.9 * buttonBackgroundColor.getBlue(), 1.0);

      buttonHexColorValue1 = JfxUtil.colorToCssString(color1);
      buttonHexColorValue2 = JfxUtil.colorToCssString(color2);
      buttonHexColorValue3 = JfxUtil.colorToCssString(color3);
      buttonHexColorValue4 = JfxUtil.colorToCssString(color4);
    }
    this.appResources = appResources;
  }
  
  private Color createColor(Color baseColor, double factor) {
    Double red = baseColor.getRed();
    Double green = baseColor.getGreen();
    Double blue = baseColor.getBlue();
    
    return new Color(red * factor,  green * factor, blue* factor, 1.0);
  }
  
  /*
   * Menu's
   */
  
  /**
   * Create a MenuBar.
   * 
   * @return the created MenuBar.
   */
  public MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    
    if (panelBackground != null) {
      Stop[] menuBarLinearGradientStops = {
          new Stop(0.0, panelBackgroundColor),
          new Stop(0.9, buttonBackgroundColor)
      };
      LinearGradient menuBarLinearGradient = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, menuBarLinearGradientStops);
      BackgroundFill menuBarBackgroundFill = new BackgroundFill(menuBarLinearGradient, CornerRadii.EMPTY, null);
      Background menuBarBackground = new Background(menuBarBackgroundFill);
      menuBar.setBackground(menuBarBackground);
      Color color = new Color(0.75 * boxBackgroundColor.getRed(), 0.75 * boxBackgroundColor.getGreen(), 0.75 * boxBackgroundColor.getBlue(), 1.0);
      menuBar.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
//      menuBar.setStyle("-fx-background-color: " + panelBackgroundHexColorValue + "; -fx-border-style: hidden hidden solid hidden; -fx-border-color: " + boxHexColorValue3 + "; -fx-border-width: 2");
    }
    
    return menuBar;
  }
  
  /**
   * Create a Menu.
   * 
   * @param text the menu text.
   * @return the created Menu.
   */
  public Menu createMenu(String text) {
    Menu menu = new Menu(text);
    
    return menu;
  }
  
  /**
   * Create a ContextMenu.
   * 
   * @return the created ContextMenu.
   */
  public ContextMenu createContextMenu() {
    ContextMenu contextMenu = new ContextMenu();
    
    return contextMenu;
  }

  /**
   * Add a context menu to a node.
   * 
   * @param node the Node to which the context menu is to be added.
   * @param contextMenu the ContextMenu to add to <code>node</code>.
   */
  public static void setContextMenuOnNode(Node node, ContextMenu contextMenu) {
    node.setOnMouseClicked(mouseEvent -> {
      if (mouseEvent.getButton() == MouseButton.SECONDARY) {
        contextMenu.show(node, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY());
        mouseEvent.consume();
      }
    });
  }
  
  /**
   * Create a MenuItem.
   * 
   * @param text the menu item text.
   * @return the created MenuItem.
   */
  public MenuItem createMenuItem(String text) {
    MenuItem menuItem = new MenuItem(text);
    
    return menuItem;
  }
  
  /**
   * Create a CheckMenuItem.
   * 
   * @param text the menu item text.
   * @return the created CheckMenuItem.
   */
  public CheckMenuItem createCheckMenuItem(String text) {
    CheckMenuItem menuItem = new CheckMenuItem(text);
    
    return menuItem;
  }
  
  /**
   * Create a RadioMenuItem.
   * 
   * @param text the menu item text.
   * @return the created RadioMenuItem.
   */
  public RadioMenuItem createRadioMenuItem(String text) {
    RadioMenuItem menuItem = new RadioMenuItem(text);
    
    return menuItem;
  }
  
  
  /*
   * Panes (panels, layouts)
   */
  
  /**
   * Customize a Pane (e.g. HBox, VBox), i.e. apply the right look.
   * 
   * @param pane the Pane to be customized.
   */
  public void customizePane(Pane pane) {
    if (panelBackground != null) {
      pane.setBackground(panelBackground);
    }
  }
  
  /**
   * Create an HBox with a default spacing of 0 and no padding.
   * 
   * @return the newly created HBox
   */
  public HBox createHBox() {
    HBox hBox = new HBox();
    customizePane(hBox);
    
    return hBox;
  }
  
  /**
   * Create an HBox with a specified spacing and no padding.
   * 
   * @param spacing the space between the children.
   * @return the newly created HBox
   */
  public HBox createHBox(double spacing) {
    HBox hBox = new HBox(spacing);
    customizePane(hBox);
    
    return hBox;
  }
  
  /**
   * Create an HBox with a specified padding.
   * 
   * @param padding the gap between the children and the border of the box.
   * @return the newly created HBox
   */
  public HBox createHBox(Insets padding) {
    HBox hBox = new HBox();
    hBox.setPadding(padding);
    customizePane(hBox);

    return hBox;
  }
  
  /**
   * Create an HBox with a specified spacing and padding.
   * 
   * @param spacing the space between the children.
   * @param padding the gap between the children and the border of the box.
   * @return the newly created HBox
   */
  public HBox createHBox(double spacing, double padding) {
    HBox hBox = new HBox(spacing);
    hBox.setPadding(new Insets(padding));
    customizePane(hBox);

    return hBox;
  }
  
  /**
   * Create an HBox with a specified spacing and padding.
   * 
   * @param spacing the space between the children.
   * @param padding the gap between the children and the border of the box.
   * @return the newly created HBox
   */
  public HBox createHBox(double spacing, Insets padding) {
    HBox hBox = new HBox(spacing);
    hBox.setPadding(padding);
    customizePane(hBox);

    return hBox;
  }
  
  /**
   * Create a VBox with a default spacing of 0.
   * 
   * @return the newly created VBox
   */
  public VBox createVBox() {
    VBox vBox = new VBox();
    customizePane(vBox);
    
    return vBox;
  }
  
  /**
   * Create a VBox with a specified spacing and no padding.
   * 
   * @param spacing the space between the children.
   * @return the newly created VBox
   */
  public VBox createVBox(double spacing) {
    VBox vBox = new VBox(spacing);
    customizePane(vBox);
    
    return vBox;
  }
  
  /**
   * Create a VBox with a specified spacing and padding.
   * 
   * @param spacing the space between the children.
   * @param padding the gap between the children and the border of the box.
   * @return the newly created VBox
   */
  public VBox createVBox(double spacing, double padding) {
    VBox vBox = new VBox(spacing);
    vBox.setPadding(new Insets(padding));
    
    customizePane(vBox);
    
    return vBox;
  }
  
  /**
   * Create a GridPane.
   * 
   * @return the new created GridPane
   */
  public GridPane createGridPane() {
    GridPane gridPane = new GridPane();
    customizePane(gridPane);

    return gridPane;
  }
  
  /**
   * Create a GridPane with specified horizontal and vertical gaps.
   * 
   * @param hGap the horizontal gap between elements.
   * @param vGap the vertical gap between elements.
   * @return the new created GridPane
   */
  public GridPane createGridPane(double hGap, double vGap) {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(hGap);
    gridPane.setVgap(vGap);
    customizePane(gridPane);

    return gridPane;
  }
  
  /**
   * Create a GridPane with specified horizontal and vertical gaps and padding.
   * 
   * @param hGap the horizontal gap between elements.
   * @param vGap the vertical gap between elements.
   * @param padding the gap between the elements and the border of the box.
   * @return the new created GridPane
   */
  public GridPane createGridPane(double hGap, double vGap, double padding) {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(hGap);
    gridPane.setVgap(vGap);
    gridPane.setPadding(new Insets(padding));
    customizePane(gridPane);

    return gridPane;
  }
  

  /*
   * Basic controls
   */
  
  /**
   * Create a {@link Label}.
   * 
   * @param text the label text.
   * @return a Label with the specified properties.
   */
  public Label createLabel(String text) {
    return createLabel(text, -1);
  }
  
  /**
   * Create a {@link Label} with a preferred width.
   * 
   * @param text the label text.
   * @param width the preferred width.
   * @return a Label with the specified properties.
   */
  public Label createLabel(String text, int width) {
    Label label = new Label(text);
    
    if (width != -1) {
      label.setPrefWidth(width);
    }
    
    if (labelBackground != null) {
      label.setBackground(labelBackground);
    }

    return label;
  }

  /**
   * Create a {@link Label} with a strong (e.g. bold) font.
   * 
   * @param text the label text.
   * @return a Label with the specified properties.
   */
  public Label createStrongLabel(String text) {
    Label label = createLabel(text);
    
    label.setStyle("-fx-font-weight: bold;");

    return label;
  }

  /**
   * Create a {@link ComboBox}.
   * 
   * @param <E> the type of the items.
   * @param items the items in the combo box.
   * @return the created ComboBox.
   */
  public <E> ComboBox<E> createComboBox(Collection<E> items) {
    ComboBox<E> comboBox = new ComboBox<>();
    customizeButton(comboBox);
//    if (boxHexColorValue1 != null) {
//      CornerRadii cornerRadii = new CornerRadii(3.0);
//      comboBox.setStyle("-fx-padding: 0 10 0 10;" + 
//          "-fx-background-color:linear-gradient(" + boxHexColorValue1 + " 0%, " + boxHexColorValue2 + " 25%, " + boxHexColorValue3 + " 75%, " + boxHexColorValue4 + " 100%);" + 
//          "-fx-background-insets: 0;" + 
//          "-fx-background-radius: 3;");
//      Color borderColor = new Color(0.7 * boxBackgroundColor.getRed(), 0.7 * boxBackgroundColor.getGreen(), 0.7 * boxBackgroundColor.getBlue(), 1.0);
//      comboBox.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, cornerRadii, new BorderWidths(1, 2, 1, 2))));
//    }
    
    if (items != null) {
      comboBox.getItems().addAll(items);
    }
    
    return comboBox;
  }

  /**
   * Create a {@link ListView}.
   * 
   * @param <T> the type of the items.
   * @param items the items in the list view.
   * @return the created ListView.
   */
  public <T> ListView<T> createListView(ObservableList<T> items) {
    ListView<T> listView = new ListView<>(items);
    
    return listView;
  }
  
  /**
   * Create an {@link AutoCompleteTextField}.
   */
  public AutoCompleteTextField createAutoCompleteTextField() {
    AutoCompleteTextField autoCompleteTextField = new AutoCompleteTextField();
    
    customizeTextInputControl(autoCompleteTextField);
    
    return autoCompleteTextField;
  }
  
  /**
   * Create an HTMLEditor.
   */
  public HTMLEditor createHTMLEditor() {
    HTMLEditor htmlEditor = new HTMLEditor();
    
    return htmlEditor;
  }
    
  
  /**
   * Customize a button.
   * 
   * @param button the Button to be customized.
   */
  public void customizeButton(Node button) {
    if (buttonHexColorValue1 != null) {
      CornerRadii cornerRadii = new CornerRadii(3.0);
      button.setStyle("-fx-padding: 3 10 3 10;-fx-font-weight: bold;" + 
          "-fx-background-color:linear-gradient(" + buttonHexColorValue1 + " 0%, " + buttonHexColorValue2 + " 25%, " + buttonHexColorValue3 + " 75%, " + buttonHexColorValue4 + " 100%);" + 
          "-fx-background-insets: 0;" + 
          "-fx-background-radius: 3;");
      Color borderColor = new Color(0.7 * textFieldBackgroundColor.getRed(), 0.7 * textFieldBackgroundColor.getGreen(), 0.7 * textFieldBackgroundColor.getBlue(), 1.0);
      
      if (button instanceof Button) {
        ((Button) button).setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, cornerRadii, new BorderWidths(1, 2, 1, 2))));
      }
      if (button instanceof ComboBox) {
        ((ComboBox<?>) button).setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, cornerRadii, new BorderWidths(1, 2, 1, 2))));
      }
    }
  }
    
  /**
   * Create a button without an image.
   * 
   * @param text the text shown on the Button (may be null).
   * @param toolTip the text shown as Tooltip (may be null).
   * @return the newly created Button
   */
  public Button createButton(String text, String toolTip) {
    return createButton(text, (ImageView) null, toolTip);
  }

  /**
   * Create a Button with an image.
   * 
   * @param text the text shown on the Button (may be null).
   * @param image the image shown on the Button (may be null).
   * @param toolTip the text shown as Tooltip (may be null).
   * @return the newly created Button
   */
  public Button createButton(String text, Image image, String toolTip) {
    ImageView imageView = new ImageView(image);
    return createButton(text, imageView, toolTip);
  }

  /**
   * Create a Button with an image.
   * 
   * @param text the text shown on the Button (may be null).
   * @param graphic the image shown on the Button (may be null).
   * @param toolTip the text shown as Tooltip (may be null).
   * @return the newly created Button
   */
  public Button createButton(String text, ImageView graphic, String toolTip) {
    Button button = new Button();
    
    if (text != null) {
      button.setText(text);
    }
    
    if (graphic != null) {
      button.setGraphic(graphic);
    }
    
    if (toolTip != null) {
      button.setTooltip(new Tooltip(toolTip));
    }
    
    customizeButton(button);

    return button;
  }
  
  /**
   * Create a tool button.
   * <p>
   * This button typically contains the name of the tool and the related image.<br/>
   * The button lightens up when the mouse moves over it.
   * 
   * @param text the text to be shown on the button.
   * @param image the image to be shown on the button.
   * @return return a button with the specified values.
   */
  public Button createToolButton(String text, Image image, String toolTip) {
    Button button = createButton(text, toolTip);
    button.setMaxWidth(100);
    button.setMinWidth(100);
    button.setMinHeight(100);
    button.wrapTextProperty().setValue(true);
    
    if (panelBackgroundHexColorValue != null) {
      button.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-background-color: " + panelBackgroundHexColorValue + ";");
    }
    
    if (image != null) {
      ImageView buttonImageView = new ImageView(image);
      buttonImageView.setFitWidth(48);
      buttonImageView.setPreserveRatio(true);
      buttonImageView.setSmooth(true);
      button.setGraphic(buttonImageView);
      button.setContentDisplay(ContentDisplay.TOP);
    }

    ColorAdjust colorAdjust = new ColorAdjust();
    final double normalBrightnessAdjust = -0.1;
    colorAdjust.setBrightness(normalBrightnessAdjust);

    button.setEffect(colorAdjust);
    button.setOnMouseEntered(_ -> {
      Timeline highlightTimeline = new Timeline(
          new KeyFrame(Duration.seconds(0.2), new KeyValue(colorAdjust.brightnessProperty(), 0.4, Interpolator.LINEAR)));
      highlightTimeline.play();
    });
    button.setOnMouseExited(_ -> {
      Timeline backToNormalTimeline = new Timeline(
          new KeyFrame(Duration.seconds(1), new KeyValue(colorAdjust.brightnessProperty(), normalBrightnessAdjust, Interpolator.LINEAR)
              ));
      backToNormalTimeline.play();
    });

    return button;
  }
  
  /**
   * Create an information button.
   * <p>
   * This is a button (with an 'i' as text) that, when pressed, shows a text dialog with information.
   * 
   * @param toolTip a tooltip text for the button.
   * @param infoDialogTitle the title for the information dialog.
   * @param image the image for the graphic in the information dialog.
   * @param infoSupplier a method that supplies the markdown text for the information dialog.
   * @return the newly created information button.
   */
  public Button createInfoButton(String toolTip, String infoDialogTitle, Image image,
      java.util.function.Supplier<String> infoSupplier) {
    Button button = createButton("i", toolTip);
    
    button.setOnAction(_ -> createMarkdownTextDialog(infoDialogTitle, image, null, infoSupplier.get()).showAndWait());
    
    return button;
  }
  
  /**
   * Create a {@link CheckBox}.
   * <p>
   * Note: the color of the checkBox is always the default value.
   * 
   * @param text the text added to the CheckBox.
   * @param selected if true, the CheckBox is set to be selected.
   * @return the newly created CheckBox.
   */
  public CheckBox createCheckBox(String text, boolean selected) {
    CheckBox checkBox = new CheckBox();
    if (text != null) {
      checkBox.setText(text);
    }
    checkBox.setSelected(selected);

    return checkBox;
  }
  
  /**
   * Create a {@link Text}.
   * 
   * @param string the text to put in the Text.
   * 
   * @return the newly created Text.
   */
  public Text createText(String string) {
    Text text = new Text(string);
    if (textFieldBackgroundHexColorValue != null) {
      text.setStyle("-fx-background-color: " + textFieldBackgroundHexColorValue);
    }
    
    return text;
  }

  // TextField, TextArea and customization.

  /**
   * Customize a TextInputControl.
   * 
   * @param textInputControl the TextInputControl to be customized.
   */
  public void customizeTextInputControl(TextInputControl textInputControl) {
    if (textFieldBackground != null) {
      textInputControl.setBackground(textFieldBackground);
    }
  }
  
  /**
   * Create an empty {@link TextField}.
   * 
   * @param width The preferred width of the TextField (-1 indicates use default width).
   * @param toolTipText The tooltip text for the TextField (which may be null).
   * @return the newly created TextField
   */
  public TextField createTextField(double width, String toolTipText) {
    return createTextField(null, width, toolTipText);
  }

  /**
   * Create a {@link TextField}
   * 
   * @param text The initial text of the TextField (which may be null).
   * @param width The preferred width of the TextField (-1 indicates use default width).
   * @param toolTipText The tooltip text for the TextField (which may be null).
   * @return the newly created TextField
   */
  public TextField createTextField(String text, double width, String toolTipText) {
    TextField textField = new TextField();

    if (text != null) {
      textField.setText(text);
    }
    
    if (width != -1) {
      textField.setPrefWidth(width);
    }

    if (toolTipText != null) {
      textField.setTooltip(new Tooltip(toolTipText));
    }
    
    customizeTextInputControl(textField);
    
    return textField;
  }

  /**
   * Create an empty {@link TextArea}.
   * 
   * @return the newly created TextArea.
   */
  public TextArea createTextArea() {
    TextArea textArea = new TextArea();
    customizeTextInputControl(textArea);
    
    return textArea;
  }

  /**
   * Create a {@link TextArea} with an initial text.
   * 
   * @param text the initial text for the TextArea.
   * @return the newly created TextArea.
   */
  public TextArea createTextArea(String text) {
    TextArea textArea = new TextArea(text);
    customizeTextInputControl(textArea);
    
    return textArea;
  }
  
  /**
   * Create a {@link Slider}.
   * 
   */
  public Slider createSlider(double min, double max, double value) {
    Slider slider = new Slider(min, max, value);
    
    return slider;
  }

  /**
   * Create a {@link FileChooser}.
   * 
   * @param title the title of the FileChooser.
   * @return the newly created FileChooser.
   */
  public FileChooser createFileChooser(String title) {
    FileChooser fileChooser = new FileChooser();
    if (title != null) {
      fileChooser.setTitle(title);
    }

    return fileChooser;
  }

  /**
   * Create a {@link DirectoryChooser}.
   * 
   * @param title the title of the DirectoryChooser.
   * @return the newly created DirectoryChooser.
   */
  public DirectoryChooser createDirectoryChooser(String title) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    if (title != null) {
      directoryChooser.setTitle(title);
    }

    return directoryChooser;
  }
  
  /**
   * Create a {@link ObjectControlFileSelecter}.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the FileChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @return the newly created FolderSelecter
   */
  @Deprecated
  public ObjectControlFileSelecter createFileSelecterObjectControl(int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean isOptional) {
    ObjectControlFileSelecter fileSelecter = new ObjectControlFileSelecter(customization, textFieldWidth, textFieldToolTipText,
        folderChooserButtonText, folderChooserButtonToolTipText, directoryChooserTitle, isOptional);
    
    customizeTextInputControl(fileSelecter.getControl());
    customizeButton(fileSelecter.getFileChooserButton());
    
    return fileSelecter;
  }
  
  /**
   * Create an {@link EditorControlFileSelecter}.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the FileChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @return the newly created FolderSelecter
   */
  public EditorControlFileSelecter createEditorControlFileSelecter(int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean isOptional) {
    EditorControlFileSelecter fileSelecter = EditorControlFileSelecter.newInstance(customization, textFieldWidth, textFieldToolTipText,
        folderChooserButtonText, folderChooserButtonToolTipText, directoryChooserTitle, isOptional);
    
    customizeTextInputControl(fileSelecter.getControl());
    customizeButton(fileSelecter.getFileChooserButton());
    
    return fileSelecter;
  }
  
  /**
   * Create a {@link ObjectControlFolderSelecter}.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the DirectoryChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @return the newly created FolderSelecter
   */
  @Deprecated
  public ObjectControlFolderSelecter createFolderSelecter(int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean isOptional) {
    ObjectControlFolderSelecter folderSelecter = new ObjectControlFolderSelecter(customization, textFieldWidth, textFieldToolTipText,
        folderChooserButtonText, folderChooserButtonToolTipText, directoryChooserTitle, isOptional);
    
    customizeTextInputControl(folderSelecter.getControl());
    customizeButton(folderSelecter.getFolderChooserButton());
    
    return folderSelecter;
  }
  
  /**
   * Create an {@link EditorControlFolderSelecter}.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the DirectoryChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @return the newly created FolderSelecter
   */
  public EditorControlFolderSelecter createEditorControlFolderSelecter(int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean isOptional) {
    EditorControlFolderSelecter folderSelecter = EditorControlFolderSelecter.newInstance(customization, textFieldWidth, textFieldToolTipText,
        folderChooserButtonText, folderChooserButtonToolTipText, directoryChooserTitle, isOptional);
    
    customizeTextInputControl(folderSelecter.getControl());
    customizeButton(folderSelecter.getFolderChooserButton());
    
    return folderSelecter;
  }
  
  /**
   * Create a {@link ProgressBar}.
   * 
   * @param progress initial progress indication, represented as a value between 0 and 1.
   * @return the newly created progress bar.
   */
  public ProgressBar createProgressBar(double progress) {
    ProgressBar progressBar = new ProgressBar(progress);
    
    if (panelBackground != null) {
      progressBar.setBackground(panelBackground);
    }
    
    return progressBar;
  }
  
  /*
   * Borders
   */
  
  /**
   * Get the {@link  BorderStroke}.
   * <p>
   * The BorderStroke for a rectangular border. The color is the border color, if set, or black otherwise.
   * 
   * @return the BorderStroke.
   */
  public BorderStroke getBorderStroke() {
    if (borderStroke == null) {
      borderStroke = new BorderStroke(borderColor != null ? borderColor : Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
    }
    
    return borderStroke;
  }
  
  /**
   * Get the rectangular border.
   * <p>
   * The color is the border color, if set, or black otherwise.
   * 
   * @return the rectangular Border.
   */
  public Border getRectangularBorder() {
    if (rectangularBorder == null) {
      rectangularBorder = new Border(getBorderStroke());
    }

    return rectangularBorder;
  }
  
  /*
   * ObjectControls
   */

  /**
   * Create a Boolean ObjectControl
   * 
   * @param text The text shown beside the CheckBox.
   * @param selected the initial selection status for the CheckBox.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlBoolean}.
   */
  @Deprecated
  public ObjectControlBoolean createObjectControlBoolean(String text, boolean selected, boolean isOptional, String toolTipText) {
    ObjectControlBoolean objectControlBoolean = new ObjectControlBoolean(customization, text, selected, isOptional, toolTipText);

//    if (panelBackgroundHexColorValue != null) {
//      objectControlBoolean.getControl().setStyle("-fx-background-color: " + panelBackgroundHexColorValue);
//    }

    return objectControlBoolean;
  }

  /**
   * Create an Integer ObjectControl.
   * 
   * @param integer The initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlInteger}.
   */
  @Deprecated
  public ObjectControlInteger createObjectControlInteger(Integer integer, double width, boolean isOptional, String toolTipText) {
    ObjectControlInteger objectControlInteger = new ObjectControlInteger(customization, integer, width, isOptional, toolTipText);

//    customizeTextInputControl(objectControlInteger);

    return objectControlInteger;
  }

  /**
   * @deprecated
   * Create a String ObjectControl.
   * 
   * @param text The initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlString}.
   */
  public ObjectControlString createObjectControlString(String text, double width, boolean isOptional, String toolTipText) {
    ObjectControlString objectControlString = new ObjectControlString(customization, text, width, isOptional, toolTipText);

    return objectControlString;
  }

  /**
   * Create a Boolean EditorControl.
   * 
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlBoolean}.
   */
  public EditorControlBoolean createEditorControlBoolean(String id, String labelBaseText, String toolTipText) {
    EditorControlBoolean editorControlBoolean = EditorControlBoolean.newInstance(customization, id, labelBaseText, toolTipText);

    return editorControlBoolean;
  }

  /**
   * Create a Multi Line String ObjectControl.
   * 
   * @param text The initial value.
   * @param isOptional if true, the value provided by this control is optional.
   * @return the newly created {@code ObjectControlMultiLineString}.
   */
  @Deprecated
  public ObjectControlMultiLineString createObjectControlMultiLineString(String text, boolean isOptional) {
    ObjectControlMultiLineString objectControlMultiLineString = new ObjectControlMultiLineString(customization, text, isOptional);

    return objectControlMultiLineString;
  }

  /**
   * Create an HTML String ObjectControl.
   * 
   * @param text The initial value.
   * @param isOptional if true, the value provided by this control is optional.
   * @return the newly created {@code ObjectControlHTMLString}.
   */
  @Deprecated
  public ObjectControlHTMLString createObjectControlHTMLString(String text, boolean isOptional) {
    ObjectControlHTMLString objectControlHTMLString = new ObjectControlHTMLString(customization, text, isOptional);

    return objectControlHTMLString;
  }

  /**
   * Create an HTML String EditorControl.
   * 
   * @param optional if true, the value provided by this control is optional.
   * @return the newly created {@code EditorControlHTMLString}.
   */
  public EditorControlHTMLString createEditorControlHTMLString(boolean optional) {
    EditorControlHTMLString editorControlHTMLString = EditorControlHTMLString.newInstance(customization, optional);

    return editorControlHTMLString;
  }
  
  /**
   * Create a PgCurrency ObjectControl.
   * 
   * @param pgCurrency The initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlCurrency}.
   */
  @Deprecated
  public ObjectControlCurrency createObjectControlCurrency(PgCurrency pgCurrency, double width, boolean isOptional, String toolTipText) {
    ObjectControlCurrency objectControlCurrency = new ObjectControlCurrency(customization, pgCurrency, width, isOptional, toolTipText);

    return objectControlCurrency;
  }

  /**
   * Create a FixedPointValue ObjectControl.
   * 
   * @param objectValue The initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlFixedPointValue}.
   */
  @Deprecated
  public ObjectControlFixedPointValue createObjectControlFixedPointValue(FixedPointValue objectValue, double width, boolean isOptional, String toolTipText) {
    ObjectControlFixedPointValue objectControlFixedPointValue = new ObjectControlFixedPointValue(customization, objectValue, width, isOptional, toolTipText);

//    customizeTextInputControl(objectControlFixedPointValue);

    return objectControlFixedPointValue;
  }

  /**
   * @deprecated
   * Create a FlexDate ObjectControl.
   * 
   * @param flexDate the initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlFlexDate}.
   */
  public ObjectControlFlexDate createObjectControlFlexDate(FlexDate flexDate, double width, boolean isOptional, String toolTipText) {
    ObjectControlFlexDate objectControlFlexDate = new ObjectControlFlexDate(customization, flexDate, width, isOptional, toolTipText);

    return objectControlFlexDate;
  }

  /**
   * Create a LocalDate ObjectControl.
   * 
   * @param localDate the initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlLocalDate}.
   */
  @Deprecated
  public ObjectControlLocalDate createObjectControlLocalDate(LocalDate localDate, double width, boolean isOptional, String toolTipText) {
    ObjectControlLocalDate objectControlLocalDate = new ObjectControlLocalDate(customization, localDate, width, isOptional, toolTipText);

//    customizeTextInputControl(objectControlLocalDate);

    return objectControlLocalDate;
  }

  /**
   * Create a TextField ObjectControl for a specific type, with its related StringConverter.
   * 
   * @param <T> The object type
   * @param stringConverter A StringConverter for type T.
   * @param initialValue the initial value
   * @param width the width of the TextField. 
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlTextField}.
   */
  @Deprecated
  public <T>ObjectControlTextField<T> createObjectControlTextField(StringConverterAndChecker<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    ObjectControlTextField<T> objectControlTextField = new ObjectControlTextField<>(customization, stringConverter, initialValue, width, isOptional, toolTipText);

//    customizeTextInputControl(objectControlTextField);

    return objectControlTextField;
  }
  
  /**
   * Create a ComboBox ObjectControl for an Enum. The texts for the enum constants are the names of the constants.
   * 
   * @param <T> The enum type
   * @param enumConstant A single enum constant of the enum.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   * @return the newly created {@code ObjectControlEnumComboBox}.
   */
  @Deprecated
  public <T extends Enum<T>> ObjectControlEnumComboBox<T> createObjectControlEnumComboBox(T enumConstant, boolean isOptional, String toolTipText) {
    return new ObjectControlEnumComboBox<T>(customization, enumConstant, isOptional, toolTipText);
  }
  
  /**
   * Create an auto complete TextField ObjectControl.
   * 
   * @param <T> the data type
   * @param stringConverter a StringConverter for type T
   * @param initialValue the initial value
   * @param width the width of the TextField.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   * @return the newly created {@code ObjectControlAutoCompleteTextField}.
   */
  @Deprecated
  public <T> ObjectControlAutoCompleteTextField<T> createObjectControlAutoCompleteTextField(StringConverterAndChecker<T> stringConverter, T initialValue, double width, boolean isOptional, String toolTipText) {
    return new ObjectControlAutoCompleteTextField<T>(customization, stringConverter, initialValue, width, isOptional, toolTipText);
  }

  /**
   * Create a WGS84Coordinates ObjectControl.
   * 
   * @param flexDate the initial value.
   * @param width the width of the TextField.
   * @param isOptional if true, the value provided by this control is optional.
   * @param toolTipText an optional tooltip text.
   * @return the newly created {@code ObjectControlFlexDate}.
   */
  public ObjectControlWGS84Coordinates createObjectControlWGS84Coordinates(WGS84Coordinates coordinates, double width, boolean isOptional, String toolTipText) {
    ObjectControlWGS84Coordinates objectControlWGS84Coordinates = new ObjectControlWGS84Coordinates(customization, coordinates, width, isOptional, toolTipText);

    return objectControlWGS84Coordinates;
  }

  /**
   * Create an ObjectControl for an image file selection.
   * 
   * @param isOptional if true, the value provided by this control is optional.
   * @return the newly created {@code ObjectControlImageFile}
   */
  @Deprecated
  public ObjectControlImageFile createObjectControlImageFile(boolean isOptional) {
    return new ObjectControlImageFile(customization, isOptional);
  }
    
  public String addHtmlContext(String text) {
    StringBuilder buf = new StringBuilder();
    buf.append("<html>");
    if (panelBackgroundHexColorValue != null) {
      buf.append("<body bgcolor='");
      buf.append(panelBackgroundHexColorValue);
      buf.append("'>");
    }
    
    buf.append(text);
    
    buf.append("</html>");
    
    
    return buf.toString();
  }
  
  /**
   * Dialogs
   */
  
  /**
   * Create an error dialog. This is a customized {@link Alert} of type AlertType.ERROR.
   * 
   * @param headerText the header text
   * @param contentText the content text
   * @return the created error Alert
   */
  public Alert createErrorDialog(String headerText, String contentText) {
    Alert alert = new Alert(AlertType.ERROR, contentText);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    if (stage == null) {
      LOGGER.severe("stage is null for errorDialog");
    }
    ObservableList<Image> icons = stage.getIcons();
    Image applicationImage = appResources.getApplicationImage(ImageSize.SIZE_0);
    if (icons == null) {
      LOGGER.severe("icons is null for stage");
    }
    if (applicationImage == null) {
      LOGGER.severe("applicationImage is null for appResources");
    }
    if (icons != null  &&  applicationImage != null) {
      icons.add(applicationImage);
    }
    
    // I don't know whether I want to customize the dialogs, but if I decide to do this, this is where to do it.
//    for (Node node: errorDialog.getDialogPane().getChildren()) {
//      LOGGER.severe("node= " + node.toString());
//      if (node instanceof Label) {
//        Label label = (Label) node;
//        if (labelBackground == null) {
//          labelBackground = new Background(new BackgroundFill(look.getLabelBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY));
//        }
//        label.setBackground(labelBackground);
//      }
//    }
    
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create an exception report dialog. This is a customized {@link Alert} of type AlertType.ERROR.
   * 
   * @param headerText the header text
   * @param contentText the content text
   * @return the created error Alert
   */
  public Alert createExceptionDialog(String headerText, Exception exception) {
    Alert alert = new Alert(AlertType.ERROR);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    alert.setTitle("Exception occurred");
    alert.setHeaderText(headerText);
    
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    String exceptionText = sw.toString();
    
    VBox expandableContent = new VBox();
    expandableContent.setMaxWidth(Double.MAX_VALUE);
    expandableContent.setMaxHeight(Double.MAX_VALUE);
    
    Label label = new Label("The exception stacktrace was:");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    VBox.setVgrow(textArea, Priority.ALWAYS);
    expandableContent.getChildren().addAll(label, textArea);
    
    alert.getDialogPane().setExpandableContent(expandableContent);
    alert.getDialogPane().setExpanded(true);
    
    alert.setHeight(1200);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create a warning dialog. This is a customized {@link Alert} of type AlertType.WARNING.
   * 
   * @param title the title
   * @param headerText the header text
   * @param contentText the content text
   * @return the created warning Alert
   */
  public Alert createWarningDialog(String headerText, String contentText) {
    Alert alert = new Alert(AlertType.WARNING, contentText);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create an information/message dialog. This is a customized {@link Alert} of type AlertType.INFORMATION.
   * 
   * @param title the title
   * @param headerText the header text
   * @param contentText the content text
   * @return the created information/message Alert
   */
  public Alert createInformationDialog(String title, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.INFORMATION, contentText);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    if (title != null) {
      alert.setTitle(title);
    }
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create an application information/message dialog. This is a customized {@link Alert} of type AlertType.INFORMATION.
   * 
   * @param title the title
   * @param image an optional Image, which will be set as the graphic
   * @param headerText the header text
   * @param contentText the content text
   * @return the created information/message Alert
   */
  public Alert createApplicationInformationDialog(String title, Image image, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.INFORMATION, contentText);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    if (title != null) {
      alert.setTitle(title);
    }
    if (image != null) {
      alert.setGraphic(new ImageView(image));
    }
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create a confirmation dialog with 'OK' and 'Cancel' buttons. This is a customized {@link Alert} of type AlertType.CONFIRMATION.
   * 
   * @param title the title
   * @param headerText the header text
   * @param contentText the content text
   * @return the created confirmation Alert
   */
  public Alert createOkCancelConfirmationDialog(String title, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.CONFIRMATION, contentText);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    if (title != null) {
      alert.setTitle(title);
    }
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    
    return alert;
  }

  /**
   * Create a confirmation dialog with 'Yes' and 'No' buttons. This is a customized {@link Alert} of type AlertType.CONFIRMATION.
   * 
   * @param title the title
   * @param headerText the header text
   * @param contentText the content text
   * @return the created confirmation Alert
   */
  public Alert createYesNoConfirmationDialog(String title, String headerText, String contentText) {
    Alert alert = createOkCancelConfirmationDialog(title, headerText, contentText);
    
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    alert.setResizable(true);
    
    return alert;
  }
  
  /**
   * Create a choice dialog. This is a customized {@link ChoiceDialog}.
   * 
   * @param <T> the type of the items to chose from
   * @param title the title
   * @param headerText the header text
   * @param contentText the content text
   * @param defaultChoice initially selected item
   * @param choices items to chose from
   * @return the created ChoiceDialog
   */
  public <T> ChoiceDialog<T> createChoiceDialog(String title, String headerText, String contentText, T defaultChoice, @SuppressWarnings("unchecked") T... choices) {
    ChoiceDialog<T> choiceDialog = new ChoiceDialog<>(defaultChoice, choices);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) choiceDialog.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));
        
    if (title != null) {
      choiceDialog.setTitle(title);
    }
    choiceDialog.setHeaderText(headerText);
    choiceDialog.setContentText(contentText);
    choiceDialog.setResizable(true);
    
    return choiceDialog;
  }

  /**
   * Create an formatted text dialog. This is a customized {@link Alert} of type AlertType.INFORMATION.
   * <p>
   * The dialog accepts markdown formatted text for the <code>contentText</code>.
   * 
   * @param title the title
   * @param image an optional Image, which will be set as the graphic
   * @param headerText the header text
   * @param contentText the content text
   * @return the created information/message Alert
   */
  public Alert createMarkdownTextDialog(String title, Image image, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.INFORMATION);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));

    // Set the background color
    alert.getDialogPane().setStyle("-fx-background-color: " + panelBackgroundHexColorValue);
        
    if (title != null) {
      alert.setTitle(title);
    }
    if (image != null) {
      alert.setGraphic(new ImageView(image));
    }
    alert.setHeaderText(headerText);
    
    Parser parser = Parser.builder().build();
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    org.commonmark.node.Node document = parser.parse(contentText);
    contentText = renderer.render(document);
    contentText = addHtmlContext(contentText);
    
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(contentText);
    alert.getDialogPane().setContent(webView);
    alert.setResizable(true);
        
    return alert;
  }
  

  /**
   * Create a formatted text dialog. This is a customized {@link Alert} of type AlertType.INFORMATION.
   * <p>
   * The dialog accepts HTML formatted text for the <code>contentText</code>.
   * 
   * @param title the title
   * @param image an optional Image, which will be set as the graphic
   * @param headerText the header text
   * @param contentText the content text
   * @return the created information/message Alert
   */
  public Alert createHtmlTextDialog(String title, Image image, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.INFORMATION);
    
    // Set the icon in the title bar.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(appResources.getApplicationImage(ImageSize.SIZE_0));

    // Set the background color
    alert.getDialogPane().setStyle("-fx-background-color: " + panelBackgroundHexColorValue);
        
    if (title != null) {
      alert.setTitle(title);
    }
    if (image != null) {
      alert.setGraphic(new ImageView(image));
    }
    alert.setHeaderText(headerText);
    
    
    contentText = addHtmlContext(contentText);
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.loadContent(contentText);
    alert.getDialogPane().setContent(webView);
    alert.setResizable(true);
        
    return alert;
  }
  
  public  Background getPanelBackground() {
    return panelBackground;
  }
}
