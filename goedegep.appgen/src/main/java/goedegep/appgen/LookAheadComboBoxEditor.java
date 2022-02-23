package goedegep.appgen;

import goedegep.util.text.TextLookAhead;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class LookAheadComboBoxEditor extends JTextField implements ComboBoxEditor {
  private final static Logger     LOGGER = Logger.getLogger(LookAheadComboBoxEditor.class.getName());

  private TextLookAhead lookAhead;
  private JComboBox<?> comboBox;

  public LookAheadComboBoxEditor(JComboBox<?> comboBox, int columns, TextLookAhead lookAhead) {
    super(columns);
    this.lookAhead = lookAhead;
    this.comboBox = comboBox;
    this.setBackground(Color.YELLOW);
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        // Remove any existing selection (I don't know if this is needed)
        setCaretPosition(getDocument().getLength());
      }
    });
    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent evt) {
        LOGGER.fine("LookAheadComboBoxEditor gained focus");
        // Select the complete text.
        setCaretPosition(0);
        moveCaretPosition(getDocument().getLength());
        for (FocusListener focusListener: comboBox.getFocusListeners()) {
          focusListener.focusGained(evt);
        }
      }

      public void focusLost(FocusEvent evt) {
        LOGGER.fine("LookAheadComboBoxEditor lost focus");
        if (evt.isTemporary() == false) {
          // Remove any existing selection
          setCaretPosition(getDocument().getLength());
          for (FocusListener focusListener: comboBox.getFocusListeners()) {
            focusListener.focusLost(evt);
          }
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
          comboBox.setSelectedItem(newContent);

          // Highlight the added text
          setCaretPosition(getText().length());
          moveCaretPosition(oldContent.length());
        }
      } catch (BadLocationException e) {
        // Won't happen
      }
    }
  }

  public Component getEditorComponent() {
    return this;
  }

  public void setItem(Object anObject) {
    if (anObject != null) {
      setText(anObject.toString());
    }
  }

  public Object getItem() {
    return comboBox.getSelectedItem();
  }

}
