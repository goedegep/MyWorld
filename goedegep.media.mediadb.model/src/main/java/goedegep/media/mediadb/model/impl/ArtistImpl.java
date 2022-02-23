/**
 */
package goedegep.media.mediadb.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artist</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getContainerArtist <em>Container Artist</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArtistImpl extends MinimalEObjectImpl.Container implements Artist {

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The cached value of the '{@link #getContainerArtist() <em>Container Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContainerArtist()
   * @generated
   * @ordered
   */
  protected Artist containerArtist;

  /**
   * This is true if the Container Artist reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean containerArtistESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ArtistImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.ARTIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__NAME, oldName, name, !oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__NAME, oldName, NAME_EDEFAULT,
          oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Artist getContainerArtist() {
    if (containerArtist != null && containerArtist.eIsProxy()) {
      InternalEObject oldContainerArtist = (InternalEObject) containerArtist;
      containerArtist = (Artist) eResolveProxy(oldContainerArtist);
      if (containerArtist != oldContainerArtist) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.ARTIST__CONTAINER_ARTIST,
              oldContainerArtist, containerArtist));
      }
    }
    return containerArtist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Artist basicGetContainerArtist() {
    return containerArtist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setContainerArtist(Artist newContainerArtist) {
    Artist oldContainerArtist = containerArtist;
    containerArtist = newContainerArtist;
    boolean oldContainerArtistESet = containerArtistESet;
    containerArtistESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__CONTAINER_ARTIST, oldContainerArtist,
          containerArtist, !oldContainerArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetContainerArtist() {
    Artist oldContainerArtist = containerArtist;
    boolean oldContainerArtistESet = containerArtistESet;
    containerArtist = null;
    containerArtistESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__CONTAINER_ARTIST,
          oldContainerArtist, null, oldContainerArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetContainerArtist() {
    return containerArtistESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.ARTIST__NAME:
      return getName();
    case MediadbPackage.ARTIST__CONTAINER_ARTIST:
      if (resolve)
        return getContainerArtist();
      return basicGetContainerArtist();
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
    case MediadbPackage.ARTIST__NAME:
      setName((String) newValue);
      return;
    case MediadbPackage.ARTIST__CONTAINER_ARTIST:
      setContainerArtist((Artist) newValue);
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
    case MediadbPackage.ARTIST__NAME:
      unsetName();
      return;
    case MediadbPackage.ARTIST__CONTAINER_ARTIST:
      unsetContainerArtist();
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
    case MediadbPackage.ARTIST__NAME:
      return isSetName();
    case MediadbPackage.ARTIST__CONTAINER_ARTIST:
      return isSetContainerArtist();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    return getName();
  }

} //ArtistImpl
