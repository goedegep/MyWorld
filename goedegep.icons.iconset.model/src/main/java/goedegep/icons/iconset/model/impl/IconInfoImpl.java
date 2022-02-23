/**
 */
package goedegep.icons.iconset.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.icons.iconset.model.IconInfo;
import goedegep.icons.iconset.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconInfoImpl#getIconSetId <em>Icon Set Id</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconInfoImpl#getIconId <em>Icon Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconInfoImpl extends MinimalEObjectImpl.Container implements IconInfo {
  /**
   * The default value of the '{@link #getIconSetId() <em>Icon Set Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconSetId()
   * @generated
   * @ordered
   */
  protected static final int ICON_SET_ID_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getIconSetId() <em>Icon Set Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconSetId()
   * @generated
   * @ordered
   */
  protected int iconSetId = ICON_SET_ID_EDEFAULT;

  /**
   * This is true if the Icon Set Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iconSetIdESet;

  /**
   * The default value of the '{@link #getIconId() <em>Icon Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconId()
   * @generated
   * @ordered
   */
  protected static final int ICON_ID_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getIconId() <em>Icon Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconId()
   * @generated
   * @ordered
   */
  protected int iconId = ICON_ID_EDEFAULT;

  /**
   * This is true if the Icon Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iconIdESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IconInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ModelPackage.Literals.ICON_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getIconSetId() {
    return iconSetId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIconSetId(int newIconSetId) {
    int oldIconSetId = iconSetId;
    iconSetId = newIconSetId;
    boolean oldIconSetIdESet = iconSetIdESet;
    iconSetIdESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_INFO__ICON_SET_ID, oldIconSetId, iconSetId, !oldIconSetIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIconSetId() {
    int oldIconSetId = iconSetId;
    boolean oldIconSetIdESet = iconSetIdESet;
    iconSetId = ICON_SET_ID_EDEFAULT;
    iconSetIdESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_INFO__ICON_SET_ID, oldIconSetId, ICON_SET_ID_EDEFAULT, oldIconSetIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIconSetId() {
    return iconSetIdESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getIconId() {
    return iconId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIconId(int newIconId) {
    int oldIconId = iconId;
    iconId = newIconId;
    boolean oldIconIdESet = iconIdESet;
    iconIdESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_INFO__ICON_ID, oldIconId, iconId, !oldIconIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIconId() {
    int oldIconId = iconId;
    boolean oldIconIdESet = iconIdESet;
    iconId = ICON_ID_EDEFAULT;
    iconIdESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_INFO__ICON_ID, oldIconId, ICON_ID_EDEFAULT, oldIconIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIconId() {
    return iconIdESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ModelPackage.ICON_INFO__ICON_SET_ID:
        return getIconSetId();
      case ModelPackage.ICON_INFO__ICON_ID:
        return getIconId();
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
      case ModelPackage.ICON_INFO__ICON_SET_ID:
        setIconSetId((Integer)newValue);
        return;
      case ModelPackage.ICON_INFO__ICON_ID:
        setIconId((Integer)newValue);
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
      case ModelPackage.ICON_INFO__ICON_SET_ID:
        unsetIconSetId();
        return;
      case ModelPackage.ICON_INFO__ICON_ID:
        unsetIconId();
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
      case ModelPackage.ICON_INFO__ICON_SET_ID:
        return isSetIconSetId();
      case ModelPackage.ICON_INFO__ICON_ID:
        return isSetIconId();
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
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (iconSetId: ");
    if (iconSetIdESet) result.append(iconSetId); else result.append("<unset>");
    result.append(", iconId: ");
    if (iconIdESet) result.append(iconId); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //IconInfoImpl
