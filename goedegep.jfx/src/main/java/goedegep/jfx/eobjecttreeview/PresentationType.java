package goedegep.jfx.eobjecttreeview;

/**
 * This enumeration type specifies the ways (String) values can be presented.
 * 
 * <ul>
 * </ul>
 */
public enum PresentationType {
  BOOLEAN,           // displayed and edited via a checkbox
  SINGLE_LINE_TEXT,  // displayed and edited as single line text (TextField)
  MULTI_LINE_TEXT,   // displayed ... and edited via a TextArea
  ENUMERATION,       // displayed as single value, edited via a chooser
  FORMAT,            // displayed 
  FILE,              // displayed as single line text, edited via a FileChooser
  FOLDER             // displayed as single line text, edited via a DirectoryChooser
}
