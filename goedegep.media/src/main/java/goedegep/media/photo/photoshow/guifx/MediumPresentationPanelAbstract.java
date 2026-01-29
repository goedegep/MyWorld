package goedegep.media.photo.photoshow.guifx;

import goedegep.util.datetime.FlexDateFormat;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class MediumPresentationPanelAbstract implements MediumPresentationPanel {
  private final static double LABEL_FONT_SIZE = 180.0;
  private final static Color LABEL_TEXT_COLOR = Color.color(0.92, 0.92, 0.92);
  protected final static FlexDateFormat FDF = new FlexDateFormat();
  
  private Font font = Font.font("Freestyle Script", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, LABEL_FONT_SIZE);
  
  
  /**
   * Create a {@code Text} for a specific text and {@code Font).
   * 
   * @param labelText The text.
   * @param font the {@code Font}
   * @return a nicely formatted {@code Text} for the provided {@code labelText} and {@code font).
   */
  protected Text createLabelText(String labelText) {
    Text text = new Text(labelText);
    text.setFont(font);

    text.setFill(LABEL_TEXT_COLOR);
    
    // Add a black outline by stroking the text. StrokeWidth controls outline thickness.
    text.setStroke(Color.BLACK);
    text.setStrokeWidth(3.0);
    
    DropShadow ds = new DropShadow();
    ds.setOffsetY(9.0f);
    ds.setOffsetX(7.0f);
    ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
    text.setEffect(ds);
    
    return text;
  }

}
