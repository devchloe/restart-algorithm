package sorting;

public class CountingSort {
    public static void main(String[] args) {
        char[] arr = "geeksforgeeks".toCharArray();
        CountingSort ob = new CountingSort();
        ob.sort(arr);
        for (int i=0; i<arr.length; i++) {
            System.out.printf("%c", arr[i]);
        }
    }

    private void sort(char[] arr) {
        int n = arr.length;
        char[] output = new char[n];
        int[] cnt = new int[256]; // 2의 8승 (8bit, 1byte  문자)

        for (int i=0; i<n; i++) {
            cnt[arr[i]]++;
        }

        for (int i=1; i<=255; i++) {
            cnt[i] += cnt[i-1];
        }

        for (int i=0; i<n; i++) {
            output[cnt[arr[i]]-1] = arr[i];
            cnt[arr[i]]--;
        }


        for (int i=0; i<n; i++) {
            arr[i] = output[i];
        }
    }
}

