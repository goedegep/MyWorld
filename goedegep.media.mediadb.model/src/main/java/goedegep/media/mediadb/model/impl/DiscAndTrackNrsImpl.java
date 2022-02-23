/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.MediadbPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Disc And Track Nrs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl#getDiscNr <em>Disc Nr</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl#getTrackNrs <em>Track Nrs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiscAndTrackNrsImpl extends MinimalEObjectImpl.Container implements DiscAndTrackNrs {
  /**
   * The default value of the '{@link #getDiscNr() <em>Disc Nr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDiscNr()
   * @generated
   * @ordered
   */
  protected static final int DISC_NR_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getDiscNr() <em>Disc Nr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDiscNr()
   * @generated
   * @ordered
   */
  protected int discNr = DISC_NR_EDEFAULT;

  /**
   * This is true if the Disc Nr attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean discNrESet;

  /**
   * The cached value of the '{@link #getTrackNrs() <em>Track Nrs</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackNrs()
   * @generated
   * @ordered
   */
  protected EList<Integer> trackNrs;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DiscAndTrackNrsImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.DISC_AND_TRACK_NRS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getDiscNr() {
    return discNr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDiscNr(int newDiscNr) {
    int oldDiscNr = discNr;
    discNr = newDiscNr;
    boolean oldDiscNrESet = discNrESet;
    discNrESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR, oldDiscNr,
          discNr, !oldDiscNrESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDiscNr() {
    int oldDiscNr = discNr;
    boolean oldDiscNrESet = discNrESet;
    discNr = DISC_NR_EDEFAULT;
    discNrESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR, oldDiscNr,
          DISC_NR_EDEFAULT, oldDiscNrESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDiscNr() {
    return discNrESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Integer> getTrackNrs() {
    if (trackNrs == null) {
      trackNrs = new EDataTypeUniqueEList.Unsettable<Integer>(Integer.class, this,
          MediadbPackage.DISC_AND_TRACK_NRS__TRACK_NRS);
    }
    return trackNrs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTrackNrs() {
    if (trackNrs != null)
      ((InternalEList.Unsettable<?>) trackNrs).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTrackNrs() {
    return trackNrs != null && ((InternalEList.Unsettable<?>) trackNrs).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR:
      return getDiscNr();
    case MediadbPackage.DISC_AND_TRACK_NRS__TRACK_NRS:
      return getTrackNrs();
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
    case MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR:
      setDiscNr((Integer) newValue);
      return;
    case MediadbPackage.DISC_AND_TRACK_NRS__TRACK_NRS:
      getTrackNrs().clear();
      getTrackNrs().addAll((Collection<? extends Integer>) newValue);
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
    case MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR:
      unsetDiscNr();
      return;
    case MediadbPackage.DISC_AND_TRACK_NRS__TRACK_NRS:
      unsetTrackNrs();
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
    case MediadbPackage.DISC_AND_TRACK_NRS__DISC_NR:
      return isSetDiscNr();
    case MediadbPackage.DISC_AND_TRACK_NRS__TRACK_NRS:
      return isSetTrackNrs();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (discNr: ");
    if (discNrESet)
      result.append(discNr);
    else
      result.append("<unset>");
    result.append(", trackNrs: ");
    result.append(trackNrs);
    result.append(')');
    return result.toString();
  }

  /**
   * @generated NOT
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    DiscAndTrackNrs discAndTrackNrs = (DiscAndTrackNrs) obj;
    if (getDiscNr() != discAndTrackNrs.getDiscNr()) {
      return false;
    }

    List<Integer> thisTrackNrs = getTrackNrs();
    List<Integer> otherTrackNrs = discAndTrackNrs.getTrackNrs();
    if (thisTrackNrs.size() != otherTrackNrs.size()) {
      return false;
    }

    for (int i = 0; i < getTrackNrs().size(); i++) {
      if (thisTrackNrs.get(i) != otherTrackNrs.get(i)) {
        return false;
      }
    }

    return true;
  }

  /**
   * @generated NOT
   */
  @Override
  public int hashCode() {
    return Objects.hash(discNr, trackNrs);
  }
} //DiscAndTrackNrsImpl
