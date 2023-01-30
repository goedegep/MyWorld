package goedegep.jfx;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

public class JfxUtilTest {

  @Test
  public void testColorToCssString() {
    final int redHex = 0x02;
    final int greenHex = 0x03;
    final int blueHex = 0xfe;
    
    final Color color = new Color(redHex / 255.0, greenHex / 255.0, blueHex / 255.0, 1.0);
    String cssColorString = JfxUtil.colorToCssString(color);
    
    assertEquals("#0203fe", cssColorString);
  }
}
