package goedegep.media.mediadb.albuminfo;

//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackPart;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.datetime.DurationFormat;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;
import goedegep.util.html.HtmlUtil;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

/**
 * This class reads 'AlbumInfo' XML files and stores the information in a <code>MediaDb</code> model.
 * <p>
 * The schema for these files is defined in the xsd file <a href="file:///D:/Database/Muziek/Albums/AlbumInfo.xsd">AlbumInfo.xsd</a>.
 *
 */
public class AlbumInfoHandler extends AbstractValidatingHandler<AlbumInfoHandler.State> {
  private static final Logger LOGGER = Logger.getLogger(AlbumInfoHandler.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static MediadbFactory FACTORY = MediadbFactory.eINSTANCE;
  // Formatters/parsers
  private static final FlexDateFormat  FDF = new FlexDateFormat();
  private static final DurationFormat DF = new DurationFormat(true);

  private static final String ALBUMS_TAG = "Albums";
  private static final String ALBUM_TAG = "Album";
  private static final String ALBUM_DESCRIPTION_TAG = "AlbumDescription";
  private static final String ALBUM_DESCRIPTION_TITLE_TAG = "AlbumDescriptionTitle";
  private static final String ALBUM_ID_TAG = "AlbumId";
  private static final String ALBUM_TITLE_TAG = "AlbumTitle";
  private static final String ALBUM_REFERENCE_TAG = "AlbumReference";
  private static final String ARTIST_TAG = "Artist";
  private static final String CATEGORY_TAG = "Category";
  private static final String CD_COVER_BACK_TAG = "CDCoverBack";
  private static final String CD_COVER_FRONT_TAG = "CDCoverFront";
  private static final String CD_COVER_INSIDE_TAG = "CDCoverInside";
  private static final String CD_LABEL_TAG = "CDLabel";
  private static final String COLLECTION_NAME_TAG = "CollectionName";
  private static final String COMPILATION_TAG = "Compilation";
  private static final String DISC_TAG = "Disc";
  private static final String DISC_NR_TAG = "DiscNr";
  private static final String IVE_HAD_ON_LP_TAG = "IveHadOnLP";
  private static final String I_HAVE_ON_TAG = "IHaveOn";
  private static final String I_HAVE_TRACKS_TAG = "IHaveTracks";
  private static final String I_WANT_TAG = "IWant";
  private static final String I_WANT_TRACKS_TAG = "IWantTracks";
  private static final String ISSUE_DATE_TAG = "IssueDate";
  private static final String ISSUED_ON_MEDIUM_TAG = "IssuedOnMedium";
  private static final String ITALICS_TAG = "Italics";
  private static final String LINE_BREAK_TAG = "br";
  private static final String MY_COMMENTS_TAG = "MyComments";
  private static final String MY_COMPILATION_TAG = "MyCompilation";
  private static final String MY_INFO_TAG = "MyInfo";
  private static final String PLAYER_TAG = "Player";
  private static final String PLAYER_INSTRUMENTS_TAG = "PlayerInstruments";
  private static final String PLAYER_NAME_TAG = "PlayerName";
  private static final String TRACK_DURATION_TAG = "TrackDuration";
  private static final String TRACK_TAG = "Track";
  private static final String TRACK_ARTIST_TAG = "TrackArtist";
  private static final String TRACK_AUTHOR_TAG = "TrackAuthor";
  private static final String TRACK_PART_TAG = "TrackPart";
  private static final String TRACK_PARTS_TAG = "TrackParts";
  private static final String TRACK_REFERENCE_TAG = "Reference";
  private static final String TRACK_REFERENCE_ARTIST_TAG = "Artist";
  private static final String TRACK_REFERENCE_ALBUM_TITLE_TAG = "AlbumTitle";
  private static final String TRACK_REFERENCE_DISCNR_TAG = "DiscNr";
  private static final String TRACK_REFERENCE_TRACKNR_TAG = "TrackNr";
  private static final String TRACK_TITLE_TAG = "TrackTitle";
  private static final String BONUS_TRACK_TAG = "BonusTrack";

  private String              albumsFileName = null;  // Will be used for generating detailed error messages.
  
  // Following two attributes aren't used currently, as there is no write() function implemented yet.
  @SuppressWarnings("unused")
  private static String       currentAlbumsFileName = null;
  @SuppressWarnings("unused")
  private static String       schema = null;

  private List<AlbumInfoErrorInfo> errors;
  
  private MediaDb mediaDb;
  // It is possible that when an 'AlbumReference' is found, the referred to Album doesn't exist yet.
  // Therefore the 'AlbumReferences' are stored in a separate list, and they are dereferenced after parsing the file(s).
  private List<AlbumReferenceInfo> albumReferences = new ArrayList<>();
  private List<Album> albumsInCurrentInfoFile = new ArrayList<>();
  // It is possible that when a 'Reference' (track reference) is found, the referred to Track (on an Album) doesn't exist yet.
  // Therefore the 'TrackReferences' are stored in a separate list, and they are dereferenced after parsing the file(s).
  private List<TrackReferenceInfo> trackReferences = new ArrayList<>();
  private TrackReferenceInfo trackReferenceInfo;
  private boolean soundtrack;      // if true, the album is a soundtrack.
  private Album album;
  private MyAlbumInfo myAlbumInfo;
  private Artist containerArtist;  // Same for all artists/albums in one file. The name is derived from the file name.
  private MyInfo myInfo;
  private TrackReference trackReference;
  private Track track;  // Upon a start TRACK_TAG, besides the trackReference, also a track is created. However this track is only added as track to the mediaDb if it isn't a reference (a track of a MyCompilation album).
  private Disc disc;
  private Player player;
  private boolean isBonusTrack;
  private String bonusTrackText;
  
  // The TRACK_TAG can appear directly under the ALBUM_TAG (referred to as a Top Level Track), or under the DISC_TAG.
  // In the MediaDb model tracks are always part of a disc. So in the first case a 'disc' is created when the first Top Level Track
  // is found. This 'disc' is added to the album when the album closing tag is seen, and 'topLevelTracks' is set.
  private boolean topLevelTracks;

  public AlbumInfoHandler() {
    setPrintStateChanges(true);
  }
  
  public MediaDb getMediaDb() {
    return mediaDb;
  }

  public void setMediaDb(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
  }

  public List<AlbumInfoErrorInfo> read(String albumsFileName) throws ParseException {
    errors = new ArrayList<>();
    albumReferences.clear();
    trackReferences.clear();
    albumsInCurrentInfoFile.clear();
    state = State.START;
    this.albumsFileName = albumsFileName;
    Path albumsPathName = Paths.get(albumsFileName);
    LOGGER.info("albumsPathName=" + albumsPathName);
    Path albumsFilePath = albumsPathName.getFileName();
    LOGGER.info("albumsFilePath=" + albumsFilePath);
    String albumsPlainFileName = albumsFilePath.toString();
    LOGGER.info("albumsPlainFileName=" + albumsPlainFileName);
    String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(albumsPlainFileName);
    if (fileNameAndExtension[0].equals("Soundtracks")) {
      soundtrack = true;
    } else {
      soundtrack = false;

      String containerArtistName = fileNameAndExtension[0];
      LOGGER.info("containerArtistName=" + containerArtistName);
      if (containerArtistName.startsWith("Artist ")) {
        containerArtistName = containerArtistName.substring(7);
        containerArtist = mediaDb.getArtist(containerArtistName);
        if (containerArtist == null) {
          containerArtist = FACTORY.createArtist();
          containerArtist.setName(containerArtistName);
          mediaDb.getArtists().add(containerArtist);
        }
      } else {
        throw new IllegalArgumentException("albumsFileName doesn't start with \"Artist \": " + albumsFileName);
      }
    }
    try {
      // parse the document
      LOGGER.info("Parse start: " + albumsFileName);
      getParser(null).parse(albumsFileName, this);
      LOGGER.info("Parse ready: " + albumsFileName);
      currentAlbumsFileName = albumsFileName;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(albumsFileName, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
    
    handleReferences();
    
    return errors;
  }
  
  public void write(String albumInfoFile, List<Album> albums) {
    StringBuilder output = new StringBuilder();
    Indent       indent = new Indent(2);

//    if (schema == null) {
//      throw new RuntimeException("Geen schema voor de aanstelling file.");
//    }
    
    try
    {
      // Rename existing file to .bak file
//      File file = new File(albumInfoFile);
//      file.renameTo(new File(albumInfoFile + ".bak"));

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
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), ALBUMS_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de aanstelling file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
    
  }
  
  /**
   * Handle the <code>albumReferences</code>.
   */
  private void handleReferences() {
    for (AlbumReferenceInfo albumReferenceInfo: albumReferences) {
      LOGGER.info("HANDLING REFERENCE: Title: " + albumReferenceInfo.getSourceAlbum().getTitle() +
          ", Reference: " + albumReferenceInfo.getReferencedAlbum());
      Album referencedAlbum = null;
      for (Album album: albumsInCurrentInfoFile) {
        if (album.getTitle().equals(albumReferenceInfo.getReferencedAlbum())) {
          referencedAlbum = album;
          break;
        }
      }
      if (referencedAlbum != null) {
        Artist referencedAlbumArtist = referencedAlbum.getArtist();
        LOGGER.fine("found candidate, Artist: " + (referencedAlbumArtist != null ? referencedAlbumArtist.getName() : "<no artist>") + ", title: " + referencedAlbum.getTitle());
        albumReferenceInfo.getSourceAlbum().getMyInfo().getAlbumReferences().add(referencedAlbum); 
      } else {
        LOGGER.severe("No candidate found: Artist=" + albumReferenceInfo.getSourceAlbum().getArtist() +
                      ", Album=" + albumReferenceInfo.getReferencedAlbum());
        AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
        error.setErrorCode(AlbumInfoError.REFERENCE_NOT_FOUND);
        error.setAlbumReferenceInfo(albumReferenceInfo);
        errors.add(error);
      }
    }

    for (TrackReferenceInfo trackReferenceInfo: trackReferences) {
      Album referencedAlbum = null;
      for (Album album: albumsInCurrentInfoFile) {
        if (album.getTitle().equals(trackReferenceInfo.getReferencedAlbumTitle())) {
          referencedAlbum = album;
          break;
        }
      }
      if (referencedAlbum != null) {
        int discNr = 0;
        if (trackReferenceInfo.getReferencedDiscNr() != -1) {
          discNr = trackReferenceInfo.getReferencedDiscNr() - 1;
        }
        if (discNr >= referencedAlbum.getDiscs().size()) {
          LOGGER.severe("DiscNr too high for track reference: " + trackReferenceInfo.toString());
          AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
          error.setErrorCode(AlbumInfoError.DISC_NR_TOO_HIGH);
          error.setTrackReferenceInfo(trackReferenceInfo);
          errors.add(error);
          
          continue;
        }
        Disc disc = referencedAlbum.getDiscs().get(discNr);
        if (trackReferenceInfo.getReferencedTrackNr() > disc.getTrackReferences().size()) {
          LOGGER.severe("TrackNr too high for track reference: " + trackReferenceInfo.toString());
          AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
          error.setErrorCode(AlbumInfoError.TRACK_NR_TOO_HIGH);
          error.setTrackReferenceInfo(trackReferenceInfo);
          errors.add(error);
          
          continue;
        }
        Track track = disc.getTrackReferences().get(trackReferenceInfo.getReferencedTrackNr() - 1).getTrack();
        TrackReference trackReference = trackReferenceInfo.getSourceTrackReference();
        trackReference.setTrack(track);
      } else {
        LOGGER.severe("No candidate found for track reference: " + trackReferenceInfo.toString());
        AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
        error.setErrorCode(AlbumInfoError.TRACK_REFERENCE_NOT_FOUND);
        error.setTrackReferenceInfo(trackReferenceInfo);
        errors.add(error);
      }
    }
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    LOGGER.fine("=> " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    
    // The album description contains <br/> tags.
    if (state != State.ALBUM_DESCRIPTION) {
      data = null;
    }
    
    switch (state) {
    case START:
      // Only ALBUMS_TAG expected.
      if (tag.compareTo(ALBUMS_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.ALBUMS);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case ALBUMS:
      // Only ALBUM_TAG expected.
      if (tag.compareTo(ALBUM_TAG) == 0) {
        album = FACTORY.createAlbum();
        myAlbumInfo = new MyAlbumInfo();
        disc = null;
        topLevelTracks = false;
        pushState(State.ALBUM);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ". File: " + albumsFileName + ", line: "+ locator.getLineNumber());
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case ALBUM:
      // No state change for tags which have no sub elements.
      // This applies to: ARTIST_TAG, ALBUM_TITLE_TAG, ALBUM_ID_TAG, ISSUE_DATE_TAG, CD_COVER_FRONT_TAG, CD_COVER_BACK_TAG,
      //                  CD_COVER_INSIDE_TAG, CD_LABEL_TAG, ALBUM_DESCRIPTION_TITLE_TAG, COMPILATION_TAG,
      //                  ISSUED_ON_MEDIUM_TAG.
      // State change for tags which do have sub elements.
      // This applies to: ALBUM_DESCRIPTION_TAG, PLAYER_TAG, MY_INFO_TAG, TRACK_TAG, DISC_TAG.
      if (tag.compareTo(ARTIST_TAG) == 0) {
        // No action
      } else if (tag.compareTo(ALBUM_TITLE_TAG) == 0) {
        // No action
      } else if (tag.compareTo(ALBUM_ID_TAG) == 0) {
        // No action
      } else if (tag.compareTo(ISSUE_DATE_TAG) == 0) {
        // No action
      } else if (tag.compareTo(CD_COVER_FRONT_TAG) == 0) {
        // No action
      } else if (tag.compareTo(CD_COVER_BACK_TAG) == 0) {
        // No action
      } else if (tag.compareTo(CD_COVER_INSIDE_TAG) == 0) {
        // No action
      } else if (tag.compareTo(CD_LABEL_TAG) == 0) {
        // No action
      } else if (tag.compareTo(ALBUM_DESCRIPTION_TITLE_TAG) == 0) {
        // No action
      } else if (tag.compareTo(ALBUM_DESCRIPTION_TAG) == 0) {
        pushState(State.ALBUM_DESCRIPTION);
      } else if (tag.compareTo(COMPILATION_TAG) == 0) {
        // No action
      } else if (tag.compareTo(PLAYER_TAG) == 0) {
        player = FACTORY.createPlayer();
        pushState(State.PLAYER);
      } else if (tag.compareTo(ISSUED_ON_MEDIUM_TAG) == 0) {
        // No action
      } else if (tag.compareTo(MY_INFO_TAG) == 0) {
        myInfo = FACTORY.createMyInfo();
        pushState(State.MY_INFO);
      } else if (tag.compareTo(TRACK_TAG) == 0) {
        if (disc == null) {
          disc = FACTORY.createDisc();
        }
        topLevelTracks = true;
        trackReference = FACTORY.createTrackReference();
        track = FACTORY.createTrack();
        isBonusTrack = false;
        bonusTrackText = null;
        pushState(State.TRACK);
      } else if (tag.compareTo(DISC_TAG) == 0) {
        disc = FACTORY.createDisc();
        pushState(State.DISC);
     } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case ALBUM_DESCRIPTION:
      if (tag.compareTo(LINE_BREAK_TAG) == 0) {
        // no action
      } else if (tag.compareTo(ITALICS_TAG) == 0) {
        data = data + "<Italics>";
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case PLAYER:
      if (tag.compareTo(PLAYER_INSTRUMENTS_TAG) == 0) {
        // no action
      } else if (tag.compareTo(PLAYER_NAME_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case MY_INFO:
      if (tag.compareTo(MY_COMPILATION_TAG) == 0) {
        myAlbumInfo.myCompilation = true;
      } else if (tag.compareTo(I_WANT_TAG) == 0) {
        // no action
      } else if (tag.compareTo(I_HAVE_ON_TAG) == 0) {
        // no action
      } else if (tag.compareTo(I_HAVE_TRACKS_TAG) == 0) {
        // no action
      } else if (tag.compareTo(I_WANT_TRACKS_TAG) == 0) {
        // no action
      } else if (tag.compareTo(MY_COMMENTS_TAG) == 0) {
        // no action
      } else if (tag.compareTo(ALBUM_REFERENCE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(COLLECTION_NAME_TAG) == 0) {
        // no action
      } else if (tag.compareTo(IVE_HAD_ON_LP_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ". Found in line " + locator.getLineNumber() + " in file " + albumsFileName);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case TRACK:
      if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_DURATION_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_AUTHOR_TAG) == 0) {
        // no action
      } else if (tag.compareTo(CATEGORY_TAG) == 0) {
        // no action
      } else if (tag.compareTo(BONUS_TRACK_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_TAG) == 0) {
        trackReferenceInfo = new TrackReferenceInfo(album, albumsFileName, locator.getLineNumber(), locator.getColumnNumber());
        pushState(State.TRACK_REFERENCE);
        // no action
      } else if (tag.compareTo(TRACK_PARTS_TAG) == 0) {
        pushState(State.TRACK_PARTS);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ", in file: " + albumsFileName + ", line: " + locator.getLineNumber());
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case TRACK_REFERENCE:
      if (tag.compareTo(TRACK_REFERENCE_ARTIST_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_ALBUM_TITLE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_DISCNR_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_TRACKNR_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case TRACK_PARTS:
      if (tag.compareTo(TRACK_PART_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case DISC:
      if (tag.compareTo(TRACK_TAG) == 0) {
        trackReference = FACTORY.createTrackReference();
        track = FACTORY.createTrack();
        isBonusTrack = false;
        bonusTrackText = null;
        pushState(State.DISC_TRACK);
      } else if (tag.compareTo(DISC_NR_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case DISC_TRACK:
      if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_DURATION_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_AUTHOR_TAG) == 0) {
        // no action
      } else if (tag.compareTo(BONUS_TRACK_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_TAG) == 0) {
        trackReferenceInfo = new TrackReferenceInfo(album, albumsFileName, locator.getLineNumber(), locator.getColumnNumber());
        pushState(State.DISC_TRACK_REFERENCE);
        // no action
      } else if (tag.compareTo(TRACK_PARTS_TAG) == 0) {
        pushState(State.DISC_TRACK_PARTS);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case DISC_TRACK_REFERENCE:
      if (tag.compareTo(TRACK_REFERENCE_ARTIST_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_ALBUM_TITLE_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_DISCNR_TAG) == 0) {
        // no action
      } else if (tag.compareTo(TRACK_REFERENCE_TRACKNR_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case DISC_TRACK_PARTS:
      if (tag.compareTo(TRACK_PART_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
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

    case ALBUMS:
      if (tag.compareTo(ALBUMS_TAG) == 0) {
        popState();
      }
      break;

    case ALBUM:
      if (tag.compareTo(ALBUM_TAG) == 0) {
        if (soundtrack) {
          album.setSoundtrack(true);
        }
        
        if (topLevelTracks) {
          album.getDiscs().add(disc);
        }
        
        // Handle MyAlbumInfo
        if (myAlbumInfo.iWant != IWant.NOT_SET  &&  !myAlbumInfo.tracksIWant.isEmpty()) {
          AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
          error.setErrorCode(AlbumInfoError.IWANT_SET_ON_ALBUM_AND_TRACK_LEVEL);
          AlbumReferenceInfo albumReferenceInfo = new AlbumReferenceInfo(albumsFileName, -1, -1, album, null);
          error.setAlbumReferenceInfo(albumReferenceInfo);
        }
        MediaDbUtil.setIWant(album, myAlbumInfo.iWant);
        
        if (!myAlbumInfo.tracksIWant.isEmpty()) {
          for (DiscAndTrackNrs discAndTrackNrs: myAlbumInfo.tracksIWant) {
            int discNr = discAndTrackNrs.getDiscNr();
            if (discNr == -1) {
              discNr = 1;
            }
            Disc disc = album.getDiscs().get(discNr - 1);
            for (int trackNr: discAndTrackNrs.getTrackNrs()) {
              TrackReference trackReference = disc.getTrackReferences().get(trackNr - 1);
              MyTrackInfo myTrackInfo;
              if (!trackReference.isSetMyTrackInfo()) {
                myTrackInfo = FACTORY.createMyTrackInfo();
                trackReference.setMyTrackInfo(myTrackInfo);
              } else {
                myTrackInfo = trackReference.getMyTrackInfo();
              }
              myTrackInfo.setIWant(IWant.YES);
            }
          }
        }
        
        if (!myAlbumInfo.iHaveOn.isEmpty()  &&  !myAlbumInfo.tracksIHave.isEmpty()) {
          AlbumInfoErrorInfo error = new AlbumInfoErrorInfo();
          error.setErrorCode(AlbumInfoError.IHAVE_SET_ON_ALBUM_AND_TRACK_LEVEL);
          AlbumReferenceInfo albumReferenceInfo = new AlbumReferenceInfo(albumsFileName, -1, -1, album, null);
          error.setAlbumReferenceInfo(albumReferenceInfo);
        }
        MediaDbUtil.setIHaveOn(album, myAlbumInfo.iHaveOn);
        
        if (!myAlbumInfo.tracksIHave.isEmpty()) {
          // By definition, if I only have some tracks of an album, it should be in the MusicFolder (indicated by HARDDISK).
          MediumInfo mediumInfo = FACTORY.createMediumInfo();
          mediumInfo.setMediumType(MediumType.HARDDISK);
          for (DiscAndTrackNrs discAndTrackNrs: myAlbumInfo.tracksIHave) {
            int discNr = discAndTrackNrs.getDiscNr();
            if (discNr == -1) {
              discNr = 1;
            }
            Disc disc = album.getDiscs().get(discNr - 1);
            for (int trackNr: discAndTrackNrs.getTrackNrs()) {
              TrackReference trackReference = disc.getTrackReferences().get(trackNr - 1);
              MyTrackInfo myTrackInfo;
              if (!trackReference.isSetMyTrackInfo()) {
                myTrackInfo = FACTORY.createMyTrackInfo();
                trackReference.setMyTrackInfo(myTrackInfo);
              } else {
                myTrackInfo = trackReference.getMyTrackInfo();
              }
              myTrackInfo.getIHaveOn().add(MediaDbUtil.createMediumInfoCopy(mediumInfo));
              if (myAlbumInfo.collection != Collection.NOT_SET) {
                myTrackInfo.setCollection(myAlbumInfo.collection);
              }
            }
          }
        }
        
        if (myAlbumInfo.albumType != null) {
          MediaDbUtil.setAlbumType(album, myAlbumInfo.albumType);
        }
        
        mediaDb.getAlbums().add(album);
        albumsInCurrentInfoFile.add(album);
        popState();
      } else if (tag.compareTo(ARTIST_TAG) == 0) {
        Artist artist = getArtist(data.trim());
        if (!artist.getName().equals("Various Artists")  &&  !artist.isSetContainerArtist()  &&  !artist.getName().equals(containerArtist.getName())) {
          artist.setContainerArtist(containerArtist);
        }
        album.setArtist(artist);
      } else if (tag.compareTo(ALBUM_TITLE_TAG) == 0) {
        album.setTitle(data.trim());
      } else if (tag.compareTo(ALBUM_ID_TAG) == 0) {
        album.setId(data.trim());
      } else if (tag.compareTo(ISSUE_DATE_TAG) == 0) {
        FlexDate issueDate;
        try {
          issueDate = FDF.parse(data.trim());
          album.setReleaseDate(issueDate);
        } catch (java.text.ParseException e) {
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), e.getMessage());
        }
      } else if (tag.compareTo(CD_COVER_FRONT_TAG) == 0) {
        album.getImagesFront().add(data.trim());
      } else if (tag.compareTo(CD_COVER_BACK_TAG) == 0) {
        album.getImagesBack().add(data.trim());
      } else if (tag.compareTo(CD_COVER_INSIDE_TAG) == 0) {
        album.getImagesFrontInside().add(data.trim());
      } else if (tag.compareTo(CD_LABEL_TAG) == 0) {
        album.getImagesLabel().add(data.trim());
      } else if (tag.compareTo(ALBUM_DESCRIPTION_TITLE_TAG) == 0) {
        album.setDescriptionTitle(data.trim());
      } else if (tag.compareTo(ALBUM_DESCRIPTION_TAG) == 0) {
        album.setDescription(data.trim());
      } else if (tag.compareTo(COMPILATION_TAG) == 0) {
        album.setCompilation(true);
      } else if (tag.compareTo(ISSUED_ON_MEDIUM_TAG) == 0) {
        album.getIssuedOnMediums().add(getMediumTypeFromText(data.trim()));
      } else if (tag.compareTo(MY_INFO_TAG) == 0) {
        album.setMyInfo(myInfo);
      }
      break;
      
    case ALBUM_DESCRIPTION:
      if (tag.compareTo(ALBUM_DESCRIPTION_TAG) == 0) {
        album.setDescription(data.trim().replace("\n", "\r\n"));
        popState();
      } else if (tag.compareTo(LINE_BREAK_TAG) == 0) {
        data = data + "<br/>";
      } else if (tag.compareTo(ITALICS_TAG) == 0) {
        data = data + "</Italics>";
      }
      break;
      
    case PLAYER:
      if (tag.compareTo(PLAYER_TAG) == 0) {
        album.getPlayers().add(player);
        popState();
      } else if (tag.compareTo(PLAYER_INSTRUMENTS_TAG) == 0) {
        String[] instruments = data.split(",");
        for (String instrument: instruments) {
          player.getInstruments().add(instrument.trim());
        }
      } else if (tag.compareTo(PLAYER_NAME_TAG) == 0) {
        player.setArtist(getArtist(data.trim()));
      }
      break;
      
    case MY_INFO:
      if (tag.compareTo(MY_INFO_TAG) == 0) {
        album.setMyInfo(myInfo);
        popState();
      } else if (tag.compareTo(MY_COMPILATION_TAG) == 0) {
//        album = convertAlbumToMyCompilation(album);
        myAlbumInfo.albumType = AlbumType.OWN_COMPILATION_ALBUM;
      } else if (tag.compareTo(I_WANT_TAG) == 0) {
        IWant iWant = getIWantFromText(data);
        if (iWant != null) {
          myAlbumInfo.iWant = iWant;
        }
      } else if (tag.compareTo(I_HAVE_ON_TAG) == 0) {
        MediumInfo mediumInfo = getMediumInfoFromText(data);
        myAlbumInfo.iHaveOn.add(mediumInfo);
      } else if (tag.compareTo(MY_COMMENTS_TAG) == 0) {
        myInfo.setMyComments(data.trim().replace("\n", "\r\n"));
      } else if (tag.compareTo(I_HAVE_TRACKS_TAG) == 0) {
        if (data != null) {
          List<DiscAndTrackNrs> discAndTrackNrsList = getDiscAndTrackNrsFromText(data);
          myAlbumInfo.tracksIHave = discAndTrackNrsList;
//          myInfo.getIHaveTracks().addAll(discAndTrackNrsList);
        }
      } else if (tag.compareTo(I_WANT_TRACKS_TAG) == 0) {
        if (data != null) {
          List<DiscAndTrackNrs> discAndTrackNrsList = getDiscAndTrackNrsFromText(data);
          myAlbumInfo.tracksIWant.addAll(discAndTrackNrsList);
        }
      } else if (tag.compareTo(COLLECTION_NAME_TAG) == 0) {
        Collection collection = Collection.get(data.trim());
        if (collection != null) {
          myAlbumInfo.collection = collection;
        } else {
          LOGGER.severe("No Collection found for CollectionName: " + data.trim());
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldige collectie '" + data + "' in MyInfo.");
        }
      } else if (tag.compareTo(IVE_HAD_ON_LP_TAG) == 0) {
        myInfo.setIveHadOnLP(true);
      } else if (tag.compareTo(ALBUM_REFERENCE_TAG) == 0) {
        AlbumReferenceInfo albumReferenceInfo = new AlbumReferenceInfo(albumsFileName, locator.getLineNumber(), locator.getColumnNumber(),
            album, data.trim());
        albumReferences.add(albumReferenceInfo);
      } else {
        LOGGER.severe("Illegal end element: " + tag + " in state: " + state);
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig eind element: '" + tag + "'.");
      }
      break;
      
    case TRACK:
      if (tag.compareTo(TRACK_TAG) == 0) {
        if (trackReferenceInfo != null) {
          TrackReference trackReference = trackReferenceInfo.getSourceTrackReference();
          List<Album> referencedAlbums = mediaDb.getAlbums(null, album.getArtist(), trackReferenceInfo.getReferencedAlbumTitle());
          if (referencedAlbums.size() == 1) {
            Album referencedAlbum = referencedAlbums.get(0);
            int referencedDiscNr = trackReferenceInfo.getReferencedDiscNr();
            if (referencedDiscNr == -1) {
              referencedDiscNr = 1;
            }
            Disc referencedDisc = referencedAlbum.getDiscs().get(referencedDiscNr - 1);
            int referencedTrackNr = trackReferenceInfo.getReferencedTrackNr();
            if (referencedTrackNr == 0) {
              LOGGER.severe("referencedTrackNr is 0 for " + referencedAlbum + ", trackReference: " + trackReference);
            }
            TrackReference referencedTrackReference = referencedDisc.getTrackReferences().get(referencedTrackNr - 1);
            trackReference.setOriginalAlbumTrackReference(referencedTrackReference);
            trackReference.setTrack(referencedTrackReference.getTrack());
            if (isBonusTrack) {
              trackReference.setBonusTrack(bonusTrackText);
            }
            disc.getTrackReferences().add(trackReference);
            trackReferenceInfo = null;
          }
        } else {
          track.setOriginalDisc(disc);
          mediaDb.getTracks().add(track);
          TrackReference trackReference = FACTORY.createTrackReference();
          trackReference.setTrack(track);
          track.getReferredBy().add(trackReference);
          if (isBonusTrack) {
            trackReference.setBonusTrack(bonusTrackText);
          }
          disc.getTrackReferences().add(trackReference);
        }
        popState();
      } else if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        track.setTitle(data.trim());
      } else if (tag.compareTo(TRACK_DURATION_TAG) == 0) {
        track.setDuration(getDurationInSeconds(data.trim()));
      } else if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        Artist artist = getArtist(data.trim());
        LOGGER.info("Setting artist: " + artist.getName() + " for album: " + album.getTitle());
        track.setArtist(artist);
      } else if (tag.compareTo(TRACK_AUTHOR_TAG) == 0) {
        String[] authors = data.split(",");
        for (String author: authors) {
          Artist artist = getArtist(author.trim());
          track.getAuthors().add(artist);
        }
      } else if (tag.compareTo(BONUS_TRACK_TAG) == 0) {
        isBonusTrack = true;
        if (data != null) {
          bonusTrackText = data.trim();
        }
      } else if (tag.compareTo(CATEGORY_TAG) == 0) {
        Collection collection = Collection.get(data.trim());
        if (collection != null) {
          MyTrackInfo myTrackInfo = FACTORY.createMyTrackInfo();
          myTrackInfo.setCollection(collection);
          trackReference.setMyTrackInfo(myTrackInfo);
        } else {
          LOGGER.severe("No Collection found for CollectionName: " + data.trim());
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldige category '" + data + "'.");
        }
      }
      break;
      
    case TRACK_REFERENCE:
      if (tag.compareTo(TRACK_REFERENCE_TAG) == 0) {
        trackReferences.add(trackReferenceInfo);
        popState();
      } else if (tag.compareTo(TRACK_REFERENCE_ALBUM_TITLE_TAG) == 0) {
        trackReferenceInfo.setReferencedAlbumTitle(data);
      } else if (tag.compareTo(TRACK_REFERENCE_DISCNR_TAG) == 0) {
        trackReferenceInfo.setReferencedDiscNr(Integer.valueOf(data));
      } else if (tag.compareTo(TRACK_REFERENCE_TRACKNR_TAG) == 0) {
        // the value is either a track number, or a disc number and a tracknumber separated by a '.'.
        String [] referenceTrackNrParts = data.split("\\.");
        if (referenceTrackNrParts.length == 1) {
          trackReferenceInfo.setReferencedTrackNr(Integer.valueOf(referenceTrackNrParts[0]));
        } else {
          trackReferenceInfo.setReferencedDiscNr(Integer.valueOf(referenceTrackNrParts[0]));
          trackReferenceInfo.setReferencedTrackNr(Integer.valueOf(referenceTrackNrParts[1]));
        }
      }
      break;
      
    case TRACK_PARTS:
      if (tag.compareTo(TRACK_PARTS_TAG) == 0) {
        popState();
      } else if (tag.compareTo(TRACK_PART_TAG) == 0) {
        TrackPart trackPart = FACTORY.createTrackPart();
        trackPart.setTitle(data);
        track.getParts().add(trackPart);
      }
      break;

    case DISC:
      if (tag.compareTo(DISC_TAG) == 0) {
        album.getDiscs().add(disc);
        popState();
      } else if (tag.compareTo(DISC_NR_TAG) == 0) {
        disc.setTitle(data);
      }
      break;
      
    case DISC_TRACK:
      if (tag.compareTo(TRACK_TAG) == 0) {
        if (trackReferenceInfo != null) {  // TODO why is there no albumTrackReference for topLevelTracks?
          if (isBonusTrack) {
            trackReferenceInfo.getSourceTrackReference().setBonusTrack(bonusTrackText);
          }
          disc.getTrackReferences().add(trackReferenceInfo.getSourceTrackReference());
          trackReferenceInfo = null;
        } else {
//          if (album.isSetArtist()) {
//            track.setArtist(album.getArtist());
//          }
          track.setOriginalDisc(disc);
          mediaDb.getTracks().add(track);
          TrackReference trackReference = FACTORY.createTrackReference();
          trackReference.setTrack(track);
          track.getReferredBy().add(trackReference);
          if (isBonusTrack) {
            trackReference.setBonusTrack(bonusTrackText);
          }
          disc.getTrackReferences().add(trackReference);
        }
        popState();
      } else if (tag.compareTo(TRACK_TITLE_TAG) == 0) {
        track.setTitle(data);
      } else if (tag.compareTo(TRACK_DURATION_TAG) == 0) {
        track.setDuration(getDurationInSeconds(data));
      } else if (tag.compareTo(TRACK_ARTIST_TAG) == 0) {
        Artist artist = getArtist(data.trim());
        LOGGER.info("Setting artist: " + artist.getName() + " for album: " + album.getTitle());
        track.setArtist(artist);
      } else if (tag.compareTo(BONUS_TRACK_TAG) == 0) {
        isBonusTrack = true;
        if (data != null) {
          bonusTrackText = data.trim();
        }
      } else if (tag.compareTo(TRACK_AUTHOR_TAG) == 0) {
        String[] authors = data.split(",");
        for (String author: authors) {
          Artist artist = getArtist(author.trim());
          track.getAuthors().add(artist);
        }
      }
      break;
      
    case DISC_TRACK_REFERENCE:
      if (tag.compareTo(TRACK_REFERENCE_TAG) == 0) {
        trackReferences.add(trackReferenceInfo);
        popState();
      } else if (tag.compareTo(TRACK_REFERENCE_ALBUM_TITLE_TAG) == 0) {
        trackReferenceInfo.setReferencedAlbumTitle(data);
      } else if (tag.compareTo(TRACK_REFERENCE_DISCNR_TAG) == 0) {
        trackReferenceInfo.setReferencedDiscNr(Integer.valueOf(data));
      } else if (tag.compareTo(TRACK_REFERENCE_TRACKNR_TAG) == 0) {
        trackReferenceInfo.setReferencedTrackNr(Integer.valueOf(data));
      }
      break;
      
    case DISC_TRACK_PARTS:
      if (tag.compareTo(TRACK_PARTS_TAG) == 0) {
        popState();
      } else if (tag.compareTo(TRACK_PART_TAG) == 0) {
        TrackPart trackPart = FACTORY.createTrackPart();
        trackPart.setTitle(data);
        track.getParts().add(trackPart);
      }
      break;
    }
  }
  
  /**
   * Translate the comma separated list of disc and track numbers, to a list of <code>DiscAndTrackNrs</code>.
   * 
   * @param text the comma separated list of disc and track numbers.
   * @returna a list of <code>DiscAndTrackNrs</code> derived from the <code>text</code>.
   */
  private List<DiscAndTrackNrs> getDiscAndTrackNrsFromText(String text) {
    List<DiscAndTrackNrs> discAndTracksNrsList;
    String[] trackIds = text.split(",");
    
    // First determine if discNrs are specified, which is the case if any 'trackId' contains a dot.
    boolean useDiscNrs = false;
    for (String trackId: trackIds) {
      trackId = trackId.trim();
      if (trackId.indexOf(".") != -1) {
        useDiscNrs = true;
        break;
      }
    }
    
    if (useDiscNrs) {
      discAndTracksNrsList = getMultiDiscDiscAndTrackNrsFromText(trackIds);
    } else {
      discAndTracksNrsList = getSingleDiscDiscAndTrackNrsFromText(trackIds);
    }
    
    // Just for debugging
    for (DiscAndTrackNrs discAndTrackNrs: discAndTracksNrsList) {
      StringBuilder buf = new StringBuilder();
      buf.append("  TrackNrs:");
      for (int trackNr: discAndTrackNrs.getTrackNrs()) {
        buf.append(" ").append(trackNr);
      }
      LOGGER.info(buf.toString());
    }
    
    return discAndTracksNrsList;
  }
  
  private List<DiscAndTrackNrs> getSingleDiscDiscAndTrackNrsFromText(String[] trackIds) {
    List<DiscAndTrackNrs> discAndTracksNrsList = new ArrayList<>();
    
    DiscAndTrackNrs discAndTrackNrs = FACTORY.createDiscAndTrackNrs();
    discAndTrackNrs.setDiscNr(-1);
    for (String trackId: trackIds) {
      if (trackId.indexOf("-") != -1) {
        String[] trackRangeParts = trackId.split("-");
        if (trackRangeParts.length != 2) {
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldig track nummer '" + data + "' in IHaveTracks.");
        }
        try {
          int fromTrackNr = Integer.parseUnsignedInt(trackRangeParts[0].trim());
          int toTrackNr = Integer.parseUnsignedInt(trackRangeParts[0].trim());
          for (int trackNr = fromTrackNr; trackNr <= toTrackNr; trackNr++) {
            discAndTrackNrs.getTrackNrs().add(trackNr);
          }
        } catch (NumberFormatException e) {
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldig track nummer '" + data + "' in IHaveTracks.");
        }
      } else {
        try {
          int trackNr = Integer.parseUnsignedInt(trackId.trim());
          discAndTrackNrs.getTrackNrs().add(trackNr);
        } catch (NumberFormatException e) {
          throw new ParseException(albumsFileName, locator.getLineNumber(),
              locator.getColumnNumber(), "ongeldig track nummer '" + data + "' in IHaveTracks.");
        }
      }
    }
    discAndTracksNrsList.add(discAndTrackNrs);
    
    return discAndTracksNrsList;
  }
  
  private List<DiscAndTrackNrs> getMultiDiscDiscAndTrackNrsFromText(String[] trackIds) {
    List<DiscAndTrackNrs> discAndTracksNrsList = new ArrayList<>();
    
    int discNr = -1;
    DiscAndTrackNrs discAndTrackNrs = null;
    
    for (String trackId: trackIds) {
      trackId = trackId.trim();
      String[] trackIdParts = trackId.split("\\.");
      if (trackIdParts.length != 2) {
        LOGGER.severe("Length not 2");
        throw new ParseException(albumsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig track nummer '" + data + "' in IWantTracks.");
      }
      int trackDiscNr = Integer.parseUnsignedInt(trackIdParts[0].trim());
      int trackNr = Integer.parseUnsignedInt(trackIdParts[1].trim());
      if (trackDiscNr != discNr) {
        if (discAndTrackNrs != null) {
          discAndTracksNrsList.add(discAndTrackNrs);
        }
        discAndTrackNrs = FACTORY.createDiscAndTrackNrs();
        discAndTrackNrs.setDiscNr(trackDiscNr);
        discNr = trackDiscNr;
      }
      
      // trackNr zero indicates all tracks of this disc. This translates to an empty list of trackNrs.
      if (trackNr != 0) {
        discAndTrackNrs.getTrackNrs().add(trackNr);
      }
    }
    discAndTracksNrsList.add(discAndTrackNrs);
    
    return discAndTracksNrsList;
  }
  
//  private String discAndTrackNrsToText(DiscAndTrackNrs discAndTrackNrs) {
//    // TODO implement and use
//    return null;
//  }
  
  /**
   * Get a time in seconds from a String.
   * 
   * @param clockTimeString Time in 'ClockTime' format.
   * @return The clockTimeString translated to seconds.
   */
  private int getDurationInSeconds(String clockTimeString) {
    Duration clockTime = (Duration) DF.parse(clockTimeString);
    return (int) clockTime.getSeconds();
  }
  
  /**
   * Get the MediumType from a textual representation.
   * @param data The textual representation of the Medium Type.
   * @return The 'data' translated to a MediumType, or null if no match is found.
   */
  private MediumType getMediumTypeFromText(String data) {
    MediumType mediumType = null;
    
    switch (data) {
    case "LP":
      mediumType = MediumType.LP;
      break;
      
    case "CD":
      mediumType = MediumType.CD_AUDIO;
      break;
      
    default:
      LOGGER.severe("Wrong medium type: " + data);
      throw new ParseException(albumsFileName, locator.getLineNumber(),
          locator.getColumnNumber(), "ongeldig medium type: '" + data + "'.");
    }
    
    return mediumType;
  }

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
    } else if (data.equals("CDRW-FLAC/LP")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.FLAC);
      mediumInfo.getSourceTypes().add(InformationType.VINYL_ANALOG);
    } else if (data.equals("LP")) {
      mediumInfo.setMediumType(MediumType.LP);
      LOGGER.severe("Wrong IHaveOn LP in file: " + albumsFileName);
      throw new ParseException(albumsFileName, locator.getLineNumber(),
          locator.getColumnNumber(), "Geeft aan dat ik dit op lp heb, maar die heb ik niet meer.");
    } else if (data.equals("SACD")) {
      mediumInfo.setMediumType(MediumType.SACD);
    } else if (data.equals("CDRW-M2TS")) {
      mediumInfo.setMediumType(MediumType.HARDDISK);
      mediumInfo.getSourceTypes().add(InformationType.M2TS);
    } else {
      LOGGER.severe("Wrong info for medium info: " + data + ", file: " + albumsFileName + ", line number: " + locator.getLineNumber());
      throw new ParseException(albumsFileName, locator.getLineNumber(),
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
//      return null;
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
   * Get the IWant value for a text.
   * @param data the text for which the IWant value is to be obtained.
   * @return the IWant value for the given text, or null if no match is found.
   */
  private IWant getIWantFromText(String data) {
    if (data.isEmpty()) {
      return null;
    } else if (data.equalsIgnoreCase("yes")) {
      return IWant.YES;
    } else if (data.equalsIgnoreCase("no")) {
      return IWant.NO;
    } else if (data.equalsIgnoreCase("don't know")) {
      return IWant.DONT_KNOW;
    }
    
    LOGGER.severe("No IWant found for: " + data);
    throw new ParseException(albumsFileName, locator.getLineNumber(),
        locator.getColumnNumber(), "ongeldig tekst voor IWant: '" + data + "'.");
  }

//  /**
//   * Create a MyCompilation copy of an 'Album'.
//   * 
//   * @param album the Album to be 'copied' into a MyCompilation.
//   * @return a MyCompilation copy of the 'album'.
//   */
//  private MyCompilation convertAlbumToMyCompilation(Album album) {
//    MyCompilation myCompilation = FACTORY.createMyCompilation();
//    
//    if (album.isSetArtist()) {
//      myCompilation.setArtist(album.getArtist());
//    }
//    
//    if (album.isSetDescription()) {
//      myCompilation.setDescription(album.getDescription());
//    }
//    
//    if (album.isSetDescriptionTitle()) {
//      myCompilation.setDescriptionTitle(album.getDescriptionTitle());
//    }
//    
//    if (album.isSetId()) {
//      myCompilation.setId(album.getId());
//    }
//    
//    for (String imageBack: album.getImagesBack()) {
//      myCompilation.getImagesBack().add(imageBack);
//    }
//    
//    for (String imageFront: album.getImagesFront()) {
//      myCompilation.getImagesFront().add(imageFront);
//    }
//    
//    for (String imageFrontInside: album.getImagesFrontInside()) {
//      myCompilation.getImagesFrontInside().add(imageFrontInside);
//    }
//    
//    for (String label: album.getImagesLabel()) {
//      myCompilation.getImagesLabel().add(label);
//    }
//    
//    for (MediumType mediumType: album.getIssuedOnMediums()) {
//      myCompilation.getIssuedOnMediums().add(mediumType);
//    }
//    
//    if (album.isSetMyInfo()) {
//      myCompilation.setMyInfo(album.getMyInfo());
//    }
//    
//    for (Player player: album.getPlayers()) {
//      Player myCompilationPlayer = FACTORY.createPlayer();
//      
//      if (player.isSetArtist()) {
//        myCompilationPlayer.setArtist(player.getArtist());
//      }
//      
//      for (String instrument: player.getInstruments()) {
//        myCompilationPlayer.getInstruments().add(instrument);
//      }
//      myCompilation.getPlayers().add(myCompilationPlayer);
//    }
//    
//    if (album.isSetReleaseDate()) {
//      myCompilation.setReleaseDate(album.getReleaseDate());
//    }
//    
//    if (album.isSetTitle()) {
//      myCompilation.setTitle(album.getTitle());
//    }
//    
//    for (Disc disc: album.getDiscs()) {
//      myCompilation.getDiscs().add(disc);
//    }
//    
//    return myCompilation;
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
    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), ALBUM_TAG)).append(NEWLINE);
    indent.increment();
    
    // Artist
    if (album.isSetArtist()) {
      output.append(SgmlUtil.createElement(indent, null, ARTIST_TAG, album.getArtist().getName())).append(NEWLINE);
    }
    
    // Album Title
    if (album.isSetTitle()) {
      output.append(SgmlUtil.createElement(indent, null, ALBUM_TITLE_TAG, album.getTitle())).append(NEWLINE);
    }
    
    // Album Id
    if (album.isSetId()) {
      output.append(SgmlUtil.createElement(indent, null, ALBUM_ID_TAG, album.getId())).append(NEWLINE);
    }
    
    // Issue date/Release date
    if (album.isSetReleaseDate()) {
      output.append(SgmlUtil.createElement(indent, null, ISSUE_DATE_TAG, FDF.format(album.getReleaseDate()))).append(NEWLINE);
    }
    
    // Players
    for (Player player: album.getPlayers()) {
      output.append(toXmlString(indent, nameSpace, player));
    }
    
    // Front images
    for (String imageFront: album.getImagesFront()) {
      output.append(SgmlUtil.createElement(indent, null, CD_COVER_FRONT_TAG, imageFront)).append(NEWLINE);
    }
    
    // Front Inside images
    for (String imageFrontInside: album.getImagesFrontInside()) {
      output.append(SgmlUtil.createElement(indent, null, CD_COVER_INSIDE_TAG, imageFrontInside)).append(NEWLINE);
    }
    
    // Back images
    for (String imageBack: album.getImagesBack()) {
      output.append(SgmlUtil.createElement(indent, null, CD_COVER_BACK_TAG, imageBack)).append(NEWLINE);
    }
    
    // Label images
    for (String imageLabel: album.getImagesLabel()) {
      output.append(SgmlUtil.createElement(indent, null, CD_LABEL_TAG, imageLabel)).append(NEWLINE);
    }
    
    // DescriptionTitle/Description
    if (album.isSetDescriptionTitle()) {
      output.append(SgmlUtil.createElement(indent, null, ALBUM_DESCRIPTION_TITLE_TAG, album.getDescriptionTitle())).append(NEWLINE);
    }
    if (album.isSetDescription()) {
      String albumDescription = album.getDescription();
      albumDescription = HtmlUtil.embedInCDataIfTextContainsHtmlMarkup(albumDescription);
      output.append(SgmlUtil.createElement(indent, null, ALBUM_DESCRIPTION_TAG, albumDescription)).append(NEWLINE);
    }
    
    // IssuedOn
    if (album.getIssuedOnMediums().size() != 0) {
      for (MediumType issuedOnMedium: album.getIssuedOnMediums()) {
        output.append(SgmlUtil.createElementOpen(indent, null, ISSUED_ON_MEDIUM_TAG));
        String mediumTypeText = null;
        switch (issuedOnMedium) {
        case CD_AUDIO:
          mediumTypeText = "CD";
          break;
          
        case CD_ROM:
          mediumTypeText = "CD-ROM";
          break;
          
        case DVD_ROM:
          mediumTypeText = "DVD-ROM";
          break;
          
        case DVD_VIDEO:
          mediumTypeText = "DVD";
          break;
          
        case LP:
          mediumTypeText = "LP";
          break;
          
        case SACD:
          mediumTypeText = "SACD";
          break;
          
        case SINGLE:
          mediumTypeText = "Single";
          break;
          
        case NOT_SET:
        default:
          break;
        }
        output.append(mediumTypeText);
        output.append(SgmlUtil.createElementClose(null, ISSUED_ON_MEDIUM_TAG)).append(NEWLINE);
      }
    }
    
    // Compilation
    if (album.isCompilation()) {
      output.append(SgmlUtil.createElementOpenClose(indent, null, COMPILATION_TAG)).append(NEWLINE);
    }
    
    // MyInfo
    output.append(toXmlString(indent, nameSpace, album.getMyInfo(), MediaDbUtil.isOwnCompilationAlbum(album)));
    
    // Discs/Tracks
    if (album.getDiscs().size() == 1) {
      Disc disc = album.getDiscs().get(0);
      for (TrackReference trackReference: disc.getTrackReferences()) {
        output.append(toXmlString(indent, nameSpace, trackReference));
      }
    } else {
      for (Disc disc: album.getDiscs()) {
        output.append(toXmlString(indent, nameSpace, disc));
      }
    }
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), ALBUM_TAG)).append(NEWLINE);
    
    return output.toString();
  }
  
  public static String toXmlString(Indent indent, String nameSpace, Disc disc) {
    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), DISC_TAG)).append(NEWLINE);
    indent.increment();
    
    // Title/Nr
    if (disc.isSetTitle()) {
      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), DISC_NR_TAG, disc.getTitle())).append(NEWLINE);
    }
    
    // Tracks
    for (TrackReference trackReference: disc.getTrackReferences()) {
      output.append(toXmlString(indent, nameSpace, trackReference));
    }
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), DISC_TAG)).append(NEWLINE);
    
    return output.toString();    
  }
  
  public static String toXmlString(Indent indent, String nameSpace, MyInfo myInfo, boolean myCompilation) {
    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), MY_INFO_TAG)).append(NEWLINE);
    indent.increment();
    
    // MyCompilation
    if (myCompilation) {
      output.append(SgmlUtil.createElementOpenClose(indent, null, MY_COMPILATION_TAG)).append(NEWLINE);
    }
    
    // IWant
    if (myInfo.isSetIWant()) {
      output.append(SgmlUtil.createElement(indent, null, I_WANT_TAG, myInfo.getIWant().getLiteral())).append(NEWLINE);
    }
    
