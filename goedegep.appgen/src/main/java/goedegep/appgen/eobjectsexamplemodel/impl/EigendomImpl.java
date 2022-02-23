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
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eigendom</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getMerk <em>Merk</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getSerieNummer <em>Serie Nummer</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getOpmerkingen <em>Opmerkingen</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getVanDatum <em>Van Datum</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getTotDatum <em>Tot Datum</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#isArchief <em>Archief</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getAfbeeldingen <em>Afbeeldingen</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getDocumenten <em>Documenten</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.EigendomImpl#getUitgave <em>Uitgave</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EigendomImpl extends EObjectImpl implements Eigendom {
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
   * The default value of the '{@link #getMerk() <em>Merk</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMerk()
   * @generated
   * @ordered
   */
  protected static final String MERK_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMerk() <em>Merk</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMerk()
   * @generated
   * @ordered
   */
  protected String merk = MERK_EDEFAULT;

  /**
   * This is true if the Merk attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean merkESet;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * This is true if the Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean typeESet;

  /**
   * The default value of the '{@link #getSerieNummer() <em>Serie Nummer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSerieNummer()
   * @generated
   * @ordered
   */
  protected static final String SERIE_NUMMER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSerieNummer() <em>Serie Nummer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSerieNummer()
   * @generated
   * @ordered
   */
  protected String serieNummer = SERIE_NUMMER_EDEFAULT;

  /**
   * This is true if the Serie Nummer attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean serieNummerESet;

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
   * The default value of the '{@link #getVanDatum() <em>Van Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVanDatum()
   * @generated
   * @ordered
   */
  protected static final FlexDate VAN_DATUM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getVanDatum() <em>Van Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVanDatum()
   * @generated
   * @ordered
   */
  protected FlexDate vanDatum = VAN_DATUM_EDEFAULT;

  /**
   * This is true if the Van Datum attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean vanDatumESet;

  /**
   * The default value of the '{@link #getTotDatum() <em>Tot Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTotDatum()
   * @generated
   * @ordered
   */
  protected static final FlexDate TOT_DATUM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTotDatum() <em>Tot Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTotDatum()
   * @generated
   * @ordered
   */
  protected FlexDate totDatum = TOT_DATUM_EDEFAULT;

  /**
   * This is true if the Tot Datum attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean totDatumESet;

  /**
   * The default value of the '{@link #isArchief() <em>Archief</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchief()
   * @generated
   * @ordered
   */
  protected static final boolean ARCHIEF_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isArchief() <em>Archief</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchief()
   * @generated
   * @ordered
   */
  protected boolean archief = ARCHIEF_EDEFAULT;

  /**
   * The cached value of the '{@link #getAfbeeldingen() <em>Afbeeldingen</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAfbeeldingen()
   * @generated
   * @ordered
   */
  protected EList<FileReference> afbeeldingen;

  /**
   * The cached value of the '{@link #getDocumenten() <em>Documenten</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocumenten()
   * @generated
   * @ordered
   */
  protected EList<FileReference> documenten;

  /**
   * The cached value of the '{@link #getUitgave() <em>Uitgave</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUitgave()
   * @generated
   * @ordered
   */
  protected Uitgave uitgave;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EigendomImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.EIGENDOM;
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
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__OMSCHRIJVING, oldOmschrijving, omschrijving, !oldOmschrijvingESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__OMSCHRIJVING, oldOmschrijving, OMSCHRIJVING_EDEFAULT, oldOmschrijvingESet));
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
  public String getMerk() {
    return merk;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMerk(String newMerk) {
    String oldMerk = merk;
    merk = newMerk;
    boolean oldMerkESet = merkESet;
    merkESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__MERK, oldMerk, merk, !oldMerkESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetMerk() {
    String oldMerk = merk;
    boolean oldMerkESet = merkESet;
    merk = MERK_EDEFAULT;
    merkESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__MERK, oldMerk, MERK_EDEFAULT, oldMerkESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetMerk() {
    return merkESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    boolean oldTypeESet = typeESet;
    typeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__TYPE, oldType, type, !oldTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetType() {
    String oldType = type;
    boolean oldTypeESet = typeESet;
    type = TYPE_EDEFAULT;
    typeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetType() {
    return typeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSerieNummer() {
    return serieNummer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSerieNummer(String newSerieNummer) {
    String oldSerieNummer = serieNummer;
    serieNummer = newSerieNummer;
    boolean oldSerieNummerESet = serieNummerESet;
    serieNummerESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__SERIE_NUMMER, oldSerieNummer, serieNummer, !oldSerieNummerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSerieNummer() {
    String oldSerieNummer = serieNummer;
    boolean oldSerieNummerESet = serieNummerESet;
    serieNummer = SERIE_NUMMER_EDEFAULT;
    serieNummerESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__SERIE_NUMMER, oldSerieNummer, SERIE_NUMMER_EDEFAULT, oldSerieNummerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSerieNummer() {
    return serieNummerESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__OPMERKINGEN, oldOpmerkingen, opmerkingen, !oldOpmerkingenESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__OPMERKINGEN, oldOpmerkingen, OPMERKINGEN_EDEFAULT, oldOpmerkingenESet));
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
  public FlexDate getVanDatum() {
    return vanDatum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVanDatum(FlexDate newVanDatum) {
    FlexDate oldVanDatum = vanDatum;
    vanDatum = newVanDatum;
    boolean oldVanDatumESet = vanDatumESet;
    vanDatumESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__VAN_DATUM, oldVanDatum, vanDatum, !oldVanDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetVanDatum() {
    FlexDate oldVanDatum = vanDatum;
    boolean oldVanDatumESet = vanDatumESet;
    vanDatum = VAN_DATUM_EDEFAULT;
    vanDatumESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__VAN_DATUM, oldVanDatum, VAN_DATUM_EDEFAULT, oldVanDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetVanDatum() {
    return vanDatumESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlexDate getTotDatum() {
    return totDatum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTotDatum(FlexDate newTotDatum) {
    FlexDate oldTotDatum = totDatum;
    totDatum = newTotDatum;
    boolean oldTotDatumESet = totDatumESet;
    totDatumESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__TOT_DATUM, oldTotDatum, totDatum, !oldTotDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetTotDatum() {
    FlexDate oldTotDatum = totDatum;
    boolean oldTotDatumESet = totDatumESet;
    totDatum = TOT_DATUM_EDEFAULT;
    totDatumESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.EIGENDOM__TOT_DATUM, oldTotDatum, TOT_DATUM_EDEFAULT, oldTotDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetTotDatum() {
    return totDatumESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isArchief() {
    return archief;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setArchief(boolean newArchief) {
    boolean oldArchief = archief;
    archief = newArchief;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__ARCHIEF, oldArchief, archief));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getAfbeeldingen() {
    if (afbeeldingen == null) {
      afbeeldingen = new EObjectContainmentEList<FileReference>(FileReference.class, this, NotaPackage.EIGENDOM__AFBEELDINGEN);
    }
    return afbeeldingen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getDocumenten() {
    if (documenten == null) {
      documenten = new EObjectContainmentEList<FileReference>(FileReference.class, this, NotaPackage.EIGENDOM__DOCUMENTEN);
    }
    return documenten;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Uitgave getUitgave() {
    if (uitgave != null && uitgave.eIsProxy()) {
      InternalEObject oldUitgave = (InternalEObject)uitgave;
      uitgave = (Uitgave)eResolveProxy(oldUitgave);
      if (uitgave != oldUitgave) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, NotaPackage.EIGENDOM__UITGAVE, oldUitgave, uitgave));
      }
    }
    return uitgave;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Uitgave basicGetUitgave() {
    return uitgave;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetUitgave(Uitgave newUitgave, NotificationChain msgs) {
    Uitgave oldUitgave = uitgave;
    uitgave = newUitgave;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__UITGAVE, oldUitgave, newUitgave);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUitgave(Uitgave newUitgave) {
    if (newUitgave != uitgave) {
      NotificationChain msgs = null;
      if (uitgave != null)
        msgs = ((InternalEObject)uitgave).eInverseRemove(this, NotaPackage.UITGAVE__AANKOOP, Uitgave.class, msgs);
      if (newUitgave != null)
        msgs = ((InternalEObject)newUitgave).eInverseAdd(this, NotaPackage.UITGAVE__AANKOOP, Uitgave.class, msgs);
      msgs = basicSetUitgave(newUitgave, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.EIGENDOM__UITGAVE, newUitgave, newUitgave));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.EIGENDOM__UITGAVE:
        if (uitgave != null)
          msgs = ((InternalEObject)uitgave).eInverseRemove(this, NotaPackage.UITGAVE__AANKOOP, Uitgave.class, msgs);
        return basicSetUitgave((Uitgave)otherEnd, msgs);
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
      case NotaPackage.EIGENDOM__AFBEELDINGEN:
        return ((InternalEList<?>)getAfbeeldingen()).basicRemove(otherEnd, msgs);
      case NotaPackage.EIGENDOM__DOCUMENTEN:
        return ((InternalEList<?>)getDocumenten()).basicRemove(otherEnd, msgs);
      case NotaPackage.EIGENDOM__UITGAVE:
        return basicSetUitgave(null, msgs);
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
      case NotaPackage.EIGENDOM__OMSCHRIJVING:
        return getOmschrijving();
      case NotaPackage.EIGENDOM__MERK:
        return getMerk();
      case NotaPackage.EIGENDOM__TYPE:
        return getType();
      case NotaPackage.EIGENDOM__SERIE_NUMMER:
        return getSerieNummer();
      case NotaPackage.EIGENDOM__OPMERKINGEN:
        return getOpmerkingen();
      case NotaPackage.EIGENDOM__VAN_DATUM:
        return getVanDatum();
      case NotaPackage.EIGENDOM__TOT_DATUM:
        return getTotDatum();
      case NotaPackage.EIGENDOM__ARCHIEF:
        return isArchief();
      case NotaPackage.EIGENDOM__AFBEELDINGEN:
        return getAfbeeldingen();
      case NotaPackage.EIGENDOM__DOCUMENTEN:
        return getDocumenten();
      case NotaPackage.EIGENDOM__UITGAVE:
        if (resolve) return getUitgave();
        return basicGetUitgave();
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
      case NotaPackage.EIGENDOM__OMSCHRIJVING:
        setOmschrijving((String)newValue);
        return;
      case NotaPackage.EIGENDOM__MERK:
        setMerk((String)newValue);
        return;
      case NotaPackage.EIGENDOM__TYPE:
        setType((String)newValue);
        return;
      case NotaPackage.EIGENDOM__SERIE_NUMMER:
        setSerieNummer((String)newValue);
        return;
      case NotaPackage.EIGENDOM__OPMERKINGEN:
        setOpmerkingen((String)newValue);
        return;
      case NotaPackage.EIGENDOM__VAN_DATUM:
        setVanDatum((FlexDate)newValue);
        return;
      case NotaPackage.EIGENDOM__TOT_DATUM:
        setTotDatum((FlexDate)newValue);
        return;
      case NotaPackage.EIGENDOM__ARCHIEF:
        setArchief((Boolean)newValue);
        return;
      case NotaPackage.EIGENDOM__AFBEELDINGEN:
        getAfbeeldingen().clear();
        getAfbeeldingen().addAll((Collection<? extends FileReference>)newValue);
        return;
      case NotaPackage.EIGENDOM__DOCUMENTEN:
        getDocumenten().clear();
        getDocumenten().addAll((Collection<? extends FileReference>)newValue);
        return;
      case NotaPackage.EIGENDOM__UITGAVE:
        setUitgave((Uitgave)newValue);
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
      case NotaPackage.EIGENDOM__OMSCHRIJVING:
        unsetOmschrijving();
        return;
      case NotaPackage.EIGENDOM__MERK:
        unsetMerk();
        return;
      case NotaPackage.EIGENDOM__TYPE:
        unsetType();
        return;
      case NotaPackage.EIGENDOM__SERIE_NUMMER:
        unsetSerieNummer();
        return;
      case NotaPackage.EIGENDOM__OPMERKINGEN:
        unsetOpmerkingen();
        return;
      case NotaPackage.EIGENDOM__VAN_DATUM:
        unsetVanDatum();
        return;
      case NotaPackage.EIGENDOM__TOT_DATUM:
        unsetTotDatum();
        return;
      case NotaPackage.EIGENDOM__ARCHIEF:
        setArchief(ARCHIEF_EDEFAULT);
        return;
      case NotaPackage.EIGENDOM__AFBEELDINGEN:
        getAfbeeldingen().clear();
        return;
      case NotaPackage.EIGENDOM__DOCUMENTEN:
        getDocumenten().clear();
        return;
      case NotaPackage.EIGENDOM__UITGAVE:
        setUitgave((Uitgave)null);
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
      case NotaPackage.EIGENDOM__OMSCHRIJVING:
        return isSetOmschrijving();
      case NotaPackage.EIGENDOM__MERK:
        return isSetMerk();
      case NotaPackage.EIGENDOM__TYPE:
        return isSetType();
      case NotaPackage.EIGENDOM__SERIE_NUMMER:
        return isSetSerieNummer();
      case NotaPackage.EIGENDOM__OPMERKINGEN:
        return isSetOpmerkingen();
      case NotaPackage.EIGENDOM__VAN_DATUM:
        return isSetVanDatum();
      case NotaPackage.EIGENDOM__TOT_DATUM:
        return isSetTotDatum();
      case NotaPackage.EIGENDOM__ARCHIEF:
        return archief != ARCHIEF_EDEFAULT;
      case NotaPackage.EIGENDOM__AFBEELDINGEN:
        return afbeeldingen != null && !afbeeldingen.isEmpty();
      case NotaPackage.EIGENDOM__DOCUMENTEN:
        return documenten != null && !documenten.isEmpty();
      case NotaPackage.EIGENDOM__UITGAVE:
        return uitgave != null;
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
    result.append(", merk: ");
    if (merkESet) result.append(merk); else result.append("<unset>");
    result.append(", type: ");
    if (typeESet) result.append(type); else result.append("<unset>");
    result.append(", serieNummer: ");
    if (serieNummerESet) result.append(serieNummer); else result.append("<unset>");
    result.append(", opmerkingen: ");
    if (opmerkingenESet) result.append(opmerkingen); else result.append("<unset>");
    result.append(", vanDatum: ");
    if (vanDatumESet) result.append(vanDatum); else result.append("<unset>");
    result.append(", totDatum: ");
    if (totDatumESet) result.append(totDatum); else result.append("<unset>");
    result.append(", archief: ");
    result.append(archief);
    result.append(')');
    return result.toString();
  }

} //EigendomImpl
