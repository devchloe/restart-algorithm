//package geometric.practice;
//
//import java.util.*;
//class Solution {
//    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{1,3},{2,6},{8,10},{15,18}};
//        int[][] output = merge(intervals);
//        for (int i=0; i<output.length; i++) {
//            System.out.println(Arrays.toString(output[i]));
//        }
//    }
//    public static int[][] merge(int[][] intervals) {
////        Arrays.sort(intervals, 0, intervals.length, new Comparator<int[]>() {
////            @Override
////            public int compare(int[] o1, int[] o2) {
////                return o1[1] - o2[1]; // 끝나는 점 순서대로 오름차순 정렬하면 시작점이 가장 작고 끝나는 점이 가장 큰 선분이 들어오면 시작점을 앞으로 당기는 업데이트를 할 수 없다.
//                // 시작점은 그대로 놔두고 겹치는 선분을 연장해 나가면서 끝나는 점을 계속 업데이트 해나가는 알고리즘에서는.
////            }
////        });
//        Arrays.sort(intervals, 0, intervals.length, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0] - o2[0];
//            }
//        });
//        int l = intervals[0][0];
//        int r = intervals[0][1];
//        list.add(new Line(l, r));
//        for (int i=1; i<intervals.length; i++) {
//            if (r > intervals[i][0]) {
//                r = Math.max(r, intervals[i][1]);
//                list.get(i-1).x2 = r; // 이전에 저장된 구간을 꺼내서 업데이트 -> 자료구조 ArrayList가 맞나?
//            } else {
//                l = intervals[i][0];
//                r = intervals[i][1];
//                list.add(new Line(l, r));
//            }
//        }
//
//        int[][] output = new int[list.size()][2];
//        for (int i=0; i<list.size(); i++) {
//            output[i][0] = list.get(i).x1;
//            output[i][1] = list.get(i).x2;
//        }
//        return output;
//    }
//
//    private static class Line {
//        int x1, x2;
//        public Line(int x1, int x2) {
//            this.x1 = x1;
//            this.x2 = x2;
//        }
//    }
//}