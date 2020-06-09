import java.io.*;
import java.util.*;
import java.lang.*;

// Implemented version of double-ended Queues (see Chapter 10 + Ex. 10.1-5)

public class Queue {
  int max = 1;
  int[] arr;
  int head;
  int tail;

  public Queue() {
    arr = new int[max];
    head = 0;
    tail = 0;
  }

  public void push(int x){ // add to front
    head--;
    arr[head] = x;
  }


  public int dequeue(){ // remove from front
    head++;
    return arr[head - 1];
  }

  public void enqueue(int x){ // add to back
    if(tail < max){
      arr[tail] = x;
      tail++;
    } else {
      max *= 2;
      int[] newarr = new int[max];
      System.arraycopy(arr, 0, newarr, 0, max / 2);
      arr = newarr;
      enqueue(x);
    }
  }

  public int pop(){ // remove from back
    tail--;
    return arr[tail];
  }

  public String toString(){
    return Arrays.toString(Arrays.copyOfRange(arr, head, tail));
  }

  public static void main(String[] args){
    Queue q = new Queue();
    // q.enqueue(0); q.enqueue(1); q.enqueue(2);
    // System.out.println(q.toString());
    // q.dequeue();
    // System.out.println("After apply dequeue: " + q.toString());
    // q.pop();
    // System.out.println("After apply pop: " + q.toString());
    // q.push(0);
    // System.out.println("After pushing 0 onto the queue: " + q.toString());
  }
}
