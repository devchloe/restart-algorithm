package geometric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PolygonArea {
    private static BufferedReader br;
    private static StringTokenizer st;
    private static int N;
    private static Point[] p;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        p = new Point[N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            p[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Point pivot = p[0];

        int ans = area(pivot, p) / 2;
        System.out.println(ans);
    }

    private static int area(Point pivot, Point[] p) {
        int sum = 0;
        for (int i=1; i<N-1; i++) {
            int j = (i+1) % N;
            Point p1 = p[i];
            Point p2 = p[j];

            sum += ccw(new Point(p[i].x-pivot.x, p[i].y-pivot.y), new Point(p[j].x-pivot.x, p[j].y-pivot.y));
//            sum += pivot.x * p1.y + p1.x * p2.y + p2.x * pivot.y - p1.y*p2.x - p2.y * pivot.x - pivot.y * p1.x;
        }

        return sum;
    }

    private static int ccw(Point p, Point q) {
        return p.x*q.y - p.y*q.x;
    }

    private static class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
