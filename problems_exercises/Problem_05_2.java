import java.io.*;
import java.util.*;

// Solution code for Problem 5.2 in CLRS, 3rd ed.

public class Problem_5_2 {
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

  public static boolean randomSearch(int[] arr, int x){
    boolean[] hasYet = new boolean[arr.length];
    for(int i = 0; i < arr.length; i++){
      hasYet[i] = false;
    }
    int numLeft = arr.length;

    while(numLeft > 0){
      int r = rand.nextInt(arr.length);
      if(arr[r] == x){
        return true;
      } else if(!hasYet[r]){
        hasYet[r] = true;
        numLeft--;
      }
    }
    return false;
  }

  public static boolean deterministicSearch(int[] arr, int x){
    for(int i = 0; i < arr.length; i++){
      if(arr[i] == x){
        return true;
      }
    }
    return false;
  }

  public static boolean scrambleSearch(int[] arr, int x){
    arr = randompermutation(arr);
    return deterministicSearch(arr, x);
  }


  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    System.out.print("Input size: ");
    int N = scan.nextInt();
    System.out.print("Num reps: ");
    int r = scan.nextInt();

    long randomSearchTime = 0;
    long deterministicSearchTime = 0;
    long scrambleSearchTime = 0;
    long randomSearchWorst = 0;
    long deterministicSearchWorst = 0;
    long scrambleSearchWorst = 0;

    for(int i = 0; i < r; i++){
      int[] arr = new int[N];
      for(int j = 0; j < N; j++){
        arr[j] = rand.nextInt(N);
      }
      int x = rand.nextInt(N);

      long beginTime, endTime;

      // Random search
      beginTime = System.nanoTime();
      boolean a = randomSearch(arr, x);
      endTime = System.nanoTime();
      randomSearchTime += endTime - beginTime;
      if(endTime - beginTime > randomSearchWorst){
        randomSearchWorst = endTime - beginTime;
      }

      // Deterministic search
      beginTime = System.nanoTime();
      boolean b = deterministicSearch(arr, x);
      endTime = System.nanoTime();
      deterministicSearchTime += endTime - beginTime;
      if(endTime - beginTime > deterministicSearchWorst){
        deterministicSearchWorst = endTime - beginTime;
      }

      // Scramble search
      beginTime = System.nanoTime();
      boolean c = scrambleSearch(arr, x);
      endTime = System.nanoTime();
      scrambleSearchTime += endTime - beginTime;
      if(endTime - beginTime > scrambleSearchWorst){
        scrambleSearchWorst = endTime - beginTime;
      }

      if(a != b || a != c || b != c){
        System.out.println("Something went wrong...");
      }
    }

    System.out.println("Average time for random search: " + randomSearchTime / r + " us");
    System.out.println("Average time for deterministic search: " + deterministicSearchTime / r + " us");
    System.out.println("Average time for scramble search: " + scrambleSearchTime / r + " us");
    System.out.println("Worst-case for random search: " + randomSearchWorst + " us");
    System.out.println("Worst-case for deterministic search: " + deterministicSearchWorst + " us");
    System.out.println("Worst-case for scramble search: " + scrambleSearchWorst + " us");
  }
}
