/**
 */
package goedegep.vacations.model.impl;

import goedegep.types.model.FileReference;

import goedegep.vacations.model.Document;
import goedegep.vacations.model.VacationsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.DocumentImpl#getDocumentReference <em>Document Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentImpl extends VacationElementImpl implements Document {
  /**
   * The cached value of the '{@link #getDocumentReference() <em>Document Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocumentReference()
   * @generated
   * @ordered
   */
  protected FileReference documentReference;

  /**
   * This is true if the Document Reference containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean documentReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DocumentImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.DOCUMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference getDocumentReference() {
    return documentReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDocumentReference(FileReference newDocumentReference, NotificationChain msgs) {
    FileReference oldDocumentReference = documentReference;
    documentReference = newDocumentReference;
    boolean oldDocumentReferenceESet = documentReferenceESet;
    documentReferenceESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, oldDocumentReference, newDocumentReference,
          !oldDocumentReferenceESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDocumentReference(FileReference newDocumentReference) {
    if (newDocumentReference != documentReference) {
      NotificationChain msgs = null;
      if (documentReference != null)
        msgs = ((InternalEObject) documentReference).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, null, msgs);
      if (newDocumentReference != null)
        msgs = ((InternalEObject) newDocumentReference).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, null, msgs);
      msgs = basicSetDocumentReference(newDocumentReference, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldDocumentReferenceESet = documentReferenceESet;
      documentReferenceESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE,
            newDocumentReference, newDocumentReference, !oldDocumentReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetDocumentReference(NotificationChain msgs) {
    FileReference oldDocumentReference = documentReference;
    documentReference = null;
    boolean oldDocumentReferenceESet = documentReferenceESet;
    documentReferenceESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, oldDocumentReference, null, oldDocumentReferenceESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDocumentReference() {
    if (documentReference != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) documentReference).eInverseRemove(this,
          EOPPOSITE_FEATURE_BASE - VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, null, msgs);
      msgs = basicUnsetDocumentReference(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldDocumentReferenceESet = documentReferenceESet;
      documentReferenceESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE, null,
            null, oldDocumentReferenceESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDocumentReference() {
    return documentReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE:
      return basicUnsetDocumentReference(msgs);
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
    case VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE:
      return getDocumentReference();
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
    case VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE:
      setDocumentReference((FileReference) newValue);
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
    case VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE:
      unsetDocumentReference();
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
    case VacationsPackage.DOCUMENT__DOCUMENT_REFERENCE:
      return isSetDocumentReference();
    }
    return super.eIsSet(featureID);
  }

} //DocumentImpl
