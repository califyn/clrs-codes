import java.io.*;
import java.util.*;

// Implemented version of Problem 8.3 part (b) in CLRS, 3rd ed.

public class Problem_8_3b {

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

  public static int[] countingsort(String[] arr, int min, int col, int[] indices){ // Counting sort: sorts an array of nonnegative integers in O(n + max) time
    int[] sorted = new int[arr.length];
    int max = Integer.MIN_VALUE;
    for(int i = min; i < arr.length; i++){
      if(max < (int) arr[indices[i]].charAt(col)){
        max = (int) arr[indices[i]].charAt(col);
      }
    }
    int[] holding = new int[max + 1];
    for(int i = min; i < arr.length; i++){
      holding[(int) arr[indices[i]].charAt(col)]++;
    }
    for(int i = 1; i <= max; i++){
      holding[i] += holding[i - 1];
    }
    for(int j = arr.length - 1; j >= min; j--){
      sorted[holding[(int) arr[indices[j]].charAt(col)] - 1 + min] = indices[j];
      holding[(int) arr[indices[j]].charAt(col)]--;
    }
    for(int j = min - 1; j >= 0; j--){
      sorted[j] = indices[j];
    }
    return sorted;
  }

  public static String[] staggered_radixsort(String[] arr){ // Solution to 8.3 part (b) here
    if(arr.length == 0){
      return new String[]{};
    }
    int[] digs = new int[arr.length];
    for(int i = 0; i < arr.length; i++){
      digs[i] = arr[i].length();
    }
    int[] dist = countDistribution(digs);
    for(int i = 1; i < dist.length; i++){
      dist[i] += dist[i - 1];
    }
    for(int i = dist.length - 1; i >= 1; i--){
      dist[i] = dist[i - 1];
    }
    dist[0] = 0;
    String[] digsSorted = new String[arr.length];
    for(int i = 0; i < arr.length; i++){
      digsSorted[dist[digs[i]]] = arr[i];
      dist[digs[i]]++;
    }
    int[] indices = new int[arr.length];
    for(int i = 0; i < arr.length; i++){
      indices[i] = i;
    }
    for(int i = dist.length - 1; i >= 1; i--){
      indices = countingsort(digsSorted, dist[i - 1], i - 1, indices);
    }
    String[] sorted = new String[arr.length];
    for(int i = 0; i < arr.length; i++){
      sorted[i] = digsSorted[indices[i]];
    }
    return sorted;
  }

  public static void main(String[] args){
    String[] arr = {"from", "have", "find", "as", "come", "do", "for", "can", "", "go", "and", "also", "abou", "even", "get", "day", "first", "could", "all", "a", "give", "about", "cant"};
    System.out.println("Before sort: " + Arrays.toString(arr));
    arr = staggered_radixsort(arr);
    System.out.println("After sort: " + Arrays.toString(arr));
  }

}
