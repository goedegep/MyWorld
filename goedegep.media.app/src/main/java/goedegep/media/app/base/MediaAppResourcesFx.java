package goedegep.media.app.base;

import goedegep.jfx.AbstractAppResourcesFx;
import javafx.scene.image.Image;

public class MediaAppResourcesFx extends AbstractAppResourcesFx  {
  private Image musicAlbumsImage = null;
  private Image playImage = null;
  private Image playPartlyImage = null;
  private Image cddaImage = null;
  private Image sacdImage = null;
  private Image notInMediaDbImage = null;
  private Image iWantImage = null;
  private Image dontKnowImage = null;
  private Image referenceImage = null;
  private Image referencePartlyImage = null;
  private Image duneImage = null;
  private Image musicFolderImage = null;
  
  private Image titleImage = null;
  private Image coordinatesImage = null;

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[1];
    Image picture = null;
    
    try {
      applicationImages[0] = new Image(getClass().getResourceAsStream("MediaDb_64x64.png"));
      playImage = new Image(getClass().getResourceAsStream("Play1Hot.png"));
      playPartlyImage = new Image(getClass().getResourceAsStream("Play1HotPartly.png"));
      cddaImage = new Image(getClass().getResourceAsStream("CDDA.png"));
      sacdImage = new Image(getClass().getResourceAsStream("SACD.png"));
      notInMediaDbImage = new Image(getClass().getResourceAsStream("notInMediaDb.png"));
      iWantImage = new Image(getClass().getResourceAsStream("download-512.png"));
      dontKnowImage = new Image(getClass().getResourceAsStream("QuestionMarkIcon.png"));
      referenceImage = new Image(getClass().getResourceAsStream("Forward.png"));
      referencePartlyImage = new Image(getClass().getResourceAsStream("ForwardPartly.png"));
      duneImage = new Image(getClass().getResourceAsStream("Dune HD TV-303D.jpg"), 500.0, 500.0, true, true);
      musicFolderImage = new Image(getClass().getResourceAsStream("MusicFolder.png"), 400.0, 400.0, true, true);
      
      titleImage = new Image(getClass().getResourceAsStream("Title.png"), 24, 24, true, true);
      coordinatesImage = new Image(getClass().getResourceAsStream("Coordinates.png"), 24, 24, true, true);
      
      picture = new Image(getClass().getResourceAsStream("CDVD.jpg"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }

  public Image getMusicAlbumsImage() {
    if (musicAlbumsImage == null) {
      musicAlbumsImage = new Image(getClass().getResourceAsStream("MusicAlbums.png"));
    }
    
    return musicAlbumsImage;
  }
  
  public Image getPlayIcon() {
    return playImage;
  }

  public Image getPlayPartlyIcon() {
    return playPartlyImage;
  }

  public Image getCddaIcon() {
    return cddaImage;
  }

  public Image getSacdIcon() {
    return sacdImage;
  }

  public Image getNotInMediaDbIcon() {
    return notInMediaDbImage;
  }

  public Image getIWantIcon() {
    return iWantImage;
  }

  public Image getDontKnowIcon() {
    return dontKnowImage;
  }

  public Image getReferenceIcon() {
    return referenceImage;
  }

  public Image getReferencePartlyIcon() {
    return referencePartlyImage;
  }

  public Image getDuneImage() {
    return duneImage;
  }

  public Image getMusicFolderImage() {
    return musicFolderImage;
  }
  
  public Image getTitleIcon() {
    return titleImage;
  }
  
  public Image getCoordinatesIcon() {
    return coordinatesImage;
  }
}
