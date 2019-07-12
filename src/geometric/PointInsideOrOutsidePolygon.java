package geometric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PointInsideOrOutsidePolygon {

    private static BufferedReader br;
    private static StringTokenizer st;
    private static StringBuilder sb;
    private static final long EXTREME_X = 1000000001; //**
    private static Point e;
    private static int T, N;
    private static Point[] p;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            p = new Point[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                p[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            Point r;
            sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                r = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                e = new Point(EXTREME_X, r.y+1); //**

                if(isInside(r, p)) {
                    sb.append("in ");
                } else {
                    sb.append("out ");
                }
            }
            System.out.printf("#%d %s\n", tc, sb.toString());
        }
    }

    private static boolean isInside(Point q, Point[] p) {
        int crossing = 0;
        for (int i = 0; i < N; i++) {
            int j = (i + 1) % N;

            Point p1 = p[i];
            Point p2 = p[j];
            int o1 = direction(p1, p2, q);
            int o2 = direction(p1, p2, e);
            int o3 = direction(q, e, p1);
            int o4 = direction(q, e, p2);

            if (o1 * o2 < 0 && o3 * o4 < 0) { //**
                crossing++;
            }
        }
        if (crossing % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static int direction(Point p, Point q, Point r) {
        double ret = ccw(new Point(q.x - p.x, q.y - p.y), new Point(r.x - p.x, r.y - p.y));
        if (ret > 0) {
            return 1;
        } else if (ret < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private static double ccw(Point p, Point q) {
        return (double) (p.x * q.y) - (double) (p.y * q.x);
    }

    private static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
