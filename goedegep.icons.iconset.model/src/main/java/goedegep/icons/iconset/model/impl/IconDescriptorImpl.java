/**
 */
package goedegep.icons.iconset.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.icons.iconset.model.IconDescriptor;
import goedegep.icons.iconset.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconDescriptorImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconDescriptorImpl#getIconId <em>Icon Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconDescriptorImpl extends MinimalEObjectImpl.Container implements IconDescriptor {
  /**
   * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUrl()
   * @generated
   * @ordered
   */
  protected static final String URL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUrl()
   * @generated
   * @ordered
   */
  protected String url = URL_EDEFAULT;

  /**
   * This is true if the Url attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean urlESet;

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
  protected IconDescriptorImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ModelPackage.Literals.ICON_DESCRIPTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getUrl() {
    return url;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUrl(String newUrl) {
    String oldUrl = url;
    url = newUrl;
    boolean oldUrlESet = urlESet;
    urlESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DESCRIPTOR__URL, oldUrl, url, !oldUrlESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetUrl() {
    String oldUrl = url;
    boolean oldUrlESet = urlESet;
    url = URL_EDEFAULT;
    urlESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DESCRIPTOR__URL, oldUrl, URL_EDEFAULT, oldUrlESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetUrl() {
    return urlESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_DESCRIPTOR__ICON_ID, oldIconId, iconId, !oldIconIdESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_DESCRIPTOR__ICON_ID, oldIconId, ICON_ID_EDEFAULT, oldIconIdESet));
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
      case ModelPackage.ICON_DESCRIPTOR__URL:
        return getUrl();
      case ModelPackage.ICON_DESCRIPTOR__ICON_ID:
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
      case ModelPackage.ICON_DESCRIPTOR__URL:
        setUrl((String)newValue);
        return;
      case ModelPackage.ICON_DESCRIPTOR__ICON_ID:
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
      case ModelPackage.ICON_DESCRIPTOR__URL:
        unsetUrl();
        return;
      case ModelPackage.ICON_DESCRIPTOR__ICON_ID:
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
      case ModelPackage.ICON_DESCRIPTOR__URL:
        return isSetUrl();
      case ModelPackage.ICON_DESCRIPTOR__ICON_ID:
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
    result.append(" (url: ");
    if (urlESet) result.append(url); else result.append("<unset>");
    result.append(", iconId: ");
    if (iconIdESet) result.append(iconId); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //IconDescriptorImpl
