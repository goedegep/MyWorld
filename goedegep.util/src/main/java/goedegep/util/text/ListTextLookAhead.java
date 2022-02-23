package goedegep.util.text;

import java.util.ArrayList;
import java.util.List;

public class ListTextLookAhead<T> implements TextLookAhead {
  private List<T> values;
  private boolean ignoreCase = false;

  public ListTextLookAhead() {
    this(null, true);
  }

  public ListTextLookAhead(T[] values) {
    this.values = new ArrayList<T>();
    for (T value: values) {
      this.values.add(value);
    }
    ignoreCase = true;
  }

  public ListTextLookAhead(List<T> values) {
    this(values, true);
  }

  public ListTextLookAhead(List<T> values, boolean ignoreCase) {
    this.values = values;
    this.ignoreCase = ignoreCase;
  }

  public void setValues(List<T> values) {
    this.values = values;
  }

  public void setValues(T[] values) {
    this.values = new ArrayList<T>();
    for (T value: values) {
      this.values.add(value);
    }
  }

  public List<T> getValues() {
    return values;
  }

  public Object doLookAhead(String key) {
    if (ignoreCase) {
      key = key.toLowerCase();
    }

    // Look for a string that starts with the key
    for (T value: values) {
      if (ignoreCase) {
        if (value.toString().toLowerCase().startsWith(key) == true) {
          return value;
        }
      } else {
        if (value.toString().startsWith(key) == true) {
          return value;
        }
      }
    }

    // No match found - return null
    return null;
  }

}
