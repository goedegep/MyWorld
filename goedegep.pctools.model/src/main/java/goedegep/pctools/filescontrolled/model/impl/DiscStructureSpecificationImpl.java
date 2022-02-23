/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Disc Structure Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl#getDirectorySpecifications <em>Directory Specifications</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl#getFilesToIgnoreCompletely <em>Files To Ignore Completely</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl#getDirectoriesToIgnoreCompletely <em>Directories To Ignore Completely</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiscStructureSpecificationImpl extends MinimalEObjectImpl.Container implements DiscStructureSpecification {
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * This is true if the Description attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionESet;

  /**
   * The cached value of the '{@link #getDirectorySpecifications() <em>Directory Specifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDirectorySpecifications()
   * @generated
   * @ordered
   */
  protected EList<DirectorySpecification> directorySpecifications;

  /**
   * The cached value of the '{@link #getFilesToIgnoreCompletely() <em>Files To Ignore Completely</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFilesToIgnoreCompletely()
   * @generated
   * @ordered
   */
  protected EList<DescribedItem> filesToIgnoreCompletely;

  /**
   * The cached value of the '{@link #getDirectoriesToIgnoreCompletely() <em>Directories To Ignore Completely</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDirectoriesToIgnoreCompletely()
   * @generated
   * @ordered
   */
  protected EList<DescribedItem> directoriesToIgnoreCompletely;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DiscStructureSpecificationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.DISC_STRUCTURE_SPECIFICATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME, oldName, name, !oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME, oldName, NAME_EDEFAULT, oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DescribedItem> getFilesToIgnoreCompletely() {
    if (filesToIgnoreCompletely == null) {
      filesToIgnoreCompletely = new EObjectContainmentEList.Unsettable<DescribedItem>(DescribedItem.class, this, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY);
    }
    return filesToIgnoreCompletely;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFilesToIgnoreCompletely() {
    if (filesToIgnoreCompletely != null) ((InternalEList.Unsettable<?>)filesToIgnoreCompletely).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFilesToIgnoreCompletely() {
    return filesToIgnoreCompletely != null && ((InternalEList.Unsettable<?>)filesToIgnoreCompletely).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DescribedItem> getDirectoriesToIgnoreCompletely() {
    if (directoriesToIgnoreCompletely == null) {
      directoriesToIgnoreCompletely = new EObjectContainmentEList.Unsettable<DescribedItem>(DescribedItem.class, this, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY);
    }
    return directoriesToIgnoreCompletely;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDirectoriesToIgnoreCompletely() {
    if (directoriesToIgnoreCompletely != null) ((InternalEList.Unsettable<?>)directoriesToIgnoreCompletely).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDirectoriesToIgnoreCompletely() {
    return directoriesToIgnoreCompletely != null && ((InternalEList.Unsettable<?>)directoriesToIgnoreCompletely).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DirectorySpecification> getDirectorySpecifications() {
    if (directorySpecifications == null) {
      directorySpecifications = new EObjectContainmentEList<DirectorySpecification>(DirectorySpecification.class, this, PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS);
    }
    return directorySpecifications;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS:
        return ((InternalEList<?>)getDirectorySpecifications()).basicRemove(otherEnd, msgs);
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY:
        return ((InternalEList<?>)getFilesToIgnoreCompletely()).basicRemove(otherEnd, msgs);
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY:
        return ((InternalEList<?>)getDirectoriesToIgnoreCompletely()).basicRemove(otherEnd, msgs);
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
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME:
        return getName();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION:
        return getDescription();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS:
        return getDirectorySpecifications();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY:
        return getFilesToIgnoreCompletely();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY:
        return getDirectoriesToIgnoreCompletely();
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
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME:
        setName((String)newValue);
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS:
        getDirectorySpecifications().clear();
        getDirectorySpecifications().addAll((Collection<? extends DirectorySpecification>)newValue);
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY:
        getFilesToIgnoreCompletely().clear();
        getFilesToIgnoreCompletely().addAll((Collection<? extends DescribedItem>)newValue);
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY:
        getDirectoriesToIgnoreCompletely().clear();
        getDirectoriesToIgnoreCompletely().addAll((Collection<? extends DescribedItem>)newValue);
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
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME:
        unsetName();
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION:
        unsetDescription();
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS:
        getDirectorySpecifications().clear();
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY:
        unsetFilesToIgnoreCompletely();
        return;
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY:
        unsetDirectoriesToIgnoreCompletely();
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
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__NAME:
        return isSetName();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DESCRIPTION:
        return isSetDescription();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS:
        return directorySpecifications != null && !directorySpecifications.isEmpty();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY:
        return isSetFilesToIgnoreCompletely();
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY:
        return isSetDirectoriesToIgnoreCompletely();
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

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    if (nameESet) result.append(name); else result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //DiscStructureSpecificationImpl
