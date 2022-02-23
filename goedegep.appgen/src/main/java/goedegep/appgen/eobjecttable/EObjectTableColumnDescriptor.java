package goedegep.appgen.eobjecttable;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * This class describes one column of an EObjectTable.
 */
public class EObjectTableColumnDescriptor {
  private EStructuralFeature eStructuralFeature;
  private String columnName;
  private Object longValue;
  private boolean isEditable;
  private TableCellRenderer renderer;
  private TableCellEditor editor;

  
  public EObjectTableColumnDescriptor(EStructuralFeature eStructuralFeature) {
    this(eStructuralFeature, null, null, false);
  }
  
  public EObjectTableColumnDescriptor(EStructuralFeature eStructuralFeature,
      String columnName, Object longValue, boolean isEditable) {
    this(eStructuralFeature, columnName, longValue, isEditable, null, null);
    this.eStructuralFeature = eStructuralFeature;
  }
  
  public EObjectTableColumnDescriptor(EStructuralFeature eStructuralFeature,
      String columnName, Object longValue, boolean isEditable, TableCellRenderer renderer, TableCellEditor editor) {
    this.eStructuralFeature = eStructuralFeature;
    this.columnName = columnName;
    this.longValue = longValue;
    this.isEditable = isEditable;
    this.renderer = renderer;
    this.editor = editor;
  }

  public EStructuralFeature geteStructuralFeature() {
    return eStructuralFeature;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public Object getLongValue() {
    return longValue;
  }

  public void setLongValue(Object longValue) {
    this.longValue = longValue;
  }

  public boolean isEditable() {
    return isEditable;
  }

  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }

  public TableCellRenderer getRenderer() {
    return renderer;
  }

  public void setRenderer(TableCellRenderer renderer) {
    this.renderer = renderer;
  }

  public TableCellEditor getEditor() {
    return editor;
  }

  public void setEditor(TableCellEditor editor) {
    this.editor = editor;
  }
}
