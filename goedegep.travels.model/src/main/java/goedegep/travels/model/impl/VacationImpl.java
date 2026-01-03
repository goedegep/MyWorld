/**
 */
package goedegep.travels.model.impl;

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

import goedegep.travels.model.Day;
import goedegep.travels.model.Travel;
import goedegep.travels.model.Vacation;
import goedegep.travels.model.VacationElement;
import goedegep.travels.model.TravelsPackage;
import goedegep.types.model.FileReference;
import goedegep.types.model.impl.EventImpl;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vakantie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.VacationImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.VacationImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.VacationImpl#getDocuments <em>Documents</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.VacationImpl#getPictures <em>Pictures</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.VacationImpl#getEndDate <em>End Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VacationImpl extends EventImpl implements Vacation {
  private static final Logger LOGGER = Logger.getLogger(VacationImpl.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat(true, true);

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
   * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate END_DATE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected FlexDate endDate = END_DATE_EDEFAULT;
  /**
   * This is true if the End Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean endDateESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.VACATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getEndDate() {
    return endDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEndDate(FlexDate newEndDate) {
    FlexDate oldEndDate = endDate;
    endDate = newEndDate;
    boolean oldEndDateESet = endDateESet;
    endDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.VACATION__END_DATE, oldEndDate, endDate,
          !oldEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetEndDate() {
    FlexDate oldEndDate = endDate;
    boolean oldEndDateESet = endDateESet;
    endDate = END_DATE_EDEFAULT;
    endDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.VACATION__END_DATE, oldEndDate,
          END_DATE_EDEFAULT, oldEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetEndDate() {
    return endDateESet;
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
          TravelsPackage.VACATION__DOCUMENTS);
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
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.VACATION__PICTURES, oldPictures, pictures));
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
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.VACATION__TITLE, oldTitle, title,
          !oldTitleESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.VACATION__TITLE, oldTitle, TITLE_EDEFAULT,
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
          TravelsPackage.VACATION__ELEMENTS);
    }
    return elements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public FileReference findDocument(String documentPath) {
    for (FileReference documentReference : getDocuments()) {
      if (documentReference.getFile().equals(documentPath)) {
        return documentReference;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public EList<FileReference> getAllFileReferences() {
    EList<FileReference> fileReferences = new BasicEList<>();

    //    fileReferences.addAll(getDocuments());

    TreeIterator<EObject> vacationIterator = eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
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
   * @generated NOT
   */
  public EList<String> getAllReferencedFiles() {
    EList<String> referencedFiles = new BasicEList<>();

    for (FileReference bestandReferente : getDocuments()) {
      referencedFiles.add(bestandReferente.getFile());
    }

    //    for (VisitedLocation visitedLocation : getStayedAt()) {
    //      for (BestandReferentie bestandReferentie : visitedLocation.getDocuments()) {
    //        referencedFiles.add(bestandReferentie.getBestand());
    //      }
    //      for (BestandReferentie bestandReferentie : visitedLocation.getPictures()) {
    //        referencedFiles.add(bestandReferentie.getBestand());
    //      }
    //    }

    return referencedFiles;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String getId() {
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;
    if (isSetDate()) {
      buf.append(FDF.format(getDate()));
      spaceNeeded = true;
    }
    if (isSetTitle()) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(getTitle());
    }
    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Integer getDayNr(VacationElement vacationElement) {
    LOGGER.info("=> vacationElement=" + vacationElement.toString());
    int dayNr = 0;
    int daysPreviousDayElement = 1;

    TreeIterator<EObject> vacationIterator = this.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      LOGGER.info("Handling eObject=" + eObject.toString());

      if (eObject instanceof Day) {
        Day vacationElementDay = (Day) eObject;
        LOGGER.info("VacationElementDay found vacationElementDay=" + vacationElementDay.toString());
        int nrOfDaysThisElement = 1;
        if (vacationElementDay.getDays() != null) {
          nrOfDaysThisElement = vacationElementDay.getDays();
          LOGGER.info("dayNr updated dayNr=" + dayNr);
        }
        dayNr += daysPreviousDayElement;
        daysPreviousDayElement = nrOfDaysThisElement;
      }

      if (vacationElement.equals(eObject)) {
        LOGGER.info("<= dayNr=" + dayNr);
        return dayNr;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case TravelsPackage.VACATION__ELEMENTS:
      return ((InternalEList<?>) getElements()).basicRemove(otherEnd, msgs);
    case TravelsPackage.VACATION__DOCUMENTS:
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
    case TravelsPackage.VACATION__TITLE:
      return getTitle();
    case TravelsPackage.VACATION__ELEMENTS:
      return getElements();
    case TravelsPackage.VACATION__DOCUMENTS:
      return getDocuments();
    case TravelsPackage.VACATION__PICTURES:
      return getPictures();
    case TravelsPackage.VACATION__END_DATE:
      return getEndDate();
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
    case TravelsPackage.VACATION__TITLE:
      setTitle((String) newValue);
      return;
    case TravelsPackage.VACATION__ELEMENTS:
      getElements().clear();
      getElements().addAll((Collection<? extends VacationElement>) newValue);
      return;
    case TravelsPackage.VACATION__DOCUMENTS:
      getDocuments().clear();
      getDocuments().addAll((Collection<? extends FileReference>) newValue);
      return;
    case TravelsPackage.VACATION__PICTURES:
      setPictures((String) newValue);
      return;
    case TravelsPackage.VACATION__END_DATE:
      setEndDate((FlexDate) newValue);
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
    case TravelsPackage.VACATION__TITLE:
      unsetTitle();
      return;
    case TravelsPackage.VACATION__ELEMENTS:
      getElements().clear();
      return;
    case TravelsPackage.VACATION__DOCUMENTS:
      getDocuments().clear();
      return;
    case TravelsPackage.VACATION__PICTURES:
      setPictures(PICTURES_EDEFAULT);
      return;
    case TravelsPackage.VACATION__END_DATE:
      unsetEndDate();
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
    case TravelsPackage.VACATION__TITLE:
      return isSetTitle();
    case TravelsPackage.VACATION__ELEMENTS:
      return elements != null && !elements.isEmpty();
    case TravelsPackage.VACATION__DOCUMENTS:
      return documents != null && !documents.isEmpty();
    case TravelsPackage.VACATION__PICTURES:
      return PICTURES_EDEFAULT == null ? pictures != null : !PICTURES_EDEFAULT.equals(pictures);
    case TravelsPackage.VACATION__END_DATE:
      return isSetEndDate();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == Travel.class) {
      switch (derivedFeatureID) {
      case TravelsPackage.VACATION__TITLE:
        return TravelsPackage.TRAVEL__TITLE;
      case TravelsPackage.VACATION__ELEMENTS:
        return TravelsPackage.TRAVEL__ELEMENTS;
      case TravelsPackage.VACATION__DOCUMENTS:
        return TravelsPackage.TRAVEL__DOCUMENTS;
      case TravelsPackage.VACATION__PICTURES:
        return TravelsPackage.TRAVEL__PICTURES;
      default:
        return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == Travel.class) {
      switch (baseFeatureID) {
      case TravelsPackage.TRAVEL__TITLE:
        return TravelsPackage.VACATION__TITLE;
      case TravelsPackage.TRAVEL__ELEMENTS:
        return TravelsPackage.VACATION__ELEMENTS;
      case TravelsPackage.TRAVEL__DOCUMENTS:
        return TravelsPackage.VACATION__DOCUMENTS;
      case TravelsPackage.TRAVEL__PICTURES:
        return TravelsPackage.VACATION__PICTURES;
      default:
        return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
    if (baseClass == Travel.class) {
      switch (baseOperationID) {
      case TravelsPackage.TRAVEL___GET_ID:
        return TravelsPackage.VACATION___GET_ID;
      case TravelsPackage.TRAVEL___GET_ALL_FILE_REFERENCES:
        return TravelsPackage.VACATION___GET_ALL_FILE_REFERENCES;
      case TravelsPackage.TRAVEL___FIND_DOCUMENT__STRING:
        return TravelsPackage.VACATION___FIND_DOCUMENT__STRING;
      case TravelsPackage.TRAVEL___GET_ALL_REFERENCED_FILES:
        return TravelsPackage.VACATION___GET_ALL_REFERENCED_FILES;
      default:
        return -1;
      }
    }
    return super.eDerivedOperationID(baseOperationID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case TravelsPackage.VACATION___GET_DAY_NR__VACATIONELEMENT:
      return getDayNr((VacationElement) arguments.get(0));
    case TravelsPackage.VACATION___GET_ID:
      return getId();
    case TravelsPackage.VACATION___GET_ALL_FILE_REFERENCES:
      return getAllFileReferences();
    case TravelsPackage.VACATION___FIND_DOCUMENT__STRING:
      return findDocument((String) arguments.get(0));
    case TravelsPackage.VACATION___GET_ALL_REFERENCED_FILES:
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
    result.append(", endDate: ");
    if (endDateESet)
      result.append(endDate);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //VakantieImpl
