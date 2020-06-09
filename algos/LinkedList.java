import java.io.*;
import java.util.*;
import java.lang.*;

// Implemented version of circular doubly-linked list w/ sentinel (at pos. 0) (see Ch. 10)

public class LinkedList {
  int max = 2;
  int[] keys;
  int[] next;
  int[] prev;
  int free;
  int size;

  public LinkedList() {
    keys = new int[max];
    next = new int[max];
    prev = new int[max];
    free = 1;
    size = 0;
    for(int i = 1; i < max - 1; i++){
      next[i] = i + 1;
    }
    next[max - 1] = 0;
    next[0] = 0;
    prev[0] = 0;
  }

  public int allocate(){ // Allocates a new object / creates space
    int x = free;
    free = next[free];
    if (free == 0){ // if running out of space
      max *= 2;
      int[] copykeys = new int[max];
      int[] copynext = new int[max];
      int[] copyprev = new int[max];
      System.arraycopy(keys, 0, copykeys, 0, max / 2);
      System.arraycopy(next, 0, copynext, 0, max / 2);
      System.arraycopy(prev, 0, copyprev, 0, max / 2);
      keys = copykeys;
      next = copynext;
      prev = copyprev;
      next[x] = max / 2;
      for(int i = max / 2; i < max - 1; i++){
        next[i] = i + 1;
      }
      next[max - 1] = 0;
    }
    free = next[x];
    return x;
  }

  public void freeobj(int x){ // Frees an object from 'memory'
    next[x] = free;
    free = x;
  }

  public int search(int i){ // Get the ith element's pointer NOT the value
    int x = next[0];
    while (i > 0 && x != 0){
      x = next[x];
      i--;
    }
    if (i < 0 || x == 0){
      throw new IndexOutOfBoundsException();
    }
    return x;
  }

  public int get(int i){ // Get the ith element's value NOT the pointer
    return keys[search(i)];
  }

  public void insertFront(int x){ // Insert an element x at the front
    int p = allocate();
    keys[p] = x;
    next[p] = next[0];
    prev[next[0]] = p;
    next[0] = p;
    prev[p] = 0;
    size++;
  }

  public void insertBack(int x){ // Insert an element x at the back
    int p = allocate();
    keys[p] = x;
    next[prev[0]] = p;
    prev[p] = prev[0];
    next[p] = 0;
    prev[0] = p;
    size++;
  }

  public void insertMiddle(int i, int x){ // Insert a element x at a position which is not at the front or back after "i" positions
    int p = allocate();
    keys[p] = x;
    int a = search(i);
    int b = search(i + 1);
    next[a] = p;
    prev[p] = a;
    prev[b] = p;
    next[p] = b;
    size++;
  }

  public void add(int x){
    add(size, x);
  }

  public void add(int i, int x) { // Adds an element x at the i th position
    if (i == 0){
      insertFront(x);
    } else if (i == size){
      insertBack(x);
    } else {
      insertMiddle(i - 1, x);
    }
  }

  public int delete(int i){ // Deletes the i th pointer NOT the ith element
    if (i <= 0){
      throw new IndexOutOfBoundsException();
    }
    next[prev[i]] = next[i];
    prev[next[i]] = prev[i];
    size--;
    freeobj(i);
    return keys[i];
  }

  public int remove(int i){ // Deletes the i th element NOT the ith pointer
    if (i < 0 || i >= size){
      throw new IndexOutOfBoundsException();
    }
    return delete(search(i));
  }

  public String toString(){
    int[] list = new int[size];
    int x = next[0];
    int i = 0;
    while (i < size){
      list[i] = keys[x];
      i++;
      x = next[x];
    }
    return Arrays.toString(list);
  }

  public static void main(String[] args){
    LinkedList ll = new LinkedList();
    ll.add(0);
    ll.add(1);
    ll.add(1); ll.add(2); ll.add(3);
    // System.out.println("A new linked list: " + ll.toString());
    ll.add(0, -1);
    // System.out.println("After adding -1 to the front of the array: " + ll.toString());
    ll.add(2, 5);
    // System.out.println("After adding 5 to the 2nd position: " + ll.toString());
    ll.remove(0);
    ll.remove(3);
    // System.out.println("After removing the 0th and 4th elements: " + ll.toString());
    // System.out.println("The first element of this as by get(): " + ll.get(1));
  }
}
