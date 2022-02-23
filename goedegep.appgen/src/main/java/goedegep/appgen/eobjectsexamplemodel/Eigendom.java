/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eigendom</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.Eigendom#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getMerk <em>Merk</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getSerieNummer <em>Serie Nummer</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getOpmerkingen <em>Opmerkingen</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getVanDatum <em>Van Datum</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getTotDatum <em>Tot Datum</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#isArchief <em>Archief</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getAfbeeldingen <em>Afbeeldingen</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getDocumenten <em>Documenten</em>}</li>
 *   <li>{@link goedegep.finan.nota.Eigendom#getUitgave <em>Uitgave</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getEigendom()
 * @model
 * @generated
 */
public interface Eigendom extends EObject {
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
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Omschrijving()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getOmschrijving();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getOmschrijving <em>Omschrijving</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getOmschrijving <em>Omschrijving</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOmschrijving()
   * @see #getOmschrijving()
   * @see #setOmschrijving(String)
   * @generated
   */
  void unsetOmschrijving();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getOmschrijving <em>Omschrijving</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Merk</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Merk</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Merk</em>' attribute.
   * @see #isSetMerk()
   * @see #unsetMerk()
   * @see #setMerk(String)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Merk()
   * @model unsettable="true"
   * @generated
   */
  String getMerk();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getMerk <em>Merk</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Merk</em>' attribute.
   * @see #isSetMerk()
   * @see #unsetMerk()
   * @see #getMerk()
   * @generated
   */
  void setMerk(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getMerk <em>Merk</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMerk()
   * @see #getMerk()
   * @see #setMerk(String)
   * @generated
   */
  void unsetMerk();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getMerk <em>Merk</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Merk</em>' attribute is set.
   * @see #unsetMerk()
   * @see #getMerk()
   * @see #setMerk(String)
   * @generated
   */
  boolean isSetMerk();

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #isSetType()
   * @see #unsetType()
   * @see #setType(String)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Type()
   * @model unsettable="true"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #isSetType()
   * @see #unsetType()
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetType()
   * @see #getType()
   * @see #setType(String)
   * @generated
   */
  void unsetType();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getType <em>Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Type</em>' attribute is set.
   * @see #unsetType()
   * @see #getType()
   * @see #setType(String)
   * @generated
   */
  boolean isSetType();

  /**
   * Returns the value of the '<em><b>Serie Nummer</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Serie Nummer</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Serie Nummer</em>' attribute.
   * @see #isSetSerieNummer()
   * @see #unsetSerieNummer()
   * @see #setSerieNummer(String)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_SerieNummer()
   * @model unsettable="true"
   * @generated
   */
  String getSerieNummer();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getSerieNummer <em>Serie Nummer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Serie Nummer</em>' attribute.
   * @see #isSetSerieNummer()
   * @see #unsetSerieNummer()
   * @see #getSerieNummer()
   * @generated
   */
  void setSerieNummer(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getSerieNummer <em>Serie Nummer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSerieNummer()
   * @see #getSerieNummer()
   * @see #setSerieNummer(String)
   * @generated
   */
  void unsetSerieNummer();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getSerieNummer <em>Serie Nummer</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Serie Nummer</em>' attribute is set.
   * @see #unsetSerieNummer()
   * @see #getSerieNummer()
   * @see #setSerieNummer(String)
   * @generated
   */
  boolean isSetSerieNummer();

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
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Opmerkingen()
   * @model unsettable="true"
   * @generated
   */
  String getOpmerkingen();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getOpmerkingen <em>Opmerkingen</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getOpmerkingen <em>Opmerkingen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOpmerkingen()
   * @see #getOpmerkingen()
   * @see #setOpmerkingen(String)
   * @generated
   */
  void unsetOpmerkingen();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getOpmerkingen <em>Opmerkingen</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Van Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Van Datum</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Van Datum</em>' attribute.
   * @see #isSetVanDatum()
   * @see #unsetVanDatum()
   * @see #setVanDatum(FlexDate)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_VanDatum()
   * @model unsettable="true" dataType="goedegep.types.EFlexDate"
   * @generated
   */
  FlexDate getVanDatum();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getVanDatum <em>Van Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Van Datum</em>' attribute.
   * @see #isSetVanDatum()
   * @see #unsetVanDatum()
   * @see #getVanDatum()
   * @generated
   */
  void setVanDatum(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getVanDatum <em>Van Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVanDatum()
   * @see #getVanDatum()
   * @see #setVanDatum(FlexDate)
   * @generated
   */
  void unsetVanDatum();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getVanDatum <em>Van Datum</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Van Datum</em>' attribute is set.
   * @see #unsetVanDatum()
   * @see #getVanDatum()
   * @see #setVanDatum(FlexDate)
   * @generated
   */
  boolean isSetVanDatum();

  /**
   * Returns the value of the '<em><b>Tot Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tot Datum</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tot Datum</em>' attribute.
   * @see #isSetTotDatum()
   * @see #unsetTotDatum()
   * @see #setTotDatum(FlexDate)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_TotDatum()
   * @model unsettable="true" dataType="goedegep.types.EFlexDate"
   * @generated
   */
  FlexDate getTotDatum();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getTotDatum <em>Tot Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tot Datum</em>' attribute.
   * @see #isSetTotDatum()
   * @see #unsetTotDatum()
   * @see #getTotDatum()
   * @generated
   */
  void setTotDatum(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.nota.Eigendom#getTotDatum <em>Tot Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTotDatum()
   * @see #getTotDatum()
   * @see #setTotDatum(FlexDate)
   * @generated
   */
  void unsetTotDatum();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.Eigendom#getTotDatum <em>Tot Datum</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Tot Datum</em>' attribute is set.
   * @see #unsetTotDatum()
   * @see #getTotDatum()
   * @see #setTotDatum(FlexDate)
   * @generated
   */
  boolean isSetTotDatum();

  /**
   * Returns the value of the '<em><b>Archief</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Archief</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Archief</em>' attribute.
   * @see #setArchief(boolean)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Archief()
   * @model default="false"
   * @generated
   */
  boolean isArchief();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#isArchief <em>Archief</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Archief</em>' attribute.
   * @see #isArchief()
   * @generated
   */
  void setArchief(boolean value);

  /**
   * Returns the value of the '<em><b>Afbeeldingen</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Afbeeldingen</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Afbeeldingen</em>' containment reference list.
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Afbeeldingen()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getAfbeeldingen();

  /**
   * Returns the value of the '<em><b>Documenten</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Documenten</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documenten</em>' containment reference list.
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Documenten()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getDocumenten();

  /**
   * Returns the value of the '<em><b>Uitgave</b></em>' reference.
   * It is bidirectional and its opposite is '{@link goedegep.finan.nota.Uitgave#getAankoop <em>Aankoop</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uitgave</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uitgave</em>' reference.
   * @see #setUitgave(Uitgave)
   * @see goedegep.finan.nota.NotaPackage#getEigendom_Uitgave()
   * @see goedegep.finan.nota.Uitgave#getAankoop
   * @model opposite="aankoop"
   * @generated
   */
  Uitgave getUitgave();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.Eigendom#getUitgave <em>Uitgave</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uitgave</em>' reference.
   * @see #getUitgave()
   * @generated
   */
  void setUitgave(Uitgave value);

} // Eigendom
