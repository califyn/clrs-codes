import java.io.*;
import java.util.*;

// Implemented version of Quick Sort (see Chapter 7)

public class quicksort {
  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int partition(int[] arr, int p, int q, int r){ // Implemented the property of Exercise 7.1-2
    int val = arr[q];
    swap(arr, q, r - 1);
    int i = p;
    int pairOff = 0;
    for(int j = p; j < r - 1; j++){
      if(arr[j] < val){
        swap(arr, j, i);
        i++;
      } else if(arr[j] == val && pairOff == 0){
        swap(arr, j, i);
        i++;
        pairOff = 1;
      } else if(arr[j] == val && pairOff == 1){
        pairOff = 0;
      }
    }
    swap(arr, i, r - 1);
    return i;
  }

  static void quicksort_g(int[] arr, int p, int r){
    if(p < r - 6){ // Exercise 7.4-5: leaving some things down to a bubblesort (instead of insertion sort; variation)
      int q = rand.nextInt(r - p) + p;
      int ind = partition(arr, p, q, r);
      quicksort_g(arr, p, ind);
      quicksort_g(arr, ind + 1, r);
    }
  }

  static int[] bubblesort(int[] arr){
    boolean pass;
    do {
      pass = true;
      for(int i = 0; i < arr.length- 1; i++){
        if(arr[i] > arr[i + 1]){
          swap(arr, i, i + 1);
          pass = false;
        }
      }
    } while (!pass);
  }

  static int[] quicksort(int[] arr){
    quicksort_g(arr, 0, arr.length);
    return bubblesort(arr);
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    // System.out.println("Array before sorting: " + Arrays.toString(arr));
    arr = quicksort(arr);
    // System.out.println("Array after sorting: " + Arrays.toString(arr));
  }

}
