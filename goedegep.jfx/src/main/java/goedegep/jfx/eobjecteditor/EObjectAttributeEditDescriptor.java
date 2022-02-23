package goedegep.jfx.eobjecteditor;

import org.eclipse.emf.ecore.EStructuralFeature;

import javafx.scene.Node;

public class EObjectAttributeEditDescriptor  {
  private String labelText;
  private Node node;
  private EStructuralFeature structuralFeature;
  private String  id;
  
  public EObjectAttributeEditDescriptor(String labelText, Node node,EStructuralFeature structuralFeature) {
    this(labelText, node, structuralFeature, null);
  }
  
  public EObjectAttributeEditDescriptor(String labelText, Node node,EStructuralFeature structuralFeature, String id) {
    this.labelText = labelText;
    this.node = node;
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

  public Node getNode() {
    return node;
  }

  public EStructuralFeature getStructuralFeature() {
    return structuralFeature;
  }

  public String getId() {
    return id;
  }

}
