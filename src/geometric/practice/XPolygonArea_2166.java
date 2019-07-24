package geometric.practice;

import java.io.*;
import java.util.StringTokenizer;

// 왜 틀리지?
public class XPolygonArea_2166 {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static Point[] p;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        p = new Point[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x = (long) Integer.parseInt(st.nextToken());
            long y = (long) Double.parseDouble(st.nextToken());
            p[i] = new Point(x, y);
        }

        long area = 0;
        Point p0 = p[0];
        for (int i = 1; i < N; i++) {
            int j = (i + 1) % N;
            area += cross(new Point(p[i].x - p0.x, p[i].y - p0.y), new Point(p[j].x - p0.x, p[j].y - p0.y));
        }

        bw.write(((Math.round((Math.abs(area)) * 10) / 10.0) / 2.0) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static long cross(Point p, Point q) {
        return p.x * q.y - p.y * q.x;
    }

    private static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
