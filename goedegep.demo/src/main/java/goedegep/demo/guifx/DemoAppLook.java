package goedegep.demo.guifx;

import java.util.logging.Logger;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.Look;
import javafx.scene.paint.Color;

public class DemoAppLook {
  private final static Logger LOGGER = Logger.getLogger(DemoAppLook.class.getName());
  
  private static Look look = null;
  
  public static Look getInstance() {
    if (look == null) {
      ConfigurationFactory configurationFactory = ConfigurationFactory.eINSTANCE;
      
      // Base the colors on ALICEBLUE
      Color bgColor = Color.ALICEBLUE;
      double bgRed = bgColor.getRed();
      double bgGreen = bgColor.getGreen();
      double bgbBlue = bgColor.getBlue();
      
      // Make the button color a little darker
      Color buttonColor = new Color(0.8 * bgRed, 0.8 * bgGreen, 0.9 * bgbBlue, 1.0);
      
      // Make the panel color a little darker
      Color panelColor = new Color(0.86 * bgRed, 0.86 * bgGreen, 0.92 * bgbBlue, 1.0);
      
      look = configurationFactory.createLook();
      look.setBackgroundColor(Color.ALICEBLUE);
      look.setBoxBackgroundColor(Color.BLUEVIOLET);
      look.setButtonBackgroundColor(buttonColor);
      look.setListBackgroundColor(Color.LIGHTBLUE);
      
      look.setPanelBackgroundColor(panelColor);
//      look.setLabelBackgroundColor(Color.LIGHTSTEELBLUE);
      
      look.setTextFieldBackgroundColor(Color.ALICEBLUE);
      
      Color c = Color.ALICEBLUE;
      LOGGER.severe("Color: " + c.getRed() + ", " + c.getGreen() +  ", " + c.getBlue());
      
//      double red = 1.0;
//      double green = 1.0;
//      double blue = 0.1;
//      double opacity = 1.0d;
//      Color color = new Color(red, green, blue, opacity);
//      look.setTextFieldBackgroundColor(color);
      
    }
    
    return look;
  }
}
