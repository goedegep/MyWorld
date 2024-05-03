/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * D
 * <!-- end-model-doc -->
 * @see goedegep.media.mediadb.model.MediadbFactory
 * @model kind="package"
 * @generated
 */
public interface MediadbPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://goedegep.media/mediadb/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "mediadb";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MediadbPackage eINSTANCE = goedegep.media.mediadb.model.impl.MediadbPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.TrackImpl <em>Track</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.TrackImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrack()
   * @generated
   */
  int TRACK = 0;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__TITLE = 0;

  /**
   * The feature id for the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__DURATION = 1;

  /**
   * The feature id for the '<em><b>Players</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__PLAYERS = 2;

  /**
   * The feature id for the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__ARTIST = 3;

  /**
   * The feature id for the '<em><b>Parts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__PARTS = 4;

  /**
   * The feature id for the '<em><b>Authors</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__AUTHORS = 5;

  /**
   * The feature id for the '<em><b>Referred By</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__REFERRED_BY = 6;

  /**
   * The feature id for the '<em><b>Original Disc</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK__ORIGINAL_DISC = 7;

  /**
   * The number of structural features of the '<em>Track</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_FEATURE_COUNT = 8;

  /**
   * The operation id for the '<em>Get Original Disc Track Reference</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK___GET_ORIGINAL_DISC_TRACK_REFERENCE = 0;

  /**
   * The number of operations of the '<em>Track</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.MediaDbImpl <em>Media Db</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.MediaDbImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediaDb()
   * @generated
   */
  int MEDIA_DB = 1;

  /**
   * The feature id for the '<em><b>Artists</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB__ARTISTS = 0;

  /**
   * The feature id for the '<em><b>Albums</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB__ALBUMS = 1;

  /**
   * The feature id for the '<em><b>Tracks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB__TRACKS = 2;

  /**
   * The feature id for the '<em><b>Trackcollections</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB__TRACKCOLLECTIONS = 3;

  /**
   * The feature id for the '<em><b>Videos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB__VIDEOS = 4;

  /**
   * The number of structural features of the '<em>Media Db</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB_FEATURE_COUNT = 5;

  /**
   * The operation id for the '<em>Get Artist</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB___GET_ARTIST__STRING = 0;

  /**
   * The operation id for the '<em>Get Album</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB___GET_ALBUM__FLEXDATE_ARTIST_STRING = 1;

  /**
   * The operation id for the '<em>Get Albums</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB___GET_ALBUMS__FLEXDATE_ARTIST_STRING = 2;

  /**
   * The operation id for the '<em>Get Track Collection</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB___GET_TRACK_COLLECTION__COLLECTION = 3;

  /**
   * The operation id for the '<em>Get Track</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB___GET_TRACK__ARTIST_STRING = 4;

  /**
   * The number of operations of the '<em>Media Db</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIA_DB_OPERATION_COUNT = 5;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.ArtistImpl <em>Artist</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.ArtistImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getArtist()
   * @generated
   */
  int ARTIST = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__NAME = 0;

  /**
   * The feature id for the '<em><b>Container Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__CONTAINER_ARTIST = 1;

  /**
   * The feature id for the '<em><b>Photo</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__PHOTO = 2;

  /**
   * The feature id for the '<em><b>Style</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__STYLE = 3;

  /**
   * The feature id for the '<em><b>My Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__MY_COMMENTS = 4;

  /**
   * The feature id for the '<em><b>Sample</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST__SAMPLE = 5;

  /**
   * The number of structural features of the '<em>Artist</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST_FEATURE_COUNT = 6;

  /**
   * The number of operations of the '<em>Artist</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.AlbumImpl <em>Album</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.AlbumImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getAlbum()
   * @generated
   */
  int ALBUM = 3;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__TITLE = 0;

  /**
   * The feature id for the '<em><b>Release Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__RELEASE_DATE = 1;

  /**
   * The feature id for the '<em><b>Discs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__DISCS = 2;

  /**
   * The feature id for the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__ARTIST = 3;

  /**
   * The feature id for the '<em><b>Images Front</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__IMAGES_FRONT = 4;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__ID = 5;

  /**
   * The feature id for the '<em><b>Players</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__PLAYERS = 6;

  /**
   * The feature id for the '<em><b>Images Front Inside</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__IMAGES_FRONT_INSIDE = 7;

  /**
   * The feature id for the '<em><b>Images Back</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__IMAGES_BACK = 8;

  /**
   * The feature id for the '<em><b>Images Label</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__IMAGES_LABEL = 9;

  /**
   * The feature id for the '<em><b>Description Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__DESCRIPTION_TITLE = 10;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__DESCRIPTION = 11;

  /**
   * The feature id for the '<em><b>Issued On Mediums</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__ISSUED_ON_MEDIUMS = 12;

  /**
   * The feature id for the '<em><b>Compilation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__COMPILATION = 13;

  /**
   * The feature id for the '<em><b>My Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__MY_INFO = 14;

  /**
   * The feature id for the '<em><b>Soundtrack</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM__SOUNDTRACK = 15;

  /**
   * The number of structural features of the '<em>Album</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM_FEATURE_COUNT = 16;

  /**
   * The operation id for the '<em>Get Track Reference</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___GET_TRACK_REFERENCE__INTEGER_INT = 0;

  /**
   * The operation id for the '<em>Get Player</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___GET_PLAYER__ARTIST = 1;

  /**
   * The operation id for the '<em>Get Artist And Title</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___GET_ARTIST_AND_TITLE = 2;

  /**
   * The operation id for the '<em>Is Multi Disc Album</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___IS_MULTI_DISC_ALBUM = 3;

  /**
   * The operation id for the '<em>Get Disc</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___GET_DISC = 4;

  /**
   * The operation id for the '<em>IWant Album Or Tracks Of Album</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___IWANT_ALBUM_OR_TRACKS_OF_ALBUM = 5;

  /**
   * The operation id for the '<em>IHave To Judge Album Or Tracks</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM___IHAVE_TO_JUDGE_ALBUM_OR_TRACKS = 6;

  /**
   * The number of operations of the '<em>Album</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALBUM_OPERATION_COUNT = 7;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl <em>Track Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.TrackReferenceImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackReference()
   * @generated
   */
  int TRACK_REFERENCE = 4;

  /**
   * The feature id for the '<em><b>Track</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE__TRACK = 0;

  /**
   * The feature id for the '<em><b>Bonus Track</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE__BONUS_TRACK = 1;

  /**
   * The feature id for the '<em><b>My Track Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE__MY_TRACK_INFO = 2;

  /**
   * The feature id for the '<em><b>Original Album Track Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE = 3;

  /**
   * The number of structural features of the '<em>Track Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE_FEATURE_COUNT = 4;

  /**
   * The operation id for the '<em>Get Track Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE___GET_TRACK_NR = 0;

  /**
   * The operation id for the '<em>Get Disc</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE___GET_DISC = 1;

  /**
   * The number of operations of the '<em>Track Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_REFERENCE_OPERATION_COUNT = 2;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.DiscImpl <em>Disc</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.DiscImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getDisc()
   * @generated
   */
  int DISC = 5;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC__TITLE = 0;

  /**
   * The feature id for the '<em><b>Track References</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC__TRACK_REFERENCES = 1;

  /**
   * The number of structural features of the '<em>Disc</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_FEATURE_COUNT = 2;

  /**
   * The operation id for the '<em>Get Album</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC___GET_ALBUM = 0;

  /**
   * The operation id for the '<em>Get Disc Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC___GET_DISC_NR = 1;

  /**
   * The number of operations of the '<em>Disc</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_OPERATION_COUNT = 2;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.MediumInfoImpl <em>Medium Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.MediumInfoImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediumInfo()
   * @generated
   */
  int MEDIUM_INFO = 6;

  /**
   * The feature id for the '<em><b>Medium Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO__MEDIUM_TYPE = 0;

  /**
   * The feature id for the '<em><b>Information Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO__INFORMATION_TYPE = 1;

  /**
   * The feature id for the '<em><b>Source Types</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO__SOURCE_TYPES = 2;

  /**
   * The feature id for the '<em><b>Source Bit Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO__SOURCE_BIT_RATE = 3;

  /**
   * The number of structural features of the '<em>Medium Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Medium Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEDIUM_INFO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.MyInfoImpl <em>My Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.MyInfoImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMyInfo()
   * @generated
   */
  int MY_INFO = 7;

  /**
   * The feature id for the '<em><b>Album References</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__ALBUM_REFERENCES = 0;

  /**
   * The feature id for the '<em><b>My Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__MY_COMMENTS = 1;

  /**
   * The feature id for the '<em><b>Ive Had On LP</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__IVE_HAD_ON_LP = 2;

  /**
   * The feature id for the '<em><b>IWant</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__IWANT = 3;

  /**
   * The feature id for the '<em><b>IHave On</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__IHAVE_ON = 4;

  /**
   * The feature id for the '<em><b>Album Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO__ALBUM_TYPE = 5;

  /**
   * The number of structural features of the '<em>My Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO_FEATURE_COUNT = 6;

  /**
   * The number of operations of the '<em>My Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_INFO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.PlayerImpl <em>Player</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.PlayerImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getPlayer()
   * @generated
   */
  int PLAYER = 8;

  /**
   * The feature id for the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PLAYER__ARTIST = 0;

  /**
   * The feature id for the '<em><b>Instruments</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PLAYER__INSTRUMENTS = 1;

  /**
   * The number of structural features of the '<em>Player</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PLAYER_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Player</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PLAYER_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.TrackPartImpl <em>Track Part</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.TrackPartImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackPart()
   * @generated
   */
  int TRACK_PART = 9;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_PART__TITLE = 0;

  /**
   * The number of structural features of the '<em>Track Part</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_PART_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Track Part</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_PART_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl <em>My Track Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.MyTrackInfoImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMyTrackInfo()
   * @generated
   */
  int MY_TRACK_INFO = 10;

  /**
   * The feature id for the '<em><b>Collection</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO__COLLECTION = 0;

  /**
   * The feature id for the '<em><b>IHave On</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO__IHAVE_ON = 1;

  /**
   * The feature id for the '<em><b>IWant</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO__IWANT = 2;

  /**
   * The feature id for the '<em><b>Compilation Track Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE = 3;

  /**
   * The number of structural features of the '<em>My Track Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>My Track Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MY_TRACK_INFO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl <em>Disc And Track Nrs</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getDiscAndTrackNrs()
   * @generated
   */
  int DISC_AND_TRACK_NRS = 11;

  /**
   * The feature id for the '<em><b>Disc Nr</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_AND_TRACK_NRS__DISC_NR = 0;

  /**
   * The feature id for the '<em><b>Track Nrs</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_AND_TRACK_NRS__TRACK_NRS = 1;

  /**
   * The number of structural features of the '<em>Disc And Track Nrs</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_AND_TRACK_NRS_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Disc And Track Nrs</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_AND_TRACK_NRS_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.TrackCollectionImpl <em>Track Collection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.TrackCollectionImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackCollection()
   * @generated
   */
  int TRACK_COLLECTION = 12;

  /**
   * The feature id for the '<em><b>Collection</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_COLLECTION__COLLECTION = 0;

  /**
   * The feature id for the '<em><b>Track References</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_COLLECTION__TRACK_REFERENCES = 1;

  /**
   * The number of structural features of the '<em>Track Collection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_COLLECTION_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Track Collection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACK_COLLECTION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.VideoImpl <em>Video</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.VideoImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getVideo()
   * @generated
   */
  int VIDEO = 13;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO__TITLE = 0;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO__DATE = 1;

  /**
   * The feature id for the '<em><b>Image</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO__IMAGE = 2;

  /**
   * The feature id for the '<em><b>Subjects</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO__SUBJECTS = 3;

  /**
   * The number of structural features of the '<em>Video</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Video</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIDEO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.impl.SubjectImpl <em>Subject</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.impl.SubjectImpl
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getSubject()
   * @generated
   */
  int SUBJECT = 14;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBJECT__TITLE = 0;

  /**
   * The feature id for the '<em><b>Tags</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBJECT__TAGS = 1;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBJECT__DATE = 2;

  /**
   * The number of structural features of the '<em>Subject</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBJECT_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Subject</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBJECT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.MediumType <em>Medium Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.MediumType
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediumType()
   * @generated
   */
  int MEDIUM_TYPE = 15;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.IWant <em>IWant</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.IWant
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getIWant()
   * @generated
   */
  int IWANT = 16;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.InformationType <em>Information Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.InformationType
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getInformationType()
   * @generated
   */
  int INFORMATION_TYPE = 17;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.Collection <em>Collection</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.Collection
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getCollection()
   * @generated
   */
  int COLLECTION = 18;

  /**
   * The meta object id for the '{@link goedegep.media.mediadb.model.AlbumType <em>Album Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.mediadb.model.AlbumType
   * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getAlbumType()
   * @generated
   */
  int ALBUM_TYPE = 19;

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Track <em>Track</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Track</em>'.
   * @see goedegep.media.mediadb.model.Track
   * @generated
   */
  EClass getTrack();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Track#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.Track#getTitle()
   * @see #getTrack()
   * @generated
   */
  EAttribute getTrack_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Track#getDuration <em>Duration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Duration</em>'.
   * @see goedegep.media.mediadb.model.Track#getDuration()
   * @see #getTrack()
   * @generated
   */
  EAttribute getTrack_Duration();

  /**
   * Returns the meta object for the reference list '{@link goedegep.media.mediadb.model.Track#getPlayers <em>Players</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Players</em>'.
   * @see goedegep.media.mediadb.model.Track#getPlayers()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_Players();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.Track#getArtist <em>Artist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Artist</em>'.
   * @see goedegep.media.mediadb.model.Track#getArtist()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_Artist();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.Track#getParts <em>Parts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parts</em>'.
   * @see goedegep.media.mediadb.model.Track#getParts()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_Parts();

  /**
   * Returns the meta object for the reference list '{@link goedegep.media.mediadb.model.Track#getAuthors <em>Authors</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Authors</em>'.
   * @see goedegep.media.mediadb.model.Track#getAuthors()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_Authors();

  /**
   * Returns the meta object for the reference list '{@link goedegep.media.mediadb.model.Track#getReferredBy <em>Referred By</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Referred By</em>'.
   * @see goedegep.media.mediadb.model.Track#getReferredBy()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_ReferredBy();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.Track#getOriginalDisc <em>Original Disc</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Original Disc</em>'.
   * @see goedegep.media.mediadb.model.Track#getOriginalDisc()
   * @see #getTrack()
   * @generated
   */
  EReference getTrack_OriginalDisc();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Track#getOriginalDiscTrackReference() <em>Get Original Disc Track Reference</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Original Disc Track Reference</em>' operation.
   * @see goedegep.media.mediadb.model.Track#getOriginalDiscTrackReference()
   * @generated
   */
  EOperation getTrack__GetOriginalDiscTrackReference();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.MediaDb <em>Media Db</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Media Db</em>'.
   * @see goedegep.media.mediadb.model.MediaDb
   * @generated
   */
  EClass getMediaDb();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MediaDb#getArtists <em>Artists</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Artists</em>'.
   * @see goedegep.media.mediadb.model.MediaDb#getArtists()
   * @see #getMediaDb()
   * @generated
   */
  EReference getMediaDb_Artists();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MediaDb#getAlbums <em>Albums</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Albums</em>'.
   * @see goedegep.media.mediadb.model.MediaDb#getAlbums()
   * @see #getMediaDb()
   * @generated
   */
  EReference getMediaDb_Albums();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MediaDb#getTracks <em>Tracks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Tracks</em>'.
   * @see goedegep.media.mediadb.model.MediaDb#getTracks()
   * @see #getMediaDb()
   * @generated
   */
  EReference getMediaDb_Tracks();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MediaDb#getTrackcollections <em>Trackcollections</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Trackcollections</em>'.
   * @see goedegep.media.mediadb.model.MediaDb#getTrackcollections()
   * @see #getMediaDb()
   * @generated
   */
  EReference getMediaDb_Trackcollections();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MediaDb#getVideos <em>Videos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Videos</em>'.
   * @see goedegep.media.mediadb.model.MediaDb#getVideos()
   * @see #getMediaDb()
   * @generated
   */
  EReference getMediaDb_Videos();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.MediaDb#getArtist(java.lang.String) <em>Get Artist</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Artist</em>' operation.
   * @see goedegep.media.mediadb.model.MediaDb#getArtist(java.lang.String)
   * @generated
   */
  EOperation getMediaDb__GetArtist__String();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.MediaDb#getAlbum(goedegep.util.datetime.FlexDate, goedegep.media.mediadb.model.Artist, java.lang.String) <em>Get Album</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Album</em>' operation.
   * @see goedegep.media.mediadb.model.MediaDb#getAlbum(goedegep.util.datetime.FlexDate, goedegep.media.mediadb.model.Artist, java.lang.String)
   * @generated
   */
  EOperation getMediaDb__GetAlbum__FlexDate_Artist_String();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.MediaDb#getAlbums(goedegep.util.datetime.FlexDate, goedegep.media.mediadb.model.Artist, java.lang.String) <em>Get Albums</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Albums</em>' operation.
   * @see goedegep.media.mediadb.model.MediaDb#getAlbums(goedegep.util.datetime.FlexDate, goedegep.media.mediadb.model.Artist, java.lang.String)
   * @generated
   */
  EOperation getMediaDb__GetAlbums__FlexDate_Artist_String();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.MediaDb#getTrackCollection(goedegep.media.mediadb.model.Collection) <em>Get Track Collection</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Track Collection</em>' operation.
   * @see goedegep.media.mediadb.model.MediaDb#getTrackCollection(goedegep.media.mediadb.model.Collection)
   * @generated
   */
  EOperation getMediaDb__GetTrackCollection__Collection();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.MediaDb#getTrack(goedegep.media.mediadb.model.Artist, java.lang.String) <em>Get Track</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Track</em>' operation.
   * @see goedegep.media.mediadb.model.MediaDb#getTrack(goedegep.media.mediadb.model.Artist, java.lang.String)
   * @generated
   */
  EOperation getMediaDb__GetTrack__Artist_String();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Artist <em>Artist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Artist</em>'.
   * @see goedegep.media.mediadb.model.Artist
   * @generated
   */
  EClass getArtist();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.media.mediadb.model.Artist#getName()
   * @see #getArtist()
   * @generated
   */
  EAttribute getArtist_Name();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Container Artist</em>'.
   * @see goedegep.media.mediadb.model.Artist#getContainerArtist()
   * @see #getArtist()
   * @generated
   */
  EReference getArtist_ContainerArtist();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Artist#getPhoto <em>Photo</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Photo</em>'.
   * @see goedegep.media.mediadb.model.Artist#getPhoto()
   * @see #getArtist()
   * @generated
   */
  EAttribute getArtist_Photo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Artist#getStyle <em>Style</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Style</em>'.
   * @see goedegep.media.mediadb.model.Artist#getStyle()
   * @see #getArtist()
   * @generated
   */
  EAttribute getArtist_Style();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Artist#getMyComments <em>My Comments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>My Comments</em>'.
   * @see goedegep.media.mediadb.model.Artist#getMyComments()
   * @see #getArtist()
   * @generated
   */
  EAttribute getArtist_MyComments();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.media.mediadb.model.Artist#getSample <em>Sample</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Sample</em>'.
   * @see goedegep.media.mediadb.model.Artist#getSample()
   * @see #getArtist()
   * @generated
   */
  EReference getArtist_Sample();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Album <em>Album</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Album</em>'.
   * @see goedegep.media.mediadb.model.Album
   * @generated
   */
  EClass getAlbum();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.Album#getTitle()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#getReleaseDate <em>Release Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Release Date</em>'.
   * @see goedegep.media.mediadb.model.Album#getReleaseDate()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_ReleaseDate();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.Album#getDiscs <em>Discs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Discs</em>'.
   * @see goedegep.media.mediadb.model.Album#getDiscs()
   * @see #getAlbum()
   * @generated
   */
  EReference getAlbum_Discs();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.Album#getArtist <em>Artist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Artist</em>'.
   * @see goedegep.media.mediadb.model.Album#getArtist()
   * @see #getAlbum()
   * @generated
   */
  EReference getAlbum_Artist();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Album#getImagesFront <em>Images Front</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Images Front</em>'.
   * @see goedegep.media.mediadb.model.Album#getImagesFront()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_ImagesFront();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see goedegep.media.mediadb.model.Album#getId()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_Id();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.Album#getPlayers <em>Players</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Players</em>'.
   * @see goedegep.media.mediadb.model.Album#getPlayers()
   * @see #getAlbum()
   * @generated
   */
  EReference getAlbum_Players();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Album#getImagesFrontInside <em>Images Front Inside</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Images Front Inside</em>'.
   * @see goedegep.media.mediadb.model.Album#getImagesFrontInside()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_ImagesFrontInside();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Album#getImagesBack <em>Images Back</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Images Back</em>'.
   * @see goedegep.media.mediadb.model.Album#getImagesBack()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_ImagesBack();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Album#getImagesLabel <em>Images Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Images Label</em>'.
   * @see goedegep.media.mediadb.model.Album#getImagesLabel()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_ImagesLabel();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#getDescriptionTitle <em>Description Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description Title</em>'.
   * @see goedegep.media.mediadb.model.Album#getDescriptionTitle()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_DescriptionTitle();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.media.mediadb.model.Album#getDescription()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_Description();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Album#getIssuedOnMediums <em>Issued On Mediums</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Issued On Mediums</em>'.
   * @see goedegep.media.mediadb.model.Album#getIssuedOnMediums()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_IssuedOnMediums();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#isCompilation <em>Compilation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compilation</em>'.
   * @see goedegep.media.mediadb.model.Album#isCompilation()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_Compilation();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.media.mediadb.model.Album#getMyInfo <em>My Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>My Info</em>'.
   * @see goedegep.media.mediadb.model.Album#getMyInfo()
   * @see #getAlbum()
   * @generated
   */
  EReference getAlbum_MyInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Album#isSoundtrack <em>Soundtrack</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Soundtrack</em>'.
   * @see goedegep.media.mediadb.model.Album#isSoundtrack()
   * @see #getAlbum()
   * @generated
   */
  EAttribute getAlbum_Soundtrack();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#getTrackReference(java.lang.Integer, int) <em>Get Track Reference</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Track Reference</em>' operation.
   * @see goedegep.media.mediadb.model.Album#getTrackReference(java.lang.Integer, int)
   * @generated
   */
  EOperation getAlbum__GetTrackReference__Integer_int();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#getPlayer(goedegep.media.mediadb.model.Artist) <em>Get Player</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Player</em>' operation.
   * @see goedegep.media.mediadb.model.Album#getPlayer(goedegep.media.mediadb.model.Artist)
   * @generated
   */
  EOperation getAlbum__GetPlayer__Artist();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#getArtistAndTitle() <em>Get Artist And Title</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Artist And Title</em>' operation.
   * @see goedegep.media.mediadb.model.Album#getArtistAndTitle()
   * @generated
   */
  EOperation getAlbum__GetArtistAndTitle();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#isMultiDiscAlbum() <em>Is Multi Disc Album</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Is Multi Disc Album</em>' operation.
   * @see goedegep.media.mediadb.model.Album#isMultiDiscAlbum()
   * @generated
   */
  EOperation getAlbum__IsMultiDiscAlbum();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#getDisc() <em>Get Disc</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Disc</em>' operation.
   * @see goedegep.media.mediadb.model.Album#getDisc()
   * @generated
   */
  EOperation getAlbum__GetDisc();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#iWantAlbumOrTracksOfAlbum() <em>IWant Album Or Tracks Of Album</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>IWant Album Or Tracks Of Album</em>' operation.
   * @see goedegep.media.mediadb.model.Album#iWantAlbumOrTracksOfAlbum()
   * @generated
   */
  EOperation getAlbum__IWantAlbumOrTracksOfAlbum();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Album#iHaveToJudgeAlbumOrTracks() <em>IHave To Judge Album Or Tracks</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>IHave To Judge Album Or Tracks</em>' operation.
   * @see goedegep.media.mediadb.model.Album#iHaveToJudgeAlbumOrTracks()
   * @generated
   */
  EOperation getAlbum__IHaveToJudgeAlbumOrTracks();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.TrackReference <em>Track Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Track Reference</em>'.
   * @see goedegep.media.mediadb.model.TrackReference
   * @generated
   */
  EClass getTrackReference();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Track</em>'.
   * @see goedegep.media.mediadb.model.TrackReference#getTrack()
   * @see #getTrackReference()
   * @generated
   */
  EReference getTrackReference_Track();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.TrackReference#getBonusTrack <em>Bonus Track</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Bonus Track</em>'.
   * @see goedegep.media.mediadb.model.TrackReference#getBonusTrack()
   * @see #getTrackReference()
   * @generated
   */
  EAttribute getTrackReference_BonusTrack();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.media.mediadb.model.TrackReference#getMyTrackInfo <em>My Track Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>My Track Info</em>'.
   * @see goedegep.media.mediadb.model.TrackReference#getMyTrackInfo()
   * @see #getTrackReference()
   * @generated
   */
  EReference getTrackReference_MyTrackInfo();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.TrackReference#getOriginalAlbumTrackReference <em>Original Album Track Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Original Album Track Reference</em>'.
   * @see goedegep.media.mediadb.model.TrackReference#getOriginalAlbumTrackReference()
   * @see #getTrackReference()
   * @generated
   */
  EReference getTrackReference_OriginalAlbumTrackReference();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.TrackReference#getTrackNr() <em>Get Track Nr</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Track Nr</em>' operation.
   * @see goedegep.media.mediadb.model.TrackReference#getTrackNr()
   * @generated
   */
  EOperation getTrackReference__GetTrackNr();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.TrackReference#getDisc() <em>Get Disc</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Disc</em>' operation.
   * @see goedegep.media.mediadb.model.TrackReference#getDisc()
   * @generated
   */
  EOperation getTrackReference__GetDisc();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Disc <em>Disc</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Disc</em>'.
   * @see goedegep.media.mediadb.model.Disc
   * @generated
   */
  EClass getDisc();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Disc#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.Disc#getTitle()
   * @see #getDisc()
   * @generated
   */
  EAttribute getDisc_Title();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.Disc#getTrackReferences <em>Track References</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Track References</em>'.
   * @see goedegep.media.mediadb.model.Disc#getTrackReferences()
   * @see #getDisc()
   * @generated
   */
  EReference getDisc_TrackReferences();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Disc#getAlbum() <em>Get Album</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Album</em>' operation.
   * @see goedegep.media.mediadb.model.Disc#getAlbum()
   * @generated
   */
  EOperation getDisc__GetAlbum();

  /**
   * Returns the meta object for the '{@link goedegep.media.mediadb.model.Disc#getDiscNr() <em>Get Disc Nr</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Disc Nr</em>' operation.
   * @see goedegep.media.mediadb.model.Disc#getDiscNr()
   * @generated
   */
  EOperation getDisc__GetDiscNr();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.MediumInfo <em>Medium Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Medium Info</em>'.
   * @see goedegep.media.mediadb.model.MediumInfo
   * @generated
   */
  EClass getMediumInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MediumInfo#getMediumType <em>Medium Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Medium Type</em>'.
   * @see goedegep.media.mediadb.model.MediumInfo#getMediumType()
   * @see #getMediumInfo()
   * @generated
   */
  EAttribute getMediumInfo_MediumType();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MediumInfo#getInformationType <em>Information Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Information Type</em>'.
   * @see goedegep.media.mediadb.model.MediumInfo#getInformationType()
   * @see #getMediumInfo()
   * @generated
   */
  EAttribute getMediumInfo_InformationType();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.MediumInfo#getSourceTypes <em>Source Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Source Types</em>'.
   * @see goedegep.media.mediadb.model.MediumInfo#getSourceTypes()
   * @see #getMediumInfo()
   * @generated
   */
  EAttribute getMediumInfo_SourceTypes();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MediumInfo#getSourceBitRate <em>Source Bit Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Source Bit Rate</em>'.
   * @see goedegep.media.mediadb.model.MediumInfo#getSourceBitRate()
   * @see #getMediumInfo()
   * @generated
   */
  EAttribute getMediumInfo_SourceBitRate();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.MyInfo <em>My Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>My Info</em>'.
   * @see goedegep.media.mediadb.model.MyInfo
   * @generated
   */
  EClass getMyInfo();

  /**
   * Returns the meta object for the reference list '{@link goedegep.media.mediadb.model.MyInfo#getAlbumReferences <em>Album References</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Album References</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#getAlbumReferences()
   * @see #getMyInfo()
   * @generated
   */
  EReference getMyInfo_AlbumReferences();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyInfo#getMyComments <em>My Comments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>My Comments</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#getMyComments()
   * @see #getMyInfo()
   * @generated
   */
  EAttribute getMyInfo_MyComments();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyInfo#isIveHadOnLP <em>Ive Had On LP</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ive Had On LP</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#isIveHadOnLP()
   * @see #getMyInfo()
   * @generated
   */
  EAttribute getMyInfo_IveHadOnLP();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyInfo#getIWant <em>IWant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>IWant</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#getIWant()
   * @see #getMyInfo()
   * @generated
   */
  EAttribute getMyInfo_IWant();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MyInfo#getIHaveOn <em>IHave On</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>IHave On</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#getIHaveOn()
   * @see #getMyInfo()
   * @generated
   */
  EReference getMyInfo_IHaveOn();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyInfo#getAlbumType <em>Album Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Album Type</em>'.
   * @see goedegep.media.mediadb.model.MyInfo#getAlbumType()
   * @see #getMyInfo()
   * @generated
   */
  EAttribute getMyInfo_AlbumType();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Player <em>Player</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Player</em>'.
   * @see goedegep.media.mediadb.model.Player
   * @generated
   */
  EClass getPlayer();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.Player#getArtist <em>Artist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Artist</em>'.
   * @see goedegep.media.mediadb.model.Player#getArtist()
   * @see #getPlayer()
   * @generated
   */
  EReference getPlayer_Artist();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Player#getInstruments <em>Instruments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Instruments</em>'.
   * @see goedegep.media.mediadb.model.Player#getInstruments()
   * @see #getPlayer()
   * @generated
   */
  EAttribute getPlayer_Instruments();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.TrackPart <em>Track Part</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Track Part</em>'.
   * @see goedegep.media.mediadb.model.TrackPart
   * @generated
   */
  EClass getTrackPart();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.TrackPart#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.TrackPart#getTitle()
   * @see #getTrackPart()
   * @generated
   */
  EAttribute getTrackPart_Title();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.MyTrackInfo <em>My Track Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>My Track Info</em>'.
   * @see goedegep.media.mediadb.model.MyTrackInfo
   * @generated
   */
  EClass getMyTrackInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyTrackInfo#getCollection <em>Collection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Collection</em>'.
   * @see goedegep.media.mediadb.model.MyTrackInfo#getCollection()
   * @see #getMyTrackInfo()
   * @generated
   */
  EAttribute getMyTrackInfo_Collection();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.MyTrackInfo#getIHaveOn <em>IHave On</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>IHave On</em>'.
   * @see goedegep.media.mediadb.model.MyTrackInfo#getIHaveOn()
   * @see #getMyTrackInfo()
   * @generated
   */
  EReference getMyTrackInfo_IHaveOn();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.MyTrackInfo#getIWant <em>IWant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>IWant</em>'.
   * @see goedegep.media.mediadb.model.MyTrackInfo#getIWant()
   * @see #getMyTrackInfo()
   * @generated
   */
  EAttribute getMyTrackInfo_IWant();

  /**
   * Returns the meta object for the reference '{@link goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference <em>Compilation Track Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Compilation Track Reference</em>'.
   * @see goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference()
   * @see #getMyTrackInfo()
   * @generated
   */
  EReference getMyTrackInfo_CompilationTrackReference();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.DiscAndTrackNrs <em>Disc And Track Nrs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Disc And Track Nrs</em>'.
   * @see goedegep.media.mediadb.model.DiscAndTrackNrs
   * @generated
   */
  EClass getDiscAndTrackNrs();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr <em>Disc Nr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Disc Nr</em>'.
   * @see goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr()
   * @see #getDiscAndTrackNrs()
   * @generated
   */
  EAttribute getDiscAndTrackNrs_DiscNr();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getTrackNrs <em>Track Nrs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Track Nrs</em>'.
   * @see goedegep.media.mediadb.model.DiscAndTrackNrs#getTrackNrs()
   * @see #getDiscAndTrackNrs()
   * @generated
   */
  EAttribute getDiscAndTrackNrs_TrackNrs();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.TrackCollection <em>Track Collection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Track Collection</em>'.
   * @see goedegep.media.mediadb.model.TrackCollection
   * @generated
   */
  EClass getTrackCollection();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.TrackCollection#getCollection <em>Collection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Collection</em>'.
   * @see goedegep.media.mediadb.model.TrackCollection#getCollection()
   * @see #getTrackCollection()
   * @generated
   */
  EAttribute getTrackCollection_Collection();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.TrackCollection#getTrackReferences <em>Track References</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Track References</em>'.
   * @see goedegep.media.mediadb.model.TrackCollection#getTrackReferences()
   * @see #getTrackCollection()
   * @generated
   */
  EReference getTrackCollection_TrackReferences();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Video <em>Video</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Video</em>'.
   * @see goedegep.media.mediadb.model.Video
   * @generated
   */
  EClass getVideo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Video#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.Video#getTitle()
   * @see #getVideo()
   * @generated
   */
  EAttribute getVideo_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Video#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.media.mediadb.model.Video#getDate()
   * @see #getVideo()
   * @generated
   */
  EAttribute getVideo_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Video#getImage <em>Image</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Image</em>'.
   * @see goedegep.media.mediadb.model.Video#getImage()
   * @see #getVideo()
   * @generated
   */
  EAttribute getVideo_Image();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.mediadb.model.Video#getSubjects <em>Subjects</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Subjects</em>'.
   * @see goedegep.media.mediadb.model.Video#getSubjects()
   * @see #getVideo()
   * @generated
   */
  EReference getVideo_Subjects();

  /**
   * Returns the meta object for class '{@link goedegep.media.mediadb.model.Subject <em>Subject</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Subject</em>'.
   * @see goedegep.media.mediadb.model.Subject
   * @generated
   */
  EClass getSubject();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Subject#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.mediadb.model.Subject#getTitle()
   * @see #getSubject()
   * @generated
   */
  EAttribute getSubject_Title();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.mediadb.model.Subject#getTags <em>Tags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Tags</em>'.
   * @see goedegep.media.mediadb.model.Subject#getTags()
   * @see #getSubject()
   * @generated
   */
  EAttribute getSubject_Tags();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.mediadb.model.Subject#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.media.mediadb.model.Subject#getDate()
   * @see #getSubject()
   * @generated
   */
  EAttribute getSubject_Date();

  /**
   * Returns the meta object for enum '{@link goedegep.media.mediadb.model.MediumType <em>Medium Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Medium Type</em>'.
   * @see goedegep.media.mediadb.model.MediumType
   * @generated
   */
  EEnum getMediumType();

  /**
   * Returns the meta object for enum '{@link goedegep.media.mediadb.model.IWant <em>IWant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>IWant</em>'.
   * @see goedegep.media.mediadb.model.IWant
   * @generated
   */
  EEnum getIWant();

  /**
   * Returns the meta object for enum '{@link goedegep.media.mediadb.model.InformationType <em>Information Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Information Type</em>'.
   * @see goedegep.media.mediadb.model.InformationType
   * @generated
   */
  EEnum getInformationType();

  /**
   * Returns the meta object for enum '{@link goedegep.media.mediadb.model.Collection <em>Collection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Collection</em>'.
   * @see goedegep.media.mediadb.model.Collection
   * @generated
   */
  EEnum getCollection();

  /**
   * Returns the meta object for enum '{@link goedegep.media.mediadb.model.AlbumType <em>Album Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Album Type</em>'.
   * @see goedegep.media.mediadb.model.AlbumType
   * @generated
   */
  EEnum getAlbumType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MediadbFactory getMediadbFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.TrackImpl <em>Track</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.TrackImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrack()
    	 * @generated
    	 */
    EClass TRACK = eINSTANCE.getTrack();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRACK__TITLE = eINSTANCE.getTrack_Title();

    /**
    	 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRACK__DURATION = eINSTANCE.getTrack_Duration();

    /**
    	 * The meta object literal for the '<em><b>Players</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__PLAYERS = eINSTANCE.getTrack_Players();

    /**
    	 * The meta object literal for the '<em><b>Artist</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__ARTIST = eINSTANCE.getTrack_Artist();

    /**
    	 * The meta object literal for the '<em><b>Parts</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__PARTS = eINSTANCE.getTrack_Parts();

    /**
    	 * The meta object literal for the '<em><b>Authors</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__AUTHORS = eINSTANCE.getTrack_Authors();

    /**
    	 * The meta object literal for the '<em><b>Referred By</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__REFERRED_BY = eINSTANCE.getTrack_ReferredBy();

    /**
    	 * The meta object literal for the '<em><b>Original Disc</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK__ORIGINAL_DISC = eINSTANCE.getTrack_OriginalDisc();

    /**
    	 * The meta object literal for the '<em><b>Get Original Disc Track Reference</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation TRACK___GET_ORIGINAL_DISC_TRACK_REFERENCE = eINSTANCE.getTrack__GetOriginalDiscTrackReference();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.MediaDbImpl <em>Media Db</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.MediaDbImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediaDb()
    	 * @generated
    	 */
    EClass MEDIA_DB = eINSTANCE.getMediaDb();

    /**
    	 * The meta object literal for the '<em><b>Artists</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MEDIA_DB__ARTISTS = eINSTANCE.getMediaDb_Artists();

    /**
    	 * The meta object literal for the '<em><b>Albums</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MEDIA_DB__ALBUMS = eINSTANCE.getMediaDb_Albums();

    /**
    	 * The meta object literal for the '<em><b>Tracks</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MEDIA_DB__TRACKS = eINSTANCE.getMediaDb_Tracks();

    /**
    	 * The meta object literal for the '<em><b>Trackcollections</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MEDIA_DB__TRACKCOLLECTIONS = eINSTANCE.getMediaDb_Trackcollections();

    /**
    	 * The meta object literal for the '<em><b>Videos</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MEDIA_DB__VIDEOS = eINSTANCE.getMediaDb_Videos();

    /**
    	 * The meta object literal for the '<em><b>Get Artist</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation MEDIA_DB___GET_ARTIST__STRING = eINSTANCE.getMediaDb__GetArtist__String();

    /**
    	 * The meta object literal for the '<em><b>Get Album</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation MEDIA_DB___GET_ALBUM__FLEXDATE_ARTIST_STRING = eINSTANCE.getMediaDb__GetAlbum__FlexDate_Artist_String();

    /**
    	 * The meta object literal for the '<em><b>Get Albums</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation MEDIA_DB___GET_ALBUMS__FLEXDATE_ARTIST_STRING = eINSTANCE
        .getMediaDb__GetAlbums__FlexDate_Artist_String();

    /**
    	 * The meta object literal for the '<em><b>Get Track Collection</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation MEDIA_DB___GET_TRACK_COLLECTION__COLLECTION = eINSTANCE.getMediaDb__GetTrackCollection__Collection();

    /**
    	 * The meta object literal for the '<em><b>Get Track</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation MEDIA_DB___GET_TRACK__ARTIST_STRING = eINSTANCE.getMediaDb__GetTrack__Artist_String();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.ArtistImpl <em>Artist</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.ArtistImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getArtist()
    	 * @generated
    	 */
    EClass ARTIST = eINSTANCE.getArtist();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ARTIST__NAME = eINSTANCE.getArtist_Name();

    /**
    	 * The meta object literal for the '<em><b>Container Artist</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ARTIST__CONTAINER_ARTIST = eINSTANCE.getArtist_ContainerArtist();

    /**
    	 * The meta object literal for the '<em><b>Photo</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ARTIST__PHOTO = eINSTANCE.getArtist_Photo();

    /**
    	 * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ARTIST__STYLE = eINSTANCE.getArtist_Style();

    /**
    	 * The meta object literal for the '<em><b>My Comments</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ARTIST__MY_COMMENTS = eINSTANCE.getArtist_MyComments();

    /**
    	 * The meta object literal for the '<em><b>Sample</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ARTIST__SAMPLE = eINSTANCE.getArtist_Sample();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.AlbumImpl <em>Album</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.AlbumImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getAlbum()
    	 * @generated
    	 */
    EClass ALBUM = eINSTANCE.getAlbum();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__TITLE = eINSTANCE.getAlbum_Title();

    /**
    	 * The meta object literal for the '<em><b>Release Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__RELEASE_DATE = eINSTANCE.getAlbum_ReleaseDate();

    /**
    	 * The meta object literal for the '<em><b>Discs</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ALBUM__DISCS = eINSTANCE.getAlbum_Discs();

    /**
    	 * The meta object literal for the '<em><b>Artist</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ALBUM__ARTIST = eINSTANCE.getAlbum_Artist();

    /**
    	 * The meta object literal for the '<em><b>Images Front</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__IMAGES_FRONT = eINSTANCE.getAlbum_ImagesFront();

    /**
    	 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__ID = eINSTANCE.getAlbum_Id();

    /**
    	 * The meta object literal for the '<em><b>Players</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ALBUM__PLAYERS = eINSTANCE.getAlbum_Players();

    /**
    	 * The meta object literal for the '<em><b>Images Front Inside</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__IMAGES_FRONT_INSIDE = eINSTANCE.getAlbum_ImagesFrontInside();

    /**
    	 * The meta object literal for the '<em><b>Images Back</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__IMAGES_BACK = eINSTANCE.getAlbum_ImagesBack();

    /**
    	 * The meta object literal for the '<em><b>Images Label</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__IMAGES_LABEL = eINSTANCE.getAlbum_ImagesLabel();

    /**
    	 * The meta object literal for the '<em><b>Description Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__DESCRIPTION_TITLE = eINSTANCE.getAlbum_DescriptionTitle();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__DESCRIPTION = eINSTANCE.getAlbum_Description();

    /**
    	 * The meta object literal for the '<em><b>Issued On Mediums</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__ISSUED_ON_MEDIUMS = eINSTANCE.getAlbum_IssuedOnMediums();

    /**
    	 * The meta object literal for the '<em><b>Compilation</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__COMPILATION = eINSTANCE.getAlbum_Compilation();

    /**
    	 * The meta object literal for the '<em><b>My Info</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ALBUM__MY_INFO = eINSTANCE.getAlbum_MyInfo();

    /**
    	 * The meta object literal for the '<em><b>Soundtrack</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ALBUM__SOUNDTRACK = eINSTANCE.getAlbum_Soundtrack();

    /**
    	 * The meta object literal for the '<em><b>Get Track Reference</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___GET_TRACK_REFERENCE__INTEGER_INT = eINSTANCE.getAlbum__GetTrackReference__Integer_int();

    /**
    	 * The meta object literal for the '<em><b>Get Player</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___GET_PLAYER__ARTIST = eINSTANCE.getAlbum__GetPlayer__Artist();

    /**
    	 * The meta object literal for the '<em><b>Get Artist And Title</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___GET_ARTIST_AND_TITLE = eINSTANCE.getAlbum__GetArtistAndTitle();

    /**
    	 * The meta object literal for the '<em><b>Is Multi Disc Album</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___IS_MULTI_DISC_ALBUM = eINSTANCE.getAlbum__IsMultiDiscAlbum();

    /**
    	 * The meta object literal for the '<em><b>Get Disc</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___GET_DISC = eINSTANCE.getAlbum__GetDisc();

    /**
    	 * The meta object literal for the '<em><b>IWant Album Or Tracks Of Album</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___IWANT_ALBUM_OR_TRACKS_OF_ALBUM = eINSTANCE.getAlbum__IWantAlbumOrTracksOfAlbum();

    /**
    	 * The meta object literal for the '<em><b>IHave To Judge Album Or Tracks</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ALBUM___IHAVE_TO_JUDGE_ALBUM_OR_TRACKS = eINSTANCE.getAlbum__IHaveToJudgeAlbumOrTracks();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl <em>Track Reference</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.TrackReferenceImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackReference()
    	 * @generated
    	 */
    EClass TRACK_REFERENCE = eINSTANCE.getTrackReference();

    /**
    	 * The meta object literal for the '<em><b>Track</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK_REFERENCE__TRACK = eINSTANCE.getTrackReference_Track();

    /**
    	 * The meta object literal for the '<em><b>Bonus Track</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRACK_REFERENCE__BONUS_TRACK = eINSTANCE.getTrackReference_BonusTrack();

    /**
    	 * The meta object literal for the '<em><b>My Track Info</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK_REFERENCE__MY_TRACK_INFO = eINSTANCE.getTrackReference_MyTrackInfo();

    /**
    	 * The meta object literal for the '<em><b>Original Album Track Reference</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE = eINSTANCE
        .getTrackReference_OriginalAlbumTrackReference();

    /**
    	 * The meta object literal for the '<em><b>Get Track Nr</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation TRACK_REFERENCE___GET_TRACK_NR = eINSTANCE.getTrackReference__GetTrackNr();

    /**
    	 * The meta object literal for the '<em><b>Get Disc</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation TRACK_REFERENCE___GET_DISC = eINSTANCE.getTrackReference__GetDisc();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.DiscImpl <em>Disc</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.DiscImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getDisc()
    	 * @generated
    	 */
    EClass DISC = eINSTANCE.getDisc();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DISC__TITLE = eINSTANCE.getDisc_Title();

    /**
    	 * The meta object literal for the '<em><b>Track References</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference DISC__TRACK_REFERENCES = eINSTANCE.getDisc_TrackReferences();

    /**
    	 * The meta object literal for the '<em><b>Get Album</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation DISC___GET_ALBUM = eINSTANCE.getDisc__GetAlbum();

    /**
    	 * The meta object literal for the '<em><b>Get Disc Nr</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation DISC___GET_DISC_NR = eINSTANCE.getDisc__GetDiscNr();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.MediumInfoImpl <em>Medium Info</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.MediumInfoImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediumInfo()
    	 * @generated
    	 */
    EClass MEDIUM_INFO = eINSTANCE.getMediumInfo();

    /**
    	 * The meta object literal for the '<em><b>Medium Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MEDIUM_INFO__MEDIUM_TYPE = eINSTANCE.getMediumInfo_MediumType();

    /**
    	 * The meta object literal for the '<em><b>Information Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MEDIUM_INFO__INFORMATION_TYPE = eINSTANCE.getMediumInfo_InformationType();

    /**
    	 * The meta object literal for the '<em><b>Source Types</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MEDIUM_INFO__SOURCE_TYPES = eINSTANCE.getMediumInfo_SourceTypes();

    /**
    	 * The meta object literal for the '<em><b>Source Bit Rate</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MEDIUM_INFO__SOURCE_BIT_RATE = eINSTANCE.getMediumInfo_SourceBitRate();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.MyInfoImpl <em>My Info</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.MyInfoImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMyInfo()
    	 * @generated
    	 */
    EClass MY_INFO = eINSTANCE.getMyInfo();

    /**
    	 * The meta object literal for the '<em><b>Album References</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MY_INFO__ALBUM_REFERENCES = eINSTANCE.getMyInfo_AlbumReferences();

    /**
    	 * The meta object literal for the '<em><b>My Comments</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_INFO__MY_COMMENTS = eINSTANCE.getMyInfo_MyComments();

    /**
    	 * The meta object literal for the '<em><b>Ive Had On LP</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_INFO__IVE_HAD_ON_LP = eINSTANCE.getMyInfo_IveHadOnLP();

    /**
    	 * The meta object literal for the '<em><b>IWant</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_INFO__IWANT = eINSTANCE.getMyInfo_IWant();

    /**
    	 * The meta object literal for the '<em><b>IHave On</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MY_INFO__IHAVE_ON = eINSTANCE.getMyInfo_IHaveOn();

    /**
    	 * The meta object literal for the '<em><b>Album Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_INFO__ALBUM_TYPE = eINSTANCE.getMyInfo_AlbumType();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.PlayerImpl <em>Player</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.PlayerImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getPlayer()
    	 * @generated
    	 */
    EClass PLAYER = eINSTANCE.getPlayer();

    /**
    	 * The meta object literal for the '<em><b>Artist</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PLAYER__ARTIST = eINSTANCE.getPlayer_Artist();

    /**
    	 * The meta object literal for the '<em><b>Instruments</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PLAYER__INSTRUMENTS = eINSTANCE.getPlayer_Instruments();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.TrackPartImpl <em>Track Part</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.TrackPartImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackPart()
    	 * @generated
    	 */
    EClass TRACK_PART = eINSTANCE.getTrackPart();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRACK_PART__TITLE = eINSTANCE.getTrackPart_Title();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl <em>My Track Info</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.MyTrackInfoImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMyTrackInfo()
    	 * @generated
    	 */
    EClass MY_TRACK_INFO = eINSTANCE.getMyTrackInfo();

    /**
    	 * The meta object literal for the '<em><b>Collection</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_TRACK_INFO__COLLECTION = eINSTANCE.getMyTrackInfo_Collection();

    /**
    	 * The meta object literal for the '<em><b>IHave On</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MY_TRACK_INFO__IHAVE_ON = eINSTANCE.getMyTrackInfo_IHaveOn();

    /**
    	 * The meta object literal for the '<em><b>IWant</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MY_TRACK_INFO__IWANT = eINSTANCE.getMyTrackInfo_IWant();

    /**
    	 * The meta object literal for the '<em><b>Compilation Track Reference</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE = eINSTANCE.getMyTrackInfo_CompilationTrackReference();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl <em>Disc And Track Nrs</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.DiscAndTrackNrsImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getDiscAndTrackNrs()
    	 * @generated
    	 */
    EClass DISC_AND_TRACK_NRS = eINSTANCE.getDiscAndTrackNrs();

    /**
    	 * The meta object literal for the '<em><b>Disc Nr</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DISC_AND_TRACK_NRS__DISC_NR = eINSTANCE.getDiscAndTrackNrs_DiscNr();

    /**
    	 * The meta object literal for the '<em><b>Track Nrs</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DISC_AND_TRACK_NRS__TRACK_NRS = eINSTANCE.getDiscAndTrackNrs_TrackNrs();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.TrackCollectionImpl <em>Track Collection</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.TrackCollectionImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getTrackCollection()
    	 * @generated
    	 */
    EClass TRACK_COLLECTION = eINSTANCE.getTrackCollection();

    /**
    	 * The meta object literal for the '<em><b>Collection</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRACK_COLLECTION__COLLECTION = eINSTANCE.getTrackCollection_Collection();

    /**
    	 * The meta object literal for the '<em><b>Track References</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRACK_COLLECTION__TRACK_REFERENCES = eINSTANCE.getTrackCollection_TrackReferences();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.VideoImpl <em>Video</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.VideoImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getVideo()
    	 * @generated
    	 */
    EClass VIDEO = eINSTANCE.getVideo();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VIDEO__TITLE = eINSTANCE.getVideo_Title();

    /**
    	 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VIDEO__DATE = eINSTANCE.getVideo_Date();

    /**
    	 * The meta object literal for the '<em><b>Image</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VIDEO__IMAGE = eINSTANCE.getVideo_Image();

    /**
    	 * The meta object literal for the '<em><b>Subjects</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VIDEO__SUBJECTS = eINSTANCE.getVideo_Subjects();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.impl.SubjectImpl <em>Subject</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.impl.SubjectImpl
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getSubject()
    	 * @generated
    	 */
    EClass SUBJECT = eINSTANCE.getSubject();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute SUBJECT__TITLE = eINSTANCE.getSubject_Title();

    /**
    	 * The meta object literal for the '<em><b>Tags</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute SUBJECT__TAGS = eINSTANCE.getSubject_Tags();

    /**
    	 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute SUBJECT__DATE = eINSTANCE.getSubject_Date();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.MediumType <em>Medium Type</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.MediumType
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getMediumType()
    	 * @generated
    	 */
    EEnum MEDIUM_TYPE = eINSTANCE.getMediumType();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.IWant <em>IWant</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.IWant
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getIWant()
    	 * @generated
    	 */
    EEnum IWANT = eINSTANCE.getIWant();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.InformationType <em>Information Type</em>}' enum.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.InformationType
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getInformationType()
    	 * @generated
    	 */
    EEnum INFORMATION_TYPE = eINSTANCE.getInformationType();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.Collection <em>Collection</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.Collection
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getCollection()
    	 * @generated
    	 */
    EEnum COLLECTION = eINSTANCE.getCollection();

    /**
    	 * The meta object literal for the '{@link goedegep.media.mediadb.model.AlbumType <em>Album Type</em>}' enum.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.media.mediadb.model.AlbumType
    	 * @see goedegep.media.mediadb.model.impl.MediadbPackageImpl#getAlbumType()
    	 * @generated
    	 */
    EEnum ALBUM_TYPE = eINSTANCE.getAlbumType();

  }

} //MediadbPackage
