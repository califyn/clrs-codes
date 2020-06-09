import java.io.*;
import java.util.*;

// Implemented version of Problem 7.6 in CLRS, 3rd ed.

public class Problem_07_6 {
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

  static void swap(int[][] arr, int a, int b){
    int temp1 = arr[b][0];
    int temp2 = arr[b][1];
    arr[b][0] = arr[a][0];
    arr[b][1] = arr[a][1];
    arr[a][0] = temp1;
    arr[a][1] = temp2;
  }

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int[] partitionPrime(int[] indices, int[][] arr, int p, int q, int r){
    int val = arr[q][0];
    swap(arr, q, r - 1);
    swap(indices, q, r - 1);
    int i = p;
    for(int j = p; j < r - 1; j++){
      if(arr[j][0] < val){
        if(arr[j][1] >= val){
          arr[j][0] = val;
        }
        swap(arr, j, i);
        swap(indices, j, i);
        i++;
      } else if(arr[j][0] > val){
        if(arr[j][0] <= arr[q][1]){
          arr[j][0] = val;
        }
      }
    }
    swap(arr, i, r - 1);
    swap(indices, i, r - 1);
    int k = i;
    for(int j = i + 1; j < r; j++){
      if(arr[j][0] == val){
        k++;
        swap(arr, j, k);
        swap(indices, j, k);
      }
    }
    int t = i;
    for(int j = 0; j < t; j++){
      if(arr[j][0] == val){
        i--;
        swap(arr, j, i);
        swap(indices, j, i);
      }
    }
    return new int[]{i, k + 1};
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

  static void quicksort_g(int[] indices, int[][] arr, int p, int r){
    if(p < r){
      int q = p;
      int[] ind = partitionPrime(indices, arr, p, q, r);
      quicksort_g(indices, arr, p, ind[0]);
      quicksort_g(indices, arr, ind[1], r);
    }
  }

  static int[][] quicksort(int[][] arr){
    int[] indices = new int[arr.length];
    int[][] arrCopy = new int[arr.length][2];
    for(int i = 0; i < arr.length; i++){
      arrCopy[i][0] = arr[i][0]; arrCopy[i][1] = arr[i][1];
      indices[i] = i;
    }
    quicksort_g(indices, arrCopy, 0, arr.length);
    int[][] ret = new int[arr.length][2];
    for(int i = 0; i < arr.length; i++){
      ret[i][0] = arr[indices[i]][0];
      ret[i][1] = arr[indices[i]][1];
    }
    return ret;
  }

  public static void main(String[] args){
    int[][] arr = {{3, 4}, {-1, 1}, {5, 6}, {2, 4}, {7, 8}, {4, 4}};
    System.out.println("Array before sorting: " + toString(arr));
    arr = quicksort(arr);
    System.out.println("Array after sorting: " + toString(arr));
  }

}
