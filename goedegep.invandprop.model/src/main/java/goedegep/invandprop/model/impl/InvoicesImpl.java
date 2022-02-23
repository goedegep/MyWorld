/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.Invoices;

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
 * An implementation of the model object '<em><b>Invoices</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoicesImpl#getInvoices <em>Invoices</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoicesImpl extends MinimalEObjectImpl.Container implements Invoices {
  /**
   * The cached value of the '{@link #getInvoices() <em>Invoices</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvoices()
   * @generated
   * @ordered
   */
  protected EList<Invoice> invoices;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoicesImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Invoice> getInvoices() {
    if (invoices == null) {
      invoices = new EObjectContainmentEList<Invoice>(Invoice.class, this, InvAndPropPackage.INVOICES__INVOICES);
    }
    return invoices;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.INVOICES__INVOICES:
        return ((InternalEList<?>)getInvoices()).basicRemove(otherEnd, msgs);
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
      case InvAndPropPackage.INVOICES__INVOICES:
        return getInvoices();
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
      case InvAndPropPackage.INVOICES__INVOICES:
        getInvoices().clear();
        getInvoices().addAll((Collection<? extends Invoice>)newValue);
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
      case InvAndPropPackage.INVOICES__INVOICES:
        getInvoices().clear();
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
      case InvAndPropPackage.INVOICES__INVOICES:
        return invoices != null && !invoices.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //InvoicesImpl
