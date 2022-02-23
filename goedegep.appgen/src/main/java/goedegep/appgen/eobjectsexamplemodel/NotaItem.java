/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.NotaItem#getAantal <em>Aantal</em>}</li>
 *   <li>{@link goedegep.finan.nota.NotaItem#getNota <em>Nota</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getNotaItem()
 * @model
 * @generated
 */
public interface NotaItem extends Uitgave {
  /**
   * Returns the value of the '<em><b>Aantal</b></em>' attribute.
   * The default value is <code>"1"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Aantal</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Aantal</em>' attribute.
   * @see #isSetAantal()
   * @see #unsetAantal()
   * @see #setAantal(int)
   * @see goedegep.finan.nota.NotaPackage#getNotaItem_Aantal()
   * @model default="1" unsettable="true" required="true"
   * @generated
   */
  int getAantal();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.NotaItem#getAantal <em>Aantal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Aantal</em>' attribute.
   * @see #isSetAantal()
   * @see #unsetAantal()
   * @see #getAantal()
   * @generated
   */
  void setAantal(int value);

/**
   * Unsets the value of the '{@link goedegep.finan.nota.NotaItem#getAantal <em>Aantal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAantal()
   * @see #getAantal()
   * @see #setAantal(int)
   * @generated
   */
  void unsetAantal();

  /**
   * Returns whether the value of the '{@link goedegep.finan.nota.NotaItem#getAantal <em>Aantal</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Aantal</em>' attribute is set.
   * @see #unsetAantal()
   * @see #getAantal()
   * @see #setAantal(int)
   * @generated
   */
  boolean isSetAantal();

  //  String getOmschrijving(boolean derive);

  /**
   * Returns the value of the '<em><b>Nota</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link goedegep.finan.nota.Nota#getNotaItems <em>Nota Items</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nota</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nota</em>' container reference.
   * @see #setNota(Nota)
   * @see goedegep.finan.nota.NotaPackage#getNotaItem_Nota()
   * @see goedegep.finan.nota.Nota#getNotaItems
   * @model opposite="notaItems" transient="false"
   * @generated
   */
  Nota getNota();

  /**
   * Sets the value of the '{@link goedegep.finan.nota.NotaItem#getNota <em>Nota</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nota</em>' container reference.
   * @see #getNota()
   * @generated
   */
  void setNota(Nota value);

} // NotaItem
