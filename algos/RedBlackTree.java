import java.io.*;
import java.util.*;
import java.lang.*;

// Implemented version of a red-black tree (see Ch. 13)

class RBNode { 
	public RBNode left;
	public RBNode right;
	public RBNode parent;
	public int type; // 0 for black, 1 for red
	public int val;

	public RBNode (int val, RBNode nil) {
		this.left = nil;
		this.right = nil;
		this.parent = nil;
		this.type = 0;
		this.val = val;
	}
}

public class RedBlackTree {
	RBNode root = null;
	RBNode nil;

	public RedBlackTree() { 
		RBNode nil = new RBNode(-1, null);
		nil.type = 0;
		nil.left = nil;
		nil.right = nil;
		nil.parent = nil;
		nil.val = -1;
		this.nil = nil;
		root = nil;
	}

	public boolean isNil (RBNode x) {
		return x.equals(nil);
	}

	public boolean isNotNil (RBNode x) {
		return !x.equals(nil);
	}

	public void left_rotate (RBNode x) {
		RBNode y = x.right;
		x.right = y.left;
		if (isNotNil(y.left)) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (isNil(x.parent)) {
			root = y;
		} else if (x.equals(x.parent.left)) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x; 
		x.parent = y;
	}

	public void right_rotate (RBNode x) {
		RBNode y = x.left;
		x.left = y.right;
		if (isNotNil(y.right)) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (isNil(x.parent)) {
			root = y;
		} else if (x.equals(x.parent.right)) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	public void transplant (RBNode x, RBNode y) {
		if (isNil(x.parent)) {
			root = y;
		} else if (x.equals(x.parent.left)) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.parent = x.parent;
	}

	@Override
	public String toString () {
		ArrayList<RBNode> dqueue = new ArrayList<RBNode>();
		int max = 3;
		dqueue.add(root);
		while (dqueue.size() > 0) {
			RBNode dtest = dqueue.remove(0);
			if (Integer.toString(dtest.val).length() > max) {
				max = Integer.toString(dtest.val).length();
			}
			if (isNotNil(dtest.left)) {
				dqueue.add(dtest.left);
			}
			if(isNotNil(dtest.right)) {
				dqueue.add(dtest.right);
			}
		}

		ArrayList<RBNode> stack = new ArrayList<RBNode>();
		ArrayList<Integer> depths = new ArrayList<Integer>();
		int pastd = -1;
		stack.add(root);
		depths.add(0);
		String ret = "";
		ArrayList<Integer> mods = new ArrayList<Integer>();
		while (stack.size() > 0) {
			RBNode test = stack.remove(stack.size() - 1);
			int depth = depths.remove(depths.size() - 1);
			if (depth - 1 != pastd) {
				ret += "\n";
				for (int i = 0; i < depth; i++) {
					if (mods.get(i) % 2 == 1 && i != 0) {
						ret += "|";
					} else {
						ret += " ";
					}
					for (int j = 0; j < max; j++) {
						ret += " ";
					}
				}
				ret += "\u11ab\u200b";
			} else {
				ret += "\u0442";
			}
			if (test.type == 1) {
				ret += "\u001B[31m";
			} else { 
				ret += "\u001B[37m";
			}
			if (isNil(test)) {
				ret += String.format("%" + max + "s", "NIL");
			} else {
				ret += String.format("%0" + max + "d", test.val);
			}
			if (test.type == 1) {
				ret += "\u001B[0m";
			}
			ret += "\u001B[35m";
			if (isNotNil(test)) {
				stack.add(test.left);
				stack.add(test.right);
				depths.add(depth + 1);
				depths.add(depth + 1);
			}
			pastd = depth;
			if (pastd >= mods.size()) {
				mods.add(0);
			}
			mods.set(depth, mods.get(depth) + 1);
		}
		ret += "\u001B[0m";
		ret = ret.substring(1, ret.length());
		ret = "-" + ret;
		return ret;
	}

	public void insert (int in) {
		RBNode z = new RBNode(in, nil);
		RBNode y = nil;
		RBNode x = root;
		while (isNotNil(x)) {
			y = x;
			if (z.val < x.val) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (isNil(y)) {
			root = z;
		} else if (z.val < y.val) {
			y.left = z;
		} else {
			y.right = z;
		}
		z.left = nil;
		z.right = nil;
		z.type = 1;
		// Color fixing
		while (z.parent.type == 1) {
			if (z.parent.equals(z.parent.parent.left)) {
				y = z.parent.parent.right;
				if (y.type == 1) {
					z.parent.type = 0;
					y.type = 0;
					z.parent.parent.type = 1;
					z = z.parent.parent;
				} else {
					if (z.equals(z.parent.right)) {
						z = z.parent;
						left_rotate(z);
					}
					z.parent.type = 0;
					z.parent.parent.type = 1;
					right_rotate(z.parent.parent);
				}
			} else {
				y = z.parent.parent.left;
				if (y.type == 1) {
					z.parent.type = 0;
					y.type = 0;	
					z.parent.parent.type = 1;
					z = z.parent.parent;
				} else {
					if (z.equals(z.parent.left)) {
						z = z.parent;
						right_rotate(z);
					}
					z.parent.type = 0;
					z.parent.parent.type = 1;
					left_rotate(z.parent.parent);
				}
			}
		}
		root.type = 0;
	}

	public void delete (RBNode z) {
		RBNode y = z;
		int origtype = y.type;
		RBNode x;
		if (isNil(z.left)) {
			x = z.right;
			transplant(z, z.right);
		} else if (isNil(z.right)) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = z.right;
			while (isNotNil(y.left)) {
				y = y.left;
			}
			origtype = y.type;
			x = y.right;
			if (y.parent.equals(z)) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.type = z.type;
		}
		if (origtype == 0) {
			while ((!x.equals(root)) && x.type == 0) {
				if (x.equals(x.parent.left)) {
					RBNode w = x.parent.right;
					if (w.type == 1) {
						w.type = 0;
						x.parent.type = 1;
						left_rotate(x.parent);
						w = x.parent.right;
					}
					if (w.left.type == 0 && w.right.type == 0) {
						w.type = 1;
						x = x.parent;
					} else {
						if (w.right.type == 0) {
							w.left.type = 0;
							w.type = 1;
							right_rotate(w);
							w = x.parent.right;
						}
						w.type = x.parent.type;
						x.parent.type = 0;
						w.right.type = 0;
						left_rotate(x.parent);
						x = root;
					}
				} else {
					RBNode w = x.parent.left;
					if (w.type == 1) {
						w.type = 0;
						x.parent.type = 1;
						right_rotate(x.parent);
						w = x.parent.left;
					}
					if (w.right.type == 0 && w.left.type == 0) {
						w.type = 1;
						x = x.parent;
					} else {
						if (w.left.type == 0) {
							w.right.type = 0;
							w.type = 1;
							left_rotate(w);
							w = x.parent.left;
						}
						w.type = x.parent.type;
						x.parent.type = 0;
						w.left.type = 0;
						right_rotate(x.parent);
						x = root;
					}
				}
			}
			x.type = 0;
		}
	}

	public boolean check () { // Check if the RB Tree is valid.
		// Check binary search tree properties.
		ArrayList<RBNode> queue = new ArrayList<RBNode>();
		queue.add(root);
		while (queue.size() > 0) {
			RBNode test = queue.remove(0);
			if (test.left.val != -1 && test.left.val > test.val) {
				return false;
			}
			if (test.right.val != -1 && test.right.val < test.val) {
				return false;
			}
			if (test.left.val != -1) {
				queue.add(test.left);
			}
			if (test.right.val != -1) {
				queue.add(test.right);
			}
		}

		// Check root is black.
		if (root.type != 0) {
			return false;
		}

		// Check that red nodes have black children.
		queue = new ArrayList<RBNode>();
		queue.add(root);
		while (queue.size() > 0) {
			RBNode test = queue.remove(0);
			if (test.type == 1) {
				if (test.left.type != 0 || test.right.type != 0) {
					return false;
				}
			}
			if (test.left.val != -1) {
				queue.add(test.left);
			}
			if (test.right.val != -1) {
				queue.add(test.right);
			}
		}

		// Check that all paths from a node to leaves have the same number of black nodes.
		queue = new ArrayList<RBNode>();
		queue.add(root);
		while (queue.size() > 0) {
			RBNode test = queue.remove(0);
			ArrayList<RBNode> miniqueue = new ArrayList<RBNode>();
			ArrayList<Integer> counts = new ArrayList<Integer>();
			miniqueue.add(test);
			counts.add(0);
			ArrayList<Integer> finals = new ArrayList<Integer>();
			while (miniqueue.size() > 0) {
				RBNode mtest = miniqueue.remove(0);
				if (isNil(mtest)) {
					finals.add(counts.get(0));
				} else {
					miniqueue.add(mtest.left);
					counts.add(counts.get(0) + 1 - mtest.type);
					miniqueue.add(mtest.right);
					counts.add(counts.get(0) + 1 - mtest.type);
				}
				counts.remove(0);
			}
			while (finals.size() > 1) {
				if (finals.get(0) != finals.get(1)) {
					return false;
				}
				finals.remove(0);
			}
			if (test.left.val != -1) {
				queue.add(test.left);
			}
			if (test.right.val != -1) {
				queue.add(test.right);
			}
		}
		return true;
	}

	public RBNode search(int x) {
		RBNode y = root;
		while (y.val != x) {
			if (y.val < x) {
				y = y.right;
			} else if (y.val > x) {
				y = y.left;
			} else if (y.val == -1) {
				return null;
			}
		}
		return y;
	}


	public static void main(String[] args) {
		RedBlackTree r = new RedBlackTree();
		int[] in = new int[]{26, 17, 41, 14, 21, 30, 47, 10, 16, 19, 23, 28, 38, 7, 12, 15, 20, 35, 39, 3};
		for(int i = 0; i < in.length; i++){
			r.insert(in[i]);
		}

		System.out.println(r.toString());
		System.out.println(r.check());

		for (int i = 0; i < in.length; i++) {
			r.delete(r.search(in[in.length - 1 - i]));	
		}
	}
}

