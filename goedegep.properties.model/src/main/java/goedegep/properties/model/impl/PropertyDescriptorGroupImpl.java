/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.impl;

import java.util.Collection;
import java.util.logging.Logger;

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
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Descriptor Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getPropertyDescriptors <em>Property Descriptors</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyDescriptorGroupImpl extends MinimalEObjectImpl.Container implements PropertyDescriptorGroup {
  private static final Logger LOGGER = Logger.getLogger(PropertyDescriptorGroupImpl.class.getName());

  /**
   * The cached value of the '{@link #getPropertyDescriptors() <em>Property Descriptors</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyDescriptors()
   * @generated
   * @ordered
   */
  protected EList<PropertyDescriptor> propertyDescriptors;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyDescriptorGroupImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PropertiesPackage.Literals.PROPERTY_DESCRIPTOR_GROUP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PropertyDescriptor> getPropertyDescriptors() {
    if (propertyDescriptors == null) {
      propertyDescriptors = new EObjectContainmentEList<PropertyDescriptor>(PropertyDescriptor.class, this,
          PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS);
    }
    return propertyDescriptors;
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
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME, oldName,
          name, !oldNameESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME,
          oldName, NAME_EDEFAULT, oldNameESet));
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
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION,
          oldDescription, description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION,
          oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public PropertyDescriptor getPropertyDescriptor(String propertyName) {
    LOGGER.fine("=> propertyName = " + propertyName);

    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      if (propertyDescriptor.getName().equals(propertyName)) {
        return propertyDescriptor;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS:
      return ((InternalEList<?>) getPropertyDescriptors()).basicRemove(otherEnd, msgs);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS:
      return getPropertyDescriptors();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      return getName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      return getDescription();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS:
      getPropertyDescriptors().clear();
      getPropertyDescriptors().addAll((Collection<? extends PropertyDescriptor>) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      setName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      setDescription((String) newValue);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS:
      getPropertyDescriptors().clear();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      unsetName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      unsetDescription();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS:
      return propertyDescriptors != null && !propertyDescriptors.isEmpty();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      return isSetName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      return isSetDescription();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR__STRING:
      return getPropertyDescriptor((String) arguments.get(0));
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    if (nameESet)
      result.append(name);
    else
      result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet)
      result.append(description);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PropertyDescriptorGroupImpl
