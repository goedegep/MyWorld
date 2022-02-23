/**
 */
package goedegep.finan.lynx2finan.model.impl;

import goedegep.finan.lynx2finan.model.LynxToFinanPackage;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lynx To Finan Share Id List Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getUniqueId <em>Unique Id</em>}</li>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getSecurityName <em>Security Name</em>}</li>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getFinanName <em>Finan Name</em>}</li>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getFiId <em>Fi Id</em>}</li>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getUniqueIdType <em>Unique Id Type</em>}</li>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl#getTickerSymbol <em>Ticker Symbol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LynxToFinanShareIdListEntryImpl extends MinimalEObjectImpl.Container implements LynxToFinanShareIdListEntry {
  /**
   * The default value of the '{@link #getUniqueId() <em>Unique Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUniqueId()
   * @generated
   * @ordered
   */
  protected static final String UNIQUE_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUniqueId() <em>Unique Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUniqueId()
   * @generated
   * @ordered
   */
  protected String uniqueId = UNIQUE_ID_EDEFAULT;

  /**
   * This is true if the Unique Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean uniqueIdESet;

  /**
   * The default value of the '{@link #getSecurityName() <em>Security Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSecurityName()
   * @generated
   * @ordered
   */
  protected static final String SECURITY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSecurityName() <em>Security Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSecurityName()
   * @generated
   * @ordered
   */
  protected String securityName = SECURITY_NAME_EDEFAULT;

  /**
   * This is true if the Security Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean securityNameESet;

  /**
   * The default value of the '{@link #getFinanName() <em>Finan Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFinanName()
   * @generated
   * @ordered
   */
  protected static final String FINAN_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFinanName() <em>Finan Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFinanName()
   * @generated
   * @ordered
   */
  protected String finanName = FINAN_NAME_EDEFAULT;

  /**
   * This is true if the Finan Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean finanNameESet;

  /**
   * The default value of the '{@link #getFiId() <em>Fi Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFiId()
   * @generated
   * @ordered
   */
  protected static final String FI_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFiId() <em>Fi Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFiId()
   * @generated
   * @ordered
   */
  protected String fiId = FI_ID_EDEFAULT;

  /**
   * This is true if the Fi Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fiIdESet;

  /**
   * The default value of the '{@link #getUniqueIdType() <em>Unique Id Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUniqueIdType()
   * @generated
   * @ordered
   */
  protected static final String UNIQUE_ID_TYPE_EDEFAULT = "";

  /**
   * The cached value of the '{@link #getUniqueIdType() <em>Unique Id Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUniqueIdType()
   * @generated
   * @ordered
   */
  protected String uniqueIdType = UNIQUE_ID_TYPE_EDEFAULT;

  /**
   * This is true if the Unique Id Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean uniqueIdTypeESet;

  /**
   * The default value of the '{@link #getTickerSymbol() <em>Ticker Symbol</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTickerSymbol()
   * @generated
   * @ordered
   */
  protected static final String TICKER_SYMBOL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTickerSymbol() <em>Ticker Symbol</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTickerSymbol()
   * @generated
   * @ordered
   */
  protected String tickerSymbol = TICKER_SYMBOL_EDEFAULT;

  /**
   * This is true if the Ticker Symbol attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean tickerSymbolESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LynxToFinanShareIdListEntryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return LynxToFinanPackage.Literals.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUniqueId(String newUniqueId) {
    String oldUniqueId = uniqueId;
    uniqueId = newUniqueId;
    boolean oldUniqueIdESet = uniqueIdESet;
    uniqueIdESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID, oldUniqueId, uniqueId, !oldUniqueIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetUniqueId() {
    String oldUniqueId = uniqueId;
    boolean oldUniqueIdESet = uniqueIdESet;
    uniqueId = UNIQUE_ID_EDEFAULT;
    uniqueIdESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID, oldUniqueId, UNIQUE_ID_EDEFAULT, oldUniqueIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetUniqueId() {
    return uniqueIdESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getSecurityName() {
    return securityName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSecurityName(String newSecurityName) {
    String oldSecurityName = securityName;
    securityName = newSecurityName;
    boolean oldSecurityNameESet = securityNameESet;
    securityNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME, oldSecurityName, securityName, !oldSecurityNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetSecurityName() {
    String oldSecurityName = securityName;
    boolean oldSecurityNameESet = securityNameESet;
    securityName = SECURITY_NAME_EDEFAULT;
    securityNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME, oldSecurityName, SECURITY_NAME_EDEFAULT, oldSecurityNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSecurityName() {
    return securityNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFinanName() {
    return finanName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFinanName(String newFinanName) {
    String oldFinanName = finanName;
    finanName = newFinanName;
    boolean oldFinanNameESet = finanNameESet;
    finanNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME, oldFinanName, finanName, !oldFinanNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFinanName() {
    String oldFinanName = finanName;
    boolean oldFinanNameESet = finanNameESet;
    finanName = FINAN_NAME_EDEFAULT;
    finanNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME, oldFinanName, FINAN_NAME_EDEFAULT, oldFinanNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFinanName() {
    return finanNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFiId() {
    return fiId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFiId(String newFiId) {
    String oldFiId = fiId;
    fiId = newFiId;
    boolean oldFiIdESet = fiIdESet;
    fiIdESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID, oldFiId, fiId, !oldFiIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFiId() {
    String oldFiId = fiId;
    boolean oldFiIdESet = fiIdESet;
    fiId = FI_ID_EDEFAULT;
    fiIdESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID, oldFiId, FI_ID_EDEFAULT, oldFiIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFiId() {
    return fiIdESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getUniqueIdType() {
    return uniqueIdType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUniqueIdType(String newUniqueIdType) {
    String oldUniqueIdType = uniqueIdType;
    uniqueIdType = newUniqueIdType;
    boolean oldUniqueIdTypeESet = uniqueIdTypeESet;
    uniqueIdTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE, oldUniqueIdType, uniqueIdType, !oldUniqueIdTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetUniqueIdType() {
    String oldUniqueIdType = uniqueIdType;
    boolean oldUniqueIdTypeESet = uniqueIdTypeESet;
    uniqueIdType = UNIQUE_ID_TYPE_EDEFAULT;
    uniqueIdTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE, oldUniqueIdType, UNIQUE_ID_TYPE_EDEFAULT, oldUniqueIdTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetUniqueIdType() {
    return uniqueIdTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTickerSymbol(String newTickerSymbol) {
    String oldTickerSymbol = tickerSymbol;
    tickerSymbol = newTickerSymbol;
    boolean oldTickerSymbolESet = tickerSymbolESet;
    tickerSymbolESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL, oldTickerSymbol, tickerSymbol, !oldTickerSymbolESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTickerSymbol() {
    String oldTickerSymbol = tickerSymbol;
    boolean oldTickerSymbolESet = tickerSymbolESet;
    tickerSymbol = TICKER_SYMBOL_EDEFAULT;
    tickerSymbolESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL, oldTickerSymbol, TICKER_SYMBOL_EDEFAULT, oldTickerSymbolESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTickerSymbol() {
    return tickerSymbolESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID:
        return getUniqueId();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME:
        return getSecurityName();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME:
        return getFinanName();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID:
        return getFiId();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE:
        return getUniqueIdType();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL:
        return getTickerSymbol();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID:
        setUniqueId((String)newValue);
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME:
        setSecurityName((String)newValue);
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME:
        setFinanName((String)newValue);
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID:
        setFiId((String)newValue);
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE:
        setUniqueIdType((String)newValue);
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL:
        setTickerSymbol((String)newValue);
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID:
        unsetUniqueId();
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME:
        unsetSecurityName();
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME:
        unsetFinanName();
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID:
        unsetFiId();
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE:
        unsetUniqueIdType();
        return;
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL:
        unsetTickerSymbol();
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID:
        return isSetUniqueId();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME:
        return isSetSecurityName();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME:
        return isSetFinanName();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID:
        return isSetFiId();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE:
        return isSetUniqueIdType();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL:
        return isSetTickerSymbol();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (uniqueId: ");
    if (uniqueIdESet) result.append(uniqueId); else result.append("<unset>");
    result.append(", securityName: ");
    if (securityNameESet) result.append(securityName); else result.append("<unset>");
    result.append(", finanName: ");
    if (finanNameESet) result.append(finanName); else result.append("<unset>");
    result.append(", fiId: ");
    if (fiIdESet) result.append(fiId); else result.append("<unset>");
    result.append(", uniqueIdType: ");
    if (uniqueIdTypeESet) result.append(uniqueIdType); else result.append("<unset>");
    result.append(", tickerSymbol: ");
    if (tickerSymbolESet) result.append(tickerSymbol); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //LynxToFinanShareIdListEntryImpl
