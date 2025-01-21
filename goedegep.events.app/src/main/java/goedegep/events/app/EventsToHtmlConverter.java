package goedegep.events.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.events.app.guifx.AttachmentTypeInfo;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.resources.ImageResource;
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;
import goedegep.util.html.HtmlUtil;

/**
 * This class creates an HTML document for {@link Events}.
 */
public class EventsToHtmlConverter {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EventsToHtmlConverter.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * Used to build the complete HTML text.
   */
  private StringBuilder buf = new StringBuilder();
  
  private FlexDateFormat flexDateFormat = new FlexDateFormat(true);
  
  /**
   * Constructor
   * 
  public EventsToHtmlConverter() {
  }

  /**
   * Create an HTML document for {@link Events}.
   * 
   * @param events the {@code Events} for which an HTML document is to be generated.
   * @return the generated HTML document.
   */
  public String eventsToHtml(Events events) {
    buf.append("<html>").append(NEWLINE);
    buf.append("<header>").append(NEWLINE);
    
    buf.append("</header>").append(NEWLINE);
    
    buf.append("<body>").append(NEWLINE);
    
    buf.append("<h1>Events</h1>").append(NEWLINE);

    buf.append("<table border=\"1\" cellpadding=\"4\" >");
    buf.append("<colgroup><col width=\"80\" /></colgroup>").append(NEWLINE);
    buf.append("<colgroup><col width=\"80\" /></colgroup>").append(NEWLINE);
    buf.append("<colgroup><col width=\"80\" /></colgroup>").append(NEWLINE);
    buf.append("<colgroup><col width=\"800\" /></colgroup>").append(NEWLINE);

    buf.append("<th>Date</th><th>Title</th><th>Picture</th><th>Attachments</th><th>Notes</th>").append(NEWLINE);

    for (EventInfo eventInfo: events.getEvents()) {
      eventInfoToHtml(eventInfo);
    }
    
    buf.append("</table>").append(NEWLINE);
    
    buf.append("</body>").append(NEWLINE);
    buf.append("</html>").append(NEWLINE);
    
    return buf.toString();
  }

  /**
   * Add HTML text for {@code EventInfo}.
   * 
   * @param eventInfo the {@code EventInfo} for which HTML text is to be generated.
   */
  private void eventInfoToHtml(EventInfo eventInfo) {
    buf.append("<tr>").append(NEWLINE);
    
    eventDateToHtml(eventInfo.getDate());
    eventTitleToHtml(eventInfo.getTitle());
    eventPictureToHtml(eventInfo.getPicture());
    eventAttachmentsToHtml(eventInfo.getAttachments());
    eventNotesToHtml(eventInfo.getNotes());
    buf.append("</tr>").append(NEWLINE);
  }

  /**
   * Add an HTML table data element for a {@code FlexDate}.
   * 
   * @param flexDate the {@code FlexDate} for which HTML text is to be generated.
   */
  private void eventDateToHtml(FlexDate flexDate) {
    buf.append("<td>").append(NEWLINE);
    
    if (flexDate != null) {
      buf.append(flexDateFormat.format(flexDate)).append(NEWLINE);
    }
    
    buf.append("</td>").append(NEWLINE);
  }

  /**
   * Add an HTML table data element for the event title, which is a {@code String}.
   * 
   * @param title the {@code String} for which HTML text is to be generated.
   */
  private void eventTitleToHtml(String title) {
    buf.append("<td>").append(NEWLINE);
    
    buf.append(title).append(NEWLINE);
    
    buf.append("</td>").append(NEWLINE);
  }

  /**
   * Add an HTML table data element for the event picture, of which the file name is specified.
   * 
   * @param pictureFileName the name of the picture file, relative to the events folder.
   */
  private void eventPictureToHtml(String pictureFileName) {
    buf.append("<td>").append(NEWLINE);
    
    if (pictureFileName != null  &&  !pictureFileName.isEmpty()) {
      Path picturePath = Paths.get(EventsRegistry.eventsFolderName, pictureFileName);
      String urlAsString = picturePath.toUri().toString();
      String caption = new File(pictureFileName).getName();
      buf.append(HtmlUtil.createPictureElement(urlAsString, 250, caption, urlAsString));
    }
    
    buf.append("</td>").append(NEWLINE);
  }

  /**
   * Add an HTML table data element for the attachments.
   * 
   * @param attachments the attachment.
   */
  private void eventAttachmentsToHtml(EList<FileReference> attachments) {
    buf.append("<td>").append(NEWLINE);
    
    for (FileReference fileReference: attachments) {
      String attachmentFileName = fileReference.getFile();
      if (fileReference.getTags() != null  &&  fileReference.getTags().equals(AttachmentTypeInfo.PHOTO_FOLDER.getTag())) {
        String iconUrlAsString = ImageResource.PHOTO_FOLDER.getImageUrl().toString();
        Path linkPath = Paths.get(fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (fileReference.getTags() != null  &&  fileReference.getTags().equals(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag())) {
        String iconUrlAsString = ImageResource.VIDEO_FOLDER.getImageUrl().toString();
        Path linkPath = Paths.get(fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (FileUtils.isPictureFile(attachmentFileName)) {
        Path pictureFilePath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String pictureUrlAsString = pictureFilePath.toUri().toString();
        String caption = pictureFilePath.getFileName().toString();
        buf.append(HtmlUtil.createPictureElement(pictureUrlAsString, 120, caption, pictureUrlAsString));
      } else if (FileUtils.isPDFFile(attachmentFileName)) {
        String iconUrlAsString = ImageResource.PDF.getImageUrl().toString();
        Path linkPath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (FileUtils.isTextFile(attachmentFileName)) {
        String iconUrlAsString = ImageResource.TEXT_FILE.getImageUrl().toString();
        Path linkPath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (FileUtils.isMSWordFile(attachmentFileName)) {
        String iconUrlAsString = ImageResource.MS_WORD.getImageUrl().toString();
        Path linkPath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (FileUtils.isODTFile(attachmentFileName)) {
        String iconUrlAsString = ImageResource.ODT.getImageUrl().toString();
        Path linkPath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      } else if (FileUtils.isGpxFile(attachmentFileName)) {
        String iconUrlAsString = ImageResource.GPX.getImageUrl().toString();
        Path linkPath = Paths.get(EventsRegistry.eventsFolderName, fileReference.getFile());
        String caption = linkPath.getFileName().toString();
        String linkUrlAsString = linkPath.toUri().toString();
        buf.append(HtmlUtil.createPictureElement(iconUrlAsString, 120, caption, linkUrlAsString));
      }
    }
      
    
    buf.append("</td>").append(NEWLINE);
  }

  /**
   * Add an HTML table data element for the event notes.
   * <p>
   * To fix constructs that aren't supported by the HTML to PDF converter:
   * <ul>
   * <li>Every '&lt;br&gt;' is replaced bij  '&lt;br/&gt;'</li>
   * <li>Every '{@literal &nbsp;}' is replaced by a single space.</li>
   * </ul>
   * 
   * @param notes the event notes (in HTML).
   */
  private void eventNotesToHtml(String notes) {
    buf.append("<td>").append(NEWLINE);
    
    if (notes != null) {
      buf.append(notes.replaceAll("<br>", "<br/>").replaceAll("&nbsp;", " "));
    }
    
    buf.append("</td>").append(NEWLINE);
  }
}
