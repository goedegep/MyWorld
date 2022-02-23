/**
 */
package goedegep.poi.model.impl;

import goedegep.poi.model.POICategoryId;
import goedegep.poi.model.POIIconResourceDescriptor;
import goedegep.poi.model.POIPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Resource Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.poi.model.impl.POIIconResourceDescriptorImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link goedegep.poi.model.impl.POIIconResourceDescriptorImpl#getIconFileName <em>Icon File Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class POIIconResourceDescriptorImpl extends MinimalEObjectImpl.Container implements POIIconResourceDescriptor {
  /**
   * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCategory()
   * @generated
   * @ordered
   */
  protected static final POICategoryId CATEGORY_EDEFAULT = POICategoryId.DEFAULT_POI;

  /**
   * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCategory()
   * @generated
   * @ordered
   */
  protected POICategoryId category = CATEGORY_EDEFAULT;

  /**
   * This is true if the Category attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean categoryESet;

  /**
   * The default value of the '{@link #getIconFileName() <em>Icon File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconFileName()
   * @generated
   * @ordered
   */
  protected static final String ICON_FILE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getIconFileName() <em>Icon File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconFileName()
   * @generated
   * @ordered
   */
  protected String iconFileName = ICON_FILE_NAME_EDEFAULT;

  /**
   * This is true if the Icon File Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iconFileNameESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected POIIconResourceDescriptorImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return POIPackage.Literals.POI_ICON_RESOURCE_DESCRIPTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public POICategoryId getCategory() {
    return category;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCategory(POICategoryId newCategory) {
    POICategoryId oldCategory = category;
    category = newCategory == null ? CATEGORY_EDEFAULT : newCategory;
    boolean oldCategoryESet = categoryESet;
    categoryESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY, oldCategory, category, !oldCategoryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCategory() {
    POICategoryId oldCategory = category;
    boolean oldCategoryESet = categoryESet;
    category = CATEGORY_EDEFAULT;
    categoryESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY, oldCategory, CATEGORY_EDEFAULT, oldCategoryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCategory() {
    return categoryESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getIconFileName() {
    return iconFileName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIconFileName(String newIconFileName) {
    String oldIconFileName = iconFileName;
    iconFileName = newIconFileName;
    boolean oldIconFileNameESet = iconFileNameESet;
    iconFileNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME, oldIconFileName, iconFileName, !oldIconFileNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIconFileName() {
    String oldIconFileName = iconFileName;
    boolean oldIconFileNameESet = iconFileNameESet;
    iconFileName = ICON_FILE_NAME_EDEFAULT;
    iconFileNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME, oldIconFileName, ICON_FILE_NAME_EDEFAULT, oldIconFileNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIconFileName() {
    return iconFileNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY:
        return getCategory();
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME:
        return getIconFileName();
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
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY:
        setCategory((POICategoryId)newValue);
        return;
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME:
        setIconFileName((String)newValue);
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
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY:
        unsetCategory();
        return;
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME:
        unsetIconFileName();
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
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY:
        return isSetCategory();
      case POIPackage.POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME:
        return isSetIconFileName();
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
    result.append(" (category: ");
    if (categoryESet) result.append(category); else result.append("<unset>");
    result.append(", iconFileName: ");
    if (iconFileNameESet) result.append(iconFileName); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //POIIconResourceDescriptorImpl
