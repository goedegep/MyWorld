package goedegep.media.mediadb.app.guifx;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * This class provides a table to list {@link Artist}s.
 */
public class ArtistsTable extends EObjectTable<Artist> {

  /**
   * Constructor
   * 
   * @param customization  the GUI customization.
   * @param mediaDb the media database from which the artists are listed
   */
  public ArtistsTable(CustomizationFx customization, MediaDb mediaDb) {
    super(customization, MediadbPackage.eINSTANCE.getArtist(), new ArtistsTableDescriptor(customization), mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Artists());
    
    setTableMenuButtonVisible(true);
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
  }

}


/**
 * This class is the table descriptor for the TracksTable.
 */
class ArtistsTableDescriptor extends EObjectTableDescriptor<Artist> {
  private static final int IMAGE_HEIGHT = 60;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  /*
   * Column descriptors which have to be adapted in the constructor.
   */
  private EObjectTableColumnDescriptorCustom<Artist> photoColumnDescriptor = new EObjectTableColumnDescriptorCustom<Artist>(MEDIA_DB_PACKAGE.getArtist_Photo(), "Photo", null, false, true, null);
  
  /*
   * The complete list of column descriptors
   */
  private List<EObjectTableColumnDescriptorAbstract<Artist>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Artist>>of(
      new EObjectTableColumnDescriptorBasic<Artist>(MEDIA_DB_PACKAGE.getArtist_Name(), "Name", true, true),
      photoColumnDescriptor,
      new EObjectTableColumnDescriptorBasic<Artist>(MEDIA_DB_PACKAGE.getArtist_Style(), "Style", true, true),
      new EObjectTableColumnDescriptorBasic<Artist>(MEDIA_DB_PACKAGE.getArtist_MyComments(), "Comments", true, true),
      new EObjectTableColumnDescriptorBasic<Artist>(MEDIA_DB_PACKAGE.getArtist_Sample(), "Sample", 300, true, true, new SampleStringConverter())
//      new EObjectTableColumnDescriptorCustom<Track>(MEDIA_DB_PACKAGE.getTrack_OriginalDisc(), "Album", null, false, true, column -> {
//        TableCell<Track, Object> cell = new TableCell<>() {
//
//          @Override
//          protected void updateItem(Object item, boolean empty) {
//            super.updateItem(item, empty);
//            if(empty || (item == null)) {
//              setText(null);
//            }
//            else {
//              StringBuilder buf = new StringBuilder();
//              Disc disc = (Disc) item;
//              Album album = disc.getAlbum();
//              Artist artist = album.getArtist();
//              if (artist != null) {
//                buf.append(artist.getName());
//              } else {
//                buf.append("<no artist>");
//              }
//              buf.append(" - ");
//              buf.append(album.getTitle());
//              
//              setText(buf.toString());
//            }
//          }
//        };
//
//        return cell;
//      }),
//      new EObjectTableColumnDescriptorCustom<Track>(null, "File", null, false, true, column -> {
//        TableCell<Track, Object> cell = new TableCell<>() {
//
//          @Override
//          protected void updateItem(Object item, boolean empty) {            
//            super.updateItem(item, empty);
//            if(empty || (item == null)) {
//              setText(null);
//            }
//            else {
//              Track track = (Track) item;
//              Path path = trackDiscLocationMap.get(track);
//              if (path != null) {
//                setText(path.toString());
//              } else {
//                setText(null);
//              }
//            }
//          }
//        };
//
//        return cell;
//      })
  );
  
//  @SuppressWarnings("serial")
//  private static Map<Operation, TableRowOperationDescriptor<Track>> rowOperations = new HashMap<>() {
//    {
//      put(Operation.OPEN, new TableRowOperationDescriptor<>("Play"));
//      put(Operation.NEW_OBJECT, new TableRowOperationDescriptor<>("New track"));
//      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete track"));
//    }
//  };
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  ArtistsTableDescriptor(CustomizationFx customization) {
    super();
    
    photoColumnDescriptor.setCellFactory(new ImageCellFactory(customization, IMAGE_HEIGHT, "D:\\Database\\Muziek\\ArtistInformation\\Pictures"));
     
    setComparator(new ArtistComparator());
    setColumnDescriptors(columnDescriptors);
  }
}

/**
 * A Comparator to sort the artists.
 */
class ArtistComparator implements Comparator<Artist> {

  @Override
  public int compare(Artist artist1, Artist artist2) {
    return artist1.getName().compareTo(artist2.getName());
  }
  
}


/**
 * A factory to create an artist photo cell.
 */
class ImageCellFactory implements Callback<TableColumn<Artist, Object>, TableCell<Artist, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  private String artistPhotoDirectory;
  
  public ImageCellFactory(CustomizationFx customization, int imageHeight, String artistPhotoDirectory) {
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.artistPhotoDirectory = artistPhotoDirectory;
  }

  @Override
  public TableCell<Artist, Object> call(TableColumn<Artist, Object> arg0) {
    return new ImageCell(customization, imageHeight, artistPhotoDirectory);
  }
  
}

/**
 * A table cell for an artist photo.
 */
class ImageCell extends TableCell<Artist, Object> {
  private static final Logger LOGGER = Logger.getLogger(ImageCell.class.getName());
  
  private int imageHeight;
  private String artistPhotoDirectory;

  public ImageCell(CustomizationFx customization, int imageHeight, String artistPhotoDirectory) {
    LOGGER.info("<=>");
    this.imageHeight = imageHeight;
    this.artistPhotoDirectory = artistPhotoDirectory;
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
  }
  
  @Override
  protected void updateItem(Object item, boolean empty) {
    LOGGER.info("=> item=" + (item != null ? item.toString() : "(null)") + ", empty=" + empty);
    super.updateItem(item, empty);
    setText(null);
    if (empty  ||  (item == null)) {
      setGraphic(null);
      setTooltip(null);
    } else {
      if (item instanceof String photoFileName) {
        Path imagePath = Paths.get(artistPhotoDirectory, photoFileName);
        LOGGER.info("Loading image: " + imagePath.toAbsolutePath().toString());
        Image image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, imageHeight,  true, true);                
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
      } else {
        throw new IllegalArgumentException("item shall be a String, but is of type '" + item.getClass().getName() + "'");
      }
    }
    LOGGER.info("<=");
  }
}

class SampleStringConverter extends StringConverter<TrackReference> {

  @Override
  public String toString(TrackReference trackReference) {
    Track track = trackReference != null ? trackReference.getTrack() : null;
    return track != null ? track.getTitle() : null;
  }

  @Override
  public TrackReference fromString(String string) {
    // Not needed here
    return null;
  }
  
}



