package goedegep.app.finan.gen;

import java.util.Comparator;

public class PeriodicReport<T extends Comparable<T>> implements Comparator<PeriodicReport<T>> {
  private T period = null; // Can be a year(Integer), Quarter or Month

  
  public PeriodicReport() {
  }

  public T getPeriod() {
    return period;
  }

  public void setPeriod(T period) {
    this.period = period;
  }
  
  public int compare(PeriodicReport<T> quarterReport1, PeriodicReport<T> quarterReport2) {
    return quarterReport1.period.compareTo(quarterReport2.period);
  }
}
