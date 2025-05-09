package goedegep.media.mediadb.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.TrackPart;
import goedegep.media.mediadb.model.TrackReference;

/**
 * This class provides utility methods related to the MediaDb data model.
 */
public class MediaDbUtil {
  private static final Logger LOGGER = Logger.getLogger(MediaDbUtil.class.getName());
  
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
    
  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private MediaDbUtil() {
  }
    
  /**
   * Check whether I should have an Album completely on disc, which is the case if there is an 'IHaveOn' of type 'HARDDISK' at album level.
   * 
   * @param album the Album to be checked
   * @return true if I should have the album on disc, false otherwise.
   */
  public static boolean haveAlbumOnDisc(Album album) {
    LOGGER.info("=> album=" + album.getArtistAndTitle());
    
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      for (MediumInfo mediumInfo: myInfo.getIHaveOn()) {
        if (mediumInfo.getMediumType() == MediumType.HARDDISK) {
          return true;
        }
      }
    }
    LOGGER.info("<= false");
    return false;
  }
  
  /**
   * Check whether I should have an Album Disc on disc, which is the case if:
   * <ul>
   * <li>At album level is specified that I have the complete album on disc</li>
   * <li>Or no 'I have on' is specified at album level and the disc has track references which all state that I have them on disc.</li>
   * </ul>
   * 
   * @param disc the Disc to be checked
   * @return true if I should have the Disc on disc, false otherwise.
   */
  public static boolean haveDiscOnDisc(Disc disc) {
    LOGGER.info("=> disc=" + disc.getTitle());
    Album album = disc.getAlbum();
    
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      if (!myInfo.getIHaveOn().isEmpty()) {
        // 'I have on' is specified at album level, so return this value
        return haveAlbumOnDisc(album);
      }
    }
    
    if (disc.getTrackReferences().isEmpty()) {
      return false;
    }

    for (TrackReference trackReference: disc.getTrackReferences()) {
      if (!haveTrackOnDisc(trackReference)) {
        LOGGER.info("<= false");
        return false;
      }
    }

    LOGGER.info("<= true");
    return true;
  }
  
  /**
   * Get the collection of tracks of an album that I have.
   *  
   * @param album the album for which the information is requested.
   * @return a List of DiscAndTrackNrs, where each item provides the tracks I have for one disc.
   */
  public static List<DiscAndTrackNrs> getAlbumTracksIHave(Album album, boolean onDisc) {
    List<DiscAndTrackNrs> discAndTrackNrsList = new ArrayList<>();

    DiscAndTrackNrs discAndTrackNrs;
    for (Disc disc: album.getDiscs()) {
      discAndTrackNrs = getDiscTracksIHave(disc, onDisc);
      if (discAndTrackNrs != null) {
        discAndTrackNrsList.add(discAndTrackNrs);
      }
    }
    
    return discAndTrackNrsList;
  }

  /**
   * Get the collection of tracks of a disc that I have.
   * 
   * @param disc the disc for which the information is requested.
   * @return a DiscAndTrackNrs object with the tracks I have for this disc.
   */
  public static DiscAndTrackNrs getDiscTracksIHave(Disc disc, boolean onDisc) {
    DiscAndTrackNrs discAndTrackNrs = null;
    
    Album album = disc.getAlbum();
    
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
              ((onDisc == false)  ||  (mediumInfo.getMediumType() == MediumType.HARDDISK))) {
            // I have this track 
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
   * Get the collection of tracks of an album that I want.
   *  
   * @param album the album for which the information is requested.
   * @return a List of DiscAndTrackNrs, where each item provides the tracks I want for one disc.
   */
  public static List<DiscAndTrackNrs> getAlbumTracksIWant(Album album) {
    List<DiscAndTrackNrs> discAndTrackNrsList = new ArrayList<>();

    DiscAndTrackNrs discAndTrackNrs;
    for (Disc disc: album.getDiscs()) {
      discAndTrackNrs = getDiscTracksIWant(disc);
      if (discAndTrackNrs != null) {
        discAndTrackNrsList.add(discAndTrackNrs);
      }
    }
    
    return discAndTrackNrsList;
  }

  /**
   * Get the collection of tracks of a disc that I want.
   * 
   * @param disc the disc for which the information is requested.
   * @return a DiscAndTrackNrs object with the tracks I want for this disc.
   */
  public static DiscAndTrackNrs getDiscTracksIWant(Disc disc) {
    DiscAndTrackNrs discAndTrackNrs = null;
    
    Album album = disc.getAlbum();
    
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
   * Check whether an album is a single disc album.
   * 
   * @param album the Album to check
   * @return true if the {@code album} has one disc, false otherwise.
   */
  public static boolean isSingleDiscAlbum(Album album) {
    return album.getDiscs().size() == 1;
  }
  
  /**
   * Check whether an album is an Own Compilation album.
   * <p>
   * This is the case if the MyInfo exists and the AlbumType in the MyInfo is AlbumType.OWN_COMPILATION_ALBUM.
   * 
   * @param album the album to check
   * @return true if {@code album} is an Own Compilation album, false otherwise.
   */
  public static boolean isOwnCompilationAlbum(Album album) {
    return album.getMyInfo() != null  &&  album.getMyInfo().getAlbumType() == AlbumType.OWN_COMPILATION_ALBUM;
  }

  /**
   * Check whether I have a part of an Album on disc. A part means one or more tracks.
   * 
   * @param album the Album for which the information is requested.
   * @return true if I have the album partly on disc, false otherwise.
   */
  public static boolean haveAlbumPartlyOnDisc(Album album) {
    for (Disc disc: album.getDiscs()) {
      if (doIHaveOneOrMoreDiscTracksOnDisc(disc)) {
        return true;
      }
    }
    
    return false;
  }

  /**
   * Check whether I have a part of a Disc on disc. A part means one or more tracks.
   * <p>
   * I have a track on disc if:
   * <ul>
   * <li>The is an 'I have on' for harddisk</li>
   * <li>Or, the track is part of a collection</li>
   * </ul>
   * 
   * @param disc the Disc for which the information is requested.
   * @return true if I have the disc partly on disc, false otherwise.
   */
  public static boolean doIHaveOneOrMoreDiscTracksOnDisc(Disc disc) {
    for (TrackReference trackReference: disc.getTrackReferences()) {
      if (doIHaveTrackOnDisc(trackReference)) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Check whether I have a track, specified by a TrackReference, on disc or not.
   * <p>
   * I can either have the specified TrackReference on disc, or the TrackReference to which the MyTrackInfo.trackReference refers (and so on).<br/>
   * I have a track on disc if there is a MediumInfo of type MediumType.HARDDISK.
   * 
   * @param trackReference reference for the track to check.
   * @return true I have the track, specified by a trackReference, on disc. False otherwise.
   */
  public static boolean doIHaveTrackOnDisc(TrackReference trackReference) {
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
        return doIHaveTrackOnDisc(referredTrackReference);
      }
    }      
    
    return false;
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
   * @param album the album to be checked.
   * @return true if I have the album on CD, false otherwise.
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
   * @param album the album to be checked.
   * @return true if I have the album on SACD, false otherwise.
   */
  public static boolean haveAlbumOnSacd(Album album) {
    return haveAlbumOnMediumType(album, MediumType.SACD);
  }
  
  /**
   * Check whether I have an album on a specific medium type (e.g. CD_AUDIO or Super Audio CD).
   * <p>
   * This is about having the complete album on the specified medium type, so this information shall be specified at album level.
   * This is the case if all track references refer to tracks that:
   * <ul>
   * <li>iHaveOn set with mediumType set to the specified medium type.</li>
   * <li>have the original album set to this album.</li>
   * </ul>
   * Also there shall be at least one disc of which the list of tracks shall not be empty.
   * 
   * @param album the album to be checked.
   * @return true if I have the album on the specified medium type, false otherwise.
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
   * Check whether I have an album on a specific medium type (e.g. CD_AUDIO or Super Audio CD).
   * <p>
   * <p>
   * This is the case if all track references refer to tracks that:
   * <ul>
   * <li>iHaveOn set with mediumType set to the specified medium type.</li>
   * <li>have the original album set to this album.</li>
   * </ul>
   * Also there shall be at least one disc of which the list of tracks shall not be empty.
   * 
   * @param album the album to be checked.
   * @return true if I have the album on the specified medium type, false otherwise.
   */
  public static Set<MediumInfo> haveAlbumOnMediumTypes(Album album) {
    Set<MediumInfo> mediumInfos = new HashSet<>();
    
    // If there are no discs defined, I shouldn't have it.
    if (album.getDiscs().isEmpty()) {
      mediumInfos.clear();
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
  
  private static boolean mediumInfosListContainsMediumInfo(List<MediumInfo> mediumInfoList, MediumInfo mediumInfo) {
    for (MediumInfo currentMediumInfo: mediumInfoList) {
      if (mediumInfosAreEqual(currentMediumInfo, mediumInfo)) {
        return true;
      }
    }
    
    return false;
  }
  
  private static boolean mediumInfosAreEqual(MediumInfo mediumInfo1, MediumInfo mediumInfo2) {
    if (mediumInfo1.getMediumType() != null  &&  mediumInfo2.getMediumType() != null) {
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
   * Check whether I should have a Track on disc, which is the case if there is a 'I have on' of type HARDDISK.
   * 
   * @param trackReference the Track to be checked
   * @return true if I should have the Track on disc, false otherwise.
   */
  public static boolean haveTrackOnDisc(TrackReference trackReference) {
    LOGGER.info("=> track=" + (trackReference.getTrack() != null ? trackReference.getTrack().getTitle() : "no track"));

    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo == null) {
      LOGGER.info("<= false");
      return false;
    }
    
    for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
      if (mediumInfo.getMediumType() == MediumType.HARDDISK) {
        return true;
      }
    }
    
    LOGGER.info("<= false");
    return false;
  }
  
  /**
   * Set the IWant information of an Album.
   * <p>
   * If there are no tracks specified for the album, the IWant information is set in the MyInfo at album level,
   * otherwise the IWant information is set per track (of all tracks of the album).<br/>
   * If <code>iWant</code> is null, nothing is done.<br/>
   * If <code>iWant</code> is NOT_SET, the values will be unset.
   * 
   * @param album the Album for which the information is to be set.
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
   * Get the IWant information of an album at album level.
   * <p>
   * The result is the same as album.getMyInfo().getIWant(), but with checks on null values.
   * 
   * @param album the Album for which the information is requested.
   * @return the IWant information at album level for the {@code album}.
   */
  public static IWant getIWant(Album album) {
    IWant iWant = IWant.NOT_SET;
    
    // If the album tracks are not available, get it from album level, otherwise get it per track.
//    if (album.getDiscs().isEmpty()) {
    MyInfo myInfo = album.getMyInfo();
    if (myInfo != null) {
      iWant = myInfo.getIWant();
    }
//    } else {
//      for (Disc disc: album.getDiscs()) {
//        for (TrackReference trackReference: disc.getTrackReferences()) {
//          Track track = trackReference.getTrack();
//          if (track == null) {
//            LOGGER.severe("track is null");
//            LOGGER.severe("STOP");
//          }
//          MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
//          if (myTrackInfo != null) {
//            if (myTrackInfo.isSetIWant()) {
//              IWant trackIWant = myTrackInfo.getIWant();
//              if (trackIWant == IWant.NOT_SET) {
//                iWant = IWant.NOT_SET;
//                break;
//              }
//              if (iWant == IWant.NOT_SET) {
//                iWant = trackIWant;
//              } else if (iWant != trackIWant) {
//                iWant = IWant.NOT_SET;
//                break;
//              }
//            } else {
//              iWant = IWant.NOT_SET;
//              break;
//            }
//          } else {
//            iWant = IWant.NOT_SET;
//            break;
//          }
//        }
//      }
//    }
    
    return iWant;
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

  /**
   * Set the AlbumType for an album.
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
  
  /**
   * Create a copy of a TrackReference.
   * 
   * @param trackReference the TrackReference to be copied.
   * @return a TrackReference, being a copy of the specified <code>trackReference</code>.
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
    
  /**
   * Create a copy of a TrackPart.
   * 
   * @param trackPart the TrackPart to be copied.
   * @return a TrackPart, being a copy of the specified <code>trackPart</code>.
   */
  public static TrackPart createTrackPartCopy(TrackPart trackPart) {
    TrackPart trackPartCopy = MEDIA_DB_FACTORY.createTrackPart();
    
    trackPartCopy.setTitle(new String(trackPart.getTitle()));
         
    return trackPartCopy;
  }
  
  /**
   * Create a copy of a MediumInfo.
   * 
   * @param mediumInfo the MediumInfo to be copied.
   * @return a MediumInfo, being a copy of the specified <code>mediumInfo</code>.
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
  
  /**
   * Create a copy of a DiscAndTrackNrs.
   * 
   * @param discAndTrackNrs the DiscAndTrackNrs to be copied.
   * @return a DiscAndTrackNrs, being a copy of the specified <code>discAndTrackNrs</code>.
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
   * Create a copy of a MyInfo.
   * 
   * @param myInfo the MyInfo to be copied.
   * @return a MyInfo, being a copy of the specified <code>myInfo</code>.
   */
  public static MyInfo createMyInfoCopy(MyInfo myInfo) {
    MyInfo myInfoCopy = MEDIA_DB_FACTORY.createMyInfo();
    
    myInfoCopy.getAlbumReferences().addAll(myInfo.getAlbumReferences());
    
    myInfoCopy.setMyComments(myInfo.getMyComments());
    
    return myInfoCopy;
  }
  
  /**
   * Create a copy of a MyTrackInfo.
   * 
   * @param myTrackInfo the MyTrackInfo to be copied.
   * @return a MyTrackInfo, being a copy of the specified <code>myTrackInfo</code>.
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
  
}
