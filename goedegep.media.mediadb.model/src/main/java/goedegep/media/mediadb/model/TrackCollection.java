/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.TrackCollection#getCollection <em>Collection</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.TrackCollection#getTrackReferences <em>Track References</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getTrackCollection()
 * @model
 * @generated
 */
public interface TrackCollection extends EObject {
  /**
   * Returns the value of the '<em><b>Collection</b></em>' attribute.
   * The default value is <code>"<not-set>"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.Collection}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Collection</em>' attribute.
   * @see goedegep.media.mediadb.model.Collection
   * @see #isSetCollection()
   * @see #unsetCollection()
   * @see #setCollection(Collection)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackCollection_Collection()
   * @model default="&lt;not-set&gt;" unsettable="true"
   * @generated
   */
  Collection getCollection();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.TrackCollection#getCollection <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Collection</em>' attribute.
   * @see goedegep.media.mediadb.model.Collection
   * @see #isSetCollection()
   * @see #unsetCollection()
   * @see #getCollection()
   * @generated
   */
  void setCollection(Collection value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.TrackCollection#getCollection <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCollection()
   * @see #getCollection()
   * @see #setCollection(Collection)
   * @generated
   */
  void unsetCollection();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.TrackCollection#getCollection <em>Collection</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Collection</em>' attribute is set.
   * @see #unsetCollection()
   * @see #getCollection()
   * @see #setCollection(Collection)
   * @generated
   */
  boolean isSetCollection();

  /**
   * Returns the value of the '<em><b>Track References</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.TrackReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Track References</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrackCollection_TrackReferences()
   * @model containment="true"
   * @generated
   */
  EList<TrackReference> getTrackReferences();

} // TrackCollection
