package goedegep.markdowneditor.gui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.markdowneditor.logic.MarkdownEditorRegistry;
import goedegep.resources.ImageSize;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class implements a simple Markdown editor with live preview.
 * <p>
 * The left side of the window contains a text area where the user can enter Markdown text.
 * The right side of the window shows the formatted Markdown text as HTML.
 */
public class MarkdownEditor extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MarkdownEditor.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The Markdown editor registry.
   */
  private MarkdownEditorRegistry markdownEditorRegistry;
  
  /**
   * Parser to parse the Markdown text.
   */
  private Parser parser = Parser.builder().build();
  
  /**
   * Renderer to render the parsed Markdown to HTML.
   */
  private HtmlRenderer renderer = HtmlRenderer.builder().build();
  
  /**
   * The text area for showing and editing the Markdown text.
   */
  private TextArea editTextArea;
  
  /**
   * The web engine for showing the formatted HTML view.
   */
  private WebEngine webEngine;
  
  /**
   * The current file being edited.
   */
  private File currentFile = null;
  
  /**
   * The original file content, used to detect modifications.
   */
  private String originalFileContent = "";
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param filename the name of the Markdown file to open, or null to start with an empty document.
   */
  public MarkdownEditor(CustomizationFx customization, String filename) {
    super(customization, null);  // The window title is set by setTitle().
    
    markdownEditorRegistry = MarkdownEditorRegistry.getInstance();
    
    createGUI();
    
    editTextArea.textProperty().addListener((_, _, newValue) -> updateFormattedView(newValue));
    
    if (filename != null) {
      currentFile = new File(filename);
      showMarkdownFile();
    }
    
    updateTitle();
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The GUI consists of a menu bar at the top and a split pane in the center.
   * Left side of the split pane is the text area for editing the Markdown text.
   * Right side of the split pane is the WebView for showing the formatted HTML view.
   */
  private void createGUI() {
    BorderPane mainPane = new BorderPane();
    
    mainPane.setTop(createMenuBar());
    
    SplitPane splitPane = new SplitPane();
    // Editor on the left.
    editTextArea = new TextArea();
    splitPane.getItems().add(editTextArea);
    
    // Formatted view on the right.
    WebView webView = new WebView();
    webEngine = webView.getEngine();
    splitPane.getItems().add(webView);
    
    mainPane.setCenter(splitPane);
    
    setScene(new Scene(mainPane));
  }
  
  /**
   * Create the menu bar.
   * 
   * @return the created menu bar.
   */
  private Node createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // File menu
    menu = componentFactory.createMenu("File");
    
    // File: New Markdown Document
    menuItem = componentFactory.createMenuItem("New Markdown Document");
    menuItem.setOnAction(_ -> handleNewMarkdownDocumentRequest());
    menu.getItems().add(menuItem);
    
    // File: Open
    menuItem = componentFactory.createMenuItem("Open Markdown file ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleFileOpenRequest();
      }
    });
    menu.getItems().add(menuItem);
    
    // File: Save Markdown Document
    menuItem = componentFactory.createMenuItem("Save Markdown Document");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSaveMarkdownDocumentRequest();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    // File: Save Markdown Document as ...
    menuItem = componentFactory.createMenuItem("Save Markdown Document as ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSaveMarkdownDocumentAsRequest();
      }
    });
    menu.getItems().add(menuItem);
            
    menuItem = componentFactory.createMenuItem("Exit");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Platform.exit();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", _ -> showHelpAboutDialog());

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  /**
   * Handle the request to open a Markdown file.
   * <p>
   * A FileChooser is shown to select the file to open.
   */
  private void handleFileOpenRequest() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select Markdown file to open");
    
    File file = fileChooser.showOpenDialog(this);
    
    if (file != null) {
      currentFile = file;
      showMarkdownFile();
    }
  }
  
  /**
   * Handle the request to create a new Markdown document.
   * <p>
   * The current file is set to null and the text area is cleared.
   */
  private void handleNewMarkdownDocumentRequest() {
    currentFile = null;
    originalFileContent = "";
    editTextArea.setText("");
  }
  
  /**
   * Load and show the current Markdown file in the text area.
   */
  private void showMarkdownFile() {
    // load the text into the text area.
    try (Reader reader = new FileReader(currentFile)) {
      StringBuilder buf = new StringBuilder();
      int ch;
      while ((ch = reader.read()) != -1) {
        buf.append((char) ch);
      }
      originalFileContent = buf.toString();
      editTextArea.setText(originalFileContent);
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Save the current Markdown document.
   * <p>
   * The document is saved to the 'current file', if it is set.
   * Otherwise the document is saved by calling {@link #saveMarkdownDocumentAs}.
   */
  private void handleSaveMarkdownDocumentRequest() {
    if (currentFile != null) {
      saveMarkdownDocumentToFile();
    } else {
      handleSaveMarkdownDocumentAsRequest();
    }
  }
  
  /**
   * Save the current Markdown document, where the user first has to specify a file name via a FileChooser.
   * <p>
   * If the user cancels the FileChooser, no further action takes place (the document isn't saved).
   */
  private void handleSaveMarkdownDocumentAsRequest() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Markdown document");
    ExtensionFilter extensionFilter = new ExtensionFilter("Markdown document", ".md");
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    
    File file = fileChooser.showSaveDialog(this);
    if (file != null) {
      LOGGER.severe("Saving: " + file.getAbsolutePath());
      currentFile = file;
      saveMarkdownDocumentToFile();
    }
  }
  
  private void saveMarkdownDocumentToFile() {
    try (FileWriter myWriter = new FileWriter(currentFile)) {
      myWriter.write(editTextArea.getText());
      originalFileContent = editTextArea.getText();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  /**
   * Update the formatted HTML view according to the given Markdown text.
   * 
   * @param markdownText the Markdown text to format and show.
   */
  private void updateFormattedView(String markdownText) {
    StringBuilder buf = new StringBuilder();

    buf.append("<html>");
    buf.append("<header>");
    buf.append("</header>");
    buf.append("<body>");
    
    org.commonmark.node.Node document = parser.parse(markdownText);
    String htmlText = renderer.render(document);
    buf.append(htmlText);

    buf.append("</body>");
    buf.append("</html>");

    webEngine.loadContent(buf.toString());
    updateTitle();
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + markdownEditorRegistry.getApplicationName(),
        customization.getResources().getApplicationImage(ImageSize.SIZE_3),
        null, 
        markdownEditorRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + markdownEditorRegistry.getVersion() + NEWLINE +
        markdownEditorRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + markdownEditorRegistry.getAuthor() + 
        (markdownEditorRegistry.isDevelopmentMode() ? (NEWLINE + NEWLINE + "Running in development mode!") : "")
        )
        .showAndWait();
  }
  
  /**
   * Check for unsaved changes in the current Markdown text.
   * 
   * @return true if there are unsaved changes, false otherwise.
   */
  private boolean fileIsModified() {
    return !editTextArea.getText().equals(originalFileContent);
  }
  
  /**
   * Update the window title according to the current file and modification state.
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(markdownEditorRegistry.getApplicationName()).append(" - ");
    if (fileIsModified()) {
      buf.append("*");
    }
    
    if (currentFile != null) {
      buf.append(currentFile.getAbsolutePath());
    } else {
      buf.append("<no file>");
    }
    
    setTitle(buf.toString());
  }
  
}
