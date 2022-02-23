/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Person#getFirstname <em>Firstname</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getInfix <em>Infix</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getSurname <em>Surname</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getInitials <em>Initials</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getGender <em>Gender</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getId <em>Id</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getFamily <em>Family</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Person#getBirthday <em>Birthday</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends PhoneNumberHolder, AddressHolder, Description, Archive {
  /**
   * Returns the value of the '<em><b>Infix</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Infix</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Infix</em>' attribute.
   * @see #isSetInfix()
   * @see #unsetInfix()
   * @see #setInfix(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Infix()
   * @model unsettable="true"
   * @generated
   */
  String getInfix();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getInfix <em>Infix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Infix</em>' attribute.
   * @see #isSetInfix()
   * @see #unsetInfix()
   * @see #getInfix()
   * @generated
   */
  void setInfix(String value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getInfix <em>Infix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInfix()
   * @see #getInfix()
   * @see #setInfix(String)
   * @generated
   */
  void unsetInfix();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getInfix <em>Infix</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Infix</em>' attribute is set.
   * @see #unsetInfix()
   * @see #getInfix()
   * @see #setInfix(String)
   * @generated
   */
  boolean isSetInfix();

  /**
   * Returns the value of the '<em><b>Firstname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Firstname</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Firstname</em>' attribute.
   * @see #isSetFirstname()
   * @see #unsetFirstname()
   * @see #setFirstname(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Firstname()
   * @model unsettable="true"
   * @generated
   */
  String getFirstname();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getFirstname <em>Firstname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Firstname</em>' attribute.
   * @see #isSetFirstname()
   * @see #unsetFirstname()
   * @see #getFirstname()
   * @generated
   */
  void setFirstname(String value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getFirstname <em>Firstname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFirstname()
   * @see #getFirstname()
   * @see #setFirstname(String)
   * @generated
   */
  void unsetFirstname();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getFirstname <em>Firstname</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Firstname</em>' attribute is set.
   * @see #unsetFirstname()
   * @see #getFirstname()
   * @see #setFirstname(String)
   * @generated
   */
  boolean isSetFirstname();

  /**
   * Returns the value of the '<em><b>Surname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Surname</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Surname</em>' attribute.
   * @see #isSetSurname()
   * @see #unsetSurname()
   * @see #setSurname(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Surname()
   * @model unsettable="true"
   * @generated
   */
  String getSurname();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getSurname <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Surname</em>' attribute.
   * @see #isSetSurname()
   * @see #unsetSurname()
   * @see #getSurname()
   * @generated
   */
  void setSurname(String value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getSurname <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSurname()
   * @see #getSurname()
   * @see #setSurname(String)
   * @generated
   */
  void unsetSurname();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getSurname <em>Surname</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Surname</em>' attribute is set.
   * @see #unsetSurname()
   * @see #getSurname()
   * @see #setSurname(String)
   * @generated
   */
  boolean isSetSurname();

  /**
   * Returns the value of the '<em><b>Initials</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initials</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initials</em>' attribute.
   * @see #isSetInitials()
   * @see #unsetInitials()
   * @see #setInitials(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Initials()
   * @model unsettable="true"
   * @generated
   */
  String getInitials();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getInitials <em>Initials</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initials</em>' attribute.
   * @see #isSetInitials()
   * @see #unsetInitials()
   * @see #getInitials()
   * @generated
   */
  void setInitials(String value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getInitials <em>Initials</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInitials()
   * @see #getInitials()
   * @see #setInitials(String)
   * @generated
   */
  void unsetInitials();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getInitials <em>Initials</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Initials</em>' attribute is set.
   * @see #unsetInitials()
   * @see #getInitials()
   * @see #setInitials(String)
   * @generated
   */
  boolean isSetInitials();

  /**
   * Returns the value of the '<em><b>Gender</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.rolodex.model.Gender}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Gender</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Gender</em>' attribute.
   * @see goedegep.rolodex.model.Gender
   * @see #isSetGender()
   * @see #unsetGender()
   * @see #setGender(Gender)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Gender()
   * @model unsettable="true"
   * @generated
   */
  Gender getGender();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getGender <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Gender</em>' attribute.
   * @see goedegep.rolodex.model.Gender
   * @see #isSetGender()
   * @see #unsetGender()
   * @see #getGender()
   * @generated
   */
  void setGender(Gender value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getGender <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetGender()
   * @see #getGender()
   * @see #setGender(Gender)
   * @generated
   */
  void unsetGender();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getGender <em>Gender</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Gender</em>' attribute is set.
   * @see #unsetGender()
   * @see #getGender()
   * @see #setGender(Gender)
   * @generated
   */
  boolean isSetGender();

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #isSetId()
   * @see #unsetId()
   * @see #setId(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Id()
   * @model unsettable="true" id="true" required="true" ordered="false"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getId <em>Id</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetId()
   * @see #getId()
   * @see #setId(String)
   * @generated
   */
  void unsetId();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getId <em>Id</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Family</b></em>' reference.
   * It is bidirectional and its opposite is '{@link goedegep.rolodex.model.Family#getMembers <em>Members</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Family</em>' reference.
   * @see #setFamily(Family)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Family()
   * @see goedegep.rolodex.model.Family#getMembers
   * @model opposite="members"
   * @generated
   */
  Family getFamily();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getFamily <em>Family</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Family</em>' reference.
   * @see #getFamily()
   * @generated
   */
  void setFamily(Family value);

  /**
   * Returns the value of the '<em><b>Birthday</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Birthday</em>' containment reference.
   * @see #isSetBirthday()
   * @see #unsetBirthday()
   * @see #setBirthday(Birthday)
   * @see goedegep.rolodex.model.RolodexPackage#getPerson_Birthday()
   * @model containment="true" unsettable="true"
   * @generated
   */
  Birthday getBirthday();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Person#getBirthday <em>Birthday</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Birthday</em>' containment reference.
   * @see #isSetBirthday()
   * @see #unsetBirthday()
   * @see #getBirthday()
   * @generated
   */
  void setBirthday(Birthday value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Person#getBirthday <em>Birthday</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBirthday()
   * @see #getBirthday()
   * @see #setBirthday(Birthday)
   * @generated
   */
  void unsetBirthday();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Person#getBirthday <em>Birthday</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Birthday</em>' containment reference is set.
   * @see #unsetBirthday()
   * @see #getBirthday()
   * @see #setBirthday(Birthday)
   * @generated
   */
  boolean isSetBirthday();

  public String getName();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Address retrieveAddress();

} // Person
