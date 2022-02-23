/**
 */
package goedegep.configuration.model.impl;

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

import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.ModuleLook;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Look</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.impl.ModuleLookImpl#getLook <em>Look</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.ModuleLookImpl#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.ModuleLookImpl#getModuleLooks <em>Module Looks</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.ModuleLookImpl#getResourcesClassName <em>Resources Class Name</em>}</li>
 *   <li>{@link goedegep.configuration.model.impl.ModuleLookImpl#getParentModuleLook <em>Parent Module Look</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModuleLookImpl extends MinimalEObjectImpl.Container implements ModuleLook {
  /**
   * The cached value of the '{@link #getLook() <em>Look</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLook()
   * @generated
   * @ordered
   */
  protected Look look;

  /**
   * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModuleName()
   * @generated
   * @ordered
   */
  protected static final String MODULE_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModuleName()
   * @generated
   * @ordered
   */
  protected String moduleName = MODULE_NAME_EDEFAULT;
  /**
   * The cached value of the '{@link #getModuleLooks() <em>Module Looks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModuleLooks()
   * @generated
   * @ordered
   */
  protected EList<ModuleLook> moduleLooks;
  /**
   * The default value of the '{@link #getResourcesClassName() <em>Resources Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResourcesClassName()
   * @generated
   * @ordered
   */
  protected static final String RESOURCES_CLASS_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getResourcesClassName() <em>Resources Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResourcesClassName()
   * @generated
   * @ordered
   */
  protected String resourcesClassName = RESOURCES_CLASS_NAME_EDEFAULT;
  /**
   * This is true if the Resources Class Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean resourcesClassNameESet;
  /**
   * The cached value of the '{@link #getParentModuleLook() <em>Parent Module Look</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParentModuleLook()
   * @generated
   * @ordered
   */
  protected ModuleLook parentModuleLook;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ModuleLookImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.MODULE_LOOK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Look getLook() {
    return look;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLook(Look newLook, NotificationChain msgs) {
    Look oldLook = look;
    look = newLook;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          ConfigurationPackage.MODULE_LOOK__LOOK, oldLook, newLook);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLook(Look newLook) {
    if (newLook != look) {
      NotificationChain msgs = null;
      if (look != null)
        msgs = ((InternalEObject) look).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - ConfigurationPackage.MODULE_LOOK__LOOK, null, msgs);
      if (newLook != null)
        msgs = ((InternalEObject) newLook).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - ConfigurationPackage.MODULE_LOOK__LOOK, null, msgs);
      msgs = basicSetLook(newLook, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.MODULE_LOOK__LOOK, newLook, newLook));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getModuleName() {
    return moduleName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setModuleName(String newModuleName) {
    String oldModuleName = moduleName;
    moduleName = newModuleName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.MODULE_LOOK__MODULE_NAME,
          oldModuleName, moduleName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ModuleLook> getModuleLooks() {
    if (moduleLooks == null) {
      moduleLooks = new EObjectContainmentEList.Unsettable<ModuleLook>(ModuleLook.class, this,
          ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS);
    }
    return moduleLooks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetModuleLooks() {
    if (moduleLooks != null)
      ((InternalEList.Unsettable<?>) moduleLooks).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetModuleLooks() {
    return moduleLooks != null && ((InternalEList.Unsettable<?>) moduleLooks).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getResourcesClassName() {
    return resourcesClassName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setResourcesClassName(String newResourcesClassName) {
    String oldResourcesClassName = resourcesClassName;
    resourcesClassName = newResourcesClassName;
    boolean oldResourcesClassNameESet = resourcesClassNameESet;
    resourcesClassNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME,
          oldResourcesClassName, resourcesClassName, !oldResourcesClassNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetResourcesClassName() {
    String oldResourcesClassName = resourcesClassName;
    boolean oldResourcesClassNameESet = resourcesClassNameESet;
    resourcesClassName = RESOURCES_CLASS_NAME_EDEFAULT;
    resourcesClassNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME,
          oldResourcesClassName, RESOURCES_CLASS_NAME_EDEFAULT, oldResourcesClassNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetResourcesClassName() {
    return resourcesClassNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ModuleLook getParentModuleLook() {
    if (parentModuleLook != null && parentModuleLook.eIsProxy()) {
      InternalEObject oldParentModuleLook = (InternalEObject) parentModuleLook;
      parentModuleLook = (ModuleLook) eResolveProxy(oldParentModuleLook);
      if (parentModuleLook != oldParentModuleLook) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK, oldParentModuleLook, parentModuleLook));
      }
    }
    return parentModuleLook;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModuleLook basicGetParentModuleLook() {
    return parentModuleLook;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setParentModuleLook(ModuleLook newParentModuleLook) {
    ModuleLook oldParentModuleLook = parentModuleLook;
    parentModuleLook = newParentModuleLook;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK,
          oldParentModuleLook, parentModuleLook));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case ConfigurationPackage.MODULE_LOOK__LOOK:
      return basicSetLook(null, msgs);
    case ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS:
      return ((InternalEList<?>) getModuleLooks()).basicRemove(otherEnd, msgs);
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
    case ConfigurationPackage.MODULE_LOOK__LOOK:
      return getLook();
    case ConfigurationPackage.MODULE_LOOK__MODULE_NAME:
      return getModuleName();
    case ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS:
      return getModuleLooks();
    case ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME:
      return getResourcesClassName();
    case ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK:
      if (resolve)
        return getParentModuleLook();
      return basicGetParentModuleLook();
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
    case ConfigurationPackage.MODULE_LOOK__LOOK:
      setLook((Look) newValue);
      return;
    case ConfigurationPackage.MODULE_LOOK__MODULE_NAME:
      setModuleName((String) newValue);
      return;
    case ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS:
      getModuleLooks().clear();
      getModuleLooks().addAll((Collection<? extends ModuleLook>) newValue);
      return;
    case ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME:
      setResourcesClassName((String) newValue);
      return;
    case ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK:
      setParentModuleLook((ModuleLook) newValue);
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
    case ConfigurationPackage.MODULE_LOOK__LOOK:
      setLook((Look) null);
      return;
    case ConfigurationPackage.MODULE_LOOK__MODULE_NAME:
      setModuleName(MODULE_NAME_EDEFAULT);
      return;
    case ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS:
      unsetModuleLooks();
      return;
    case ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME:
      unsetResourcesClassName();
      return;
    case ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK:
      setParentModuleLook((ModuleLook) null);
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
    case ConfigurationPackage.MODULE_LOOK__LOOK:
      return look != null;
    case ConfigurationPackage.MODULE_LOOK__MODULE_NAME:
      return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
    case ConfigurationPackage.MODULE_LOOK__MODULE_LOOKS:
      return isSetModuleLooks();
    case ConfigurationPackage.MODULE_LOOK__RESOURCES_CLASS_NAME:
      return isSetResourcesClassName();
    case ConfigurationPackage.MODULE_LOOK__PARENT_MODULE_LOOK:
      return parentModuleLook != null;
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
    result.append(" (moduleName: ");
    result.append(moduleName);
    result.append(", resourcesClassName: ");
    if (resourcesClassNameESet)
      result.append(resourcesClassName);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ModuleLookImpl
