/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.NewInterestRateWithCompensation;
import goedegep.util.fixedpointvalue.FixedPointValue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>New Interest Rate With Compensation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl#getPercentageDecemberPayment <em>Percentage December Payment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NewInterestRateWithCompensationImpl extends NewInterestRateImpl implements NewInterestRateWithCompensation {
  /**
   * The default value of the '{@link #getCompensationPercentageBorrower() <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPercentageBorrower()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompensationPercentageBorrower() <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPercentageBorrower()
   * @generated
   * @ordered
   */
  protected FixedPointValue compensationPercentageBorrower = COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT;

  /**
   * This is true if the Compensation Percentage Borrower attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compensationPercentageBorrowerESet;

  /**
   * The default value of the '{@link #getPercentageDecemberPayment() <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentageDecemberPayment()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPercentageDecemberPayment() <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentageDecemberPayment()
   * @generated
   * @ordered
   */
  protected FixedPointValue percentageDecemberPayment = PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT;

  /**
   * This is true if the Percentage December Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean percentageDecemberPaymentESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NewInterestRateWithCompensationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.NEW_INTEREST_RATE_WITH_COMPENSATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getCompensationPercentageBorrower() {
    return compensationPercentageBorrower;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompensationPercentageBorrower(FixedPointValue newCompensationPercentageBorrower) {
    FixedPointValue oldCompensationPercentageBorrower = compensationPercentageBorrower;
    compensationPercentageBorrower = newCompensationPercentageBorrower;
    boolean oldCompensationPercentageBorrowerESet = compensationPercentageBorrowerESet;
    compensationPercentageBorrowerESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER, oldCompensationPercentageBorrower, compensationPercentageBorrower, !oldCompensationPercentageBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompensationPercentageBorrower() {
    FixedPointValue oldCompensationPercentageBorrower = compensationPercentageBorrower;
    boolean oldCompensationPercentageBorrowerESet = compensationPercentageBorrowerESet;
    compensationPercentageBorrower = COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT;
    compensationPercentageBorrowerESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER, oldCompensationPercentageBorrower, COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT, oldCompensationPercentageBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompensationPercentageBorrower() {
    return compensationPercentageBorrowerESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getPercentageDecemberPayment() {
    return percentageDecemberPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPercentageDecemberPayment(FixedPointValue newPercentageDecemberPayment) {
    FixedPointValue oldPercentageDecemberPayment = percentageDecemberPayment;
    percentageDecemberPayment = newPercentageDecemberPayment;
    boolean oldPercentageDecemberPaymentESet = percentageDecemberPaymentESet;
    percentageDecemberPaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT, oldPercentageDecemberPayment, percentageDecemberPayment, !oldPercentageDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPercentageDecemberPayment() {
    FixedPointValue oldPercentageDecemberPayment = percentageDecemberPayment;
    boolean oldPercentageDecemberPaymentESet = percentageDecemberPaymentESet;
    percentageDecemberPayment = PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT;
    percentageDecemberPaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT, oldPercentageDecemberPayment, PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT, oldPercentageDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPercentageDecemberPayment() {
    return percentageDecemberPaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER:
        return getCompensationPercentageBorrower();
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT:
        return getPercentageDecemberPayment();
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
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER:
        setCompensationPercentageBorrower((FixedPointValue)newValue);
        return;
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT:
        setPercentageDecemberPayment((FixedPointValue)newValue);
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
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER:
        unsetCompensationPercentageBorrower();
        return;
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT:
        unsetPercentageDecemberPayment();
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
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER:
        return isSetCompensationPercentageBorrower();
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT:
        return isSetPercentageDecemberPayment();
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
    result.append(" (compensationPercentageBorrower: ");
    if (compensationPercentageBorrowerESet) result.append(compensationPercentageBorrower); else result.append("<unset>");
    result.append(", percentageDecemberPayment: ");
    if (percentageDecemberPaymentESet) result.append(percentageDecemberPayment); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //NewInterestRateWithCompensationImpl
