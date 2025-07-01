public class LineSegment {
    private final Point p1;
    private final Point p2;

    public LineSegment(Point p, Point q){
        this.p1 = p;
        this.p2 = q;
    }
    public void draw() {
        p1.drawTo(p2);
    }
    public String toString(){
        return p1.toString() + "->" + p2.toString();
    }
}
