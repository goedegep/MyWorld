/**
 */
package goedegep.properties.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getRegistryName <em>Registry Name</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link goedegep.properties.model.impl.PropertyDescriptorImpl#isUserSettable <em>User Settable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyDescriptorImpl extends MinimalEObjectImpl.Container implements PropertyDescriptor {
  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final PropertyType TYPE_EDEFAULT = PropertyType.STRING;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected PropertyType type = TYPE_EDEFAULT;

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
   * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayName()
   * @generated
   * @ordered
   */
  protected static final String DISPLAY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayName()
   * @generated
   * @ordered
   */
  protected String displayName = DISPLAY_NAME_EDEFAULT;

  /**
   * This is true if the Display Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean displayNameESet;

  /**
   * The default value of the '{@link #getRegistryName() <em>Registry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRegistryName()
   * @generated
   * @ordered
   */
  protected static final String REGISTRY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRegistryName() <em>Registry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRegistryName()
   * @generated
   * @ordered
   */
  protected String registryName = REGISTRY_NAME_EDEFAULT;

  /**
   * This is true if the Registry Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean registryNameESet;

  /**
   * The default value of the '{@link #getInitialValue() <em>Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitialValue()
   * @generated
   * @ordered
   */
  protected static final String INITIAL_VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInitialValue() <em>Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitialValue()
   * @generated
   * @ordered
   */
  protected String initialValue = INITIAL_VALUE_EDEFAULT;

  /**
   * This is true if the Initial Value attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean initialValueESet;

  /**
   * The default value of the '{@link #isUserSettable() <em>User Settable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUserSettable()
   * @generated
   * @ordered
   */
  protected static final boolean USER_SETTABLE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isUserSettable() <em>User Settable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUserSettable()
   * @generated
   * @ordered
   */
  protected boolean userSettable = USER_SETTABLE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyDescriptorImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PropertiesPackage.Literals.PROPERTY_DESCRIPTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropertyType getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setType(PropertyType newType) {
    PropertyType oldType = type;
    type = newType == null ? TYPE_EDEFAULT : newType;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__TYPE, oldType, type));
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
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__NAME, oldName, name,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR__NAME, oldName,
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
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION,
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
  public String getDisplayName() {
    return displayName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDisplayName(String newDisplayName) {
    String oldDisplayName = displayName;
    displayName = newDisplayName;
    boolean oldDisplayNameESet = displayNameESet;
    displayNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME,
          oldDisplayName, displayName, !oldDisplayNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDisplayName() {
    String oldDisplayName = displayName;
    boolean oldDisplayNameESet = displayNameESet;
    displayName = DISPLAY_NAME_EDEFAULT;
    displayNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME,
          oldDisplayName, DISPLAY_NAME_EDEFAULT, oldDisplayNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDisplayName() {
    return displayNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getRegistryName() {
    return registryName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRegistryName(String newRegistryName) {
    String oldRegistryName = registryName;
    registryName = newRegistryName;
    boolean oldRegistryNameESet = registryNameESet;
    registryNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME,
          oldRegistryName, registryName, !oldRegistryNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetRegistryName() {
    String oldRegistryName = registryName;
    boolean oldRegistryNameESet = registryNameESet;
    registryName = REGISTRY_NAME_EDEFAULT;
    registryNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME,
          oldRegistryName, REGISTRY_NAME_EDEFAULT, oldRegistryNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetRegistryName() {
    return registryNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getInitialValue() {
    return initialValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInitialValue(String newInitialValue) {
    String oldInitialValue = initialValue;
    initialValue = newInitialValue;
    boolean oldInitialValueESet = initialValueESet;
    initialValueESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE,
          oldInitialValue, initialValue, !oldInitialValueESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInitialValue() {
    String oldInitialValue = initialValue;
    boolean oldInitialValueESet = initialValueESet;
    initialValue = INITIAL_VALUE_EDEFAULT;
    initialValueESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE,
          oldInitialValue, INITIAL_VALUE_EDEFAULT, oldInitialValueESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInitialValue() {
    return initialValueESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isUserSettable() {
    return userSettable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUserSettable(boolean newUserSettable) {
    boolean oldUserSettable = userSettable;
    userSettable = newUserSettable;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_DESCRIPTOR__USER_SETTABLE,
          oldUserSettable, userSettable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case PropertiesPackage.PROPERTY_DESCRIPTOR__TYPE:
      return getType();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__NAME:
      return getName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION:
      return getDescription();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
      return getDisplayName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME:
      return getRegistryName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE:
      return getInitialValue();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__USER_SETTABLE:
      return isUserSettable();
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR__TYPE:
      setType((PropertyType) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__NAME:
      setName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
      setDisplayName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME:
      setRegistryName((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE:
      setInitialValue((String) newValue);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__USER_SETTABLE:
      setUserSettable((Boolean) newValue);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR__TYPE:
      setType(TYPE_EDEFAULT);
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__NAME:
      unsetName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION:
      unsetDescription();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
      unsetDisplayName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME:
      unsetRegistryName();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE:
      unsetInitialValue();
      return;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__USER_SETTABLE:
      setUserSettable(USER_SETTABLE_EDEFAULT);
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR__TYPE:
      return type != TYPE_EDEFAULT;
    case PropertiesPackage.PROPERTY_DESCRIPTOR__NAME:
      return isSetName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DESCRIPTION:
      return isSetDescription();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
      return isSetDisplayName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__REGISTRY_NAME:
      return isSetRegistryName();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__INITIAL_VALUE:
      return isSetInitialValue();
    case PropertiesPackage.PROPERTY_DESCRIPTOR__USER_SETTABLE:
      return userSettable != USER_SETTABLE_EDEFAULT;
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
    result.append(" (type: ");
    result.append(type);
    result.append(", name: ");
    if (nameESet)
      result.append(name);
    else
      result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet)
      result.append(description);
    else
      result.append("<unset>");
    result.append(", displayName: ");
    if (displayNameESet)
      result.append(displayName);
    else
      result.append("<unset>");
    result.append(", registryName: ");
    if (registryNameESet)
      result.append(registryName);
    else
      result.append("<unset>");
    result.append(", initialValue: ");
    if (initialValueESet)
      result.append(initialValue);
    else
      result.append("<unset>");
    result.append(", userSettable: ");
    result.append(userSettable);
    result.append(')');
    return result.toString();
  }

} //PropertyDescriptorImpl
