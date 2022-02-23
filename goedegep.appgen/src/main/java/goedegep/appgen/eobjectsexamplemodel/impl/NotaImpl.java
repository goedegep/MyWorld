/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel.impl;

import goedegep.appgen.eobjectsexamplemodel.Nota;
import goedegep.appgen.eobjectsexamplemodel.NotaItem;
import goedegep.appgen.eobjectsexamplemodel.NotaPackage;
import goedegep.util.datetime.FlexDate;
import goedegep.util.money.PgCurrency;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nota</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.NotaImpl#getDatum <em>Datum</em>}</li>
 *   <li>{@link goedegep.appgen.eobjectsexamplemodel.impl.NotaImpl#getBedrijf <em>Bedrijf</em>}</li>
 *   <li>{@link ggoedegep.app.gen.eobjectsexamplemodel.impl.NotaImpl#getNotaItems <em>Nota Items</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotaImpl extends UitgaveImpl implements Nota {
  /**
   * The default value of the '{@link #getDatum() <em>Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDatum()
   * @generated
   * @ordered
   */
  protected static final FlexDate DATUM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDatum() <em>Datum</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDatum()
   * @generated
   * @ordered
   */
  protected FlexDate datum = DATUM_EDEFAULT;

  /**
   * This is true if the Datum attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean datumESet;

  /**
   * The default value of the '{@link #getBedrijf() <em>Bedrijf</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBedrijf()
   * @generated
   * @ordered
   */
  protected static final String BEDRIJF_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBedrijf() <em>Bedrijf</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBedrijf()
   * @generated
   * @ordered
   */
  protected String bedrijf = BEDRIJF_EDEFAULT;

  /**
   * This is true if the Bedrijf attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean bedrijfESet;

  /**
   * The cached value of the '{@link #getNotaItems() <em>Nota Items</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNotaItems()
   * @generated
   * @ordered
   */
  protected EList<NotaItem> notaItems;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NotaImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return NotaPackage.Literals.NOTA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlexDate getDatum() {
    return datum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDatum(FlexDate newDatum) {
    FlexDate oldDatum = datum;
    datum = newDatum;
    boolean oldDatumESet = datumESet;
    datumESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.NOTA__DATUM, oldDatum, datum, !oldDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDatum() {
    FlexDate oldDatum = datum;
    boolean oldDatumESet = datumESet;
    datum = DATUM_EDEFAULT;
    datumESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.NOTA__DATUM, oldDatum, DATUM_EDEFAULT, oldDatumESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDatum() {
    return datumESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getBedrijf() {
    return bedrijf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBedrijf(String newBedrijf) {
    String oldBedrijf = bedrijf;
    bedrijf = newBedrijf;
    boolean oldBedrijfESet = bedrijfESet;
    bedrijfESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NotaPackage.NOTA__BEDRIJF, oldBedrijf, bedrijf, !oldBedrijfESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetBedrijf() {
    String oldBedrijf = bedrijf;
    boolean oldBedrijfESet = bedrijfESet;
    bedrijf = BEDRIJF_EDEFAULT;
    bedrijfESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, NotaPackage.NOTA__BEDRIJF, oldBedrijf, BEDRIJF_EDEFAULT, oldBedrijfESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetBedrijf() {
    return bedrijfESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<NotaItem> getNotaItems() {
    if (notaItems == null) {
      notaItems = new EObjectContainmentWithInverseEList<NotaItem>(NotaItem.class, this, NotaPackage.NOTA__NOTA_ITEMS, NotaPackage.NOTA_ITEM__NOTA);
    }
    return notaItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case NotaPackage.NOTA__NOTA_ITEMS:
        return ((InternalEList<InternalEObject>)(InternalEList<?>)getNotaItems()).basicAdd(otherEnd, msgs);
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
      case NotaPackage.NOTA__NOTA_ITEMS:
        return ((InternalEList<?>)getNotaItems()).basicRemove(otherEnd, msgs);
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
      case NotaPackage.NOTA__DATUM:
        return getDatum();
      case NotaPackage.NOTA__BEDRIJF:
        return getBedrijf();
      case NotaPackage.NOTA__NOTA_ITEMS:
        return getNotaItems();
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
      case NotaPackage.NOTA__DATUM:
        setDatum((FlexDate)newValue);
        return;
      case NotaPackage.NOTA__BEDRIJF:
        setBedrijf((String)newValue);
        return;
      case NotaPackage.NOTA__NOTA_ITEMS:
        getNotaItems().clear();
        getNotaItems().addAll((Collection<? extends NotaItem>)newValue);
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
      case NotaPackage.NOTA__DATUM:
        unsetDatum();
        return;
      case NotaPackage.NOTA__BEDRIJF:
        unsetBedrijf();
        return;
      case NotaPackage.NOTA__NOTA_ITEMS:
        getNotaItems().clear();
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
      case NotaPackage.NOTA__DATUM:
        return isSetDatum();
      case NotaPackage.NOTA__BEDRIJF:
        return isSetBedrijf();
      case NotaPackage.NOTA__NOTA_ITEMS:
        return notaItems != null && !notaItems.isEmpty();
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
    result.append(" (datum: ");
    if (datumESet) result.append(datum); else result.append("<unset>");
    result.append(", bedrijf: ");
    if (bedrijfESet) result.append(bedrijf); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }
  
  public PgCurrency getTotaalBedragNotaItems() {
    PgCurrency bedrag = null;
    for (NotaItem notaItem: getNotaItems()) {
      if (bedrag == null) {
        bedrag = notaItem.getBedrag();
      } else {
        if (notaItem.getBedrag() != null) {
          bedrag = bedrag.add(notaItem.getBedrag());
        }
      }
    }
    return bedrag;
  }

//  public void moveNotaItem(int index, boolean up) {
//    EList<NotaItem> notaItems = getNotaItems();
//    NotaItem notaItem = notaItems.get(index);
//    System.out.println("NotaImpl:moveNotaItem: index = " + index);
//    
//    if (up) {
//      if (index == 0) {
//        throw new IllegalArgumentException("Het eerste nota item kan niet omhoog verplaatst worden");
//      }
//    } else {
//      if (index == notaItems.size() - 1) {
//        throw new IllegalArgumentException("Het laatste nota item kan niet omlaag verplaatst worden");
//      }
//    }
//
//    notaItems.remove(index);
//    if (up) {
//      index--;
//      notaItems.add(index, notaItem);
//    } else {
//      index++;
//      if (index > notaItems.size() - 1) {
//        notaItems.add(notaItem);
//      } else {
//        notaItems.add(index, notaItem);
//      }
//    }
//  }

  public String getDisplayOmschrijving() {
    if (isSetOmschrijving()) {
      return omschrijving;
    } else {
      StringBuilder buf = new StringBuilder();
      boolean first = true;
      for (NotaItem notaItem: getNotaItems()) {
        if (!first) {
          buf.append(", ");
        } else {
          first = false;
        }
        buf.append(notaItem.getOmschrijving());
      }
      return buf.toString();
    }
  }

} //NotaImpl
