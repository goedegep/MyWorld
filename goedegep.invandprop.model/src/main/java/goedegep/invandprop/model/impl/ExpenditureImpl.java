/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.Expenditure;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Property;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expenditure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.ExpenditureImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.ExpenditureImpl#getAmount <em>Amount</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.ExpenditureImpl#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.ExpenditureImpl#isDescriptionFromProperty <em>Description From Property</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.ExpenditureImpl#getPurchase <em>Purchase</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExpenditureImpl extends MinimalEObjectImpl.Container implements Expenditure {
  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * This is true if the Description attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionESet;

  /**
   * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected static final PgCurrency AMOUNT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected PgCurrency amount = AMOUNT_EDEFAULT;

  /**
   * This is true if the Amount attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean amountESet;

  /**
   * The default value of the '{@link #getRemarks() <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRemarks()
   * @generated
   * @ordered
   */
  protected static final String REMARKS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRemarks() <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRemarks()
   * @generated
   * @ordered
   */
  protected String remarks = REMARKS_EDEFAULT;

  /**
   * This is true if the Remarks attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean remarksESet;

  /**
   * The default value of the '{@link #isDescriptionFromProperty() <em>Description From Property</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDescriptionFromProperty()
   * @generated
   * @ordered
   */
  protected static final boolean DESCRIPTION_FROM_PROPERTY_EDEFAULT = true;

  /**
   * The cached value of the '{@link #isDescriptionFromProperty() <em>Description From Property</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDescriptionFromProperty()
   * @generated
   * @ordered
   */
  protected boolean descriptionFromProperty = DESCRIPTION_FROM_PROPERTY_EDEFAULT;

  /**
   * The cached value of the '{@link #getPurchase() <em>Purchase</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchase()
   * @generated
   * @ordered
   */
  protected Property purchase;

  /**
   * This is true if the Purchase reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean purchaseESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExpenditureImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.EXPENDITURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.EXPENDITURE__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PgCurrency getAmount() {
    return amount;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAmount(PgCurrency newAmount) {
    PgCurrency oldAmount = amount;
    amount = newAmount;
    boolean oldAmountESet = amountESet;
    amountESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__AMOUNT, oldAmount, amount, !oldAmountESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetAmount() {
    PgCurrency oldAmount = amount;
    boolean oldAmountESet = amountESet;
    amount = AMOUNT_EDEFAULT;
    amountESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.EXPENDITURE__AMOUNT, oldAmount, AMOUNT_EDEFAULT, oldAmountESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetAmount() {
    return amountESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRemarks() {
    return remarks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRemarks(String newRemarks) {
    String oldRemarks = remarks;
    remarks = newRemarks;
    boolean oldRemarksESet = remarksESet;
    remarksESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__REMARKS, oldRemarks, remarks, !oldRemarksESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetRemarks() {
    String oldRemarks = remarks;
    boolean oldRemarksESet = remarksESet;
    remarks = REMARKS_EDEFAULT;
    remarksESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.EXPENDITURE__REMARKS, oldRemarks, REMARKS_EDEFAULT, oldRemarksESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetRemarks() {
    return remarksESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isDescriptionFromProperty() {
    return descriptionFromProperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescriptionFromProperty(boolean newDescriptionFromProperty) {
    boolean oldDescriptionFromProperty = descriptionFromProperty;
    descriptionFromProperty = newDescriptionFromProperty;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__DESCRIPTION_FROM_PROPERTY, oldDescriptionFromProperty, descriptionFromProperty));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property getPurchase() {
    if (purchase != null && purchase.eIsProxy()) {
      InternalEObject oldPurchase = (InternalEObject)purchase;
      purchase = (Property)eResolveProxy(oldPurchase);
      if (purchase != oldPurchase) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvAndPropPackage.EXPENDITURE__PURCHASE, oldPurchase, purchase));
      }
    }
    return purchase;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property basicGetPurchase() {
    return purchase;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPurchase(Property newPurchase, NotificationChain msgs) {
    Property oldPurchase = purchase;
    purchase = newPurchase;
    boolean oldPurchaseESet = purchaseESet;
    purchaseESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__PURCHASE, oldPurchase, newPurchase, !oldPurchaseESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPurchase(Property newPurchase) {
    if (newPurchase != purchase) {
      NotificationChain msgs = null;
      if (purchase != null)
        msgs = ((InternalEObject)purchase).eInverseRemove(this, InvAndPropPackage.PROPERTY__EXPENDITURE, Property.class, msgs);
      if (newPurchase != null)
        msgs = ((InternalEObject)newPurchase).eInverseAdd(this, InvAndPropPackage.PROPERTY__EXPENDITURE, Property.class, msgs);
      msgs = basicSetPurchase(newPurchase, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldPurchaseESet = purchaseESet;
      purchaseESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.EXPENDITURE__PURCHASE, newPurchase, newPurchase, !oldPurchaseESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetPurchase(NotificationChain msgs) {
    Property oldPurchase = purchase;
    purchase = null;
    boolean oldPurchaseESet = purchaseESet;
    purchaseESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.EXPENDITURE__PURCHASE, oldPurchase, null, oldPurchaseESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetPurchase() {
    if (purchase != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject)purchase).eInverseRemove(this, InvAndPropPackage.PROPERTY__EXPENDITURE, Property.class, msgs);
      msgs = basicUnsetPurchase(msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldPurchaseESet = purchaseESet;
      purchaseESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.EXPENDITURE__PURCHASE, null, null, oldPurchaseESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetPurchase() {
    return purchaseESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        if (purchase != null)
          msgs = ((InternalEObject)purchase).eInverseRemove(this, InvAndPropPackage.PROPERTY__EXPENDITURE, Property.class, msgs);
        return basicSetPurchase((Property)otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        return basicUnsetPurchase(msgs);
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
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION:
        return getDescription();
      case InvAndPropPackage.EXPENDITURE__AMOUNT:
        return getAmount();
      case InvAndPropPackage.EXPENDITURE__REMARKS:
        return getRemarks();
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION_FROM_PROPERTY:
        return isDescriptionFromProperty();
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        if (resolve) return getPurchase();
        return basicGetPurchase();
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
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case InvAndPropPackage.EXPENDITURE__AMOUNT:
        setAmount((PgCurrency)newValue);
        return;
      case InvAndPropPackage.EXPENDITURE__REMARKS:
        setRemarks((String)newValue);
        return;
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION_FROM_PROPERTY:
        setDescriptionFromProperty((Boolean)newValue);
        return;
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        setPurchase((Property)newValue);
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
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION:
        unsetDescription();
        return;
      case InvAndPropPackage.EXPENDITURE__AMOUNT:
        unsetAmount();
        return;
      case InvAndPropPackage.EXPENDITURE__REMARKS:
        unsetRemarks();
        return;
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION_FROM_PROPERTY:
        setDescriptionFromProperty(DESCRIPTION_FROM_PROPERTY_EDEFAULT);
        return;
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        unsetPurchase();
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
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION:
        return isSetDescription();
      case InvAndPropPackage.EXPENDITURE__AMOUNT:
        return isSetAmount();
      case InvAndPropPackage.EXPENDITURE__REMARKS:
        return isSetRemarks();
      case InvAndPropPackage.EXPENDITURE__DESCRIPTION_FROM_PROPERTY:
        return descriptionFromProperty != DESCRIPTION_FROM_PROPERTY_EDEFAULT;
      case InvAndPropPackage.EXPENDITURE__PURCHASE:
        return isSetPurchase();
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
    result.append(" (description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(", amount: ");
    if (amountESet) result.append(amount); else result.append("<unset>");
    result.append(", remarks: ");
    if (remarksESet) result.append(remarks); else result.append("<unset>");
    result.append(", descriptionFromProperty: ");
    result.append(descriptionFromProperty);
    result.append(')');
    return result.toString();
  }

} //ExpenditureImpl
