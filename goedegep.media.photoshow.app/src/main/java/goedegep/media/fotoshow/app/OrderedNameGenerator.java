package goedegep.media.fotoshow.app;

import java.text.DecimalFormat;

/**
 * This class provides an ordered name generator.
 * <p>
 * This means that a newly generated name comes alphabetically after all earlier generated names.<br>
 * The generated names have the format: imgXXXX<br>
 * Where XXXX is a number, starting at '0000' and each time incremented by 1.
 */
public class OrderedNameGenerator {
  private final static String BASE_NAME = "img";
  private final static DecimalFormat NF = new DecimalFormat("0000");
  private int number = 0;

  public String generateName() {
    String name = BASE_NAME + NF.format(number);
    
    number++;
    
    return name;
  }
}
