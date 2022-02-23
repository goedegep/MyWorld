/**
 */
package goedegep.gpx.model.impl;

import goedegep.gpx.model.*;

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
public class GPXFactoryImpl extends EFactoryImpl implements GPXFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static GPXFactory init() {
    try {
      GPXFactory theGPXFactory = (GPXFactory)EPackage.Registry.INSTANCE.getEFactory(GPXPackage.eNS_URI);
      if (theGPXFactory != null) {
        return theGPXFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new GPXFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPXFactoryImpl() {
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
      case GPXPackage.BOUNDS_TYPE: return createBoundsType();
      case GPXPackage.COPYRIGHT_TYPE: return createCopyrightType();
      case GPXPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case GPXPackage.EMAIL_TYPE: return createEmailType();
      case GPXPackage.EXTENSIONS_TYPE: return createExtensionsType();
      case GPXPackage.GPX_TYPE: return createGpxType();
      case GPXPackage.LINK_TYPE: return createLinkType();
      case GPXPackage.METADATA_TYPE: return createMetadataType();
      case GPXPackage.PERSON_TYPE: return createPersonType();
      case GPXPackage.PTSEG_TYPE: return createPtsegType();
      case GPXPackage.PT_TYPE: return createPtType();
      case GPXPackage.RTE_TYPE: return createRteType();
      case GPXPackage.TRKSEG_TYPE: return createTrksegType();
      case GPXPackage.TRK_TYPE: return createTrkType();
      case GPXPackage.WPT_TYPE: return createWptType();
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
      case GPXPackage.FIX_TYPE:
        return createFixTypeFromString(eDataType, initialValue);
      case GPXPackage.DEGREES_TYPE:
        return createDegreesTypeFromString(eDataType, initialValue);
      case GPXPackage.DGPS_STATION_TYPE:
        return createDgpsStationTypeFromString(eDataType, initialValue);
      case GPXPackage.FIX_TYPE_OBJECT:
        return createFixTypeObjectFromString(eDataType, initialValue);
      case GPXPackage.LATITUDE_TYPE:
        return createLatitudeTypeFromString(eDataType, initialValue);
      case GPXPackage.LONGITUDE_TYPE:
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
      case GPXPackage.FIX_TYPE:
        return convertFixTypeToString(eDataType, instanceValue);
      case GPXPackage.DEGREES_TYPE:
        return convertDegreesTypeToString(eDataType, instanceValue);
      case GPXPackage.DGPS_STATION_TYPE:
        return convertDgpsStationTypeToString(eDataType, instanceValue);
      case GPXPackage.FIX_TYPE_OBJECT:
        return convertFixTypeObjectToString(eDataType, instanceValue);
      case GPXPackage.LATITUDE_TYPE:
        return convertLatitudeTypeToString(eDataType, instanceValue);
      case GPXPackage.LONGITUDE_TYPE:
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
  public CopyrightType createCopyrightType() {
    CopyrightTypeImpl copyrightType = new CopyrightTypeImpl();
    return copyrightType;
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
  public EmailType createEmailType() {
    EmailTypeImpl emailType = new EmailTypeImpl();
    return emailType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExtensionsType createExtensionsType() {
    ExtensionsTypeImpl extensionsType = new ExtensionsTypeImpl();
    return extensionsType;
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
  public LinkType createLinkType() {
    LinkTypeImpl linkType = new LinkTypeImpl();
    return linkType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataType createMetadataType() {
    MetadataTypeImpl metadataType = new MetadataTypeImpl();
    return metadataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PersonType createPersonType() {
    PersonTypeImpl personType = new PersonTypeImpl();
    return personType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PtsegType createPtsegType() {
    PtsegTypeImpl ptsegType = new PtsegTypeImpl();
    return ptsegType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PtType createPtType() {
    PtTypeImpl ptType = new PtTypeImpl();
    return ptType;
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
  public FixType createFixTypeObjectFromString(EDataType eDataType, String initialValue) {
    return createFixTypeFromString(GPXPackage.Literals.FIX_TYPE, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFixTypeObjectToString(EDataType eDataType, Object instanceValue) {
    return convertFixTypeToString(GPXPackage.Literals.FIX_TYPE, instanceValue);
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
  public GPXPackage getGPXPackage() {
    return (GPXPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static GPXPackage getPackage() {
    return GPXPackage.eINSTANCE;
  }

} //GPXFactoryImpl
