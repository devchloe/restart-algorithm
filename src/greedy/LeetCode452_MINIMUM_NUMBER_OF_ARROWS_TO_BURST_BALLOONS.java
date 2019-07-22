package greedy;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class LeetCode452_MINIMUM_NUMBER_OF_ARROWS_TO_BURST_BALLOONS {
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static int[][] points;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        points = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            points[i][0] = x1;
            points[i][1] = x2;
        }


        Arrays.sort(points, 0, points.length, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];// 시작순이 아니라 끝나는 순으로 정렬
                // 이유: 시작하는 x좌표로 정렬 했을 때, 해당 선분 위에 어디에나 화살을 쏘아 맞으려면 다음에 나오는 선분들의 끝 x좌표가 순차적으로 커야한다.
                // 즉 선분의 끝 x좌표보다 다음 선분의 끝 x좌표가 커야한다는 필수 조건이 필요하다.
                // 이것을 끝나는 x좌표를 기준으로 정렬한다면 선분의 끝 x좌표가 앞의 선분보다 항상 크다는 조건이 충족된다.
                // 그래서 끝나는 x좌표를 기준으로 정렬하면 현재 오른쪽 구간보다 시잠점이 큰 선분의 개수를 찾으면 되므로 쉬워진다.
//                if (o1[0] == o2[0]) return o1[1] - o2[1];
//                return o1[0] - o2[0];
            }
        });

        int arrows = 0;
        if (N > 0) {
            int l = points[0][0];
            int r = points[0][1];
            arrows = 1;
            for (int i = 1; i < N; i++) {
                if (r < points[i][0]) {
                    arrows++;
                    l = points[i][0];
                    r = points[i][1];
                }
            }
        }

//        arrows++;
        bw.write(arrows + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
