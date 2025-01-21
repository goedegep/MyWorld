package goedegep.media.mediadb.app.guifx;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.TrackReferencesIterator;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This clas provides a window to add an Artist to a media database.
 */
public class ArtistDetailsEditor extends ObjectEditorTemplate<Artist> {
  private static final Logger LOGGER = Logger.getLogger(ArtistDetailsEditor.class.getName());
//  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  /**
   * The media database
   */
  private MediaDb mediaDb;
  
  /**
   * Factory for creating GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * Control for the name of the artist
   */
  private ObjectControlString nameObjectControl;
  
  /**
   * Control for the container artist.
   */
  private ObjectControlAutoCompleteTextField<Artist> containerArtistObjectControl;
  
  /**
   * Control for a photo of the artist.
   */
  private ObjectControlImageFile photoObjectControl;
  
  /**
   * Control for the style.
   */
  private ObjectControlString styleObjectControl;
  
  /**
   * Control for my comments.
   */
  private ObjectControlMultiLineString myCommentsControl;
  
  /**
   * Control for sample track
   */
  private ObjectControlAutoCompleteTextField<TrackReference> sampleTrackObjectControl;
  
  /**
   * Map from track name to track reference
   * <p>
   * Generating a name for a track reference is straightforward, but the other way around requires complex parsing.
   * Therefore when the sampleTrackObjectControl its options are set we store a name to track reference for each track in this map.
   */
  private Map<String, TrackReference> trackNameToReferenceMap = new HashMap<>();
  
  
  /**
   * Factory method to obtain a new instance of an {@code ArtistDetailsEditor}.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   * @return a newly created {@code ArtistDetailsEditor}.
   */
  public static ArtistDetailsEditor newInstance(CustomizationFx customization, String windowTitle, MediaDbService mediaDbService) {
    ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(customization, windowTitle, mediaDbService);
    artistDetailsEditor.performInitialization();
    
    return artistDetailsEditor;
  }
  
  public void setArtistName(String artistName) {
    nameObjectControl.setObject(artistName);
  }
    
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param windowTitle the window title
   * @param mediaDb the media database to add the artist to
   */
  private ArtistDetailsEditor(CustomizationFx customization, String windowTitle, MediaDbService mediaDbService) {
    super(customization, windowTitle, mediaDbService::addArtistToMediaDatabase);
    
    mediaDb = mediaDbService.getMediaDbResource().getEObject();
    
    componentFactory = customization.getComponentFactoryFx();

  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add artist", "Add the artist to the database");
    setUpdateObjectTexts("Update", "Update the current artist");
    setNewObjectTexts("New", "Clear the controls to start entering new artist information");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    // Create object controls
    nameObjectControl = componentFactory.createObjectControlString(null, 300, false, "Title of the event");
    containerArtistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(
        new StringConverterAndChecker<Artist>() {

          @Override
          public boolean isValid(String artistName) {
            if (artistName == null) {
              return true;
            } else {
              return mediaDb.getArtist(artistName) != null;
            }
          }

          @Override
          public String toString(Artist artist) {
            if (artist == null) {
              return null;
            } else {
              return artist.getName();
            }
          }

          @Override
          public Artist fromString(String artistName) {
            if (artistName == null) {
              return null;
            } else {
              return mediaDb.getArtist(artistName);
            }
          }

        }, null, 400, true, "Select an optional container artist");
    photoObjectControl = componentFactory.createObjectControlImageFile(true);
    photoObjectControl.setInitialDirectory(new File(MediaRegistry.musicDataDirectory + "\\ArtistInformation\\Pictures"));
    styleObjectControl = componentFactory.createObjectControlString(null, 300, true, "Free text describing the music style of the artist");
    myCommentsControl = componentFactory.createObjectControlMultiLineString(null, true);
    sampleTrackObjectControl = componentFactory.createObjectControlAutoCompleteTextField(
        new StringConverterAndChecker<TrackReference>() {

          @Override
          public boolean isValid(String trackText) {
            // always return true because the user can only select an item which was generated.
            return true;
          }

          @Override
          public String toString(TrackReference trackReference) {
            if (trackReference == null) {
              LOGGER.severe("<= null");
              return null;
            } else {
              String result = trackReferenceToString(trackReference);
              LOGGER.severe("<= " + result);
              return result;
            }
          }

          @Override
          public TrackReference fromString(String trackText) {
            if (trackText == null) {
              LOGGER.severe("<= null");
              return null;
            } else {
              TrackReference trackReference = trackNameToReferenceMap.get(trackText);
              LOGGER.severe("<= " + trackReference);
              return trackReference;
            }
          }

        }, null, 400, true, "Select an optional container artist");
    
    
    updateContainerArtistObjectControl();
    updateSampleTrackObjectControl();
    
    
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("Change detected: " + notification.toString());

        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }

        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
        if (MEDIA_DB_PACKAGE.getMediaDb_Artists().equals(feature)) {
          updateContainerArtistObjectControl();
        }
        
