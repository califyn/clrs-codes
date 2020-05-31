import java.io.*;
import java.util.*;

// Implemented version of two order statistics algorithms (see Chapter 9)

public class orderstatistics {
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

  static int select(int[] arr, int i){ // O(n) average-case (randomized) time complexity; more simple
    return select_g(arr, 0, arr.length, i);
  }

  static int[] bubble_sort(int[] arr){
    int bubbles = 1;
    do {
      bubbles = 0;
      for (int i = 0; i < arr.length - 1; i++){
        if(arr[i + 1] < arr[i]){
          swap(arr, i, i + 1);
          bubbles++;
        }
      }
    } while (bubbles > 0);
    return arr;
  }

  static int S_partition(int[] arr, int pow, int p, int r, int k, int c){
    int val = arr[k];
    swap(arr, k, c);
    int i = (int) Math.floor(2 * (Math.pow(5, pow) - 1) / 4) + p;
    int pairOff = 0;
    for(int j = (int) Math.floor(2 * (Math.pow(5, pow) - 1) / 4) + p; j < c; j += Math.pow(5, pow)){
      if(arr[j] < val){
        swap(arr, j, i);
        i += Math.pow(5, pow);
        if(i >= r - r % (int) Math.floor(Math.pow(5, pow))){
          i = c;
        }
      }
    }
    if(arr[c] < val){
      swap(arr, c, i);
      i += Math.pow(5, pow);
      if(i >= r - r % (int) Math.floor(Math.pow(5, pow))){
        i = c;
      }
    }
    swap(arr, i, c);
    return i;
  }

  static int Select_g(int[] arr, int pow, int p, int r, int k, int c){
    if (r - p <= Math.pow(5, pow + 1)){
      ArrayList<Integer> indices = new ArrayList<Integer>();
      int l = p + (int) Math.floor(Math.pow(5, pow));
      while (l <= r) {
        indices.add(l - (int) Math.floor((Math.pow(5, pow) + 1) / 2));
        l += (int) Math.floor(Math.pow(5, pow));
      }
      if (c != -1){
        indices.add(c);
      }
      Collections.sort(indices, new Comparator<Integer>(){
        public int compare(Integer left, Integer right) {
          return arr[left] - arr[right];
        }
      });
      return indices.get(k);
    }
    for (int i = (int) Math.floor(2 * (Math.pow(5, pow + 1) - 1) / 4) + p; i < r - (int) Math.floor(2 * Math.pow(5, pow)); i += Math.pow(5, pow + 1)) {
      int[] sorted = new int[]{i - (int) Math.floor(2 * Math.pow(5, pow)), i - (int) Math.floor(1 * Math.pow(5, pow)), i, i + (int) Math.floor(1 * Math.pow(5, pow)), i + (int) Math.floor(2 * Math.pow(5, pow))};
      for (int j = 0; j < 5; j++){
        sorted[j] = arr[sorted[j]];
      }
      sorted = bubble_sort(sorted);
      for (int j = -2; j < 3; j++){
        arr[i + j * (int) Math.floor(Math.pow(5, pow))] = sorted[j + 2];
      }
    }
    int l = (int) Math.floor((r - p) / Math.pow(5, pow + 1));
    l *= Math.pow(5, pow + 1);
    l += p;
    l += Math.pow(5, pow);
    ArrayList<Integer> indices = new ArrayList<Integer>();
    while (l - (int) Math.floor((Math.pow(5, pow) + 1) / 2) < r) {
      indices.add(l - (int) Math.floor((Math.pow(5, pow) + 1) / 2));
      l += Math.pow(5, pow);
    }
    if (c != -1) {
      indices.add(c);
    }
    int[] sorted = new int[indices.size()];
    for (int i = 0; i < sorted.length; i++){
      sorted[i] = arr[indices.get(i)];
    }
    sorted = bubble_sort(sorted);
    for (int i = 0; i < sorted.length; i++){
      arr[indices.get(i)] = sorted[i];
    }
    int index;
    if (Math.ceil((r - p)/Math.pow(5, pow)) > 5){
      index = Select_g(arr, pow + 1, p, r, (int) Math.floor(Math.floor(Math.ceil((r - p) / Math.pow(5, pow + 1)) - 1)/2), indices.size() == 0 ? -1 : indices.get((indices.size() - 1)/2));
    } else {
      index = (int) Math.floor((Math.ceil((r - p)/Math.pow(5, pow)) -1)/2);
    }
    int t = S_partition(arr, pow, p, r, index, c == -1 ? r- 1 : c);
    int s = (int) Math.floor((t - p) / Math.pow(5, pow));
    if (s == k){
      return t;
    } else if (s > k){
      if (t == c) {
        return Select_g(arr, pow, p, (int) Math.floor(((int) ((r - p) / Math.pow(5, pow))) * Math.pow(5, pow)) + p, k, -1);
      }
      return Select_g(arr, pow, p, t, k, -1);
    } else {
      return Select_g(arr, pow, (int) Math.floor((s + 1) * Math.pow(5, pow)) + p, r, k - s - 1, c);
    }
  }

  static int Select(int[] arr, int k){ // O(n) worst-case time complexity; more complex
    int ret = Select_g(arr, 0, 0, arr.length, k, -1);
    ret = arr[ret];
    return ret;
  }

  public static void main(String[] args){
    int[] arr = new int[]{3, 1, 5, 2, 4, 3};
    /*
    for(int i = 0; i < 6; i++){
      arr = new int[]{3, 1, 5, 2, 4, 3};
      System.out.println(i + "th element: " + Select(arr, i));
      System.out.println(i + "th element: " + select(arr, i));
    }
    */
  }

}
