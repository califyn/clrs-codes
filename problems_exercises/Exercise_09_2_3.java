import java.io.*;
import java.util.*;

// Implemented version of Exercise 9.2-3 in CLRS, 3rd ed.

public class Exercise_09_2_3 {
  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
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

  static int select_g(int[] arr, int p, int r, int i){ // Iterative version
    while (true) {
      if (p + 1 == r){
        return arr[p];
      }
      int q = rand.nextInt(r - p) + p;
      int k = partition(arr, p, q, r);
      k = k - p;
      if (k == i){
        return arr[k + p];
      } else if (k > i){
        r = k + p;
      } else {
        p = k + p + 1;
        i = i - k - 1;
      }
    }
  }

  static int select(int[] arr, int i){
    return select_g(arr, 0, arr.length, i);
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    for(int i = 0; i < 6; i++){
      arr = new int[]{3, 1, 5, 2, 4, 3};
      System.out.println(i + "th element: " + select(arr, i));
    }
  }

}
