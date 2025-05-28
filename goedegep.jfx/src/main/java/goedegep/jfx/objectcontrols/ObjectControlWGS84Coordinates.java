package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.stringconverterandchecker.WGS84CoordinatesFormatType;
import goedegep.jfx.stringconverterandchecker.WGS84CoordinatesStringConverterAndChecker;

/**
 * This class provides a TextField to be used to edit {@link WGS84Coordinates}.
 */
public class ObjectControlWGS84Coordinates extends ObjectControlTextField<WGS84Coordinates> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlWGS84Coordinates.class.getName());
  private static final WGS84CoordinatesStringConverterAndChecker WGS84_COORDINATES_STRING_CONVERTER = WGS84CoordinatesStringConverterAndChecker.getDecimalFormatInstance();
  
  /**
   * Constructor.
   * 
   * @param flexDate Initial date to be filled in in the TextField.
   * @param width Width of the TextField.
   * @param isOptional Indication of whether the value is an optional values.
   * @param toolTipText An optional Tooltip text.
   */
  public ObjectControlWGS84Coordinates(CustomizationFx customization, WGS84Coordinates coordinates, double width, boolean isOptional, String toolTipText) {
    super(customization, coordinates, width, isOptional, toolTipText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected WGS84Coordinates stringToObject(String valueAsString) {
    WGS84Coordinates coordinates = null;
    
//      try {
        coordinates = WGS84_COORDINATES_STRING_CONVERTER.fromString(valueAsString);
//      } catch (ParseException e) {
//        LOGGER.info("ParseException on value: " + valueAsString);
//      }
    
    return coordinates;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String objectToString(WGS84Coordinates coordinates) {
    return WGS84_COORDINATES_STRING_CONVERTER.toString(coordinates);
  }
}
