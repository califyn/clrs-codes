import java.io.*;
import java.util.*;
import java.lang.*;

// Implemented version of Heaps, heapsort, priority queues (see Chapter 6)
// Typically binary unless a number d (for a d-ary heap) is specified

// To change to a minheap: override canbeabove (example below)

public class Heap {
  ArrayList<Integer> list = new ArrayList<Integer>();
  int d = 2;

  // Operation for if a's value can be above b's value.
  // Override this for min heaps, other kind of heaps. (see example)
  public boolean canbeabove(int a, int b){
    return list.get(a) >= list.get(b);
  }

  // Returns the uppermost of two elements.
  int top(int a, int b){
    if(canbeabove(a, b)){
      return a;
    }
    return b;
  }

  // Largest child
  public int maxchild(int i){
    int ind = d * i + 1;
    int maxInd = ind;
    do {
      if(canbeabove(ind, maxInd)){
        maxInd = ind;
      }
      ind++;
    } while (ind < list.size() && ind <= (i + 1)* d);
    return maxInd;
  }

  // Parent
  public int parent(int i){
    if(i < 1 || i >= list.size()){
      throw new IndexOutOfBoundsException();
    } else {
      return (i - 1) / d;
    }
  }

  // Constructor
  public Heap(int[] toAdd){
    this.d = 2;
    this.list = new ArrayList<Integer>();
    for(int i = 0; i < toAdd.length; i++){
      this.list.add(toAdd[i]);
    }
    this.buildheap();
  }

  public Heap(int[] toAdd, int d){
    this.d = d;
    this.list = new ArrayList<Integer>();
    for(int i = 0; i < toAdd.length; i++){
      this.list.add(toAdd[i]);
    }
    this.buildheap();
  }

  // Size of heap
  public int size(){
    return list.size();
  }

  // Top of the heap
  public int head(){
    return list.get(0);
  }

  // Move an element at position i down the heap as needed to maintain the heap property
  void heapify(int i){
    int ind = i;
    int replace, temp;
    while(d * ind < list.size() - 1){
      replace = top(maxchild(ind), ind);
      if(ind == replace){
        break;
      } else {
        temp = list.get(ind);
        list.set(ind, list.get(replace));
        list.set(replace, temp);
        ind = replace;
      }
    }
  }

  // Set position i to be a certain key; adjusts position as needed to maintain heap property
  public void updatekey(int i, int key){
    list.set(i, key);
    int temp;
    while(i > 0 && !canbeabove(parent(i), i)){
      temp = list.get(i);
      list.set(i, list.get(parent(i)));
      list.set(parent(i), temp);
      i = parent(i);
    }
    heapify(i);
  }

  // Adds a new key to the heap
  public void appendkey(int key){
    int i = list.size();
    list.add(key);
    int temp;
    while(i > 0 && !canbeabove(parent(i), i)){
      temp = list.get(i);
      list.set(i, list.get(parent(i)));
      list.set(parent(i), temp);
      i = parent(i);
    }
  }

  // Deletes and returns a specified key
  public int deletekey(int i){
    if(list.size() <= i){
      throw new ArrayIndexOutOfBoundsException();
    }
    int ret = list.get(i);
    if(i != list.size() - 1){
      updatekey(i, list.remove(list.size() - 1));
    } else {
      list.remove(0);
    }
    return ret;
  }

  // Returns and removes the top element of the heap, maintaining the heap property
  public int pop(){
    return deletekey(0);
  }

  // Creates a new heap
  void buildheap(){
    for(int i = list.size() / d + 1; i >= 0; i--){
      heapify(i);
    }
  }

  // Heap sort (assuming the heap is a max heap)
  // Note heap is unusable after this, if you want to keep it you have to duplicate it before
  public int[] heapsort(){
    int[] ret = new int[list.size()];
    for(int i = list.size() - 1; i > 0; i--){
      ret[i] = list.get(0);
      list.set(0, list.remove(i));
      heapify(0);
    }
    ret[0] = list.get(0);
    return ret;
  }

  public static void main(String[] args){
    // Heapsort example
    Heap myheap = new Heap(new int[]{1, 2, 3, 4, 5, 6, 7});
    int[] sorted = myheap.heapsort();
    // System.out.println(Arrays.toString(sorted));

    // Priority queue example
    myheap = new Heap(new int[0]){
      @Override
      public boolean canbeabove(int a, int b){
        return list.get(a) <= list.get(b);
      }
    };
    myheap.appendkey(0);
    myheap.appendkey(5);
    myheap.appendkey(3);
    /* for(int i = 0; i < 3; i++){
      System.out.print(myheap.pop());
    } */

    // Ternary heap example
    myheap = new Heap(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 3);
    /* for(int i = 0; i < 10; i++){
      System.out.print(myheap.list.get(i) + " ");
    }
    System.out.println(); */
    sorted = myheap.heapsort();
    // System.out.println(Arrays.toString(sorted));
  }

}
