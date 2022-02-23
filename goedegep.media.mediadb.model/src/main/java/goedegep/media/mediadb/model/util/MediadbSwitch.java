/**
 */
package goedegep.media.mediadb.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import goedegep.media.mediadb.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage
 * @generated
 */
public class MediadbSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MediadbPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MediadbSwitch() {
    if (modelPackage == null) {
      modelPackage = MediadbPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
    case MediadbPackage.TRACK: {
      Track track = (Track) theEObject;
      T result = caseTrack(track);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.MEDIA_DB: {
      MediaDb mediaDb = (MediaDb) theEObject;
      T result = caseMediaDb(mediaDb);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.ARTIST: {
      Artist artist = (Artist) theEObject;
      T result = caseArtist(artist);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.ALBUM: {
      Album album = (Album) theEObject;
      T result = caseAlbum(album);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.MY_COMPILATION: {
      MyCompilation myCompilation = (MyCompilation) theEObject;
      T result = caseMyCompilation(myCompilation);
      if (result == null)
        result = caseAlbum(myCompilation);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.TRACK_REFERENCE: {
      TrackReference trackReference = (TrackReference) theEObject;
      T result = caseTrackReference(trackReference);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.DISC: {
      Disc disc = (Disc) theEObject;
      T result = caseDisc(disc);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.MEDIUM_INFO: {
      MediumInfo mediumInfo = (MediumInfo) theEObject;
      T result = caseMediumInfo(mediumInfo);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.MY_INFO: {
      MyInfo myInfo = (MyInfo) theEObject;
      T result = caseMyInfo(myInfo);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.PLAYER: {
      Player player = (Player) theEObject;
      T result = casePlayer(player);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.TRACK_PART: {
      TrackPart trackPart = (TrackPart) theEObject;
      T result = caseTrackPart(trackPart);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.MY_TRACK_INFO: {
      MyTrackInfo myTrackInfo = (MyTrackInfo) theEObject;
      T result = caseMyTrackInfo(myTrackInfo);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.DISC_AND_TRACK_NRS: {
      DiscAndTrackNrs discAndTrackNrs = (DiscAndTrackNrs) theEObject;
      T result = caseDiscAndTrackNrs(discAndTrackNrs);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case MediadbPackage.TRACK_COLLECTION: {
      TrackCollection trackCollection = (TrackCollection) theEObject;
      T result = caseTrackCollection(trackCollection);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    default:
      return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Track</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Track</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrack(Track object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Media Db</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Media Db</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMediaDb(MediaDb object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Artist</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Artist</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseArtist(Artist object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Album</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Album</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAlbum(Album object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>My Compilation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>My Compilation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMyCompilation(MyCompilation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Track Reference</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Track Reference</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrackReference(TrackReference object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Disc</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Disc</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDisc(Disc object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Medium Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Medium Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMediumInfo(MediumInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>My Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>My Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMyInfo(MyInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Player</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Player</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePlayer(Player object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Track Part</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Track Part</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrackPart(TrackPart object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>My Track Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>My Track Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMyTrackInfo(MyTrackInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Disc And Track Nrs</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Disc And Track Nrs</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDiscAndTrackNrs(DiscAndTrackNrs object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Track Collection</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Track Collection</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrackCollection(TrackCollection object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //MediaDbSwitch
