package goedegep.appgen.eobjecttable;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("serial")
public class TestEObjectTableModel<T extends EObject> extends EObjectTableModel<T> {

  public TestEObjectTableModel(EObjectJTable<T> table, EObjectTableColumnDescriptor[] columnDescriptors, List<T> objects) {
    super(null, null, null);
  }
}
