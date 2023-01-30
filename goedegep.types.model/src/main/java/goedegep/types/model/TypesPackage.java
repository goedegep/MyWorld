/**
 */
package goedegep.types.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.types.model.TypesFactory
 * @model kind="package"
 * @generated
 */
public interface TypesPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http:///goedegep.types/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "types";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TypesPackage eINSTANCE = goedegep.types.model.impl.TypesPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.types.model.impl.FileReferenceImpl <em>File Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.types.model.impl.FileReferenceImpl
   * @see goedegep.types.model.impl.TypesPackageImpl#getFileReference()
   * @generated
   */
  int FILE_REFERENCE = 0;

  /**
   * The feature id for the '<em><b>File</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__FILE = 0;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__TITLE = 1;

  /**
   * The feature id for the '<em><b>Tags</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__TAGS = 2;

  /**
   * The number of structural features of the '<em>File Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>File Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link java.lang.Comparable <em>Comparable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.lang.Comparable
   * @see goedegep.types.model.impl.TypesPackageImpl#getComparable()
   * @generated
   */
  int COMPARABLE = 1;

  /**
   * The number of structural features of the '<em>Comparable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARABLE_FEATURE_COUNT = 0;

  /**
   * The number of operations of the '<em>Comparable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARABLE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.types.model.impl.DateRateTupletImpl <em>Date Rate Tuplet</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.types.model.impl.DateRateTupletImpl
   * @see goedegep.types.model.impl.TypesPackageImpl#getDateRateTuplet()
   * @generated
   */
  int DATE_RATE_TUPLET = 2;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATE_RATE_TUPLET__DATE = 0;

  /**
   * The feature id for the '<em><b>Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATE_RATE_TUPLET__RATE = 1;

  /**
   * The number of structural features of the '<em>Date Rate Tuplet</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATE_RATE_TUPLET_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Date Rate Tuplet</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATE_RATE_TUPLET_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.types.model.impl.EventImpl <em>Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.types.model.impl.EventImpl
   * @see goedegep.types.model.impl.TypesPackageImpl#getEvent()
   * @generated
   */
  int EVENT = 3;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__DATE = 0;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__NOTES = 1;

  /**
   * The number of structural features of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '<em>EMoney</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.util.money.PgCurrency
   * @see goedegep.types.model.impl.TypesPackageImpl#getEMoney()
   * @generated
   */
  int EMONEY = 4;

  /**
   * The meta object id for the '<em>EFlex Date</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.util.datetime.FlexDate
   * @see goedegep.types.model.impl.TypesPackageImpl#getEFlexDate()
   * @generated
   */
  int EFLEX_DATE = 5;

  /**
   * The meta object id for the '<em>EColor</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see javafx.scene.paint.Color
   * @see goedegep.types.model.impl.TypesPackageImpl#getEColor()
   * @generated
   */
  int ECOLOR = 6;

  /**
   * The meta object id for the '<em>EFixed Point Value</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.util.fixedpointvalue.FixedPointValue
   * @see goedegep.types.model.impl.TypesPackageImpl#getEFixedPointValue()
   * @generated
   */
  int EFIXED_POINT_VALUE = 7;

  /**
   * The meta object id for the '<em>ELocal Date</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.time.LocalDate
   * @see goedegep.types.model.impl.TypesPackageImpl#getELocalDate()
   * @generated
   */
  int ELOCAL_DATE = 8;

  /**
   * The meta object id for the '<em>EWGS84 Coordinates</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.geo.WGS84Coordinates
   * @see goedegep.types.model.impl.TypesPackageImpl#getEWGS84Coordinates()
   * @generated
   */
  int EWGS84_COORDINATES = 9;

  /**
   * The meta object id for the '<em>EYear Month</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.time.YearMonth
   * @see goedegep.types.model.impl.TypesPackageImpl#getEYearMonth()
   * @generated
   */
  int EYEAR_MONTH = 10;

  /**
   * Returns the meta object for class '{@link goedegep.types.model.FileReference <em>File Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Reference</em>'.
   * @see goedegep.types.model.FileReference
   * @generated
   */
  EClass getFileReference();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.FileReference#getFile <em>File</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File</em>'.
   * @see goedegep.types.model.FileReference#getFile()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_File();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.FileReference#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.types.model.FileReference#getTitle()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.FileReference#getTags <em>Tags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tags</em>'.
   * @see goedegep.types.model.FileReference#getTags()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_Tags();

  /**
   * Returns the meta object for class '{@link java.lang.Comparable <em>Comparable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Comparable</em>'.
   * @see java.lang.Comparable
   * @model instanceClass="java.lang.Comparable" typeParameters="T"
   * @generated
   */
  EClass getComparable();

  /**
   * Returns the meta object for class '{@link goedegep.types.model.DateRateTuplet <em>Date Rate Tuplet</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Date Rate Tuplet</em>'.
   * @see goedegep.types.model.DateRateTuplet
   * @generated
   */
  EClass getDateRateTuplet();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.DateRateTuplet#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.types.model.DateRateTuplet#getDate()
   * @see #getDateRateTuplet()
   * @generated
   */
  EAttribute getDateRateTuplet_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.DateRateTuplet#getRate <em>Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Rate</em>'.
   * @see goedegep.types.model.DateRateTuplet#getRate()
   * @see #getDateRateTuplet()
   * @generated
   */
  EAttribute getDateRateTuplet_Rate();

  /**
   * Returns the meta object for class '{@link goedegep.types.model.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event</em>'.
   * @see goedegep.types.model.Event
   * @generated
   */
  EClass getEvent();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.Event#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.types.model.Event#getDate()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.types.model.Event#getNotes <em>Notes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Notes</em>'.
   * @see goedegep.types.model.Event#getNotes()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Notes();

  /**
   * Returns the meta object for data type '{@link goedegep.util.money.PgCurrency <em>EMoney</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EMoney</em>'.
   * @see goedegep.util.money.PgCurrency
   * @model instanceClass="goedegep.util.money.PgCurrency"
   * @generated
   */
  EDataType getEMoney();

  /**
   * Returns the meta object for data type '{@link goedegep.util.datetime.FlexDate <em>EFlex Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EFlex Date</em>'.
   * @see goedegep.util.datetime.FlexDate
   * @model instanceClass="goedegep.util.datetime.FlexDate"
   * @generated
   */
  EDataType getEFlexDate();

  /**
   * Returns the meta object for data type '{@link javafx.scene.paint.Color <em>EColor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EColor</em>'.
   * @see javafx.scene.paint.Color
   * @model instanceClass="javafx.scene.paint.Color"
   * @generated
   */
  EDataType getEColor();

  /**
   * Returns the meta object for data type '{@link goedegep.util.fixedpointvalue.FixedPointValue <em>EFixed Point Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EFixed Point Value</em>'.
   * @see goedegep.util.fixedpointvalue.FixedPointValue
   * @model instanceClass="goedegep.util.fixedpointvalue.FixedPointValue"
   * @generated
   */
  EDataType getEFixedPointValue();

  /**
   * Returns the meta object for data type '{@link java.time.LocalDate <em>ELocal Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>ELocal Date</em>'.
   * @see java.time.LocalDate
   * @model instanceClass="java.time.LocalDate"
   * @generated
   */
  EDataType getELocalDate();

  /**
   * Returns the meta object for data type '{@link goedegep.geo.WGS84Coordinates <em>EWGS84 Coordinates</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EWGS84 Coordinates</em>'.
   * @see goedegep.geo.WGS84Coordinates
   * @model instanceClass="goedegep.geo.WGS84Coordinates"
   * @generated
   */
  EDataType getEWGS84Coordinates();

  /**
   * Returns the meta object for data type '{@link java.time.YearMonth <em>EYear Month</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>EYear Month</em>'.
   * @see java.time.YearMonth
   * @model instanceClass="java.time.YearMonth"
   * @generated
   */
  EDataType getEYearMonth();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  TypesFactory getTypesFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
    	 * The meta object literal for the '{@link goedegep.types.model.impl.FileReferenceImpl <em>File Reference</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.types.model.impl.FileReferenceImpl
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getFileReference()
    	 * @generated
    	 */
    EClass FILE_REFERENCE = eINSTANCE.getFileReference();

    /**
    	 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FILE_REFERENCE__FILE = eINSTANCE.getFileReference_File();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FILE_REFERENCE__TITLE = eINSTANCE.getFileReference_Title();

    /**
    	 * The meta object literal for the '<em><b>Tags</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FILE_REFERENCE__TAGS = eINSTANCE.getFileReference_Tags();

    /**
    	 * The meta object literal for the '{@link java.lang.Comparable <em>Comparable</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see java.lang.Comparable
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getComparable()
    	 * @generated
    	 */
    EClass COMPARABLE = eINSTANCE.getComparable();

    /**
    	 * The meta object literal for the '{@link goedegep.types.model.impl.DateRateTupletImpl <em>Date Rate Tuplet</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.types.model.impl.DateRateTupletImpl
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getDateRateTuplet()
    	 * @generated
    	 */
    EClass DATE_RATE_TUPLET = eINSTANCE.getDateRateTuplet();

    /**
    	 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DATE_RATE_TUPLET__DATE = eINSTANCE.getDateRateTuplet_Date();

    /**
    	 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DATE_RATE_TUPLET__RATE = eINSTANCE.getDateRateTuplet_Rate();

    /**
    	 * The meta object literal for the '{@link goedegep.types.model.impl.EventImpl <em>Event</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.types.model.impl.EventImpl
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEvent()
    	 * @generated
    	 */
    EClass EVENT = eINSTANCE.getEvent();

    /**
    	 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute EVENT__DATE = eINSTANCE.getEvent_Date();

    /**
    	 * The meta object literal for the '<em><b>Notes</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute EVENT__NOTES = eINSTANCE.getEvent_Notes();

    /**
    	 * The meta object literal for the '<em>EMoney</em>' data type.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.util.money.PgCurrency
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEMoney()
    	 * @generated
    	 */
    EDataType EMONEY = eINSTANCE.getEMoney();

    /**
    	 * The meta object literal for the '<em>EFlex Date</em>' data type.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.util.datetime.FlexDate
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEFlexDate()
    	 * @generated
    	 */
    EDataType EFLEX_DATE = eINSTANCE.getEFlexDate();

    /**
    	 * The meta object literal for the '<em>EColor</em>' data type.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see javafx.scene.paint.Color
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEColor()
    	 * @generated
    	 */
    EDataType ECOLOR = eINSTANCE.getEColor();

    /**
    	 * The meta object literal for the '<em>EFixed Point Value</em>' data type.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.util.fixedpointvalue.FixedPointValue
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEFixedPointValue()
    	 * @generated
    	 */
    EDataType EFIXED_POINT_VALUE = eINSTANCE.getEFixedPointValue();

    /**
    	 * The meta object literal for the '<em>ELocal Date</em>' data type.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see java.time.LocalDate
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getELocalDate()
    	 * @generated
    	 */
    EDataType ELOCAL_DATE = eINSTANCE.getELocalDate();

    /**
    	 * The meta object literal for the '<em>EWGS84 Coordinates</em>' data type.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.geo.WGS84Coordinates
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEWGS84Coordinates()
    	 * @generated
    	 */
    EDataType EWGS84_COORDINATES = eINSTANCE.getEWGS84Coordinates();

    /**
    	 * The meta object literal for the '<em>EYear Month</em>' data type.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see java.time.YearMonth
    	 * @see goedegep.types.model.impl.TypesPackageImpl#getEYearMonth()
    	 * @generated
    	 */
    EDataType EYEAR_MONTH = eINSTANCE.getEYearMonth();

  }

} //TypesPackage
