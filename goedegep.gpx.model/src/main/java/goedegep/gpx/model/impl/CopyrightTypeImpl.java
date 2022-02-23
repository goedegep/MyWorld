/**
 */
package goedegep.gpx.model.impl;

import goedegep.gpx.model.CopyrightType;
import goedegep.gpx.model.GPXPackage;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Copyright Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.impl.CopyrightTypeImpl#getYear <em>Year</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.CopyrightTypeImpl#getLicense <em>License</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.CopyrightTypeImpl#getAuthor <em>Author</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CopyrightTypeImpl extends MinimalEObjectImpl.Container implements CopyrightType {
  /**
   * The default value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected static final XMLGregorianCalendar YEAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected XMLGregorianCalendar year = YEAR_EDEFAULT;

  /**
   * The default value of the '{@link #getLicense() <em>License</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLicense()
   * @generated
   * @ordered
   */
  protected static final String LICENSE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLicense() <em>License</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLicense()
   * @generated
   * @ordered
   */
  protected String license = LICENSE_EDEFAULT;

  /**
   * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAuthor()
   * @generated
   * @ordered
   */
  protected static final String AUTHOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAuthor()
   * @generated
   * @ordered
   */
  protected String author = AUTHOR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CopyrightTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPXPackage.Literals.COPYRIGHT_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XMLGregorianCalendar getYear() {
    return year;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setYear(XMLGregorianCalendar newYear) {
    XMLGregorianCalendar oldYear = year;
    year = newYear;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.COPYRIGHT_TYPE__YEAR, oldYear, year));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLicense() {
    return license;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLicense(String newLicense) {
    String oldLicense = license;
    license = newLicense;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.COPYRIGHT_TYPE__LICENSE, oldLicense, license));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAuthor() {
    return author;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAuthor(String newAuthor) {
    String oldAuthor = author;
    author = newAuthor;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.COPYRIGHT_TYPE__AUTHOR, oldAuthor, author));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case GPXPackage.COPYRIGHT_TYPE__YEAR:
        return getYear();
      case GPXPackage.COPYRIGHT_TYPE__LICENSE:
        return getLicense();
      case GPXPackage.COPYRIGHT_TYPE__AUTHOR:
        return getAuthor();
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
      case GPXPackage.COPYRIGHT_TYPE__YEAR:
        setYear((XMLGregorianCalendar)newValue);
        return;
      case GPXPackage.COPYRIGHT_TYPE__LICENSE:
        setLicense((String)newValue);
        return;
      case GPXPackage.COPYRIGHT_TYPE__AUTHOR:
        setAuthor((String)newValue);
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
      case GPXPackage.COPYRIGHT_TYPE__YEAR:
        setYear(YEAR_EDEFAULT);
        return;
      case GPXPackage.COPYRIGHT_TYPE__LICENSE:
        setLicense(LICENSE_EDEFAULT);
        return;
      case GPXPackage.COPYRIGHT_TYPE__AUTHOR:
        setAuthor(AUTHOR_EDEFAULT);
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
      case GPXPackage.COPYRIGHT_TYPE__YEAR:
        return YEAR_EDEFAULT == null ? year != null : !YEAR_EDEFAULT.equals(year);
      case GPXPackage.COPYRIGHT_TYPE__LICENSE:
        return LICENSE_EDEFAULT == null ? license != null : !LICENSE_EDEFAULT.equals(license);
      case GPXPackage.COPYRIGHT_TYPE__AUTHOR:
        return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
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
    result.append(" (year: ");
    result.append(year);
    result.append(", license: ");
    result.append(license);
    result.append(", author: ");
    result.append(author);
    result.append(')');
    return result.toString();
  }

} //CopyrightTypeImpl
