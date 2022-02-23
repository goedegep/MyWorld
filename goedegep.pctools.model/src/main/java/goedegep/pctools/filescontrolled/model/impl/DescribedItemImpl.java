/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Described Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl#getItem <em>Item</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DescribedItemImpl extends MinimalEObjectImpl.Container implements DescribedItem {
  /**
   * The default value of the '{@link #getItem() <em>Item</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getItem()
   * @generated
   * @ordered
   */
  protected static final String ITEM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getItem() <em>Item</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getItem()
   * @generated
   * @ordered
   */
  protected String item = ITEM_EDEFAULT;

  /**
   * This is true if the Item attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean itemESet;

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
  protected DescribedItemImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.DESCRIBED_ITEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getItem() {
    return item;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setItem(String newItem) {
    String oldItem = item;
    item = newItem;
    boolean oldItemESet = itemESet;
    itemESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DESCRIBED_ITEM__ITEM, oldItem, item, !oldItemESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetItem() {
    String oldItem = item;
    boolean oldItemESet = itemESet;
    item = ITEM_EDEFAULT;
    itemESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DESCRIBED_ITEM__ITEM, oldItem, ITEM_EDEFAULT, oldItemESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetItem() {
    return itemESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
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
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PCToolsPackage.DESCRIBED_ITEM__ITEM:
        return getItem();
      case PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION:
        return getDescription();
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
      case PCToolsPackage.DESCRIBED_ITEM__ITEM:
        setItem((String)newValue);
        return;
      case PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION:
        setDescription((String)newValue);
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
      case PCToolsPackage.DESCRIBED_ITEM__ITEM:
        unsetItem();
        return;
      case PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION:
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
      case PCToolsPackage.DESCRIBED_ITEM__ITEM:
        return isSetItem();
      case PCToolsPackage.DESCRIBED_ITEM__DESCRIPTION:
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
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (item: ");
    if (itemESet) result.append(item); else result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //DescribedItemImpl
