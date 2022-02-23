package goedegep.media.mediadb.trackinfo;

//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

/**
 * This class read 'TrackInfo' XML files and stores the information in a <code>MediaDb</code> model.
 * <p>
 * The schema for these files is defined in the xsd file <a href="file:///D:/Database/Muziek/Tracks/TrackInfo.xsd">AlbumInfo.xsd</a>.
 *
 */
public class TrackInfoHandler extends AbstractValidatingHandler<State> {
  private static final Logger LOGGER = Logger.getLogger(TrackInfoHandler.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private static MediadbFactory FACTORY = MediadbFactory.eINSTANCE;

  private static final String TRACKS_TAG = "Tracks";
  private static final String TRACK_TAG = "Track";
  private static final String ALBUM_TAG = "Album";
//  private static final String ARTIST_TAG = "TrackArtist";
  private static final String CATEGORY_TAG = "Category";
  private static final String I_HAVE_ON_TAG = "IHaveOn";
  private static final String MY_INFO_TAG = "MyInfo";
  private static final String TRACK_ARTIST_TAG = "TrackArtist";
  private static final String TRACK_TITLE_TAG = "TrackTitle";
  private static final String TRACK_LINK_TAG = "TrackLink";

  private String              tracksFileName = null;  // Will be used for generating detailed error messages.

  // Following two attributes aren't used currently, as there is no write() function implemented yet.
  @SuppressWarnings("unused")
  private static String       currentTracksFileName = null;
  @SuppressWarnings("unused")
  private static String       schema = null;

  private List<TrackInfoErrorInfo> errors;

  private MediaDb mediaDb;
  private MyTrackInfo myTrackInfo;
  private TrackReference trackReference;
  private Track track;



  public TrackInfoHandler() {
    setPrintStateChanges(true);
  }

  public MediaDb getMediaDb() {
    return mediaDb;
  }

  public TrackInfoHandler setMediaDb(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
    
    return this;
  }

  public List<TrackInfoErrorInfo> read(String tracksFileName) throws ParseException {
    errors = new ArrayList<>();
    state = State.START;
    this.tracksFileName = tracksFileName;
    Path tracksPathName = Paths.get(tracksFileName);
    LOGGER.info("tracksPathName=" + tracksPathName);
    Path tracksFilePath = tracksPathName.getFileName();
    LOGGER.info("tracksFilePath=" + tracksFilePath);
    String tracksPlainFileName = tracksFilePath.toString();
    LOGGER.info("tracksPlainFileName=" + tracksPlainFileName);

    try {
      // parse the document
      LOGGER.info("Parse start: " + tracksFileName);
      getParser(null).parse(tracksFileName, this);
      LOGGER.info("Parse ready: " + tracksFileName);
      currentTracksFileName = tracksFileName;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(tracksFileName, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }

    return errors;
  }

  public void write(String albumInfoFile, List<Album> albums) {
    StringBuilder output = new StringBuilder();
    Indent       indent = new Indent(2);

    //  if (schema == null) {
    //    throw new RuntimeException("Geen schema voor de aanstelling file.");
    //  }

    try
    {
      // Rename existing file to .bak file
      //    File file = new File(albumInfoFile);
      //    file.renameTo(new File(albumInfoFile + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(albumInfoFile);
      output.append(XmlUtil.createStartLine());
      output.append("<?xml-stylesheet type=\"text/xsl\" href=\"AlbumList.xsl\" ?>").append(NEWLINE);
      output.append("<Albums xmlns=\"http://www/goedegep/2006/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
          "xsi:schemaLocation=\"http://www/goedegep/2006/XMLSchema AlbumInfo.xsd\">").append(NEWLINE);
      indent.increment();

      for (Album album: albums) {
        output.append(toXmlString(indent, getXmlNameSpaceName(), album));
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), TRACKS_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de aanstelling file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }

  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    LOGGER.fine("=> " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

    data = null;

    switch (state) {
    case START:
      // Only TRACKS_TAG expected.
      if (tag.compareTo(TRACKS_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.TRACKS);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(tracksFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case TRACKS:
      // Only TRACK_TAG expected.
      if (tag.compareTo(TRACK_TAG) == 0) {
        trackReference = FACTORY.createTrackReference();
        track = FACTORY.createTrack();
        pushState(State.TRACK);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(tracksFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case TRACK:
      if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(ALBUM_TAG) == 0) {
        // no action
      } else if (tag.compareTo(MY_INFO_TAG) == 0) {
        myTrackInfo = FACTORY.createMyTrackInfo();
        pushState(State.MY_INFO);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ", in file: " + tracksFileName + ", line: " + locator.getLineNumber());
        throw new ParseException(tracksFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case MY_INFO:
      if (tag.compareTo(I_HAVE_ON_TAG) == 0) {
        // no action
      } else if (tag.compareTo(CATEGORY_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_LINK_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ". Found in line " + locator.getLineNumber() + " in file " + tracksFileName);
        throw new ParseException(tracksFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
    LOGGER.fine("=> localpart = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

    switch (state) {
    case START:
      // No action.
      break;

    case TRACKS:
      if (tag.compareTo(TRACKS_TAG) == 0) {
        popState();
      }
      break;

    case TRACK:
      if (tag.compareTo(TRACK_TAG) == 0) {
        // Add the track to the right TrackCollection.
        MyTrackInfo info = trackReference.getMyTrackInfo();
        Collection collection = info.getCollection();
        if (collection != null) {
          trackReference.setTrack(track);
          TrackCollection trackCollection = mediaDb.getTrackCollection(collection);
          trackCollection.getTrackReferences().add(trackReference);
        } else {
          throw new RuntimeException("No Collection set for track: " + track.getTitle());          
        }
        mediaDb.getTracks().add(track);
        popState();
      } else if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        Artist artist = getArtist(data.trim());
        track.setArtist(artist);
      } else if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        track.setTitle(data.trim());
      } else if (tag.compareTo(ALBUM_TAG) == 0) {
//        track.setId(data.trim());  // TODO Do I add album title to track (for tracks from albums not in database? Or do I create an album, with IWant/HaveTrack set to this track.
      }
      break;

    case MY_INFO:
      if (tag.compareTo(MY_INFO_TAG) == 0) {
        // By definition I have all tracks on disc. So add a MediumInfo with Writability.MANY.
        MediumInfo mediumInfo = FACTORY.createMediumInfo();
        myTrackInfo.getIHaveOn().add(mediumInfo);
        
        trackReference.setMyTrackInfo(myTrackInfo);
        popState();
     } else if (tag.compareTo(I_HAVE_ON_TAG) == 0) {
        MediumInfo mediumInfo = getMediumInfoFromText(data);
        myTrackInfo.getIHaveOn().add(mediumInfo);
      } else if (tag.compareTo(CATEGORY_TAG) == 0) {
        Collection collection = Collection.get(data.trim());
        if (collection != null) {
          myTrackInfo.setCollection(collection);
        } else {
          LOGGER.severe("No Collection found for CollectionName: " + data.trim());
          throw new ParseException(tracksFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldige collectie '" + data + "' in MyInfo.");
        }
      } else if (tag.compareTo(TRACK_LINK_TAG) == 0) {
//        myInfo.setIveHadOnLP(true); TODO 
      } else {
        LOGGER.severe("Illegal end element: " + tag + " in state: " + state);
        throw new ParseException(tracksFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig eind element: '" + tag + "'.");
      }
      break;

    }
  }

//  /**
//   * Get the MediumType from a textual representation.
//   * @param data The textual representation of the Medium Type.
//   * @return The 'data' translated to a MediumType, or null if no match is found.
//   */
//  private MediumType getMediumTypeFromText(String data) {
//    MediumType mediumType = null;
//
//    switch (data) {
//    case "LP":
//      mediumType = MediumType.LP;
//      break;
//
//    case "CD":
//      mediumType = MediumType.CD_AUDIO;
//      break;
//
//    default:
//      LOGGER.severe("Wrong medium type: " + data);
//      throw new ParseException(tracksFileName, locator.getLineNumber(),
//          locator.getColumnNumber(), "ongeldig medium type: '" + data + "'.");
//    }
//
//    return mediumType;
//  }

  /**
   * Get the MediumInfo for a given text.
   * @param data the text from which the MediumInfo if to be derived.
   * @return The MediumInfo for the given text. This can be empty if no match is found.
   */
  private MediumInfo getMediumInfoFromText(String data) {
    MediumInfo mediumInfo = FACTORY.createMediumInfo();

    if (data.equalsIgnoreCase("CD")) {
      mediumInfo.setMediumType(MediumType.CD_AUDIO);
    } else if (data.equals("CDR")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
    } else if (data.equals("CDR-APE")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
      mediumInfo.getSourceTypes().add(InformationType.APE);
    } else if (data.equals("CDR-CD")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
      mediumInfo.getSourceTypes().add(InformationType.WAV);
    } else if (data.equals("CDR-LP")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
      mediumInfo.getSourceTypes().add(InformationType.VINYL_ANALOG);
    } else if (data.equals("CDR-FLAC")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
      mediumInfo.getSourceTypes().add(InformationType.FLAC);
    } else if (data.equals("CDR-MP3")) {
      mediumInfo.setMediumType(MediumType.CDR_AUDIO);
      mediumInfo.getSourceTypes().add(InformationType.MP3);
    } else if (data.equals("CDRW-AIFF")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.AIFF);
    } else if (data.equals("CDRW-CD")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.WAV);
    } else if (data.equals("CDRW-FLAC")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.FLAC);
    } else if (data.equals("CDRW-MP3")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.MP3);
    } else if (data.equals("CDRW-WAV/MP3")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.WAV);
      mediumInfo.getSourceTypes().add(InformationType.MP3);
    } else if (data.equals("CDRW-WAV")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.WAV);
    } else if (data.equals("CDRW-MP3/LP")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.MP3);
      mediumInfo.getSourceTypes().add(InformationType.VINYL_ANALOG);
    } else if (data.equals("LP")) {
      mediumInfo.setMediumType(MediumType.LP);
      LOGGER.severe("Wrong IHaveOn LP in file: " + tracksFileName);
      throw new ParseException(tracksFileName, locator.getLineNumber(),
          locator.getColumnNumber(), "Geeft aan dat ik dit op lp heb, maar die heb ik niet meer.");
    } else if (data.equals("SACD")) {
      mediumInfo.setMediumType(MediumType.SACD);
    } else if (data.equals("CDRW-M2TS")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.M2TS);
    } else {
      LOGGER.severe("Wrong info for medium info: " + data);
      throw new ParseException(tracksFileName, locator.getLineNumber(),
          locator.getColumnNumber(), "ongeldige medium tekst: '" + data + "'.");
    }

    return mediumInfo;
  }

//  private static String mediumInfoToText(MediumInfo mediumInfo) {
//    String sourceTypesText = sourceTypesToText(mediumInfo.getSourceTypes());
//
//    switch (mediumInfo.getMediumType()) {
//    case CD_AUDIO:
//      return "CD";
//
//    case CD_ROM:
//      break;
//
//    case DVD_ROM:
//      break;
//
//    case DVD_VIDEO:
//      break;
//
//    case LP:
//      break;
//
//    case SACD:
//      return "SACD";
//
//    case SINGLE:
//      break;
//
//    case NOT_SET:
//      break;
//    }
//
//    return null;
//  }

//  private static String sourceTypesToText(List<InformationType> informationTypes) {
//    StringBuilder buf = new StringBuilder();
//    boolean first = true;
//
//    for (InformationType informationType: informationTypes) {
//      if (first) {
//        first = false;
//      } else {
//        buf.append("/");
//      }
//      switch(informationType) {
//      case FLAC:
//        buf.append("FLAC");
//        break;
//
//      case VINYL_ANALOG:
//        buf.append("LP");
//        break;
//
//      case M2TS:
//        // not possible, no action
//        break;
//
//      case MP3:
//        buf.append("MP3");
//        break;
//
//      case WAV:
//        buf.append("CD");
//        break;
//
//      case APE:
//        buf.append("APE");
//        break;
//
//      case AIFF:
//        buf.append("AIFF");
//        break;
//
//      case NOT_SET:
//        // no action
//        break;        
//      }
//    }
//
//    return buf.toString();
//  }


  /**
   * Get an Artist object from the mediaDb. If no artist exists yet for the given name, the Artist is created and added to the mediaDb.
   * @param artistName the name of an artist.
   * @return The Artist with the specified artistName.
   */
  private Artist getArtist(String artistName) {
    Artist artist = mediaDb.getArtist(artistName);

    if (artist == null) {
      artist = FACTORY.createArtist();
      artist.setName(artistName);
      mediaDb.getArtists().add(artist);
    }

    return artist;
  }

  public static String toXmlString(Indent indent, String nameSpace, Album album) {
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), ALBUM_TAG)).append(NEWLINE);
//    indent.increment();
//
//    // Artist
//    if (album.isSetArtist()) {
//      output.append(SgmlUtil.createElement(indent, null, ARTIST_TAG, album.getArtist().getName())).append(NEWLINE);
//    }
//
//    // Album Title
//    if (album.isSetTitle()) {
//      output.append(SgmlUtil.createElement(indent, null, ALBUM_TITLE_TAG, album.getTitle())).append(NEWLINE);
//    }
//
//    // Album Id
//    if (album.isSetId()) {
//      output.append(SgmlUtil.createElement(indent, null, ALBUM_ID_TAG, album.getId())).append(NEWLINE);
//    }
//
//    // Issue date/Release date
//    if (album.isSetReleaseDate()) {
//      output.append(SgmlUtil.createElement(indent, null, ISSUE_DATE_TAG, FDF.format(album.getReleaseDate()))).append(NEWLINE);
//    }
//
//    // Players
//    for (Player player: album.getPlayers()) {
//      output.append(toXmlString(indent, nameSpace, player));
//    }
//
//    // Front images
//    for (String imageFront: album.getImagesFront()) {
//      output.append(SgmlUtil.createElement(indent, null, CD_COVER_FRONT_TAG, imageFront)).append(NEWLINE);
//    }
//
//    // Front Inside images
//    for (String imageFrontInside: album.getImagesFrontInside()) {
//      output.append(SgmlUtil.createElement(indent, null, CD_COVER_INSIDE_TAG, imageFrontInside)).append(NEWLINE);
//    }
//
//    // Back images
//    for (String imageBack: album.getImagesBack()) {
//      output.append(SgmlUtil.createElement(indent, null, CD_COVER_BACK_TAG, imageBack)).append(NEWLINE);
//    }
//
//    // Label images
//    for (String imageLabel: album.getImagesLabel()) {
//      output.append(SgmlUtil.createElement(indent, null, CD_LABEL_TAG, imageLabel)).append(NEWLINE);
//    }
//
//    // DescriptionTitle/Description
//    if (album.isSetDescriptionTitle()) {
//      output.append(SgmlUtil.createElement(indent, null, ALBUM_DESCRIPTION_TITLE_TAG, album.getDescriptionTitle())).append(NEWLINE);
//    }
//    if (album.isSetDescription()) {
//      String albumDescription = album.getDescription();
//      albumDescription = HtmlUtil.embedInCDataIfTextContainsHtmlMarkup(albumDescription);
//      output.append(SgmlUtil.createElement(indent, null, ALBUM_DESCRIPTION_TAG, albumDescription)).append(NEWLINE);
//    }
//
//    // Comments
//    if (album.isSetComments()) {
//      output.append(SgmlUtil.createElement(indent, null, COMMENTS_TAG, album.getComments())).append(NEWLINE);
//    }
//
//    // IssuedOn
//    if (album.getIssuedOnMediums().size() != 0) {
//      for (MediumType issuedOnMedium: album.getIssuedOnMediums()) {
//        output.append(SgmlUtil.createElementOpen(indent, null, ISSUED_ON_MEDIUM_TAG));
//        String mediumTypeText = null;
//        switch (issuedOnMedium) {
//        case CD_AUDIO:
//          mediumTypeText = "CD";
//          break;
//
//        case CD_ROM:
//          mediumTypeText = "CD-ROM";
//          break;
//
//        case DVD_ROM:
//          mediumTypeText = "DVD-ROM";
//          break;
//
//        case DVD_VIDEO:
//          mediumTypeText = "DVD";
//          break;
//
//        case LP:
//          mediumTypeText = "LP";
//          break;
//
//        case M2TS:
//          // not allowed
//          break;
//
//        case SACD:
//          mediumTypeText = "SACD";
//          break;
//
//        case SINGLE:
//          mediumTypeText = "Single";
//          break;
//
//        case NOT_SET:
//        default:
//          break;
//        }
//        output.append(mediumTypeText);
//        output.append(SgmlUtil.createElementClose(null, ISSUED_ON_MEDIUM_TAG)).append(NEWLINE);
//      }
//    }
//
//    // Compilation
//    if (album.isCompilation()) {
//      output.append(SgmlUtil.createElementOpenClose(indent, null, COMPILATION_TAG)).append(NEWLINE);
//    }
//
//    // MyInfo
//    output.append(toXmlString(indent, nameSpace, album.getMyInfo(), album instanceof MyCompilation));
//
//    // Discs/Tracks
//    if (album.getDiscs().size() == 1) {
//      Disc disc = album.getDiscs().get(0);
//      for (TrackSpecification trackSpecification: disc.getTracks()) {
//        output.append(toXmlString(indent, nameSpace, trackSpecification));
//      }
//    } else {
//      for (Disc disc: album.getDiscs()) {
//        output.append(toXmlString(indent, nameSpace, disc));
//      }
//    }
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), ALBUM_TAG)).append(NEWLINE);
//
//    return output.toString();
    return null;
  }

