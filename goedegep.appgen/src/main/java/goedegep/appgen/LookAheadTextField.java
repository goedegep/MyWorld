package goedegep.appgen;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import goedegep.util.text.TextLookAhead;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class LookAheadTextField extends JTextField {
  private TextLookAhead lookAhead;

  public LookAheadTextField() {
    this(0, null);
  }

  public LookAheadTextField(int columns) {
    this(columns, null);
  }

  public LookAheadTextField(int columns, TextLookAhead lookAhead) {
    super(columns);
    this.lookAhead = lookAhead;
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        // Remove any existing selection
        setCaretPosition(getDocument().getLength());
      }
    });
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent evt) {
        // Select the complete text.
        setCaretPosition(0);
        moveCaretPosition(getDocument().getLength());
     }

      public void focusLost(FocusEvent evt) {
        if (evt.isTemporary() == false) {
          // Remove any existing selection
          setCaretPosition(getDocument().getLength());
        }
      }
    });
  }


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

          // Highlight the added text
          setCaretPosition(getText().length());
          moveCaretPosition(oldContent.length());
        }
      } catch (BadLocationException e) {
        // Won't happen
      }
    }
  }
}