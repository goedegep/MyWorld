package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.impl.TrackReferenceImpl;
import goedegep.util.emf.EmfUtil;

class TrackReferenceCopy extends TrackReferenceImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
    
  private TrackReference sourceTrackReference;
  
  /**
   * Constructor
   * 
   * @param trackReference the TrackReference of which a copy is to be made.
   */
  TrackReferenceCopy(TrackReference trackReference) {
    sourceTrackReference = trackReference;
    
    String bonusTrack = trackReference.getBonusTrack();
    if (bonusTrack != null) {
      setBonusTrack(new String(bonusTrack));
    }
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReference.getTrack());  // no copy needed as it cannot be changed.
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getTrackReference_OriginalAlbumTrackReference(), trackReference.getOriginalAlbumTrackReference());  // no copy needed as it cannot be changed.
    
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo != null) {
      setMyTrackInfo(new MyTrackInfoCopy(myTrackInfo));
    }
  }

  public void updateSourceTrackReference() {
    String bonusTrack = getBonusTrack();
    if (bonusTrack != null) {
      sourceTrackReference.setBonusTrack(new String(bonusTrack));  // Also make a copy here, as editing may continue and then be cancelled.
    }
    EmfUtil.setFeatureValue(sourceTrackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), getTrack());
    EmfUtil.setFeatureValue(sourceTrackReference, MEDIA_DB_PACKAGE.getTrackReference_OriginalAlbumTrackReference(), getOriginalAlbumTrackReference());
    
    MyTrackInfoCopy myTrackInfoCopy = (MyTrackInfoCopy) getMyTrackInfo();
    if (myTrackInfoCopy != null) {
      myTrackInfoCopy.updateSourceMyTrackInfo();
    }
  }

  public TrackReference getSourceTrackReference() {
    return sourceTrackReference;
  }

}
