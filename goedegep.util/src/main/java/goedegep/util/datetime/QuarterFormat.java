package goedegep.util.datetime;


public class QuarterFormat {
  public String format(Quarter quarter) {
    StringBuilder stringBuilder = new StringBuilder();
    
    stringBuilder.append(quarter.getQuarter());
    stringBuilder.append("-");
    stringBuilder.append(quarter.getYear());
    
    return stringBuilder.toString();
  }
}
