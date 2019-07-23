package geometric;

import javax.lang.model.type.IntersectionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

// 직사각형 넓이의 합... 아직 못함...
public class XLineSweeping2DIndexedTree {

    private static BufferedReader br;
    private static StringTokenizer st;
    private static int N;
    private static Rectangle[] r;
    private static Queue<Event> event;
    private static List<Integer> dataY;
    private static int[] intervalY;
    private static int[] tree;
    private static int[][] idx;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        r = new Rectangle[N + 1];
        dataY = new ArrayList<>();
        event = new PriorityQueue<>(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if (o1.x == o2.x) {
                    return o1.flag - o2.flag;
                }
                return o1.x - o2.x;
            }
        });
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            r[i] = new Rectangle(x1, y1, x2, y2);
            event.add(new Event(x1, 1, i));
            event.add(new Event(x2, 0, i));
        }


        Set s = new HashSet<Integer>();
        for (int i = 1; i <= N; i++) {
            s.add(r[i].y1);
            s.add(r[i].y2);
        }

        Iterator<Integer> iter = s.iterator();
        while (iter.hasNext()) {
            dataY.add(iter.next());
        }
        Collections.sort(dataY);
        intervalY = new int[dataY.size()];
        for (int i = 0; i < dataY.size() - 1; i++) {
            int y1 = dataY.get(i);
            int y2 = dataY.get(i + 1);
            intervalY[i + 1] = y2 - y1;
        }

        IndexedTree it = new IndexedTree(intervalY.length - 1);
//        it.setData(intervalY);
//        System.out.println(Arrays.toString(it.tree));

        int prevX = 0;
        int ans = 0;
        while (!event.isEmpty()) {
            Event e = event.poll();
            ans += it.tree[1].sum * (e.x - prevX);
            prevX = e.x;
            if (e.flag == 1) {
                int ri = e.ri;
                int y1 = r[ri].y1;
                int y2 = r[ri].y2;
                it.update(idx[y1][y2], it.tree[idx[y1][y2]].sum + 1);
            } else {
                int ri = e.ri;
                int y1 = r[ri].y1;
                int y2 = r[ri].y2;
                it.update(idx[y1][y2], it.tree[idx[y1][y2]].sum - 1);
            }

        }
    }

    private static int binarySearch(int value) {
        int idx = 0;
        int s = 1, e = dataY.size() - 1;
        while (s <= e) {
            int m = (s + 2) / 2;
            if (value <= dataY.get(m)) {
                e = m - 1;
                idx = e;
            } else {
                s = m + 1;
            }
        }
        return idx;
    }

    private static class Event {
        int x;
        int flag;
        int ri;

        public Event(int x, int flag, int ri) {
            this.x = x;
            this.flag = flag;
            this.ri = ri;
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
        int dy;

        public Node() {
            this.sum = 0;
            this.count = 0;
            this.dy = 0;
        }

        public Node(int sum, int count, int dy) {
            this.sum = sum;
            this.count = count;
            this.dy = dy;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "sum=" + sum +
                    ", count=" + count +
                    '}';
        }
    }

    private static class IndexedTree {
        int bIdx, size;
        Node[] tree;

        public IndexedTree(int size) {
            super();
            this.size = size;

            int len = 1;
            while (len < this.size) {
                len <<= 1;
            }
            len <<= 1;
            tree = new Node[len];
            for (int i = 1; i < len; i++) {
                tree[i] = new Node();
            }
            this.bIdx = len / 2 - 1;
            System.out.println(len);
            System.out.println(this.bIdx);
        }

//        public void setData(int[] data) {
//            for (int i = 1; i <= size; i++) {
//                tree[bIdx + i].dy = data[i];
//            }
//
//            for (int i = bIdx; i >= 1; i--) {
//                tree[i].sum = tree[2 * i].sum + tree[2 * i + 1].sum;
//            }
//        }

        public void update(int idx, int value) {
            int tIdx = bIdx + idx;
            tree[tIdx].sum = value;

            while ((tIdx >>= 1) > 0) {
                tree[tIdx].sum = tree[tIdx * 2].sum + tree[tIdx * 2 + 1].sum;
            }
        }

        public int query(int s, int e) {
            int sum = 0;
            s += bIdx;
            e += bIdx;

            while (s <= e) {
                if (s % 2 == 1) sum += tree[s++].sum;
                if (e % 2 == 0) sum += tree[e--].sum;
                s >>= 1;
                e >>= 1;
            }

            return sum;
        }
    }
}
