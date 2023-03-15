package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;

import goedegep.media.mediadb.model.InformationType;
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
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), mediumInfo.getSourceBitRate());
    
    getSourceTypes().addAll(mediumInfo.getSourceTypes());
  }

  public void updateSourceMediumInfo() {
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), getMediumType());
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), getSourceBitRate());
    
    // Replace the source types if there's any change
    List<InformationType> sourceSourceTypes = sourceMediumInfo.getSourceTypes();
    List<InformationType> sourceTypes = getSourceTypes();
    boolean sourceTypesChanged = false;
    
    if (sourceSourceTypes.size() != sourceTypes.size()) {
      sourceTypesChanged = true;
    }
    
    if (!sourceTypesChanged) {
      for (int i = 0; i < sourceTypes.size(); i++) {
        InformationType sourceSourceType = sourceSourceTypes.get(i);
        InformationType sourceType = sourceTypes.get(i);
        if (!sourceSourceType.equals(sourceType)) {
          sourceTypesChanged = true;
          break;
        }
      }
    }
    
    if (sourceTypesChanged) {
      sourceSourceTypes.clear();
      sourceSourceTypes.addAll(sourceTypes);
    }
  }
}