//    // IHaveOn
//    for (MediumInfo mediumInfo: myInfo.getIHaveOn()) {
//      String mediumInfoText = mediumInfoToText(mediumInfo);
//      if (mediumInfoText != null) {
//        output.append(SgmlUtil.createElement(indent, null, I_HAVE_ON_TAG, mediumInfoText)).append(NEWLINE);
//      }
//    }
    
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

    // IHaveTracks
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
    
    // AlbumReferences
    for (Album album: myInfo.getAlbumReferences()) {
      output.append(SgmlUtil.createElement(indent, null, ALBUM_REFERENCE_TAG, album.getTitle())).append(NEWLINE);
    }
    
//    // CollectionName
//    if (myInfo.isSetCollection()) {
//      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), COLLECTION_NAME_TAG));
//      output.append(collectionToText(myInfo.getCollection()));
//      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), COLLECTION_NAME_TAG)).append(NEWLINE);
//    }
    
    // Inlay
    
    // MyComments
    if (myInfo.isSetMyComments()) {
      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), MY_COMMENTS_TAG));
      output.append(myInfo.getMyComments());
      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), MY_COMMENTS_TAG)).append(NEWLINE);
    }
    
    // IveHadOnLP
    if (myInfo.isIveHadOnLP()) {
      output.append(SgmlUtil.createElementOpenClose(indent, null, IVE_HAD_ON_LP_TAG)).append(NEWLINE);
    }
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), MY_INFO_TAG)).append(NEWLINE);
    
    return output.toString();
  }
  
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
  
  public static String toXmlString(Indent indent, String nameSpace, Player player) {
    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), PLAYER_TAG)).append(NEWLINE);
    indent.increment();
    
    // PlayerName
    if (player.isSetArtist()) {
      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), PLAYER_NAME_TAG, player.getArtist().getName())).append(NEWLINE);
    }
    
    // PlayerInstruments
    if (player.getInstruments().size() != 0) {
      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), PLAYER_INSTRUMENTS_TAG));
      boolean first = true;
      for (String instrument: player.getInstruments()) {
        if (first) {
          first = false;
        } else {
          output.append(", ");
        }
        output.append(instrument);
      }
      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), PLAYER_INSTRUMENTS_TAG)).append(NEWLINE);
    }
      
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), PLAYER_TAG)).append(NEWLINE);
    
    return output.toString();
  }
  
  public static String toXmlString(Indent indent, String nameSpace, Track track) {
    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
    indent.increment();
    
    // Title
    if (track.isSetTitle()) {
      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_TITLE_TAG, track.getTitle())).append(NEWLINE);
    }
    
    // Artist
    if (track.isSetArtist()) {
      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_ARTIST_TAG, track.getArtist().getName())).append(NEWLINE);
    }
    
    // Parts
    if (track.getParts().size() != 0) {
      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_PARTS_TAG)).append(NEWLINE);
      indent.increment();
      
      for (TrackPart trackPart: track.getParts()) {
        output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_PART_TAG, trackPart.getTitle())).append(NEWLINE);
      }
      
      indent.decrement();
      output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_PARTS_TAG)).append(NEWLINE);
    }
    
    // Duration
    if (track.isSetDuration()) {
      int durationInSeconds = track.getDuration();
      Duration duration = Duration.ofSeconds(durationInSeconds);
      String durationText = DF.format(duration);
      output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_DURATION_TAG, durationText)).append(NEWLINE);
    }
    
    // Authors
    if (track.getAuthors().size() != 0) {
      output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_AUTHOR_TAG));
      boolean first = true;
      for (Artist author: track.getAuthors()) {
        if (first) {
          first = false;
        } else {
          output.append(", ");
        }
        output.append(author.getName());
      }
      output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(), TRACK_AUTHOR_TAG)).append(NEWLINE);
    }
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
    
    return output.toString();
  }
  
  public static String toXmlString(Indent indent, String nameSpace, TrackReference trackReference) {
//    Track referredTrack = trackReference.getTrack();
//    Album referredAlbum = referredTrack.getDisc().getAlbum();

    StringBuilder output = new StringBuilder();
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
    indent.increment();
    
//    NumberFormat formatter = new DecimalFormat("00");
//    String trackTitle = FDF.format(referredAlbum.getReleaseDate()) + " - " +
//        referredAlbum.getTitle() + " - " +
//        formatter.format(referredTrack.getTrackNr()) + " - " +
//        referredTrack.getTitle();
//    output.append(SgmlUtil.createElement(indent, null, TRACK_TITLE_TAG, trackTitle)).append(NEWLINE);
    
    output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TAG)).append(NEWLINE);
    indent.increment();
    
