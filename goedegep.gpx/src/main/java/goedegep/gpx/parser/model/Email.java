package goedegep.gpx.parser.model;

import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class Email {
    private static final String NEWLINE = System.getProperty("line.separator");

    private String id;
    private String domain;

    public Email(String id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public Email(String emailId) {
        String[] split = emailId.split("@");
        this.id = split[0];
        this.domain = split[1];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
      if (!setValuesOnly  ||  (id != null)) {
        buf.append(indent.toString()).append("id: ").append(id).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (domain != null)) {
        buf.append(indent.toString()).append("domain: ").append(domain).append(NEWLINE);
      }
    }
}
