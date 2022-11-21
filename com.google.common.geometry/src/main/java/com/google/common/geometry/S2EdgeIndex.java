/*
 * Copyright 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.geometry;

import static com.google.common.geometry.S2Projections.PROJ;
import static java.lang.Math.min;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class structures a set S of data edges, so that one can quickly find which edges of S may
 * potentially intersect or touch a query edge.
 *
 * <p>The set S is assumed to be indexable by a consecutive sequence of integers in the range
 * [0..getNumEdges()-1]. You subclass this class by defining the three functions getNumEdges(),
 * edgeFrom(), edgeTo(). Then you use it as follows for a query edge (a,b):
 *
 * <p>{@code
 *   MyS2EdgeIndex edgeIndex;
 *   MyS2EdgeIndex.DataEdgeIterator it = new MyS2EdgeIndex.DataEdgeIterator(edgeIndex);
 *   S2Point from;
 *   S2Point to;
 *   for (it.getCandidates(a, b); it.hasnext(); it.next()) {
 *     ...
 *     robustCrossing(a, b, edgeIndex.edgeFrom(it.index()), edgeIndex.edgeTo(it.index()));
 *     ...
 *   }
 * }
 *
 * <p>What is this getEdge()? Since a subclass might have a more efficient way of getting an edge
 * than calling edgeFrom() and edgeTo(), you may want to implement a getEdge() to do that as
 * efficiently as possible.
 *
 * <p>The function getCandidates initializes the iterator to return a set of candidate edges from S,
 * such that we are sure that any data edge that touches or crosses (a,b) is a candidate.
 *
 * <p>This class returns all edges until it finds that it is worth it to compute a quad tree on the
 * data set. Chance may have it that you compute the quad tree exactly when it's too late and all
 * the work is done. If this happens, we only double the total running time.
 *
 * <p>You can help the class by declaring in advance that you will make a certain number of calls to
 * getCandidates():
 *
 * <p>{@code
 *   MyS2EdgeIndex.DataEdgeIterator it = new MyS2EdgeIndex.DataEdgeIterator(edgeIndex);
 *   edgeIndex.predictAdditionalCalls(n);
 *   for (int i = 0; i < n; ++i) {
 *     for (it.getCandidates(v.get(i), v.get(i+1)); it.hasNext(); it.next()) {
 *       ...
 *       robustCrossing(
 *           v.get(i),
 *           v.get(i + 1),
 *           edgeIndex.edgeFrom(it.index()),
 *           edgeIndex.edgeTo(it.index()));
 *       ...
 *     }
 *   }
 * }
 *
 * <p>Here, we say that we will call getCandidates() n times. If we have 1000 data edges and n=1000,
 * then we will compute the quad tree immediately instead of waiting till we've wasted enough time
 * to justify the cost.
 *
 * <p>The tradeoff between brute force and quad tree depends on many things, we use a very
 * approximate trade-off.
 *
 * <p>See examples in {@link S2Loop} and {@link S2Polygon}, in particular, look at the optimization
 * that allows one to use the EdgeCrosser.
 *
 * <p>----------------------------------------------------------------------------
 *
 * <p>Implementation notes:
 *
 * <p>An object of this class contains a set S of edges called the test edges. For a query edge q,
 * you want to compute a superset of all test edges that intersect q.
 *
 * <p>Each edge is covered by one or several S2 cells, stored in a list of sorted [cell, edge]
 * entries. To perform a query, you cover the query edge with a set of cells. For each such cell c,
 * you find all test edges that are in c, in an ancestor of c or in a child of c.
 *
 * <p>This is simple, but there are two complications:
 *
 * <ul>
 *   <li>For containment queries, the query edge is very long (from S2.origin() to the query point).
 *       A standard cell covering of q is either useless or too large. The covering needs to be
 *       adapted to S: if a cell contains too many edges from S, you subdivide it and keep only the
 *       subcells that intersect q. See comments for findCandidateCrossings().
 *   <li>To decide if edge q could possibly cross edge e, we end up comparing both with edges that
 *       bound s2 cells. Numerical inaccuracies can lead to inconsistencies, e.g.: there may be an
 *       edge b at the boundary of two cells such that q and e are on opposite sides of b, yet they
 *       cross each other. This special case happens a lot if your test and query edges are cell
 *       boundaries themselves, and this in turn is a common case when regions are approximated by
 *       cell unions.
 * </ul>
 *
 * <p>We expand here on the solution to the second problem. Two components:
 *
 * <ul>
 *   <li>Each test edge is thickened to a rectangle before it is S2-covered. See the comment for
 *       getThickenedEdgeCovering().
 *   <li>When recursing through the children of a cell c for a query edge q, we test q against the
 *       boundaries of c's children in a 'lenient' way. That is, instead of testing e.g.
 *       {@code area(abc)*area(abd) < 0}, we check if it is 'approximately negative'.
 * </ul>
 *
 * <p>To see how the second point is necessary, imagine that your query edge q is the North boundary
 * of cell x. We recurse into the four children a,b,c,d of x. To do so, we check if q crosses or
 * touches any of a,b,c or d boundaries. As all the situations are degenerate, it is possible that
 * all crossing tests return false, thus making q suddenly 'disappear'. Using the lenient crossing
 * test, we are guaranteed that q will intersect one of the four edges of the cross that bounds
 * a,b,c,d. The same holds true if q passes through the cell center of x.
 *
 * @author andriy@google.com (Andriy Bihun) ported from util/geometry
 * @author pilloff@google.com (Mark Pilloff) original author
 * @author julienbasch@google.com (Julien Basch) original author
 */
