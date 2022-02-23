/**
 */
package goedegep.vacations.model.impl;

import goedegep.types.model.FileReference;

import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.VacationsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Image</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getImageReference <em>Image Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapImageImpl extends VacationElementImpl implements MapImage {
  /**
   * The cached value of the '{@link #getImageReference() <em>Image Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImageReference()
   * @generated
   * @ordered
   */
  protected FileReference imageReference;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MapImageImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.MAP_IMAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference getImageReference() {
    return imageReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetImageReference(FileReference newImageReference, NotificationChain msgs) {
    FileReference oldImageReference = imageReference;
    imageReference = newImageReference;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE, oldImageReference, newImageReference);
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
  public void setImageReference(FileReference newImageReference) {
    if (newImageReference != imageReference) {
      NotificationChain msgs = null;
      if (imageReference != null)
        msgs = ((InternalEObject) imageReference).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE, null, msgs);
      if (newImageReference != null)
        msgs = ((InternalEObject) newImageReference).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE, null, msgs);
      msgs = basicSetImageReference(newImageReference, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE,
          newImageReference, newImageReference));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE:
      return basicSetImageReference(null, msgs);
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
    case VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE:
      return getImageReference();
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
    case VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE:
      setImageReference((FileReference) newValue);
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
    case VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE:
      setImageReference((FileReference) null);
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
    case VacationsPackage.MAP_IMAGE__IMAGE_REFERENCE:
      return imageReference != null;
    }
    return super.eIsSet(featureID);
  }

} //MapImageImpl
