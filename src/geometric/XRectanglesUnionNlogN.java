package geometric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class XRectanglesUnionNlogN {

    public static void main(String[] args) {
        int N = 2;
        Rectangle[] rects = new Rectangle[N];
        rects[0] = new Rectangle(10, 10, 20, 20);
        rects[1] = new Rectangle(15, 15, 25, 30);

        ArrayList<EventX> eV = new ArrayList<EventX>();
        ArrayList<Integer> yList = new ArrayList<>();
        for (int i=0; i<N; i++) {
            eV.add(new EventX(rects[i].x1, 1, i));
            eV.add(new EventX(rects[i].x2, -1, i));
            yList.add(rects[i].y1);
            yList.add(rects[i].y2);
        }
        ArrayList<Integer> uniqueYList = new ArrayList<>(new HashSet<>(yList));
        Collections.sort(eV, (e1, e2) -> (e1.x - e2.x));
        Collections.sort(uniqueYList);

        Tree tree = new Tree(uniqueYList.size()-1);
        for (int i=0; i<uniqueYList.size(); i++) {
            int y1 = uniqueYList.get(i);
            int y2 = uniqueYList.get(i+1);
        }

    }

    private static class EventX {
        int x, type, rectNum;
        public EventX(int x, int type, int rectNum) {
            this.x = x;
            this.type = type;
            this.rectNum = rectNum;
        }
    }

    private static class Tree {
        int ts;
        Interval[] tree;

        public Tree(int cntYRange) {
            this.ts = 1;
            while (ts < cntYRange) {
                this.ts <<= 1;
            }
            this.tree = new Interval[this.ts << 1];
        }
    }

    private static class Interval {
        int y1, y2, interval;
        int sum, count;

        public Interval(int y1, int y2) {
            this.y1 = y1;
            this.y2 = y2;
            this.interval = this.y2 - this.y1;
            this.sum = 0;
            this.count = 0;
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
}
