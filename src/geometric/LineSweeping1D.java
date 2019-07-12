package geometric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class LineSweeping1D {
    private static BufferedReader br;
    private static StringTokenizer st;
    private static int N;
    private static int INF = -1000000000;
    private static Point[] p;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        p = new Point[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            p[i] = new Point(x, y);
        }

        Arrays.sort(p, 0, N, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x1 - o2.x1;
            }
        });

        int ans = 0;
        int left = INF, right = INF;
        for (int i=0; i<N; i++) {
            if (right < p[i].x1) {
                ans += right - left;
                left = p[i].x1;
                right = p[i].x2;
            } else {
                right = Math.max(right, p[i].x2);
            }
        }

        ans += right - left;
        System.out.println(ans);
    }

    private static class Point {
        int x1, x2;

        public Point(int x1, int x2) {
            this.x1 = x1;
            this.x2 = x2;
        }

        @Override
        public String toString() {
            return "(" + x1 + ", " + x2 + ")";
        }
    }

}

/*
*4
1 3
2 5
3 5
6 7
* */
