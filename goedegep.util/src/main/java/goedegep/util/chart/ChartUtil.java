package goedegep.util.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.util.logging.MyLoggingFormatter;


/**
 * This is a utility class with support methods for drawing a chart.
 */
public class ChartUtil {
  private static final Logger LOGGER = Logger.getLogger(ChartUtil.class.getName());
  
  /**
   * Private Constructor
   */
  private ChartUtil() {
    
  }

  /**
   * Calculate chart labels for a specific range.
   * 
   * @param min the minimum value
   * @param max the maximum value
   * @param requestedNumberOfLabels The number of labels requested. The actual number of labels may be slightly higher or lower.
   * @return the list of labels for the specified range
   */
  public static List<Double> calculateLabels(double min, double max, int requestedNumberOfLabels) {
    if (min >= max) {
      throw new IllegalArgumentException("max shall be greater than min: min = " + min + ", max = " + max);
    }
    
    double range = niceNumber(max - min, false);
    
    double labelSpacing = niceNumber(range / (requestedNumberOfLabels - 1), true);
    LOGGER.info("labelSpacing = " + labelSpacing);
    
    double axisMin = Math.floor(min / labelSpacing) * labelSpacing;
    double axisMax = Math.ceil(max / labelSpacing) * labelSpacing;
    LOGGER.info("axisMin=" + axisMin + ", axisMax=" + axisMax);
    
    List<Double> labels = new ArrayList<>();
    for (double label = axisMin; label < axisMax + .5 * labelSpacing; label += labelSpacing) {
      labels.add(label);
    }
    
    return labels;
  }
  
  /**
   * Find a "nice" number approximately equal to a specific {@code number}.
   * 
   * @param number the range for which the number is to be calculated
   * @param round if true, rounding is used, otherwise ceiling is used.
   * @return the nice number for {@code range}.
   */
  public static double niceNumber(double number, boolean round) {
    LOGGER.info("=> number=" + number + ", round=" + round);
    int exp = (int) Math.floor(Math.log10(number));
    LOGGER.info("exp = " + exp);
    
    double fraction = number / Math.pow(10., exp);   /* fraction between 1 and 10 */
    LOGGER.info("fraction = " + fraction);
    
    double y;
    if (round) {
      if (fraction < 1.5) {
        y = 1.0;
      } else if (fraction < 3. ) {
        y = 2.0;
      } else if (fraction < 7.) {
        y = 5.0;
      } else {
        y = 10.0;
      }
    } else { // ceiling
      if (fraction <= 1.0) {
        y = 1.0;
      } else if (fraction <= 2.0) {
        y = 2.0;
      } else if (fraction<=5.0) {
        y = 5.0;
      } else {
        y = 10.0;
      }
    }
    
    double result = y * Math.pow(10., exp);
    LOGGER.info("<= " + result);
    return y * Math.pow(10., exp);
  }
  
  /**
   * Show usage of calculateLabels.
   * 
   * @param args none
   */
  public static void main(String args[]) {
    logSetup(Level.SEVERE);
    double[][] inputValues = {
        {0.0, 100, 5},
        {-78, 204, 10},
        {3468, 123465, 20},
        {2.03, 2.0332, 4},
    };
    
    for (double[] inputValue: inputValues) {
      List<Double> labels = calculateLabels(inputValue[0], inputValue[1], (int) inputValue[2]);
      System.out.println("Labels for range: " + inputValue[0] + " - " + inputValue[1] + "  (requested number of labels is " + (int) inputValue[2] + " actual number of labels is " + labels.size() + ")");
      boolean first = true;
      for (Double label: labels) {
        if (first) {
          first = false;
        } else {
          System.out.print(", ");
        }
        System.out.print(label);
      }
      System.out.println();
      System.out.println();
    }
  }
  
  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install a {@link MyLoggingFormatter}.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   */
  protected static void logSetup(Level level) {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(level);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(level);
  }
}
