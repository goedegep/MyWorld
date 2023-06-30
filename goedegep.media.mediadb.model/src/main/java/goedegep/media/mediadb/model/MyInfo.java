/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>My Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#getAlbumReferences <em>Album References</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#getMyComments <em>My Comments</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#isIveHadOnLP <em>Ive Had On LP</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#getIWant <em>IWant</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#getIHaveOn <em>IHave On</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyInfo#getAlbumType <em>Album Type</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo()
 * @model
 * @generated
 */
public interface MyInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Album References</b></em>' reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Album}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Album References</em>' reference list.
   * @see #isSetAlbumReferences()
   * @see #unsetAlbumReferences()
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_AlbumReferences()
   * @model unsettable="true"
   * @generated
   */
  EList<Album> getAlbumReferences();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getAlbumReferences <em>Album References</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAlbumReferences()
   * @see #getAlbumReferences()
   * @generated
   */
  void unsetAlbumReferences();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyInfo#getAlbumReferences <em>Album References</em>}' reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Album References</em>' reference list is set.
   * @see #unsetAlbumReferences()
   * @see #getAlbumReferences()
   * @generated
   */
  boolean isSetAlbumReferences();

  /**
   * Returns the value of the '<em><b>IWant</b></em>' attribute.
   * The default value is <code>"<not-set>"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.IWant}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * This attribute is only used if there is no disc/track information present. If disc/track information is present the attribute shall be unset.
   * <!-- end-model-doc -->
   * @return the value of the '<em>IWant</em>' attribute.
   * @see goedegep.media.mediadb.model.IWant
   * @see #isSetIWant()
   * @see #unsetIWant()
   * @see #setIWant(IWant)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_IWant()
   * @model default="&lt;not-set&gt;" unsettable="true"
   * @generated
   */
  IWant getIWant();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getIWant <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>IWant</em>' attribute.
   * @see goedegep.media.mediadb.model.IWant
   * @see #isSetIWant()
   * @see #unsetIWant()
   * @see #getIWant()
   * @generated
   */
  void setIWant(IWant value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getIWant <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIWant()
   * @see #getIWant()
   * @see #setIWant(IWant)
   * @generated
   */
  void unsetIWant();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyInfo#getIWant <em>IWant</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>IWant</em>' attribute is set.
   * @see #unsetIWant()
   * @see #getIWant()
   * @see #setIWant(IWant)
   * @generated
   */
  boolean isSetIWant();

  /**
   * Returns the value of the '<em><b>IHave On</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.MediumInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>IHave On</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_IHaveOn()
   * @model containment="true"
   * @generated
   */
  EList<MediumInfo> getIHaveOn();

  /**
   * Returns the value of the '<em><b>Album Type</b></em>' attribute.
   * The default value is <code>"NORMAL_ALBUM"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.AlbumType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Album Type</em>' attribute.
   * @see goedegep.media.mediadb.model.AlbumType
   * @see #isSetAlbumType()
   * @see #unsetAlbumType()
   * @see #setAlbumType(AlbumType)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_AlbumType()
   * @model default="NORMAL_ALBUM" unsettable="true"
   * @generated
   */
  AlbumType getAlbumType();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getAlbumType <em>Album Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Album Type</em>' attribute.
   * @see goedegep.media.mediadb.model.AlbumType
   * @see #isSetAlbumType()
   * @see #unsetAlbumType()
   * @see #getAlbumType()
   * @generated
   */
  void setAlbumType(AlbumType value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getAlbumType <em>Album Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAlbumType()
   * @see #getAlbumType()
   * @see #setAlbumType(AlbumType)
   * @generated
   */
  void unsetAlbumType();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyInfo#getAlbumType <em>Album Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Album Type</em>' attribute is set.
   * @see #unsetAlbumType()
   * @see #getAlbumType()
   * @see #setAlbumType(AlbumType)
   * @generated
   */
  boolean isSetAlbumType();

  /**
   * Returns the value of the '<em><b>My Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>My Comments</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>My Comments</em>' attribute.
   * @see #isSetMyComments()
   * @see #unsetMyComments()
   * @see #setMyComments(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_MyComments()
   * @model unsettable="true"
   * @generated
   */
  String getMyComments();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getMyComments <em>My Comments</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyInfo#getMyComments <em>My Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMyComments()
   * @see #getMyComments()
   * @see #setMyComments(String)
   * @generated
   */
  void unsetMyComments();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyInfo#getMyComments <em>My Comments</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Ive Had On LP</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ive Had On LP</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ive Had On LP</em>' attribute.
   * @see #setIveHadOnLP(boolean)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyInfo_IveHadOnLP()
   * @model required="true"
   * @generated
   */
  boolean isIveHadOnLP();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyInfo#isIveHadOnLP <em>Ive Had On LP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ive Had On LP</em>' attribute.
   * @see #isIveHadOnLP()
   * @generated
   */
  void setIveHadOnLP(boolean value);

} // MyInfo
