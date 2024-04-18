package goedegep.media.mediadb.albuminfo;

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
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;

/**
 * This class reads an 'ArtistInformation' XML file and stores the information in a <code>MediaDb</code> model.
 */
public class ArtistInfoHandler extends AbstractValidatingHandler<ArtistInfoHandler.State> {
  private static final Logger LOGGER = Logger.getLogger(ArtistInfoHandler.class.getName());
  
  private static MediadbFactory FACTORY = MediadbFactory.eINSTANCE;

  private static final String ARTISTS_TAG = "Artists";
  private static final String FOTO_TAG = "Foto";
  private static final String NAME_TAG = "Name";
  private static final String ARTIST_TAG = "Artist";
  private static final String SAMPLE_TAG = "Sample";
  private static final String JUDGEMENT_TAG = "Judgement";
  private static final String ALBUMS_TAG = "Albums";
  private static final String STYLE_TAG = "Style";
  private static final String ALBUM_TITLE_TAG = "AlbumTitle";
  private static final String SAMPLE_LINK_TAG = "SampleLink";
  private static final String SAMPLE_TITLE_TAG = "SampleTitle";

  private String              artistsFileName = null;  // Will be used for generating detailed error messages.
  
  private MediaDb mediaDb;
  // It is possible that when an 'AlbumReference' is found, the referred to Album doesn't exist yet.
  // Therefore the 'AlbumReferences' are stored in a separate list, and they are dereferenced after parsing the file(s).
  private List<AlbumReferenceInfo> albumReferences = new ArrayList<>();
  private List<Album> albumsInCurrentInfoFile = new ArrayList<>();
  // It is possible that when a 'Reference' (track reference) is found, the referred to Track (on an Album) doesn't exist yet.
  // Therefore the 'TrackReferences' are stored in a separate list, and they are dereferenced after parsing the file(s).
  private List<TrackReferenceInfo> trackReferences = new ArrayList<>();
  
  private String name;
  private String foto;
  private String style;
  private String judgement;
  private String sampleLink;
  private String sampleTitle;
  private List<String> albumTitles = new ArrayList<>();
  
  public ArtistInfoHandler() {
    setPrintStateChanges(true);
  }
  
  public MediaDb getMediaDb() {
    return mediaDb;
  }

  public void setMediaDb(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
  }

