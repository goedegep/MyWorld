/**
 */
package goedegep.media.mediadb.model;

import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Album</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * In this model an album can be any collection of tracks. It can be an LP, CD, BD, or your own compilation of tracks.<br/>
 * An album consists of one or more discs. So even if you have your own compilation of tracks, which you only have on your harddisk, this is still handled via tracks on a disc.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Album#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getReleaseDate <em>Release Date</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getDiscs <em>Discs</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getImagesFront <em>Images Front</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getId <em>Id</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getPlayers <em>Players</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getImagesFrontInside <em>Images Front Inside</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getImagesBack <em>Images Back</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getImagesLabel <em>Images Label</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getDescriptionTitle <em>Description Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#getMyInfo <em>My Info</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Album#isSoundtrack <em>Soundtrack</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum()
 * @model
 * @generated
 */
public interface Album extends EObject {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The album title.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

  /**
   * Returns the value of the '<em><b>Release Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Release Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The date on which the album was released (issued).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Release Date</em>' attribute.
   * @see #isSetReleaseDate()
   * @see #unsetReleaseDate()
   * @see #setReleaseDate(FlexDate)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_ReleaseDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getReleaseDate();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getReleaseDate <em>Release Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Release Date</em>' attribute.
   * @see #isSetReleaseDate()
   * @see #unsetReleaseDate()
   * @see #getReleaseDate()
   * @generated
   */
  void setReleaseDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getReleaseDate <em>Release Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetReleaseDate()
   * @see #getReleaseDate()
   * @see #setReleaseDate(FlexDate)
   * @generated
   */
  void unsetReleaseDate();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getReleaseDate <em>Release Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Release Date</em>' attribute is set.
   * @see #unsetReleaseDate()
   * @see #getReleaseDate()
   * @see #setReleaseDate(FlexDate)
   * @generated
   */
  boolean isSetReleaseDate();

  /**
   * Returns the value of the '<em><b>Discs</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Disc}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Discs</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The discs of the album. Even if an album has no physicals discs, the track references will be divided over one of more discs.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Discs</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Discs()
   * @model containment="true"
   * @generated
   */
  EList<Disc> getDiscs();

