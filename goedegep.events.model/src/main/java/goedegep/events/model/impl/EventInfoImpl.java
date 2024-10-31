/**
 */
package goedegep.events.model.impl;

import goedegep.events.model.EventInfo;
import goedegep.events.model.EventsPackage;
import goedegep.types.model.FileReference;
import goedegep.types.model.impl.EventImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.events.model.impl.EventInfoImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.events.model.impl.EventInfoImpl#getPicture <em>Picture</em>}</li>
 *   <li>{@link goedegep.events.model.impl.EventInfoImpl#getAttachments <em>Attachments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventInfoImpl extends EventImpl implements EventInfo {
  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;

  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The default value of the '{@link #getPicture() <em>Picture</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPicture()
   * @generated
   * @ordered
   */
  protected static final String PICTURE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPicture() <em>Picture</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPicture()
   * @generated
   * @ordered
   */
  protected String picture = PICTURE_EDEFAULT;

  /**
   * This is true if the Picture attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean pictureESet;

  /**
   * The cached value of the '{@link #getAttachments() <em>Attachments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttachments()
   * @generated
   * @ordered
   */
  protected EList<FileReference> attachments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EventInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return EventsPackage.Literals.EVENT_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EventsPackage.EVENT_INFO__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EventsPackage.EVENT_INFO__TITLE, oldTitle, TITLE_EDEFAULT, oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPicture() {
    return picture;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPicture(String newPicture) {
    String oldPicture = picture;
    picture = newPicture;
    boolean oldPictureESet = pictureESet;
    pictureESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EventsPackage.EVENT_INFO__PICTURE, oldPicture, picture, !oldPictureESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetPicture() {
    String oldPicture = picture;
    boolean oldPictureESet = pictureESet;
    picture = PICTURE_EDEFAULT;
    pictureESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EventsPackage.EVENT_INFO__PICTURE, oldPicture, PICTURE_EDEFAULT, oldPictureESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetPicture() {
    return pictureESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getAttachments() {
    if (attachments == null) {
      attachments = new EObjectContainmentEList<FileReference>(FileReference.class, this, EventsPackage.EVENT_INFO__ATTACHMENTS);
    }
    return attachments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case EventsPackage.EVENT_INFO__ATTACHMENTS:
        return ((InternalEList<?>)getAttachments()).basicRemove(otherEnd, msgs);
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
      case EventsPackage.EVENT_INFO__TITLE:
        return getTitle();
      case EventsPackage.EVENT_INFO__PICTURE:
        return getPicture();
      case EventsPackage.EVENT_INFO__ATTACHMENTS:
        return getAttachments();
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
      case EventsPackage.EVENT_INFO__TITLE:
        setTitle((String)newValue);
        return;
      case EventsPackage.EVENT_INFO__PICTURE:
        setPicture((String)newValue);
        return;
      case EventsPackage.EVENT_INFO__ATTACHMENTS:
        getAttachments().clear();
        getAttachments().addAll((Collection<? extends FileReference>)newValue);
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
      case EventsPackage.EVENT_INFO__TITLE:
        unsetTitle();
        return;
      case EventsPackage.EVENT_INFO__PICTURE:
        unsetPicture();
        return;
      case EventsPackage.EVENT_INFO__ATTACHMENTS:
        getAttachments().clear();
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
      case EventsPackage.EVENT_INFO__TITLE:
        return isSetTitle();
      case EventsPackage.EVENT_INFO__PICTURE:
        return isSetPicture();
      case EventsPackage.EVENT_INFO__ATTACHMENTS:
        return attachments != null && !attachments.isEmpty();
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
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder();
    result.append(super.toString());
    result.append("Title: ")
        .append(getTitle() != null ? getTitle() : "<unset>")
        .append("\n");
    result.append("Picture: ")
        .append(getPicture() != null ? getPicture() : "<unset>")
        .append("\n");
    for (FileReference fileReference: getAttachments()) {
      result.append("Attachment")
      .append(fileReference.toString());
    }
    
    return result.toString();
  }

} //EventInfoImpl
