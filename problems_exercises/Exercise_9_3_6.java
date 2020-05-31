import java.io.*;
import java.util.*;

// Implemented version of Exercise 9.3-6 in CLRS, 3rd ed.

public class Exercise_9_3_6 {
  static Random rand = new Random();

  static void swap(int[] arr, int a, int b){
    int temp = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
  }

  static int interrandom(int a, int b){
    return rand.nextInt(b - a) + a;
  }

  public static int[] randompermutation(int[] arr){
    int r, temp;
    for(int i = 0; i < arr.length; i++){
      r = interrandom(i, arr.length);
      temp = arr[i];
      arr[i] = arr[r];
      arr[r] = temp;
    }
    return arr;
  }

  static int partition(int[] arr, int p, int q, int r){
    int val = arr[q];
    swap(arr, q, r - 1);
    int i = p;
    int pairOff = 0;
    for(int j = p; j < r - 1; j++){
      if(arr[j] < val){
        swap(arr, j, i);
        i++;
      } else if(arr[j] == val && pairOff == 0){
        swap(arr, j, i);
        i++;
        pairOff = 1;
      } else if(arr[j] == val && pairOff == 1){
        pairOff = 0;
      }
    }
    swap(arr, i, r - 1);
    return i;
  }

  static int select_g(int[] arr, int p, int r, int i){
    //3System.out.println(Arrays.toString(arr) + " " + p + " " + r + " " + i + "...");
    if (r > arr.length){
      r = arr.length;
    }
    if (p + 1 == r){
      return arr[p];
    }
    int q = rand.nextInt(r - p) + p;
    int k = partition(arr, p, q, r);
    k = k - p;
    if (k == i){
      return arr[k + p];
    } else if (k > i){
      return select_g(arr, p, k + p, i);
    } else {
      return select_g(arr, k + p + 1, r, i - k - 1);
    }
  }

  static int kth_qindex(int n, int k, int i){
    return i == 0 ? 0 : (int) Math.ceil(i * n / k) - 1;
  }

  static int[] kth_quantiles(int[] arr, int k){
    int[] ret = new int[k - 1];
    for (int lvl = (int) Math.ceil(Math.log(k - 1) / Math.log(2)); lvl > 0; lvl--){
      int x = (int) Math.floor(Math.pow(2, lvl - 1));
      System.out.println(lvl + " " + x+ " asdas");
      do {
        ret[x] = select_g(arr, kth_qindex(arr.length, k, x - (int) Math.floor(Math.pow(2, lvl - 1))),
                            kth_qindex(arr.length, k, x + (int) Math.floor(Math.pow(2, lvl - 1))),
                            kth_qindex(arr.length, k, x) - kth_qindex(arr.length, k, x - (int) Math.floor(Math.pow(2, lvl - 1))));
        x += (int) Math.floor(Math.pow(2, lvl));
      } while (x < k - 1);
    }
    return ret;
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    for(int i = 2; i < 6; i++){
      arr = new int[]{3, 1, 5, 2, 4, 3};
      System.out.println(i + "th quantiles: " + Arrays.toString(kth_quantiles(arr, i)));
    }
  }
}
