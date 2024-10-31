/**
 */
package goedegep.types.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bestand Referentie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.types.model.impl.FileReferenceImpl#getFile <em>File</em>}</li>
 *   <li>{@link goedegep.types.model.impl.FileReferenceImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.types.model.impl.FileReferenceImpl#getTags <em>Tags</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileReferenceImpl extends MinimalEObjectImpl.Container implements FileReference {
  /**
   * The default value of the '{@link #getFile() <em>File</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFile()
   * @generated
   * @ordered
   */
  protected static final String FILE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFile()
   * @generated
   * @ordered
   */
  protected String file = FILE_EDEFAULT;

  /**
   * This is true if the File attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fileESet;

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
   * The default value of the '{@link #getTags() <em>Tags</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTags()
   * @generated
   * @ordered
   */
  protected static final String TAGS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTags() <em>Tags</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTags()
   * @generated
   * @ordered
   */
  protected String tags = TAGS_EDEFAULT;

  /**
   * This is true if the Tags attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean tagsESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FileReferenceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TypesPackage.Literals.FILE_REFERENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFile() {
    return file;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFile(String newFile) {
    String oldFile = file;
    file = newFile;
    boolean oldFileESet = fileESet;
    fileESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FILE_REFERENCE__FILE, oldFile, file,
          !oldFileESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFile() {
    String oldFile = file;
    boolean oldFileESet = fileESet;
    file = FILE_EDEFAULT;
    fileESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FILE_REFERENCE__FILE, oldFile, FILE_EDEFAULT,
          oldFileESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFile() {
    return fileESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FILE_REFERENCE__TITLE, oldTitle, title,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FILE_REFERENCE__TITLE, oldTitle,
          TITLE_EDEFAULT, oldTitleESet));
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
  public String getTags() {
    return tags;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTags(String newTags) {
    String oldTags = tags;
    tags = newTags;
    boolean oldTagsESet = tagsESet;
    tagsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FILE_REFERENCE__TAGS, oldTags, tags,
          !oldTagsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTags() {
    String oldTags = tags;
    boolean oldTagsESet = tagsESet;
    tags = TAGS_EDEFAULT;
    tagsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FILE_REFERENCE__TAGS, oldTags, TAGS_EDEFAULT,
          oldTagsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTags() {
    return tagsESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case TypesPackage.FILE_REFERENCE__FILE:
      return getFile();
    case TypesPackage.FILE_REFERENCE__TITLE:
      return getTitle();
    case TypesPackage.FILE_REFERENCE__TAGS:
      return getTags();
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
    case TypesPackage.FILE_REFERENCE__FILE:
      setFile((String) newValue);
      return;
    case TypesPackage.FILE_REFERENCE__TITLE:
      setTitle((String) newValue);
      return;
    case TypesPackage.FILE_REFERENCE__TAGS:
      setTags((String) newValue);
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
    case TypesPackage.FILE_REFERENCE__FILE:
      unsetFile();
      return;
    case TypesPackage.FILE_REFERENCE__TITLE:
      unsetTitle();
      return;
    case TypesPackage.FILE_REFERENCE__TAGS:
      unsetTags();
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
    case TypesPackage.FILE_REFERENCE__FILE:
      return isSetFile();
    case TypesPackage.FILE_REFERENCE__TITLE:
      return isSetTitle();
    case TypesPackage.FILE_REFERENCE__TAGS:
      return isSetTags();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder();
    
    result.append("File: ")
    .append(getFile() != null ? getFile() : "<unset>")
    .append("\n");
    
    result.append("Title: ")
    .append(getTitle() != null ? getTitle() : "<unset>")
    .append("\n");
    
    result.append("Tags: ")
    .append(getTags() != null ? getTags() : "<unset>")
    .append("\n");
    
    return result.toString();
  }

} //BestandReferentieImpl
