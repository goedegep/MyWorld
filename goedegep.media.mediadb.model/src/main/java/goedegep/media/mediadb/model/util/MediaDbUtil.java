package goedegep.media.mediadb.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackPart;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.text.FuzzyStringCompare;


/**
 * This class provides utility methods related to the MediaDb data model.
 */
public class MediaDbUtil {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MediaDbUtil.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  /*
   * The methods are grouped by their first parameter type.
   */
    
  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private MediaDbUtil() {
  }
  
  
  /*
   * MediaDb related methods
   */
  
  /**
   * Get an {@code Artist} by name.
   * 
   * @param mediaDb the {@code MediaDb} to search in.
   * @param artistName the name of the {@code Artist} to get.
   * @return the {@code Artist} with the specified name, or null if no such {@code Artist} exists.
   */
  public static Artist getArtist(MediaDb mediaDb, String artistName) {
    for (Artist artist : mediaDb.getArtists()) {
      if (artist.getName().equals(artistName)) {
        return artist;
      }
    }

    return null;
  }

  /**
   * Get an {@code Album} by release date, artist and title.
   * <p>
   * For both artist name and album title, a fuzzy string compare is done, so that small differences in spelling or punctuation do not prevent a match.
   * See {@link FuzzyStringCompare#fuzzyStringCompare()} for details.
   * The search is first done on the artist, and if no album is found, the search is done on the container artist of the artist.
   * 
   * @param mediaDb the {@code MediaDb} to search in.
   * @param releaseDate the release date of the {@code Album} to get. May be null.
   * @param artist the {@code Artist}, or container artist, of the {@code Album} to get. May not be null.
   * @param title the title of the {@code Album} to get. May not be null.
   * @return the {@code Album} with the specified release date, artist and title, or null if no such {@code Album} exists.
   */
  public static Album getAlbum(MediaDb mediaDb, FlexDate releaseDate, Artist artist, String title) {
    Objects.requireNonNull(artist, "The artist may not be null");
    Objects.requireNonNull(title, "The title may not be null");

    for (Album album : mediaDb.getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate != null) {
        if (album.getReleaseDate() == null  ||  album.getReleaseDate().compareTo(releaseDate) != 0) {
          compareOkSoFar = false;
        }
      }

      if (album.getArtist() == null) {
        compareOkSoFar = false;
      }

      if (compareOkSoFar
          && FuzzyStringCompare.fuzzyStringCompare(album.getTitle(), title)
          && FuzzyStringCompare.fuzzyStringCompare(album.getArtist().getName(), artist.getName())) {
        return album;
      }
    }

    // No album found, go through the albums again but now check on the containerArtist.
    for (Album album : mediaDb.getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate != null) {
        if (album.getReleaseDate() == null  ||  album.getReleaseDate().compareTo(releaseDate) != 0) {
          compareOkSoFar = false;
        }
      }

      if (album.getArtist() == null) {
        LOGGER.severe("No artist set for album: " + MediaDbUtil.getArtistAndTitle(album));
        compareOkSoFar = false;
      }

