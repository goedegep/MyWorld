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
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.scene.paint.Color;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesFactoryImpl extends EFactoryImpl implements TypesFactory {
  private static final Logger LOGGER = Logger.getLogger(TypesFactoryImpl.class.getName());

  private static PgCurrencyFormat cf = new PgCurrencyFormat();
  private static FlexDateFormat FDF = new FlexDateFormat();
  private static FixedPointValueFormat FPVF = new FixedPointValueFormat();
  private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM-dd-yyyy");

  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @generated
   */
  public static TypesFactory init() {
    try {
      TypesFactory theTypesFactory = (TypesFactory) EPackage.Registry.INSTANCE.getEFactory(TypesPackage.eNS_URI);
      if (theTypesFactory != null) {
        return theTypesFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TypesFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypesFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
    case TypesPackage.FILE_REFERENCE:
      return createFileReference();
    case TypesPackage.DATE_RATE_TUPLET:
      return createDateRateTuplet();
    case TypesPackage.EVENT:
      return createEvent();
    default:
      throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
    case TypesPackage.EMONEY:
      return createEMoneyFromString(eDataType, initialValue);
    case TypesPackage.EFLEX_DATE:
      return createEFlexDateFromString(eDataType, initialValue);
    case TypesPackage.ECOLOR:
      return createEColorFromString(eDataType, initialValue);
    case TypesPackage.EFIXED_POINT_VALUE:
      return createEFixedPointValueFromString(eDataType, initialValue);
    case TypesPackage.ELOCAL_DATE:
      return createELocalDateFromString(eDataType, initialValue);
    case TypesPackage.EWGS84_COORDINATES:
      return createEWGS84CoordinatesFromString(eDataType, initialValue);
    case TypesPackage.EYEAR_MONTH:
      return createEYearMonthFromString(eDataType, initialValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
    case TypesPackage.EMONEY:
      return convertEMoneyToString(eDataType, instanceValue);
    case TypesPackage.EFLEX_DATE:
      return convertEFlexDateToString(eDataType, instanceValue);
    case TypesPackage.ECOLOR:
      return convertEColorToString(eDataType, instanceValue);
    case TypesPackage.EFIXED_POINT_VALUE:
      return convertEFixedPointValueToString(eDataType, instanceValue);
    case TypesPackage.ELOCAL_DATE:
      return convertELocalDateToString(eDataType, instanceValue);
    case TypesPackage.EWGS84_COORDINATES:
      return convertEWGS84CoordinatesToString(eDataType, instanceValue);
    case TypesPackage.EYEAR_MONTH:
      return convertEYearMonthToString(eDataType, instanceValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference createFileReference() {
    FileReferenceImpl fileReference = new FileReferenceImpl();
    return fileReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DateRateTuplet createDateRateTuplet() {
    DateRateTupletImpl dateRateTuplet = new DateRateTupletImpl();
    return dateRateTuplet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Event createEvent() {
    EventImpl event = new EventImpl();
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public PgCurrency createEMoneyFromString(EDataType eDataType, String initialValue) {
    try {
      return cf.parse(initialValue);
    } catch (ParseException e) {
      System.out.println("Exception");
      throw new IllegalArgumentException("De waarde '" + initialValue + "' is geen geldige waarde voor Money");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String convertEMoneyToString(EDataType eDataType, Object instanceValue) {
    return cf.format((PgCurrency) instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public FlexDate createEFlexDateFromString(EDataType eDataType, String initialValue) {
    try {
      return FDF.parse(initialValue);
    } catch (ParseException e) {
      System.out.println("Exception");
      throw new IllegalArgumentException("De waarde '" + initialValue + "' is geen geldige waarde voor een datum");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String convertEFlexDateToString(EDataType eDataType, Object instanceValue) {
    return FDF.format((FlexDate) instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * Format <digits>,<digits>,<digits>
   * Where <digits> consists of 1, 2, or 3 decimal numbers.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Color createEColorFromString(EDataType eDataType, String initialValue) {
    String[] colorValues = initialValue.split(",");
    Color color = new Color(Double.valueOf(colorValues[0].trim()) / 255, Double.valueOf(colorValues[1].trim()) / 255,
        Double.valueOf(colorValues[2].trim()) / 255, 1.0);
    LOGGER.fine("Color created, initialValue=" + initialValue + ", color=" + color.toString());

    return color;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String convertEColorToString(EDataType eDataType, Object instanceValue) {
    Color color = (Color) instanceValue;
    StringBuilder buf = new StringBuilder();

    buf.append(Double.toString(color.getRed()));
    buf.append(",");
    buf.append(Double.toString(color.getGreen()));
    buf.append(",");
    buf.append(Double.toString(color.getBlue()));
    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public FixedPointValue createEFixedPointValueFromString(EDataType eDataType, String initialValue) {
    try {
      return FPVF.parse(initialValue);
    } catch (ParseException e) {
      throw new IllegalArgumentException(
          "De waarde '" + initialValue + "' is geen geldige waarde voor een fixed point waarde");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String convertEFixedPointValueToString(EDataType eDataType, Object instanceValue) {
    return FPVF.format((FixedPointValue) instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public LocalDate createELocalDateFromString(EDataType eDataType, String initialValue) {
    try {
      return LocalDate.parse(initialValue, DTF);
    } catch (DateTimeParseException exc) {
      throw new IllegalArgumentException("De waarde '" + initialValue + "' is geen geldige waarde voor een datum");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String convertELocalDateToString(EDataType eDataType, Object instanceValue) {
    try {
      return ((LocalDate) instanceValue).format(DTF);
    } catch (DateTimeException exc) {
      throw new IllegalArgumentException(
          "De datum '" + instanceValue + "' kan niet naar een string geconverteerd worden.");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public WGS84Coordinates createEWGS84CoordinatesFromString(EDataType eDataType, String initialValue) {
    String[] coordinates = initialValue.split(",");
    if (coordinates.length != 2) {
      throw new IllegalArgumentException("The value '" + initialValue + "' cannot be converted to WGS84Coordinates.");
    }
    double latitude = Double.valueOf(coordinates[0].trim());
    double longitude = Double.valueOf(coordinates[1].trim());
    return new WGS84Coordinates(latitude, longitude);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEWGS84CoordinatesToString(EDataType eDataType, Object instanceValue) {
    return super.convertToString(eDataType, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YearMonth createEYearMonthFromString(EDataType eDataType, String initialValue) {
    return (YearMonth) super.createFromString(eDataType, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEYearMonthToString(EDataType eDataType, Object instanceValue) {
    return super.convertToString(eDataType, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TypesPackage getTypesPackage() {
    return (TypesPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TypesPackage getPackage() {
    return TypesPackage.eINSTANCE;
  }

} //TypesFactoryImpl
