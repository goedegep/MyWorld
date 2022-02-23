package goedegep.gpx.parser.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class Metadata extends Extension {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
    
	private String name;
	private String desc;
	private Person author;
	private Copyright copyright;
	private HashSet<Link> links;
	private Date time;
	private String keywords;
	private Bounds bounds;

	public Metadata() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
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

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

  public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
    if (!setValuesOnly  ||  (name != null)) {
      buf.append(indent.toString()).append("name: ").append(name).append(NEWLINE);
    }
    
    if (!setValuesOnly  ||  (desc != null)) {
      buf.append(indent.toString()).append("desc: ").append(desc).append(NEWLINE);
    }
    
    if (!setValuesOnly  ||  (author != null)) {
      buf.append(indent.toString()).append("author:").append(NEWLINE);
      indent.increment();
      if (author != null) {
        author.toString(buf, indent, setValuesOnly);
      } else {
        buf.append(indent.toString()).append("<no author>").append(NEWLINE);
      }
      indent.decrement();
    }
    
    if (!setValuesOnly  ||  (copyright != null)) {
      buf.append(indent.toString()).append("copyright:").append(NEWLINE);
      indent.increment();
      if (copyright != null) {
        copyright.toString(buf, indent, setValuesOnly);
      } else {
        buf.append(indent.toString()).append("<no copyright>").append(NEWLINE);
      }
      indent.decrement();
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
    
    if (!setValuesOnly  ||  (time != null)) {
      buf.append(indent.toString()).append("time: ").append(time != null ? DF.format(time) : "<no time>").append(NEWLINE);
    }

    if (!setValuesOnly  ||  (keywords != null)) {
      buf.append(indent.toString()).append("keywords: ").append(keywords).append(NEWLINE);
    }
    
    if (!setValuesOnly  ||  (bounds != null)) {
      buf.append(indent.toString()).append("bounds:").append(NEWLINE);
      indent.increment();
      if (bounds != null) {
        bounds.toString(buf, indent, setValuesOnly);
      } else {
        buf.append("<no bounds>");
      }
      indent.decrement();
    }
  }
}
