/**
 */
package goedegep.gpx.model.util;

import goedegep.gpx.model.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see goedegep.gpx.model.GPXPackage
 * @generated
 */
public class GPXValidator extends EObjectValidator {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final GPXValidator INSTANCE = new GPXValidator();

  /**
   * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.common.util.Diagnostic#getSource()
   * @see org.eclipse.emf.common.util.Diagnostic#getCode()
   * @generated
   */
  public static final String DIAGNOSTIC_SOURCE = "goedegep.gpx.model";

  /**
   * A constant with a fixed name that can be used as the base value for additional hand written constants.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

  /**
   * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

  /**
   * The cached base package validator.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected XMLTypeValidator xmlTypeValidator;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPXValidator() {
    super();
    xmlTypeValidator = XMLTypeValidator.INSTANCE;
  }

  /**
   * Returns the package of this validator switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EPackage getEPackage() {
    return GPXPackage.eINSTANCE;
  }

  /**
   * Calls <code>validateXXX</code> for the corresponding classifier of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
    switch (classifierID) {
      case GPXPackage.BOUNDS_TYPE:
        return validateBoundsType((BoundsType)value, diagnostics, context);
      case GPXPackage.COPYRIGHT_TYPE:
        return validateCopyrightType((CopyrightType)value, diagnostics, context);
      case GPXPackage.DOCUMENT_ROOT:
        return validateDocumentRoot((DocumentRoot)value, diagnostics, context);
      case GPXPackage.EMAIL_TYPE:
        return validateEmailType((EmailType)value, diagnostics, context);
      case GPXPackage.EXTENSIONS_TYPE:
        return validateExtensionsType((ExtensionsType)value, diagnostics, context);
      case GPXPackage.GPX_TYPE:
        return validateGpxType((GpxType)value, diagnostics, context);
      case GPXPackage.LINK_TYPE:
        return validateLinkType((LinkType)value, diagnostics, context);
      case GPXPackage.METADATA_TYPE:
        return validateMetadataType((MetadataType)value, diagnostics, context);
      case GPXPackage.PERSON_TYPE:
        return validatePersonType((PersonType)value, diagnostics, context);
      case GPXPackage.PTSEG_TYPE:
        return validatePtsegType((PtsegType)value, diagnostics, context);
      case GPXPackage.PT_TYPE:
        return validatePtType((PtType)value, diagnostics, context);
      case GPXPackage.RTE_TYPE:
        return validateRteType((RteType)value, diagnostics, context);
      case GPXPackage.TRKSEG_TYPE:
        return validateTrksegType((TrksegType)value, diagnostics, context);
      case GPXPackage.TRK_TYPE:
        return validateTrkType((TrkType)value, diagnostics, context);
      case GPXPackage.WPT_TYPE:
        return validateWptType((WptType)value, diagnostics, context);
      case GPXPackage.FIX_TYPE:
        return validateFixType((FixType)value, diagnostics, context);
      case GPXPackage.DEGREES_TYPE:
        return validateDegreesType((BigDecimal)value, diagnostics, context);
      case GPXPackage.DGPS_STATION_TYPE:
        return validateDgpsStationType((BigInteger)value, diagnostics, context);
      case GPXPackage.FIX_TYPE_OBJECT:
        return validateFixTypeObject((FixType)value, diagnostics, context);
      case GPXPackage.LATITUDE_TYPE:
        return validateLatitudeType((BigDecimal)value, diagnostics, context);
      case GPXPackage.LONGITUDE_TYPE:
        return validateLongitudeType((BigDecimal)value, diagnostics, context);
      default:
        return true;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateBoundsType(BoundsType boundsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(boundsType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCopyrightType(CopyrightType copyrightType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(copyrightType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateEmailType(EmailType emailType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(emailType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateExtensionsType(ExtensionsType extensionsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(extensionsType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateGpxType(GpxType gpxType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(gpxType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLinkType(LinkType linkType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(linkType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateMetadataType(MetadataType metadataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(metadataType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validatePersonType(PersonType personType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(personType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validatePtsegType(PtsegType ptsegType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(ptsegType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validatePtType(PtType ptType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(ptType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateRteType(RteType rteType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(rteType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateTrksegType(TrksegType trksegType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(trksegType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateTrkType(TrkType trkType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(trkType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateWptType(WptType wptType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(wptType, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateFixType(FixType fixType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return true;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDegreesType(BigDecimal degreesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validateDegreesType_Min(degreesType, diagnostics, context);
    if (result || diagnostics != null) result &= validateDegreesType_Max(degreesType, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateDegreesType_Min
   */
  public static final BigDecimal DEGREES_TYPE__MIN__VALUE = new BigDecimal("0.0");

