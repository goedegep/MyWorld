/**
 */
package goedegep.icons.iconset.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.icons.iconset.model.IconSize;
import goedegep.icons.iconset.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Size</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconSizeImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconSizeImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.impl.IconSizeImpl#getDpi <em>Dpi</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconSizeImpl extends MinimalEObjectImpl.Container implements IconSize {
  /**
   * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWidth()
   * @generated
   * @ordered
   */
  protected static final int WIDTH_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWidth()
   * @generated
   * @ordered
   */
  protected int width = WIDTH_EDEFAULT;

  /**
   * This is true if the Width attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean widthESet;

  /**
   * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeight()
   * @generated
   * @ordered
   */
  protected static final int HEIGHT_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeight()
   * @generated
   * @ordered
   */
  protected int height = HEIGHT_EDEFAULT;

  /**
   * This is true if the Height attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean heightESet;

  /**
   * The default value of the '{@link #getDpi() <em>Dpi</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDpi()
   * @generated
   * @ordered
   */
  protected static final int DPI_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getDpi() <em>Dpi</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDpi()
   * @generated
   * @ordered
   */
  protected int dpi = DPI_EDEFAULT;

  /**
   * This is true if the Dpi attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dpiESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IconSizeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ModelPackage.Literals.ICON_SIZE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setWidth(int newWidth) {
    int oldWidth = width;
    width = newWidth;
    boolean oldWidthESet = widthESet;
    widthESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_SIZE__WIDTH, oldWidth, width, !oldWidthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetWidth() {
    int oldWidth = width;
    boolean oldWidthESet = widthESet;
    width = WIDTH_EDEFAULT;
    widthESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_SIZE__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetWidth() {
    return widthESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHeight(int newHeight) {
    int oldHeight = height;
    height = newHeight;
    boolean oldHeightESet = heightESet;
    heightESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_SIZE__HEIGHT, oldHeight, height, !oldHeightESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetHeight() {
    int oldHeight = height;
    boolean oldHeightESet = heightESet;
    height = HEIGHT_EDEFAULT;
    heightESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_SIZE__HEIGHT, oldHeight, HEIGHT_EDEFAULT, oldHeightESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetHeight() {
    return heightESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getDpi() {
    return dpi;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDpi(int newDpi) {
    int oldDpi = dpi;
    dpi = newDpi;
    boolean oldDpiESet = dpiESet;
    dpiESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ICON_SIZE__DPI, oldDpi, dpi, !oldDpiESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDpi() {
    int oldDpi = dpi;
    boolean oldDpiESet = dpiESet;
    dpi = DPI_EDEFAULT;
    dpiESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ICON_SIZE__DPI, oldDpi, DPI_EDEFAULT, oldDpiESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDpi() {
    return dpiESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ModelPackage.ICON_SIZE__WIDTH:
        return getWidth();
      case ModelPackage.ICON_SIZE__HEIGHT:
        return getHeight();
      case ModelPackage.ICON_SIZE__DPI:
        return getDpi();
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
      case ModelPackage.ICON_SIZE__WIDTH:
        setWidth((Integer)newValue);
        return;
      case ModelPackage.ICON_SIZE__HEIGHT:
        setHeight((Integer)newValue);
        return;
      case ModelPackage.ICON_SIZE__DPI:
        setDpi((Integer)newValue);
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
      case ModelPackage.ICON_SIZE__WIDTH:
        unsetWidth();
        return;
      case ModelPackage.ICON_SIZE__HEIGHT:
        unsetHeight();
        return;
      case ModelPackage.ICON_SIZE__DPI:
        unsetDpi();
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
      case ModelPackage.ICON_SIZE__WIDTH:
        return isSetWidth();
      case ModelPackage.ICON_SIZE__HEIGHT:
        return isSetHeight();
      case ModelPackage.ICON_SIZE__DPI:
        return isSetDpi();
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
    result.append(" (width: ");
    if (widthESet) result.append(width); else result.append("<unset>");
    result.append(", height: ");
    if (heightESet) result.append(height); else result.append("<unset>");
    result.append(", dpi: ");
    if (dpiESet) result.append(dpi); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //IconSizeImpl
