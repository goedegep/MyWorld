/**
 */
package goedegep.vacations.model.impl;

import goedegep.types.model.impl.EventImpl;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.vacations.model.DayTrip;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsPackage;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Day Trip</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.DayTripImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.DayTripImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DayTripImpl extends EventImpl implements DayTrip {
  private static final FlexDateFormat FDF = new FlexDateFormat(true, true);

  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;

  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected EList<VacationElement> elements;

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
    return VacationsPackage.Literals.DAY_TRIP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.DAY_TRIP__TITLE, oldTitle, title,
          !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.DAY_TRIP__TITLE, oldTitle,
          TITLE_EDEFAULT, oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationElement> getElements() {
    if (elements == null) {
      elements = new EObjectContainmentEList<VacationElement>(VacationElement.class, this,
          VacationsPackage.DAY_TRIP__ELEMENTS);
    }
    return elements;
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

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.DAY_TRIP__ELEMENTS:
      return ((InternalEList<?>) getElements()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case VacationsPackage.DAY_TRIP__TITLE:
      return getTitle();
    case VacationsPackage.DAY_TRIP__ELEMENTS:
      return getElements();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case VacationsPackage.DAY_TRIP__TITLE:
      setTitle((String) newValue);
      return;
    case VacationsPackage.DAY_TRIP__ELEMENTS:
      getElements().clear();
      getElements().addAll((Collection<? extends VacationElement>) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case VacationsPackage.DAY_TRIP__TITLE:
      unsetTitle();
      return;
    case VacationsPackage.DAY_TRIP__ELEMENTS:
      getElements().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case VacationsPackage.DAY_TRIP__TITLE:
      return isSetTitle();
    case VacationsPackage.DAY_TRIP__ELEMENTS:
      return elements != null && !elements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case VacationsPackage.DAY_TRIP___GET_ID:
      return getId();
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (title: ");
    if (titleESet)
      result.append(title);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //DayTripImpl
