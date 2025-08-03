package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EnumStringConverter<T extends Enum<T>> {

    private final Class<T> enumClass;
    private final Function<Object, String> toDisplayNameFunction;
    
    private Map<String, T> displayNameToEnumMap;
    private List<String> displayNames;
    private boolean initialized = false;
    

    public EnumStringConverter(Class<T> enumClass, Function<Object, String> toDisplayNameFunction) {
        this.enumClass = enumClass;
        this.toDisplayNameFunction = toDisplayNameFunction;
    }

    public String toDisplayName(Object object) {
      if (toDisplayNameFunction != null) {
          return toDisplayNameFunction.apply(object);
      } else {
        return object.toString();
      }
    }

    public T fromDisplayName(String displayName) {
      if (!initialized) {
          initialize();
      }
      
      return displayNameToEnumMap.get(displayName);
    }

    public List<String> getDisplayNames() {
      if (!initialized) {
        initialize();
      }
      
      return displayNames;
    }

    private void initialize() {
      displayNameToEnumMap = new HashMap<>();
      displayNames = new ArrayList<>();
      
      for (T enumConstant : enumClass.getEnumConstants()) {
          String displayName = toDisplayName(enumConstant);
          displayNameToEnumMap.put(displayName, enumConstant);
          displayNames.add(displayName);
      }
      
      Collections.sort(displayNames);
      
      initialized = true;
    }
}
