package goedegep.media.photo.photoshow.guifx;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A simple full screen panorama viewer.
 *
 * The image is scaled so its full height fits the screen. Initially the left part is shown.
 * The image is then animated to move left automatically until the right end is reached.
 */
public class PanoramaViewer extends Stage {
  private final ImageView imageView;
  private Timeline timeline;

  /**
   * Create and show a panorama viewer for the supplied image file.
   * The viewer will open full screen and start panning automatically.
   *
   * @param panoramaFile the panorama image file (must exist)
   */
  public PanoramaViewer(File panoramaFile) {
    if (panoramaFile == null || !panoramaFile.exists()) {
      throw new IllegalArgumentException("panoramaFile must exist");
    }

    // Load image (do not background-load to ensure sizing is available)
    Image image = new Image(panoramaFile.toURI().toString(), false);
    imageView = new ImageView(image);
    imageView.setPreserveRatio(true);

    // Use a Pane so the ImageView is positioned at (0,0) and not auto-centered by a layout pane.
    javafx.scene.layout.Pane root = new javafx.scene.layout.Pane(imageView);
    // Position image at top-left
    imageView.setLayoutX(0);
    imageView.setLayoutY(0);
    root.setStyle("-fx-background-color: black;");

    // Create a scene sized to the primary screen visual bounds
    Rectangle2D vb = Screen.getPrimary().getVisualBounds();
    Scene scene = new Scene(root, vb.getWidth(), vb.getHeight(), Color.BLACK);

    setScene(scene);
    setFullScreen(true);

    // Close on ESC
    scene.setOnKeyPressed(ev -> {
      if (ev.getCode() == KeyCode.ESCAPE) {
        stopPan();
        close();
      }
    });

    // When the stage is shown, compute sizing and start panning
    setOnShown(e -> Platform.runLater(() -> {
      fitImageToHeight(scene.getHeight());
      startPan(scene.getWidth(), scene.getHeight());
    }));

    // If screen size changes (unlikely while full screen) adjust
    scene.heightProperty().addListener((obs, oldV, newV) -> {
      fitImageToHeight(newV.doubleValue());
    });

    show();
  }

  private void fitImageToHeight(double sceneHeight) {
    // Set the image view fitHeight so the whole image height is visible
    imageView.setFitHeight(sceneHeight);
    // ensure the translate is reset to leftmost when resizing
    imageView.setTranslateX(0);
    // Ensure the image view origin is at (0,0) to show left/top when translateX=0
    imageView.setLayoutX(0);
    imageView.setLayoutY(0);
    // Also set the ImageView x/y coordinates (defensive)
    imageView.setX(0);
    imageView.setY(0);
  }

  private void startPan(double sceneWidth, double sceneHeight) {
    Image image = imageView.getImage();
    if (image == null) {
      return;
    }

    double imageOriginalWidth = image.getWidth();
    double imageOriginalHeight = image.getHeight();
    if (imageOriginalHeight <= 0) {
      return;
    }

    // Displayed width after scaling to fit height
    double displayedWidth = imageOriginalWidth * (sceneHeight / imageOriginalHeight);

    double maxShift = Math.max(0, displayedWidth - sceneWidth);
    System.out.println("PanoramaViewer: sceneWidth=" + sceneWidth + ", sceneHeight=" + sceneHeight +
        ", displayedWidth=" + displayedWidth + ", maxShift=" + maxShift);
    if (maxShift <= 1) {
      // No need to pan
      return;
    }

    // Duration proportional to distance: e.g. 15 seconds per screen width of pan distance
    double seconds = Math.max(8, (maxShift / 1000.0) * 12.0);
    System.out.println("PanoramaViewer: pan seconds=" + seconds);

    // Animate translateX from 0 to -maxShift
    stopPan();
    timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(imageView.translateXProperty(), 0.0)),
        new KeyFrame(Duration.seconds(seconds), new KeyValue(imageView.translateXProperty(), -maxShift)));
    timeline.setCycleCount(1);
    timeline.play();
  }

  private void stopPan() {
    if (timeline != null) {
      timeline.stop();
      timeline = null;
    }
  }
}