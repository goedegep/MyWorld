/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This used for (my) compilation albums.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.TrackReference#getBonusTrack <em>Bonus Track</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.TrackReference#getMyTrackInfo <em>My Track Info</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.TrackReference#isOriginalAlbumTrackReference <em>Original Album Track Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getTrackReference()
 * @model
 * @generated
 */
public interface TrackReference extends EObject {
  /**
   * Returns the value of the '<em><b>Track</b></em>' reference.
   * It is bidirectional and its opposite is '{@link goedegep.media.mediadb.model.Track#getReferredBy <em>Referred By</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Track</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Track</em>' reference.
   * @see #isSetTrack()
   * @see #unsetTrack()
   * @see #setTrack(Track)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackReference_Track()
   * @see goedegep.media.mediadb.model.Track#getReferredBy
   * @model opposite="referredBy" unsettable="true" required="true"
   * @generated
   */
  Track getTrack();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Track</em>' reference.
   * @see #isSetTrack()
   * @see #unsetTrack()
   * @see #getTrack()
   * @generated
   */
  void setTrack(Track value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTrack()
   * @see #getTrack()
   * @see #setTrack(Track)
   * @generated
   */
  void unsetTrack();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Track</em>' reference is set.
   * @see #unsetTrack()
   * @see #getTrack()
   * @see #setTrack(Track)
   * @generated
   */
  boolean isSetTrack();

  /**
   * Returns the value of the '<em><b>Bonus Track</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bonus Track</em>' attribute.
   * @see #isSetBonusTrack()
   * @see #unsetBonusTrack()
   * @see #setBonusTrack(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackReference_BonusTrack()
   * @model unsettable="true"
   * @generated
   */
  String getBonusTrack();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getBonusTrack <em>Bonus Track</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bonus Track</em>' attribute.
   * @see #isSetBonusTrack()
   * @see #unsetBonusTrack()
   * @see #getBonusTrack()
   * @generated
   */
  void setBonusTrack(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getBonusTrack <em>Bonus Track</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBonusTrack()
   * @see #getBonusTrack()
   * @see #setBonusTrack(String)
   * @generated
   */
  void unsetBonusTrack();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.TrackReference#getBonusTrack <em>Bonus Track</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Bonus Track</em>' attribute is set.
   * @see #unsetBonusTrack()
   * @see #getBonusTrack()
   * @see #setBonusTrack(String)
   * @generated
   */
  boolean isSetBonusTrack();

  /**
   * Returns the value of the '<em><b>My Track Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>My Track Info</em>' containment reference.
   * @see #isSetMyTrackInfo()
   * @see #unsetMyTrackInfo()
   * @see #setMyTrackInfo(MyTrackInfo)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackReference_MyTrackInfo()
   * @model containment="true" unsettable="true"
   * @generated
   */
  MyTrackInfo getMyTrackInfo();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getMyTrackInfo <em>My Track Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>My Track Info</em>' containment reference.
   * @see #isSetMyTrackInfo()
   * @see #unsetMyTrackInfo()
   * @see #getMyTrackInfo()
   * @generated
   */
  void setMyTrackInfo(MyTrackInfo value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.TrackReference#getMyTrackInfo <em>My Track Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMyTrackInfo()
   * @see #getMyTrackInfo()
   * @see #setMyTrackInfo(MyTrackInfo)
   * @generated
   */
  void unsetMyTrackInfo();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.TrackReference#getMyTrackInfo <em>My Track Info</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>My Track Info</em>' containment reference is set.
   * @see #unsetMyTrackInfo()
   * @see #getMyTrackInfo()
   * @see #setMyTrackInfo(MyTrackInfo)
   * @generated
   */
  boolean isSetMyTrackInfo();

  /**
   * Returns the value of the '<em><b>Original Album Track Reference</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * If set this track reference indicates that this track is the track on the original album.<br/>
   * This can e.g. be used to create the file name for a track in a compilation album or a track collection, where the original album release year and tiltle can be part of the name.<br/>
   * You can of course use this attribute in a practical way. If you only have the album information of a compilation album containing a certain track, you can still set this as the original album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Original Album Track Reference</em>' attribute.
   * @see #isSetOriginalAlbumTrackReference()
   * @see #unsetOriginalAlbumTrackReference()
   * @see #setOriginalAlbumTrackReference(boolean)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackReference_OriginalAlbumTrackReference()
   * @model unsettable="true"
   * @generated
   */
  boolean isOriginalAlbumTrackReference();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.TrackReference#isOriginalAlbumTrackReference <em>Original Album Track Reference</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Original Album Track Reference</em>' attribute.
   * @see #isSetOriginalAlbumTrackReference()
   * @see #unsetOriginalAlbumTrackReference()
   * @see #isOriginalAlbumTrackReference()
   * @generated
   */
  void setOriginalAlbumTrackReference(boolean value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.TrackReference#isOriginalAlbumTrackReference <em>Original Album Track Reference</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOriginalAlbumTrackReference()
   * @see #isOriginalAlbumTrackReference()
   * @see #setOriginalAlbumTrackReference(boolean)
   * @generated
   */
  void unsetOriginalAlbumTrackReference();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.TrackReference#isOriginalAlbumTrackReference <em>Original Album Track Reference</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Original Album Track Reference</em>' attribute is set.
   * @see #unsetOriginalAlbumTrackReference()
   * @see #isOriginalAlbumTrackReference()
   * @see #setOriginalAlbumTrackReference(boolean)
   * @generated
   */
  boolean isSetOriginalAlbumTrackReference();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  int getTrackNr();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  Disc getDisc();

} // TrackReference