//    output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), ALBUM_TITLE_TAG, referredAlbum.getTitle())).append(NEWLINE);    
//    output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TRACKNR_TAG, Integer.toString(referredTrack.getTrackNr()))).append(NEWLINE);   
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_REFERENCE_TAG)).append(NEWLINE);    
    
    indent.decrement();
    output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), TRACK_TAG)).append(NEWLINE);
    
    return output.toString();
  }

  protected enum State {
    START,
    ALBUMS,
    ALBUM,
    PLAYER,
    MY_INFO,
    TRACK,
    TRACK_REFERENCE,
    TRACK_PARTS,
    DISC,
    DISC_TRACK,
    DISC_TRACK_REFERENCE,
    DISC_TRACK_PARTS,
    ALBUM_DESCRIPTION
  }
}

/**
 * This class is used to store the MyInfo information.
 * It is used to set the MyTrackInfo when all information of an album has been parsed (i.e. when the 'Album' closing tag is encountered.
 */
class MyAlbumInfo {
  boolean myCompilation = false;
  IWant iWant = IWant.NOT_SET;
  List<MediumInfo> iHaveOn = new ArrayList<>();
  List<DiscAndTrackNrs> tracksIWant = new ArrayList<>();
  List<DiscAndTrackNrs> tracksIHave = new ArrayList<>();
  Collection collection = Collection.NOT_SET;
  AlbumType albumType = null;
}
