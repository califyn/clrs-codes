import java.io.*;
import java.util.*;
import java.lang.*;

public class Problem_12_2 {
	public static void main (String[] args) {
		String[] input = new String[]{"0", "100", "10", "", "1011", "011", "1110000111", "1001101111", "0101000110", "1010001010", "0000110010", "0101100001", "0110011111", "0111010001", "1011111010", "1100011000"};
		System.out.println("Original: " + Arrays.toString(input));
		int maxlen = -1;
		for (int i = 0; i < input.length; i++) {
			if (input[i].length() > maxlen) {
				maxlen = input[i].length();
			}
		}
		java.util.LinkedList<Integer> left = new java.util.LinkedList<Integer>();
		java.util.LinkedList<Integer> right = new java.util.LinkedList<Integer>();
		java.util.LinkedList<Integer> parent = new java.util.LinkedList<Integer>();
		java.util.ArrayList<Integer> type = new java.util.ArrayList<Integer>();

		left.add(-1);
		right.add(-1);
		parent.add(-1);
		type.add(0);
		for (int i = 0; i < input.length; i++) {
			int x = 0;
			for (int j = 0; j < input[i].length(); j++) {
				if (input[i].charAt(j) == '0') {
					if (left.get(x) == -1) {
						left.set(x, type.size());
						parent.add(x);
						left.add(-1);
						right.add(-1);
						type.add(0);
						x = left.get(x);
					} else {
						x = left.get(x);
					}
				} else if (input[i].charAt(j) == '1') {
					if (right.get(x) == -1) {
						right.set(x, type.size());
						parent.add(x);
						left.add(-1);
						right.add(-1);
						type.add(0);
						x = right.get(x);
					} else {
						x = right.get(x);
					}
				}
			}
			type.set(x, 1);
		}

		java.util.ArrayList<Integer> process_stack = new java.util.ArrayList<Integer>();
		java.util.ArrayList<String> process_strings = new java.util.ArrayList<String>();
		java.util.ArrayList<String> sorted = new java.util.ArrayList<String>();
		process_stack.add(0, 0);
		process_strings.add(0, "");
		while (process_stack.size() > 0) {
			int process = process_stack.remove(0);
			String toadd = process_strings.remove(0);
			if (type.get(process) == 1) {
				if (process == 0) {
					sorted.add("");
				} else {
					sorted.add(toadd);
				}
			}
			if (right.get(process) != -1) {
				process_stack.add(0, right.get(process));
				process_strings.add(0, toadd + "1");
			}
			if (left.get(process) != -1) {
				process_stack.add(0, left.get(process));
				process_strings.add(0, toadd + "0");
			}
		}
		
		String ret = "[";
		for (int i = 0; i < sorted.size(); i++) {
			ret += "\"" + sorted.get(i) + "\", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		ret += "]";

		System.out.println("Sorted: " + ret);
	}
}
