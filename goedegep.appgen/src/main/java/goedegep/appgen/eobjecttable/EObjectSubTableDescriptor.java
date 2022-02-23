package goedegep.appgen.eobjecttable;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EObjectSubTableDescriptor {
  private EObjectJTable<? extends EObject>   eObjectTable;
  private List<EStructuralFeature>          eStructuralFeatures;

  public EObjectSubTableDescriptor(EObjectJTable<? extends EObject> eObjectTable, List<EStructuralFeature> eStructuralFeatures) {
    this.eObjectTable = eObjectTable;
    this.eStructuralFeatures = eStructuralFeatures;
  }

  public EObjectJTable<? extends EObject> geteObjectTable() {
    return eObjectTable;
  }

  public List<EStructuralFeature> geteStructuralFeatures() {
    return eStructuralFeatures;
  }
}
