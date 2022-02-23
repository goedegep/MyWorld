package goedegep.media.fotoshow.app.guifx;

import goedegep.jfx.ComponentFactoryFx;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class GatherPhotoInfoStatusPanel extends HBox {
  private Text text;
  private ProgressBar progressBar;

  public GatherPhotoInfoStatusPanel(ComponentFactoryFx componentFactory) {
    componentFactory.customizePane(this);
    setSpacing(20);
    
    text = componentFactory.createText(null);
    progressBar = componentFactory.createProgressBar(-1);
  }
  
  public void updateText(String message) {
    getChildren().clear();
    text.setText(message);
    getChildren().add(text);
    
    double progress = progressBar.getProgress();
    
    if (progress >= 0.0  &&  progress < 1.0) {
       getChildren().add(progressBar);
    }
  }
  
  public void updateProgress(double progress) {
    getChildren().clear();
    getChildren().add(text);
    
    progressBar.setProgress(progress);
    
    if (progress >= 0.0  &&  progress < 1.0) {
       getChildren().add(progressBar);
    }
  }
}
