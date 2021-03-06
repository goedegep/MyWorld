/**
 */
package goedegep.icons.iconset.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.icons.iconset.model.IconData;
import goedegep.icons.iconset.model.IconDefinition;
import goedegep.icons.iconset.model.IconInfo;
import goedegep.icons.iconset.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconDefinitionImpl#getIconInfo <em>Icon Info</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconDefinitionImpl#getIconData <em>Icon Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconDefinitionImpl extends MinimalEObjectImpl.Container implements IconDefinition {
  /**
   * The cached value of the '{@link #getIconInfo() <em>Icon Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconInfo()
   * @generated
   * @ordered
   */
  protected IconInfo iconInfo;

  /**
   * This is true if the Icon Info containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iconInfoESet;

  /**
   * The cached value of the '{@link #getIconData() <em>Icon Data</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconData()
   * @generated
   * @ordered
   */
  protected IconData iconData;

  /**
   * This is true if the Icon Data containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iconDataESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IconDefinitionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ModelPackage.Literals.ICON_DEFINITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IconInfo getIconInfo() {
    return iconInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIconInfo(IconInfo newIconInfo, NotificationChain msgs) {
    IconInfo oldIconInfo = iconInfo;
    iconInfo = newIconInfo;
    boolean oldIconInfoESet = iconInfoESet;
    iconInfoESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DEFINITION__ICON_INFO, oldIconInfo, newIconInfo, !oldIconInfoESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIconInfo(IconInfo newIconInfo) {
    if (newIconInfo != iconInfo) {
      NotificationChain msgs = null;
      if (iconInfo != null)
        msgs = ((InternalEObject)iconInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_INFO, null, msgs);
      if (newIconInfo != null)
        msgs = ((InternalEObject)newIconInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_INFO, null, msgs);
      msgs = basicSetIconInfo(newIconInfo, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldIconInfoESet = iconInfoESet;
      iconInfoESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DEFINITION__ICON_INFO, newIconInfo, newIconInfo, !oldIconInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetIconInfo(NotificationChain msgs) {
    IconInfo oldIconInfo = iconInfo;
    iconInfo = null;
    boolean oldIconInfoESet = iconInfoESet;
    iconInfoESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DEFINITION__ICON_INFO, oldIconInfo, null, oldIconInfoESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIconInfo() {
    if (iconInfo != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject)iconInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_INFO, null, msgs);
      msgs = basicUnsetIconInfo(msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldIconInfoESet = iconInfoESet;
      iconInfoESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DEFINITION__ICON_INFO, null, null, oldIconInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIconInfo() {
    return iconInfoESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IconData getIconData() {
    return iconData;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIconData(IconData newIconData, NotificationChain msgs) {
    IconData oldIconData = iconData;
    iconData = newIconData;
    boolean oldIconDataESet = iconDataESet;
    iconDataESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DEFINITION__ICON_DATA, oldIconData, newIconData, !oldIconDataESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIconData(IconData newIconData) {
    if (newIconData != iconData) {
      NotificationChain msgs = null;
      if (iconData != null)
        msgs = ((InternalEObject)iconData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_DATA, null, msgs);
      if (newIconData != null)
        msgs = ((InternalEObject)newIconData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_DATA, null, msgs);
      msgs = basicSetIconData(newIconData, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldIconDataESet = iconDataESet;
      iconDataESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DEFINITION__ICON_DATA, newIconData, newIconData, !oldIconDataESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetIconData(NotificationChain msgs) {
    IconData oldIconData = iconData;
    iconData = null;
    boolean oldIconDataESet = iconDataESet;
    iconDataESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DEFINITION__ICON_DATA, oldIconData, null, oldIconDataESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIconData() {
    if (iconData != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject)iconData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ICON_DEFINITION__ICON_DATA, null, msgs);
      msgs = basicUnsetIconData(msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldIconDataESet = iconDataESet;
      iconDataESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DEFINITION__ICON_DATA, null, null, oldIconDataESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIconData() {
    return iconDataESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ModelPackage.ICON_DEFINITION__ICON_INFO:
        return basicUnsetIconInfo(msgs);
      case ModelPackage.ICON_DEFINITION__ICON_DATA:
        return basicUnsetIconData(msgs);
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
      case ModelPackage.ICON_DEFINITION__ICON_INFO:
        return getIconInfo();
      case ModelPackage.ICON_DEFINITION__ICON_DATA:
        return getIconData();
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
      case ModelPackage.ICON_DEFINITION__ICON_INFO:
        setIconInfo((IconInfo)newValue);
        return;
      case ModelPackage.ICON_DEFINITION__ICON_DATA:
        setIconData((IconData)newValue);
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
      case ModelPackage.ICON_DEFINITION__ICON_INFO:
        unsetIconInfo();
        return;
      case ModelPackage.ICON_DEFINITION__ICON_DATA:
        unsetIconData();
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
      case ModelPackage.ICON_DEFINITION__ICON_INFO:
        return isSetIconInfo();
      case ModelPackage.ICON_DEFINITION__ICON_DATA:
        return isSetIconData();
    }
    return super.eIsSet(featureID);
  }

} //IconDefinitionImpl
