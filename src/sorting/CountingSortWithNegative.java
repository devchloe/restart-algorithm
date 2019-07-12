package sorting;

import java.util.Arrays;

public class CountingSortWithNegative {
    public static void main(String[] args) {
        int[] arr = {-5, -10, 0, -3, 8, 5, -1, 10};
        CountingSortWithNegative ob = new CountingSortWithNegative();
        ob.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private void sort(int[] arr) {
        int n = arr.length;
        int[] output = new int[n];
        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();
        int range = max - min + 1;
        int[] cnt = new int[range];
        for (int i =0; i<n; i++) {
            cnt[arr[i] - min]++;
        }
        for (int i=1; i<range; i++) {
            cnt[i] += cnt[i-1];
        }

        for (int i=0; i<n; i++) {
            output[cnt[arr[i]-min]-1] = arr[i];
            cnt[arr[i]-min]--;
        }

        for (int i=0; i<n; i++) {
            arr[i] = output[i];
        }
    }
}
