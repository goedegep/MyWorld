package goedegep.jfx.browser;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

/**
 * This class provides a simple browser, based on a {@link WebView}.
 * <p>
 * A WebView provides already a lot of functionality, but there are some problems:
 * <ul>
 * <li>No support for BACK and FORWARD mouse buttons<br/>
 * The WebView handles mouse buttons correctly, but not for the BACK and FORWARD buttons. If you press them an exception is thrown.<br/>
 * With this class these buttons go back and forward through the page history. 
 * </li>
 * <li>webView.getEngine().loadContent(htmlText) is not recorded in the history<br/>
 * This is also not possible, because the history is based on URL's.
 * This leads however to the problem that when you use loadContent() and then on this page
 * follow a link to a new page, you cannot go back. This class handles this special case.
 * </li>
 * </ul>
 */
public class Browser extends StackPane {
  /*
   * The WebView class cannot be extended to extend the functionality, therefore the following
   * solution is chosen:
   * To handle the mouse events, a StackPane is used where a GridPane is put on top of the WebView.
   * This GridPane handles the BACK and FORWARD buttons and all other mouse and scroll events are
   * forwarded to the WebView. 
   */
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(Browser.class.getName());

  private final WebView webView = new WebView();
  private final WebEngine webEngine = webView.getEngine();
  
  private String content;

  public Browser(String url) {
    getStyleClass().add("browser");
    getChildren().add(webView);

    GridPane gridPane = new GridPane();
    gridPane.addEventHandler(MouseEvent.ANY, e -> {
      e.consume();
      if (e.getButton() == MouseButton.BACK) {
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
          final WebHistory history = webView.getEngine().getHistory();
          ObservableList<WebHistory.Entry> entryList = history.getEntries();
          int currentIndex = history.getCurrentIndex();
          if ((currentIndex == 0)  &&  (entryList.size() == 1)  &&  (content != null)) {
            webEngine.loadContent(content);
          } else {
            history.go((entryList.size() > 1  && currentIndex > 0) ? -1: 0);
          }
        }
      } else if (e.getButton() == MouseButton.FORWARD) {
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
          final WebHistory history = webView.getEngine().getHistory(); 
          ObservableList<WebHistory.Entry> entryList = history.getEntries();
          int currentIndex = history.getCurrentIndex();
          history.go((entryList.size() > 1 && currentIndex < entryList.size() - 1) ? 1 : 0); 
        }
      } else {
        webView.fireEvent(e);
      }
    });
    gridPane.setOnScroll((ScrollEvent event) -> { webView.fireEvent(event);});
    getChildren().add(gridPane);

    if (url != null) {
      webEngine.load(url);
    }
  }
  
  /**
   * Loads the given HTML content directly. This method is useful when you have an HTMLString composed in memory, or loaded from some system
   * which cannot be reached via URL (for example, the HTML text may have come from a database). As with load(String), this method is asynchronous.
   * @param content the HTML content to load
   */
  public void loadContent(String content) {
    this.content = content;
    
    webEngine.loadContent(content);
  }

//  @Override protected void layoutChildren() {
//    double w = getWidth();
//    double h = getHeight();
//    layoutInArea(webView,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
//  }
//
//  @Override protected double computePrefWidth(double height) {
//    return 750;
//  }
//
//  @Override protected double computePrefHeight(double width) {
//    return 500;
//  }
}