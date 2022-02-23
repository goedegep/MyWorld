/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyGroup;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.impl.PropertyGroupImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyGroupImpl#getPropertyGroups <em>Property Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyGroupImpl extends MinimalEObjectImpl.Container implements PropertyGroup {
  /**
   * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperties()
   * @generated
   * @ordered
   */
  protected EList<Property> properties;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The cached value of the '{@link #getPropertyGroups() <em>Property Groups</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyGroups()
   * @generated
   * @ordered
   */
  protected EList<PropertyGroup> propertyGroups;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyGroupImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PropertiesPackage.Literals.PROPERTY_GROUP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Property> getProperties() {
    if (properties == null) {
      properties = new EObjectContainmentEList<Property>(Property.class, this,
          PropertiesPackage.PROPERTY_GROUP__PROPERTIES);
    }
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_GROUP__NAME, oldName, name,
          !oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_GROUP__NAME, oldName,
          NAME_EDEFAULT, oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PropertyGroup> getPropertyGroups() {
    if (propertyGroups == null) {
      propertyGroups = new EObjectContainmentEList<PropertyGroup>(PropertyGroup.class, this,
          PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS);
    }
    return propertyGroups;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case PropertiesPackage.PROPERTY_GROUP__PROPERTIES:
      return ((InternalEList<?>) getProperties()).basicRemove(otherEnd, msgs);
    case PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS:
      return ((InternalEList<?>) getPropertyGroups()).basicRemove(otherEnd, msgs);
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
    case PropertiesPackage.PROPERTY_GROUP__PROPERTIES:
      return getProperties();
    case PropertiesPackage.PROPERTY_GROUP__NAME:
      return getName();
    case PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS:
      return getPropertyGroups();
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
    case PropertiesPackage.PROPERTY_GROUP__PROPERTIES:
      getProperties().clear();
      getProperties().addAll((Collection<? extends Property>) newValue);
      return;
    case PropertiesPackage.PROPERTY_GROUP__NAME:
      setName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS:
      getPropertyGroups().clear();
      getPropertyGroups().addAll((Collection<? extends PropertyGroup>) newValue);
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
    case PropertiesPackage.PROPERTY_GROUP__PROPERTIES:
      getProperties().clear();
      return;
    case PropertiesPackage.PROPERTY_GROUP__NAME:
      unsetName();
      return;
    case PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS:
      getPropertyGroups().clear();
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
    case PropertiesPackage.PROPERTY_GROUP__PROPERTIES:
      return properties != null && !properties.isEmpty();
    case PropertiesPackage.PROPERTY_GROUP__NAME:
      return isSetName();
    case PropertiesPackage.PROPERTY_GROUP__PROPERTY_GROUPS:
      return propertyGroups != null && !propertyGroups.isEmpty();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    if (nameESet)
      result.append(name);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PropertyGroupImpl
