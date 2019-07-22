package greedy;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LeetCode435_NON_OVERLAPPING_INTERVALS {
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static StringTokenizer st;
    private static int N;
    private static int[][] intervals;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        intervals = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            intervals[i][0] = x1;
            intervals[i][1] = x2;
        }

        int n = intervals.length;
        Arrays.sort(intervals, 0, N, (l1, l2) -> (l1[1] - l2[1]));

        int overlapping = 0;
        if (n > 0) {
            int l = intervals[0][0];
            int r = intervals[0][1];
            for (int i = 1; i < N; i++) {
                if (r > intervals[i][0]) {
                    overlapping++;
                } else {
                    l = intervals[i][0];
                    r = intervals[i][1];
                }
            }
        }
        bw.write(overlapping + "\n");
        bw.flush();
        br.close();
        bw.close();
    }
}
