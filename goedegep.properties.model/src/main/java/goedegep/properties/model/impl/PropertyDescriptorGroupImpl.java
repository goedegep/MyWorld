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
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getPropertyDescriptorGroups <em>Property Descriptor Groups</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl#getRegistryClassName <em>Registry Class Name</em>}</li>
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
   * The cached value of the '{@link #getPropertyDescriptorGroups() <em>Property Descriptor Groups</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyDescriptorGroups()
   * @generated
   * @ordered
   */
  protected EList<PropertyDescriptorGroup> propertyDescriptorGroups;

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
   * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackageName()
   * @generated
   * @ordered
   */
  protected static final String PACKAGE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackageName()
   * @generated
   * @ordered
   */
  protected String packageName = PACKAGE_NAME_EDEFAULT;

  /**
   * This is true if the Package Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean packageNameESet;

  /**
   * The default value of the '{@link #getRegistryClassName() <em>Registry Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRegistryClassName()
   * @generated
   * @ordered
   */
  protected static final String REGISTRY_CLASS_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRegistryClassName() <em>Registry Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRegistryClassName()
   * @generated
   * @ordered
   */
  protected String registryClassName = REGISTRY_CLASS_NAME_EDEFAULT;

  /**
   * This is true if the Registry Class Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean registryClassNameESet;

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
  public EList<PropertyDescriptorGroup> getPropertyDescriptorGroups() {
    if (propertyDescriptorGroups == null) {
      propertyDescriptorGroups = new EObjectContainmentEList<PropertyDescriptorGroup>(PropertyDescriptorGroup.class,
          this, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS);
    }
    return propertyDescriptorGroups;
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
   * @generated
   */
  @Override
  public String getPackageName() {
    return packageName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPackageName(String newPackageName) {
    String oldPackageName = packageName;
    packageName = newPackageName;
    boolean oldPackageNameESet = packageNameESet;
    packageNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME,
          oldPackageName, packageName, !oldPackageNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPackageName() {
    String oldPackageName = packageName;
    boolean oldPackageNameESet = packageNameESet;
    packageName = PACKAGE_NAME_EDEFAULT;
    packageNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME,
          oldPackageName, PACKAGE_NAME_EDEFAULT, oldPackageNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPackageName() {
    return packageNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getRegistryClassName() {
    return registryClassName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRegistryClassName(String newRegistryClassName) {
    String oldRegistryClassName = registryClassName;
    registryClassName = newRegistryClassName;
    boolean oldRegistryClassNameESet = registryClassNameESet;
    registryClassNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME, oldRegistryClassName, registryClassName,
          !oldRegistryClassNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetRegistryClassName() {
    String oldRegistryClassName = registryClassName;
    boolean oldRegistryClassNameESet = registryClassNameESet;
    registryClassName = REGISTRY_CLASS_NAME_EDEFAULT;
    registryClassNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET,
          PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME, oldRegistryClassName,
          REGISTRY_CLASS_NAME_EDEFAULT, oldRegistryClassNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetRegistryClassName() {
    return registryClassNameESet;
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
   * @generated NOT
   */
  public PropertyDescriptorGroup getPropertyDescriptorGroup(String qualifiedGroupName) {
    int firstDotIndex = qualifiedGroupName.indexOf(".");

    if (firstDotIndex == -1) {
      System.out.println("Searching in this group");
      for (PropertyDescriptorGroup propertyDescriptorGroup : getPropertyDescriptorGroups()) {
        if (propertyDescriptorGroup.getName().equals(qualifiedGroupName)) {
          return propertyDescriptorGroup;
        }
      }
    } else {
      String groupName = qualifiedGroupName.substring(0, firstDotIndex);
      qualifiedGroupName = qualifiedGroupName.substring(firstDotIndex + 1);
      System.out.println(
          "Going to search in sub group, groupName = " + groupName + ", qualifiedGroupName = " + qualifiedGroupName);
      for (PropertyDescriptorGroup propertyDescriptorGroup : propertyDescriptorGroups) {
        if (propertyDescriptorGroup.getName().equals(groupName)) {
          return propertyDescriptorGroup.getPropertyDescriptorGroup(qualifiedGroupName);
        }
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS:
      return ((InternalEList<?>) getPropertyDescriptorGroups()).basicRemove(otherEnd, msgs);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS:
      return getPropertyDescriptorGroups();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      return getName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      return getDescription();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME:
      return getPackageName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME:
      return getRegistryClassName();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS:
      getPropertyDescriptorGroups().clear();
      getPropertyDescriptorGroups().addAll((Collection<? extends PropertyDescriptorGroup>) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      setName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME:
      setPackageName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME:
      setRegistryClassName((String) newValue);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS:
      getPropertyDescriptorGroups().clear();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      unsetName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      unsetDescription();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME:
      unsetPackageName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME:
      unsetRegistryClassName();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS:
      return propertyDescriptorGroups != null && !propertyDescriptorGroups.isEmpty();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__NAME:
      return isSetName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION:
      return isSetDescription();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME:
      return isSetPackageName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME:
      return isSetRegistryClassName();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR_GROUP__STRING:
      return getPropertyDescriptorGroup((String) arguments.get(0));
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
    result.append(", packageName: ");
    if (packageNameESet)
      result.append(packageName);
    else
      result.append("<unset>");
    result.append(", registryClassName: ");
    if (registryClassNameESet)
      result.append(registryClassName);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PropertyDescriptorGroupImpl
