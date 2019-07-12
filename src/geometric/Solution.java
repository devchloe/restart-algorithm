//package geometric;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.StringTokenizer;
//
//public class Solution {
//    private static BufferedReader br;
//    private static StringTokenizer st;
//    private static int T, N;
//    private static Line[] l;
//    private static XPoint[] xp;
//    private static YPoint[] yp;
//
//    public static void main(String[] args) throws IOException {
//        br = new BufferedReader(new InputStreamReader(System.in));
//        T = Integer.parseInt(br.readLine());
//        for (int tc = 1; tc <= T; tc++) {
//            N = Integer.parseInt(br.readLine());
//            l = new Line[N];
//            xp = new XPoint[2 * N];
//            yp = new YPoint[2 * N];
//            for (int i = 0; i < N; i++) {
//                st = new StringTokenizer(br.readLine());
//                int x1 = Integer.parseInt(st.nextToken());
//                int y1 = Integer.parseInt(st.nextToken());
//                int x2 = Integer.parseInt(st.nextToken());
//                int y2 = Integer.parseInt(st.nextToken());
//                l[i] = new Line(new Point(x1, y1), new Point(x2, y2));
//                xp[i] = new XPoint(x1, 0, i);
//                xp[i * 2 + 1] = new XPoint(x2, 1, i);
//                yp[i] = new YPoint(y1, 0, i);
//                yp[i * 2 + 1] = new YPoint(y2, 1, i);
//            }
//
//            for (int i=0; i<N; i++) {
//                int j = (i+1) % N;
//                Line a = l[i];
//                Line b = l[j];
//
//                if (isIntersect(a, b)) {
//                    union(a, b);
//                }
//            }
//
//        }
//
//    }
//
//    private static boolean isIntersect(Line a, Line b) {
//        if (!isUnion(a, b)) {
//            int pqr = direction(a.p, a.q, b.p);
//            int pqs = direction(a.p, a.q, b.q);
//            int rsp = direction(b.p, b.q, a.p);
//            int rsq = direction(b.p, b.q, a.q);
//
//            if (pqr*pqs == 0 && rsp*rsq == 0) {
//                if (a.q.smallerThan(a.p)) {
//                    Point temp = a.p;
//                    a.p = a.q;
//                    a.q = temp;
//                }
//
//                if (b.q.smallerThan(b.p)) {
//                    Point temp = b.p;
//                    b.p = b.q;
//                    b.q = temp;
//                }
//
//                if (b)
//            }
//
//            if (pqr * pqs <= 0 && rsp*rsq <=0) {
//                union(i, j);
//            }
//        }
//    }
//
//    private static class Line {
//        Point p, q;
//
//        public Line(Point p, Point q) {
//            this.p = p;
//            this.q = q;
//        }
//    }
//
//    private static class Point {
//        int x, y;
//
//        public Point(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    private static class XPoint {
//        int x;
//        boolean isLeft = false;
//        boolean isRight = false;
//        int groupNum;
//
//        public XPoint(int x, int flag, int groupNum) {
//            this.x = x;
//            if (flag == 0) {
//                this.isLeft = true;
//            } else {
//                this.isRight = true;
//            }
//            this.groupNum = groupNum;
//        }
//
//    }
//
//    private static class YPoint {
//        int y;
//        boolean isTop = false;
//        boolean isBottom = false;
//        int groupNum;
//
//        public YPoint(int y, int flag, int groupNum) {
//            this.y = y;
//            if (flag == 0) {
//                this.isTop = true;
//            } else {
//                this.isBottom = true;
//            }
//            this.groupNum = groupNum;
//        }
//
//    }
//
//}
