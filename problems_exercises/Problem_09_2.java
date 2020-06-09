import java.io.*;
import java.util.*;

// Implemented version of Problem 9.2 part (c) in CLRS, 3rd ed.

public class Problem_09_2 {
  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static void swap(double[] arr, int a, int b){
    double temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int interrandom(int a, int b){
    return rand.nextInt(b - a) + a;
  }

  public static int[] randompermutation(int[] arr){
    int r, temp;
    for(int i = 0; i < arr.length; i++){
      r = interrandom(i, arr.length);
      temp = arr[i];
      arr[i] = arr[r];
      arr[r] = temp;
    }
    return arr;
  }

  static int partition(int[] arr, double[] w, int p, int q, int r){
    int val = arr[q];
    swap(arr, q, r - 1);
    swap(w, q, r - 1);
    int i = p;
    int pairOff = 0;
    for(int j = p; j < r - 1; j++){
      if(arr[j] < val){
        swap(arr, j, i);
        swap(w, j, i);
        i++;
      } else if(arr[j] == val && pairOff == 0){
        swap(arr, j, i);
        swap(w, j, i);
        i++;
        pairOff = 1;
      } else if(arr[j] == val && pairOff == 1){
        pairOff = 0;
      }
    }
    swap(arr, i, r - 1);
    swap(w, i, r - 1);
    return i;
  }

  static int wselect_g(int[] arr, double[] w, int p, int r, double i){
    if (p + 1 == r){
      return arr[p];
    }
    int q = rand.nextInt(r - p) + p;
    int k = partition(arr, w, p, q, r);
    double l = 0;
    for (int x = p; x < k; x++){
      l += w[x];
    }
    if (l == i){
      return arr[k];
    } else if (l > i){
      return wselect_g(arr, w, p, k, i);
    } else {
      return wselect_g(arr, w, k, r, i - l);
    }
  }

  static int wselect(int[] arr, double[] w, double i){ // Weighted version of select
    return wselect_g(arr, w, 0, arr.length, i);
  }

  static int wmedian(int[] arr, double[] w){
    return wselect_g(arr, w, 0, arr.length, 0.5);
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    double[] w = new double[]{0.1, 0.2, 0.05, 0.4, 0.15, 0.1};
    System.out.println("Array: " + Arrays.toString(arr));
    System.out.println("Weights: " + Arrays.toString(w));
    for(double i = 0; i < 1; i += 0.25){
      arr = new int[]{3, 1, 5, 2, 4, 3};
      w = new double[]{0.1, 0.2, 0.05, 0.4, 0.15, 0.1};
      System.out.println("Above " + i + ": " + wselect(arr, w, i));
    }
    System.out.println("Weighted median: " + wmedian(arr, w));
  }
}
