package geometric.practice;

import java.io.*;
import java.util.*;

public class XRectanglesUnionPerimeter_2567 {
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static Rectangle[] rects;
    private static ArrayList<Event> eV;
    private static ArrayList<Integer> yList;
    private static ArrayList<Integer> uniqueList;
    private static int N;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        rects = new Rectangle[N];
        eV = new ArrayList<>();
        yList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            rects[i] = new Rectangle(w, h, w + 10, h + 10);
            eV.add(new Event(w, 1, i));
            eV.add(new Event(w + 10, -1, i));
            yList.add(h);
            yList.add(h + 10);
        }

        Collections.sort(eV, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.x - o2.x;
            }
        });
        yList = new ArrayList<Integer>(new TreeSet<>(yList));

        int perimeter = unionArea(rects, eV, yList);
        bw.write(perimeter + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static int unionArea(Rectangle[] rects, ArrayList<Event> eV, ArrayList<Integer> yList) {
        int[] counter = new int[yList.size() - 1];
        int perimeter = 0;

        for (int i = 0; i < eV.size(); i++) {
            Event cur = eV.get(i);
            int y1 = rects[cur.rectIdx].y1;
            int y2 = rects[cur.rectIdx].y2;
            int deltaCount = cur.flag;

            int cutLength = 0;
            for (int j = 0; j < yList.size(); j++) {
                if (y1 <= yList.get(j) && yList.get(j) < y2) {
                    if (cur.flag == 1) {
                        if (counter[j] == 0) {
                            cutLength += yList.get(j + 1) - yList.get(j);
                        }
                    } else if (cur.flag == -1){
                        if (counter[j] == 1) {
                            cutLength+= yList.get(j + 1) - yList.get(j);
                        }
                    }
                    counter[j] += deltaCount;
                }
            }

            int deltaX = 0;
            if (i + 1 < eV.size()) {
                deltaX = eV.get(i + 1).x - cur.x;
            }
            perimeter += (cutLength + 2 * deltaX);

        }

        return perimeter;
    }

    private static class Event {
        int x;
        int flag;
        int rectIdx;

        public Event(int x, int flag, int rectIdx) {
            this.x = x;
            this.flag = flag;
            this.rectIdx = rectIdx;
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