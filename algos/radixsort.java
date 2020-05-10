import java.io.*;
import java.util.*;

// Implemented version of Radix Sort (see Chapter 8)

public class radixsort {

  public static int[] countingsort(int[][] arr, int col, int[] indices){ // Counting sort: sorts an array of nonnegative integers in O(n + max) time
    int[] sorted = new int[arr.length];
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < arr.length; i++){
      if(max < arr[indices[i]][col]){
        max = arr[indices[i]][col];
      }
    }
    int[] holding = new int[max + 1];
    for(int i = 0; i < arr.length; i++){
      holding[arr[i][col]]++;
    }
    for(int i = 1; i <= max; i++){
      holding[i] += holding[i - 1];
    }
    for(int j = arr.length - 1; j >= 0; j--){
      sorted[holding[arr[indices[j]][col]] - 1] = indices[j];
      holding[arr[indices[j]][col]]--;
    }
    return sorted;
  }

  public static int[][] radixsort(int[][] arr){ // Radix sort : sorts multicolumn data in O(d(n + k)) time
    int[] indices = new int[arr.length];
    for(int i = 0; i < arr.length; i++){
      indices[i] = i;
    }
    for(int i = arr[0].length - 1; i >= 0; i--){
      indices = countingsort(arr, i, indices);
    }
    int[][] sorted = new int[arr.length][arr[0].length];
    for(int i = 0; i < arr.length; i++){
      for(int j = 0; j < arr[0].length; j++){
        sorted[i][j] = arr[indices[i]][j];
      }
    }
    return sorted;
  }

  static String toString(int[][] arr){
    if(arr.length == 1){
      return "[" + Arrays.toString(arr[0]) + "]";
    } else if(arr.length == 0){
      return "[]";
    }
    String ret = "";
    ret += "[" + Arrays.toString(arr[0]) + ",";
    for(int i = 1; i < arr.length - 1; i++){
      ret += " " + Arrays.toString(arr[i]) + ",";
    }
    ret += " " + Arrays.toString(arr[arr.length - 1]) + "]";
    return ret;
  }

  public static void main(String[] args){
    int[][] arr = {{1, 4}, {1, 2}, {1, 3}, {2, 2}, {1, 1}, {1, 3}, {2, 3}, {2, 6}, {2, 1}};
    // System.out.println("Array before sorting: " + toString(arr));
    arr = radixsort(arr);
    // System.out.println("Array after sorting: " + toString(arr));
  }
}
