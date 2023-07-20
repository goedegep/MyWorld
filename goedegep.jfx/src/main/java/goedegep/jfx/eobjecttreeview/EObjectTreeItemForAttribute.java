package goedegep.jfx.eobjecttreeview;

import org.eclipse.emf.ecore.EStructuralFeature;

public class EObjectTreeItemForAttribute extends EObjectTreeItem {

  public EObjectTreeItemForAttribute(Object object, EObjectTreeItemType eObjectTreeItemType, EStructuralFeature eStructuralFeature,
      EObjectTreeItemDescriptor presentationDescriptor, EObjectTreeView eObjectTreeView) {
    super(object, eObjectTreeItemType, eStructuralFeature, presentationDescriptor, eObjectTreeView);
  }
}
