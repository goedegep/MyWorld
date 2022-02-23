/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoices;
import goedegep.invandprop.model.InvoicesAndProperties;

import goedegep.invandprop.model.Properties;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invoices And Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl#getInvoices <em>Invoices</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoicesAndPropertiesImpl extends MinimalEObjectImpl.Container implements InvoicesAndProperties {
  /**
   * The cached value of the '{@link #getInvoices() <em>Invoices</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvoices()
   * @generated
   * @ordered
   */
  protected Invoices invoices;

  /**
   * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperties()
   * @generated
   * @ordered
   */
  protected Properties properties;

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
  public Invoices getInvoices() {
    return invoices;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInvoices(Invoices newInvoices, NotificationChain msgs) {
    Invoices oldInvoices = invoices;
    invoices = newInvoices;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES, oldInvoices, newInvoices);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInvoices(Invoices newInvoices) {
    if (newInvoices != invoices) {
      NotificationChain msgs = null;
      if (invoices != null)
        msgs = ((InternalEObject)invoices).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES, null, msgs);
      if (newInvoices != null)
        msgs = ((InternalEObject)newInvoices).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES, null, msgs);
      msgs = basicSetInvoices(newInvoices, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES, newInvoices, newInvoices));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetProperties(Properties newProperties, NotificationChain msgs) {
    Properties oldProperties = properties;
    properties = newProperties;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES, oldProperties, newProperties);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProperties(Properties newProperties) {
    if (newProperties != properties) {
      NotificationChain msgs = null;
      if (properties != null)
        msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES, null, msgs);
      if (newProperties != null)
        msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES, null, msgs);
      msgs = basicSetProperties(newProperties, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES, newProperties, newProperties));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES:
        return basicSetInvoices(null, msgs);
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES:
        return basicSetProperties(null, msgs);
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES:
        return getInvoices();
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES:
        return getProperties();
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES:
        setInvoices((Invoices)newValue);
        return;
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES:
        setProperties((Properties)newValue);
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES:
        setInvoices((Invoices)null);
        return;
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES:
        setProperties((Properties)null);
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__INVOICES:
        return invoices != null;
      case InvAndPropPackage.INVOICES_AND_PROPERTIES__PROPERTIES:
        return properties != null;
    }
    return super.eIsSet(featureID);
  }

} //InvoicesAndPropertiesImpl
