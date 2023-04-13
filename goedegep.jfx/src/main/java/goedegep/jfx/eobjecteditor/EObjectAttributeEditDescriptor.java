package goedegep.jfx.eobjecteditor;

import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.jfx.objectcontrols.ObjectControl;

public class EObjectAttributeEditDescriptor  {
  private String labelText;
  private ObjectControl<?> objectControl;
  private EStructuralFeature structuralFeature;
  private String  id;
  
  public EObjectAttributeEditDescriptor(String labelText, ObjectControl<?> objectControl, EStructuralFeature structuralFeature) {
    this(labelText, objectControl, structuralFeature, null);
  }
  
  public EObjectAttributeEditDescriptor(String labelText, ObjectControl<?> objectControl,EStructuralFeature structuralFeature, String id) {
    this.labelText = labelText;
    this.objectControl = objectControl;
    this.structuralFeature = structuralFeature;
    if (id != null) {
      this.id = id;
    } else {
      this.id = structuralFeature.getName();
    }
  }

  public String getLabelText() {
    return labelText;
  }

  public ObjectControl<?> getObjectControl() {
    return objectControl;
  }

  public EStructuralFeature getStructuralFeature() {
    return structuralFeature;
  }

  public String getId() {
    return id;
  }

}
