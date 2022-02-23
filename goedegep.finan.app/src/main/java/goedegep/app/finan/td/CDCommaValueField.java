package goedegep.app.finan.td;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class CDCommaValueField extends CD {
  private int     columns;
  private String  toolTipText;
  private Integer factor;
  private Long    minValue;
  private Long    maxValue;
  
  public CDCommaValueField(String name, int columns, String toolTipText,
      Integer factor, Long minValue, Long maxValue, boolean optional) {
    super(name, true, optional);
    this.columns = columns;
    this.toolTipText = toolTipText;
    this.factor = factor;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  public CDCommaValueField(String name, int columns, String toolTipText,
      Integer factor, Long minValue, Long maxValue) {
    this(name, columns, toolTipText, factor, minValue, maxValue, false);
  }

  public int getColumns() {
    return columns;
  }

  public String getToolTipText() {
    return toolTipText;
  }

  public Integer getFactor() {
    return factor;
  }

  public Long getMinValue() {
    return minValue;
  }

  public Long getMaxValue() {
    return maxValue;
  }

  @Override
  public DC<JTextField> createComponent(
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCCommaValueField(this, transactionEntryStatus, customization);
  }
}