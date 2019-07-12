package geometric;

public class Orientation {
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 4);
        Point p3 = new Point(1, 2);
        int o = orientation(p1, p2, p3);
        if (o > 0) System.out.println("clockwise");
        else if (o < 0) System.out.println("counterclockwise");
        else System.out.println("collinear");

    }

    private static int orientation(Point p1, Point p2, Point p3) {
        return (p2.y - p1.y)*(p3.x-p2.x) - (p2.x - p1.x)*(p3.y-p2.y);
    }

    private static class Point {
        int x, y;
        public Point(int p, int q) {
            this.x = p;
            this.y = q;
        }
    }
}
