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

  public static int[] randompermutation(int[] arr, int m){
    int r, temp;
    for(int i = 0; i < m; i++){
      r = interrandom(i, arr.length);
      temp = arr[i];
      arr[i] = arr[r];
      arr[r] = temp;
    }
    return Arrays.copyOfRange(arr, 0, m);
  }

  public static void main(String[] args){
    // Examples of usage
    int[] arr = new int[]{3, 1, 4, 2, 5, 3};
    // System.out.println("Original array: " + Arrays.toString(arr));

    // Random permutation of arr
    arr = randompermutation(arr);
    // System.out.println("After random permutation: " + Arrays.toString(arr));

    // Random sample of size 2 from arr
    int[] sample = randompermutation(arr, 2);
    // System.out.println("A random sample: " + Arrays.toString(sample));
  }


}
