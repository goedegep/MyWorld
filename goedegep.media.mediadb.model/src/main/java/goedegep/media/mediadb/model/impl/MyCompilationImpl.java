/**
 */
package goedegep.media.mediadb.model.impl;

import org.eclipse.emf.ecore.EClass;

import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyCompilation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Compilation</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class MyCompilationImpl extends AlbumImpl implements MyCompilation {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MyCompilationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MY_COMPILATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("My Compilation").append(NEWLINE);
    buf.append(super.toString());
    return buf.toString();
  }

} //MyCompilationImpl
