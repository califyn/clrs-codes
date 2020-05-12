import java.io.*;
import java.util.*;

// Implemented version of Bucket Sort (see Chapter 8)

public class bucketsort {

  public static double[] bucketsort(double[] arr, int n){
    if(n == -1){
      n = arr.length;
    }
    LinkedList<Double>[] buckets = new LinkedList[n];
    for(int i = 0; i < n; i++){
      buckets[i] = new LinkedList<Double>();
    }
    for(int i = 0; i < arr.length; i++){
      buckets[(int) Math.floor(arr[i] * 10)].add(arr[i]);
    }
    for(int i = 0;i < n; i++){
      Collections.sort(buckets[i]);
    }
    int whichBucket = 0;
    for(int i = 0; i < arr.length; i++){
      while(buckets[whichBucket].size() == 0){
        whichBucket++;
      }
      arr[i] = buckets[whichBucket].pop();
    }
    return arr;
  }

  public static void main(String[] args){
    double[] arr = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
    // System.out.println("Before sorting: " + Arrays.toString(arr));
    arr = bucketsort(arr, -1);
    // System.out.println("After sorting: " + Arrays.toString(arr));
  }

}
