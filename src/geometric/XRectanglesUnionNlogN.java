package geometric;

import java.io.*;
import java.util.*;

public class XRectanglesUnionNlogN {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 30000;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        ArrayList<Event> eV = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            eV.add(new Event(x1, y1, y2, 1));
            eV.add(new Event(x2, y1, y2, -1));
        }
        Collections.sort(eV, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.x - o2.x;
            }
        });

        Tree tree = new Tree(MAX_Y - MIN_Y + 1);

        int area = 0;
        Event prev = null;
        for (int i = 0; i < eV.size(); i++) {
            Event cur = eV.get(i);
            int l = cur.y1;
            int r = cur.y2;
            int deltaX = 0;
            int cutLength = 0;
            if (prev != null) {
                deltaX = cur.x - prev.x;
                cutLength = tree.query(1, MIN_Y, MAX_Y, MIN_Y, MAX_Y);
//                cutLength = tree.query(1, MIN_Y, MAX_Y, l, r);
                area += deltaX * cutLength;
            }
            tree.update(1, MIN_Y, MAX_Y, l, r, cur.delta);
            prev = cur;
        }

        bw.write(area + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static class Event {
        int x, y1, y2, delta;

        public Event(int x, int y1, int y2, int delta) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.delta = delta;
        }
    }

    private static class Tree {
        int ts;
        Node[] tree;

        public Tree(int cntYSegment) { // 0 ~ 30000, cntYSegment = 30001
            this.ts = 1;
            while (this.ts < cntYSegment) {
                this.ts <<= 1;
            }
            tree = new Node[this.ts << 1];
            for (int i = 0; i < tree.length; i++) {
                tree[i] = new Node();
            }
        }

        public int query(int node, int s, int e, int l, int r) {
            if (r < s || e < l) return 0;

            if (l <= s && e <= r) return e - s + 1;

            return query(node*2, s, (s+e)/2, l, r) + query(node*2+1, (s+e)/2+1, e, l, r);
        }

        public void update(int node, int s, int e, int l, int r, int delta) {
            if (r < s || e < l) return;
            if (l <= s && e <= r) {
                tree[node].count += delta;
            } else { // **
                update(node * 2, s, (s + e) / 2, l, r, delta);
                update(node * 2 + 1, (s + e) / 2 + 1, e, l, r, delta);
//                tree[node].count = tree[node * 2].count + tree[node * 2 + 1].count;
            }

            if (tree[node].count == 0) {
                tree[node].sum = tree[node*2].sum + tree[node*2+1].sum;
            } else {
                tree[node].sum = e - s + 1;
            }
        }
    }

    private static class Node {
        int sum, count;

        public Node() {
            this.sum = 0;
            this.count = 0;
        }
    }
}