//  public static String toXmlString(Indent indent, String nameSpace, Disc disc) {
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), DISC_TAG)).append(NEWLINE);
//    indent.increment();
//
//    // Title/Nr
//    if (disc.isSetTitle()) {
//      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), DISC_NR_TAG, disc.getTitle())).append(NEWLINE);
//    }
//
//    // Tracks
//    for (TrackSpecification trackSpecification: disc.getTracks()) {
//      output.append(toXmlString(indent, nameSpace, trackSpecification));
//    }
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), DISC_TAG)).append(NEWLINE);
//
//    return output.toString();    
//  }

//  public static String toXmlString(Indent indent, String nameSpace, MyInfo myInfo, boolean myCompilation) {
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), MY_INFO_TAG)).append(NEWLINE);
//    indent.increment();
//
//    // MyCompilation
//    if (myCompilation) {
//      output.append(SgmlUtil.createElementOpenClose(indent, null, MY_COMPILATION_TAG)).append(NEWLINE);
//    }
//
//    // IWant
//    if (myInfo.isSetIWant()) {
//      output.append(SgmlUtil.createElement(indent, null, I_WANT_TAG, myInfo.getIWant().getLiteral())).append(NEWLINE);
//    }
//
//    // IHaveOn
//    for (MediumInfo mediumInfo: myInfo.getIHaveOn()) {
//      String mediumInfoText = mediumInfoToText(mediumInfo);
//      if (mediumInfoText != null) {
//        output.append(SgmlUtil.createElement(indent, null, I_HAVE_ON_TAG, mediumInfoText)).append(NEWLINE);
//      }
//    }
//
//    // IWantTracks
//    if (myInfo.getIWantTracks().size() > 0) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), I_WANT_TRACKS_TAG));
//      boolean first = true;
//      for (DiscAndTrackNrs discAndTrackNrs: myInfo.getIWantTracks()) {
//        int discNr = discAndTrackNrs.getDiscNr();
//        for (int trackNr: discAndTrackNrs.getTrackNrs()) {
//          if (first) {
//            first = false;
//          } else {
//            output.append(", ");
//          }
//          if (discNr != -1) {
//            output.append(discNr).append(".");
//          }
//          output.append(trackNr);
//        }
//      }
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), I_WANT_TRACKS_TAG)).append(NEWLINE);
//    }
//
//    // IHaveTracks
//    if (myInfo.getIHaveTracks().size() > 0) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), I_HAVE_TRACKS_TAG));
//      boolean first = true;
//      for (DiscAndTrackNrs discAndTrackNrs: myInfo.getIHaveTracks()) {
//        int discNr = discAndTrackNrs.getDiscNr();
//        for (int trackNr: discAndTrackNrs.getTrackNrs()) {
//          if (first) {
//            first = false;
//          } else {
//            output.append(", ");
//          }
//          if (discNr != -1) {
//            output.append(discNr).append(".");
//          }
//          output.append(trackNr);
//        }
//      }
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), I_HAVE_TRACKS_TAG)).append(NEWLINE);
//    }
//
//    // AlbumReferences
//    for (Album album: myInfo.getAlbumReferences()) {
//      output.append(SgmlUtil.createElement(indent, null, ALBUM_REFERENCE_TAG, album.getTitle())).append(NEWLINE);
//    }
//
//    // CollectionName
//    if (myInfo.isSetCollection()) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), COLLECTION_NAME_TAG));
//      output.append(collectionToText(myInfo.getCollection()));
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), COLLECTION_NAME_TAG)).append(NEWLINE);
//    }
//
//    // Inlay
//
//    // MyComments
//    if (myInfo.isSetMyComments()) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), MY_COMMENTS_TAG));
//      output.append(myInfo.getMyComments());
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), MY_COMMENTS_TAG)).append(NEWLINE);
//    }
//
//    // IveHadOnLP
//    if (myInfo.isIveHadOnLP()) {
//      output.append(SgmlUtil.createElementOpenClose(indent, null, IVE_HAD_ON_LP_TAG)).append(NEWLINE);
//    }
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), MY_INFO_TAG)).append(NEWLINE);
//
//    return output.toString();
//  }

