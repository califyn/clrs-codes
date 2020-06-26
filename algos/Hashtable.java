import java.io.*;
import java.util.*;
import java.lang.*;

public class Hashtable {
	ArrayList<ArrayList<Integer>> ll = new ArrayList<ArrayList<Integer>>(0);

	public int hashfunc(int x){
		int p = 5;
		return x % p;
	}

	/*
	 * The routine above describes division method hashing.
	 *
	 * Here are some alternative hashing algorithms discussed in Chapter 11:
	 *
	 * Multiplicative method hashing:
	 *
	 * public int hashfunc(int x){
	 *	int m = 1024;
	 *	double A = (Math.sqrt(5) - 1)/2;
	 *	return ((int) Math.floor(m * k * A)) % m;
	 * }
	 *
	 * Universal hashing:
	 *
	 * int a = -1;
	 * int b = -1;
	 * int m = 1024;
	 * int p = 1000000007; // or other large prime
	 *
	 * public int hashfunc(int x){
	 *  int m = 1024;
	 * 	return ((a * x + b) % p) % m;
	 * }
	 *
	 * public Hashtable () {
	 * 	Random rand = new Random();
	 * 	a = rand.nextInt(p);
	 * 	b = rand.nextInt(p) + 1;
	 * }
	 *
	 */


	public Hashtable () {
	}

	public void insert (int x) {
		int index = hashfunc(x);
		if (ll.size() <= index) {
			int oldsize = ll.size();
			for (int j = oldsize; j < index + 1; j++){
				ll.add(new ArrayList<Integer>());
			}
		}
		ll.get(index).add(x);
	}

	public void delete (int x) {
		int index = hashfunc(x);
		ll.get(index).remove(ll.get(index).indexOf(x));
	}

	public boolean search (int x) {
		int index = hashfunc(x);
		if (ll.size() <= index) {
			int oldsize = ll.size();
			for (int j = oldsize; j < index + 1; j++){
				ll.add(new ArrayList<Integer>());
			}
		}
		return ll.get(index).contains(x);
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < ll.size(); i++){
			for (int j = 0; j < ll.get(i).size(); j++){
				ret += ll.get(i).get(j) + ", ";
			}
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}

	public static void main(String[] args){
		Hashtable h = new Hashtable();
		h.insert(1);
		h.insert(2);
		h.insert(3);
		h.insert(6);
		System.out.println("After adding 1, 2, 3, 6:" + h.toString());
		System.out.println("Is 1 in the list? " + h.search(1));
		System.out.println("Is 4 in the list? " + h.search(4));
		h.delete(3);
		System.out.println("After deleting 3: " + h.toString());
	}

}
