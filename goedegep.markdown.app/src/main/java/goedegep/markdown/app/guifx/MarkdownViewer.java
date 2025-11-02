package goedegep.markdown.app.guifx;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.markdown.app.MarkdownRegistry;
import goedegep.resources.ImageSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class MarkdownViewer extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MarkdownViewer.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "Markdown Viewer";
  
  private MarkdownRegistry markdownRegistry = MarkdownRegistry.getInstance();
  private Parser parser = Parser.builder().build();
  private HtmlRenderer renderer = HtmlRenderer.builder().build();
  private WebView webView = new WebView();
  private WebEngine webEngine = webView.getEngine();
  
  public MarkdownViewer(CustomizationFx customization, String filename) {
    super(customization, WINDOW_TITLE);
    LOGGER.info("=>");
    
    markdownRegistry = MarkdownRegistry.getInstance();
    
    createGUI();
    
    if (filename != null) {
      showMarkdownFile(new File(filename));
    }
    
    show();
  }
  
  private void createGUI() {
    BorderPane mainPane = new BorderPane();
    
    mainPane.setTop(createMenuBar());
    
//    HBox hBox = new HBox();
//    hBox.getChildren().add(new Label("CENTER"));
//    hBox.getChildren().add(webView);
    mainPane.setCenter(webView);
    
    mainPane.setBottom(new Label("Status label"));
    
    setScene(new Scene(mainPane));
  }
  
  private Node createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // File menu
    menu = componentFactory.createMenu("File");
    
    menuItem = componentFactory.createMenuItem("Open");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleFileOpenRequest();
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
  
  private void handleFileOpenRequest() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select Markdown file to open");
    
    File file = fileChooser.showOpenDialog(this);
    
    if (file != null) {
      showMarkdownFile(file);
    }
  }
  
  private void showMarkdownFile(File file) {
    StringBuilder buf = new StringBuilder();

    if (file != null) {
      buf.append("<html>");
      buf.append("<header>");
      buf.append("</header>");
      buf.append("<body>");
      try {
        Reader reader = new FileReader(file);
        org.commonmark.node.Node document = parser.parseReader(reader);
        String htmlText = renderer.render(document);
        buf.append(htmlText);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      buf.append("</body>");
      buf.append("</html>");
    }
    
    webEngine.loadContent(buf.toString());
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + markdownRegistry.getApplicationName(),
        customization.getResources().getApplicationImage(ImageSize.SIZE_3),
        null, 
        markdownRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + markdownRegistry.getVersion() + NEWLINE +
        markdownRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + markdownRegistry.getAuthor())
        .showAndWait();
  }
  
}
