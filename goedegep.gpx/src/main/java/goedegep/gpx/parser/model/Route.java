
package goedegep.gpx.parser.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.util.text.Indent;

public class Route extends Extension {
  private static final Logger LOGGER = Logger.getLogger(Route.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private String name;
  private String comment;
  private String description;
  private String src;
  private HashSet<Link> links;
  private int number;

  private String type;
  private ArrayList<Waypoint> routePoints;

  /**
   * Returns the name of this route.
   *
   * @return A String representing the name of this route.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for route name property. This maps to &lt;name&gt; tag value.
   *
   * @param name A String representing the name of this route.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the comment of this route.
   *
   * @return A String representing the comment of this route.
   */
  public String getComment() {
    return comment;
  }

  /**
   * Setter for route comment property. This maps to &lt;comment&gt; tag value.
   *
   * @param comment A String representing the comment of this route.
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Returns the description of this route.
   *
   * @return A String representing the description of this route.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter for route description property. This maps to &lt;description&gt; tag value.
   *
   * @param description A String representing the description of this route.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the src of this route.
   *
   * @return A String representing the src of this route.
   */
  public String getSrc() {
    return src;
  }

  /**
   * Setter for src type property. This maps to &lt;src&gt; tag value.
   *
   * @param src A String representing the src of this route.
   */
  public void setSrc(String src) {
    this.src = src;
  }

  /**
   * Returns the number of this route.
   *
   * @return A String representing the number of this route.
   */
  public Integer getNumber() {
    return number;
  }

  /**
   * Setter for route number property. This maps to &lt;number&gt; tag value.
   *
   * @param number An Integer representing the number of this route.
   */
  public void setNumber(Integer number) {
    this.number = number;
  }

  /**
   * Returns the type of this route.
   *
   * @return A String representing the type of this route.
   */
  public String getType() {
    return type;
  }

  /**
   * Setter for route type property. This maps to &lt;type&gt; tag value.
   *
   * @param type A String representing the type of this route.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Getter for the list of waypoints of this route.
   *
   * @return an ArrayList of {@link Waypoint} representing the points of the route.
   */
  public ArrayList<Waypoint> getRoutePoints() {
    return routePoints;
  }

  /**
   * Setter for the list of waypoints of this route.
   *
   * @param routePoints an ArrayList of {@link Waypoint} representing the points of the route.
   */
  public void setRoutePoints(ArrayList<Waypoint> routePoints) {
    this.routePoints = routePoints;
  }

  /**
   * Adds this new waypoint to this route.
   *
   * @param waypoint a {@link Waypoint}.
   */
  public void addRoutePoint(Waypoint waypoint) {
    if (routePoints == null) {
      routePoints = new ArrayList<Waypoint>();
    }
    routePoints.add(waypoint);
  }

  public HashSet<Link> getLinks() {
    return links;
  }

  public void setLinks(HashSet<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    if (links == null) {
      links = new HashSet<>();
    }
    links.add(link);
  }

  public WGS84BoundingBox getBoundingBox() {
    LOGGER.severe("=>");
    WGS84BoundingBox boundingBox = null;
    boolean first = true;
    for (Waypoint routePoint : routePoints) {
      if (first) {
        boundingBox = new WGS84BoundingBox(routePoint.getLongitude(), routePoint.getLatitude());
        first = false;
      } else {
        boundingBox = boundingBox.extend(routePoint.getLongitude(), routePoint.getLatitude());
      }
    }
    LOGGER.severe("=> " + boundingBox.toString());
    return boundingBox;
  }

  /**
   * Returns a String representation of this track.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("rte[");
    sb.append("name:" + name + " ");
    int points = 0;
    if (routePoints != null) {
      points = routePoints.size();
    }
    sb.append("rtepts:" + points + " ");
    sb.append("]");
    return sb.toString();
  }

  public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
    if (!setValuesOnly  ||  (name != null)) {
      buf.append(indent.toString()).append("name: ").append(name).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (comment != null)) {
      buf.append(indent.toString()).append("comment: ").append(comment).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (description != null)) {
      buf.append(indent.toString()).append("description: ").append(description).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (src != null)) {
      buf.append(indent.toString()).append("src: ").append(src).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (number != 0)) {
      buf.append(indent.toString()).append("number: ").append(number).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (type != null)) {
      buf.append(indent.toString()).append("type: ").append(type).append(NEWLINE);
    }

    if (!setValuesOnly  ||  (links != null)) {
      buf.append(indent.toString()).append("links:").append(NEWLINE);
      indent.increment();
      if (links != null) {
        for (Link link: links) {
          buf.append(indent.toString()).append("link:").append(NEWLINE);
          indent.increment();
          link.toString(buf, indent, setValuesOnly);
          indent.decrement();
        }
      } else {
        buf.append(indent.toString()).append("<no links>").append(NEWLINE);
      }
      indent.decrement();
    }

    if (!setValuesOnly  ||  (routePoints != null)) {
      buf.append(indent.toString()).append("routePoints:").append(NEWLINE);
      indent.increment();
      if (routePoints != null) {
        for (Waypoint waypoint: routePoints) {
          buf.append(indent.toString()).append("routePoint:").append(NEWLINE);
          indent.increment();
          waypoint.toString(buf, indent, setValuesOnly);
          indent.decrement();
        }
      } else {
        buf.append(indent.toString()).append("<no routePoints>").append(NEWLINE);
      }
      indent.decrement();
    }
  }
}
