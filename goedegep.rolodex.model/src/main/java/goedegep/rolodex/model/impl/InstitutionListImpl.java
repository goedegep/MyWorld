/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.InstitutionList;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Institution List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionListImpl#getInstitutions <em>Institutions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InstitutionListImpl extends MinimalEObjectImpl.Container implements InstitutionList {
  /**
   * The cached value of the '{@link #getInstitutions() <em>Institutions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstitutions()
   * @generated
   * @ordered
   */
  protected EList<Institution> institutions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InstitutionListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.INSTITUTION_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Institution> getInstitutions() {
    if (institutions == null) {
      institutions = new EObjectContainmentEList<Institution>(Institution.class, this,
          RolodexPackage.INSTITUTION_LIST__INSTITUTIONS);
    }
    return institutions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.INSTITUTION_LIST__INSTITUTIONS:
      return ((InternalEList<?>) getInstitutions()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.INSTITUTION_LIST__INSTITUTIONS:
      return getInstitutions();
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
    case RolodexPackage.INSTITUTION_LIST__INSTITUTIONS:
      getInstitutions().clear();
      getInstitutions().addAll((Collection<? extends Institution>) newValue);
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
    case RolodexPackage.INSTITUTION_LIST__INSTITUTIONS:
      getInstitutions().clear();
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
    case RolodexPackage.INSTITUTION_LIST__INSTITUTIONS:
      return institutions != null && !institutions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  @Override
  public Institution getInstitution(String name) {
    for (Institution institution : institutions) {
      if (institution.getName().equals(name)) {
        return institution;
      }
    }

    return null;
  }

} //InstitutionListImpl
