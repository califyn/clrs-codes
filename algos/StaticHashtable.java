import java.io.*;
import java.util.*;
import java.lang.*;

class StaticHashtableSmall {
	int[] arr = new int[1];
	int size;
	int a = -1;
	int b = -1;
	int p = 1000000007;

	public int hashfunc(int x){
		return size == 0 ? 0 : (int) (((long) a * x + (long) b) % p) % size;
	}

	public StaticHashtableSmall(int[] values) {
		Random rand = new Random();
		this.size = values.length * values.length;
		arr = new int[size];
		for (int i = 0; i < size; i++){
			arr[i] = -1;
		}
		boolean okayAB = false;
	x:	while (!okayAB) {
			this.a = rand.nextInt(p);
			this.b = rand.nextInt(p) + 1;
			System.out.println(this.a + " " + this.b + " " + hashfunc(9));
			okayAB = true;
			for (int i = 0; i < values.length; i++) {
				if(arr[hashfunc(values[i])] == -1){			
					arr[hashfunc(values[i])] = values[i];
				} else {
					okayAB = false;
					for (int j = 0; j < size; j++){
						arr[j] = -1;
					}
					continue x;
				}
			}
		}
	}	

	public StaticHashtableSmall(Integer[] values) {
		Random rand = new Random();
		this.size = values.length * values.length;
		arr = new int[size];
		for (int i = 0; i < size; i++){
			arr[i] = -1;
		}
		boolean okayAB = false;
	x:	while (!okayAB) {
			this.a = rand.nextInt(p);
			this.b = rand.nextInt(p) + 1;
			okayAB = true;
			System.out.println(this.a + " " + this.b + " " + hashfunc(9));
			for (int i = 0; i < values.length; i++) {
				if(arr[hashfunc(values[i])] == -1){			
					arr[hashfunc(values[i])] = values[i];
				} else {
					okayAB = false;
					for (int j = 0; j < size; j++){
						arr[j] = -1;
					}
					continue x;
				}
			}
		}
	}	

	public boolean search (int x) {
		return size > 0 ? arr[hashfunc(x)] == x : false;
	}

	@Override
	public String toString () {
		String ret = "";
		int negCount = 0;
		for (int i = 0; i < size; i++) {
			if (arr[i] != -1) {
				ret += arr[i] + ", ";
			} else {
				negCount++;
			}
		}
		ret = ret.length() > 0 ? ret.substring(0, ret.length() - 2) : ret;
		return "[" +  ret + " || Empty: " + negCount + " objs]";
	}
}

class StaticHashtableLarge {
	StaticHashtableSmall[] arr = null;
	int size;
	int a = -1;
	int b = -1;
	int p = 1000000007;

	public int hashfunc(int x) {
		return (int) (((long) a * x + (long) b) % p) % size;
	}

	public StaticHashtableLarge(int[] values) {
		Random rand = new Random();
		this.size = values.length;
		arr = new StaticHashtableSmall[size];
		boolean okayAB = false;
	x:  while (!okayAB) {
			okayAB = true;
			this.a = rand.nextInt(p);
			this.b = rand.nextInt(p) + 1;
			java.util.LinkedList<Integer>[] ll = new java.util.LinkedList[size];
			for (int i = 0; i < size; i++) {
				ll[i] = new java.util.LinkedList<Integer>();
			}
			for (int i = 0; i < size; i++) {
				ll[hashfunc(values[i])].add(values[i]);
			}
			int totalSize = 0;
			for (int i = 0; i < size; i++) {
				totalSize += ll[i].size() * ll[i].size();
			}
			System.out.println(totalSize);
			if (totalSize > 6 * size) {
				okayAB = false;
				continue x;
			}
			for (int i = 0; i < size; i++) {
				System.out.println(Arrays.toString(ll[i].toArray()));
				arr[i] = new StaticHashtableSmall(ll[i].toArray(new Integer[ll[i].size()]));
				System.out.println(arr[i].toString());
			}
		}
	}

	public boolean search(int x){
		return arr[hashfunc(x)].search(x);
	}

	@Override
	public String toString () {
		String ret = "";
		for (int i = 0; i < size; i++) {
			ret += arr[i].toString() + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}
}

public class StaticHashtable {
	public static void main(String[] args) {
		StaticHashtableSmall s = new StaticHashtableSmall(new int[]{1, 4, 9, 6});
		System.out.println("Small hash table values: " + s.toString());
		System.out.println("Searching for 9: " + s.search(9));
		System.out.println("Searching for 8: " + s.search(8));

		StaticHashtableLarge l = new StaticHashtableLarge (new int[]{469, 621, 164, 864, 843, 289, 141, 901, 628, 82});
		System.out.println("Large hash table values: " + l.toString());
		System.out.println("Searching for 469: " + l.search(469));
		System.out.println("Seaching for 600: " + l.search(600));
	}
}
