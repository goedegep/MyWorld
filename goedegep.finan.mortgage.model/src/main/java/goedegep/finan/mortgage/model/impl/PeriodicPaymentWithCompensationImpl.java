/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Periodic Payment With Compensation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl#getBorrowerCompensation <em>Borrower Compensation</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PeriodicPaymentWithCompensationImpl extends PeriodicPaymentImpl implements PeriodicPaymentWithCompensation {
  /**
   * The default value of the '{@link #getBorrowerCompensation() <em>Borrower Compensation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBorrowerCompensation()
   * @generated
   * @ordered
   */
  protected static final PgCurrency BORROWER_COMPENSATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBorrowerCompensation() <em>Borrower Compensation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBorrowerCompensation()
   * @generated
   * @ordered
   */
  protected PgCurrency borrowerCompensation = BORROWER_COMPENSATION_EDEFAULT;

  /**
   * This is true if the Borrower Compensation attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean borrowerCompensationESet;

  /**
   * The default value of the '{@link #getDecemberPaymentAccumulation() <em>December Payment Accumulation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDecemberPaymentAccumulation()
   * @generated
   * @ordered
   */
  protected static final PgCurrency DECEMBER_PAYMENT_ACCUMULATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDecemberPaymentAccumulation() <em>December Payment Accumulation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDecemberPaymentAccumulation()
   * @generated
   * @ordered
   */
  protected PgCurrency decemberPaymentAccumulation = DECEMBER_PAYMENT_ACCUMULATION_EDEFAULT;

  /**
   * This is true if the December Payment Accumulation attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean decemberPaymentAccumulationESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PeriodicPaymentWithCompensationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.PERIODIC_PAYMENT_WITH_COMPENSATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getBorrowerCompensation() {
    return borrowerCompensation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBorrowerCompensation(PgCurrency newBorrowerCompensation) {
    PgCurrency oldBorrowerCompensation = borrowerCompensation;
    borrowerCompensation = newBorrowerCompensation;
    boolean oldBorrowerCompensationESet = borrowerCompensationESet;
    borrowerCompensationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION, oldBorrowerCompensation, borrowerCompensation, !oldBorrowerCompensationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBorrowerCompensation() {
    PgCurrency oldBorrowerCompensation = borrowerCompensation;
    boolean oldBorrowerCompensationESet = borrowerCompensationESet;
    borrowerCompensation = BORROWER_COMPENSATION_EDEFAULT;
    borrowerCompensationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION, oldBorrowerCompensation, BORROWER_COMPENSATION_EDEFAULT, oldBorrowerCompensationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBorrowerCompensation() {
    return borrowerCompensationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getDecemberPaymentAccumulation() {
    return decemberPaymentAccumulation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDecemberPaymentAccumulation(PgCurrency newDecemberPaymentAccumulation) {
    PgCurrency oldDecemberPaymentAccumulation = decemberPaymentAccumulation;
    decemberPaymentAccumulation = newDecemberPaymentAccumulation;
    boolean oldDecemberPaymentAccumulationESet = decemberPaymentAccumulationESet;
    decemberPaymentAccumulationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION, oldDecemberPaymentAccumulation, decemberPaymentAccumulation, !oldDecemberPaymentAccumulationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDecemberPaymentAccumulation() {
    PgCurrency oldDecemberPaymentAccumulation = decemberPaymentAccumulation;
    boolean oldDecemberPaymentAccumulationESet = decemberPaymentAccumulationESet;
    decemberPaymentAccumulation = DECEMBER_PAYMENT_ACCUMULATION_EDEFAULT;
    decemberPaymentAccumulationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION, oldDecemberPaymentAccumulation, DECEMBER_PAYMENT_ACCUMULATION_EDEFAULT, oldDecemberPaymentAccumulationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDecemberPaymentAccumulation() {
    return decemberPaymentAccumulationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION:
        return getBorrowerCompensation();
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION:
        return getDecemberPaymentAccumulation();
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
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION:
        setBorrowerCompensation((PgCurrency)newValue);
        return;
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION:
        setDecemberPaymentAccumulation((PgCurrency)newValue);
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
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION:
        unsetBorrowerCompensation();
        return;
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION:
        unsetDecemberPaymentAccumulation();
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
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION:
        return isSetBorrowerCompensation();
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION:
        return isSetDecemberPaymentAccumulation();
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
    result.append(" (borrowerCompensation: ");
    if (borrowerCompensationESet) result.append(borrowerCompensation); else result.append("<unset>");
    result.append(", decemberPaymentAccumulation: ");
    if (decemberPaymentAccumulationESet) result.append(decemberPaymentAccumulation); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PeriodicPaymentWithCompensationImpl
