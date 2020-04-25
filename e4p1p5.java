import java.io.*;
import java.util.*;

// Solution code for Exercises 4.1-1 to 4.1-5 in CLRS, 3rd ed.

public class e4p1p5 {

  public static int max(int a, int b, int c){
    if(a >= b && a >= c){
      return a;
    } else if (b >= a && b >= c){
      return b;
    }
    return c;
  }

  public static int max_subarray_brute(int[] arr){
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < arr.length; i++){
      int cumulative_sum = 0;
      for(int j = i; j < arr.length; j++){
        cumulative_sum += arr[j];
        if(cumulative_sum > max){
          max = cumulative_sum;
        }
      }
    }
    return max;
  }

  public static int max_crossing_subarray(int[] arr){
    int low = 0;
    int high = arr.length;
    int mid = (low + high) / 2;
    // Downwards iteration
    int left_sum_max = Integer.MIN_VALUE;
    int left_sum = 0;
    for(int i = mid - 1; i >= low; i--){
      left_sum += arr[i];
      if(left_sum > left_sum_max){
        left_sum_max = left_sum;
      }
    }
    if(left_sum_max == Integer.MIN_VALUE){
      left_sum_max = 0;
    }
    // Upwards iteration
    int right_sum_max = Integer.MIN_VALUE;
    int right_sum = 0;
    for(int i = mid; i < high; i++){
      right_sum += arr[i];
      if(right_sum > right_sum_max){
        right_sum_max = right_sum;
      }
    }
    if(right_sum_max == Integer.MIN_VALUE){
      right_sum_max = 0;
    }
    return left_sum_max + right_sum_max;
  }

  public static int max_subarray_med(int[] arr){
    if(arr.length == 0){
      return 0;
    } else if(arr.length == 1){
      return max(0, 0, arr[0]);
    }
    int[] a = Arrays.copyOfRange(arr, 0, arr.length/2);
    int[] b = Arrays.copyOfRange(arr, arr.length/2, arr.length);
    int max_left = max_subarray_med(a);
    int max_right = max_subarray_med(b);
    int max_cross = max_crossing_subarray(arr);
    //System.out.println(max_left + " " + max_right + " " + max_cross + " " + Arrays.toString(arr));
    return max(max_left, max_right, max_cross);
  }

  public static int max_subarray_med_opt(int[] arr){
    if(arr.length < 250){
      return max_subarray_brute(arr);
    }
    if(arr.length == 0){
      return 0;
    } else if(arr.length == 1){
      return max(0, 0, arr[0]);
    }
    int[] a = Arrays.copyOfRange(arr, 0, arr.length/2);
    int[] b = Arrays.copyOfRange(arr, arr.length/2, arr.length);
    int max_left = max_subarray_med(a);
    int max_right = max_subarray_med(b);
    int max_cross = max_crossing_subarray(arr);
    //System.out.println(max_left + " " + max_right + " " + max_cross + " " + Arrays.toString(arr));
    return max(max_left, max_right, max_cross);
  }

  public static int max_subarray_fast(int[] arr){
    int allMaxSubarray = Integer.MIN_VALUE;
    int edgeMaxSubarray = 0;
    for(int i = 0; i < arr.length; i++){
      edgeMaxSubarray = max(edgeMaxSubarray + arr[i], arr[i], arr[i]);
      if(edgeMaxSubarray > allMaxSubarray){
        allMaxSubarray = edgeMaxSubarray;
      }
    }
    return allMaxSubarray;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("Input size: ");
    int N = scan.nextInt();
    int R = 10;

    long slowTime = 0;
    long medTime = 0;
    long medOTime = 0;
    long fastTime = 0;
    for(int t = 0; t < R; t++){
      Random rand = new Random();
      int[] a = new int[N];
      for(int i = 0; i < N; i++){
        a[i] = rand.nextInt(2 * N) - N;
      }
      //System.out.println(Arrays.toString(a));

      long startTime, endTime;

      startTime = System.nanoTime();
      int slow = max_subarray_brute(a);
      endTime = System.nanoTime();
      slowTime += endTime - startTime;

      startTime = System.nanoTime();
      int med = max_subarray_med(a);
      endTime = System.nanoTime();
      medTime += endTime - startTime;

      startTime = System.nanoTime();
      int medO = max_subarray_med(a);
      endTime = System.nanoTime();
      medOTime += endTime - startTime;

      startTime = System.nanoTime();
      int fast = max_subarray_fast(a);
      endTime = System.nanoTime();
      fastTime += endTime - startTime;
      //System.out.println(fast + " " + medO);

      //System.out.println(slow + " in " + slowTime);
      //System.out.println(med + " in " + medTime);

    }
    System.out.println("Average time (Brute-force): " + (float) slowTime / R);
    System.out.println("Average time (O(nlgn) algo): " + (float) medTime / R);
    System.out.println("Average time (O(nlgn) algo, optimized w/ brute): " + (float) medOTime / R);
    System.out.println("Average time (O(n) algo): " + (float) fastTime / R);
  }

}
