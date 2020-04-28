import java.io.*;
import java.util.*;

// Solution code for Problem 4.5 in CLRS, 3rd ed.

public class Problem_4_5 {
  static Random rand = new Random();

  // Simulating switch behavior
  public static int[] switchOutput(int a, int b){
    if(a == 0 && b == 0){
      return new int[]{rand.nextInt(2), rand.nextInt(2)};
    } else if(a == 0 && b == 1){
      return new int[]{rand.nextInt(2), 0};
    } else if(a == 1 && b == 0){
      return new int[]{0, rand.nextInt(2)};
    } else if(a == 1 && b == 1){
      return new int[]{1, 1};
    }
    System.out.println("Something went wrong...");
    return new int[]{-1, -1};
  }

  public static void main(String[] args){
    System.out.print("Input size: ");
    Scanner scan = new Scanner(System.in);
    int N = scan.nextInt();

    // Data generation
    boolean[] isGood = new boolean[N];
    int sum = 0;
    do {
      sum = 0;
      for(int i = 0; i < N; i++){
        isGood[i] = rand.nextInt(2) == 1;
        sum += isGood[i] ? 1 : 0;
      }
    } while (2 * sum <= N);

    // Finding one good switch
    long startTime = System.nanoTime();
    LinkedList<Integer> left = new LinkedList<Integer>();
    int[] results;
    int a, b, good;
    for(int i = 0; i < N; i++){
      left.add(i);
    }
    while(left.size() > 1){
      a = left.poll(); b = left.poll();
      if(a == b){
        System.out.println("Something went wrong...");
      }
      results = switchOutput(isGood[a] ? 1 : 0, isGood[b] ? 1 : 0);
      if(results[0] == 1 && results[1] == 1){
        left.add(a);
      }
    }
    good = left.poll();

    // Results
    boolean[] output = new boolean[N];
    for(int i = 0; i < N; i++){
      if(i != good){
        output[i] = switchOutput(isGood[i] ? 1 : 0, isGood[good] ? 1 : 0)[1] == 1;
      } else {
        output[i] = true;
      }
    }
    long endTime = System.nanoTime();

    // Verification
    boolean isCorrect = true;
    out: for(int i = 0; i < N; i++){
      if(output[i] != isGood[i]){
        isCorrect = false;
        break out;
      }
    }
    System.out.println("Answer is correct: " + isCorrect);
    System.out.println("Running time: " + (endTime - startTime) / 1000000000.03 + "sec");
    System.out.println("Running time / N: " + (endTime - startTime) / N + "nanosec");
  }
}
