/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artist</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This inteface provides information on an artist. An artist can be the artist of an album or track, or a player on an album or track. An artist can also be the container artist of an  artist.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getPhoto <em>Photo</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getStyle <em>Style</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getMyComments <em>My Comments</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getSample <em>Sample</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getArtist()
 * @model
 * @generated
 */
public interface Artist extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name of the artist.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_Name()
   * @model unsettable="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Container Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Container Artist</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Some artists have albums under various names, which can best be grouped. For example "Bob Marley" and "Bob Marley The Wailers". In this case "Bob Marley" is the 'containerArtist' of "Bob Marley The Wailers".
   * <!-- end-model-doc -->
   * @return the value of the '<em>Container Artist</em>' reference.
   * @see #isSetContainerArtist()
   * @see #unsetContainerArtist()
   * @see #setContainerArtist(Artist)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_ContainerArtist()
   * @model unsettable="true"
   * @generated
   */
  Artist getContainerArtist();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Container Artist</em>' reference.
   * @see #isSetContainerArtist()
   * @see #unsetContainerArtist()
   * @see #getContainerArtist()
   * @generated
   */
  void setContainerArtist(Artist value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetContainerArtist()
   * @see #getContainerArtist()
   * @see #setContainerArtist(Artist)
   * @generated
   */
  void unsetContainerArtist();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Container Artist</em>' reference is set.
   * @see #unsetContainerArtist()
   * @see #getContainerArtist()
   * @see #setContainerArtist(Artist)
   * @generated
   */
  boolean isSetContainerArtist();

  /**
   * Returns the value of the '<em><b>Photo</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The filename of a photo of the artist.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Photo</em>' attribute.
   * @see #isSetPhoto()
   * @see #unsetPhoto()
   * @see #setPhoto(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_Photo()
   * @model unsettable="true"
   * @generated
   */
  String getPhoto();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getPhoto <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Photo</em>' attribute.
   * @see #isSetPhoto()
   * @see #unsetPhoto()
   * @see #getPhoto()
   * @generated
   */
  void setPhoto(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getPhoto <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPhoto()
   * @see #getPhoto()
   * @see #setPhoto(String)
   * @generated
   */
  void unsetPhoto();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getPhoto <em>Photo</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Photo</em>' attribute is set.
   * @see #unsetPhoto()
   * @see #getPhoto()
   * @see #setPhoto(String)
   * @generated
   */
  boolean isSetPhoto();

  /**
   * Returns the value of the '<em><b>Style</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A free format style of the artist.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Style</em>' attribute.
   * @see #isSetStyle()
   * @see #unsetStyle()
   * @see #setStyle(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_Style()
   * @model unsettable="true"
   * @generated
   */
  String getStyle();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getStyle <em>Style</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Style</em>' attribute.
   * @see #isSetStyle()
   * @see #unsetStyle()
   * @see #getStyle()
   * @generated
   */
  void setStyle(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getStyle <em>Style</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStyle()
   * @see #getStyle()
   * @see #setStyle(String)
   * @generated
   */
  void unsetStyle();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getStyle <em>Style</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Style</em>' attribute is set.
   * @see #unsetStyle()
   * @see #getStyle()
   * @see #setStyle(String)
   * @generated
   */
  boolean isSetStyle();

  /**
   * Returns the value of the '<em><b>My Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * My comments about the artist.
   * <!-- end-model-doc -->
   * @return the value of the '<em>My Comments</em>' attribute.
   * @see #isSetMyComments()
   * @see #unsetMyComments()
   * @see #setMyComments(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_MyComments()
   * @model unsettable="true"
   * @generated
   */
  String getMyComments();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getMyComments <em>My Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>My Comments</em>' attribute.
   * @see #isSetMyComments()
   * @see #unsetMyComments()
   * @see #getMyComments()
   * @generated
   */
  void setMyComments(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getMyComments <em>My Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMyComments()
   * @see #getMyComments()
   * @see #setMyComments(String)
   * @generated
   */
  void unsetMyComments();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getMyComments <em>My Comments</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>My Comments</em>' attribute is set.
   * @see #unsetMyComments()
   * @see #getMyComments()
   * @see #setMyComments(String)
   * @generated
   */
  boolean isSetMyComments();

  /**
   * Returns the value of the '<em><b>Sample</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sample</em>' containment reference.
   * @see #isSetSample()
   * @see #unsetSample()
   * @see #setSample(TrackReference)
   * @see goedegep.media.mediadb.model.MediadbPackage#getArtist_Sample()
   * @model containment="true" unsettable="true"
   * @generated
   */
  TrackReference getSample();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Artist#getSample <em>Sample</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sample</em>' containment reference.
   * @see #isSetSample()
   * @see #unsetSample()
   * @see #getSample()
   * @generated
   */
  void setSample(TrackReference value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Artist#getSample <em>Sample</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSample()
   * @see #getSample()
   * @see #setSample(TrackReference)
   * @generated
   */
  void unsetSample();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Artist#getSample <em>Sample</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Sample</em>' containment reference is set.
   * @see #unsetSample()
   * @see #getSample()
   * @see #setSample(TrackReference)
   * @generated
   */
  boolean isSetSample();

} // Artist
