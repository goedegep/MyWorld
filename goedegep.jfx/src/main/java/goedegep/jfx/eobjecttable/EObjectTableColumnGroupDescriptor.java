package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class EObjectTableColumnGroupDescriptor<T extends EObject> extends EObjectTableColumnDescriptorAbstract<T> {
  private List<EObjectTableColumnDescriptorAbstract<T>> childColumnDescriptors = new ArrayList<>();
  
  @SafeVarargs
  public EObjectTableColumnGroupDescriptor(String columnName, EObjectTableColumnDescriptorAbstract<T>... childColumnDescriptor) {
    super(columnName);
    
    for (EObjectTableColumnDescriptorAbstract<T> aChildColumnDescriptor: childColumnDescriptor) {
      childColumnDescriptors.add(aChildColumnDescriptor);
    }
  }

  public List<EObjectTableColumnDescriptorAbstract<T>> getChildColumnDescriptors() {
    return childColumnDescriptors;
  }
  
}
