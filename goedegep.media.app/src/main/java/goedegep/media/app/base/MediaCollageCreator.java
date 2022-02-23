package goedegep.media.app.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.collage.CollageImage;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import javafx.scene.canvas.Canvas;

public class MediaCollageCreator {
  private static final Logger LOGGER = Logger.getLogger(MediaCollageCreator.class.getName());

  /**
   * Create a photo collage for the background picture.
   * <p>
   * The collage is created from the front images of albums that I have.
   * 
   * @return the generated photo collage.
   */
  public static Canvas createCollage(int width, int minNrOfPicturesInCollage, int maxNrOfPicturesInCollage, MediaDb mediaDb) {
    LOGGER.info("=>");
    
    Canvas collage = null;
    
    // Candidates are the first front images of the albums for which 'I have on' is not empty.
    List<String> candidates = new ArrayList<>();
    for (Album album: mediaDb.getAlbums()) {
      if (!MediaDbUtil.haveAlbumOnMediumTypes(album).isEmpty()) {
        List<String> imagesFront = album.getImagesFront();
        if (!imagesFront.isEmpty()) {
          candidates.add(imagesFront.get(0));
        }
      }
    }
        
    // Randomly remove candidates until we have the right amount of pictures left over
    while (candidates.size() > maxNrOfPicturesInCollage) {
      int index = (int) (Math.random() * candidates.size());
      candidates.remove(index);
    }
    
    // Only create the collage if there are sufficient pictures
    if (candidates.size() > minNrOfPicturesInCollage) {
      List<File> imageFiles = new ArrayList<>();
      for (String fileName: candidates) {
        File file = new File(MediaRegistry.albumInfoDirectory, fileName);       
        imageFiles.add(file);
      }

      collage = CollageImage.createTiledCollage(width, 6, imageFiles);
    }
    
    return collage;
  }

}
