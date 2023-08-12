/**
 */
package goedegep.gpx10.model;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gpx Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx10.model.GpxType#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getDesc <em>Desc</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getAuthor <em>Author</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getEmail <em>Email</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getUrl <em>Url</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getUrlname <em>Urlname</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getTime <em>Time</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getBounds <em>Bounds</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getWpt <em>Wpt</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getRte <em>Rte</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getTrk <em>Trk</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getAny <em>Any</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getVersion <em>Version</em>}</li>
 *   <li>{@link goedegep.gpx10.model.GpxType#getCreator <em>Creator</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx10.model.GPX10Package#getGpxType()
 * @model extendedMetaData="name='gpx_._type' kind='elementOnly'"
 * @generated
 */
public interface GpxType extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Desc</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Desc</em>' attribute.
   * @see #setDesc(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Desc()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='desc' namespace='##targetNamespace'"
   * @generated
   */
  String getDesc();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getDesc <em>Desc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Desc</em>' attribute.
   * @see #getDesc()
   * @generated
   */
  void setDesc(String value);

  /**
   * Returns the value of the '<em><b>Author</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Author</em>' attribute.
   * @see #setAuthor(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Author()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='author' namespace='##targetNamespace'"
   * @generated
   */
  String getAuthor();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getAuthor <em>Author</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Author</em>' attribute.
   * @see #getAuthor()
   * @generated
   */
  void setAuthor(String value);

  /**
   * Returns the value of the '<em><b>Email</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Email</em>' attribute.
   * @see #setEmail(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Email()
   * @model dataType="goedegep.gpx10.model.EmailType"
   *        extendedMetaData="kind='element' name='email' namespace='##targetNamespace'"
   * @generated
   */
  String getEmail();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getEmail <em>Email</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Email</em>' attribute.
   * @see #getEmail()
   * @generated
   */
  void setEmail(String value);

  /**
   * Returns the value of the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Url</em>' attribute.
   * @see #setUrl(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Url()
   * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
   *        extendedMetaData="kind='element' name='url' namespace='##targetNamespace'"
   * @generated
   */
  String getUrl();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getUrl <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Url</em>' attribute.
   * @see #getUrl()
   * @generated
   */
  void setUrl(String value);

  /**
   * Returns the value of the '<em><b>Urlname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Urlname</em>' attribute.
   * @see #setUrlname(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Urlname()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='urlname' namespace='##targetNamespace'"
   * @generated
   */
  String getUrlname();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getUrlname <em>Urlname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Urlname</em>' attribute.
   * @see #getUrlname()
   * @generated
   */
  void setUrlname(String value);

  /**
   * Returns the value of the '<em><b>Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Time</em>' attribute.
   * @see #setTime(XMLGregorianCalendar)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Time()
   * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
   *        extendedMetaData="kind='element' name='time' namespace='##targetNamespace'"
   * @generated
   */
  XMLGregorianCalendar getTime();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getTime <em>Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time</em>' attribute.
   * @see #getTime()
   * @generated
   */
  void setTime(XMLGregorianCalendar value);

  /**
   * Returns the value of the '<em><b>Keywords</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Keywords</em>' attribute.
   * @see #setKeywords(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Keywords()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='keywords' namespace='##targetNamespace'"
   * @generated
   */
  String getKeywords();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getKeywords <em>Keywords</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Keywords</em>' attribute.
   * @see #getKeywords()
   * @generated
   */
  void setKeywords(String value);

  /**
   * Returns the value of the '<em><b>Bounds</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bounds</em>' containment reference.
   * @see #setBounds(BoundsType)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Bounds()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='bounds' namespace='##targetNamespace'"
   * @generated
   */
  BoundsType getBounds();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getBounds <em>Bounds</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bounds</em>' containment reference.
   * @see #getBounds()
   * @generated
   */
  void setBounds(BoundsType value);

  /**
   * Returns the value of the '<em><b>Wpt</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx10.model.WptType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Wpt</em>' containment reference list.
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Wpt()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='wpt' namespace='##targetNamespace'"
   * @generated
   */
  EList<WptType> getWpt();

  /**
   * Returns the value of the '<em><b>Rte</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx10.model.RteType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rte</em>' containment reference list.
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Rte()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='rte' namespace='##targetNamespace'"
   * @generated
   */
  EList<RteType> getRte();

  /**
   * Returns the value of the '<em><b>Trk</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx10.model.TrkType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trk</em>' containment reference list.
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Trk()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trk' namespace='##targetNamespace'"
   * @generated
   */
  EList<TrkType> getTrk();

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':12' processing='strict'"
   * @generated
   */
  FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Version</b></em>' attribute.
   * The default value is <code>"1.0"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Version</em>' attribute.
   * @see #isSetVersion()
   * @see #unsetVersion()
   * @see #setVersion(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Version()
   * @model default="1.0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='version'"
   * @generated
   */
  String getVersion();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Version</em>' attribute.
   * @see #isSetVersion()
   * @see #unsetVersion()
   * @see #getVersion()
   * @generated
   */
  void setVersion(String value);

  /**
   * Unsets the value of the '{@link goedegep.gpx10.model.GpxType#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVersion()
   * @see #getVersion()
   * @see #setVersion(String)
   * @generated
   */
  void unsetVersion();

  /**
   * Returns whether the value of the '{@link goedegep.gpx10.model.GpxType#getVersion <em>Version</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Version</em>' attribute is set.
   * @see #unsetVersion()
   * @see #getVersion()
   * @see #setVersion(String)
   * @generated
   */
  boolean isSetVersion();

  /**
   * Returns the value of the '<em><b>Creator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Creator</em>' attribute.
   * @see #setCreator(String)
   * @see goedegep.gpx10.model.GPX10Package#getGpxType_Creator()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='creator'"
   * @generated
   */
  String getCreator();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.GpxType#getCreator <em>Creator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Creator</em>' attribute.
   * @see #getCreator()
   * @generated
   */
  void setCreator(String value);

} // GpxType
