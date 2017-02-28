package largest_time;

import java.util.*;

/* 
 * Given four integers, display the maximum time possible in 24 hour format HH:MM. 
 * For example, if you are give A = 1, B = 9, C = 9, D = 2 then output should be 19:29. 
 * Max time can be 23:59 and min time can be 00:00. If it is not possible to construct 
 * 24 hour time then return error. For example, given A = 1, B = 9, C = 7, D = 9 an 
 * error should be returned since minimum time represented by these integers is 17:99 
 * which is invalid.
 */

public class TimeFinder {
	static String getLargestTime(int a, int b, int c, int d) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(a, 1);
		if (map.containsKey(b)) map.put(b, map.get(b) + 1);
		else map.put(b, 1);
		if (map.containsKey(c)) map.put(c, map.get(c) + 1);
		else map.put(c, 1);
		if (map.containsKey(d)) map.put(d, map.get(d) + 1);
		else map.put(d, 1);
		
		int H1, H2, M1, M2;
		H1 = H2 = M1 = M2 = -1;
		
		// set H1
		for (int h1 = 2; h1 >= 0; h1--) {
			if (map.containsKey(h1)) {
				H1 = h1;
				if (map.get(h1) > 1) map.put(h1, map.get(h1) - 1);
				else map.remove(h1);
				break;
			}
		}
		
		// set H2
		switch (H1) {
		case -1:
			return "ERROR";
		case 2:
			for (int h2 = 3; h2 >= 0; h2--) {
				if (map.containsKey(h2)) {
					H2 = h2;
					if (map.get(h2) > 1) map.put(h2, map.get(h2) - 1);
					else map.remove(h2);
					break;
				}
			}
			if (H2 == -1) return "ERROR";
			break;
		default:
			for (int h2 = 9; h2 >= 0; h2--) {
				if (map.containsKey(h2)) {
					H2 = h2;
					if (map.get(h2) > 1) map.put(h2, map.get(h2) - 1);
					else map.remove(h2);
					break;
				}
			}
			break;
		}
		
		// set M1
		for (int m1 = 5; m1 >= 0; m1--) {
			if (map.containsKey(m1)) {
				M1 = m1;
				if (map.get(m1) > 1) map.put(m1, map.get(m1) - 1);
				else map.remove(m1);
				break;
			}
		}
		if (M1 == -1) return "ERROR";
		
		// set M2
		for (int m2 = 9; m2 >= 0; m2--) {
			if (map.containsKey(m2)) {
				M2 = m2;
				break;
			}
		}
		
		return "" + H1 + H2 + ":" + M1 + M2;
	}
	
	static String getLargestTime2(int a, int b, int c, int d) {
		List<Integer> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		int H1, H2, M1, M2;
		H1 = H2 = M1 = M2 = -1;
		
		// set H1
		for (int h1 : list)
			if (h1 < 3 && h1 > H1) H1 = h1;
		if (H1 == -1) return "ERROR";
		list.remove(Integer.valueOf(H1));
		
		// set H2
		for (int h2 : list) {
			if (H1 == 2) {
				if (h2 < 4 && h2 > H2) H2 = h2;
			} else {
				if (h2 > H2) H2 = h2;
			}
		}
		if (H2 == -1) return "ERROR";
		list.remove(Integer.valueOf(H2));
		
		// set M1
		for (int m1 : list) {
			if (m1 < 6 && m1 > M1) M1 = m1; 
		}
		if (M1 == -1) return "ERROR";
		list.remove(Integer.valueOf(M1));
		
		// set M2
		M2 = list.remove(0);
		
		return "" + H1 + H2 + ":" + M1 + M2;
	}
	
	public static void main(String[] args) {
		System.out.println(getLargestTime(1,2,3,4) + "\t" + getLargestTime2(1,2,3,4));
		System.out.println(getLargestTime(0,9,6,5) + "\t" + getLargestTime2(0,9,6,5));
		System.out.println(getLargestTime(2,5,3,4) + "\t" + getLargestTime2(2,5,3,4));
		System.out.println(getLargestTime(2,2,2,4) + "\t" + getLargestTime2(2,2,2,4));
		System.out.println(getLargestTime(1,1,2,2) + "\t" + getLargestTime2(1,1,2,2));
		System.out.println(getLargestTime(0,0,0,0) + "\t" + getLargestTime2(0,0,0,0));
		System.out.println(getLargestTime(3,1,6,0) + "\t" + getLargestTime2(3,1,6,0));
	}
}