        updateSampleTrackObjectControl();

      }
      

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
    nameObjectControl.addListener((observable) -> updateSampleTrackObjectControl());
    
    // Add the object controls to the {@code objectControlsGroup}
    objectControlsGroup.addObjectControls(nameObjectControl, containerArtistObjectControl, photoObjectControl, styleObjectControl, myCommentsControl, sampleTrackObjectControl);
//    objectControlsGroup.addObjectControls(nameObjectControl, containerArtistObjectControl, photoObjectControl, styleObjectControl, myCommentsControl, sampleTrackObjectControl);    
  }
  
  private String trackReferenceToString(TrackReference trackReference) {
    StringBuilder buf = new StringBuilder();

    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo != null  &&  myTrackInfo.getTrackReference() != null) {
      trackReference = myTrackInfo.getTrackReference();
    }
    
    EObject trackReferenceContainer = trackReference.eContainer();
    switch (trackReferenceContainer) {
    case TrackCollection trackCollection -> buf.append(trackReference.getTrack().getTitle()).append(" in collection ").append(trackCollection.getCollection().getLiteral());
    case Disc disc -> {
      Album album = disc.getAlbum();
      buf.append(album.getTitle());
      if (album.isMultiDiscAlbum()) {
        buf.append(" - ").append(disc.getTitle());
      }
      buf.append(" - ").append(trackReference.getTrackNr());
      Track track = trackReference.getTrack();
      buf.append(" - ").append(track.getTitle());
    }
    case Artist artist -> buf.append(trackReference.getTrack().getTitle()).append(" sample for ").append(artist.getName());
    case Object object -> throw new RuntimeException("Unsupported container type: " + object.getClass().getName());
    }

    return buf.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    nameObjectControl.setObject(null);
    containerArtistObjectControl.setObject(null);
    photoObjectControl.setObject(null);
    styleObjectControl.setObject(null);
    myCommentsControl.setObject(null);
    sampleTrackObjectControl.setObject(null);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel(VBox rootPane) {
    /*
     * Edit panel is a GridPane with the artist details
     */
            
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Name: <artist-name>'   'Photo: <picture>'
    Label label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, 0);
    
    gridPane.add(nameObjectControl.getControl(), 1, 0);
    gridPane.add(nameObjectControl.getStatusIndicator(), 2, 0);;
    
    label = componentFactory.createLabel("Photo:");
    gridPane.add(label, 2, 0);
    
    gridPane.add(photoObjectControl.getControl(), 3, 0, 1, 4);
    gridPane.add(photoObjectControl.getStatusIndicator(), 4, 0);
    

    // Second row: 'Container artist: <artist>'
    label = componentFactory.createLabel("Container artist:");
    gridPane.add(label, 0, 1);
    
    gridPane.add(containerArtistObjectControl.getControl(), 1, 1);
    gridPane.add(containerArtistObjectControl.getStatusIndicator(), 2, 1);
    
    // Third row: style
    label = componentFactory.createLabel("Style:");
    gridPane.add(label, 0, 2);
    
    gridPane.add(styleObjectControl.getControl(), 1, 2);
    gridPane.add(styleObjectControl.getStatusIndicator(), 2, 2);
    
    // Fourth row: my comments
    label = componentFactory.createLabel("Comments:");
    gridPane.add(label, 0, 3);
    
    gridPane.add(myCommentsControl.getControl(), 1, 3);
    gridPane.add(myCommentsControl.getStatusIndicator(), 2, 3);
    
    // Fifth row: my comments
    label = componentFactory.createLabel("Sample:");
    gridPane.add(label, 0, 4);
    
    gridPane.add(sampleTrackObjectControl.getControl(), 1, 4);
    gridPane.add(sampleTrackObjectControl.getStatusIndicator(), 2, 4);

    rootPane.getChildren().add(gridPane);
  }
  
  private void updateContainerArtistObjectControl() {
    Artist currentSelectedArtist = containerArtistObjectControl.getValue();
    ObservableList<Artist> artists = FXCollections.observableArrayList(mediaDb.getArtists());
    artists.sort((Artist artist1, Artist artist2) -> {return artist1.getName().compareTo(artist2.getName());});
    containerArtistObjectControl.setOptions(artists);
    if (currentSelectedArtist != null) {
      containerArtistObjectControl.setObject(currentSelectedArtist);
    }
  }
  
  /**
   * Update the {@code sampleTrackObjectControl}.
   * <p>
   * The control is filled with all tracks for the artist who's name is in the {@code nameObjectControl}.
   * So it has to be updated when the value of the {@code nameObjectControl} is changed, or on any track reference change.
   */
  private void updateSampleTrackObjectControl() {
    TrackReference currentSelectedSample = sampleTrackObjectControl.getValue();
    
    trackNameToReferenceMap.clear();
    ObservableList<TrackReference> trackReferences = FXCollections.observableArrayList();
    
    Artist artistBeingEdited = null;
    if (nameObjectControl.isFilledIn()) {
      String artistName = nameObjectControl.getValue();
      artistBeingEdited = mediaDb.getArtist(artistName);
    }
    
    if (artistBeingEdited != null) {
      Iterator<TrackReference> iterator = new TrackReferencesIterator(mediaDb);
      while (iterator.hasNext()) {
        TrackReference trackReference = iterator.next();
        Track track = trackReference.getTrack();
        if (track != null) {
          if (artistBeingEdited == track.getTrackArtist()) {
            trackReferences.add(trackReference);
            trackNameToReferenceMap.put(trackReferenceToString(trackReference), trackReference);
          }
        }
      }
    }
    sampleTrackObjectControl.setOptions(trackReferences);
    
    if (currentSelectedSample != null) {
      sampleTrackObjectControl.setObject(currentSelectedSample);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createObject() {
    object = MEDIA_DB_FACTORY.createArtist();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateObjectFromControls() {
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_Name(), nameObjectControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_ContainerArtist(), containerArtistObjectControl.getValue());
    File photoFile = photoObjectControl.getValue();
    if (photoFile == null) {
      EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_Photo(), null);
    } else {
      String photoFileName = FileUtils.getPathRelativeToFolder(MediaRegistry.musicDataDirectory + "\\ArtistInformation\\Pictures\\", photoFile.getAbsolutePath());
      LOGGER.severe("photoFileName: " + photoFileName);
      EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_Photo(), photoFileName);
    }
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_Style(), styleObjectControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_MyComments(), myCommentsControl.getValue());
    TrackReference newSampleReference = sampleTrackObjectControl.getValue();
    if (newSampleReference == null) {
      EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getArtist_Sample(), null);
    } else {
      TrackReference trackReference = object.getSample();
      if (trackReference == null) {
        trackReference = MEDIA_DB_FACTORY.createTrackReference();
        object.setSample(trackReference);
      }
      
      MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
      if (myTrackInfo == null) {
        myTrackInfo = MEDIA_DB_FACTORY.createMyTrackInfo();
        trackReference.setMyTrackInfo(myTrackInfo);
      }
      
      EmfUtil.setFeatureValue(myTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_TrackReference(), newSampleReference);
    }
  }

  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsFromObject() {
    if (object == null) {
      return;
    }
    nameObjectControl.setObject(object.getName());
    containerArtistObjectControl.setObject(object.getContainerArtist());
    String photoFileName = object.getPhoto();
    if (photoFileName != null) {
      String photosFolder = MediaRegistry.musicDataDirectory + "\\ArtistInformation\\Pictures";
      File file = new File(photosFolder, photoFileName);
      if (file.exists()) {
        LOGGER.severe("OK");
      } else {
        LOGGER.severe("NOT OK");
      }
      photoObjectControl.setObject(file);
    }
    styleObjectControl.setObject(object.getStyle());
    myCommentsControl.setObject(object.getMyComments());
    TrackReference sampleTrackReference = object.getSample();
    LOGGER.severe("sampleTrackReference: " + sampleTrackReference);
    TrackReference finalTrackReference = getFinalTrackReference(sampleTrackReference);
    LOGGER.severe("finalTrackReference: " + finalTrackReference);

    sampleTrackObjectControl.setObject(finalTrackReference);
  }
  
  public static TrackReference getFinalTrackReference(TrackReference trackReference) {
    if (trackReference == null) {
      return null;
    }
    
    TrackReference nextTrackReference = null;
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo != null) {
      nextTrackReference = myTrackInfo.getTrackReference();
    }
    
    if (nextTrackReference == null) {
      return trackReference;
    } else {
      return getFinalTrackReference(nextTrackReference);
    }
  }
}

/**
 * An exception with a list messages about what is wrong in the provided artist information.
 */
@SuppressWarnings("serial")
class ArtistDetailsException extends Exception {
  private List<String> problems;
  
  ArtistDetailsException(List<String> problems) {
    this.problems = problems;
  }
  
  List<String> getProblems() {
    return problems;
  }
}

