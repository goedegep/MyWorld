package goedegep.util.datetime;

import java.util.Optional;

import java.time.ZoneId;
import net.iakovlev.timeshape.TimeZoneEngine;

public class TimeZoneRetriever {
  
  private static TimeZoneEngine engine = TimeZoneEngine.initialize();

  public static ZoneId getZoneId(double latitude, double longitude) {
    Optional<ZoneId> maybeZoneId = engine.query(latitude, longitude);

    if (maybeZoneId.isPresent()) {
      return maybeZoneId.get();
    } else {
      return null;
    }
  }
}
