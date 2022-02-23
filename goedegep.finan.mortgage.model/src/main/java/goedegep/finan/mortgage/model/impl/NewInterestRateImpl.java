/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.util.fixedpointvalue.FixedPointValue;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>New Interest Rate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.NewInterestRateImpl#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.NewInterestRateImpl#getFixedInterestPeriod <em>Fixed Interest Period</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.NewInterestRateImpl#getStartingDate <em>Starting Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NewInterestRateImpl extends MortgageEventImpl implements NewInterestRate {
  /**
   * The default value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue INTEREST_RATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected FixedPointValue interestRate = INTEREST_RATE_EDEFAULT;

  /**
   * This is true if the Interest Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean interestRateESet;

  /**
   * The default value of the '{@link #getFixedInterestPeriod() <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedInterestPeriod()
   * @generated
   * @ordered
   */
  protected static final int FIXED_INTEREST_PERIOD_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getFixedInterestPeriod() <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedInterestPeriod()
   * @generated
   * @ordered
   */
  protected int fixedInterestPeriod = FIXED_INTEREST_PERIOD_EDEFAULT;

  /**
   * This is true if the Fixed Interest Period attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fixedInterestPeriodESet;

  /**
   * The default value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected static final Date STARTING_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected Date startingDate = STARTING_DATE_EDEFAULT;

  /**
   * This is true if the Starting Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startingDateESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NewInterestRateImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.NEW_INTEREST_RATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getInterestRate() {
    return interestRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInterestRate(FixedPointValue newInterestRate) {
    FixedPointValue oldInterestRate = interestRate;
    interestRate = newInterestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE, oldInterestRate, interestRate, !oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInterestRate() {
    FixedPointValue oldInterestRate = interestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRate = INTEREST_RATE_EDEFAULT;
    interestRateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE, oldInterestRate, INTEREST_RATE_EDEFAULT, oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInterestRate() {
    return interestRateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getFixedInterestPeriod() {
    return fixedInterestPeriod;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFixedInterestPeriod(int newFixedInterestPeriod) {
    int oldFixedInterestPeriod = fixedInterestPeriod;
    fixedInterestPeriod = newFixedInterestPeriod;
    boolean oldFixedInterestPeriodESet = fixedInterestPeriodESet;
    fixedInterestPeriodESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD, oldFixedInterestPeriod, fixedInterestPeriod, !oldFixedInterestPeriodESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFixedInterestPeriod() {
    int oldFixedInterestPeriod = fixedInterestPeriod;
    boolean oldFixedInterestPeriodESet = fixedInterestPeriodESet;
    fixedInterestPeriod = FIXED_INTEREST_PERIOD_EDEFAULT;
    fixedInterestPeriodESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD, oldFixedInterestPeriod, FIXED_INTEREST_PERIOD_EDEFAULT, oldFixedInterestPeriodESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFixedInterestPeriod() {
    return fixedInterestPeriodESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getStartingDate() {
    return startingDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingDate(Date newStartingDate) {
    Date oldStartingDate = startingDate;
    startingDate = newStartingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE, oldStartingDate, startingDate, !oldStartingDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartingDate() {
    Date oldStartingDate = startingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDate = STARTING_DATE_EDEFAULT;
    startingDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE, oldStartingDate, STARTING_DATE_EDEFAULT, oldStartingDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStartingDate() {
    return startingDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE:
        return getInterestRate();
      case MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD:
        return getFixedInterestPeriod();
      case MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE:
        return getStartingDate();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE:
        setInterestRate((FixedPointValue)newValue);
        return;
      case MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD:
        setFixedInterestPeriod((Integer)newValue);
        return;
      case MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE:
        setStartingDate((Date)newValue);
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
      case MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE:
        unsetInterestRate();
        return;
      case MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD:
        unsetFixedInterestPeriod();
        return;
      case MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE:
        unsetStartingDate();
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
      case MortgagePackage.NEW_INTEREST_RATE__INTEREST_RATE:
        return isSetInterestRate();
      case MortgagePackage.NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD:
        return isSetFixedInterestPeriod();
      case MortgagePackage.NEW_INTEREST_RATE__STARTING_DATE:
        return isSetStartingDate();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (interestRate: ");
    if (interestRateESet) result.append(interestRate); else result.append("<unset>");
    result.append(", fixedInterestPeriod: ");
    if (fixedInterestPeriodESet) result.append(fixedInterestPeriod); else result.append("<unset>");
    result.append(", startingDate: ");
    if (startingDateESet) result.append(startingDate); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //NewInterestRateImpl
