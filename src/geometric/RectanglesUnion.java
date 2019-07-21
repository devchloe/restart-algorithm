package geometric;

import java.io.*;
import java.util.*;

public class RectanglesUnion {

    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static Point[][] rects;
    //    private static Event[] eV;
//    private static Event[] eH;
    private static List<Event> eV;
    private static List<Event> eH;
//    private static int MAX = 1000;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        rects = new Point[N + 1][2];
        eV = new ArrayList<>();
        eH = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            int x, y;
            Point p;

            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            p = new Point(x, y);
            rects[i][0] = p;
            eV.add(new Event(i, 0));
            eH.add(new Event(i, 0));

            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            p = new Point(x, y);
            rects[i][1] = p;
            eV.add(new Event(i, 1));
            eH.add(new Event(i, 1));
        }
        int cntPoints = 2 * N;
        Collections.sort(eV, (e1, e2) -> (rects[e1.ind][e1.type].x - rects[e2.ind][e2.type].x));
        Collections.sort(eH, (e1, e2) -> (rects[e1.ind][e1.type].y - rects[e2.ind][e2.type].y));


        // 전처리 끝

        int ans = unionArea(eV, eH, N, cntPoints);
        bw.write(ans + "\n");
        bw.flush();
        br.close();
        bw.close();
    }

    private static int unionArea(List<Event> eV, List<Event> eH, int N, int cntPoints) {
        int[] inActive = new int[N + 1]; // inactive: 0, active: 1
        int initRectInd = eV.get(0).ind;
        inActive[initRectInd] = 1; // why
        int area = 0;

        for (int i = 1; i < cntPoints; i++) {// i는 eV에서 인덱스이다! rects에서 사각형 번호가 아님
            Event curX = eV.get(i);
            Event prev = eV.get(i - 1);
            int deltaX = rects[curX.ind][curX.type].x - rects[prev.ind][prev.type].x;
            // deltaX 범위.. 0, 양수
            // deltaX == 0인 경우는? 4가지
            // i) 이전 사각형의 X 끝/시작과 현재 사각형의 X *시작/끝이 겹치는 경우
            // ii) 이전 사격형의 X 끝/시작과 현재 사격형의 X 끝/시작이 겹치는 경우
            if (deltaX == 0) { // 면적을 계산해도 0이므로 eH 개수만큼 y축으로 스위핑하지 않아도 된다.
                if (curX.type == 0) {
                    inActive[curX.ind] = 1;
                } else {
                    inActive[curX.ind] = 0;
                }
                continue;
            }

            int cntOverlappedRects = 0;
            int beginY = 0;
            for (int j = 0; j < eH.size(); j++) {
                Event curY = eH.get(j);
                if (inActive[curY.ind] == 1) {
                    if (curY.type == 0) {
                        if (cntOverlappedRects == 0) beginY = rects[curY.ind][curY.type].y;
                        cntOverlappedRects++;
                    } else {
                        cntOverlappedRects--;
                        if (cntOverlappedRects == 0) {
                            int deltaY = rects[curY.ind][curY.type].y - beginY;
                            area += deltaX * deltaY;

                        }
                    }
                }
            }
            if (curX.type == 0) {
                inActive[curX.ind] = 1; // 현재 x축에서 y축으로 스위핑하고 시작X일 경우 사각형을 활성화 시켜주는 이유: y축으로 스위핑할 때 현재 활성화된 사각형에 대해서만 overlapping된 카운트를 증가시켜야 하기 때문
            } else {
                inActive[curX.ind] = 0;
            }
        }
        return area;
    }

    private static class Event {
        int ind;
        int type; // lower-left: 0, upper-right: 1

        public Event(int ind, int type) {
            this.ind = ind;
            this.type = type;
        }
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
