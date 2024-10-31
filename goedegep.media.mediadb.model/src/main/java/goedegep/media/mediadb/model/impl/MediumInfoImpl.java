/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Medium Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediumInfoImpl#getMediumType <em>Medium Type</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediumInfoImpl#getInformationType <em>Information Type</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediumInfoImpl#getSourceBitRate <em>Source Bit Rate</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediumInfoImpl#getSourceType <em>Source Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MediumInfoImpl extends MinimalEObjectImpl.Container implements MediumInfo {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MediumInfoImpl.class.getName());

  /**
   * The default value of the '{@link #getMediumType() <em>Medium Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMediumType()
   * @generated
   * @ordered
   */
  protected static final MediumType MEDIUM_TYPE_EDEFAULT = MediumType.NOT_SET;

  /**
   * The cached value of the '{@link #getMediumType() <em>Medium Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMediumType()
   * @generated
   * @ordered
   */
  protected MediumType mediumType = MEDIUM_TYPE_EDEFAULT;

  /**
   * This is true if the Medium Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mediumTypeESet;

  /**
   * The default value of the '{@link #getInformationType() <em>Information Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInformationType()
   * @generated
   * @ordered
   */
  protected static final InformationType INFORMATION_TYPE_EDEFAULT = InformationType.NOT_SET;

  /**
   * The cached value of the '{@link #getInformationType() <em>Information Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInformationType()
   * @generated
   * @ordered
   */
  protected InformationType informationType = INFORMATION_TYPE_EDEFAULT;

  /**
   * This is true if the Information Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean informationTypeESet;

  /**
   * The default value of the '{@link #getSourceBitRate() <em>Source Bit Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceBitRate()
   * @generated
   * @ordered
   */
  protected static final int SOURCE_BIT_RATE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getSourceBitRate() <em>Source Bit Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceBitRate()
   * @generated
   * @ordered
   */
  protected int sourceBitRate = SOURCE_BIT_RATE_EDEFAULT;

  /**
   * This is true if the Source Bit Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean sourceBitRateESet;

  /**
   * The default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceType()
   * @generated
   * @ordered
   */
  protected static final InformationType SOURCE_TYPE_EDEFAULT = InformationType.NOT_SET;

  /**
   * The cached value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceType()
   * @generated
   * @ordered
   */
  protected InformationType sourceType = SOURCE_TYPE_EDEFAULT;

  /**
   * This is true if the Source Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean sourceTypeESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MediumInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MEDIUM_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MediumType getMediumType() {
    return mediumType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMediumType(MediumType newMediumType) {
    MediumType oldMediumType = mediumType;
    mediumType = newMediumType == null ? MEDIUM_TYPE_EDEFAULT : newMediumType;
    boolean oldMediumTypeESet = mediumTypeESet;
    mediumTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE, oldMediumType,
          mediumType, !oldMediumTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMediumType() {
    MediumType oldMediumType = mediumType;
    boolean oldMediumTypeESet = mediumTypeESet;
    mediumType = MEDIUM_TYPE_EDEFAULT;
    mediumTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE, oldMediumType,
          MEDIUM_TYPE_EDEFAULT, oldMediumTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMediumType() {
    return mediumTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InformationType getInformationType() {
    return informationType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInformationType(InformationType newInformationType) {
    InformationType oldInformationType = informationType;
    informationType = newInformationType == null ? INFORMATION_TYPE_EDEFAULT : newInformationType;
    boolean oldInformationTypeESet = informationTypeESet;
    informationTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE,
          oldInformationType, informationType, !oldInformationTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInformationType() {
    InformationType oldInformationType = informationType;
    boolean oldInformationTypeESet = informationTypeESet;
    informationType = INFORMATION_TYPE_EDEFAULT;
    informationTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE,
          oldInformationType, INFORMATION_TYPE_EDEFAULT, oldInformationTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInformationType() {
    return informationTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getSourceBitRate() {
    return sourceBitRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSourceBitRate(int newSourceBitRate) {
    int oldSourceBitRate = sourceBitRate;
    sourceBitRate = newSourceBitRate;
    boolean oldSourceBitRateESet = sourceBitRateESet;
    sourceBitRateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE,
          oldSourceBitRate, sourceBitRate, !oldSourceBitRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetSourceBitRate() {
    int oldSourceBitRate = sourceBitRate;
    boolean oldSourceBitRateESet = sourceBitRateESet;
    sourceBitRate = SOURCE_BIT_RATE_EDEFAULT;
    sourceBitRateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE,
          oldSourceBitRate, SOURCE_BIT_RATE_EDEFAULT, oldSourceBitRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSourceBitRate() {
    return sourceBitRateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InformationType getSourceType() {
    return sourceType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSourceType(InformationType newSourceType) {
    InformationType oldSourceType = sourceType;
    sourceType = newSourceType == null ? SOURCE_TYPE_EDEFAULT : newSourceType;
    boolean oldSourceTypeESet = sourceTypeESet;
    sourceTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MEDIUM_INFO__SOURCE_TYPE, oldSourceType,
          sourceType, !oldSourceTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetSourceType() {
    InformationType oldSourceType = sourceType;
    boolean oldSourceTypeESet = sourceTypeESet;
    sourceType = SOURCE_TYPE_EDEFAULT;
    sourceTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MEDIUM_INFO__SOURCE_TYPE, oldSourceType,
          SOURCE_TYPE_EDEFAULT, oldSourceTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSourceType() {
    return sourceTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE:
      return getMediumType();
    case MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE:
      return getInformationType();
    case MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE:
      return getSourceBitRate();
    case MediadbPackage.MEDIUM_INFO__SOURCE_TYPE:
      return getSourceType();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE:
      setMediumType((MediumType) newValue);
      return;
    case MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE:
      setInformationType((InformationType) newValue);
      return;
    case MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE:
      setSourceBitRate((Integer) newValue);
      return;
    case MediadbPackage.MEDIUM_INFO__SOURCE_TYPE:
      setSourceType((InformationType) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE:
      unsetMediumType();
      return;
    case MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE:
      unsetInformationType();
      return;
    case MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE:
      unsetSourceBitRate();
      return;
    case MediadbPackage.MEDIUM_INFO__SOURCE_TYPE:
      unsetSourceType();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case MediadbPackage.MEDIUM_INFO__MEDIUM_TYPE:
      return isSetMediumType();
    case MediadbPackage.MEDIUM_INFO__INFORMATION_TYPE:
      return isSetInformationType();
    case MediadbPackage.MEDIUM_INFO__SOURCE_BIT_RATE:
      return isSetSourceBitRate();
    case MediadbPackage.MEDIUM_INFO__SOURCE_TYPE:
      return isSetSourceType();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("mediumType: ").append(getMediumType() != null ? getMediumType().getLiteral() : "<not set>");
    result.append(", informationType: ")
        .append(getInformationType() != null ? getInformationType().getLiteral() : "<not set>");
    result.append(", sourceTypes:").append(getSourceType() != null ? getSourceType().getLiteral() : "<not set>");
    result.append(", sourceBitRate: ").append(isSetSourceBitRate() ? getSourceBitRate() : "<not set>");

    return result.toString();
  }

} //MediumInfoImpl
