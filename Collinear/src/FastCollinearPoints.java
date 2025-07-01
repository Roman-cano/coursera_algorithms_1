import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Le tableau de points est null");
        }

        ArrayList<LineSegment> segs = new ArrayList<>();
        int n = points.length;
        Point[] copy = new Point[n];

        // Copier les points et détecter doublons basiquement
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("null element!");
            }

            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(copy[j]) == 0) {
                    throw new IllegalArgumentException("No duplicates allowed in the array of points!");
                }
            }
            copy[i] = points[i];
        }


        // Pour chaque point origine, trier par pente et chercher segments
        for (int i = 0; i < n; i++) {
            Arrays.sort(copy, points[i].slopeOrder());
            Point origin = copy[0];

            int incr = 1;
            for (int j = 1; j < n - 2; j += incr) {

                incr = 1;
                ArrayList<Point> candidates = new ArrayList<>();
                candidates.add(origin);
                candidates.add(copy[j]);

                while (j + incr < n && origin.slopeTo(copy[j]) == origin.slopeTo(copy[j + incr])){
                    candidates.add(copy[j + incr]);
                    incr++;
                }

                if (candidates.size() >= 4) {
                    Collections.sort(candidates);
                    if (origin.compareTo(candidates.get(0)) == 0) {
                        segs.add(new LineSegment(candidates.get(0), candidates.get(candidates.size() - 1)));
                        numberOfSegments++;
                    }
                }
            }
        }

        segments = new LineSegment[segs.size()];
        for (int i = 0; i < segs.size(); i++) {
            segments[i] = segs.get(i);
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }
}
