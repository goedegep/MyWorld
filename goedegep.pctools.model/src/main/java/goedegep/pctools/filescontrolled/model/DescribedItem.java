/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Described Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DescribedItem#getItem <em>Item</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DescribedItem#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDescribedItem()
 * @model
 * @generated
 */
public interface DescribedItem extends EObject {
  /**
   * Returns the value of the '<em><b>Item</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Item</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Item</em>' attribute.
   * @see #isSetItem()
   * @see #unsetItem()
   * @see #setItem(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDescribedItem_Item()
   * @model unsettable="true"
   * @generated
   */
  String getItem();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getItem <em>Item</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Item</em>' attribute.
   * @see #isSetItem()
   * @see #unsetItem()
   * @see #getItem()
   * @generated
   */
  void setItem(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getItem <em>Item</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetItem()
   * @see #getItem()
   * @see #setItem(String)
   * @generated
   */
  void unsetItem();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getItem <em>Item</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Item</em>' attribute is set.
   * @see #unsetItem()
   * @see #getItem()
   * @see #setItem(String)
   * @generated
   */
  boolean isSetItem();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDescribedItem_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

} // DescribedItem
