/**
 */
package goedegep.travels.model.impl;

import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.TravelsPackage;
import goedegep.types.model.FileReference;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vacation Element GPX</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.GPXTrackImpl#getTrackReference <em>Track Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GPXTrackImpl extends VacationElementImpl implements GPXTrack {
  /**
   * The cached value of the '{@link #getTrackReference() <em>Track Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackReference()
   * @generated
   * @ordered
   */
  protected FileReference trackReference;

  /**
   * This is true if the Track Reference containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean trackReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GPXTrackImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.GPX_TRACK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference getTrackReference() {
    return trackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTrackReference(FileReference newTrackReference, NotificationChain msgs) {
    FileReference oldTrackReference = trackReference;
    trackReference = newTrackReference;
    boolean oldTrackReferenceESet = trackReferenceESet;
    trackReferenceESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          TravelsPackage.GPX_TRACK__TRACK_REFERENCE, oldTrackReference, newTrackReference, !oldTrackReferenceESet);
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
  public void setTrackReference(FileReference newTrackReference) {
    if (newTrackReference != trackReference) {
      NotificationChain msgs = null;
      if (trackReference != null)
        msgs = ((InternalEObject) trackReference).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - TravelsPackage.GPX_TRACK__TRACK_REFERENCE, null, msgs);
      if (newTrackReference != null)
        msgs = ((InternalEObject) newTrackReference).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - TravelsPackage.GPX_TRACK__TRACK_REFERENCE, null, msgs);
      msgs = basicSetTrackReference(newTrackReference, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldTrackReferenceESet = trackReferenceESet;
      trackReferenceESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.GPX_TRACK__TRACK_REFERENCE,
            newTrackReference, newTrackReference, !oldTrackReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetTrackReference(NotificationChain msgs) {
    FileReference oldTrackReference = trackReference;
    trackReference = null;
    boolean oldTrackReferenceESet = trackReferenceESet;
    trackReferenceESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          TravelsPackage.GPX_TRACK__TRACK_REFERENCE, oldTrackReference, null, oldTrackReferenceESet);
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
  public void unsetTrackReference() {
    if (trackReference != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) trackReference).eInverseRemove(this,
          EOPPOSITE_FEATURE_BASE - TravelsPackage.GPX_TRACK__TRACK_REFERENCE, null, msgs);
      msgs = basicUnsetTrackReference(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldTrackReferenceESet = trackReferenceESet;
      trackReferenceESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.GPX_TRACK__TRACK_REFERENCE, null, null,
            oldTrackReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTrackReference() {
    return trackReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case TravelsPackage.GPX_TRACK__TRACK_REFERENCE:
      return basicUnsetTrackReference(msgs);
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
    case TravelsPackage.GPX_TRACK__TRACK_REFERENCE:
      return getTrackReference();
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
    case TravelsPackage.GPX_TRACK__TRACK_REFERENCE:
      setTrackReference((FileReference) newValue);
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
    case TravelsPackage.GPX_TRACK__TRACK_REFERENCE:
      unsetTrackReference();
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
    case TravelsPackage.GPX_TRACK__TRACK_REFERENCE:
      return isSetTrackReference();
    }
    return super.eIsSet(featureID);
  }

} //VacationElementGPXImpl
