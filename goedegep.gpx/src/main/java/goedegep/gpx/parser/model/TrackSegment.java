package goedegep.gpx.parser.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class TrackSegment extends Extension {
  private static final Logger LOGGER = Logger.getLogger(TrackSegment.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private ArrayList<Waypoint> waypoints;

  public ArrayList<Waypoint> getWaypoints() {
    return waypoints;
  }

  public void setWaypoints(ArrayList<Waypoint> waypoints) {
    this.waypoints = waypoints;
  }

  public void addWaypoint(Waypoint wp) {
    if (waypoints == null) {
      waypoints = new ArrayList<>();
    }
    waypoints.add(wp);
  }

  public WGS84BoundingBox getBoundingBox() {
    LOGGER.severe("=>");
    WGS84BoundingBox boundingBox = null;
    boolean first = true;
    for (Waypoint waypoint : waypoints) {
      if (first) {
        boundingBox = new WGS84BoundingBox(waypoint.getLongitude(), waypoint.getLatitude());
        first = false;
      } else {
        boundingBox = boundingBox.extend(waypoint.getLongitude(), waypoint.getLatitude());
      }
    }
    LOGGER.severe("=> " + boundingBox.toString());
    return boundingBox;
  }

  public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
    if (!setValuesOnly  ||  (waypoints != null)) {
      buf.append(indent.toString()).append("waypoints:").append(NEWLINE);
      indent.increment();
      if (waypoints != null) {
        for (Waypoint waypoint: waypoints) {
          buf.append(indent.toString()).append("waypoint:").append(NEWLINE);
          indent.increment();
          waypoint.toString(buf, indent, setValuesOnly);
          indent.decrement();
        }
      } else {
        buf.append(indent.toString()).append("<no waypoints>").append(NEWLINE);
      }
      indent.decrement();
    }
  }
}
