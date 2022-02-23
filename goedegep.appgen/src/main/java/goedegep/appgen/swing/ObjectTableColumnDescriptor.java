package goedegep.appgen.swing;

/**
 * This class shouldn't be used for now.
 * It can be used when introspection is used to create a table.
 * This class now exists because it was first created to serve for EObjects,
 * which now have EObjectTableColumnDescriptor.
 *
 */
public class ObjectTableColumnDescriptor {
  private String featureName;
  private String columnName;
  private Object longValue;
  private Class<?> featureClass;
  
  public ObjectTableColumnDescriptor(String featureName, String columnName,
      Object longValue, Class<?> featureClass) {
    super();
    this.featureName = featureName;
    this.columnName = columnName;
    this.longValue = longValue;
    this.featureClass = featureClass;
  }

  public String getFeatureName() {
    return featureName;
  }

  public String getColumnName() {
    return columnName;
  }

  public Object getLongValue() {
    return longValue;
  }

  @SuppressWarnings("rawtypes")
  public Class getFeatureClass() {
    return featureClass;
  }

}
