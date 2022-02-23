package goedegep.appgen.swing;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import goedegep.types.model.FileReference;

@SuppressWarnings("serial")
public class BestandReferentieListCellRenderer extends DefaultListCellRenderer {
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, 
        value, 
        index, 
        isSelected, 
        cellHasFocus);
    if (value instanceof FileReference) {
      FileReference bestandReferentie = (FileReference) value;

      if (bestandReferentie.getTitle() != null) {
        setText(bestandReferentie.getTitle());
      } else {
        setText(bestandReferentie.getFile());
      }
    } else {
      setText(value.toString());
    }
    
    return this;
  }

}
