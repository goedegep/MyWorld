/**
 */
package goedegep.travels.model.impl;

import goedegep.travels.model.Travel;
import goedegep.travels.model.TravelsPackage;
import goedegep.travels.model.VacationElement;
import goedegep.types.model.FileReference;

import goedegep.types.model.impl.EventImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Travel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.TravelImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelImpl#getDocuments <em>Documents</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelImpl#getPictures <em>Pictures</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TravelImpl extends EventImpl implements Travel {
  private static final Logger LOGGER = Logger.getLogger(TravelImpl.class.getName());
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
   * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected EList<VacationElement> elements;

  /**
   * The cached value of the '{@link #getDocuments() <em>Documents</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocuments()
   * @generated
   * @ordered
   */
  protected EList<FileReference> documents;

  /**
   * The default value of the '{@link #getPictures() <em>Pictures</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPictures()
   * @generated
   * @ordered
   */
  protected static final String PICTURES_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getPictures() <em>Pictures</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPictures()
   * @generated
   * @ordered
   */
  protected String pictures = PICTURES_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.TRAVEL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVEL__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.TRAVEL__TITLE, oldTitle, TITLE_EDEFAULT,
          oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationElement> getElements() {
    if (elements == null) {
      elements = new EObjectContainmentEList<VacationElement>(VacationElement.class, this,
          TravelsPackage.TRAVEL__ELEMENTS);
    }
    return elements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FileReference> getDocuments() {
    if (documents == null) {
      documents = new EObjectContainmentEList<FileReference>(FileReference.class, this,
          TravelsPackage.TRAVEL__DOCUMENTS);
    }
    return documents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPictures() {
    return pictures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPictures(String newPictures) {
    String oldPictures = pictures;
    pictures = newPictures;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVEL__PICTURES, oldPictures, pictures));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getId() {
    String id = getTitle();
    if (id == null || id.isEmpty()) {
      id = "Unnamed Travel";
    }
    
    return id;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public EList<FileReference> getAllFileReferences() {
    EList<FileReference> fileReferences = new BasicEList<>();

    TreeIterator<EObject> travelIterator = eAllContents();
    while (travelIterator.hasNext()) {
      EObject eObject = travelIterator.next();
      if (eObject instanceof FileReference) {
        FileReference fileReference = (FileReference) eObject;
        LOGGER.info("found file reference:" + fileReference.getFile());
        fileReferences.add(fileReference);
      }
    }

    return fileReferences;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileReference findDocument(String documentPath) {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getAllReferencedFiles() {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case TravelsPackage.TRAVEL__ELEMENTS:
      return ((InternalEList<?>) getElements()).basicRemove(otherEnd, msgs);
    case TravelsPackage.TRAVEL__DOCUMENTS:
      return ((InternalEList<?>) getDocuments()).basicRemove(otherEnd, msgs);
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
    case TravelsPackage.TRAVEL__TITLE:
      return getTitle();
    case TravelsPackage.TRAVEL__ELEMENTS:
      return getElements();
    case TravelsPackage.TRAVEL__DOCUMENTS:
      return getDocuments();
    case TravelsPackage.TRAVEL__PICTURES:
      return getPictures();
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
    case TravelsPackage.TRAVEL__TITLE:
      setTitle((String) newValue);
      return;
    case TravelsPackage.TRAVEL__ELEMENTS:
      getElements().clear();
      getElements().addAll((Collection<? extends VacationElement>) newValue);
      return;
    case TravelsPackage.TRAVEL__DOCUMENTS:
      getDocuments().clear();
      getDocuments().addAll((Collection<? extends FileReference>) newValue);
      return;
    case TravelsPackage.TRAVEL__PICTURES:
      setPictures((String) newValue);
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
    case TravelsPackage.TRAVEL__TITLE:
      unsetTitle();
      return;
    case TravelsPackage.TRAVEL__ELEMENTS:
      getElements().clear();
      return;
    case TravelsPackage.TRAVEL__DOCUMENTS:
      getDocuments().clear();
      return;
    case TravelsPackage.TRAVEL__PICTURES:
      setPictures(PICTURES_EDEFAULT);
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
    case TravelsPackage.TRAVEL__TITLE:
      return isSetTitle();
    case TravelsPackage.TRAVEL__ELEMENTS:
      return elements != null && !elements.isEmpty();
    case TravelsPackage.TRAVEL__DOCUMENTS:
      return documents != null && !documents.isEmpty();
    case TravelsPackage.TRAVEL__PICTURES:
      return PICTURES_EDEFAULT == null ? pictures != null : !PICTURES_EDEFAULT.equals(pictures);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case TravelsPackage.TRAVEL___GET_ID:
      return getId();
    case TravelsPackage.TRAVEL___GET_ALL_FILE_REFERENCES:
      return getAllFileReferences();
    case TravelsPackage.TRAVEL___FIND_DOCUMENT__STRING:
      return findDocument((String) arguments.get(0));
    case TravelsPackage.TRAVEL___GET_ALL_REFERENCED_FILES:
      return getAllReferencedFiles();
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (title: ");
    if (titleESet)
      result.append(title);
    else
      result.append("<unset>");
    result.append(", pictures: ");
    result.append(pictures);
    result.append(')');
    return result.toString();
  }

} //TravelImpl
