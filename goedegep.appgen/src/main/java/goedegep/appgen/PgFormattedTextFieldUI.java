package goedegep.appgen;

import javax.swing.plaf.metal.MetalTextFieldUI;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.beans.PropertyChangeEvent;
import javax.swing.text.Element;
import javax.swing.text.View;

/**
 * Title: Formatted Input Field
 * Description: Modified version of FormattedTextField from the book
 * Core Swing Advanced Programmin.
 * @author Kim Topley, Peter Goedegebure
 * @version 0.1
 *
 */

public class PgFormattedTextFieldUI extends MetalTextFieldUI implements PropertyChangeListener {
  protected PgFormattedTextField.FormatSpec formatSpec;
  protected PgFormattedTextField editor;

  public static ComponentUI createUI(JComponent c) {
    return new PgFormattedTextFieldUI();
  }

  public PgFormattedTextFieldUI() {
    super();
  }

  public void installUI(JComponent c) {
    super.installUI(c);

    if (c instanceof PgFormattedTextField) {
      c.addPropertyChangeListener(this);
      editor = (PgFormattedTextField)c;
      formatSpec = editor.getFormatSpec();
    }
  }

  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    c.removePropertyChangeListener(this);
  }

  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(PgFormattedTextField.FORMAT_PROPERTY)) {
      // Install the new format specification
      formatSpec = editor.getFormatSpec();

      // Recreate the View hierarchy
      modelChanged();
    }
  }

  // ViewFactory method - creates a view
  public View create(Element elem) {
    return new PgFormattedFieldView(elem, formatSpec);
  }

}
