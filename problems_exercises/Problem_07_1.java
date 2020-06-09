import java.io.*;
import java.util.*;

// Solution code for Problem 7.1 part (e) in CLRS, 3rd ed.

public class Problem_7_1 {

  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int hoare_partition(int[] arr, int p, int q, int r){ // Hoare partition algorithm
    int val = arr[q];
    int i = p - 1; int j = r;
    if(r - p <= 1){
      return -2;
    }
    outer: while(i < j){
      do {
        i++;
      } while (arr[i] < val);
      do {
        j--;
      } while (arr[j] > val);
      if(i < j){
        swap(arr, i, j);
      } else {
        break outer;
      }
    }
    return j;
  }

  static int[] quicksort_g(int[] arr, int p, int r){
    if(p < r){
      int q = p;
      int ind = hoare_partition(arr, p, q, r) + 1;
      if(ind != -1){
        arr = quicksort_g(arr, p, ind);
        arr = quicksort_g(arr, ind, r);
      }
    }
    return arr;
  }

  static int[] quicksort(int[] arr){
    return quicksort_g(arr, 0, arr.length);
  }

  public static void main(String[] args){
    int[] arr = new int[]{1, 2, 5, 4, 2, 6, 8, 7};
    System.out.println("Array before sorting: " + Arrays.toString(arr));
    arr = quicksort(arr);
    System.out.println("Array after sorting: " + Arrays.toString(arr));
  }
}
