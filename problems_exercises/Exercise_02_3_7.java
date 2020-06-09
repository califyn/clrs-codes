import java.io.*;
import java.util.*;

// Solution code for Exercise 2.3-7 in CLRS, 3rd ed.

public class Exercise_02_3_7 {
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
        i++;
      } else if (a[i] >= b[j]){
        c[i + j] = b[j];
        j++;
      } else {
        c[i + j] = a[i];
        i++;
      }
    }
    for(int t = p; t < r; t++){
      arr[t] = c[t - p];
    }
    return arr;
  }

  public static int[] mergesort_g(int p, int q, int[] arr){
    if(q - p == 0 || q - p == 1){
      return arr;
    }
    arr = mergesort_g(p, (int) (p + q)/2, arr);
    arr = mergesort_g((int) (p + q)/2, q, arr);
    arr = merge(p, (int) (p + q)/2, q, arr);
    return arr;
  }

  public static int[] mergesort(int[] arr){
    return mergesort_g(0, arr.length, arr);
  }

  public static void main(String[] args) {
    int N = 100;

    Random rand = new Random();
    int[] a = new int[N];
    for(int i = 0; i < N; i++){
      a[i] = rand.nextInt(N);
    }
    int x = rand.nextInt(2 * N);

    // Slow verification
    boolean yes = false;
    for(int i = 0; i < N; i++){
      for(int j = i + 1; j < N; j++){
        if(a[i] + a[j] == x){
          yes = true;
        }
      }
    }
    System.out.println("Slow check: " + yes);

    // Fast verification
    yes = false;
    a = mergesort(a);
    int[] b = new int[N];
    //System.out.println(Arrays.toString(a));
    //System.out.println(x);
    for(int i = 0; i < N; i++){
      b[i] = x - a[N - 1 - i];
    }
    //System.out.println(Arrays.toString(b));
    int i = 0;
    int j = 0;
    while(!yes && i < N && j < N){
      if(a[i] == b[j]){
        if(i + j != N - 1){
          yes = true;
        }
        i++;
      } else if(a[i] < b[j]){
        i++;
      } else if(a[i] > b[j]){
        j++;
      }
    }
    System.out.println("Fast check: " + yes);
  }

}
