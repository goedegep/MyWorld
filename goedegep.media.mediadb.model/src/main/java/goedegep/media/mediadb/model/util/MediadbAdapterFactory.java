/**
 */
package goedegep.media.mediadb.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import goedegep.media.mediadb.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage
 * @generated
 */
public class MediadbAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MediadbPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MediadbAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = MediadbPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject) object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MediadbSwitch<Adapter> modelSwitch = new MediadbSwitch<Adapter>() {
    @Override
    public Adapter caseTrack(Track object) {
      return createTrackAdapter();
    }

    @Override
    public Adapter caseMediaDb(MediaDb object) {
      return createMediaDbAdapter();
    }

    @Override
    public Adapter caseArtist(Artist object) {
      return createArtistAdapter();
    }

    @Override
    public Adapter caseAlbum(Album object) {
      return createAlbumAdapter();
    }

    @Override
    public Adapter caseTrackReference(TrackReference object) {
      return createTrackReferenceAdapter();
    }

    @Override
    public Adapter caseDisc(Disc object) {
      return createDiscAdapter();
    }

    @Override
    public Adapter caseMediumInfo(MediumInfo object) {
      return createMediumInfoAdapter();
    }

    @Override
    public Adapter caseMyInfo(MyInfo object) {
      return createMyInfoAdapter();
    }

    @Override
    public Adapter casePlayer(Player object) {
      return createPlayerAdapter();
    }

    @Override
    public Adapter caseTrackPart(TrackPart object) {
      return createTrackPartAdapter();
    }

    @Override
    public Adapter caseMyTrackInfo(MyTrackInfo object) {
      return createMyTrackInfoAdapter();
    }

    @Override
    public Adapter caseDiscAndTrackNrs(DiscAndTrackNrs object) {
      return createDiscAndTrackNrsAdapter();
    }

    @Override
    public Adapter caseTrackCollection(TrackCollection object) {
      return createTrackCollectionAdapter();
    }

    @Override
    public Adapter caseVideo(Video object) {
      return createVideoAdapter();
    }

    @Override
    public Adapter caseSubject(Subject object) {
      return createSubjectAdapter();
    }

    @Override
    public Adapter defaultCase(EObject object) {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject) target);
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Track <em>Track</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Track
   * @generated
   */
  public Adapter createTrackAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.MediaDb <em>Media Db</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.MediaDb
   * @generated
   */
  public Adapter createMediaDbAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Artist <em>Artist</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Artist
   * @generated
   */
  public Adapter createArtistAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Album <em>Album</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Album
   * @generated
   */
  public Adapter createAlbumAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.TrackReference <em>Track Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.TrackReference
   * @generated
   */
  public Adapter createTrackReferenceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Disc <em>Disc</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Disc
   * @generated
   */
  public Adapter createDiscAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.MediumInfo <em>Medium Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.MediumInfo
   * @generated
   */
  public Adapter createMediumInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.MyInfo <em>My Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.MyInfo
   * @generated
   */
  public Adapter createMyInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Player <em>Player</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Player
   * @generated
   */
  public Adapter createPlayerAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.TrackPart <em>Track Part</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.TrackPart
   * @generated
   */
  public Adapter createTrackPartAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.MyTrackInfo <em>My Track Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.MyTrackInfo
   * @generated
   */
  public Adapter createMyTrackInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.DiscAndTrackNrs <em>Disc And Track Nrs</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.DiscAndTrackNrs
   * @generated
   */
  public Adapter createDiscAndTrackNrsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.TrackCollection <em>Track Collection</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.TrackCollection
   * @generated
   */
  public Adapter createTrackCollectionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Video <em>Video</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Video
   * @generated
   */
  public Adapter createVideoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.mediadb.model.Subject <em>Subject</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.mediadb.model.Subject
   * @generated
   */
  public Adapter createSubjectAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //MediaDbAdapterFactory
