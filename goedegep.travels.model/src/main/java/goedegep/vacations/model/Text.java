/**
 */
package goedegep.vacations.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Element Text</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Text#getText <em>Text</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getText()
 * @model
 * @generated
 */
public interface Text extends VacationElement {

  /**
   * Returns the value of the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Text</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Text</em>' attribute.
   * @see #isSetText()
   * @see #unsetText()
   * @see #setText(String)
   * @see goedegep.vacations.model.VacationsPackage#getText_Text()
   * @model unsettable="true"
   * @generated
   */
  String getText();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Text#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text</em>' attribute.
   * @see #isSetText()
   * @see #unsetText()
   * @see #getText()
   * @generated
   */
  void setText(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Text#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetText()
   * @see #getText()
   * @see #setText(String)
   * @generated
   */
  void unsetText();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Text#getText <em>Text</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Text</em>' attribute is set.
   * @see #unsetText()
   * @see #getText()
   * @see #setText(String)
   * @generated
   */
  boolean isSetText();
} // VacationElementText
