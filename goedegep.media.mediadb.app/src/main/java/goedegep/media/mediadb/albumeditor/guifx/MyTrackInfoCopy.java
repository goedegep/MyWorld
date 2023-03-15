package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.impl.MyTrackInfoImpl;
import goedegep.util.emf.EmfUtil;

class MyTrackInfoCopy extends MyTrackInfoImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
    
  private MyTrackInfo sourceMyTrackInfo;
  
  MyTrackInfoCopy(MyTrackInfo myTrackInfo) {
    sourceMyTrackInfo = myTrackInfo;
    
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), myTrackInfo.getCollection());
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), myTrackInfo.getIWant());
    
    for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
      getIHaveOn().add(new MediumInfoCopy(mediumInfo));
    }
    
    if (myTrackInfo.getCompilationTrackReference() != null) {
      setCompilationTrackReference(new TrackReferenceCopy(myTrackInfo.getCompilationTrackReference()));
    }
  }

  public void updateSourceMyTrackInfo() {
    EmfUtil.setFeatureValue(sourceMyTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), getCollection());
    EmfUtil.setFeatureValue(sourceMyTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), getIWant());
    
    for (MediumInfo mediumInfo: getIHaveOn()) {
      MediumInfoCopy mediumInfoCopy = (MediumInfoCopy) mediumInfo;
      mediumInfoCopy.updateSourceMediumInfo();
    }
    
    if (getCompilationTrackReference() != null) {
      TrackReferenceCopy compilationTrackReferenceCopy = (TrackReferenceCopy) getCompilationTrackReference();
      compilationTrackReferenceCopy.updateSourceTrackReference();
    }
  }
}
