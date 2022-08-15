package goedegep.jfx.eobjecttable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * This class is a column descriptor for a group column with its sub columns.
 * <p>
 * One attribute is added to the EObjectTableColumnDescriptorAbstract; a list of child column descriptors.
 */
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
