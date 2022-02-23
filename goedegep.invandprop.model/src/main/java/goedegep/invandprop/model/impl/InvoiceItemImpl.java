/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceItem;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invoice Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceItemImpl#getNumberOfItems <em>Number Of Items</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoiceItemImpl extends ExpenditureImpl implements InvoiceItem {
  /**
   * The default value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfItems()
   * @generated
   * @ordered
   */
  protected static final int NUMBER_OF_ITEMS_EDEFAULT = 1;

  /**
   * The cached value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfItems()
   * @generated
   * @ordered
   */
  protected int numberOfItems = NUMBER_OF_ITEMS_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoiceItemImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICE_ITEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getNumberOfItems() {
    return numberOfItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfItems(int newNumberOfItems) {
    int oldNumberOfItems = numberOfItems;
    numberOfItems = newNumberOfItems;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_ITEM__NUMBER_OF_ITEMS, oldNumberOfItems, numberOfItems));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_ITEM__NUMBER_OF_ITEMS:
        return getNumberOfItems();
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
      case InvAndPropPackage.INVOICE_ITEM__NUMBER_OF_ITEMS:
        setNumberOfItems((Integer)newValue);
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
      case InvAndPropPackage.INVOICE_ITEM__NUMBER_OF_ITEMS:
        setNumberOfItems(NUMBER_OF_ITEMS_EDEFAULT);
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
      case InvAndPropPackage.INVOICE_ITEM__NUMBER_OF_ITEMS:
        return numberOfItems != NUMBER_OF_ITEMS_EDEFAULT;
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
    result.append(" (numberOfItems: ");
    result.append(numberOfItems);
    result.append(')');
    return result.toString();
  }

} //InvoiceItemImpl
