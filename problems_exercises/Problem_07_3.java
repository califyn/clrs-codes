import java.io.*;
import java.util.*;

// Implemented version of Problem 7.3 part (c) in CLRS, 3rd ed.

public class Problem_07_3 {
  static Random rand = new Random();

  static int median(int a, int b, int c){
    if((a >= b && a >= c) || (a <= b && a >= c)){
      return a;
    }
    if((b >= c && b <= a) || (b >= a && b <= c)){
      return b;
    }
    return c;
  }

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int partition(int[] arr, int p, int q, int r){
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

  static int[] bitail_quicksort(int[] arr, int p, int r){
    while(p < r){
      int q = rand.nextInt(r - p) + p;
      int ind = partition(arr, p, q, r);
      if(ind - p > r - ind - 1){
        arr = bitail_quicksort(arr, ind + 1, r);
        r = ind;
      } else {
        arr = bitail_quicksort(arr, p, ind);
        p = ind + 1;
      }
    }
    return arr;
  }

  static int[] quicksort(int[] arr){
    return bitail_quicksort(arr, 0, arr.length);
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    System.out.println("Array before sorting: " + Arrays.toString(arr));
    arr = quicksort(arr);
    System.out.println("Array after sorting: " + Arrays.toString(arr));
  }

}
