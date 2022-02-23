/**
 */
package goedegep.media.mediadb.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.media.mediadb.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MediadbFactoryImpl extends EFactoryImpl implements MediadbFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static MediadbFactory init() {
    try {
      MediadbFactory theMediadbFactory = (MediadbFactory) EPackage.Registry.INSTANCE
          .getEFactory(MediadbPackage.eNS_URI);
      if (theMediadbFactory != null) {
        return theMediadbFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new MediadbFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MediadbFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
    case MediadbPackage.TRACK:
      return createTrack();
    case MediadbPackage.MEDIA_DB:
      return createMediaDb();
    case MediadbPackage.ARTIST:
      return createArtist();
    case MediadbPackage.ALBUM:
      return createAlbum();
    case MediadbPackage.MY_COMPILATION:
      return createMyCompilation();
    case MediadbPackage.TRACK_REFERENCE:
      return createTrackReference();
    case MediadbPackage.DISC:
      return createDisc();
    case MediadbPackage.MEDIUM_INFO:
      return createMediumInfo();
    case MediadbPackage.MY_INFO:
      return createMyInfo();
    case MediadbPackage.PLAYER:
      return createPlayer();
    case MediadbPackage.TRACK_PART:
      return createTrackPart();
    case MediadbPackage.MY_TRACK_INFO:
      return createMyTrackInfo();
    case MediadbPackage.DISC_AND_TRACK_NRS:
      return createDiscAndTrackNrs();
    case MediadbPackage.TRACK_COLLECTION:
      return createTrackCollection();
    default:
      throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
    case MediadbPackage.MEDIUM_TYPE:
      return createMediumTypeFromString(eDataType, initialValue);
    case MediadbPackage.IWANT:
      return createIWantFromString(eDataType, initialValue);
    case MediadbPackage.INFORMATION_TYPE:
      return createInformationTypeFromString(eDataType, initialValue);
    case MediadbPackage.COLLECTION:
      return createCollectionFromString(eDataType, initialValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
    case MediadbPackage.MEDIUM_TYPE:
      return convertMediumTypeToString(eDataType, instanceValue);
    case MediadbPackage.IWANT:
      return convertIWantToString(eDataType, instanceValue);
    case MediadbPackage.INFORMATION_TYPE:
      return convertInformationTypeToString(eDataType, instanceValue);
    case MediadbPackage.COLLECTION:
      return convertCollectionToString(eDataType, instanceValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Track createTrack() {
    TrackImpl track = new TrackImpl();
    return track;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MediaDb createMediaDb() {
    MediaDbImpl mediaDb = new MediaDbImpl();
    return mediaDb;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Artist createArtist() {
    ArtistImpl artist = new ArtistImpl();
    return artist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Album createAlbum() {
    AlbumImpl album = new AlbumImpl();
    return album;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MyCompilation createMyCompilation() {
    MyCompilationImpl myCompilation = new MyCompilationImpl();
    return myCompilation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackReference createTrackReference() {
    TrackReferenceImpl trackReference = new TrackReferenceImpl();
    return trackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Disc createDisc() {
    DiscImpl disc = new DiscImpl();
    return disc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MediumInfo createMediumInfo() {
    MediumInfoImpl mediumInfo = new MediumInfoImpl();
    return mediumInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MyInfo createMyInfo() {
    MyInfoImpl myInfo = new MyInfoImpl();
    return myInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Player createPlayer() {
    PlayerImpl player = new PlayerImpl();
    return player;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackPart createTrackPart() {
    TrackPartImpl trackPart = new TrackPartImpl();
    return trackPart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MyTrackInfo createMyTrackInfo() {
    MyTrackInfoImpl myTrackInfo = new MyTrackInfoImpl();
    return myTrackInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DiscAndTrackNrs createDiscAndTrackNrs() {
    DiscAndTrackNrsImpl discAndTrackNrs = new DiscAndTrackNrsImpl();
    return discAndTrackNrs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackCollection createTrackCollection() {
    TrackCollectionImpl trackCollection = new TrackCollectionImpl();
    return trackCollection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MediumType createMediumTypeFromString(EDataType eDataType, String initialValue) {
    MediumType result = MediumType.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertMediumTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWant createIWantFromString(EDataType eDataType, String initialValue) {
    IWant result = IWant.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertIWantToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InformationType createInformationTypeFromString(EDataType eDataType, String initialValue) {
    InformationType result = InformationType.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertInformationTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Collection createCollectionFromString(EDataType eDataType, String initialValue) {
    Collection result = Collection.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertCollectionToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MediadbPackage getMediadbPackage() {
    return (MediadbPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static MediadbPackage getPackage() {
    return MediadbPackage.eINSTANCE;
  }

} //MediadbFactoryImpl
