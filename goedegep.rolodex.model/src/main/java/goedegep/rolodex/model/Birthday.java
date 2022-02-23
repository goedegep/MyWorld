/**
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Birthday</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Birthday#getMonth <em>Month</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Birthday#getDay <em>Day</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Birthday#getYear <em>Year</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getBirthday()
 * @model
 * @generated
 */
public interface Birthday extends EObject {
  /**
   * Returns the value of the '<em><b>Month</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Month</em>' attribute.
   * @see #isSetMonth()
   * @see #unsetMonth()
   * @see #setMonth(int)
   * @see goedegep.rolodex.model.RolodexPackage#getBirthday_Month()
   * @model unsettable="true"
   * @generated
   */
  int getMonth();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Birthday#getMonth <em>Month</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Month</em>' attribute.
   * @see #isSetMonth()
   * @see #unsetMonth()
   * @see #getMonth()
   * @generated
   */
  void setMonth(int value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Birthday#getMonth <em>Month</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMonth()
   * @see #getMonth()
   * @see #setMonth(int)
   * @generated
   */
  void unsetMonth();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Birthday#getMonth <em>Month</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Month</em>' attribute is set.
   * @see #unsetMonth()
   * @see #getMonth()
   * @see #setMonth(int)
   * @generated
   */
  boolean isSetMonth();

  /**
   * Returns the value of the '<em><b>Day</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Day</em>' attribute.
   * @see #isSetDay()
   * @see #unsetDay()
   * @see #setDay(int)
   * @see goedegep.rolodex.model.RolodexPackage#getBirthday_Day()
   * @model unsettable="true"
   * @generated
   */
  int getDay();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Birthday#getDay <em>Day</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Day</em>' attribute.
   * @see #isSetDay()
   * @see #unsetDay()
   * @see #getDay()
   * @generated
   */
  void setDay(int value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Birthday#getDay <em>Day</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDay()
   * @see #getDay()
   * @see #setDay(int)
   * @generated
   */
  void unsetDay();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Birthday#getDay <em>Day</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Day</em>' attribute is set.
   * @see #unsetDay()
   * @see #getDay()
   * @see #setDay(int)
   * @generated
   */
  boolean isSetDay();

  /**
   * Returns the value of the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Year</em>' attribute.
   * @see #setYear(int)
   * @see goedegep.rolodex.model.RolodexPackage#getBirthday_Year()
   * @model
   * @generated
   */
  int getYear();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Birthday#getYear <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Year</em>' attribute.
   * @see #getYear()
   * @generated
   */
  void setYear(int value);

} // Birthday
