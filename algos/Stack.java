import java.io.*;
import java.util.*;
import java.lang.*;

// Implemented version of Stacks (see Chapter 10)

public class Stack {
  int max = 1;
  int[] arr;
  int head;

  public Stack() {
    arr = new int[max];
    head = 0;
  }

  public void push(int x){
    if(head < max){
      arr[head] = x;
      head++;
    } else {
      max *= 2;
      int[] newarr = new int[max];
      System.arraycopy(arr, 0, newarr, 0, max / 2);
      arr = newarr;
      push(x);
    }
  }

  public int pop(){
    head--;
    return arr[head];
  }

  public static void main(String[] args){
    Stack s = new Stack();
    // s.push(0); s.push(1); s.push(2);
    // System.out.println(s.pop());
  }
}
