package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

// 합쳐진 구간의 시작과 끝을 출력
// or 합쳐진 구간의 개수 출력
public class LeetCode56_Merge_Intervals {
    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1,3},{2,6},{8,10},{15,18}};
        int[][] output = merge(intervals);
        for (int i=0; i<output.length; i++) {
            System.out.println(Arrays.toString(output[i]));
        }
    }
    public static int[][] merge(int[][] intervals) {
            LinkedList<Interval> merged = new LinkedList<>();
        if (intervals.length > 0) {
            Arrays.sort(intervals, 0, intervals.length, (o1, o2) -> Integer.compare(o1[0], o2[0]));
            for (int i=0; i<intervals.length; i++) {
                if (merged.isEmpty() || merged.getLast().end < intervals[i][0]) {
                    merged.add(new Interval(intervals[i][0], intervals[i][1]));
                    continue;
                }
                merged.getLast().end = Math.max(merged.getLast().end, intervals[i][1]);
            }
        }
        int[][] output = new int[merged.size()][2];
        for (int i=0; i<merged.size(); i++) {
            output[i][0] = merged.get(i).start;
            output[i][1] = merged.get(i).end;
        }
        return output;
    }

    private static class Interval {
        int start, end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
