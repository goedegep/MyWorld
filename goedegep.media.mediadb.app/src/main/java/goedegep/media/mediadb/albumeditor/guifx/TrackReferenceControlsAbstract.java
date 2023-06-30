package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.jfx.objectcontrols.ObjectControlAbstract;
import goedegep.media.mediadb.model.TrackReference;

public abstract class TrackReferenceControlsAbstract extends ObjectControlAbstract<TrackReference>  {
  private String id;
  
  public TrackReferenceControlsAbstract(boolean optional) {
    super(optional);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetId(String id) {
    this.id = id;
  }

  
}
