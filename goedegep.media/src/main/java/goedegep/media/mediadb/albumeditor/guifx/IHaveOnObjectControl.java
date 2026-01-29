package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediumInfo;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class IHaveOnObjectControl extends ObjectControlTextField<List<MediumInfo>> {
  private static final Logger         LOGGER = Logger.getLogger(IHaveOnObjectControl.class.getName());
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * Constructor.
   * 
   * @param customization - The GUI customization.
   */
  public IHaveOnObjectControl(CustomizationFx customization) {
    super(customization, new MediumInfoListStringConverter(), null, 200,  true, "");
    
    this.customization = customization;
    
    getControl().setEventDispatcher(new DoubleClickEventDispatcher(getControl().getEventDispatcher()));
    getControl().addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(getControl(), e));
  }

  private void handleMouseDoubleClickedEvent(TextField control, MouseEvent e) {
    LOGGER.severe("Launching MediumInfo editor");
    MediumInfoListEditor mediumInfoListEditor = MediumInfoListEditor.newInstance(customization, this::setObject);
    mediumInfoListEditor.setObject(getValue());
    mediumInfoListEditor.show();
  }
  
}

class MediumInfoListStringConverter extends StringConverter<List<MediumInfo>> {

  @Override
  public String toString(List<MediumInfo> mediumInfos) {
    if (mediumInfos == null) {
      return null;
    }
    
    StringBuilder buf = new StringBuilder();
    boolean first = true;
    
    for (MediumInfo mediumInfo: mediumInfos) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(mediumInfoToString(mediumInfo));
    }
    
    return buf.toString();
  }

  private String mediumInfoToString(MediumInfo mediumInfo) {
    StringBuilder buf = new StringBuilder();
    
    if (mediumInfo.getMediumType() != null) {
      buf.append(mediumInfo.getMediumType().getLiteral()).append(":");
    }
    
    if (mediumInfo.getInformationType() != null) {
      buf.append(mediumInfo.getInformationType().getLiteral()).append(":");
    }
    
    InformationType sourceType = mediumInfo.getSourceType();
    buf.append(sourceType.getLiteral()).append(":");
    
    if (mediumInfo.isSetSourceBitRate()) {
      buf.append(mediumInfo.getSourceBitRate()).append(":");
    }
    
    return buf.toString();
  }

  @Override
  public List<MediumInfo> fromString(String string) {
    // Not needed
    return null;
  }
  
}
