package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;

/**
 * This class is the parent for all column descriptor classes.
 * <p>
 * This class defines the following properties.
 * <ul>
 * <li>columnName<br/>
 * The text (title or name) of the column.
 * </li>
 * <li>id<br/>
 * An identification of the column. This can e.g. be used to get a specific TableColumn from the Table.
 * </li>
 * </ul>
 *
 * @param <T> The type of the items listed in the table.
 */
public class EObjectTableColumnDescriptorAbstract<T extends EObject> {
  
  /**
   * The name of the column (optional).
   */
  private String columnName;
  
  /**
   * 
   * Identification of the column (optional).
   */
  private String id;
  
  /**
   * Constructor.
   * 
   * @param columnName
   */
  public EObjectTableColumnDescriptorAbstract(String columnName) {
    this(null, columnName);
  }

  /**
   * Constructor.
   * 
   * @param columnName
   */
  public EObjectTableColumnDescriptorAbstract(String id, String columnName) {
    this.id = id;
    this.columnName = columnName;
  }

  /**
   * Get the <code>columnName</code>.
   * 
   * @return the <code>columnName</code>.
   */
  public String getColumnName() {
    return columnName;
  }

  /**
   * Set the <code>columnName</code>.
   * 
   * @param columnName the new value for the <code>columnName</code>.
   */
  public void setColumnNameAbstract(String columnName) {
    this.columnName = columnName;
  }

  /**
   * Get the column identification string.
   * 
   * @return the column identification string.
   */
  public String getId() {
    return id;
  }

  /**
   * Set the column identification string.
   * 
   * @param id the column identification string.
   */
  public void setId(String id) {
    this.id = id;
  }
}
