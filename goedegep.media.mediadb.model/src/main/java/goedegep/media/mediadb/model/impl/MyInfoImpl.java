/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#getAlbumReferences <em>Album References</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#getMyComments <em>My Comments</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#isIveHadOnLP <em>Ive Had On LP</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#getIWant <em>IWant</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#getIHaveOn <em>IHave On</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyInfoImpl#getAlbumType <em>Album Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MyInfoImpl extends MinimalEObjectImpl.Container implements MyInfo {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MyInfoImpl.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The cached value of the '{@link #getAlbumReferences() <em>Album References</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlbumReferences()
   * @generated
   * @ordered
   */
  protected EList<Album> albumReferences;
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
   * The default value of the '{@link #isIveHadOnLP() <em>Ive Had On LP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIveHadOnLP()
   * @generated
   * @ordered
   */
  protected static final boolean IVE_HAD_ON_LP_EDEFAULT = false;
  /**
   * The cached value of the '{@link #isIveHadOnLP() <em>Ive Had On LP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIveHadOnLP()
   * @generated
   * @ordered
   */
  protected boolean iveHadOnLP = IVE_HAD_ON_LP_EDEFAULT;
  /**
   * The default value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected static final IWant IWANT_EDEFAULT = IWant.NOT_SET;
  /**
   * The cached value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected IWant iWant = IWANT_EDEFAULT;
  /**
   * This is true if the IWant attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iWantESet;

  /**
   * The cached value of the '{@link #getIHaveOn() <em>IHave On</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIHaveOn()
   * @generated
   * @ordered
   */
  protected EList<MediumInfo> iHaveOn;

  /**
   * The default value of the '{@link #getAlbumType() <em>Album Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlbumType()
   * @generated
   * @ordered
   */
  protected static final AlbumType ALBUM_TYPE_EDEFAULT = AlbumType.NORMAL_ALBUM;
  /**
   * The cached value of the '{@link #getAlbumType() <em>Album Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlbumType()
   * @generated
   * @ordered
   */
  protected AlbumType albumType = ALBUM_TYPE_EDEFAULT;
  /**
   * This is true if the Album Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean albumTypeESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MyInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MY_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Album> getAlbumReferences() {
    if (albumReferences == null) {
      albumReferences = new EObjectResolvingEList.Unsettable<Album>(Album.class, this,
          MediadbPackage.MY_INFO__ALBUM_REFERENCES);
    }
    return albumReferences;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetAlbumReferences() {
    if (albumReferences != null)
      ((InternalEList.Unsettable<?>) albumReferences).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetAlbumReferences() {
    return albumReferences != null && ((InternalEList.Unsettable<?>) albumReferences).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IWant getIWant() {
    return iWant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIWant(IWant newIWant) {
    IWant oldIWant = iWant;
    iWant = newIWant == null ? IWANT_EDEFAULT : newIWant;
    boolean oldIWantESet = iWantESet;
    iWantESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_INFO__IWANT, oldIWant, iWant, !oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIWant() {
    IWant oldIWant = iWant;
    boolean oldIWantESet = iWantESet;
    iWant = IWANT_EDEFAULT;
    iWantESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_INFO__IWANT, oldIWant, IWANT_EDEFAULT,
          oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIWant() {
    return iWantESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<MediumInfo> getIHaveOn() {
    if (iHaveOn == null) {
      iHaveOn = new EObjectContainmentEList<MediumInfo>(MediumInfo.class, this, MediadbPackage.MY_INFO__IHAVE_ON);
    }
    return iHaveOn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AlbumType getAlbumType() {
    return albumType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAlbumType(AlbumType newAlbumType) {
    AlbumType oldAlbumType = albumType;
    albumType = newAlbumType == null ? ALBUM_TYPE_EDEFAULT : newAlbumType;
    boolean oldAlbumTypeESet = albumTypeESet;
    albumTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_INFO__ALBUM_TYPE, oldAlbumType, albumType,
          !oldAlbumTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetAlbumType() {
    AlbumType oldAlbumType = albumType;
    boolean oldAlbumTypeESet = albumTypeESet;
    albumType = ALBUM_TYPE_EDEFAULT;
    albumTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_INFO__ALBUM_TYPE, oldAlbumType,
          ALBUM_TYPE_EDEFAULT, oldAlbumTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetAlbumType() {
    return albumTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.MY_INFO__IHAVE_ON:
      return ((InternalEList<?>) getIHaveOn()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_INFO__MY_COMMENTS, oldMyComments,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_INFO__MY_COMMENTS, oldMyComments,
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
  public boolean isIveHadOnLP() {
    return iveHadOnLP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIveHadOnLP(boolean newIveHadOnLP) {
    boolean oldIveHadOnLP = iveHadOnLP;
    iveHadOnLP = newIveHadOnLP;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_INFO__IVE_HAD_ON_LP, oldIveHadOnLP,
          iveHadOnLP));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.MY_INFO__ALBUM_REFERENCES:
      return getAlbumReferences();
    case MediadbPackage.MY_INFO__MY_COMMENTS:
      return getMyComments();
    case MediadbPackage.MY_INFO__IVE_HAD_ON_LP:
      return isIveHadOnLP();
    case MediadbPackage.MY_INFO__IWANT:
      return getIWant();
    case MediadbPackage.MY_INFO__IHAVE_ON:
      return getIHaveOn();
    case MediadbPackage.MY_INFO__ALBUM_TYPE:
      return getAlbumType();
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
    case MediadbPackage.MY_INFO__ALBUM_REFERENCES:
      getAlbumReferences().clear();
      getAlbumReferences().addAll((Collection<? extends Album>) newValue);
      return;
    case MediadbPackage.MY_INFO__MY_COMMENTS:
      setMyComments((String) newValue);
      return;
    case MediadbPackage.MY_INFO__IVE_HAD_ON_LP:
      setIveHadOnLP((Boolean) newValue);
      return;
    case MediadbPackage.MY_INFO__IWANT:
      setIWant((IWant) newValue);
      return;
    case MediadbPackage.MY_INFO__IHAVE_ON:
      getIHaveOn().clear();
      getIHaveOn().addAll((Collection<? extends MediumInfo>) newValue);
      return;
    case MediadbPackage.MY_INFO__ALBUM_TYPE:
      setAlbumType((AlbumType) newValue);
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
    case MediadbPackage.MY_INFO__ALBUM_REFERENCES:
      unsetAlbumReferences();
      return;
    case MediadbPackage.MY_INFO__MY_COMMENTS:
      unsetMyComments();
      return;
    case MediadbPackage.MY_INFO__IVE_HAD_ON_LP:
      setIveHadOnLP(IVE_HAD_ON_LP_EDEFAULT);
      return;
    case MediadbPackage.MY_INFO__IWANT:
      unsetIWant();
      return;
    case MediadbPackage.MY_INFO__IHAVE_ON:
      getIHaveOn().clear();
      return;
    case MediadbPackage.MY_INFO__ALBUM_TYPE:
      unsetAlbumType();
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
    case MediadbPackage.MY_INFO__ALBUM_REFERENCES:
      return isSetAlbumReferences();
    case MediadbPackage.MY_INFO__MY_COMMENTS:
      return isSetMyComments();
    case MediadbPackage.MY_INFO__IVE_HAD_ON_LP:
      return iveHadOnLP != IVE_HAD_ON_LP_EDEFAULT;
    case MediadbPackage.MY_INFO__IWANT:
      return isSetIWant();
    case MediadbPackage.MY_INFO__IHAVE_ON:
      return iHaveOn != null && !iHaveOn.isEmpty();
    case MediadbPackage.MY_INFO__ALBUM_TYPE:
      return isSetAlbumType();
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
    StringBuilder buf = new StringBuilder();

    buf.append("albumType: ").append(getAlbumType()).append(NEWLINE);
    buf.append("My comments: ").append(isSetMyComments() ? getMyComments() : "<no-my-comments>").append(NEWLINE);
    buf.append("I want: ").append(getIWant() != null ? getIWant().toString() : "<no-iwant>").append(NEWLINE);

    if (getIHaveOn().isEmpty()) {
      buf.append("No I Have on").append(NEWLINE);
    } else {
      for (MediumInfo mediumInfo : getIHaveOn()) {
        buf.append("I have on: ").append(mediumInfo).append(NEWLINE);
      }
    }

    if (getAlbumReferences().isEmpty()) {
      buf.append("No album references").append(NEWLINE);
    } else {
      for (Album album : getAlbumReferences()) {
        buf.append("Album reference: ").append(album.getArtistAndTitle()).append(NEWLINE);
      }
    }

    buf.append("iveHadOnLp: ").append(isIveHadOnLP() ? "yes" : "no").append(NEWLINE);

    return buf.toString();
  }

} //MyInfoImpl
