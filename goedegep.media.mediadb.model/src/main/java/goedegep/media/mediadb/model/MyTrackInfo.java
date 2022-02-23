/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>My Track Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * iWant and iHaveOn are related as follows:
 * if iWant is set, iHaveOn shall not be set.
 * If iHaveOn is set, iWant shall not be set.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.MyTrackInfo#getCollection <em>Collection</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyTrackInfo#getIHaveOn <em>IHave On</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyTrackInfo#getIWant <em>IWant</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference <em>Compilation Track Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getMyTrackInfo()
 * @model
 * @generated
 */
public interface MyTrackInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Collection</b></em>' attribute.
   * The default value is <code>"<not-set>"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.Collection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Collection</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Collection</em>' attribute.
   * @see goedegep.media.mediadb.model.Collection
   * @see #isSetCollection()
   * @see #unsetCollection()
   * @see #setCollection(Collection)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyTrackInfo_Collection()
   * @model default="&lt;not-set&gt;" unsettable="true"
   * @generated
   */
  Collection getCollection();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCollection <em>Collection</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCollection <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCollection()
   * @see #getCollection()
   * @see #setCollection(Collection)
   * @generated
   */
  void unsetCollection();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCollection <em>Collection</em>}' attribute is set.
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
   * Returns the value of the '<em><b>IHave On</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.MediumInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>IHave On</em>' containment reference list.
   * @see #isSetIHaveOn()
   * @see #unsetIHaveOn()
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyTrackInfo_IHaveOn()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<MediumInfo> getIHaveOn();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getIHaveOn <em>IHave On</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIHaveOn()
   * @see #getIHaveOn()
   * @generated
   */
  void unsetIHaveOn();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getIHaveOn <em>IHave On</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>IHave On</em>' containment reference list is set.
   * @see #unsetIHaveOn()
   * @see #getIHaveOn()
   * @generated
   */
  boolean isSetIHaveOn();

  /**
   * Returns the value of the '<em><b>IWant</b></em>' attribute.
   * The default value is <code>"<not-set>"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.IWant}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>IWant</em>' attribute.
   * @see goedegep.media.mediadb.model.IWant
   * @see #isSetIWant()
   * @see #unsetIWant()
   * @see #setIWant(IWant)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyTrackInfo_IWant()
   * @model default="&lt;not-set&gt;" unsettable="true"
   * @generated
   */
  IWant getIWant();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getIWant <em>IWant</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getIWant <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIWant()
   * @see #getIWant()
   * @see #setIWant(IWant)
   * @generated
   */
  void unsetIWant();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getIWant <em>IWant</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Compilation Track Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compilation Track Reference</em>' reference.
   * @see #isSetCompilationTrackReference()
   * @see #unsetCompilationTrackReference()
   * @see #setCompilationTrackReference(TrackReference)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMyTrackInfo_CompilationTrackReference()
   * @model unsettable="true"
   * @generated
   */
  TrackReference getCompilationTrackReference();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference <em>Compilation Track Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compilation Track Reference</em>' reference.
   * @see #isSetCompilationTrackReference()
   * @see #unsetCompilationTrackReference()
   * @see #getCompilationTrackReference()
   * @generated
   */
  void setCompilationTrackReference(TrackReference value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference <em>Compilation Track Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompilationTrackReference()
   * @see #getCompilationTrackReference()
   * @see #setCompilationTrackReference(TrackReference)
   * @generated
   */
  void unsetCompilationTrackReference();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MyTrackInfo#getCompilationTrackReference <em>Compilation Track Reference</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Compilation Track Reference</em>' reference is set.
   * @see #unsetCompilationTrackReference()
   * @see #getCompilationTrackReference()
   * @see #setCompilationTrackReference(TrackReference)
   * @generated
   */
  boolean isSetCompilationTrackReference();

} // MyTrackInfo
