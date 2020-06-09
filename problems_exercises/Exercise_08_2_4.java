import java.io.*;
import java.util.*;

// Implemented version of Exercise 8.2-4 in CLRS, 3rd ed.

public class Exercise_8_2_4 {

  public static int[] rangepreprocessing(int[] arr){ // Counting sort: sorts an array of nonnegative integers in O(n + max) time
    int[] sorted = new int[arr.length];
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < arr.length; i++){
      if(max < arr[i]){
        max = arr[i];
      }
    }
    int[] holding = new int[max + 1];
    for(int i = 0; i < arr.length; i++){
      holding[arr[i]]++;
    }
    for(int i = 1; i <= max; i++){
      holding[i] += holding[i - 1];
    }
    return holding;
  }

  public static int range(int[] holding, int a, int b){
    int less = 0;
    if(a == 0){
      less = 0;
    } else {
      less = holding[a - 1];
    }
    int more = 0;
    if(b >= holding.length){
      more = holding[holding.length - 1];
    } else {
      more = holding[b];
    }
    return more - less;
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    System.out.println("Array: " + Arrays.toString(arr));
    int[] holding = rangepreprocessing(arr);
    System.out.println("Number between 2 and 4: " + range(holding, 2, 4));
  }
}
