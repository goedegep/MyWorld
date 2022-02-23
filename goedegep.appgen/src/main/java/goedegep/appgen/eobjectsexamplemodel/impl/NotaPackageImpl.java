/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import goedegep.appgen.eobjectsexamplemodel.Eigendom;
import goedegep.appgen.eobjectsexamplemodel.EigendommenLijst;
import goedegep.appgen.eobjectsexamplemodel.Nota;
import goedegep.appgen.eobjectsexamplemodel.NotaFactory;
import goedegep.appgen.eobjectsexamplemodel.NotaItem;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;
import goedegep.appgen.eobjectsexamplemodel.Notas;
import goedegep.appgen.eobjectsexamplemodel.Uitgave;
import goedegep.types.model.TypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NotaPackageImpl extends EPackageImpl implements NotaPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass notasEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass notaEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass notaItemEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eigendommenLijstEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eigendomEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass uitgaveEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.appgen.eobjectsexamplemodel.NotaPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private NotaPackageImpl() {
    super(eNS_URI, NotaFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link NotaPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static NotaPackage init() {
    if (isInited) return (NotaPackage)EPackage.Registry.INSTANCE.getEPackage(NotaPackage.eNS_URI);

    // Obtain or create and register package
    NotaPackageImpl theNotaPackage = (NotaPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NotaPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NotaPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theNotaPackage.createPackageContents();

    // Initialize created meta-data
    theNotaPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theNotaPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(NotaPackage.eNS_URI, theNotaPackage);
    return theNotaPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNotas() {
    return notasEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNotas_Notas() {
    return (EReference)notasEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNota() {
    return notaEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNota_Datum() {
    return (EAttribute)notaEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNota_Bedrijf() {
    return (EAttribute)notaEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNota_NotaItems() {
    return (EReference)notaEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNotaItem() {
    return notaItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNotaItem_Aantal() {
    return (EAttribute)notaItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNotaItem_Nota() {
    return (EReference)notaItemEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEigendommenLijst() {
    return eigendommenLijstEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEigendommenLijst_Eigendommen() {
    return (EReference)eigendommenLijstEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEigendom() {
    return eigendomEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_Omschrijving() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_Merk() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_Type() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_SerieNummer() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_Opmerkingen() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_VanDatum() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_TotDatum() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEigendom_Archief() {
    return (EAttribute)eigendomEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEigendom_Afbeeldingen() {
    return (EReference)eigendomEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEigendom_Documenten() {
    return (EReference)eigendomEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEigendom_Uitgave() {
    return (EReference)eigendomEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUitgave() {
    return uitgaveEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUitgave_Omschrijving() {
    return (EAttribute)uitgaveEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUitgave_Bedrag() {
    return (EAttribute)uitgaveEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUitgave_Opmerkingen() {
    return (EAttribute)uitgaveEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getUitgave_Aankoop() {
    return (EReference)uitgaveEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotaFactory getNotaFactory() {
    return (NotaFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    notasEClass = createEClass(NOTAS);
    createEReference(notasEClass, NOTAS__NOTAS);

    notaEClass = createEClass(NOTA);
    createEAttribute(notaEClass, NOTA__DATUM);
    createEAttribute(notaEClass, NOTA__BEDRIJF);
    createEReference(notaEClass, NOTA__NOTA_ITEMS);

    notaItemEClass = createEClass(NOTA_ITEM);
    createEAttribute(notaItemEClass, NOTA_ITEM__AANTAL);
    createEReference(notaItemEClass, NOTA_ITEM__NOTA);

    eigendommenLijstEClass = createEClass(EIGENDOMMEN_LIJST);
    createEReference(eigendommenLijstEClass, EIGENDOMMEN_LIJST__EIGENDOMMEN);

    eigendomEClass = createEClass(EIGENDOM);
    createEAttribute(eigendomEClass, EIGENDOM__OMSCHRIJVING);
    createEAttribute(eigendomEClass, EIGENDOM__MERK);
    createEAttribute(eigendomEClass, EIGENDOM__TYPE);
    createEAttribute(eigendomEClass, EIGENDOM__SERIE_NUMMER);
    createEAttribute(eigendomEClass, EIGENDOM__OPMERKINGEN);
    createEAttribute(eigendomEClass, EIGENDOM__VAN_DATUM);
    createEAttribute(eigendomEClass, EIGENDOM__TOT_DATUM);
    createEAttribute(eigendomEClass, EIGENDOM__ARCHIEF);
    createEReference(eigendomEClass, EIGENDOM__AFBEELDINGEN);
    createEReference(eigendomEClass, EIGENDOM__DOCUMENTEN);
    createEReference(eigendomEClass, EIGENDOM__UITGAVE);

    uitgaveEClass = createEClass(UITGAVE);
    createEAttribute(uitgaveEClass, UITGAVE__OMSCHRIJVING);
    createEAttribute(uitgaveEClass, UITGAVE__BEDRAG);
    createEAttribute(uitgaveEClass, UITGAVE__OPMERKINGEN);
    createEReference(uitgaveEClass, UITGAVE__AANKOOP);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    notaEClass.getESuperTypes().add(this.getUitgave());
    notaItemEClass.getESuperTypes().add(this.getUitgave());

    // Initialize classes and features; add operations and parameters
    initEClass(notasEClass, Notas.class, "Notas", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getNotas_Notas(), this.getNota(), null, "notas", null, 0, -1, Notas.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(notaEClass, Nota.class, "Nota", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNota_Datum(), theTypesPackage.getEFlexDate(), "datum", null, 0, 1, Nota.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNota_Bedrijf(), ecorePackage.getEString(), "bedrijf", null, 0, 1, Nota.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNota_NotaItems(), this.getNotaItem(), this.getNotaItem_Nota(), "notaItems", null, 1, -1, Nota.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(notaItemEClass, NotaItem.class, "NotaItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNotaItem_Aantal(), ecorePackage.getEInt(), "aantal", "1", 1, 1, NotaItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getNotaItem_Nota(), this.getNota(), this.getNota_NotaItems(), "nota", null, 0, 1, NotaItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eigendommenLijstEClass, EigendommenLijst.class, "EigendommenLijst", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEigendommenLijst_Eigendommen(), this.getEigendom(), null, "eigendommen", null, 0, 8, EigendommenLijst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eigendomEClass, Eigendom.class, "Eigendom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEigendom_Omschrijving(), ecorePackage.getEString(), "omschrijving", null, 1, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_Merk(), ecorePackage.getEString(), "merk", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_Type(), ecorePackage.getEString(), "type", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_SerieNummer(), ecorePackage.getEString(), "serieNummer", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_Opmerkingen(), ecorePackage.getEString(), "opmerkingen", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_VanDatum(), theTypesPackage.getEFlexDate(), "vanDatum", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_TotDatum(), theTypesPackage.getEFlexDate(), "totDatum", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEigendom_Archief(), ecorePackage.getEBoolean(), "archief", "false", 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEigendom_Afbeeldingen(), theTypesPackage.getFileReference(), null, "afbeeldingen", null, 0, -1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEigendom_Documenten(), theTypesPackage.getFileReference(), null, "documenten", null, 0, -1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEigendom_Uitgave(), this.getUitgave(), this.getUitgave_Aankoop(), "uitgave", null, 0, 1, Eigendom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(uitgaveEClass, Uitgave.class, "Uitgave", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUitgave_Omschrijving(), ecorePackage.getEString(), "omschrijving", null, 0, 1, Uitgave.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getUitgave_Bedrag(), theTypesPackage.getEMoney(), "bedrag", null, 0, 1, Uitgave.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getUitgave_Opmerkingen(), ecorePackage.getEString(), "opmerkingen", null, 0, 1, Uitgave.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getUitgave_Aankoop(), this.getEigendom(), this.getEigendom_Uitgave(), "aankoop", null, 0, 1, Uitgave.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //NotaPackageImpl
