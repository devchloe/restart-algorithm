package leetcode;

import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;

public class XLeetCode850_Rectangle_AREA_2_With_SegmentTree {

    public static void main(String[] args) {
        int[][] rectangles = new int[][]{{0, 0, 2, 2}, {1, 0, 2, 3}, {1, 0, 3, 1}};
//        int[][] rectangles = new int[][]{{224386961, 128668997, 546647847, 318900555},
//                {852286866, 238086790, 992627088, 949888275}, {160239672, 137108804, 398130330, 944807066}, {431047948, 462092719, 870611028, 856851714},
//                {736895365, 511285772, 906155231, 721626624}, {289309389, 607009433, 558359552, 883664714}, {780746435, 397872372, 931219192, 863727103},
//                {573523994, 124874359, 889018012, 471879750}, {619886375, 149607927, 727026507, 446976526}, {51739879, 716225241, 115331335, 785850603},
//                {171077223, 267051983, 548436248, 349498903}, {314437215, 169054168, 950814572, 481179241},
//                {64126215, 646689712, 595562376, 829164135}, {926011655, 481539702, 982179297, 832455610},
//                {40370235, 231510218, 770233582, 851797196}, {292546319, 45032676, 413358795, 783606009},
//                {424366277, 369838051, 453541063, 777456024}, {211837048, 142665527, 217366958, 952362711},
//                {228416869, 402115549, 672143142, 644930626}, {755018294, 194555696, 846854520, 939022548},
//                {192890972, 586071668, 992336688, 759060552}, {127869582, 392855032, 338983665, 954245205},
//                {665603955, 208757599, 767586006, 276627875}, {260384651, 10960359, 736299693, 761411808}, {46440611, 559601039, 911666265, 904518674},
//                {54013763, 90331595, 332153447, 106222561}, {73093292, 378586103, 423488105, 826750366}, {327100855, 516514806, 676134763, 653520887}, {930781786, 407609872, 960671631, 510621750}, {35479655, 449171431, 931212840, 617916927}};
        long area = rectangleArea(rectangles);
        System.out.println(area);
    }

    public static int rectangleArea(int[][] rectangles) {
        long area = 0;

        if (rectangles.length > 0) {
            ArrayList<Event> eV = new ArrayList<>();
            ArrayList<Integer> eH = new ArrayList<>();
            for (int i = 0; i < rectangles.length; i++) {
                int[] rect = rectangles[i];
                eV.add(new Event(rect[0], 1, i));
                eV.add(new Event(rect[2], -1, i));
                eH.add(rect[1]);
                eH.add(rect[3]);
            }
            Collections.sort(eV, (e1, e2) -> e1.x - e2.x);
            ArrayList<Integer> uniqueYList = new ArrayList<>(new HashSet<>(eH));
            Collections.sort(eH, (y1, y2) -> y1 - y2);

            Integer[] Y = uniqueYList.toArray(new Integer[0]);
            Tree tree = new Tree(uniqueYList.size(), Y);
            Map<Integer, Integer> Yi = new HashMap<>();
            for (int i = 0; i < uniqueYList.size(); i++) {
                Yi.put(uniqueYList.get(i), i);
            }

            for (int i = 0; i < eV.size(); i++) {
                Event cur = eV.get(i);
                int y1 = rectangles[cur.rectNum][1];
                int y2 = rectangles[cur.rectNum][3];
                long deltaX = 0;
                if (i + 1 < eV.size()) {
                    deltaX = eV.get(i + 1).x - cur.x;
                }
                long cutLength = tree.query(1, 1, uniqueYList.size(), Yi.get(y1), Yi.get(y2));
                area += deltaX * cutLength;
                tree.update(1, 1, uniqueYList.size(), Yi.get(y1), Yi.get(y2), cur.flag);
            }
            area %= 1_000_000_007;
        }
        return (int) area;
    }

    private static class Event {
        int x, flag, rectNum;

        public Event(int x, int flag, int rectNum) {
            this.x = x;
            this.flag = flag;
            this.rectNum = rectNum;
        }
    }

    private static class Tree {
        int ts;
        Interval[] tree;
        Integer[] Y;

        public Tree(int N, Integer[] Y) {
            this.Y = Y;
            this.ts = 1;
            while (this.ts < N) {
                this.ts <<= 1;
            }
            tree = new Interval[this.ts << 1];
            for (int i = 0; i < tree.length; i++) {
                tree[i] = new Interval();
            }
        }

        public long query(int node, int s, int e, Integer i, Integer j) {
            if (j < s || e < i) return 0;
            if (i <= s && e <= j) return tree[node].total;
            int mid = (s+e)/2;
            long leftSum = query(node*2, s, mid, i, j);
            long rightSum = query(node*2+1, mid+1, e, i,j);
            return leftSum + rightSum;
        }

        public void update(int node, int s, int e, Integer i, Integer j, int diff) {
            if (j < s || e < i) return;
            if (i == s && j == e) {
                tree[node].count += diff;
            } else {
                if (s != e) {
                    int mid = (s + e) / 2;
                    update(node * 2, s, mid, i, j, diff);
                    update(node * 2 + 1, mid + 1, e, i, j, diff);
                }
            }

            if (tree[node].count > 0) {
                tree[node].total = Y[j] - Y[i];
            } else {
//                if (s != e) {
                    tree[node].total = tree[node * 2].total + tree[node * 2 + 1].total;
//                }
            }
            return;
        }
    }

    private static class Interval {
        int total, count;

        public Interval() {
            this.total = 0;
            this.count = 0;
        }
    }
}
