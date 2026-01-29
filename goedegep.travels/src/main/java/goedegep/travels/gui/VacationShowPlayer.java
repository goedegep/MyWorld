package goedegep.travels.gui;

import java.io.File;
import java.net.URI;

import goedegep.jfx.JfxApplication;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VacationShowPlayer extends JfxApplication {

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    
//    File mediaFile = new File("D:\\Video\\Edit\\Takes\\2019-11 Australië - Yvette en Marc\\P1030106.MP4");
//    Media media = new Media(mediaFile);
    
    File filestring = new File("D:\\Video\\Music Videos\\David Gilmour - Remember That Night\\David Gilmour - Remember That Night - part 2.m2ts");
    URI uri = filestring.toURI();
    System.out.println(uri.toString());
    Media media = new Media(uri.toString());    
    
    MediaPlayer player = new MediaPlayer(media);
    MediaView view = new MediaView(player);
    Pane mpane = new Pane(); 
    mpane.getChildren().add(view);

    borderPane.setCenter(mpane);
    player.play();
    
    Scene scene = new Scene(borderPane);
    primaryStage.setScene(scene);
    
    primaryStage.show();
  }


  public static void main(String[] args) {
    VacationShowPlayer.launch(args);
  }
}
