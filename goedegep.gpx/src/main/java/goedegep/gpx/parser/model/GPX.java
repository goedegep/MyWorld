package goedegep.gpx.parser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.util.text.Indent;

/**
 * This class holds gpx information from a &lt;gpx&gt; node. <br>
 * <p>
 * GPX specification for this tag:
 * </p>
 * <code>
 * &lt;gpx version="1.1" creator=""xsd:string [1]"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;metadata&gt; xsd:string &lt;/metadata&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;wpt&gt; xsd:string &lt;/wpt&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;rte&gt; xsd:string &lt;/rte&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;trk&gt; xsd:string &lt;/trk&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;extensions&gt; extensionsType &lt;/extensions&gt; [0..1]<br>
 * &lt;/gpx&gt;<br>
 * </code>
 * TODO: should this extend Extension, or have a reference to it? 
 */
public class GPX extends Extension {
  private static final Logger LOGGER = Logger.getLogger(GPX.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  // Attributes
  // TFE, 20180201: support multiple xmlns attributes
  private HashMap<String, String> xmlns;
  private String creator;
  private String version = "1.1";

  // Nodes
  private Metadata metadata;
  private HashSet<Route> routes;
  private HashSet<Track> tracks;
  private HashSet<Waypoint> waypoints;

  public GPX() {
    this.waypoints = new HashSet<>();
    this.tracks = new HashSet<>();
    this.routes = new HashSet<>();
  }

  /**
   * Adds a new Route to a gpx object
   *
   * @param route
   *            a {@link Route}
   */
  public void addRoute(Route route) {
    if (this.routes == null) {
      this.routes = new HashSet<>();
    }
    this.routes.add(route);
  }

  /**
   * Adds a new track to a gpx object
   *
   * @param track
   *            a {@link Track}
   */
  public void addTrack(Track track) {
    if (this.tracks == null) {
      this.tracks = new HashSet<>();
    }
    this.tracks.add(track);
  }

  /**
   * Adds a new waypoint to a gpx object
   *
   * @param waypoint
   *            a {@link Waypoint}
   */
  public void addWaypoint(Waypoint waypoint) {
    if (this.waypoints == null) {
      this.waypoints = new HashSet<>();
    }
    this.waypoints.add(waypoint);

  }

  /**
   * Returns the xmlns of this gpx object
   *
   * @return A HashMap<String, String> representing the xmlns of a gpx object
   */
  public HashMap<String, String> getXmlns() {
    return xmlns;
  }

  /**
   * Returns the creator of this gpx object
   *
   * @return A String representing the creator of a gpx object
   */
  public String getCreator() {
    return this.creator;
  }

  /**
   * Getter for the list of routes from a gpx object
   *
   * @return a HashSet of {@link Route}
   */
  public HashSet<Route> getRoutes() {
    return this.routes;
  }

  /**
   * Getter for the list of Tracks from a gpx objecty
   *
   * @return a HashSet of {@link Track}
   */
  public HashSet<Track> getTracks() {
    return this.tracks;
  }

  /**
   * Returns the version of a gpx object
   *
   * @return A String representing the version of this gpx object
   */
  public String getVersion() {
    return this.version;
  }

  /**
   * Getter for the list of waypoints from a gpx objecty
   *
   * @return a HashSet of {@link Waypoint}
   */
  public HashSet<Waypoint> getWaypoints() {
    return this.waypoints;
  }

  /**
   * Setter for gpx xmlns property. This maps to <i>xmlns</i> attribute
   * value.
   *
   * @param xmlns
   *            A HashMap<String, String> representing the xmlns of a gpx file.
   */
  public void setXmlns(HashMap<String, String> xmlns) {
    this.xmlns = xmlns;
  }

  /**
   * Setter for gpx creator property. This maps to <i>creator</i> attribute
   * value.
   *
   * @param creator
   *            A String representing the creator of a gpx file.
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }

  /**
   * Setter for the list of routes from a gpx object
   *
   * @param routes
   *            a HashSet of {@link Route}
   */
  public void setRoutes(HashSet<Route> routes) {
    this.routes = routes;
  }

  /**
   * Setter for the list of tracks from a gpx object
   *
   * @param tracks
   *            a HashSet of {@link Track}
   */
  public void setTracks(HashSet<Track> tracks) {
    this.tracks = tracks;
  }

  /**
   * Setter for the list of waypoints from a gpx object
   *
   * @param waypoints
   *            a HashSet of {@link Waypoint}
   */
  public void setWaypoints(HashSet<Waypoint> waypoints) {
    this.waypoints = waypoints;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Adds a new xmlns string into the xmlns data with the key set by xmlnsId.
   * 
   * @param xmlnsId a unique key representing the id of the xmlns content.
   * @param value a string holding the parsed information.
   */
  public void addXmlns(final String xmlnsId, final String value) {
    if(xmlns == null) {
      xmlns = new HashMap<>();
    }
    xmlns.put(xmlnsId, value);
  }
  
  public WGS84BoundingBox getBoundingBox() {
    LOGGER.severe("=>");
    boolean first = true;
    WGS84BoundingBox boundingBox = null;
    for (Route route : routes) {
      WGS84BoundingBox routeBoundingBox = route.getBoundingBox();
      if (routeBoundingBox != null) {
        if (first) {
          boundingBox = routeBoundingBox;
          first = false;
        } else {
          boundingBox = boundingBox.extend(routeBoundingBox);
        }
      }
    }
    for (Track track : tracks) {
      WGS84BoundingBox routeBoundingBox = track.getBoundingBox();
      if (routeBoundingBox != null) {
        if (first) {
          boundingBox = routeBoundingBox;
          first = false;
        } else {
          boundingBox = boundingBox.extend(routeBoundingBox);
        }
      }
    }
    for (Waypoint waypoint : waypoints) {
      LOGGER.severe("Handling waypoint");
      if (first) {
        boundingBox = new WGS84BoundingBox(waypoint.getLongitude(), waypoint.getLatitude());
      } else {
        boundingBox = boundingBox.extend(waypoint.getLongitude(), waypoint.getLatitude());
      }
    }
    LOGGER.severe("=> " + boundingBox.toString());
    return boundingBox;
  }
  
  public WGS84Coordinates getCenter() {
    WGS84BoundingBox boundingBox = getBoundingBox();
    return boundingBox.getCenter();
  }

  public String toString(boolean setValuesOnly) {
    StringBuilder buf = new StringBuilder();
    
    Indent indent = new Indent(4);

    if (!setValuesOnly  ||  (creator != null)) {
      buf.append(indent.toString()).append("creator: ").append(creator).append(NEWLINE);
    }
    
    if (!setValuesOnly  ||  (metadata != null)) {
      buf.append("metadata:").append(NEWLINE);
      indent.increment();
      if (metadata != null) {
        metadata.toString(buf, indent, setValuesOnly);
      } else {
        buf.append(indent.toString()).append("<no metadata>").append(NEWLINE);
      }
      indent.decrement();
    }
    
    if (!setValuesOnly  ||  !waypoints.isEmpty()) {
      buf.append("waypoints:").append(NEWLINE);
      indent.increment();
      if (!waypoints.isEmpty()) {
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
    
    if (!setValuesOnly  ||  !tracks.isEmpty()) {
      buf.append("tracks:").append(NEWLINE);
      indent.increment();
      if (!tracks.isEmpty()) {
        for (Track track: tracks) {
          buf.append(indent.toString()).append("track:").append(NEWLINE);
          indent.increment();
          track.toString(buf, indent, setValuesOnly);
          indent.decrement();
        }
      } else {
        buf.append(indent.toString()).append("<no tracks>").append(NEWLINE);
      }
      indent.decrement();
    }
    
    if (!setValuesOnly  ||  !routes.isEmpty()) {
    buf.append("routes:").append(NEWLINE);
    indent.increment();
    if (!routes.isEmpty()) {
      for (Route route: routes) {
        buf.append(indent.toString()).append("route:").append(NEWLINE);
        indent.increment();
        route.toString(buf, indent, setValuesOnly);
        indent.decrement();
      }
    } else {
      buf.append(indent.toString()).append("<no routes>").append(NEWLINE);
    }
    indent.decrement();
    }
    
    return buf.toString();
  }
}
