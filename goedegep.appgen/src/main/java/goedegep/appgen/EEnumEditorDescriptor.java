package goedegep.appgen;

public class EEnumEditorDescriptor {
  private Object[] values;
  private String   noValueText;
  
  public EEnumEditorDescriptor(Object[] values, String noValueText) {
    this.values = values;
    this.noValueText = noValueText;
  }

  public Object[] getValues() {
    return values;
  }

  public String getNoValueText() {
    return noValueText;
  }

}
