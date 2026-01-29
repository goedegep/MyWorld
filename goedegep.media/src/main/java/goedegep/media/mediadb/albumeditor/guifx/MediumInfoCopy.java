package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.impl.MediumInfoImpl;
import goedegep.util.emf.EmfUtil;

class MediumInfoCopy extends MediumInfoImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private MediumInfo sourceMediumInfo;
  
  MediumInfoCopy(MediumInfo mediumInfo) {
    sourceMediumInfo = mediumInfo;
    
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), mediumInfo.getMediumType());
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_SourceType(), mediumInfo.getSourceType());
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), mediumInfo.getSourceBitRate());
  }

  public void updateSourceMediumInfo() {
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), getMediumType());
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_SourceType(), getSourceType());
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), getSourceBitRate());    
  }
}
