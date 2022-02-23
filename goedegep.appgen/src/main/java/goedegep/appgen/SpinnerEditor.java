package goedegep.appgen;

import goedegep.util.text.TextLookAhead;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class SpinnerEditor extends JTextField implements ChangeListener {
  protected TextLookAhead lookAhead;
  private JSpinner spinner;

  public SpinnerEditor(JSpinner spinner, int columns, TextLookAhead lookAhead) {
    super(columns);
    this.lookAhead = lookAhead;
    this.spinner = spinner;
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        // Remove any existing selection
        setCaretPosition(getDocument().getLength());
      }
    });
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent evt) {
      }

      public void focusLost(FocusEvent evt) {
        if (evt.isTemporary() == false) {
          // Remove any existing selection
          setCaretPosition(getDocument().getLength());
        }
      }
    });
    spinner.addChangeListener(this);
  }

  public void setLookAhead(TextLookAhead lookAhead) {
    this.lookAhead = lookAhead;
  }

  //    public PgTextLookAhead getLookAhead() {
  //      return lookAhead;
  //    }

  public void replaceSelection(String content) {
    super.replaceSelection(content);

    if (isEditable() == false || isEnabled() == false) {
      return;
    }

    Document doc = getDocument();
    if (doc != null && lookAhead != null) {
      try {
        String oldContent = doc.getText(0, doc.getLength());
        Object newContent = lookAhead.doLookAhead(oldContent);
        if (newContent != null) {
          // Substitute the new content
          setText(newContent.toString());
          spinner.setValue(newContent);

          // Highlight the added text
          setCaretPosition(getText().length());
          moveCaretPosition(oldContent.length());
        }
      } catch (BadLocationException e) {
        // Won't happen
      }
    }
  }

  public void stateChanged(ChangeEvent e) {
    if (e.getSource() instanceof JSpinner) {
      JSpinner spinner = (JSpinner) e.getSource();
      setText(spinner.getValue().toString());
    }
  }
}