package goedegep.util.emf;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
  private static final Logger LOGGER = Logger.getLogger(EMFResource.class.getName());
  private static final String DEFAULT_URI  = "http:/goedegep.emfsample/model/defaultfile";
  
  private EmfObjectCreator<E> emfObjectCreator;  // Used to create a new object of type E.
  private boolean createBackupFileOnSave;        // If true, the save() method makes a backup of the file before saving.
  
  private ResourceSet resourceSet = null;        // needed to handle a Resource
  
  /*
   * When a Resource is created, it is always coupled to a file.
   * Therefore, to allow creating and editing an object before any load or save operation, the object is stored
   * separately in eObject. As soon as the resource is created, the object is added to it (and eObject is set to null).
   */
  private Resource resource = null;              // the current resource
  private URI defaultURI = null;
  
  private EContentAdapter eContentAdapter = null;  // to detect changes in the resource.
  private BooleanProperty dirty = new SimpleBooleanProperty();  // indicates whether there are unsaved changes.
  private StringProperty fileNameProperty = new SimpleStringProperty();    // the last known file from which the resource was loaded, or to which it was saved
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
    LOGGER.info("=> ePackage="  + ePackage.getName());
    
    this.emfObjectCreator = emfObjectCreator;
    this.createBackupFileOnSave = createBackupFileOnSave;
    dirty.set(false);
    updateFileNameProperty();
    
    eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(Notification notification) {
//        LOGGER.info(EmfUtil.printNotification(notification));
        
        super.notifyChanged(notification);
        dirty.set(true);
        notifyNotificationListeners(notification);
      }

    };
    
    resourceSet = EMFResourceSet.getResourceSet();
    
    defaultURI = URI.createURI(DEFAULT_URI + fileExtension);
    resource = resourceSet.createResource(defaultURI);

    // disable DTD resolution
    Map<String, Boolean> parserFeatures = new HashMap<>();
    parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    resourceSet.getLoadOptions().put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
    
    LOGGER.info("<=");
  }
  
//  /**
//   * Add a Resource.Factory for a file extension.
//   * 
//   * @param fileExtension the file extension (e.g. "gpx").
//   * @param resourceFactory the Resource.Factory for files with this extension.
//   */
//  public void addResourceFactoryForFileExtension(String fileExtension, Resource.Factory resourceFactory) {
//    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension, resourceFactory);
//    
//  }
  
  /**
   * Create a new instance of type E.
   * <p>
   * Any current instance will be discarded.
   */
  public E newEObject() {
    E newEObject = emfObjectCreator.createEObject();
    
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
   */
  public E load(URL resourceURL) throws FileNotFoundException {
//    LOGGER.severe("=> resourceFileName="  + resourceURL);
    
    URI fileURI = URI.createURI(resourceURL.toString());
    resource.unload();
    resource.setURI(fileURI);
    try {
      resource.load(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
        
    if (resource.getContents().size() != 1) {
      throw new RuntimeException("Wrong number of elements in contents: " + resource.getContents().size());
    }
    LOGGER.info("<= " + resource.getContents().get(0).toString());
    
    @SuppressWarnings("unchecked")
    E retval = (E) resource.getContents().get(0);
    dirty.set(false);
    retval.eAdapters().add(eContentAdapter);
    updateFileNameProperty();
    
    return retval;
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
  public E load(String resourceFileName) throws FileNotFoundException {
    java.net.URI resourceURI = Path.of(resourceFileName).toUri();
//    java.net.URI resourceURI = java.net.URI.create("file:" + resourceFileName);

    URL resourceURL = null;
    try {
      resourceURL = resourceURI.toURL();
    } catch (MalformedURLException e) {
      throw new FileNotFoundException(resourceFileName);
    }
    return load(resourceURL);    
  }
  
  /**
   * Save the current state of the resource to the 'current file'.
   * 
   * @throws IOException Thrown if saving to the file failed.
   */
  public void save() throws IOException {
    LOGGER.info("=>");

    if (resource.getURI() == defaultURI) {
      throw new RuntimeException("No current file name. This method can only be called if there is a current file name.");
    }

    if (createBackupFileOnSave) {
      File file = new File(fileNameProperty.get());
      String directory = file.getParent();
      String fileName = file.getName();
      LOGGER.info("directory: " + directory + ", fileName: " + fileName);
      FileUtils.moveFileToBackupFolder(directory, fileName, true);
    }

    resource.save(null);
    dirty.set(false);

    LOGGER.info("<=");
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
    LOGGER.info("=> resourceFileName=" + resourceFileName);
        
    Path resourceFilePath = Paths.get(resourceFileName).toAbsolutePath().normalize();
    LOGGER.fine("path=" + resourceFilePath.toString());
        
    URI fileURI = URI.createFileURI(resourceFilePath.toString());
    resource.setURI(fileURI);
    
    try {
      resource.save(null);
      dirty.set(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    updateFileNameProperty();
    
    LOGGER.info("<=");
  }
    
  /**
   * Unload the resource.
   */
  public void unload() {
    LOGGER.info("=>");
    
    resource.unload();
    dirty.set(false);
    
    LOGGER.info("<=");
  }
  
  /**
   * Delete the resource.
   * <p>
   * The current resource file is deleted and the resource is unloaded.
   */
  public void delete() {
    LOGGER.info("=>");
    
    try {
      resource.delete(null);
      resourceSet.getResources().remove(resource);
      resource = resourceSet.createResource(defaultURI);

      dirty.set(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    LOGGER.info("<=");
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
   * Get the 'fileName' property
   * @return
   */
  public StringProperty fileNameProperty() {
    return fileNameProperty;
  }
  
  /**
   * Get the absolute pathname of the resource file.
   * 
   * @return the absolute pathname of the resource file.
   */
  public String getFileName() {
    return fileNameProperty.get();
  }
  
  /**
   * Set the absolute pathname of the resource file.
   * 
   * @return the absolute pathname of the resource file.
   */
  public void setFileName(String resourceFileName) {
    Path resourceFilePath = Paths.get(resourceFileName).toAbsolutePath().normalize();
    LOGGER.fine("path=" + resourceFilePath.toString());
        
    URI fileURI = URI.createFileURI(resourceFilePath.toString());
    resource.setURI(fileURI);
    updateFileNameProperty();
  }
  
  /**
   * Get EObject value.
   * 
   * @return the current EObject value, which may be null.
   */
  public E getEObject() {
    @SuppressWarnings("unchecked")
    E retval = (E) resource.getContents().get(0);
    return retval;
  }
  
  /**
   * Set the EObject value.
   * <p>
   * Any current instance will be discarded.
   */
  public void setEObject(E eObject) {
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
  
  /**
   * Update the 'fileName' property.
   * <p>
   * The filename is obtained from the URI known to the resource.
   */
  private void updateFileNameProperty() {
    String newFileName = null;

    if (resource != null) {
      URI uri = resource.getURI();
      if (uri != null) {
        newFileName = uri.toFileString();
      }
    }

    fileNameProperty.set(newFileName);
  }
  
}
