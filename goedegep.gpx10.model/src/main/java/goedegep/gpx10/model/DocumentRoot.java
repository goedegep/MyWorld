/**
 */
package goedegep.gpx10.model;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx10.model.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link goedegep.gpx10.model.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link goedegep.gpx10.model.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link goedegep.gpx10.model.DocumentRoot#getGpx <em>Gpx</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx10.model.GPX10Package#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
  /**
   * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mixed</em>' attribute list.
   * @see goedegep.gpx10.model.GPX10Package#getDocumentRoot_Mixed()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' name=':mixed'"
   * @generated
   */
  FeatureMap getMixed();

  /**
   * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>XMLNS Prefix Map</em>' map.
   * @see goedegep.gpx10.model.GPX10Package#getDocumentRoot_XMLNSPrefixMap()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
   *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
   * @generated
   */
  EMap<String, String> getXMLNSPrefixMap();

  /**
   * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>XSI Schema Location</em>' map.
   * @see goedegep.gpx10.model.GPX10Package#getDocumentRoot_XSISchemaLocation()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
   *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
   * @generated
   */
  EMap<String, String> getXSISchemaLocation();

  /**
   * Returns the value of the '<em><b>Gpx</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Gpx</em>' containment reference.
   * @see #setGpx(GpxType)
   * @see goedegep.gpx10.model.GPX10Package#getDocumentRoot_Gpx()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='gpx' namespace='##targetNamespace'"
   * @generated
   */
  GpxType getGpx();

  /**
   * Sets the value of the '{@link goedegep.gpx10.model.DocumentRoot#getGpx <em>Gpx</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Gpx</em>' containment reference.
   * @see #getGpx()
   * @generated
   */
  void setGpx(GpxType value);

} // DocumentRoot
