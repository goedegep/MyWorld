/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.string.StringUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Album</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getDiscs <em>Discs</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getImagesFront <em>Images Front</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getId <em>Id</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getPlayers <em>Players</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getImagesFrontInside <em>Images Front Inside</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getImagesBack <em>Images Back</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getImagesLabel <em>Images Label</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getDescriptionTitle <em>Description Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#getMyInfo <em>My Info</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.AlbumImpl#isSoundtrack <em>Soundtrack</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AlbumImpl extends MinimalEObjectImpl.Container implements Album {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final FlexDateFormat FDF = new FlexDateFormat();

  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;

  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The default value of the '{@link #getReleaseDate() <em>Release Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReleaseDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate RELEASE_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getReleaseDate() <em>Release Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReleaseDate()
   * @generated
   * @ordered
   */
  protected FlexDate releaseDate = RELEASE_DATE_EDEFAULT;

  /**
   * This is true if the Release Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean releaseDateESet;

  /**
   * The cached value of the '{@link #getDiscs() <em>Discs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDiscs()
   * @generated
   * @ordered
   */
  protected EList<Disc> discs;

  /**
   * The cached value of the '{@link #getArtist() <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArtist()
   * @generated
   * @ordered
   */
  protected Artist artist;

  /**
   * This is true if the Artist reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean artistESet;

  /**
   * The cached value of the '{@link #getImagesFront() <em>Images Front</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImagesFront()
   * @generated
   * @ordered
   */
  protected EList<String> imagesFront;
  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final String ID_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected String id = ID_EDEFAULT;
  /**
   * This is true if the Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean idESet;
  /**
   * The cached value of the '{@link #getPlayers() <em>Players</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPlayers()
   * @generated
   * @ordered
   */
  protected EList<Player> players;
  /**
   * The cached value of the '{@link #getImagesFrontInside() <em>Images Front Inside</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImagesFrontInside()
   * @generated
   * @ordered
   */
  protected EList<String> imagesFrontInside;
  /**
   * The cached value of the '{@link #getImagesBack() <em>Images Back</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImagesBack()
   * @generated
   * @ordered
   */
  protected EList<String> imagesBack;
  /**
   * The cached value of the '{@link #getImagesLabel() <em>Images Label</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImagesLabel()
   * @generated
   * @ordered
   */
  protected EList<String> imagesLabel;
  /**
   * The default value of the '{@link #getDescriptionTitle() <em>Description Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescriptionTitle()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_TITLE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getDescriptionTitle() <em>Description Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescriptionTitle()
   * @generated
   * @ordered
   */
  protected String descriptionTitle = DESCRIPTION_TITLE_EDEFAULT;
  /**
   * This is true if the Description Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionTitleESet;
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
   * The cached value of the '{@link #getMyInfo() <em>My Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMyInfo()
   * @generated
   * @ordered
   */
  protected MyInfo myInfo;
  /**
   * This is true if the My Info containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean myInfoESet;

  /**
   * The default value of the '{@link #isSoundtrack() <em>Soundtrack</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSoundtrack()
   * @generated
   * @ordered
   */
  protected static final boolean SOUNDTRACK_EDEFAULT = false;
  /**
   * The cached value of the '{@link #isSoundtrack() <em>Soundtrack</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSoundtrack()
   * @generated
   * @ordered
   */
  protected boolean soundtrack = SOUNDTRACK_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AlbumImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.ALBUM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__TITLE, oldTitle, TITLE_EDEFAULT,
          oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getReleaseDate() {
    return releaseDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setReleaseDate(FlexDate newReleaseDate) {
    FlexDate oldReleaseDate = releaseDate;
    releaseDate = newReleaseDate;
    boolean oldReleaseDateESet = releaseDateESet;
    releaseDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__RELEASE_DATE, oldReleaseDate,
          releaseDate, !oldReleaseDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetReleaseDate() {
    FlexDate oldReleaseDate = releaseDate;
    boolean oldReleaseDateESet = releaseDateESet;
    releaseDate = RELEASE_DATE_EDEFAULT;
    releaseDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__RELEASE_DATE, oldReleaseDate,
          RELEASE_DATE_EDEFAULT, oldReleaseDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetReleaseDate() {
    return releaseDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Disc> getDiscs() {
    if (discs == null) {
      discs = new EObjectContainmentEList<Disc>(Disc.class, this, MediadbPackage.ALBUM__DISCS);
    }
    return discs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Artist getArtist() {
    if (artist != null && artist.eIsProxy()) {
      InternalEObject oldArtist = (InternalEObject) artist;
      artist = (Artist) eResolveProxy(oldArtist);
      if (artist != oldArtist) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.ALBUM__ARTIST, oldArtist, artist));
      }
    }
    return artist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Artist basicGetArtist() {
    return artist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setArtist(Artist newArtist) {
    Artist oldArtist = artist;
    artist = newArtist;
    boolean oldArtistESet = artistESet;
    artistESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__ARTIST, oldArtist, artist,
          !oldArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetArtist() {
    Artist oldArtist = artist;
    boolean oldArtistESet = artistESet;
    artist = null;
    artistESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__ARTIST, oldArtist, null,
          oldArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetArtist() {
    return artistESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getImagesFront() {
    if (imagesFront == null) {
      imagesFront = new EDataTypeUniqueEList.Unsettable<String>(String.class, this, MediadbPackage.ALBUM__IMAGES_FRONT);
    }
    return imagesFront;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetImagesFront() {
    if (imagesFront != null)
      ((InternalEList.Unsettable<?>) imagesFront).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetImagesFront() {
    return imagesFront != null && ((InternalEList.Unsettable<?>) imagesFront).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setId(String newId) {
    String oldId = id;
    id = newId;
    boolean oldIdESet = idESet;
    idESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__ID, oldId, id, !oldIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetId() {
    String oldId = id;
    boolean oldIdESet = idESet;
    id = ID_EDEFAULT;
    idESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__ID, oldId, ID_EDEFAULT, oldIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetId() {
    return idESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Player> getPlayers() {
    if (players == null) {
      players = new EObjectContainmentEList.Unsettable<Player>(Player.class, this, MediadbPackage.ALBUM__PLAYERS);
    }
    return players;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPlayers() {
    if (players != null)
      ((InternalEList.Unsettable<?>) players).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPlayers() {
    return players != null && ((InternalEList.Unsettable<?>) players).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getImagesFrontInside() {
    if (imagesFrontInside == null) {
      imagesFrontInside = new EDataTypeUniqueEList.Unsettable<String>(String.class, this,
          MediadbPackage.ALBUM__IMAGES_FRONT_INSIDE);
    }
    return imagesFrontInside;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetImagesFrontInside() {
    if (imagesFrontInside != null)
      ((InternalEList.Unsettable<?>) imagesFrontInside).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetImagesFrontInside() {
    return imagesFrontInside != null && ((InternalEList.Unsettable<?>) imagesFrontInside).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getImagesBack() {
    if (imagesBack == null) {
      imagesBack = new EDataTypeUniqueEList.Unsettable<String>(String.class, this, MediadbPackage.ALBUM__IMAGES_BACK);
    }
    return imagesBack;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetImagesBack() {
    if (imagesBack != null)
      ((InternalEList.Unsettable<?>) imagesBack).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetImagesBack() {
    return imagesBack != null && ((InternalEList.Unsettable<?>) imagesBack).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getImagesLabel() {
    if (imagesLabel == null) {
      imagesLabel = new EDataTypeUniqueEList.Unsettable<String>(String.class, this, MediadbPackage.ALBUM__IMAGES_LABEL);
    }
    return imagesLabel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetImagesLabel() {
    if (imagesLabel != null)
      ((InternalEList.Unsettable<?>) imagesLabel).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetImagesLabel() {
    return imagesLabel != null && ((InternalEList.Unsettable<?>) imagesLabel).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getDescriptionTitle() {
    return descriptionTitle;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescriptionTitle(String newDescriptionTitle) {
    String oldDescriptionTitle = descriptionTitle;
    descriptionTitle = newDescriptionTitle;
    boolean oldDescriptionTitleESet = descriptionTitleESet;
    descriptionTitleESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__DESCRIPTION_TITLE,
          oldDescriptionTitle, descriptionTitle, !oldDescriptionTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescriptionTitle() {
    String oldDescriptionTitle = descriptionTitle;
    boolean oldDescriptionTitleESet = descriptionTitleESet;
    descriptionTitle = DESCRIPTION_TITLE_EDEFAULT;
    descriptionTitleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__DESCRIPTION_TITLE,
          oldDescriptionTitle, DESCRIPTION_TITLE_EDEFAULT, oldDescriptionTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescriptionTitle() {
    return descriptionTitleESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__DESCRIPTION, oldDescription,
          description, !oldDescriptionESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__DESCRIPTION, oldDescription,
          DESCRIPTION_EDEFAULT, oldDescriptionESet));
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
  public MyInfo getMyInfo() {
    return myInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMyInfo(MyInfo newMyInfo, NotificationChain msgs) {
    MyInfo oldMyInfo = myInfo;
    myInfo = newMyInfo;
    boolean oldMyInfoESet = myInfoESet;
    myInfoESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__MY_INFO,
          oldMyInfo, newMyInfo, !oldMyInfoESet);
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
  public void setMyInfo(MyInfo newMyInfo) {
    if (newMyInfo != myInfo) {
      NotificationChain msgs = null;
      if (myInfo != null)
        msgs = ((InternalEObject) myInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ALBUM__MY_INFO,
            null, msgs);
      if (newMyInfo != null)
        msgs = ((InternalEObject) newMyInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ALBUM__MY_INFO,
            null, msgs);
      msgs = basicSetMyInfo(newMyInfo, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldMyInfoESet = myInfoESet;
      myInfoESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__MY_INFO, newMyInfo, newMyInfo,
            !oldMyInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetMyInfo(NotificationChain msgs) {
    MyInfo oldMyInfo = myInfo;
    myInfo = null;
    boolean oldMyInfoESet = myInfoESet;
    myInfoESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__MY_INFO,
          oldMyInfo, null, oldMyInfoESet);
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
  public void unsetMyInfo() {
    if (myInfo != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) myInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MediadbPackage.ALBUM__MY_INFO,
          null, msgs);
      msgs = basicUnsetMyInfo(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldMyInfoESet = myInfoESet;
      myInfoESet = false;
      if (eNotificationRequired())
        eNotify(
            new ENotificationImpl(this, Notification.UNSET, MediadbPackage.ALBUM__MY_INFO, null, null, oldMyInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMyInfo() {
    return myInfoESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSoundtrack() {
    return soundtrack;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSoundtrack(boolean newSoundtrack) {
    boolean oldSoundtrack = soundtrack;
    soundtrack = newSoundtrack;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.ALBUM__SOUNDTRACK, oldSoundtrack, soundtrack));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public TrackReference getTrackReference(Integer discNr, int trackNr) {
    if (discNr == null) {
      discNr = 1;
    }
    Disc disc = getDiscs().get(discNr - 1);
    int nrOfTracks = disc.getTrackReferences().size();
    if (trackNr >= nrOfTracks) {
      return null;
    }
    return disc.getTrackReferences().get(trackNr - 1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Player getPlayer(Artist artist) {
    for (Player player : getPlayers()) {
      if (player.getArtist().equals(artist)) {
        return player;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getArtistAndTitle() {
    StringBuilder buf = new StringBuilder();
    if (getArtist() != null) {
      Artist artist = getArtist();
      buf.append(artist.getName());
    } else {
      buf.append("<no artist>");
    }

    buf.append(" - ");

    if (isSetTitle()) {
      buf.append(getTitle());
    } else {
      buf.append("<no title>");
    }

    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean isMultiDiscAlbum() {
    return getDiscs().size() > 1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Disc getDisc() {
    if (isMultiDiscAlbum()) {
      throw new RuntimeException("This method may not be called on a multi disc album");
    }
    List<Disc> discs = getDiscs();
    if (discs.isEmpty()) {
      return null;
    } else {
      return discs.get(0);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean iWantAlbumOrTracksOfAlbum() {

    if ((getMyInfo() != null) && (getMyInfo().getIWant() == IWant.YES)) {
      return true;
    }

    for (Disc disc : getDiscs()) {
      for (TrackReference trackReference : disc.getTrackReferences()) {
        if (trackReference.getMyTrackInfo() != null && trackReference.getMyTrackInfo().getIWant() == IWant.YES) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean iHaveToJudgeAlbumOrTracks() {

    if ((getMyInfo() != null) && (getMyInfo().getIWant() == IWant.DONT_KNOW)) {
      return true;
    }

    for (Disc disc : getDiscs()) {
      for (TrackReference trackReference : disc.getTrackReferences()) {
        if (trackReference.getMyTrackInfo() != null && trackReference.getMyTrackInfo().getIWant() == IWant.DONT_KNOW) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.ALBUM__DISCS:
      return ((InternalEList<?>) getDiscs()).basicRemove(otherEnd, msgs);
    case MediadbPackage.ALBUM__PLAYERS:
      return ((InternalEList<?>) getPlayers()).basicRemove(otherEnd, msgs);
    case MediadbPackage.ALBUM__MY_INFO:
      return basicUnsetMyInfo(msgs);
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
    case MediadbPackage.ALBUM__TITLE:
      return getTitle();
    case MediadbPackage.ALBUM__RELEASE_DATE:
      return getReleaseDate();
    case MediadbPackage.ALBUM__DISCS:
      return getDiscs();
    case MediadbPackage.ALBUM__ARTIST:
      if (resolve)
        return getArtist();
      return basicGetArtist();
    case MediadbPackage.ALBUM__IMAGES_FRONT:
      return getImagesFront();
    case MediadbPackage.ALBUM__ID:
      return getId();
    case MediadbPackage.ALBUM__PLAYERS:
      return getPlayers();
    case MediadbPackage.ALBUM__IMAGES_FRONT_INSIDE:
      return getImagesFrontInside();
    case MediadbPackage.ALBUM__IMAGES_BACK:
      return getImagesBack();
    case MediadbPackage.ALBUM__IMAGES_LABEL:
      return getImagesLabel();
    case MediadbPackage.ALBUM__DESCRIPTION_TITLE:
      return getDescriptionTitle();
    case MediadbPackage.ALBUM__DESCRIPTION:
      return getDescription();
    case MediadbPackage.ALBUM__MY_INFO:
      return getMyInfo();
    case MediadbPackage.ALBUM__SOUNDTRACK:
      return isSoundtrack();
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
    case MediadbPackage.ALBUM__TITLE:
      setTitle((String) newValue);
      return;
    case MediadbPackage.ALBUM__RELEASE_DATE:
      setReleaseDate((FlexDate) newValue);
      return;
    case MediadbPackage.ALBUM__DISCS:
      getDiscs().clear();
      getDiscs().addAll((Collection<? extends Disc>) newValue);
      return;
    case MediadbPackage.ALBUM__ARTIST:
      setArtist((Artist) newValue);
      return;
    case MediadbPackage.ALBUM__IMAGES_FRONT:
      getImagesFront().clear();
      getImagesFront().addAll((Collection<? extends String>) newValue);
      return;
    case MediadbPackage.ALBUM__ID:
      setId((String) newValue);
      return;
    case MediadbPackage.ALBUM__PLAYERS:
      getPlayers().clear();
      getPlayers().addAll((Collection<? extends Player>) newValue);
      return;
    case MediadbPackage.ALBUM__IMAGES_FRONT_INSIDE:
      getImagesFrontInside().clear();
      getImagesFrontInside().addAll((Collection<? extends String>) newValue);
      return;
    case MediadbPackage.ALBUM__IMAGES_BACK:
      getImagesBack().clear();
      getImagesBack().addAll((Collection<? extends String>) newValue);
      return;
    case MediadbPackage.ALBUM__IMAGES_LABEL:
      getImagesLabel().clear();
      getImagesLabel().addAll((Collection<? extends String>) newValue);
      return;
    case MediadbPackage.ALBUM__DESCRIPTION_TITLE:
      setDescriptionTitle((String) newValue);
      return;
    case MediadbPackage.ALBUM__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case MediadbPackage.ALBUM__MY_INFO:
      setMyInfo((MyInfo) newValue);
      return;
    case MediadbPackage.ALBUM__SOUNDTRACK:
      setSoundtrack((Boolean) newValue);
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
    case MediadbPackage.ALBUM__TITLE:
      unsetTitle();
      return;
    case MediadbPackage.ALBUM__RELEASE_DATE:
      unsetReleaseDate();
      return;
    case MediadbPackage.ALBUM__DISCS:
      getDiscs().clear();
      return;
    case MediadbPackage.ALBUM__ARTIST:
      unsetArtist();
      return;
    case MediadbPackage.ALBUM__IMAGES_FRONT:
      unsetImagesFront();
      return;
    case MediadbPackage.ALBUM__ID:
      unsetId();
      return;
    case MediadbPackage.ALBUM__PLAYERS:
      unsetPlayers();
      return;
    case MediadbPackage.ALBUM__IMAGES_FRONT_INSIDE:
      unsetImagesFrontInside();
      return;
    case MediadbPackage.ALBUM__IMAGES_BACK:
      unsetImagesBack();
      return;
    case MediadbPackage.ALBUM__IMAGES_LABEL:
      unsetImagesLabel();
      return;
    case MediadbPackage.ALBUM__DESCRIPTION_TITLE:
      unsetDescriptionTitle();
      return;
    case MediadbPackage.ALBUM__DESCRIPTION:
      unsetDescription();
      return;
    case MediadbPackage.ALBUM__MY_INFO:
      unsetMyInfo();
      return;
    case MediadbPackage.ALBUM__SOUNDTRACK:
      setSoundtrack(SOUNDTRACK_EDEFAULT);
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
    case MediadbPackage.ALBUM__TITLE:
      return isSetTitle();
    case MediadbPackage.ALBUM__RELEASE_DATE:
      return isSetReleaseDate();
    case MediadbPackage.ALBUM__DISCS:
      return discs != null && !discs.isEmpty();
    case MediadbPackage.ALBUM__ARTIST:
      return isSetArtist();
    case MediadbPackage.ALBUM__IMAGES_FRONT:
      return isSetImagesFront();
    case MediadbPackage.ALBUM__ID:
      return isSetId();
    case MediadbPackage.ALBUM__PLAYERS:
      return isSetPlayers();
    case MediadbPackage.ALBUM__IMAGES_FRONT_INSIDE:
      return isSetImagesFrontInside();
    case MediadbPackage.ALBUM__IMAGES_BACK:
      return isSetImagesBack();
    case MediadbPackage.ALBUM__IMAGES_LABEL:
      return isSetImagesLabel();
    case MediadbPackage.ALBUM__DESCRIPTION_TITLE:
      return isSetDescriptionTitle();
    case MediadbPackage.ALBUM__DESCRIPTION:
      return isSetDescription();
    case MediadbPackage.ALBUM__MY_INFO:
      return isSetMyInfo();
    case MediadbPackage.ALBUM__SOUNDTRACK:
      return soundtrack != SOUNDTRACK_EDEFAULT;
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
    case MediadbPackage.ALBUM___GET_TRACK_REFERENCE__INTEGER_INT:
      return getTrackReference((Integer) arguments.get(0), (Integer) arguments.get(1));
    case MediadbPackage.ALBUM___GET_PLAYER__ARTIST:
      return getPlayer((Artist) arguments.get(0));
    case MediadbPackage.ALBUM___GET_ARTIST_AND_TITLE:
      return getArtistAndTitle();
    case MediadbPackage.ALBUM___IS_MULTI_DISC_ALBUM:
      return isMultiDiscAlbum();
    case MediadbPackage.ALBUM___GET_DISC:
      return getDisc();
    case MediadbPackage.ALBUM___IWANT_ALBUM_OR_TRACKS_OF_ALBUM:
      return iWantAlbumOrTracksOfAlbum();
    case MediadbPackage.ALBUM___IHAVE_TO_JUDGE_ALBUM_OR_TRACKS:
      return iHaveToJudgeAlbumOrTracks();
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("Artist: ");
    if (isSetArtist()) {
      Artist artist = getArtist();
      buf.append(artist != null ? artist.getName() : "<no-artist>");
    } else {
      buf.append("<no-artist>");
    }
    buf.append(NEWLINE);

    buf.append("Title: ").append(isSetTitle() ? getTitle() : "<no-title>").append(NEWLINE);

    buf.append("Id: ").append(isSetId() ? getId() : "<no-id>").append(NEWLINE);

    buf.append("Release date: ").append(isSetReleaseDate() ? FDF.format(getReleaseDate()) : "<no-release-date>")
        .append(NEWLINE);

    buf.append("Images front: ").append(StringUtil.stringCollectionToCommaSeparatedStrings(getImagesFront()))
        .append(NEWLINE);
    buf.append("Images front inside: ")
        .append(StringUtil.stringCollectionToCommaSeparatedStrings(getImagesFrontInside())).append(NEWLINE);
    buf.append("Images back: ").append(StringUtil.stringCollectionToCommaSeparatedStrings(getImagesBack()))
        .append(NEWLINE);
    buf.append("Images label: ").append(StringUtil.stringCollectionToCommaSeparatedStrings(getImagesLabel()))
        .append(NEWLINE);

    buf.append("Players: ").append(StringUtil.objectCollectionToCommaSeparatedStrings(getPlayers())).append(NEWLINE);

    buf.append("Description title: ").append(isSetDescriptionTitle() ? getDescriptionTitle() : "<no-description-title>")
        .append(NEWLINE);
    buf.append("Description: ").append(isSetDescription() ? getDescription() : "<no-description>").append(NEWLINE);

    buf.append("Soundtrack: ").append(isSoundtrack() ? "yes" : "no").append(NEWLINE);

    buf.append("My info: ");
    if (getMyInfo() != null) {
      buf.append(NEWLINE).append(getMyInfo().toString());
    } else {
      buf.append("<my-info-not-set>");
    }
    buf.append(NEWLINE);

    buf.append("Discs: ").append(NEWLINE);
    for (Disc disc : getDiscs()) {
      buf.append(disc.toString());
    }

    return buf.toString();
  }

} //AlbumImpl
