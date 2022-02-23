package goedegep.media.mediadb.app.guifx;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;

public abstract class AlbumDetailsAbstract extends JfxStage {

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  public AlbumDetailsAbstract(String title, CustomizationFx customization) {
    super(title, customization);    
  }
  
}
