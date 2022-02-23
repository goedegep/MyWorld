/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.finan.nota.NotaFactory
 * @model kind="package"
 * @generated
 */
public interface NotaPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "nota";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http:///org/goedegep/nota.ecore";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "goedegep.finan";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  NotaPackage eINSTANCE = goedegep.appgen.eobjectsexamplemodel.impl.NotaPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.NotasImpl <em>Notas</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.NotasImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getNotas()
   * @generated
   */
  int NOTAS = 0;

  /**
   * The feature id for the '<em><b>Notas</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTAS__NOTAS = 0;

  /**
   * The number of structural features of the '<em>Notas</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTAS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.UitgaveImpl <em>Uitgave</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.UitgaveImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getUitgave()
   * @generated
   */
  int UITGAVE = 5;

  /**
   * The feature id for the '<em><b>Omschrijving</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UITGAVE__OMSCHRIJVING = 0;

  /**
   * The feature id for the '<em><b>Bedrag</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UITGAVE__BEDRAG = 1;

  /**
   * The feature id for the '<em><b>Opmerkingen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UITGAVE__OPMERKINGEN = 2;

  /**
   * The feature id for the '<em><b>Aankoop</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UITGAVE__AANKOOP = 3;

  /**
   * The number of structural features of the '<em>Uitgave</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UITGAVE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.NotaImpl <em>Nota</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.NotaImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getNota()
   * @generated
   */
  int NOTA = 1;

  /**
   * The feature id for the '<em><b>Omschrijving</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__OMSCHRIJVING = UITGAVE__OMSCHRIJVING;

  /**
   * The feature id for the '<em><b>Bedrag</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__BEDRAG = UITGAVE__BEDRAG;

  /**
   * The feature id for the '<em><b>Opmerkingen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__OPMERKINGEN = UITGAVE__OPMERKINGEN;

  /**
   * The feature id for the '<em><b>Aankoop</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__AANKOOP = UITGAVE__AANKOOP;

  /**
   * The feature id for the '<em><b>Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__DATUM = UITGAVE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Bedrijf</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__BEDRIJF = UITGAVE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Nota Items</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA__NOTA_ITEMS = UITGAVE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Nota</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_FEATURE_COUNT = UITGAVE_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.NotaItemImpl <em>Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.NotaItemImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getNotaItem()
   * @generated
   */
  int NOTA_ITEM = 2;

  /**
   * The feature id for the '<em><b>Omschrijving</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__OMSCHRIJVING = UITGAVE__OMSCHRIJVING;

  /**
   * The feature id for the '<em><b>Bedrag</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__BEDRAG = UITGAVE__BEDRAG;

  /**
   * The feature id for the '<em><b>Opmerkingen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__OPMERKINGEN = UITGAVE__OPMERKINGEN;

  /**
   * The feature id for the '<em><b>Aankoop</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__AANKOOP = UITGAVE__AANKOOP;

  /**
   * The feature id for the '<em><b>Aantal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__AANTAL = UITGAVE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nota</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM__NOTA = UITGAVE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOTA_ITEM_FEATURE_COUNT = UITGAVE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.EigendommenLijstImpl <em>Eigendommen Lijst</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.EigendommenLijstImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getEigendommenLijst()
   * @generated
   */
  int EIGENDOMMEN_LIJST = 3;

  /**
   * The feature id for the '<em><b>Eigendommen</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOMMEN_LIJST__EIGENDOMMEN = 0;

  /**
   * The number of structural features of the '<em>Eigendommen Lijst</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOMMEN_LIJST_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.finan.nota.impl.EigendomImpl <em>Eigendom</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.nota.impl.EigendomImpl
   * @see goedegep.finan.nota.impl.NotaPackageImpl#getEigendom()
   * @generated
   */
  int EIGENDOM = 4;

  /**
   * The feature id for the '<em><b>Omschrijving</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__OMSCHRIJVING = 0;

  /**
   * The feature id for the '<em><b>Merk</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__MERK = 1;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__TYPE = 2;

  /**
   * The feature id for the '<em><b>Serie Nummer</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__SERIE_NUMMER = 3;

  /**
   * The feature id for the '<em><b>Opmerkingen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__OPMERKINGEN = 4;

  /**
   * The feature id for the '<em><b>Van Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__VAN_DATUM = 5;

  /**
   * The feature id for the '<em><b>Tot Datum</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__TOT_DATUM = 6;

  /**
   * The feature id for the '<em><b>Archief</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__ARCHIEF = 7;

  /**
   * The feature id for the '<em><b>Afbeeldingen</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__AFBEELDINGEN = 8;

  /**
   * The feature id for the '<em><b>Documenten</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__DOCUMENTEN = 9;

  /**
   * The feature id for the '<em><b>Uitgave</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM__UITGAVE = 10;

  /**
   * The number of structural features of the '<em>Eigendom</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EIGENDOM_FEATURE_COUNT = 11;


  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.Notas <em>Notas</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Notas</em>'.
   * @see goedegep.finan.nota.Notas
   * @generated
   */
  EClass getNotas();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.nota.Notas#getNotas <em>Notas</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Notas</em>'.
   * @see goedegep.finan.nota.Notas#getNotas()
   * @see #getNotas()
   * @generated
   */
  EReference getNotas_Notas();

  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.Nota <em>Nota</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Nota</em>'.
   * @see goedegep.finan.nota.Nota
   * @generated
   */
  EClass getNota();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Nota#getDatum <em>Datum</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Datum</em>'.
   * @see goedegep.finan.nota.Nota#getDatum()
   * @see #getNota()
   * @generated
   */
  EAttribute getNota_Datum();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Nota#getBedrijf <em>Bedrijf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Bedrijf</em>'.
   * @see goedegep.finan.nota.Nota#getBedrijf()
   * @see #getNota()
   * @generated
   */
  EAttribute getNota_Bedrijf();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.nota.Nota#getNotaItems <em>Nota Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Nota Items</em>'.
   * @see goedegep.finan.nota.Nota#getNotaItems()
   * @see #getNota()
   * @generated
   */
  EReference getNota_NotaItems();

  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.NotaItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Item</em>'.
   * @see goedegep.finan.nota.NotaItem
   * @generated
   */
  EClass getNotaItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.NotaItem#getAantal <em>Aantal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Aantal</em>'.
   * @see goedegep.finan.nota.NotaItem#getAantal()
   * @see #getNotaItem()
   * @generated
   */
  EAttribute getNotaItem_Aantal();

  /**
   * Returns the meta object for the container reference '{@link goedegep.finan.nota.NotaItem#getNota <em>Nota</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Nota</em>'.
   * @see goedegep.finan.nota.NotaItem#getNota()
   * @see #getNotaItem()
   * @generated
   */
  EReference getNotaItem_Nota();

  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.EigendommenLijst <em>Eigendommen Lijst</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eigendommen Lijst</em>'.
   * @see goedegep.finan.nota.EigendommenLijst
   * @generated
   */
  EClass getEigendommenLijst();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.nota.EigendommenLijst#getEigendommen <em>Eigendommen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Eigendommen</em>'.
   * @see goedegep.finan.nota.EigendommenLijst#getEigendommen()
   * @see #getEigendommenLijst()
   * @generated
   */
  EReference getEigendommenLijst_Eigendommen();

  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.Eigendom <em>Eigendom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eigendom</em>'.
   * @see goedegep.finan.nota.Eigendom
   * @generated
   */
  EClass getEigendom();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getOmschrijving <em>Omschrijving</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Omschrijving</em>'.
   * @see goedegep.finan.nota.Eigendom#getOmschrijving()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_Omschrijving();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getMerk <em>Merk</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Merk</em>'.
   * @see goedegep.finan.nota.Eigendom#getMerk()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_Merk();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see goedegep.finan.nota.Eigendom#getType()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_Type();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getSerieNummer <em>Serie Nummer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Serie Nummer</em>'.
   * @see goedegep.finan.nota.Eigendom#getSerieNummer()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_SerieNummer();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getOpmerkingen <em>Opmerkingen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Opmerkingen</em>'.
   * @see goedegep.finan.nota.Eigendom#getOpmerkingen()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_Opmerkingen();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getVanDatum <em>Van Datum</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Van Datum</em>'.
   * @see goedegep.finan.nota.Eigendom#getVanDatum()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_VanDatum();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#getTotDatum <em>Tot Datum</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tot Datum</em>'.
   * @see goedegep.finan.nota.Eigendom#getTotDatum()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_TotDatum();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Eigendom#isArchief <em>Archief</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Archief</em>'.
   * @see goedegep.finan.nota.Eigendom#isArchief()
   * @see #getEigendom()
   * @generated
   */
  EAttribute getEigendom_Archief();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.nota.Eigendom#getAfbeeldingen <em>Afbeeldingen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Afbeeldingen</em>'.
   * @see goedegep.finan.nota.Eigendom#getAfbeeldingen()
   * @see #getEigendom()
   * @generated
   */
  EReference getEigendom_Afbeeldingen();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.nota.Eigendom#getDocumenten <em>Documenten</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Documenten</em>'.
   * @see goedegep.finan.nota.Eigendom#getDocumenten()
   * @see #getEigendom()
   * @generated
   */
  EReference getEigendom_Documenten();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.nota.Eigendom#getUitgave <em>Uitgave</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Uitgave</em>'.
   * @see goedegep.finan.nota.Eigendom#getUitgave()
   * @see #getEigendom()
   * @generated
   */
  EReference getEigendom_Uitgave();

  /**
   * Returns the meta object for class '{@link goedegep.finan.nota.Uitgave <em>Uitgave</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Uitgave</em>'.
   * @see goedegep.finan.nota.Uitgave
   * @generated
   */
  EClass getUitgave();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Uitgave#getOmschrijving <em>Omschrijving</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Omschrijving</em>'.
   * @see goedegep.finan.nota.Uitgave#getOmschrijving()
   * @see #getUitgave()
   * @generated
   */
  EAttribute getUitgave_Omschrijving();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Uitgave#getBedrag <em>Bedrag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Bedrag</em>'.
   * @see goedegep.finan.nota.Uitgave#getBedrag()
   * @see #getUitgave()
   * @generated
   */
  EAttribute getUitgave_Bedrag();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.nota.Uitgave#getOpmerkingen <em>Opmerkingen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Opmerkingen</em>'.
   * @see goedegep.finan.nota.Uitgave#getOpmerkingen()
   * @see #getUitgave()
   * @generated
   */
  EAttribute getUitgave_Opmerkingen();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.nota.Uitgave#getAankoop <em>Aankoop</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Aankoop</em>'.
   * @see goedegep.finan.nota.Uitgave#getAankoop()
   * @see #getUitgave()
   * @generated
   */
  EReference getUitgave_Aankoop();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  NotaFactory getNotaFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.NotasImpl <em>Notas</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.NotasImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getNotas()
     * @generated
     */
    EClass NOTAS = eINSTANCE.getNotas();

    /**
     * The meta object literal for the '<em><b>Notas</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NOTAS__NOTAS = eINSTANCE.getNotas_Notas();

    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.NotaImpl <em>Nota</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.NotaImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getNota()
     * @generated
     */
    EClass NOTA = eINSTANCE.getNota();

    /**
     * The meta object literal for the '<em><b>Datum</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NOTA__DATUM = eINSTANCE.getNota_Datum();

    /**
     * The meta object literal for the '<em><b>Bedrijf</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NOTA__BEDRIJF = eINSTANCE.getNota_Bedrijf();

    /**
     * The meta object literal for the '<em><b>Nota Items</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NOTA__NOTA_ITEMS = eINSTANCE.getNota_NotaItems();

    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.NotaItemImpl <em>Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.NotaItemImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getNotaItem()
     * @generated
     */
    EClass NOTA_ITEM = eINSTANCE.getNotaItem();

    /**
     * The meta object literal for the '<em><b>Aantal</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NOTA_ITEM__AANTAL = eINSTANCE.getNotaItem_Aantal();

    /**
     * The meta object literal for the '<em><b>Nota</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NOTA_ITEM__NOTA = eINSTANCE.getNotaItem_Nota();

    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.EigendommenLijstImpl <em>Eigendommen Lijst</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.EigendommenLijstImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getEigendommenLijst()
     * @generated
     */
    EClass EIGENDOMMEN_LIJST = eINSTANCE.getEigendommenLijst();

    /**
     * The meta object literal for the '<em><b>Eigendommen</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EIGENDOMMEN_LIJST__EIGENDOMMEN = eINSTANCE.getEigendommenLijst_Eigendommen();

    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.EigendomImpl <em>Eigendom</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.EigendomImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getEigendom()
     * @generated
     */
    EClass EIGENDOM = eINSTANCE.getEigendom();

    /**
     * The meta object literal for the '<em><b>Omschrijving</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__OMSCHRIJVING = eINSTANCE.getEigendom_Omschrijving();

    /**
     * The meta object literal for the '<em><b>Merk</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__MERK = eINSTANCE.getEigendom_Merk();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__TYPE = eINSTANCE.getEigendom_Type();

    /**
     * The meta object literal for the '<em><b>Serie Nummer</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__SERIE_NUMMER = eINSTANCE.getEigendom_SerieNummer();

    /**
     * The meta object literal for the '<em><b>Opmerkingen</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__OPMERKINGEN = eINSTANCE.getEigendom_Opmerkingen();

    /**
     * The meta object literal for the '<em><b>Van Datum</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__VAN_DATUM = eINSTANCE.getEigendom_VanDatum();

    /**
     * The meta object literal for the '<em><b>Tot Datum</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__TOT_DATUM = eINSTANCE.getEigendom_TotDatum();

    /**
     * The meta object literal for the '<em><b>Archief</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EIGENDOM__ARCHIEF = eINSTANCE.getEigendom_Archief();

    /**
     * The meta object literal for the '<em><b>Afbeeldingen</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EIGENDOM__AFBEELDINGEN = eINSTANCE.getEigendom_Afbeeldingen();

    /**
     * The meta object literal for the '<em><b>Documenten</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EIGENDOM__DOCUMENTEN = eINSTANCE.getEigendom_Documenten();

    /**
     * The meta object literal for the '<em><b>Uitgave</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EIGENDOM__UITGAVE = eINSTANCE.getEigendom_Uitgave();

    /**
     * The meta object literal for the '{@link goedegep.finan.nota.impl.UitgaveImpl <em>Uitgave</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.nota.impl.UitgaveImpl
     * @see goedegep.finan.nota.impl.NotaPackageImpl#getUitgave()
     * @generated
     */
    EClass UITGAVE = eINSTANCE.getUitgave();

    /**
     * The meta object literal for the '<em><b>Omschrijving</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UITGAVE__OMSCHRIJVING = eINSTANCE.getUitgave_Omschrijving();

    /**
     * The meta object literal for the '<em><b>Bedrag</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UITGAVE__BEDRAG = eINSTANCE.getUitgave_Bedrag();

    /**
     * The meta object literal for the '<em><b>Opmerkingen</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UITGAVE__OPMERKINGEN = eINSTANCE.getUitgave_Opmerkingen();

    /**
     * The meta object literal for the '<em><b>Aankoop</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UITGAVE__AANKOOP = eINSTANCE.getUitgave_Aankoop();

  }

} //NotaPackage
