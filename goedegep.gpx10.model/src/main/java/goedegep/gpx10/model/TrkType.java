/**
 */
package goedegep.gpx10.model;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trk Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx10.model.TrkType#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getCmt <em>Cmt</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getDesc <em>Desc</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getSrc <em>Src</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getUrl <em>Url</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getUrlname <em>Urlname</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getNumber <em>Number</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getAny <em>Any</em>}</li>
 *   <li>{@link goedegep.gpx10.model.TrkType#getTrkseg <em>Trkseg</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx10.model.GPX10Package#getTrkType()
 * @model extendedMetaData="name='trk_._type' kind='elementOnly'"
 * @generated
 */
public interface TrkType extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Cmt</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cmt</em>' attribute.
   * @see #setCmt(String)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Cmt()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='cmt' namespace='##targetNamespace'"
   * @generated
   */
  String getCmt();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getCmt <em>Cmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cmt</em>' attribute.
   * @see #getCmt()
   * @generated
   */
  void setCmt(String value);

  /**
   * Returns the value of the '<em><b>Desc</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Desc</em>' attribute.
   * @see #setDesc(String)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Desc()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='desc' namespace='##targetNamespace'"
   * @generated
   */
  String getDesc();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getDesc <em>Desc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Desc</em>' attribute.
   * @see #getDesc()
   * @generated
   */
  void setDesc(String value);

  /**
   * Returns the value of the '<em><b>Src</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Src</em>' attribute.
   * @see #setSrc(String)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Src()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='src' namespace='##targetNamespace'"
   * @generated
   */
  String getSrc();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getSrc <em>Src</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Src</em>' attribute.
   * @see #getSrc()
   * @generated
   */
  void setSrc(String value);

  /**
   * Returns the value of the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Url</em>' attribute.
   * @see #setUrl(String)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Url()
   * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
   *        extendedMetaData="kind='element' name='url' namespace='##targetNamespace'"
   * @generated
   */
  String getUrl();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getUrl <em>Url</em>}' attribute.
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
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Urlname()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='urlname' namespace='##targetNamespace'"
   * @generated
   */
  String getUrlname();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getUrlname <em>Urlname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Urlname</em>' attribute.
   * @see #getUrlname()
   * @generated
   */
  void setUrlname(String value);

  /**
   * Returns the value of the '<em><b>Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number</em>' attribute.
   * @see #setNumber(BigInteger)
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Number()
   * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
   *        extendedMetaData="kind='element' name='number' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getNumber();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.TrkType#getNumber <em>Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number</em>' attribute.
   * @see #getNumber()
   * @generated
   */
  void setNumber(BigInteger value);

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':7' processing='strict'"
   * @generated
   */
  FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Trkseg</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx10.model.TrksegType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trkseg</em>' containment reference list.
   * @see goedegep.gpx10.model.GPX10Package#getTrkType_Trkseg()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trkseg' namespace='##targetNamespace'"
   * @generated
   */
  EList<TrksegType> getTrkseg();

} // TrkType
