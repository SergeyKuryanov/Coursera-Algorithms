import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments = null;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] pointsCopy = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            if (p == null) {
                throw new IllegalArgumentException();
            }

            pointsCopy[i] = p;
        }
        
        Arrays.sort(pointsCopy);
        List<LineSegment> lineSegments = new LinkedList<LineSegment>();

        Point previousPoint = null;

        for (int i = 0; i < pointsCopy.length; i++) {
            if (previousPoint != null && pointsCopy[i].compareTo(previousPoint) == 0) {
                throw new IllegalArgumentException();
            } else {
                previousPoint = pointsCopy[i];
            }

            for (int j = i + 1; j < pointsCopy.length; j++) {
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        Point p = pointsCopy[i];
                        Point q = pointsCopy[j];
                        Point r = pointsCopy[k];
                        Point s = pointsCopy[l];

                        if (Double.compare(p.slopeTo(q), p.slopeTo(r)) == 0 && Double.compare(p.slopeTo(r),  p.slopeTo(s)) == 0) {
                            LineSegment segment = new LineSegment(p, s);
                            lineSegments.add(segment);
                        }
                    }
                }
            }   
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }