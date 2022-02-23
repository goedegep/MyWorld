/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Phone Number Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.PhoneNumberHolder#getPhoneNumbers <em>Phone Numbers</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumberHolder()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface PhoneNumberHolder extends EObject {

  /**
   * Returns the value of the '<em><b>Phone Numbers</b></em>' reference list.
   * The list contents are of type {@link goedegep.rolodex.model.PhoneNumber}.
   * It is bidirectional and its opposite is '{@link goedegep.rolodex.model.PhoneNumber#getNumberHolders <em>Number Holders</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Numbers</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Numbers</em>' reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumberHolder_PhoneNumbers()
   * @see goedegep.rolodex.model.PhoneNumber#getNumberHolders
   * @model opposite="numberHolders"
   * @generated
   */
  EList<PhoneNumber> getPhoneNumbers();
} // PhoneNumberHolder