  /**
   * Validates the Min constraint of '<em>Degrees Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDegreesType_Min(BigDecimal degreesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = degreesType.compareTo(DEGREES_TYPE__MIN__VALUE) >= 0;
    if (!result && diagnostics != null)
      reportMinViolation(GPXPackage.Literals.DEGREES_TYPE, degreesType, DEGREES_TYPE__MIN__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateDegreesType_Max
   */
  public static final BigDecimal DEGREES_TYPE__MAX__VALUE = new BigDecimal("360.0");

  /**
   * Validates the Max constraint of '<em>Degrees Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDegreesType_Max(BigDecimal degreesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = degreesType.compareTo(DEGREES_TYPE__MAX__VALUE) < 0;
    if (!result && diagnostics != null)
      reportMaxViolation(GPXPackage.Literals.DEGREES_TYPE, degreesType, DEGREES_TYPE__MAX__VALUE, false, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDgpsStationType(BigInteger dgpsStationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validateDgpsStationType_Min(dgpsStationType, diagnostics, context);
    if (result || diagnostics != null) result &= validateDgpsStationType_Max(dgpsStationType, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateDgpsStationType_Min
   */
  public static final BigInteger DGPS_STATION_TYPE__MIN__VALUE = new BigInteger("0");

  /**
   * Validates the Min constraint of '<em>Dgps Station Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDgpsStationType_Min(BigInteger dgpsStationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = dgpsStationType.compareTo(DGPS_STATION_TYPE__MIN__VALUE) >= 0;
    if (!result && diagnostics != null)
      reportMinViolation(GPXPackage.Literals.DGPS_STATION_TYPE, dgpsStationType, DGPS_STATION_TYPE__MIN__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateDgpsStationType_Max
   */
  public static final BigInteger DGPS_STATION_TYPE__MAX__VALUE = new BigInteger("1023");

  /**
   * Validates the Max constraint of '<em>Dgps Station Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDgpsStationType_Max(BigInteger dgpsStationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = dgpsStationType.compareTo(DGPS_STATION_TYPE__MAX__VALUE) <= 0;
    if (!result && diagnostics != null)
      reportMaxViolation(GPXPackage.Literals.DGPS_STATION_TYPE, dgpsStationType, DGPS_STATION_TYPE__MAX__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateFixTypeObject(FixType fixTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return true;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLatitudeType(BigDecimal latitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validateLatitudeType_Min(latitudeType, diagnostics, context);
    if (result || diagnostics != null) result &= validateLatitudeType_Max(latitudeType, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateLatitudeType_Min
   */
  public static final BigDecimal LATITUDE_TYPE__MIN__VALUE = new BigDecimal("-90.0");

  /**
   * Validates the Min constraint of '<em>Latitude Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLatitudeType_Min(BigDecimal latitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = latitudeType.compareTo(LATITUDE_TYPE__MIN__VALUE) >= 0;
    if (!result && diagnostics != null)
      reportMinViolation(GPXPackage.Literals.LATITUDE_TYPE, latitudeType, LATITUDE_TYPE__MIN__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateLatitudeType_Max
   */
  public static final BigDecimal LATITUDE_TYPE__MAX__VALUE = new BigDecimal("90.0");

  /**
   * Validates the Max constraint of '<em>Latitude Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLatitudeType_Max(BigDecimal latitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = latitudeType.compareTo(LATITUDE_TYPE__MAX__VALUE) <= 0;
    if (!result && diagnostics != null)
      reportMaxViolation(GPXPackage.Literals.LATITUDE_TYPE, latitudeType, LATITUDE_TYPE__MAX__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLongitudeType(BigDecimal longitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validateLongitudeType_Min(longitudeType, diagnostics, context);
    if (result || diagnostics != null) result &= validateLongitudeType_Max(longitudeType, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateLongitudeType_Min
   */
  public static final BigDecimal LONGITUDE_TYPE__MIN__VALUE = new BigDecimal("-180.0");

  /**
   * Validates the Min constraint of '<em>Longitude Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLongitudeType_Min(BigDecimal longitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = longitudeType.compareTo(LONGITUDE_TYPE__MIN__VALUE) >= 0;
    if (!result && diagnostics != null)
      reportMinViolation(GPXPackage.Literals.LONGITUDE_TYPE, longitudeType, LONGITUDE_TYPE__MIN__VALUE, true, diagnostics, context);
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @see #validateLongitudeType_Max
   */
  public static final BigDecimal LONGITUDE_TYPE__MAX__VALUE = new BigDecimal("180.0");

  /**
   * Validates the Max constraint of '<em>Longitude Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateLongitudeType_Max(BigDecimal longitudeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = longitudeType.compareTo(LONGITUDE_TYPE__MAX__VALUE) < 0;
    if (!result && diagnostics != null)
      reportMaxViolation(GPXPackage.Literals.LONGITUDE_TYPE, longitudeType, LONGITUDE_TYPE__MAX__VALUE, false, diagnostics, context);
    return result;
  }

  /**
   * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator() {
    // Specialize this to return a resource locator for messages specific to this validator.
    // Ensure that you remove @generated or mark it @generated NOT
    return super.getResourceLocator();
  }

} //GPXValidator
