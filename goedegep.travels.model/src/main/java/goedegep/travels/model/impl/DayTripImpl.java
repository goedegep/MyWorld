/**
 */
package goedegep.travels.model.impl;

import goedegep.travels.model.DayTrip;
import goedegep.travels.model.TravelsPackage;
import goedegep.util.datetime.FlexDateFormat;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Day Trip</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DayTripImpl extends TravelImpl implements DayTrip {
  private static final FlexDateFormat FDF = new FlexDateFormat(true, true);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DayTripImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.DAY_TRIP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String getId() {
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;
    if (isSetDate()) {
      buf.append(FDF.format(getDate()));
      spaceNeeded = true;
    }
    if (isSetTitle()) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(getTitle());
    }
    return buf.toString();
  }

} //DayTripImpl
