/**
 */
package goedegep.poi.model.impl;

import goedegep.poi.model.POIIconResourceDescriptor;
import goedegep.poi.model.POIIconResourceInfo;
import goedegep.poi.model.POIPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon Resource Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.poi.model.impl.POIIconResourceInfoImpl#getPoiIconResourceDescriptors <em>Poi Icon Resource Descriptors</em>}</li>
 * </ul>
 *
 * @generated
 */
public class POIIconResourceInfoImpl extends MinimalEObjectImpl.Container implements POIIconResourceInfo {
  /**
   * The cached value of the '{@link #getPoiIconResourceDescriptors() <em>Poi Icon Resource Descriptors</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPoiIconResourceDescriptors()
   * @generated
   * @ordered
   */
  protected EList<POIIconResourceDescriptor> poiIconResourceDescriptors;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected POIIconResourceInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return POIPackage.Literals.POI_ICON_RESOURCE_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<POIIconResourceDescriptor> getPoiIconResourceDescriptors() {
    if (poiIconResourceDescriptors == null) {
      poiIconResourceDescriptors = new EObjectContainmentEList<POIIconResourceDescriptor>(POIIconResourceDescriptor.class, this, POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS);
    }
    return poiIconResourceDescriptors;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS:
        return ((InternalEList<?>)getPoiIconResourceDescriptors()).basicRemove(otherEnd, msgs);
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
      case POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS:
        return getPoiIconResourceDescriptors();
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
      case POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS:
        getPoiIconResourceDescriptors().clear();
        getPoiIconResourceDescriptors().addAll((Collection<? extends POIIconResourceDescriptor>)newValue);
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
      case POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS:
        getPoiIconResourceDescriptors().clear();
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
      case POIPackage.POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS:
        return poiIconResourceDescriptors != null && !poiIconResourceDescriptors.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //POIIconResourceInfoImpl
