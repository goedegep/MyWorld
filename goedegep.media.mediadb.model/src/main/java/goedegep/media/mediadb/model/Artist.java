/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artist</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Artist#getContainerArtist <em>Container Artist</em>}</li>
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

} // Artist
