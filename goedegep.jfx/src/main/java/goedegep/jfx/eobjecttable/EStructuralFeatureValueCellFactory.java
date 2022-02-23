package goedegep.jfx.eobjecttable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * This class provides a cell value for an <code>eTypedElement</code> of the row value.
 *
 * @param <S> The type of the objects contained within the TableView items list.
 * @param <T> The column (and thus the cell) data type.
 */
public class EStructuralFeatureValueCellFactory<S extends EObject, T> implements Callback<CellDataFeatures<S, T>, ObservableValue<T>> {
  private final static Logger LOGGER = Logger.getLogger(EStructuralFeatureValueCellFactory.class.getName());

    private final List<ETypedElement> eTypedElements;
    
    /**
     * Constructor
     * 
     * @param eTypedElement the attribute, reference, or operation to be used to get the value from the row object.
     */
    public EStructuralFeatureValueCellFactory(List<ETypedElement> eTypedElements) {
        this.eTypedElements = eTypedElements;
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public ObservableValue<T> call(CellDataFeatures<S, T> param) {
      S value = param.getValue();
      if (value == null) {
        LOGGER.severe("parm value is null!!");
        return null;
      }
      T object = null;
      
      Object currentValue = value;
      LOGGER.info("initial currentValue=" + currentValue);
      for (ETypedElement eTypedElement: eTypedElements) {
        LOGGER.info("eTypedElement=" + (eTypedElement != null ? eTypedElement.getName() : "(null)"));
        if (currentValue == null) {
          return null;
        }
        
        if (eTypedElement instanceof EStructuralFeature) {
//          if (((EObject) currentValue).eIsSet((EStructuralFeature) eTypedElement)) {
            currentValue = ((EObject) currentValue).eGet((EStructuralFeature) eTypedElement);
//          } else {
//            currentValue = null;
//          }
        } else if (eTypedElement instanceof EOperation) {
          try {
            currentValue = ((EObject) currentValue).eInvoke((EOperation) eTypedElement, null);
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
        LOGGER.info("currentValue: " + currentValue);
      }
      object = (T) currentValue;
      LOGGER.info("eTypedElements=" + eTypedElements + ", Object: " + object);
      
      return new ReadOnlyObjectWrapper<T>(object);
    }

}
