package goedegep.appgen.eobjecttable;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.EEnumEditorDescriptor;
import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;

public class EObjectTableDescriptor<T extends EObject> {
  private Dimension                                            dimension;
  private Class<? extends EObjectTableModel<T>>                tableModelClass;
  private EObjectTableColumnDescriptor[]                       columnDescriptors;
  private EObjectTableSortingDescriptor                        sortingDescriptor;
  private Map<Class<?>, TableCellRenderer>                     classSpecificCellRenderers;
  private Map<Class<?>, TableCellEditor>                       classSpecificCellEditors;
  private Map<Class<?>, EEnumEditorDescriptor>                 enumSpecificCellEditorValues;
  @SuppressWarnings("rawtypes")
  private Map<Operation, TableRowOperationDescriptor>  rowOperations;

  public EObjectTableDescriptor() {
  }
  
  public EObjectTableDescriptor(
      Dimension dimension,
      EObjectTableColumnDescriptor[]   columnDescriptors,
      EObjectTableSortingDescriptor sortingDescriptor,
      Map<Class<?>, TableCellRenderer> classSpecificCellRenderers,
      Map<Class<?>, TableCellEditor>   classSpecificCellEditors,
      Map<Class<?>, EEnumEditorDescriptor> enumSpecificCellEditorValues,
      @SuppressWarnings("rawtypes") Map<Operation, TableRowOperationDescriptor> rowOperations,
      Class<? extends EObjectTableModel<T>> tableModelClass) {
    this.dimension = dimension;
    this.columnDescriptors = columnDescriptors;
    this.sortingDescriptor = sortingDescriptor;
    this.classSpecificCellRenderers = classSpecificCellRenderers;
    this.classSpecificCellEditors = classSpecificCellEditors;
    this.enumSpecificCellEditorValues = enumSpecificCellEditorValues;
    this.rowOperations = rowOperations;
    this.tableModelClass = tableModelClass;
  }

  public Dimension getDimension() {
    return dimension;
  }
  
  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  public EObjectTableColumnDescriptor[] getColumnDescriptors() {
    return columnDescriptors;
  }

  public EObjectTableSortingDescriptor getSortingDescriptor() {
    return sortingDescriptor;
  }

  public void setColumnDescriptors(EObjectTableColumnDescriptor[] columnDescriptors) {
    this.columnDescriptors = columnDescriptors;
  }

  public Map<Class<?>, TableCellRenderer> getClassSpecificCellRenderers() {
    return classSpecificCellRenderers;
  }

  public void setClassSpecificCellRenderers(Map<Class<?>, TableCellRenderer> classSpecificCellRenderers) {
    this.classSpecificCellRenderers = classSpecificCellRenderers;
  }

  public Map<Class<?>, TableCellEditor> getClassSpecificCellEditors() {
    return classSpecificCellEditors;
  }

  public void setClassSpecificCellEditors(
      Map<Class<?>, TableCellEditor> classSpecificCellEditors) {
    this.classSpecificCellEditors = classSpecificCellEditors;
  }

  public Map<Class<?>, EEnumEditorDescriptor> getEnumSpecificCellEditorValues() {
    return enumSpecificCellEditorValues;
  }

  public void setEnumSpecificCellEditorValues(
      Map<Class<?>, EEnumEditorDescriptor> enumSpecificCellEditorValues) {
    this.enumSpecificCellEditorValues = enumSpecificCellEditorValues;
  }

  @SuppressWarnings("rawtypes")
  public Map<Operation, TableRowOperationDescriptor> getRowOperations() {
    return rowOperations;
  }
  
  public Class<? extends EObjectTableModel<T>> getTableModelClass() {
    return tableModelClass;
  }
}