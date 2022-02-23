/**
 */
package goedegep.util.emf.samplemodel.impl;

import goedegep.util.emf.samplemodel.DirectorySpecification;
import goedegep.util.emf.samplemodel.PCToolsPackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Directory Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl#getDirectoryPath <em>Directory Path</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl#getSynchronizationSpecification <em>Synchronization Specification</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl#getSourceControlSpecification <em>Source Control Specification</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl#isToBeChecked <em>To Be Checked</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DirectorySpecificationImpl extends MinimalEObjectImpl.Container implements DirectorySpecification {
  /**
   * The default value of the '{@link #getDirectoryPath() <em>Directory Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDirectoryPath()
   * @generated
   * @ordered
   */
  protected static final String DIRECTORY_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDirectoryPath() <em>Directory Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDirectoryPath()
   * @generated
   * @ordered
   */
  protected String directoryPath = DIRECTORY_PATH_EDEFAULT;

  /**
   * This is true if the Directory Path attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean directoryPathESet;

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
   * The default value of the '{@link #getSynchronizationSpecification() <em>Synchronization Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSynchronizationSpecification()
   * @generated
   * @ordered
   */
  protected static final String SYNCHRONIZATION_SPECIFICATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSynchronizationSpecification() <em>Synchronization Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSynchronizationSpecification()
   * @generated
   * @ordered
   */
  protected String synchronizationSpecification = SYNCHRONIZATION_SPECIFICATION_EDEFAULT;

  /**
   * This is true if the Synchronization Specification attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean synchronizationSpecificationESet;

  /**
   * The default value of the '{@link #getSourceControlSpecification() <em>Source Control Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceControlSpecification()
   * @generated
   * @ordered
   */
  protected static final String SOURCE_CONTROL_SPECIFICATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSourceControlSpecification() <em>Source Control Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceControlSpecification()
   * @generated
   * @ordered
   */
  protected String sourceControlSpecification = SOURCE_CONTROL_SPECIFICATION_EDEFAULT;

  /**
   * This is true if the Source Control Specification attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean sourceControlSpecificationESet;

  /**
   * The default value of the '{@link #isToBeChecked() <em>To Be Checked</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isToBeChecked()
   * @generated
   * @ordered
   */
  protected static final boolean TO_BE_CHECKED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isToBeChecked() <em>To Be Checked</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isToBeChecked()
   * @generated
   * @ordered
   */
  protected boolean toBeChecked = TO_BE_CHECKED_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DirectorySpecificationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.DIRECTORY_SPECIFICATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDirectoryPath() {
    return directoryPath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDirectoryPath(String newDirectoryPath) {
    String oldDirectoryPath = directoryPath;
    directoryPath = newDirectoryPath;
    boolean oldDirectoryPathESet = directoryPathESet;
    directoryPathESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH, oldDirectoryPath, directoryPath, !oldDirectoryPathESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDirectoryPath() {
    String oldDirectoryPath = directoryPath;
    boolean oldDirectoryPathESet = directoryPathESet;
    directoryPath = DIRECTORY_PATH_EDEFAULT;
    directoryPathESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH, oldDirectoryPath, DIRECTORY_PATH_EDEFAULT, oldDirectoryPathESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDirectoryPath() {
    return directoryPathESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSynchronizationSpecification() {
    return synchronizationSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public void setSynchronizationSpecification(String newSynchronizationSpecification) {
    if (toBeChecked) {
      throw new IllegalArgumentException("A 'to be checked' directory cannot be a synchronized directory.");
    }
    
    String oldSynchronizationSpecification = synchronizationSpecification;
    synchronizationSpecification = newSynchronizationSpecification;
    boolean oldSynchronizationSpecificationESet = synchronizationSpecificationESet;
    synchronizationSpecificationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION, oldSynchronizationSpecification, synchronizationSpecification, !oldSynchronizationSpecificationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSynchronizationSpecification() {
    String oldSynchronizationSpecification = synchronizationSpecification;
    boolean oldSynchronizationSpecificationESet = synchronizationSpecificationESet;
    synchronizationSpecification = SYNCHRONIZATION_SPECIFICATION_EDEFAULT;
    synchronizationSpecificationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION, oldSynchronizationSpecification, SYNCHRONIZATION_SPECIFICATION_EDEFAULT, oldSynchronizationSpecificationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSynchronizationSpecification() {
    return synchronizationSpecificationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSourceControlSpecification() {
    return sourceControlSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public void setSourceControlSpecification(String newSourceControlSpecification) {
    if (toBeChecked) {
      throw new IllegalArgumentException("A 'to be checked' directory cannot be a directory which is under source control.");
    }
    
    String oldSourceControlSpecification = sourceControlSpecification;
    sourceControlSpecification = newSourceControlSpecification;
    boolean oldSourceControlSpecificationESet = sourceControlSpecificationESet;
    sourceControlSpecificationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION, oldSourceControlSpecification, sourceControlSpecification, !oldSourceControlSpecificationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSourceControlSpecification() {
    String oldSourceControlSpecification = sourceControlSpecification;
    boolean oldSourceControlSpecificationESet = sourceControlSpecificationESet;
    sourceControlSpecification = SOURCE_CONTROL_SPECIFICATION_EDEFAULT;
    sourceControlSpecificationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION, oldSourceControlSpecification, SOURCE_CONTROL_SPECIFICATION_EDEFAULT, oldSourceControlSpecificationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSourceControlSpecification() {
    return sourceControlSpecificationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isToBeChecked() {
    return toBeChecked;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToBeChecked(boolean newToBeChecked) {
    boolean oldToBeChecked = toBeChecked;
    toBeChecked = newToBeChecked;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.DIRECTORY_SPECIFICATION__TO_BE_CHECKED, oldToBeChecked, toBeChecked));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean isControlled() {
    return (synchronizationSpecification != null)  ||  (sourceControlSpecification != null);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH:
        return getDirectoryPath();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION:
        return getDescription();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION:
        return getSynchronizationSpecification();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION:
        return getSourceControlSpecification();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__TO_BE_CHECKED:
        return isToBeChecked();
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
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH:
        setDirectoryPath((String)newValue);
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION:
        setSynchronizationSpecification((String)newValue);
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION:
        setSourceControlSpecification((String)newValue);
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__TO_BE_CHECKED:
        setToBeChecked((Boolean)newValue);
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
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH:
        unsetDirectoryPath();
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION:
        unsetDescription();
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION:
        unsetSynchronizationSpecification();
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION:
        unsetSourceControlSpecification();
        return;
      case PCToolsPackage.DIRECTORY_SPECIFICATION__TO_BE_CHECKED:
        setToBeChecked(TO_BE_CHECKED_EDEFAULT);
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
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DIRECTORY_PATH:
        return isSetDirectoryPath();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__DESCRIPTION:
        return isSetDescription();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION:
        return isSetSynchronizationSpecification();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION:
        return isSetSourceControlSpecification();
      case PCToolsPackage.DIRECTORY_SPECIFICATION__TO_BE_CHECKED:
        return toBeChecked != TO_BE_CHECKED_EDEFAULT;
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
      case PCToolsPackage.DIRECTORY_SPECIFICATION___IS_CONTROLLED:
        return isControlled();
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
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (directoryPath: ");
    if (directoryPathESet) result.append(directoryPath); else result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(", synchronizationSpecification: ");
    if (synchronizationSpecificationESet) result.append(synchronizationSpecification); else result.append("<unset>");
    result.append(", sourceControlSpecification: ");
    if (sourceControlSpecificationESet) result.append(sourceControlSpecification); else result.append("<unset>");
    result.append(", toBeChecked: ");
    result.append(toBeChecked);
    result.append(')');
    return result.toString();
  }

} //DirectorySpecificationImpl
