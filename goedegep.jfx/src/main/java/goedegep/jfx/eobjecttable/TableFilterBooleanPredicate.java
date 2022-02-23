package goedegep.jfx.eobjecttable;

import java.util.function.Predicate;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This class is a Predicate used to filter rows from a table. It filters on a boolean value of a column.
 *
 * @param <T> The type of the items in the table.
 */
public class TableFilterBooleanPredicate<T extends EObject> implements Predicate<T> {
  private static final Logger LOGGER = Logger.getLogger(TableFilterBooleanPredicate.class.getName());

  /**
   * The filter value.
   */
  private boolean value;
  
  /**
   * The EStructuralFeature to filter on.
   */
  private EAttribute eAttribute;
  
  /**
   * Constructor.
   * 
   * @param value The filter value.
   * @param eStructuralFeature The EStructuralFeature to filter on, or null to filter on all attributes of the item (whether shown in the table or not).
   */
  public TableFilterBooleanPredicate(boolean value, EAttribute eAttribute) {
    this.value = value;
    this.eAttribute = eAttribute;
  }

  @Override
  public boolean test(EObject eObject) {
    LOGGER.info("=>" );
    
    for (EAttribute currentEAttribute: eObject.eClass().getEAllAttributes()) {
      if (!currentEAttribute.equals(eAttribute)) {
        continue;
      }
      
      LOGGER.info("currentEAttribute=" + currentEAttribute.getName());
        Object attribute = eObject.eGet(eAttribute);
        if (attribute != null) {
          Boolean attributeValue = (Boolean) attribute;
          LOGGER.info("attributeValue=" + attributeValue);
          if (attributeValue == value) {
            LOGGER.info("<= HIT");
            return true;
          }
        }
    }
    
    LOGGER.info("<= false");
    return false;
  }
  
}
