package geometric;

import org.w3c.dom.css.Rect;

import java.io.*;
import java.util.*;

//
public class RectanglesUnionWithSegment {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static Rectangle[] rects;
    private static EventX[] eV;
    private static EventY[] eH;
    private static int[] intervals;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        rects = new Rectangle[N + 1];
        eV = new EventX[2 * N + 1];
        eH = new EventY[2 * N + 1];

        for (int i = 1; i <= N; i++) {
            int x1, y1, x2, y2;
            Rectangle r;

            st = new StringTokenizer(br.readLine());
            x1 = Integer.parseInt(st.nextToken());
            y1 = Integer.parseInt(st.nextToken());
            x2 = Integer.parseInt(st.nextToken());
            y2 = Integer.parseInt(st.nextToken());
            r = new Rectangle(x1, y1, x2, y2);
            rects[i] = r;

            eV[i] = new EventX(x1, i, 0);
            eH[i] = new EventY(y1, i, 0);
            eV[2 * i] = new EventX(x2, i, 1);
            eH[2 * i] = new EventY(y2, i, 1);
        }
        Arrays.sort(eV, 1, 2 * N + 1, new Comparator<EventX>() {
            @Override
            public int compare(EventX o1, EventX o2) {
                if (o1.x == o2.x) return o1.type - o2.type;
                return o1.x - o2.x;
            }
        });
        Arrays.sort(eH, 1, 2 * N + 1, new Comparator<EventY>() {
            @Override
            public int compare(EventY o1, EventY o2) {
                if (o1.y == o2.y) return o1.type - o2.type;
                return o1.y - o2.y;
            }
        });


        intervals = new int[]
        // 전처리 끝

        int cntInterval = 2*N-1;
        Tree tree = new Tree(N);
        // 트리 초기화

        long ans = unionArea(eV, N, tree);
        bw.write(ans + "\n");
        bw.flush();
        br.close();
        bw.close();
    }

    private static long unionArea(EventX[] eV, int N, Tree tree) {
        long area = 0;
        EventX prev, cur;
        prev = eV[1];
        for (int i = 2; i <= 2 * N; i++) {
            // 현재 면적 계산
            cur = eV[i];
            long deltaX = cur.x - prev.x;
            long deltaY = tree.query(1, 1, N, 1, N);
            area += deltaX * deltaY;

            int l = rects[cur.ind].y1;
            int r = rects[cur.ind].y2;
            // 그다음 면적을 걔산하기 위해서 rect 카운터 계산
            if (cur.type == 0) {
                tree.update(1, 1, N, l, r, 1);
            } else {
                tree.update(1, 1, N, l, r, -1);
            }

            prev = cur;
        }

        return area;
    }

    private static class EventX {
        int x;
        int ind;
        int type; // lower-left: 0, upper-right: 1

        public EventX(int x, int ind, int type) {
            this.x = x;
            this.ind = ind;
            this.type = type;
        }
    }

    private static class EventY {
        int y;
        int ind;
        int type;

        public EventY(int y, int ind, int type) {
            this.y = y;
            this.ind = ind;
            this.type = type;
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

    private static class Node {
        int sum;
        int count;
        public Node() {
            this.sum = 0;
            this.count = 0;
        }
    }
    private static class Tree {
        int ts;
        Node[] tree;

        public Tree(int N) {
            this.ts = 1;
            while (this.ts < N) {
                this.ts <<= 1;
            }
            this.tree = new Node[this.ts * 2];
        }

        public long query(int node, int s, int e, int l, int r) {
            if (r < s || e < l) return 0;
            if (l <= s && e <= r) return tree[node].sum;

            int mid = (s + e) / 2;
            return query(node * 2, s, mid, l, r) + query(node * 2 + 1, mid + 1, e, l, r);
        }

        public void update(int node, int s, int e, int l, int r, int delta) {
            if (r < s || e < l) return;
            if (l <= s && e <= r) {
                tree[node].count += delta;
                if (tree[node].count == 0) {
                    tree[node].sum = tree[node*2].sum + tree[node*2+1].sum;
                } else {

                }
            }

            int mid = (s+e)/2;
            update(node*2, s, mid, l, r, delta);
            update(node*2+1, mid+1, e, l, r, delta);
//            if ()

        }
    }
}
