/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uitgave</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.Uitgave#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link goedegep.finan.nota.Uitgave#getBedrag <em>Bedrag</em>}</li>
 *   <li>{@link goedegep.finan.nota.Uitgave#getOpmerkingen <em>Opmerkingen</em>}</li>
 *   <li>{@link goedegep.finan.nota.Uitgave#getAankoop <em>Aankoop</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getUitgave()
 * @model
 * @generated
 */
public interface Uitgave extends EObject {
  /**
   * Returns the value of the '<em><b>Omschrijving</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Omschrijving</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Omschrijving</em>' attribute.
   * @see #isSetOmschrijving()
   * @see #unsetOmschrijving()
   * @see #setOmschrijving(String)
   * @see goedegep.finan.nota.NotaPackage#getUitgave_Omschrijving()
   * @model unsettable="true"
   * @generated
   */
  String getOmschrijving();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Uitgave#getOmschrijving <em>Omschrijving</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Omschrijving</em>' attribute.
   * @see #isSetOmschrijving()
   * @see #unsetOmschrijving()
   * @see #getOmschrijving()
   * @generated
   */
  void setOmschrijving(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Uitgave#getOmschrijving <em>Omschrijving</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOmschrijving()
   * @see #getOmschrijving()
   * @see #setOmschrijving(String)
   * @generated
   */
  void unsetOmschrijving();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Uitgave#getOmschrijving <em>Omschrijving</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Omschrijving</em>' attribute is set.
   * @see #unsetOmschrijving()
   * @see #getOmschrijving()
   * @see #setOmschrijving(String)
   * @generated
   */
  boolean isSetOmschrijving();

  /**
   * Returns the value of the '<em><b>Bedrag</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bedrag</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bedrag</em>' attribute.
   * @see #isSetBedrag()
   * @see #unsetBedrag()
   * @see #setBedrag(PgCurrency)
   * @see goedegep.finan.nota.NotaPackage#getUitgave_Bedrag()
   * @model unsettable="true" dataType="goedegep.types.EMoney"
   * @generated
   */
  PgCurrency getBedrag();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Uitgave#getBedrag <em>Bedrag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bedrag</em>' attribute.
   * @see #isSetBedrag()
   * @see #unsetBedrag()
   * @see #getBedrag()
   * @generated
   */
  void setBedrag(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Uitgave#getBedrag <em>Bedrag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBedrag()
   * @see #getBedrag()
   * @see #setBedrag(PgCurrency)
   * @generated
   */
  void unsetBedrag();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Uitgave#getBedrag <em>Bedrag</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Bedrag</em>' attribute is set.
   * @see #unsetBedrag()
   * @see #getBedrag()
   * @see #setBedrag(PgCurrency)
   * @generated
   */
  boolean isSetBedrag();

  /**
   * Returns the value of the '<em><b>Opmerkingen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Opmerkingen</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Opmerkingen</em>' attribute.
   * @see #isSetOpmerkingen()
   * @see #unsetOpmerkingen()
   * @see #setOpmerkingen(String)
   * @see goedegep.finan.nota.NotaPackage#getUitgave_Opmerkingen()
   * @model unsettable="true"
   * @generated
   */
  String getOpmerkingen();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Uitgave#getOpmerkingen <em>Opmerkingen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Opmerkingen</em>' attribute.
   * @see #isSetOpmerkingen()
   * @see #unsetOpmerkingen()
   * @see #getOpmerkingen()
   * @generated
   */
  void setOpmerkingen(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Uitgave#getOpmerkingen <em>Opmerkingen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOpmerkingen()
   * @see #getOpmerkingen()
   * @see #setOpmerkingen(String)
   * @generated
   */
  void unsetOpmerkingen();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Uitgave#getOpmerkingen <em>Opmerkingen</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Opmerkingen</em>' attribute is set.
   * @see #unsetOpmerkingen()
   * @see #getOpmerkingen()
   * @see #setOpmerkingen(String)
   * @generated
   */
  boolean isSetOpmerkingen();

  /**
   * Returns the value of the '<em><b>Aankoop</b></em>' reference.
   * It is bidirectional and its opposite is '{@link goedegep.finan.nota.Eigendom#getUitgave <em>Uitgave</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Aankoop</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Aankoop</em>' reference.
   * @see #setAankoop(Eigendom)
   * @see goedegep.finan.nota.NotaPackage#getUitgave_Aankoop()
   * @see goedegep.finan.nota.Eigendom#getUitgave
   * @model opposite="uitgave"
   * @generated
   */
  Eigendom getAankoop();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Uitgave#getAankoop <em>Aankoop</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Aankoop</em>' reference.
   * @see #getAankoop()
   * @generated
   */
  void setAankoop(Eigendom value);

} // Uitgave
