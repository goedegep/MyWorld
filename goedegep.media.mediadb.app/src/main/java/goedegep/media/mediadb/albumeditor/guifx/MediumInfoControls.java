package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.ObjectControlEnumComboBox;
import goedegep.jfx.controls.ObjectControlInteger;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import javafx.scene.layout.GridPane;

/**
 * This class provides the controls for MediumInfo.
 */
class MediumInfoControls {
  private ObjectControlEnumComboBox<MediumType> mediumTypeComboBox;
  private ObjectControlEnumComboBox<InformationType> informationTypeComboBox;
  private ObjectControlEnumComboBox<InformationType> sourceTypeComboBox;
  private ObjectControlInteger sourceBitRateControl;

  public MediumInfoControls(CustomizationFx customization, GridPane gridPane, int row, int column, MediumInfo mediumInfo) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    // Medium type
    mediumTypeComboBox = componentFactory.createObjectInputEEnumComboBox(MediumType.NOT_SET, MediumType.NOT_SET, MediadbPackage.eINSTANCE.getMediumType(), true, "Select the medium type");
    MediumType mediumType = mediumInfo.getMediumType();
    mediumTypeComboBox.setObjectValue(mediumType);
    gridPane.add(mediumTypeComboBox, column++, row);
    
    // Information type
    informationTypeComboBox = componentFactory.createObjectInputEEnumComboBox(InformationType.NOT_SET, InformationType.NOT_SET, MediadbPackage.eINSTANCE.getInformationType(), true, "Select the information type");
    InformationType informationType = mediumInfo.getInformationType();
    informationTypeComboBox.setObjectValue(informationType);
    gridPane.add(informationTypeComboBox, column++, row);

    // Source type
    sourceTypeComboBox = componentFactory.createObjectInputEEnumComboBox(InformationType.NOT_SET, InformationType.NOT_SET, MediadbPackage.eINSTANCE.getInformationType(), true, "Select the source type(s)");
    InformationType sourceType = null;
    if (!mediumInfo.getSourceTypes().isEmpty()) {
      sourceType = mediumInfo.getSourceTypes().get(0);
    }
    sourceTypeComboBox.setObjectValue(sourceType);
    gridPane.add(sourceTypeComboBox, column++, row);

    // Source bit rate
    sourceBitRateControl = componentFactory.createObjectInputInteger(mediumInfo.getSourceBitRate(), 100.0, false, "enter the source bitrate");
    gridPane.add(sourceBitRateControl, column++, row);
  }
}