  public void read(String artistInformationFileName) throws ParseException {
    albumReferences.clear();
    trackReferences.clear();
    albumsInCurrentInfoFile.clear();
    state = State.START;
    this.artistsFileName = artistInformationFileName;
    Path albumsPathName = Paths.get(artistInformationFileName);
    LOGGER.info("albumsPathName=" + albumsPathName);
    Path albumsFilePath = albumsPathName.getFileName();
    LOGGER.info("albumsFilePath=" + albumsFilePath);
    String albumsPlainFileName = albumsFilePath.toString();
    LOGGER.info("albumsPlainFileName=" + albumsPlainFileName);
    try {
      // parse the document
      LOGGER.info("Parse start: " + artistInformationFileName);
      getParser(null).parse(artistInformationFileName, this);
      LOGGER.info("Parse ready: " + artistInformationFileName);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(artistInformationFileName, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }
  
  public void write(String albumInfoFile, List<Album> albums) {
  }
  

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    LOGGER.fine("=> " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = "";
    
    switch (state) {
    case START:
      // Only ARTISTS_TAG expected.
      if (tag.compareTo(ARTISTS_TAG) == 0) {
        pushState(State.ARTISTS);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(artistsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case ARTISTS:
      // Only ARTIST_TAG expected.
      if (tag.compareTo(ARTIST_TAG) == 0) {
        name = null;
        foto = null;
        style = null;
        judgement = null;
        sampleLink = null;
        sampleTitle = null;
        albumTitles.clear();
        pushState(State.ARTIST);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ". File: " + artistsFileName + ", line: "+ locator.getLineNumber());
        throw new ParseException(artistsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case ARTIST:
      // No state change for tags which have no sub elements.
      // This applies to: .
      // State change for tags which do have sub elements.
      // This applies to: .
      if (tag.compareTo(NAME_TAG) == 0) {
        // No action
      } else if (tag.compareTo(FOTO_TAG) == 0) {
        // No action
      } else if (tag.compareTo(STYLE_TAG) == 0) {
        // No action
      } else if (tag.compareTo(JUDGEMENT_TAG) == 0) {
        // No action
      } else if (tag.compareTo(SAMPLE_TAG) == 0) {
        pushState(State.SAMPLE);
      } else if (tag.compareTo(ALBUMS_TAG) == 0) {
        pushState(State.ALBUMS);
        throw new RuntimeException("Tag Albums is no longer supported. Artist is: " + name);
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(artistsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;
      
    case SAMPLE:
      if (tag.compareTo(SAMPLE_LINK_TAG) == 0) {
        // no action
      } else if (tag.compareTo(SAMPLE_TITLE_TAG) == 0) {
        // no action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state);
        throw new ParseException(artistsFileName, locator.getLineNumber(),
            locator.getColumnNumber(), "ongeldig start element '" + tag + "'.");
      }
      break;

    case ALBUMS:
      if (tag.compareTo(ALBUM_TITLE_TAG) == 0) {
        // No action
      } else {
        LOGGER.severe("Illegal start element: " + tag + " in state: " + state + ". Found in line " + locator.getLineNumber() + " in file " + artistsFileName);
        throw new ParseException(artistsFileName, locator.getLineNumber(),
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

    case ARTISTS:
      if (tag.compareTo(ARTISTS_TAG) == 0) {
        popState();
      }
      break;

    case ARTIST:
      if (tag.compareTo(ARTIST_TAG) == 0) {
        Artist artist = getArtist(name);
        LOGGER.info("Artist for name: " + name + ", " + (artist != null ? artist.getName() : "not found"));  // TODO fill details
        if (foto != null) {
          artist.setPhoto(foto.substring(9));  // Delete the 'Pictures/' part.
        } else {
          LOGGER.severe("No photo for artist: " + artist.getName());
        }
        if (style != null) {
          artist.setStyle(style);
        } else {
          LOGGER.severe("No style for artist: " + artist.getName());
        }
        if (judgement != null) {
          artist.setMyComments(judgement);
        } else {
          LOGGER.severe("No judgement for artist: " + artist.getName());
        }
        if (sampleLink != null) {
          MyTrackInfo myTrackInfo = FACTORY.createMyTrackInfo();
          myTrackInfo.setCollection(Collection.SAMPLE);
          MediumInfo mediumInfo = FACTORY.createMediumInfo();
          mediumInfo.setMediumType(MediumType.HARDDISK);
          myTrackInfo.getIHaveOn().add(mediumInfo);
          TrackReference trackReference = FACTORY.createTrackReference();
          trackReference.setMyTrackInfo(myTrackInfo);
          Track track = getTrack(artist, sampleTitle);
          trackReference.setTrack(track);
          artist.setSample(trackReference);
        } else {
          LOGGER.severe("No sample for artist: " + artist.getName());
        }
        popState();
      } else if (tag.compareTo(NAME_TAG) == 0) {
        name = data.trim();
        if (name == null  ||  name.isEmpty()) {
          throw new RuntimeException("name may not be null");
        }
        LOGGER.info("Name: " + name);
      } else if (tag.compareTo(FOTO_TAG) == 0) {
        foto = data.trim();
        LOGGER.info("Foto: " + foto);
      } else if (tag.compareTo(STYLE_TAG) == 0) {
        style = data.trim();
        LOGGER.info("Style: " + style);
      } else if (tag.compareTo(JUDGEMENT_TAG) == 0) {
        judgement = data.trim();
        LOGGER.info("Judgement: " + judgement);
      }
      break;
      
    case SAMPLE:
      if (tag.compareTo(SAMPLE_LINK_TAG) == 0) {
        sampleLink = data.trim();
        LOGGER.info("SampleLink: " + sampleLink);
      } else if (tag.compareTo(SAMPLE_TITLE_TAG) == 0) {
        sampleTitle = data.trim();
        LOGGER.info("SampleTitle: " + sampleTitle);
      } else if (tag.compareTo(SAMPLE_TAG) == 0) {
        popState();
      }
      break;
      
    case ALBUMS:
      if (tag.compareTo(ALBUMS_TAG) == 0) {
        popState();
      } else if (tag.compareTo(ALBUM_TITLE_TAG) == 0) {
        albumTitles.add(data.trim());
      }
      break;
    }
  }
  
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
  
  private Track getTrack(Artist artist, String title) {
    Track track = mediaDb.getTrack(artist, title);
    
    if (track == null) {
      LOGGER.info("Track doesn't exist: " + artist.getName() + " - " + title + "   , so we're going to add it");
      track = FACTORY.createTrack();
      track.setArtist(artist);
      track.setTitle(title);
      mediaDb.getTracks().add(track);
    } else {
      LOGGER.severe("Track already exists: " + artist.getName() + " - " + title);
    }
    
    return track;
  }

  protected enum State {
    START,
    ARTISTS,
    ARTIST,
    SAMPLE,
    ALBUMS,
  }
}
