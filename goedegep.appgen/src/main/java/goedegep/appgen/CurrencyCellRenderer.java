package goedegep.appgen;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.money.PgCurrencyPlusStatus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

/**
 * A cell renderer for values of type PgCurrency and PgCurrencyPlusStatus.
 * Note: For these types the TextBasedCellRenderer is not used, because this renderer uses specific colors depending on the status
 * of the value.
 */
@SuppressWarnings("serial")
public class CurrencyCellRenderer extends TextBasedCellRenderer {
  private PgCurrencyFormat cf;

  private String toolTipText;
//  private Color  normalBackGround = null;
  private Color[]  backGroundColors = new Color[4];
  private Color[]  backGroundColorsError = new Color[4];
  private Color[]  backGroundColorsSuspicious = new Color[4];
  
  public CurrencyCellRenderer() {
    this((String) null);
  }
  
  public CurrencyCellRenderer(String toolTipText) {
    this(new PgCurrencyFormat(), toolTipText);
  }
  
  public CurrencyCellRenderer(PgCurrencyFormat cf) {
    this(cf, null);
  }
  
  public CurrencyCellRenderer(PgCurrencyFormat cf, String toolTipText) {
    super(null);
    this.cf =  cf;
    this.toolTipText = toolTipText;
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
    PgCurrency  money = null;
    PgCurrencyPlusStatus.Status status = PgCurrencyPlusStatus.Status.ERROR;
    
    if (value == null) {
      status = PgCurrencyPlusStatus.Status.OK;
    }

    if (value instanceof PgCurrency) {
      money = (PgCurrency) value;
      status = PgCurrencyPlusStatus.Status.OK;
    }

    if (value instanceof PgCurrencyPlusStatus) {
      money = ((PgCurrencyPlusStatus) value).getMoney();
      status = ((PgCurrencyPlusStatus) value).getStatus();
    }

    super.getTableCellRendererComponent(table, money, isSelected, hasFocus, row, column);
    int colorIndex = (isSelected ? 1 : 0) + (hasFocus ? 2 : 0);
    if (backGroundColors[colorIndex] == null) {
      Color color = getBackground();
      backGroundColors[colorIndex] = color;
      backGroundColorsSuspicious[colorIndex] = new Color(Math.min((int) (1.15 * color.getRed()), 255),
                                                         (int) (0.85 * color.getGreen()),
                                                         (int) (0.85 * color.getBlue()));
      backGroundColorsError[colorIndex] = new Color(Math.min((int) (1.3 * color.getRed()), 255),
                                                         (int) (0.7 * color.getGreen()),
                                                         (int) (0.7 * color.getBlue()));
    }

    setText(getText(money));
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
    }

    switch(status) {
    case OK:
      setBackground(backGroundColors[colorIndex]);
      break;
      
    case SUSPICIOUS:
      setBackground(backGroundColorsSuspicious[colorIndex]);
      break;     
      
    case ERROR:
    default:
      setBackground(backGroundColorsError[colorIndex]);
      break;
    }

    return this;
  }

  public String getText(Object value) {
    return cf.format((PgCurrency) value);
  }
}