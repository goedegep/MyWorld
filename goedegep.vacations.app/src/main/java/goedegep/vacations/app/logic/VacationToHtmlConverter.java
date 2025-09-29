package goedegep.vacations.app.logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.emf.ecore.EObject;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import goedegep.gpx.GpxUtil;
import goedegep.gpx.app.Activity;
import goedegep.gpx.app.GpxAppUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.poi.app.LocationCategory;
import goedegep.resources.ImageSize;
import goedegep.types.model.FileReference;
import goedegep.util.emf.EMFResource;
import goedegep.util.file.FileUtils;
import goedegep.util.html.HtmlUtil;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.Document;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Text;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;

/**
 * This class generates an HTML document for a Vacation.
 * <p>
 * The generation can be controlled via settings, which are passed on to the constructor.<br/>
 * The main method ({@link #vacationToHtml(vacation)}) can generate a plain HTML document
 * or a complete zip file also containing all images and documents referred to ({@code createZipFile = true}).<br/>
 * The plain file can only be used on the computer on which it was generated, because the links are to the files on that computer.<br/>
 * The zip file can be extracted anywhere (so also on a web server) and than the links will work.
 * 
 * The following settings are available:
 * <ul>
 * <li> SHOW_LOCATION_COORDINATES<br/>
 *   If set, the coordinates of locations are shown (by default they are not shown).
 * </li>
 * <li>PARAGRAPH_MODE<br/>
 *   In paragraph mode, Days are shown as paragraphs. Otherwise they are rows in a table (referred to as table mode).
 * </li>
 * </ul>
 * 
 * There are different types of images:
 * <ul>
 * <li>Pictures:<br/>
 * These are e.g. pictures taken during the vacation. They are represented by {@code Picture} elements.
 * The pictures aren't shown in their original (typically large) size. They are shown in the size determined by the {@code PhotoThumbnailManager} class.<br/>
 * When you click on a picture, the original picture is shown.<br/>
 * </li>
 * <li><b>Icons</b>
 * Location categories have icons, which are shown as part of the information of a location. These icons are small and shown with a fixed size (32 x 32).<br/>
 * The icons are contained in a jar file and have be accessed via their URL.<br/>
 * Also icons often occur more than once in the output, so it makes sense to cache them.<br/>
 * </li>
 * <ul>
 * In both cases, the images can be embedded in the HTML document (base64 encoding).
 * TODO: I think I will always embed the images. If not, it should be a setting, instead of a separate method.
 */
