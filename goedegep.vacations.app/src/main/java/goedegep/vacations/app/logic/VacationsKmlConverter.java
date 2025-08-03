package goedegep.vacations.app.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.emf.ecore.EObject;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleMap;
import de.micromata.opengis.kml.v_2_2_0.StyleState;
import goedegep.geo.WGS84Coordinates;
import goedegep.poi.app.LocationCategory;
import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.html.HtmlUtil;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.util.sgml.SgmlUtil;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Text;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class creates a KML file for a Vacations structure.
 */
public class VacationsKmlConverter extends VacationToTextConverterAbstract {
  private static final Logger LOGGER = Logger.getLogger(VacationsKmlConverter.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String ICON_DIRECTORY = "C:/MyWorld/goedegep.poi.app/src/main/resources/goedegep/poi/app/guifx";
  private static final FlexDateFormat FDF = new FlexDateFormat(true, false);
//  private static final FlexDateFormat FDF_REV = new FlexDateFormat(true, true);
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

  private Parser parser = Parser.builder().build();
  private HtmlRenderer renderer = HtmlRenderer.builder().build();
  private StringBuilder buf = new StringBuilder();
  private final Kml kml = new Kml();
  private Document kmlDocument;
  private Map<LocationCategory, StyleMap> iconStyleMapMap = new HashMap<>();

  /**
   * Constructor
   * 
   */
  public VacationsKmlConverter() {
  }
  
  /**
   * Create a KML file for a Vacations structure.
   * <p>
   * The KML file will have the following structure:<pre>
   * - Home
   * - Vacations
   *   - Vacation: folder, name is the vacation Title
   *     - Vacation elements
   * </pre>
   * 
   * @param vacations The <code>Vacations</code> structure for which a KML file is to be created.
   * @param file The file to be created.
   * @throws FileNotFoundException if the file cannot be written.
   */
  public void createKmlForVacations(Vacations vacations, File file) throws FileNotFoundException {
    if (!Files.isWritable(file.getParentFile().toPath())) {
      LOGGER.severe("Geen schrijfrechten in " + file.getParentFile().toString());
      throw new RuntimeException("Geen schrijfrechten in " + file.getParentFile().toString());
    }
    
    kmlDocument = kml.createAndSetDocument().withOpen(true);
    
    createAndAddKmlForHome(vacations);
    
    Folder vacationsFolder = kmlDocument.createAndAddFolder().withName("Vakanties").withOpen(true);
    
    for (Vacation vacation: vacations.getVacations()) {
      createKmlForVacation(vacation, vacationsFolder);
    }
    
    saveToFile(file);
  }
  
  /**
   * Create a KML file for a Vacations structure.
   * <p>
   * The KML file will have the following structure:<pre>
   * - Home
   * - Vacations
   *   - Vacation: folder, name is the vacation Title
   *     - Vacation elements
   * </pre>
   * 
   * @param vacation The <code>Vacation</code>s structure for which a KML file is to be created.
   * @param file The file to be created.
   * @throws FileNotFoundException if the file cannot be written.
   */
  public void createKmlForVacation(Vacation vacation, File file) throws FileNotFoundException {
    if (!Files.isWritable(file.getParentFile().toPath())) {
      LOGGER.severe("Geen schrijfrechten in " + file.getParentFile().toString());
      throw new RuntimeException("Geen schrijfrechten in " + file.getParentFile().toString());
    }
    
    kmlDocument = kml.createAndSetDocument().withOpen(true);
    
//    createAndAddKmlForHome(vacations);
    
    Folder vacationsFolder = kmlDocument.createAndAddFolder().withName("Vakanties").withOpen(true);
    
//    for (Vacation vacation: vacations.getVacations()) {
      createKmlForVacation(vacation, vacationsFolder);
//    }
    
    saveToFile(file);
  }
  
  /**
   * Save the KML to a file
   * 
   * @param file The file to write the data to.
   */
  private void saveToFile(File file) {
    /*
     * kml.marshall replaces characters like '<' with '&lt;'. Therefore we marshall to a string
     * and then change the characters back.
     * Also all 'ns:' name space stuff is removed.
     */
    StringWriter stringWriter = new StringWriter();
    kml.marshal(stringWriter);
    String kmlString = stringWriter.toString();

    kmlString = StringUtils.replace(kmlString, "xmlns:ns2=", "xmlns=");
    kmlString = StringUtils.replace(kmlString, "<ns2:", "<");
    kmlString = StringUtils.replace(kmlString, "</ns2:", "</");

    kmlString = StringUtils.replaceEach(kmlString,
        new String[]{"&lt;", "&gt;", "&amp;"},
        new String[]{"<", ">", ""});


    try {
      FileUtils.writeStringToFile(file, kmlString, "utf-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create a node for the home location.
   * 
   * @param vacations The Vacations structure from which the home location is to be taken
   */
  private void createAndAddKmlForHome(Vacations vacations) {
    if (vacations.isSetHome()) {
      Placemark homePlacemark = kmlDocument.createAndAddPlacemark().withName("Home").withOpen(true);
      Location homeLocation = vacations.getHome();
      if (homeLocation.isSetLongitude()  &&  homeLocation.isSetLatitude()) {
        homePlacemark.createAndSetPoint().addToCoordinates(homeLocation.getLongitude(), homeLocation.getLatitude(), 0F);
      }
      buf.setLength(0);
      addLocationTextToBuffer(homeLocation, buf);
      homePlacemark.setDescription(SgmlUtil.encloseInCData(buf.toString()));
      
      StyleMap iconStyleMap = getIconStyleMap(LocationCategory.HOME);
      homePlacemark.setStyleUrl(iconStyleMap.getId());
    }
  }
  
  /**
   * Create a node for a vacation.
   * 
   * @param vacation The <code>Vacation</code> to be added.
   * @param vacationsFolder The folder in the KML document to which the folder is to be added.
   */
  private void createKmlForVacation(Vacation vacation, Folder vacationsFolder) {
    
    // Folder name
    Folder vacationFolder = vacationsFolder.createAndAddFolder().withName(HtmlUtil.encodeHTML(getVacationTitle(vacation)));
    
    // Folder description
    buf.setLength(0);
    if (vacation.isSetNotes()) {
      buf.append(vacation.getNotes()).append(NEWLINE);
    }
    
    if (vacation.isSetDate()) {
      buf.append("@van: ");
      buf.append(FDF.format(vacation.getDate())).append(NEWLINE);
      
      if (vacation.getEndDate() != null) {
        buf.append("@tot: ");
        buf.append(FDF.format(vacation.getEndDate())).append(NEWLINE);
      }
    }
    
    vacationFolder.setDescription(SgmlUtil.encloseInCData(buf.toString()));
    
    for (VacationElement element: vacation.getElements()) {
      createKmlForVacationElement(element, vacationFolder);
    }
    
    try {
      List<List<WGS84Coordinates>> locationsConnectingLines = VacationsUtils.getLocationConnectingLines(vacation);
      for (List<WGS84Coordinates> locationsConnectingLine: locationsConnectingLines) {
        addPath(vacationFolder, locationsConnectingLine);
      }
      
      
//      List<WGS84Coordinates> lineNodes = VacationsUtils.getGeoLocations(vacation);
//      if (lineNodes.size() > 1) {
//        addPath(vacationFolder, lineNodes);
//      }
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found");
    }
  }

  /**
   * Generate kml data for a <code>VacationElement</code>.
   * 
   * @param element the <code>VacationElement</code> for which kml data is to be generated.
   * @param parentFolder the parent <code>Folder</code> element.
   */
  private void createKmlForVacationElement(VacationElement element, Folder parentFolder) {
    Feature feature = null;
    
    switch(element.eClass().getClassifierID()) {
    case VacationsPackage.DAY:
      feature = createKmlForVacationElementDay((Day) element, parentFolder);
      break;
      
    case VacationsPackage.LOCATION:
      feature = createKmlForVacationElementLocation((Location) element, parentFolder);
      break;
      
    case VacationsPackage.TEXT:
      feature = createKmlForVacationElementText((Text) element, parentFolder);
      break;
      
    case VacationsPackage.PICTURE:
      createKmlForVacationElementPicture((Picture) element, parentFolder);
      break;
      
    case VacationsPackage.GPX_TRACK:
//      createKmlForVacationElementGPXT((VacationElementGPX) element);
      break;
    }
    
    for (VacationElement childElement: element.getChildren()) {
      if (feature != null  &&  feature instanceof Folder) {
        createKmlForVacationElement(childElement, (Folder) feature);
      } else {
        createKmlForVacationElement(childElement, parentFolder);
//        throw new RuntimeException("Cannot add child " + childElement.toString() + "as node is null or not a Folder");
      }
    }
  }

  /**
   * Create a node for a VacationElementDay
   * <p>
   * The node is a Folder with the day number and date as name.
   * 
   * @param day The VacationElementDay for which a node has to be created.
   * @param parentFolder The Folder to which the node is to be added.
   */
  private Folder createKmlForVacationElementDay(Day day, Folder parentFolder) {
    buf.setLength(0);
    buf.append("Dag ");
    buf.append(day.getDayNr());
    Date date = day.getDate();
    if (date != null) {
      buf.append(" - ");
      buf.append(DF.format(date));
    }
    Folder folder = parentFolder.createAndAddFolder().withName(buf.toString());
    
    return folder;
  }

  /**
   * Create a node for a Location.
   * <p>
   * In a Vacations structure, a Location can have children. However in KML a Placemark cannot have children.<br/>
   * As most Locations will not have children, and a Placemark is the most logical element to map a Location to, a Placemark is
   * used if there are no children, else a Folder with a Placemark is used.
   * 
   * @param location The Location for which a node has to be added.
   * @param parentFolder The Folder to which the node is to be added.
   */
  private Feature createKmlForVacationElementLocation(Location location, Folder parentFolder) {
    // Placemark/Folder name
    buf.setLength(0);
    if (location.isSetName()) {
      buf.append(location.getName());
    }
    String name = HtmlUtil.encodeHTML(buf.toString());
    
    // Folder/Placemark description
    buf.setLength(0);
    
    if (location.isStayedAtThisLocation()) {
      buf.append("<b><i>");
      buf.append(HtmlUtil.encodeHTML("Verblijf"));
      buf.append("</i></b><br/>");
    }
    
    if (location.isSetStartDate()) {
      buf.append("@van: ");
      buf.append(FDF.format(location.getStartDate())).append("</br>").append(NEWLINE);
      
      if (location.isSetEndDate()) {
        buf.append("@tot: ");
        buf.append(FDF.format(location.getEndDate())).append("</br>").append(NEWLINE);
      }
    }
    
    if (location.isSetDescription()) {
      buf.append(location.getDescription()).append("</br>").append(NEWLINE);
    }
    addLocationTextToBuffer(location, buf);
    String description = SgmlUtil.encloseInCData(buf.toString());

    Placemark placemark = KmlFactory.createPlacemark()
        .withName(name)
        .withOpen(Boolean.TRUE);

    if (location.isSetLatitude()  &&  location.isSetLongitude()) {
      placemark.createAndSetPoint().addToCoordinates(location.getLongitude(), location.getLatitude(), 0F);      
    }
    
    StyleMap iconStyleMap = getIconStyleMap(location.getLocationCategory());
    placemark.setStyleUrl(iconStyleMap.getId());
    
    if (location.getChildren().size() == 0) {
      placemark.setDescription(description);
      parentFolder.addToFeature(placemark);
      return placemark;
    } else {
      Folder folder = parentFolder.createAndAddFolder().withName(name);
      folder.setDescription(description);
      folder.setStyleUrl(iconStyleMap.getId());
      folder.addToFeature(placemark);
      return folder;
    }
  }

  /**
   * Create a Text node.
   * <p>
   * The node is a Folder, with the text as description and the first part of the text as name.
   * 
   * @param vacationElementText The Text for which a node has to be added.
   * @param parentFolder The Folder to which the node is to be added.
   */
  private Folder createKmlForVacationElementText(Text vacationElementText, Folder parentFolder) {
    if (!vacationElementText.isSetText()) {
      return null;
    }
    
    String text = vacationElementText.getText();
    
    int nameTextLength = Math.min(20, text.length());
    buf.setLength(0);
    buf.append(text.substring(0, nameTextLength));
    if (buf.length() < text.length()) {
      buf.append("...");
    }
    Folder folder = parentFolder.createAndAddFolder().withName(buf.toString());
    
    Node document = parser.parse(text);
    String htmlText = renderer.render(document);  
    folder.setDescription(SgmlUtil.encloseInCData(htmlText));
    
    return folder;
  }

  /**
   * Create a node for a Picture.
   * <p>
   * In a Vacations structure, a Picture can have children. However in KML a Placemark cannot have children.<br/>
   * As most Pictures will not have children, and a Placemark is the most logical element to map a Picture to, a Placemark is
   * used if there are no children, else a Folder with a Placemark is used.
   * 
   * @param picture The VacationElementPicture for which a node has to be added.
   * @param parentFolder The Folder to which the node is to be added.
   */
  private Feature createKmlForVacationElementPicture(Picture picture, Folder parentFolder) {
    FileReference pictureReference = picture.getPictureReference();
    if (pictureReference == null) {
      return null;
    }
    
    // Placemark/Folder name
    String name = pictureReference.getTitle();
    if (name != null) {
      name = HtmlUtil.encodeHTML(name);
    } else {
      File file = new File(pictureReference.getFile());
      name = file.getName();
    }
    
    Placemark placemark = KmlFactory.createPlacemark()
        .withName(name)
        .withOpen(Boolean.TRUE);
    
    StyleMap pictureStyleMap = createPictureStyleMap(pictureReference);
    placemark.setStyleUrl("#" + pictureStyleMap.getId());
    
    StringBuilder buf = new StringBuilder();
    
    
    buf.append("<p><p><img src='file:///");
    buf.append(pictureReference.getFile());
    buf.append("' width='1000' height='562'></p></p><h2>");
    buf.append(name);
    buf.append("</h2>");
    LOGGER.info("Description: " + buf.toString());
    placemark.setDescription(SgmlUtil.encloseInCData(buf.toString()));
    
    // If the picture has a location, use it, else check whether there is a parent item of type Location.
    WGS84Coordinates coordinates = null;
//    LocalDateTime creationDateTime = null;
    File file = new File(picture.getPictureReference().getFile());
    
    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
      coordinates = photoFileMetaDataHandler.getGeoLocation();
//      creationDateTime = photoFileMetaDataHandler.getCreationDateTime();
    } catch (ImageReadException | IOException e) {
      e.printStackTrace();
    }
    
    if (coordinates == null) {
      Location location = getRelatedLocation(picture);
      if (location != null) {
        coordinates = new WGS84Coordinates(location.getLatitude(), location.getLongitude());
      }
    }
    
    if (coordinates != null) {
      placemark.createAndSetPoint().addToCoordinates(coordinates.getLongitude(), coordinates.getLatitude(), 0F);      
    }
    
    if (picture.getChildren().size() == 0) {
      parentFolder.addToFeature(placemark);
      return placemark;
    } else {
      Folder folder = parentFolder.createAndAddFolder().withName(name);
//      folder.setStyleUrl(iconStyleMap.getId());
      folder.addToFeature(placemark);
      return folder;
    }
  }
  
  /**
   * Get the <code>Location</code> to which a picture belongs.
   * <p>
   * Parent relations (container) are followed until a Location is encountered, or until there's no parent anymore.
   * 
   * @param vacationElementPicture for which the related location is to be found.
   * @return The <code>Location</code> to which the picture is related.
   */
  private Location getRelatedLocation(Picture vacationElementPicture) {
    if (vacationElementPicture == null) {
      return null;
    }
    
    EObject container = vacationElementPicture.eContainer();
    
    while ((container != null)  &&  !(container instanceof Location)) {
      container = container.eContainer();
    }
    
    return (Location) container;
  }
  
  /**
   * Create a <code>StyleMap</code> for a picture.
   * 
   * @param pictureReference the reference to the picture for which a <code>StyleMap</code> is to be created.
   * @return a <code>StyleMap</code> created for the <code>pictureReference</code>.
   */
  private StyleMap createPictureStyleMap(FileReference pictureReference) {
    // normal is photo icon
    // highlight is the photo
    
    File file = new File(pictureReference.getFile());
    String imageId = file.getName();
    String styleNormalId = String.format("ps_%s", imageId);
    String styleHighlightId = String.format("ps_%s_hl", imageId);
    String styleMapId = String.format("pm_%s", imageId);
    
    Icon icon = KmlFactory.createIcon().withHref(pictureReference.getFile());
    
    Style normalStyle = kmlDocument
        .createAndAddStyle()
        .withId(styleNormalId);

    IconStyle normalIconStyle = normalStyle
        .createAndSetIconStyle()
        .withScale(1.0);
    normalIconStyle.setIcon(icon);
    
    Style highlightStyle = kmlDocument
        .createAndAddStyle()
        .withId(styleHighlightId);

    IconStyle highlightIconStyle = highlightStyle
        .createAndSetIconStyle()
        .withScale(1.3);
    highlightIconStyle.setIcon(icon);
    
    return kmlDocument.createAndAddStyleMap().withId(styleMapId)
    .addToPair(KmlFactory.createPair().withKey(StyleState.NORMAL).withStyleUrl("#" + styleNormalId))
    .addToPair(KmlFactory.createPair().withKey(StyleState.HIGHLIGHT).withStyleUrl("#" + styleHighlightId));
  }
  
  /**
   * Add the text for a <code>Location</code> to a buffer.
   * 
   * @param location the <code>Location</code> for which the text is to be added to the <code>buffer</code>.
   * @param buffer the <code>StringBuilder</code> to which the text is to be added.
   */
  private void addLocationTextToBuffer(Location location, StringBuilder buffer) {
    if (location.isSetCountry()) {
      buffer.append("@land: ");
      buffer.append(location.getCountry()).append("</br>").append(NEWLINE);
    }
    
    if (location.isSetCity()) {
      buffer.append("@plaats: ");
      buffer.append(location.getCity()).append("</br>").append(NEWLINE);
    }
    
    if (location.isSetStreet()) {
      buffer.append("@straat: ");
      buffer.append(location.getStreet()).append("</br>").append(NEWLINE);
      
      if (location.isSetHouseNumber()) {
        buffer.append("@huisnummer: ");
        buffer.append(location.getHouseNumber()).append("</br>").append(NEWLINE);
      }
    }
    
    if (location.isSetWebSite()) {
      buffer.append("@website: ");
      buffer.append(location.getWebSite()).append("</br>").append(NEWLINE);
    }
  }

  /**
   * Get the <code>StyleMap</code> for a location type.
   * <p>
   * The requested <code>StyleMap</code> is created if it doesn't exist yet.
   * 
   * @param locationType the <code>POICategoryId</code> representing the location type.
   * @return the <code>StyleMap</code> for <code>locationType</code>.
   */
  private StyleMap getIconStyleMap(LocationCategory locationType) {
    StyleMap styleMap = iconStyleMapMap.get(locationType);
    
    if (styleMap == null) {
      styleMap = createAndAddIconStyleMap(locationType);
      iconStyleMapMap.put(locationType, styleMap);
    }
    return styleMap;
  }

  /**
   * Create and add Icon Style Map
   * <p>
   * Two Styles are created and written to the kmlDocument; one for the normal and one for the highlighted icon state.
   * Both Styles use the same Icon, which is the image file related to the poiCategoryId.
   * However for the normal state the scale is set to 1.0, while for the highlight state it is set to 1.3.
   * For the normal Style, the Id is 's_<poiCategoryId-literal>'.
   * For the highlight Style, the Id is 's_<poiCategoryId-literal>_hl'.
   * 
   * To show an icon for a Placemark, a StyleMap is used.
   * The StyleMap has an Id, which is referred to by the Placemark via the StyleUrl attribute.
   * The StyleMap Id has the format 'm_<poiCategoryId-literal>'.
   * 
   * The StyleMap has 2 entries, one 
   * 
   * @param poiCategoryId The POICategoryId for the style to be created
   * @return the created StyleMap
   */
  private StyleMap createAndAddIconStyleMap(LocationCategory poiCategoryId) {
    String imageId = poiCategoryId.name();
    String styleNormalId = String.format("s_%s", imageId);
    String styleHighlightId = String.format("s_%s_hl", imageId);
    String styleMapId = String.format("m_%s", imageId);
    
    Icon icon = KmlFactory.createIcon().withHref(ICON_DIRECTORY + "/" + poiCategoryId.getIconFilename());
    
    Style normalStyle = kmlDocument
        .createAndAddStyle()
        .withId(styleNormalId);

    IconStyle normalIconStyle = normalStyle
        .createAndSetIconStyle()
        .withScale(1.0);
    normalIconStyle.setIcon(icon);
    
    Style highlightStyle = kmlDocument
        .createAndAddStyle()
        .withId(styleHighlightId);

    IconStyle highlightIconStyle = highlightStyle
        .createAndSetIconStyle()
        .withScale(1.3);
    highlightIconStyle.setIcon(icon);
    
    return kmlDocument.createAndAddStyleMap().withId(styleMapId)
    .addToPair(KmlFactory.createPair().withKey(StyleState.NORMAL).withStyleUrl("#" + styleNormalId))
    .addToPair(KmlFactory.createPair().withKey(StyleState.HIGHLIGHT).withStyleUrl("#" + styleHighlightId));
  }

  /**
   * Add a path (poly line) between the <code>lineNodes</code> to a <code>Folder</code>.
   * 
   * @param folder the <code>Folder</code> to which the path is to be added.
   */
  private void addPath(Folder folder, List<WGS84Coordinates> lineNodes) {
    Placemark path = folder.createAndAddPlacemark().withName("path");

    Style pathStyle = path.createAndAddStyle();
    pathStyle.createAndSetLineStyle()
    .withColor("ff0000ff")
    .withWidth(3.0);

    LineString line = path
        .createAndSetLineString()
        .withExtrude(false)
        .withTessellate(true);

    lineNodes.forEach((lineNode) -> {
      line.addToCoordinates(lineNode.getLongitude(), lineNode.getLatitude());
    });
  }
}
