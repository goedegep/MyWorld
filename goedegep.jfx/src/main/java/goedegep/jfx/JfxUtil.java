package goedegep.jfx;

import java.util.logging.Logger;

import javafx.scene.paint.Color;

/**
 * This class provide utility methods to be used together with JavaFx.
 */
public class JfxUtil {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(JfxUtil.class.getName());
  
  public static Color awtColorToFxColor(java.awt.Color awtColor) {
    int r = awtColor.getRed();
    int g = awtColor.getGreen();
    int b = awtColor.getBlue();
    int a = awtColor.getAlpha();
    double opacity = a / 255.0 ;
    
    return javafx.scene.paint.Color.rgb(r, g, b, opacity);
  }
  
  /**
   * Generate a hex string, to be used in e.g. CSS style sheets, from a javafx.scene.paint.Color.
   * <p>
   * The generated string has the format '#rrggbb'.
   * 
   * @param color the Color for which a hex string is to be created.
   * @return a hex string 
   */
  public static String colorToCssString(Color color) {
    StringBuilder buf = new StringBuilder();
    
    buf.append("#");
    double colorComponentValue = color.getRed() * 255;
    int colorComponentIntValue = (int) colorComponentValue;
    if (colorComponentIntValue < 16) {
      buf.append("0");
    }
    buf.append(Integer.toHexString(colorComponentIntValue));
    
    colorComponentValue = color.getGreen() * 255;
    colorComponentIntValue = (int) colorComponentValue;
    if (colorComponentIntValue < 16) {
      buf.append("0");
    }
    buf.append(Integer.toHexString(colorComponentIntValue));
    
    colorComponentValue = color.getBlue() * 255;
    colorComponentIntValue = (int) colorComponentValue;
    if (colorComponentIntValue < 16) {
      buf.append("0");
    }
    buf.append(Integer.toHexString(colorComponentIntValue));
    
    return buf.toString();
  }
}
