/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;

import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.util.datetime.FlexDate;

import goedegep.util.money.PgCurrency;

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
 * An implementation of the model object '<em><b>Invoice And Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyImpl#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyImpl#getCompany <em>Company</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyImpl#getInvoiceandpropertyitems <em>Invoiceandpropertyitems</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoiceAndPropertyImpl extends InvoiceAndPropertyItemImpl implements InvoiceAndProperty {
  /**
   * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected FlexDate date = DATE_EDEFAULT;

  /**
   * This is true if the Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dateESet;

  /**
   * The default value of the '{@link #getCompany() <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompany()
   * @generated
   * @ordered
   */
  protected static final String COMPANY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompany() <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompany()
   * @generated
   * @ordered
   */
  protected String company = COMPANY_EDEFAULT;

  /**
   * This is true if the Company attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean companyESet;

  /**
   * The cached value of the '{@link #getInvoiceandpropertyitems() <em>Invoiceandpropertyitems</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvoiceandpropertyitems()
   * @generated
   * @ordered
   */
  protected EList<InvoiceAndPropertyItem> invoiceandpropertyitems;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoiceAndPropertyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICE_AND_PROPERTY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlexDate getDate() {
    return date;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDate(FlexDate newDate) {
    FlexDate oldDate = date;
    date = newDate;
    boolean oldDateESet = dateESet;
    dateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY__DATE, oldDate, date, !oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDate() {
    FlexDate oldDate = date;
    boolean oldDateESet = dateESet;
    date = DATE_EDEFAULT;
    dateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY__DATE, oldDate, DATE_EDEFAULT, oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDate() {
    return dateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCompany() {
    return company;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompany(String newCompany) {
    String oldCompany = company;
    company = newCompany;
    boolean oldCompanyESet = companyESet;
    companyESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY, oldCompany, company, !oldCompanyESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetCompany() {
    String oldCompany = company;
    boolean oldCompanyESet = companyESet;
    company = COMPANY_EDEFAULT;
    companyESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY, oldCompany, COMPANY_EDEFAULT, oldCompanyESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetCompany() {
    return companyESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<InvoiceAndPropertyItem> getInvoiceandpropertyitems() {
    if (invoiceandpropertyitems == null) {
      invoiceandpropertyitems = new EObjectContainmentEList<InvoiceAndPropertyItem>(InvoiceAndPropertyItem.class, this, InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS);
    }
    return invoiceandpropertyitems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public PgCurrency getTotalAmountInvoiceItems() {
    PgCurrency amount = null;
    for (InvoiceAndPropertyItem invoiceAndPropertyItem : getInvoiceandpropertyitems()) {
      if (amount == null) {
        amount = invoiceAndPropertyItem.getAmount();
      } else {
        if (invoiceAndPropertyItem.getAmount() != null) {
          amount = amount.add(invoiceAndPropertyItem.getAmount());
        }
      }
    }
    return amount;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS:
        return ((InternalEList<?>)getInvoiceandpropertyitems()).basicRemove(otherEnd, msgs);
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
      case InvAndPropPackage.INVOICE_AND_PROPERTY__DATE:
        return getDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY:
        return getCompany();
      case InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS:
        return getInvoiceandpropertyitems();
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
      case InvAndPropPackage.INVOICE_AND_PROPERTY__DATE:
        setDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY:
        setCompany((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS:
        getInvoiceandpropertyitems().clear();
        getInvoiceandpropertyitems().addAll((Collection<? extends InvoiceAndPropertyItem>)newValue);
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
      case InvAndPropPackage.INVOICE_AND_PROPERTY__DATE:
        unsetDate();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY:
        unsetCompany();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS:
        getInvoiceandpropertyitems().clear();
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
      case InvAndPropPackage.INVOICE_AND_PROPERTY__DATE:
        return isSetDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY__COMPANY:
        return isSetCompany();
      case InvAndPropPackage.INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS:
        return invoiceandpropertyitems != null && !invoiceandpropertyitems.isEmpty();
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
      case InvAndPropPackage.INVOICE_AND_PROPERTY___GET_TOTAL_AMOUNT_INVOICE_ITEMS:
        return getTotalAmountInvoiceItems();
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
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (date: ");
    if (dateESet) result.append(date); else result.append("<unset>");
    result.append(", company: ");
    if (companyESet) result.append(company); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //InvoiceAndPropertyImpl
