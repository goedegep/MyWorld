package goedegep.gpx.parser.model;

import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class Person {
    private static final String NEWLINE = System.getProperty("line.separator");
    
    private String name;
    private Email email;
    private Link link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
      if (!setValuesOnly  ||  (name != null)) {
        buf.append(indent.toString()).append("name: ").append(name).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (email != null)) {
        buf.append(indent.toString()).append("email: ").append(NEWLINE);
        indent.increment();
        if (email != null) {
          email.toString(buf, indent, setValuesOnly);
        } else {
          buf.append("<no email>");
        }
        indent.decrement();
      }

      if (!setValuesOnly  ||  (link != null)) {
        buf.append(indent.toString()).append("link: ").append(NEWLINE);
        indent.increment();
        if (link != null) {
          link.toString(buf, indent, setValuesOnly);
        } else {
          buf.append("<no link>");
        }
        indent.decrement();
      }
    }
}
