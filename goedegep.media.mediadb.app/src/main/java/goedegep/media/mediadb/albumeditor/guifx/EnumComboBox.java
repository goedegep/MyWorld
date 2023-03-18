package goedegep.media.mediadb.albumeditor.guifx;

import java.util.logging.Logger;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class EnumComboBox<T extends Enum<T>> extends ComboBox<T> {
  private static final Logger         LOGGER = Logger.getLogger(EnumComboBox.class.getName());

  public EnumComboBox(T enumConstant) {
    setConverter(new StringConverter<T>() {

      @Override
      public String toString(T enumConstant) {
        return (enumConstant != null ? enumConstant.toString() : "<null>");
      }

      @Override
      public T fromString(String string) {
        LOGGER.severe("=> " + string);
        for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
          if (constant.toString().equals(string)) {
            return constant;
          }
        }
        return null;
      }
      
    } );

    for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
      getItems().add(constant);
    }
    
  }

}
