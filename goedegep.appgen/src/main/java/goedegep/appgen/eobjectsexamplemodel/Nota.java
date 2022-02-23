/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import goedegep.util.datetime.FlexDate;
import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nota</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.Nota#getDatum <em>Datum</em>}</li>
 *   <li>{@link goedegep.finan.nota.Nota#getBedrijf <em>Bedrijf</em>}</li>
 *   <li>{@link goedegep.finan.nota.Nota#getNotaItems <em>Nota Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getNota()
 * @model
 * @generated
 */
public interface Nota extends Uitgave {
  /**
   * Returns the value of the '<em><b>Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Datum</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Datum</em>' attribute.
   * @see #isSetDatum()
   * @see #unsetDatum()
   * @see #setDatum(FlexDate)
   * @see goedegep.finan.nota.NotaPackage#getNota_Datum()
   * @model unsettable="true" dataType="goedegep.types.EFlexDate"
   * @generated
   */
  FlexDate getDatum();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Nota#getDatum <em>Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Datum</em>' attribute.
   * @see #isSetDatum()
   * @see #unsetDatum()
   * @see #getDatum()
   * @generated
   */
  void setDatum(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Nota#getDatum <em>Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDatum()
   * @see #getDatum()
   * @see #setDatum(FlexDate)
   * @generated
   */
  void unsetDatum();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Nota#getDatum <em>Datum</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Datum</em>' attribute is set.
   * @see #unsetDatum()
   * @see #getDatum()
   * @see #setDatum(FlexDate)
   * @generated
   */
  boolean isSetDatum();

  /**
   * Returns the value of the '<em><b>Bedrijf</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bedrijf</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bedrijf</em>' attribute.
   * @see #isSetBedrijf()
   * @see #unsetBedrijf()
   * @see #setBedrijf(String)
   * @see goedegep.finan.nota.NotaPackage#getNota_Bedrijf()
   * @model unsettable="true"
   * @generated
   */
  String getBedrijf();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Nota#getBedrijf <em>Bedrijf</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bedrijf</em>' attribute.
   * @see #isSetBedrijf()
   * @see #unsetBedrijf()
   * @see #getBedrijf()
   * @generated
   */
  void setBedrijf(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Nota#getBedrijf <em>Bedrijf</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBedrijf()
   * @see #getBedrijf()
   * @see #setBedrijf(String)
   * @generated
   */
  void unsetBedrijf();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Nota#getBedrijf <em>Bedrijf</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Bedrijf</em>' attribute is set.
   * @see #unsetBedrijf()
   * @see #getBedrijf()
   * @see #setBedrijf(String)
   * @generated
   */
  boolean isSetBedrijf();

  String getDisplayOmschrijving();
  
  /**
   * Returns the value of the '<em><b>Nota Items</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.nota.NotaItem}.
   * It is bidirectional and its opposite is '{@link goedegep.finan.nota.NotaItem#getNota <em>Nota</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nota Items</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nota Items</em>' containment reference list.
   * @see goedegep.finan.nota.NotaPackage#getNota_NotaItems()
   * @see goedegep.finan.nota.NotaItem#getNota
   * @model opposite="nota" containment="true" required="true"
   * @generated
   */
  EList<NotaItem> getNotaItems();

  PgCurrency getTotaalBedragNotaItems();
  
//  void moveNotaItem(int index, boolean up);
} // Nota
