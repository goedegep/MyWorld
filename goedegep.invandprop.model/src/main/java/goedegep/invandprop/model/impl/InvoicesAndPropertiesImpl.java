/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoicesAndProperties;
import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invoices And Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl#getInvoicseandpropertys <em>Invoicseandpropertys</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoicesAndPropertiesImpl extends MinimalEObjectImpl.Container implements InvoicesAndProperties {
  /**
   * The cached value of the '{@link #getInvoicseandpropertys() <em>Invoicseandpropertys</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvoicseandpropertys()
   * @generated
   * @ordered
   */
  protected EList<InvoiceAndProperty> invoicseandpropertys;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoicesAndPropertiesImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICES_AND_PROPERTIES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<InvoiceAndProperty> getInvoicseandpropertys() {
    if (invoicseandpropertys == null) {
      invoicseandpropertys = new EObjectContainmentEList<InvoiceAndProperty>(InvoiceAndProperty.class, this, InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS);
    }
    return invoicseandpropertys;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS:
        return ((InternalEList<?>)getInvoicseandpropertys()).basicRemove(otherEnd, msgs);
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS:
        return getInvoicseandpropertys();
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS:
        getInvoicseandpropertys().clear();
        getInvoicseandpropertys().addAll((Collection<? extends InvoiceAndProperty>)newValue);
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS:
        getInvoicseandpropertys().clear();
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS:
        return invoicseandpropertys != null && !invoicseandpropertys.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //InvoicesAndPropertiesImpl
