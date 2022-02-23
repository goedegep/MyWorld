package goedegep.invandprop.app.guifx;

import goedegep.invandprop.model.Property;
import javafx.util.StringConverter;

public class PropertyStringConverter extends StringConverter<Property>  {

  @Override
  public String toString(Property property) {
    if (property == null) {
      return null;
    }
    
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;
    
    if (property.getDescription() != null) {
      buf.append(property.getDescription());
      spaceNeeded = true;
    }
    
    if (property.getBrand() != null) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(property.getBrand());
    }
    
    if (property.getType() != null) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(property.getType());
    }
    
    return buf.toString();
  }

  @Override
  public Property fromString(String string) {
    throw new UnsupportedOperationException("This method isn't implemented yet");
  }

}
