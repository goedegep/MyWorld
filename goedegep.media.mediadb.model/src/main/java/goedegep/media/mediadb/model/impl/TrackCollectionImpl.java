/**
 */
package goedegep.media.mediadb.model.impl;

import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Track Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackCollectionImpl#getCollection <em>Collection</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackCollectionImpl#getTrackReferences <em>Track References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackCollectionImpl extends MinimalEObjectImpl.Container implements TrackCollection {
  /**
   * The default value of the '{@link #getCollection() <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCollection()
   * @generated
   * @ordered
   */
  protected static final Collection COLLECTION_EDEFAULT = Collection.NOT_SET;

  /**
   * The cached value of the '{@link #getCollection() <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCollection()
   * @generated
   * @ordered
   */
  protected Collection collection = COLLECTION_EDEFAULT;

  /**
   * This is true if the Collection attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean collectionESet;

  /**
   * The cached value of the '{@link #getTrackReferences() <em>Track References</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackReferences()
   * @generated
   * @ordered
   */
  protected EList<TrackReference> trackReferences;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TrackCollectionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.TRACK_COLLECTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection getCollection() {
    return collection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCollection(Collection newCollection) {
    Collection oldCollection = collection;
    collection = newCollection == null ? COLLECTION_EDEFAULT : newCollection;
    boolean oldCollectionESet = collectionESet;
    collectionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK_COLLECTION__COLLECTION, oldCollection,
          collection, !oldCollectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCollection() {
    Collection oldCollection = collection;
    boolean oldCollectionESet = collectionESet;
    collection = COLLECTION_EDEFAULT;
    collectionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK_COLLECTION__COLLECTION,
          oldCollection, COLLECTION_EDEFAULT, oldCollectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCollection() {
    return collectionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TrackReference> getTrackReferences() {
    if (trackReferences == null) {
      trackReferences = new EObjectContainmentEList<TrackReference>(TrackReference.class, this,
          MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES);
    }
    return trackReferences;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public TrackReference getTrackReferece(Artist artist, String title) {
    java.util.Objects.requireNonNull(artist, "Parameter artist may not be null");
    java.util.Objects.requireNonNull(title, "Parameter title may not be null");

    TrackReference collectionTrackReference = null;

    for (TrackReference trackReference : getTrackReferences()) {
      if (artist == trackReference.getTrack().getArtist() && title.equals(trackReference.getTrack().getTitle())) {
        if (collectionTrackReference != null) {
          throw new RuntimeException("Found more than one TrackReference for: " + artist.getName() + " - " + title);
        }

        collectionTrackReference = trackReference;
      }
    }

    return collectionTrackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES:
      return ((InternalEList<?>) getTrackReferences()).basicRemove(otherEnd, msgs);
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
    case MediadbPackage.TRACK_COLLECTION__COLLECTION:
      return getCollection();
    case MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES:
      return getTrackReferences();
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
    case MediadbPackage.TRACK_COLLECTION__COLLECTION:
      setCollection((Collection) newValue);
      return;
    case MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES:
      getTrackReferences().clear();
      getTrackReferences().addAll((java.util.Collection<? extends TrackReference>) newValue);
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
    case MediadbPackage.TRACK_COLLECTION__COLLECTION:
      unsetCollection();
      return;
    case MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES:
      getTrackReferences().clear();
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
    case MediadbPackage.TRACK_COLLECTION__COLLECTION:
      return isSetCollection();
    case MediadbPackage.TRACK_COLLECTION__TRACK_REFERENCES:
      return trackReferences != null && !trackReferences.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case MediadbPackage.TRACK_COLLECTION___GET_TRACK_REFERECE__ARTIST_STRING:
      return getTrackReferece((Artist) arguments.get(0), (String) arguments.get(1));
    }
    return super.eInvoke(operationID, arguments);
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
    result.append(" (collection: ");
    if (collectionESet)
      result.append(collection);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //TrackCollectionImpl
