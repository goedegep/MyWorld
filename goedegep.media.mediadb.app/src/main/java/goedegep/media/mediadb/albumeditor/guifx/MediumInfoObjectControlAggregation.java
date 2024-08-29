package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAggregationTemplate;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlInteger;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.util.emf.EmfUtil;

public class MediumInfoObjectControlAggregation extends ObjectControlAggregationTemplate<MediumInfo> {
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  /**
   * {@code ObjectControl} for the medium type.
   */
  private ObjectControlEnumComboBox<MediumType> mediumTypeObjectControl;
  
  /**
   * {@code ObjectControl} for the information type.
   */
  private ObjectControlEnumComboBox<InformationType> informationTypeObjectControl;
  /**
   * {@code ObjectControl} for the source type.
   */
  private ObjectControlEnumComboBox<InformationType> sourceTypeObjectControl;
  
  /**
   * {@code ObjectControl} for the source bit rate.
   */
  private ObjectControlInteger sourceBitRateObjectControl;

  
  /**
   * Constructor
   * 
   * @param customization The GUI customization
   */
  public MediumInfoObjectControlAggregation(CustomizationFx customization) {
    super(customization);
  }
  
  public ObjectControlEnumComboBox<MediumType> getMediumTypeObjectControl() {
    return mediumTypeObjectControl;
  }

  public ObjectControlEnumComboBox<InformationType> getInformationTypeObjectControl() {
    return informationTypeObjectControl;
  }

  public ObjectControlEnumComboBox<InformationType> getSourceTypeObjectControl() {
    return sourceTypeObjectControl;
  }

  public ObjectControlInteger getSourceBitRateObjectControl() {
    return sourceBitRateObjectControl;
  }



  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    mediumTypeObjectControl = componentFactory.createObjectControlEnumComboBox(MediumType.NOT_SET, MediumType.NOT_SET, MediadbPackage.eINSTANCE.getMediumType(), true, "Select the medium type");
    mediumTypeObjectControl.setId("medium type");
    
    informationTypeObjectControl = componentFactory.createObjectControlEnumComboBox(InformationType.NOT_SET, InformationType.NOT_SET, MediadbPackage.eINSTANCE.getInformationType(), true, "Select the information type");
    informationTypeObjectControl.setId("information type");
    
    sourceTypeObjectControl = componentFactory.createObjectControlEnumComboBox(InformationType.NOT_SET, InformationType.NOT_SET, MediadbPackage.eINSTANCE.getInformationType(), true, "Select the source type(s)");
    sourceTypeObjectControl.setId("source type");
    
    sourceBitRateObjectControl = componentFactory.createObjectControlInteger(0, 100.0, false, "enter the source bitrate");
    sourceBitRateObjectControl.setId("source bit rate");

    objectControlsGroup.addObjectControls(
        mediumTypeObjectControl,
        informationTypeObjectControl,
        sourceTypeObjectControl,
        sourceBitRateObjectControl
        );
    
    object = MEDIA_DB_FACTORY.createMediumInfo();
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    mediumTypeObjectControl.setValue(MediumType.NOT_SET);
    informationTypeObjectControl.setValue(InformationType.NOT_SET);
    sourceTypeObjectControl.setValue(InformationType.NOT_SET);
    sourceBitRateObjectControl.setValue(null);
  }

  @Override
  protected void fillControlsFromObject() {
    fillControlsWithDefaultValues();
    
    if (object == null) {
      return;
    }
    
    mediumTypeObjectControl.setValue(object.getMediumType());
    informationTypeObjectControl.setValue(object.getInformationType());
    if (!object.getSourceTypes().isEmpty()) {
      sourceTypeObjectControl.setValue(object.getSourceTypes().get(0));
    }
    sourceBitRateObjectControl.setValue(object.getSourceBitRate());
  }

  @Override
  public void createObject() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), mediumTypeObjectControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getMediumInfo_InformationType(), informationTypeObjectControl.getValue());
    List<InformationType> sourceTypes = new ArrayList<>();
    if (informationTypeObjectControl.getValue() != null) {
      sourceTypes.add(informationTypeObjectControl.getValue());
    }
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getMediumInfo_SourceTypes(), sourceTypes);
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), sourceBitRateObjectControl.getValue());
  }
}
