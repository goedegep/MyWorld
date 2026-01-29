package goedegep.media.photo.photoshow.guifx;

import java.io.File;

import goedegep.util.datetime.FlexDate;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PhotospherePresentationPanel extends MediumPresentationPanelAbstract {
  
  /**
   * The root node of this presentation panel.
   */
  private Pane root;
  
  private ImageView iv1;
  private ImageView iv2;
  
  private Timeline timeline;

  @Override
  public Parent getRootNode() {
    if (root == null) {
      root = new Pane();
      root.setStyle("-fx-background-color: black;");
    }
    
    return root;
  }

  @Override
  public void showMedium(String fileName, String title, FlexDate flexDate) {
    root.getChildren().clear();
    File panoramaFile = new File(fileName);
    Image image = new Image(panoramaFile.toURI().toString(), false);
    iv1 = new ImageView(image);
    iv2 = new ImageView(image);
    iv1.setPreserveRatio(true);
    iv2.setPreserveRatio(true);

    root.getChildren().addAll(iv1, iv2);

//    Rectangle2D vb = Screen.getPrimary().getVisualBounds();
//    Scene scene = new Scene(root, vb.getWidth(), vb.getHeight(), Color.BLACK);

//    setScene(scene);
//    setFullScreen(true);

//    scene.setOnKeyPressed(ev -> {
//      if (ev.getCode() == KeyCode.ESCAPE) {
//        stopPan();
////        close();
//      }
//    });

    Platform.runLater(() -> {
      fitAndStart(root.getWidth(), root.getHeight());
    });
  }

  private void fitAndStart(double sceneWidth, double sceneHeight) {
    Image image = iv1.getImage();
    if (image == null) {
      return;
    }

    double origW = image.getWidth();
    double origH = image.getHeight();
    if (origH <= 0) return;

    double displayedWidth = origW * (sceneHeight / origH);

    // scale both images so full height fits
    iv1.setFitHeight(sceneHeight);
    iv2.setFitHeight(sceneHeight);

    // position them side-by-side starting at x=0
    iv1.setLayoutX(0);
    iv1.setLayoutY(0);
    iv2.setLayoutX(displayedWidth);
    iv2.setLayoutY(0);

    // ensure pane is large enough to contain both
    root.setPrefWidth(displayedWidth * 2);
    root.setPrefHeight(sceneHeight);

    startPan(displayedWidth);
  }

  private void startPan(double displayedWidth) {
    stopPan();

    // if image not wider than screen, still scroll slowly to show motion
    double distance = Math.max(1, displayedWidth);

    // duration proportional to distance
    double seconds = Math.max(8, (distance / 1000.0) * 12.0);

    timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(root.translateXProperty(), 0.0)),
        new KeyFrame(Duration.seconds(seconds), new KeyValue(root.translateXProperty(), -displayedWidth, Interpolator.LINEAR)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void stopPan() {
    if (timeline != null) {
      timeline.stop();
      timeline = null;
    }
  }

  @Override
  public boolean hasTitle() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void removeTitle() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Point2D zoomInOnCursorPosition(double zoomLevelIncrease, boolean cursorOn) {
    return null;
  }

  @Override
  public Point2D zoomOutFromCenter(double zoomLevelDecrease) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void moveImageRight(double percentage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageLeft(double percentage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageUp(double percentage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageDown(double percentage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageRight(int pixels) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageLeft(int pixels) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageUp(int pixels) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveImageDown(int pixels) {
    // TODO Auto-generated method stub
    
  }
}
