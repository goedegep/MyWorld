/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;

import goedegep.invandprop.model.InvoiceItem;
import goedegep.types.model.FileReference;
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
 * An implementation of the model object '<em><b>Invoice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceImpl#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceImpl#getCompany <em>Company</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceImpl#getInvoiceItems <em>Invoice Items</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceImpl#getDocuments <em>Documents</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoiceImpl extends ExpenditureImpl implements Invoice {
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
   * The cached value of the '{@link #getInvoiceItems() <em>Invoice Items</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvoiceItems()
   * @generated
   * @ordered
   */
  protected EList<InvoiceItem> invoiceItems;

  /**
   * The cached value of the '{@link #getDocuments() <em>Documents</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocuments()
   * @generated
   * @ordered
   */
  protected EList<FileReference> documents;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoiceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE__DATE, oldDate, date, !oldDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE__DATE, oldDate, DATE_EDEFAULT, oldDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE__COMPANY, oldCompany, company, !oldCompanyESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE__COMPANY, oldCompany, COMPANY_EDEFAULT, oldCompanyESet));
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
  public EList<InvoiceItem> getInvoiceItems() {
    if (invoiceItems == null) {
      invoiceItems = new EObjectContainmentEList<InvoiceItem>(InvoiceItem.class, this, InvAndPropPackage.INVOICE__INVOICE_ITEMS);
    }
    return invoiceItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getDocuments() {
    if (documents == null) {
      documents = new EObjectContainmentEList<FileReference>(FileReference.class, this, InvAndPropPackage.INVOICE__DOCUMENTS);
    }
    return documents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public PgCurrency getTotalAmountInvoiceItems() {
    PgCurrency amount = null;
    for (InvoiceItem invoiceItem : getInvoiceItems()) {
      if (amount == null) {
        amount = invoiceItem.getAmount();
      } else {
        if (invoiceItem.getAmount() != null) {
          amount = amount.add(invoiceItem.getAmount());
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
      case InvAndPropPackage.INVOICE__INVOICE_ITEMS:
        return ((InternalEList<?>)getInvoiceItems()).basicRemove(otherEnd, msgs);
      case InvAndPropPackage.INVOICE__DOCUMENTS:
        return ((InternalEList<?>)getDocuments()).basicRemove(otherEnd, msgs);
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
      case InvAndPropPackage.INVOICE__DATE:
        return getDate();
      case InvAndPropPackage.INVOICE__COMPANY:
        return getCompany();
      case InvAndPropPackage.INVOICE__INVOICE_ITEMS:
        return getInvoiceItems();
      case InvAndPropPackage.INVOICE__DOCUMENTS:
        return getDocuments();
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
      case InvAndPropPackage.INVOICE__DATE:
        setDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.INVOICE__COMPANY:
        setCompany((String)newValue);
        return;
      case InvAndPropPackage.INVOICE__INVOICE_ITEMS:
        getInvoiceItems().clear();
        getInvoiceItems().addAll((Collection<? extends InvoiceItem>)newValue);
        return;
      case InvAndPropPackage.INVOICE__DOCUMENTS:
        getDocuments().clear();
        getDocuments().addAll((Collection<? extends FileReference>)newValue);
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
      case InvAndPropPackage.INVOICE__DATE:
        unsetDate();
        return;
      case InvAndPropPackage.INVOICE__COMPANY:
        unsetCompany();
        return;
      case InvAndPropPackage.INVOICE__INVOICE_ITEMS:
        getInvoiceItems().clear();
        return;
      case InvAndPropPackage.INVOICE__DOCUMENTS:
        getDocuments().clear();
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
      case InvAndPropPackage.INVOICE__DATE:
        return isSetDate();
      case InvAndPropPackage.INVOICE__COMPANY:
        return isSetCompany();
      case InvAndPropPackage.INVOICE__INVOICE_ITEMS:
        return invoiceItems != null && !invoiceItems.isEmpty();
      case InvAndPropPackage.INVOICE__DOCUMENTS:
        return documents != null && !documents.isEmpty();
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
      case InvAndPropPackage.INVOICE___GET_TOTAL_AMOUNT_INVOICE_ITEMS:
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

} //InvoiceImpl
