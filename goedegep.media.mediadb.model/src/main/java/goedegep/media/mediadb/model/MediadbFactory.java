/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage
 * @generated
 */
public interface MediadbFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MediadbFactory eINSTANCE = goedegep.media.mediadb.model.impl.MediadbFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Track</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Track</em>'.
   * @generated
   */
  Track createTrack();

  /**
   * Returns a new object of class '<em>Media Db</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Media Db</em>'.
   * @generated
   */
  MediaDb createMediaDb();

  /**
   * Returns a new object of class '<em>Artist</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Artist</em>'.
   * @generated
   */
  Artist createArtist();

  /**
   * Returns a new object of class '<em>Album</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Album</em>'.
   * @generated
   */
  Album createAlbum();

  /**
   * Returns a new object of class '<em>My Compilation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>My Compilation</em>'.
   * @generated
   */
  MyCompilation createMyCompilation();

  /**
   * Returns a new object of class '<em>Track Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Track Reference</em>'.
   * @generated
   */
  TrackReference createTrackReference();

  /**
   * Returns a new object of class '<em>Disc</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Disc</em>'.
   * @generated
   */
  Disc createDisc();

  /**
   * Returns a new object of class '<em>Medium Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Medium Info</em>'.
   * @generated
   */
  MediumInfo createMediumInfo();

  /**
   * Returns a new object of class '<em>My Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>My Info</em>'.
   * @generated
   */
  MyInfo createMyInfo();

  /**
   * Returns a new object of class '<em>Player</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Player</em>'.
   * @generated
   */
  Player createPlayer();

  /**
   * Returns a new object of class '<em>Track Part</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Track Part</em>'.
   * @generated
   */
  TrackPart createTrackPart();

  /**
   * Returns a new object of class '<em>My Track Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>My Track Info</em>'.
   * @generated
   */
  MyTrackInfo createMyTrackInfo();

  /**
   * Returns a new object of class '<em>Disc And Track Nrs</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Disc And Track Nrs</em>'.
   * @generated
   */
  DiscAndTrackNrs createDiscAndTrackNrs();

  /**
   * Returns a new object of class '<em>Track Collection</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Track Collection</em>'.
   * @generated
   */
  TrackCollection createTrackCollection();

  /**
   * Returns a new object of class '<em>Video</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Video</em>'.
   * @generated
   */
  Video createVideo();

  /**
   * Returns a new object of class '<em>Subject</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Subject</em>'.
   * @generated
   */
  Subject createSubject();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  MediadbPackage getMediadbPackage();

} //MediadbFactory
