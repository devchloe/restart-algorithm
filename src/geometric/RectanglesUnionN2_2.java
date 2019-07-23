package geometric;

import java.io.*;
import java.util.*;

public class RectanglesUnionN2_2 {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static Rectangle[] rects;
    private static ArrayList<EventX> eV;
    private static ArrayList<Integer> yList;
    private static ArrayList<Integer> uniqueYList;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        rects = new Rectangle[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            rects[i] = new Rectangle(x1, y1, x2, y2);
        }
        int ans = unionArea(rects);
        bw.write(ans + "\n");
        bw.flush();
        br.close();
        bw.close();
    }

    private static int unionArea(Rectangle[] rects) {
        getEventXYAndSort(rects);
        int area = 0;
        int cntYList = uniqueYList.size();
        int[] count = new int[cntYList - 1]; // Y값들이 만드는 구간 수만큼 생성
        for (int i = 0; i < eV.size(); i++) {
            EventX cur = eV.get(i);
            int x = cur.x;
            int delta = cur.type;
            int rectIdx = cur.rectIdx;
            int y1 = rects[rectIdx].y1;
            int y2 = rects[rectIdx].y2;

            for (int j = 0; j < uniqueYList.size(); j++) {
                if (y1 <= uniqueYList.get(j) && uniqueYList.get(j) < y2) {
                    count[j] += delta;
                }
            }

            int cutLength = 0;
            for (int j = 0; j < count.length; j++) {
                if (count[j] > 0) {
                    cutLength += uniqueYList.get(j + 1) - uniqueYList.get(j);
                }
            }
            int deltaX = 0;
            if (i + 1 < eV.size()) {
                deltaX = eV.get(i + 1).x - x;
            }
            area += cutLength * deltaX;
        }
        return area;
    }

    private static void getEventXYAndSort(Rectangle[] rects) {
        eV = new ArrayList<>();
        yList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            Rectangle r = rects[i];
            eV.add(new EventX(r.x1, 1, i));
            eV.add(new EventX(r.x2, -1, i));
            yList.add(r.y1);
            yList.add(r.y2);
        }

        Collections.sort(eV, new Comparator<EventX>() {
            @Override
            public int compare(EventX o1, EventX o2) {
                return o1.x - o2.x; // x좌표가 시작인지 끝인지에 따라 정렬하지 않아도 면적을 구하는데 오차는 생기지 않는다.
            }
        });
        uniqueYList = new ArrayList<>(new HashSet<>(yList));
        Collections.sort(uniqueYList);
    }

    private static class EventX {
        int x, type, rectIdx;

        public EventX(int x, int type, int rectIdx) {
            this.x = x;
            this.type = type;
            this.rectIdx = rectIdx;
        }

        @Override
        public String toString() {
            return "EventX{" +
                    "x=" + x +
                    ", type=" + type +
                    ", rectIdx=" + rectIdx +
                    '}';
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
