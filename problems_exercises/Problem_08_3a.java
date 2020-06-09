import java.io.*;
import java.util.*;

// Implemented version of Problem 8.3 part (a) in CLRS, 3rd ed.

public class Problem_08_3a {

  public static int[] countDistribution(int[] arr){
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < arr.length; i++){
      if(arr[i] > max){
        max = arr[i];
      }
    }
    int[] dist = new int[max + 1];
    for(int i = 0; i < arr.length; i++){
      dist[arr[i]]++;
    }
    return dist;
  }

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
    if(arr.length == 0){
      return new int[][]{{}};
    }
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

  public static int[] digitsort(int[] arr){ // Sorts total of n digis in O(n) time -- problem 8.3 (a)
    int[] digs = new int[arr.length];
    for(int i = 0; i < arr.length; i++){
      if(arr[i] == 0 || arr[i] == 1){
        digs[i] = 1;
      } else {
        digs[i] = (int) Math.ceil(Math.log(arr[i]) / Math.log(10));
      }
    }
    int[] dist = countDistribution(digs);
    for(int i = 1; i < dist.length; i++){
      dist[i] += dist[i - 1];
    }
    for(int i = dist.length - 1; i >= 1; i--){
      dist[i] = dist[i - 1];
    }
    dist[0] = 0;
    int[] digsSorted = new int[arr.length];
    for(int i = 0; i < arr.length; i++){
      digsSorted[dist[digs[i]]] = arr[i];
      dist[digs[i]]++;
    }
    int lowBound = 0;
    for(int i = 0; i < dist.length; i++){
      if(i == 0){
        lowBound = 0;
      } else {
        lowBound = dist[i - 1];
      }
      int[][] toRadixSort = new int[dist[i] - lowBound][i + 1];
      for(int j = lowBound; j < dist[i]; j++){
        for(int k = i; k >= 0; k--){
          toRadixSort[j - lowBound][k] = digsSorted[j] % 10;
          digsSorted[j] = digsSorted[j] / 10;
        }
      }
      toRadixSort = radixsort(toRadixSort);
      for(int j = lowBound; j < dist[i]; j++){
        digsSorted[j] = 0;
        for(int k = 0; k < i + 1; k++){
          digsSorted[j] += toRadixSort[j - lowBound][k];
          digsSorted[j] *= 10;
        }
        digsSorted[j] = digsSorted[j] / 10;
      }
    }
    return digsSorted;
  }

  public static void main(String[] args){
    int[] arr = {1600, 2, 1, 1527, 13, 0, 1, 101, 1526, 89, 25, 4, 105924};
    System.out.println("Before sort: " + Arrays.toString(arr));
    arr = digitsort(arr);
    System.out.println("After sort: " + Arrays.toString(arr));
  }

}
