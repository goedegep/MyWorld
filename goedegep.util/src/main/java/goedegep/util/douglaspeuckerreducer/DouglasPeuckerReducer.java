package goedegep.util.douglaspeuckerreducer;

import java.util.ArrayList;
import java.util.List;

import goedegep.geo.WGS84Coordinates;
import javafx.util.Callback;

/**
 * This class provides a method to reduce the number of points in a shape using the Douglas-Peucker algorithm.
 * It is based on
 * https://github.com/spyhunter99/kml-reducer/blob/master/src/main/java/org/miloss/kmlreducer/Main.java
 */
public class DouglasPeuckerReducer {
  
  /**
   * Reduce the number of points in a shape using the Douglas-Peucker algorithm.
   *
   * @param shape     The shape to reduce
   * @param tolerance The tolerance to decide whether or not to keep a point, in
   *                  the coordinate system of the points (micro-degrees here)
   * @param           coordinateExtractor method to obtain WGS84Coordinates from each object in the shape.
   *                  If this is <code>null</code> it is assumed that the items in the shape are of type <code>WGS84Coordinates</code> (i.e. type <code>T</code> is <code>WGS84Coordinates</code>).
   * @return the reduced shape
   */
  public static <T extends Object> List<T> reduceWithTolerance(List<T> shape, double tolerance, Callback<Object, WGS84Coordinates> coordinateExtractor) {
    int n = shape.size();
    // if a shape has 2 or less points it cannot be reduced
    if (tolerance <= 0 || n < 3) {
      return shape;
    }

    boolean[] marked = new boolean[n]; // vertex indexes to keep will be marked as "true"
    for (int i = 1; i < n - 1; i++) {
      marked[i] = false;
    }
    // automatically add the first and last point to the returned shape
    marked[0] = marked[n - 1] = true;

    // the first and last points in the original shape are
    // used as the entry point to the algorithm.
    douglasPeuckerReduction(shape, // original shape
        marked, // reduced shape
        tolerance, // tolerance
        0, // index of first point
        n - 1, // index of last point
        coordinateExtractor
    );

    // all done, return the reduced shape
    ArrayList<T> newShape = new ArrayList<>(n); // the new shape to return
    for (int i = 0; i < n; i++) {
      if (marked[i]) {
        newShape.add(shape.get(i));
      }
    }
    return newShape;
  }

  /**
   * Reduce the number of points in a shape using the Douglas-Peucker algorithm
   *
   * @param shape     The shape to reduce
   * @param tolerance The tolerance to decide whether or not to keep a point, in
   *                  the coordinate system of the points (micro-degrees here)
   * @return the reduced shape
   */
  public static List<WGS84Coordinates> reduceWithTolerance(List<WGS84Coordinates> shape, double tolerance) {
    return reduceWithTolerance(shape, tolerance, null);
  }

  /**
   * Reduce the points in shape between the specified first and last index. Mark
   * the points to keep in marked[]
   *
   * @param shape     The original shape
   * @param marked    The points to keep (marked as true)
   * @param tolerance The tolerance to determine if a point is kept
   * @param firstIdx  The index in original shape's point of the starting point
   *                  for this line segment
   * @param lastIdx   The index in original shape's point of the ending point for
   *                  this line segment
   */
  private static void douglasPeuckerReduction(List<? extends Object> shape, boolean[] marked, double tolerance, int firstIdx,
      int lastIdx, Callback<Object, WGS84Coordinates> coordinateExtractor) {
    if (lastIdx <= firstIdx + 1) {
      // overlapping indexes, just return
      return;
    }

    // loop over the points between the first and last points
    // and find the point that is the farthest away
    double maxDistance = 0.0;
    int indexFarthest = 0;

    WGS84Coordinates firstPoint = coordinateExtractor != null ? coordinateExtractor.call(shape.get(firstIdx)) : (WGS84Coordinates) shape.get(firstIdx);
    WGS84Coordinates lastPoint = coordinateExtractor != null ? coordinateExtractor.call(shape.get(lastIdx)) : (WGS84Coordinates) shape.get(lastIdx);

    for (int idx = firstIdx + 1; idx < lastIdx; idx++) {
      WGS84Coordinates point;
      if (coordinateExtractor == null) {
        point = (WGS84Coordinates) shape.get(idx);
      } else {
        point = coordinateExtractor.call(shape.get(idx));
      }

      double distance = orthogonalDistance(point, firstPoint, lastPoint);

      // keep the point with the greatest distance
      if (distance > maxDistance) {
        maxDistance = distance;
        indexFarthest = idx;
      }
    }

    if (maxDistance > tolerance) {
      // The farthest point is outside the tolerance: it is marked and the algorithm
      // continues.
      marked[indexFarthest] = true;

      // reduce the shape between the starting point to newly found point
      douglasPeuckerReduction(shape, marked, tolerance, firstIdx, indexFarthest, coordinateExtractor);

      // reduce the shape between the newly found point and the finishing point
      douglasPeuckerReduction(shape, marked, tolerance, indexFarthest, lastIdx, coordinateExtractor);
    }
    // else: the farthest point is within the tolerance, the whole segment is
    // discarded.
  }

  /**
   * Calculate the orthogonal distance from the line joining the lineStart and
   * lineEnd points to point
   *
   * @param point     The point the distance is being calculated for
   * @param lineStart The point that starts the line
   * @param lineEnd   The point that ends the line
   * @return The distance in points coordinate system
   */
  public static double orthogonalDistance(WGS84Coordinates point, WGS84Coordinates lineStart, WGS84Coordinates lineEnd) {
    double area = Math.abs((1.0 * lineStart.getLatitude() * 1e6 * lineEnd.getLongitude() * 1e6
        + 1.0 * lineEnd.getLatitude() * 1e6 * point.getLongitude() * 1e6
        + 1.0 * point.getLatitude() * 1e6 * lineStart.getLongitude() * 1e6
        - 1.0 * lineEnd.getLatitude() * 1e6 * lineStart.getLongitude() * 1e6
        - 1.0 * point.getLatitude() * 1e6 * lineEnd.getLongitude() * 1e6
        - 1.0 * lineStart.getLatitude() * 1e6 * point.getLongitude() * 1e6) / 2.0);

    double bottom = Math.hypot(lineStart.getLatitude() * 1e6 - lineEnd.getLatitude() * 1e6,
        lineStart.getLongitude() * 1e6 - lineEnd.getLongitude() * 1e6);

    return area / bottom * 2.0;
  }

}
