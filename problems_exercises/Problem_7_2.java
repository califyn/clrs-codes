import java.io.*;
import java.util.*;

// Implemented version of Quick Sort (see Chapter 7)

public class Problem_7_2 {
  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int[] partitionPrime(int[] arr, int p, int q, int r){
    int val = arr[q];
    swap(arr, q, r - 1);
    int i = p;
    for(int j = p; j < r - 1; j++){
      if(arr[j] < val){
        swap(arr, j, i);
        i++;
      }
    }
    swap(arr, i, r - 1);
    int k = i;
    for(int j = i + 1; j < r - 1; j++){
      if(arr[j] == val){
        k++;
        swap(arr, j, k);
      }
    }
    return new int[]{i, k + 1};
  }

  static int[] quicksort_g(int[] arr, int p, int r){
    if(p < r){
      int q = rand.nextInt(r - p) + p;
      int[] ind = partitionPrime(arr, p, q, r);
      arr = quicksort_g(arr, p, ind[0]);
      arr = quicksort_g(arr, ind[1], r);
    }
    return arr;
  }
  static int[] quicksort(int[] arr){
    return quicksort_g(arr, 0, arr.length);
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 3, 4, 3};
    System.out.println("Array before sorting: " + Arrays.toString(arr));
    arr = quicksort(arr);
    System.out.println("Array after sorting: " + Arrays.toString(arr));
  }

}
