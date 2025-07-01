import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;
    private final int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Tableau de points nul");

        int n = points.length;

        // Copie indépendante du tableau
        Point[] copy = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Point nul détecté");
            copy[i] = points[i];
        }

        // Tri pour détecter doublons et faciliter la recherche
        Arrays.sort(copy);

        for (int i = 1; i < n; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException("Points dupliqués détectés");
            }
        }

        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        // Recherche brute : 4 points colinéaires
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {  // Remplacement de l par m
                        double slope1 = copy[i].slopeTo(copy[j]);
                        double slope2 = copy[i].slopeTo(copy[k]);
                        double slope3 = copy[i].slopeTo(copy[m]);
                        if (Double.compare(slope1, slope2) == 0 && Double.compare(slope2, slope3) == 0) {
                            // Créer segment du plus petit au plus grand point
                            Point[] segmentPoints = {copy[i], copy[j], copy[k], copy[m]};
                            Arrays.sort(segmentPoints);
                            foundSegments.add(new LineSegment(segmentPoints[0], segmentPoints[3]));
                        }
                    }
                }
            }
        }

        numberOfSegments = foundSegments.size();
        segments = foundSegments.toArray(new LineSegment[numberOfSegments]);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        // Retourne une copie pour éviter mutation externe
        return segments.clone();
    }
}
