package goedegep.util.datetime;

public class ClockTime {
  long timeInSeconds;
  
  public ClockTime(int hours, int minutes, int seconds) {
    timeInSeconds = 3600 * hours + 60 * minutes + seconds;
  }
  
  public ClockTime(long timeInSeconds) {
    this.timeInSeconds = timeInSeconds;
  }

  public int getHours() {
    return (int) timeInSeconds / 3600;
  }
  
  public int getMinutes() {
    return (int) (timeInSeconds % 3600) / 60;
  }
  
  public int getSeconds() {
    return (int) timeInSeconds % 60;
  }
  
  public long getTimeInSeconds() {
    return timeInSeconds;
  }
}
