/**
 */
package goedegep.gpx10.model.impl;

import goedegep.gpx10.model.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GPX10FactoryImpl extends EFactoryImpl implements GPX10Factory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static GPX10Factory init() {
    try {
      GPX10Factory theGPX10Factory = (GPX10Factory)EPackage.Registry.INSTANCE.getEFactory(GPX10Package.eNS_URI);
      if (theGPX10Factory != null) {
        return theGPX10Factory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new GPX10FactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPX10FactoryImpl() {
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
      case GPX10Package.BOUNDS_TYPE: return createBoundsType();
      case GPX10Package.DOCUMENT_ROOT: return createDocumentRoot();
      case GPX10Package.GPX_TYPE: return createGpxType();
      case GPX10Package.RTEPT_TYPE: return createRteptType();
      case GPX10Package.RTE_TYPE: return createRteType();
      case GPX10Package.TRKPT_TYPE: return createTrkptType();
      case GPX10Package.TRKSEG_TYPE: return createTrksegType();
      case GPX10Package.TRK_TYPE: return createTrkType();
      case GPX10Package.WPT_TYPE: return createWptType();
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
      case GPX10Package.FIX_TYPE:
        return createFixTypeFromString(eDataType, initialValue);
      case GPX10Package.DEGREES_TYPE:
        return createDegreesTypeFromString(eDataType, initialValue);
      case GPX10Package.DGPS_STATION_TYPE:
        return createDgpsStationTypeFromString(eDataType, initialValue);
      case GPX10Package.EMAIL_TYPE:
        return createEmailTypeFromString(eDataType, initialValue);
      case GPX10Package.FIX_TYPE_OBJECT:
        return createFixTypeObjectFromString(eDataType, initialValue);
      case GPX10Package.LATITUDE_TYPE:
        return createLatitudeTypeFromString(eDataType, initialValue);
      case GPX10Package.LONGITUDE_TYPE:
        return createLongitudeTypeFromString(eDataType, initialValue);
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
      case GPX10Package.FIX_TYPE:
        return convertFixTypeToString(eDataType, instanceValue);
      case GPX10Package.DEGREES_TYPE:
        return convertDegreesTypeToString(eDataType, instanceValue);
      case GPX10Package.DGPS_STATION_TYPE:
        return convertDgpsStationTypeToString(eDataType, instanceValue);
      case GPX10Package.EMAIL_TYPE:
        return convertEmailTypeToString(eDataType, instanceValue);
      case GPX10Package.FIX_TYPE_OBJECT:
        return convertFixTypeObjectToString(eDataType, instanceValue);
      case GPX10Package.LATITUDE_TYPE:
        return convertLatitudeTypeToString(eDataType, instanceValue);
      case GPX10Package.LONGITUDE_TYPE:
        return convertLongitudeTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoundsType createBoundsType() {
    BoundsTypeImpl boundsType = new BoundsTypeImpl();
    return boundsType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentRoot createDocumentRoot() {
    DocumentRootImpl documentRoot = new DocumentRootImpl();
    return documentRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GpxType createGpxType() {
    GpxTypeImpl gpxType = new GpxTypeImpl();
    return gpxType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RteptType createRteptType() {
    RteptTypeImpl rteptType = new RteptTypeImpl();
    return rteptType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RteType createRteType() {
    RteTypeImpl rteType = new RteTypeImpl();
    return rteType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrkptType createTrkptType() {
    TrkptTypeImpl trkptType = new TrkptTypeImpl();
    return trkptType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrksegType createTrksegType() {
    TrksegTypeImpl trksegType = new TrksegTypeImpl();
    return trksegType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrkType createTrkType() {
    TrkTypeImpl trkType = new TrkTypeImpl();
    return trkType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WptType createWptType() {
    WptTypeImpl wptType = new WptTypeImpl();
    return wptType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FixType createFixTypeFromString(EDataType eDataType, String initialValue) {
    FixType result = FixType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFixTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal createDegreesTypeFromString(EDataType eDataType, String initialValue) {
    return (BigDecimal)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DECIMAL, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDegreesTypeToString(EDataType eDataType, Object instanceValue) {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DECIMAL, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigInteger createDgpsStationTypeFromString(EDataType eDataType, String initialValue) {
    return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.INTEGER, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDgpsStationTypeToString(EDataType eDataType, Object instanceValue) {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.INTEGER, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String createEmailTypeFromString(EDataType eDataType, String initialValue) {
    return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEmailTypeToString(EDataType eDataType, Object instanceValue) {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FixType createFixTypeObjectFromString(EDataType eDataType, String initialValue) {
    return createFixTypeFromString(GPX10Package.Literals.FIX_TYPE, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFixTypeObjectToString(EDataType eDataType, Object instanceValue) {
    return convertFixTypeToString(GPX10Package.Literals.FIX_TYPE, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal createLatitudeTypeFromString(EDataType eDataType, String initialValue) {
    return (BigDecimal)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DECIMAL, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertLatitudeTypeToString(EDataType eDataType, Object instanceValue) {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DECIMAL, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal createLongitudeTypeFromString(EDataType eDataType, String initialValue) {
    return (BigDecimal)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DECIMAL, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertLongitudeTypeToString(EDataType eDataType, Object instanceValue) {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DECIMAL, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPX10Package getGPX10Package() {
    return (GPX10Package)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static GPX10Package getPackage() {
    return GPX10Package.eINSTANCE;
  }

} //GPX10FactoryImpl
