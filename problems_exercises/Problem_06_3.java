import java.io.*;
import java.util.*;

// Solution code for Problem 6.3 in CLRS, 3rd ed.

class YoungTableau {
  int[][] values; // Storage of the values inside the tableau
  int infinity = Integer.MAX_VALUE; // This is our "infinity" value

  public void check(){ // Check if the tableau is valid.  Throws IllegalArgumentException if it is not valid
    for(int i = 0; i < values.length; i++){
      for(int j = 0; j < values[0].length - 1; j++){
        if(values[i][j + 1] < values[i][j]){
          throw new IllegalArgumentException();
        }
      }
    }
    for(int i = 0; i < values[0].length; i++){
      for(int j = 0; j < values.length - 1; j++){
        if(values[j + 1][i] < values[j][i]){
          throw new IllegalArgumentException();
        }
      }
    }
  }

  public YoungTableau(int[][] values){ // Two dimensional initializer
    this.values = values;
    check();
  }

  int max(int a, int b){ // Maximum of two numbers
    return a >= b ? a : b;
  }

  int min(int a, int b){ // Minimum of two numbers
    return a <= b ? a : b;
  }

  void downwards(int i, int j){ // Move a number which is too BIG downwards to its proper place in the tableau
    int m = values.length;
    int n = values[0].length;
    if(values[i][j] == infinity && (i == m - 1 || values[i + 1][j] == infinity) && (j == n - 1 || values[i][j + 1] == infinity)){
      return;
    }
    if(i == m - 1 && j == n - 1){
      return;
    } else if(i == m - 1){
      if(values[i][j] > values[i][j + 1]){
        int temp = values[i][j + 1];
        values[i][j + 1] = values[i][j];
        values[i][j] = temp;
        downwards(i, j + 1);
      }
    } else if(j == n - 1){
      if(values[i][j] > values[i + 1][j]){
        int temp = values[i + 1][j];
        values[i + 1][j] = values[i][j];
        values[i][j] = temp;
        downwards(i + 1, j);
      }
    } else {
      int min = min(values[i][j], min(values[i + 1][j], values[i][j + 1]));
      if(min == values[i][j]){
        return;
      } else if(min == values[i + 1][j]){
        int temp = values[i + 1][j];
        values[i + 1][j] = values[i][j];
        values[i][j] = temp;
        downwards(i + 1, j);
      } else if(min == values[i][j + 1]){
        int temp = values[i][j + 1];
        values[i][j + 1] = values[i][j];
        values[i][j] = temp;
        downwards(i, j + 1);
      }
      return;
    }
  }

  void upwards(int i, int j){ // Move an element which is too SMALL upwards into its proper place in the tableau
    int m = values.length;
    int n = values[0].length;
    if(i == 0 && j == 0){
      return;
    } else if(i == 0){
      if(values[i][j] < values[i][j - 1]){
        int temp = values[i][j - 1];
        values[i][j - 1] = values[i][j];
        values[i][j] = temp;
        upwards(i, j - 1);
      }
    } else if(j == 0){
      if(values[i][j] < values[i - 1][j]){
        int temp = values[i - 1][j];
        values[i - 1][j] = values[i][j];
        values[i][j] = temp;
        upwards(i - 1, j);
      }
    } else {
      int max = max(values[i][j], max(values[i - 1][j], values[i][j - 1]));
      if(max == values[i][j]){
        return;
      } else if(max == values[i - 1][j]){
        int temp = values[i - 1][j];
        values[i - 1][j] = values[i][j];
        values[i][j] = temp;
        upwards(i - 1, j);
      } else if(max == values[i][j - 1]){
        int temp = values[i][j - 1];
        values[i][j - 1] = values[i][j];
        values[i][j] = temp;
        upwards(i, j - 1);
      }
      return;
    }
  }

  public YoungTableau(int[] values, int m, int n){ // One-dimensional initializer; orders the array into a Young Tableau [Part a]
    this.values = new int[m][n];
    for(int i = 0; i < values.length; i++){
      this.values[i / n][i % n] = values[i];
    }
    for(int i = values.length; i < m * n; i++){
      this.values[i / n][i % n] = infinity;
    }
    for(int i = m - 1; i >= 0; i--){
      for(int j = n - 1; j >= 0; j--){
        downwards(i, j);
      }
    }
  }

  public int extractmin(){ // Return and delete the minimum from the tableau, shifting all other elements as needed [Part c]
    int ret = values[0][0];
    values[0][0] = infinity;
    downwards(0, 0);
    return ret;
  }

  public void insert(int i){ // Insert a new element into the tableau, decreasing the number of infinity elements by 1 [Part d]
    int row = 0;
    int m = values.length; int n = values[0].length;
    while(values[row][n - 1] != infinity){
      row++;
    }
    if(row > m - 1){
      throw new ArrayIndexOutOfBoundsException();
    }
    int column = n - 1;
    while(values[row][column] == infinity){
      column--;
      if(column == -1){
        break;
      }
    }
    column++;
    values[row][column] = i;
    upwards(row, column);
  }

  public int[] tableausort(){ // Sort n numbers in O(n^(3/2)) times using the tableau [Part e]
    int numFill = 0;
    int m = values.length; int n = values[0].length;
    for(int i = 0; i < m - 1; i++){
      for(int j = 0; j < n - 1; j++){
        if(values[i][j] != infinity){
          numFill++;
        } else {
          break;
        }
      }
      if(values[i][0] == infinity){
        break;
      }
    }
    int[] ret = new int[numFill];
    for(int i = 0; i < numFill; i++){
      ret[i] = extractmin();
    }
    return ret;

  public boolean search(int x){  // Search for an integer x in the tableau [Part f]
    int m = values.length; int n = values[0].length;
    int column = n - 1;
    for(int i = 0; i < m - 1; i++){
      while(values[i][column] > x){
        column--;
        if(column == 0){
          return false;
        }
      }
      if(values[i][column] == x){
        return true;
      } else {
        column++;
      }
    }
    return false;
  }

  public String toString(){ // Return a string with all the elements of the tableau
    String ret = "[ ";
    for(int i = 0; i < values.length; i++){
      for(int j = 0; j < values[0].length; j++){
        if(values[i][j] == infinity){
          ret += "inf, ";
        } else {
          ret += values[i][j] + ", ";
        }
      }
      if(i != values.length - 1){
        ret += "\n";
      }
    }
    ret += "]";
    return ret;
  }
}

public class Problem_06_3 {

  public static void main(String[] args){
    // Creation and ordering of the tableau (part A)
    YoungTableau y = new YoungTableau(new int[]{9, 16, 3, 2, 4, 8, 5, 14, 12}, 4, 4);
    System.out.println("Creation of the Young Tableau: " + y.toString());

    // Extract min (part C)
    int min = y.extractmin();
    System.out.println("Extracted minimum: " + min);
    System.out.println("After extraction, the tableau has been shifted: " + y.toString());

    // Insert an element (part D)
    y.insert(10);
    System.out.println("After insertion: " + y.toString());

    // Sort using tableau (part E)
    int[] sorted = y.tableausort();
    System.out.println("Result of sorting the tableau: " + Arrays.toString(sorted));

    y = new YoungTableau(new int[]{9, 16, 3, 2, 4, 8, 5, 14, 12}, 4, 4); // Reset

    // Searching for an element (part F)
    boolean isIn = y.search(8);
    boolean isNotIn = y.search(11);
    System.out.println("Searching for 8 (should be true): " + isIn + ", searching for 11 (should be false): " + isNotIn);
  }

}
