import java.io.*;
import java.util.*;

// Implemented version of Problem 10.3 in CLRS, 3rd ed.

class MergeableHeapSorted {
  ArrayList<Integer> ll = new ArrayList<Integer>();

  int parent(int x){
    return (x - 1) / 2;
  }

  int left(int x){
    return 2 * x + 1;
  }

  int right(int x){
    return 2 * x + 2;
  }

  public MergeableHeapSorted(ArrayList<Integer> vals, boolean isSorted) { // O(1) time if already sorted; O(n log n time otherwise)
    if (!isSorted){
      Collections.sort(vals);
    }
    this.ll = vals;
  }

  public MergeableHeapSorted(ArrayList<Integer> vals) {
	  this.ll = vals;
  }

  // [Note: we cannot use the O(log n) algorithm because it does not preserve the property that ll is sorted.]
  void insert(int x){ // O(n) time
    int i = 0;
    while (ll.get(i) < x) {
      i++;
    }
    ll.add(i, x);
  }

  int min(){ // O(1) time
    return ll.get(0);
  }

  int extract_min() { // O(1) time
    return ll.remove(0);
  }

  static MergeableHeapSorted union(MergeableHeapSorted m, MergeableHeapSorted n){ // O(m + n)
    int i = 0; int j = 0;
    MergeableHeapSorted ret = new MergeableHeapSorted(new ArrayList<Integer>());
    int iter_max = n.ll.size() + m.ll.size();
    n.ll.add(Integer.MAX_VALUE); m.ll.add(Integer.MAX_VALUE);
    while (i < n.ll.size() - 1 || j < m.ll.size() - 1){
      if (m.ll.get(j) < n.ll.get(i)){
        ret.ll.add(m.ll.get(j));
        j++;
      } else {
        ret.ll.add(n.ll.get(i));
        i++;
      }
    }
    n.ll.remove(n.ll.size() - 1); m.ll.remove(m.ll.size() - 1);
    return ret;
  }


  @Override
  public String toString(){
      String s = "";
	  for (int i = 0; i < ll.size(); i++){
		  s += ll.get(i) + " ";
	  }
	  s += "\n";
	  return s;
  }
}

class MergeableHeapUnsorted {
  ArrayList<Integer> ll = new ArrayList<Integer>();

  int parent(int x){
    return (x - 1) / 2;
  }

  int left(int x){
    return 2 * x + 1;
  }

  int right(int x){
    return 2 * x + 2;
  }

  int min_child(int i){
    if (ll.size() <= 2 * i + 1) {
      return Integer.MAX_VALUE;
    } else if(ll.size() <= 2 * i + 2) {
      return ll.get(2 * i + 1);
    } else {
      return ll.get(2 * i + 2) < ll.get(2 * i + 1) ? ll.get(2 * i + 2) : ll.get(2 * i + 1);
    }
  }

  int min_child_ind (int i) {
    if (ll.size() <= 2 * i + 2){
      return 2 * i + 1;
    } else {
      return ll.get(2 * i + 2) < ll.get(2 * i + 1) ? 2 * i + 2 : 2 * i + 1;
    }
  }

  void ensure_heap () {
    for (int i = ll.size() - 1; i >= 0; i--) {
      int j = i;
      while (min_child(j) < ll.get(j)) {
        int temp = ll.get(j);
        ll.set(j, min_child(j));
        j = min_child_ind(j);
        ll.set(j, temp);
      }
    }
  }

  public MergeableHeapUnsorted(ArrayList<Integer> vals) { // O(1)
    this.ll = vals;
  }

  void insert(int x){ // O(log n)
    ll.add(x);
    int i = ll.size() - 1;
    while (ll.get(i) < ll.get(parent(i))) {
      int temp = ll.get(i);
      ll.set(i, ll.get(parent(i)));
      ll.set(parent(i), temp);
      i = parent(i);
    }
  }

  int min() { // O (1)
    return ll.get(0);
  }

  int extract_min() { // O (log n)
    int ret = ll.remove(0);
    int i = 0;
    while (i > min_child(i)) {
      int mci = min_child_ind(i);
      int temp = ll.get(mci);
      ll.set(mci, ll.get(i));
      ll.set(i, temp);
      i = mci;
    }
    return ret;
  }

  static MergeableHeapUnsorted union (MergeableHeapUnsorted m, MergeableHeapUnsorted n) {
	MergeableHeapUnsorted x = new MergeableHeapUnsorted(new ArrayList<Integer>());
	x.ll.addAll(n.ll);
	x.ll.addAll(m.ll);
	x.ensure_heap();
	return x;
  }

  @Override
  public String toString(){
      String s = "";
	  for (int i = 0; i < ll.size(); i++){
		  s += ll.get(i) + " ";
	  }
	  s += "\n";
	  return s;
  }
}

public class Problem_10_2 {

	public static void main(String[] args){
		MergeableHeapSorted m = new MergeableHeapSorted(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 5, 6, 7, 10, 11)));
		System.out.println(m.extract_min());
		System.out.println(m.min());
		MergeableHeapSorted n = new MergeableHeapSorted(new ArrayList<Integer>(Arrays.asList(4, 6, 8, 9, 10, 11)));
		MergeableHeapSorted v = MergeableHeapSorted.union(m, n);
		System.out.println(v.toString());
		
		MergeableHeapUnsorted p = new MergeableHeapUnsorted(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 5, 6, 7, 10, 11)));
		System.out.println(p.extract_min());
		System.out.println(p.min());
		MergeableHeapUnsorted q = new MergeableHeapUnsorted(new ArrayList<Integer>(Arrays.asList(4, 6, 8, 9, 10, 11)));
		MergeableHeapUnsorted u = MergeableHeapUnsorted.union(p, q);
		System.out.println(u.toString());
	}
}
