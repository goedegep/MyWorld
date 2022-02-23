package goedegep.app.finan.td;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class CDNumberField extends CD {
  private int     columns;
  private String  toolTipText;
  private Integer minValue;
  private Integer maxValue;
  
  public CDNumberField(String name, int columns, String toolTipText,
      Integer minValue, Integer maxValue) {
    super(name, true);
    this.columns = columns;
    this.toolTipText = toolTipText;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  public int getColumns() {
    return columns;
  }

  public String getToolTipText() {
    return toolTipText;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  @Override
  public DC<JTextField> createComponent(
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCNumberField(this, transactionEntryStatus, customization);
  }
}