//  private static String collectionToText(Collection collection) {
//    switch (collection) {
//    case EASY_LISTENING:
//      return "Easy Listening";
//
//    case FILM_BACKING_TRACKS:
//      return "Film Backing Tracks";
//
//    case FRANSTALIG:
//      return "Franstalig";
//
//    case KLASSIEK:
//      return "Klassiek";
//
//    case NEDERLANDSTALIG:
//      return "Nederlandstalig";
//
//    case POP:
//      return "Pop";
//
//    case ROCK:
//      return "Rock";
//
//    case NOT_SET:
//      throw new IllegalArgumentException("Collection may not be NOT_SET");
//
//    default:
//      throw new IllegalArgumentException("Unknown Collection value");
//    }
//  }

//  public static String toXmlString(Indent indent, String nameSpace, Player player) {
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), PLAYER_TAG)).append(NEWLINE);
//    indent.increment();
//
//    // PlayerName
//    if (player.isSetArtist()) {
//      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), PLAYER_NAME_TAG, player.getArtist().getName())).append(NEWLINE);
//    }
//
//    // PlayerInstruments
//    if (player.getInstruments().size() != 0) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), PLAYER_INSTRUMENTS_TAG));
//      boolean first = true;
//      for (String instrument: player.getInstruments()) {
//        if (first) {
//          first = false;
//        } else {
//          output.append(", ");
//        }
//        output.append(instrument);
//      }
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), PLAYER_INSTRUMENTS_TAG)).append(NEWLINE);
//    }
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), PLAYER_TAG)).append(NEWLINE);
//
//    return output.toString();
//  }

