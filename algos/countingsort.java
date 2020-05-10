import java.io.*;
import java.util.*;

// Implemented version of Counting Sort (see Chapter 8)

public class countingsort {

  public static int[] countingsort(int[] arr){ // Counting sort: sorts an array of nonnegative integers in O(n + max) time
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
    for(int j = arr.length - 1; j >= 0; j--){
      sorted[holding[arr[j]] - 1] = arr[j];
      holding[arr[j]]--;
    }
    return sorted;
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    // System.out.println("Array before sorting: " + Arrays.toString(arr));
    arr = countingsort(arr);
    // System.out.println("Array after sorting: " + Arrays.toString(arr));
  }
}
