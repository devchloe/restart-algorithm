package geometric;

import java.util.*;

public class RectanglesUnion2 {

    public static void main(String[] args) {
        Rectangle[] rects = new Rectangle[2];
        rects[0] = new Rectangle(10, 10, 20, 20);
        rects[1] = new Rectangle(15, 15, 25, 30);
        System.out.println(unionArea(rects));
    }

    private static int unionArea(Rectangle[] rects) {
        // 1. X축 이벤트 정렬
        // 2. Y축 구간 좌표 중복 제거, 정렬
        int N = rects.length;
        ArrayList<Event> eV = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            eV.add(new Event(rects[i].x1, i, 1)); //0
            eV.add(new Event(rects[i].x2, i, -1)); // 1
            yList.add(rects[i].y1);
            yList.add(rects[i].y2);
        }

        Collections.sort(eV, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if (o1.x == o2.x) {
                    return o2.delta - o1.delta;
                }
                return o1.x - o2.x;
            }
        });
        ArrayList<Integer> uniqueYList = new ArrayList<>(new HashSet<>(yList));
        Collections.sort(uniqueYList);


        int area = 0;

        int[] count = new int[uniqueYList.size() - 1];
        for (int i = 0; i < eV.size(); i++) {
            Event cur = eV.get(i);
            int x = cur.x;
            int delta = cur.delta; // 겹치는 사각형 개수를 카운트할 때 쓰는 값, 1 또는 -1
//            if (cur.type == 0) {
//                delta = 1;
//            } else {
//                delta = -1;
//            }
            int rectNum = cur.num;
            int y1 = rects[cur.num].y1;
            int y2 = rects[cur.num].y2;

            for (int j = 0; j < uniqueYList.size() - 1; j++) {
                int nextY = uniqueYList.get(j);
                if (y1 <= nextY && nextY <= y2) {
                    count[j] += delta;
                }

//                if (j > 0 && count[j - 1] == 0) {
//                    beginY = j - 1;
//                }
//                count[j] += delta;
//                if (count[j] == 0) {
//                    int deltaX = cur.x - eV.get(i - 1).x;
//                    area += (deltaX * (beginY - j));
//                }
            }
            int cutLength = 0;
            for (int k = 0; k < uniqueYList.size() - 1; k++) {
                if (count[k] > 0) {
                    cutLength += uniqueYList.get(k + 1) - uniqueYList.get(k);
                }
            }

            if (i + 1 < eV.size()) {
                int deltaX = eV.get(i + 1).x - x;
                area += (cutLength * deltaX);
            }
        }

        return area;
    }

    private static class Event {
        int x; // point x
        int num; // retangle idx
        int delta; // lower-left: 0, upper-right: 0

        public Event(int x, int num, int delta) {
            this.x = x;
            this.num = num;
            this.delta = delta;
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
