package geometric.practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class XLineIntersection_SWCP0025 {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;

    private static int[] U;
    private static int T, N;
    private static Line[] lines;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            lines = new Line[N + 1];
            U = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                int x1 = Integer.parseInt(st.nextToken());
                int y1 = Integer.parseInt(st.nextToken());
                int x2 = Integer.parseInt(st.nextToken());
                int y2 = Integer.parseInt(st.nextToken());
                lines[i] = new Line(new Point(x1, y1), new Point(x2, y2));
                U[i] = i;
            }

            for (int i = 1; i < N; i++) {
                for (int j = i + 1; j <= N; j++) {
                    if (!isUnion(i, j)) {
                        if (isIntersect(lines[i], lines[j])) {
                            union(i, j);
                        }
                    }
                }
            }

            int A = 0, B = 0, C = 0;
            int[] cnt = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                if (i == U[i]) {
                    A++;
                    cnt[U[i]]++;
                    continue;
                }
                int pIdx = find(i);
                cnt[pIdx]++;
                B = Math.max(cnt[pIdx], B);
            }

            B = Arrays.stream(cnt).max().getAsInt();


            // 디버깅 시작 -
            System.out.println(Arrays.toString(U));
            ArrayList<Integer> xs = new ArrayList<>();
            ArrayList<Integer> ys = new ArrayList<>();
//            Rectangle[] rects = new Rectangle[A];
            ArrayList<EventX> eX = new ArrayList<EventX>();
            ArrayList<EventY> eY = new ArrayList<EventY>();
            int group = 0;
            for (int i = 1; i < U.length; i++) {
                xs.clear();
                ys.clear();
                Point p1, p2;
                if (U[i] == i) {
                    p1 = lines[i].p1;
                    p2 = lines[i].p2;
                    xs.add(p1.x);
                    xs.add(p2.x);
                    ys.add(p1.y);
                    ys.add(p2.y);
                    int pIdx = U[i];
                    for (int j = 1; j < U.length; j++) {
                        if (U[j] == pIdx) {
                            p1 = lines[j].p1;
                            p2 = lines[j].p2;
                            xs.add(p1.x);
                            xs.add(p2.x);
                            ys.add(p1.y);
                            ys.add(p2.y);
                        }
                    }
                    Collections.sort(xs);
                    Collections.sort(ys);
                    int minX = xs.get(0);
                    int maxX = xs.get(xs.size() - 1);
                    int minY = ys.get(0);
                    int maxY = ys.get(ys.size() - 1);
                    eX.add(new EventX(minX, maxX));
                    eY.add(new EventY(minY, maxY));
//                    rects[group++] = new Rectangle(minX, minY, maxX, maxY);
                }
            }


//            for (Integer key : keys) {
//                int x1 = rects.get(key).x1;
//                int x2 = rects.get(key).x2;
//                int y1 = rects.get(key).y1;
//                int y2 = rects.get(key).y2;
//                if (x1 > x2) {
//                    int temp = x1;
//                    x1 = x2;
//                    x2 = temp;
//                }
//                if (y1 > y2) {
//                    int temp = y1;
//                    y1 = y2;
//                    y2 = temp;
//                }
//                eX.add(new Event(x1, x2));
//                eY.add(new Event(y1, y2));
//            }
            Collections.sort(eX, (o1, o2) -> (o1.x - o2.x));
            Collections.sort(eY, (o1, o2) -> (o1.y - o2.y));

            int min = eX.get(0).x;
            int range;
            int[] count = new int[eX.size() - 1];
            for (int i = 0; i < eX.size(); i++) {
                EventX cur = eX.get(i);
                int x = cur.x;
                count[i] += cur.flag;


            }


//            int maxX = 0, overlapping = 0;
//            int l = eX.get(0).a;
//            int r = eX.get(0).b;
//            for (int i = 1; i < eX.size(); i++) {
//                if (r >= eX.get(i).a) {
//                    overlapping++;
//
//                } else {
//                    if (maxX < overlapping) {
//                        maxX = Math.max(maxX, overlapping);
//                    }
//                    overlapping = 0;
//                    l = eX.get(i).a;
//                    r = eX.get(i).b;
//                }
//            }
//            if (maxX < overlapping) {
//                maxX = Math.max(maxX, overlapping);
//            }
//
//            overlapping = 0;
//            int maxY = 0;
//            l = eY.get(0).a;
//            r = eY.get(0).b;
//            for (int i = 1; i < eY.size(); i++) {
//                if (r >= eY.get(i).a) {
//                    overlapping++;
//                } else {
//                    if (maxY < overlapping) {
//                        maxY = Math.max(maxY, overlapping);
//                    }
//                    overlapping = 0;
//                    l = eY.get(i).a;
//                    r = eY.get(i).b;
//                }
//            }
//            if (maxY < overlapping) {
//                maxY = Math.max(maxY, overlapping);
//            }


//            int max = Math.max(maxX, maxY);
//            C = A - max;
            bw.write("#" + tc + " " + A + " " + B + " " + C + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean isIntersect(Line l1, Line l2) {
        Point p = l1.p1;
        Point q = l1.p2;
        Point r = l2.p1;
        Point s = l2.p2;

        int pq = ccw(p, q, r) * ccw(p, q, s);
        int rs = ccw(r, s, p) * ccw(r, s, q);

        if (pq == 0 && rs == 0) {
            if (q.isLessThan(p)) {
                Point temp = p;
                p = q;
                q = temp;
            }
            if (s.isLessThan(r)) {
                Point temp = r;
                r = s;
                s = temp;
            }
            return !(s.isLessThan(p) || q.isLessThan(r));
        }

        return pq <= 0 && rs <= 0;
    }

    private static int ccw(Point p, Point q, Point s) {
        Point a = new Point(q.x - p.x, q.y - p.y);
        Point b = new Point(s.x - p.x, s.y - p.y);
        long ret = a.cross(b);
        if (ret > 0) return 1;
        else if (ret < 0) return -1;
        else return 0;
    }

    private static void union(int x, int y) {
        int X = find(x);
        int Y = find(y);
        if (X != Y) {
            U[X] = Y;
        }
    }

    private static boolean isUnion(int i, int j) {
        return find(i) == find(j);
    }

    private static int find(int i) {
        if (i == U[i]) return U[i];
        U[i] = find(U[i]);
        return U[i];
    }

    private static class EventX {
        int x;
        int flag;

        public EventX(int x, int flag) {
            this.x = x;
            this.flag = flag;
        }
    }

    private static class EventY {
        int y;
        int flag;

        public EventY(int y, int flag) {
            this.y = y;
            this.flag = flag;
        }
    }

    private static class Rectangle {
        int x1, y1, x2, y2;

        public Rectangle(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private static class Line {
        Point p1, p2;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isLessThan(Point p) {
            return this.x != p.x ? this.x < p.x : this.y < p.y;
        }

        public long cross(Point p) {
            return (long) this.x * p.y - (long) this.y * p.x;
        }
    }
}
