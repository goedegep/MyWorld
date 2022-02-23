/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.appgen.eobjectsexamplemodel.Nota;
import goedegep.appgen.eobjectsexamplemodel.NotaItem;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.NotaItemImpl#getAantal <em>Aantal</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.NotaItemImpl#getNota <em>Nota</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotaItemImpl extends UitgaveImpl implements NotaItem {
  /**
   * The default value of the '{@link #getAantal() <em>Aantal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAantal()
   * @generated
   * @ordered
   */
  protected static final int AANTAL_EDEFAULT = 1;

  /**
   * The cached value of the '{@link #getAantal() <em>Aantal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAantal()
   * @generated
   * @ordered
   */
  protected int aantal = AANTAL_EDEFAULT;

  /**
   * This is true if the Aantal attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean aantalESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NotaItemImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.NOTA_ITEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getAantal() {
    return aantal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAantal(int newAantal) {
    int oldAantal = aantal;
    aantal = newAantal;
    boolean oldAantalESet = aantalESet;
    aantalESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.NOTA_ITEM__AANTAL, oldAantal, aantal, !oldAantalESet));
  }

/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetAantal() {
    int oldAantal = aantal;
    boolean oldAantalESet = aantalESet;
    aantal = AANTAL_EDEFAULT;
    aantalESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.NOTA_ITEM__AANTAL, oldAantal, AANTAL_EDEFAULT, oldAantalESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetAantal() {
    return aantalESet;
  }

  //  public String getOmschrijving(boolean derive) {
//    if (isSetOmschrijving()) {
//      return getOmschrijving();
//    }
//    
//    if (isSetEigendom()) {
//      Eigendom eigendom = getEigendom();
//      StringBuilder buf = new StringBuilder();
//      boolean spaceNeeded = false;
//      if (eigendom.isSetOmschrijving()) {
//        buf.append(eigendom.getOmschrijving());
//        spaceNeeded = true;
//      }
//      if (eigendom.isSetMerk()) {
//        if (spaceNeeded) {
//          buf.append(" ");
//        }
//        buf.append(eigendom.getMerk());
//        spaceNeeded = true;
//      }
//      if (eigendom.isSetType()) {
//        if (spaceNeeded) {
//          buf.append(" ");
//        }
//        buf.append(eigendom.getType());
//      }
//      return buf.toString();
//    }
//    
//    return null;
//  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Nota getNota() {
    if (eContainerFeatureID() != NotaPackage.NOTA_ITEM__NOTA) return null;
    return (Nota)eInternalContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetNota(Nota newNota, NotificationChain msgs) {
    msgs = eBasicSetContainer((InternalEObject)newNota, NotaPackage.NOTA_ITEM__NOTA, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNota(Nota newNota) {
    if (newNota != eInternalContainer() || (eContainerFeatureID() != NotaPackage.NOTA_ITEM__NOTA && newNota != null)) {
      if (EcoreUtil.isAncestor(this, newNota))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newNota != null)
        msgs = ((InternalEObject)newNota).eInverseAdd(this, NotaPackage.NOTA__NOTA_ITEMS, Nota.class, msgs);
      msgs = basicSetNota(newNota, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.NOTA_ITEM__NOTA, newNota, newNota));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.NOTA_ITEM__NOTA:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetNota((Nota)otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.NOTA_ITEM__NOTA:
        return basicSetNota(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
    switch (eContainerFeatureID()) {
      case NotaPackage.NOTA_ITEM__NOTA:
        return eInternalContainer().eInverseRemove(this, NotaPackage.NOTA__NOTA_ITEMS, Nota.class, msgs);
    }
    return super.eBasicRemoveFromContainerFeature(msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case NotaPackage.NOTA_ITEM__AANTAL:
        return getAantal();
      case NotaPackage.NOTA_ITEM__NOTA:
        return getNota();
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
      case NotaPackage.NOTA_ITEM__AANTAL:
        setAantal((Integer)newValue);
        return;
      case NotaPackage.NOTA_ITEM__NOTA:
        setNota((Nota)newValue);
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
      case NotaPackage.NOTA_ITEM__AANTAL:
        unsetAantal();
        return;
      case NotaPackage.NOTA_ITEM__NOTA:
        setNota((Nota)null);
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
      case NotaPackage.NOTA_ITEM__AANTAL:
        return isSetAantal();
      case NotaPackage.NOTA_ITEM__NOTA:
        return getNota() != null;
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

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (aantal: ");
    if (aantalESet) result.append(aantal); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //NotaItemImpl
