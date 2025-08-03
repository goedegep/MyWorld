package goedegep.vacations.app.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

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
import goedegep.util.text.Indent;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.Document;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class generates an HTML document for a Vacation.
 */
public class VacationToHtmlConverter extends VacationToTextConverterAbstract {
  private static final Logger LOGGER = Logger.getLogger(VacationToHtmlConverter.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  private StringBuilder buf = new StringBuilder();
  private Parser parser = Parser.builder().build();
  private HtmlRenderer renderer = HtmlRenderer.builder().build();
  
  private boolean embedImages = false;
  
  /* For exporting a website zip file (first try, all files in same folder):
   *   Images: copy the image to the imfs, replace URL with just the filename
   *   File reference: same as image
   * Name the html file 'Vacation'
   * Put everything in a zip file called Vacation.zip
   */
  private Path vacationFolderPath;


  // For creating the table.
  // If the element is a Day:
  //     If !firstColumn:
  //         if firstRow, firstRow = false, else write </td></tr>
  //         write <tr><td>, firstColumn = true
  // If the element is not a Day:
  //     If firstColumn: write </td><td>, firstColumn = false
  
  // A table is started when the first 'day' element is encountered.
  boolean tableStarted;
  boolean firstRow = true;
  Indent indent;
  
  /**
   * Constructor
   * 
   * @param poiIcons the object to provide POI icons.
   */
  public VacationToHtmlConverter() {
  }
  
  /**
   * Create an HTML document for a Vacation.
   * 
   * @param vacation the <code>Vacation</code> for which an HTML document is to be generated.
   * @return the generated HTML document.
   */
  public String vacationToHtml(Vacation vacation, boolean generateForWeb) {
    LOGGER.info("=>");
    
    // Set up IMFS if we're generating for web.
    FileSystem imfs = null;
    if (generateForWeb) {
      
      // create/get an in memory file system (imfs)
      // Create an in-memory file system
      imfs = Jimfs.newFileSystem(Configuration.unix());
      vacationFolderPath = imfs.getPath("/Vacation");
      try {
        Files.createDirectory(vacationFolderPath);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    firstRow = true;
    tableStarted = false;
    buf.setLength(0);
    indent = new Indent(4);
    
    if (vacation == null) {
      throw new IllegalArgumentException("argument vacation may not be null");
    }
    
    buf.append("<html>");
    buf.append("<header>\n");
    
    buf.append("</header>");
    
    buf.append("<body>");
    
    buf.append("<h1>");
    buf.append(HtmlUtil.encodeHTML(getVacationTitle(vacation)));
    buf.append("</h1>");
    
    // Vacation: Notes
    if (vacation.isSetNotes()) {
      buf.append("<h2>Algemeen</h2>");
      Node document = parser.parse(vacation.getNotes());
      String htmlText = renderer.render(document);  
      
      buf.append(htmlText);
    }
    
    // Documents
    if (!vacation.getDocuments().isEmpty()) {
      buf.append("<h2>Documenten</h2>");
      
      for (FileReference fileReference: vacation.getDocuments()) {
        String text = fileReference.getTitle();
        if (text == null) {
          text = fileReference.getFile();
        }
        
        String filename = fileReference.getFile();
        if (generateForWeb && filename != null) {
          File file = new File(filename);
          filename = file.getName();
          try {
            Path destinationPath = vacationFolderPath.resolve(filename);
            if (!Files.exists(destinationPath)) {
              Files.copy(Paths.get(fileReference.getFile()), destinationPath);
            }
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        buf.append("<a href=\"")
        .append(filename)
        .append("\">")
        .append(text)
        .append("</a><br/>");
      }
    }
    
    for (VacationElement element: vacation.getElements()) {
      vacationElementToHtml(element, generateForWeb);
    }
    
    if (tableStarted) {
      if (!firstRow) {
        buf.append("</td></tr>");
      }
      buf.append("</table>");
    }
    
    
    buf.append("</body>");
    buf.append("</html>");
    
    if (generateForWeb) {
      
      // Create a zip file from the imfs folder
      Path zipFile = Paths.get("D:\\Database\\Vakantie\\Vakantie.zip");
      Path folderToZip = imfs.getPath("/");
      try {
        FileUtils.createZipFileForFolder(zipFile, folderToZip);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      // Delete the imfs folder
      try {
        imfs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
//    LOGGER.severe("<= " + buf.toString());
    return buf.toString();
  }
  
  /**
   * Generate an HTML document for a <code>Location</code>.
   * <p>
   * A complete HTML document is generated (including 'html', 'header' and 'body'tags).
   * 
   * @param location the <code>Location</code> for which an HTML document is to be generated. This argument may not be null.
   * @return an HTML document describing <code>location</code>.
   */
  public String LocationToHtml(Location location, boolean generateForWeb) {
    buf.setLength(0);

    if (location == null) {
      throw new IllegalArgumentException("argument location may not be null");
    }
      
    buf.append("<html>");
    buf.append("<header>");
    buf.append("</header>");
    
    buf.append("<body>");
    
    vacationElementLocationToHtml(location, generateForWeb);
    
    buf.append("</body>");
    buf.append("</html>");
    
    return buf.toString();
  }

  /**
   * Generate HTML text for a <code>VacationElement</code>.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param element the <code>VacationElement</code> for which HTML text is to be generated.
   */
  private void vacationElementToHtml(VacationElement element, boolean generateForWeb) {
    indent.increment();
    LOGGER.info("=> element=" + indent.toString() + element.toString());
    LOGGER.info("      type=" + indent.toString() + element.eClass().getName());
    
    switch(element.eClass().getClassifierID()) {
    case VacationsPackage.DAY:
      vacationElementDayToHtml((Day) element);
      break;
      
    case VacationsPackage.DOCUMENT:
      vacationElementDocumentToHtml((Document) element);
      break;
           
    case VacationsPackage.LOCATION:
      vacationElementLocationToHtml((Location) element, generateForWeb);
      break;
      
    case VacationsPackage.TEXT:
      vacationElementTextToHtml((goedegep.vacations.model.Text) element);
      break;
      
    case VacationsPackage.PICTURE:
      vacationElementPictureToHtml((Picture) element, generateForWeb);
      break;
      
    case VacationsPackage.GPX_TRACK:
      vacationElementGPXToHtml((GPXTrack) element, generateForWeb);
      break;
      
    case VacationsPackage.MAP_IMAGE:
      vacationElementMapImageToHtml((MapImage) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      vacationElementToHtml(childElement, generateForWeb);
    }
    
    indent.decrement();
    LOGGER.info("<=");
  }

  /**
   * Generate HTML text for a <code>Day</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.<br/>
   * The first time a <code>Day</code> is encountered, the day-table is created.
   * 
   * @param day the <code>Day</code> element for which HTML text is to be generated.
   */
  private void vacationElementDayToHtml(Day day) {
    // If a 'day' element is encountered, a table is used.
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

  private void vacationElementDocumentToHtml(Document document) {
    FileReference fileReference = document.getDocumentReference();
    if (fileReference != null  &&  fileReference.getFile() != null) {
      File file = new File(fileReference.getFile());
      String filename = file.getName();
      String text = fileReference.getTitle();
      if (text == null) {
        text = filename;
      }
      buf.append("<p>").append("Document: ");
      buf.append("<a href=\"")
      .append(filename)
      .append("\">")
      .append(text)
      .append("</a></p>");
    }
  }

  /**
   * Generate HTML text for a <code>Location</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.<br/>
   * 
   * @param location the <code>Location</code> element for which HTML text is to be generated.
   */
  private void vacationElementLocationToHtml(Location location, boolean generateForWeb) {
    boolean separatorNeeded = false;
    boolean newLineNeeded = false;
    
    buf.append("<p/>");
    
    if (location.isStayedAtThisLocation()) {
      buf.append("<b><i>");
      buf.append(HtmlUtil.encodeHTML("Verblijf"));
      buf.append("</i></b><nbsp/>");
    }
    
    if (location.isSetStartDate()) {
      buf.append("van ");
      buf.append(FDF.format(location.getStartDate()));
      
      if (location.isSetEndDate()) {
        buf.append(", tot ");
        buf.append(FDF.format(location.getEndDate())).append("<br/>");
      }
    } else {
      buf.append("<br/>");
    }
    
    if (location.getLocationCategory() != null) {
      LocationCategory poiCategoryId = location.getLocationCategory();
      URL url = poiCategoryId.getIconURL();
      if (url != null) {
        addImage(buf, url.toString(), 32, null, generateForWeb);
        separatorNeeded = true;
      } else {
        LOGGER.severe("No icon for POICategoryId: " + poiCategoryId);
      }
    }
    
    if (location.isSetName()) {
      if (separatorNeeded) {
        buf.append(": ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getName()));
      separatorNeeded = true;
      newLineNeeded = true;
    }
    
    if (location.getCity() != null) {
      if (separatorNeeded) {
        buf.append(" - ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getCity()));
      newLineNeeded = true;
    }
    
    if (location.getStreet() != null) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      buf.append(HtmlUtil.encodeHTML(location.getStreet()));
      if (location.getHouseNumber() != null) {
        buf.append(" ");
        buf.append(HtmlUtil.encodeHTML(location.getHouseNumber()));
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
    
    if (VacationsRegistry.showCoordinatesInDocument  &&  location.isSetLatitude()  &&  location.isSetLongitude()) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      
      buf.append("Lat./Long.: ");
      buf.append(location.getLatitude());
      buf.append(",");
      buf.append(location.getLongitude());
      
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
   * Generate HTML text for a <code>Picture</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param picture the <code>Picture</code> element for which HTML text is to be generated.
   */
  private void vacationElementPictureToHtml(Picture picture, boolean generateForWeb) {
    if (picture.isSetPictureReference()) {
      FileReference pictureReference = picture.getPictureReference();
      String pictureFileName = pictureReference.getFile();
      if ((pictureFileName == null)  ||  pictureFileName.isEmpty()) {
        return;
      }
      
//      String filename = fileReference.getFile();
//      if (generateForWeb && filename != null) {
//        File file = new File(filename);
//        filename = file.getName();
//        try {
//          Path destinationPath = vacationFolderPath.resolve(filename);
//          Files.copy(Paths.get(fileReference.getFile()), destinationPath);
//        } catch (IOException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//      }
//      buf.append("<a href=\"")
//      .append(filename)
//      .append("\">")
//      .append(text)
//      .append("</a><br/>");
      
      
      Path picturePath = Paths.get(pictureFileName);
      
      buf.append("<a href=\"");
      buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString()));
      buf.append("\">");
      buf.append("<figure>");
      String caption = VacationsUtils.getPictureCaption(picture);
      addImage(buf, picturePath.toUri().toString(), 250, caption, generateForWeb);
      buf.append("</figure>");   
      buf.append("</a>");
    }
  }

  /**
   * Generate HTML text for a <code>GPXTrack</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param gpxTrack the <code>GPXTrack</code> element for which HTML text is to be generated.
   */
  private void vacationElementGPXToHtml(GPXTrack gpxTrack, boolean generateForWeb) {
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
          
          addImage(buf, iconUrl.toString(), 32, null, generateForWeb);

          String name = null;
          MetadataType metadataType = gpxType.getMetadata();
          if (metadataType != null) {
            name = metadataType.getName();
          }
          if (name != null  && !name.isEmpty()) {
            buf.append(HtmlUtil.encodeHTML(name));
          } else {
            FileReference fileReference = gpxTrack.getTrackReference();
            if (fileReference != null) {
              String title = fileReference.getTitle();
              if (title != null  &&  !title.isEmpty()) {
                buf.append(HtmlUtil.encodeHTML(title));
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
          
        } catch (FileNotFoundException e) {
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
    buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString()));
    buf.append("\">");
    buf.append("<figure>");
    buf.append("<img src=\"");
    buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString()));
    buf.append("\" >");
    if (mapImage.getTitle() != null) {
      buf.append("<figcaption>");
      buf.append(HtmlUtil.encodeHTML(mapImage.getTitle()));
      buf.append("</figcaption>");
    }
    buf.append("</img>");
    buf.append("</figure>");   
    buf.append("</a>");
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
  private void addImage(StringBuilder buf, String urlString, int height, String caption, boolean generateForWeb) {
    String resizedFile = null;
    if (imageHasToBeResized(urlString, height)) {
      resizedFile = createResizedImageFile(urlString, height);
      urlString = resizedFile;
    }
    
    buf.append("<img src=\"");
    if (!embedImages) {
      if (generateForWeb && urlString != null) {
        String absoluteFileName = urlString.substring(5);
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
        buf.append(HtmlUtil.encodeHTML(filename));
      } else {
        buf.append(HtmlUtil.encodeHTML(urlString));
      }
      
    } else {
      buf.append("data:image/")
      .append(getImageType(urlString))
      .append(";base64,")
      .append(getBase64EncodedImage(urlString, 32, 32));
    }
    buf.append("\" height=\"")
    .append(height)
    .append("\" >");
    if (caption != null) {
      buf.append("<figcaption>");
      buf.append(HtmlUtil.encodeHTML(caption));
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
  
  private String createResizedImageFile(String urlString, int height) {
    // TODO Auto-generated method stub
    return null;
  }

  private boolean imageHasToBeResized(String urlString, int height) {
    // TODO Auto-generated method stub
    return false;
  }

  private Object getBase64EncodedImage(String urlString, int height, int width) {
    try {
      byte[] fileContent = IOUtils.toByteArray((new URL(urlString)).openStream());
//      byte[] fileContent = FileUtils.readFileToByteArray(new File(urlString));
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
