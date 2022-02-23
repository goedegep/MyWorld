/**
 */
package goedegep.vacations.model.impl;

import goedegep.types.model.FileReference;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.VacationsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vacation Element Picture</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.PictureImpl#getPictureReference <em>Picture Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PictureImpl extends VacationElementImpl implements Picture {
  /**
   * The cached value of the '{@link #getPictureReference() <em>Picture Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPictureReference()
   * @generated
   * @ordered
   */
  protected FileReference pictureReference;

  /**
   * This is true if the Picture Reference containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean pictureReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PictureImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.PICTURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference getPictureReference() {
    return pictureReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPictureReference(FileReference newPictureReference, NotificationChain msgs) {
    FileReference oldPictureReference = pictureReference;
    pictureReference = newPictureReference;
    boolean oldPictureReferenceESet = pictureReferenceESet;
    pictureReferenceESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          VacationsPackage.PICTURE__PICTURE_REFERENCE, oldPictureReference, newPictureReference,
          !oldPictureReferenceESet);
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
  public void setPictureReference(FileReference newPictureReference) {
    if (newPictureReference != pictureReference) {
      NotificationChain msgs = null;
      if (pictureReference != null)
        msgs = ((InternalEObject) pictureReference).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.PICTURE__PICTURE_REFERENCE, null, msgs);
      if (newPictureReference != null)
        msgs = ((InternalEObject) newPictureReference).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.PICTURE__PICTURE_REFERENCE, null, msgs);
      msgs = basicSetPictureReference(newPictureReference, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldPictureReferenceESet = pictureReferenceESet;
      pictureReferenceESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.PICTURE__PICTURE_REFERENCE,
            newPictureReference, newPictureReference, !oldPictureReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetPictureReference(NotificationChain msgs) {
    FileReference oldPictureReference = pictureReference;
    pictureReference = null;
    boolean oldPictureReferenceESet = pictureReferenceESet;
    pictureReferenceESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          VacationsPackage.PICTURE__PICTURE_REFERENCE, oldPictureReference, null, oldPictureReferenceESet);
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
  public void unsetPictureReference() {
    if (pictureReference != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) pictureReference).eInverseRemove(this,
          EOPPOSITE_FEATURE_BASE - VacationsPackage.PICTURE__PICTURE_REFERENCE, null, msgs);
      msgs = basicUnsetPictureReference(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldPictureReferenceESet = pictureReferenceESet;
      pictureReferenceESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.PICTURE__PICTURE_REFERENCE, null, null,
            oldPictureReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPictureReference() {
    return pictureReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.PICTURE__PICTURE_REFERENCE:
      return basicUnsetPictureReference(msgs);
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
    case VacationsPackage.PICTURE__PICTURE_REFERENCE:
      return getPictureReference();
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
    case VacationsPackage.PICTURE__PICTURE_REFERENCE:
      setPictureReference((FileReference) newValue);
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
    case VacationsPackage.PICTURE__PICTURE_REFERENCE:
      unsetPictureReference();
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
    case VacationsPackage.PICTURE__PICTURE_REFERENCE:
      return isSetPictureReference();
    }
    return super.eIsSet(featureID);
  }

} //VacationElementPictureImpl
