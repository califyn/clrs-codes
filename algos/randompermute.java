import java.io.*;
import java.util.*;

// Implemented version of Random Permutations (In Place) and Random Sampling (see Chapter 5)

public class randompermute {
  static Random rand = new Random();

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

  static HashMap<Integer, Integer> randomsample_g(int m, int n){
    if(m == 0){
      return new HashMap<Integer, Integer>();
    } else {
      HashMap<Integer, Integer> S = randomsample_g(m - 1, n - 1);
      int i = interrandom(0, n);
      if(S.containsKey(i)){
        S.put(n - 1, -1);
      } else {
        S.put(i, -1);
      }
      return S;
    }
  }

  public static int[] randomsample(int[] arr, int m){
    Object[] indices = randomsample_g(m, arr.length).keySet().toArray();
    int[] out = new int[m];
    for(int i = 0; i < m; i++){
      out[i] = arr[(Integer) indices[i]];
    }
    out = randompermutation(out);
    return out;
  }

  public static void main(String[] args){
    // Examples of usage
    int[] arr = new int[]{3, 1, 4, 2, 5, 3};
    System.out.println("Original array: " + Arrays.toString(arr));

    // Random permutation of arr
    arr = randompermutation(arr);
    System.out.println("After random permutation: " + Arrays.toString(arr));

    // Random sample of size 2 from arr
    int[] sample = randomsample(arr, 2);
    System.out.println("A random sample: " + Arrays.toString(sample));
  }


}
