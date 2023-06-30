package goedegep.media.mediadb.app.derivealbuminfo;

import java.util.List;

import goedegep.util.datetime.FlexDate;

public record AlbumInfo(String albumTitle, String albumArtist, FlexDate albumDate, List<DiscInfo> discs) {
  
}
