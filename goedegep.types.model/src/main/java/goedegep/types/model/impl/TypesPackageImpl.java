/**
 */
package goedegep.types.model.impl;

import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.types.model.FileReference;
import goedegep.types.model.DateRateTuplet;
import goedegep.types.model.Event;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.datetime.FlexDate;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import java.lang.Comparable;

import java.time.LocalDate;
import java.time.YearMonth;
import javafx.scene.paint.Color;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPackageImpl extends EPackageImpl implements TypesPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fileReferenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass comparableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dateRateTupletEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eventEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eMoneyEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eFlexDateEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eColorEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eFixedPointValueEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eLocalDateEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType ewgs84CoordinatesEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eYearMonthEDataType = null;

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
   * @see goedegep.types.model.TypesPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private TypesPackageImpl() {
    super(eNS_URI, TypesFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link TypesPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static TypesPackage init() {
    if (isInited)
      return (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredTypesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    TypesPackageImpl theTypesPackage = registeredTypesPackage instanceof TypesPackageImpl
        ? (TypesPackageImpl) registeredTypesPackage
        : new TypesPackageImpl();

    isInited = true;

    // Create package meta-data objects
    theTypesPackage.createPackageContents();

    // Initialize created meta-data
    theTypesPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theTypesPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, theTypesPackage);
    return theTypesPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFileReference() {
    return fileReferenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFileReference_File() {
    return (EAttribute) fileReferenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFileReference_Title() {
    return (EAttribute) fileReferenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getComparable() {
    return comparableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDateRateTuplet() {
    return dateRateTupletEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDateRateTuplet_Date() {
    return (EAttribute) dateRateTupletEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDateRateTuplet_Rate() {
    return (EAttribute) dateRateTupletEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getEvent() {
    return eventEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getEvent_Date() {
    return (EAttribute) eventEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getEvent_Notes() {
    return (EAttribute) eventEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEMoney() {
    return eMoneyEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEFlexDate() {
    return eFlexDateEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEColor() {
    return eColorEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEFixedPointValue() {
    return eFixedPointValueEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getELocalDate() {
    return eLocalDateEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEWGS84Coordinates() {
    return ewgs84CoordinatesEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getEYearMonth() {
    return eYearMonthEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TypesFactory getTypesFactory() {
    return (TypesFactory) getEFactoryInstance();
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
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    fileReferenceEClass = createEClass(FILE_REFERENCE);
    createEAttribute(fileReferenceEClass, FILE_REFERENCE__FILE);
    createEAttribute(fileReferenceEClass, FILE_REFERENCE__TITLE);

    comparableEClass = createEClass(COMPARABLE);

    dateRateTupletEClass = createEClass(DATE_RATE_TUPLET);
    createEAttribute(dateRateTupletEClass, DATE_RATE_TUPLET__DATE);
    createEAttribute(dateRateTupletEClass, DATE_RATE_TUPLET__RATE);

    eventEClass = createEClass(EVENT);
    createEAttribute(eventEClass, EVENT__DATE);
    createEAttribute(eventEClass, EVENT__NOTES);

    // Create data types
    eMoneyEDataType = createEDataType(EMONEY);
    eFlexDateEDataType = createEDataType(EFLEX_DATE);
    eColorEDataType = createEDataType(ECOLOR);
    eFixedPointValueEDataType = createEDataType(EFIXED_POINT_VALUE);
    eLocalDateEDataType = createEDataType(ELOCAL_DATE);
    ewgs84CoordinatesEDataType = createEDataType(EWGS84_COORDINATES);
    eYearMonthEDataType = createEDataType(EYEAR_MONTH);
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
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters
    addETypeParameter(comparableEClass, "T");

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(fileReferenceEClass, FileReference.class, "FileReference", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileReference_File(), ecorePackage.getEString(), "file", null, 1, 1, FileReference.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileReference_Title(), ecorePackage.getEString(), "title", null, 0, 1, FileReference.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(comparableEClass, Comparable.class, "Comparable", IS_ABSTRACT, IS_INTERFACE,
        !IS_GENERATED_INSTANCE_CLASS);

    initEClass(dateRateTupletEClass, DateRateTuplet.class, "DateRateTuplet", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDateRateTuplet_Date(), this.getELocalDate(), "date", null, 0, 1, DateRateTuplet.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDateRateTuplet_Rate(), this.getEMoney(), "rate", null, 0, 1, DateRateTuplet.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEvent_Date(), this.getEFlexDate(), "date", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEvent_Notes(), ecorePackage.getEString(), "notes", null, 0, 1, Event.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize data types
    initEDataType(eMoneyEDataType, PgCurrency.class, "EMoney", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(eFlexDateEDataType, FlexDate.class, "EFlexDate", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(eColorEDataType, Color.class, "EColor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(eFixedPointValueEDataType, FixedPointValue.class, "EFixedPointValue", IS_SERIALIZABLE,
        !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(eLocalDateEDataType, LocalDate.class, "ELocalDate", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(ewgs84CoordinatesEDataType, WGS84Coordinates.class, "EWGS84Coordinates", IS_SERIALIZABLE,
        !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(eYearMonthEDataType, YearMonth.class, "EYearMonth", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //TypesPackageImpl
