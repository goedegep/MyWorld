/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import goedegep.appgen.eobjectsexamplemodel.Eigendom;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;
import goedegep.appgen.eobjectsexamplemodel.Uitgave;
import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Uitgave</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.UitgaveImpl#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.UitgaveImpl#getBedrag <em>Bedrag</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.UitgaveImpl#getOpmerkingen <em>Opmerkingen</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.UitgaveImpl#getAankoop <em>Aankoop</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UitgaveImpl extends EObjectImpl implements Uitgave {
  /**
   * The default value of the '{@link #getOmschrijving() <em>Omschrijving</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOmschrijving()
   * @generated
   * @ordered
   */
  protected static final String OMSCHRIJVING_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOmschrijving() <em>Omschrijving</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOmschrijving()
   * @generated
   * @ordered
   */
  protected String omschrijving = OMSCHRIJVING_EDEFAULT;

  /**
   * This is true if the Omschrijving attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean omschrijvingESet;

  /**
   * The default value of the '{@link #getBedrag() <em>Bedrag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBedrag()
   * @generated
   * @ordered
   */
  protected static final PgCurrency BEDRAG_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBedrag() <em>Bedrag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBedrag()
   * @generated
   * @ordered
   */
  protected PgCurrency bedrag = BEDRAG_EDEFAULT;

  /**
   * This is true if the Bedrag attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean bedragESet;

  /**
   * The default value of the '{@link #getOpmerkingen() <em>Opmerkingen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOpmerkingen()
   * @generated
   * @ordered
   */
  protected static final String OPMERKINGEN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOpmerkingen() <em>Opmerkingen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOpmerkingen()
   * @generated
   * @ordered
   */
  protected String opmerkingen = OPMERKINGEN_EDEFAULT;

  /**
   * This is true if the Opmerkingen attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean opmerkingenESet;

  /**
   * The cached value of the '{@link #getAankoop() <em>Aankoop</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAankoop()
   * @generated
   * @ordered
   */
  protected Eigendom aankoop;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UitgaveImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.UITGAVE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getOmschrijving() {
    return omschrijving;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOmschrijving(String newOmschrijving) {
    String oldOmschrijving = omschrijving;
    omschrijving = newOmschrijving;
    boolean oldOmschrijvingESet = omschrijvingESet;
    omschrijvingESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.UITGAVE__OMSCHRIJVING, oldOmschrijving, omschrijving, !oldOmschrijvingESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetOmschrijving() {
    String oldOmschrijving = omschrijving;
    boolean oldOmschrijvingESet = omschrijvingESet;
    omschrijving = OMSCHRIJVING_EDEFAULT;
    omschrijvingESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.UITGAVE__OMSCHRIJVING, oldOmschrijving, OMSCHRIJVING_EDEFAULT, oldOmschrijvingESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetOmschrijving() {
    return omschrijvingESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PgCurrency getBedrag() {
    return bedrag;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBedrag(PgCurrency newBedrag) {
    PgCurrency oldBedrag = bedrag;
    bedrag = newBedrag;
    boolean oldBedragESet = bedragESet;
    bedragESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.UITGAVE__BEDRAG, oldBedrag, bedrag, !oldBedragESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetBedrag() {
    PgCurrency oldBedrag = bedrag;
    boolean oldBedragESet = bedragESet;
    bedrag = BEDRAG_EDEFAULT;
    bedragESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.UITGAVE__BEDRAG, oldBedrag, BEDRAG_EDEFAULT, oldBedragESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetBedrag() {
    return bedragESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getOpmerkingen() {
    return opmerkingen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOpmerkingen(String newOpmerkingen) {
    String oldOpmerkingen = opmerkingen;
    opmerkingen = newOpmerkingen;
    boolean oldOpmerkingenESet = opmerkingenESet;
    opmerkingenESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.UITGAVE__OPMERKINGEN, oldOpmerkingen, opmerkingen, !oldOpmerkingenESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetOpmerkingen() {
    String oldOpmerkingen = opmerkingen;
    boolean oldOpmerkingenESet = opmerkingenESet;
    opmerkingen = OPMERKINGEN_EDEFAULT;
    opmerkingenESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.UITGAVE__OPMERKINGEN, oldOpmerkingen, OPMERKINGEN_EDEFAULT, oldOpmerkingenESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetOpmerkingen() {
    return opmerkingenESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Eigendom getAankoop() {
    if (aankoop != null && aankoop.eIsProxy()) {
      InternalEObject oldAankoop = (InternalEObject)aankoop;
      aankoop = (Eigendom)eResolveProxy(oldAankoop);
      if (aankoop != oldAankoop) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, NotaPackage.UITGAVE__AANKOOP, oldAankoop, aankoop));
      }
    }
    return aankoop;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Eigendom basicGetAankoop() {
    return aankoop;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAankoop(Eigendom newAankoop, NotificationChain msgs) {
    Eigendom oldAankoop = aankoop;
    aankoop = newAankoop;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NotaPackage.UITGAVE__AANKOOP, oldAankoop, newAankoop);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAankoop(Eigendom newAankoop) {
    if (newAankoop != aankoop) {
      NotificationChain msgs = null;
      if (aankoop != null)
        msgs = ((InternalEObject)aankoop).eInverseRemove(this, NotaPackage.EIGENDOM__UITGAVE, Eigendom.class, msgs);
      if (newAankoop != null)
        msgs = ((InternalEObject)newAankoop).eInverseAdd(this, NotaPackage.EIGENDOM__UITGAVE, Eigendom.class, msgs);
      msgs = basicSetAankoop(newAankoop, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.UITGAVE__AANKOOP, newAankoop, newAankoop));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.UITGAVE__AANKOOP:
        if (aankoop != null)
          msgs = ((InternalEObject)aankoop).eInverseRemove(this, NotaPackage.EIGENDOM__UITGAVE, Eigendom.class, msgs);
        return basicSetAankoop((Eigendom)otherEnd, msgs);
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
      case NotaPackage.UITGAVE__AANKOOP:
        return basicSetAankoop(null, msgs);
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
      case NotaPackage.UITGAVE__OMSCHRIJVING:
        return getOmschrijving();
      case NotaPackage.UITGAVE__BEDRAG:
        return getBedrag();
      case NotaPackage.UITGAVE__OPMERKINGEN:
        return getOpmerkingen();
      case NotaPackage.UITGAVE__AANKOOP:
        if (resolve) return getAankoop();
        return basicGetAankoop();
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
      case NotaPackage.UITGAVE__OMSCHRIJVING:
        setOmschrijving((String)newValue);
        return;
      case NotaPackage.UITGAVE__BEDRAG:
        setBedrag((PgCurrency)newValue);
        return;
      case NotaPackage.UITGAVE__OPMERKINGEN:
        setOpmerkingen((String)newValue);
        return;
      case NotaPackage.UITGAVE__AANKOOP:
        setAankoop((Eigendom)newValue);
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
      case NotaPackage.UITGAVE__OMSCHRIJVING:
        unsetOmschrijving();
        return;
      case NotaPackage.UITGAVE__BEDRAG:
        unsetBedrag();
        return;
      case NotaPackage.UITGAVE__OPMERKINGEN:
        unsetOpmerkingen();
        return;
      case NotaPackage.UITGAVE__AANKOOP:
        setAankoop((Eigendom)null);
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
      case NotaPackage.UITGAVE__OMSCHRIJVING:
        return isSetOmschrijving();
      case NotaPackage.UITGAVE__BEDRAG:
        return isSetBedrag();
      case NotaPackage.UITGAVE__OPMERKINGEN:
        return isSetOpmerkingen();
      case NotaPackage.UITGAVE__AANKOOP:
        return aankoop != null;
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
    result.append(" (omschrijving: ");
    if (omschrijvingESet) result.append(omschrijving); else result.append("<unset>");
    result.append(", bedrag: ");
    if (bedragESet) result.append(bedrag); else result.append("<unset>");
    result.append(", opmerkingen: ");
    if (opmerkingenESet) result.append(opmerkingen); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //UitgaveImpl
