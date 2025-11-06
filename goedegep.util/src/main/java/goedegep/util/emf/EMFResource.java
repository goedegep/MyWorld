package goedegep.util.emf;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.XMLResource;

import goedegep.util.file.FileUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class handles an EMF {@link Resource}.
 * <p>
 * The class uses the ResourceSet singleton provided by {@link EMFResourceSet#getResourceSet}.
 * <p>
 * Typical ways of using this class.
 * <ul>
 *   <li>
 *   Open, edit and save a resource<br/>
 *   <ul>
 *     <li>
 *     Open the resource by calling {@link #load(resourceFileName)}<br/>
 *     This returns the object, which can be shown to the user and edited. And sets '{@code resourceFileName}' as the 'current file'.
 *     </li>
 *     <li>
 *     Edit the object outside of this class.
 *     </li>
 *     <li>
 *     Save the object by calling {@link #save()}.<br/>
 *     This saves the object to the 'current file'. In this case overwriting the file which is was loaded from.</br>
 *     The object can also be saved to a different file by calling {@link #save(resourceFileName)}.
 *     </li>
 *   </ul>
 *   </li>
 *   <li>
 *   Create a new object and save it.
 *   <ul>
 *     <li>
 *     Create the object by calling {@link #newEObject} and edit it outside of this class.
 *     </li>
 *     <li>
 *     Save the object by calling {@link #save(resourceFileName)}. This also sets '{@code resourceFileName}' as the 'current file'.
 *     So after further editing the object can be saved by calling {@link #save()}. 
 *     </li> 
 *   </ul>
 *   </li>
 * </ul>
 * 
 * Or in terms of a standard 'File menu'.
 * <ul>
 * <li>
 * New<br/>
 * Obtain the new object by calling {@link #newEObject()}.
 * </li>
 * <li>
 * Open ...<br/>
 * The file name has to be determined by the application, typically via a FileChooser. Load the object from the file
 * by calling {@link #load(resourceFileName)}.
 * </li>
 * <li>
 * Save<br/>
 * If there is a 'current file', which can be checked by calling {@link #getFileName()}, save the object by calling {@link #save()}.
 * Else, the application has to determine a file name, typically via a FileChooser, and then call {@link #save(String resourceFileName)}.
 * </li>
 * <li>
 * Save as ...<br/>
 * The application has to determine a file name, typically via a FileChooser, and then call {@link #save(String resourceFileName)}.
 * </li>
 * </ul>
 * If an application has to react to changes in the object, it can register an EMFNotificationListener (instead of creating its own Adapter and adding that to the object).
 * <p>
 * The following Properties are provided:
 * <ul>
 * <li>
 * dirty</br>
 * If true, there are unsaved changes in the object.
 * </li>
 * <li>
 * resourceFileName</br>
 * The name of the current file.
 * </li>
 * </ul>
 * 
 * Note: When an xml file is loaded using a model generated from the related xsd file, you first have to register the EPackage.
 * E.g. EPackage.Registry.INSTANCE.put(GPXPackage.eNS_URI, GPXPackage.eINSTANCE);<br/>
 * Note: Initially a Resource is created for a dummy URI. This way the EObject is always related to a Resource and ResourceSet, which makes it possible to find references
 * by calling EcoreUtil.UsageCrossReferencer.find(eObject, resourceSet).

 * 
 * @param <E> The data type handled by this resource.
 */
public class EMFResource<E extends EObject> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EMFResource.class.getName());
  
  /**
   * Functional interface to create a new instance of type E.
   */
  private EmfObjectCreator<E> emfObjectCreator;
  
  /**
   * If true, the save() method makes a backup of the file before saving.
   */
  private boolean createBackupFileOnSave;
  
  /**
   * An {@link EMFResourceSet} needed to handle a Resource.
   */
  private ResourceSet resourceSet = null;
  
  /**
   * The {@link Resource} handled by this class.
   * <p>
   * When no file name/URI is known yet, the resource if for a dummy URI.
   * By always having a Resource, it is possible to find references using EcoreUtil.UsageCrossReferencer.find(eObject, resourceSet).
   */
  private Resource resource = null;
  
  /**
   * Dummy URI used when no file is yet known.
   */
  private static URI dummyURI = URI.createURI("http:/goedegep.dummy/dummyfile");
  
  /**
   * An {@link EContentAdapter} to detect changes in the resource.
   */
  private EContentAdapter eContentAdapter = null;
  
  /**
   * Indicaton of whether there are unsaved changes or not.
   */
  private BooleanProperty dirty = new SimpleBooleanProperty();
  
  /**
   * The current URI of the resource file.
   */
  private ObjectProperty<URI> uriProperty = new SimpleObjectProperty<>();
  
  /**
   * Listeners for changes in the resource.
   */
  private List<EMFNotificationListener> emfNotificationListeners = new ArrayList<>();
  

  /**
   * Constructor.
   * 
   * Upon loading a file, the related EPackage must be registered. As soon as an EPackage is accessed, it is automatically
   * registered. So by having the EPackage as a parameter to the constructor, it is automatically guaranteed that it is registered.
   * 
   * @param ePackage dummy parameter, just to make sure the EPackage is registered.
   * @param emfObjectCreator method (functional interface) to create a new instance of type E.
   */
  public EMFResource(EPackage ePackage, EmfObjectCreator<E> emfObjectCreator, String fileExtension) {
    this(ePackage, emfObjectCreator, fileExtension, false);
  }

  /**
   * Constructor.
   * 
   * Upon loading a file, the related EPackage must be registered. As soon as an EPackage is accessed, it is automatically
   * registered. So by having the EPackage as a parameter to the constructor, it is automatically guaranteed that it is registered.
   * 
   * @param ePackage dummy parameter, just to make sure the EPackage is registered.
   * @param emfObjectCreator method (functional interface) to create a new instance of type E.
   */
  public EMFResource(EPackage ePackage, EmfObjectCreator<E> emfObjectCreator, String fileExtension, boolean createBackupFileOnSave) {
    this.emfObjectCreator = emfObjectCreator;
    this.createBackupFileOnSave = createBackupFileOnSave;
    dirty.set(false);
    
    eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(Notification notification) {
        
        super.notifyChanged(notification);
        dirty.set(true);
        notifyNotificationListeners(notification);
      }

    };
    
    resourceSet = EMFResourceSet.getResourceSet();
    
    resource = resourceSet.createResource(dummyURI);
    uriProperty.set(null);

    // disable DTD resolution
    Map<String, Boolean> parserFeatures = new HashMap<>();
    parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    resourceSet.getLoadOptions().put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
    
  }
  
  /**
   * Create a new instance of type E.
   * <p>
   * Any current instance will be discarded.
   */
  public E newEObject() {
    E newEObject = emfObjectCreator.createEObject();
    
    E currentEObject = getEObject();
    if (currentEObject != null) {
      currentEObject.eAdapters().remove(eContentAdapter);
    }
    
    resource.getContents().clear();
    resource.getContents().add(newEObject);
    
    dirty.set(false);
    newEObject.eAdapters().add(eContentAdapter);
    
    return newEObject;
  }
  
  /**
   * Load the resource from a specific file.
   * <p>
   * This file will now be the 'current file'.
   * 
   * @param resourceFileName the name of the resource file.
   * @return the top level EObject of the resource.
   * @throws FileNotFoundException if the specified file doesn't exist.
   * @throws MalformedURLException 
   */
  public E load(String resourceFileName) throws IOException, FileNotFoundException {
    java.net.URI resourceURI = Path.of(resourceFileName).toUri();

    return load(resourceURI);    
  }
  
  /**
   * Load the resource from a specific file.
   * <p>
   * This file will now be the 'current file'.
   * 
   * @param resourceFileName the name of the resource file.
   * @return the top level EObject of the resource.
   * @throws FileNotFoundException if the specified file doesn't exist.
   */
  public E load(java.net.URI resourceURL) throws IOException, FileNotFoundException {
    URI fileURI = URI.createURI(resourceURL.toString());
    
    resource.unload();
    // creation of the Resource depends on the file name extension. So we have to create a new Resource.
    resourceSet.getResources().remove(resource);
    resource = resourceSet.createResource(fileURI);
    uriProperty.set(resource.getURI());
    resource.load(null);
        
    if (resource.getContents().size() != 1) {
      throw new RuntimeException("Wrong number of elements in contents: " + resource.getContents().size());
    }
    
    @SuppressWarnings("unchecked")
    E retval = (E) resource.getContents().get(0);
    dirty.set(false);
    retval.eAdapters().add(eContentAdapter);
    
    return retval;
  }
  
  /**
   * Save the current state of the resource to the 'current file'.
   * 
   * @throws IOException Thrown if saving to the file failed.
   */
  public void save() throws IOException {

    if (resource.getURI() == dummyURI) {
      throw new RuntimeException("No current file name. This method can only be called if there is a current file name.");
    }

    if (createBackupFileOnSave) {
      File file = new File(uriProperty.get().toFileString());
      String directory = file.getParent();
      String fileName = file.getName();
      FileUtils.moveFileToBackupFolder(directory, fileName, true);
    }

    resource.save(null);
    dirty.set(false);
  }
  
  /**
   * Save the current state of the resource to a specific file.
   * <p>
   * This file will now be the 'current file'.
   * 
   * @param resourceFileName the name of the resource file.
   * @throws IOException Thrown if saving to the file failed.
   */
  public void save(String resourceFileName) throws IOException {
        
    Path resourceFilePath = Paths.get(resourceFileName).toAbsolutePath().normalize();
        
    URI fileURI = URI.createFileURI(resourceFilePath.toString());
    resource.setURI(fileURI);
    uriProperty.set(resource.getURI());
    
    save();    
  }
    
  /**
   * Unload the resource.
   */
  public void unload() {
    
    resource.unload();
    dirty.set(false);
  }
  
  /**
   * Delete the resource.
   * <p>
   * The current resource file is deleted and the resource is unloaded.
   */
  public void delete() {
    
    try {
      resource.delete(null);
      resource = resourceSet.createResource(dummyURI);
      uriProperty.set(null);
      dirty.set(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  /**
   * Check whether there are unsaved changes in the object.
   * 
   * @return true if there are unsaved changes in the object, false otherwise.
   */
  public boolean isDirty() {
    return dirty.get();
  }
  
  /**
   * Get the 'dirty' property.
   * 
   * @return the 'dirty' property.
   */
  public BooleanProperty dirtyProperty() {
    return dirty;
  }
  
  /**
   * Get the resource file URI property.
   * 
   * @return the current URI property.
   */
  public ObjectProperty<URI> uriProperty() {
    return uriProperty;
  }
  
  /**
   * Get the URI of the resource file.
   * 
   * @return the absolute pathname of the resource file.
   */
  public URI getURI() {
    return uriProperty.get();
  }
  
  /**
   * Get file name of the resource file. This is a String representation of the URI of the resource file.
   * 
   * @return file name of the resource file.
   */
  public String getFileName() {
    URI uri = uriProperty.get();
    
    return uri != null ? uri.toFileString() : null;
  }
  
  /**
   * Set the absolute pathname of the resource file.
   */
  public void setFileName(String resourceFileName) {
    Path resourceFilePath = Paths.get(resourceFileName).toAbsolutePath().normalize();
    java.net.URI  uri = resourceFilePath.toUri();
    setFileURI(uri);        
  }
  
  /**
   * Set the URI of the resource file.
   */
  public void setFileURI(java.net.URI resourceFileURI) {
    URI uri = URI.createURI(resourceFileURI.toString());
    resource.setURI(uri);
    uriProperty.set(resource.getURI());
  }
  
  /**
   * Get EObject value.
   * 
   * @return the current EObject value, which may be null.
   */
  @SuppressWarnings("unchecked")
  public E getEObject() {
    E retval = null;
    
    List<EObject> contents = resource.getContents();
    if (!contents.isEmpty()) {
      retval = (E) resource.getContents().get(0);
    }
     
    return retval;
  }
  
  /**
   * Set the EObject value.
   * <p>
   * Any current instance will be discarded.
   */
  public void setEObject(E eObject) {
    E currentEObject = getEObject();
    if (currentEObject != null) {
      currentEObject.eAdapters().remove(eContentAdapter);
    }
    
    resource.getContents().clear();
    resource.getContents().add(eObject);

    dirty.set(false);
    eObject.eAdapters().add(eContentAdapter);
  }
  
  /**
   * Add a Notification listener.
   * 
   * @param emfNotificationListener the <code>EMFNotificationListener</code> to be added.
   */
  public void addNotificationListener(EMFNotificationListener emfNotificationListener) {
    emfNotificationListeners.add(emfNotificationListener);
  }
  
  /**
   * Remove a Notification listener.
   * 
   * @param emfNotificationListener the <code>EMFNotificationListener</code> to be removed.
   */
  public void removeNotificationListener(EMFNotificationListener emfNotificationListener) {
    emfNotificationListeners.remove(emfNotificationListener);
  }
  
  /**
   * Notify the Notification listeners.
   * 
   * @param notification the <code>Notification</code>
   */
  private void notifyNotificationListeners(Notification notification) {
    for (EMFNotificationListener emfNotificationListener: emfNotificationListeners) {
      emfNotificationListener.notifyChanged(notification);
    }
  }
    
}
