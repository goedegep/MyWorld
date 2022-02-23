/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.appgen.eobjectsexamplemodel.Nota;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;
import goedegep.appgen.eobjectsexamplemodel.Notas;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Notas</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.NotasImpl#getNotas <em>Notas</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotasImpl extends EObjectImpl implements Notas {
  private static final String         NEWLINE = System.getProperty("line.separator");

  /**
   * The cached value of the '{@link #getNotas() <em>Notas</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNotas()
   * @generated
   * @ordered
   */
  protected EList<Nota> notas;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NotasImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.NOTAS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Nota> getNotas() {
    if (notas == null) {
      notas = new EObjectContainmentEList<Nota>(Nota.class, this, NotaPackage.NOTAS__NOTAS);
    }
    return notas;
  }
  
  public void moveNota(int index, boolean up) {
    Nota nota = notas.get(index);
    System.out.println("NotasImpl:moveNota: index = " + index);
    
    if (up) {
      if (index == 0) {
        throw new IllegalArgumentException("De eerste nota kan niet omhoog verplaatst worden");
      }
    } else {
      if (index == notas.size() - 1) {
        throw new IllegalArgumentException("De laatste nota kan niet omlaag verplaatst worden");
      }
    }

    notas.remove(index);
    if (up) {
      index--;
      notas.add(index, nota);
    } else {
      index++;
      if (index > notas.size() - 1) {
        notas.add(nota);
      } else {
        notas.add(index, nota);
      }
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.NOTAS__NOTAS:
        return ((InternalEList<?>)getNotas()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case NotaPackage.NOTAS__NOTAS:
        return getNotas();
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
      case NotaPackage.NOTAS__NOTAS:
        getNotas().clear();
        getNotas().addAll((Collection<? extends Nota>)newValue);
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
      case NotaPackage.NOTAS__NOTAS:
        getNotas().clear();
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
      case NotaPackage.NOTAS__NOTAS:
        return notas != null && !notas.isEmpty();
    }
    return super.eIsSet(featureID);
  }
  
  /**
   *  @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    for (Nota nota: getNotas()) {
      buf.append(nota.toString());
      buf.append(NEWLINE);
    }
    
    return buf.toString();
  }

} //NotasImpl
