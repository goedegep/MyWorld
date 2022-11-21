
package goedegep.gpx.parser.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.util.text.Indent;

public class Track extends Extension {
  private static final Logger LOGGER = Logger.getLogger(Track.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private String name;
  private String comment;
  private String description;
  private String src;
  private HashSet<Link> links;
  private int number;
  private String type;
  private ArrayList<TrackSegment> trackSegments;

  /**
   * Returns the name of this track.
   *
   * @return A String representing the name of this track.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for track name property. This maps to &lt;name&gt; tag value.
   *
   * @param name A String representing the name of this track.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the comment of this track.
   *
   * @return A String representing the comment of this track.
   */
  public String getComment() {
    return comment;
  }

  /**
   * Setter for track comment property. This maps to &lt;comment&gt; tag value.
   *
   * @param comment A String representing the comment of this track.
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Returns the description of this track.
   *
   * @return A String representing the description of this track.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter for track description property. This maps to &lt;description&gt; tag value.
   *
   * @param description A String representing the description of this track.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the src of this track.
   *
   * @return A String representing the src of this track.
   */
  public String getSrc() {
    return src;
  }

  /**
   * Setter for src type property. This maps to &lt;src&gt; tag value.
   *
   * @param src A String representing the src of this track.
   */
  public void setSrc(String src) {
    this.src = src;
  }

  /**
   * Returns the number of this track.
   *
   * @return A String representing the number of this track.
   */
  public Integer getNumber() {
    return number;
  }

  /**
   * Setter for track number property. This maps to &lt;number&gt; tag value.
   *
   * @param number An Integer representing the number of this track.
   */
  public void setNumber(Integer number) {
    this.number = number;
  }

  /**
   * Returns the type of this track.
   *
   * @return A String representing the type of this track.
   */
  public String getType() {
    return type;
  }

  /**
   * Setter for track type property. This maps to &lt;type&gt; tag value.
   *
   * @param type A String representing the type of this track.
   */
  public void setType(String type) {
    this.type = type;
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
    boolean first = true;
    WGS84BoundingBox boundingBox = null;
    for (TrackSegment trackSegment: trackSegments) {
      WGS84BoundingBox trackSegmentBoundingBox = trackSegment.getBoundingBox();
      if (trackSegmentBoundingBox != null) {
        if (first) {
          boundingBox = trackSegmentBoundingBox;
          first = false;
        } else {
          boundingBox = boundingBox.extend(trackSegmentBoundingBox);
        }
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
    sb.append("trk[");
    sb.append("name:" + name + " ");
    sb.append("]");
    return sb.toString();
  }

  public ArrayList<TrackSegment> getTrackSegments() {
    return trackSegments;
  }

  public void setTrackSegments(ArrayList<TrackSegment> trackSegments) {
    this.trackSegments = trackSegments;
  }

  public void addTrackSegment(TrackSegment trackSegment) {
    if (trackSegments == null) {
      trackSegments = new ArrayList<>();
    }
    trackSegments.add(trackSegment);
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

    if (!setValuesOnly  ||  (trackSegments != null)) {
      buf.append(indent.toString()).append("trackSegments:").append(NEWLINE);
      indent.increment();
      if (trackSegments != null) {
        for (TrackSegment trackSegment: trackSegments) {
          buf.append(indent.toString()).append("trackSegment:").append(NEWLINE);
          indent.increment();
          trackSegment.toString(buf, indent, setValuesOnly);
          indent.decrement();
        }
      } else {
        buf.append(indent.toString()).append("<no trackSegment>").append(NEWLINE);
      }
      indent.decrement();
    }
  }
}
