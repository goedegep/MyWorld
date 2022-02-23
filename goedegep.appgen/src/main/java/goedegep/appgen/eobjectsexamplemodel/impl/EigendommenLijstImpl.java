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

import goedegep.appgen.eobjectsexamplemodel.Eigendom;
import goedegep.appgen.eobjectsexamplemodel.EigendommenLijst;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eigendommen Lijst</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendommenLijstImpl#getEigendommen <em>Eigendommen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EigendommenLijstImpl extends EObjectImpl implements EigendommenLijst {
  /**
   * The cached value of the '{@link #getEigendommen() <em>Eigendommen</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEigendommen()
   * @generated
   * @ordered
   */
  protected EList<Eigendom> eigendommen;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EigendommenLijstImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.EIGENDOMMEN_LIJST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Eigendom> getEigendommen() {
    if (eigendommen == null) {
      eigendommen = new EObjectContainmentEList<Eigendom>(Eigendom.class, this, NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN);
    }
    return eigendommen;
  }
  
//  public void moveEigendom(int index, boolean up) {
//    Eigendom nota = eigendommen.get(index);
//    System.out.println("EigendommenImpl:moveEigendom: index = " + index);
//    
//    if (up) {
//      if (index == 0) {
//        throw new IllegalArgumentException("Het eerste eigendom kan niet omhoog verplaatst worden");
//      }
//    } else {
//      if (index == eigendommen.size() - 1) {
//        throw new IllegalArgumentException("Het laatste eigendom kan niet omlaag verplaatst worden");
//      }
//    }
//
//    eigendommen.remove(index);
//    if (up) {
//      index--;
//      eigendommen.add(index, nota);
//    } else {
//      index++;
//      if (index > eigendommen.size() - 1) {
//        eigendommen.add(nota);
//      } else {
//        eigendommen.add(index, nota);
//      }
//    }
//  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN:
        return ((InternalEList<?>)getEigendommen()).basicRemove(otherEnd, msgs);
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
      case NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN:
        return getEigendommen();
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
      case NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN:
        getEigendommen().clear();
        getEigendommen().addAll((Collection<? extends Eigendom>)newValue);
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
      case NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN:
        getEigendommen().clear();
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
      case NotaPackage.EIGENDOMMEN_LIJST__EIGENDOMMEN:
        return eigendommen != null && !eigendommen.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //EigendommenLijstImpl
