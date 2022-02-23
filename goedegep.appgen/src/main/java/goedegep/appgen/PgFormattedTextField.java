package goedegep.appgen;

import javax.swing.JTextField;
import javax.swing.text.Document;

import java.awt.Font;
import javax.swing.text.AttributeSet;
import java.awt.Toolkit;
import java.util.Objects;

/**
 * Title: Formatted Input Field
 * Description: Modified version of FormattedTextField from the book
 * Core Swing Advanced Programmin.
 * @author Kim Topley, Peter Goedegebure
 * @version 0.1
 *
 */

@SuppressWarnings("serial")
public class PgFormattedTextField extends JTextField {
  
  protected           FormatSpec formatSpec;
  public static final String     FORMAT_PROPERTY = "format";

  public PgFormattedTextField() {
    this(null, null, 0, null);
  }

  public PgFormattedTextField(String text, FormatSpec spec) {
    this(null, text, 0, spec);
  }

  public PgFormattedTextField(int columns, FormatSpec spec) {
    this(null, null, columns, spec);
  }

  public PgFormattedTextField(String text, int columns, FormatSpec spec) {
    this(null, text, columns, spec);
  }

  public PgFormattedTextField(Document doc, String text, int columns, FormatSpec spec) {
    super(doc, text, columns);
    int fontSize = getFont().getSize();
    setFont(new Font("monospaced", Font.PLAIN, fontSize));
    if (spec != null) {
      setFormatSpec(spec);
    }
    setText(text);    // this shouldn't be needed. setFormatSpec seems to cause problem.
  }

  public void updateUI() {
    setUI(new PgFormattedTextFieldUI());
  }

  public FormatSpec getFormatSpec() {
    return formatSpec;
  }

  public void setFormatSpec(PgFormattedTextField.FormatSpec formatSpec) {
    FormatSpec oldFormatSpec = this.formatSpec;

    // Do nothing if no change to the format specification
    if (formatSpec.equals(oldFormatSpec) == false) {
      this.formatSpec = formatSpec;

      // Limit the input to the number of markers.
      Document doc = getDocument();
      if (doc instanceof PgBoundedPlainDocument) {
        ((PgBoundedPlainDocument)doc).setMaxLength(formatSpec.getMarkerCount());
      }

      // Notify a change in the format spec
      firePropertyChange(FORMAT_PROPERTY, oldFormatSpec, formatSpec);
    }
  }

	// Use a model that bounds the input length
  protected Document createDefaultModel() {
    PgBoundedPlainDocument doc = new PgBoundedPlainDocument();

    doc.addInsertErrorListener(new PgBoundedPlainDocument.InsertErrorListener() {
      public void insertFailed(PgBoundedPlainDocument doc, int offset, String str, AttributeSet a) {
        // Beep when the field is full
        Toolkit.getDefaultToolkit().beep();
      }
    });
    return doc;
  }

  public static class FormatSpec {
    private             String format;
    private             String mask;
    private             int    formatSize;
    private             int    markerCount;
    public static final char   MARKER_CHAR = '*';

    public FormatSpec(String format, String mask) {
      this.format = format;
      this.mask = mask;
      this.formatSize = format.length();
      if (formatSize != mask.length()) {
        throw new IllegalArgumentException("Format and mask must be the same size");
      }

      for (int i = 0; i < formatSize; i++) {
        if (mask.charAt(i) == MARKER_CHAR) {
          markerCount++;
        }
      }
    }

    public String getFormat() {
      return format;
    }

    public String getMask() {
      return mask;
    }

    public int getFormatSize() {
      return formatSize;
    }

    public int getMarkerCount() {
      return markerCount;
    }

    @Override
    public boolean equals(Object object) {
      if (object == null) {
        return false;
      }

      if (this == object) {
        return true;
      }

      if (getClass() != object.getClass()) {
        return false;
      }
      
      FormatSpec formatSpec = (FormatSpec) object;
      return format.equals(formatSpec.getFormat()) &&
             (formatSize == formatSpec.getFormatSize()) &&
             (markerCount == formatSpec.getMarkerCount()) &&
             mask.contentEquals(formatSpec.getMask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, formatSize, markerCount, mask);
    }

    public String toString() {
      return "FormatSpec with format <" + format + ">, mask <" + mask + ">";
    }

  }

}