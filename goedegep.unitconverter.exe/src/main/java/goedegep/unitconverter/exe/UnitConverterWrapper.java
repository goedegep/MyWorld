package goedegep.unitconverter.exe;

/*
 * This class is the entyry point for the Unit Converter executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class UnitConverterWrapper {
  public static void main(String[] args) {
    
    UnitConverterApplication.main(args);
  }
}
