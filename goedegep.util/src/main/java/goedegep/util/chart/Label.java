package goedegep.util.chart;

public class Label {
  private static final int NTICK = 5;         // desired number of tick marks
  
  public static void calculateLabels(double yMin, double yMax) {
//    char str[6], temp[20];
    /* we expect ymin!=ymax */
    double range = nicenum(yMax-yMin, false);
    System.out.println("range = " + range);
    double d = nicenum(range /(NTICK-1), true);        /* tick mark spacing */
    System.out.println("d = " + d);
    double graphymin = Math.floor(yMin / d) * d;
    double graphymax = Math.ceil(yMax / d) * d;
    int exp = (int) Math.floor(Math.log10(d));
    System.out.println("exp = " + exp);

//    sprintf(str, "%%.%df", exp<0 ? -exp : 0);   /* simplest axis labels */

    for (double y = graphymin; y < graphymax + .5 * d; y += d) {
      System.out.println("Label = " + y);
//      sprintf(temp, str, y);
//      printf("(%s)\n", temp);
    }
  }
  
  /**
   * Find a "nice" number approximately equal to x
   * round if round=1, ceil if round=0
   */

  public static double nicenum(double x, boolean round) {

    int exp = (int) Math.floor(Math.log10(x));
    System.out.println("exp = " + exp);
    double f = x / Math.pow(10., exp);   /* fraction between 1 and 10 */
    System.out.println("f = " + f);
    double y;
    if (round) {
      if (f < 1.5) {
        y = 1.;
      } else if (f <3. ) {
        y = 2.;
      } else if (f < 7.) {
        y = 5.;
      } else {
        y = 10.;
      }
    } else { // ceil
      if (f <= 1.) {
        y = 1.;
      } else if (f <= 2.) {
        y = 2.;
      } else if (f<=5.) {
        y = 5.;
      } else {
        y = 10.;
      }
    }
    
    return y * Math.pow(10., exp);
  }
  
  public static void main(String args[]) {
    if (args.length != 2) {
      System.err.println("Usage: label <ymin> <ymax>\n");
      return;
    }
    
    double yMin = Double.parseDouble(args[0]);
    double yMax = Double.parseDouble(args[1]);
    System.out.println("Arguments: " + yMin + ", " + yMax);
    calculateLabels(yMin, yMax);
  }
}
