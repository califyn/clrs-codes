import java.io.*;
import java.util.*;

// Solution code for Problem 2.4 in CLRS, 3rd ed.
// Test hi ! 

public class p2p4d {
  static int cumulative = 0;
  public static int[] merge(int p, int q, int r, int[] arr){
    int[] a = Arrays.copyOfRange(arr, p, q);
    int[] b = Arrays.copyOfRange(arr, q, r);
    int[] c = new int[r - p];
    int i = 0;
    int j = 0;
    while(i + j < r - p){
      if(i == q - p){
        c[i + j] = b[j];
        j++;
      } else if (j == r - q){
        c[i + j] = a[i];
        cumulative += r - q;
        i++;
      } else if (a[i] > b[j]){
        c[i + j] = b[j];
        j++;
      } else {
        c[i + j] = a[i];
        cumulative += j;
        //System.out.println(cumulative);
        i++;
      }
    }
    for(int t = p; t < r; t++){
      arr[t] = c[t - p];
    }
    return arr;
  }

  public static int[] mergeinversions_g(int p, int q, int[] arr){
    if(q - p == 0 || q - p == 1){
      return arr;
    }
    arr = mergeinversions_g(p, (int) (p + q)/2, arr);
    arr = mergeinversions_g((int) (p + q)/2, q, arr);
    arr = merge(p, (int) (p + q)/2, q, arr);
    return arr;
  }

  public static int[] mergeinversions(int[] arr){
    cumulative = 0;
    return mergeinversions_g(0, arr.length, arr);
  }

  public static void main(String[] args) {
    int N = 100;

    Random rand = new Random();
    int[] a = new int[N];
    for(int i = 0; i < N; i++){
      a[i] = rand.nextInt(N);
    }

    // Slow verification
    int slow = 0;
    for(int i = 0; i < N; i++){
      for(int j = i + 1; j < N; j++){
        if(a[i] > a[j]){
          slow++;
        }
      }
    }
    System.out.println("Slow check: " + slow);
    // System.out.println(Arrays.toString(a));
    // Fast verification
    a = mergeinversions(a);
    // System.out.println(Arrays.toString(a));
    System.out.println("Fast check: " + cumulative);
  }

}