      if (compareOkSoFar
          && album.getArtist().getContainerArtist() != null
          && FuzzyStringCompare.fuzzyStringCompare(album.getTitle(), title)
          && FuzzyStringCompare.fuzzyStringCompare(album.getArtist().getContainerArtist().getName(), artist.getName())) {
        return album;
      }
    }

    return null;
  }

  /**
   * Get a list of {@code Album}s for a specific release date, artist and title.
   * <p>
   * TODO: explain usage.
   * 
   * @param mediaDb the {@code MediaDb} to search in.
   * @param releaseDate the release date of the {@code Album}s to get. If null, no release date check is done.
   * @param artist the {@code Artist}, or container artist, of the {@code Album}s to get. If null, no artist check is done.
   * @param title the title of the {@code Album}s to get. If null, no title check is done.
   * @return a list of {@code Album}s that match the specified release date, artist and title. The list may be empty, but is never null.
   */
  public static EList<Album> getAlbums(MediaDb mediaDb, FlexDate releaseDate, Artist artist, String title) {
    EList<Album> albums = new BasicEList<Album>();
    if ((artist != null) && (artist.getContainerArtist() != null)) {
      Artist containerArtist = artist.getContainerArtist();
      if (containerArtist == null) {
        throw new RuntimeException("container artist is null for artist: " + artist.getName());
      }
      artist = containerArtist;
    }

    for (Album album : mediaDb.getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate == null) {
        // no release date check needed
        LOGGER.fine("date OK, not specified");
      } else if (album.getReleaseDate() == null) {
        LOGGER.fine("date not OK; date specified, but not available in album");
        compareOkSoFar = false;
      } else {
        LOGGER.fine("Real date check");
        LOGGER.fine("releaseDate: " + FDF.format(releaseDate) + ", this album: " + FDF.format(album.getReleaseDate()));
        if (album.getReleaseDate().getYear() == null) {
          LOGGER.severe("Year is null for album: " + album.toString());
        }
        if (album.getReleaseDate().isSpecializationOf(releaseDate)) {
          LOGGER.fine("date OK!!!");
        } else {
          LOGGER.fine("date not OK");
          compareOkSoFar = false;
        }
      }

      Artist albumArtist = album.getArtist();
      if ((albumArtist != null) && albumArtist.isSetContainerArtist()) {
        albumArtist = albumArtist.getContainerArtist();
      }
      if (albumArtist == null) {
        LOGGER.info("albumArtist is null for album: " + album.getTitle());
      }
      if (compareOkSoFar) {
        if ((artist != null) && (albumArtist == null) || (artist == null) && (albumArtist != null)) {
          compareOkSoFar = false;
        } else if ((artist != null) && (albumArtist != null)) {
          compareOkSoFar = artist.getName().equals(albumArtist.getName());
        }
      }

      if (compareOkSoFar && (title != null)) {
        if (!title.equals(album.getTitle())) {
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar) {
        albums.add(album);
      }
    }

    return albums;
  }

  /**
   * Get a {@code TrackCollection} for a specific {@code Collection}.
   * <p>
   * This method searches for a {@code TrackCollection} in the {@code mediaDb} that corresponds to the specified {@code Collection}.
   * If no such {@code TrackCollection} exists, a new one is created and added to the {@code mediaDb}.
   * 
   * @param mediaDb the {@code MediaDb} to search in.
   * @param collection the {@code Collection} for which the {@code TrackCollection} is requested.
   * @return the {@code TrackCollection} corresponding to the specified {@code Collection}.
   */
  public static TrackCollection getTrackCollection(MediaDb mediaDb, goedegep.media.mediadb.model.Collection collection) {
    TrackCollection trackCollection = null;

    for (TrackCollection aTrackCollection : mediaDb.getTrackcollections()) {
      if (aTrackCollection.getCollection().equals(collection)) {
        trackCollection = aTrackCollection;
        break;
      }
    }

    if (trackCollection == null) {
      trackCollection = MediadbFactory.eINSTANCE.createTrackCollection();
      trackCollection.setCollection(collection);
      mediaDb.getTrackcollections().add(trackCollection);
    }

    return trackCollection;
  }
  

  /**
   * Get a {@code Track} by {@code Artist} and title.
   *
   * @param mediaDb the {@code MediaDb} to search in.
   * @param artist the {@code Artist} of the {@code Track} to get.
   * @param title the title of the {@code Track} to get.
   * @return the {@code Track} with the specified {@code Artist} and title, or null if no such {@code Track} exists.
   */
  public static Track getTrack(MediaDb mediaDb, Artist artist, String title) {
    for (Track track : mediaDb.getTracks()) {
      Artist trackArtist = track.getArtist();
      if (trackArtist == null) {
        TrackReference trackReference = MediaDbUtil.getOriginalDiscTrackReference(track);
        if (trackReference != null) {
          trackArtist = MediaDbUtil.getAlbum(MediaDbUtil.getDisc(trackReference)).getArtist();
        }
      }
      if (artist.equals(trackArtist) && title.equals(track.getTitle())) {
        return track;
      }
    }

    return null;
  }
  
  
  /*
   * TrackCollection related methods
   */
  
  /**
   * Find a track in a track collection.
   * <p>
   * This method searches the complete collection and if there is more than one track with the specified artist and title, a {@code RuntimeException} is thrown.
   * 
   * @param trackCollection The {@code TrackCollection} to search in.
   * @param artist The {@code Artist} of the track.
   * @param title The title of the track.
   * @return A track reference for the track specified by {@code artist} and {@code title}.
   */
  public static TrackReference getTrackReferece(TrackCollection trackCollection, Artist artist, String title) {
    java.util.Objects.requireNonNull(artist, "Parameter artist may not be null");
    java.util.Objects.requireNonNull(title, "Parameter title may not be null");

    TrackReference collectionTrackReference = null;

    for (TrackReference trackReference : trackCollection.getTrackReferences()) {
      if (artist == trackReference.getTrack().getArtist() && title.equals(trackReference.getTrack().getTitle())) {
        if (collectionTrackReference != null) {
          throw new RuntimeException("Found more than one TrackReference for: " + artist.getName() + " - " + title);
        }

        collectionTrackReference = trackReference;
      }
    }

    return collectionTrackReference;
  }
  
  
  /*
   * Album related methods
   */
  
  /**
   * Check whether an {@code Album} is a single disc album.
   * 
   * @param album the {@code Album} to check
   * @return true if the {@code album} has one disc, false otherwise.
   */
  public static boolean isSingleDiscAlbum(Album album) {
    return album.getDiscs().size() == 1;
  }

  /**
   * Check whether the album is a multi disc album, i.e. the album consists of more than one disc.<br/>
   * The result of this method is the same as: album.getDiscs().size() > 1.
   * 
   * @return true if the album is a multi disc album, false otherwise.
   */
  public static boolean isMultiDiscAlbum(Album album) {
    return album.getDiscs().size() > 1;
  }
  
  /**
   * Check whether an album is an Own Compilation album.
   * <p>
   * This is the case if the MyInfo exists and the AlbumType in the MyInfo is AlbumType.OWN_COMPILATION_ALBUM.
   * 
   * @param album the {@code Album} to check
   * @return true if {@code album} is an Own Compilation album, false otherwise.
   */
  public static boolean isOwnCompilationAlbum(Album album) {
    return album.getMyInfo() != null  &&  (album.getMyInfo().getAlbumType() == AlbumType.OWN_COMPILATION_ALBUM);
  }

  /**
   * Set the AlbumType for an album.
   * <p>
   * This is the same as {@code album.getMyInfo().setAlbumType(albumType)}, but here the {@code MyInfo} is created if it doesn't exist.
   * 
   * @param album the Album
   * @param albumType the AlbumType to set for the {@code album}.
   */
  public static void setAlbumType(Album album, AlbumType albumType) {
    MyInfo myInfo = album.getMyInfo();
    if (myInfo == null) {
      myInfo = MEDIA_DB_FACTORY.createMyInfo();
      album.setMyInfo(myInfo);
    }
    myInfo.setAlbumType(albumType);
  }
  
  /**
   * Check whether I have an album on CD Digital Audio.
   * <p>
   * This is the case if all track references refer to tracks that:
   * <ul>
   * <li>iHaveOn set with mediumType set to CD_AUDIO.</li>
   * <li>have the original album set to this album.</li>
   * </ul>
   * Also there shall be at least one disc of which the list of tracks shall not be empty.
   * 
   * @param album the {@code Album} to be checked.
   * @return true if I have the {@code album} on CD, false otherwise.
   */
  public static boolean haveAlbumOnCdda(Album album) {
    return haveAlbumOnMediumType(album, MediumType.CD_AUDIO);
  }
  
  /**
   * Check whether I have an album on Super Audio CD.
   * <p>
   * <p>
   * This is the case if all track references refer to tracks that:
   * <ul>
   * <li>iHaveOn set with mediumType set to SACD.</li>
   * <li>have the original album set to this album.</li>
   * </ul>
   * Also there shall be at least one disc of which the list of tracks shall not be empty.
   * 
   * @param album the {@code Album} to be checked.
   * @return true if I have the {@code album} on SACD, false otherwise.
   */
  public static boolean haveAlbumOnSacd(Album album) {
    return haveAlbumOnMediumType(album, MediumType.SACD);
  }
  
  /**
   * Check whether I have an album on a specific medium type (e.g. CD_AUDIO or Super Audio CD).
   * <p>
   * This is about having the complete album on the specified medium type, so this information shall be specified at album level.
   * 
   * @param album the {@code Album} to be checked.
   * @param mediumType the {@code MediumType} to have the {@code album} on.
   * @return true if I have the {@code album} on the specified {@code mediumType}, false otherwise.
   */
  public static boolean haveAlbumOnMediumType(Album album, MediumType mediumType) {
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      for (MediumInfo mediumInfo: myInfo.getIHaveOn()) {
        if (mediumType.equals(mediumInfo.getMediumType())) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * Check whether I have an album (doesn't matter on which medium type).
   * <p>
   * This is about having the complete album, so this information shall be specified at album level.
   * Or all track references refer to track references (and so on) that I have.
   * 
   * @param album the {@code Album} to be checked.
   * @return true if I have the {@code album}, false otherwise.
   */
  public static boolean haveAlbum(Album album) {
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      return !myInfo.getIHaveOn().isEmpty();
    }
    
    return false;
  }
  
  /**
   * Check whether I have an album partly (doesn't matter on which medium type).
   * <p>
   * This is about having at least one track of the album, but not the complete album.
   * 
   * @param album the {@code Album} to be checked.
   * @return true if I have the {@code album} partly, false otherwise.
   */
  public static boolean haveAlbumPartly(Album album) {
    if (haveAlbum(album)) {
      return false;
    }
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        TrackReference finalTrackReference = MediaDbUtil.getFinalTrackReference(trackReference);
        if (haveTrack(finalTrackReference)) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public static boolean haveTrack(TrackReference trackReference) {
    TrackReference finalTrackReference = MediaDbUtil.getFinalTrackReference(trackReference);
    MyTrackInfo myTrackInfo = finalTrackReference.getMyTrackInfo();
    if (myTrackInfo != null  &&  !myTrackInfo.getIHaveOn().isEmpty()) {
      return true;
    }
    
    return false;
  }
  
  /**
   * Get the final track reference of a track reference.
   * <p>
   * This follows the MyTrackInfo.trackReference until a track reference is found that has no MyTrackInfo or the MyTrackInfo has no track reference.
   * This may be the same track reference as the one specified in the parameter, or a different one.
   * 
   * @param trackReference the {@code TrackReference} to get the final track reference for.
   * @return the final {@code TrackReference} of the specified {@code trackReference}.
   */
  public static TrackReference getFinalTrackReference(TrackReference trackReference) {
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo == null  || myTrackInfo.getTrackReference() == null) {
      return trackReference;
    }
    
    return getFinalTrackReference(myTrackInfo.getTrackReference());
  }


  /**
   * Return the set of medium infos of which I have the album on.
   * 
   * @param album the {@code Album} to get the information for.
   * @return a {@code Set} of {@code MediumInfo} for which I have the album on.
   */
  public static Set<MediumInfo> haveAlbumOnMediumTypes(Album album) {
    Set<MediumInfo> mediumInfos = new HashSet<>();
    
    MyInfo myInfo = album.getMyInfo();
    List<MediumInfo> iHaveOn = myInfo.getIHaveOn();
    if (!iHaveOn.isEmpty()) {
      for (MediumInfo mediumInfo: iHaveOn) {
        mediumInfos.add(mediumInfo);
      }
      return mediumInfos;
    }
    
    
    // If there are no discs defined, I shouldn't have it.
    if (album.getDiscs().isEmpty()) {
      return mediumInfos;
    }
    
    for (Disc disc: album.getDiscs()) {
      // If there's a Disc with no tracks defined, I shouldn't have it.
      if (disc.getTrackReferences().isEmpty()) {
        mediumInfos.clear();
        return mediumInfos;
      }
      
      for (TrackReference trackReference: disc.getTrackReferences()) {
        if (trackReference.getTrack() == null) {
          // This shouldn't happen, but we return an empty list to be safe.
          mediumInfos.clear();
          return mediumInfos;
        }
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        // If this is not set, I shouldn't have it.
        if (myTrackInfo == null) {
          mediumInfos.clear();
          return mediumInfos;
        }
        List<MediumInfo> trackMediumInfos = myTrackInfo.getIHaveOn();
        if (trackMediumInfos.isEmpty()) {
          mediumInfos.clear();
          return mediumInfos;
        }
        // if first track (set is empty), add all media the set.
        if (mediumInfos.isEmpty()) {
          mediumInfos.addAll(trackMediumInfos);
        } else {
          List<MediumInfo> mediumInfosToRemove = new ArrayList<>();
          for (MediumInfo mediumInfo: mediumInfos) {
            if (!mediumInfosListContainsMediumInfo(trackMediumInfos, mediumInfo)) {
              mediumInfosToRemove.add(mediumInfo);
            }
          }
          for (MediumInfo mediumInfo: mediumInfosToRemove) {
            mediumInfos.remove(mediumInfo);
          }
         }
      }
      
      if (mediumInfos.isEmpty()) {
        return mediumInfos;
      }
    }
    
    return mediumInfos;
  }
  
  /**
   * Check whether an {@code Album} is an album that I don't want.
   * <p>
   * This is the case if 'I Want' at album level is 'No',
   * or if all track references state 'I don't want this track'.
   * 
   * @param album the {@code Album} to be checked
   * @return true if I don't want the {@code album}, false otherwise.
   */
  public static boolean isAlbumAnAlbumThatIDontWant(Album album) {
    IWant iWant = getIWant(album);
    if (iWant != IWant.NOT_SET) {
      // it specified at album level, so return this value.
      return iWant == IWant.NO;
    }
    
    // Check all track references. If any track doesn't specify 'I don't want this track' return false.
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo == null  ||  myTrackInfo.getIWant() != IWant.NO) {
          return false;
        }
      }
    }
    
    return true;
  }
  
  /**
   * Get the IWant information of an album at album level.
   * <p>
   * The result is the same as album.getMyInfo().getIWant(), but with checks on null values.
   * 
   * @param album the Album for which the information is requested.
   * @return the IWant information at album level for the {@code album}.
   */
  public static IWant getIWant(Album album) {
    IWant iWant = IWant.NOT_SET;
    
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      iWant = myInfo.getIWant();
    }
    
    return iWant;
  }

  /**
   * Check whether I want this {@code Album} or some tracks of the album, which means I still have to obtain the album or tracks of the album.<br/>
   * 
   * @return true if {@code iWant} is set to YES at album level or on any track, false otherwise.
   */
  public static boolean iWantAlbumOrTracksOfAlbum(Album album) {

    if ((album.getMyInfo() != null) && (album.getMyInfo().getIWant() == IWant.YES)) {
      return true;
    }

    for (Disc disc : album.getDiscs()) {
      for (TrackReference trackReference : disc.getTrackReferences()) {
        if (trackReference.getMyTrackInfo() != null && trackReference.getMyTrackInfo().getIWant() == IWant.YES) {
          return true;
        }
      }
    }

    return false;
  }
  
  /**
   * Get the collection of tracks of an album that I want.
   *
   * @param album the {@code Album} for which the information is requested.
   * @return a List of {@code DiscAndTrackNrs}, where each item provides the tracks I want for one album disc.
   */
  public static List<DiscAndTrackNrs> getAlbumTracksIWant(Album album) {
    List<DiscAndTrackNrs> discAndTrackNrsList = new ArrayList<>();

    DiscAndTrackNrs discAndTrackNrs = null;
    for (Disc disc: album.getDiscs()) {
      discAndTrackNrs = getDiscTracksIWant(disc);
      if (discAndTrackNrs != null) {
        discAndTrackNrsList.add(discAndTrackNrs);
      }
    }
    
    return discAndTrackNrsList;
  }
  
  /**
   * Set the IWant information of an Album.
   * <p>
   * If there are no tracks specified for the album, the IWant information is set in the MyInfo at album level,
   * otherwise the IWant information is set per track (of all tracks of the album).<br/>
   * If {@code iWant} is null, nothing is done.<br/>
   * If {@code iWant} is NOT_SET, the values will be unset.
   * 
   * @param album the {@code Album} for which the information is to be set.
   * @param iWant the value to set the IWant information to.
   */
  public static void setIWant(Album album, IWant iWant) {
    if (iWant == null) {
      return;
    }

    // If the album tracks are not available, set it at album level, otherwise set it per track.
    if (!areAllTracksAvailableForAlbum(album)) {
      MyInfo myInfo = album.getMyInfo();
      if (myInfo == null) {
        myInfo = MEDIA_DB_FACTORY.createMyInfo();
        album.setMyInfo(myInfo);
      }
      myInfo.setIWant(iWant);
    } else {
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          MyTrackInfo myTrackInfo;
          if (trackReference.isSetMyTrackInfo()) {
            myTrackInfo = trackReference.getMyTrackInfo();
          } else {
            myTrackInfo = MEDIA_DB_FACTORY.createMyTrackInfo();
            trackReference.setMyTrackInfo(myTrackInfo);
          }
          myTrackInfo.setIWant(iWant);
        }
      }
    }
  }
  
  /**
   * Check that the tracks (track references) are set for an {@code Album}.
   * @param album the {@code Album} to check
   * @return true if the tracks are set, false otherwise.
   */
  private static boolean areAllTracksAvailableForAlbum(Album album) {
    boolean tracksAvailable = false;
    
    if (!album.getDiscs().isEmpty()) {
      boolean allDiscTracksAvailable = true;
      
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          if (trackReference.getTrack() == null) {
            allDiscTracksAvailable = false;
            break;
          }
        }
        
        if (!allDiscTracksAvailable) {
          break;
        }
      }
      
      if (allDiscTracksAvailable) {
        tracksAvailable = true;
      }
    }
    
    return tracksAvailable;
  }
    
  /**
   * Check whether I should have an Album completely on hard disk, which is the case if there is an 'IHaveOn' of type 'HARDDISK' at album level.
   * 
   * @param album the {@code Album} to be checked
   * @return true if I should have the {@code album} on hard disk, false otherwise.
   */
  public static boolean haveAlbumOnHardDisk(Album album) {
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      for (MediumInfo mediumInfo: myInfo.getIHaveOn()) {
        if (mediumInfo.getMediumType() == MediumType.HARDDISK) {
          return true;
        }
      }
    }
 
    return false;
  }

  /**
   * Check whether I have a part of an {@code Album} on hard disk. A part means one or more tracks.
   * <p>
   * Note that if I have the complete album on hard disk, I also have it partly on hard disk.<br/>
   * I have the album partly on hard disk, if there is at least one disc of the album of which I have at least one track on hard disk.
   * 
   * @param album the {@code Album} for which the information is requested.
   * @return true if I have the {@code album} partly on hard disk, false otherwise.
   */
  public static boolean haveAlbumPartlyOnHardDisk(Album album) {
    for (Disc disc: album.getDiscs()) {
      if (doIHaveOneOrMoreDiscTracksOnHardDisk(disc)) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Get the {@code TrackReference specified by disc number and track number.
   * 
   * @param album The {@code Album} to get the {@code TrackReference} from.
   * @param discNr The disc number (starting at 1) on which a track is to be found.
   * @param trackNr The track number.
   * @return The {@code TrackReference} specified by {@code discNr} and {@code trackNr}, or null if such a track doesn't exist.
   */
  public static TrackReference getTrackReference(Album album, Integer discNr, int trackNr) {
    if (discNr == null) {
      discNr = 1;
    }
    Disc disc = album.getDiscs().get(discNr - 1);
    int nrOfTracks = disc.getTrackReferences().size();
    if (trackNr >= nrOfTracks) {
      return null;
    }
    return disc.getTrackReferences().get(trackNr - 1);
  }
  
  /**
   * Get the collection of tracks of an album that I have (on hard disk).
   *  
   * @param album the {@code Album} for which the information is requested.
   * @param onHardDisk if true, only return the tracks that I have on hard disk.
   * @return a {@code List} of {@code DiscAndTrackNrs}, where each item provides the tracks I have (on hard disk) for one album disc.
   */
  public static List<DiscAndTrackNrs> getAlbumTracksIHave(Album album, boolean onHardDisk) {
    List<DiscAndTrackNrs> discAndTrackNrsList = new ArrayList<>();

    DiscAndTrackNrs discAndTrackNrs;
    for (Disc disc: album.getDiscs()) {
      discAndTrackNrs = getDiscTracksIHave(disc, onHardDisk);
      if (discAndTrackNrs != null) {
        discAndTrackNrsList.add(discAndTrackNrs);
      }
    }
    
    return discAndTrackNrsList;
  }

  /**
   * Get a player specified by the {@code Artist}.
   *
   * @param artist The {@code Artist} of the player.
   * @return the {@code Player} for the specified {@code artist}, or null if such a player doesn't exist.
   */
  public static Player getPlayer(Album album, Artist artist) {
    for (Player player : album.getPlayers()) {
      if (player.getArtist().equals(artist)) {
        return player;
      }
    }

    return null;
  }

  /**
   * Get Artist and Title of the album.
   * <p>
   * The information is provided in the format: &lt;artist&gt; - &lt;title&gt;<br/>
   * where:<br/>
   *  &lt;artist&gt; is the name of the artist of the album, or '&lt;no artist&gt;' if this information isn't available.<br/>.
   *  &lt;title&gt; is the title of the album,  '&lt;no title&gt;' if title isn't set.
   *  
   *  @param album the {@code Album} for which the information is requested.
   *  @return the artist and title of the album.
   */
  public static String getArtistAndTitle(Album album) {
    StringBuilder buf = new StringBuilder();
    if (album.getArtist() != null) {
      Artist artist = album.getArtist();
      buf.append(artist.getName());
    } else {
      buf.append("<no artist>");
    }

    buf.append(" - ");

    if (album.isSetTitle()) {
      buf.append(album.getTitle());
    } else {
      buf.append("<no title>");
    }

    return buf.toString();
  }

  /**
   * Get the {@code Disc} of a single disc album. A RuntimeException is thrown if the album is a multi disc album.
   *
   * @param album the {@code Album} for which the information is requested.
   * @return the {@code Disc} of the album, or null if the album has no disc.
   */
  public static Disc getDisc(Album album) {
    if (MediaDbUtil.isMultiDiscAlbum(album)) {
      throw new RuntimeException("This method may not be called on a multi disc album");
    }
    List<Disc> discs = album.getDiscs();
    if (discs.isEmpty()) {
      return null;
    } else {
      return discs.get(0);
    }
  }

  /**
   * Check whether I still have to judge the album or tracks of the album.
   * <p>
   * This is the case if {@code iWant} is set to 'DONT KNOW' at album level or on any track.
   * 
   * @param album the {@code Album} for which the information is requested.
   * @return true if I still have to judge this album, false otherwise.
   */
  public static boolean iHaveToJudgeAlbumOrTracks(Album album) {

    if ((album.getMyInfo() != null) && (album.getMyInfo().getIWant() == IWant.DONT_KNOW)) {
      return true;
    }

    for (Disc disc : album.getDiscs()) {
      for (TrackReference trackReference : disc.getTrackReferences()) {
        if (trackReference.getMyTrackInfo() != null && trackReference.getMyTrackInfo().getIWant() == IWant.DONT_KNOW) {
          return true;
        }
      }
    }

    return false;
  }
  
  
  /*
   * Disc related methods
   */

  /**
   * Get the {@code Album} of which this disc is a part.
   * 
   * @param disc the {@code Disc} for which the information is requested.
   * @return the {@code Album} of which this disc is a part.
   */
  public static Album getAlbum(Disc disc) {
    return (Album) disc.eContainer();
  }

  /**
   * Get the index of a disc (starting at 1) in the list of discs of the album.
   * 
   * @param disc the {@code Disc} for which the information is requested.
   * @return the index of the disc in the list of discs of the album, starting at 1.
   */
  public static int getDiscNr(Disc disc) {
    return MediaDbUtil.getAlbum(disc).getDiscs().indexOf(disc) + 1;
  }

  /**
   * Get the collection of tracks of an album disc that I want.
   * 
   * @param disc the {@code Disc} for which the information is requested.
   * @return a {@code DiscAndTrackNrs} object with the tracks I want for this disc.
   */
  public static DiscAndTrackNrs getDiscTracksIWant(Disc disc) {
    DiscAndTrackNrs discAndTrackNrs = null;
    
    Album album = MediaDbUtil.getAlbum(disc);
    
    int discNr;
    if (isSingleDiscAlbum(album)) {
      discNr = -1;
    } else {
      discNr = album.getDiscs().indexOf(disc) + 1;
    }
      
    int trackNr = 1;
    for (TrackReference trackReference: disc.getTrackReferences()) {
      MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
      if (myTrackInfo != null) {
        IWant iWant = myTrackInfo.getIWant();
        if (iWant.equals(IWant.YES)) {
          if (discAndTrackNrs == null) {
            discAndTrackNrs = MEDIA_DB_FACTORY.createDiscAndTrackNrs();
            if (discNr != -1) {
              discAndTrackNrs.setDiscNr(discNr);
            }
          }
          discAndTrackNrs.getTrackNrs().add(trackNr);
        }
      }

      trackNr++;
    }
    
    return discAndTrackNrs;
  }
  
  /**
   * Check whether I should have an Album Disc on hard disk, which is the case if:
   * <ul>
   * <li>At album level is specified that I have the complete album on hard disck</li>
   * <li>Or no 'I have on' is specified at album level and the disc has track references which all state that I have them on hard disk.</li>
   * </ul>
   * 
   * @param disc the {@code Disc} to be checked
   * @return true if I should have the {@code disc} on hard disk, false otherwise.
   */
  public static boolean haveDiscOnHardDisk(Disc disc) {
    Album album = MediaDbUtil.getAlbum(disc);
    
    if (haveAlbumOnHardDisk(album)) {
      return true;
    }
    
    if (disc.getTrackReferences().isEmpty()) {
      return false;
    }

    for (TrackReference trackReference: disc.getTrackReferences()) {
      if (!doIHaveTrackOnHardDisk(trackReference)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check whether I have a part of a {@code Disc} on hard disk. A part means one or more tracks.
   * <p>
   * I have a track on disc if:
   * <ul>
   * <li>There is an 'I have on' for hard disk</li>
   * <li>Or, the track is part of a collection</li>
   * </ul>
   * 
   * @param disc the {@code Disc} for which the information is requested.
   * @return true if I have the {@code disc} partly on hard disk, false otherwise.
   */
  public static boolean doIHaveOneOrMoreDiscTracksOnHardDisk(Disc disc) {
    for (TrackReference trackReference: disc.getTrackReferences()) {
      if (doIHaveTrackOnHardDisk(trackReference)) {
        return true;
      }
    }
    
    return false;
  }

  /**
   * Get the collection of tracks of a {@code Disc} that I have (on hard disk).
   * 
   * @param disc the {@code Disc} for which the information is requested.
   * @param onHardDisk if true, only return the tracks that I have on hard disk.
   * @return a {@code DiscAndTrackNrs} object with the tracks I have (on hard disk) for this {@code disc}.
   */
  public static DiscAndTrackNrs getDiscTracksIHave(Disc disc, boolean onHardDisk) {
    DiscAndTrackNrs discAndTrackNrs = null;
    
    Album album = MediaDbUtil.getAlbum(disc);
    
    int discNr;
    if (isSingleDiscAlbum(album)) {
      discNr = -1;
    } else {
      discNr = album.getDiscs().indexOf(disc) + 1;
    }
      
    int trackNr = 1;
    for (TrackReference trackReference: disc.getTrackReferences()) {
      MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
      if (myTrackInfo != null) {
        List<MediumInfo> mediumInfos = myTrackInfo.getIHaveOn();
        for (MediumInfo mediumInfo: mediumInfos) {
          MediumType mediumType = mediumInfo.getMediumType();
          if (mediumType != null  &&  mediumType != MediumType.NOT_SET  &&
              ((onHardDisk == false)  ||  (mediumInfo.getMediumType() == MediumType.HARDDISK))) {
            // I have this track (on disc}
            if (discAndTrackNrs == null) {
              discAndTrackNrs = MEDIA_DB_FACTORY.createDiscAndTrackNrs();
              if (discNr != -1) {
                discAndTrackNrs.setDiscNr(discNr);
              }
            }
            discAndTrackNrs.getTrackNrs().add(trackNr);
            break;
          }
        }        
      }

      trackNr++;
    }
    
    return discAndTrackNrs;
  }
  
  /**
   * Create a copy of a Disc.
   * 
   * @param disc the {@code Disc} to be copied.
   * @return a Disc, being a copy of the specified {@code disc}.
   */
  public static Disc createDiscCopy(Disc disc) {
    Disc discCopy = MEDIA_DB_FACTORY.createDisc();
    
    String title = disc.getTitle();
    if (title != null) {
      discCopy.setTitle(new String(title));
    }
    
    for (TrackReference trackReference: disc.getTrackReferences()) {
      discCopy.getTrackReferences().add(createTrackReferenceCopy(trackReference));
    }
    
    return discCopy;
  }
  
  
  /*
   * TrackReference related methods
   */

  /**
   * Get the track number of a {@code TrackReference} in the list of track references of the disc.
   * 
   * @param trackReference the {@code TrackReference} for which the information is requested.
   * @return the track number of the {@code TrackReference} in the list of track references of the disc, starting at 1. If the {@code TrackReference} is not part of a disc, -1 is returned.
   */
  public static int getTrackNr(TrackReference trackReference) {
    EObject container = trackReference.eContainer();

    if (container instanceof Disc disc) {
      EList<TrackReference> tracks = disc.getTrackReferences();
      int myIndex = tracks.indexOf(trackReference);

      return myIndex + 1;
    } else {
      return -1;
    }
  }

  /**
   * Get the {@code Disc} of a {@code TrackReference}.
   * 
   * @param trackReference the {@code TrackReference} for which the information is requested.
   * @return the {@code Disc} of the {@code TrackReference}, or null if the {@code TrackReference} is not part of a disc.
   */
  public static Disc getDisc(TrackReference trackReference) {
    EObject eContainer = trackReference.eContainer();
    if (eContainer instanceof Disc disc) {
      return disc;
    } else {
      return null;
    }
  }
  
  /**
   * Check whether a {@code TrackReference} is a dummy track reference, which is the case if the track is not set and there is no track reference in the {@code MyTrackInfo}.
   * @param trackReference
   * @return
   */
  public static boolean isDummyTrackReference(TrackReference trackReference) {
    if (trackReference.getTrack() != null) {
      return false;
    }
    
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo == null) {
      return true;
    }
    
    TrackReference referredTrackReference = myTrackInfo.getTrackReference();
    if (referredTrackReference == null) {
      return true;
    }
    
    return false;
  }
  
  /**
   * Check whether I have a track, specified by a {@code TrackReference}, on hard disk or not.
   * <p>
   * I can either have the specified {@code TrackReference} on hard disk, or the {@code TrackReference} to which the {@code MyTrackInfo.trackReference} refers (and so on).<br/>
   * I have a track on hard disk if there is a MediumInfo of type MediumType.HARDDISK.
   * 
   * @param trackReference reference for the track to check.
   * @return true I have the track, specified by a {@code trackReference}, on hard disk. False otherwise.
   */
  public static boolean doIHaveTrackOnHardDisk(TrackReference trackReference) {
    Objects.requireNonNull(trackReference, "Parameter trackReference may not be null");
    
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    
    if (myTrackInfo != null) {
      List<MediumInfo> mediumInfos = myTrackInfo.getIHaveOn();
      for (MediumInfo mediumInfo: mediumInfos) {
        if ((mediumInfo.getMediumType() == MediumType.HARDDISK)) {
          return true;
        }
      }
      
      TrackReference referredTrackReference = myTrackInfo.getTrackReference();
      if (referredTrackReference != null) {
        return doIHaveTrackOnHardDisk(referredTrackReference);
      }
    }      
    
    return false;
  }
  
  /**
   * Create a copy of a {@code TrackReference}.
   * 
   * @param trackReference the {@code TrackReference} to be copied.
   * @return a {@code TrackReference}, being a copy of the specified {@code trackReference}.
   */
  public static TrackReference createTrackReferenceCopy(TrackReference trackReference) {
    TrackReference trackReferenceCopy = MEDIA_DB_FACTORY.createTrackReference();
    
    String bonusTrack = trackReference.getBonusTrack();
    if (bonusTrack != null) {
      trackReferenceCopy.setBonusTrack(new String(bonusTrack));
    }
    trackReferenceCopy.setTrack(trackReference.getTrack());
    trackReferenceCopy.setOriginalAlbumTrackReference(trackReference.isOriginalAlbumTrackReference());
    
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo != null) {
      trackReferenceCopy.setMyTrackInfo(createMyTrackInfoCopy(myTrackInfo));
    }
    
    return trackReferenceCopy;
  }
  

  /*
   * Track related methods
   */

  /**
   * Get the {@code TrackReference} of the original album.
   * <p>
   * This is the 'referred by' {@code TrackReference} which has its {@code originalAlbumTrackReference} set.
   * 
   * @param track the {@code Track} for which the information is requested.
   * @return the {@code TrackReference} of the original album, or null if such a reference doesn't exist.
   */
  public static TrackReference getOriginalDiscTrackReference(Track track) {
    for (TrackReference trackReference : track.getReferredBy()) {
      if (trackReference.isOriginalAlbumTrackReference()) {
        return trackReference;
      }
    }

    return null;
  }

  /**
   * Get the track artist. If {@code artist} is set, this is returned. Else, if {@code originalDisc} is set, the artist of the album of that disc is returned.
   * 
   * @param track the {@code Track} for which the information is requested.
   * @return the {@code Artist} of the track, or null if no artist is set and no original disc is set.
   */
  public static Artist getTrackArtist(Track track) {
    Artist trackArtist = track.getArtist();

    if (trackArtist == null) {
      Disc disc = MediaDbUtil.getOriginalDisc(track);
      if (disc != null) {
        Album album = MediaDbUtil.getAlbum(disc);
        trackArtist = album.getArtist();
      }
    }

    return trackArtist;
  }

  /**
   * Get the {@code Disc} of the original album.
   * <p>
   * This is equal to calling {@code getOriginalDiscTrackReference()} followed by calling {@code getDisc()} on the obtained {@code TrackReference} (if it isn't null).
   * 
   * @param track the {@code Track} for which the information is requested.
   * @return the {@code Disc} of the original album, or null if such a disc doesn't exist.
   */
  public static Disc getOriginalDisc(Track track) {
    Disc originalDisc = null;

    TrackReference trackReference = MediaDbUtil.getOriginalDiscTrackReference(track);
    if (trackReference != null) {
      originalDisc = MediaDbUtil.getDisc(trackReference);
    }

    return originalDisc;
  }
  
  
  /*
   * TrackPart related methods
   */

  /**
   * Create a copy of a {@code TrackPart}.
   * 
   * @param trackPart the  {@code TrackPart} to be copied.
   * @return a  {@code TrackPart}, being a copy of the specified  {@code trackPart}.
   */
  public static TrackPart createTrackPartCopy(TrackPart trackPart) {
    TrackPart trackPartCopy = MEDIA_DB_FACTORY.createTrackPart();

    trackPartCopy.setTitle(new String(trackPart.getTitle()));

    return trackPartCopy;
  }
  
  
  /*
   * MyInfo related methods
   */
  
  /**
   * Create a copy of a {@code MyInfo}.
   * 
   * @param myInfo the {@code MyInfo} to be copied.
   * @return a {@code MyInfo}, being a copy of the specified {@code myInfo}.
   */
  public static MyInfo createMyInfoCopy(MyInfo myInfo) {
    MyInfo myInfoCopy = MEDIA_DB_FACTORY.createMyInfo();
    
    myInfoCopy.getAlbumReferences().addAll(myInfo.getAlbumReferences());
    
    myInfoCopy.setMyComments(myInfo.getMyComments());
    
    return myInfoCopy;
  }
  
  
  /*
   * MyTrackInfo related methods
   */
  
  
  /**
   * Create a copy of a {@code MyTrackInfo}.
   * 
   * @param myTrackInfo the {@code MyTrackInfo} to be copied.
   * @return a {@code MyTrackInfo}, being a copy of the specified {@code myTrackInfo}.
   */
  public static MyTrackInfo createMyTrackInfoCopy(MyTrackInfo myTrackInfo) {
    MyTrackInfo myTrackInfoCopy = MEDIA_DB_FACTORY.createMyTrackInfo();
    
    myTrackInfoCopy.setIWant(myTrackInfo.getIWant());
    
    for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
      myTrackInfoCopy.getIHaveOn().add(createMediumInfoCopy(mediumInfo));
    }
    
    if (myTrackInfo.getTrackReference() != null) {
      myTrackInfoCopy.setTrackReference(createTrackReferenceCopy(myTrackInfo.getTrackReference()));
    }
    
    return myTrackInfoCopy;
  }

  
  /*
   * MediumInfo related methods
   */
  
  /**
   * Check whether a list of {@code MediumInfo} contains a specific {@code MediumInfo}.
   * 
   * @param mediumInfoList a list of {@code MediumInfo}
   * @param mediumInfo a {@code MediumInfo}
   * @return true if the {@code mediumInfoList} contains {@code mediumInfo}, false otherwise.
   */
  private static boolean mediumInfosListContainsMediumInfo(List<MediumInfo> mediumInfoList, MediumInfo mediumInfo) {
    for (MediumInfo currentMediumInfo: mediumInfoList) {
      if (mediumInfosAreEqual(currentMediumInfo, mediumInfo)) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Check whether two {@code MediumInfo} instances are equal.
   * 
   * @param mediumInfo1 the first {@code MediumInfo}.
   * @param mediumInfo2 tge second {@code MediumInfo}
   * @return true if {@code mediumInfo1} and {@code mediumInfo2} are equal, false otherwise.
   */
  private static boolean mediumInfosAreEqual(MediumInfo mediumInfo1, MediumInfo mediumInfo2) {
    if (mediumInfo1.getMediumType() == null  &&  mediumInfo2.getMediumType() != null) {
      return false;
    }
    
    if (mediumInfo2.getMediumType() != null  &&  mediumInfo1.getMediumType() == null) {
      return false;
    }
    
    if (mediumInfo1.getMediumType() != null  &&  mediumInfo2.getMediumType() != null) {
      if (!mediumInfo1.getMediumType().equals(mediumInfo2.getMediumType())) {
        return false;
      }
    }
    
    if (mediumInfo1.getSourceType() != null  &&  mediumInfo2.getSourceType() != null) {
      if (!mediumInfo1.getSourceType().equals(mediumInfo2.getSourceType())) {
        return false;
      }
    }
    
    if (mediumInfo1.isSetSourceBitRate()  &&  !mediumInfo2.isSetSourceBitRate()) {
      return false;
    }
    
    if (mediumInfo2.isSetSourceBitRate()  &&  !mediumInfo1.isSetSourceBitRate()) {
      return false;
    }
    
    if (mediumInfo1.isSetSourceBitRate()  &&  mediumInfo2.isSetSourceBitRate()) {
      if (mediumInfo1.getSourceBitRate() != mediumInfo2.getSourceBitRate()) {
        return false;
      }
    }

    return true;
  }
  
  /**
   * Create a copy of a {@code MediumInfo}.
   * 
   * @param mediumInfo the {@code MediumInfo} to be copied.
   * @return a {@code MediumInfo}, being a copy of the specified {@code mediumInfo}.
   */
  public static MediumInfo createMediumInfoCopy(MediumInfo mediumInfo) {
    MediumInfo mediumInfoCopy = MEDIA_DB_FACTORY.createMediumInfo();
    
    mediumInfoCopy.setMediumType(mediumInfo.getMediumType());
    
    if (mediumInfo.isSetSourceBitRate()) {
      mediumInfoCopy.setSourceBitRate(mediumInfo.getSourceBitRate());
    }
    
    mediumInfoCopy.setSourceType(mediumInfo.getSourceType());
    
    return mediumInfoCopy;
  }
  
  
  /*
   * DiscAndTrackNrs related methods
   */
  
  /**
   * Create a copy of a {@code DiscAndTrackNrs}.
   * 
   * @param discAndTrackNrs the {@code DiscAndTrackNrs} to be copied.
   * @return a {@code DiscAndTrackNrs}, being a copy of the specified {@code discAndTrackNrs}.
   */
  public static DiscAndTrackNrs createDiscAndTrackNrsCopy(DiscAndTrackNrs discAndTrackNrs) {
    DiscAndTrackNrs discAndTrackNrsCopy = MEDIA_DB_FACTORY.createDiscAndTrackNrs();
    
    discAndTrackNrsCopy.setDiscNr(discAndTrackNrs.getDiscNr());
    
    for (Integer trackNr:  discAndTrackNrs.getTrackNrs()) {
      discAndTrackNrsCopy.getTrackNrs().add(Integer.valueOf(trackNr));
    }
    
    return  discAndTrackNrsCopy;
  }
  
  /**
   * Set the IHaveOn information of an Album.
   * <p>
   * The IHaveOn information is always set per track (of all tracks of the album).<br/>
   * If <code>iHaveOn</code> is null, nothing is done.<br/>
   * 
   * @param album the Album for which the information is to be set.
   * @param iHaveOn the value to set the IHaveOn information to.
   */
//  public static void setIHaveOn(Album album, List<MediumInfo> iHaveOn) {
//    if (iHaveOn == null  || iHaveOn.isEmpty()) {
//      return;
//    }
//    
//    if (album.getDiscs().isEmpty()) {
//      MyInfo myInfo;
//      if (!album.isSetMyInfo()) {
//        myInfo = MEDIA_DB_FACTORY.createMyInfo();
//        album.setMyInfo(myInfo);
//      } else {
//        myInfo = album.getMyInfo();
//      }
//      for (MediumInfo mediumInfo: iHaveOn) {
//        myInfo.getIHaveOn().add(createMediumInfoCopy(mediumInfo));
//      }
//    } else {
//      for (Disc disc: album.getDiscs()) {
//        for (TrackReference trackReference: disc.getTrackReferences()) {
//          Track track = trackReference.getTrack();
//          if (track == null) {
//            LOGGER.severe("track is null");
//            LOGGER.severe("STOP");
//          }
//          MyTrackInfo myTrackInfo;
//          if (trackReference.isSetMyTrackInfo()) {
//            myTrackInfo = trackReference.getMyTrackInfo();
//          } else {
//            myTrackInfo = MEDIA_DB_FACTORY.createMyTrackInfo();
//            trackReference.setMyTrackInfo(myTrackInfo);
//          }
//          myTrackInfo.getIHaveOn().clear();
//          
//          for (MediumInfo mediumInfo: iHaveOn) {
//            myTrackInfo.getIHaveOn().add(createMediumInfoCopy(mediumInfo));
//          }
//        }
//      }
//    }
//  }
  
}
