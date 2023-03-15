/**
 */
package goedegep.media.mediadb.model.impl;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.Video;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Subject;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackPart;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.types.model.TypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MediadbPackageImpl extends EPackageImpl implements MediadbPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass trackEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mediaDbEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass artistEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass albumEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass myCompilationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass trackReferenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass discEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mediumInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass myInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass playerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass trackPartEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass myTrackInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass discAndTrackNrsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass trackCollectionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass videoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass subjectEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum mediumTypeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum iWantEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum informationTypeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum collectionEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.media.mediadb.model.MediadbPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private MediadbPackageImpl() {
    super(eNS_URI, MediadbFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link MediadbPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static MediadbPackage init() {
    if (isInited)
      return (MediadbPackage) EPackage.Registry.INSTANCE.getEPackage(MediadbPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredMediadbPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    MediadbPackageImpl theMediadbPackage = registeredMediadbPackage instanceof MediadbPackageImpl
        ? (MediadbPackageImpl) registeredMediadbPackage
        : new MediadbPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theMediadbPackage.createPackageContents();

    // Initialize created meta-data
    theMediadbPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theMediadbPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(MediadbPackage.eNS_URI, theMediadbPackage);
    return theMediadbPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTrack() {
    return trackEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTrack_Title() {
    return (EAttribute) trackEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTrack_Duration() {
    return (EAttribute) trackEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_Players() {
    return (EReference) trackEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_Artist() {
    return (EReference) trackEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_Parts() {
    return (EReference) trackEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_Authors() {
    return (EReference) trackEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_ReferredBy() {
    return (EReference) trackEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrack_OriginalDisc() {
    return (EReference) trackEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getTrack__GetOriginalDiscTrackReference() {
    return trackEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMediaDb() {
    return mediaDbEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMediaDb_Artists() {
    return (EReference) mediaDbEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMediaDb_Albums() {
    return (EReference) mediaDbEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMediaDb_Tracks() {
    return (EReference) mediaDbEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMediaDb_Trackcollections() {
    return (EReference) mediaDbEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMediaDb_Videos() {
    return (EReference) mediaDbEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMediaDb__GetArtist__String() {
    return mediaDbEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMediaDb__GetAlbum__FlexDate_Artist_String() {
    return mediaDbEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMediaDb__GetAlbums__FlexDate_Artist_String() {
    return mediaDbEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMediaDb__GetTrackCollection__Collection() {
    return mediaDbEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getArtist() {
    return artistEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getArtist_Name() {
    return (EAttribute) artistEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getArtist_ContainerArtist() {
    return (EReference) artistEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAlbum() {
    return albumEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_Title() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_ReleaseDate() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAlbum_Discs() {
    return (EReference) albumEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAlbum_Artist() {
    return (EReference) albumEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_ImagesFront() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_Id() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAlbum_Players() {
    return (EReference) albumEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_ImagesFrontInside() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_ImagesBack() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_ImagesLabel() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_DescriptionTitle() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_Description() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_IssuedOnMediums() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_Compilation() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAlbum_MyInfo() {
    return (EReference) albumEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAlbum_Soundtrack() {
    return (EAttribute) albumEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAlbum__GetTrackReference__Integer_int() {
    return albumEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAlbum__GetPlayer__Artist() {
    return albumEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAlbum__GetArtistAndTitle() {
    return albumEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAlbum__IsMultiDiscAlbum() {
    return albumEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAlbum__GetDisc() {
    return albumEClass.getEOperations().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMyCompilation() {
    return myCompilationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTrackReference() {
    return trackReferenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrackReference_Track() {
    return (EReference) trackReferenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTrackReference_BonusTrack() {
    return (EAttribute) trackReferenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrackReference_MyTrackInfo() {
    return (EReference) trackReferenceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrackReference_OriginalAlbumTrackReference() {
    return (EReference) trackReferenceEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getTrackReference__GetTrackNr() {
    return trackReferenceEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getTrackReference__GetDisc() {
    return trackReferenceEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDisc() {
    return discEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDisc_Title() {
    return (EAttribute) discEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDisc_TrackReferences() {
    return (EReference) discEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getDisc__GetAlbum() {
    return discEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getDisc__GetDiscNr() {
    return discEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMediumInfo() {
    return mediumInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMediumInfo_MediumType() {
    return (EAttribute) mediumInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMediumInfo_InformationType() {
    return (EAttribute) mediumInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMediumInfo_SourceTypes() {
    return (EAttribute) mediumInfoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMediumInfo_SourceBitRate() {
    return (EAttribute) mediumInfoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMyInfo() {
    return myInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMyInfo_AlbumReferences() {
    return (EReference) myInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMyInfo_MyComments() {
    return (EAttribute) myInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMyInfo_IveHadOnLP() {
    return (EAttribute) myInfoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMyInfo_IWant() {
    return (EAttribute) myInfoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMyInfo_IHaveOn() {
    return (EReference) myInfoEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPlayer() {
    return playerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPlayer_Artist() {
    return (EReference) playerEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPlayer_Instruments() {
    return (EAttribute) playerEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTrackPart() {
    return trackPartEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTrackPart_Title() {
    return (EAttribute) trackPartEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMyTrackInfo() {
    return myTrackInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMyTrackInfo_Collection() {
    return (EAttribute) myTrackInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMyTrackInfo_IHaveOn() {
    return (EReference) myTrackInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMyTrackInfo_IWant() {
    return (EAttribute) myTrackInfoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMyTrackInfo_CompilationTrackReference() {
    return (EReference) myTrackInfoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDiscAndTrackNrs() {
    return discAndTrackNrsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDiscAndTrackNrs_DiscNr() {
    return (EAttribute) discAndTrackNrsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDiscAndTrackNrs_TrackNrs() {
    return (EAttribute) discAndTrackNrsEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTrackCollection() {
    return trackCollectionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTrackCollection_Collection() {
    return (EAttribute) trackCollectionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTrackCollection_TrackReferences() {
    return (EReference) trackCollectionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVideo() {
    return videoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVideo_Title() {
    return (EAttribute) videoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVideo_Date() {
    return (EAttribute) videoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVideo_Image() {
    return (EAttribute) videoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVideo_Subjects() {
    return (EReference) videoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSubject() {
    return subjectEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSubject_Title() {
    return (EAttribute) subjectEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSubject_Tags() {
    return (EAttribute) subjectEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSubject_Date() {
    return (EAttribute) subjectEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getMediumType() {
    return mediumTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getIWant() {
    return iWantEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getInformationType() {
    return informationTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getCollection() {
    return collectionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MediadbFactory getMediadbFactory() {
    return (MediadbFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    trackEClass = createEClass(TRACK);
    createEAttribute(trackEClass, TRACK__TITLE);
    createEAttribute(trackEClass, TRACK__DURATION);
    createEReference(trackEClass, TRACK__PLAYERS);
    createEReference(trackEClass, TRACK__ARTIST);
    createEReference(trackEClass, TRACK__PARTS);
    createEReference(trackEClass, TRACK__AUTHORS);
    createEReference(trackEClass, TRACK__REFERRED_BY);
    createEReference(trackEClass, TRACK__ORIGINAL_DISC);
    createEOperation(trackEClass, TRACK___GET_ORIGINAL_DISC_TRACK_REFERENCE);

    mediaDbEClass = createEClass(MEDIA_DB);
    createEReference(mediaDbEClass, MEDIA_DB__ARTISTS);
    createEReference(mediaDbEClass, MEDIA_DB__ALBUMS);
    createEReference(mediaDbEClass, MEDIA_DB__TRACKS);
    createEReference(mediaDbEClass, MEDIA_DB__TRACKCOLLECTIONS);
    createEReference(mediaDbEClass, MEDIA_DB__VIDEOS);
    createEOperation(mediaDbEClass, MEDIA_DB___GET_ARTIST__STRING);
    createEOperation(mediaDbEClass, MEDIA_DB___GET_ALBUM__FLEXDATE_ARTIST_STRING);
    createEOperation(mediaDbEClass, MEDIA_DB___GET_ALBUMS__FLEXDATE_ARTIST_STRING);
    createEOperation(mediaDbEClass, MEDIA_DB___GET_TRACK_COLLECTION__COLLECTION);

    artistEClass = createEClass(ARTIST);
    createEAttribute(artistEClass, ARTIST__NAME);
    createEReference(artistEClass, ARTIST__CONTAINER_ARTIST);

    albumEClass = createEClass(ALBUM);
    createEAttribute(albumEClass, ALBUM__TITLE);
    createEAttribute(albumEClass, ALBUM__RELEASE_DATE);
    createEReference(albumEClass, ALBUM__DISCS);
    createEReference(albumEClass, ALBUM__ARTIST);
    createEAttribute(albumEClass, ALBUM__IMAGES_FRONT);
    createEAttribute(albumEClass, ALBUM__ID);
    createEReference(albumEClass, ALBUM__PLAYERS);
    createEAttribute(albumEClass, ALBUM__IMAGES_FRONT_INSIDE);
    createEAttribute(albumEClass, ALBUM__IMAGES_BACK);
    createEAttribute(albumEClass, ALBUM__IMAGES_LABEL);
    createEAttribute(albumEClass, ALBUM__DESCRIPTION_TITLE);
    createEAttribute(albumEClass, ALBUM__DESCRIPTION);
    createEAttribute(albumEClass, ALBUM__ISSUED_ON_MEDIUMS);
    createEAttribute(albumEClass, ALBUM__COMPILATION);
    createEReference(albumEClass, ALBUM__MY_INFO);
    createEAttribute(albumEClass, ALBUM__SOUNDTRACK);
    createEOperation(albumEClass, ALBUM___GET_TRACK_REFERENCE__INTEGER_INT);
    createEOperation(albumEClass, ALBUM___GET_PLAYER__ARTIST);
    createEOperation(albumEClass, ALBUM___GET_ARTIST_AND_TITLE);
    createEOperation(albumEClass, ALBUM___IS_MULTI_DISC_ALBUM);
    createEOperation(albumEClass, ALBUM___GET_DISC);

    myCompilationEClass = createEClass(MY_COMPILATION);

    trackReferenceEClass = createEClass(TRACK_REFERENCE);
    createEReference(trackReferenceEClass, TRACK_REFERENCE__TRACK);
    createEAttribute(trackReferenceEClass, TRACK_REFERENCE__BONUS_TRACK);
    createEReference(trackReferenceEClass, TRACK_REFERENCE__MY_TRACK_INFO);
    createEReference(trackReferenceEClass, TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE);
    createEOperation(trackReferenceEClass, TRACK_REFERENCE___GET_TRACK_NR);
    createEOperation(trackReferenceEClass, TRACK_REFERENCE___GET_DISC);

    discEClass = createEClass(DISC);
    createEAttribute(discEClass, DISC__TITLE);
    createEReference(discEClass, DISC__TRACK_REFERENCES);
    createEOperation(discEClass, DISC___GET_ALBUM);
    createEOperation(discEClass, DISC___GET_DISC_NR);

    mediumInfoEClass = createEClass(MEDIUM_INFO);
    createEAttribute(mediumInfoEClass, MEDIUM_INFO__MEDIUM_TYPE);
    createEAttribute(mediumInfoEClass, MEDIUM_INFO__INFORMATION_TYPE);
    createEAttribute(mediumInfoEClass, MEDIUM_INFO__SOURCE_TYPES);
    createEAttribute(mediumInfoEClass, MEDIUM_INFO__SOURCE_BIT_RATE);

    myInfoEClass = createEClass(MY_INFO);
    createEReference(myInfoEClass, MY_INFO__ALBUM_REFERENCES);
    createEAttribute(myInfoEClass, MY_INFO__MY_COMMENTS);
    createEAttribute(myInfoEClass, MY_INFO__IVE_HAD_ON_LP);
    createEAttribute(myInfoEClass, MY_INFO__IWANT);
    createEReference(myInfoEClass, MY_INFO__IHAVE_ON);

    playerEClass = createEClass(PLAYER);
    createEReference(playerEClass, PLAYER__ARTIST);
    createEAttribute(playerEClass, PLAYER__INSTRUMENTS);

    trackPartEClass = createEClass(TRACK_PART);
    createEAttribute(trackPartEClass, TRACK_PART__TITLE);

    myTrackInfoEClass = createEClass(MY_TRACK_INFO);
    createEAttribute(myTrackInfoEClass, MY_TRACK_INFO__COLLECTION);
    createEReference(myTrackInfoEClass, MY_TRACK_INFO__IHAVE_ON);
    createEAttribute(myTrackInfoEClass, MY_TRACK_INFO__IWANT);
    createEReference(myTrackInfoEClass, MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE);

    discAndTrackNrsEClass = createEClass(DISC_AND_TRACK_NRS);
    createEAttribute(discAndTrackNrsEClass, DISC_AND_TRACK_NRS__DISC_NR);
    createEAttribute(discAndTrackNrsEClass, DISC_AND_TRACK_NRS__TRACK_NRS);

    trackCollectionEClass = createEClass(TRACK_COLLECTION);
    createEAttribute(trackCollectionEClass, TRACK_COLLECTION__COLLECTION);
    createEReference(trackCollectionEClass, TRACK_COLLECTION__TRACK_REFERENCES);

    videoEClass = createEClass(VIDEO);
    createEAttribute(videoEClass, VIDEO__TITLE);
    createEAttribute(videoEClass, VIDEO__DATE);
    createEAttribute(videoEClass, VIDEO__IMAGE);
    createEReference(videoEClass, VIDEO__SUBJECTS);

    subjectEClass = createEClass(SUBJECT);
    createEAttribute(subjectEClass, SUBJECT__TITLE);
    createEAttribute(subjectEClass, SUBJECT__TAGS);
    createEAttribute(subjectEClass, SUBJECT__DATE);

    // Create enums
    mediumTypeEEnum = createEEnum(MEDIUM_TYPE);
    iWantEEnum = createEEnum(IWANT);
    informationTypeEEnum = createEEnum(INFORMATION_TYPE);
    collectionEEnum = createEEnum(COLLECTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    myCompilationEClass.getESuperTypes().add(this.getAlbum());

    // Initialize classes, features, and operations; add parameters
    initEClass(trackEClass, Track.class, "Track", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTrack_Title(), ecorePackage.getEString(), "title", null, 0, 1, Track.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTrack_Duration(), ecorePackage.getEIntegerObject(), "duration", null, 0, 1, Track.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTrack_Players(), this.getPlayer(), null, "players", null, 0, -1, Track.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getTrack_Artist(), this.getArtist(), null, "artist", null, 0, 1, Track.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getTrack_Parts(), this.getTrackPart(), null, "parts", null, 0, -1, Track.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getTrack_Authors(), this.getArtist(), null, "authors", null, 0, -1, Track.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getTrack_ReferredBy(), this.getTrackReference(), this.getTrackReference_Track(), "referredBy", null,
        0, -1, Track.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTrack_OriginalDisc(), this.getDisc(), null, "originalDisc", null, 0, 1, Track.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEOperation(getTrack__GetOriginalDiscTrackReference(), this.getTrackReference(), "getOriginalDiscTrackReference",
        0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(mediaDbEClass, MediaDb.class, "MediaDb", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMediaDb_Artists(), this.getArtist(), null, "artists", null, 0, -1, MediaDb.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getMediaDb_Albums(), this.getAlbum(), null, "albums", null, 0, -1, MediaDb.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getMediaDb_Tracks(), this.getTrack(), null, "tracks", null, 0, -1, MediaDb.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getMediaDb_Trackcollections(), this.getTrackCollection(), null, "trackcollections", null, 0, -1,
        MediaDb.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMediaDb_Videos(), this.getVideo(), null, "videos", null, 0, -1, MediaDb.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    EOperation op = initEOperation(getMediaDb__GetArtist__String(), this.getArtist(), "getArtist", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "artistName", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getMediaDb__GetAlbum__FlexDate_Artist_String(), this.getAlbum(), "getAlbum", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, theTypesPackage.getEFlexDate(), "releaseDate", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getArtist(), "artist", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "title", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getMediaDb__GetAlbums__FlexDate_Artist_String(), this.getAlbum(), "getAlbums", 0, -1, IS_UNIQUE,
        !IS_ORDERED);
    addEParameter(op, theTypesPackage.getEFlexDate(), "releaseDate", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getArtist(), "artist", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "title", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getMediaDb__GetTrackCollection__Collection(), this.getTrackCollection(), "getTrackCollection",
        0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getCollection(), "collection", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(artistEClass, Artist.class, "Artist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getArtist_Name(), ecorePackage.getEString(), "name", null, 0, 1, Artist.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getArtist_ContainerArtist(), this.getArtist(), null, "containerArtist", null, 0, 1, Artist.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(albumEClass, Album.class, "Album", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAlbum_Title(), ecorePackage.getEString(), "title", null, 0, 1, Album.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_ReleaseDate(), theTypesPackage.getEFlexDate(), "releaseDate", null, 0, 1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAlbum_Discs(), this.getDisc(), null, "discs", null, 0, -1, Album.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getAlbum_Artist(), this.getArtist(), null, "artist", null, 0, 1, Album.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getAlbum_ImagesFront(), ecorePackage.getEString(), "imagesFront", null, 0, -1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_Id(), ecorePackage.getEString(), "id", null, 0, 1, Album.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAlbum_Players(), this.getPlayer(), null, "players", null, 0, -1, Album.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getAlbum_ImagesFrontInside(), ecorePackage.getEString(), "imagesFrontInside", null, 0, -1,
        Album.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getAlbum_ImagesBack(), ecorePackage.getEString(), "imagesBack", null, 0, -1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_ImagesLabel(), ecorePackage.getEString(), "imagesLabel", null, 0, -1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_DescriptionTitle(), ecorePackage.getEString(), "descriptionTitle", null, 0, 1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_Description(), ecorePackage.getEString(), "description", null, 0, 1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_IssuedOnMediums(), this.getMediumType(), "issuedOnMediums", null, 0, -1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAlbum_Compilation(), ecorePackage.getEBoolean(), "compilation", "false", 0, 1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAlbum_MyInfo(), this.getMyInfo(), null, "myInfo", null, 0, 1, Album.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getAlbum_Soundtrack(), ecorePackage.getEBoolean(), "soundtrack", null, 0, 1, Album.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getAlbum__GetTrackReference__Integer_int(), this.getTrackReference(), "getTrackReference", 0, 1,
        IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEIntegerObject(), "discNr", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEInt(), "trackNr", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getAlbum__GetPlayer__Artist(), this.getPlayer(), "getPlayer", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getArtist(), "artist", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getAlbum__GetArtistAndTitle(), ecorePackage.getEString(), "getArtistAndTitle", 0, 1, IS_UNIQUE,
        IS_ORDERED);

    initEOperation(getAlbum__IsMultiDiscAlbum(), ecorePackage.getEBoolean(), "isMultiDiscAlbum", 0, 1, IS_UNIQUE,
        IS_ORDERED);

    initEOperation(getAlbum__GetDisc(), this.getDisc(), "getDisc", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(myCompilationEClass, MyCompilation.class, "MyCompilation", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);

    initEClass(trackReferenceEClass, TrackReference.class, "TrackReference", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTrackReference_Track(), this.getTrack(), this.getTrack_ReferredBy(), "track", null, 1, 1,
        TrackReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
        IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTrackReference_BonusTrack(), ecorePackage.getEString(), "bonusTrack", null, 0, 1,
        TrackReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getTrackReference_MyTrackInfo(), this.getMyTrackInfo(), null, "myTrackInfo", null, 0, 1,
        TrackReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTrackReference_OriginalAlbumTrackReference(), this.getTrackReference(), null,
        "originalAlbumTrackReference", null, 0, 1, TrackReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getTrackReference__GetTrackNr(), ecorePackage.getEInt(), "getTrackNr", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getTrackReference__GetDisc(), this.getDisc(), "getDisc", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(discEClass, Disc.class, "Disc", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDisc_Title(), ecorePackage.getEString(), "title", null, 0, 1, Disc.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDisc_TrackReferences(), this.getTrackReference(), null, "trackReferences", null, 1, -1,
        Disc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getDisc__GetAlbum(), this.getAlbum(), "getAlbum", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getDisc__GetDiscNr(), ecorePackage.getEInt(), "getDiscNr", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(mediumInfoEClass, MediumInfo.class, "MediumInfo", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMediumInfo_MediumType(), this.getMediumType(), "mediumType", "<not-set>", 0, 1, MediumInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMediumInfo_InformationType(), this.getInformationType(), "informationType", null, 0, 1,
        MediumInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getMediumInfo_SourceTypes(), this.getInformationType(), "sourceTypes", null, 0, -1, MediumInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMediumInfo_SourceBitRate(), ecorePackage.getEInt(), "sourceBitRate", null, 0, 1, MediumInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(myInfoEClass, MyInfo.class, "MyInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMyInfo_AlbumReferences(), this.getAlbum(), null, "albumReferences", null, 0, -1, MyInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMyInfo_MyComments(), ecorePackage.getEString(), "myComments", null, 0, 1, MyInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMyInfo_IveHadOnLP(), ecorePackage.getEBoolean(), "iveHadOnLP", null, 1, 1, MyInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMyInfo_IWant(), this.getIWant(), "iWant", "<not-set>", 0, 1, MyInfo.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMyInfo_IHaveOn(), this.getMediumInfo(), null, "iHaveOn", null, 0, -1, MyInfo.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(playerEClass, Player.class, "Player", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPlayer_Artist(), this.getArtist(), null, "artist", null, 0, 1, Player.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getPlayer_Instruments(), ecorePackage.getEString(), "instruments", null, 0, -1, Player.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(trackPartEClass, TrackPart.class, "TrackPart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTrackPart_Title(), ecorePackage.getEString(), "title", null, 0, 1, TrackPart.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(myTrackInfoEClass, MyTrackInfo.class, "MyTrackInfo", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMyTrackInfo_Collection(), this.getCollection(), "collection", "<not-set>", 0, 1,
        MyTrackInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getMyTrackInfo_IHaveOn(), this.getMediumInfo(), null, "iHaveOn", null, 0, -1, MyTrackInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMyTrackInfo_IWant(), this.getIWant(), "iWant", "<not-set>", 0, 1, MyTrackInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMyTrackInfo_CompilationTrackReference(), this.getTrackReference(), null,
        "compilationTrackReference", null, 0, 1, MyTrackInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(discAndTrackNrsEClass, DiscAndTrackNrs.class, "DiscAndTrackNrs", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDiscAndTrackNrs_DiscNr(), ecorePackage.getEInt(), "discNr", null, 0, 1, DiscAndTrackNrs.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDiscAndTrackNrs_TrackNrs(), ecorePackage.getEInt(), "trackNrs", null, 1, -1,
        DiscAndTrackNrs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(trackCollectionEClass, TrackCollection.class, "TrackCollection", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTrackCollection_Collection(), this.getCollection(), "collection", "<not-set>", 0, 1,
        TrackCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getTrackCollection_TrackReferences(), this.getTrackReference(), null, "trackReferences", null, 0, -1,
        TrackCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(videoEClass, Video.class, "Video", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVideo_Title(), ecorePackage.getEString(), "title", null, 0, 1, Video.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVideo_Date(), theTypesPackage.getEFlexDate(), "date", null, 0, 1, Video.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVideo_Image(), ecorePackage.getEString(), "image", null, 0, 1, Video.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVideo_Subjects(), this.getSubject(), null, "subjects", null, 0, -1, Video.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(subjectEClass, Subject.class, "Subject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSubject_Title(), ecorePackage.getEString(), "title", null, 0, 1, Subject.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSubject_Tags(), ecorePackage.getEString(), "tags", null, 0, -1, Subject.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSubject_Date(), theTypesPackage.getEFlexDate(), "date", null, 0, 1, Subject.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(mediumTypeEEnum, MediumType.class, "MediumType");
    addEEnumLiteral(mediumTypeEEnum, MediumType.NOT_SET);
    addEEnumLiteral(mediumTypeEEnum, MediumType.CD_AUDIO);
    addEEnumLiteral(mediumTypeEEnum, MediumType.CDR_AUDIO);
    addEEnumLiteral(mediumTypeEEnum, MediumType.CDRW_AUDIO);
    addEEnumLiteral(mediumTypeEEnum, MediumType.LP);
    addEEnumLiteral(mediumTypeEEnum, MediumType.SINGLE);
    addEEnumLiteral(mediumTypeEEnum, MediumType.SACD);
    addEEnumLiteral(mediumTypeEEnum, MediumType.DVD_VIDEO);
    addEEnumLiteral(mediumTypeEEnum, MediumType.CD_ROM);
    addEEnumLiteral(mediumTypeEEnum, MediumType.DVD_ROM);
    addEEnumLiteral(mediumTypeEEnum, MediumType.HARDDISK);

    initEEnum(iWantEEnum, IWant.class, "IWant");
    addEEnumLiteral(iWantEEnum, IWant.YES);
    addEEnumLiteral(iWantEEnum, IWant.NO);
    addEEnumLiteral(iWantEEnum, IWant.DONT_KNOW);
    addEEnumLiteral(iWantEEnum, IWant.NOT_SET);

    initEEnum(informationTypeEEnum, InformationType.class, "InformationType");
    addEEnumLiteral(informationTypeEEnum, InformationType.NOT_SET);
    addEEnumLiteral(informationTypeEEnum, InformationType.MP3);
    addEEnumLiteral(informationTypeEEnum, InformationType.VINYL_ANALOG);
    addEEnumLiteral(informationTypeEEnum, InformationType.M2TS);
    addEEnumLiteral(informationTypeEEnum, InformationType.FLAC);
    addEEnumLiteral(informationTypeEEnum, InformationType.WAV);
    addEEnumLiteral(informationTypeEEnum, InformationType.APE);
    addEEnumLiteral(informationTypeEEnum, InformationType.AIFF);
    addEEnumLiteral(informationTypeEEnum, InformationType.DSF);

    initEEnum(collectionEEnum, Collection.class, "Collection");
    addEEnumLiteral(collectionEEnum, Collection.EASY_LISTENING);
    addEEnumLiteral(collectionEEnum, Collection.FILM_BACKING_TRACKS);
    addEEnumLiteral(collectionEEnum, Collection.FRANSTALIG);
    addEEnumLiteral(collectionEEnum, Collection.KLASSIEK);
    addEEnumLiteral(collectionEEnum, Collection.NEDERLANDSTALIG);
    addEEnumLiteral(collectionEEnum, Collection.POP);
    addEEnumLiteral(collectionEEnum, Collection.ROCK);
    addEEnumLiteral(collectionEEnum, Collection.NOT_SET);
    addEEnumLiteral(collectionEEnum, Collection.PUNK);

    // Create resource
    createResource(eNS_URI);
  }

} //MediadbPackageImpl
