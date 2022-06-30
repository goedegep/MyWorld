package goedegep.vacations.app.logic;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.gluonhq.maps.GPXLayer;
import com.gluonhq.maps.MapView;

import goedegep.appgen.swing.DefaultCustomization;
import goedegep.gluonmaps.gpx.GPXFile;
import goedegep.gluonmaps.gpx.GPXMeasurable;
import goedegep.gpx.parser.model.GPX;
import goedegep.gpx.parser.model.Metadata;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.types.model.FileReference;
import goedegep.util.file.FileUtils;
import goedegep.util.html.HtmlUtil;
import goedegep.util.text.Indent;
import goedegep.vacations.app.guifx.ActivityIcons;
import goedegep.vacations.app.guifx.MapRelatedItemsLayer;
import goedegep.vacations.app.guifx.VacationsWindow;
import goedegep.vacations.model.ActivityLabel;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsPackage;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

/**
 * This class generates an HTML document for a Vacation.
 */
public class VacationToHtmlConverter extends VacationToTextConverterAbstract {
  private static final Logger LOGGER = Logger.getLogger(VacationToHtmlConverter.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  private POIIcons poiIcons;
  private VacationsWindow vacationsWindow;
  private ActivityIcons activityIcons = new ActivityIcons();
  private StringBuilder buf = new StringBuilder();
  private Parser parser = Parser.builder().build();
  private HtmlRenderer renderer = HtmlRenderer.builder().build();


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
  public VacationToHtmlConverter(POIIcons poiIcons, VacationsWindow vacationsWindow) {
    this.poiIcons = poiIcons;
    this.vacationsWindow = vacationsWindow;
  }
  
  /**
   * Create an HTML document for a Vacation.
   * 
   * @param vacation the <code>Vacation</code> for which an HTML document is to be generated.
   * @return the generated HTML document.
   */
  public String vacationToHtml(Vacation vacation) {
    LOGGER.info("=>");
    
    firstRow = true;
    tableStarted = false;
    buf.setLength(0);
    indent = new Indent(4);
    
    if (vacation == null) {
      throw new IllegalArgumentException("argument vacation may not be null");
    }
    
    buf.append("<html>");
    buf.append("<header>");
    buf.append("</header>");
    
    buf.append("<body>");
    buf.append("<h1>");
    buf.append(HtmlUtil.encodeHTML(getVacationTitle(vacation)));
    buf.append("</h1>");
    
    // Vacation: Algemeen
    if (vacation.isSetNotes()) {
      Node document = parser.parse(vacation.getNotes());
//      printCommonMarkNode(document);
      String htmlText = renderer.render(document);  
      
      buf.append(htmlText);
    }
    
    for (VacationElement element: vacation.getElements()) {
      vacationElementToHtml(element);
    }
    
    if (tableStarted) {
      if (!firstRow) {
        buf.append("</td></tr>");
      }
      buf.append("</table>");
    }
    
    
    buf.append("</body>");
    buf.append("</html>");
    
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
  public String LocationToHtml(Location location) {
    buf.setLength(0);

    if (location == null) {
      throw new IllegalArgumentException("argument location may not be null");
    }
      
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
   * Generate HTML text for a <code>VacationElement</code>.
   * <p>
   * The generated text is appended to <code>buf</code>.
   * 
   * @param element the <code>VacationElement</code> for which HTML text is to be generated.
   */
  private void vacationElementToHtml(VacationElement element) {
    indent.increment();
    LOGGER.info("=> element=" + indent.toString() + element.toString());
    LOGGER.info("      type=" + indent.toString() + element.eClass().getName());
    
    switch(element.eClass().getClassifierID()) {
    case VacationsPackage.DAY:
      vacationElementDayToHtml((Day) element);
      break;
      
    case VacationsPackage.LOCATION:
      vacationElementLocationToHtml((Location) element);
      break;
      
    case VacationsPackage.TEXT:
      vacationElementTextToHtml((goedegep.vacations.model.Text) element);
      break;
      
    case VacationsPackage.PICTURE:
      vacationElementPictureToHtml((Picture) element);
      break;
      
    case VacationsPackage.GPX_TRACK:
      vacationElementGPXToHtml((GPXTrack) element);
      break;
      
    case VacationsPackage.MAP_IMAGE:
      vacationElementMapImageToHtml((MapImage) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      vacationElementToHtml(childElement);
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

  /**
   * Generate HTML text for a <code>Location</code> element.
   * <p>
   * The generated text is appended to <code>buf</code>.<br/>
   * 
   * @param location the <code>Location</code> element for which HTML text is to be generated.
   */
  private void vacationElementLocationToHtml(Location location) {
    boolean separatorNeeded = false;
    boolean newLineNeeded = false;
    
    buf.append("<p/>");
    
    if (location.isSetLabel()) {
      buf.append("<b><i>");
      buf.append(HtmlUtil.encodeHTML(location.getLabel().getLiteral()));
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
    
    if (location.isSetLocationType()) {
      POICategoryId poiCategoryId = location.getLocationType();
      buf.append("<img src=\"");
      buf.append(HtmlUtil.encodeHTML(poiIcons.getIconUrl(poiCategoryId).toString()));
      buf.append("\" height=\"32\" width=\"32\"/>");
      separatorNeeded = true;
    }
    
    if (location.isSetName()) {
      if (separatorNeeded) {
        buf.append(": ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getName()));
      separatorNeeded = true;
      newLineNeeded = true;
    }
    
    if (location.isSetCity()) {
      if (separatorNeeded) {
        buf.append(" - ");
      }
      buf.append(HtmlUtil.encodeHTML(location.getCity()));
      newLineNeeded = true;
    }
    
    if (location.isSetStreet()) {
      if (newLineNeeded) {
        buf.append("<br/>");
      }
      if (location.getStreet() == null) {
        LOGGER.severe("Street is set, but is null: " + location.toString());
      }
      buf.append(HtmlUtil.encodeHTML(location.getStreet()));
      if (location.isSetHouseNumber()) {
        if (location.getHouseNumber() == null) {
          LOGGER.severe("HouseNumber is set, but is null: " + location.toString());
        }
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
  private void vacationElementPictureToHtml(Picture picture) {
    if (picture.isSetPictureReference()) {
      FileReference pictureReference = picture.getPictureReference();
      String pictureFileName = pictureReference.getFile();
      if ((pictureFileName == null)  ||  pictureFileName.isEmpty()) {
        return;
      }
      Path picturePath = Paths.get(pictureFileName);
      buf.append("<a href=\"");
      buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString()));
      buf.append("\">");
      buf.append("<figure>");
      buf.append("<img src=\"");
      buf.append(HtmlUtil.encodeHTML(picturePath.toUri().toString()));
      buf.append("\" height=\"250\" >");
      String caption = VacationsUtils.getPictureCaption(picture);
      if (caption != null) {
        buf.append("<figcaption>");
        buf.append(HtmlUtil.encodeHTML(caption));
        buf.append("</figcaption>");
      }
      buf.append("</img>");
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
  private void vacationElementGPXToHtml(GPXTrack gpxTrack) {
    if (gpxTrack.isSetTrackReference()) {
      buf.append("<p/>");

      FileReference trackReference = gpxTrack.getTrackReference();
      if ((trackReference != null)  &&  (trackReference.getFile() != null)  &&  !trackReference.getFile().isEmpty()) {
        File file = new File(trackReference.getFile());
        GPXFile gpxFile = new GPXFile(file);
        GPX gpx = gpxFile.getGPX();
        Metadata metaData = gpx.getMetadata();
        if (metaData != null) {
          String keywords = metaData.getKeywords();
          ActivityLabel activityLabel = null;
          if ((keywords != null)  &&  !keywords.isEmpty()) {
            for (String keyword: keywords.split(",")) {
              keyword = keyword.trim().toLowerCase();
              switch (keyword) {
              case "wandeling":
                activityLabel = ActivityLabel.WANDELING;
                break;

              case "autorit":
                activityLabel = ActivityLabel.AUTORIT;
                break;
              }
            }
          }
          if (activityLabel != null) {
            buf.append("<img src=\"");
            buf.append(HtmlUtil.encodeHTML(activityIcons.getIconUrl(activityLabel).toString()));
            buf.append("\" height=\"16\" width=\"16\"/> ");
          }
          String name = metaData.getName();
          if (name != null) {
            buf.append(HtmlUtil.encodeHTML(name));
          }
          double length = 0.0;
          double cumulativeAscent = 0.0;
          for (GPXMeasurable measurable: gpxFile.getGPXMeasurables()) {
            length += measurable.getLength();
            cumulativeAscent += measurable.getCumulativeAscent();
          }
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
    vacationsWindow.createMapImageFile(mapImage, poiIcons);
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
}
