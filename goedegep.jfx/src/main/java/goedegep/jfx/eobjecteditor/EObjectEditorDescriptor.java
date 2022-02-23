package goedegep.jfx.eobjecteditor;

import java.util.ArrayList;
import java.util.List;

public class EObjectEditorDescriptor {
  private String windowTitle = null;
  private List<EObjectAttributeEditDescriptor> eObjectAttributeDescriptors = new ArrayList<>();

  
  public String getWindowTitle() {
    return windowTitle;
  }

  public void setWindowTitle(String windowTitle) {
    this.windowTitle = windowTitle;
  }

  public List<EObjectAttributeEditDescriptor> getEObjectAttributeEditDescriptors() {
    return eObjectAttributeDescriptors;
  }
  
  public EObjectAttributeEditDescriptor getEObjectAttributeEditDescriptor(String id) {
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: eObjectAttributeDescriptors) {
      if (eObjectAttributeEditDescriptor.getId().equals(id)) {
        return eObjectAttributeEditDescriptor;
      }
    }
    
    return null;
  }
}
