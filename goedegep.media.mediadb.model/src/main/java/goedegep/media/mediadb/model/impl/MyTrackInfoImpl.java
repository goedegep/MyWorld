/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.TrackReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Track Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getCollection <em>Collection</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getIHaveOn <em>IHave On</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getIWant <em>IWant</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getCompilationTrackReference <em>Compilation Track Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MyTrackInfoImpl extends MinimalEObjectImpl.Container implements MyTrackInfo {
  private static final Logger LOGGER = Logger.getLogger(MyTrackInfoImpl.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The default value of the '{@link #getCollection() <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCollection()
   * @generated
   * @ordered
   */
  protected static final Collection COLLECTION_EDEFAULT = Collection.NOT_SET;

  /**
   * The cached value of the '{@link #getCollection() <em>Collection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCollection()
   * @generated
   * @ordered
   */
  protected Collection collection = COLLECTION_EDEFAULT;

  /**
   * This is true if the Collection attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean collectionESet;

  /**
   * The cached value of the '{@link #getIHaveOn() <em>IHave On</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIHaveOn()
   * @generated
   * @ordered
   */
  protected EList<MediumInfo> iHaveOn;

  /**
   * The default value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected static final IWant IWANT_EDEFAULT = IWant.NOT_SET;

  /**
   * The cached value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected IWant iWant = IWANT_EDEFAULT;

  /**
   * This is true if the IWant attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iWantESet;

  /**
   * The cached value of the '{@link #getCompilationTrackReference() <em>Compilation Track Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompilationTrackReference()
   * @generated
   * @ordered
   */
  protected TrackReference compilationTrackReference;

  /**
   * This is true if the Compilation Track Reference reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compilationTrackReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MyTrackInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MY_TRACK_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection getCollection() {
    return collection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCollection(Collection newCollection) {
    Collection oldCollection = collection;
    collection = newCollection == null ? COLLECTION_EDEFAULT : newCollection;
    boolean oldCollectionESet = collectionESet;
    collectionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_TRACK_INFO__COLLECTION, oldCollection,
          collection, !oldCollectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCollection() {
    Collection oldCollection = collection;
    boolean oldCollectionESet = collectionESet;
    collection = COLLECTION_EDEFAULT;
    collectionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_TRACK_INFO__COLLECTION, oldCollection,
          COLLECTION_EDEFAULT, oldCollectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCollection() {
    return collectionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<MediumInfo> getIHaveOn() {
    if (iHaveOn == null) {
      iHaveOn = new EObjectContainmentEList.Unsettable<MediumInfo>(MediumInfo.class, this,
          MediadbPackage.MY_TRACK_INFO__IHAVE_ON);
    }
    return iHaveOn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIHaveOn() {
    if (iHaveOn != null)
      ((InternalEList.Unsettable<?>) iHaveOn).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIHaveOn() {
    return iHaveOn != null && ((InternalEList.Unsettable<?>) iHaveOn).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IWant getIWant() {
    return iWant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIWant(IWant newIWant) {
    IWant oldIWant = iWant;
    iWant = newIWant == null ? IWANT_EDEFAULT : newIWant;
    boolean oldIWantESet = iWantESet;
    iWantESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_TRACK_INFO__IWANT, oldIWant, iWant,
          !oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIWant() {
    IWant oldIWant = iWant;
    boolean oldIWantESet = iWantESet;
    iWant = IWANT_EDEFAULT;
    iWantESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_TRACK_INFO__IWANT, oldIWant,
          IWANT_EDEFAULT, oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIWant() {
    return iWantESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackReference getCompilationTrackReference() {
    if (compilationTrackReference != null && compilationTrackReference.eIsProxy()) {
      InternalEObject oldCompilationTrackReference = (InternalEObject) compilationTrackReference;
      compilationTrackReference = (TrackReference) eResolveProxy(oldCompilationTrackReference);
      if (compilationTrackReference != oldCompilationTrackReference) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE, oldCompilationTrackReference,
              compilationTrackReference));
      }
    }
    return compilationTrackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrackReference basicGetCompilationTrackReference() {
    return compilationTrackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompilationTrackReference(TrackReference newCompilationTrackReference) {
    TrackReference oldCompilationTrackReference = compilationTrackReference;
    compilationTrackReference = newCompilationTrackReference;
    boolean oldCompilationTrackReferenceESet = compilationTrackReferenceESet;
    compilationTrackReferenceESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE,
          oldCompilationTrackReference, compilationTrackReference, !oldCompilationTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompilationTrackReference() {
    TrackReference oldCompilationTrackReference = compilationTrackReference;
    boolean oldCompilationTrackReferenceESet = compilationTrackReferenceESet;
    compilationTrackReference = null;
    compilationTrackReferenceESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE,
          oldCompilationTrackReference, null, oldCompilationTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompilationTrackReference() {
    return compilationTrackReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return ((InternalEList<?>) getIHaveOn()).basicRemove(otherEnd, msgs);
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
    case MediadbPackage.MY_TRACK_INFO__COLLECTION:
      return getCollection();
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return getIHaveOn();
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      return getIWant();
    case MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE:
      if (resolve)
        return getCompilationTrackReference();
      return basicGetCompilationTrackReference();
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
    case MediadbPackage.MY_TRACK_INFO__COLLECTION:
      setCollection((Collection) newValue);
      return;
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      getIHaveOn().clear();
      getIHaveOn().addAll((java.util.Collection<? extends MediumInfo>) newValue);
      return;
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      setIWant((IWant) newValue);
      return;
    case MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE:
      setCompilationTrackReference((TrackReference) newValue);
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
    case MediadbPackage.MY_TRACK_INFO__COLLECTION:
      unsetCollection();
      return;
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      unsetIHaveOn();
      return;
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      unsetIWant();
      return;
    case MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE:
      unsetCompilationTrackReference();
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
    case MediadbPackage.MY_TRACK_INFO__COLLECTION:
      return isSetCollection();
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return isSetIHaveOn();
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      return isSetIWant();
    case MediadbPackage.MY_TRACK_INFO__COMPILATION_TRACK_REFERENCE:
      return isSetCompilationTrackReference();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("collection: ");
    buf.append(isSetCollection() ? getCollection().toString() : "<no-collection>").append(NEWLINE);

    buf.append("iWant: ");
    buf.append(isSetIWant() ? getIWant().toString() : "<no-iwant>").append(NEWLINE);

    buf.append("iHaveOn: ");
    buf.append(isSetIHaveOn() ? getIHaveOn().toString() : "<no-ihave-on>").append(NEWLINE);

    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean iHaveOnEquals(List<MediumInfo> mediumInfos1, List<MediumInfo> mediumInfos2) {
    if (mediumInfos1.size() != mediumInfos2.size()) {
      return false;
    }

    for (int i = 0; i < mediumInfos1.size(); i++) {
      MediumInfo mediumInfo1 = mediumInfos1.get(i);
      MediumInfo mediomInfo2 = mediumInfos2.get(i);
      if (!mediumInfo1.equals(mediomInfo2)) {
        LOGGER.severe("Medium info differs: mediumInfo1=" + mediumInfo1 + ", mediomInfo2=" + mediomInfo2);
        return false;
      }
    }

    return true;
  }

} //MyTrackInfoImpl