//  public static String toXmlString(Indent indent, String nameSpace, TrackSpecification trackSpecification) {
//    if (trackSpecification instanceof Track) {
//      return toXmlString(indent, nameSpace, (Track) trackSpecification);
//    } else if (trackSpecification instanceof TrackReference) {
//      return toXmlString(indent, nameSpace, (TrackReference) trackSpecification);
//    } else {
//      throw new IllegalArgumentException("Unknown subtype of TrackSpecification: " + trackSpecification.getClass().getName());
//    }
//  }

//  public static String toXmlString(Indent indent, String nameSpace, Track track) {
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
//    indent.increment();
//
//    // Title
//    if (track.isSetTitle()) {
//      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_TITLE_TAG, track.getTitle())).append(NEWLINE);
//    }
//
//    // Artist
//    if (track.isSetArtist()) {
//      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_ARTIST_TAG, track.getArtist().getName())).append(NEWLINE);
//    }
//
//    // Parts
//    if (track.getParts().size() != 0) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_PARTS_TAG)).append(NEWLINE);
//      indent.increment();
//
//      for (TrackPart trackPart: track.getParts()) {
//        output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_PART_TAG, trackPart.getTitle())).append(NEWLINE);
//      }
//
//      indent.decrement();
//      output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_PARTS_TAG)).append(NEWLINE);
//    }
//
//    // Duration
//    if (track.isSetDuration()) {
//      int durationInSeconds = track.getDuration();
//      ClockTime duration = new ClockTime(durationInSeconds);
//      String durationText = CTF.format(duration);
//      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_DURATION_TAG, durationText)).append(NEWLINE);
//    }
//
//    // Authors
//    if (track.getAuthors().size() != 0) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_AUTHOR_TAG));
//      boolean first = true;
//      for (Artist author: track.getAuthors()) {
//        if (first) {
//          first = false;
//        } else {
//          output.append(", ");
//        }
//        output.append(author.getName());
//      }
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), TRACK_AUTHOR_TAG)).append(NEWLINE);
//    }
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
//
//    return output.toString();
//  }

//  public static String toXmlString(Indent indent, String nameSpace, TrackReference trackReference) {
//    Track referredTrack = trackReference.getTrack();
//    Album referredAlbum = referredTrack.getDisc().getAlbum();
//
//    StringBuilder output = new StringBuilder();
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
//    indent.increment();
//
//    NumberFormat formatter = new DecimalFormat("00");
//    String trackTitle = FDF.format(referredAlbum.getReleaseDate()) + " - " +
//        referredAlbum.getTitle() + " - " +
//        formatter.format(referredTrack.getTrackNr()) + " - " +
//        referredTrack.getTitle();
//    output.append(SgmlUtil.createElement(indent, null, TRACK_TITLE_TAG, trackTitle)).append(NEWLINE);
//
//    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TAG)).append(NEWLINE);
//    indent.increment();
//
//    output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), ALBUM_TITLE_TAG, referredAlbum.getTitle())).append(NEWLINE);    
//    output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TRACKNR_TAG, Integer.toString(referredTrack.getTrackNr()))).append(NEWLINE);   
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TAG)).append(NEWLINE);    
//
//    indent.decrement();
//    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
//
//    return output.toString();
//  }

}

enum State {
  START,
  TRACKS,
  TRACK,
  MY_INFO
}
