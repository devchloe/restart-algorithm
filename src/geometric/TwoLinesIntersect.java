package geometric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TwoLinesIntersect {

    private static BufferedReader br;
    private static StringTokenizer st;
    private static int T;
    private static Vector[] p;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            p = new Vector[4];
            Vector p1, p2, p3, p4;
            st = new StringTokenizer(br.readLine());
            p1 = new Vector(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            p2 = new Vector(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            st = new StringTokenizer(br.readLine());
            p3 = new Vector(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            p4 = new Vector(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            if (isIntersect(p1, p2, p3, p4)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
    }

    private static boolean isIntersect(Vector a, Vector b, Vector c, Vector d) {
        int ab = direction(a, b, c) * direction(a, b, d);
        int cd = direction(c, d, a) * direction(c, d, b);

        if (ab == 0 || cd == 0) {

            if (b.smallerThan(a)) {
                Vector temp = a;
                a = b;
                b = temp;
            }

            if (d.smallerThan(c)) {
                Vector temp = c;
                c = d;
                d = temp;
            }

            if (b.smallerThan(c) || d.smallerThan(a)) {
                return false;
            }
        }

        return ab <= 0 && cd <= 0;
    }

    private static int direction(Vector p, Vector q, Vector r) {
        double ret = ccw(new Vector(q.x-p.x, q.y-p.y), new Vector(r.x-p.x, r.y-p.y));
        if (ret > 0) {
            return 1;
        } else if (ret < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private static double ccw(Vector p, Vector q) {
        return (double) (p.x * q.y) - (double) (p.y*q.x);
    }

    private static class Vector {
        int x, y;
        public Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean smallerThan(Vector p) {
            return this.x != p.x ? this.x < p.x : this.y < p.y;
        }
    }
}
