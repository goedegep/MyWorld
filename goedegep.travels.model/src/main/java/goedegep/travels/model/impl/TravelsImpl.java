/**
 */
package goedegep.travels.model.impl;

import goedegep.travels.model.DayTrip;
import goedegep.travels.model.Location;
import goedegep.travels.model.Travel;
import goedegep.travels.model.TravelCategory;
import goedegep.travels.model.Vacation;
import goedegep.travels.model.Travels;
import goedegep.travels.model.TravelsPackage;
import goedegep.util.datetime.FlexDate;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vakanties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.TravelsImpl#getVacations <em>Vacations</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelsImpl#getHome <em>Home</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelsImpl#getTips <em>Tips</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelsImpl#getDayTrips <em>Day Trips</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelsImpl#getTravelCategories <em>Travel Categories</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TravelsImpl extends MinimalEObjectImpl.Container implements Travels {
  /**
   * The cached value of the '{@link #getVacations() <em>Vacations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacations()
   * @generated
   * @ordered
   */
  protected EList<Vacation> vacations;

  /**
   * The cached value of the '{@link #getHome() <em>Home</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHome()
   * @generated
   * @ordered
   */
  protected Location home;
  /**
   * This is true if the Home containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean homeESet;

  /**
   * The default value of the '{@link #getTips() <em>Tips</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTips()
   * @generated
   * @ordered
   */
  protected static final String TIPS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTips() <em>Tips</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTips()
   * @generated
   * @ordered
   */
  protected String tips = TIPS_EDEFAULT;

  /**
   * The cached value of the '{@link #getDayTrips() <em>Day Trips</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDayTrips()
   * @generated
   * @ordered
   */
  protected EList<DayTrip> dayTrips;

  /**
   * The cached value of the '{@link #getTravelCategories() <em>Travel Categories</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravelCategories()
   * @generated
   * @ordered
   */
  protected EList<TravelCategory> travelCategories;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelsImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.TRAVELS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Vacation> getVacations() {
    if (vacations == null) {
      vacations = new EObjectContainmentEList.Unsettable<Vacation>(Vacation.class, this,
          TravelsPackage.TRAVELS__VACATIONS);
    }
    return vacations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetVacations() {
    if (vacations != null)
      ((InternalEList.Unsettable<?>) vacations).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetVacations() {
    return vacations != null && ((InternalEList.Unsettable<?>) vacations).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Location getHome() {
    return home;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHome(Location newHome, NotificationChain msgs) {
    Location oldHome = home;
    home = newHome;
    boolean oldHomeESet = homeESet;
    homeESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVELS__HOME,
          oldHome, newHome, !oldHomeESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHome(Location newHome) {
    if (newHome != home) {
      NotificationChain msgs = null;
      if (home != null)
        msgs = ((InternalEObject) home).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TravelsPackage.TRAVELS__HOME,
            null, msgs);
      if (newHome != null)
        msgs = ((InternalEObject) newHome).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TravelsPackage.TRAVELS__HOME,
            null, msgs);
      msgs = basicSetHome(newHome, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldHomeESet = homeESet;
      homeESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVELS__HOME, newHome, newHome,
            !oldHomeESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetHome(NotificationChain msgs) {
    Location oldHome = home;
    home = null;
    boolean oldHomeESet = homeESet;
    homeESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, TravelsPackage.TRAVELS__HOME,
          oldHome, null, oldHomeESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetHome() {
    if (home != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) home).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TravelsPackage.TRAVELS__HOME, null,
          msgs);
      msgs = basicUnsetHome(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldHomeESet = homeESet;
      homeESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.TRAVELS__HOME, null, null, oldHomeESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetHome() {
    return homeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTips() {
    return tips;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTips(String newTips) {
    String oldTips = tips;
    tips = newTips;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVELS__TIPS, oldTips, tips));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DayTrip> getDayTrips() {
    if (dayTrips == null) {
      dayTrips = new EObjectContainmentEList<DayTrip>(DayTrip.class, this, TravelsPackage.TRAVELS__DAY_TRIPS);
    }
    return dayTrips;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TravelCategory> getTravelCategories() {
    if (travelCategories == null) {
      travelCategories = new EObjectContainmentEList<TravelCategory>(TravelCategory.class, this,
          TravelsPackage.TRAVELS__TRAVEL_CATEGORIES);
    }
    return travelCategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Vacation findVacation(FlexDate date) {
    for (Vacation vacation : getVacations()) {
      if (vacation.getDate().equals(date)) {
        return vacation;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public EList<Travel> getTravels() {
    EList<Travel> travels = new BasicEList<>();

    travels.addAll(getVacations());
    travels.addAll(getDayTrips());

    for (TravelCategory travelCategory : getTravelCategories()) {
      travels.addAll(travelCategory.getTravels());
    }

    return travels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Vacation findVacation(FlexDate date, String title) {
    for (Vacation vacation : getVacations()) {
      if (vacation.getDate().equals(date) && vacation.getTitle().equals(title)) {
        return vacation;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public void addVacation(Vacation vacation) {
    if (!vacation.isSetDate()) {
      throw new IllegalArgumentException("vacation must have attribute date set.");
    }

    boolean added = false;
    FlexDate date = vacation.getDate();
    EList<Vacation> currentVacations = getVacations();
    for (int i = 0; i < currentVacations.size(); i++) {
      Vacation currentVacation = currentVacations.get(i);
      if (date.compareTo(currentVacation.getDate()) > 0) {
        getVacations().add(i, vacation);
        added = true;
        break;
      }
    }

    if (!added) {
      currentVacations.add(vacation);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case TravelsPackage.TRAVELS__VACATIONS:
      return ((InternalEList<?>) getVacations()).basicRemove(otherEnd, msgs);
    case TravelsPackage.TRAVELS__HOME:
      return basicUnsetHome(msgs);
    case TravelsPackage.TRAVELS__DAY_TRIPS:
      return ((InternalEList<?>) getDayTrips()).basicRemove(otherEnd, msgs);
    case TravelsPackage.TRAVELS__TRAVEL_CATEGORIES:
      return ((InternalEList<?>) getTravelCategories()).basicRemove(otherEnd, msgs);
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
    case TravelsPackage.TRAVELS__VACATIONS:
      return getVacations();
    case TravelsPackage.TRAVELS__HOME:
      return getHome();
    case TravelsPackage.TRAVELS__TIPS:
      return getTips();
    case TravelsPackage.TRAVELS__DAY_TRIPS:
      return getDayTrips();
    case TravelsPackage.TRAVELS__TRAVEL_CATEGORIES:
      return getTravelCategories();
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
    case TravelsPackage.TRAVELS__VACATIONS:
      getVacations().clear();
      getVacations().addAll((Collection<? extends Vacation>) newValue);
      return;
    case TravelsPackage.TRAVELS__HOME:
      setHome((Location) newValue);
      return;
    case TravelsPackage.TRAVELS__TIPS:
      setTips((String) newValue);
      return;
    case TravelsPackage.TRAVELS__DAY_TRIPS:
      getDayTrips().clear();
      getDayTrips().addAll((Collection<? extends DayTrip>) newValue);
      return;
    case TravelsPackage.TRAVELS__TRAVEL_CATEGORIES:
      getTravelCategories().clear();
      getTravelCategories().addAll((Collection<? extends TravelCategory>) newValue);
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
    case TravelsPackage.TRAVELS__VACATIONS:
      unsetVacations();
      return;
    case TravelsPackage.TRAVELS__HOME:
      unsetHome();
      return;
    case TravelsPackage.TRAVELS__TIPS:
      setTips(TIPS_EDEFAULT);
      return;
    case TravelsPackage.TRAVELS__DAY_TRIPS:
      getDayTrips().clear();
      return;
    case TravelsPackage.TRAVELS__TRAVEL_CATEGORIES:
      getTravelCategories().clear();
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
    case TravelsPackage.TRAVELS__VACATIONS:
      return isSetVacations();
    case TravelsPackage.TRAVELS__HOME:
      return isSetHome();
    case TravelsPackage.TRAVELS__TIPS:
      return TIPS_EDEFAULT == null ? tips != null : !TIPS_EDEFAULT.equals(tips);
    case TravelsPackage.TRAVELS__DAY_TRIPS:
      return dayTrips != null && !dayTrips.isEmpty();
    case TravelsPackage.TRAVELS__TRAVEL_CATEGORIES:
      return travelCategories != null && !travelCategories.isEmpty();
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
    case TravelsPackage.TRAVELS___FIND_VACATION__FLEXDATE_STRING:
      return findVacation((FlexDate) arguments.get(0), (String) arguments.get(1));
    case TravelsPackage.TRAVELS___ADD_VACATION__VACATION:
      addVacation((Vacation) arguments.get(0));
      return null;
    case TravelsPackage.TRAVELS___FIND_VACATION__FLEXDATE:
      return findVacation((FlexDate) arguments.get(0));
    case TravelsPackage.TRAVELS___GET_TRAVELS:
      return getTravels();
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
    result.append(" (tips: ");
    result.append(tips);
    result.append(')');
    return result.toString();
  }

} //VakantiesImpl
