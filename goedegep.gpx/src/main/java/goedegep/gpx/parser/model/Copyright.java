package goedegep.gpx.parser.model;

import goedegep.util.text.Indent;

/**
 * Created by Himanshu on 7/5/2015.
 */
public class Copyright {
  private static final String NEWLINE = System.getProperty("line.separator");
  
	private String year;
	private String license;
	private String author;

	public Copyright(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

    public void toString(StringBuilder buf, Indent indent, boolean setValuesOnly) {
      if (!setValuesOnly  ||  (year != null)) {
        buf.append(indent.toString()).append("year: ").append(year).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (license != null)) {
        buf.append(indent.toString()).append("license: ").append(license).append(NEWLINE);
      }

      if (!setValuesOnly  ||  (author != null)) {
        buf.append(indent.toString()).append("author: ").append(author).append(NEWLINE);
      }
    }
}
