import java.io.*;
import java.util.*;

// Implemented version of Merge Sort (see Chapter 2)

public class mergesort {
  public static int[] merge(int p, int q, int r, int[] arr, int[] cmerge){
    int i = p;
    int j = q;
    while(i + j < r + q){
      if(i == q){
        cmerge[i + j - q] = arr[j];
        j++;
      } else if (j == r){
        cmerge[i + j - q] = arr[i];
        i++;
      } else if (arr[i] >= arr[j]){
        cmerge[i + j - q] = arr[j];
        j++;
      } else {
        cmerge[i + j - q] = arr[i];
        i++;
      }
    }
    for(int t = p; t < r; t++){
      arr[t] = cmerge[t];
    }
    return arr;
  }

  public static int[] mergesort_g(int p, int q, int[] arr, int[] cmerge){
    if(q - p == 0 || q - p == 1){
      return arr;
    }
    arr = mergesort_g(p, (int) (p + q)/2, arr, cmerge);
    arr = mergesort_g((int) (p + q)/2, q, arr, cmerge);
    arr = merge(p, (int) (p + q)/2, q, arr, cmerge);
    return arr;
  }

  public static int[] mergesort(int[] arr){
    int[] cmerge = new int[arr.length];
    return mergesort_g(0, arr.length, arr, cmerge);
  }

  public static void main(String[] args){
    // Example of usage
    int[] arr = new int[]{5, 1, 2, 4, 3, 3};
    //System.out.println("Original array: " + Arrays.toString(arr));
    
    arr = mergesort(arr);
    //System.out.println("After merge sort: " + Arrays.toString(arr));
  }

}