  /**
   * Returns the value of the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Artist</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Artist</em>' reference.
   * @see #isSetArtist()
   * @see #unsetArtist()
   * @see #setArtist(Artist)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Artist()
   * @model unsettable="true"
   * @generated
   */
  Artist getArtist();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getArtist <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Artist</em>' reference.
   * @see #isSetArtist()
   * @see #unsetArtist()
   * @see #getArtist()
   * @generated
   */
  void setArtist(Artist value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getArtist <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetArtist()
   * @see #getArtist()
   * @see #setArtist(Artist)
   * @generated
   */
  void unsetArtist();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getArtist <em>Artist</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Artist</em>' reference is set.
   * @see #unsetArtist()
   * @see #getArtist()
   * @see #setArtist(Artist)
   * @generated
   */
  boolean isSetArtist();

  /**
   * Returns the value of the '<em><b>Images Front</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Images Front</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A list of file names for the images on the front of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Images Front</em>' attribute list.
   * @see #isSetImagesFront()
   * @see #unsetImagesFront()
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_ImagesFront()
   * @model unsettable="true"
   * @generated
   */
  EList<String> getImagesFront();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getImagesFront <em>Images Front</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetImagesFront()
   * @see #getImagesFront()
   * @generated
   */
  void unsetImagesFront();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getImagesFront <em>Images Front</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Images Front</em>' attribute list is set.
   * @see #unsetImagesFront()
   * @see #getImagesFront()
   * @generated
   */
  boolean isSetImagesFront();

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The official id of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #isSetId()
   * @see #unsetId()
   * @see #setId(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Id()
   * @model unsettable="true"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #isSetId()
   * @see #unsetId()
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetId()
   * @see #getId()
   * @see #setId(String)
   * @generated
   */
  void unsetId();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getId <em>Id</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Id</em>' attribute is set.
   * @see #unsetId()
   * @see #getId()
   * @see #setId(String)
   * @generated
   */
  boolean isSetId();

  /**
   * Returns the value of the '<em><b>Players</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Player}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Players</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The list of players on this album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Players</em>' containment reference list.
   * @see #isSetPlayers()
   * @see #unsetPlayers()
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Players()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<Player> getPlayers();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getPlayers <em>Players</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPlayers()
   * @see #getPlayers()
   * @generated
   */
  void unsetPlayers();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getPlayers <em>Players</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Players</em>' containment reference list is set.
   * @see #unsetPlayers()
   * @see #getPlayers()
   * @generated
   */
  boolean isSetPlayers();

  /**
   * Returns the value of the '<em><b>Images Front Inside</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Images Front Inside</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A list of file names for the images on the inside of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Images Front Inside</em>' attribute list.
   * @see #isSetImagesFrontInside()
   * @see #unsetImagesFrontInside()
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_ImagesFrontInside()
   * @model unsettable="true"
   * @generated
   */
  EList<String> getImagesFrontInside();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getImagesFrontInside <em>Images Front Inside</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetImagesFrontInside()
   * @see #getImagesFrontInside()
   * @generated
   */
  void unsetImagesFrontInside();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getImagesFrontInside <em>Images Front Inside</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Images Front Inside</em>' attribute list is set.
   * @see #unsetImagesFrontInside()
   * @see #getImagesFrontInside()
   * @generated
   */
  boolean isSetImagesFrontInside();

  /**
   * Returns the value of the '<em><b>Images Back</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Images Back</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A list of file names for the images on the back of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Images Back</em>' attribute list.
   * @see #isSetImagesBack()
   * @see #unsetImagesBack()
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_ImagesBack()
   * @model unsettable="true"
   * @generated
   */
  EList<String> getImagesBack();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getImagesBack <em>Images Back</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetImagesBack()
   * @see #getImagesBack()
   * @generated
   */
  void unsetImagesBack();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getImagesBack <em>Images Back</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Images Back</em>' attribute list is set.
   * @see #unsetImagesBack()
   * @see #getImagesBack()
   * @generated
   */
  boolean isSetImagesBack();

  /**
   * Returns the value of the '<em><b>Images Label</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Images Label</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A list of file names for the images on the label of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Images Label</em>' attribute list.
   * @see #isSetImagesLabel()
   * @see #unsetImagesLabel()
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_ImagesLabel()
   * @model unsettable="true"
   * @generated
   */
  EList<String> getImagesLabel();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getImagesLabel <em>Images Label</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetImagesLabel()
   * @see #getImagesLabel()
   * @generated
   */
  void unsetImagesLabel();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getImagesLabel <em>Images Label</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Images Label</em>' attribute list is set.
   * @see #unsetImagesLabel()
   * @see #getImagesLabel()
   * @generated
   */
  boolean isSetImagesLabel();

  /**
   * Returns the value of the '<em><b>Description Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A title for the description of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description Title</em>' attribute.
   * @see #isSetDescriptionTitle()
   * @see #unsetDescriptionTitle()
   * @see #setDescriptionTitle(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_DescriptionTitle()
   * @model unsettable="true"
   * @generated
   */
  String getDescriptionTitle();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getDescriptionTitle <em>Description Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description Title</em>' attribute.
   * @see #isSetDescriptionTitle()
   * @see #unsetDescriptionTitle()
   * @see #getDescriptionTitle()
   * @generated
   */
  void setDescriptionTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getDescriptionTitle <em>Description Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescriptionTitle()
   * @see #getDescriptionTitle()
   * @see #setDescriptionTitle(String)
   * @generated
   */
  void unsetDescriptionTitle();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getDescriptionTitle <em>Description Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description Title</em>' attribute is set.
   * @see #unsetDescriptionTitle()
   * @see #getDescriptionTitle()
   * @see #setDescriptionTitle(String)
   * @generated
   */
  boolean isSetDescriptionTitle();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A free text description of the album.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

  /**
   * Returns the value of the '<em><b>My Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>My Info</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Your personal information on the album.<br/>
   * This information shall always be there, as the {@albumType} is mandatory.
   * <!-- end-model-doc -->
   * @return the value of the '<em>My Info</em>' containment reference.
   * @see #isSetMyInfo()
   * @see #unsetMyInfo()
   * @see #setMyInfo(MyInfo)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_MyInfo()
   * @model containment="true" unsettable="true" required="true"
   * @generated
   */
  MyInfo getMyInfo();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#getMyInfo <em>My Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>My Info</em>' containment reference.
   * @see #isSetMyInfo()
   * @see #unsetMyInfo()
   * @see #getMyInfo()
   * @generated
   */
  void setMyInfo(MyInfo value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Album#getMyInfo <em>My Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMyInfo()
   * @see #getMyInfo()
   * @see #setMyInfo(MyInfo)
   * @generated
   */
  void unsetMyInfo();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Album#getMyInfo <em>My Info</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>My Info</em>' containment reference is set.
   * @see #unsetMyInfo()
   * @see #getMyInfo()
   * @see #setMyInfo(MyInfo)
   * @generated
   */
  boolean isSetMyInfo();

  /**
   * Returns the value of the '<em><b>Soundtrack</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Soundtrack</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * If set to true, this indicates that the album is a movie soundtrack.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Soundtrack</em>' attribute.
   * @see #setSoundtrack(boolean)
   * @see goedegep.media.mediadb.model.MediadbPackage#getAlbum_Soundtrack()
   * @model
   * @generated
   */
  boolean isSoundtrack();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Album#isSoundtrack <em>Soundtrack</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Soundtrack</em>' attribute.
   * @see #isSoundtrack()
   * @generated
   */
  void setSoundtrack(boolean value);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the track reference specified by disc number and track number.
   * 
   * @param discNr The disc number (starting at 1) on which a track is to be found.
   * @param trackNr The track number.
   * @return The {@code TrackReference} specified by {@code discNr} and {@code trackNr}, or null if such a track doesn't exist.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  TrackReference getTrackReference(Integer discNr, int trackNr);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get a player specified by the artist.
   * 
   * @param artist The {@code Artist} of the player.
   * @return the {@code Player} for the specified {@code artist}, or null if such a player doesn't exist.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  Player getPlayer(Artist artist);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get Artist and Title of the album.
   * <p>
   * The information is provided in the format: &lt;artist&gt; - &lt;title&gt;<br/>
   * where:<br/>
   *  &lt;artist&gt; is the name of the artist of the album, or '&lt;no artist&gt;' if this information isn't available.<br/>.
   *  &lt;title&gt; is the title of the album,  '&lt;no title&gt;' if title isn't set.
   * 
   * @return the artist and title of the album.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  String getArtistAndTitle();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Check whether the album is a multi disc album, i.e. the album consists of more than one disc.<br/>
   * The result of this method is the same as: album.getDiscs().size() > 1.
   * 
   * @return true if the album is a multi disc album, false otherwise.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  boolean isMultiDiscAlbum();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the Disc of a single disc album. A RuntimeException is thrown if the album is a multi disc album.
   * 
   * @return the disc of the album, or null if the album has no disc.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Disc getDisc();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Check whether I want this album or some tracks of the album, which means I still have to obtain the album or tracks of the album.<br/>
   * 
   * @return true if {@code iWant}  is set to YES at album level or on any track, false otherwise.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  boolean iWantAlbumOrTracksOfAlbum();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Check whether I still have to judge the album or tracks of the album.<br/>
   * This is the case if {@code iWant} is set to 'DONT KNOW' at album level or on any track.
   * 
   * @return true if I still have to judge this album, false otherwise.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  boolean iHaveToJudgeAlbumOrTracks();

} // Album
