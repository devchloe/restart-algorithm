package geometric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ConvexHullWithGrahamScan {
    private static BufferedReader br;
    private static StringTokenizer st;
    private static int T, N;
    private static Point[] p;
    private static int[] s;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            p = new Point[N+2];
            s = new int[N+1];

            for (int i=1; i<=N; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                p[i] = new Point(x, y);
            }

            int minIdx = 1;
            for (int i=2; i<=N; i++) {
                if (p[i].y < p[minIdx].y || (p[i].y == p[minIdx].y && p[i].x < p[minIdx].x)) {
                    minIdx = i;
                }

//                if (p[i].y < p[minIdx].y) {
//                    minIdx = i;
//                } else if (p[i].y == p[minIdx].y && p[i].x < p[minIdx].x) {
//                    minIdx = i;
//                }
            }

            Arrays.sort(p, 2, N + 1, new Comparator<Point>() {
                @Override
                public int compare(Point p1, Point p2) {
                    int value = ccw(p[1], p1, p2);
                    if (value == 0) {
                        long d1 = distance(p[1], p1);
                        long d2 = distance(p[1], p2);
                        if (d1 < d2) {
                            return -1;
                        } else if (d1 > d2) {
                            return 1;
                        }
                    }

                    if (value > 0) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });


            s[1] = 1;
            s[2] = 2;
            int top = 2;
            p[N+1] = p[1];

            for (int i=3; i<=N+1; i++) {
                while(top > 1 && ccw(p[s[top-1]], p[s[top]], p[i]) <= 0) {
                    top--;
                }

                if (i == N+1) {
                    break;
                }

                ++top;
                s[top] = i;
            }

            System.out.printf("#%d %d", tc, top);
        }
    }

    private static long distance(Point p, Point q) {
        return (long) (p.x-q.x)*(p.x-q.x) + (long) (p.y-q.y)*(p.y-q.y);
    }

    private static int ccw(Point p0, Point p1, Point p2) {
        long ret = ccw(new Point(p1.x-p0.x, p1.y-p0.y), new Point(p2.x-p0.x, p2.y-p0.y));
        if (ret > 0) {
            return 1;
        } else if (ret < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private static long ccw(Point p, Point q) {
        return (long) p.x*q.y - (long) p.y*q.x;
    }

    private static class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
