/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.util.fixedpointvalue.FixedPointValue;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageEventImpl#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageEventImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageEventImpl#getNewInterestRate <em>New Interest Rate</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MortgageEventImpl extends MinimalEObjectImpl.Container implements MortgageEvent {
  /**
   * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected static final Date DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected Date date = DATE_EDEFAULT;

  /**
   * This is true if the Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dateESet;

  /**
   * The default value of the '{@link #getComments() <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getComments()
   * @generated
   * @ordered
   */
  protected static final String COMMENTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getComments() <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getComments()
   * @generated
   * @ordered
   */
  protected String comments = COMMENTS_EDEFAULT;

  /**
   * This is true if the Comments attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean commentsESet;

  /**
   * The default value of the '{@link #getNewInterestRate() <em>New Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewInterestRate()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue NEW_INTEREST_RATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNewInterestRate() <em>New Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewInterestRate()
   * @generated
   * @ordered
   */
  protected FixedPointValue newInterestRate = NEW_INTEREST_RATE_EDEFAULT;

  /**
   * This is true if the New Interest Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean newInterestRateESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgageEventImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.MORTGAGE_EVENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getDate() {
    return date;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDate(Date newDate) {
    Date oldDate = date;
    date = newDate;
    boolean oldDateESet = dateESet;
    dateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_EVENT__DATE, oldDate, date, !oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDate() {
    Date oldDate = date;
    boolean oldDateESet = dateESet;
    date = DATE_EDEFAULT;
    dateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_EVENT__DATE, oldDate, DATE_EDEFAULT, oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDate() {
    return dateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getComments() {
    return comments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setComments(String newComments) {
    String oldComments = comments;
    comments = newComments;
    boolean oldCommentsESet = commentsESet;
    commentsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_EVENT__COMMENTS, oldComments, comments, !oldCommentsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetComments() {
    String oldComments = comments;
    boolean oldCommentsESet = commentsESet;
    comments = COMMENTS_EDEFAULT;
    commentsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_EVENT__COMMENTS, oldComments, COMMENTS_EDEFAULT, oldCommentsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetComments() {
    return commentsESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getNewInterestRate() {
    return newInterestRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNewInterestRate(FixedPointValue newNewInterestRate) {
    FixedPointValue oldNewInterestRate = newInterestRate;
    newInterestRate = newNewInterestRate;
    boolean oldNewInterestRateESet = newInterestRateESet;
    newInterestRateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE, oldNewInterestRate, newInterestRate, !oldNewInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetNewInterestRate() {
    FixedPointValue oldNewInterestRate = newInterestRate;
    boolean oldNewInterestRateESet = newInterestRateESet;
    newInterestRate = NEW_INTEREST_RATE_EDEFAULT;
    newInterestRateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE, oldNewInterestRate, NEW_INTEREST_RATE_EDEFAULT, oldNewInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetNewInterestRate() {
    return newInterestRateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE_EVENT__DATE:
        return getDate();
      case MortgagePackage.MORTGAGE_EVENT__COMMENTS:
        return getComments();
      case MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE:
        return getNewInterestRate();
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
      case MortgagePackage.MORTGAGE_EVENT__DATE:
        setDate((Date)newValue);
        return;
      case MortgagePackage.MORTGAGE_EVENT__COMMENTS:
        setComments((String)newValue);
        return;
      case MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE:
        setNewInterestRate((FixedPointValue)newValue);
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
      case MortgagePackage.MORTGAGE_EVENT__DATE:
        unsetDate();
        return;
      case MortgagePackage.MORTGAGE_EVENT__COMMENTS:
        unsetComments();
        return;
      case MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE:
        unsetNewInterestRate();
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
      case MortgagePackage.MORTGAGE_EVENT__DATE:
        return isSetDate();
      case MortgagePackage.MORTGAGE_EVENT__COMMENTS:
        return isSetComments();
      case MortgagePackage.MORTGAGE_EVENT__NEW_INTEREST_RATE:
        return isSetNewInterestRate();
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
    result.append(" (date: ");
    if (dateESet) result.append(date); else result.append("<unset>");
    result.append(", comments: ");
    if (commentsESet) result.append(comments); else result.append("<unset>");
    result.append(", newInterestRate: ");
    if (newInterestRateESet) result.append(newInterestRate); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //MortgageEventImpl