public class VacationToHtmlConverter extends VacationToTextConverterAbstract {
  private static final Logger LOGGER = Logger.getLogger(VacationToHtmlConverter.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  /**
   * The buffer in which the generated HTML text is stored.
   */
  private StringBuilder buf = new StringBuilder();
  
  /**
   * The parser to parse MarkDown text.
   */
  private Parser parser = Parser.builder().build();
  
  /**
   * The renderer to convert the parsed MarkDown text to HTML.
   */
  private HtmlRenderer renderer = HtmlRenderer.builder().build();
  
  /**
   * By default images use links to the files on the computer. If this flag is set to true, they are embedded in the HTML.
   */
  private boolean embedImages;
  
  /**
   * If set, a zip file is created, containing the HTML document and all related images and documents.
   */
  private boolean createZipFile;

  /**
   * If {@code createZipFile = true}, this is the path to the folder in the in-memory file system in which the vacation files are gathered.
   * From there, the zip file is created.
   */
  private Path vacationFolderPath;

  /**
   * If {@code true}, paragraph mode is used. Otherwise table mode is used.
   */
  private boolean paragraphMode;
  
  /**
   * This flag is used in table mode.<br/>
   * When the first {@code Day} element is encountered, an HTML table is started and this flag is set.
   * After all elements have been processed, if this flag is set, the table is closed.
   */
  boolean tableStarted;
  
  /**
   * This flag is used in table mode.<br/>
   * It is used to close the last row of the table.
   */
  boolean firstRow;
  
  /**
   * The picture cache, containing the thumbnails of the pictures.
   */
//  PictureCache pictureCache;  disabled for not. See if using thumbnails is fast enough.
    
  /**
   * The zip file system for the current thumbnails zip file.
   */
  private FileSystem zipfs;
  private Path vacationPhotosPath;
  
  /**
   * If {@code true}, the coordinates of locations are shown.
   */
  private boolean showLocationCoordinates;
  
  private Map<URL, String> iconUrlToBase64Map = new HashMap<>();
  
  /**
   * Constructor
   * 
   * @param vacationToHtmlConverterSettings settings used to control the generation of the HTML document.
   */
  public VacationToHtmlConverter(Set<VacationToHtmlConverterSetting> vacationToHtmlConverterSettings) {
    Objects.requireNonNull(vacationToHtmlConverterSettings, "argument vacationToHtmlConverterSettings may not be null");
    
    updateSettings(vacationToHtmlConverterSettings);
  }
  
  public void updateSettings(Set<VacationToHtmlConverterSetting> vacationToHtmlConverterSettings) {
    Objects.requireNonNull(vacationToHtmlConverterSettings, "argument vacationToHtmlConverterSettings may not be null");
    
    // Set default values.
    showLocationCoordinates = false;
    paragraphMode = false;
    
    // Update settings.
    for (VacationToHtmlConverterSetting setting: vacationToHtmlConverterSettings) {
      switch(setting) {
      case SHOW_LOCATION_COORDINATES:
        showLocationCoordinates = true;
        break;
        
      case PARAGRAPH_MODE:
        paragraphMode = true;
        break;
      }
    }
  }
  
  /**
   * Generate an HTML document for a Vacation.
   * <p>
   * The links in the document (for images, documents, etc.) are to files on the computer..
   * 
   * @param vacation the {@code Vacation} for which an HTML document is to be generated. This argument may not be null.
   * @return an HTML document for the {@code vacation}.
   * @deprecated
   */
  public String vacationToHtml(Vacation vacation) {
    return vacationToHtml(vacation, false, null);
  }
  
  /**
   * Generate an HTML document for a Vacation, with images embedded in the HTML.
   * <p>
   * Images will be embedded in the HTML, but other links are to files on the computer..
   * 
   * @param vacation the {@code Vacation} for which an HTML document is to be generated. This argument may not be null.
   * @return an HTML document for the {@code vacation}.
   */
  public String vacationToHtmlWithEmbedImages(Vacation vacation) {
    return vacationToHtml(vacation, true, null);
  }
  
  /**
   * Generate an HTML document for a Vacation, which with all related images and documents is put in a zip file.
   * <p>
   * The links in the document are to the files in the zip file.
   * 
   * @param vacation the {@code Vacation} for which an HTML document is to be generated. This argument may not be null.
   * @return an HTML document for the {@code vacation}.
   */
  public String vacationToHtmlZipFile(Vacation vacation, Path zipFile) {
    return vacationToHtml(vacation, true, zipFile);
  }
  
  /**
   * Create an HTML document for a Vacation.
   * 
   * @param vacation the <code>Vacation</code> for which an HTML document is to be generated.
   * @return the generated HTML document.
   */
  private String vacationToHtml(Vacation vacation, boolean embedImages, Path zipFile) {
    Objects.requireNonNull(vacation, "argument vacation may not be null");
    
    this.embedImages = embedImages;
    if (zipFile != null) {
      createZipFile = true;
    }
    
    // open thumbnails file.
    zipfs = null;
    if (vacationHasPhotos(vacation)) {
      String vacationPhotosFolder = determinePhotosFolder(vacation);
      if (vacationPhotosFolder != null) {
        vacationPhotosPath = Paths.get(VacationsRegistry.vacationPicturesFolderName, vacationPhotosFolder);
        Path thumbnailZipPath = vacationPhotosPath.resolve(".thumbnails.zip");
        if (Files.exists(thumbnailZipPath)) {
          try {
            URI zipFileUri = thumbnailZipPath.toUri();  // Extra step needed because URI.create(String) requires *encoded* string.
            URI uri = URI.create("jar:file:" + zipFileUri.getRawPath());
            Map<String, String> env = new HashMap<>();

            zipfs = FileSystems.newFileSystem(uri, env);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    
//    pictureCache = PictureCache.getInstance();
//    pictureCache.createThumbnails(Paths.get(vacation.getPictures()));
    
    // Set up IMFS if we're generating for web.
    FileSystem imfs = null;
    if (createZipFile) {
      imfs = Jimfs.newFileSystem(Configuration.unix());
      vacationFolderPath = imfs.getPath("/Vacation");
      try {
        Files.createDirectory(vacationFolderPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    firstRow = true;
    tableStarted = false;
    
    buf.setLength(0);  // clear the buffer as vacationToHtml may be called multiple times.
    
    buf.append("<html>");
    buf.append("<header>\n");    
    buf.append("</header>");
    
    buf.append("<body>");
    
    buf.append("<h1>");
    buf.append(HtmlUtil.encodeHTML(getVacationTitle(vacation), false));
    buf.append("</h1>");
    
    // Vacation: Notes
    if (vacation.isSetNotes()) {
      buf.append("<h2>Algemeen</h2>");
      String htmlText = markDownToHtml(vacation.getNotes());      
      buf.append(htmlText);
    }
    
    // Documents
    if (!vacation.getDocuments().isEmpty()) {
      buf.append("<h2>Documenten</h2>");
      
      for (FileReference fileReference: vacation.getDocuments()) {
        String text = getTheTextForAFileReference(fileReference);
        
        String filePathName = fileReference.getFile();
        String filename = filePathName;
        if (createZipFile  &&  filePathName != null) {
          filename = addFileToVacationFolderPath(filePathName);
        }
        
        buf.append(HtmlUtil.createLinkElement(filename, text))
        .append("<br/>");
      }
    }
    
    for (VacationElement element: vacation.getElements()) {
      vacationElementToHtml(element);
    }
    
    // If a table was started, close it.
    if (tableStarted) {
      if (!firstRow) {
        buf.append("</td></tr>");
      }
      buf.append("</table>");
    }
    
    
    buf.append("</body>");
    buf.append("</html>");
    
    if (zipfs != null) {
      try {
        zipfs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    if (createZipFile) {
      createZipFile(imfs, zipFile);
            
      // Delete the imfs.
      try {
        imfs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return buf.toString();
  }
  
  /**
   * Determine the folder in which the photos of the vacation are stored.
   * <p>
   * If the pictures attribute is set, this is returned.
   * Else, the first Picture element is searched for, and if found, and it is in any subfolder of the ..., the folder in which the picture is stored is returned.
   * 
   * @param vacation
   * @return the name of the folder (only that name, not an absolute path) in which the photos of the vacation are stored, or {@code null} if it cannot be determined.
   */
  private String determinePhotosFolder(Vacation vacation) {
    if (vacation.getPictures() != null) {
      String picturesPath = vacation.getPictures();
      File picturesFile = new File(picturesPath);
      return picturesFile.getName();
    }
    
    Iterator<EObject> iterator = vacation.eAllContents();
    while (iterator.hasNext()) {
      Object e = iterator.next();
      if (e instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String filePathName = fileReference.getFile();
          if (filePathName != null  &&  filePathName.startsWith(VacationsRegistry.vacationPicturesFolderName)) {
            String relativeFileName = FileUtils.getPathRelativeToFolder(filePathName, filePathName);
            int index = relativeFileName.indexOf(File.separator);
            String photosFolder = relativeFileName.substring(0, index);
            return photosFolder;
          }
        }
      }
    }
    
    
    return null;
  }

  /**
   * Determine if the vacation has photos.
   * 
   * @param vacation the vacation to be checked.
   * @return {@code true} if the vacation has photos, {@code false} otherwise.
   */
  private boolean vacationHasPhotos(Vacation vacation) {
    Iterator<EObject> iterator = vacation.eAllContents();
    while (iterator.hasNext()) {
      Object e = iterator.next();
      if (e instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String filePathName = fileReference.getFile();
          if (filePathName != null  &&  !filePathName.isEmpty()) {
            return true;
          }
        }
      }
    }
    
    return false;
  }

  /**
   * Generate an HTML document for a {@code Location}.
   * <p>
   * A complete HTML document is generated (including 'html', 'header' and 'body' tags).
   * 
   * @param location the {@code Location} for which an HTML document is to be generated. This argument may not be null.
   * @return an HTML document describing {@code location}.
   */
  public String LocationToHtml(Location location) {
    Objects.requireNonNull(location, "argument location may not be null");
    
    buf.setLength(0);
      
    buf.append("<html>");
    buf.append("<header>");
    buf.append("</header>");
    
    buf.append("<body>");
    
    vacationElementLocationToHtml(location);
    
    buf.append("</body>");
    buf.append("</html>");
    
    return buf.toString();
  }

  /**
   * Generate HTML text for a {@code VacationElement}.
   * <p>
   * The generated text is appended to {@code buf}.
   * 
   * @param element the {@code VacationElement} for which HTML text is to be generated.
   */
  private void vacationElementToHtml(VacationElement element) {
    switch(element) {
    case Day day -> vacationElementDayToHtml(day);
      
    case Document document -> vacationElementDocumentToHtml(document);
           
    case Location location -> vacationElementLocationToHtml(location);
      
    case Text text -> vacationElementTextToHtml(text);
      
    case Picture picture -> vacationElementPictureToHtml(picture);
      
    case GPXTrack gpxTrack -> vacationElementGPXToHtml(gpxTrack);
      
    case MapImage mapImage -> vacationElementMapImageToHtml(mapImage);
      
    default -> {}
    }
    
    for (VacationElement childElement: element.getChildren()) {
      vacationElementToHtml(childElement);
    }
    
    LOGGER.info("<=");
  }

  /**
   * Generate HTML text for a {@code Day} element.
   * <p>
   * The generated text is appended to {@code buf}.<br/>
   * The first time a {@code Day} is encountered in table mode, the day-table is created.
   * 
   * @param day the {@code Day} element for which HTML text is to be generated.
   */
  private void vacationElementDayToHtml(Day day) {
    if (paragraphMode) {
      buf.append("<h2>");
      buf.append("Dag ");
      buf.append(day.getDayNr());
      Date date = day.getDate();
      if (date != null) {
        buf.append(" (");
        buf.append(DF.format(date));
        buf.append(")");
      }
      if (day.isSetTitle()) {
        buf.append(": ");
        buf.append(day.getTitle());
      }
      buf.append("</h2>");
    } else {
      if (!tableStarted) {
        buf.append("<table border=\"1\" cellpadding=\"4\" >");
        buf.append("<colgroup><col width=\"120\" /></colgroup>");

        buf.append("<th>Dag   </th><th>Omschrijving</th>");

        tableStarted = true;
      }

      handleTableTagsBeforeDayElement();

      buf.append("Dag ");
      buf.append(day.getDayNr());
      Date date = day.getDate();
      if (date != null) {
        buf.append("<br/>");
        buf.append(DF.format(date));
      }
      if (day.isSetTitle()) {
        buf.append("<br/>");
        buf.append(day.getTitle());
      }

      handleTableTagsAfterDayElement();
    }
  }

  /**
   * Generate HTML text for a {@code Document} element.
   * <p>
   * The generated text is appended to {@code buf}.<br/>
   * 
   * @param document the {@code Document} element for which HTML text is to be generated.
   */
  private void vacationElementDocumentToHtml(Document document) {
    FileReference fileReference = document.getDocumentReference();
    if (fileReference == null  ||  fileReference.getFile() == null) {
      return;
    }
    
    String text = getTheTextForAFileReference(fileReference);

    String filePathName = fileReference.getFile();
    String filename = filePathName;
    if (createZipFile  &&  filePathName != null) {
      filename = addFileToVacationFolderPath(filePathName);
    }
    
    buf.append("<p>")
    .append("Document: ")
    .append(HtmlUtil.createLinkElement(filename, text))
    .append("</p>");
  }

  /**
   * Generate HTML text for a {code Location} element.
   * <p>
   * The generated text is appended to {@code buf}.<br/>
   * 
   * @param location the {code Location} element for which HTML text is to be generated.
   */
  private void vacationElementLocationToHtml(Location location) {
    boolean separatorNeeded = false;
    boolean newLineNeeded = false;
    
    buf.append("<p/>");
    
    if (location.isStayedAtThisLocation()) {
      buf.append("<b><i>");
      buf.append(HtmlUtil.encodeHTML("Verblijf", false));
      buf.append("</i></b><nbsp/>");
    }
    
    if (location.isSetStartDate()) {
      buf.append("van ");
      buf.append(FDF.format(location.getStartDate()));
      
      if (location.isSetEndDate()) {
        buf.append(", tot ");
        buf.append(FDF.format(location.getEndDate()));
      }
    }
    buf.append("<br/>");
    
    LocationCategory locationCategory = location.getLocationCategory();
    if (locationCategory != null) {
      URL url = locationCategory.getIconURL(ImageSize.SIZE_1);
      String encodedIcon = getBase64EncodedIcon(url);
      buf.append(HtmlUtil.createEmbeddedPictureElement(encodedIcon, getImageType(url.toString())));
      separatorNeeded = true;
//      try {
//        InputStream inputStrean = url.openStream();
//        URI uri = url.toURI();
//        Path path = Paths.get(uri);
//        addImage(buf, path, inputStrean, 32, null);
//        separatorNeeded = true;
//      } catch (IOException e) {
//        e.printStackTrace();
//      } catch (URISyntaxException e) {
//        e.printStackTrace();
//      }
    }
    
    if (location.isSetName()) {
      if (separatorNeeded) {
        buf.append(": ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getName(), false));
      separatorNeeded = true;
      newLineNeeded = true;
    }
    
    if (location.getCity() != null) {
      if (separatorNeeded) {
        buf.append(" - ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getCity(), false));
      newLineNeeded = true;
    }
    
    if (location.getStreet() != null) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      buf.append(HtmlUtil.encodeHTML(location.getStreet(), false));
      if (location.getHouseNumber() != null) {
        buf.append(" ");
        buf.append(HtmlUtil.encodeHTML(location.getHouseNumber(), false));
      }
      newLineNeeded = true;
    }
    
    if (location.isSetWebSite()) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      buf.append("Website: ");
      buf.append("<a href=\"");
      buf.append(location.getWebSite());
      buf.append("\">");
      buf.append(location.getWebSite());
      buf.append("</a>");
      newLineNeeded = true;
    }
    
    if (location.isSetDescription()) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      
      Node document = parser.parse(location.getDescription());
      String htmlText = renderer.render(document);  
      buf.append(htmlText);
      
      newLineNeeded = true;
    }
    
    if (showLocationCoordinates  &&  location.isSetLatitude()  &&  location.isSetLongitude()) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      
      if (showLocationCoordinates) {
        buf.append("Lat./Long.: ");
        buf.append(location.getLatitude());
        buf.append(",");
        buf.append(location.getLongitude());
      }
      
      newLineNeeded = true;
    }
  }

  /**
   * Generate HTML text for a <code>Text</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.<br/>
   * The <code>text</code> is interpreted as MarkDown text.
   * 
   * @param text the <code>Text</code> element for which HTML text is to be generated.
   */
  private void vacationElementTextToHtml(goedegep.vacations.model.Text text) {
    if (text.isSetText()) {
      buf.append("<p/>");
      
      Node document = parser.parse(text.getText());
      String htmlText = renderer.render(document);  
      
      buf.append(htmlText);
    }
  }

  /**
   * Generate HTML text for a {@code Picture} element.
   * <p>
   * The generated text is appended to {@code buf}.
   * 
   * @param picture the {@code Picture} element for which HTML text is to be generated.
   */
  private void vacationElementPictureToHtml(Picture picture) {
    FileReference pictureReference = picture.getPictureReference();
    
    // If there is no picture reference, we can't show the picture.
    if (pictureReference == null) {
      return;
    }

    String pictureFileName = pictureReference.getFile();
    
    // If there is no picture file name, we can't show the picture.
    if ((pictureFileName == null)  ||  pictureFileName.isEmpty()) {
      return;
    }

    buf.append("<p>");
    if (createZipFile) {
      addFileToVacationFolderPath(pictureFileName);
    }
    
    Path picturePath = Paths.get(pictureFileName);  // by default, assume it's a normal file.
    if (zipfs != null) {
      // See if there is a thumbnail in the thumbnails zip file. If so, use that.
      String thumbFileName = FileUtils.getPathRelativeToFolder(vacationPhotosPath.toString(), pictureFileName);
      thumbFileName = thumbFileName.replaceAll("\\.jpg$", "_thumb.jpg");
      Path pathInZipfile = zipfs.getPath(thumbFileName);
      if (Files.exists(pathInZipfile)) {
        picturePath = pathInZipfile;
      }
    }

    buf.append("<a href=\"");
    buf.append(HtmlUtil.encodeHTML(pictureFileName, true));
    buf.append("\">");
    buf.append("<figure>");
    String caption = VacationsUtils.getPictureCaption(picture);
    addImage(buf, picturePath, 250, caption);
    //      addImage(buf, picturePath.toUri().toString(), 250, caption);
    buf.append("</figure>");   
    buf.append("</a>");
    buf.append("</p>");
  }

  /**
   * Generate HTML text for a <code>GPXTrack</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param gpxTrack the <code>GPXTrack</code> element for which HTML text is to be generated.
   */
  private void vacationElementGPXToHtml(GPXTrack gpxTrack) {
    if (gpxTrack.isSetTrackReference()) {
      buf.append("<p/>");

      FileReference trackReference = gpxTrack.getTrackReference();
      if ((trackReference != null)  &&  (trackReference.getFile() != null)  &&  !trackReference.getFile().isEmpty()) {
        
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(trackReference.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          URL iconUrl = goedegep.resources.ImageResource.GPX.getImageUrl();
          GpxType gpxType = documentRoot.getGpx();
          Activity activity = GpxAppUtil.getActivity(gpxType);
          if (activity != null) {
            iconUrl = activity.getIconUrl(ImageSize.SIZE_1);
            if (iconUrl == null) {
              LOGGER.severe("Stop here");
              iconUrl = activity.getIconUrl(ImageSize.SIZE_1);
            }
          }
          
//          addImage(buf, iconUrl.toString(), 32, null);

          String name = null;
          MetadataType metadataType = gpxType.getMetadata();
          if (metadataType != null) {
            name = metadataType.getName();
          }
          if (name != null  && !name.isEmpty()) {
            buf.append(HtmlUtil.encodeHTML(name, false));
          } else {
            FileReference fileReference = gpxTrack.getTrackReference();
            if (fileReference != null) {
              String title = fileReference.getTitle();
              if (title != null  &&  !title.isEmpty()) {
                buf.append(HtmlUtil.encodeHTML(title, false));
              } else {
                buf.append("file: ").append(trackReference.getFile());
              }
            }
          }
          double length = gpxType.getLength();
          double cumulativeAscent = gpxType.getCumulativeAscent();
          double cumulativeDescent = gpxType.getCumulativeDescent();
          if (length > 0.01) {
            buf.append(", lengte ");
            buf.append(String.format("%1$.1f", length/1000d));
            buf.append("km");
          }
          if (cumulativeAscent > 20) {
            buf.append(", totale stijging ");
            buf.append(String.format("%1$.0f", cumulativeAscent));
            buf.append("m");
          }
          if (cumulativeDescent > 20) {
            buf.append(", totale daling ");
            buf.append(String.format("%1$.0f", cumulativeDescent));
            buf.append("m");
          }
          
          buf.append("<br/>");
          
          if (metadataType != null) {
            String description = metadataType.getDesc();
            if ((description != null)  &&  !description.isEmpty()) {
              buf.append(description);
              buf.append("<br/>");
            }
          }
          
        } catch (IOException e) {
          e.printStackTrace();
        }
      }      
    }
  }

  /**
   * Generate HTML text for a <code>MapImage</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param picture the <code>Picture</code> element for which HTML text is to be generated.
   */
  private void vacationElementMapImageToHtml(MapImage mapImage) {
//    vacationsWindow.createMapImageFile(mapImage, poiIcons);
    Path picturePath = Paths.get(mapImage.getFileName());
    buf.append("<a href=\"");
    buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString(), true));
    buf.append("\">");
    buf.append("<figure>");
    buf.append("<img src=\"");
    buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString(), true));
    buf.append("\" >");
    if (mapImage.getTitle() != null) {
      buf.append("<figcaption>");
      buf.append(HtmlUtil.encodeHTML(mapImage.getTitle(), false));
      buf.append("</figcaption>");
    }
    buf.append("</img>");
    buf.append("</figure>");   
    buf.append("</a>");
  }
  
  /**
   * Convert MarkDown text to HTML text.
   * 
   * @param markDownText the MarkDown text to be converted. This argument may not be null.
   * @return the converted HTML text.
   */
  private String markDownToHtml(String markDownText) {
    Node document = parser.parse(markDownText);
    String htmlText = renderer.render(document); 
    
    return htmlText;
  }
  
  /**
   * Get the text for a file reference.
   * <p>
   * If the title is set, that is returned. Otherwise the file name is returned.<br/>
   * Note that a file reference shall always have the file attribute set.
   * 
   * @param fileReference
   * @return the text for the file reference.
   */
  private String getTheTextForAFileReference(FileReference fileReference) {
    String text = fileReference.getTitle();
    if (text == null) {
      text = fileReference.getFile();
    }
    return text;
  }
  
  /**
   * Add a file to the vacation folder path in the imfs file system.
   * 
   * @param filePathName the path name of the file to be added. This argument may not be null.
   */
  private String addFileToVacationFolderPath(String filePathName) {
    File file = new File(filePathName);
    String filename = file.getName();
    try {
      Path destinationPath = vacationFolderPath.resolve(filename);
      if (!Files.exists(destinationPath)) {
        Files.copy(Paths.get(filePathName), destinationPath);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return filename;
  }
  
  /**
   * Create a zip file from the imfs folder.
   * <p>
   * First the HTML document is written to a file in the imfs file system, then a zip file is created from the imfs folder.
   * 
   * @param imfs the in-memory file system containing the vacation folder path. This argument may not be null.
   * @param zipFile the path to the zip file to be created. This argument may not be null.
   */
  private void createZipFile(FileSystem imfs, Path zipFile) {
    try {
      // Write the HTML document to a file in the imfs file system.
      String htmlFileName = "Vacation.html";
      Path htmlFilePath = vacationFolderPath.resolve(htmlFileName);
      Files.write(htmlFilePath, buf.toString().getBytes(StandardCharsets.UTF_8));
      
      // Create a zip file from the imfs folder
      Path folderToZip = imfs.getPath("/");
      FileUtils.createZipFileForFolder(zipFile, folderToZip);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handle table tags before a <code>Day</code> element.
   * <p>
   * When a new day element is encountered, a new row in the table is started.
   * This method writes the related HTML tags to <code>buf</code>.
   */
  private void handleTableTagsBeforeDayElement() {
    if (!tableStarted) {
      return;
    }

    if (firstRow) {
      firstRow = false;
    } else {
      // close previous second column data and row.
      buf.append("</td></tr>");
    }
    // open new row and first column data.
    buf.append("<tr><td>");
  }
  
  /**
   * Handle table tags after a <code>Day</code> element.
   * <p>
   * This method closes the first column 'dt' element and opens the second column 'dt' element.
   */
  private void handleTableTagsAfterDayElement() {
    if (!tableStarted) {
      return;
    }
    
    // close first column data, open second column data
    buf.append("</td><td>");
  }
  
  /**
   * Add the 'img' part for an image
   * 
   * @param buf the {@code StringBuilder} to add the text to.
   * @param urlString URL of the image.
   * @param height maximum image height (to be implemented).
   * @param caption the image caption.
   * @param generateForWeb if {@code true} information for deploying on a website is generated.
   */
  private void addImage(StringBuilder buf, Path path, int height, String caption) {
    String resizedFile = null;
    
    buf.append("<img src=\"");
    if (!embedImages) {
      if (createZipFile && path != null) {
        String absoluteFileName = path.toString().substring(5);
        while (absoluteFileName.startsWith("/")) {
          absoluteFileName = absoluteFileName.substring(1);
        }
        absoluteFileName = absoluteFileName.replaceAll("%20", " ");
        File file = new File(absoluteFileName);
        String filename = file.getName();
        if (absoluteFileName.startsWith("/")) {
          LOGGER.severe("Illegal start: " + absoluteFileName);
        }
        try {
          Path destinationPath = vacationFolderPath.resolve(filename);
          if (!Files.exists(destinationPath)) {
            Files.copy(Paths.get(absoluteFileName), destinationPath);
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        buf.append(HtmlUtil.encodeHTML(filename, true));
      } else {
        buf.append(HtmlUtil.encodeHTML(path.toString(), true));
      }
      
    } else {
//      InputStream inputStream;
//      try {
//        inputStream = Files.newInputStream(path);
        buf.append("data:image/")
        .append(getImageType(path.toString()))
        .append(";base64,")
        .append(createBase64EncodedImage(path));
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
    }
    
//    buf.append("\" height=\"")
//    .append(height)
    buf.append("\" >");
    if (caption != null) {
      buf.append("<figcaption>");
      buf.append(HtmlUtil.encodeHTML(caption, false));
      buf.append("</figcaption>");
    }
    buf.append("</img>");
    if (resizedFile != null) {
      try {
        Files.delete(Paths.get(resizedFile));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
//  private String createResizedImageFile(String urlString, int height) {
//    // TODO Auto-generated method stub
//    return null;
//  }

//  private boolean imageHasToBeResized(Path path, int height) {
//    // TODO Auto-generated method stub
//    return false;
//  }

//  private Object getBase64EncodedImage(String urlString, int height, int width) {
//    try {
//      byte[] fileContent = IOUtils.toByteArray((new URL(urlString)).openStream());
//      String encodedString = Base64.getEncoder().encodeToString(fileContent);
//      return encodedString;
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    
//    return null;
//  }

  /**
   * Get the base64 encoded image.
   * <p>
   * The image is read from the file system.
   * 
   * @param path the path to the image file.
   * @param height maximum image height (to be implemented).
   * @param width maximum image width (to be implemented).
   * @return the base64 encoded image, or {@code null} if the image could not be read.
   */
  private Object createBase64EncodedImage(Path path) {
//    URI uri = path.toUri();
//    URL url;
//    try {
//      url = uri.toURL();
//      Files.stream
//      InputStream input = url.openStream();
//      byte[] fileContent = IOUtils.toByteArray(input);
//      String encodedString = Base64.getEncoder().encodeToString(fileContent);
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
    try {
      byte[] fileContent = IOUtils.toByteArray(Files.newInputStream(path));
      String encodedString = Base64.getEncoder().encodeToString(fileContent);
      return encodedString;
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * Get the base64 encoded image.
   * <p>
   * The image is read from the file system.
   * 
   * @param path the path to the image file.
   * @param height maximum image height (to be implemented).
   * @param width maximum image width (to be implemented).
   * @return the base64 encoded image, or {@code null} if the image could not be read.
   */
  private String getBase64EncodedImage(InputStream inputStream, int height, int width) {
    try {
      BufferedImage originalImage = ImageIO.read(inputStream);
      Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = resizedImage.createGraphics();
      g2d.drawImage(scaledImage, 0, 0, null);
      g2d.dispose();
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      ImageIO.write(resizedImage, "png", baos);
      byte[] fileContent = baos.toByteArray();
      String encodedString = Base64.getEncoder().encodeToString(fileContent);
      return encodedString;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private String getBase64EncodedIcon(URL url) {
    if (iconUrlToBase64Map.containsKey(url)) {
      return iconUrlToBase64Map.get(url);
    }
    
    String base64EncodedIcon = createBase64EncodedIcon(url);
    iconUrlToBase64Map.put(url, base64EncodedIcon);
    
    return base64EncodedIcon;
  }
  
  /**
   * Create a base64 encoded icon for a URL.
   * <p>
   * Icon images may also have different sizes, so we convert them to a fixed height (32).
   * @return
   */
  private String createBase64EncodedIcon(URL url) {
    int height = 32;
    try {
      InputStream inputStream = url.openStream();
      BufferedImage originalImage = ImageIO.read(inputStream);
      
      int originalHeight = originalImage.getHeight();
      int originalWidth = originalImage.getWidth();
      
      int width = (int) ((double) height * originalWidth / originalHeight);
      
      Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = resizedImage.createGraphics();
      g2d.drawImage(scaledImage, 0, 0, null);
      g2d.dispose();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(resizedImage, "png", outputStream);
      byte[] fileContent = outputStream.toByteArray();
      String encodedString = Base64.getEncoder().encodeToString(fileContent);
      return encodedString;
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  private String getImageType(String urlString) {
    if (urlString.endsWith(".jpg")) {
      return "jpg";
    } else if (urlString.endsWith(".png")) {
      return "png";
    } else {
      return null;
    }
  }
}

enum Mode {
  STANDARD,
  EMBED_IMAGES,
  CREATE_ZIP_FILE;
}
