package goedegep.appgen.swing;

/**
 * This class shouldn't be used for now.
 * It can be used when introspection is used to create a table.
 * This class now exists because it was first created to serve for EObjects,
 * which now have EObjectTableDescriptor.
 *
 */
public class ObjectTableDescriptor {
  private ObjectTableColumnDescriptor[] columnDescriptors;

  public ObjectTableDescriptor(
      ObjectTableColumnDescriptor[] columnDescriptors) {
    this.columnDescriptors = columnDescriptors;
  }

  public ObjectTableColumnDescriptor[] getColumnDescriptors() {
    return columnDescriptors;
  }
  

}
