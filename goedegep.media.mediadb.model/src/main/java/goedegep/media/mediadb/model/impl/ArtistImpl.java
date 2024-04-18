/**
 */
package goedegep.media.mediadb.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.TrackReference;

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
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getPhoto <em>Photo</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getMyComments <em>My Comments</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.ArtistImpl#getSample <em>Sample</em>}</li>
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
   * The default value of the '{@link #getPhoto() <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoto()
   * @generated
   * @ordered
   */
  protected static final String PHOTO_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPhoto() <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoto()
   * @generated
   * @ordered
   */
  protected String photo = PHOTO_EDEFAULT;

  /**
   * This is true if the Photo attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean photoESet;

  /**
   * The default value of the '{@link #getStyle() <em>Style</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStyle()
   * @generated
   * @ordered
   */
  protected static final String STYLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStyle() <em>Style</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStyle()
   * @generated
   * @ordered
   */
  protected String style = STYLE_EDEFAULT;

  /**
   * This is true if the Style attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean styleESet;

  /**
   * The default value of the '{@link #getMyComments() <em>My Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMyComments()
   * @generated
   * @ordered
   */
  protected static final String MY_COMMENTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMyComments() <em>My Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMyComments()
   * @generated
   * @ordered
   */
  protected String myComments = MY_COMMENTS_EDEFAULT;

  /**
   * This is true if the My Comments attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean myCommentsESet;

  /**
   * The cached value of the '{@link #getSample() <em>Sample</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSample()
   * @generated
   * @ordered
   */
  protected TrackReference sample;

  /**
   * This is true if the Sample containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean sampleESet;

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
  public String getPhoto() {
    return photo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoto(String newPhoto) {
    String oldPhoto = photo;
    photo = newPhoto;
    boolean oldPhotoESet = photoESet;
    photoESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__PHOTO, oldPhoto, photo, !oldPhotoESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPhoto() {
    String oldPhoto = photo;
    boolean oldPhotoESet = photoESet;
    photo = PHOTO_EDEFAULT;
    photoESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__PHOTO, oldPhoto, PHOTO_EDEFAULT,
          oldPhotoESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPhoto() {
    return photoESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getStyle() {
    return style;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStyle(String newStyle) {
    String oldStyle = style;
    style = newStyle;
    boolean oldStyleESet = styleESet;
    styleESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__STYLE, oldStyle, style, !oldStyleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStyle() {
    String oldStyle = style;
    boolean oldStyleESet = styleESet;
    style = STYLE_EDEFAULT;
    styleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__STYLE, oldStyle, STYLE_EDEFAULT,
          oldStyleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStyle() {
    return styleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getMyComments() {
    return myComments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMyComments(String newMyComments) {
    String oldMyComments = myComments;
    myComments = newMyComments;
    boolean oldMyCommentsESet = myCommentsESet;
    myCommentsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__MY_COMMENTS, oldMyComments,
          myComments, !oldMyCommentsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMyComments() {
    String oldMyComments = myComments;
    boolean oldMyCommentsESet = myCommentsESet;
    myComments = MY_COMMENTS_EDEFAULT;
    myCommentsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__MY_COMMENTS, oldMyComments,
          MY_COMMENTS_EDEFAULT, oldMyCommentsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMyComments() {
    return myCommentsESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackReference getSample() {
    return sample;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSample(TrackReference newSample, NotificationChain msgs) {
    TrackReference oldSample = sample;
    sample = newSample;
    boolean oldSampleESet = sampleESet;
    sampleESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__SAMPLE,
          oldSample, newSample, !oldSampleESet);
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
  public void setSample(TrackReference newSample) {
    if (newSample != sample) {
      NotificationChain msgs = null;
      if (sample != null)
        msgs = ((InternalEObject) sample).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ARTIST__SAMPLE,
            null, msgs);
      if (newSample != null)
        msgs = ((InternalEObject) newSample).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ARTIST__SAMPLE,
            null, msgs);
      msgs = basicSetSample(newSample, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldSampleESet = sampleESet;
      sampleESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ARTIST__SAMPLE, newSample, newSample,
            !oldSampleESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetSample(NotificationChain msgs) {
    TrackReference oldSample = sample;
    sample = null;
    boolean oldSampleESet = sampleESet;
    sampleESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__SAMPLE,
          oldSample, null, oldSampleESet);
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
  public void unsetSample() {
    if (sample != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) sample).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ARTIST__SAMPLE,
          null, msgs);
      msgs = basicUnsetSample(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldSampleESet = sampleESet;
      sampleESet = false;
      if (eNotificationRequired())
        eNotify(
            new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ARTIST__SAMPLE, null, null, oldSampleESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSample() {
    return sampleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.ARTIST__SAMPLE:
      return basicUnsetSample(msgs);
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
    case MediadbPackage.ARTIST__NAME:
      return getName();
    case MediadbPackage.ARTIST__CONTAINER_ARTIST:
      if (resolve)
        return getContainerArtist();
      return basicGetContainerArtist();
    case MediadbPackage.ARTIST__PHOTO:
      return getPhoto();
    case MediadbPackage.ARTIST__STYLE:
      return getStyle();
    case MediadbPackage.ARTIST__MY_COMMENTS:
      return getMyComments();
    case MediadbPackage.ARTIST__SAMPLE:
      return getSample();
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
    case MediadbPackage.ARTIST__PHOTO:
      setPhoto((String) newValue);
      return;
    case MediadbPackage.ARTIST__STYLE:
      setStyle((String) newValue);
      return;
    case MediadbPackage.ARTIST__MY_COMMENTS:
      setMyComments((String) newValue);
      return;
    case MediadbPackage.ARTIST__SAMPLE:
      setSample((TrackReference) newValue);
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
    case MediadbPackage.ARTIST__PHOTO:
      unsetPhoto();
      return;
    case MediadbPackage.ARTIST__STYLE:
      unsetStyle();
      return;
    case MediadbPackage.ARTIST__MY_COMMENTS:
      unsetMyComments();
      return;
    case MediadbPackage.ARTIST__SAMPLE:
      unsetSample();
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
    case MediadbPackage.ARTIST__PHOTO:
      return isSetPhoto();
    case MediadbPackage.ARTIST__STYLE:
      return isSetStyle();
    case MediadbPackage.ARTIST__MY_COMMENTS:
      return isSetMyComments();
    case MediadbPackage.ARTIST__SAMPLE:
      return isSetSample();
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
