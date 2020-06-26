import java.io.*;
import java.util.*;
import java.lang.*;

public class OAHashtable {
	int[] arr = new int[1];
	int size;

	public int hashfunc(int k, int i){
		int p = size;
		int q = size - 1;
		return ((k % p) + i * (k % q)) % size;
	}

	/*
	 * The routine above describes double hashing.
	 *
	 * There are some alternative hashing algorithms discussed in Chapter 11:
	 *
	 * Linear probing:
	 *
	 * public int hashfunc(int k, int i){
	 *     return (k % 701) + i;
	 * }
	 *
	 * Quadratic probing:
	 *
	 * public int hashfun (int k, int i) {
	 *     int c1 = ;; // depends on size [size]
	 *     int c2 = ;; // depends on size [size]
	 *     return (k % 701) + c1 * i + c2 * i * i;
	 * }
	 *
	 */

	public OAHashtable(int size){
		this.size = size;
		this.arr = new int[size];
		for (int i = 0; i < size; i++){
			arr[i] = -1;
		}
	}

	public void insert(int k) throws Exception {
		int i = 0;
	x:	do {
			int j = hashfunc(k, i);
			if (arr[j] == -1){
				arr[j] = k;
				break x;
			} else {
				i++;
			}
		} while (i < size);
		if (i == size){
			throw new Exception("Hash table overflow");
		}
	}

	public int search(int k){
		int i = 0;
		int j = 0;
	x:  do {
			j = hashfunc(k, i);
			if (arr[j] == k){
				return j;
			}
			i++;
		} while (i < size && arr[j] != -1);
		return -1;
	}

	public void delete(int k){
		int i = 0;
	x:  do {
			int j = hashfunc(k, i);
			if (arr[j] == k){
				arr[j] = -2;
				break;
			}
			i++;
		} while (i < size);
		return;
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < size; i++){
			if (arr[i] >= 0){
				ret += arr[i] + ", ";
			}
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}

	public static void main(String[] args) throws Exception {
		OAHashtable h = new OAHashtable(7);
		h.insert(1);
		h.insert(6);
		h.insert(4);
		h.insert(5);
		h.insert(13);
		h.insert(8);
		//System.out.println("Hash table: " + h.toString());
		h.delete(1);
		h.delete(8);
		//System.out.println("Hash table after 1, 8 deleted: " + h.toString());
		//System.out.println("The element 6: " + h.arr[h.search(6)]);
	}
}
