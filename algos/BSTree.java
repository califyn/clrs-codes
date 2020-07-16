import java.io.*;
import java.util.*;
import java.lang.*;

public class BSTree {
	java.util.LinkedList<Integer> left = new java.util.LinkedList<Integer>();
	java.util.LinkedList<Integer> right = new java.util.LinkedList<Integer>();
	java.util.LinkedList<Integer> parent = new java.util.LinkedList<Integer>();
	java.util.LinkedList<Integer> vals = new java.util.LinkedList<Integer>();
	ArrayDeque<Integer> free = new ArrayDeque<Integer>();
	int root = -1;
	
	public BSTree () {
	}

	public String sortedWalk_g (int x) {
		if (x == -1) {
			return "";
		}
		String ret = "";
		ret += sortedWalk_g(left.get(x));
		if (left.get(x) != -1){
			ret += ", ";
		}
		ret += vals.get(x) + (right.get(x) == -1 ? "" : ", ");
		ret += sortedWalk_g(right.get(x));
		return ret;
	}

	public String sortedWalk () {
		return sortedWalk_g (root);
	}

	public int search (int x) {
		int k = root;
		while (k != -1 && vals.get(k) != x) {
			if (vals.get(k) > x) {
				k = left.get(k);
			} else {
				k = right.get(k);
			}
		}
		return k;
	}

	public int min_g (int x) {
		int k = x;
		int prev = root;
		while (k != -1) {
			prev = k;
			k = left.get(k);
		}
		return prev;
	}

	public int max_g (int x) {
		int k = x;
		int prev = root;
		while (k != -1) {
			prev = k;
			k = right.get(k);
		}
		return prev;
	}

	public int min () {
		return min_g(root);
	}
	
	public int max () {
		return max_g(root);
	}

	public int successor (int x) {
		if (right.get(x) != -1) {
			return min_g(right.get(x));
		}
		int y = parent.get(x);
		while (y != -1 && x == right.get(y)) {
			x = y;
			y = parent.get(y);
		}
		return y;
	}

	public int predecessor (int x) {
		if (left.get(x) != -1) {
			return min_g(left.get(x));
		}
		int y = parent.get(x);
		while (y != -1 && x == left.get(y)) {
			x = y;
			y = parent.get(y);
		}
		return y;
	}

	public void insert (int x) {
		int z = -1;
		int y = root;
		while (y != -1) {
			z = y;
			if (x < vals.get(y)){
				y = left.get(y);
			} else {
				y = right.get(y);
			}
		}
		int index = -1;
		if (free.size() == 0) {
			left.add(-1);
			right.add(-1);
			parent.add(-1);
			vals.add(-1);
			free.add(left.size() - 1);
		}
		index = free.poll();
		vals.set(index, x);
		parent.set(index, z);
		if (z == -1) {
			root = index;
		} else if (x < vals.get(z)) {
			left.set(z, index);
		} else {
			right.set(z, index);
		}
	}

	void transplant (int u, int v) {
		if (parent.get(u) == -1) {
			 root = v;
		} else if (u == left.get(parent.get(u))) {
			left.set(parent.get(u), v);
		} else {
			right.set(parent.get(u), v);
		}
		if (v != -1) {
			parent.set(v, parent.get(u));
		}
	}

	public void delete (int x) {
		if (left.get(x) == -1) {
			transplant(x, right.get(x));
		} else if (right.get(x) == -1) {
			transplant(x, left.get(x));
		} else {
			int y = min_g(right.get(x));
			if (parent.get(y) != x) {
				transplant(y, right.get(y));
				right.set(y, right.get(x));
				parent.set(right.get(y), y);
			}
			transplant(x, y);
			left.set(y, left.get(x));
			parent.set(left.get(y), y);
		}
	}

	public boolean checkBS (int x) {
		return (left.get(x) == -1 ? true : (vals.get(x) >= vals.get(left.get(x)) && checkBS(left.get(x)))) && (right.get(x) == -1 ? true : (vals.get(x) <= vals.get(right.get(x)) && checkBS(right.get(x))));
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "ROOT: " + root + "\n";
		for (int i = 0; i < parent.size(); i++) {
			ret += "# " + i + ": [val] " + vals.get(i) + ", [l/r/p] " + left.get(i) + ", " + right.get(i) + ", " + parent.get(i) + "\n";
		}
		return ret;
	}

	public static void main (String[] args) {
		BSTree b = new BSTree();
		b.insert(7047);
		b.insert(7623);
		b.insert(2168);
	}
}
