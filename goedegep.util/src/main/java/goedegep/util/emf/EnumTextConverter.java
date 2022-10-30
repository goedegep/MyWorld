package goedegep.util.emf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * This class provides a list of Strings for enum constants and it converts enum constants to texts and vice versa.
 * <p>
 * It is possible to specify a 'not set value', for which a null String will be used.
 * Main usage of this class is for setting the items of a ComboBox. Enums often have a 'not set value' like e.g. NOT_SET with literal '<not-set>'.
 * The '<not-set>' doesn't look nice in the values of a ComboBox. Via this class it is possible to show an empty field for this value.
 * <p>
 * By default the text for an enum constant is its name. When you specify an EEnum in the constructor, the literals of the enum constants are used.
 * Finally you can specify a Map for the mapping of the enum constants to their texts.
 *
 * @param <T> The Enum type.
 */
public class EnumTextConverter<T extends Enum<T>> {
  private static final Logger LOGGER = Logger.getLogger(EnumTextConverter.class.getName());
  
  /**
   * The enum constant representing the fact that no value is specified.
   */
  private T notSetValue;
  
  /**
   * Mapping of strings (as shown in a GUI) to the enum constants..
   */
  private Map<String, T> stringToEnumMap = new HashMap<>();
  
  /**
   * Mapping of enum constants to their texts.
   */
  private Map<T, String> enumToStringMap = new HashMap<>();
  
  /**
   * Constructor.
   * <p>
   * With this constructor the text for an enum constant is its name.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   */
  public EnumTextConverter(T enumConstant, T notSetValue) {
    for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
      if (notSetValue != null  &&  constant.name().equals(notSetValue.name())) {
        if (constant == notSetValue) {
          LOGGER.severe("This also works");
        } else {
          LOGGER.severe("No it doesn't work");
        }
        this.notSetValue = notSetValue;
      } else {
        stringToEnumMap.put(constant.name(), constant);
        enumToStringMap.put(constant, constant.name());
      }
    }
  }
  
  /**
   * Constructor.
   * <p>
   * With this constructor the texts for the enum constants is defined by the provided Map.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   */
  public EnumTextConverter(T enumConstant, T notSetValue, Map<T, String> enumToStringMap) {
    this.enumToStringMap = enumToStringMap;
    for (T constant: enumToStringMap.keySet()) {
      if (constant != notSetValue) {
        stringToEnumMap.put(enumToStringMap.get(constant), constant);
      } else {
        LOGGER.severe("Skipping not set value");
      }
    }
  }
  
  /**
   * Constructor.
   * <p>
   * With this constructor the text for an enum constant is its literal, as defined by the <code>eEnum</code>.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   * @param eEnum an EEnum, matching the other parameters.
   */
  public EnumTextConverter(T enumConstant, T notSetValue, EEnum eEnum) {
    List<EEnumLiteral> literals = eEnum.getELiterals();
    
    for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
      if ((notSetValue != null)  &&  (constant == notSetValue)) {
        this.notSetValue = notSetValue;
      } else {
        for (EEnumLiteral literal: literals) {
//          LOGGER.severe("literal.name: " + literal.getName() + ", literal.literal: " + literal.getLiteral());
          if (constant.name().equals(literal.getName())) {
//            LOGGER.severe("Match");
            stringToEnumMap.put(literal.getLiteral(), constant);
            enumToStringMap.put(constant, literal.getLiteral());
            break;
          }
        }
      }
    }
  }

  /**
   * Get the enum constant for a String.
   * 
   * @param string the String representation of the enum constant.
   * @return the enum constant for <code>string</code>, or null if <code>string</code> isn't one of the values in the Collection
   *         that can be obtained by getStringValues().
   */
  public T getEnumForString(String string) {
    if (string == null) {
      return (T) null;
    } else {
      return stringToEnumMap.get(string);
    }
  }
  
  /**
   * Get the string representation for an enum constant.
   * 
   * @param enumConstant the enum constant.
   * @return the string representation for the <code>enumConstant</code>.
   */
  public String getStringValue(T enumConstant) {
    if (enumConstant == null  ||  enumConstant == notSetValue) {
      return null;
    } else {
      String string = enumToStringMap.get(enumConstant);
      return string;
    }
  }
  
  /**
   * Get the List of string values for the Enum.
   * 
   * @return the sorted List of string values for the Enum.
   */
  public List<String> getStringValues() {
    List<String> stringValues = new ArrayList<>(stringToEnumMap.keySet());
    Collections.sort(stringValues);
    stringValues.add(0, null);
    
    return stringValues;
  }
  
}
