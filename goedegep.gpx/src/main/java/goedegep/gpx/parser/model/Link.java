package goedegep.gpx.parser.model;

import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class Link {
  private static final String NEWLINE = System.getProperty("line.separator");
  
    private String href;

    private String text;
    private String type;

    public Link(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
      if (!setValuesOnly  ||  (href != null)) {
        buf.append(indent.toString()).append("href: ").append(href).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (text != null)) {
        buf.append(indent.toString()).append("text: ").append(text).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (type != null)) {
        buf.append(indent.toString()).append("type: ").append(type).append(NEWLINE);
      }
    }
}