public abstract strictfp class S2EdgeIndex {
  /**
   * Thicken the edge in all directions by roughly 1% of the edge length when thickenEdge is true.
   */
  private static final double THICKENING = 0.01;

  /** The cell containing each edge, as given in the parallel array <code>edges</code>. */
  private long[] cells;

  /** The edge contained by each cell, as given in the parallel array <code>cells</code>. */
  private int[] edges;

  /**
   * No cell strictly below this level appears in mapping. Initially leaf level, that's the minimum
   * level at which we will ever look for test edges.
   */
  private int minimumS2LevelUsed;

  /** Has the index been computed already? */
  private boolean indexComputed;

  /** Number of queries so far */
  private int queryCount;

  /** Empties the index in case it already contained something. */
  public void reset() {
    minimumS2LevelUsed = S2CellId.MAX_LEVEL;
    indexComputed = false;
    queryCount = 0;
    cells = null;
    edges = null;
  }

  /**
   * Compares [cell1, edge1] to [cell2, edge2], by cell first and edge second.
   *
   * @return -1 if [cell1, edge1] is less than [cell2, edge2], 1 if [cell1, edge1] is greater than
   *     [cell2, edge2], 0 otherwise.
   */
  private static final int compare(long cell1, int edge1, long cell2, int edge2) {
    if (cell1 < cell2) {
      return -1;
    } else if (cell1 > cell2) {
      return 1;
    } else if (edge1 < edge2) {
      return -1;
    } else if (edge1 > edge2) {
      return 1;
    } else {
      return 0;
    }
  }

  /** Computes the index (if it has not been previously done). */
  public final void computeIndex() {
    if (indexComputed) {
      return;
    }
    List<Long> cellList = Lists.newArrayList();
    List<Integer> edgeList = Lists.newArrayList();
    for (int i = 0; i < getNumEdges(); ++i) {
      S2Point from = edgeFrom(i);
      S2Point to = edgeTo(i);
      ArrayList<S2CellId> cover = Lists.newArrayList();
      int level = getCovering(from, to, true, cover);
      minimumS2LevelUsed = min(minimumS2LevelUsed, level);
      for (S2CellId cellId : cover) {
        cellList.add(cellId.id());
        edgeList.add(i);
      }
    }
    cells = new long[cellList.size()];
    edges = new int[edgeList.size()];
    for (int i = 0; i < cells.length; i++) {
      cells[i] = cellList.get(i);
      edges[i] = edgeList.get(i);
    }
    sortIndex();
    indexComputed = true;
  }

  /** Sorts the parallel <code>cells</code> and <code>edges</code> arrays. */
  private void sortIndex() {
    // Create an array of indices and sort it based on the values in the parallel arrays at each
    // index.
    Integer[] indices = new Integer[cells.length];
    for (int i = 0; i < indices.length; i++) {
      indices[i] = i;
    }
    Arrays.sort(
        indices,
        new Comparator<Integer>() {
          @Override
          public int compare(Integer index1, Integer index2) {
            return S2EdgeIndex.compare(cells[index1], edges[index1], cells[index2], edges[index2]);
          }
        });
    // Copy the cells and edges in the order given by the sorted list of indices
    long[] newCells = new long[cells.length];
    int[] newEdges = new int[edges.length];
    for (int i = 0; i < indices.length; i++) {
      newCells[i] = cells[indices[i]];
      newEdges[i] = edges[indices[i]];
    }
    // Replace the cells and edges with the sorted arrays
    cells = newCells;
    edges = newEdges;
  }

  public final boolean isIndexComputed() {
    return indexComputed;
  }

  /**
   * Tell the index that we just received a new request for candidates. Useful to compute when to
   * switch to quad tree.
   */
  protected final void incrementQueryCount() {
    ++queryCount;
  }

  /**
   * If the index hasn't been computed yet, looks at how much work has gone into iterating using the
   * brute force method, and how much more work is planned as defined by 'cost'. If it were to have
   * been cheaper to use a quad tree from the beginning, then compute it now. This guarantees that
   * we will never use more than twice the time we would have used had we known in advance exactly
   * how many edges we would have wanted to test. It is the theoretical best.
   *
   * <p>The value 'n' is the number of iterators we expect to request from this edge index.
   *
   * <p>If we have m data edges and n query edges, then the brute force cost is m * n * testCost
   * where testCost is taken to be the cost of EdgeCrosser.robustCrossing, measured to be about 30ns
   * at the time of this writing.
   *
   * <p>If we compute the index, the cost becomes: m * costInsert + n * costFind(m)
   *
   * <ul>
   *   <li>costInsert can be expected to be reasonably stable, and was measured at 1200ns with the
   *       BM_QuadEdgeInsertionCost benchmark.
   *   <li>costFind depends on the length of the edge . For m=1000 edges, we got timings ranging
   *       from 1ms (edge the length of the polygon) to 40ms. The latter is for very long query
   *       edges, and needs to be optimized. We will assume for the rest of the discussion that
   *       costFind is roughly 3ms.
   * </ul>
   *
   * <p>When doing one additional query, the differential cost is m * testCost - costFind(m) With
   * the numbers above, it is better to use the quad tree (if we have it) if {@code m >= 100}.
   *
   * <p>If m = 100, 30 queries will give m*n*testCost = m * costInsert = 100ms, while the marginal
   * cost to find is 3ms. Thus, this is a reasonable thing to do.
   */
  public final void predictAdditionalCalls(int n) {
    if (indexComputed) {
      return;
    }
    if (getNumEdges() > 100 && (queryCount + n) > 30) {
      computeIndex();
    }
  }

  /** Returns the number of edges in this index. */
  public abstract int getNumEdges();

  /** Returns the starting vertex of the edge at offset {@code index}. */
  public abstract S2Point edgeFrom(int index);

  /** Returns the ending vertex of the edge at offset {@code index}. */
  public abstract S2Point edgeTo(int index);

  /**
   * Return both vertices of the given {@code index} in one call. Can be overridden by some
   * subclasses to more efficiently retrieve both edge points at once, which makes a difference in
   * performance, especially for small loops.
   */
  public S2Edge edgeFromTo(int index) {
    return new S2Edge(edgeFrom(index), edgeTo(index));
  }

  /**
   * Appends to "candidateCrossings" all edge references which may cross the given edge. This is
   * done by covering the edge and then finding all references of edges whose coverings overlap this
   * covering. Parent cells are checked level by level. Child cells are checked all at once by
   * taking advantage of the natural ordering of S2CellIds.
   */
  protected void findCandidateCrossings(S2Point a, S2Point b, List<Integer> candidateCrossings) {
    Preconditions.checkState(indexComputed);
    ArrayList<S2CellId> cover = Lists.newArrayList();
    getCovering(a, b, false, cover);

    // Edge references are inserted into the map once for each covering cell, so absorb duplicates
    // here
    Set<Integer> uniqueSet = new HashSet<>();
    getEdgesInParentCells(cover, uniqueSet);

    // TODO(user): An important optimization for long query edges (Contains queries): keep a
    // bounding cap and clip the query edge to the cap before starting the descent.
    getEdgesInChildrenCells(a, b, cover, uniqueSet);

    candidateCrossings.clear();
    candidateCrossings.addAll(uniqueSet);
  }

  /**
   * Returns the smallest cell containing all four points, or {@link S2CellId#sentinel()} if they
   * are not all on the same face. The points don't need to be normalized.
   */
  private static S2CellId containingCell(S2Point pa, S2Point pb, S2Point pc, S2Point pd) {
    S2CellId a = S2CellId.fromPoint(pa);
    S2CellId b = S2CellId.fromPoint(pb);
    S2CellId c = S2CellId.fromPoint(pc);
    S2CellId d = S2CellId.fromPoint(pd);

    if (a.face() != b.face() || a.face() != c.face() || a.face() != d.face()) {
      return S2CellId.sentinel();
    }

    while (!a.equals(b) || !a.equals(c) || !a.equals(d)) {
      a = a.parent();
      b = b.parent();
      c = c.parent();
      d = d.parent();
    }
    return a;
  }

  /**
   * Returns the smallest cell containing both points, or Sentinel if they are not all on the same
   * face. The points don't need to be normalized.
   */
  private static S2CellId containingCell(S2Point pa, S2Point pb) {
    S2CellId a = S2CellId.fromPoint(pa);
    S2CellId b = S2CellId.fromPoint(pb);

    if (a.face() != b.face()) {
      return S2CellId.sentinel();
    }

    while (!a.equals(b)) {
      a = a.parent();
      b = b.parent();
    }
    return a;
  }

  /**
   * Computes a cell covering of an edge and fills it into the given edgeCovering list, which is
   * cleared first. Returns the level of the s2 cells used in the covering (only one level is ever
   * used for each call).
   *
   * <p>If thickenEdge is true, the edge is thickened and extended by 1% of its length.
   *
   * <p>It is guaranteed that no child of a covering cell will fully contain the covered edge.
   */
  @CanIgnoreReturnValue
  private int getCovering(
      S2Point a, S2Point b, boolean thickenEdge, ArrayList<S2CellId> edgeCovering) {
    edgeCovering.clear();

    // Selects the ideal s2 level at which to cover the edge, this will be the level whose S2 cells
    // have a width roughly commensurate to the length of the edge. We multiply the edge length by
    // 2*THICKENING to guarantee the thickening is honored (it's not a big deal if we honor it when
    // we don't request it) when doing the covering-by-cap trick.
    double edgeLength = a.angle(b);
    int idealLevel = PROJ.minWidth.getMaxLevel(edgeLength * (1 + 2 * THICKENING));

    S2CellId containingCellId;
    if (!thickenEdge) {
      containingCellId = containingCell(a, b);
    } else {
      if (idealLevel == S2CellId.MAX_LEVEL) {
        // If the edge is tiny, instabilities are more likely, so we want to limit the number of
        // operations. We pretend we are in a cell much larger so as to trigger the 'needs covering'
        // case, so we won't try to thicken the edge.
        containingCellId = (new S2CellId(0xFFF0)).parent(3);
      } else {
        S2Point pq = S2Point.mul(S2Point.minus(b, a), THICKENING);
        S2Point ortho =
            S2Point.mul(S2Point.normalize(S2Point.crossProd(pq, a)), edgeLength * THICKENING);
        S2Point p = S2Point.minus(a, pq);
        S2Point q = S2Point.add(b, pq);
        // If p and q were antipodal, the edge wouldn't be lengthened, and it could even flip! This
        // is not a problem because idealLevel != 0 here. The farther p and q can be is roughly a
        // quarter Earth away from each other, so we remain Theta(THICKENING).
        containingCellId =
            containingCell(
                S2Point.minus(p, ortho),
                S2Point.add(p, ortho),
                S2Point.minus(q, ortho),
                S2Point.add(q, ortho));
      }
    }

    // Best case: edge is fully contained in a cell that's not too big.
    if (!containingCellId.equals(S2CellId.sentinel())
        && containingCellId.level() >= idealLevel - 2) {
      edgeCovering.add(containingCellId);
      return containingCellId.level();
    }

    if (idealLevel == 0) {
      // Edge is very long, maybe even longer than a face width, so the trick below doesn't work.
      // For now, we will add the whole S2 sphere. TODO(user): Do something a tad smarter
      // (and beware of the antipodal case).
      for (S2CellId cellid = S2CellId.begin(0);
          !cellid.equals(S2CellId.end(0));
          cellid = cellid.next()) {
        edgeCovering.add(cellid);
      }
      return 0;
    }
    // TODO(user): Check trick below works even when vertex is at interface between three
    // faces.

    // Use trick as in S2PolygonBuilder.PointIndex.findNearbyPoint: Cover the edge by a cap centered
    // at the edge midpoint, then cover the cap by four big-enough cells around the cell vertex
    // closest to the cap center.
    S2Point middle = S2Point.normalize(S2Point.div(S2Point.add(a, b), 2));
    int actualLevel = min(idealLevel, S2CellId.MAX_LEVEL - 1);
    S2CellId.fromPoint(middle).getVertexNeighbors(actualLevel, edgeCovering);
    return actualLevel;
  }

  /**
   * Filters a list of entries down to the inclusive range defined by the given cells, in <code>
   * O(log N)</code> time.
   *
   * @param cell1 One side of the inclusive query range.
   * @param cell2 The other side of the inclusive query range.
   * @return An array of length 2, containing the start/end indices.
   */
  private int[] getEdges(long cell1, long cell2) {
    // ensure cell1 <= cell2
    if (cell1 > cell2) {
      long temp = cell1;
      cell1 = cell2;
      cell2 = temp;
    }
    // The binary search returns -N-1 to indicate an insertion point at index N, if an exact match
    // cannot be found. Since the edge indices queried for are not valid edge indices, we will
    // always get -N-1, so we immediately convert to N.
    return new int[] {
      -1 - binarySearch(cell1, Integer.MIN_VALUE), -1 - binarySearch(cell2, Integer.MAX_VALUE)
    };
  }

  private int binarySearch(long cell, int edge) {
    int low = 0;
    int high = cells.length - 1;
    while (low <= high) {
      int mid = (low + high) >>> 1;
      int cmp = compare(cells[mid], edges[mid], cell, edge);
      if (cmp < 0) {
        low = mid + 1;
      } else if (cmp > 0) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -(low + 1);
  }

  /**
   * Adds to candidateCrossings all the edges present in any ancestor of any cell of cover, down to
   * minimumS2LevelUsed. The cell->edge map is in the variable mapping.
   */
  private void getEdgesInParentCells(List<S2CellId> cover, Set<Integer> candidateCrossings) {
    // Find all parent cells of covering cells.
    Set<S2CellId> parentCells = Sets.newHashSet();
    for (S2CellId coverCell : cover) {
      for (int parentLevel = coverCell.level() - 1;
          parentLevel >= minimumS2LevelUsed;
          --parentLevel) {
        if (!parentCells.add(coverCell.parent(parentLevel))) {
          break; // cell is already in => parents are too.
        }
      }
    }

    // Put parent cell edge references into result.
    for (S2CellId parentCell : parentCells) {
      int[] bounds = getEdges(parentCell.id(), parentCell.id());
      for (int i = bounds[0]; i < bounds[1]; i++) {
        candidateCrossings.add(edges[i]);
      }
    }
  }

  /** Returns true if the edge and the cell (including boundary) intersect. */
  private static boolean edgeIntersectsCellBoundary(S2Point a, S2Point b, S2Cell cell) {
    S2Point[] vertices = new S2Point[4];
    for (int i = 0; i < 4; ++i) {
      vertices[i] = cell.getVertex(i);
    }
    for (int i = 0; i < 4; ++i) {
      S2Point fromPoint = vertices[i];
      S2Point toPoint = vertices[(i + 1) % 4];
      if (S2EdgeUtil.lenientCrossing(a, b, fromPoint, toPoint)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Appends to candidateCrossings the edges that are fully contained in an S2 covering of edge. The
   * covering of edge used is initially cover, but is refined to eliminate quickly subcells that
   * contain many edges but do not intersect with edge.
   */
  private void getEdgesInChildrenCells(
      S2Point a, S2Point b, List<S2CellId> cover, Set<Integer> candidateCrossings) {
    // Put all edge references of (covering cells + descendant cells) into result. This relies on
    // the natural ordering of S2CellIds.
    S2Cell[] children = null;
    while (!cover.isEmpty()) {
      S2CellId cell = cover.remove(cover.size() - 1);
      int[] bounds = getEdges(cell.rangeMin().id(), cell.rangeMax().id());
      if (bounds[1] - bounds[0] <= 16) {
        for (int i = bounds[0]; i < bounds[1]; i++) {
          candidateCrossings.add(edges[i]);
        }
      } else {
        // Add cells at this level
        bounds = getEdges(cell.id(), cell.id());
        for (int i = bounds[0]; i < bounds[1]; i++) {
          candidateCrossings.add(edges[i]);
        }
        // Recurse on the children -- hopefully some will be empty.
        if (children == null) {
          children = new S2Cell[4];
          for (int i = 0; i < 4; ++i) {
            children[i] = new S2Cell();
          }
        }
        new S2Cell(cell).subdivide(children);
        for (S2Cell child : children) {
          // TODO(user): Do the check for the four cells at once, as it is enough to check
          // the four edges between the cells. At this time, we are checking 16 edges, 4 times too
          // many.
          //
          // Note that given the guarantee of AppendCovering, it is enough to check that the edge
          // intersect with the cell boundary as it cannot be fully contained in a cell.
          if (edgeIntersectsCellBoundary(a, b, child)) {
            cover.add(child.id());
          }
        }
      }
    }
  }

  /**
   * Adds points where the edge index intersects the edge {@code [a0, a1]} to {@code intersections}.
   * Each intersection is paired with a {@code t}-value indicating the fractional geodesic rotation
   * of the intersection from 0 (at {@code a0}) to 1 (at {@code a1}).
   *
   * @param a0 First vertex of the edge to clip.
   * @param a1 Second vertex of the edge to clip.
   * @param addSharedEdges Whether an exact duplicate of {@code [a0, a1]} in the index should count
   *     as an intersection or not.
   * @param intersections The resulting list of intersections.
   */
  public void clipEdge(
      final S2Point a0,
      final S2Point a1,
      boolean addSharedEdges,
      Collection<ParametrizedS2Point> intersections) {
    S2EdgeIndex.DataEdgeIterator it = new S2EdgeIndex.DataEdgeIterator(this);
    S2EdgeUtil.EdgeCrosser crosser = new S2EdgeUtil.EdgeCrosser(a0, a1, a0);
    S2Point b0 = null;
    S2Point b1 = null;
    for (it.getCandidates(a0, a1); it.hasNext(); it.next()) {
      S2Point previous = b1;
      S2Edge bEdge = edgeFromTo(it.index());
      b0 = bEdge.getStart();
      b1 = bEdge.getEnd();
      if (previous == null || !previous.equals(b0)) {
        crosser.restartAt(b0);
      }
      int crossing = crosser.robustCrossing(b1);
      if (crossing < 0) {
        continue;
      }
      if (crossing > 0) {
        // There is a proper edge crossing.
        S2Point x = S2EdgeUtil.getIntersection(a0, a1, b0, b1);
        double t = S2EdgeUtil.getDistanceFraction(x, a0, a1);
        intersections.add(new ParametrizedS2Point(t, x));
      } else if (S2EdgeUtil.vertexCrossing(a0, a1, b0, b1)) {
        // There is a crossing at one of the vertices. The basic rule is simple: if a0 equals one of
        // the "b" vertices, the crossing occurs at t=0; otherwise, it occurs at t=1.
        //
        // This has the effect that when two symmetric edges are encountered (an edge and its
        // reverse), neither one is included in the output. When two duplicate edges are
        // encountered, both are included in the output. The "addSharedEdges" flag allows one of
        // these two copies to be removed by changing its intersection parameter from 0 to 1.
        double t = (a0.equals(b0) || a0.equals(b1)) ? 0 : 1;
        if (!addSharedEdges && a1.equals(b1)) {
          t = 1;
        }
        intersections.add(new ParametrizedS2Point(t, t == 0 ? a0 : a1));
      }
    }
  }

  /**
   * An iterator on data edges that may cross a query edge (a,b). Create the iterator, call
   * getCandidates(), then hasNext()/next() repeatedly.
   *
   * <p>The current edge in the iteration has index index(), goes between from() and to().
   */
  public static class DataEdgeIterator {
    /** The structure containing the data edges. */
    private final S2EdgeIndex edgeIndex;

    /**
     * Tells whether getCandidates() obtained the candidates through brute force iteration or using
     * the quad tree structure.
     */
    private boolean isBruteForce;

    /** Index of the current edge and of the edge before the last next() call. */
    private int currentIndex;

    /** Cache of edgeIndex.getNumEdges() so that hasNext() doesn't make an extra call */
    private int numEdges;

    /**
     * All the candidates obtained by getCandidates() when we are using a quad-tree (i.e.
     * isBruteForce = false).
     */
    ArrayList<Integer> candidates;

    /**
     * Index within array above. We have: currentIndex = candidates.get(currentIndexInCandidates).
     */
    private int currentIndexInCandidates;

    public DataEdgeIterator(S2EdgeIndex edgeIndex) {
      this.edgeIndex = edgeIndex;
      candidates = Lists.newArrayList();
    }

    /**
     * Initializes the iterator to iterate over a set of candidates that may cross the edge (a,b).
     */
    // TODO(user): Get a better API without the clumsy getCandidates(). Maybe
    // edgeIndex.GetIterator()?
    public void getCandidates(S2Point a, S2Point b) {
      edgeIndex.predictAdditionalCalls(1);
      isBruteForce = !edgeIndex.isIndexComputed();
      if (isBruteForce) {
        edgeIndex.incrementQueryCount();
        currentIndex = 0;
        numEdges = edgeIndex.getNumEdges();
      } else {
        candidates.clear();
        edgeIndex.findCandidateCrossings(a, b, candidates);
        currentIndexInCandidates = 0;
        if (!candidates.isEmpty()) {
          currentIndex = candidates.get(0);
        }
      }
    }

    /** Index of the current edge in the iteration. */
    public int index() {
      Preconditions.checkState(hasNext());
      return currentIndex;
    }

    /** False if there are no more candidates; true otherwise. */
    public boolean hasNext() {
      if (isBruteForce) {
        return (currentIndex < numEdges);
      } else {
        return currentIndexInCandidates < candidates.size();
      }
    }

    /** Iterate to the next available candidate. */
    public void next() {
      Preconditions.checkState(hasNext());
      if (isBruteForce) {
        ++currentIndex;
      } else {
        ++currentIndexInCandidates;
        if (currentIndexInCandidates < candidates.size()) {
          currentIndex = candidates.get(currentIndexInCandidates);
        }
      }
    }
  }
}
